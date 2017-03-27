package core;

/**
 * Created by Owner on 31-01-2016.
 */
public class Donor {
   public String name,number,wtype,address;

    public Donor(String name, String number, String wtype, String address) {
        this.name = name;
        this.number = number;
        this.wtype = wtype;
        this.address = address;
    }

    @Override
    public String toString() {
        return "Donor{" +
                "name='" + name + '\'' +
                ", number='" + number + '\'' +
                ", wtype='" + wtype + '\'' +
                ", address='" + address + '\'' +
                '}';
    }

    public String getName() {

        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getWtype() {
        return wtype;
    }

    public void setWtype(String wtype) {
        this.wtype = wtype;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
