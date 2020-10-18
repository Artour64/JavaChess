
public class AdvancedAI extends Player {
  int depth=2;
  
  public AdvancedAI() { 

  }
  
  public AdvancedAI(int depth) { 
    this.depth=depth;
  }
  
  public String getMove(ArtourGui.ChessGame board){
    String move=AI.chooseMove(board.game,depth).move;
    System.out.println(move);
    return move;
  }
  
  public static void main(String[] args) { 
    
  }
}
