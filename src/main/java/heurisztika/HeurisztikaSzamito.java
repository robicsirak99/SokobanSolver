package heurisztika;

import allapotter.Allapot;

import java.util.ArrayList;
import java.util.List;

public class HeurisztikaSzamito {

    public double heurisztika(Allapot allapot){
        int dobozACelon = 0;
        for(int i = 0; i < allapot.getAllapotTomb().length; i++){
            for(int j = 0; j < allapot.getAllapotTomb()[0].length; j++){
                if(allapot.getAllapotTomb()[i][j] == 5) dobozACelon++;
            }
        }
        int temp = (int) (atlagTavolsagSzamol(allapot.getAllapotTomb())*100);
        return (20 - (double) temp/100) + dobozACelon*2;
    }

    public double atlagTavolsagSzamol(int[][] allapotTomb){
        List<int[]> dobozok = dobozokFeltolt(allapotTomb);
        List<int[]> celok = celokFeltolt(allapotTomb, dobozok);

        double atlagTavolsag = 0;
        for(int i = 0; i< dobozok.size(); i++){
            atlagTavolsag = atlagTavolsag + Math.abs(dobozok.get(i)[0]-celok.get(i)[0]) + Math.abs(dobozok.get(i)[1]-celok.get(i)[1]);
        }
        return atlagTavolsag/ dobozok.size();
    }

    public List<int[]> dobozokFeltolt(int[][] allapotTomb){
        List<int[]> dobozok = new ArrayList<>();

        for(int i=0; i<allapotTomb.length; i++){
            for(int j=0; j<allapotTomb[0].length; j++){
                if((allapotTomb[i][j] == 2) || (allapotTomb[i][j] == 5)) dobozok.add(new int[]{i,j});
            }
        }

        return dobozok;
    }

    public List<int[]> celokFeltolt(int[][] allapotTomb, List<int[]> dobozok){
        List<int[]> celok = new ArrayList<>();

        for (int[] koord : dobozok) {
            int distance = 99999;
            int celX = -1;
            int celY = -1;
            for(int i=0; i<allapotTomb.length; i++){
                for(int j=0; j<allapotTomb[0].length; j++){
                    if((allapotTomb[i][j]==3) || (allapotTomb[i][j]==5) || (allapotTomb[i][j]==7)){
                        if(!foglalt(celok, new int[]{i,j})){
                            if((Math.abs(koord[0]-i)+Math.abs(koord[1]-j)) < distance){
                                distance = Math.abs(koord[0]-i)+Math.abs(koord[1]-j);
                                celX = i;
                                celY = j;
                            }
                        }
                    }
                }
            }
            celok.add(new int[]{ celX, celY });
        }

        return celok;
    }

    public boolean foglalt(List<int[]> array, int[] val){
        boolean foglalt = false;
        if(array.isEmpty()) return false;
        else {
            for (int[] a: array) {
                if(a[0]==val[0] && a[1]==val[1]) foglalt = true;
            }
        }
        return foglalt;
    }
}
