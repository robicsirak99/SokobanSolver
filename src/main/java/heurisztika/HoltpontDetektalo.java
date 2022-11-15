package heurisztika;

public class HoltpontDetektalo {

    public int[][] detektalo(int[][] allapotTomb){
        int[][] detektaltHelyek = new int[allapotTomb.length][allapotTomb[0].length];
        int[][] allapotTombMasolat = new int[allapotTomb.length][allapotTomb[0].length];

        //készítünk egy másolat tömböt az állapot tömbről, amibe csak a falakat és a cél helyeket tesszük bele.
        for(int i = 0; i < allapotTomb.length; i++) {
            for (int j = 0; j < allapotTomb[0].length; j++) {
                if (allapotTomb[i][j] == 1) {
                    allapotTombMasolat[i][j] = 1;
                    detektaltHelyek[i][j] = 1;
                }
                if(allapotTomb[i][j]==3){
                    allapotTombMasolat[i][j] = 3;
                }
            }
        }

        //az állapot tömb másolatban meg kell határozni az üres helyek esetében, hogy a pálya belső, vagy külső részén vannak-e;
        //a pálya külső részén található üres helyeket 8-asra írjuk át

        for(int i = 0; i < allapotTombMasolat.length; i++) {
            int j = 0;
            while(allapotTombMasolat[i][j]!=1){
                allapotTombMasolat[i][j] = 8;
                j++;
            }
        }
        for(int i = 0; i < allapotTombMasolat.length; i++) {
            int j = allapotTombMasolat[0].length-1;
            while(allapotTombMasolat[i][j]!=1){
                allapotTombMasolat[i][j] = 8;
                j--;
            }
        }
        for(int j = 0; j < allapotTombMasolat[0].length; j++) {
            int i = 0;
            while(allapotTombMasolat[i][j]!=1){
                allapotTombMasolat[i][j] = 8;
                i++;
            }
        }
        for(int j = 0; j < allapotTombMasolat[0].length; j++) {
            int i = allapotTombMasolat.length-1;
            while(allapotTombMasolat[i][j]!=1){
                allapotTombMasolat[i][j] = 8;
                i--;
            }
        }

        for(int i = 0; i < allapotTombMasolat.length-1; i++){
            for(int j = 0; j < allapotTombMasolat[0].length-1; j++){
                if((allapotTombMasolat[i][j]==1) && (allapotTombMasolat[i+1][j]==1)){
                    int k = i;
                    int l = j;
                    l++;
                    while ((allapotTombMasolat[k][l] == 1) && (allapotTombMasolat[k + 1][l] == 0)) {
                        l++;
                    }
                    if (l != j + 1) {
                        if ((allapotTombMasolat[k][l] == 1) && (allapotTombMasolat[k + 1][l] == 1)) {
                            k = i;
                            l = j;
                            l++;
                            while ((allapotTombMasolat[k][l] == 1) && (allapotTombMasolat[k + 1][l] == 0)) {
                                detektaltHelyek[k + 1][l] = 9;
                                l++;
                            }
                        }
                    }

                    k = i;
                    l = j;
                    l++;
                    while ((allapotTombMasolat[k][l] == 0) && (allapotTombMasolat[k + 1][l] == 1)) {
                        l++;
                    }
                    if (l != j + 1) {
                        if ((allapotTombMasolat[k][l] == 1) && (allapotTombMasolat[k + 1][l] == 1)) {
                            k = i;
                            l = j;
                            l++;
                            while ((allapotTombMasolat[k][l] == 0) && (allapotTombMasolat[k + 1][l] == 1)) {
                                detektaltHelyek[k][l] = 9;
                                l++;
                            }
                        }
                    }
                }

                if((allapotTombMasolat[i][j]==1) && (allapotTombMasolat[i][j+1]==1)){
                    int k = i;
                    int l = j;
                    k++;
                    while ((allapotTombMasolat[k][l] == 1) && (allapotTombMasolat[k][l + 1] == 0)) {
                        k++;
                    }
                    if (k != i + 1) {
                        if ((allapotTombMasolat[k][l] == 1) && (allapotTombMasolat[k][l + 1] == 1)) {
                            k = i;
                            l = j;
                            k++;
                            while ((allapotTombMasolat[k][l] == 1) && (allapotTombMasolat[k][l + 1] == 0)) {
                                detektaltHelyek[k][l + 1] = 9;
                                k++;
                            }
                        }
                    }

                    k = i;
                    l = j;
                    k++;
                    while ((allapotTombMasolat[k][l] == 0) && (allapotTombMasolat[k][l + 1] == 1)) {
                        k++;
                    }
                    if (l != j + 1) {
                        if ((allapotTombMasolat[k][l] == 1) && (allapotTombMasolat[k][l + 1] == 1)) {
                            k = i;
                            l = j;
                            k++;
                            while ((allapotTombMasolat[k][l] == 0) && (allapotTombMasolat[k][l + 1] == 1)) {
                                detektaltHelyek[k][l] = 9;
                                k++;
                            }
                        }
                    }
                }
            }
        }

        return detektaltHelyek;
    }
}
