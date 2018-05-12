package client;

import user.UserRepository;

import java.io.DataOutputStream;
import java.io.ObjectInputStream;
import java.net.BindException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class TCPclient {

    public static void main(String args[]) {
        Scanner in = new Scanner(System.in);
        Socket socket, socketG;
        String serverAddress = "localhost" ;
        int dport;
        System.out.println("Write your username: ");
        String name = in.next();
        String address, func = "";
        try{
            while(!func.equals("close")){
                ServerSocket tmpsocket = new ServerSocket(0);
                int lport = tmpsocket.getLocalPort();
                System.out.println(lport);
                // server setup
                socketG = new Socket("localhost", 3030);
                DataOutputStream out = new DataOutputStream(socketG.getOutputStream());
                out.write(lport);
                out.writeUTF(name);
                ObjectInputStream input = new ObjectInputStream(socketG.getInputStream());
                UserRepository usrRep = (UserRepository) input.readObject();
                usrRep.print();
                socketG.close();
                input.close();
                //p2p setup
                System.out.print("Enter your action: ");
                func = in.next();
                if(func.equals("connect")){
                    address = in.next();
                    dport = in.nextInt();
                    System.out.println("connecting to the user...");
                    socket = new Socket(address, dport);
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
                snd.join();
                socket.close();
                tmpsocket.close();
            }
        }catch (BindException e){
            System.out.println(e.getMessage());
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }
}
