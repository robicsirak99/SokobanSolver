package palya;

import allapotter.Allapot;

public class Palya {

    private int szelesseg;
    private int magassag;

    private int[][] palyaTomb;

    private Allapot jelenlegiAllapot;

    public Palya(int szelesseg, int magassag, int[][] palyaTomb){
        this.szelesseg = szelesseg;
        this.magassag = magassag;
        this.palyaTomb = palyaTomb;
    }

    public int getSzelesseg() {
        return szelesseg;
    }

    public int getMagassag() {
        return magassag;
    }

    public int[][] getPalyaTomb() {
        return palyaTomb;
    }

    public Allapot getAllapot() {
        return jelenlegiAllapot;
    }

    public void setAllapot(Allapot jelenlegiAllapot) {
        this.jelenlegiAllapot = jelenlegiAllapot;
    }
}
