package allapotter;

import heurisztika.HoltpontDetektalo;

public class AllapotVizsgalo {

    private HoltpontDetektalo holtPontDetektalo = new HoltpontDetektalo();

    /**
     * -1 : vereseg
     * 1 : gyozelem
     * 0 : koztes allapot
     */

    public int allapotVizsgal(Allapot allapot){

        if(vesztesCel(allapot) || veresegVegallapot(allapot)) {
            return -1;
        }
        else if(nyertesCel(allapot)) return 1;
        else return  0;
    }

    //TRUE értéket küld vissza, ha az állapot GYŐZELEM VÉGÁLLAPOT
    //FALSE értéket küld vissza, ha az állapot NEM VÉGÁLLAPOT
    public boolean nyertesCel(Allapot allapot){
        for(int i = 0; i < allapot.tombMagassag(); i++) {
            for (int j = 0; j < allapot.tombSzelesseg(); j++) {
                if ((allapot.getAllapotTomb()[i][j] == 3) || (allapot.getAllapotTomb()[i][j] == 7)) return false;
            }
        }
        return true;
    }

    //TRUE értéket küld vissza, ha az állapot VERESÉG VÉGÁLLAPOT
    //FALSE értéket küld vissza, ha az állapot NEN VÉGÁLLAPOT
    public boolean veresegVegallapot(Allapot allapot){
        int[][] holtpontok = holtPontDetektalo.detektalo(allapot.getAllapotTomb());
        var allapotTomb = allapot.getAllapotTomb();
        for(int i = 0; i < holtpontok.length; i++) {
            for(int j = 0; j < holtpontok[0].length; j++) {
                if((holtpontok[i][j] == 9) && (allapotTomb[i][j] == 2)) {
                    System.out.println("HOLTPONT");
                    return true;
                }
            }
        }

        for(int i = 0; i < allapot.tombMagassag()-1; i++) {
            for(int j = 0; j < allapot.tombSzelesseg()-1; j++) {
                if(((allapotTomb[i][j] == 1) || (allapotTomb[i][j] == 2)) &&
                        ((allapotTomb[i+1][j] == 1) || (allapotTomb[i+1][j] == 2)) &&
                        ((allapotTomb[i][j+1] == 1) || (allapotTomb[i][j+1] == 2)) &&
                        ((allapotTomb[i+1][j+1] == 1) || (allapotTomb[i+1][j+1] == 2)) &&
                        !((allapotTomb[i][j] == 1) && (allapotTomb[i+1][j] == 1) && (allapotTomb[i][j+1] == 1) &&(allapotTomb[i+1][j+1] == 1)))
                    return true;
            }
        }
        return false;
    }

    public boolean vesztesCel(Allapot allapot){

        //ha nem mozgattunk dobozt nem jöhetett létre vesztes cél állapot
        if(allapot.getUtoljaraMozgatottDobozX() ==-1 && allapot.getUtoljaraMozgatottDobozY() ==-1) return false;
            //megnézzük, hogy a láda sarokba lépett-e ("sarok" pozicio vagy "4x4"-es pozicio)
            //ha "4x4" jött létre és a "4x4" bármelyik eleme 2-es értékű (nem "láda a célon") akkor vesztes célállapot jön létre
        else if(
                ((dobozFolott(allapot)==2 || dobozFolott(allapot)==1 || dobozFolott(allapot)==5) && (dobozJobbon(allapot)==2 || dobozJobbon(allapot)==1 || dobozJobbon(allapot)==5) && (dobozJFSarkan(allapot)==2 || dobozJFSarkan(allapot)==1 || dobozJFSarkan(allapot)==5) && (dobozFolott(allapot)==2 || dobozJobbon(allapot)==2 || dobozJFSarkan(allapot)==2 || dobozPoz(allapot)==2))
                        || ((dobozFolott(allapot)==2 || dobozFolott(allapot)==1 || dobozFolott(allapot)==5) && (dobozBalon(allapot)==2 || dobozBalon(allapot)==1 || dobozBalon(allapot)==5) && (dobozBFSarkan(allapot)==2 || dobozBFSarkan(allapot)==1 || dobozBFSarkan(allapot)==5) && (dobozFolott(allapot)==2 || dobozBalon(allapot)==2 || dobozBFSarkan(allapot)==2 || dobozPoz(allapot)==2))
                        || ((dobozAlatt(allapot)==2 || dobozAlatt(allapot)==1 || dobozAlatt(allapot)==5) && (dobozJobbon(allapot)==2 || dobozJobbon(allapot)==1 || dobozJobbon(allapot)==5) && (dobozJASarkan(allapot)==2 || dobozJASarkan(allapot)==1 || dobozJASarkan(allapot)==5) && (dobozAlatt(allapot)==2 || dobozJobbon(allapot)==2 || dobozJASarkan(allapot)==2 || dobozPoz(allapot)==2))
                        || ((dobozAlatt(allapot)==2 || dobozAlatt(allapot)==1 || dobozAlatt(allapot)==5) && (dobozBalon(allapot)==2 || dobozBalon(allapot)==1 || dobozBalon(allapot)==5) && (dobozBASarkan(allapot)==2 || dobozBASarkan(allapot)==1 || dobozBASarkan(allapot)==5) && (dobozAlatt(allapot)==2 || dobozBalon(allapot)==2 || dobozBASarkan(allapot)==2 || dobozPoz(allapot)==2))
        ) return true;

            //megnézzük, hogy nem került-e a láda "C" pozícióba
        else {
            if (dobozJobbon(allapot) == 1 && dobozPoz(allapot) != 5 && jobbFelVeg(allapot) && jobbLeVeg(allapot)) {
                System.out.println("JOBBVEG");
                return true;
            }
            if (dobozBalon(allapot) == 1 && dobozPoz(allapot) != 5 && balFelVeg(allapot) && balLeVeg(allapot)) {
                System.out.println("BALVEG");
                return true;
            }
            if (dobozFolott(allapot) == 1 && dobozPoz(allapot) != 5 && felJobbVeg(allapot) && felBalVeg(allapot)) {
                System.out.println("FELVEG");
                return true;
            }
            if (dobozAlatt(allapot) == 1 && dobozPoz(allapot) != 5 && leJobbVeg(allapot) && leBalVeg(allapot)) {
                System.out.println("LEVEG");
                return true;
            }
        }

        return false;
    }

