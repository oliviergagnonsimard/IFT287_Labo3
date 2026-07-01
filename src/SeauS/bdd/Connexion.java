package SeauS.bdd;
import SeauS.SeauSException;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoDatabase;

/**
 * Gestionnaire d'une connexion avec une BD relationnelle via JDBC.<br><br>
 * 
 * Cette classe ouvre une connexion avec une BD via JDBC.<br>
 * La méthode serveursSupportes() indique les serveurs supportés.<br>
 * <pre>
 * Pré-condition
 *   Le driver JDBC approprié doit être accessible.
 * 
 * Post-condition
 *   La connexion est ouverte en mode autocommit false et sérialisable, 
 *   (s'il est supporté par le serveur).
 * </pre>
 * <br>
 * IFT287 - Exploitation de BD relationnelles et OO
 * 
 * @author Marc Frappier - Université de Sherbrooke
 * @version Version 2.0 - 13 novembre 2004
 * 
 * 
 * @author Vincent Ducharme - Université de Sherbrooke
 * @version Version 3.0 - 21 mai 2016
 */
public class Connexion implements IConnexion
{
    private MongoClient client;
    private MongoDatabase database;

    /**
     * Ouverture d'une connexion en mode autocommit false et sérialisable (si
     * supporté)
     * 
     * @param serveur Le type de serveur SQL à utiliser (Valeur : local, dinf).
     * @param bd      Le nom de la base de données sur le serveur.
     * @param user    Le nom d'utilisateur à utiliser pour se connecter à la base de données.
     * @param pass    Le mot de passe associé à l'utilisateur.
     */
    public Connexion(String serveur, String bd, String user, String pass) throws SeauSException
    {
        if (serveur.equals("local"))
        {
            client = new MongoClient();
        }
        else if (serveur.equals("dinf"))
        {
            MongoClientURI uri = new MongoClientURI("mongodb://"+user+":"+pass+"@bd-info2.dinf.usherbrooke.ca:27017/"+bd+"?ssl=true");
            client = new MongoClient(uri);
        }
        else
        {
            throw new SeauSException("Serveur inconnu");
        }

        database = client.getDatabase(bd);

        System.out.println("Ouverture de la connexion :\n"
                + "Connecté sur la BD MongoDB "
                + bd + " avec l'utilisateur " + user);
    }

    /**
     * Fermeture d'une connexion
     */
    public void fermer() throws Exception
    {
        client.close();
        System.out.println("Connexion fermée");
    }

    public void commit() throws Exception {

    }

    public void rollback() throws Exception {

    }

    public void demarreTransaction() throws Exception {

    }

    /**
     * Retourne la Connection MONGO
     */
    public MongoClient getClient() {
        return client;
    }

    public MongoDatabase getDatabase() {
        return database;
    }
}
