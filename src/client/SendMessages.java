package client;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class SendMessages  extends Thread{
    private DataOutputStream output;
    private Scanner in;


    /*
        Class responsible of sending the chat messages
     */


    SendMessages(Socket socket) throws IOException {
        output = new DataOutputStream(socket.getOutputStream());
        in = new Scanner(System.in);
    }

    public void run() {
        try{
            boolean connected = true;
            while(connected){
                try{
                    output.writeUTF(in.nextLine());
                }catch (IOException e){
                    connected = false;
                }
            }
        }catch (Exception e){
        }
    }
}
