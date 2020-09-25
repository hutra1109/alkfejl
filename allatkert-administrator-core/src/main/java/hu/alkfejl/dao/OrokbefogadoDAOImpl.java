package hu.alkfejl.dao;

import hu.alkfejl.model.Allat;
import hu.alkfejl.model.Konyveles;
import hu.alkfejl.model.Orokbefogado;
//import hu.alkfejl.view.AllatAddDialog;


import java.sql.*;


import java.util.ArrayList;
import java.util.List;

public class OrokbefogadoDAOImpl implements OrokbefogadoDAO{

   // private static final String CONN_STR = "jdbc:sqlite:allatkert-projekt.db";
    private static final String SELECT_OB = "SELECT nev,elerhetoseg,felvetelIdopontja FROM Orokbefogado;";
    private static final String SELECT_OB_USER = "SELECT username,password FROM Orokbefogado;";
    private static final String SELECT_ADOPTED = "SELECT Allat.nev,Allat.faj,Allat.szoveg,Allat.szulEv FROM Konyveles,Allat WHERE Allat.nev = Konyveles.allatNev;";
    private static final String DELETE_O = "DELETE FROM Orokbefogado where elerhetoseg = ?;";

    private static final String CREATE_OB = "CREATE TABLE IF NOT EXISTS Orokbefogado("+
            "nev text NOT NULL," +
            "elerhetoseg text NOT NULL PRIMARY KEY," +
            "felvetelIdopontja DATE NOT NULL," +
            "username text NOT NULL," +
            "password text NOT NULL);";
    private static final String CREATE_ALLAT = "CREATE TABLE IF NOT EXISTS Allat(" +
            "nev text," +
            "faj text NOT NULL," +
            "fenykep text,"+
            "szoveg text," +
            "szulEv int NOT NULL," +
            "PRIMARY KEY(nev,faj,szulEv));";

    private static final String CREATE_K = "CREATE TABLE IF NOT EXISTS Konyveles(obNev text NOT NULL, allatNev text, mikor DATE NOT NULL, leiras text DEFAULT NULL, tamogatasTipus text NOT NULL, osszeg integer DEFAULT NULL, mennyiseg integer DEFAULT NULL, tamogatasGyakorisag text NOT NULL, PRIMARY KEY(obNev,allatNev),FOREIGN KEY(obNev) REFERENCES Orokbefogado(nev),FOREIGN KEY (allatNev) REFERENCES Allat(nev));";
    private static final String INSERT_OB = "INSERT INTO Orokbefogado VALUES (?,?,?,?,?);";
    private static final String SELECT_ALLAT = "SELECT * FROM Allat;";

    private static final String INSERT_ALLAT = "INSERT INTO Allat VALUES (?,?,?,?,?);";
    private static final String SELECT_K = "SELECT * FROM Konyveles;";

    private static final String INSERT_K = "INSERT INTO Konyveles VALUES (?,?,?,?,?,?,?,?);";
    private static final String DELETE_A = "DELETE FROM Allat where nev = ? and faj = ? and szulEv = ?;";
    private static final String DELETE_K = "DELETE FROM Konyveles where obNev = ? and allatNev = ?;";
    private static final String UPDATE_O = "UPDATE Orokbefogado SET nev = ?, elerhetoseg = ?, felvetelIdopontja = ? where elerhetoseg=?;";
    private static final String UPDATE_A = "UPDATE Allat SET nev = ?, faj = ?,fenykep=?,szoveg = ?, szulEv = ? where nev = ? and faj = ? and szulEv = ?;";



