import java.util.Scanner;

import static java.lang.Math.max;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int aantalTestgevallen = Integer.parseInt(sc.nextLine());

        for (int i = 1; i <= aantalTestgevallen; i++) {
            int maxNees = Integer.parseInt(sc.next());
            int maxJaren = Integer.parseInt(sc.next());

            int[][] rooster = new int[maxNees + 1][maxJaren + 1];

            // triviale gevallen
            for (int j = 1; j <= maxNees; j++) {
                rooster[j][1] = 1;
                rooster[j][0] = 0;
            }
            // triviale geval maxNees = 1
            for (int k = 1; k <= maxJaren; k++) {
                rooster[1][k] = k;
            }

            for (int j = 2; j <= maxNees; j++) {
                for (int k = 2; k <= maxJaren; k++) {
                    rooster[j][k] = Integer.MAX_VALUE;
                    for (int l = 1; l <= k; l++) {
                        int resultaat = max(rooster[j - 1][l - 1], 1 + rooster[j][k - l]);
                        if (resultaat < rooster[j][k]) {
                            rooster[j][k] = resultaat;
                        }
                    }
                }
            }
            int max = 0;
            for (int rij = 0; rij <= maxJaren; rij++) {
                if (rooster[maxNees][rij] > max) {
                    max = rooster[maxNees][rij];
                }
            }
            System.out.println(i + " " + max);
        }
        sc.close();
    }
}
