package allapotter;

public class Allapot {

    //0 - üres
    //1 - fal
    //2 - doboz
    //3 - cél
    //5 - doboz a célon
    //4 - játékos
    //7 - játékos célon

    private int[][] allapotTomb;

    private int utoljaraMozgatottDobozX;
    private int utoljaraMozgatottDobozY;

    private int vegallapot;

    private TavolsagSzamito tavolsagSzamito = new TavolsagSzamito();

    private HoltpontDetektalo holtPontDetektalo = new HoltpontDetektalo();

    public Allapot(int[][] allapotTomb){
        this.allapotTomb = allapotTomb;
        this.utoljaraMozgatottDobozX = -1;
        this.utoljaraMozgatottDobozY = -1;
    }

    public Allapot(int[][] allapotTomb, int utoljaraMozgatottDobozX, int utoljaraMozgatottDobozY){
        this.allapotTomb = allapotTomb;
        this.utoljaraMozgatottDobozX = utoljaraMozgatottDobozX;
        this.utoljaraMozgatottDobozY = utoljaraMozgatottDobozY;
        if(vesztesCel() || veresegVegallapot()) {
            this.vegallapot = -1;
        }
        else if(nyertesCel()) this.vegallapot = 1;
        else this.vegallapot = 0;
    }

    public int tombMagassag() {
        return this.allapotTomb.length;
    }

    public int tombSzelesseg() {
        return this.allapotTomb[0].length;
    }

    public double heurisztika(){
        int dobozACelon = 0;
        for(int i = 0; i < allapotTomb.length; i++){
            for(int j = 0; j < allapotTomb[0].length; j++){
                if(allapotTomb[i][j] == 5) dobozACelon++;
            }
        }
        int temp = (int) (tavolsagSzamito.atlagTavolsagSzamol(this.allapotTomb)*100);
        return (20 - (double) temp/100) + dobozACelon*2;
    }

    //TRUE értéket küld vissza, ha az állapot GYŐZELEM VÉGÁLLAPOT
    //FALSE értéket küld vissza, ha az állapot NEM VÉGÁLLAPOT
    public boolean nyertesCel(){
        for(int i = 0; i < tombMagassag(); i++) {
            for (int j = 0; j < tombSzelesseg(); j++) {
                if ((allapotTomb[i][j] == 3) || (allapotTomb[i][j] == 7)) return false;
            }
        }
        return true;
    }

