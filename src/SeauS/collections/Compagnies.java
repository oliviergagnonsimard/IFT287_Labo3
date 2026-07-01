package SeauS.collections;

import org.bson.Document;

import com.mongodb.client.MongoCollection;
import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Updates.combine;
import static com.mongodb.client.model.Updates.set;

import SeauS.bdd.Connexion;
import SeauS.documents.Compagnie;

public class Compagnies extends GestionTables {

    private MongoCollection<Document> compagnieCollection;

    public Compagnies(Connexion cx) {
        super(cx);

        compagnieCollection = cx.getDatabase().getCollection("compagnie");
    }

    /* Statements SELECT */
    public boolean existe(String nom) {
        return compagnieCollection.find(eq("nom_compagnie", nom)).first() != null;
    }

    public boolean existe(int idCompagnie) {
        return compagnieCollection.find(eq("idcompagnie", idCompagnie)).first() != null;
    }

    public Compagnie getCompagnie(String nom) {
        Document c = compagnieCollection.find(eq("nom_compagnie", nom)).first();
        if (c != null)
            return new Compagnie(c);
        return null;
    }

    public Compagnie getCompagnieWithID(Compagnie c) {
        Document d = compagnieCollection.find(eq("nom_compagnie", c.idCompagnie)).first();
        if (d != null)
            return new Compagnie(d);
        return null;
    }

    /* Statements INSERT */
    public int ajouterCompagnie(Compagnie c) {
        compagnieCollection.insertOne(c.toDocument());

        return 1;
    }
    
    /* Statements DELETE */
    public int supprimerCompagnie(String nom) {
    	return (int)compagnieCollection.deleteOne(eq("nom_compagnie", nom)).getDeletedCount();
    }

    /* Statements UPDATE */
    public int editerCompagnie(Compagnie c, String ancienNom) {
        return (int) compagnieCollection.updateOne(eq("nom_compagnie", ancienNom),
         combine(
            set("nom_communaute", c.nom_compagnie),
            set("adresse", c.adresse)
        )).getModifiedCount();
    }
}
