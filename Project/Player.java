/* 
 * File: Player.java 
 * Purpose: Player is a A.I that implements min-max search to determine the best
 *          possible move for it to go
 * Date: 5/1/14 
 * Class: CS 112 
 * 
 * Authors: Calvin Yung (cyung20@bu.edu) and Tony Yao (partner)
 */


public class Player { 
    
    private static int Depth = 4; 
    
    // Calculates the evaluation function
    private int eval(Graph G) { 
        if(G.isCycleOfLength(3, -1) == true) {  // blue - computer 
            return -100000; 
        } else if (G.isCycleOfLength(3, 1) == true) { // red - human 
            return 100000; 
        } else { 
            int countBlue = 0; 
            int countRed = 0; 
            for(int i = 0; i < G.getN(); i++) { 
                countBlue += G.degree(i, -1); 
                countRed += G.degree(i, 1); 
            } 
            return countRed - countBlue; 
        } 
    } 
    
    
    // Cycles through every available move and returns the best move possible
    public Move chooseMove(Graph G) {  
        int max = -1000000;  
        Move best = new Move(0, 0);  
        for(int i = 0; i < G.getN(); i++) { 
            for(int j = 0; j < G.getN(); j++){ 
                if(G.isEdge(i, j) == false && i != j){ 
                    G.addEdge(i, j, -1); 
                    if(G.isCycleOfLength(3, -1) == false){ 
                        int val = minMax( G, 1, -1000000, 1000000 );  
                        if(val > max) {  
                            best = new Move(i,j);  
                            max = val;  
                        } 
                    } 
                    G.removeEdge(i, j); 
                } 
            } 
        } 
        return best;  
    }  
    
    
    // returns the maximum of the two integers passed into the parameters
    public int max(int alpha, int val) { 
        if (alpha >= val) { 
            return alpha; 
        } 
        else { 
            return val; 
        } 
    } 
    
    
    // returns the minimum of the two integers passed into the parameters
    public int min(int beta, int val) { 
        if (beta <= val) { 
            return beta; 
        } 
        else { 
            return val; 
        } 
    } 
    
    
    // Implements the min-max recursive evaluation using alpha-beta pruning
    public int minMax(Graph G, int depth, int alpha, int beta ) {  
        if(depth == this.Depth) { 
            return eval(G);   
        }  
        
        else if (depth % 2 == 0) { 
            int val = -100000;  
            for(int i = 0; i < G.getN(); i++) {
                for(int j = 0; j < G.getN(); j++) { 
                    if(G.isEdge(i, j) == false && i != j) { 
                        if(G.isCycleOfLength(3, -1) == false) { 
                            alpha = max(alpha, val); 
                            if(beta < alpha) { 
                                break; 
                            }
                            else {
                                val = max(val, minMax(G, depth + 1, alpha, beta)); 
                            }
                        }    
                    }    
                }
            } 
            return val;   
        }  
        
        else { // is a min node  
            int val = 1000000;  
            for(int i = 0; i < G.getN(); i++) { 
                for(int j = 0; j < G.getN(); j++) { 
                    if(G.isEdge(i, j) == false && i != j) { 
                        if(G.isCycleOfLength(3, -1) == false) { 
                            beta = min(beta, val); 
                            if(beta < alpha) { 
                                break; 
                            } 
                            else {
                                val = min(val, minMax(G, depth + 1, alpha, beta));
                            }
                        }    
                    }    
                }
            } 
            return val;  
        }   
    } 
} 