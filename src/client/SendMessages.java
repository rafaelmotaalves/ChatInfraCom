package client;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class SendMessages  extends Thread{
    private DataOutputStream output[];
    private Scanner in;
    private int current;
    private int size;

    /*
        Class responsible of sending the chat messages
     */


    SendMessages() throws IOException {
        this.size = 10;
        this.current = 0;
        output = new DataOutputStream[10];
        in = new Scanner(System.in);
    }


    public void insertSocket(Socket socket) throws  IOException{
        if(size == current) {
            this.size = this.size * 2;
            DataOutputStream newOut[] = new DataOutputStream[this.size];
            for (int i = 0; i < current; i++) {
                newOut[i] = output[i];
            }
            output = newOut;
        }
        output[current] = new DataOutputStream(socket.getOutputStream());
        current++;
    }

    public void run() {
        try{
            String msg = "";
            while(true){
                    msg = in.nextLine();
                    for(int i = 0 ; i < this.current; i++){
                        try{
                            output[i].writeUTF(msg);
                        }catch (IOException e){
                            output[i] = output[current-1];
                            current--;
                        }
                    }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
