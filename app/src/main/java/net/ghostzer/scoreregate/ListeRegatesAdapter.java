package net.ghostzer.scoreregate;

import android.content.Context;

import java.util.List;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TextView;

import net.ghostzer.scoreregate.Models.Regate;

/**
 * Created by Afpa on 28/02/2017.
 */

public class ListeRegatesAdapter extends ArrayAdapter<Regate> {


    Button btnEventRegister;
    Button btnEventMore;
    TableLayout tblEvent;
    TextView lblEventBy;


    public ListeRegatesAdapter(Context context, List<Regate> listRegates) {
        super(context, 0, ListeRegates);
    }


    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        // Get the data item for this position

        final Regate lEvent = getItem(position);

        // Check if an existing view is being reused, otherwise inflate the view

        if (convertView == null) {


            convertView = LayoutInflater.from(getContext()).inflate(R.layout.adapter_view, parent, false);

        }

        // Lookup view for data population

        TextView lblEventTitle = (TextView) convertView.findViewById(R.id.lblEventTitle);
        TextView lblEventBy = (TextView) convertView.findViewById(R.id.lblEventBy);

        tblEvent = (TableLayout) convertView.findViewById(R.id.tblEvent);
        tblEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getContext(), DetailsEvent.class);
                intent.putExtra("event_id","" + lEvent.getId());
                getContext().startActivity(intent);
            }
        });





        // Populate the data into the template view using the data object


        lblEventTitle.setText(lEvent.getTitle());
        lblEventBy.setText("Par " + lEvent.getFirstname() + " " + lEvent.getLastname() );


        // Return the completed view to render on screen

        return convertView;

    }
}