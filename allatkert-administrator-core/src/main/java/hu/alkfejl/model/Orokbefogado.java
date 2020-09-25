package hu.alkfejl.model;


import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.sql.Date;

public class Orokbefogado {
    private StringProperty nev = new SimpleStringProperty();
    private StringProperty elerhetoseg = new SimpleStringProperty();
    private Date felvetelIdopontja;
    private StringProperty username = new SimpleStringProperty();
    private StringProperty password = new SimpleStringProperty();

    public Orokbefogado(String nev, String elerhetoseg, Date felvetelIdopontja,String username, String pw) {
        this.nev.set(nev);
        this.elerhetoseg.set(elerhetoseg);
        this.felvetelIdopontja = felvetelIdopontja;
        this.username.set(username);
        this.password.set(pw);
    }
    public Orokbefogado(String nev, String elerhetoseg, Date felvetelIdopontja) {
        this.nev.set(nev);
        this.elerhetoseg.set(elerhetoseg);
        this.felvetelIdopontja = felvetelIdopontja;

    }
    public Orokbefogado(String uname, String pw) {
        this.username.set(uname);
        this.password.set(pw);
    }


    public Orokbefogado() {
    }

    @Override
    public String toString() {
        return "Orokbefogado{" +
                "nev='" + nev + '\'' +
                ", elerhetoseg='" + elerhetoseg + '\'' +
                ", felvetelIdopontja=" + felvetelIdopontja +
                '}';
    }

    public String getUsername() {
        return username.get();
    }

    public StringProperty usernameProperty() {
        return username;
    }

    public void setUsername(String username) {
        this.username.set(username);
    }

    public String getPassword() {
        return password.get();
    }

    public StringProperty passwordProperty() {
        return password;
    }

    public void setPassword(String password) {
        this.password.set(password);
    }

    public String getNev() {
        return nev.get();
    }

    public StringProperty nevProperty() {
        return nev;
    }

    public void setNev(String nev) {
        this.nev.set(nev);
    }

    public String getElerhetoseg() {
        return elerhetoseg.get();
    }

    public StringProperty elerhetosegProperty() {
        return elerhetoseg;
    }

    public void setElerhetoseg(String elerhetoseg) {
        this.elerhetoseg.set(elerhetoseg);
    }

    public Date getFelvetelIdopontja() {
        return felvetelIdopontja;
    }

    public void setFelvetelIdopontja(Date felvetelIdopontja) {
        this.felvetelIdopontja = felvetelIdopontja;
    }
}