    public int dobozFolott(Allapot allapot){
        return allapot.getAllapotTomb()[allapot.getUtoljaraMozgatottDobozX() -1][allapot.getUtoljaraMozgatottDobozY()];
    }
    public int dobozAlatt(Allapot allapot){
        return allapot.getAllapotTomb()[allapot.getUtoljaraMozgatottDobozX() +1][allapot.getUtoljaraMozgatottDobozY()];
    }
    public int dobozJobbon(Allapot allapot){
        return allapot.getAllapotTomb()[allapot.getUtoljaraMozgatottDobozX()][allapot.getUtoljaraMozgatottDobozY() +1];
    }
    public int dobozBalon(Allapot allapot){
        return allapot.getAllapotTomb()[allapot.getUtoljaraMozgatottDobozX()][allapot.getUtoljaraMozgatottDobozY() -1];
    }
    public int dobozJFSarkan(Allapot allapot){
        return allapot.getAllapotTomb()[allapot.getUtoljaraMozgatottDobozX() -1][allapot.getUtoljaraMozgatottDobozY() +1];
    }
    public int dobozBFSarkan(Allapot allapot){
        return allapot.getAllapotTomb()[allapot.getUtoljaraMozgatottDobozX() -1][allapot.getUtoljaraMozgatottDobozY() -1];
    }
    public int dobozJASarkan(Allapot allapot){
        return allapot.getAllapotTomb()[allapot.getUtoljaraMozgatottDobozX() +1][allapot.getUtoljaraMozgatottDobozY() +1];
    }
    public int dobozBASarkan(Allapot allapot){
        return allapot.getAllapotTomb()[allapot.getUtoljaraMozgatottDobozX() +1][allapot.getUtoljaraMozgatottDobozY() -1];
    }
    public int dobozPoz(Allapot allapot){
        return allapot.getAllapotTomb()[allapot.getUtoljaraMozgatottDobozX()][allapot.getUtoljaraMozgatottDobozY()];
    }

