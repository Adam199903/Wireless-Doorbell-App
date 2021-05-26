package com.adam.iotappv2;

public class UserHelperClass {

    String Name, Username, Email, Password, PhoneNo;

    public UserHelperClass() {

    }

    public UserHelperClass(String name, String username, String email, String password, String phoneNo) {
        Name = name;
        Username = username;
        Email = email;
        Password = password;
        PhoneNo = phoneNo;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getUsername() {
        return Username;
    }

    public void setUsername(String username) {
        Username = username;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public String getPhoneNo() {
        return PhoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        PhoneNo = phoneNo;
    }
}
