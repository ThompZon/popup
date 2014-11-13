/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package popup.lab3;

import java.util.BitSet;

/**
 *
 * @author Alexander
 */
public class ErathostenesSieve {

    static final char FALSE = '0';
    static final char TRUE = '1';
    private int primes;

    BitSet matrix;

    public ErathostenesSieve(int size) {
        int mSize = (size + 1) / 2;
        primes = 1;
        matrix = new BitSet(mSize);
        int tmp;
        for (int i = 1; i < mSize; i++) {
            if (!matrix.get(i)) {
                primes++;
                tmp = i << 1;
                tmp++;
                for (int j = i+tmp; j <= mSize; j += tmp) {
                    matrix.set(j);
                }
            }
        }
    }
    
    public int numPrimes(){
        return primes;
    }

    public char query(int aInt) {
        char ret = TRUE;
        if (aInt < 2 || (aInt > 2 && 0 == (aInt & 1))) {
            ret = FALSE;
        } else if (aInt > 2) {
            aInt = aInt >> 1;
            if (matrix.get(aInt)) {
                ret = FALSE;
            }
        }
        return ret;
    }

    public static void main(String[] args) {
//        int number = 1;
//
//        int[] numbers = new int[100];
//
//        for (int i = 1; i < 100; i++) {
//
//            number += 2;
//            numbers[i] = number;
//            System.err.println((number) + " i: " + ((number) >> 1));
//        }

        Kattio kio = new Kattio(System.in, System.out);
        ErathostenesSieve es;
        int queries;
        if (kio.hasMoreTokens()) {
            es = new ErathostenesSieve(kio.getInt());
            queries = kio.getInt();
            kio.println(es.numPrimes());
            for (int i = 0; i < queries; i++) {
                kio.println(es.query(kio.getInt()));
            }
            //kio.flush();
        }
        kio.flush();
        kio.close();
    }
}
