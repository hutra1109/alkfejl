package hu.alkfejl.controller;

import hu.alkfejl.dao.OrokbefogadoDAO;
import hu.alkfejl.dao.OrokbefogadoDAOImpl;
import hu.alkfejl.model.Allat;
import hu.alkfejl.model.Konyveles;
import hu.alkfejl.model.Orokbefogado;

import java.sql.Date;
import java.util.List;

public class OrokbefogadoController {
    private OrokbefogadoDAO dao = new OrokbefogadoDAOImpl();



    public OrokbefogadoController() {

    }
    public boolean addOrokbefogado(Orokbefogado o) { return dao.addOrokbefogado(o); }

    public List<Orokbefogado> listAll() { return dao.listAll(); }

    public List<Orokbefogado> listAllUser() { return dao.listAllUser(); }

    public boolean addAllat(Allat a) { return dao.addAllat(a); }

    public List<Allat> listAllAllat() { return dao.listAllAllat(); }

    public boolean addKonyveles(Konyveles k) { return dao.addKonyveles(k); }

    public List<Konyveles> listAllKonyveles() { return dao.listAllKonyveles(); }

    public boolean deleteOrokbefogado(Orokbefogado o) { return dao.deleteOrokbefogado(o); }

    public boolean deleteAllat(Allat a) { return dao.deleteAllat(a); }

    public boolean deleteKonyveles(Konyveles k) { return dao.deleteKonyveles(k); }

    public boolean updateOrokbefogado(String nev, String elerhetoseg, Date date, String kulcs) { return dao.updateOrokbefogado(nev,elerhetoseg,date,kulcs); }

    public boolean updateAllat(String nev, String faj, String fenykep,String szoveg, int szulEv, String kulcs1, String kulcs2, int kulcs3) { return dao.updateAllat(nev,faj,fenykep,szoveg,szulEv,kulcs1,kulcs2,kulcs3); }
}
