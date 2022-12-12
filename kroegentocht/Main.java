import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int aantalTestgevallen = sc.nextInt();

        for (int a = 1; a <= aantalTestgevallen; a++) {

            int B = sc.nextInt();
            int N = sc.nextInt();

            int[][] kroegen = new int[N][2];

            // lees prijs en gezelligheidscore per kroeg
            for (int i = 0; i < N; i++) {
                kroegen[i][0] = sc.nextInt();
                kroegen[i][1] = sc.nextInt();
            }

            // 2D array met maximum gezelligheid score voor elk budget en aantal kroegen
            int[][] dp = new int[B + 1][N + 1];

            // Initialiseer de maximum gezelligheidscore voor elk budget en aantal kroegen op 0
            for (int i = 0; i <= B; i++) {
                Arrays.fill(dp[i], 0);
            }

            // Bereken de maximum gezelligheidsscore gebruikmakend van dynamisch programmeren
            for (int i = 0; i < N; i++) {
                for (int j = 0; j <= B; j++) {
                    // Als we de mocktail niet kunnen betalen, blijft de maximum gezelligheidscore
                    // hetzelfde als de vorige kroeg
                    if (j < kroegen[i][0]) {
                        dp[j][i + 1] = dp[j][i];
                    } else {
                        // Anders wordt de maximum gezelligheidscore het maximum van de gezelligheidscore van de vorige kroeg
                        // en de gezelligheidscore van de kroeg plus de maximum gezelligheidscore van het resterende budget
                        // van de vorige kroeg
                        dp[j][i + 1] = Math.max(dp[j][i], dp[j - kroegen[i][0]][i] + kroegen[i][1]);
                    }
                }
            }

            System.out.println(a + " " + dp[B][N]);
        }
    }
}