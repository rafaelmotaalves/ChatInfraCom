package client;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;

public class ReceiveMessages  extends Thread{
    private DataInputStream input;
    private String name;

    ReceiveMessages(Socket socket, String name) throws IOException {
        input = new DataInputStream(socket.getInputStream());
        this.name = name;
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
                    System.out.println(this.name +": "+ msg);
                }catch (IOException e){
                    connected = false;
                }
            }
            System.out.println(this.name + " disconnected");
            input.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
