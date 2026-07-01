package SeauS.collections;

import SeauS.bdd.Connexion;
import SeauS.documents.Compagnie;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Compagnies extends GestionTables {

    private final PreparedStatement stmtExisteCompagnie;
    private final PreparedStatement stmtExisteCompagnie_id;
    private final PreparedStatement stmtInsertCompagnie;
    private final PreparedStatement stmtUpdateCompagnie;
    private final PreparedStatement stmtDeleteCompagnie;
    private final PreparedStatement stmtSelectCompagnie;

    public Compagnies(Connexion cx) throws SQLException {
        super(cx);

        stmtExisteCompagnie = cx.getConnection().prepareStatement(
                "select idcompagnie, nom_compagnie, adresse from compagnie where nom_compagnie = ?");
        stmtExisteCompagnie_id = cx.getConnection().prepareStatement(
                "select idcompagnie, nom_compagnie, adresse from compagnie where idcompagnie = ?");
        stmtInsertCompagnie = cx.getConnection().prepareStatement(
                "insert into compagnie (nom_compagnie, adresse) values (?,?)");
        stmtUpdateCompagnie = cx.getConnection().prepareStatement(
                "update compagnie set nom_compagnie = ?, adresse = ? where nom_compagnie = ?");
        stmtDeleteCompagnie = cx.getConnection().prepareStatement(
                "delete from compagnie where nom_compagnie = ?");

        stmtSelectCompagnie = cx.getConnection().prepareStatement(
            "select * from compagnie where nom_compagnie = ?");
    }

    /* Statements SELECT */
    public boolean existe(String nom) throws SQLException {
        stmtExisteCompagnie.setString(1, nom);

        ResultSet rs;
        try {
            rs = stmtExisteCompagnie.executeQuery();
            boolean result = rs.next();
            rs.close();
            return result;
        } catch (SQLException e) {
            throw new SQLException("Erreur dans l'exécution du Query SQL" + e);
        }
    }

    public boolean existe(int idCompagnie) throws SQLException {
        stmtExisteCompagnie_id.setInt(1, idCompagnie);

        ResultSet rs;
        try {
            rs = stmtExisteCompagnie_id.executeQuery();
            boolean result = rs.next();
            rs.close();
            return result;
        } catch (SQLException e) {
            throw new SQLException("Erreur dans l'exécution du Query SQL" + e);
        }
    }

    public ResultSet getCompagnie(String nom) throws SQLException {
        stmtSelectCompagnie.setString(1, nom);

        ResultSet rs;
        try {
            rs = stmtSelectCompagnie.executeQuery();
        } catch (SQLException e) {
            throw new SQLException("Erreur dans l'exécution du Query SQL: " + e);
        }

        return rs;
    }

    public ResultSet getCompagnieWithID(Compagnie c) throws SQLException {
        stmtExisteCompagnie_id.setInt(1, c.idCompagnie);

        ResultSet rs;
        try {
            rs = stmtExisteCompagnie_id.executeQuery();
        } catch (SQLException e) {
            throw new SQLException("Erreur dans l'exécution du Query SQL: " + e);
        }

        return rs;
    }

    /* Statements INSERT */
    public int ajouterCompagnie(Compagnie c) throws SQLException {
        stmtInsertCompagnie.setString(1, c.nom_compagnie);
        stmtInsertCompagnie.setString(2, c.adresse);

        int rs = 0;
        try {
            rs = stmtInsertCompagnie.executeUpdate();
        } catch (SQLException e) {
            throw new SQLException("Erreur dans l'exécution du Query SQL: " + e);
        }

        return rs;
    }

    /* Statements UPDATE */
    public int editerCompagnie(Compagnie c, String ancienNom) throws SQLException {
        stmtUpdateCompagnie.setString(1, c.nom_compagnie);
        stmtUpdateCompagnie.setString(2, c.adresse);
        stmtUpdateCompagnie.setString(3, ancienNom);

        int rs = 0;
        try {
            rs = stmtUpdateCompagnie.executeUpdate();
        } catch (SQLException e) {
            throw new SQLException("Erreur dans l'exécution du Query SQL: " + e);
        }

        return rs;
    }

    /* Statements DELETE */
    public int supprimerCompagnie(String nom) throws SQLException {
        stmtDeleteCompagnie.setString(1, nom);

        int rs = 0;
        try {
            rs = stmtDeleteCompagnie.executeUpdate();
        } catch (SQLException e) {
            throw new SQLException("Erreur dans l'exécution du Query SQL: " + e);
        }

        return rs;
    }

}
