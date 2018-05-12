package client;

import user.User;
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
        System.out.print("Write your username: ");
        String name = in.next();
        String func = "";
        try{
            ServerSocket tmpsocket = new ServerSocket(0);
            int lport = tmpsocket.getLocalPort();

            // server setup
            socketG = new Socket(serverAddress, 3030);
            DataOutputStream out = new DataOutputStream(socketG.getOutputStream());

            out.writeUTF(""+  lport);
            out.writeUTF(name);

            ObjectInputStream input = new ObjectInputStream(socketG.getInputStream());
            UserRepository usrRep = (UserRepository) input.readObject();

            socketG.close();
            input.close();
            while(!func.equals("close")){

                System.out.println("Online Hosts: ");
                usrRep.remove("localhost", lport);
                usrRep.print();

                //p2p setup
                System.out.print("Enter your action: ");
                func = in.next();
                if(func.equals("connect")){
                    int i = in.nextInt();
                    User connectedUser = usrRep.find(i);
                    System.out.println("connecting to the user...");
                    socket = new Socket(connectedUser.getAddress(), connectedUser.getPort());
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
            }
            tmpsocket.close();
        }catch (BindException e){
            System.out.println(e.getMessage());
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }
}
