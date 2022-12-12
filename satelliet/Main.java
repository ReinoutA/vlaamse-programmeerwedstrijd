import java.util.*;
import java.lang.Math;


class Main {
    // Vorige resultaten worden opgeslagen in vorigeScans zodat het programma snel loopt
    // We mochten er vanuit gaan dat de scan maximaal 10.000 keer een 1 of een 0 bevat
    String[] vorigeScans = new String[10001];
    Map<String, Character> vertaling = new HashMap<>();

    public String satellietScan(String sequentie, int index) {
        if (index == 0) return "";
        if (!Objects.equals(vorigeScans[index], "0")) return vorigeScans[index];

        // opt is de optimale oplossing: de kortste, en dan de alfabetisch eerste
        // als de finale oplossing "dummy" is, weet ik dat het onmogelijk is
        String opt = "dummy";

        // aantal lettercodes ligt tussen 1 en 20
        for (int i = Math.max(0, index - 20); i < index; i++) {
            String sub = satellietScan(sequentie, i);
            if (!Objects.equals(sub, "dummy") && vertaling.containsKey(sequentie.substring(i, index))) {
                sub = sub + vertaling.get(sequentie.substring(i, index));
                if (opt.equals("dummy")) opt = sub;
                else {
                    if ((sub.length() < opt.length()) || (sub.length() == opt.length() && sub.compareTo(opt) < 0))
                        opt = sub;
                }
            }
        }
        vorigeScans[index] = opt;

        return opt;
    }

    public static void main(String[] args) {
        Main m = new Main();
        m.run(args);
    }

    public void run(String[] args) {

        Scanner in = new Scanner(System.in);
        int aantalTestgevallen = in.nextInt();

        for (int i = 0; i < aantalTestgevallen; i++) {
            String sequentie = in.next();
            int aantalCodes = in.nextInt();
            vertaling.clear();
            for (int j = 0; j < aantalCodes; j++) {
                char x = in.next().charAt(0);
                String y = in.next();
                vertaling.put(y, x);
            }


            for (int j = 0; j < 10001; j++) {
                vorigeScans[j] = "0";
            }


            System.out.print((i + 1) + " ");
            String resultaat = satellietScan(sequentie, sequentie.length());

            // Als het resultaat nog steeds "dummy" is, weet ik dat het onmogelijk is
            if (Objects.equals(resultaat, "dummy")) {
                resultaat = "ONMOGELIJK";
            }

            System.out.println(resultaat);

        }
    }
}