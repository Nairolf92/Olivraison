package projet.olivraison;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.List;

/**
 * Created by doteminkonate on 01/03/2017.
 */

public class LivreurAdpter extends ArrayAdapter<Livreur> {

    private final Context context;
    private final List livreurs;

    public LivreurAdpter(Context context, int ressourcesId, List livreurs) {
        super(context, android.R.layout.simple_spinner_dropdown_item, livreurs);
        this.context = context;
        this.livreurs = livreurs;
    }



    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(android.R.layout.simple_spinner_item, parent, false);

        Livreur livreur = getItem(position);

        TextView nomLivreur = (TextView) rowView.findViewById(android.R.id.text1);
        nomLivreur.setText(livreur.getFirst_name().toString());





        return rowView;
    }
}
