package allapotter;

public class Operator {

    private char op;

    public Operator(char op){
        this.op = op;
    }

    public Allapot alkalmaz(Allapot allapot) {

        int[][] ujAllapotTomb = new int[allapot.tombMagassag()][allapot.tombSzelesseg()];
        int mozgatottLadaX = -1;
        int mozgatottLadaY = -1;

        for(int i=0; i<allapot.tombMagassag(); i++){
            if (allapot.tombSzelesseg() >= 0)
                System.arraycopy(allapot.allapotTomb[i], 0, ujAllapotTomb[i], 0, allapot.tombSzelesseg());
        }
        int jatekosX = jatekosKeres(ujAllapotTomb,'x');
        int jatekosY = jatekosKeres(ujAllapotTomb,'y');

        //ha a pozíció amiről ellép egy célhely akkor maradjon meg annak
        if(ujAllapotTomb[jatekosX][jatekosY]==7)
            ujAllapotTomb[jatekosX][jatekosY] = 3;
        else ujAllapotTomb[jatekosX][jatekosY] = 0;

        //ha doboz van a játékos előtt
        if(jatekosElottEgyel(ujAllapotTomb,jatekosX,jatekosY)==2 || jatekosElottEgyel(ujAllapotTomb,jatekosX,jatekosY)==5){
            //ha a dobozt cél helyre toljuk
            if(jatekosElottKettovel(ujAllapotTomb,jatekosX,jatekosY)==3)
                lerakJatekosEleKettovel(ujAllapotTomb,jatekosX,jatekosY,5);
                //ha üres helyre toljuk
            else lerakJatekosEleKettovel(ujAllapotTomb,jatekosX,jatekosY,2);
            mozgatottLadaX = ladaPozicio(jatekosX,jatekosY)[0];
            mozgatottLadaY = ladaPozicio(jatekosX,jatekosY)[1];
        }
        //ha cél helyre lép a játkos
        if((jatekosElottEgyel(ujAllapotTomb,jatekosX,jatekosY)==3) || (jatekosElottEgyel(ujAllapotTomb,jatekosX,jatekosY)==5))
            lerakJatekosEleEgyel(ujAllapotTomb,jatekosX,jatekosY,7);
        else lerakJatekosEleEgyel(ujAllapotTomb,jatekosX,jatekosY,4);

        return new Allapot(ujAllapotTomb, mozgatottLadaX, mozgatottLadaY);
    }

    public boolean alkalmazhato(Allapot allapot){
        int jatekosX = jatekosKeres(allapot.allapotTomb,'x');
        int jatekosY = jatekosKeres(allapot.allapotTomb,'y');
        //ha fal van előtte
        if (jatekosElottEgyel(allapot.allapotTomb,jatekosX,jatekosY)==1)
            return false;
            //ha doboz + fal van előtte
        else if(
                ((jatekosElottEgyel(allapot.allapotTomb,jatekosX,jatekosY)==2) && (jatekosElottKettovel(allapot.allapotTomb,jatekosX,jatekosY)==1))
                        //ha doboz + doboz van előtte
                        || ((jatekosElottEgyel(allapot.allapotTomb,jatekosX,jatekosY)==2) && (jatekosElottKettovel(allapot.allapotTomb,jatekosX,jatekosY)==2))
                        //ha doboz a célon + fal van előtte
                        || ((jatekosElottEgyel(allapot.allapotTomb,jatekosX,jatekosY)==5) && (jatekosElottKettovel(allapot.allapotTomb,jatekosX,jatekosY)==1))
                        //ha doboz a célon + doboz a célon van előtte
                        || ((jatekosElottEgyel(allapot.allapotTomb,jatekosX,jatekosY)==5) && (jatekosElottKettovel(allapot.allapotTomb,jatekosX,jatekosY)==5))
                        //ha doboz + doboz a célon van előtte
                        || ((jatekosElottEgyel(allapot.allapotTomb,jatekosX,jatekosY)==2) && (jatekosElottKettovel(allapot.allapotTomb,jatekosX,jatekosY)==5))
                        //ha doboz a célon + doboz van előtte
                        || ((jatekosElottEgyel(allapot.allapotTomb,jatekosX,jatekosY)==5) && (jatekosElottKettovel(allapot.allapotTomb,jatekosX,jatekosY)==2))
        ) return false;
        else return true;
    }

    public int jatekosElottEgyel(int[][] allapotTomb, int pozX, int pozY){
        switch(this.op){
            case 'f' : return allapotTomb[pozX-1][pozY];
            case 'l' : return allapotTomb[pozX+1][pozY];
            case 'j' : return allapotTomb[pozX][pozY+1];
            case 'b' : return allapotTomb[pozX][pozY-1];
            default : return -1;
        }
    }
    public void lerakJatekosEleEgyel(int[][] allapotTomb, int pozX, int pozY, int ertek){
        switch(this.op){
            case 'f' : {allapotTomb[pozX-1][pozY] = ertek;break;}
            case 'l' : {allapotTomb[pozX+1][pozY] = ertek;break;}
            case 'j' : {allapotTomb[pozX][pozY+1] = ertek;break;}
            case 'b' : {allapotTomb[pozX][pozY-1] = ertek;break;}
            default:
                throw new IllegalStateException("Unexpected value: " + this.op);
        }
    }
    public int jatekosElottKettovel(int[][] allapotTomb, int pozX, int pozY){
        switch(this.op){
            case 'f' : return allapotTomb[pozX-2][pozY];
            case 'l' : return allapotTomb[pozX+2][pozY];
            case 'j' : return allapotTomb[pozX][pozY+2];
            case 'b' : return allapotTomb[pozX][pozY-2];
            default : return -1;
        }
    }
    public void lerakJatekosEleKettovel(int[][] allapotTomb, int pozX, int pozY, int ertek){
        switch(this.op){
            case 'f' : {allapotTomb[pozX-2][pozY] = ertek;break;}
            case 'l' : {allapotTomb[pozX+2][pozY] = ertek;break;}
            case 'j' : {allapotTomb[pozX][pozY+2] = ertek;break;}
            case 'b' : {allapotTomb[pozX][pozY-2] = ertek;break;}
            default:
                throw new IllegalStateException("Unexpected value: " + this.op);
        }
    }
    public int[] ladaPozicio(int pozX, int pozY){
        switch(this.op){
            case 'f' : {return new int[]{pozX-2,pozY};}
            case 'l' : {return new int[]{pozX+2,pozY};}
            case 'j' : {return new int[]{pozX,pozY+2};}
            case 'b' : {return new int[]{pozX,pozY-2};}
            default : return new int[]{-1,-1};
        }
    }

    public int jatekosKeres(int[][] allapotTomb, char xy){
        for(int i=0; i<allapotTomb.length; i++){
            for(int j=0; j<allapotTomb[0].length; j++){
                if((allapotTomb[i][j]==4) || (allapotTomb[i][j]==7)) {
                    if(xy=='x') return i;
                    else if (xy=='y') return j;
                }
            }
        }
        return -1;
    }
}
