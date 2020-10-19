package com.company;

public class Node {
  public Node prev=null;
  public Node[] next=null;
  public QuickChess game=null;
  
  public boolean[] win=new boolean[2];
  public int depth=0;
  public boolean[] draw=new boolean[2];
  
  public Node() { 
    
  }
  
  public Node(QuickChess game) { 
    this.game=game;
  }
  
  public void createNext(){
    Move[] moves=game.getMovesLegal();
    next=new Node[moves.length];
    QuickChess sim=null;
    for(int c=0;c<moves.length;c++){
      sim=new QuickChess(game);
      sim.move(moves[c]);
      next[c]=new Node(sim);
    }
  }
  
  public void sort(){//by forcefulness(number of moves next player can do after)
    
  }
  
  public void cut(){
    prev=null;
  }
  
  public static void main(String[] args) { 
    
  }
}
