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
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


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
        totalCmdView.setText(totalCmd);

        /*
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        }); */

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

/*    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.details_commande_cours, menu);
        return true;
    }*/

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.deconnexion) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();



        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
