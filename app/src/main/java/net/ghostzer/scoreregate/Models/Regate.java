package net.ghostzer.scoreregate.Models;

import java.util.Date;

/**
 * Created by Afpa on 28/02/2017.
 */

public class Regate {

    int id_regate;
    String nom_regate;
    int num_regate;
    Date date_regate;
    int distance_regate;
    Commissaire commissaire;

    public Regate(int id_regate, String nom_regate, int num_regate, Date date_regate, int distance_regate) {
        this.id_regate = id_regate;
        this.nom_regate = nom_regate;
        this.num_regate = num_regate;
        this.date_regate = date_regate;
        this.distance_regate = distance_regate;
    }

    public Regate(int id_regate, String nom_regate, int num_regate, Date date_regate, int distance_regate, Commissaire commissaire) {
        this.id_regate = id_regate;
        this.nom_regate = nom_regate;
        this.num_regate = num_regate;
        this.date_regate = date_regate;
        this.distance_regate = distance_regate;
        this.commissaire = commissaire;
    }

    public int getId_regate() {
        return id_regate;
    }

    public void setId_regate(int id_regate) {
        this.id_regate = id_regate;
    }

    public String getNom_regate() {
        return nom_regate;
    }

    public void setNom_regate(String nom_regate) {
        this.nom_regate = nom_regate;
    }

    public int getNum_regate() {
        return num_regate;
    }

    public void setNum_regate(int num_regate) {
        this.num_regate = num_regate;
    }

    public Date getDate_regate() {
        return date_regate;
    }

    public void setDate_regate(Date date_regate) {
        this.date_regate = date_regate;
    }

    public int getDistance_regate() {
        return distance_regate;
    }

    public void setDistance_regate(int distance_regate) {
        this.distance_regate = distance_regate;
    }

    @Override
    public String toString() {
        return "Regate{" +
                "id_regate=" + id_regate +
                ", nom_regate='" + nom_regate + '\'' +
                ", num_regate=" + num_regate +
                ", date_regate=" + date_regate +
                ", distance_regate=" + distance_regate +
                '}';
    }
}
