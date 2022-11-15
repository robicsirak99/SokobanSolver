package allapotter;

import java.util.ArrayList;
import java.util.List;

public class TavolsagSzamito {

    public int[][] tomb;
    public List<int[]> ladak = new ArrayList<int[]>();
    public List<int[]> celok = new ArrayList<int[]>();

    public double atlagTavolsagSzamol(int[][] allapot_tomb){
        this.tomb = allapot_tomb;
        ladakFeltolt();
        celokFeltolt();
        double atlag_tavolsag = 0;
        for(int i=0; i<ladak.size(); i++){
            atlag_tavolsag = atlag_tavolsag + Math.abs(ladak.get(i)[0]-celok.get(i)[0]) + Math.abs(ladak.get(i)[1]-celok.get(i)[1]);
        }
        return atlag_tavolsag/ladak.size();
    }

    public void ladakFeltolt(){
        for(int i=0; i<tomb.length; i++){
            for(int j=0; j<tomb[0].length; j++){
                if((tomb[i][j] == 2) || (tomb[i][j] == 5)) ladak.add(new int[]{i,j});
            }
        }
    }

    public void celokFeltolt(){
        for (int[] koord : ladak) {
            int distance = 99999;
            int cel_x = -1;
            int cel_y = -1;
            for(int i=0; i<tomb.length; i++){
                for(int j=0; j<tomb[0].length; j++){
                    if((tomb[i][j]==3) || (tomb[i][j]==5) || (tomb[i][j]==7)){
                        if(!foglalt(celok,new int[]{i,j})){
                            if((Math.abs(koord[0]-i)+Math.abs(koord[1]-j)) < distance){
                                distance = Math.abs(koord[0]-i)+Math.abs(koord[1]-j);
                                cel_x = i;
                                cel_y = j;
                            }
                        }
                    }
                }
            }
            celok.add(new int[]{cel_x,cel_y});
        }
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