    //TRUE értéket küld vissza, ha az állapot VERESÉG VÉGÁLLAPOT
    //FALSE értéket küld vissza, ha az állapot NEN VÉGÁLLAPOT
    public boolean veresegVegallapot(){
        int[][] holtpontok = holtPontDetektalo.detektalo(allapotTomb);
        for(int i = 0; i < holtpontok.length; i++) {
            for(int j = 0; j < holtpontok[0].length; j++) {
                if((holtpontok[i][j] == 9) && (allapotTomb[i][j] == 2)) {
                    System.out.println("HOLTPONT");
                    return true;
                }
            }
        }


        for(int i = 0; i < tombMagassag()-1; i++) {
            for(int j = 0; j < tombSzelesseg()-1; j++) {
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

    public boolean vesztesCel(){
        //ha nem mozgattunk dobozt nem jöhetett létre vesztes cél állapot
        if(utoljaraMozgatottDobozX ==-1 && utoljaraMozgatottDobozY ==-1) return false;
            //megnézzük, hogy a láda sarokba lépett-e ("sarok" pozicio vagy "4x4"-es pozicio)
            //ha "4x4" jött létre és a "4x4" bármelyik eleme 2-es értékű (nem "láda a célon") akkor vesztes célállapot jön létre
        else if(
                ((dobozFolott()==2 || dobozFolott()==1 || dobozFolott()==5) && (dobozJobbon()==2 || dobozJobbon()==1 || dobozJobbon()==5) && (dobozJFSarkan()==2 || dobozJFSarkan()==1 || dobozJFSarkan()==5) && (dobozFolott()==2 || dobozJobbon()==2 || dobozJFSarkan()==2 || dobozPoz()==2))
                        || ((dobozFolott()==2 || dobozFolott()==1 || dobozFolott()==5) && (dobozBalon()==2 || dobozBalon()==1 || dobozBalon()==5) && (dobozBFSarkan()==2 || dobozBFSarkan()==1 || dobozBFSarkan()==5) && (dobozFolott()==2 || dobozBalon()==2 || dobozBFSarkan()==2 || dobozPoz()==2))
                        || ((dobozAlatt()==2 || dobozAlatt()==1 || dobozAlatt()==5) && (dobozJobbon()==2 || dobozJobbon()==1 || dobozJobbon()==5) && (dobozJASarkan()==2 || dobozJASarkan()==1 || dobozJASarkan()==5) && (dobozAlatt()==2 || dobozJobbon()==2 || dobozJASarkan()==2 || dobozPoz()==2))
                        || ((dobozAlatt()==2 || dobozAlatt()==1 || dobozAlatt()==5) && (dobozBalon()==2 || dobozBalon()==1 || dobozBalon()==5) && (dobozBASarkan()==2 || dobozBASarkan()==1 || dobozBASarkan()==5) && (dobozAlatt()==2 || dobozBalon()==2 || dobozBASarkan()==2 || dobozPoz()==2))
        ) return true;

        //megnézzük, hogy nem került-e a láda "C" pozícióba
        else {
            if (dobozJobbon() == 1 && dobozPoz() != 5 && jobbFelVeg() && jobbLeVeg()) {
                System.out.println("JOBBVEG");
                return true;
            }
            if (dobozBalon() == 1 && dobozPoz() != 5 && balFelVeg() && balLeVeg()) {
                System.out.println("BALVEG");
                return true;
            }
            if (dobozFolott() == 1 && dobozPoz() != 5 && felJobbVeg() && felBalVeg()) {
                System.out.println("FELVEG");
                return true;
            }
            if (dobozAlatt() == 1 && dobozPoz() != 5 && leJobbVeg() && leBalVeg()) {
                System.out.println("LEVEG");
                return true;
            }
        }

        return false;
    }

    public int dobozFolott(){
        return this.allapotTomb[utoljaraMozgatottDobozX -1][utoljaraMozgatottDobozY];
    }
    public int dobozAlatt(){
        return this.allapotTomb[utoljaraMozgatottDobozX +1][utoljaraMozgatottDobozY];
    }
    public int dobozJobbon(){
        return this.allapotTomb[utoljaraMozgatottDobozX][utoljaraMozgatottDobozY +1];
    }
    public int dobozBalon(){
        return this.allapotTomb[utoljaraMozgatottDobozX][utoljaraMozgatottDobozY -1];
    }
    public int dobozJFSarkan(){
        return this.allapotTomb[utoljaraMozgatottDobozX -1][utoljaraMozgatottDobozY +1];
    }
    public int dobozBFSarkan(){
        return this.allapotTomb[utoljaraMozgatottDobozX -1][utoljaraMozgatottDobozY -1];
    }
    public int dobozJASarkan(){
        return this.allapotTomb[utoljaraMozgatottDobozX +1][utoljaraMozgatottDobozY +1];
    }
    public int dobozBASarkan(){
        return this.allapotTomb[utoljaraMozgatottDobozX +1][utoljaraMozgatottDobozY -1];
    }
    public int dobozPoz(){
        return this.allapotTomb[utoljaraMozgatottDobozX][utoljaraMozgatottDobozY];
    }

    public boolean jobbFelVeg(){
        int k=1;
        while(true){
            if(this.allapotTomb[utoljaraMozgatottDobozX - k][utoljaraMozgatottDobozY] == 0 && this.allapotTomb[utoljaraMozgatottDobozX - k][utoljaraMozgatottDobozY + 1] == 1)
                k++;
            else return this.allapotTomb[utoljaraMozgatottDobozX - k][utoljaraMozgatottDobozY] == 1 && this.allapotTomb[utoljaraMozgatottDobozX - k][utoljaraMozgatottDobozY + 1] == 1;
        }
    }
    public boolean jobbLeVeg(){
        int k=1;
        while(true){
            if(this.allapotTomb[utoljaraMozgatottDobozX + k][utoljaraMozgatottDobozY] == 0 && this.allapotTomb[utoljaraMozgatottDobozX + k][utoljaraMozgatottDobozY + 1] == 1)
                k++;
            else return this.allapotTomb[utoljaraMozgatottDobozX + k][utoljaraMozgatottDobozY] == 1 && this.allapotTomb[utoljaraMozgatottDobozX + k][utoljaraMozgatottDobozY + 1] == 1;
        }
    }
    public boolean balFelVeg(){
        int k=1;
        while(true){
            if(this.allapotTomb[utoljaraMozgatottDobozX - k][utoljaraMozgatottDobozY] == 0 && this.allapotTomb[utoljaraMozgatottDobozX - k][utoljaraMozgatottDobozY - 1] == 1)
                k++;
            else return this.allapotTomb[utoljaraMozgatottDobozX - k][utoljaraMozgatottDobozY] == 1 && this.allapotTomb[utoljaraMozgatottDobozX - k][utoljaraMozgatottDobozY - 1] == 1;
        }
    }
    public boolean balLeVeg(){
        int k=1;
        while(true){
            if(this.allapotTomb[utoljaraMozgatottDobozX + k][utoljaraMozgatottDobozY] == 0 && this.allapotTomb[utoljaraMozgatottDobozX + k][utoljaraMozgatottDobozY - 1] == 1)
                k++;
            else return this.allapotTomb[utoljaraMozgatottDobozX + k][utoljaraMozgatottDobozY] == 1 && this.allapotTomb[utoljaraMozgatottDobozX + k][utoljaraMozgatottDobozY - 1] == 1;
        }
    }
    public boolean felJobbVeg(){
        int k=1;
        while(true){
            if(this.allapotTomb[utoljaraMozgatottDobozX][utoljaraMozgatottDobozY + k] == 0 && this.allapotTomb[utoljaraMozgatottDobozX - 1][utoljaraMozgatottDobozY + k] == 1)
                k++;
            else return this.allapotTomb[utoljaraMozgatottDobozX][utoljaraMozgatottDobozY + k] == 1 && this.allapotTomb[utoljaraMozgatottDobozX - 1][utoljaraMozgatottDobozY + k] == 1;
        }
    }
    public boolean felBalVeg(){
        int k=1;
        while(true){
            if(this.allapotTomb[utoljaraMozgatottDobozX][utoljaraMozgatottDobozY - k] == 0 && this.allapotTomb[utoljaraMozgatottDobozX - 1][utoljaraMozgatottDobozY - k] == 1)
                k++;
            else return this.allapotTomb[utoljaraMozgatottDobozX][utoljaraMozgatottDobozY - k] == 1 && this.allapotTomb[utoljaraMozgatottDobozX - 1][utoljaraMozgatottDobozY - k] == 1;
        }
    }
    public boolean leJobbVeg(){
        int k=1;
        while(true){
            if(this.allapotTomb[utoljaraMozgatottDobozX][utoljaraMozgatottDobozY + k] == 0 && this.allapotTomb[utoljaraMozgatottDobozX + 1][utoljaraMozgatottDobozY + k] == 1)
                k++;
            else return this.allapotTomb[utoljaraMozgatottDobozX][utoljaraMozgatottDobozY + k] == 1 && this.allapotTomb[utoljaraMozgatottDobozX + 1][utoljaraMozgatottDobozY + k] == 1;
        }
    }
    public boolean leBalVeg(){
        int k=1;
        while(true){
            if(this.allapotTomb[utoljaraMozgatottDobozX][utoljaraMozgatottDobozY - k] == 0 && this.allapotTomb[utoljaraMozgatottDobozX + 1][utoljaraMozgatottDobozY - k] == 1)
                k++;
            else return this.allapotTomb[utoljaraMozgatottDobozX][utoljaraMozgatottDobozY - k] == 1 && this.allapotTomb[utoljaraMozgatottDobozX + 1][utoljaraMozgatottDobozY - k] == 1;
        }
    }

    public int getVegallapot() {
        return vegallapot;
    }

    public int[][] getAllapotTomb() {
        return allapotTomb;
    }
}
