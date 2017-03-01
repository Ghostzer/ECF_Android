package net.ghostzer.scoreregate.Models;

/**
 * Created by Afpa on 01/03/2017.
 */

public class Score {

    int id_voilier;
    String nom_voilier;
    String num_voile;
    int place;
    int tps_reel;
    int tps_compense;

    public Score(int id_voilier, String nom_voilier, String num_voile, int place, int tps_reel, int tps_compense) {
        this.id_voilier = id_voilier;
        this.nom_voilier = nom_voilier;
        this.num_voile = num_voile;
        this.place = place;
        this.tps_reel = tps_reel;
        this.tps_compense = tps_compense;
    }

    public int getId_voilier() {
        return id_voilier;
    }

    public void setId_voilier(int id_voilier) {
        this.id_voilier = id_voilier;
    }

    public String getNom_voilier() {
        return nom_voilier;
    }

    public void setNom_voilier(String nom_voilier) {
        this.nom_voilier = nom_voilier;
    }

    public String getNum_voile() {
        return num_voile;
    }

    public void setNum_voile(String num_voile) {
        this.num_voile = num_voile;
    }

    public int getPlace() {
        return place;
    }

    public void setPlace(int place) {
        this.place = place;
    }

    public int getTps_reel() {
        return tps_reel;
    }

    public void setTps_reel(int tps_reel) {
        this.tps_reel = tps_reel;
    }

    public int getTps_compense() {
        return tps_compense;
    }

    public void setTps_compense(int tps_compense) {
        this.tps_compense = tps_compense;
    }

    @Override
    public String toString() {
        return "Score{" +
                "id_voilier=" + id_voilier +
                ", nom_voilier='" + nom_voilier + '\'' +
                ", num_voile='" + num_voile + '\'' +
                ", place=" + place +
                ", tps_reel=" + tps_reel +
                ", tps_compense=" + tps_compense +
                '}';
    }
}
