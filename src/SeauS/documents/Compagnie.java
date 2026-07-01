package SeauS.documents;

import org.bson.Document;


public class Compagnie {
    public int idCompagnie;
    public String nom_compagnie;
    public String adresse;

    public Compagnie(String nom_compagnie, String adresse) {
        this.nom_compagnie = nom_compagnie;
        this.adresse = adresse;
    }

    public Compagnie(Document d) {
        this.nom_compagnie = d.getString("nom_compagnie");
        this.adresse = d.getString("adresse");
    }

    public Document toDocument() {
        return new Document().append("nom_compagnie", nom_compagnie)
                .append("adresse", adresse);
    }
}
