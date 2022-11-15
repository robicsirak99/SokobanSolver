package palya;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PalyaBetolto {

    public static String PALYA_LOKACIO =  "D:/Rendszerezett Fájlok/Egyetem/Szakdolgozathoz/SokobanLevels/";

    public int palyaSzelesseg = 0;
    public int palyaMagassag = 0;

    public Palya palyatBetolt(int szint) {
        String[] stringPalyaTomb = new String[]{};

        try {
            BufferedReader in = new BufferedReader(new FileReader(PALYA_LOKACIO + "lvl"+szint+".txt"));
            String beolvasottSor;
            List<String> beolvasottSorLista = new ArrayList<>();
            while((beolvasottSor = in.readLine()) != null){
                if(palyaSzelesseg < beolvasottSor.length()) palyaSzelesseg = beolvasottSor.length();
                beolvasottSorLista.add(beolvasottSor);
                palyaMagassag++;
            }
            stringPalyaTomb = beolvasottSorLista.toArray(new String[0]);
        } catch (IOException e){
            e.printStackTrace();
        }

        return new Palya(palyaSzelesseg, palyaMagassag, intPalyaTombKeszit(stringPalyaTomb));
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
                }
            }
        }
        return  intPlayaTomb;
    }
}
