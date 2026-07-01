package SeauS.collections;

import java.util.ArrayList;
import java.util.List;

import org.bson.Document;

import com.mongodb.client.MongoCollection;
import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Updates.combine;
import static com.mongodb.client.model.Updates.set;

import SeauS.bdd.Connexion;

public class Parents extends GestionTables {

    private Connexion cx;
    private MongoCollection<Document> parentsCollection;

    public Parents(Connexion cx) {
        super(cx);
        this.cx = cx;
        parentsCollection = cx.getDatabase().getCollection("parent");
    }

    public boolean existe(String idParent) {
        return parentsCollection.find(eq("idParent", idParent)).first() != null;
    }

    // On retourne les IDs des parents
    public List<String> getParents(String id) {
        List<String> resultats = new ArrayList<>();
        for (Document d : parentsCollection.find(eq("idEnfant", id))) {
            resultats.add(d.getString("idParent"));
        }
        return resultats;
    }

    public List<String> getEnfants(String id) {
        List<String> resultats = new ArrayList<>();
        for (Document d : parentsCollection.find(eq("idParent", id))) {
            resultats.add(d.getString("idEnfant"));
        }
        return resultats;
    }

    /* Statements INSERT */
    public int ajouterParent(String idParent, String idEnfant) {
        Document d = new Document().append("idParent", idParent)
                .append("idEnfant", idEnfant);

        parentsCollection.insertOne(d);
        return 1;
    }

    /* Statements DELETE */
    public int supprimerParent(String idParent, String idEnfant) {
    	return (int)parentsCollection.deleteOne(
            combine(
                eq("idParent", idParent),
                eq("idEnfant", idEnfant)
        )).getDeletedCount();
    }

    /* Statements UPDATE */
    public int editerParent(String idParent, String idEnfant) {
        return (int)parentsCollection.updateOne(eq("idEnfant", idEnfant),
         combine(
            set("idParent", idParent),
            set("idEnfant", idEnfant)
        )).getModifiedCount();
    }

}
