
public class Piece {
  public static final Piece PIECE=new Piece();
  public static final int points=0;
  private static final char rep='.';//square with a non extended piece represents an empty square
  public static final String[] image={"white.png","black.png"};
  int color=-2;
  
  public Piece() { 
    
  }
  
  public Piece(int color){ 
    this.color=color;
  }
  
  public Move[] getMoves(QuickChess game, String in){//overwriten
    return new Move[0];
  }
  
  public Move[] getMovesLegal(QuickChess game, String in){//overwriten
    return this.getMovesLegal(game,in);
  }
  
  public boolean canMove(QuickChess game, String in){//overwriten
    return this.canMove(game,in);
  }
  
  public boolean isChecking(QuickChess game, String in){//overwriten
    game.printBoard();
    QuickChess.printArray(game.alive[game.turn]);
    System.out.println(in);
    int x=1/0;
    return false;
  }
  
  public int points(){
    return points;
  }
  
  public char repChar(){
    return rep;
  }
  
  public String image(){
    return null;
  }
  
  public String strRep(){
    String out=" ";
    if(color==0){
      out="W";
    }else if(color==1){
      out="B";
    }
    out+=this.repChar();
    return out;
  }
  
  public static void main(String[] args) { 
    
  }
}
