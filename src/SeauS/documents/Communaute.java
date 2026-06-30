package SeauS.tuples;

public class Communaute {
    public int idCommunaute;
    public String nom_communaute;
    public String Nation;
    public String chef_communaute;
    public String coordonnees;

    public Communaute(String nom_communaute, String Nation, String chef_communaute, String coordonnees) {
        this.nom_communaute = nom_communaute;
        this.Nation = Nation;
        this.chef_communaute = chef_communaute;
        this.coordonnees = coordonnees;
    }
}
