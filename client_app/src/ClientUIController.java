import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class ClientUIController {
    public TextArea txtMassageArea;
    public TextField txtMassage;
    public Button sendBtn;

    static Socket socket = null;
    static DataInputStream dataInputStream;
    static DataOutputStream dataOutputStream;


    public void initialize(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    socket= new Socket("localhost",5000);
                    dataInputStream= new DataInputStream(socket.getInputStream());
                    dataOutputStream= new DataOutputStream(socket.getOutputStream());

                    String messageIn = "";

                    while (!messageIn.equals("end")){
                        messageIn= dataInputStream.readUTF();
                        txtMassageArea.appendText("\nServe:"+messageIn.trim()+"\n");
                    }
                }catch (IOException e){
                    e.printStackTrace();
                }
            }
        }).start();
    }


    public void sendMassage(ActionEvent actionEvent) throws IOException {
        String replay = "";
        replay = txtMassage.getText();
        txtMassageArea.appendText("\t\t\t\t\t\t\t\tClient:" +replay.trim());
        dataOutputStream.writeUTF(replay);
        txtMassage.setText("");
    }
}
