package my.dev.shopapp.models;

import java.io.Serializable;

public class User implements Serializable {
    private int Id;
    private String Name;
    private String Password;
    public User(int id,String name, String password){
        Id = id;
        Name = name;
        Password = password;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }
}
