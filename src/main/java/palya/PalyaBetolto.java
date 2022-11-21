package palya;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class PalyaBetolto {

    private int palyaSzelesseg = 0;
    private int palyaMagassag = 0;

    public Palya palyatBetolt(int szint) {
        String[] stringPalyaTomb = new String[]{};

        ClassLoader classLoader = getClass().getClassLoader();
        InputStream inputStream = classLoader.getResourceAsStream("SokobanLevels/lvl" + szint + ".txt");
        List<String> beolvasottSorLista;

        try (
            InputStreamReader streamReader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
            BufferedReader reader = new BufferedReader(streamReader)) {

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

    private int[][] intPalyaTombKeszit(String[] stringPalyaTomb){
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
