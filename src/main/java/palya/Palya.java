package palya;

import allapotter.Allapot;

public class Palya {

    private int[][] palyaTomb;

    private Allapot jelenlegiAllapot;

    public Palya(int[][] palyaTomb){
        this.palyaTomb = palyaTomb;
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
