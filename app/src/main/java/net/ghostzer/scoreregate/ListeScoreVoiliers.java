package net.ghostzer.scoreregate;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;
import java.util.List;
import net.ghostzer.scoreregate.ApiMethod.FindScoreByRegate;
import net.ghostzer.scoreregate.Models.Score;

import java.util.concurrent.ExecutionException;

/**
 * Created by Afpa on 01/03/2017.
 */

public class ListeScoreVoiliers extends AppCompatActivity {
//    List<Score> scores = new ArrayList<>();

        List<Score> scores;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_liste_score_regate);

        String id_regate = getIntent().getExtras().getString("regate_id");

        System.out.print("LISTESCOREREGATE : " + id_regate);

        try {

            FindScoreByRegate data = new FindScoreByRegate();
            data.execute(id_regate);
            scores = data.get();
            ListView listView = (ListView) findViewById(R.id.listViewScore);
            final AdapterListeScoreVoilier adapter = new AdapterListeScoreVoilier(this, scores);
            listView.setAdapter(adapter);




//            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//                @Override
//                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                    Score lregate = adapter.getItem(position);
//
//                    Log.i("selected id regate:", String.valueOf(lregate.getId_voilier()));
//                    Intent i = new Intent(getApplicationContext(), DetailsRegate.class);
//                    startActivity(i);
//                }
//            });


        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }


    }


}