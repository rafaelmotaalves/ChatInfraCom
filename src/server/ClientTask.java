package server;

import user.User;
import user.UserRepository;

import javax.xml.crypto.Data;
import java.io.DataInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ClientTask extends Thread {
    private Socket clientSocket;
    private User client;
    private UserRepository userRep;


    ClientTask(Socket clientSocket, UserRepository userRep, String address){
        this.client  = null;
        this.userRep = userRep;
        this.clientSocket = clientSocket;
    }


    public void run() {
        try{
            DataInputStream in = new DataInputStream(clientSocket.getInputStream());
            String clientPort = in.readUTF();
            String clientName = in.readUTF();
            client = new User(clientName, "localhost", Integer.parseInt(clientPort));
            this.userRep.insert(client);
            ObjectOutputStream out = new ObjectOutputStream(clientSocket.getOutputStream());
            System.out.println(client.toString() + " connected");
            out.writeObject(userRep);
        }catch (Exception e){

        }
    }
}
