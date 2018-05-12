package user;

import java.io.Serializable;

public class UserRepository implements Serializable {
    private User array[];
    private int size;
    private int current;

    public UserRepository(int size) {
        this.array =  new User[size];
        this.size = size;
        this.current = 0;
    }

    public User [] getArray(){
        return array;
    }

    public void doubleSize(){
        this.size = 2 * this.size;
        User aux[] = new User[this.size];
        for(int i = 0; i < this.current ; i++){
            aux[i] = array[i];
        }
        array = aux;
    }
    public void print(){
        for(int i = 0 ; i < this.current; i++){
            System.out.println("Name: " + array[i].getName() + " Address: " + array[i].getAddress() + " " + "Port: " + array[i].getPort());
        }
    }

    public void insert(User newUser){
        array[this.current] = newUser;
        this.current++;
    }

    void remove(String address, int port){
        for(int i = 0; i < current; i++){
            if(array[i].getAddress().equals(address) && array[i].getPort() == port){
                array[i] = array[this.current-1];
                this.current--;
            }
        }
    }

}
