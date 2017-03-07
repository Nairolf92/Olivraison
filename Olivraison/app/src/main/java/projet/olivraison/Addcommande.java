package projet.olivraison;

import android.content.Intent;
import android.os.Bundle;
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
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.io.SequenceInputStream;
import java.util.HashMap;
import java.util.Map;

public class Addcommande extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    Spinner spinnerCivilite;
    ArrayAdapter<CharSequence> adapter;


    private EditText editTextNom;
    private EditText editTextPrenom;
    private EditText editTextAdresse;
    private EditText editTextPhone;
    private EditText editTextTotal;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_commande);
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

        Button BoutonAjouterCommande = (Button) findViewById(R.id.bouton_ajouter_commande);
        BoutonAjouterCommande.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Toast.makeText(getApplicationContext(), "Commande créée", Toast.LENGTH_LONG).show();
                Intent i = new Intent (getApplicationContext(), Index.class);
                i.putExtra("fullname",fullname);
                i.putExtra("id_p",id_p);
                i.putExtra("id_role",id_role);
                startActivity(i);
            }
        });




        editTextNom = (EditText) findViewById(R.id.editTextNom);
        editTextPrenom = (EditText) findViewById(R.id.editTextPrenom);
        editTextAdresse= (EditText) findViewById(R.id.editTextAdresse);
        editTextPhone= (EditText) findViewById(R.id.editTextPhone);
        editTextTotal= (EditText) findViewById(R.id.editTextTotal);





        spinnerCivilite = (Spinner) findViewById(R.id.spinnerCivilite);
        adapter = ArrayAdapter.createFromResource(this, R.array.civilite, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCivilite.setAdapter(adapter);
        spinnerCivilite.setOnItemSelectedListener(new OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int i, long l) {
                //Toast.makeText(getBaseContext(), parent.getItemAtPosition(i) +" est selectionner", Toast.LENGTH_LONG ).show();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent){

            }
        });


        final Button btAjouter_commande = (Button) findViewById(R.id.bouton_ajouter_commande);
        btAjouter_commande.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Perform action on click

                String name = editTextNom.getText().toString().trim();
                String prenom = editTextPrenom.getText().toString();
                String adresse = editTextAdresse.getText().toString();
                String phone = editTextPhone.getText().toString();
                String total = editTextTotal.getText().toString();

                String addcommandeurl = "http://www.antoine-lucas.fr/api_android/web/index.php/api/commande/create?livreur=5&nom="+name+"&prenom="+prenom+"&phone="+phone+"&adresse="+adresse+"&prix_total="+total;
                addcommandeurl = addcommandeurl.replaceAll(" ", "%20");

                StringRequest stringRequest = new StringRequest(Request.Method.GET, addcommandeurl,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                Toast.makeText(Addcommande.this,response,Toast.LENGTH_LONG).show();
                                finish();

                                Intent i = new Intent (getApplicationContext(), Index.class);
                                i.putExtra("fullname",fullname);
                                i.putExtra("id_p",id_p);
                                i.putExtra("id_role",id_role);
                                startActivity(i);

                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Toast.makeText(Addcommande.this,error.toString(),Toast.LENGTH_LONG).show();
                            }
                        }){
                    @Override
                    protected Map<String,String> getParams(){
                        Map<String,String> params = new HashMap<String, String>();

                        return params;
                    }

                };

                RequestQueue requestQueue = Volley.newRequestQueue(Addcommande.this);
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
    public void onBackPressed() {
       finish();
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        switch (item.getItemId()) {
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
