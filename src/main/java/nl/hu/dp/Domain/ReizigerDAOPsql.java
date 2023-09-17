package nl.hu.dp.Domain;

import java.sql.*;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ReizigerDAOPsql implements ReizigerDAO{
    private Connection connection = null;

    public ReizigerDAOPsql(Connection inConnection) {
        this.connection = inConnection;
    }

    @Override
    public boolean save(Reiziger reiziger) {
        try {
            String q = "INSERT INTO reiziger (reiziger_id, voorletters, tussenvoegsel, achternaam, geboortedatum) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement pst = connection.prepareStatement(q);
            pst.setInt(1, reiziger.getReiziger_id());
            pst.setString(2, reiziger.getVoorletters());
            pst.setString(3, reiziger.getTussenvoegsel());
            pst.setString(4, reiziger.getAchternaam());
            pst.setDate(5, reiziger.getGeboortedatum());
            return pst.execute();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    @Override
    public boolean update(Reiziger reiziger) {
        try {
            String q = "UPDATE reiziger SET voorletters = ?, tussenvoegsel = ?, achternaam = ?, geboortedatum = ? WHERE reiziger_id = ?";
            PreparedStatement pst = connection.prepareStatement(q);
            pst.setString(1, reiziger.getVoorletters());
            pst.setString(2, reiziger.getTussenvoegsel());
            pst.setString(3, reiziger.getAchternaam());
            pst.setDate(4, reiziger.getGeboortedatum());
            pst.setInt(5, reiziger.getReiziger_id());
            return pst.execute();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    @Override
    public boolean delete(Reiziger reiziger) {
        try {
            String q = "DELETE FROM reiziger WHERE reiziger_id = ?";
            PreparedStatement pst = connection.prepareStatement(q);
            pst.setInt(1, reiziger.getReiziger_id());
            return pst.execute();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    @Override
    public Reiziger findById(int id) {
        try {
            String q = "SELECT * FROM reiziger WHERE reiziger_id = ?";
            PreparedStatement pst = connection.prepareStatement(q);
            pst.setInt(1, id);
            ResultSet resultSet = pst.executeQuery();
            if (resultSet.next()){
                return new Reiziger(
                        resultSet.getInt("reiziger_id"),
                        resultSet.getString("voorletters"),
                        resultSet.getString("tussenvoegsel"),
                        resultSet.getString("achternaam"),
                        resultSet.getDate("geboortedatum"));
            }
            return null;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return null;
        }

    }

    @Override
    public List<Reiziger> findByGbdatum(String datum) {
        try {
            List <Reiziger> reizigers = new ArrayList<>();
            String q = "SELECT * FROM reiziger WHERE geboortedatum = ?";
            PreparedStatement pst = connection.prepareStatement(q);
            pst.setDate(1, Date.valueOf(datum));
            ResultSet resultSet = pst.executeQuery();
            while (resultSet.next()){
                reizigers.add(new Reiziger(
                        resultSet.getInt("reiziger_id"),
                        resultSet.getString("voorletters"),
                        resultSet.getString("tussenvoegsel"),
                        resultSet.getString("achternaam"),
                        resultSet.getDate("geboortedatum")));
            }
            return reizigers;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    @Override
    public List<Reiziger> findAll() {
        try {
            List <Reiziger> reizigers = new ArrayList<>();
            String q = "SELECT * FROM reiziger";
            PreparedStatement pst = connection.prepareStatement(q);
            ResultSet resultSet = pst.executeQuery();
            while (resultSet.next()){
                reizigers.add(new Reiziger(
                        resultSet.getInt("reiziger_id"),
                        resultSet.getString("voorletters"),
                        resultSet.getString("tussenvoegsel"),
                        resultSet.getString("achternaam"),
                        resultSet.getDate("geboortedatum")));
            }
            return reizigers;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }
}
