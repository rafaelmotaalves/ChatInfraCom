package server;

import java.net.ServerSocket;
import java.net.Socket;

import user.UserRepository;

public class TCPServer {


    /* Class responsible of getting new user's connections
     */

    public static void main(String args[]) {
        int port = 3030;
        UserRepository userRep = new UserRepository(10);

        try{
           System.out.println("Server running on port 3030...");
           ServerSocket tmpsocket = new ServerSocket(port);
           while (true){
               // waits for users to connect and starts a new clientTask thread for each user connected
               Socket clientSocket = tmpsocket.accept();
               new ClientTask(clientSocket, userRep).start();
           }
        }catch (Exception e){
            System.out.println(e.getMessage());
        }

    }
}
