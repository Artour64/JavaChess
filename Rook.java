
public class Rook extends Piece {
  public static final Rook[] PIECE={new Rook(0),new Rook(1)};
  public static final int points=5;
  private static final char rep='R';
  public static final String[] image={"rookWhite.png","rookBlack.png"};
  
  public char repChar(){
    return rep;
  }
  
  public int points(){
    return points;
  }
  
  public String image(){
    return image[color]; 
  }
  
  public Rook() { 
    
  }
  
  public Rook(int color) { 
    this.color=color;
  }
  
  public Move[] getMoves(QuickChess game, String in){
    Move[] moves=new Move[14];
    int index=0;
    boolean stop=false;
    String destination="";
    for(int otherSide=1;otherSide>=-1;otherSide-=2){
      for(int x=1;x<8&&!stop;x++){
        destination=Square.shift(in,x*otherSide,0);
        if(QuickChess.inBounds(destination)&&game.getSquare(destination).piece.color!=color){
          moves[index]=new Move(in+destination);
          index++;
          if(game.getSquare(destination).piece.color==(color+1)%2){
            //System.out.println("R1:"+moves[index-1].move);//----------------------------------------------
            stop=true;
          }
        }else{
          stop=true;
        }
      }
      stop=false;
      for(int y=1;y<8&&!stop;y++){
        destination=Square.shift(in,0,y*otherSide);
        if(QuickChess.inBounds(destination)&&game.getSquare(destination).piece.color!=color){
          moves[index]=new Move(in+destination);
          index++;
          if(game.getSquare(destination).piece.color==(color+1)%2){
            //System.out.println("R2:"+moves[index-1].move);//----------------------------------------------
            stop=true;
          }
        }else{
          stop=true;
        }
      }
      stop=false;
    }
    Move[] out=new Move[index];
    for(int c=0;c<index;c++){
      out[c]=moves[c];
    }
    return out;
  }
  
  public Move[] getMovesLegal(QuickChess game, String in){
    Move[] moves=new Move[14];
    Move tempVar=null;
    int index=0;
    boolean stop=false;
    String destination="";
    for(int otherSide=1;otherSide>=-1;otherSide-=2){
      for(int x=1;x<8&&!stop;x++){
        destination=Square.shift(in,x*otherSide,0);
        if(QuickChess.inBounds(destination)&&game.getSquare(destination).piece.color!=color){
          tempVar=new Move(in+destination);
          if(tempVar.isLegal(game)){
            moves[index]=tempVar;
            index++;
          }
          if(game.getSquare(destination).piece.color==(color+1)%2){
            //System.out.println("R1:"+moves[index-1].move);//----------------------------------------------
            stop=true;
          }
        }else{
          stop=true;
        }
      }
      stop=false;
      for(int y=1;y<8&&!stop;y++){
        destination=Square.shift(in,0,y*otherSide);
        if(QuickChess.inBounds(destination)&&game.getSquare(destination).piece.color!=color){
          tempVar=new Move(in+destination);
          if(tempVar.isLegal(game)){
            moves[index]=tempVar;
            index++;
          }
          if(game.getSquare(destination).piece.color==(color+1)%2){
            //System.out.println("R2:"+moves[index-1].move);//----------------------------------------------
            stop=true;
          }
        }else{
          stop=true;
        }
      }
      stop=false;
    }
    Move[] out=new Move[index];
    for(int c=0;c<index;c++){
      out[c]=moves[c];
    }
    return out;
  }
  
  public boolean canMove(QuickChess game, String in){
    Move tempVar=null;
    boolean stop=false;
    String destination="";
    for(int otherSide=1;otherSide>=-1;otherSide-=2){
      for(int x=1;x<8&&!stop;x++){
        destination=Square.shift(in,x*otherSide,0);
        if(QuickChess.inBounds(destination)&&game.getSquare(destination).piece.color!=color){
          tempVar=new Move(in+destination);
          if(tempVar.isLegal(game)){
            return true;
          }
          if(game.getSquare(destination).piece.color==(color+1)%2){
            //System.out.println("R1:"+moves[index-1].move);//----------------------------------------------
            stop=true;
          }
        }else{
          stop=true;
        }
      }
      stop=false;
      for(int y=1;y<8&&!stop;y++){
        destination=Square.shift(in,0,y*otherSide);
        if(QuickChess.inBounds(destination)&&game.getSquare(destination).piece.color!=color){
          tempVar=new Move(in+destination);
          if(tempVar.isLegal(game)){
            return true;
          }
          if(game.getSquare(destination).piece.color==(color+1)%2){
            //System.out.println("R2:"+moves[index-1].move);//----------------------------------------------
            stop=true;
          }
        }else{
          stop=true;
        }
      }
      stop=false;
    }
    return false;
  }
  
  public boolean isChecking(QuickChess game, String in){
    int enemy=(game.getSquare(in).piece.color+1)%2;
    boolean stop=false;
    String destination="";
    for(int otherSide=1;otherSide>=-1;otherSide-=2){
      for(int x=1;x<8&&!stop;x++){
        destination=Square.shift(in,x*otherSide,0);
        if(QuickChess.inBounds(destination)&&game.getSquare(destination).piece.color!=color){
          if(destination.equalsIgnoreCase(game.king[enemy])){
            return true;
          }
          if(game.getSquare(destination).piece.color==(color+1)%2){
            //System.out.println("R1:"+moves[index-1].move);//----------------------------------------------
            stop=true;
          }
        }else{
          stop=true;
        }
      }
      stop=false;
      for(int y=1;y<8&&!stop;y++){
        destination=Square.shift(in,0,y*otherSide);
        if(QuickChess.inBounds(destination)&&game.getSquare(destination).piece.color!=color){
          if(destination.equalsIgnoreCase(game.king[enemy])){
            return true;
          }
          if(game.getSquare(destination).piece.color==(color+1)%2){
            //System.out.println("R2:"+moves[index-1].move);//----------------------------------------------
            stop=true;
          }
        }else{
          stop=true;
        }
      }
      stop=false;
    }
    return false;
  }
  
  
  
  public static void main(String[] args) { 
    
  }
}
