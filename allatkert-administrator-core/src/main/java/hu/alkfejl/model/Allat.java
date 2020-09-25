package hu.alkfejl.model;

import java.io.InputStream;
import java.sql.Blob;
import java.sql.Date;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Allat {

    private StringProperty nev = new SimpleStringProperty();
    private StringProperty faj = new SimpleStringProperty();
    private StringProperty fenykep = new SimpleStringProperty();
    private StringProperty szoveg = new SimpleStringProperty();
    private IntegerProperty szulEv = new SimpleIntegerProperty();

    public Allat(String nev, String faj,String fenykep,String szoveg, int szulEv) {
        this.nev.set(nev);
        this.faj.set(faj);
        this.fenykep.set(fenykep);
        this.szoveg.set(szoveg);
        this.szulEv.set(szulEv);
    }

    public Allat() {

    }

    public String getFenykep() {
        return fenykep.get();
    }

    public StringProperty fenykepProperty() {
        return fenykep;
    }

    public void setFenykep(String fenykep) {
        this.fenykep.set(fenykep);
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

    public String getFaj() {
        return faj.get();
    }

    public StringProperty fajProperty() {
        return faj;
    }

    public void setFaj(String faj) {
        this.faj.set(faj);
    }

    public String getSzoveg() {
        return szoveg.get();
    }

    public StringProperty szovegProperty() {
        return szoveg;
    }

    public void setSzoveg(String szoveg) {
        this.szoveg.set(szoveg);
    }

    public int getSzulEv() {
        return szulEv.get();
    }

    public IntegerProperty szulEvProperty() {
        return szulEv;
    }

    public void setSzulEv(int szulEv) {
        this.szulEv.set(szulEv);
    }
}
