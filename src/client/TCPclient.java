package client;

import java.net.BindException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class TCPclient {

    public static void main(String args[]) {
        Scanner in = new Scanner(System.in);
        Socket socket;
        String address, func = "";
        System.out.print("Enter your port: ");
        int port = in.nextInt();
        try{
            ServerSocket tmpsocket = new ServerSocket(port);
            while(!func.equals("close")){
                System.out.print("Enter your action: ");
                func = in.next();

                if(func.equals("connect")){
                    System.out.print("Enter destination address: ");
                    address = in.next();
                    System.out.print("Enter destination port: ");
                    port = in.nextInt();
                    System.out.println("connecting to the user...");

                    socket = new Socket(address, port);
                }else if (func.equals("wait")){
                    System.out.println("waiting connection...");
                    socket = tmpsocket.accept();
                }else{
                    System.out.println("closing chat...");
                    break;
                }

                System.out.println("connection established");
                Thread rcv = new ReceiveMessages(socket);
                Thread snd = new SendMessages(socket);
                rcv.start();
                snd.start();
                rcv.join();
            }
            tmpsocket.close();
        }catch (BindException e){
            System.out.println(e.getMessage());
            System.out.println("other user disconnected");
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }
}
