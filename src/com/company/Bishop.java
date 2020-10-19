package com.company;

public class Bishop extends Piece {
  public static final Bishop[] PIECE={new Bishop(0),new Bishop(1)};
  public static final int points=3;
  private static final char rep='B';
  public static final String[] image={"bishopWhite.png","bishopBlack.png"};
  
  public char repChar(){
    return rep;
  }
  
  public int points(){
    return points;
  }
  
  public String image(){
    return image[color]; 
  }
  
  public Bishop() { 
    
  }
  
  public Bishop(int color) { 
    this.color=color;
  }
  
  public Move[] getMoves2(QuickChess game, String in){
    Move[] moves=new Move[13];
    int index=0;
    boolean stop=false;
    String destination="";
    for(int flip=1;flip>=-1;flip-=2){
      for(int otherSide=1;otherSide>=-1;otherSide-=2){
        for(int xy=1;xy<8&&!stop;xy++){
          destination= Square.shift(in,xy*otherSide,xy*flip);
          if(QuickChess.inBounds(destination)&&game.getSquare(destination).piece.color!=color){
            moves[index]=new Move(in+destination);
            index++;
            if(game.getSquare(destination).piece.color==(color+1)%2){
              stop=true;
            }
          }else{
            stop=true;
          }
        }
        stop=false;
      }
    }
    Move[] out=new Move[index];
    for(int c=0;c<index;c++){
      out[c]=moves[c];
    }
    return out;
  }
  
  public Move[] getMoves(QuickChess game, String in){
    Move[] moves=new Move[13];
    int index=0;
    boolean stop=false;
    String destination="";
    for(char x=(char)(in.charAt(0)+1),y=(char)(in.charAt(1)+1);x<'I'&&y<'9'&&!stop;x++,y++){
      destination=x+""+y;
      if(game.getSquare(destination).piece!= Piece.PIECE){
        stop=true;
        if(game.getSquare(destination).piece.color!=color){
          moves[index]=new Move(in+destination);
          index++;
        }
      }else{
        moves[index]=new Move(in+destination);
        index++;
      }
    }
    stop=false;
    for(char x=(char)(in.charAt(0)-1),y=(char)(in.charAt(1)-1);x>'@'&&y>'0'&&!stop;x--,y--){
      destination=x+""+y;
      if(game.getSquare(destination).piece!= Piece.PIECE){
        stop=true;
        if(game.getSquare(destination).piece.color!=color){
          moves[index]=new Move(in+destination);
          index++;
        }
      }else{
        moves[index]=new Move(in+destination);
        index++;
      }
    }
    stop=false;
    for(char x=(char)(in.charAt(0)+1),y=(char)(in.charAt(1)-1);x<'I'&&y>'0'&&!stop;x++,y--){
      destination=x+""+y;
      if(game.getSquare(destination).piece!= Piece.PIECE){
        stop=true;
        if(game.getSquare(destination).piece.color!=color){
          moves[index]=new Move(in+destination);
          index++;
        }
      }else{
        moves[index]=new Move(in+destination);
        index++;
      }
    }
    stop=false;
    for(char x=(char)(in.charAt(0)-1),y=(char)(in.charAt(1)+1);x>'@'&&y<'9'&&!stop;x--,y++){
      destination=x+""+y;
      if(game.getSquare(destination).piece!= Piece.PIECE){
        stop=true;
        if(game.getSquare(destination).piece.color!=color){
          moves[index]=new Move(in+destination);
          index++;
        }
      }else{
        moves[index]=new Move(in+destination);
        index++;
      }
    }
    Move[] out=new Move[index];
    for(int c=0;c<index;c++){
      out[c]=moves[c];
    }
    return out;
  }
  
