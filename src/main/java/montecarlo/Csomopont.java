package montecarlo;

import allapotter.Allapot;

import java.util.ArrayList;
import java.util.List;

public class Csomopont {
    public Allapot allapot;
    public Csomopont szulo;
    public List<Csomopont> gyerekTomb = new ArrayList<Csomopont>();
    public int latogatottsag;
    public double ertek;
    public Csomopont(Allapot allapot, Csomopont szuloCsomopont){
        this.latogatottsag=0;
        this.allapot=allapot;
        this.szulo=szuloCsomopont;
    }

    public void setErtek(double ertek) {
        this.ertek = ertek;
    }

    public double getErtek() {
        return ertek;
    }

    public void gyerekTombAdd(Csomopont csomopont){
        this.gyerekTomb.add(csomopont);
    }

    public List<Csomopont> gyerekTombGet(){
        return this.gyerekTomb;
    }

    public Allapot getAllapot() {
        return allapot;
    }

    public Csomopont getSzulo() {
        return szulo;
    }

    public void setLatogatottsag(int latogatottsag) {
        this.latogatottsag = latogatottsag;
    }

    public int getLatogatottsag() {
        return latogatottsag;
    }
}
