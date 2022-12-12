import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;


public class Main {
    public static int berekenMinimaleRafeligheid(int lengte, ArrayList<String> woorden) {
        int aantal = (woorden.size());
        ArrayList<Integer> offsets = new ArrayList<>();
        offsets.add(0);
        for (String woord : woorden) {
            offsets.add(offsets.get(offsets.size() - 1) + woord.length());
        }
        ArrayList<Integer> minima = new ArrayList<>();
        minima.add(0);
        for (int i = 0; i < aantal; i++) {
            minima.add((int) Math.pow(10, 20));
        }
        ArrayList<Integer> regels = new ArrayList<>();
        for (int i = 0; i < aantal + 1; i++) {
            regels.add(0);
        }
        for (int i = 0; i < aantal; i++) {
            int j = i + 1;

            int w = Integer.MIN_VALUE;
            while (j <= aantal && w < lengte)  {
                w = offsets.get(j) - offsets.get(i) + j - i - 1;
                if (w <= lengte) {
                    int kost = (int) (minima.get(i) + Math.pow((lengte - w), 2));
                    if (kost < minima.get(j)) {
                        minima.set(j, kost);
                        regels.set(j, i);
                    }
                    j += 1;
                }
            }
        }
        return minima.get(minima.size() - 1);
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int aantalTestgevallen = Integer.parseInt(sc.nextLine());
        for (int i = 1; i <= aantalTestgevallen; i++) {
            int maxLengte = Integer.parseInt(sc.nextLine());
            String[] woordArray = sc.nextLine().split(" ");
            ArrayList<String> woorden = new ArrayList<>(Arrays.asList(woordArray));
            int resultaat = berekenMinimaleRafeligheid(maxLengte, woorden);
            System.out.println(i + " " + resultaat);
        }
    }
}