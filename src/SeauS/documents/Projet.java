package SeauS.documents;

import java.util.Date;

import org.bson.Document;


public class Projet {
    public int idProjet;
    public int idCommunaute;
    public int idCompagnie;
    public double budgetInitial;
    public double budgetFinal;
    public String chargeProjet;
    public Date dateAnnonce;
    public Date dateDebut;
    public Date dateFin;
    public String etatAvancement;

    public Projet(int idCommunaute, int idCompagnie, float budgetInitial, float budgetFinal, String chargeProjet, Date dateAnnonce, Date dateDebut, Date dateFin, String etatAvancement) {
        this.idCommunaute = idCommunaute;
        this.idCompagnie = idCompagnie;
        this.budgetInitial = budgetInitial;
        this.budgetFinal = budgetFinal;
        this.chargeProjet = chargeProjet;
        this.dateAnnonce = dateAnnonce;
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
        this.etatAvancement = etatAvancement;
    }

    public Projet(Document d) {
        this.idCommunaute = d.getInteger("idCommunaute");
        this.idCompagnie = d.getInteger("idCompagnie");
        this.budgetInitial = d.getDouble("budgetInitial");
        this.budgetFinal = d.getDouble("budgetFinal");
        this.chargeProjet = d.getString("chargeProjet");
        this.dateAnnonce = d.getDate("dateAnnonce");
        this.dateDebut = d.getDate("dateDebut");
        this.dateFin = d.getDate("dateFin");
        this.etatAvancement = d.getString("etatAvancement");
    }

    public Document toDocument() {
        return new Document()
                .append("idCommunaute", idCommunaute)
                .append("idCompagnie", idCompagnie)
                .append("budgetInitial", budgetInitial)
                .append("budgetFinal", budgetFinal)
                .append("chargeProjet", chargeProjet)
                .append("dateAnnonce", dateAnnonce)
                .append("dateDebut", dateDebut)
                .append("dateFin", dateFin)
                .append("etatAvancement", etatAvancement);
    }
}
