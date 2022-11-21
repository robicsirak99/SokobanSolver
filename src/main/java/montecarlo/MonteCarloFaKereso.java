package montecarlo;

import allapotter.*;
import heurisztika.HeurisztikaSzamito;

import java.util.List;
import java.util.Random;

public class MonteCarloFaKereso {
    private final List<Operator> operatorok;

    private final AllapotMegjelenito allapotMegjelenito = new AllapotMegjelenito();
    private final AllapotVizsgalo allapotVizsgalo = new AllapotVizsgalo();
    private final HeurisztikaSzamito heurisztikaSzamito = new HeurisztikaSzamito();
    private final Random random = new Random();

    public MonteCarloFaKereso(List<Operator> operatorok){
        this.operatorok = operatorok;
    }

    public void algoritmusElindit(Allapot kezdoAllapot){
        Csomopont gyokerCsomopot = new Csomopont(kezdoAllapot,null);
        Fa fa = new Fa(gyokerCsomopot);

        for(int i=0; i<4; i++){
            if(operatorok.get(i).alkalmazhato(gyokerCsomopot.getAllapot())){
                Allapot ujAllapot = operatorok.get(i).alkalmaz(gyokerCsomopot.getAllapot());
                Csomopont ujCsomopont = new Csomopont(ujAllapot,gyokerCsomopot);
                gyokerCsomopot.gyerekTombAdd(ujCsomopont);
            }
        }

        var gyozelem = false;
        var startTime = System.currentTimeMillis();
        long elapsedTimeInSeconds = 0;

        while(!gyozelem){
            gyozelem = algoritmusIteracio(fa);
            elapsedTimeInSeconds = (System.currentTimeMillis() - startTime) / 1000;
            if(elapsedTimeInSeconds > 10000) break;
        }

        System.out.println("Run time: " + elapsedTimeInSeconds);
    }

    private boolean algoritmusIteracio(Fa fa){
        var jelenlegiCsomopont = fa.getGyoker();

        while (!jelenlegiCsomopont.gyerekTombGet().isEmpty()) {
            jelenlegiCsomopont = UCB1.csomopontValasztUCB1Alapjan(jelenlegiCsomopont);
            if(gyozelemAllapot(jelenlegiCsomopont.getAllapot())){
                eredmenytMegjelenit(jelenlegiCsomopont);
                return true;
            }
        }
        allapotMegjelenito.allapotKirajzol(jelenlegiCsomopont.getAllapot());
        if(jelenlegiCsomopont.getLatogatottsag() == 0){
            if(veresegAllapot(jelenlegiCsomopont.getAllapot())){
                visszaTerjeszt(jelenlegiCsomopont,-1);
            }
            else {
                double randomSzimulacioErteke = randomSzimulacio(jelenlegiCsomopont.getAllapot());
                visszaTerjeszt(jelenlegiCsomopont, randomSzimulacioErteke);
            }
        } else {
            kibovit(fa, jelenlegiCsomopont);
        }
        return false;
    }

    private void kibovit(Fa fa, Csomopont csomopont){
        Csomopont ujCsomopont;
        Allapot ujAllapot;
        for(var i = 0; i < 4; i++){
            if(operatorok.get(i).alkalmazhato(csomopont.getAllapot())){
                ujAllapot = operatorok.get(i).alkalmaz(csomopont.getAllapot());
                if(!allapotMarLetezikAFaban(fa.getGyoker(), ujAllapot)){
                    ujCsomopont = new Csomopont(ujAllapot,csomopont);
                    csomopont.gyerekTombAdd(ujCsomopont);
                }
            }
        }
        if(csomopont.gyerekTombGet().isEmpty()){
            visszaTerjeszt(csomopont, -1);
        } else {
            if(veresegAllapot(csomopont.getAllapot())){
                visszaTerjeszt(csomopont, -1);
            } else {
                csomopont = csomopont.gyerekTombGet().get(0);
                double randomSzimulacioErteke = randomSzimulacio(csomopont.getAllapot());
                visszaTerjeszt(csomopont, randomSzimulacioErteke);
            }
        }
    }

    private boolean gyozelemAllapot(Allapot allapot){
        return allapotVizsgalo.allapotVizsgal(allapot) == 1;
    }

    private boolean veresegAllapot(Allapot allapot){
        return allapotVizsgalo.allapotVizsgal(allapot) == -1;
    }

    private boolean vegallapot(Allapot allapot){
        return allapotVizsgalo.allapotVizsgal(allapot) != 0;
    }

    private boolean allapotMarLetezikAFaban(Csomopont csomopont, Allapot vizsgaltAllapot){
        if(allapotMegegyezik(csomopont.getAllapot(), vizsgaltAllapot)){
            return true;
        }
        else if(!csomopont.gyerekTombGet().isEmpty()){
            for (int i=0; i< csomopont.gyerekTombGet().size(); i++){
                var result = allapotMarLetezikAFaban(csomopont.gyerekTombGet().get(i), vizsgaltAllapot);
                if(result != false){
                    return true;
                }
            }
        }
        return false;
    }

    private boolean allapotMegegyezik(Allapot allapot1, Allapot allapot2){
        for(int i = 0; i<allapot1.tombMagassag(); i++){
            for(int j = 0; j<allapot1.tombSzelesseg(); j++){
                if(allapot1.getAllapotTomb()[i][j] != allapot2.getAllapotTomb()[i][j]) return false;
            }
        }
        return true;
    }

    private void visszaTerjeszt(Csomopont csomopont, double ertek){

        csomopont.setErtek(csomopont.getErtek() + ertek);
        csomopont.setLatogatottsag(csomopont.getLatogatottsag() + 1);

        do {
            csomopont = csomopont.getSzuloCsomopont();
            csomopont.setErtek(csomopont.getErtek() + ertek);
            csomopont.setLatogatottsag(csomopont.getLatogatottsag() + 1);
        } while (csomopont.getSzuloCsomopont() != null);
    }

    private double randomSzimulacio(Allapot allapot){
        var randomOperator = 0;
        if (vegallapot(allapot)) return heurisztikaSzamito.heurisztika(allapot);

        for(var i = 0; i < 10000; i++){
            randomOperator = random.nextInt(4);
            if (operatorok.get(randomOperator).alkalmazhato(allapot)) {
                allapot = operatorok.get(randomOperator).alkalmaz(allapot);
            }
            if (vegallapot(allapot)) break;
        }
        return heurisztikaSzamito.heurisztika(allapot);
    }

    private void eredmenytMegjelenit(Csomopont csomopont){
        allapotMegjelenito.allapotKirajzol(csomopont.getAllapot());
        var faMelyseg = 0;
        do {
            System.out.print("LATOGATOTTSAG: " + csomopont.getLatogatottsag());
            System.out.print(" ERTEK: " + csomopont.getErtek());
            System.out.println();
            csomopont = csomopont.getSzuloCsomopont();
            faMelyseg++;
        } while (csomopont.getSzuloCsomopont() != null);

        System.out.println("FA MELYSEGE: " + faMelyseg);
    }
}
