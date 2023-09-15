package nl.hu.dp;

import nl.hu.dp.Domain.Reiziger;
import nl.hu.dp.Domain.ReizigerDAOPsql;

import java.sql.SQLException;
import java.sql.*;

public class Main {
    private static Connection connection;

    public static void main(String[] args) throws SQLException {
        ReizigerDAOPsql rdao = new ReizigerDAOPsql(getConnection());
        Reiziger reiziger = new Reiziger(6, "J", "", "Jordy", Date.valueOf("1999-09-09"));
        rdao.findById(6);
        closeConnection();
    }

    private static Connection getConnection() throws SQLException {
        if (connection == null) {
            String url =
                    "jdbc:postgresql://localhost:5433/ovchip?user=postgres&password=Data1234";
            connection = DriverManager.getConnection(url);
        }
        return connection;
    }

    private static void closeConnection() throws SQLException {
        if (connection != null) {
            connection.close();
            connection = null;
        }
    }

    private static void testConnection() throws SQLException {
        getConnection();

        String q = "SELECT * FROM reiziger";
        PreparedStatement pst = connection.prepareStatement(q);
//        pst.setString(1, "reiziger");
        ResultSet resultSet = pst.executeQuery();
        System.out.println("Alle reizigers:");
        while (resultSet.next()) {
            String tussv = resultSet.getString("tussenvoegsel");
            if (tussv == null) {
                tussv = "";
            }
            System.out.printf("#%s: %s. %s %s (%s)%n",
                    resultSet.getString("reiziger_id"),
                    resultSet.getString("voorletters"),
                    tussv,
                    resultSet.getString("achternaam"),
                    resultSet.getString("geboortedatum"));
        }
    }
}