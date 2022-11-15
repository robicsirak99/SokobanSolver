package allapotter;

public class Allapot {

    /**
     * 0 - ures
     * 1 - fal
     * 2 - doboz
     * 3 - cel
     * 5 - doboz a celon
     * 4 - jatekos
     * 7 - jatekos a celon
     */

    private int[][] allapotTomb;

    private int utoljaraMozgatottDobozX;
    private int utoljaraMozgatottDobozY;

    public Allapot(int[][] allapotTomb){
        this.allapotTomb = allapotTomb;
        this.utoljaraMozgatottDobozX = -1;
        this.utoljaraMozgatottDobozY = -1;
    }

    public Allapot(int[][] allapotTomb, int utoljaraMozgatottDobozX, int utoljaraMozgatottDobozY){
        this.allapotTomb = allapotTomb;
        this.utoljaraMozgatottDobozX = utoljaraMozgatottDobozX;
        this.utoljaraMozgatottDobozY = utoljaraMozgatottDobozY;
    }

    public int tombMagassag() {
        return this.allapotTomb.length;
    }

    public int tombSzelesseg() {
        return this.allapotTomb[0].length;
    }

    public int[][] getAllapotTomb() {
        return allapotTomb;
    }

    public int getUtoljaraMozgatottDobozX() {
        return utoljaraMozgatottDobozX;
    }

    public int getUtoljaraMozgatottDobozY() {
        return utoljaraMozgatottDobozY;
    }
}
