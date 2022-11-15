package allapotter;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PalyaBeolvaso {

    public int palya_szelesseg = 0;
    public int palya_magassag = 0;

    public String[] beolvasKovetkezoSzint(int szint){
        try {
            BufferedReader in = new BufferedReader(new FileReader("C:/Users/robic/Desktop/szakdolgozat/SokobanLevels/lvl"+szint+".txt"));
            String sor;
            List<String> list = new ArrayList<String>();
            while((sor = in.readLine()) != null){
                if(palya_szelesseg < sor.length()) palya_szelesseg = sor.length();
                list.add(sor);
                palya_magassag++;
            }
            String[] stringArr = list.toArray(new String[0]);
            return stringArr;
        } catch (IOException e){
            e.printStackTrace();
        }

        return null;
    }

}
