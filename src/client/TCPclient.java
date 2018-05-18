package client;

import user.User;
import user.UserRepository;
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
        Socket socket, socketG;
        String serverAddress = "localhost" , func;
        System.out.println("WELCOME TO INFRA COM CHAT:");
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
                User connectedUser = null;
                System.out.println(usrRep.getOnlineUsers());

                System.out.print("Enter your action [connect/wait]:");
                func = in.next();

                if(func.equals("connect")){

                    // connects to the chosen user
                    int i = in.nextInt();
                    connectedUser = usrRep.find(i);
                    socket = new Socket(connectedUser.getAddress(), connectedUser.getPort());

                }else if (func.equals("wait")){

                    // sends to the server the user's name and the port he is waiting for connection
                    out.writeInt(lport);
                    out.writeUTF(name);

                    // starts server connection control thread
                    MaintainServerConnection mnt = new MaintainServerConnection(socketG);
                    mnt.start();

                    // waits for othe user to connect to the client's server socket
                    System.out.println("Waiting connection");
                    socket = svrSocket.accept();

                    // kills server connection control thread
                    mnt.kill();
                }else{
                    System.out.println("Closing chat...");
                    break;
                }

                // starts chat opening ReceiveMessages and SendMessages threads
                System.out.println("You are now connected:");
                Thread rcv = new ReceiveMessages(socket);
                Thread snd = new SendMessages(socket);
                rcv.start();
                snd.start();


                // closes all sockets, DataStreams and Threads opened in the execution
                snd.join();
                out.close();
                socket.close();
                socketG.close();
            }
            in.close();
            svrSocket.close();
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }
}
