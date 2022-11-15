package montecarlo;

import allapotter.Allapot;

import java.util.ArrayList;
import java.util.List;

public class Csomopont {

    private Allapot allapot;
    private Csomopont szuloCsomopont;
    private List<Csomopont> gyerekCsomopontTomb = new ArrayList<>();

    private int latogatottsag;
    private double ertek;

    public Csomopont(Allapot allapot, Csomopont szuloCsomopont){
        this.latogatottsag=0;
        this.allapot=allapot;
        this.szuloCsomopont =szuloCsomopont;
    }

    public void setErtek(double ertek) {
        this.ertek = ertek;
    }

    public double getErtek() {
        return ertek;
    }

    public void gyerekTombAdd(Csomopont csomopont){
        this.gyerekCsomopontTomb.add(csomopont);
    }

    public List<Csomopont> gyerekTombGet(){
        return this.gyerekCsomopontTomb;
    }

    public Allapot getAllapot() {
        return allapot;
    }

    public Csomopont getSzuloCsomopont() {
        return szuloCsomopont;
    }

    public void setLatogatottsag(int latogatottsag) {
        this.latogatottsag = latogatottsag;
    }

    public int getLatogatottsag() {
        return latogatottsag;
    }
}
