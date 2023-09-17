package nl.hu.dp;

import nl.hu.dp.Domain.Adres;
import nl.hu.dp.Domain.AdresDOAPsql;
import nl.hu.dp.Domain.Reiziger;
import nl.hu.dp.Domain.ReizigerDAOPsql;

import java.sql.SQLException;
import java.sql.*;
import java.util.List;

public class Main {
    private static Connection connection;

    public static void main(String[] args) throws SQLException {
        ReizigerDAOPsql rdao = new ReizigerDAOPsql(getConnection());
        AdresDOAPsql adao = new AdresDOAPsql(getConnection());
        System.out.println(adao.findByReiziger(rdao.findById(2)));
        System.out.println(rdao.findById(2));
//        Adres adres = new Adres(6, "1234AB", "1", "Straat", "Stad", 6);
//        adao.save(adres);

        closeConnection();
    }

    /**
     * P2. Reiziger DAO: persistentie van een klasse
     *
     * Deze methode test de CRUD-functionaliteit van de Reiziger DAO
     *
     * @throws SQLException
     */
    private static void testReizigerDAO(ReizigerDAOPsql rdao) throws SQLException {
        System.out.println("\n---------- Test ReizigerDAO -------------");

        // Haal alle reizigers op uit de database
        List<Reiziger> reizigers = rdao.findAll();
        System.out.println("[Test] ReizigerDAO.findAll() geeft de volgende reizigers:");
        for (Reiziger r : reizigers) {
            System.out.println(r);
        }
        System.out.println();

        // Maak een nieuwe reiziger aan en persisteer deze in de database
        String gbdatum = "1981-03-14";
        Reiziger sietske = new Reiziger(77, "S", "", "Boers", java.sql.Date.valueOf(gbdatum));
        System.out.print("[Test] Eerst " + reizigers.size() + " reizigers, na ReizigerDAO.save() ");
        rdao.save(sietske);
        reizigers = rdao.findAll();
        System.out.println(reizigers.size() + " reizigers\n");

        System.out.print("[Test] Eerst " + rdao.findById(77) + " reizigers, na ReizigerDAO.update() ");
        sietske.setAchternaam("Melk");
        rdao.update(sietske);
        System.out.println(rdao.findById(77) + "\n");

        System.out.println("[Test] ReizigerDAO.findByGbdatum() geeft de volgende reizigers:");
        System.out.println(rdao.findByGbdatum("1981-03-14") + "\n");

        // Voeg aanvullende tests van de ontbrekende CRUD-operaties in.
        System.out.print("[Test] Eerst " + reizigers.size() + " reizigers, na ReizigerDAO.delete() ");
        rdao.delete(sietske);
        reizigers = rdao.findAll();
        System.out.println(reizigers.size() + " reizigers\n");


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