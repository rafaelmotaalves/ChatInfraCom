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
            int clientPort = in.read();
            String clientName = in.readUTF();
            client = new User(clientName, "localhost", clientPort);
            this.userRep.insert(client);
            ObjectOutputStream out = new ObjectOutputStream(clientSocket.getOutputStream());
            userRep.print();
            out.writeObject(userRep);
        }catch (Exception e){

        }
    }
}
