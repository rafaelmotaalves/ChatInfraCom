package client;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class SendMessages  extends Thread{
    private DataOutputStream output;
    private Scanner in;

    SendMessages(Socket socket) throws IOException {
        output = new DataOutputStream(socket.getOutputStream());
        in = new Scanner(System.in);
    }

    public void run() {
        try{
            String msg = "";
            while(true){
                output.writeUTF(in.nextLine());
            }
        }catch (IOException e){
            System.out.println("User disconnected");
        }
    }
}
