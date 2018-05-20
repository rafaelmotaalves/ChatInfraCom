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

    public int getCurrent(){
        return current;
    }

    public User get(int i){
        return array[i];
    }

    public void doubleSize(){
        this.size = 2 * this.size;
        User aux[] = new User[this.size];
        for(int i = 0; i < this.current ; i++){
            aux[i] = array[i];
        }
        array = aux;
    }
    public String getOnlineUsers(){
        if(this.current > 0){
            String result = "Online Users\n";
            for(int i = 0 ; i < this.current; i++){
                 result += "["+ i +"]" + " Name: " + array[i].getName() + "\n";
            }
            return result;
        }else{
            return "No users online Right now";
        }
    }

    public void insert(User newUser){
        if(this.current == this.size){
            doubleSize();
        }
        array[this.current] = newUser;
        this.current++;
    }

    public User find(int i) {
        return array[i];
    }

    public void remove(User user){
        for(int i = 0; i < current; i++){
            if(array[i].equals(user)){
                array[i] = array[this.current-1];
                this.current--;
            }
        }
    }

}
