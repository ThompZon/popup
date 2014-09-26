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

    
    
    Polymul2(){
        
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
                int[] coefficinetsFirst = new int[degreeFirst+1];
                for(int j = 0; j < degreeFirst+1; i++){
                    coefficinetsFirst[j] = io.getInt();
                }
                int degreeSecond = io.getInt();
                int[] coefficinetsSecond = new int[degreeSecond+1];
                for(int j = 0; j < degreeSecond+1; i++){
                    coefficinetsSecond[j] = io.getInt();
                }
                Polymul2();
            }
        }
        io.flush();
        io.close();
    }

}
