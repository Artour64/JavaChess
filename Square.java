
public class Square {
  public Piece piece=null;
  
  public Square() { 
    
  }
  
  public void promote(QuickChess game,char move){
    game.lastCap=0;
    int turn=(game.turn+1)%2;
    game.points[turn]--;
    if(move=='Q'){
      piece=Queen.PIECE[turn];
    }else if(move=='N'){
      piece=Knight.PIECE[turn];
    }else if(move=='R'){
      piece=Rook.PIECE[turn];
    }else if(move=='B'){
      piece=Bishop.PIECE[turn];
    }else{
      System.out.println("Somthing is broken:"+move);
    }
    game.points[turn]+=piece.points();
//    int turn=(game.turn+1)%2;
//    for(int c=0;c<game.deadIndex[turn];c++){
//      if(game.dead[turn][c].repChar()==move){
//        game.points[turn]+=game.dead[turn][c].points()-1;
//        piece=game.dead[turn][c];
//        game.dead[turn][c]=Pawn.PIECE[turn];
//        return;
//      }
//    }
    //System.out.println("Promoted");
  }
  
  public static String shift(String in, int x, int y){
    String out="";
    out+=(char)(in.charAt(0)+x);
    out+=(char)(in.charAt(1)+y);
    return out;
  }
  
  public static void main(String[] args) {
    //System.out.println(shift("C3",-1,-2));
  }
  
}
