package SeauS.gestion;

import SeauS.SeauSException;
import SeauS.bdd.Connexion;
import SeauS.collections.Compagnies;
import SeauS.collections.Parents;
import SeauS.collections.Projets;
import SeauS.documents.Compagnie;
import SeauS.documents.Projet;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class GestionCompagnie extends GestionTransactions {

    private final Parents parents;
    private final Compagnies compagnies;
    private final Projets projets;

    private final Connexion connexion;

    public GestionCompagnie(Compagnies compagnies, Parents parents, Projets projets) throws SQLException {
        super(projets.getConnexion());

        this.parents = parents;
        this.compagnies = compagnies;
        this.projets = projets;

        this.connexion = compagnies.getConnexion();
    }

    public int ajouterCompagnie(String nom, String adresse) throws SQLException, SeauSException {
        int rs = 0;
        try {
            if (compagnies.existe(nom)) {
                throw new SeauSException("Compagnie existe déjà : " + nom);
            }

            Compagnie c = new Compagnie(nom, adresse);
            rs = compagnies.ajouterCompagnie(c);

            connexion.commit();
        } catch (SQLException | SeauSException e) {
            connexion.rollback();
            throw e;
        }
        return rs;
    }

    // TODO : implémenter le reste des fonctions
    /* Statements SELECT */
    public List<Projet> afficherProjetsCompagnie(String nom) throws SeauSException, SQLException {
        PreparedStatement stmtSelectProjetsComp = projets.getProjetsCompStatement();

        stmtSelectProjetsComp.setString(1, nom);
        ResultSet rs = stmtSelectProjetsComp.executeQuery();

        List<Projet> liste = new ArrayList<>();
        while (rs.next()) {
            Projet p = getProjet(rs);
            liste.add(p);
        }

        return liste;
    }

    // Fct utilitaire qui retourne VRAI si la compagnie est un Parent ou Enfant d'une autre.
    private boolean verifierParents(String nom) throws SeauSException, SQLException {
        List<Integer> enf = parents.getEnfants(nom);
        List<Integer> total = parents.getParents(nom);
        total.addAll(enf);

        // for (int i : total) {
        //     System.out.println(i);
        // }
        return !total.isEmpty();
    }

    // Retourne une liste de tous les IDs des compagnies parentes à celle passée en paramètre
    public List<Integer> afficherParents(String nom) throws SeauSException, SQLException {
        List<Integer> liste = parents.getParents(nom);

        for (int i : liste) {
            System.out.println(i);
        }

        return liste;
    }

    public int supprimerCompagnie(String nom) throws SeauSException, SQLException {
        int rs = 0;
        try {
            if (!compagnies.existe(nom)) {
                throw new SeauSException("Compagnie existe pas: " + nom);
            }

            if (verifierParents(nom)) {
                throw new SeauSException("Compagnie a un lien enfant ou parent: " + nom);
            }

            rs = compagnies.supprimerCompagnie(nom);
            connexion.commit();
        } catch (SQLException | SeauSException e) {
            connexion.rollback();
            throw e;
        }

        return rs;
    }

    public int enleverParent(String nomParent, String nomEnfant) throws SeauSException, SQLException {
        int rs = 0;
        try {
            if (!compagnies.existe(nomParent)) {
                throw new SeauSException("Compagnie existe pas: " + nomParent);
            }
            if (!compagnies.existe(nomEnfant)) {
                throw new SeauSException("Compagnie existe pas: " + nomEnfant);
            }

            ResultSet rsParent = compagnies.getCompagnie(nomParent);
            rsParent.next();
            int idParent = rsParent.getInt("idcompagnie");
            rsParent.close();

            ResultSet rsEnfant = compagnies.getCompagnie(nomEnfant);
            rsEnfant.next();
            int idEnfant = rsEnfant.getInt("idcompagnie");
            rsEnfant.close();

            rs = parents.supprimerParent(String.valueOf(idParent), String.valueOf(idEnfant));
            connexion.commit();
        } catch (SQLException | SeauSException e) {
            connexion.rollback();
            throw e;
        }
        return rs;
    }

    public int editerCompagnie(String nom, String nouveauNom, String adresse) throws SeauSException, SQLException {
        int rs = 0;
        try {
            if (!compagnies.existe(nom)) {
                throw new SeauSException("Compagnie existe pas: " + nom);
            }

            Compagnie c = new Compagnie(nouveauNom, adresse);
            rs = compagnies.editerCompagnie(c, nom);
            connexion.commit();
        } catch (SQLException | SeauSException e) {
            connexion.rollback();
            throw e;
        }

        return rs;
    }

    public void afficherCompagnie(String nom) throws SeauSException, SQLException {
        ResultSet rs;
        try {
            if (!compagnies.existe(nom)) {
                throw new SeauSException("Compagnie existe pas: " + nom);
            }

            rs = compagnies.getCompagnie(nom);
            connexion.commit();
        } catch (SQLException | SeauSException e) {
            connexion.rollback();
            throw e;
        }

        while (rs.next()) {
            int id = rs.getInt("idcompagnie");
            String name = rs.getString("nom_compagnie");
            String adresse = rs.getString("adresse");

            System.out.println("CompagnieID: " + id + ", Nom: " + name + ", Adresse: " + adresse);
        }
    }

    public int ajouterParent(String nomParent, String nomEnfant) throws SQLException, SeauSException {
        int rs = 0;
        try {
            if (!compagnies.existe(nomParent)) {
                throw new SeauSException("Compagnie parent existe pas: " + nomParent);
            }
            if (!compagnies.existe(nomEnfant)) {
                throw new SeauSException("Compagnie enfant existe pas: " + nomEnfant);
            }

            ResultSet rsParent = compagnies.getCompagnie(nomParent);
            rsParent.next();
            int idParent = rsParent.getInt("idcompagnie");
            rsParent.close();

            ResultSet rsEnfant = compagnies.getCompagnie(nomEnfant);
            rsEnfant.next();
            int idEnfant = rsEnfant.getInt("idcompagnie");
            rsEnfant.close();

            rs = parents.ajouterParent(String.valueOf(idParent), String.valueOf(idEnfant));
            connexion.commit();
        } catch (SQLException | SeauSException e) {
            connexion.rollback();
            throw e;
        }
        return rs;
    }
}
