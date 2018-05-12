package user;

import java.io.Serializable;

public class User implements Serializable {

    private String name;
    private String address;
    private int port;

    public User(String name,String address, int port){
        this.name = name;
        this.address = address;
        this.port = port;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getName() {
        return name;

    }

    public String getAddress() {
        return address;
    }

    public int getPort() {
        return port;
    }

}
