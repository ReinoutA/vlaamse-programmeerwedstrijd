import java.util.HashMap;
import java.util.Scanner;


public class Main {

    public static void main(String[] args) {
        // Haal de data van de terminal input

        Scanner input = new Scanner(System.in);
        int aantalSegmenten = input.nextInt();
        for (int i = 0; i < aantalSegmenten; i++) {
            int sequentieGrootte = input.nextInt();
            parseCoaster(i + 1, input.next());
        }
        input.close();
    }

    // Maak CoasterMaker-object en schrijf
    private static void parseCoaster(int n, String coaster) {
        new CoasterMaker(coaster).schrijf(n);
    }

    private static class CoasterMaker {
        // Richting constantes
        private static final int NOORD = 0, OOST = 1, ZUID = 2, WEST = 3;

        // Rail weergave constantes
        private static final char LEEG = '.', STATION = '=', RAIL = '_', UP = '/', DOWN = '\\', FRONT = '#';

        // Positie klasse voor de key in de rails HashMap
        // Om ervoor te zorgen dat de klasse werkt als een HashMap key
        // Moet ik hashCode en equals correct implementeren
        private static class Positie {
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

        // Segment klasse om de rail en zijn diepte te storen
        private static class Segment {
            public final char s;
            public final int z;

            private Segment(char c, int z) {
                this.s = c;
                this.z = z;
            }
        }

        // Verkrijg het juiste type schuine rail
        private char getSchuineRail(boolean up) {
            switch (r) {
                case OOST:
                    return up ? UP : DOWN;
                case WEST:
                    return up ? DOWN : UP;
                case NOORD:
                case ZUID:
                    return FRONT;
            }
            throw new IllegalStateException();
        }


        // Huidige positie van het spoor dat gevormd wordt
        private int x = -1, y = 0, z = 0, r = OOST;

        // Dimensies spoor
        private int minX = 0, minY = 0, maxX = 0, maxY = 0;

        // Spoordelen zelf (Positie op de 2d plaats) : (Rail en diepte)
        private final HashMap<Positie, Segment> coaster = new HashMap<>();

        // Update de rail op de huidige positie
        // als de rail niet achter andere rails staat
        private void updateCel(char c) {
            Positie huidig = new Positie(x, y);
            Segment temp = coaster.get(huidig);
            if (temp == null || temp.z >= z) coaster.put(huidig, new Segment(c, z));
        }

        // Verander kijkrichting
        private void draaiLinks() {
            switch (r) {
                case NOORD -> r = WEST;
                case OOST -> r = NOORD;
                case ZUID -> r = OOST;
                case WEST -> r = ZUID;
            }
        }

        private void draaiRechts() {
            switch (r) {
                case NOORD -> r = OOST;
                case OOST -> r = ZUID;
                case ZUID -> r = WEST;
                case WEST -> r = NOORD;
            }
        }

        // Ga 1 stap vooruit
        private void vooruit() {
            switch (r) {
                case OOST -> {
                    x += 1;
                    maxX = Math.max(maxX, x);
                }
                case WEST -> {
                    x -= 1;
                    minX = Math.min(minX, x);
                }
                case NOORD -> {
                    z += 1;
                }
                case ZUID -> {
                    z -= 1;
                }
            }
        }

        // Ga 1 stap achteruit
        private void achteruit() {
            switch (r) {
                case OOST -> {
                    x -= 1;
                    minX = Math.min(minX, x);
                }
                case WEST -> {
                    x += 1;
                    maxX = Math.max(maxX, x);
                }
                case NOORD -> {
                    z -= 1;
                }
                case ZUID -> {
                    z += 1;
                }
            }
        }

        // Coaster maker
        public CoasterMaker(String coaster) {
            /*
                S Station
                V 1 vak vooruit
                U 1 vak omhoog diagonaal
                D 1 vak omlaag diagonaal
                L 90* links draaien
                R 90* rechts draaien
             */
            boolean stationering = true;
            char laatsteRail = 'S';

            // Ga door alle chars in de sequentie
            for (char c : coaster.toCharArray()) {

                switch (c) {
                    case 'S' -> {
                        vooruit();
                        updateCel(STATION);
                    }
                    case 'V' -> {
                        vooruit();
                        updateCel(RAIL);
                    }
                    case 'U' -> {
                        vooruit();
                        updateCel(getSchuineRail(true));
                        y += 1;
                        continue;
                    }
                    case 'D' -> {
                        y -= 1;
                        maxY = Math.max(maxY, y);
                        minY = Math.min(minY, y);

                        vooruit();
                        updateCel(getSchuineRail(false));

                    }
                    case 'L' -> {
                        vooruit();
                        updateCel(RAIL);
                        draaiLinks();
                    }
                    case 'R' -> {
                        vooruit();
                        updateCel(RAIL);
                        draaiRechts();
                    }
                    default -> throw new IllegalArgumentException();
                }
                maxY = Math.max(maxY, y);
            }
            if (x != -1 || y != 0 || r != OOST) throw new IllegalArgumentException();
        }

        // Lege cel voor de default HashMap entry
        private static final Segment LEGE_CEL = new Segment(LEEG, 0);

        public void schrijf(int n) {
            // Ga van boven naar onder
            for (int y = maxY; y >= minY; y--) {
                System.out.print(n + " ");
                // Ga van links naar rechts
                for (int x = minX; x <= maxX; x++) {
                    Positie cursor = new Positie(x, y);
                    // Print elke rail op de gegeven positie indien beschikbaar
                    char c = coaster.getOrDefault(cursor, LEGE_CEL).s;
                    System.out.print(c);
                }
                System.out.println();
            }
        }
    }
}
