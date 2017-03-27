package in.neebal.firstandroidapp.core;

/**
 * Created by root on 31/12/15.
 */
public class SuperHero
{
    private    String name,phone,email;

    public SuperHero(String name, String phone, String email) {
        this.name = name;
        this.phone = phone;
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public String getPhone() {
        return phone;
    }

    public String getEmail() {
        return email;
    }
}
