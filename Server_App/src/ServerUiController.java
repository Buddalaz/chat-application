import javafx.event.ActionEvent;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerUiController {
    public TextArea txtMassageArea;
    public TextField txtMassage;

    static ServerSocket serverSocket;
    static DataOutputStream dataOutputStream;
    static DataInputStream dataInputStream;
    static Socket socket;

    String massageIn = "";

    public void initialize(){
        new Thread(()->{
            try {
                serverSocket = new ServerSocket(5000);
                System.out.println("Server Started Waiting for client!....");
                socket = serverSocket.accept();
                System.out.println("Client Acceped!...");

                dataInputStream = new DataInputStream(socket.getInputStream());
                dataOutputStream = new DataOutputStream(socket.getOutputStream());

                while (!massageIn.equals("end")){
                    massageIn = dataInputStream.readUTF();
                    txtMassageArea.appendText("\nClient:" +massageIn.trim()+"\n");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
    }

    public void sendMassage(ActionEvent actionEvent) throws IOException {

        String text = txtMassage.getText();
//        System.out.println(text);
        txtMassageArea.appendText("\t\t\t\t\t\t\t\tServer:" +text.trim());
        dataOutputStream.writeUTF(txtMassage.getText().trim()+"\n");
        txtMassage.setText("");

    }
}
