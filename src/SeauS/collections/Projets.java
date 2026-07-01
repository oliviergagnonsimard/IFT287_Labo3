package SeauS.collections;

import java.sql.SQLException;

import org.bson.Document;

import com.mongodb.client.MongoCollection;
import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Updates.combine;
import static com.mongodb.client.model.Updates.set;

import SeauS.SeauSException;
import SeauS.bdd.Connexion;
import SeauS.documents.Projet;

public class Projets extends GestionTables {

    private MongoCollection<Document> projetCollection;

    public Projets(Connexion cx) throws SQLException {
        super(cx);

        projetCollection = cx.getDatabase().getCollection("projet");
    }

    public boolean existe(int idProjet) throws SQLException {
    	return projetCollection.find(eq("idProjet", idProjet)).first() != null;
    }

    public Projet getProjet(int idProjet) throws SQLException {
       Document d = projetCollection.find(eq("idProjet", idProjet)).first();
    	if(d != null)
    	{
    		return new Projet(d);
    	}
        return null;
    }


    /* Statements INSERT */
    public int ajouterProjet(Projet projet) throws SQLException {
        // Ajout du livre.
        projetCollection.insertOne(projet.toDocument());

        return 1;
    }

//idCommunaute, idCompagnie, budgetInitial, budgetFinal, charge, dateAnnonce, dateDebut, dateFin, etatAvancement, idProjet
    /* Statements UPDATE */
    public int editerProjet(Projet projet, int idProjet) throws SQLException, SeauSException {
        return (int)projetCollection.updateOne(eq("idProjet", idProjet),
         combine(
            set("idrojet", projet.idProjet),
            set("idcompagnie", projet.idCompagnie),
            set("idcommunaute", projet.idCommunaute),
            set("date_annonce", projet.dateAnnonce),
            set("date_debut", projet.dateDebut),
            set("date_fin", projet.dateFin),
            set("budget_initial", projet.budgetInitial),
            set("budget_final", projet.budgetFinal),
            set("charge_projet", projet.chargeProjet),
            set("etat_avancement", projet.etatAvancement)

        )).getModifiedCount();
    }

    /* Statements DELETE */
    public int supprimerProjet(int idProjet) throws SQLException {
        return (int)projetCollection.deleteOne(eq("idProjet", idProjet)).getDeletedCount();
    }

}
