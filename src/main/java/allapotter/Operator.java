package allapotter;

import allapotter.Allapot;

public class Operator {

    public char op;

    public Operator(char op){
        this.op = op;
    }

    public Allapot alkalmaz(Allapot allapot) {

        int[][] uj_allapot_tomb = new int[allapot.magassag()][allapot.szelesseg()];
        int mozgatott_lada_x = -1;
        int mozgatott_lada_y = -1;

        for(int i=0; i<allapot.magassag(); i++){
            for(int j=0; j<allapot.szelesseg(); j++){
                uj_allapot_tomb[i][j] = allapot.allapot_tomb[i][j];
            }
        }
        int jatekos_x = jatekosKeres(uj_allapot_tomb,'x');
        int jatekos_y = jatekosKeres(uj_allapot_tomb,'y');

        //ha a pozíció amiről ellép egy célhely akkor maradjon meg annak
        if(uj_allapot_tomb[jatekos_x][jatekos_y]==7)
            uj_allapot_tomb[jatekos_x][jatekos_y] = 3;
        else uj_allapot_tomb[jatekos_x][jatekos_y] = 0;

        //ha doboz van a játékos előtt
        if(jatekosElottEgyel(uj_allapot_tomb,jatekos_x,jatekos_y)==2 || jatekosElottEgyel(uj_allapot_tomb,jatekos_x,jatekos_y)==5){
            //ha a dobozt cél helyre toljuk
            if(jatekosElottKettovel(uj_allapot_tomb,jatekos_x,jatekos_y)==3)
                lerakJatekosEleKettovel(uj_allapot_tomb,jatekos_x,jatekos_y,5);
                //ha üres helyre toljuk
            else lerakJatekosEleKettovel(uj_allapot_tomb,jatekos_x,jatekos_y,2);
            mozgatott_lada_x = ladaPozicio(jatekos_x,jatekos_y)[0];
            mozgatott_lada_y = ladaPozicio(jatekos_x,jatekos_y)[1];
        }
        //ha cél helyre lép a játkos
        if((jatekosElottEgyel(uj_allapot_tomb,jatekos_x,jatekos_y)==3) || (jatekosElottEgyel(uj_allapot_tomb,jatekos_x,jatekos_y)==5))
            lerakJatekosEleEgyel(uj_allapot_tomb,jatekos_x,jatekos_y,7);
        else lerakJatekosEleEgyel(uj_allapot_tomb,jatekos_x,jatekos_y,4);

        return new Allapot(uj_allapot_tomb, mozgatott_lada_x, mozgatott_lada_y);
    }

    public boolean alkalmazhato(Allapot allapot){
        int jatekos_x = jatekosKeres(allapot.allapot_tomb,'x');
        int jatekos_y = jatekosKeres(allapot.allapot_tomb,'y');
        //ha fal van előtte
        if (jatekosElottEgyel(allapot.allapot_tomb,jatekos_x,jatekos_y)==1)
            return false;
            //ha doboz + fal van előtte
        else if(
                ((jatekosElottEgyel(allapot.allapot_tomb,jatekos_x,jatekos_y)==2) && (jatekosElottKettovel(allapot.allapot_tomb,jatekos_x,jatekos_y)==1))
                        //ha doboz + doboz van előtte
                        || ((jatekosElottEgyel(allapot.allapot_tomb,jatekos_x,jatekos_y)==2) && (jatekosElottKettovel(allapot.allapot_tomb,jatekos_x,jatekos_y)==2))
                        //ha doboz a célon + fal van előtte
                        || ((jatekosElottEgyel(allapot.allapot_tomb,jatekos_x,jatekos_y)==5) && (jatekosElottKettovel(allapot.allapot_tomb,jatekos_x,jatekos_y)==1))
                        //ha doboz a célon + doboz a célon van előtte
                        || ((jatekosElottEgyel(allapot.allapot_tomb,jatekos_x,jatekos_y)==5) && (jatekosElottKettovel(allapot.allapot_tomb,jatekos_x,jatekos_y)==5))
                        //ha doboz + doboz a célon van előtte
                        || ((jatekosElottEgyel(allapot.allapot_tomb,jatekos_x,jatekos_y)==2) && (jatekosElottKettovel(allapot.allapot_tomb,jatekos_x,jatekos_y)==5))
                        //ha doboz a célon + doboz van előtte
                        || ((jatekosElottEgyel(allapot.allapot_tomb,jatekos_x,jatekos_y)==5) && (jatekosElottKettovel(allapot.allapot_tomb,jatekos_x,jatekos_y)==2))
        ) return false;
        else return true;
    }

    public int jatekosElottEgyel(int[][] allapot_tomb, int poz_x, int poz_y){
        switch(this.op){
            case 'f' : return allapot_tomb[poz_x-1][poz_y];
            case 'l' : return allapot_tomb[poz_x+1][poz_y];
            case 'j' : return allapot_tomb[poz_x][poz_y+1];
            case 'b' : return allapot_tomb[poz_x][poz_y-1];
            default : return -1;
        }
    }
    public void lerakJatekosEleEgyel(int[][] allapot_tomb, int poz_x, int poz_y, int ertek){
        switch(this.op){
            case 'f' : {allapot_tomb[poz_x-1][poz_y] = ertek;break;}
            case 'l' : {allapot_tomb[poz_x+1][poz_y] = ertek;break;}
            case 'j' : {allapot_tomb[poz_x][poz_y+1] = ertek;break;}
            case 'b' : {allapot_tomb[poz_x][poz_y-1] = ertek;break;}
        }
    }
    public int jatekosElottKettovel(int[][] allapot_tomb, int poz_x, int poz_y){
        switch(this.op){
            case 'f' : return allapot_tomb[poz_x-2][poz_y];
            case 'l' : return allapot_tomb[poz_x+2][poz_y];
            case 'j' : return allapot_tomb[poz_x][poz_y+2];
            case 'b' : return allapot_tomb[poz_x][poz_y-2];
            default : return -1;
        }
    }
    public void lerakJatekosEleKettovel(int[][] allapot_tomb, int poz_x, int poz_y, int ertek){
        switch(this.op){
            case 'f' : {allapot_tomb[poz_x-2][poz_y] = ertek;break;}
            case 'l' : {allapot_tomb[poz_x+2][poz_y] = ertek;break;}
            case 'j' : {allapot_tomb[poz_x][poz_y+2] = ertek;break;}
            case 'b' : {allapot_tomb[poz_x][poz_y-2] = ertek;break;}
        }
    }
    public int[] ladaPozicio(int poz_x, int poz_y){
        switch(this.op){
            case 'f' : {return new int[]{poz_x-2,poz_y};}
            case 'l' : {return new int[]{poz_x+2,poz_y};}
            case 'j' : {return new int[]{poz_x,poz_y+2};}
            case 'b' : {return new int[]{poz_x,poz_y-2};}
            default : return new int[]{-1,-1};
        }
    }

    public int jatekosKeres(int[][] allapot_tomb, char xy){
        for(int i=0; i<allapot_tomb.length; i++){
            for(int j=0; j<allapot_tomb[0].length; j++){
                if((allapot_tomb[i][j]==4) || (allapot_tomb[i][j]==7)) {
                    if(xy=='x') return i;
                    else if (xy=='y') return j;
                }
            }
        }
        return -1;
    }
}
