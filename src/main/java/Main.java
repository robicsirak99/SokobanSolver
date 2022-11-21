import allapotter.Allapot;
import allapotter.Operator;
import palya.Palya;
import palya.PalyaBetolto;
import montecarlo.MonteCarloFaKereso;

import java.util.ArrayList;
import java.util.List;

public class Main{

    private static final int SZINT = 1;
    private static List<Operator> operatorok = new ArrayList<>(){};
    private static PalyaBetolto palyaBetolto = new PalyaBetolto();

    public static void main(String[] args) {
        Palya palya = palyaBetolto.palyatBetolt(SZINT);
        palya.setAllapot(new Allapot(palya.getPalyaTomb()));

        operatorok.add(new Operator('f'));
        operatorok.add(new Operator('l'));
        operatorok.add(new Operator('j'));
        operatorok.add(new Operator('b'));

        MonteCarloFaKereso monteCarloFaKereso = new MonteCarloFaKereso(operatorok);
        monteCarloFaKereso.algoritmusElindit(palya.getAllapot());
    }
}
