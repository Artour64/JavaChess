
public class RandomAI extends Player {
  public RandomAI() { 
    
  }
  
  public String getMove(ArtourGui.ChessGame board){
    Move[] move=board.game.getMovesLegal();
    String stringMove=move[(int)(Math.random()*move.length)].move;
    System.out.println(stringMove);
    return stringMove;
  }
  
  public static void main(String[] args) { 
    
  }
}
