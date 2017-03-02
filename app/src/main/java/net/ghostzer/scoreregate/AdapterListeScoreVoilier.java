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

public class AdapterListeScoreVoilier extends ArrayAdapter<Score> {

    TableLayout tblScore;

    public AdapterListeScoreVoilier(Context context, List<Score> listscores) {
        super(context, 0, listscores);
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {


        final Score lscore = getItem(position);


        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.adapter_scores, parent, false);
        }

        TextView lblNomVoilier = (TextView) convertView.findViewById(R.id.lblNomVoilier);
        TextView lblPlaceVoilier = (TextView) convertView.findViewById(R.id.lblPlaceVoilier);
        TextView lblNumVoile = (TextView) convertView.findViewById(R.id.lblNumVoile);
        TextView lblTpsReel = (TextView) convertView.findViewById(R.id.lblTpsReel);



        int hour = lscore.getTps_reel() / 60;
        int min = hour % 60;

        System.out.print("ADAPTER_LISTE_SCORE_VOILIER : lscore : " + lscore);
        lblPlaceVoilier.setText(String.valueOf(lscore.getPlace()));
        lblNomVoilier.setText(lscore.getNom_voilier());
        lblNumVoile.setText(String.valueOf("N°" + lscore.getNum_voile()));
        lblTpsReel.setText(String.valueOf("Tps : " + hour + "h" + min));



        return convertView;

    }
}