package popup.lab3;

import java.io.BufferedOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.util.TreeSet;
import java.util.logging.Level;
import java.util.logging.Logger;

public class StringMatch1 {

    final static int ASCIISIZE = 127;
    final static int ASCIISTART = 32;
    Automata[] automats;
    int currAut;
    //int subStrings;

    StringMatch1(int numAutomats) {
        currAut = 0;
        automats = new Automata[numAutomats];
        //subStrings = 0;
    }

    private String newMsg(String tmp) {
        StringBuilder retList = new StringBuilder();

        for (int i = 0; i < tmp.length(); i++) {
            for (Automata a : automats) {
                a.nextLetter(tmp.charAt(i), i);
            }
        }
        for (Automata a : automats) {
            retList.append(a.getAnswer());
            retList.append(System.lineSeparator());
        }
        return retList.toString();
    }

    private void addWord(String tmp) {
        automats[currAut] = new Automata(tmp);
        currAut++;
    }

    private class Automata {

        int[][] DFA;
        private int state;
        private int finalState;
        private int length;
        private StringBuilder sb;

        private void newDFA(String pattern) {

            int tmpState = 0;
            TreeSet<Character> ts = new TreeSet();
            char currChar;

            DFA[0][pattern.charAt(0)] = 1;
            ts.add(pattern.charAt(0));

            for (int i = 1; i < pattern.length(); i++) {
                currChar = pattern.charAt(i);
                ts.add(currChar);

                for (Character c : ts) {
                    //constCount++;
                    if (c == pattern.charAt(i)) {
                        DFA[i][c] = (i + 1);
                    } else {

                        DFA[i][c] = DFA[tmpState][c];

                    }

                }

                if (tmpState >= 0) {
                    tmpState = DFA[tmpState][currChar];
                } else {
                    tmpState = 0;
                }
            }

            for (int j = ASCIISTART; j < ASCIISIZE; j++) {
                //constCount++;
                DFA[pattern.length()][j] = DFA[tmpState][j];

            }

        }

        Automata(String pattern) {
            finalState = pattern.length();
            state = 0;
            length = pattern.length();
            DFA = new int[length + 1][ASCIISIZE];
            sb = new StringBuilder();
            newDFA(pattern);
        }

        public int getLength() {
            return this.length;
        }

        public boolean nextLetter(char c, int index) {
            state = DFA[state][c];

            if (state == finalState) {
                sb.append(index - (length - 1));
                sb.append(" ");
                return true;
            }

            return false;
        }

        public String getAnswer() {
            return sb.toString();
        }

    }

    public static void main(String[] args) throws FileNotFoundException {
        //System.setIn(new FileInputStream("C:\\Users\\Alexander\\Documents\\NetBeansProjects\\PopupLab\\test\\popup\\lab3\\testInput"));
        
        Kattio kio = new Kattio(System.in);
        OutputStream out = new BufferedOutputStream ( System.out );
        StringMatch1 mfos;
        String tmp;
        int lengthDictionary;

        //while (kio.hasMoreTokens()) {
        boolean start = false;

        while (kio.hasMoreTokens()) {
            try {
                start = true;
                //lengthDictionary = kio.getInt();
                lengthDictionary = kio.getInt();
                mfos = new StringMatch1(lengthDictionary);
                for (int i = 0; i < lengthDictionary; i++) {
                    mfos.addWord(kio.getLine());
                }
                
                
                
                out.write(mfos.newMsg(kio.getLine()).getBytes());
                
                //kio.flush();
            } catch (IOException ex) {
                Logger.getLogger(StringMatch1.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        kio.flush();
        kio.close();

    }
}
