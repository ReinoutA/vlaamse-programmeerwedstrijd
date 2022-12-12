import java.util.HashMap;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int aantalTestgevallen = sc.nextInt();
        for (int i = 0; i < aantalTestgevallen; i++) {
            String next = sc.next();
            System.out.println((i + 1) + " " + subgroups(next));
        }
    }

    // De HashMap met voorheen geziene sequenties zorgt ervoor dat het programma sneller loopt, het functioneert ook zonder, maar veel trager
    private static final HashMap<String, Integer> voorheenGezien = new HashMap<>();

    private static int subgroups(String sequentie) {
        if (sequentie.isEmpty()) return 1;
        if (voorheenGezien.containsKey(sequentie)) {
            return voorheenGezien.get(sequentie);
        }

        int som = 0;
        int ghostbusters = 0, ghosts = 0;
        // Volg de spoken en ghostbusters tussen 0 en i om uitgebalanceerde sneden te kunnen maken en zo intersecties te vermijden
        for (int i = 0; i < sequentie.length(); i++) {
            if (sequentie.charAt(i) == 'G') ghosts++;
            else ghostbusters++;
            if (ghosts == ghostbusters) {
                // Wanneer we een match hebben, betekent dit dat we 2 secties hebben waarop we verdeel en heers met recursie kunnen toepassen
                String links = sequentie.substring(1, i);
                int subgroupLinks = subgroups(links);

                String rechts = sequentie.substring(i + 1);
                int subgroupRechts = subgroups(rechts);

                // tel het product op als het aantal extra mogelijke configuraties
                som += subgroupLinks * subgroupRechts;
            }
        }

        voorheenGezien.put(sequentie, som);

        return som;
    }
}
