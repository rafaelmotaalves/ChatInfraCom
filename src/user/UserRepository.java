package user;

public class UserRepository {
    private User array[];
    private int size;
    private int current;

    UserRepository(int size) {
        this.array =  new User[size];
        this.size = size;
        this.current = 0;
    }

    User [] getArray(){
        return array;
    }

    void doubleSize(){
        this.size = 2 * this.size;
        User aux[] = new User[this.size];
        for(int i = 0; i < this.current ; i++){
            aux[i] = array[i];
        }
        array = aux;
    }

    void insert(User newUser){
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
