import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

class Huis {
    Positie positie;
    boolean heeftEten;

    Huis(Positie positie, boolean heeftEten) {
        this.positie = positie;
        this.heeftEten = heeftEten;
    }
}

class Garfield {
    int oplossing;
    // ArrayList van alle huizen, waarbij elk huis een positie (coordinaat) heeft,
    // Elk huis bevat een attribuut om te zien of er eten aanwezig is.
    ArrayList<Huis> rooster;
    int aantalMinuten;

    Garfield(int aantalMinuten) {
        this.aantalMinuten = aantalMinuten;
        oplossing = 0;
        rooster = new ArrayList<>();
    }

    int berekenAfstand(int positie1, int positie2) {
        return Math.abs(rooster.get(positie1).positie.x - rooster.get(positie2).positie.x)
                + Math.abs(rooster.get(positie1).positie.y - rooster.get(positie2).positie.y);
    }

    void bereken(int positie, int etenTeller, int tijd) {
        // breek uit de recursieve functie
        if (oplossing == rooster.size() - 1) {
            return;
        }

        // breek uit de recursieve functie
        if (berekenKleinsteAfstand(positie, 1 + oplossing - etenTeller) > tijd) {
            return;
        }

        // Maximale hoeveelheid eten dat Garfield in de gegeven tijd kan opeten wordt ge-update indien nodig
        oplossing = Math.max(etenTeller, oplossing);

        for (Huis h : rooster) {
            if (h.heeftEten) {
                h.heeftEten = false;
                bereken(rooster.indexOf(h), etenTeller + 1, tijd - berekenAfstand(positie, rooster.indexOf(h)) - 1);
                h.heeftEten = true;
            }
        }
    }

    private int berekenKleinsteAfstand(int positie, int etenTeller) {
        //HashMap<Huisnummer, afstandTotHuis>
        HashMap<Integer, Integer> alleAfstandenTotEten = new HashMap<>();
        int temp = positie;

        // Stop alle huisnummers met eten in de HashMap, met de afstand tot het huis als value
        for (Huis h : rooster) {
            if (h.heeftEten) {
                alleAfstandenTotEten.put(rooster.indexOf(h), berekenAfstand(positie, rooster.indexOf(h)));
            }
        }

        int afstand = etenTeller;

        for (; etenTeller > 0; etenTeller--) {
            int kleinsteAfstandHuisnummer = 0;
            int kleinsteAfstand = Integer.MAX_VALUE;
            for (int huisnummer : alleAfstandenTotEten.keySet()) {
                if (kleinsteAfstand > alleAfstandenTotEten.get(huisnummer)) {
                    kleinsteAfstand = alleAfstandenTotEten.get(huisnummer);
                    kleinsteAfstandHuisnummer = huisnummer;
                }
            }

            // totale afstand wordt aangepast
            afstand += kleinsteAfstand;
            int verplaats = kleinsteAfstandHuisnummer;
            // verwijder dit huisnummer, met bijhorende afstand, uit de map
            alleAfstandenTotEten.remove(kleinsteAfstandHuisnummer);

            if (berekenAfstand(0, verplaats) < berekenAfstand(0, temp)) {
                temp = verplaats;
            }

            for (int huisnummer : alleAfstandenTotEten.keySet()) {
                int minimum = Math.min(berekenAfstand(verplaats, huisnummer), alleAfstandenTotEten.get(huisnummer));
                alleAfstandenTotEten.replace(huisnummer, minimum);
            }
        }
        return berekenAfstand(0, temp) + afstand;
    }
}

class Positie {
    public final int x, y;

    public Positie(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public boolean equals(Object andere) {
        if (this == andere) return true;
        if (!(andere instanceof Positie)) return false;
        Positie p = (Positie) andere;
        return x == p.x && y == p.y;
    }

    @Override
    public int hashCode() {
        return 31 * x + y;
    }
}


public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int aantalTestgevallen = Integer.parseInt(sc.nextLine());


        for (int i = 1; i <= aantalTestgevallen; i++) {
            String[] lijn = sc.nextLine().split(" ");
            int breedte = Integer.parseInt(lijn[0]);
            int hoogte = Integer.parseInt(lijn[1]);
            int minuten = Integer.parseInt(lijn[2]);

            Garfield garfield = new Garfield(minuten);

            for (int h = 0; h < hoogte; h++) {
                String lijn2 = sc.nextLine();
                for (int b = 0; b < breedte; b++) {
                    switch (lijn2.charAt(b)) {
                        case 'G' -> garfield.rooster.add(0, (new Huis(new Positie(b, h), false)));
                        case 'E' -> garfield.rooster.add(new Huis(new Positie(b, h), true));
                    }
                }

            }

            garfield.bereken(0, 0, minuten);
            System.out.println(i + " " + garfield.oplossing);
            garfield.rooster.clear();

        }
        sc.close();
    }
}