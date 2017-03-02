package net.ghostzer.scoreregate.ApiMethod;

import android.os.AsyncTask;

import net.ghostzer.scoreregate.Config.ConfigApi;
import net.ghostzer.scoreregate.Models.Regate;
import net.ghostzer.scoreregate.Models.Score;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

/**
 * Created by Afpa on 01/03/2017.
 */

public class FindScoreByRegate extends AsyncTask<String, Void, List<Score>> {

    private final String link = ConfigApi.resultatsbyregate;


    @Override
    protected List<Score> doInBackground(String... params) {
        List<Score> scores = new ArrayList<>();
        StringBuilder sb = new StringBuilder();
        HttpURLConnection urlConnection;


        try {
            URL url = new URL(link + params[0]);
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setReadTimeout(9999);
            urlConnection.setConnectTimeout(9999);
            urlConnection.setRequestMethod("GET");
            urlConnection.setRequestProperty("User-Agent", "scoreregate");
            urlConnection.setRequestProperty("Accept", "application/json");
            urlConnection.connect();
            if (urlConnection.getResponseCode() == HttpsURLConnection.HTTP_OK) {
//                System.out.println("OK FIND EVENT");

                InputStream in = new BufferedInputStream(urlConnection.getInputStream());
                BufferedReader br = new BufferedReader(new InputStreamReader(in));

                String line;
                while ((line = br.readLine()) != null) {
                    sb.append(line);
                }
                br.close();

            } else {
                System.out.println("PAS OK FIND REGATE");
            }
            urlConnection.disconnect();

            JSONArray jsonArray = new JSONArray(sb.toString());

            for (int i = 0; i < jsonArray.length(); i++) {

                JSONObject jsonObject = jsonArray.getJSONObject(i);
                int id_voilier = jsonObject.getInt("id_voilier");
                String nom_voilier = jsonObject.getString("nom_voilier");
                String num_voile = jsonObject.getString("num_voile");
                int place = jsonObject.getInt("place");
                int tps_reel = jsonObject.getInt("tps_reel");
                int tps_compense = jsonObject.getInt("tps_compense");

                Score sco = new Score(id_voilier, nom_voilier, num_voile, place, tps_reel, tps_compense);

                scores.add(sco);

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return scores;

    }

    private static Date convertDate(String str) {
        DateFormat formatter = null;
        Date convertedDate = null;
        formatter = new SimpleDateFormat("yyyy-MM-dd");
        try {
            convertedDate = (Date) formatter.parse(str);
        } catch (ParseException ex) {
//            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }

        return convertedDate;
    }


}

