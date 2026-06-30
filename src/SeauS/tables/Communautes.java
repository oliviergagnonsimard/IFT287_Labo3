package SeauS.tables;

import SeauS.bdd.Connexion;
import SeauS.tuples.Communaute;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Communautes extends GestionTables {

    private final PreparedStatement stmtExisteCommunaute;
    private final PreparedStatement stmtExisteCommunaute_id;
    private final PreparedStatement stmtInsertCommunaute;
    private final PreparedStatement stmtUpdateCommunaute;
    private final PreparedStatement stmtDeleteCommunaute;

    public Communautes(Connexion cx) throws SQLException {
        super(cx);

        stmtExisteCommunaute = cx.getConnection().prepareStatement(
                "select idcommunaute, nom_communaute, nation, chef_communaute, coordonnees from communaute where nom_communaute = ?");
        stmtExisteCommunaute_id = cx.getConnection().prepareStatement(
                "select idcommunaute, nom_communaute, nation, chef_communaute, coordonnees from communaute where idcommunaute = ?");
        stmtInsertCommunaute = cx.getConnection().prepareStatement(
                "insert into communaute (nom_communaute, nation, chef_communaute, coordonnees) values (?,?,?,?)");
        stmtUpdateCommunaute = cx.getConnection().prepareStatement(
                "update communaute set nom_communaute = ?, nation = ?, chef_communaute = ?, coordonnees = ? where nom_communaute = ?");
        stmtDeleteCommunaute = cx.getConnection().prepareStatement(
                "delete from communaute where nom_communaute = ?");
    }

    public boolean existe(String nom) throws SQLException {
        stmtExisteCommunaute.setString(1, nom);

        ResultSet rs;
        try {
            rs = stmtExisteCommunaute.executeQuery();
            boolean result = rs.next();
            rs.close();
            return result;
        } catch (SQLException e) {
            throw new SQLException("Erreur dans l'exécution du Query SQL" + e);
        }
    }

    public ResultSet getCommunaute(String nom) throws SQLException {
        stmtExisteCommunaute.setString(1, nom);

        ResultSet rs;
        try {
            rs = stmtExisteCommunaute.executeQuery();
        } catch (SQLException e) {
            throw new SQLException("Erreur dans l'exécution du Query SQL: " + e);
        }

        return rs;
    }

    public ResultSet getCommunauteID(String idCommunaute) throws SQLException {
        stmtExisteCommunaute_id.setInt(1, Integer.parseInt(idCommunaute));

        ResultSet rs;
        try {
            rs = stmtExisteCommunaute_id.executeQuery();
        } catch (SQLException e) {
            throw new SQLException("Erreur dans l'exécution du Query SQL: " + e);
        }

        return rs;
    }

    /* Statements INSERT */
    public int ajouterCommunaute(Communaute communaute) throws SQLException {
        stmtInsertCommunaute.setString(1, communaute.nom_communaute);
        stmtInsertCommunaute.setString(2, communaute.Nation);
        stmtInsertCommunaute.setString(3, communaute.chef_communaute);
        stmtInsertCommunaute.setString(4, communaute.coordonnees);

        int rs = 0;
        try {
            rs = stmtInsertCommunaute.executeUpdate();
        } catch (SQLException e) {
            throw new SQLException("Erreur dans l'exécution du Query SQL: " + e);
        }

        return rs;
    }

    /* Statements UPDATE */
    public int editerCommunaute(Communaute communaute, String ancienNom) throws SQLException {

        stmtUpdateCommunaute.setString(1, communaute.nom_communaute);
        stmtUpdateCommunaute.setString(2, communaute.Nation);
        stmtUpdateCommunaute.setString(3, communaute.chef_communaute);
        stmtUpdateCommunaute.setString(4, communaute.coordonnees);
        stmtUpdateCommunaute.setString(5, ancienNom);

        int rs = 0;
        try {
            rs = stmtUpdateCommunaute.executeUpdate();
        } catch (SQLException e) {
            throw new SQLException("Erreur dans l'exécution du Query SQL: " + e);
        }

        return rs;
    }

    /* Statements DELETE */
    public int supprimerCommunaute(String nom) throws SQLException {
        stmtDeleteCommunaute.setString(1, nom);

        int rs = 0;
        try {
            rs = stmtDeleteCommunaute.executeUpdate();
        } catch (SQLException e) {
            throw new SQLException("Erreur dans l'exécution du Query SQL: " + e);
        }
        return rs;
    }
}
