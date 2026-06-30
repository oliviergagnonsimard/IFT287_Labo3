package SeauS.tables;

import SeauS.bdd.Connexion;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Parents extends GestionTables {

    private final PreparedStatement stmtExisteParent_id;
    private final PreparedStatement stmtInsertParent;
    private final PreparedStatement stmtUpdateParent;
    private final PreparedStatement stmtDeleteParent;

    private final PreparedStatement stmtGetParents;
    private final PreparedStatement stmtGetEnfants;

    public Parents(Connexion cx) throws SQLException {
        super(cx);

        stmtExisteParent_id = cx.getConnection().prepareStatement(
                "select idparent from parent where idparent = ?");
        stmtInsertParent = cx.getConnection().prepareStatement(
                "insert into parent (idparent, idenfant) values (?,?)");
        stmtUpdateParent = cx.getConnection().prepareStatement(
                "update parent set idparent = ? WHERE idenfant = ? AND idparent = ?");
        stmtDeleteParent = cx.getConnection().prepareStatement(
                "delete from parent where idparent = ? AND idenfant = ?");

        stmtGetParents = cx.getConnection().prepareStatement("select idparent from parent p LEFT JOIN compagnie c on c.idcompagnie = p.idenfant where c.nom_compagnie = ?");
        stmtGetEnfants = cx.getConnection().prepareStatement("select idenfant from parent p LEFT JOIN compagnie c on c.idcompagnie = p.idparent where c.nom_compagnie = ?");

    }

    public boolean existe(int idParent) throws SQLException {
        stmtExisteParent_id.setInt(1, idParent);

        ResultSet rs;
        try {
            rs = stmtExisteParent_id.executeQuery();

        } catch (SQLException e) {
            throw new SQLException("Erreur dans l'exécution du Query SQL: " + e);
        }

        return rs.next();
    }

    // On retourne les IDs des parents
    public List<Integer> getParents(String nom) throws SQLException {
        stmtGetParents.setString(1, nom);

        ResultSet rs;
        List<Integer> liste = new ArrayList<>();
        try {
            rs = stmtGetParents.executeQuery();
            int idParent;
            while (rs.next()) {
                idParent = rs.getInt("idparent");
                liste.add(idParent);
            }
        } catch (SQLException e) {
            throw new SQLException("Erreur dans l'exécution du Query SQL: " + e);
        }

        return liste;
    }

    public List<Integer> getEnfants(String nom) throws SQLException {
        stmtGetEnfants.setString(1, nom);

        ResultSet rs;
        List<Integer> liste = new ArrayList<>();
        try {
            rs = stmtGetEnfants.executeQuery();
            int idenfant;
            while (rs.next()) {
                idenfant = rs.getInt("idenfant");
                liste.add(idenfant);
            }
        } catch (SQLException e) {
            throw new SQLException("Erreur dans l'exécution du Query SQL: " + e);
        }

        return liste;
    }

    /* Statements INSERT */
    public int ajouterParent(String idParent, String idEnfant) throws SQLException {
        stmtInsertParent.setInt(1, Integer.parseInt(idParent));
        stmtInsertParent.setInt(2, Integer.parseInt(idEnfant));
        int rs = 0;
        try {
            rs = stmtInsertParent.executeUpdate();
        } catch (SQLException e) {
            throw new SQLException("Erreur dans l'exécution du Query SQL: " + e);
        }

        return rs;
    }

    /* Statements UPDATE */
    public int editerParent(String idParent, String idEnfant) throws SQLException {
        stmtUpdateParent.setInt(1, Integer.parseInt(idParent));
        stmtUpdateParent.setInt(2, Integer.parseInt(idEnfant));

        int rs = 0;
        try {
            rs = stmtUpdateParent.executeUpdate();
        } catch (SQLException e) {
            throw new SQLException("Erreur dans l'exécution du Query SQL: " + e);
        }

        return rs;
    }

    /* Statements DELETE */
    public int supprimerParent(String idParent, String idEnfant) throws SQLException {
        stmtDeleteParent.setInt(1, Integer.parseInt(idParent));
        stmtDeleteParent.setInt(2, Integer.parseInt(idEnfant));

        int rs = 0;
        try {
            rs = stmtDeleteParent.executeUpdate();
        } catch (SQLException e) {
            throw new SQLException("Erreur dans l'exécution du Query SQL: " + e);
        }

        return rs;
    }
}
