package projet.olivraison;

/**
 * Created by doteminkonate on 01/03/2017.
 */

public class Livreur {

    public Livreur(){

    }

    String id;
    String last_name;
    String first_name;



    public String getId() {

        return id;
    }
    public void setId(String id) {

        this.id = id;
    }

    //Nom
    public String getLast_name() {

        return last_name;
    }
    public void setLast_name(String last_name) {

        this.last_name = last_name;
    }


    public String getFirst_name() {

        return first_name;
    }
    public void setFirst_name(String first_name) {

        this.first_name = first_name;
    }

}
