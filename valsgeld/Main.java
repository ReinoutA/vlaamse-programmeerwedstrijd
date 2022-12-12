import java.util.*;


public class Main {
    private static HashSet<String> letters = new HashSet<>();

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        int aantalTestgevallen = Integer.parseInt(sc.nextLine());

        for (int i = 0; i < aantalTestgevallen; i++) {
            int aantalWegingen = Integer.parseInt(sc.nextLine());
            for (int k = 0; k < 26; k++) {
                letters.add((char) (k + 97) + "L");
                letters.add((char) (k + 97) + "H");
            }

            for (int j = 0; j < aantalWegingen; j++) {
                String[] weging = sc.nextLine().split(" ");
                String linkerSchaal = weging[0];
                String rechterSchaal = weging[1];
                String richting = weging[2];

                switch (richting) {
                    case "evenwicht" -> {
                        for (int k = 0; k < linkerSchaal.length(); k++) {
                            String elementL = linkerSchaal.charAt(k) + "L";
                            String elementH = linkerSchaal.charAt(k) + "H";
                            if ((letters.contains(elementL)) || (letters.contains(elementH))) {
                                letters.remove(elementL);
                                letters.remove(elementH);
                            }
                        }
                        for (int k = 0; k < rechterSchaal.length(); k++) {
                            String elementL = rechterSchaal.charAt(k) + "L";
                            String elementH = rechterSchaal.charAt(k) + "H";
                            if ((letters.contains(elementL)) || (letters.contains(elementH))) {
                                letters.remove(elementL);
                                letters.remove(elementH);
                            }
                        }
                    }

                    case "omhoog" -> {
                        HashSet<String> nieuweLetters = new HashSet<>();
                        for (int k = 0; k < linkerSchaal.length(); k++) {
                            String elementL = linkerSchaal.charAt(k) + "L";
                            if (letters.contains(elementL)) {
                                nieuweLetters.add(elementL);
                            }
                        }
                        for (int k = 0; k < rechterSchaal.length(); k++) {
                            String elementH = rechterSchaal.charAt(k) + "H";
                            if (letters.contains(elementH)) {
                                nieuweLetters.add(elementH);
                            }
                        }
                        letters = nieuweLetters;
                    }
                    case "omlaag" -> {
                        HashSet<String> nieuweLetters = new HashSet<>();
                        for (int k = 0; k < linkerSchaal.length(); k++) {
                            String elementH = linkerSchaal.charAt(k) + "H";
                            if (letters.contains(elementH)) {
                                nieuweLetters.add(elementH);
                            }
                        }
                        for (int k = 0; k < rechterSchaal.length(); k++) {
                            String elementL = rechterSchaal.charAt(k) + "L";
                            if (letters.contains(elementL)) {
                                nieuweLetters.add(elementL);
                            }
                        }
                        letters = nieuweLetters;
                    }
                }


            }

            // Resultaat printen
            if (letters.size() == 0) {
                System.out.println("Inconsistente gegevens.");
            } else if (letters.size() > 1) {
                System.out.println("Te weinig gegevens.");
            } else {
                if (letters.iterator().next().charAt(1) == 'H') {
                    System.out.println("Het valse geldstuk " + letters.iterator().next().charAt(0) + " is lichter.");
                } else {
                    System.out.println("Het valse geldstuk " + letters.iterator().next().charAt(0) + " is zwaarder.");
                }
            }
            letters.clear();
        }

        sc.close();
    }
}