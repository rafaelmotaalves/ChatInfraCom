package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import user.UserRepository;

public class TCPServer {



    public static void main(String args[]) {
        int port = 3030;
        UserRepository userRep = new UserRepository(10);

        try{
           ServerSocket tmpsocket = new ServerSocket(port);
           while (true){
               Socket clientSocket = tmpsocket.accept();
               String clientAddress = clientSocket.getInetAddress().toString();
               new ClientTask(clientSocket, userRep, clientAddress).start();
           }


        }catch (IOException e){
            System.out.println(e.getMessage());
        }

    }
}
