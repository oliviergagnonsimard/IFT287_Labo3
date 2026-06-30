package SeauS.gestion;

import SeauS.SeauSException;
import SeauS.bdd.Connexion;
import SeauS.tuples.Projet;
import java.sql.ResultSet;
import java.sql.SQLException;


public abstract class GestionTransactions {
    protected final Connexion cx;

    protected GestionTransactions(Connexion cx) {
        this.cx = cx;
    }

     // Fonction utilitaire que je me fais pour pas aovoir à faire plusieurs fois le même code
    protected Projet getProjet(ResultSet rs) throws SeauSException, SQLException {
         Projet p = new Projet(
                rs.getInt("idcompagnie"),
                rs.getInt("idcommunaute"),
                rs.getFloat("budget_initial"),
                rs.getFloat("budget_final"),
                rs.getString("charge_projet"),
                rs.getDate("date_annonce"),
                rs.getDate("date_debut"),
                rs.getDate("date_fin"),
                rs.getString("etat_avancement")
            );
            
        return p;
    }
}
