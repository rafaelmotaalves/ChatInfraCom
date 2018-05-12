package server;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import user.UserRepository;
import user.User;

public class TCPServer {

    final static ExecutorService clientProcessingPool = Executors.newFixedThreadPool(10);


    public static void main(String args[]) {
        int port = 3030;
        UserRepository userRep = new UserRepository(10);

        try{
           ServerSocket tmpsocket = new ServerSocket(port);
           while (true){
               Socket clientSocket = tmpsocket.accept();
               int clientPort = clientSocket.getPort();
               String clientAddress = clientSocket.getInetAddress().toString();
               clientProcessingPool.submit( new ClientTask(clientSocket, userRep, clientAddress) );
           }


        }catch (IOException e){
            System.out.println(e.getMessage());
        }

    }
}
