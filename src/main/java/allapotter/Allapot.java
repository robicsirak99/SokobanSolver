package allapotter;

public class Allapot {

    //0 - üres
    //1 - fal
    //2 - doboz
    //3 - cél
    //5 - doboz a célon
    //4 - játékos
    //7 - játékos célon

    private int[][] allapotTomb;

    private int utoljaraMozgatottDobozX;
    private int utoljaraMozgatottDobozY;

    private TavolsagSzamito tavolsagSzamito = new TavolsagSzamito();

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

    public double heurisztika(){
        int dobozACelon = 0;
        for(int i = 0; i < allapotTomb.length; i++){
            for(int j = 0; j < allapotTomb[0].length; j++){
                if(allapotTomb[i][j] == 5) dobozACelon++;
            }
        }
        int temp = (int) (tavolsagSzamito.atlagTavolsagSzamol(this.allapotTomb)*100);
        return (20 - (double) temp/100) + dobozACelon*2;
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
