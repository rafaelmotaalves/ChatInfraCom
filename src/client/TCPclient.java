package client;

import jdk.management.jfr.RecordingInfo;
import user.User;
import user.UserRepository;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.lang.Thread;
import java.util.Scanner;

/*
    This class is responsible for the client administration and user interface via the command line
 */


public class TCPclient {

    public static void main(String args[]) {
        Scanner in = new Scanner(System.in);
        Socket socketG;
        String serverAddress = "localhost" , func;
        System.out.println("WELCOME TO INFRA COM CHAT");
        System.out.print("Enter a username: ");
        String name = in.next();
        try{
            // Opens server socket on random open port so it can receive connects from other clients
            ServerSocket svrSocket = new ServerSocket(0);
            int lport = svrSocket.getLocalPort();

            while(true){

                // connects to application administration server
                socketG = new Socket(serverAddress, 3030);
                ObjectInputStream input = new ObjectInputStream(socketG.getInputStream());
                DataOutputStream out = new DataOutputStream(socketG.getOutputStream());

                // receives userRepository object with all the currently online users

                UserRepository usrRep = (UserRepository) input.readObject();

                // sends to the server the user's name and the port he is waiting for connection
                out.writeInt(lport);
                out.writeUTF(name);

                // starts server connection control thread
                MaintainServerConnection mnt = new MaintainServerConnection(socketG);
                mnt.start();

                Thread msg = new SendMessages();
                msg.start();

                for(int i = 0; i < usrRep.getCurrent(); i++){
                    String uName = usrRep.get(i).getName();
                    System.out.println(uName + " is online");
                    Socket tmpS = new Socket( usrRep.get(i).getAddress(), usrRep.get(i).getPort());

                    DataOutputStream tempOut = new DataOutputStream(tmpS.getOutputStream());
                    tempOut.writeUTF(name);

                    ((SendMessages) msg).insertSocket(tmpS);
                    new ReceiveMessages(tmpS,uName).start();
                }
                System.out.println("You are now connected to the chat:");



                while(true){
                    Socket socket = svrSocket.accept();
                    ((SendMessages) msg).insertSocket(socket);

                    DataInputStream tempIn = new DataInputStream(socket.getInputStream());
                    String uName = tempIn.readUTF();
                    System.out.println(uName + " is online now");

                    new ReceiveMessages(socket,uName).start();
                }

            }
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }
}
