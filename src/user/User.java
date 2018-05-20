package user;

import java.io.Serializable;
import java.net.Socket;

public class User implements Serializable {

    private String name;
    private String address;
    private int port;

    public User(String name,String address, int port){
        this.name = name;
        this.address = address;
        this.port = port;
    }
    public boolean equals(User user){
        return this.name.equals(user.getName()) && this.address.equals(user.getAddress())
                && this.port == user.getPort();
    }

    public String toString(){
        return "Name: " + this.name + " Address: "  + this.address +  " Port: " + this.port;
    }

    public String getName() { return name; }

    public String getAddress() {
        return address;
    }

    public int getPort() {
        return port;
    }

}
