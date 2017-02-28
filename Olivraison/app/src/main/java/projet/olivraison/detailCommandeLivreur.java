package projet.olivraison;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
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
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import static projet.olivraison.R.id.fab;
import static projet.olivraison.R.id.fullname;

public class detailCommandeLivreur extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private RequestQueue requestQueue;
    private String jsonResponse;
    Integer id = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_commande_livreur);
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

        // Titre de la commande
        TextView titreCommande = (TextView)findViewById(R.id.titreCommande);

        TextView textCommandeLivreur = (TextView)findViewById(R.id.textCommandeLivreur);
        Intent intent = getIntent();

        String position = intent.getStringExtra("position");


        id = intent.getIntExtra("id", id);

        //textCommandeLivreur.setText(position);

        Button monBouton = (Button) findViewById(R.id.button);
        TextView TextAdresse = (TextView) findViewById(R.id.TextAdresse);

        monBouton.setText("prendre la commande n° : "+id);

        monBouton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                //Toast.makeText(getApplicationContext(), "bouton clicker", Toast.LENGTH_LONG).show();

                Intent i = new Intent (getApplicationContext(), LivreurMaps.class);
                i.putExtra("id", id);
                 startActivity(i);
            }
        });

        String url = "http://antoine-lucas.fr/api_android/web/index.php/api/commande/"+id;

        JsonObjectRequest jsObjRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        //mTxtDisplay.setText("Response: " + response.toString());
                        Log.i("test", response.toString());
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO Auto-generated method stub

                    }
                });
// Access the RequestQueue through your singleton class.
        Volley.newRequestQueue(this).add(jsObjRequest);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

    }

    @Override
    public void onBackPressed() {
        finish();
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.deconnexion:
                getIntent().removeExtra("fullname");
                getIntent().removeExtra("id_role");
                getIntent().removeExtra("id_p");
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
