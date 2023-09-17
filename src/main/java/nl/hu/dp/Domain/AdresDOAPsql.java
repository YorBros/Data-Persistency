package nl.hu.dp.Domain;

import java.sql.*;

public class AdresDOAPsql implements AdresDAO{
    private Connection connection = null;

    public AdresDOAPsql(Connection inConnection) {
        this.connection = inConnection;
    }

    @Override
    public boolean save(Adres adres) {
        try {
            String q = "INSERT INTO adres (adres_id, postcode, huisnummer, straat, woonplaats, reiziger_id) VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement pst = connection.prepareStatement(q);
            pst.setInt(1, adres.getAdres_id());
            pst.setString(2, adres.getPostcode());
            pst.setString(3, adres.getHuisnummer());
            pst.setString(4, adres.getStraat());
            pst.setString(5, adres.getWoonplaats());
            pst.setInt(6, adres.getReiziger_id());
            return pst.execute();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    @Override
    public boolean update(Adres adres) {
        try {
            String q = "UPDATE adres SET postcode = ?, huisnummer = ?, straat = ?, woonplaats = ? WHERE adres_id = ?";
            PreparedStatement pst = connection.prepareStatement(q);
            pst.setString(1, adres.getPostcode());
            pst.setString(2, adres.getHuisnummer());
            pst.setString(3, adres.getStraat());
            pst.setString(4, adres.getWoonplaats());
            pst.setInt(5, adres.getAdres_id());
            return pst.execute();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    @Override
    public boolean delete(Adres adres) {
        try {
            String q = "DELETE FROM adres WHERE adres_id = ?";
            PreparedStatement pst = connection.prepareStatement(q);
            pst.setInt(1, adres.getAdres_id());
            return pst.execute();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    @Override
    public Adres findByReiziger(Reiziger reiziger) throws SQLException {
        try {
            String q = "SELECT * FROM adres WHERE reiziger_id = ?";
            PreparedStatement pst = connection.prepareStatement(q);
            pst.setInt(1, reiziger.getReiziger_id());
            ResultSet resultSet = pst.executeQuery();
            if (resultSet.next()) {
                return new Adres(
                        resultSet.getInt("adres_id"),
                        resultSet.getString("postcode"),
                        resultSet.getString("huisnummer"),
                        resultSet.getString("straat"),
                        resultSet.getString("woonplaats"),
                        resultSet.getInt("reiziger_id")
                );
            } return null;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }
}
