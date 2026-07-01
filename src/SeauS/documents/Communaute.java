package SeauS.documents;

import org.bson.Document;


public class Communaute {
    public String nom_communaute;
    public String nation;
    public String chef_communaute;
    public String coordonnees;

    public Communaute(Document d) {
        this.nom_communaute = d.getString("nom_communaute");
        this.nation = d.getString("nation");
        this.chef_communaute = d.getString("chef_communaute");
        this.coordonnees = d.getString("coordonnees");
    }

    public Communaute(String nom_communaute, String nation, String chef_communaute, String coordonnees) {
        this.nom_communaute = nom_communaute;
        this.nation = nation;
        this.chef_communaute = chef_communaute;
        this.coordonnees = coordonnees;
    }

    public Document toDocument() {
        return new Document().append("nom_communaute", nom_communaute)
                .append("nation", nation)
                .append("chef_communaute", chef_communaute)
                .append("coordonnees" , coordonnees);
    }
}
