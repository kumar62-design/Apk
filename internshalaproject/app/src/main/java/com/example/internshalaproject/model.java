package com.example.internshalaproject;

public class model {

    String dateofbirth,firstname,hometown,lastname,pimg;

    public model() {
//         need empthy constructure
    }

    public model(String dateofbirth, String firstname, String hometown, String lastname, String pimg) {
        this.dateofbirth = dateofbirth;
        this.firstname = firstname;
        this.hometown = hometown;
        this.lastname = lastname;
        this.pimg = pimg;
    }

    public String getDateofbirth() {
        return dateofbirth;
    }

    public void setDateofbirth(String dateofbirth) {
        this.dateofbirth = dateofbirth;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getHometown() {
        return hometown;
    }

    public void setHometown(String hometown) {
        this.hometown = hometown;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getPimg() {
        return pimg;
    }

    public void setPimg(String pimg) {
        this.pimg = pimg;
    }
}
