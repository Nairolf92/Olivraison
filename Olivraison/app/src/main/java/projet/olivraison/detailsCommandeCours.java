package projet.olivraison;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;

import android.widget.ImageView;

import android.widget.Button;

import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


//http://antoine-lucas.fr/api_android/web/index.php/api/commande/update/idcmd?livreur=id_livreur&statut=0

public class detailsCommandeCours extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    // creation de liste des commande en cours
    private Spinner spinner;
    private RequestQueue requestQueue;
    private String jsonResponse;

    private ArrayList<Livreur> livreurs = new ArrayList<Livreur>();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_commande_cours);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Récupération des extras
        Bundle extras = getIntent().getExtras();
        // On récupère le nom+prénom de l'utilisateur qui s'est connecté ainsi que son statut (admin ou livreur) + l'id classique
        final String fullname = extras.getString("fullname");
        final String id_role = extras.getString("id_role");
        final String id_p = extras.getString("id_p");
        // Définition du menu
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        View headerView = navigationView.getHeaderView(0);
        // Mise en place de l'icone livreur dans le menu
        ImageView image_role = (ImageView) headerView.findViewById(R.id.icon_role);
        image_role.setImageResource(R.drawable.ic_livreur);
        // Mise en place du fullname dans le menu de gauche
        TextView navUsername = (TextView) headerView.findViewById(R.id.fullname);
        navUsername.setText(fullname);


        //initialisation de la requette
        requestQueue = Volley.newRequestQueue(this);
        String url = "http://antoine-lucas.fr/api_android/web/index.php/api/livreurs";

        JsonArrayRequest jsonArray = new JsonArrayRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            // Parsing json array response
                            // loop through each json object
                            jsonResponse = "";
                            for (int i = 0; i < response.length(); i++) {

                                JSONObject person = (JSONObject) response
                                        .get(i);

                                String id = person.getString("id");
                                String last_name = person.getString("last_name");
                                String first_name = person.getString("first_name");



                                Livreur livreur = new Livreur();

                                livreur.setId(id);
                                livreur.setFirst_name(first_name);
                                livreur.setLast_name(last_name);




                                livreurs.add(livreur);

                            }


                            Spinner spinner = (Spinner)findViewById(R.id.spinnerLivreur);
                            ArrayAdapter<Livreur> adapter = new LivreurAdpter(detailsCommandeCours.this, android.R.layout.simple_spinner_dropdown_item, livreurs);
                            //ArrayAdapter<Livreur> adapter = new ArrayAdapter<Livreur>(detailsCommandeCours.this, android.R.layout.simple_spinner_dropdown_item, livreurs);

                            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);


                            spinner.setAdapter(adapter);


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO Auto-generated method stub
                        Log.i("test", error.getMessage());

                    }
                });
        Volley.newRequestQueue(this).add(jsonArray);


        //recuperation et affichage des details d'une commande

        String idcommande = this.getIntent().getExtras().getString("id");

        String codecommandecours = this.getIntent().getExtras().getString("reference");
        TextView codecommandeView = (TextView)findViewById(R.id.codeCommandeCours);
        codecommandeView.setText(codecommandecours);

        String nom = this.getIntent().getExtras().getString("nom");
        String prenom = this.getIntent().getExtras().getString("prenom");
        TextView nomclientView = (TextView)findViewById(R.id.nomClient);
        nomclientView.setText(nom +" " +prenom );

        String adresseLivraison = this.getIntent().getExtras().getString("adresseLivraison");
        TextView adresseLivraisonView = (TextView)findViewById(R.id.adresseLivraison);
        adresseLivraisonView.setText(adresseLivraison);

        String phoneClient = this.getIntent().getExtras().getString("phone");
        TextView phoneClientView = (TextView)findViewById(R.id.phoneClient);
        phoneClientView.setText(phoneClient);

        String totalCmd = this.getIntent().getExtras().getString("prix_total");
        TextView totalCmdView = (TextView)findViewById(R.id.totalCmd);
        totalCmdView.setText(totalCmd+" €");





        final String CmdUpdateURL = "http://antoine-lucas.fr/api_android/web/index.php/api/commande/update/"+idcommande+"?livreur=1&statut=0";
        final Button btAssigner = (Button) findViewById(R.id.assigner);
        btAssigner.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Perform action on click


                StringRequest stringRequest = new StringRequest(Request.Method.GET, CmdUpdateURL,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                Toast.makeText(detailsCommandeCours.this,response,Toast.LENGTH_LONG).show();
                                finish();
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Toast.makeText(detailsCommandeCours.this,error.toString(),Toast.LENGTH_LONG).show();
                            }
                        }){
                    @Override
                    protected Map<String,String> getParams(){
                        Map<String,String> params = new HashMap<String, String>();

                        return params;
                    }

                };

                RequestQueue requestQueue = Volley.newRequestQueue(detailsCommandeCours.this);
                requestQueue.add(stringRequest);


            }
        });





        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

    }

    @Override
    public void onBackPressed() {
       finish();
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        Bundle extras = getIntent().getExtras();
        String fullname = extras.getString("fullname");
        String id_role = extras.getString("id_role");
        String id_p = extras.getString("id_p");
        switch (item.getItemId()) {
            case R.id.nav_livraison_cours:
                Intent h = new Intent (getApplicationContext(), Index.class);
                h.putExtra("fullname",fullname);
                h.putExtra("id_p",id_p);
                h.putExtra("id_role",id_role);
                startActivity(h);
                return true;
            case R.id.nav_ajout_commande:
                Intent i = new Intent (getApplicationContext(), Addcommande.class);
                i.putExtra("fullname",fullname);
                i.putExtra("id_p",id_p);
                i.putExtra("id_role",id_role);
                startActivity(i);
                return true;
            case R.id.nav_liste_commande:
                Intent j = new Intent (getApplicationContext(), listeCommande.class);
                j.putExtra("fullname",fullname);
                j.putExtra("id_p",id_p);
                j.putExtra("id_role",id_role);
                startActivity(j);
                return true;
            case R.id.nav_liste_livreur:
                Intent k = new Intent (getApplicationContext(), ListeLivreurs.class);
                k.putExtra("fullname",fullname);
                k.putExtra("id_p",id_p);
                k.putExtra("id_role",id_role);
                startActivity(k);
                return true;
            case R.id.nav_ajout_livreur:
                Intent l = new Intent (getApplicationContext(), Addlivreur.class);
                l.putExtra("fullname",fullname);
                l.putExtra("id_p",id_p);
                l.putExtra("id_role",id_role);
                startActivity(l);
                return true;
            case R.id.deconnexion:
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                return true;
            default:
                //DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
                //drawer.closeDrawer(GravityCompat.START);
                return true;
        }
    }
}
