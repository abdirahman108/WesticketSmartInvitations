package com.westechhub.westicketsmartinvitations.Model;

public class Users
{
    private String name, username, password, image, address;

    public Users()
    {

    }

    public Users(String name, String username, String password, String image, String address) {
        this.name = name;
        this.username = username;
        this.password = password;
        this.image = image;
        this.address = address;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
