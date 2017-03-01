package net.ghostzer.scoreregate;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import net.ghostzer.scoreregate.ApiMethod.FindRegateById;
import net.ghostzer.scoreregate.Models.Regate;

import java.util.concurrent.ExecutionException;

/**
 * Created by Afpa on 28/02/2017.
 */

public class DetailsRegate extends AppCompatActivity {
    String regateId;
    TextView txtTitreRegate;
    TextView txtNumRegate;
    TextView txtDateRegate;
    TextView txtDistanceRegate;
    TextView txtComAffect;
    Button btnVoirResultats;
//    Date date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_regate);

        txtTitreRegate = (TextView) findViewById(R.id.txtTitreRegate);
        txtNumRegate = (TextView) findViewById(R.id.txtNumRegate);
        txtDateRegate = (TextView) findViewById(R.id.txtDateRegate);
        txtDistanceRegate = (TextView) findViewById(R.id.txtDistanceRegate);
        txtComAffect = (TextView) findViewById(R.id.txtComAffect);

        regateId = getIntent().getStringExtra("regate_id");

        try {
            FindRegateById data = new FindRegateById();
            data.execute(regateId);
            Regate rgt = data.get();

//            Date date = convertDate(evt.getDate());
            System.out.println("DETAILS regateId= " + regateId);

            txtTitreRegate.setText(rgt.getNom_regate());
            txtNumRegate.setText(String.valueOf("N° " + rgt.getNum_regate()));
            txtDateRegate.setText(String.valueOf(rgt.getDate_regate()));
            txtDistanceRegate.setText(String.valueOf(rgt.getDistance_regate()));
//            txtComAffect.setText(rgt.get);


//            Log.i("DATEFORMATEE", String.valueOf(date));
//            Log.i("EVTDATE", String.valueOf(rgt.getDate()));


            final Button btnVoirResultats = (Button) findViewById(R.id.btnVoirResultats);
            btnVoirResultats.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {

                    Intent i = new Intent(getApplicationContext(), ListeScoreRegate.class);
                    i.putExtra("regate_id", regateId);
                    startActivity(i);
                }
            });


        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

    }


//    private static Date convertDate(Date date) {
//        DateFormat formatter = new SimpleDateFormat("yyyy-mm-dd");
//        String fDate = formatter.format(date);
//        Date cDate;
//
//        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd");
//        cDate = dateFormat.parse(fDate);
//
//        return cDate;
//    }


}