package palya;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PalyaBetolto {

    private static final String PALYA_LOKACIO =  "D:/Rendszerezett Fájlok/Egyetem/Szakdolgozathoz/SokobanLevels/";

    private int palyaSzelesseg = 0;
    private int palyaMagassag = 0;

    public Palya palyatBetolt(int szint) {
        String[] stringPalyaTomb = new String[]{};

        List<String> beolvasottSorLista;
        try (BufferedReader reader = new BufferedReader(new FileReader(PALYA_LOKACIO + "lvl" + szint + ".txt"))) {
            String beolvasottSor;
            beolvasottSorLista = new ArrayList<>();
            while ((beolvasottSor = reader.readLine()) != null) {
                if (palyaSzelesseg < beolvasottSor.length()) palyaSzelesseg = beolvasottSor.length();
                beolvasottSorLista.add(beolvasottSor);
                palyaMagassag++;
            }
            stringPalyaTomb = beolvasottSorLista.toArray(new String[0]);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return new Palya(intPalyaTombKeszit(stringPalyaTomb));
    }

    public int[][] intPalyaTombKeszit(String[] stringPalyaTomb){
        int[][] intPlayaTomb = new int[palyaMagassag][palyaSzelesseg];
        for(int i=0; i<stringPalyaTomb.length; i++){
            for(int j=0; j<stringPalyaTomb[i].length(); j++){
                switch (stringPalyaTomb[i].charAt(j)) {
                    case '-' :{ intPlayaTomb[i][j]=0; break; }
                    case '#' :{ intPlayaTomb[i][j]=1; break; }
                    case '$' :{ intPlayaTomb[i][j]=2; break; }
                    case '.' :{ intPlayaTomb[i][j]=3; break; }
                    case '@' :{ intPlayaTomb[i][j]=4; break; }
                    default:
                        throw new IllegalStateException("Unexpected value: " + stringPalyaTomb[i].charAt(j));
                }
            }
        }
        return  intPlayaTomb;
    }
}
