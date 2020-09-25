package hu.alkfejl.model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.sql.Date;

public class Konyveles {
    private StringProperty obNev = new SimpleStringProperty();
    private StringProperty allatNev = new SimpleStringProperty();
    private Date mikor;
    private StringProperty leiras = new SimpleStringProperty();
    private StringProperty tamogatasTipus = new SimpleStringProperty();
    private IntegerProperty osszeg = new SimpleIntegerProperty();
    private IntegerProperty mennyiseg = new SimpleIntegerProperty();
    private StringProperty tamogatasGyakorisag = new SimpleStringProperty();

    public Konyveles(String obNev, String allatNev, Date mikor, String leiras, String tamogatasTipus, int osszeg, int mennyiseg, String tamogatasGyakorisag) {
        this.obNev.set(obNev);
        this.allatNev.set(allatNev);
        this.mikor = mikor;
        this.leiras.set(leiras);
        this.tamogatasTipus.set(tamogatasTipus);
        this.osszeg.set(osszeg);
        this.mennyiseg.set(mennyiseg);
        this.tamogatasGyakorisag.set(tamogatasGyakorisag);
    }

    public Konyveles() {
    }

    @Override
    public String toString() {
        return "Konyveles{" +
                "obNev='" + obNev + '\'' +
                ", allatNev='" + allatNev + '\'' +
                ", mikor=" + mikor +
                ", leiras='" + leiras + '\'' +
                ", tamogatasTipus='" + tamogatasTipus + '\'' +
                ", osszeg=" + osszeg +
                ", mennyiseg=" + mennyiseg +
                ", tamogatasGyakorisag='" + tamogatasGyakorisag + '\'' +
                '}';
    }

    public String getObNev() {
        return obNev.get();
    }

    public StringProperty obNevProperty() {
        return obNev;
    }

    public void setObNev(String obNev) {
        this.obNev.set(obNev);
    }

    public String getAllatNev() {
        return allatNev.get();
    }

    public StringProperty allatNevProperty() {
        return allatNev;
    }

    public void setAllatNev(String allatNev) {
        this.allatNev.set(allatNev);
    }

    public Date getMikor() {
        return mikor;
    }

    public void setMikor(Date mikor) {
        this.mikor = mikor;
    }

    public String getLeiras() {
        return leiras.get();
    }

    public StringProperty leirasProperty() {
        return leiras;
    }

    public void setLeiras(String leiras) {
        this.leiras.set(leiras);
    }

    public String getTamogatasTipus() {
        return tamogatasTipus.get();
    }

    public StringProperty tamogatasTipusProperty() {
        return tamogatasTipus;
    }

    public void setTamogatasTipus(String tamogatasTipus) {
        this.tamogatasTipus.set(tamogatasTipus);
    }

    public int getOsszeg() {
        return osszeg.get();
    }

    public IntegerProperty osszegProperty() {
        return osszeg;
    }

    public void setOsszeg(int osszeg) {
        this.osszeg.set(osszeg);
    }

    public int getMennyiseg() {
        return mennyiseg.get();
    }

    public IntegerProperty mennyisegProperty() {
        return mennyiseg;
    }

    public void setMennyiseg(int mennyiseg) {
        this.mennyiseg.set(mennyiseg);
    }

    public String getTamogatasGyakorisag() {
        return tamogatasGyakorisag.get();
    }

    public StringProperty tamogatasGyakorisagProperty() {
        return tamogatasGyakorisag;
    }

    public void setTamogatasGyakorisag(String tamogatasGyakorisag) {
        this.tamogatasGyakorisag.set(tamogatasGyakorisag);
    }
}
