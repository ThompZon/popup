package popup.lab3;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;

public class StringMatch1 {

    List<Automata> automats;
    int subStrings;

    StringMatch1() {
        automats = new LinkedList<>();
        subStrings = 0;
    }

    private String newMsg(String tmp) {
        return msgConf(tmp);
    }

    private String msgConf(String tmp) {
        StringBuilder retList = new StringBuilder();

        for (int i = 0; i < tmp.length(); i++) {
            for (Automata a : automats) {
                a.nextLetter(tmp.charAt(i),i);
            }
        }
        for (Automata a : automats) {
            retList.append(a.getAnswer());
            retList.append(System.lineSeparator());
        }
        return retList.toString();
    }

    private void addWord(String tmp) {
        automats.add(new Automata(tmp));
    }

    private class Automata {

        // BitSet present;
        ArrayList<HashMap<Character, Integer>> DFA;
        private int state;
        private int finalState;
        private int length;
        private StringBuilder sb;

        private void newDFA(String pattern) {

            int tmpState = -1;
            LinkedHashSet<Character> letters = new LinkedHashSet<>();

            char currChar;
            HashMap<Character, Integer> tmp;
            for (int i = 0; i < pattern.length(); i++) {
                currChar = pattern.charAt(i);
                letters.add(currChar);
                tmp = new HashMap<Character, Integer>();
                for (Character c : letters) {

                    if (c == pattern.charAt(i)) {
                        tmp.put(c, i + 1);
                    } else {
                        Integer ns = DFA.get(tmpState).get(c);
                        if (ns != null) {
                            tmp.put(c, ns);
                        }

                    }

                }
                DFA.add(tmp);

                if (tmpState >= 0) {
                    HashMap<Character, Integer> hs = DFA.get(tmpState);
                    Integer ns = hs.get(currChar);
                    if (ns == null) {
                        tmpState = 0;
                    } else {
                        tmpState = ns;
                    }
                } else {
                    tmpState = 0;
                }
            }

            tmp = new HashMap<Character, Integer>();
            for (Character c : letters) {
                Integer ns = DFA.get(tmpState).get(c);
                if (ns != null) {
                    tmp.put(c, ns);
                }
            }
            DFA.add(tmp);

        }

        Automata(String pattern) {
            finalState = pattern.length();
            state = 0;
            length = pattern.length();
            DFA = new ArrayList<HashMap<Character, Integer>>();
            sb = new StringBuilder();
            newDFA(pattern);

        }

        public int getLength() {
            return this.length;
        }

        public boolean nextLetter(char c, int index) {
            Integer tmpState = DFA.get(state).get(c);
            state = (tmpState == null) ? 0 : tmpState;

            if (state == finalState) {
                sb.append(index - (length - 1));
                sb.append(" ");
                return true;
            }

            return false;
        }

        private void reset() {
            state = 0;
        }
        
        public String getAnswer(){
            return sb.toString();
        }

    }

    public static void main(String[] args) {
        Kattio kio = new Kattio(System.in, System.out);
        StringMatch1 mfos;
        String tmp;
        int lengthDictionary;

        while (kio.hasMoreTokens()) {
            mfos = new StringMatch1();
            lengthDictionary = kio.getInt();
            for (int i = 0; i < lengthDictionary; i++) {
                tmp = kio.getLine();

                mfos.addWord(tmp);
            }
            

            tmp = kio.getLine();

            kio.print(mfos.newMsg(tmp));

            kio.flush();
        }
        kio.flush();
        kio.close();
    }
}
