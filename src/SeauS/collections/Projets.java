package SeauS.tables;

import SeauS.SeauSException;
import SeauS.bdd.Connexion;
import SeauS.tuples.Projet;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Projets extends GestionTables {

    private final PreparedStatement stmtExisteProjet;
    private final PreparedStatement stmtExisteProjet_id;
    private final PreparedStatement stmtInsertProjet;
    private final PreparedStatement stmtUpdateProjet;
    private final PreparedStatement stmtDeleteProjet;

    private final PreparedStatement stmtSelectProjetsCommu;
    private final PreparedStatement stmtSelectProjetsComp;

    public Projets(Connexion cx) throws SQLException {
        super(cx);

        stmtExisteProjet = cx.getConnection().prepareStatement(
                "select idprojet, idcompagnie, idcommunaute, date_debut, date_fin, date_annonce, budget_initial, "
                + "budget_final, charge_projet, etat_avancement from projet where idprojet = ?");
        stmtExisteProjet_id = cx.getConnection().prepareStatement(
                "select idprojet, idcompagnie, idcommunaute, date_debut, date_fin, date_annonce, budget_initial, "
                + "budget_final, charge_projet, etat_avancement from projet where idprojet = ?");
        stmtInsertProjet = cx.getConnection().prepareStatement(
                "insert into projet (idcompagnie, idcommunaute, date_debut, date_fin, date_annonce, budget_initial, "
                + "budget_final, charge_projet, etat_avancement) values (?,?,?,?,?,?,?,?,?)");
        stmtUpdateProjet = cx.getConnection().prepareStatement(
                "update projet set idCompagnie = ?, idcommunaute = ?, date_debut = ?, date_fin = ?, date_annonce = ?,"
                + "budget_initial = ?, budget_final = ?, charge_projet = ?, etat_avancement = ? where idprojet = ?");
        stmtDeleteProjet = cx.getConnection().prepareStatement(
                "delete from projet where idprojet= ?");

        stmtSelectProjetsCommu = cx.getConnection().prepareStatement(
                "select p.* from projet p left join communaute c on p.idcommunaute = c.idcommunaute where c.nom_communaute= ?");

        stmtSelectProjetsComp = cx.getConnection().prepareStatement(
                "select p.* from projet p left join compagnie c on c.idcompagnie = p.idcompagnie where c.nom_compagnie= ?");
    }

    public boolean existe(int idProjet) throws SQLException {
        stmtExisteProjet.setInt(1, idProjet);

        ResultSet rs;
        try {
            rs = stmtExisteProjet.executeQuery();
            boolean result = rs.next();
            rs.close();
            return result;
        } catch (SQLException e) {
            throw new SQLException("Erreur dans l'exécution du Query SQL" + e);
        }
    }

    public ResultSet getProjet(int idProjet) throws SQLException {
        stmtExisteProjet_id.setInt(1, idProjet);

        ResultSet rs;
        try {
            rs = stmtExisteProjet_id.executeQuery();
        } catch (SQLException e) {
            throw new SQLException("Erreur dans l'exécution du Query SQL: " + e);
        }

        return rs;
    }


    /* Statements INSERT */
    public int ajouterProjet(Projet projet) throws SQLException {
        stmtInsertProjet.setInt(1, projet.idCompagnie);
        stmtInsertProjet.setInt(2, projet.idCommunaute);
        stmtInsertProjet.setDate(3, projet.dateDebut);
        stmtInsertProjet.setDate(4, projet.dateFin);
        stmtInsertProjet.setDate(5, projet.dateAnnonce);
        stmtInsertProjet.setFloat(6, projet.budgetInitial);
        stmtInsertProjet.setFloat(7, projet.budgetFinal);
        stmtInsertProjet.setString(8, projet.chargeProjet);
        stmtInsertProjet.setString(9, projet.etatAvancement);

        int rs = 0;
        try {
            rs = stmtInsertProjet.executeUpdate();
        } catch (SQLException e) {
            throw new SQLException("Erreur dans l'exécution du Query SQL: " + e);
        }

        return rs;
    }

//idCommunaute, idCompagnie, budgetInitial, budgetFinal, charge, dateAnnonce, dateDebut, dateFin, etatAvancement, idProjet
    /* Statements UPDATE */
    public int editerProjet(Projet projet, int idProjet) throws SQLException, SeauSException {
        stmtUpdateProjet.setInt(1, projet.idCompagnie);
        stmtUpdateProjet.setInt(2, projet.idCommunaute);
        stmtUpdateProjet.setDate(3, projet.dateDebut);
        stmtUpdateProjet.setDate(4, projet.dateFin);
        stmtUpdateProjet.setDate(5, projet.dateAnnonce);
        stmtUpdateProjet.setFloat(6, projet.budgetInitial);
        stmtUpdateProjet.setFloat(7, projet.budgetFinal);
        stmtUpdateProjet.setString(8, projet.chargeProjet);
        stmtUpdateProjet.setString(9, projet.etatAvancement);
        stmtUpdateProjet.setInt(10, idProjet);

        int rs = 0;
        try {
            rs = stmtUpdateProjet.executeUpdate();
        } catch (SQLException e) {
            throw new SQLException("Erreur dans l'exécution du Query SQL: " + e);
        }

        return rs;
    }

    /* Statements DELETE */
    public int supprimerProjet(int idProjet) throws SQLException {
        stmtDeleteProjet.setInt(1, idProjet);

        int rs = 0;
        try {
            rs = stmtDeleteProjet.executeUpdate();
        } catch (SQLException e) {
            throw new SQLException("Erreur dans l'exécution du Query SQL: " + e);
        }

        return rs;
    }

    public PreparedStatement getProjetsCommuStatement() {
        return stmtSelectProjetsCommu;
    }

    public PreparedStatement getProjetsCompStatement() {
        return stmtSelectProjetsComp;
    }

}
