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
import org.w3c.dom.Text;

import static projet.olivraison.R.id.fab;

public class detailCommandeLivreur extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private RequestQueue requestQueue;
    private String jsonResponse;
    public String adresse;
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
        Intent intent = getIntent();

        String position = intent.getStringExtra("position");


        id = intent.getIntExtra("id", id);

        //recuperation et affichage des details d'une commande

        String idcommande = this.getIntent().getExtras().getString("id");

        String codecommandecours = this.getIntent().getExtras().getString("reference");
        TextView codecommandeView = (TextView)findViewById(R.id.referenceCommande);
        codecommandeView.setText(codecommandecours);

        String nom = this.getIntent().getExtras().getString("nom");
        String prenom = this.getIntent().getExtras().getString("prenom");
        TextView nomclientView = (TextView)findViewById(R.id.fullnameCommande);
        nomclientView.setText(nom +" " +prenom );

        final String adresseLivraison = this.getIntent().getExtras().getString("adresseLivraison");
        TextView adresseLivraisonView = (TextView)findViewById(R.id.adresseCommande);
        adresseLivraisonView.setText(adresseLivraison);

        String phoneClient = this.getIntent().getExtras().getString("phone");
        TextView phoneClientView = (TextView)findViewById(R.id.telephoneCommande);
        phoneClientView.setText(phoneClient);

        String totalCmd = this.getIntent().getExtras().getString("prix_total");
        TextView totalCmdView = (TextView)findViewById(R.id.prixCommande);
        totalCmdView.setText(totalCmd+" €");

        Button boutonPrendreCommande = (Button) findViewById(R.id.boutonPrendreCommande);
        boutonPrendreCommande.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                //Toast.makeText(getApplicationContext(), "bouton clicker", Toast.LENGTH_LONG).show();

                Intent i = new Intent (getApplicationContext(), LivreurMaps.class);
                i.putExtra("adresse", adresseLivraison);
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
    public void onBackPressed() {
        finish();
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.nav_livraison_cours:
                Intent i = new Intent(getApplicationContext(), indexLivreur.class);
                Bundle extras = getIntent().getExtras();
                String fullname = extras.getString("fullname");
                String id_role = extras.getString("id_role");
                String id_p = extras.getString("id_p");
                i.putExtra("id_role",id_role);
                i.putExtra("fullname",fullname);
                i.putExtra("id_p",id_p);
                startActivity(i);
                finish();
                return true;
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
