/*
 * Authors: Alexander Gomez and Thomas Sjöholm.
 */
package popup.lab3;

import java.util.BitSet;

/**
 * This class can be asked if an integer inside a range is prime.
 */
public class ErathostenesSieve {

    static final char FALSE = '0';
    static final char TRUE = '1';
    private int primes;

    BitSet matrix;

    /**
     * Computes for all numbers 0 to size is they are prime.
     *
     * @param size
     */
    public ErathostenesSieve(int size) {
        int mSize = (size + 1) / 2;
        int cap = (int)Math.round( Math.sqrt(size));
        primes = 1;
        matrix = new BitSet(mSize);
        int tmp;
        for (int i = 1; i < mSize; i++) {
            if (!matrix.get(i)) {
                primes++;

                tmp = i << 1;
                tmp++;

                //Starts from ith prime^2
                if (tmp <= cap) {
                    for (int j = (i + (tmp * i)); j <= mSize; j += tmp) {
                // doesnt work, must be overflow
                        //for (int j = (i +tmp); j <= mSize; j += tmp) {
                        matrix.set(j);

                    }
                }

            }
        }
    }

    /**
     * Return number of primes this table contains
     *
     * @return number of primes
     */
    public int numPrimes() {
        return primes;
    }

    /**
     * Returns 1 if aInt is prime else 0, aInt should not be larger than size
     *
     * @param aInt integer to query
     * @return 1 if aInt is prime, else 0
     */
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

    /**
     * First reads an integer to set the size of the ErathostenesSieve, then
     * queries it with integers
     *
     * @param args
     */
    public static void main(String[] args) {

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
