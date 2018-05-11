package client;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;

public class ReceiveMessages  extends Thread{
    private DataInputStream input;


    ReceiveMessages(Socket socket) throws IOException {
        input = new DataInputStream(socket.getInputStream());
    }

    public void run() {
        try{
            String msg;
            while(true) {
                msg = input.readUTF();
                System.out.println(msg);
            }
        }catch (IOException e){
            System.out.println(e.getMessage());
        }
    }
}
