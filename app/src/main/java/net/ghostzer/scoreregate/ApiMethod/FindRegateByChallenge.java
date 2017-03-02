package net.ghostzer.scoreregate.ApiMethod;

import android.os.AsyncTask;
import net.ghostzer.scoreregate.Config.ConfigApi;
import net.ghostzer.scoreregate.Models.Regate;
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

public class FindRegateByChallenge extends AsyncTask<String, Void, List<Regate>> {

    // winter of summer
    private final String link = ConfigApi.findeventbychallengewinter;


    @Override
    protected List<Regate> doInBackground(String... params) {
        StringBuilder sb = new StringBuilder();
        HttpURLConnection urlConnection;

        List<Regate> regates = new ArrayList<>();

        try {
            URL url = new URL(link);
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setReadTimeout(9999);
            urlConnection.setConnectTimeout(9999);
            urlConnection.setRequestMethod("GET");
            urlConnection.setRequestProperty("User-Agent", "scoreregate");
            urlConnection.setRequestProperty("Accept", "application/json");
            urlConnection.connect();
            if (urlConnection.getResponseCode() == HttpsURLConnection.HTTP_OK) {
                System.out.println("OK FIND EVENT");

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
                int id_regate = jsonObject.getInt("id_regate");
                String nom_regate = jsonObject.getString("nom_regate");
                int num_regate = jsonObject.getInt("num_regate");
                String date_regate = jsonObject.getString("date_regate");
                int distance_regate = jsonObject.getInt("distance_regate");
                String nom_commissaire = jsonObject.getString("nom_personne");
                String prenom_commissaire = jsonObject.getString("prenom_personne");

                Date dateRegate = convertDate(date_regate);

                Regate rgt = new Regate(id_regate, nom_regate, num_regate, dateRegate, distance_regate, nom_commissaire, prenom_commissaire);

                regates.add(rgt);

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return regates;

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
