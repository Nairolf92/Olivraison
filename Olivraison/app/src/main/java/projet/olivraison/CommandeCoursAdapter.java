package projet.olivraison;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;


public class CommandeCoursAdapter extends ArrayAdapter<Commande>{
    private final Context context;
    private final List commandesCours;

    public CommandeCoursAdapter(Context context, int ressourcesId, List commandesCours) {
        super(context, R.layout.activity_index, commandesCours);
        this.context = context;
        this.commandesCours = commandesCours;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.list_commande_cours, parent, false);

        Commande commande = getItem(position);

        TextView idCommandeCours = (TextView) rowView.findViewById(R.id.idCommandeCours);
        idCommandeCours.setText(commande.getId().toString());

        //TextView nomCommandeCours = (TextView) rowView.findViewById(R.id.nomCommandeCours);
        //nomCommandeCours.setText(commande.getIdClient()) ;



        return rowView;
    }
}