  public Move[] getMovesLegal(QuickChess game, String in){
    Move[] moves=new Move[13];
    int index=0;
    Move tempVar=null;
    boolean stop=false;
    String destination="";
    for(char x=(char)(in.charAt(0)+1),y=(char)(in.charAt(1)+1);x<'I'&&y<'9'&&!stop;x++,y++){
      destination=x+""+y;
      if(game.getSquare(destination).piece!= Piece.PIECE){
        stop=true;
        if(game.getSquare(destination).piece.color!=color){
          tempVar=new Move(in+destination);
          if(tempVar.isLegal(game)){
            moves[index]=tempVar;
            index++;
          }
        }
      }else{
        tempVar=new Move(in+destination);
        if(tempVar.isLegal(game)){
          moves[index]=tempVar;
          index++;
        }
      }
    }
    stop=false;
    for(char x=(char)(in.charAt(0)-1),y=(char)(in.charAt(1)-1);x>'@'&&y>'0'&&!stop;x--,y--){
      destination=x+""+y;
      if(game.getSquare(destination).piece!= Piece.PIECE){
        stop=true;
        if(game.getSquare(destination).piece.color!=color){
          tempVar=new Move(in+destination);
          if(tempVar.isLegal(game)){
            moves[index]=tempVar;
            index++;
          }
        }
      }else{
        tempVar=new Move(in+destination);
        if(tempVar.isLegal(game)){
          moves[index]=tempVar;
          index++;
        }
      }
    }
    stop=false;
    for(char x=(char)(in.charAt(0)+1),y=(char)(in.charAt(1)-1);x<'I'&&y>'0'&&!stop;x++,y--){
      destination=x+""+y;
      if(game.getSquare(destination).piece!= Piece.PIECE){
        stop=true;
        if(game.getSquare(destination).piece.color!=color){
          tempVar=new Move(in+destination);
          if(tempVar.isLegal(game)){
            moves[index]=tempVar;
            index++;
          }
        }
      }else{
        tempVar=new Move(in+destination);
        if(tempVar.isLegal(game)){
          moves[index]=tempVar;
          index++;
        }
      }
    }
    stop=false;
    for(char x=(char)(in.charAt(0)-1),y=(char)(in.charAt(1)+1);x>'@'&&y<'9'&&!stop;x--,y++){
      destination=x+""+y;
      if(game.getSquare(destination).piece!= Piece.PIECE){
        stop=true;
        if(game.getSquare(destination).piece.color!=color){
          tempVar=new Move(in+destination);
          if(tempVar.isLegal(game)){
            moves[index]=tempVar;
            index++;
          }
        }
      }else{
        tempVar=new Move(in+destination);
        if(tempVar.isLegal(game)){
          moves[index]=tempVar;
          index++;
        }
      }
    }
    Move[] out=new Move[index];
    for(int c=0;c<index;c++){
      out[c]=moves[c];
    }
    return out;
  }
  
  public Move[] getMovesLegal2(QuickChess game, String in){
    Move[] moves=new Move[13];
    Move tempVar=null;
    int index=0;
    boolean stop=false;
    String destination="";
    for(int flip=1;flip>=-1;flip-=2){
      for(int otherSide=1;otherSide>=-1;otherSide-=2){
        for(int xy=1;xy<8&&!stop;xy++){
          destination= Square.shift(in,xy*otherSide,xy*flip);
          if(QuickChess.inBounds(destination)&&game.getSquare(destination).piece.color!=color){
            tempVar=new Move(in+destination);
            if(tempVar.isLegal(game)){
              moves[index]=tempVar;
              index++;
            }
            if(game.getSquare(destination).piece.color==(color+1)%2){
              stop=true;
            }
          }else{
            stop=true;
          }
        }
        stop=false;
      }
    }
    Move[] out=new Move[index];
    for(int c=0;c<index;c++){
      out[c]=moves[c];
    }
    return out;
  }
  
  public boolean canMove(QuickChess game, String in){
    //Move[] moves=new Move[8];
    Move tempVar=null;
    //int index=0;
    boolean stop=false;
    String destination="";
    for(int flip=1;flip>=-1;flip-=2){
      for(int otherSide=1;otherSide>=-1;otherSide-=2){
        for(int xy=1;xy<8&&!stop;xy++){
          destination= Square.shift(in,xy*otherSide,xy*flip);
          if(QuickChess.inBounds(destination)&&game.getSquare(destination).piece.color!=color){
            tempVar=new Move(in+destination);
            if(tempVar.isLegal(game)){
              return true;
            }
            if(game.getSquare(destination).piece.color==(color+1)%2){
              stop=true;
            }
          }else{
            stop=true;
          }
        }
        stop=false;
      }
    }
    return false;
  }
  
  public boolean isChecking(QuickChess game, String in){
    int enemy=(game.getSquare(in).piece.color+1)%2;
    boolean stop=false;
    String destination="";
    for(int flip=1;flip>=-1;flip-=2){
      for(int otherSide=1;otherSide>=-1;otherSide-=2){
        for(int xy=1;xy<8&&!stop;xy++){
          destination= Square.shift(in,xy*otherSide,xy*flip);
          if(QuickChess.inBounds(destination)&&game.getSquare(destination).piece.color!=color){
            if(destination.equalsIgnoreCase(game.king[enemy])){
              return true;
            }
            if(game.getSquare(destination).piece.color==(color+1)%2){
              stop=true;
            }
          }else{
            stop=true;
          }
        }
        stop=false;
      }
    }
    return false;
  }
  
  public static void main(String[] args) { 
    
  }
}
