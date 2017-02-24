
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
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
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

public class indexLivreur extends AppCompatActivity {


    private RequestQueue requestQueue;
    private String jsonResponse;
    private ArrayList<String> ArrayCommande = new ArrayList<String>();
    public int ArrayId[];
    private TextView mLoginView;
    private TextView mTestView;

    private EditText mPasswordView;
    public int id;

    // creation de liste des commande en cours
    ListView listCommandesLivreurs;
    String[] commandes = new String[]{
            "Commande 1: Patte plus", "Commande 2: pidzza Poulet", "Commande 3: pidzza curie", "Commande 4: pidzza 360", "Commande 5: pidzza Viande",
            "Commande 6 :pidzza porc", "Commande 7: pidzza dinde", "Commande 8: pidzza royale", "Commande 9: pidzza elegance", "Commande 10: pidzza premium"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_index_livreur);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
/*
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        */


        requestQueue = Volley.newRequestQueue(this);
        String url = "http://antoine-lucas.fr/api_android/web/index.php/api/commandes";
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

                                //ArrayId[i] = id;

                                jsonResponse = " commande : " + id + " -- "+username;
                                Log.i("test" , jsonResponse);
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

                Intent i = new Intent (getApplicationContext(), detailCommandeLivreur.class);
                ArrayList<String> ArrayCommande = indexLivreur.this.ArrayCommande;
                //Log.i("test", ArrayCommande.get(position) );
                //int idLocal =  ArrayCommande.get(position);
                //i.putExtra("position", ArrayId[] );

               // Toast.makeText(getApplicationContext(), "id " + id + ArrayCommande.get(position), Toast.LENGTH_LONG).show();
                i.putExtra("id", id);
                startActivity(i);

            }
        });

        /*
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        */
    }

}

