package hu.alkfejl.dao;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class DBConfig {
    private DBConfig() {}

    public static final String DB_CONN_STR;

    static {
        Properties prop = new Properties();
        try(InputStream propInput = DBConfig.class.getResourceAsStream("/db.properties")) {
            prop.load(propInput);
        }catch (IOException e) {
            e.printStackTrace();
        }
        DB_CONN_STR = prop.getProperty("db_conn_str");
    }

}
