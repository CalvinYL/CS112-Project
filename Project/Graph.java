/*
 * File: Graph.java
 * Purpose: This is a graph for the SimGame to be played on
 * Date: 5/1/14
 * Class: CS 112
 *
 * Authors: Calvin Yung (cyung20@bu.edu) and Tony Yao (partner)
 */

public class Graph {
    private int N = 0;
    private int[][] B;    // 0 = no edge; 1 = red edge; -1 = blue edge
    
    
    // Constructor that initializes the array 
    public Graph(int N) {    
        B = new int[N][N]; 
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                B[i][j] = 0;
            }
        }
        this.N = N;
    }
    
    
    // returns N's value
    public int getN() {
        return N;
    }
    
    
    // adds an edge from vertex u to v with value w (only 0, -1, or 1)
    public void addEdge(int u, int v, int w)  { 
        B[u][v] = w;
        B[v][u] = w;
    }
    
    
    // removes the edge from u to v and the (duplicate) edge from v to u
    public void removeEdge(int u, int v) {           
        B[u][v] = 0;
        B[v][u] = 0;
    }    
    
    
    // returns the value (-1, 0, or 1) of the edge that goes from u to v
    public int getEdge(int u, int v) {      
        return B[u][v];
    }
    
    
    // returns true or false depending on whether there is an edge (of either color) from u to v
    public boolean isEdge(int u, int v) {            
        if (B[u][v] == 1 || B[u][v] == -1 ) {
            return true;
        } else {
            return false;
        }
    }
    
    
    // return the number of edges of either color connected to vertex v
    public int degree(int v) {                    
        int count = 0;
        for (int i = 0; i < N; i++) {
            if (B[i][v] == 1 || B[i][v] == -1) {
                count++;
            }
        }
        return count;
    }
    
    
    // returns the number of edges of color w connected to vertex v
    public int degree(int v, int w) {                
        int count = 0;
        for (int i = 0; i < N; i++) {
            if (B[i][v] == w) {
                count++;
            }
        }
        return count;
    }
    
    
    // prints out the edge matrix, as shown above; this is only for debugging
    public void printEdges() {                      
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                System.out.print(B[i][j]);
            }
            System.out.println();
        }
    }    
    
    
    // return true if there is a cycle of length n among edges of color w 
    public boolean isCycleOfLength(int n, int w) {   
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (i == j) {
                    if (isCycleHelper(i, j, w, n)) {
                        return true;
                    }
                } 
            }
        }
        
        return false;
    }
    
    // isCycleOfLength helper method
    private boolean isCycleHelper(int u, int v, int w, int length) {
        int k = 0;
        int m = 0;
        int y = 0;
        for (int i=u; i<N; i++) {
            for (int j=v; j<N; j++) {
                if (B[i][j] == w && degree(j, w) == 2) {
                    k = i;
                    m = j;
                    length--;
                }
                
                if(length < 2) {
                    y = j;
                }
                
                if (B[k][m] == B[m][y] && B[k][m] == B[k][y] && B[k][m] == w) {
                    return true;
                }
            }
        }
        
        if (B[k][m] == B[m][y] && B[k][m] == B[k][y] && B[k][m] == w) {
            return true;
        }
        else {
            return false;
        }
    }
    
    
    // Unit test
    public static void main(String args[]) { 
        
        Graph mygraph = new Graph(6);
        System.out.println("Printing initial array:");
        mygraph.printEdges();
        mygraph.addEdge(2, 3, 1);  //user 
        mygraph.addEdge(2, 4, -1); //computer
        System.out.println("Printing array after user and computer inputs:");
        mygraph.printEdges();
        mygraph.removeEdge(2, 3);
        System.out.println("Printing array after removing user input value 1:");
        mygraph.printEdges();
        System.out.println("Value of the edge should be -1 and it is: " + mygraph.getEdge(2, 4));
        System.out.println("True if the edge is colored, so it is: " + mygraph.isEdge(2, 4));
        System.out.println("Degree should be 1, the number of degrees is: " + mygraph.degree(4));
        System.out.println("Degree should be 0, the number of degrees is: " + mygraph.degree(4, 1));
        System.out.println("isCycleOfLength should be false, it is: " + mygraph.isCycleOfLength(3, 1));
        mygraph.addEdge(1, 1, 1); //user
        mygraph.addEdge(1, 4, 1); //user
        mygraph.addEdge(4, 4, 1); //user
        System.out.println("Printing array after 3 user inputs:");
        mygraph.printEdges();
        System.out.println("isCycleOfLength should be true, it is: " + mygraph.isCycleOfLength(3, 1));
    }
}    