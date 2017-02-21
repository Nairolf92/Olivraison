package projet.olivraison;

import android.content.Intent;
import android.os.Bundle;
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
import android.widget.ListView;
import android.widget.TextView;

public class Index extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    // creation de liste des commande en cours
    ListView listCommandeCours;
    String[] commandes = new String[]{
            "Commande 1: Patte plus", "Commande 2: pidzza Poulet", "Commande 3: pidzza curie", "Commande 4: pidzza 360", "Commande 5: pidzza Viande",
            "Commande 6 :pidzza porc", "Commande 7: pidzza dinde", "Commande 8: pidzza royale", "Commande 9: pidzza elegance", "Commande 10: pidzza premium"
    };
    Intent intent;
    private TextView mFullName;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        setContentView(R.layout.activity_index);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Bundle extras = getIntent().getExtras();
        String fullname;
        String status;
        if (extras != null) {
            // On récupère le nom+prénom de l'utilisateur qui s'est connecté ainsi que son statut (admin ou livreur)
            fullname = extras.getString("fullname");
            status = extras.getString("status");
            //mFullName = (TextView) findViewById(R.id.fullname);
            //mFullName.setText(fullname);
        }

        listCommandeCours = (ListView) findViewById(R.id.listViewCommandeCours);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(Index.this,
                android.R.layout.simple_list_item_1, commandes);
        listCommandeCours.setAdapter(adapter);


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
