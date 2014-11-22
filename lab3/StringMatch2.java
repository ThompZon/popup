package popup.lab3;

import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.TreeSet;

public class StringMatch2 {

    final static int ASCIISIZE = 127;
    final static int ASCIISTART = 32;
    Automata[] automats;
    int currAut;
    BufferedOutputStream bos;


    StringMatch2(int numAutomats, BufferedOutputStream bos) {
	this.bos = bos;
	currAut = 0;
	automats = new Automata[numAutomats];

    }

    private void newMsg(char[] tmp) throws IOException  {

	for (Automata a : automats) {
	    for (int i = 0; i < tmp.length; i++) {
		a.nextLetter(tmp[i], i);
	    }
	    bos.write(System.lineSeparator().getBytes());

	}
	
	
    }

    private void addWord(char[] tmp) {
	automats[currAut] = new Automata(tmp);
	currAut++;
    }

    private class Automata {

	int[][] DFA;
	private int state;
	private int finalState;
	private int length;


	private void newDFA(char[] pattern) {

	    int tmpState = 0;
	    TreeSet<Character> ts = new TreeSet();
	    char currChar;

	    DFA[0][pattern[0]] = 1;
	    ts.add(pattern[0]);

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

	Automata(char[] pattern) {
	    finalState = pattern.length;
	    state = 0;
	    length = pattern.length;
	    DFA = new int[length + 1][ASCIISIZE];

	    newDFA(pattern);
	}

	public int getLength() {
	    return this.length;
	}

	public boolean nextLetter(char c, int index) throws IOException {
	    state = DFA[state][c];

	    if (state == finalState) {
		
		bos.write(((index - (length - 1)) + " ").getBytes());

		return true;
	    }

	    return false;
	}


    }

    public static void main(String[] args) throws FileNotFoundException, IOException {

	Kattio kio = new Kattio(System.in);

	BufferedOutputStream out = 
		new BufferedOutputStream(System.out);

	StringMatch2 mfos;
	int lengthDictionary;



	while (kio.hasMoreTokens()) {


	    lengthDictionary = kio.getInt();

	    mfos = new StringMatch2(lengthDictionary, out);

	    
	    for (int i = 0; i < lengthDictionary; i++) {
		    mfos.addWord(kio.getLine().toCharArray());
	    }


	    mfos.newMsg(kio.getLine().toCharArray());
	    

	    //out.flush();
	}


  
	out.flush();
	//out.close();
	
	kio.close();
    }
}
