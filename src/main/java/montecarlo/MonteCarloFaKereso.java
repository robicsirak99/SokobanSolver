package montecarlo;

import allapotter.Allapot;
import allapotter.HalottHelyDetektalo;
import allapotter.Operator;

import java.util.List;
import java.util.Random;

public class MonteCarloFaKereso {
    public Allapot kezdoAllapot;
    public List<Operator> OPERATOROK;
    public Fa fa;
    public boolean benne_viszgalo = false;

    public UCB1 ucb1 = new UCB1();

    public MonteCarloFaKereso(Allapot kezdoAllapot, List<Operator> OPERATOROK){
        this.kezdoAllapot = kezdoAllapot;
        this.OPERATOROK = OPERATOROK;
    }

    public void elindit(){
        Csomopont gyokerCsomopot = new Csomopont(this.kezdoAllapot,null);
        fa = new Fa(gyokerCsomopot);

        for(int i=0; i<4; i++){
            if(OPERATOROK.get(i).alkalmazhato(gyokerCsomopot.getAllapot())){
                Allapot ujAllapot = OPERATOROK.get(i).alkalmaz(gyokerCsomopot.getAllapot());
                Csomopont ujCsomopont = new Csomopont(ujAllapot,gyokerCsomopot);
                gyokerCsomopot.gyerekTombAdd(ujCsomopont);
            }
        }

        boolean c = false;
        for(int k=0; k<1000000; k++) {
            c = algoritmus(gyokerCsomopot);
            if(c) break;
        }
        System.out.println(gyokerCsomopot.getErtek());
        vegigjar(this.fa.getGyoker());
    }

    public boolean algoritmus1(Csomopont jelenlegiCsomopont){

        //amíg el nem érünk egy levél csomópontot, végigmegyünk a fán mindíg a nagyobb UCB1 értékű csomópontot választva
        while (jelenlegiCsomopont.gyerekTombGet().size()!=0) {
            jelenlegiCsomopont = ucb1.findBestNodeWithUCB1(jelenlegiCsomopont);
            /*int valasztott_gyerek_index = gyerekCsomopontValasztlUCB1Alapjan(jelenlegiCsomopont);
            jelenlegiCsomopont = jelenlegiCsomopont.gyerekTombGet().get(valasztott_gyerek_index);*/
        }

        jelenlegiCsomopont.allapot.allapotKirajzol();
        if(jelenlegiCsomopont.allapot.vereseg_vegallapot()) System.out.println("----*********-----------VESZTES CEL----------------------//////////////////----");
        System.out.println(jelenlegiCsomopont.allapot.heurisztika());

        //ha nyertes célhoz jutottunk kilépünk az algoritmusból (ezt később talán ki kell venni innnen)
        if(jelenlegiCsomopont.allapot.nyertes_cel()){ return true; }

        //ha a csomópont látogatottsága 0, csinálunk egy rollout és visszaterjesztjük a kapott értékkel
        if(jelenlegiCsomopont.getLatogatottsag()==0 && jelenlegiCsomopont.allapot != kezdoAllapot){
            System.out.println("ROLLOUT(0 LATOGATOTTSAG)");
            double rollout_ertek = randomLepked(jelenlegiCsomopont.getAllapot());
            System.out.println("ROLLOUT ERTEK: " + rollout_ertek);
            visszaTerjeszt(jelenlegiCsomopont,rollout_ertek);
        }

        //ha a csomópont látogatottsága nem 0, az összes lehetséges lépéshez új csomópontot hozunk létre a fában
        else {
            System.out.println("EXPANSION STARTED");
            Csomopont ujCsomopont;
            Allapot ujAllapot;
            for(int i=0; i<4; i++){
                if(OPERATOROK.get(i).alkalmazhato(jelenlegiCsomopont.getAllapot())){
                    System.out.println("talaltunk egy alkalmazhato operatort:" + i);
                    ujAllapot = OPERATOROK.get(i).alkalmaz(jelenlegiCsomopont.getAllapot());
                    this.benne_viszgalo=false;
                    //System.out.println("vizsgalat elott:" + this.benne_viszgalo);
                    benne(this.fa.getGyoker(),ujAllapot);
                    //System.out.println("vizsgalat utan:" + this.benne_viszgalo);*/
                    if(benne_viszgalo==false){
                        ujCsomopont = new Csomopont(ujAllapot,jelenlegiCsomopont);
                        jelenlegiCsomopont.gyerekTombAdd(ujCsomopont);
                    }
                }
            }
            //ha az így létrejött gyerek csomópont tömb üres, akkor csinálunk egy rolloutot
            if(jelenlegiCsomopont.gyerekTombGet().size()==0){
                System.out.println("ROLLOUT(CANNOT EXPAND PROBLEM)");
                double rollout_ertek = randomLepked(jelenlegiCsomopont.getAllapot());
                visszaTerjeszt(jelenlegiCsomopont,rollout_ertek);
            }

            //ha az így létrejött gyerek csomópont tömb nem üres, akkor kiválasztjuk az első gyereket, és csinálunk egy rolloutot
            else {
                jelenlegiCsomopont = jelenlegiCsomopont.gyerekTombGet().get(0);
                System.out.println("ROLLOUT(EXPANSION UTAN)");
                double rollout_ertek = randomLepked(jelenlegiCsomopont.getAllapot());
                visszaTerjeszt(jelenlegiCsomopont,rollout_ertek);
                System.out.println("EXPANTION FINISHED");
            }
        }
        return false;
    }

