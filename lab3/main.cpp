/* 
 * File:   main.cpp
 * Author: Alexander
 *
 * Created on November 22, 2014, 1:07 PM
 */

#include <cstdlib>
#include <string>
#include <iostream>
#include <cmath>
#include <bitset>
#include <stdio.h>

const int ALPSIZE = 256;

class Automata {
    int ** DFA;
    std::bitset<ALPSIZE> used;
    int state, finalState, length;
public:
    Automata(const std::string);
    void nextLetter(unsigned char, int);
    ~Automata(){ 
		for(int i = 0; i < (length + 1);i++){
			delete[] DFA[i];
		}
		delete[] DFA;
		}
    Automata& operator=(const Automata& that){
		DFA = that.DFA;
		state = that.state;
		finalState = that.finalState;
		length = that.length;
		return *this;
	}
};

Automata::Automata(const std::string pattern) {
    finalState = pattern.length();
    state = 0;
    unsigned char letUsed [ALPSIZE]; 
    unsigned int index = 0;
    length = pattern.length();
    std::string::const_iterator it = pattern.begin();
    unsigned char currChar;
    //std::bitset<ALPSIZE> used;
    for (; it != pattern.end(); ++it) {
		currChar = *it;
		if(!used.test(currChar)){
			used.set(currChar);
			letUsed[index] = currChar;
			index++;
		}
	}
	
	//for(unsigned int j = 0; j < index; j++){
	//	std::cout << letUsed[j] << "\n";
	//}
	
	it = pattern.begin();
    
    DFA = new int*[length + 1];

    int tmpState = 0;
    currChar = *it;
    DFA[0] = new int[ALPSIZE];
    for (unsigned int j = 0; j < index; j++) {
        DFA[0][letUsed[j]] = 0;
    }
    DFA[0][currChar] = 1;

    ++it;
    int i = 1;
    for (; it != pattern.end(); ++it) {
        DFA[i] = new int[ALPSIZE];

        currChar = *it;

        for (unsigned int j = 0; j < index; j++) {
            DFA[i][letUsed[j]] = DFA[tmpState][letUsed[j]];
        }
        
        DFA[i][currChar] = i + 1;
        tmpState = DFA[tmpState][currChar ];

        i++;

    }
    

    DFA[length] = new int[ALPSIZE];
    for (unsigned int j = 0; j < index; j++) {
        DFA[length][letUsed[j]] = DFA[tmpState][letUsed[j]];
    }

}

void Automata::nextLetter(unsigned char c, int index) {
    
    if(used.test(c)){
		state = DFA[state][c];
		//std::cout << "char : " << c << " state " << state << " ";
		}else{
			state = 0;
		}
		
    

    if (state == finalState) {
        std::cout << (index - (length - 1)) << " ";
    }
}

class StringMatch {
    Automata *automat;
public:
    void addWord(const std::string);
    void newMsg(std::string);
    void reset() { delete automat; }
    ~StringMatch() { delete automat; } 
    StringMatch& operator=(const StringMatch& that){
		automat = that.automat;
		return *this;
	}
};

void StringMatch::newMsg(std::string tmp) {
    for (unsigned int i = 0; i < tmp.length(); i++) {
        automat->nextLetter(tmp[i], i);
    }
    std::cout << "\n";
}

void StringMatch::addWord(const std::string tmp) {
    automat = new Automata(tmp);
}

int main(int argc, char** argv) {
    int dict;
    std::ios::sync_with_stdio(false);
    std::cin.tie(NULL);
    std::string text;
    
    //std::string pattern;

    StringMatch *mfos;
    std::string first;

    while(std::cin >> dict){
	//while(!std::getline(std::cin, pattern).eof()){
		mfos = new StringMatch();
		std::cin.ignore();

        
        std::string dictionary[dict];

        for (int i = 0; i < dict; i++) {
            std::getline(std::cin, dictionary[i]);

        }
        std::getline(std::cin, text);
        

        for (int i = 0; i < dict; i++) {
            mfos->addWord(dictionary[i]);
            //mfos->addWord(pattern);
            mfos->newMsg(text);
        }

        char c = std::cin.peek();
        
        
        if(std::cin.eof() || c == std::char_traits<char>::eof()){
			//std::cout << "\n";
			//std::cout.flush();
			delete mfos;
            return 0;
        }
        delete mfos;
    }
    
    return 0;
}
