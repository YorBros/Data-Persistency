package nl.hu.dp.Domain;

import java.sql.*;
import java.sql.SQLException;
import java.util.List;

public class ReizigerDAOPsql implements ReizigerDAO{
    private Connection connection = null;

    public ReizigerDAOPsql(Connection inConnection) {
        this.connection = inConnection;
    }

    @Override
    public boolean save(Reiziger reiziger) throws SQLException {
        String q = "INSERT INTO reiziger (reiziger_id, voorletters, tussenvoegsel, achternaam, geboortedatum) VALUES (?, ?, ?, ?, ?)";
        PreparedStatement pst = connection.prepareStatement(q);
        pst.setInt(1, reiziger.getReiziger_id());
        pst.setString(2, reiziger.getVoorletters());
        pst.setString(3, reiziger.getTussenvoegsel());
        pst.setString(4, reiziger.getAchternaam());
        pst.setDate(5, reiziger.getGeboortedatum());
        return pst.execute();
    }

    @Override
    public boolean update(Reiziger reiziger) throws SQLException {
        String q = "UPDATE reiziger SET voorletters = ?, tussenvoegsel = ?, achternaam = ?, geboortedatum = ? WHERE reiziger_id = ?";
        PreparedStatement pst = connection.prepareStatement(q);
        pst.setString(1, reiziger.getVoorletters());
        pst.setString(2, reiziger.getTussenvoegsel());
        pst.setString(3, reiziger.getAchternaam());
        pst.setDate(4, reiziger.getGeboortedatum());
        pst.setInt(5, reiziger.getReiziger_id());
        return pst.execute();
    }

    @Override
    public boolean delete(Reiziger reiziger) throws SQLException {
        String q = "DELETE FROM reiziger WHERE reiziger_id = ?";
        PreparedStatement pst = connection.prepareStatement(q);
        pst.setInt(1, reiziger.getReiziger_id());
        return pst.execute();
    }

    @Override
    public Reiziger findById(int id) throws SQLException {
        String q = "SELECT * FROM reiziger WHERE reiziger_id = ?";
        PreparedStatement pst = connection.prepareStatement(q);
        pst.setInt(1, id);
        ResultSet resultSet = pst.executeQuery();
        return null;
    }

    @Override
    public List<Reiziger> findByGbdatum(String datum) throws SQLException {
        String q = "SELECT * FROM reiziger WHERE geboortedatum = ?";
        PreparedStatement pst = connection.prepareStatement(q);
        pst.setDate(1, Date.valueOf(datum));
        ResultSet resultSet = pst.executeQuery();
        System.out.println(resultSet);
        return null;
    }

    @Override
    public List<Reiziger> findAll() throws SQLException {
        String q = "SELECT * FROM reiziger";
        PreparedStatement pst = connection.prepareStatement(q);
        ResultSet resultSet = pst.executeQuery();
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
        return null;
    }
}
