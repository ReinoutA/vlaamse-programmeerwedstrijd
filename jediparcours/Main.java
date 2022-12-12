import java.util.Scanner;

class Edge {
    int begin, eind, gewicht;
    Edge() {
        begin = 0;
        eind = 0;
        gewicht = 0;
    }
}

public class Main {
    int V; // Vertex
    int E; // Edge
    Edge[] edges;

    Main(int V, int E) {
        this.V = V;
        this.E = E;
        edges = new Edge[E];
        for (int i = 0; i < E; i++) {
            edges[i] = new Edge();
        }
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        int aantalTestGevallen = Integer.parseInt(sc.nextLine());

        for (int i = 1; i <= aantalTestGevallen; i++) {

            int aantalKnopen = Integer.parseInt(sc.next());
            int aantalVerbindingen = Integer.parseInt(sc.next());

            Main graaf = new Main(aantalKnopen, aantalVerbindingen);

            for (int j = 0; j < aantalVerbindingen; j++) {
                int begin = Integer.parseInt(sc.next());
                int einde = Integer.parseInt(sc.next());
                int gewicht = Integer.parseInt(sc.next());

                graaf.edges[j].begin = begin;
                graaf.edges[j].eind = einde;
                graaf.edges[j].gewicht = gewicht;
            }

            int res = graaf.bellmanFord(graaf);

            switch (res) {
                case Integer.MAX_VALUE -> System.out.println(i + " plus oneindig");
                case Integer.MIN_VALUE -> System.out.println(i + " min oneindig");
                default -> System.out.println(i + " " + res);
            }
        }
        sc.close();
    }

    // Bron: Bellman-Ford Moore algoritme
    int bellmanFord(Main graaf) {
        int V = graaf.V;
        int E = graaf.E;
        int[] afstand = new int[V];

        for (int i = 0; i < V; ++i)
            afstand[i] = Integer.MAX_VALUE;
        afstand[0] = 0;

        for (int i = 1; i < V; ++i) {
            for (int j = 0; j < E; ++j) {
                int u = graaf.edges[j].begin - 1;
                int v = graaf.edges[j].eind - 1;
                int gewicht = graaf.edges[j].gewicht;
                if (afstand[u] != Integer.MAX_VALUE && afstand[u] + gewicht < afstand[v])
                    afstand[v] = afstand[u] + gewicht;
            }
        }

        for (int j = 0; j < E; ++j) {
            int u = graaf.edges[j].begin - 1;
            int v = graaf.edges[j].eind - 1;
            int gewicht = graaf.edges[j].gewicht;
            if (afstand[u] != Integer.MAX_VALUE && afstand[u] + gewicht < afstand[v]) {
                return Integer.MIN_VALUE;
            }
        }
        return afstand[afstand.length - 1];
    }
}
