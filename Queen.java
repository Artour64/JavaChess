
public class Queen extends Piece {
  public static final Queen[] PIECE={new Queen(0),new Queen(1)};
  public static final int points=9;
  private static char rep='Q';
  public static final String[] image={"queenWhite.png","queenBlack.png"};
  
  public char repChar(){
    return rep;
  }
  
  public int points(){
    return points;
  }
  
  public String image(){
    return image[color]; 
  }
  
  public Queen() { 
    
  }
  
  public Queen(int color) { 
    this.color=color;
  }
  
  public Move[] getMoves(QuickChess game, String in){//queen is coded as a piece with combined capabilities of a bishop and rook
    Move[] rook=Rook.PIECE[color].getMoves(game,in);
    Move[] bishop=Bishop.PIECE[color].getMoves(game,in);
    Move[] out=new Move[rook.length+bishop.length];
    for(int c=0;c<rook.length;c++){
      out[c]=rook[c];
    }
    for(int c=rook.length;c<out.length;c++){
      out[c]=bishop[c-rook.length];
    }
    return out;
  }
  
    public Move[] getMoves2(QuickChess game, String in){
    Move[] moves=new Move[17];
    int index=0;
//    char left=0;
//    char right=0;
//    char up=0;
//    char down=0;
    int temp=0;
    //up
    
    Move[] out=new Move[index];
    for(int c=0;c<index;c++){
      out[c]=moves[c];
    }
    return out;
  }
  
  public Move[] getMovesLegal(QuickChess game, String in){//queen is coded as a piece with combined capabilities of a bishop and rook
    Move[] rook=Rook.PIECE[color].getMovesLegal(game,in);
    Move[] bishop=Bishop.PIECE[color].getMovesLegal(game,in);
    Move[] out=new Move[rook.length+bishop.length];
    for(int c=0;c<rook.length;c++){
      out[c]=rook[c];
    }
    for(int c=rook.length;c<out.length;c++){
      out[c]=bishop[c-rook.length];
    }
    return out;
  }
  
  public boolean canMove(QuickChess game, String in){
    if(Rook.PIECE[color].canMove(game,in)){
      return true;
    }
    if(Bishop.PIECE[color].canMove(game,in)){
     return true; 
    }
    return false;
  }
  
  public boolean isChecking(QuickChess game, String in){
    if(Rook.PIECE[color].isChecking(game,in)){
      return true;
    }
    if(Bishop.PIECE[color].isChecking(game,in)){
     return true; 
    }
    return false;
  }
  
  public static void main(String[] args) { 
    
  }
}
