import java.util.*;

class Pad {
    int aantalMijnen; // kost van het pad
    int afstand;
    int x;
    int y;

    Pad(int aantalMijnen, int afstand, int x, int y) {
        this.aantalMijnen = aantalMijnen;
        this.afstand = afstand;
        this.x = x;
        this.y = y;
    }
}

// Pad met minste mijnen kiezen
// zijn er meerdere?
// kies dan het kortste pad
class PadComparator implements Comparator<Pad> {
    @Override
    public int compare(Pad p1, Pad p2) {
        if (p1.aantalMijnen != p2.aantalMijnen) {
            return p1.aantalMijnen - p2.aantalMijnen;
        } else {
            return p1.afstand - p2.afstand;
        }
    }
}


// Gewogen graaf waarin gewichten >= 0
public class Main extends PadComparator {

    public static Pad oplossen(int aantalRijen, int aantalKolommen, ArrayList<ArrayList<Integer>> veld) {

        // 1 <= r,k <= 100
        boolean[][] gezien = new boolean[100][100];

        // Camparator op basis van aantal mijnen, daarna eventueel op afstand
        PadComparator padComparator = new PadComparator();

        // Onbezochte knopen in PriorityQueue
        // = Snellere implementatie met min PriorityQueue
        PriorityQueue<Pad> queue = new PriorityQueue<>(padComparator);
        for (int i = 0; i < aantalRijen; i++) {
            queue.add(new Pad(0, 0, -1, i));
        }

        while(queue.peek().x != aantalKolommen - 1){
            Pad p = queue.peek();
            queue.poll();
            if (p.x < 0 || !gezien[p.x][p.y]) {
                if (p.x >= 0) {
                    gezien[p.x][p.y] = true;
                }
                queue.add(new Pad(p.aantalMijnen + veld.get(p.y).get(p.x + 1), p.afstand + 1, p.x + 1, p.y));
                if (p.x > 0)
                    queue.add(new Pad(p.aantalMijnen + veld.get(p.y).get(p.x - 1), p.afstand + 1, p.x - 1, p.y));
                if (p.x >= 0 && p.y > 0)
                    queue.add(new Pad(p.aantalMijnen + veld.get(p.y - 1).get(p.x), p.afstand + 1, p.x, p.y - 1));
                if (p.x >= 0 && p.y < aantalRijen - 1)
                    queue.add(new Pad(p.aantalMijnen + veld.get(p.y + 1).get(p.x), p.afstand + 1, p.x, p.y + 1));
            }
        }
        return queue.peek();
    }


    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        int aantalTestgevallen = Integer.parseInt(sc.nextLine());

        for (int a = 1; a <= aantalTestgevallen; a++) {

            int aantalRijen = sc.nextInt();
            int aantalKolommen = sc.nextInt();

            ArrayList<ArrayList<Integer>> veld = new ArrayList<>();
            for (int r = 0; r < aantalRijen; r++) {
                veld.add(new ArrayList<>());
            }
            for (int r = 0; r < aantalRijen; r++) {
                for (int k = 0; k < aantalKolommen; k++) {
                    veld.get(r).add(sc.nextInt());
                }
            }
            Pad oplossing = oplossen(aantalRijen, aantalKolommen, veld);
            System.out.println(a + " " + oplossing.afstand + " " + oplossing.aantalMijnen);
        }
        sc.close();
    }
}


