
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

    private TextView mLoginView;
    private TextView mTestView;

    private EditText mPasswordView;


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

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });


        requestQueue = Volley.newRequestQueue(this);
        String url = "https://jsonplaceholder.typicode.com/users";

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
                                String username = person.getString("username");

                                jsonResponse = username + ": +"+name;
                                Log.i("test" , jsonResponse);
                                ArrayCommande.add(jsonResponse);

                            }

                            listCommandesLivreurs = (ListView) findViewById(R.id.listViewCommandesLivreurs);

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
/*

        //l'action qui se passe lorsque je clique sur un element de la liste des commande
        listCommandesLivreurs.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                Toast.makeText(getApplicationContext(),
                        "Click ListItem Number " + position, Toast.LENGTH_LONG)
                        .show();

                //quand je clique sur un element, j'affiche la vue details pour afficher les details de la commande
                Intent i = new Intent (getApplicationContext(), detailsCommandeCours.class);
                ArrayList<String> ArrayCommande = Index.this.commandeCours;

                i.putExtra("position",ArrayCommande.get(position) );
                i.putExtra("name",ArrayCommande.set(2,name) );
                startActivity(i);
            }
        });

        */




/*
        listCommandesLivreurs = (ListView) findViewById(R.id.listViewCommandesLivreurs);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(indexLivreur.this,
                android.R.layout.simple_list_item_1, ArrayCommande);

        listCommandesLivreurs.setAdapter(adapter);
        */

    }


}

