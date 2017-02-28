
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
    private ArrayList<String> ArrayCommande = new ArrayList<String>();
    public int ArrayId[] = new int[9999];
    private TextView mLoginView;
    private TextView mTestView;
    private EditText mPasswordView;
    public int id;

    // creation de liste des commande en cours
    ListView listCommandesLivreurs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_index_livreur);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Récupération des extras
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            // On récupère le extra de déconnexion si l'on souhaite se déconnecter

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
            image_role.setImageResource(R.drawable.ic_livreur);
            TextView navUsername = (TextView) headerView.findViewById(R.id.fullname);
            navUsername.setText(fullname);
        }

        requestQueue = Volley.newRequestQueue(this);
        String url = "http://antoine-lucas.fr/api_android/web/index.php/api/commandes/livreur/1";
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

                                String name = person.getString("client");
                                String username = person.getString("adresse");
                                id = person.getInt("id");


                                ArrayId[i] = id;
                                //Toast.makeText(getApplicationContext(), "id " + ArrayId[i], Toast.LENGTH_LONG).show();
                                //ArrayId[i] = id;


                                jsonResponse = username + ": +" + name;
                                Log.i("test", jsonResponse);

                                jsonResponse = " commande : " + id + " -- "+username;
                                //Log.i("test" , jsonResponse);
                                ArrayCommande.add(jsonResponse);

                            }


                            ArrayAdapter<String> adapter = new ArrayAdapter<String>(indexLivreur.this,
                                    android.R.layout.simple_list_item_1, ArrayCommande);

                            listCommandesLivreurs.setAdapter(adapter);
                            //mTestView.setText(Arrays.toString(ArrayCommande.toArray()));

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


        listCommandesLivreurs.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent i = new Intent(getApplicationContext(), detailCommandeLivreur.class);
                ArrayList<String> ArrayCommande = indexLivreur.this.ArrayCommande;

                i.putExtra("position", ArrayCommande.get(position));
                //i.putExtra("name",ArrayCommande.set(2,name) );


                //Log.i("test", ArrayCommande.get(position) );
                String idLocal =  ArrayCommande.get(position);
                //i.putExtra("position", ArrayId[] );


               Toast.makeText(getApplicationContext(), "id " + ArrayId[position], Toast.LENGTH_LONG).show();
                i.putExtra("id", ArrayId[position]);

               // Toast.makeText(getApplicationContext(), "id " + id + ArrayCommande.get(position), Toast.LENGTH_LONG).show();
                i.putExtra("id", id);


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
            case R.id.deconnexion:
                getIntent().removeExtra("fullname");
                getIntent().removeExtra("id_role");
                getIntent().removeExtra("id_p");
                finish();
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
     /*   DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }*/
    }

}

