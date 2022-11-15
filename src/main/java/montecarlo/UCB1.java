package montecarlo;

import java.util.Collections;
import java.util.Comparator;

public class UCB1 {

    /**
    UCB1(Si) = Vi + c * sqrt(ln(N) / ni)
    Vi : average value : Vi = ti / ni
    ti : current node score
    ni : current node visit
    c : usually sqrt(2)
    N : parent node visit
    */
    public static double ucb1Ertek(
            int N,  // parent node visit
            int ni, // current node visit
            double ti  // current node score
        ){

        double Vi = ti / ni;
        double c = 1.41 ;
        double ucb1Val = Vi + c * Math.sqrt(Math.log(N) / ni);

        if (ni == 0) return Integer.MAX_VALUE;
        return ucb1Val;

    }

    public static Csomopont csomopontKeresUCB1Alapjan(Csomopont csomopont){
        int parentNodeVisit = csomopont.getLatogatottsag();
        return Collections.max(csomopont.gyerekTombGet(), Comparator.comparing(c -> ucb1Ertek(parentNodeVisit, c.getLatogatottsag(), c.getErtek())));
    }

}
