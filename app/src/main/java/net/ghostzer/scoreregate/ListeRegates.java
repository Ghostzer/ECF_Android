package net.ghostzer.scoreregate;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import net.ghostzer.scoreregate.ApiMethod.FindRegates;
import net.ghostzer.scoreregate.Models.Regate;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * Created by Afpa on 28/02/2017.
 */

public class ListeRegates extends AppCompatActivity {
    List<Regate> events = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        List<Regate> regates = null;
        try {

            FindRegates data = new FindRegates();
            data.execute();
            regates = data.get();
            ListView listView = (ListView) findViewById(R.id.listViewRegates);
            final ListeRegatesAdapter adapter = new ListeRegatesAdapter(this, events);
            listView.setAdapter(adapter);
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Regate lregate = adapter.getItem(position);

                    Log.i("selected id event:", String.valueOf(lregate.getId_regate()));
                    Intent i = new Intent(getApplicationContext(), DetailsRegate.class);
                    startActivity(i);

                }
            });



//            FloatingActionButton btnAddEvent = (FloatingActionButton)findViewById(R.id.btnAddEvent);
//            btnAddEvent.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    Intent activityChangeIntent = new Intent(ListEvents.this, AddEvent.class);
//                    ListEvents.this.startActivity(activityChangeIntent);
//                }
//            });





        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }


    }


}