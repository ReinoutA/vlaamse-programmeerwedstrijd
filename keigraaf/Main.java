import java.util.*;


class Node {
    private static final int INIT_CAP = 500;
    public ArrayList<Integer> nieuweUpdates = new ArrayList<>(INIT_CAP);
    public int aantalKeien;
    public ArrayList<Node> buren = new ArrayList<>(INIT_CAP);

    public Node(int aantalkeien) {
        this.aantalKeien = aantalkeien;
    }

    public void updateAnderen() {
        int aantalBuren = buren.size();
        if (aantalBuren == 0) return;
        int resultaat = aantalKeien / aantalBuren;
        if (resultaat > 0) {
            for (Node buur : buren) {
                buur.nieuweUpdates.add(resultaat);
            }
            nieuweUpdates.add(-resultaat * aantalBuren);
        }
    }

    public void updateZelf() {
        int resultaat = aantalKeien;
        for (Integer nieuweUpdate : nieuweUpdates) {
            resultaat += nieuweUpdate;
        }
        nieuweUpdates.clear();
        aantalKeien = resultaat;
    }
}

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int aantalTestgevallen = Integer.parseInt(sc.next());
        for (int i = 1; i <= aantalTestgevallen; i++) {
            Map<String, Integer> configuraties = new HashMap<>();
            int aantalNodes = Integer.parseInt(sc.next());
            Node[] Nodes = new Node[aantalNodes];
            for (int j = 1; j <= aantalNodes; j++) {
                Nodes[j - 1] = new Node(Integer.parseInt(sc.next()));
            }
            int aantalEdges = Integer.parseInt(sc.next());
            for (int k = 1; k <= aantalEdges; k++) {
                int nodeNr = Integer.parseInt(sc.next());
                int buurNodeNr = Integer.parseInt(sc.next());
                Nodes[nodeNr - 1].buren.add(Nodes[buurNodeNr - 1]);
                Nodes[buurNodeNr - 1].buren.add(Nodes[nodeNr - 1]);
            }
            var tempHash = "";
            int teller = 0;
            StringBuilder tempHashBuilder = new StringBuilder();
            do {
                configuraties.put(tempHash, teller);
                teller++;
                for (Node node : Nodes) {
                    node.updateAnderen();
                }
                for (Node node : Nodes) {
                    node.updateZelf();
                }
                tempHashBuilder.setLength(0);
                for (Node node : Nodes) {
                    tempHashBuilder.append(node.aantalKeien);
                }
                tempHash = tempHashBuilder.toString();
            }while(!configuraties.containsKey(tempHash));
            System.out.println(teller - configuraties.get(tempHash));
        }
        sc.close();
    }
}