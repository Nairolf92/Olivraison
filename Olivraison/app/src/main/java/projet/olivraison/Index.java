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
    private RequestQueue requestQueue;
    private String jsonResponse;
    private ArrayList<String> commandeCours = new ArrayList<String>();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_index);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Récupération des extras
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            // On récupère le nom+prénom de l'utilisateur qui s'est connecté ainsi que son statut (admin ou livreur) + l'id classique
            String fullname = extras.getString("fullname");
            String id_role = extras.getString("id_role");
            String id_p = extras.getString("id_p");
            // Menu
            NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
            navigationView.setNavigationItemSelectedListener(this);
            View headerView = navigationView.getHeaderView(0);
            // Mise en place de l'icone admin dans le menu
            ImageView image_role = (ImageView) headerView.findViewById(R.id.icon_role);
            image_role.setImageResource(R.drawable.ic_admin);
            // Mise en place du nom + prénom de la personne dans le menu
            TextView navUsername = (TextView) headerView.findViewById(R.id.fullname);
            navUsername.setText(fullname);
        }

        //recuperation de la vue qui affiche les donnees de l'API
        listCommandeCoursView = (ListView) findViewById(R.id.listViewCommandeCours);
        final String name = null;

        //initialisation de la requette
        requestQueue = Volley.newRequestQueue(this);
        String url = "http://jsonplaceholder.typicode.com/users";
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

                                String name = person.getString("name");

                                jsonResponse = "Cmde: "+ name;
                                commandeCours.add(jsonResponse);

                            }

                            ArrayAdapter<String> adapter = new ArrayAdapter<String>(Index.this,
                                    android.R.layout.simple_list_item_1, commandeCours);
                            listCommandeCoursView.setAdapter(adapter);


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
                Toast.makeText(getApplicationContext(),
                        "Click ListItem Number " + position, Toast.LENGTH_LONG)
                        .show();

                //quand je clique sur un element, j'affiche la vue details pour afficher les details de la commande
                Intent i = new Intent (getApplicationContext(), detailsCommandeCours.class);
                ArrayList<String> commandeCours = Index.this.commandeCours;
                i.putExtra("position",commandeCours.get(position) );
                i.putExtra("name",commandeCours.set(2,name) );
                startActivity(i);
            }
        });
///////////////////////////////////////////////////////////////

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Activiter d'ajout de commande a creer", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
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
     /*   DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }*/
    }

    @SuppressWarnings("StatementWithEmptyBody")


    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        switch (item.getItemId()) {
            case R.id.deconnexion:
                getIntent().removeExtra("fullname");
                getIntent().removeExtra("id_role");
                getIntent().removeExtra("id_p");
                Intent intent = new Intent(Index.this, LoginActivity.class);
                startActivity(intent);
                return true;
            default:
                //DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
                //drawer.closeDrawer(GravityCompat.START);
                return true;
        }
    }
}
