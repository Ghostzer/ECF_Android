package net.ghostzer.scoreregate.ApiMethod;

import android.os.AsyncTask;

import net.ghostzer.scoreregate.Config.ConfigApi;
import net.ghostzer.scoreregate.Models.Regate;

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
import java.util.Date;

import javax.net.ssl.HttpsURLConnection;

/**
 * Created by Afpa on 28/02/2017.
 */

public class FindRegateBy extends AsyncTask<String, Void, Regate> {

    private final String link = ConfigApi.findRegatebyid;


    @Override
    protected Regate doInBackground(String... params) {
        StringBuilder sb = new StringBuilder();
        HttpURLConnection urlConnection;
        Regate rgt = null;

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
                System.out.println("OK");

                InputStream in = new BufferedInputStream(urlConnection.getInputStream());
                BufferedReader br = new BufferedReader(new InputStreamReader(in));

                String line;
                while ((line = br.readLine()) != null) {
                    sb.append(line);
                }
                br.close();

            } else {
                System.out.println("PAS OK");
            }
            urlConnection.disconnect();

            JSONObject jsonObject= new JSONObject(sb.toString());

            int id_regate = jsonObject.getInt("id_regate");
            String nom_regate = jsonObject.getString("nom_regate");
            int num_regate = jsonObject.getInt("num_regate");
            String date_regate = jsonObject.getString("date_regate");
            int distance_regate = jsonObject.getInt("distance_regate");

            Date dateRegate = convertDate(date_regate);

            rgt = new Regate(id_regate, nom_regate, num_regate, dateRegate, distance_regate);




        } catch (Exception e) {
            e.printStackTrace();
        }
        return rgt;

    }

    private static Date convertDate(String str) {
        DateFormat formatter = null;
        Date convertedDate = null;
        formatter = new SimpleDateFormat("yyyy-mm-dd");
        try {
            convertedDate = formatter.parse(str);
        } catch (ParseException ex) {
//            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }

        return convertedDate;
    }
}
