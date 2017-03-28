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

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.addlivreur, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
