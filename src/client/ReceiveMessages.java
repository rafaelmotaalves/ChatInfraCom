package client;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;

public class ReceiveMessages  extends Thread{
    private DataInputStream input;

    ReceiveMessages(Socket socket) throws IOException {
        input = new DataInputStream(socket.getInputStream());
    }


    /*
        Class responsible of receiving the chat messages
     */


    public void run() {
        try{
            String msg;
            boolean connected = true;
            while(connected) {
                try{
                    msg = input.readUTF();
                    System.out.println(msg);
                }catch (IOException e){
                    connected = false;
                }
            }
            System.out.println("User disconnected");
            input.close();
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }
}
