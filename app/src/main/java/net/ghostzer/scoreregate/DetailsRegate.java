package net.ghostzer.scoreregate;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.frateinc.yopbooking2.ApiMethod.FindEventById;
import com.frateinc.yopbooking2.Models.Event;

import java.util.Date;
import java.util.concurrent.ExecutionException;

/**
 * Created by Afpa on 28/02/2017.
 */

public class DetailsRegate extends AppCompatActivity {
    TextView txtDetailsPartyName;
    TextView txtDetailsOrg;
    TextView txtDetailsDate;
    TextView txtDetailsaddress;
    TextView txtDetailsZipcode;
    TextView txtDetailsComment;
    String eventId;
    Date date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.details_event);

        txtDetailsPartyName = (TextView) findViewById(R.id.txtDetailsPartyName);
        txtDetailsOrg = (TextView) findViewById(R.id.txtDetailsOrg);
        txtDetailsDate = (TextView) findViewById(R.id.txtDetailsDate);
        txtDetailsaddress = (TextView) findViewById(R.id.txtDetailsaddress);
        txtDetailsZipcode = (TextView) findViewById(R.id.txtDetailsZipcode);
        txtDetailsComment = (TextView) findViewById(R.id.txtDetailsComment);
        txtDetailsComment.setMovementMethod(new ScrollingMovementMethod());
        btnDetailsRegister = (FloatingActionButton)findViewById(R.id.btnDetailsRegister);

        eventId = getIntent().getStringExtra("event_id");

        try {
            FindEventById data = new FindEventById();
            data.execute(eventId);
            Event evt = data.get();

//            Date date = convertDate(evt.getDate());
            txtDetailsPartyName.setText(evt.getTitle());
            txtDetailsOrg.setText("Par " + evt.getFirstname() + " " + evt.getLastname());
            txtDetailsDate.setText(evt.getDate() + " à " + evt.getHour() + " h.");
            txtDetailsaddress.setText(evt.getaddress());
            txtDetailsZipcode.setText(String.valueOf(evt.getZipcode() + " " + evt.getCity()));
            txtDetailsComment.setText(evt.getComment());

            Log.i("DATEFORMATEE", String.valueOf(date));
            Log.i("EVTDATE", String.valueOf(evt.getDate()));


        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        System.out.println("eventId= " + eventId);

        // Bouton à voir, fait planter =>

//        btnDetailsRegister = (FloatingActionButton) findViewById(R.id.btnDetailsRegister);
//        btnDetailsRegister.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                Intent intent = new Intent(DetailsEvent.this, RegisterEvent.class);
//                intent.putExtra("event_id", "" + eventId);
//                DetailsEvent.this.startActivity(intent);
//            }
//        });
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