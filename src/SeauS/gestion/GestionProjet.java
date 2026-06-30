package SeauS.gestion;

import SeauS.SeauSException;
import SeauS.bdd.Connexion;
import SeauS.tables.Projets;
import SeauS.tuples.Projet;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;

public class GestionProjet extends GestionTransactions {

    private final Projets projets;
    private final Connexion connexion;

    public GestionProjet(Projets projets) {
        super(projets.getConnexion());
        this.projets = projets;
        this.connexion = projets.getConnexion();

    }

    public int ajouterProjet(int idCommunaute, int idCompagnie, float budgetInitial, float budgetFinal, String charge, String dateAnnonce,
            String dateDebut, String dateFin, String etat_avancement) throws SQLException, SeauSException {
        Projet p = new Projet(
                idCommunaute,
                idCompagnie,
                budgetInitial,
                budgetFinal,
                charge,
                Date.valueOf(dateAnnonce),
                Date.valueOf(dateDebut),
                Date.valueOf(dateFin),
                etat_avancement);

        int rs = 0;
        try {
            rs = projets.ajouterProjet(p);
            connexion.commit();
        } catch (SQLException e) {
            connexion.rollback();
            throw new SQLException("Erreur dans l'ajout de projet: " + e);
        }

        return rs;
    }

    public int editerProjet(int idProjet, int idCommunaute, int idCompagnie, float budgetInitial, float budgetFinal, String charge, String dateAnnonce,
            String dateDebut, String dateFin, String etat_avancement) throws SQLException, SeauSException {

        Projet p = new Projet(
                idCommunaute,
                idCompagnie,
                budgetInitial,
                budgetFinal,
                charge,
                Date.valueOf(dateAnnonce),
                Date.valueOf(dateDebut),
                Date.valueOf(dateFin),
                etat_avancement);

        int rs = 0;
        try {
            if (!projets.existe(idProjet)) {
                throw new SeauSException("Projet existe déjà : " + idProjet);
            }
            rs = projets.editerProjet(p, idProjet);
            connexion.commit();
        } catch (SQLException | SeauSException e) {
            connexion.rollback();
            throw new SQLException("Erreur dans l'édition du projet: " + e);
        }

        return rs;
    }

    public void afficherProjet(int idProjet) throws SQLException, SeauSException {
        ResultSet rs;
        try {
            if (!projets.existe(idProjet)) {
                throw new SeauSException("Projet existe pas: " + idProjet);
            }

            rs = projets.getProjet(idProjet);
            connexion.commit();
        } catch (SQLException | SeauSException e) {
            connexion.rollback();
            throw e;
        }

        while (rs.next()) {
            System.out.println(rs.getInt("idprojet"));
            System.out.println(rs.getInt("idcompagnie"));
            System.out.println(rs.getInt("idcommunaute"));
            System.out.println(rs.getDate("date_annonce"));
            System.out.println(rs.getDate("date_debut"));
            System.out.println(rs.getDate("date_fin"));
            System.out.println(rs.getFloat("budget_initial"));
            System.out.println(rs.getFloat("budget_final"));
            System.out.println(rs.getString("charge_projet"));
            System.out.println(rs.getString("etat_avancement"));
        }

    }

    public int supprimerProjet(int idProjet) throws SeauSException, SQLException {
        int rs = 0;
        try {
            if (!projets.existe(idProjet)) {
                throw new SeauSException("Projet existe pas: " + idProjet);
            }

            rs = projets.supprimerProjet(idProjet);
            connexion.commit();
        } catch (SQLException | SeauSException e) {
            connexion.rollback();
            throw e;
        }

        return rs;
    }
}
