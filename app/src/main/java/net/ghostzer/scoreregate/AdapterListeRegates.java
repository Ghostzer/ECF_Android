package net.ghostzer.scoreregate;

import android.content.Context;

import java.util.List;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TableLayout;
import android.widget.TextView;

import net.ghostzer.scoreregate.Models.Regate;

/**
 * Created by Afpa on 28/02/2017.
 */

public class AdapterListeRegates extends ArrayAdapter<Regate> {


    TableLayout tblRegate;


    public AdapterListeRegates(Context context, List<Regate> listRegates) {
        super(context, 0, listRegates);
    }


    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        // Get the data item for this position

        final Regate lRegate = getItem(position);

        // Check if an existing view is being reused, otherwise inflate the view

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.adapter_regates, parent, false);
        }

        // Lookup view for data population

        TextView lblNomRegate = (TextView) convertView.findViewById(R.id.lblNomRegate);

        tblRegate = (TableLayout) convertView.findViewById(R.id.tblRegate);
        tblRegate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getContext(), DetailsRegate.class);
                System.out.println("ADAPTER regate id : " + lRegate.getId_regate());
                intent.putExtra("regate_id", "" + lRegate.getId_regate());
                getContext().startActivity(intent);
            }
        });

        // Populate the data into the template view using the data object
        lblNomRegate.setText(lRegate.getNom_regate());

        // Return the completed view to render on screen

        return convertView;

    }
}