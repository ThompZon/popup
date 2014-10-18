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
public class LinSolver {

    LinSolver(int n, double[][] A, double[] b, double[] nC, Kattio io) {
        int row;
        double epsilon = 0.00001;

        int[] order = new int[n];

        boolean[] takenRow = new boolean[n];

        for (int c = 0; c < n; c++) {
            row = -1;
            for (int i = 0; i < n; i++) {
                if (!takenRow[i]) {

                    if (nC[i] == (int) nC[i]) {
                        if ((int) nC[i] == 1) {
                            row = i;
                            break;
                        }
                    }
                    if (Math.abs(nC[i]) > 0) {
                        row = i;
                    }
                }
            }
            order[c] = row;
            if (row == -1) {
                //io.println("multiple");
                //return;

                for (int i = 0; i < n; i++) {

                    if (c + 1 < n) {
                        nC[i] = A[i][c + 1];

                    }

                }
                continue;
            }
            takenRow[row] = true;
            double denom = 1;
            if (A[row][c] > 1.0 + epsilon || A[row][c] < 1.0 - epsilon) {
                denom = A[row][c];

                for (int i = 0; i < n; i++) {
                    if (A[row][i] > epsilon || A[row][i] < -epsilon) {
                        A[row][i] /= denom;
                    }
                }
                b[row] /= denom;
            }
//            double diff = Math.abs(denom - (int) denom);
//
//            if (diff > 0 && diff < epsilon) {
//                denom = Math.round(denom);
//            }
            for (int i = 0; i < n; i++) {

                if (i == row) {
                    if (c + 1 < n) {
                        nC[i] = A[i][c + 1];
                    }

                    continue;
                }
                if (A[i][c] > -epsilon && A[i][c] < epsilon) {
                    if (c + 1 < n) {
                        nC[i] = A[i][c + 1];

                    }
                    continue;
                }
                denom = A[i][c] / A[row][c];

                for (int j = c; j < n; j++) {
                    if (A[row][j] != 0) {
                        A[i][j] -= denom * A[row][j];
                    }
                }
                b[i] -= denom * b[row];

                if (c + 1 < n) {
                    nC[i] = A[i][c + 1];

                }

            }
        }
        int variables;
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < n; i++) {
            if (!takenRow[i] && b[i] != 0) {
                sb.append("inconsistent");
                io.println(sb.toString());
                return;
            }
        }
        boolean multiple = false;
        for (int i = 0; i < n; i++) {
            variables = 0;
            if (order[i] == -1) {
                //sb.append("? ");
                multiple = true;
                continue;
            }
            for (int j = 0; j < n; j++) {
                if (A[order[i]][j] > epsilon || A[order[i]][j] < -epsilon) {
                    variables++;
                }
            }
            if (variables > 1) {
                //io.println("multiple");
                //return;
                multiple = true;
                //sb.append("? ");
            } else if (variables == 0) {
                if (b[order[i]] != 0) {
                    sb.setLength(0);
                    sb.append("inconsistent");
                    break;
                }
                multiple = true;
                //io.println("multiple");
                //return;
                //sb.append("? ");
            } else {
                sb.append(b[order[i]] + " ");
//                if(b[order[i]] == (int)b[order[i]]){
//                    sb.append((int)b[order[i]] + " ");
//                }else{
//                    sb.append(String.format( "%.2f", b[order[i]]) + " ");
//                }

            }

        }
        if(multiple){
            io.println("multiple");
        }else{
            io.println(sb.toString());
        }
        
    }

    public static void main(String[] args) {

//        double test1 = 4.999999999;
//        double test2 = 4.0;
//        System.err.println("(int)4.5 = " + (int)(test2+0.5));
//        System.err.println("4.0-4.000002 "+Math.abs(test1-(int)test1));
//        if(test1 == (int)test1){
//            System.err.println("4.0000000002 == 4");
//        }if(test2+0.1 == (int)test2){
//            System.err.println("4.1==4.0");
//        }
//        System.err.println();
        Kattio io = new Kattio(System.in, System.out);
        while (io.hasMoreTokens()) {
            int n = io.getInt();
            if (n == 0) {
                break;
            }
            double[][] A = new double[n][n];
            double[] b = new double[n];
            double[] nC = new double[n];
            for (int i = 0; i < n; i++) {
                nC[i] = A[i][0] = io.getDouble();
                for (int j = 1; j < n; j++) {
                    A[i][j] = io.getDouble();
//                    if (Math.abs(Math.round(A[i][j]) - A[i][j]) < 0.0001) {
//                        A[i][j] = Math.round(A[i][j]);
//                    }
                }
            }
            for (int i = 0; i < n; i++) {
                b[i] = io.getDouble();
            }
            new LinSolver(n, A, b, nC, io);
            //io.flush();
        }
        io.flush();
        io.close();
    }
}
