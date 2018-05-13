package client;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class MaintainServerConnection extends Thread{
    private DataOutputStream output;

    MaintainServerConnection(Socket socket) throws IOException {
        output = new DataOutputStream(socket.getOutputStream());
    }

    public void run() {
        while (true){
            try{
                while(true){
                    sleep(5000);
                    output.write(1);
                    System.out.println(1);
                }
            }catch (IOException e){

            }catch (InterruptedException e){

            }
        }

    }
}
