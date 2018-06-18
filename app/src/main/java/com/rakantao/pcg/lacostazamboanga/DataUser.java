package com.rakantao.pcg.lacostazamboanga;

public class DataUser {
    public String Usertype;
    public String email;
    public String Password;
    public String FirstName;
    public String MiddleName;
    public String LastName;

    public DataUser(){

    }

    public DataUser(String user_type, String email, String password){
        this.Usertype = user_type;
        this.email = email;
        this.Password = password;
    }
}
