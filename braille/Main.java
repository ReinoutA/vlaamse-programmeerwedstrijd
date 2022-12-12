import java.util.*;

public class Main {
    // Key: Braillestring
    // Value: Letter alfabet
    private static final HashMap<String, Character> alfabet = new HashMap<>();

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        // de 3 eerste lijnen inlezen die het braille alfabet bevatten
        String[] lijnen = new String[3];
        for (int i = 0; i < 3; i++) {
            lijnen[i] = sc.nextLine();
        }

        // Alle braille karakters worden opgeslagen in 1 StringBuilder object
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < lijnen[0].length(); i += 2) {
            for (int j = 0; j < 3; j++) {
                sb.append(lijnen[j].charAt(i)).append(lijnen[j].charAt(i + 1));
            }
        }

        // Splitst het StringBuilder object per braille karakter
        // Elk braille karakter wordt opgeslagen in 1 String van 6 chars
        for (int i = 0; i < 26; i++) {
            alfabet.put(sb.substring(6 * i, 6 * i + 6), (char) (i + 65));
        }

        int aantalTestgevallen = Integer.parseInt(sc.nextLine());

        for (int i = 1; i <= aantalTestgevallen; i++) {
            System.out.print(i + " ");
            String[] invoer = new String[3];
            for (int j = 0; j < 3; j++) {
                invoer[j] = sc.nextLine();
            }
            StringBuilder sb2 = new StringBuilder();
            for (int k = 0; k < invoer[0].length(); k += 2) {
                for (int j = 0; j < 3; j++) {
                    sb2.append(invoer[j].charAt(k)).append(invoer[j].charAt(k + 1));
                }
                System.out.print(alfabet.get(sb2.toString()));
                sb2.setLength(0);
            }
            System.out.println();
        }
        sc.close();
    }
}




