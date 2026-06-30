package SeauS.gestion;

import SeauS.SeauSException;
import SeauS.bdd.Connexion;
import SeauS.tables.Communautes;
import SeauS.tables.Projets;
import SeauS.tuples.Communaute;
import SeauS.tuples.Projet;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class GestionCommunaute extends GestionTransactions {

    private final Communautes communautes;
    private final Projets projets;

    private final Connexion connexion;


    public GestionCommunaute(Communautes communautes, Projets projets) {
        super(projets.getConnexion());

        this.communautes = communautes;
        this.projets = projets;

        this.connexion = communautes.getConnexion();
    }

    public int ajouterCommunaute(String nom, String nation, String chef, String coord) throws SQLException, SeauSException {
        Communaute c = new Communaute(nom, nation, chef, coord);
        int rs = 0;

        try
        {
            if (communautes.existe(c.nom_communaute))
            {
                throw new SeauSException("Communauté existe déjà : " + c.nom_communaute);
            }

            // Création de la communauté
            rs = communautes.ajouterCommunaute(c);

            // Commit
            cx.commit();
        }
        catch (SQLException | SeauSException e)
        {
            cx.rollback();
            throw e;
        }

        return rs;
    }

    public List<Projet> afficherProjetsCommunaute(String nom) throws SeauSException, SQLException {
        PreparedStatement stmtSelectProjetsCommu = projets.getProjetsCommuStatement();

        stmtSelectProjetsCommu.setString(1, nom);
        ResultSet rs = stmtSelectProjetsCommu.executeQuery();


        List<Projet> liste = new ArrayList<>();
        while (rs.next()) {
            Projet p = getProjet(rs);
            liste.add(p);
        }

        return liste;
    }

    public int supprimerCommunaute(String nom) throws SQLException, SeauSException {
        int rs;
        try {
            if (!communautes.existe(nom)) {
                throw new SeauSException("Communaute existe pas: " + nom);
            }

            rs = communautes.supprimerCommunaute(nom);
            connexion.commit();
        } catch (SQLException | SeauSException e) {
            connexion.rollback();
            throw e;
        }

        return rs; 
    }

    public int editerCommunaute(String nomActuel, String nouveauNom, String nation, String chef, String coord) throws SQLException, SeauSException {
        int rs;
        Communaute c = new Communaute(nouveauNom, nation, chef, coord);

        try {
            if (!communautes.existe(nomActuel)) {
                throw new SeauSException("Communaute existe pas: " + nomActuel);
            }

            rs = communautes.editerCommunaute(c, nomActuel);
            connexion.commit();
        } catch (SQLException | SeauSException e) {
            connexion.rollback();
            throw e;
        }

        return rs; 
    }

    public void afficherCommunaute(String nom) throws SQLException, SeauSException {
        ResultSet rs;
        try {
            if (!communautes.existe(nom)) {
                throw new SeauSException("Communaute existe pas: " + nom);
            }

            rs = communautes.getCommunaute(nom);
            connexion.commit();
        } catch (SQLException | SeauSException e) {
            connexion.rollback();
            throw e;
        }

        while (rs.next()) {
            System.out.println(rs.getInt("idcommunaute"));
            System.out.println(rs.getString("nom_communaute"));
            System.out.println(rs.getString("nation"));
            System.out.println(rs.getString("chef_communaute"));
            System.out.println(rs.getString("coordonnees"));
        }

    }
    
}
