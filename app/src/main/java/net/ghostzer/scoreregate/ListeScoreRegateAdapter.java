package net.ghostzer.scoreregate;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TableLayout;
import android.widget.TextView;
import net.ghostzer.scoreregate.Models.Score;
import java.util.List;


/**
 * Created by Afpa on 01/03/2017.
 */

public class ListeScoreRegateAdapter extends ArrayAdapter<Score> {

    TableLayout tblScore;

    public ListeScoreRegateAdapter(Context context, List<Score> listscores) {
        super(context, 0, listscores);
    }


    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {


        final Score lscore = getItem(position);


        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.adapter_regates, parent, false);
        }

        TextView lblNomVoilier = (TextView) convertView.findViewById(R.id.lblNomVoilier);




        tblScore = (TableLayout) convertView.findViewById(R.id.tblScore);
        tblScore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getContext(), DetailsRegate.class);
                System.out.println("ADAPTER regate id : " + lscore.getId_voilier());
                intent.putExtra("regate_id", "" + lscore.getId_voilier());
                getContext().startActivity(intent);
            }
        });


        lblNomVoilier.setText(lscore.getNom_voilier());



        return convertView;

    }
}