    public boolean algoritmus(Csomopont jelenlegiCsomopont){

        //UCB1 alapján a legjobb csomópontokon végigmegyünk
        while (jelenlegiCsomopont.gyerekTombGet().size()!=0) {
            jelenlegiCsomopont = ucb1.findBestNodeWithUCB1(jelenlegiCsomopont);
            if(jelenlegiCsomopont.allapot.nyertes_cel()){
                System.out.println("...................WIN....................");
                return true;
            }
        }

        jelenlegiCsomopont.allapot.allapotKirajzol();
        //if(jelenlegiCsomopont.allapot.vereseg_vegallapot()) System.out.println("----*********-----------VESZTES CEL----------------------//////////////////----");
        //System.out.println(jelenlegiCsomopont.allapot.heurisztika());
        if(jelenlegiCsomopont.getLatogatottsag()==0 && jelenlegiCsomopont.allapot != kezdoAllapot){
            if(jelenlegiCsomopont.allapot.vegallapot == -1){
                visszaTerjeszt(jelenlegiCsomopont,-1);
            }
            /*if(jelenlegiCsomopont.allapot.vereseg_vegallapot()){
                visszaTerjeszt(jelenlegiCsomopont,-1);
            }*/
            else {
                System.out.println("ROLLOUT and BACKPROPAGATE.");
                double rollout_ertek = randomLepked(jelenlegiCsomopont.getAllapot());
                System.out.println("ROLLOUT ERTEK: " + rollout_ertek);
                visszaTerjeszt(jelenlegiCsomopont,rollout_ertek);
            }
        } else {
            System.out.println("EXPAND");
            Csomopont ujCsomopont;
            Allapot ujAllapot;
            for(int i=0; i<4; i++){
                if(OPERATOROK.get(i).alkalmazhato(jelenlegiCsomopont.getAllapot())){
                    ujAllapot = OPERATOROK.get(i).alkalmaz(jelenlegiCsomopont.getAllapot());
                    //if(!ujAllapot.vesztes_cel()){
                    this.benne_viszgalo=false;
                    benne(this.fa.getGyoker(),ujAllapot);
                    if(benne_viszgalo==false){
                        ujCsomopont = new Csomopont(ujAllapot,jelenlegiCsomopont);
                        jelenlegiCsomopont.gyerekTombAdd(ujCsomopont);
                    } //else System.out.println("MAR BENNE VAN..........................................");
                    //} else System.out.println("VESZTES CELT NEM RAKUNK BELE"); -> EZ FONTOS CSAK NEM IGY
                }
            }
            //CANNOT EXPAND PROBLEM
            if(jelenlegiCsomopont.gyerekTombGet().size()==0){
                //jelenlegiCsomopont.getAllapot().allapotKirajzol();
                System.out.println("CANNOT EXPAND HERE");
                visszaTerjeszt(jelenlegiCsomopont, -1);
            } else {
                if(jelenlegiCsomopont.getAllapot().vesztes_cel()) {
                    System.out.println("VESZTES CEL EXPANTION ELOT");
                    visszaTerjeszt(jelenlegiCsomopont, -1);
                } else {
                    System.out.println("EXPANTION STARTED");
                    jelenlegiCsomopont = jelenlegiCsomopont.gyerekTombGet().get(0);
                    double rollout_ertek = randomLepked(jelenlegiCsomopont.getAllapot());
                    System.out.println("GOT THE OLLOUT VALUE");
                    visszaTerjeszt(jelenlegiCsomopont,rollout_ertek);
                    System.out.println("EXPANTION FINISHED");
                }
            }
        }
        return false;
    }

