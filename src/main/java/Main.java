import allapotter.Allapot;
import allapotter.Operator;
import allapotter.PalyaBeolvaso;
import montecarlo.MonteCarloFaKereso;
import montecarlo.UCB1;

import java.util.ArrayList;
import java.util.List;

public class Main{

    public static List<Operator> OPERATOROK = new ArrayList<Operator>();

    public static void main(String[] args) {

        PalyaBeolvaso palyaBeolvaso = new PalyaBeolvaso();
        int szint = 1;
        String[] jelenlegi_palya = palyaBeolvaso.beolvasKovetkezoSzint(szint);
        int palya_szelesseg = palyaBeolvaso.palya_szelesseg;
        int palya_magassag = palyaBeolvaso.palya_magassag;

        Allapot kezdoAllapot = kezdoAllapotKeszit(jelenlegi_palya, palya_magassag, palya_szelesseg);

        kezdoAllapot.allapotKirajzol();

        OPERATOROK.add(new Operator('f'));
        OPERATOROK.add(new Operator('l'));
        OPERATOROK.add(new Operator('j'));
        OPERATOROK.add(new Operator('b'));

        MonteCarloFaKereso monteCarloFaKereso = new MonteCarloFaKereso(kezdoAllapot,OPERATOROK);
        monteCarloFaKereso.elindit();
    }


    public static Allapot kezdoAllapotKeszit(String[] jelenlegi_palya,int magassag, int szelesseg){
        int[][] allapot_tomb = new int[magassag][szelesseg];
        for(int i=0; i<jelenlegi_palya.length; i++){
            for(int j=0; j<jelenlegi_palya[i].length(); j++){
                switch (jelenlegi_palya[i].charAt(j)) {
                    case '-' :{ allapot_tomb[i][j]=0; break; }
                    case '#' :{ allapot_tomb[i][j]=1; break; }
                    case '$' :{ allapot_tomb[i][j]=2; break; }
                    case '.' :{ allapot_tomb[i][j]=3; break; }
                    case '@' :{ allapot_tomb[i][j]=4; break; }
                }
            }
        }
        return new Allapot(allapot_tomb,-1,-1);
    }
}
