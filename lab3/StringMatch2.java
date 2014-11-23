/**
 * Authors: Alexander Gomez, Thomas Sjöholm
 */

package popup.lab3;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.util.TreeSet;
/**
 * This class finds the index of a pattern in a text.
 * 
 * When presented with a pattern it uses KMP to build a deterministic finite 
 * automata for that pattern. O(p*d) where p is size of pattern and d is size of
 * alphabet used.
 * 
 * When presented with a text it uses the automata to find the occurrences of 
 * pattern in text. O(t) where t is the size of text.
 * 
 * Problem def: https://kth.kattis.com/problems/stringmatching
 * 
 */
public class StringMatch2 {

    final static int ASCIISIZE = 127;
    final static int ASCIISTART = 32;
    Automata[] automats;
    int currAut;

    StringMatch2(int numAutomats) {
        currAut = 0;
        automats = new Automata[numAutomats];
    }

    /**
     * Runs all the automatas on the text tmp to search for matches.
     * @param tmp the text
     * @return string with indexes of the occurences for the automats
     */
    private String newMsg(char[] tmp) {
        StringBuilder retList = new StringBuilder();

        for (int i = 0; i < tmp.length; i++) {
            for (Automata a : automats) {
                a.nextLetter(tmp[i], i);
            }
        }
        for (Automata a : automats) {
            retList.append(a.getAnswer());
            retList.append(System.lineSeparator());
        }
        return retList.toString();
    }

    /**
     * Creates an automata for the pattern tmp
     * @param tmp 
     */
    private void addWord(char[] tmp) {
        automats[currAut] = new Automata(tmp);
        currAut++;
    }
    /**
     * A Deterministic Finite Automata
     */
    private class Automata {

        int[][] DFA;
        private int state;
        private int finalState;
        private int length;
        private StringBuilder sb;

        /**
         * Builds the matrix that represents the automat
         * @param pattern 
         */
        private void newDFA(char[] pattern) {

            int tmpState = 0;
            TreeSet<Character> ts = new TreeSet();
            char currChar;

            DFA[0][pattern[0]] = 1;
            ts.add(pattern[0]);
            
            /**
             * Build the automat matrix using KMP , using the previous entries 
             * in the matrix to fill it in with the current letter in the 
             * previous position.
             */
            
            for (int i = 1; i < pattern.length; i++) {
                currChar = pattern[i];
                ts.add(currChar);
                for (Character c : ts) {
                    DFA[i][c] = DFA[tmpState][c];
                }
                
                DFA[i][currChar] = i + 1;

                tmpState = DFA[tmpState][currChar];

            }
            
            for (Character c : ts) {
                DFA[length][c] = DFA[tmpState][c];
            }

        }

        /**
         * Automata for the pattern. It's built while constructing.
         * 
         * @param pattern pattern for which the automat will be for
         */
        Automata(char[] pattern) {
            finalState = pattern.length;
            state = 0;
            length = pattern.length;
            DFA = new int[length + 1][ASCIISIZE];
            sb = new StringBuilder();
            newDFA(pattern);
        }

        /**
         * Updates state of the automat, if final state is reached it appends 
         * the index of the match in the answer.
         * @param c letter read in text
         * @param index index of where in the text the letter c was read
         */
        public void nextLetter(char c, int index) {
            state = DFA[state][c];

            if (state == finalState) {
                sb.append(index - (length - 1));
                sb.append(" ");
            }
        }

        /**
         * Returns indexes of there the pattern occurred
         * @return a string representation of indexes where the pattern occurred
         */
        public String getAnswer() {
            return sb.toString();
        }

    }

    /**
     * Reads input until end-of-file, first reads a pattern to look for then 
     * reads the line and parses it. It prints the index of the occurrences of 
     * the pattern.
     * 
     * Problem: https://kth.kattis.com/problems/stringmatching
     * 
     */
    public static void main(String[] args) throws  IOException {
        Kattio kio = new Kattio(System.in);
        BufferedOutputStream out = new BufferedOutputStream(System.out);
        StringMatch2 mfos;
        int lengthDictionary = 1;

            while (kio.hasMoreTokens()) {

                mfos = new StringMatch2(lengthDictionary);
                for (int i = 0; i < lengthDictionary; i++) {
                    mfos.addWord(kio.getLine().toCharArray());
                }
                
                out.write(mfos.newMsg(kio.getLine().toCharArray()).getBytes());

            }
        out.flush();
        out.close();
        kio.close();
    }
}
