package allapotter;

import java.util.ArrayList;
import java.util.List;

public class TavolsagSzamito {

    private int[][] tomb;
    private List<int[]> dobozok = new ArrayList<>();
    private List<int[]> celok = new ArrayList<>();

    public double atlagTavolsagSzamol(int[][] allapotTomb){
        this.tomb = allapotTomb;
        dobozokFeltolt();
        celokFeltolt();
        double atlagTavolsag = 0;
        for(int i = 0; i< dobozok.size(); i++){
            atlagTavolsag = atlagTavolsag + Math.abs(dobozok.get(i)[0]-celok.get(i)[0]) + Math.abs(dobozok.get(i)[1]-celok.get(i)[1]);
        }
        return atlagTavolsag/ dobozok.size();
    }

    public void dobozokFeltolt(){
        for(int i=0; i<tomb.length; i++){
            for(int j=0; j<tomb[0].length; j++){
                if((tomb[i][j] == 2) || (tomb[i][j] == 5)) dobozok.add(new int[]{i,j});
            }
        }
    }

    public void celokFeltolt(){
        for (int[] koord : dobozok) {
            int distance = 99999;
            int celX = -1;
            int celY = -1;
            for(int i=0; i<tomb.length; i++){
                for(int j=0; j<tomb[0].length; j++){
                    if((tomb[i][j]==3) || (tomb[i][j]==5) || (tomb[i][j]==7)){
                        if(!foglalt(celok,new int[]{i,j})){
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
