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

const int ALPSIZE = 256;

class Automata {
    int ** DFA;
    int state, finalState, length;
public:
    Automata(const std::string);
    void nextLetter(unsigned char, int);
    ~Automata(){ delete[] DFA;}
};

Automata::Automata(const std::string pattern) {
    finalState = pattern.length();
    state = 0;
    length = pattern.length();
    std::string::const_iterator it = pattern.begin();

    DFA = new int*[length + 1];

    int tmpState = 0;
    unsigned char currChar = *it;
    DFA[0] = new int[ALPSIZE];
    for (int j = 32; j < ALPSIZE; j++) {
        DFA[0][j] = 0;
    }
    DFA[0][currChar] = 1;

    ++it;
    int i = 1;
    for (; it != pattern.end(); ++it) {
        DFA[i] = new int[ALPSIZE];

        currChar = *it;

        for (int j = 32; j < ALPSIZE; j++) {
            DFA[i][j] = DFA[tmpState][j];
        }
        
        DFA[i][currChar ] = i + 1;
        tmpState = DFA[tmpState][currChar ];

        i++;
        //std::cout.flush();
    }
    

    //for (int i = 1; i < pattern.length(); i++) {

    //}
    DFA[length] = new int[ALPSIZE];
    for (int j = 32; j < ALPSIZE; j++) {
        DFA[length][j] = DFA[tmpState][j];
    }
//        for(int i = 0; i < (length+1); i++){
//            for(int j = 0; j<ALPSIZE; j++){
//                std::cout << "[" << DFA[i][j] << "]";
//            }
//            std::cout << std::endl;
//        }
}

void Automata::nextLetter(unsigned char c, int index) {
    state = DFA[state][c];

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
};

void StringMatch::newMsg(std::string tmp) {
    for (int i = 0; i < tmp.length(); i++) {
        automat->nextLetter(tmp[i], i);
    }
    std::cout << std::endl;
}

void StringMatch::addWord(const std::string tmp) {
    automat = new Automata(tmp);
}

int main(int argc, char** argv) {
    int dict;

    std::string text;
    //while (std::cin >> dict) {
    StringMatch mfos;
    std::string first;
    while (std::getline(std::cin, first) ){
        std::cout << std::cin::;
        if(first.empty()){
            return 0;
        }
        dict = std::atoi( first.c_str() );
        
        //std::getline(std::cin, text);
        //std::cout << dict;
        
        std::string dictionary[dict];

        for (int i = 0; i < dict; i++) {
            std::getline(std::cin, dictionary[i]);
            
        }
        
        std::getline(std::cin, text);
        for (int i = 0; i < dict; i++) {
            mfos.addWord(dictionary[i]);
            //mfos.addWord(first);
            mfos.newMsg(text);
        }
        //char c = std::cin.get();
        //std::cin.
        char c = std::cin.peek();
        mfos.reset();
        
        if(std::cin.eof() || c == std::char_traits<char>::eof()){
            return 0;
        }
        //std::cin.putback(c);
    }
    //std::cout.flush();
    return 0;
}
