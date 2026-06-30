package SeauS.tuples;

import java.sql.Date;

public class Projet {
    public int idProjet;
    public int idCommunaute;
    public int idCompagnie;
    public float budgetInitial;
    public float budgetFinal;
    public String chargeProjet;
    public Date dateAnnonce;
    public Date dateDebut;
    public Date dateFin;
    public String etatAvancement;

    public Projet(int idCommunaute, int idCompagnie, float budgetInitial, float budgetFinal,
                  String chargeProjet, Date dateAnnonce, Date dateDebut, Date dateFin, String etatAvancement) {
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
}
