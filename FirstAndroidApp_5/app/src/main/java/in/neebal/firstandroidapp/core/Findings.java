package in.neebal.firstandroidapp.core;

/**
 * Created by root on 5/1/16.
 */
public class Findings {
    private float height;
    private int age;
    private String address;
    private long id;
    public Findings(long i,float height, int age, String address) {
        id=i;
        this.height = height;
        this.age = age;
        this.address = address;
    }

    @Override
    public String toString() {
        return "Findings{" +
                "height=" + height +
                ", age=" + age +
                ", address='" + address + '\'' +
                '}';
    }

    public long getId() {
        return id;
    }

    public float getHeight() {
        return height;
    }

    public void setHeight(float height) {
        this.height = height;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
