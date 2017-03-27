package projet.olivraison;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;


public class ListeLivreurAdapter extends ArrayAdapter<Livreur>{
    private final Context context;
    private final List LivreurList;

    public ListeLivreurAdapter(Context context, int ressourcesId, List LivreurList) {
        super(context, R.layout.activity_liste_livreurs, LivreurList);
        this.context = context;
        this.LivreurList = LivreurList;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.list_livreurs, parent, false);

        Livreur livreur = getItem(position);

        TextView phone_number = (TextView) rowView.findViewById(R.id.adresseLivreur);
        phone_number.setText(livreur.getMobile_phone().toString());

        TextView nomCommandeCours = (TextView) rowView.findViewById(R.id.nomLivreur);
        nomCommandeCours.setText(livreur.getFirst_name() + " " + livreur.getLast_name()) ;
/**
        TextView phoneClientCommandeCours = (TextView) rowView.findViewById(R.id.contactClientCommandeCours);
        phoneClientCommandeCours.setText(commande.getPhone()) ;

        TextView adresseCommandeCours = (TextView) rowView.findViewById(R.id.adresseCommandeCours);
        adresseCommandeCours.setText(commande.getAdresseLivraison()) ;

**/

        return rowView;
    }
}
