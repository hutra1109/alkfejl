package hu.alkfejl.dao;

import hu.alkfejl.model.Allat;
import hu.alkfejl.model.Konyveles;
import hu.alkfejl.model.Orokbefogado;

import java.sql.Date;
import java.util.List;

public interface OrokbefogadoDAO {
    List<Orokbefogado> listAll();
    List<Orokbefogado> listAllUser();

    boolean addOrokbefogado(Orokbefogado o);

    List<Allat> listAllAllat();

    boolean addAllat(Allat a);

    List<Konyveles> listAllKonyveles();

    boolean addKonyveles(Konyveles k);

    boolean deleteOrokbefogado(Orokbefogado o);
    boolean deleteAllat(Allat a);
    boolean deleteKonyveles(Konyveles k);

    boolean updateOrokbefogado(String nev, String elerhetoseg, Date date, String kulcs);
    boolean updateAllat(String nev, String faj, String fenykep,String szoveg, int szulEv,String kulcs1,String kulcs2,int kulcs3);

    List<Allat> listNotAdopted();


}
