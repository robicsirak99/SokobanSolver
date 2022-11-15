package allapotter;

public class AllapotRajzolo {

    public void allapotKirajzol(Allapot allapot){
        for(int i=0; i<allapot.tombMagassag(); i++){
            for(int j=0; j<allapot.tombSzelesseg(); j++){
                switch (allapot.allapotTomb[i][j]){
                    case 0 : { System.out.print(" "); break; }
                    case 1 : { System.out.print("#"); break; }
                    case 2 : { System.out.print("X"); break; }
                    case 3 : { System.out.print("0"); break; }
                    case 4 : { System.out.print("@"); break; }
                    case 5 : { System.out.print("X"); break; }
                    case 7 : { System.out.print("@"); break; }
                    default:
                        throw new IllegalStateException("Unexpected value: " + allapot.allapotTomb[i][j]);
                }
            }
            System.out.println();
        }
        System.out.println("===============================");
    }
}
