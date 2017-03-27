package projet.olivraison;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.MenuInflater;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

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
import java.util.Arrays;



public class Index extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    // creation de liste des commande en cours
    private ListView listCommandeCoursView;
    private TextView no_commandes;
    private RequestQueue requestQueue;
    private String jsonResponse;

    private ArrayList<Commande> commandeCours = new ArrayList<Commande>();
    private TextView mFullName;
    private Menu menu;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_index);
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
        image_role.setImageResource(R.drawable.ic_admin);
        // Mise en place du fullname dans le menu de gauche
        TextView navUsername = (TextView) headerView.findViewById(R.id.fullname);
        navUsername.setText(fullname);

        //recuperation de la vue qui affiche les donnees de l'API
        listCommandeCoursView = (ListView) findViewById(R.id.listViewCommandeCours);
        final String name = null;

        // Message pas de commandes trouvées
        no_commandes = (TextView) findViewById(R.id.no_commandes_admin);

        //initialisation de la requette
        requestQueue = Volley.newRequestQueue(this);
        String url = "http://antoine-lucas.fr/api_android/web/index.php/api/commandes/todo";

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
                                String reference = person.getString("reference");
                                String nom = person.getString("nom");
                                String prenom = person.getString("prenom");
                                String phone = person.getString("phone");
                                String adresseLivraison = person.getString("adresse");
                                String prix_total = person.getString("prix_total");


                                Commande commande = new Commande();

                                commande.setId(id);
                                commande.setReference(reference);
                                commande.setNom(nom);
                                commande.setPrenom(prenom);
                                commande.setPhone(phone);
                                commande.setAdresseLivraison(adresseLivraison);
                                commande.setPrixTotal(prix_total);



                                commandeCours.add(commande); 

                            }

                            if(commandeCours.isEmpty())
                            {
                                no_commandes.setVisibility(View.VISIBLE);
                            }else {
                                ArrayAdapter<Commande> adapter = new CommandeCoursAdapter(Index.this, R.layout.activity_index, commandeCours);
                                listCommandeCoursView.setAdapter(adapter);
                            }

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

        //l'action qui se passe lorsque je clique sur un element de la liste des commande
        listCommandeCoursView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
              Commande commande = commandeCours.get(position);

                Intent i = new Intent (getApplicationContext(), detailsCommandeCours.class);
                i.putExtra("id", commande.getId() );
                i.putExtra("reference", commande.getReference() );
                i.putExtra("nom", commande.getNom());
                i.putExtra("prenom", commande.getPrenom());
                i.putExtra("phone", commande.getPhone());
                i.putExtra("adresseLivraison", commande.getAdresseLivraison() );
                i.putExtra("prix_total", commande.getPrixTotal());
                i.putExtra("fullname",fullname);
                i.putExtra("id_p",id_p);
                i.putExtra("id_role",id_role);
                startActivity(i);
            }
        });


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*Snackbar.make(view, "Activiter d'ajout de commande a creer", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show(); */

                Intent newCommandeView = new Intent (getApplicationContext(), Addcommande.class);
                newCommandeView.putExtra("fullname",fullname);
                newCommandeView.putExtra("id_p",id_p);
                newCommandeView.putExtra("id_role",id_role);
                startActivity(newCommandeView);
            }
        });


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

    }

    @Override
    public void onBackPressed() {
        Toast.makeText(getApplicationContext(),
                "Veuillez vous déconnecter si vous souhaitez revenir à la page précédente ",Toast.LENGTH_SHORT)
                .show();
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
                DrawerLayout mDrawerLayout;
                mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
                mDrawerLayout.closeDrawers();
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
                Intent h = new Intent (getApplicationContext(), ListeLivreurs.class);
                h.putExtra("fullname",fullname);
                h.putExtra("id_p",id_p);
                h.putExtra("id_role",id_role);
                startActivity(h);
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