    public boolean jobbFelVeg(Allapot allapot){
        int k=1;
        while(true){
            if(allapot.getAllapotTomb()[allapot.getUtoljaraMozgatottDobozX() - k][allapot.getUtoljaraMozgatottDobozY()] == 0 && allapot.getAllapotTomb()[allapot.getUtoljaraMozgatottDobozX() - k][allapot.getUtoljaraMozgatottDobozY() + 1] == 1)
                k++;
            else return allapot.getAllapotTomb()[allapot.getUtoljaraMozgatottDobozX() - k][allapot.getUtoljaraMozgatottDobozY()] == 1 && allapot.getAllapotTomb()[allapot.getUtoljaraMozgatottDobozX() - k][allapot.getUtoljaraMozgatottDobozY() + 1] == 1;
        }
    }
    public boolean jobbLeVeg(Allapot allapot){
        int k=1;
        while(true){
            if(allapot.getAllapotTomb()[allapot.getUtoljaraMozgatottDobozX() + k][allapot.getUtoljaraMozgatottDobozY()] == 0 && allapot.getAllapotTomb()[allapot.getUtoljaraMozgatottDobozX() + k][allapot.getUtoljaraMozgatottDobozY() + 1] == 1)
                k++;
            else return allapot.getAllapotTomb()[allapot.getUtoljaraMozgatottDobozX() + k][allapot.getUtoljaraMozgatottDobozY()] == 1 && allapot.getAllapotTomb()[allapot.getUtoljaraMozgatottDobozX() + k][allapot.getUtoljaraMozgatottDobozY() + 1] == 1;
        }
    }
    public boolean balFelVeg(Allapot allapot){
        int k=1;
        while(true){
            if(allapot.getAllapotTomb()[allapot.getUtoljaraMozgatottDobozX() - k][allapot.getUtoljaraMozgatottDobozY()] == 0 && allapot.getAllapotTomb()[allapot.getUtoljaraMozgatottDobozX() - k][allapot.getUtoljaraMozgatottDobozY() - 1] == 1)
                k++;
            else return allapot.getAllapotTomb()[allapot.getUtoljaraMozgatottDobozX() - k][allapot.getUtoljaraMozgatottDobozY()] == 1 && allapot.getAllapotTomb()[allapot.getUtoljaraMozgatottDobozX() - k][allapot.getUtoljaraMozgatottDobozY() - 1] == 1;
        }
    }
    public boolean balLeVeg(Allapot allapot){
        int k=1;
        while(true){
            if(allapot.getAllapotTomb()[allapot.getUtoljaraMozgatottDobozX() + k][allapot.getUtoljaraMozgatottDobozY()] == 0 && allapot.getAllapotTomb()[allapot.getUtoljaraMozgatottDobozX() + k][allapot.getUtoljaraMozgatottDobozY() - 1] == 1)
                k++;
            else return allapot.getAllapotTomb()[allapot.getUtoljaraMozgatottDobozX() + k][allapot.getUtoljaraMozgatottDobozY()] == 1 && allapot.getAllapotTomb()[allapot.getUtoljaraMozgatottDobozX() + k][allapot.getUtoljaraMozgatottDobozY() - 1] == 1;
        }
    }
    public boolean felJobbVeg(Allapot allapot){
        int k=1;
        while(true){
            if(allapot.getAllapotTomb()[allapot.getUtoljaraMozgatottDobozX()][allapot.getUtoljaraMozgatottDobozY() + k] == 0 && allapot.getAllapotTomb()[allapot.getUtoljaraMozgatottDobozX() - 1][allapot.getUtoljaraMozgatottDobozY() + k] == 1)
                k++;
            else return allapot.getAllapotTomb()[allapot.getUtoljaraMozgatottDobozX()][allapot.getUtoljaraMozgatottDobozY() + k] == 1 && allapot.getAllapotTomb()[allapot.getUtoljaraMozgatottDobozX() - 1][allapot.getUtoljaraMozgatottDobozY() + k] == 1;
        }
    }
    public boolean felBalVeg(Allapot allapot){
        int k=1;
        while(true){
            if(allapot.getAllapotTomb()[allapot.getUtoljaraMozgatottDobozX()][allapot.getUtoljaraMozgatottDobozY() - k] == 0 && allapot.getAllapotTomb()[allapot.getUtoljaraMozgatottDobozX() - 1][allapot.getUtoljaraMozgatottDobozY() - k] == 1)
                k++;
            else return allapot.getAllapotTomb()[allapot.getUtoljaraMozgatottDobozX()][allapot.getUtoljaraMozgatottDobozY() - k] == 1 && allapot.getAllapotTomb()[allapot.getUtoljaraMozgatottDobozX() - 1][allapot.getUtoljaraMozgatottDobozY() - k] == 1;
        }
    }
    public boolean leJobbVeg(Allapot allapot){
        int k=1;
        while(true){
            if(allapot.getAllapotTomb()[allapot.getUtoljaraMozgatottDobozX()][allapot.getUtoljaraMozgatottDobozY() + k] == 0 && allapot.getAllapotTomb()[allapot.getUtoljaraMozgatottDobozX() + 1][allapot.getUtoljaraMozgatottDobozY() + k] == 1)
                k++;
            else return allapot.getAllapotTomb()[allapot.getUtoljaraMozgatottDobozX()][allapot.getUtoljaraMozgatottDobozY() + k] == 1 && allapot.getAllapotTomb()[allapot.getUtoljaraMozgatottDobozX() + 1][allapot.getUtoljaraMozgatottDobozY() + k] == 1;
        }
    }
    public boolean leBalVeg(Allapot allapot){
        int k=1;
        while(true){
            if(allapot.getAllapotTomb()[allapot.getUtoljaraMozgatottDobozX()][allapot.getUtoljaraMozgatottDobozY() - k] == 0 && allapot.getAllapotTomb()[allapot.getUtoljaraMozgatottDobozX() + 1][allapot.getUtoljaraMozgatottDobozY() - k] == 1)
                k++;
            else return allapot.getAllapotTomb()[allapot.getUtoljaraMozgatottDobozX()][allapot.getUtoljaraMozgatottDobozY() - k] == 1 && allapot.getAllapotTomb()[allapot.getUtoljaraMozgatottDobozX() + 1][allapot.getUtoljaraMozgatottDobozY() - k] == 1;
        }
    }
}
