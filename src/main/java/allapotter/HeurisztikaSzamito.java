package allapotter;

public class HeurisztikaSzamito {

    private TavolsagSzamito tavolsagSzamito = new TavolsagSzamito();

    public double heurisztikatSzamit(Allapot allapot){
        var allapotTomb = allapot.getAllapotTomb();
        int dobozACelon = 0;
        for(int i = 0; i < allapot.tombMagassag(); i++){
            for(int j = 0; j < allapot.tombSzelesseg(); j++){
                if(allapotTomb[i][j] == 5) dobozACelon++;
            }
        }
        int temp = (int) (tavolsagSzamito.atlagTavolsagSzamol(allapotTomb)*100);
        return (20 - (double) temp/100) + dobozACelon*2;
    }
}