    public void initializeTable() {
        try(Connection conn = DriverManager.getConnection(DBConfig.DB_CONN_STR); Statement st = conn.createStatement())
        {
            st.executeUpdate(CREATE_K);
            st.executeUpdate(CREATE_OB);
            st.executeUpdate(CREATE_ALLAT);


        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public OrokbefogadoDAOImpl() {

        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        initializeTable();
    }

    @Override
    public List<Orokbefogado> listAll() {
        List<Orokbefogado> result = new ArrayList<>();
        try(Connection conn = DriverManager.getConnection(DBConfig.DB_CONN_STR);
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(SELECT_OB))
        {
            while(rs.next()) {
                Orokbefogado o = new Orokbefogado(
                        rs.getString(1),
                        rs.getString(2),
                        rs.getDate(3)
                );
                result.add(o);
            }

        }catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return result;
    }

    @Override
    public List<Orokbefogado> listAllUser() {
        List<Orokbefogado> result = new ArrayList<>();
        try(Connection conn = DriverManager.getConnection(DBConfig.DB_CONN_STR);
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(SELECT_OB_USER))
        {
            while(rs.next()) {
                Orokbefogado o = new Orokbefogado(
                        rs.getString(1),
                        rs.getString(2)
                );
                result.add(o);
            }

        }catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return result;
    }

    @Override
    public boolean addOrokbefogado(Orokbefogado o) {
        try(Connection conn = DriverManager.getConnection(DBConfig.DB_CONN_STR);
            PreparedStatement st = conn.prepareStatement(INSERT_OB))
        {
            st.setString(1,o.getNev());
            st.setString(2,o.getElerhetoseg());
            st.setDate(3, o.getFelvetelIdopontja());
            st.setString(4,o.getUsername());
            st.setString(5,o.getPassword());
            int res = st.executeUpdate();
            if(res == 1) {
                return true;
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return false;
    }

    @Override
    public List<Allat> listAllAllat() {
        List<Allat> result = new ArrayList<>();
        try(Connection conn = DriverManager.getConnection(DBConfig.DB_CONN_STR);
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(SELECT_ALLAT))
        {

            while(rs.next()) {
                Allat a = new Allat(
                        rs.getString(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getString(4),
                        rs.getInt(5)

                );
                result.add(a);
            }

        }catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return result;
    }

    @Override
    public boolean addAllat(Allat a) {
        try(Connection conn = DriverManager.getConnection(DBConfig.DB_CONN_STR);
            PreparedStatement st = conn.prepareStatement(INSERT_ALLAT))
        {

            st.setString(1,a.getNev());
            st.setString(2,a.getFaj());
            st.setString(3,a.getFenykep());
            st.setString(4,a.getSzoveg());
            st.setInt(5,a.getSzulEv());
            int res = st.executeUpdate();
            if(res == 1) {
                return true;
            }

        } catch (Exception throwables) {
            throwables.printStackTrace();
        }
        return false;
    }
    @Override
    public List<Konyveles> listAllKonyveles() {
        List<Konyveles> result = new ArrayList<>();
        try(Connection conn = DriverManager.getConnection(DBConfig.DB_CONN_STR);
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(SELECT_K))
        {
            while(rs.next()) {
                Konyveles k = new Konyveles(
                        rs.getString(1),
                        rs.getString(2),
                        rs.getDate(3),
                        rs.getString(4),
                        rs.getString(5),
                        rs.getInt(6),
                        rs.getInt(7),
                        rs.getString(8)
                );
                result.add(k);
            }

        }catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return result;
    }

    @Override
    public boolean addKonyveles(Konyveles k) {
        try(Connection conn = DriverManager.getConnection(DBConfig.DB_CONN_STR);
            PreparedStatement st = conn.prepareStatement(INSERT_K))
        {
            st.setString(1,k.getObNev());
            st.setString(2,k.getAllatNev());
            st.setDate(3, k.getMikor());
            st.setString(4,k.getLeiras());
            st.setString(5,k.getTamogatasTipus());
            st.setInt(6, k.getOsszeg());
            st.setInt(7,k.getMennyiseg());
            st.setString(8,k.getTamogatasGyakorisag());
            int res = st.executeUpdate();
            if(res == 1) {
                return true;
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean deleteOrokbefogado(Orokbefogado o) {
        try(Connection conn = DriverManager.getConnection(DBConfig.DB_CONN_STR);
            PreparedStatement st = conn.prepareStatement(DELETE_O))
        {
            st.setString(1,o.getElerhetoseg());

            int res = st.executeUpdate();
            if(res == 1) {
                return true;
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean updateOrokbefogado(String nev, String elerhetoseg, Date date, String kulcs) {
        try(Connection conn = DriverManager.getConnection(DBConfig.DB_CONN_STR);
            PreparedStatement st = conn.prepareStatement(UPDATE_O))
        {
            st.setString(1,nev);
            st.setString(2,elerhetoseg);
            st.setDate(3, date);
            st.setString(4,kulcs);

            int res = st.executeUpdate();
            if(res == 1) {
                return true;
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean deleteAllat(Allat a) {
        try(Connection conn = DriverManager.getConnection(DBConfig.DB_CONN_STR);
            PreparedStatement st = conn.prepareStatement(DELETE_A))
        {
            st.setString(1,a.getNev());
            st.setString(2,a.getFaj());
            st.setInt(3,a.getSzulEv());

            int res = st.executeUpdate();
            if(res == 1) {
                return true;
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean deleteKonyveles(Konyveles k) {
        try(Connection conn = DriverManager.getConnection(DBConfig.DB_CONN_STR);
            PreparedStatement st = conn.prepareStatement(DELETE_K))
        {
            st.setString(1,k.getObNev());
            st.setString(2,k.getAllatNev());


            int res = st.executeUpdate();
            if(res == 1) {
                return true;
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean updateAllat(String nev, String faj, String fenykep,String szoveg, int szulEv, String kulcs1, String kulcs2, int kulcs3) {
        try(Connection conn = DriverManager.getConnection(DBConfig.DB_CONN_STR);
            PreparedStatement st = conn.prepareStatement(UPDATE_A))
        {
            st.setString(1,nev);
            st.setString(2,faj);
            st.setString(3,fenykep);
            st.setString(4,szoveg);
            st.setInt(5,szulEv);
            st.setString(6,kulcs1);
            st.setString(7,kulcs2);
            st.setInt(8,kulcs3);

            int res = st.executeUpdate();
            if(res == 1) {
                return true;
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return false;
    }

    @Override
    public List<Allat> listNotAdopted() {
        List<Allat> result = new ArrayList<>();
        try(Connection conn = DriverManager.getConnection(DBConfig.DB_CONN_STR);
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(SELECT_ADOPTED))
        {

            while(rs.next()) {
                Allat a = new Allat(
                        rs.getString(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getString(4),
                        rs.getInt(5)

                );
                result.add(a);
            }

        }catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return result;
    }

}

