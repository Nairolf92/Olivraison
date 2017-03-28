package projet.olivraison;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class Addlivreur extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private EditText editTextNomLivreur;
    private EditText editTextPrenomLivreur;
    private EditText editTextTelephone;
    private EditText editTextLogin;
    private EditText editTextPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addlivreur);
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


        editTextNomLivreur= (EditText) findViewById(R.id.editTextNomLivreur);
        editTextPrenomLivreur= (EditText) findViewById(R.id.editTextPrenomLivreur);
        editTextTelephone= (EditText) findViewById(R.id.editTextTelephone);
        editTextLogin= (EditText) findViewById(R.id.editTextLogin);
        editTextPassword= (EditText) findViewById(R.id.editTextPassword);



        final Button btAjouterLivreur = (Button) findViewById(R.id.btAjouterLivreur);
        btAjouterLivreur.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Perform action on click

                String name = editTextNomLivreur.getText().toString().trim();
                String prenom = editTextPrenomLivreur.getText().toString();
                String phone = editTextTelephone.getText().toString();
                String login = editTextLogin.getText().toString();
                String pwd = editTextPassword.getText().toString();

                String addlivreururl ="http://www.antoine-lucas.fr/api_android/web/index.php/api/user/create?first_name="+name+"&last_name="+prenom+"&phone="+phone+"&login="+login+"&password="+pwd+"&id_role=1";
                addlivreururl = addlivreururl.replaceAll(" ", "%20");

                StringRequest stringRequest = new StringRequest(Request.Method.GET, addlivreururl,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                //Toast.makeText(Addlivreur.this,response,Toast.LENGTH_LONG).show();
                                Toast.makeText(Addlivreur.this,"Livreur ajouter avec succes",Toast.LENGTH_LONG).show();
                                finish();

                                Intent i = new Intent (getApplicationContext(), ListeLivreurs.class);
                                i.putExtra("fullname",fullname);
                                i.putExtra("id_p",id_p);
                                i.putExtra("id_role",id_role);
                                startActivity(i);

                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Toast.makeText(Addlivreur.this,error.toString(),Toast.LENGTH_LONG).show();
                            }
                        }){
                    @Override
                    protected Map<String,String> getParams(){
                        Map<String,String> params = new HashMap<String, String>();

                        return params;
                    }

                };

                RequestQueue requestQueue = Volley.newRequestQueue(Addlivreur.this);
                requestQueue.add(stringRequest);


            }
        });


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
    }

    @Override
    public void onBackPressed() {finish();}

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
                Intent h = new Intent(getApplicationContext(), Index.class);
                h.putExtra("fullname", fullname);
                h.putExtra("id_p", id_p);
                h.putExtra("id_role", id_role);
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
}
