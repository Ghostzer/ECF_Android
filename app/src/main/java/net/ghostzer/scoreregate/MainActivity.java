package net.ghostzer.scoreregate;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import net.ghostzer.scoreregate.ApiMethod.FindRegateByChallenge;
import net.ghostzer.scoreregate.Models.Regate;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity {
    List<Regate> regates = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        List<Regate> regates = null;
        try {

            FindRegateByChallenge data = new FindRegateByChallenge();
            data.execute();
            regates = data.get();
            ListView listView = (ListView) findViewById(R.id.listViewRegates);
            final ListeRegatesAdapter adapter = new ListeRegatesAdapter(this, regates);
            listView.setAdapter(adapter);
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Regate lregate = adapter.getItem(position);

                    Log.i("selected id regate:", String.valueOf(lregate.getId_regate()));
                    Intent i = new Intent(getApplicationContext(), DetailsRegate.class);
                    startActivity(i);
                }
            });


        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }


    }


}