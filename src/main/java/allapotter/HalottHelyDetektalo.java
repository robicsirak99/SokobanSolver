package allapotter;

public class HalottHelyDetektalo {

    public int[][] detektalo(int[][] allapot_tomb){
        int[][] detektalt_helyek = new int[allapot_tomb.length][allapot_tomb[0].length];
        int[][] allapot_tomb_masolat = new int[allapot_tomb.length][allapot_tomb[0].length];

        //készítünk egy másolat tömböt az állapot tömbről, amibe csak a falakat és a cél helyeket tesszük bele.
        for(int i = 0; i < allapot_tomb.length; i++) {
            for (int j = 0; j < allapot_tomb[0].length; j++) {
                if (allapot_tomb[i][j] == 1) {
                    allapot_tomb_masolat[i][j] = 1;
                    detektalt_helyek[i][j] = 1;
                }
                if(allapot_tomb[i][j]==3){
                    allapot_tomb_masolat[i][j] = 3;
                }
            }
        }

        //az állapot tömb másolatban meg kell határozni az üres helyek esetében, hogy a pálya belső, vagy külső részén vannak-e;
        //a pálya külső részén található üres helyeket 8-asra írjuk át

        for(int i = 0; i < allapot_tomb_masolat.length; i++) {
            int j = 0;
            while(allapot_tomb_masolat[i][j]!=1){
                allapot_tomb_masolat[i][j] = 8;
                j++;
            }
        }
        for(int i = 0; i < allapot_tomb_masolat.length; i++) {
            int j = allapot_tomb_masolat[0].length-1;
            while(allapot_tomb_masolat[i][j]!=1){
                allapot_tomb_masolat[i][j] = 8;
                j--;
            }
        }
        for(int j = 0; j < allapot_tomb_masolat[0].length; j++) {
            int i = 0;
            while(allapot_tomb_masolat[i][j]!=1){
                allapot_tomb_masolat[i][j] = 8;
                i++;
            }
        }
        for(int j = 0; j < allapot_tomb_masolat[0].length; j++) {
            int i = allapot_tomb_masolat.length-1;
            while(allapot_tomb_masolat[i][j]!=1){
                allapot_tomb_masolat[i][j] = 8;
                i--;
            }
        }

        for(int i = 0; i < allapot_tomb_masolat.length-1; i++){
            for(int j = 0; j < allapot_tomb_masolat[0].length-1; j++){
                if((allapot_tomb_masolat[i][j]==1) && (allapot_tomb_masolat[i+1][j]==1)){
                        int k = i;
                        int l = j;
                        l++;
                        while ((allapot_tomb_masolat[k][l] == 1) && (allapot_tomb_masolat[k + 1][l] == 0)) {
                            l++;
                        }
                        if (l != j + 1) {
                            if ((allapot_tomb_masolat[k][l] == 1) && (allapot_tomb_masolat[k + 1][l] == 1)) {
                                k = i;
                                l = j;
                                l++;
                                while ((allapot_tomb_masolat[k][l] == 1) && (allapot_tomb_masolat[k + 1][l] == 0)) {
                                    detektalt_helyek[k + 1][l] = 9;
                                    l++;
                                }
                            }
                        }


                        //////////////////////////

                        k = i;
                        l = j;
                        l++;
                        while ((allapot_tomb_masolat[k][l] == 0) && (allapot_tomb_masolat[k + 1][l] == 1)) {
                            l++;
                        }
                        if (l != j + 1) {
                            if ((allapot_tomb_masolat[k][l] == 1) && (allapot_tomb_masolat[k + 1][l] == 1)) {
                                k = i;
                                l = j;
                                l++;
                                while ((allapot_tomb_masolat[k][l] == 0) && (allapot_tomb_masolat[k + 1][l] == 1)) {
                                    detektalt_helyek[k][l] = 9;
                                    l++;
                                }
                            }
                        }

                }

                if((allapot_tomb_masolat[i][j]==1) && (allapot_tomb_masolat[i][j+1]==1)){
                        int k = i;
                        int l = j;
                        k++;
                        while ((allapot_tomb_masolat[k][l] == 1) && (allapot_tomb_masolat[k][l + 1] == 0)) {
                            k++;
                        }
                        if (k != i + 1) {
                            if ((allapot_tomb_masolat[k][l] == 1) && (allapot_tomb_masolat[k][l + 1] == 1)) {
                                k = i;
                                l = j;
                                k++;
                                while ((allapot_tomb_masolat[k][l] == 1) && (allapot_tomb_masolat[k][l + 1] == 0)) {
                                    detektalt_helyek[k][l + 1] = 9;
                                    k++;
                                }
                            }
                        }


                        //////////////////////////

                        k = i;
                        l = j;
                        k++;
                        while ((allapot_tomb_masolat[k][l] == 0) && (allapot_tomb_masolat[k][l + 1] == 1)) {
                            k++;
                        }
                        if (l != j + 1) {
                            if ((allapot_tomb_masolat[k][l] == 1) && (allapot_tomb_masolat[k][l + 1] == 1)) {
                                k = i;
                                l = j;
                                k++;
                                while ((allapot_tomb_masolat[k][l] == 0) && (allapot_tomb_masolat[k][l + 1] == 1)) {
                                    detektalt_helyek[k][l] = 9;
                                    k++;
                                }
                            }
                        }

                }

            }
        }


        return detektalt_helyek;
    }

}
