/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package popup.lab1;

/**
 *
 * @author Alexander
 */
public class Polymul2 {

    static private int[] answer;
    
    Polymul2(int degreeFirst, int[] coefficientsFirst, int degreeSecond, int[] coefficientsSecond){
        
        
        int maxDeg = Math.max(degreeFirst,degreeSecond);
        if(maxDeg<3){
            int bd = coefficientsFirst[0] * coefficientsSecond[0];
            int abcd = (coefficientsFirst[0]+ coefficientsFirst[1])*(coefficientsSecond[0] + coefficientsSecond[1]);
            int ac = coefficientsFirst[1] * coefficientsSecond[1];
            System.err.println(bd + " + " + (abcd-bd-ac) +" + " + ac);
        }
        
        
    }
    
    
    
    private int karatsuba(int ffrom, int fto, int[] first, int sfrom, int sto, int[] second){
        
        
        
        
        
        
        
        
        
        
        return 0;
    }
    
    
    /**
     * @param args
     */
    public static void main(String[] args) {
        Kattio io = new Kattio(System.in, System.out);
        while(io.hasMoreTokens()){
            int T = io.getInt();
            for(int i = 0; i < T; i++){
                int degreeFirst = io.getInt();
                int[] coefficientsFirst = new int[degreeFirst+1];
                for(int j = 0; j < degreeFirst+1; j++){
                    coefficientsFirst[j] = io.getInt();
                }
                int degreeSecond = io.getInt();
                int[] coefficientsSecond = new int[degreeSecond+1];
                for(int j = 0; j < degreeSecond+1; j++){
                    coefficientsSecond[j] = io.getInt();
                }
                answer = new int[degreeFirst+degreeSecond+2];
                new Polymul2(degreeFirst, coefficientsFirst, degreeSecond, coefficientsSecond);
            }
            break;
        }
        io.flush();
        io.close();
    }

}
