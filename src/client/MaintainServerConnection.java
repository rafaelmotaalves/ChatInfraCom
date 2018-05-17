package client;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class MaintainServerConnection extends Thread{
    private DataOutputStream output;
    private boolean waiting;

    /*
        Class responsible of administration the client's connection to the user
     */

    MaintainServerConnection(Socket socket) throws IOException {
        output = new DataOutputStream(socket.getOutputStream());
        waiting = true;
    }

    public void kill(){
        waiting = false;
    }

    public void run() {
        while (true){
            try{
                // sends a fixed message every 2 seconds to the server
                while(this.waiting){
                    output.writeInt(1);
                    sleep(2000);
                }
                output.close();
            }catch (Exception e){
                System.out.println(e.getMessage());
            }
        }

    }
}
