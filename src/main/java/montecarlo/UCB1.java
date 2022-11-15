package montecarlo;

import java.util.Collections;
import java.util.Comparator;

public class UCB1 {

    public static double ucb1Value(
            int N,  // parent node visit
            int ni, // current node visit
            double ti  // current node score
        ){

        /*

        UCB1(Si) = Vi + c * sqrt(ln(N) / ni)
        Vi : average value : Vi = ti / ni
        ti : current node score
        ni : current node visit
        c : usually sqrt(2)
        N : parent node visit

        */

        double Vi = (double) ti / (double) ni;
        double c = 1.41 ;
        double ucb1Val = Vi + c * Math.sqrt(Math.log(N) / (double) ni);

        if (ni == 0) {
            //System.out.println(Integer.MAX_VALUE);
            return Integer.MAX_VALUE;
        }
        //System.out.println(ucb1Val);
        return ucb1Val;

    }

    public static Csomopont findBestNodeWithUCB1(Csomopont csomopont){
        int parentNodeVisit = csomopont.getLatogatottsag();
        return Collections.max(csomopont.gyerekTombGet(), Comparator.comparing(c -> ucb1Value(parentNodeVisit, c.getLatogatottsag(), c.getErtek())));
    }

}
