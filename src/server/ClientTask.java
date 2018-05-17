package server;

import user.User;
import user.UserRepository;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ClientTask extends Thread {
    private Socket clientSocket;
    private User client;
    private UserRepository userRep;

    /*
    Class responsible of administrating a single user connection

     */


    ClientTask(Socket clientSocket, UserRepository userRep){
        this.client  = null;
        this.userRep = userRep;
        this.clientSocket = clientSocket;
    }


    public void run() {
        try{
            // creates InputStreams and Send the current online users
            DataInputStream in = new DataInputStream(clientSocket.getInputStream());
            ObjectOutputStream out = new ObjectOutputStream(clientSocket.getOutputStream());
            out.writeObject(userRep);

            // gets the client port and his name and adds it to the repository of online users
            int clientPort = in.readInt();
            String clientName = in.readUTF();

            client = new User(clientName, clientSocket.getInetAddress().getHostName(),clientPort);
            System.out.println(client.toString() + " connected");
            userRep.insert(client);

            // reads the client maintain connection messages
            boolean connected = true;
            while(connected){
                try {
                    in.readInt();
                } catch (IOException e){
                    // detects client disconnected from the channel
                    connected = false;
                }
            }

            // Removes user from connected users repository and closes sockets
            userRep.remove(client);
            System.out.println(client.toString() + " disconnected");
            clientSocket.close();
        }catch (IOException e) {
            System.out.print(e.getMessage());
        }
    }
}
