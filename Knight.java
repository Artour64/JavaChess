
public class Knight extends Piece {
  public static final Knight[] PIECE={new Knight(0),new Knight(1)};
  public static final int points=3;
  private static final char rep='N';
  public static final String[] image={"knightWhite.png","knightBlack.png"};
  public char repChar(){
    return rep;
  }
  
  public int points(){
    return points;
  }
  
  public Knight() { 
    
  }
  
  public String image(){
    return image[color]; 
  }
  
  public Knight(int color) { 
    this.color=color;
  }
  
  public Move[] getMoves(QuickChess game, String in){
    int index=0;
    Move[] moves=new Move[8];
    String destination="";
    for(int x=1,y=2,c=0;c<2;x=2,y=1,c++){
      for(int signX=1;signX>=-1;signX-=2){
        for(int signY=1;signY>=-1;signY-=2){
          destination=Square.shift(in,x*signX,y*signY);
          if(QuickChess.inBounds(destination)&&game.getSquare(destination).piece.color!=color){
            moves[index]=new Move(in+destination);
            index++;
          }
        }
      }
    }
    Move[] out=new Move[index];
    for(int c=0;c<index;c++){
      out[c]=moves[c];
    }
    return out;
  }
  
    public Move[] getMoves2(QuickChess game, String in){
    int index=0;
    Move[] moves=new Move[8];
    String destination="";
    char X=in.charAt(0);
    char Y=in.charAt(1);
    boolean[] left=new boolean[2];
    boolean[] right=new boolean[2];
    boolean[] up=new boolean[2];
    boolean[] down=new boolean[2];
    
    if(X>'A'){
      left[0]=true;
      if(X>'B'){
        left[1]=true;
        if(X<'E'){
          right[0]=true;
          if(X<'D'){
            right[1]=true;
          }
        }
      }else{
        right[0]=true;
        right[1]=true;
      }
    }else{
      right[0]=true;
      right[1]=true;
    }
    
    if(Y>'1'){
      down[0]=true;
      if(Y>'2'){
        down[1]=true;
        if(Y<'6'){
          up[0]=true;
          if(Y<'5'){
            up[1]=true;
          }
        }
      }else{
        up[0]=true;
        up[1]=true;
      }
    }else{
      up[0]=true;
      up[1]=true;
    }
    
    if(left[0]){
      if(left[1]){
        if(up[0]){
          if(up[1]){
            destination=(X-1)+""+(Y+2);
            if(game.getSquare(destination).piece.color!=game.turn){
              moves[index]=new Move(in+destination);
              index++;
            }
            destination=(X-2)+""+(Y+1);
            if(game.getSquare(destination).piece.color!=game.turn){
              moves[index]=new Move(in+destination);
              index++;
            }
          }else{
            destination=(X-2)+"6";
            if(game.getSquare(destination).piece.color!=game.turn){
              moves[index]=new Move(in+destination);
              index++;
            }
          }
        }else{
          destination=(X-2)+"5";
          if(game.getSquare(destination).piece.color!=game.turn){
            moves[index]=new Move(in+destination);
            index++;
          }
          destination=(X-1)+"4";
          if(game.getSquare(destination).piece.color!=game.turn){
            moves[index]=new Move(in+destination);
            index++;
          }
        }
        if(down[0]){
          destination=(X-2)+""+(Y-1);
          if(game.getSquare(destination).piece.color!=game.turn){
            moves[index]=new Move(in+destination);
            index++;
          }
          if(down[1]){
            destination=(X-1)+""+(Y-2);
            if(game.getSquare(destination).piece.color!=game.turn){
              moves[index]=new Move(in+destination);
              index++;
            }
          }
        }
      }else{//X=A,C,D
        if(up[1]){
          destination=(X-1)+""+(Y+2);
          if(game.getSquare(destination).piece.color!=game.turn){
            moves[index]=new Move(in+destination);
            index++;
          }
        }
        if(down[1]){
          destination=(X-1)+""+(Y-2);
          if(game.getSquare(destination).piece.color!=game.turn){
            moves[index]=new Move(in+destination);
            index++;
          }
        }
        //other side
      }
    }else{
      
    }
    
    Move[] out=new Move[index];
    for(int c=0;c<index;c++){
      out[c]=moves[c];
    }
    return out;
  }
  
  public Move[] getMovesLegal(QuickChess game, String in){
    int index=0;
    Move[] moves=new Move[8];
    Move tempVar=null;
    String destination="";
    for(int x=1,y=2,c=0;c<2;x=2,y=1,c++){
      for(int signX=1;signX>=-1;signX-=2){
        for(int signY=1;signY>=-1;signY-=2){
          destination=Square.shift(in,x*signX,y*signY);
          if(QuickChess.inBounds(destination)&&game.getSquare(destination).piece.color!=color){
            tempVar=new Move(in+destination);
            if(tempVar.isLegal(game)){
              moves[index]=tempVar;
              index++;
            }
          }
        }
      }
    }
    Move[] out=new Move[index];
    for(int c=0;c<index;c++){
      out[c]=moves[c];
    }
    return out;
  }
  
  public boolean canMove(QuickChess game, String in){
    Move tempVar=null;
    String destination="";
    for(int x=1,y=2,c=0;c<2;x=2,y=1,c++){
      for(int signX=1;signX>=-1;signX-=2){
        for(int signY=1;signY>=-1;signY-=2){
          destination=Square.shift(in,x*signX,y*signY);
          if(QuickChess.inBounds(destination)&&game.getSquare(destination).piece.color!=color){
            tempVar=new Move(in+destination);
            if(tempVar.isLegal(game)){
              return true;
            }
          }
        }
      }
    }
    return false;
  }
  
  public boolean isChecking(QuickChess game, String in){
    int enemy=(game.getSquare(in).piece.color+1)%2;
    String destination="";
    for(int x=1,y=2,c=0;c<2;x=2,y=1,c++){
      for(int signX=1;signX>=-1;signX-=2){
        for(int signY=1;signY>=-1;signY-=2){
          destination=Square.shift(in,x*signX,y*signY);
          if(QuickChess.inBounds(destination)&&game.getSquare(destination).piece.color!=color){
            if(destination.equalsIgnoreCase(game.king[enemy])){
              return true;
            }
          }
        }
      }
    }
    return false;
  }
  
  
  
  public static void main(String[] args) { 
    
  }
}
