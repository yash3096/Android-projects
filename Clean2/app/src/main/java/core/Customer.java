package core;
/**
 * Created by Owner on 23-01-2016.
 */
public class Customer {
    String name;
    String address;
    String Username;
    String Password;
    String Number;
    int id,points;
    public Customer(String name, String address, String username, String password, String number) {
        this.name = name;
        this.address = address;
        Username = username;
        Password = password;
        Number=number;
    }
    public String getNumber() {
        return Number;
    }
    public void setNumber(String number) {
        Number = number;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getAddress() {
        return address;
    }
    public void setAddress(String address) {
        this.address = address;
    }
    public String getUsername() {
        return Username;
    }
    public void setUsername(String username) {
        Username = username;
    }
    public String getPassword() {
        return Password;
    }
    public void setPassword(String password) {
        Password = password;
    }
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public int getPoints() {
        return points;
    }
    public void setPoints(int points) {
        this.points = points;
    }
}
