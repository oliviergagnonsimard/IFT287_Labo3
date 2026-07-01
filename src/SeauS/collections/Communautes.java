package SeauS.collections;

import org.bson.Document;

import com.mongodb.client.MongoCollection;
import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Updates.combine;
import static com.mongodb.client.model.Updates.set;

import SeauS.bdd.Connexion;
import SeauS.documents.Communaute;

public class Communautes extends GestionTables {

    private MongoCollection<Document> communauteCollection;

    public Communautes(Connexion cx) {
        super(cx);

        communauteCollection = cx.getDatabase().getCollection("communaute");
    }

    public boolean existe(String nom) {
        return communauteCollection.find(eq("nom_communaute", nom)).first() != null;
    }

    public Communaute getCommunaute(String nom) {
        Document c = communauteCollection.find(eq("nom_communaute", nom)).first();
    	if(c != null)
    		return new Communaute(c);

        return null;
    }

    public Communaute getCommunauteID(String idCommunaute) {
        Document c = communauteCollection.find(eq("idcommunaute", idCommunaute)).first();
    	if(c != null)
    		return new Communaute(c);

        return null;
    }

    /* Statements INSERT */
    public int ajouterCommunaute(Communaute communaute) {
        communauteCollection.insertOne(communaute.toDocument());

        return 1;
    }

    /* Statements DELETE */
    public int supprimerCommunaute(String nom) {
        return (int)communauteCollection.deleteOne(eq("nom_communaute", nom)).getDeletedCount();
    }

    /* Statements UPDATE */
    public int editerCommunaute(Communaute communaute, String ancienNom) {
    	return (int)communauteCollection.updateOne(eq("nom_communaute", ancienNom),
         combine(
            set("nom_communaute", communaute.nom_communaute),
            set("chef_communaute", communaute.chef_communaute),
            set("nation", communaute.nation),
            set("coordonnees", communaute.coordonnees)
        )).getModifiedCount();
    }
    
}
