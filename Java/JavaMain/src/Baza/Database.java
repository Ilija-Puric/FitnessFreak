package Baza;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Database {

    private static final String USERNAME = "ilija";
    private static final String PASSWORD = "ananas123";
    private static final String CONN_STRING = "jdbc:mysql://localhost/appfitness";

    private static Connection konekcija = null;

    //Ovo je Singleton dizajn pattern
    public static Connection connectToDatabase() throws SQLException {
        if (konekcija == null) {
            try {
                konekcija = DriverManager.getConnection(CONN_STRING, USERNAME, PASSWORD);
                return konekcija;
            } catch (SQLException e) {
                PomocnaKlasa.greska(e);
                return null;
            }
        } else {
            return konekcija;
        }
    }

    public static ResultSet getResultSet(String sql) {
        try {
            Connection k = connectToDatabase();

            Statement stmt = k.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_READ_ONLY);
            ResultSet resultSet = stmt.executeQuery(sql);
            return resultSet;
        } catch (SQLException ex) {
            PomocnaKlasa.greska(ex);
            return null;
        }
    }

}
