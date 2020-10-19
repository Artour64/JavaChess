package com.company;

public class King extends Piece {
  public static final King[] PIECE={new King(0),new King(1)};
  public static final int points=0;
  private static final char rep='K';
  public static final String[] image={"kingWhite.png","kingBlack.png"};
  
  public char repChar(){
    return rep;
  }
  
  public int points(){
    return points;
  }
  
  public String image(){
    return image[color]; 
  }
  
  public King() { 
    
  }
  
  public King(int color) { 
    this.color=color;
  }
  
  public Move[] getMoves(QuickChess game, String in){//obsolete?
    Move[] moves=new Move[10];
    int index=0;
    String destination="";
    for(int x=1;x>=-1;x-=2){
      if(QuickChess.inBounds(Square.shift(in,x,0))){
        for(int y=-1;y<=1;y++){
          destination= Square.shift(in,x,y);
          if(QuickChess.inBounds(destination)&&game.getSquare(destination).piece.color!=color){
            moves[index]=new Move(in+destination);
            index++;
          }
        }
      }
    }
    for(int y=1;y>=-1;y-=2){
      destination= Square.shift(in,0,y);
      if(QuickChess.inBounds(destination)&&game.getSquare(destination).piece.color!=color){
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
    Move[] moves=new Move[10];
    Move tempVar=null;
    int index=0;
    String destination="";
    for(int x=1;x>=-1;x-=2){
      if(QuickChess.inBounds(Square.shift(in,x,0))){
        for(int y=-1;y<=1;y++){
          destination= Square.shift(in,x,y);
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
    for(int y=1;y>=-1;y-=2){
      destination= Square.shift(in,0,y);
      if(QuickChess.inBounds(destination)&&game.getSquare(destination).piece.color!=color){
        tempVar=new Move(in+destination);
        if(tempVar.isLegal(game)){
          moves[index]=tempVar;
          index++;
        }
      }
    }
    //castling
    if(game.canCastle[color]){
      if(!game.isCheck()){
        if(game.kingCastle[color]){
          //game.turn=(game.turn+1)%2;
          if(game.getSquare(Square.shift(in,3,0)).piece== Rook.PIECE[color]){
            destination=(Square.shift(in,1,0));
            if(game.getSquare(destination).piece== Piece.PIECE){
              if(new Move(in+destination).isLegal(game)){
                destination=(Square.shift(destination,1,0));
                if(game.getSquare(destination).piece== Piece.PIECE){
                  if(new Move(in+destination).isLegal(game)){
                    moves[index]=new Move(in+destination);
                    index++;
                  }
                }
              }
            }
          }
          //game.move(new Move("A"+rank+"D"+rank));
        }
        if(game.queenCastle[color]){
          if(game.getSquare(Square.shift(in,-4,0)).piece== Rook.PIECE[color]){
            destination=(Square.shift(in,-1,0));
            if(game.getSquare(destination).piece== Piece.PIECE){
              if(new Move(in+destination).isLegal(game)){
                destination=(Square.shift(destination,-1,0));
                if(game.getSquare(destination).piece== Piece.PIECE){
                  if(game.getSquare("B"+in.charAt(1)).piece== Piece.PIECE){
                    if(new Move(in+destination).isLegal(game)){
                      moves[index]=new Move(in+destination);
                      index++;
                    }
                  }
                }
              }
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
    //Move[] moves=new Move[8];
    Move tempVar=null;
    //int index=0;
    String destination="";
    for(int x=1;x>=-1;x-=2){
      if(QuickChess.inBounds(Square.shift(in,x,0))){
        for(int y=-1;y<=1;y++){
          destination= Square.shift(in,x,y);
          if(QuickChess.inBounds(destination)&&game.getSquare(destination).piece.color!=color){
            tempVar=new Move(in+destination);
            if(tempVar.isLegal(game)){
              return true;
            }
          }
        }
      }
    }
    for(int y=1;y>=-1;y-=2){
      destination= Square.shift(in,0,y);
      if(QuickChess.inBounds(destination)&&game.getSquare(destination).piece.color!=color){
        tempVar=new Move(in+destination);
        if(tempVar.isLegal(game)){
          return true;
        }
      }
    }
    return false;
  }
  
  public boolean isChecking(QuickChess game, String in){
    int enemy=(game.getSquare(in).piece.color+1)%2;
    String destination="";
    for(int x=1;x>=-1;x-=2){
      if(QuickChess.inBounds(Square.shift(in,x,0))){
        for(int y=-1;y<=1;y++){
          destination= Square.shift(in,x,y);
          if(QuickChess.inBounds(destination)&&game.getSquare(destination).piece.color!=color){
            if(destination.equalsIgnoreCase(game.king[enemy])){
              return true;
            }
          }
        }
      }
    }
    for(int y=1;y>=-1;y-=2){
      destination= Square.shift(in,0,y);
      if(QuickChess.inBounds(destination)&&game.getSquare(destination).piece.color!=color){
        if(destination.equalsIgnoreCase(game.king[enemy])){
          return true;
        }
      }
    }
    return false;
  }
  
  
  
  public static void main(String[] args) { 
    
  }
}
