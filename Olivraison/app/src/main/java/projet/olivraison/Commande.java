package projet.olivraison;

import java.util.ArrayList;

/**
 * Created by doteminkonate on 31/01/2017.
 */
public class Commande {

    public Commande(){

    }

    String id;
    String reference;
    String nom;
    String prenom;
    String phone;
    String adresseLivraison;
    String prix_total;


    public String getId() {

        return id;
    }
    public void setId(String id) {

        this.id = id;
    }


    public String getReference() {

        return reference;
    }
    public void setReference(String reference) {

        this.reference = reference;
    }

    //Nom
    public String getNom() {

        return nom;
    }
    public void setNom(String nom) {

        this.nom = nom;
    }


    //PreNom
    public String getPrenom() {

        return prenom;
    }
    public void setPrenom(String prenom) {

        this.prenom = prenom;
    }

    //Phone
    public String getPhone() {

        return phone;
    }
    public void setPhone(String phone) {

        this.phone = phone;
    }

    //AdresseLivraison
    public String getAdresseLivraison() {

        return adresseLivraison;
    }
    public void setAdresseLivraison(String adresseLivraison) {

        this.adresseLivraison = adresseLivraison;
    }

    //prix_total
    public String getPrixTotal() {

        return prix_total;
    }
    public void setPrixTotal(String prix_total) {

        this.prix_total = prix_total;
    }
}