    public void benne(Csomopont csomopont, Allapot vizsgaltAllapot){
        if(allapotMegegyezik(csomopont.getAllapot(),vizsgaltAllapot)) this.benne_viszgalo = true;
        else if(csomopont.gyerekTombGet().size()!=0){
            for (int i=0; i< csomopont.gyerekTombGet().size(); i++){
                benne(csomopont.gyerekTombGet().get(i), vizsgaltAllapot);
            }
        }
    }
    public boolean allapotMegegyezik(Allapot allapot1, Allapot allapot2){
        boolean c = true;
        for(int i=0; i<allapot1.allapot_tomb.length; i++){
            for(int j=0; j<allapot1.allapot_tomb[0].length; j++){
                if(allapot1.allapot_tomb[i][j]!=allapot2.allapot_tomb[i][j]) c=false;
            }
        }
        return c;
    }

    public void visszaTerjeszt(Csomopont csomopont, double ertek){
        while (true){
            csomopont.setErtek(csomopont.getErtek()+ertek);
            csomopont.setLatogatottsag(csomopont.getLatogatottsag()+1);
            csomopont = csomopont.getSzulo();
            if(csomopont.getAllapot()==this.kezdoAllapot){
                csomopont.setErtek(csomopont.getErtek()+ertek);
                csomopont.setLatogatottsag(csomopont.getLatogatottsag()+1);
                break;
            }
        }
        System.out.println("KILEPTT A VISSZATERJESZTESBOL");

    }

    public double randomLepked(Allapot allapot){
        System.out.println("| random lepkedes megkezdve");
        //allapot.allapotKirajzol();
        Random random = new Random();
        int random_operator;
        /*for(int i = 0; i <= 3; i++){
            if(OPERATOROK.get(i).alkalmazhato(allapot)){
                allapot = OPERATOROK.get(i).alkalmaz(allapot);
                talalt = true;
            }
            if(allapot.vesztes_cel() || allapot.nyertes_cel()) break;
        }*/
        int k = 0;
        while(true){
            k++;
            if(allapot.vereseg_vegallapot() || allapot.nyertes_cel()) break;
            random_operator = random.nextInt(4);
            //System.out.println(random_operator);
            if(OPERATOROK.get(random_operator).alkalmazhato(allapot)){
                allapot = OPERATOROK.get(random_operator).alkalmaz(allapot);
            }
            if(allapot.vereseg_vegallapot() || allapot.nyertes_cel()) break;
            if(k == 1000) {
                System.out.println("NEM TALALT OPERATORT???????????????????????");
                break;
            }
        }
        System.out.println("| random lepkedes befejezve");
        return allapot.heurisztika();
    }

    public int gyerekCsomopontValasztlUCB1Alapjan(Csomopont csomopont){
        double ucb1 = 0;
        int gyerek_csomopont_index = 0;
        for(int i=0; i<csomopont.gyerekTombGet().size(); i++){
            if(csomopont.gyerekTombGet().get(i).getLatogatottsag()==0) return i;
            else {
                double ertek = (csomopont.gyerekTombGet().get(i).getErtek()) / (csomopont.gyerekTombGet().get(i).getLatogatottsag())
                        + 2 * (Math.sqrt(Math.log(csomopont.getLatogatottsag()) / (csomopont.gyerekTombGet().get(i).getLatogatottsag())));
                if(ertek > ucb1){
                    ucb1 = ertek;
                    gyerek_csomopont_index = i;
                }
            }
        }
        return gyerek_csomopont_index;
    }

    public int operatorValaszt(Allapot allapot){
        Random random = new Random();
        int random_operator = -1;
        while(true){
            random_operator = random.nextInt(4);
            if(OPERATOROK.get(random_operator).alkalmazhato(allapot))
                if(!OPERATOROK.get(random_operator).alkalmaz(allapot).vesztes_cel()) break;
        }
        return random_operator;
    }

    public void vegigjar(Csomopont jelenlegiCsomopont){
        int k = 0;
        while(true){
            System.out.println(jelenlegiCsomopont.latogatottsag);
            k++;
            int gyerek_tomb_index = gyerekCsomopontValasztlUCB1Alapjan(jelenlegiCsomopont);
            jelenlegiCsomopont = jelenlegiCsomopont.gyerekTombGet().get(gyerek_tomb_index);
            if(jelenlegiCsomopont.gyerekTombGet().size()==0) break;
        }
        System.out.println("K: "+k);
        jelenlegiCsomopont.getAllapot().allapotKirajzol();
    }

}
