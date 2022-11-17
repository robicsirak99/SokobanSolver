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

        boolean c = false;
        long startTime = System.currentTimeMillis();

        for(int k=0; k<1000000; k++) {
            c = algoritmus(fa, gyokerCsomopot);
            if(c) break;
        }

        long elapsedTime = System.currentTimeMillis() - startTime;
        long elapsedSeconds = elapsedTime / 1000;

        System.out.println("Run time: " + elapsedSeconds);
    }

    private boolean algoritmus(Fa fa, Csomopont jelenlegiCsomopont){
        var kezdoAllapot = jelenlegiCsomopont.getAllapot();

        //UCB1 alapján a legjobb csomópontokon végigmegyünk
        while (!jelenlegiCsomopont.gyerekTombGet().isEmpty()) {
            jelenlegiCsomopont = UCB1.csomopontValasztUCB1Alapjan(jelenlegiCsomopont);
            if(gyozelemAllapot(jelenlegiCsomopont.getAllapot())){
                allapotMegjelenito.allapotKirajzol(jelenlegiCsomopont.getAllapot());
                eredmenytMegjelenit(jelenlegiCsomopont);
                return true;
            }
        }

        allapotMegjelenito.allapotKirajzol(jelenlegiCsomopont.getAllapot());

        if(jelenlegiCsomopont.getLatogatottsag()==0 && jelenlegiCsomopont.getAllapot() != kezdoAllapot){
            if(veresegAllapot(jelenlegiCsomopont.getAllapot())){
                visszaTerjeszt(jelenlegiCsomopont,-1);
            }
            else {
                System.out.println("RANDOM SZIMULACIO + VISSZATERJESZTES");
                double randomSzimulacioErteke = randomSzimulacio(jelenlegiCsomopont.getAllapot());
                System.out.println("RANDOM SZIMULACIO ERTEKE: " + randomSzimulacioErteke);
                visszaTerjeszt(jelenlegiCsomopont, randomSzimulacioErteke);
            }
        } else {
            System.out.println("BOVITES");
            Csomopont ujCsomopont;
            Allapot ujAllapot;
            for(int i=0; i<4; i++){
                if(operatorok.get(i).alkalmazhato(jelenlegiCsomopont.getAllapot())){
                    ujAllapot = operatorok.get(i).alkalmaz(jelenlegiCsomopont.getAllapot());

                    if(!allapotMarLetezikAFaban(fa.getGyoker(), ujAllapot)){
                        ujCsomopont = new Csomopont(ujAllapot,jelenlegiCsomopont);
                        jelenlegiCsomopont.gyerekTombAdd(ujCsomopont);
                    }
                }
            }
            if(jelenlegiCsomopont.gyerekTombGet().isEmpty()){
                System.out.println("BOVITES NEM LEHETSEGES");
                visszaTerjeszt(jelenlegiCsomopont, -1);
            } else {
                if(veresegAllapot(jelenlegiCsomopont.getAllapot())){
                    System.out.println("VESZTES CEL BOVITES ELOTT");
                    visszaTerjeszt(jelenlegiCsomopont, -1);
                } else {
                    System.out.println("KIBOVITES MEGKEZDVE");
                    jelenlegiCsomopont = jelenlegiCsomopont.gyerekTombGet().get(0);
                    double randomSzimulacioErteke = randomSzimulacio(jelenlegiCsomopont.getAllapot());
                    System.out.println("RANDOM SZIMULACIOS ERTEK MEKAPVA");
                    visszaTerjeszt(jelenlegiCsomopont, randomSzimulacioErteke);
                    System.out.println("KIBOVITES BEFEJEZVE");
                }
            }
        }
        return false;
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
        boolean c = true;
        for(int i = 0; i<allapot1.tombMagassag(); i++){
            for(int j = 0; j<allapot1.tombSzelesseg(); j++){
                if(allapot1.getAllapotTomb()[i][j]!=allapot2.getAllapotTomb()[i][j]) c=false;
            }
        }
        return c;
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
        System.out.println("RANDOM SZIMULACIO MEGKEZDVE");
        Random random = new Random();
        int randomOperator;

        int k = 0;
        while (true) {
            k++;
            if (vegallapot(allapot)) break;
            randomOperator = random.nextInt(4);
            if (operatorok.get(randomOperator).alkalmazhato(allapot)) {
                allapot = operatorok.get(randomOperator).alkalmaz(allapot);
            }
            if (vegallapot(allapot)) break;
            if (k == 100) {
                System.out.println("NEM TALALT OPERATORT");
                break;
            }
        }
        System.out.println("RANDOM SZIMULACIO BEFEJEZVE");

        return heurisztikaSzamito.heurisztika(allapot);
    }

    private void eredmenytMegjelenit(Csomopont csomopont){
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
