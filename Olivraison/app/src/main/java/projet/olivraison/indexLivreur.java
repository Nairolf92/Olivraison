
package projet.olivraison;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;

import android.widget.EditText;
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
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;

public class indexLivreur extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {


    private RequestQueue requestQueue;
    private String jsonResponse;
    private ListView listCommandesLivreurs;
    private ArrayList<Commande> commandeLivreur = new ArrayList<Commande>();
    public int ArrayId[] = new int[9999];
    public int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_index_livreur);
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

        requestQueue = Volley.newRequestQueue(this);
        String url = "http://antoine-lucas.fr/api_android/web/index.php/api/commandes/livreur/"+id_p;
        listCommandesLivreurs = (ListView) findViewById(R.id.listViewCommandesLivreurs);

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

                                Log.i("reponse : " , "rep "+ person);
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


                                commandeLivreur.add(commande);

                            }


                            ArrayAdapter<Commande> adapter = new CommandeCoursAdapter(indexLivreur.this, R.layout.activity_detail_commande_livreur, commandeLivreur);
                            listCommandesLivreurs.setAdapter(adapter);

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
        listCommandesLivreurs.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                Commande commande = commandeLivreur.get(position);

                Intent i = new Intent (getApplicationContext(), detailCommandeLivreur.class);
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


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

    }
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        switch (item.getItemId()) {
            case R.id.nav_livraison_cours:
                DrawerLayout mDrawerLayout;
                mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
                mDrawerLayout.closeDrawers();
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
    @Override
    public void onBackPressed() {
        Toast.makeText(getApplicationContext(),
                "Veuillez vous déconnecter si vous souhaitez revenir à la page précédente ",Toast.LENGTH_SHORT)
                .show();
    }

}

