package com.company;

public class Pawn extends Piece {
  public static final Pawn[] PIECE={new Pawn(0),new Pawn(1)};
  public static final int points=1;
  private static final char rep='P';
  public static final String[] image={"pawnWhite.png","pawnBlack.png"};
  
  public char repChar(){
    return rep;
  }
  
  public int points(){
    return points;
  }
  
  public String image(){
    return image[color]; 
  }
  
  public Pawn() { 
    
  }
  
  public Pawn(int color) { 
    this.color=color;
  }
  
  public Move[] getMoves(QuickChess game, String in){//optimization possible
    Move[] moves=new Move[4];
    int index=0;
    String destination="";
    destination= Square.shift(in,0,1-(game.turn*2));
    if(game.getSquare(destination).piece.repChar()=='.'){
      moves[index]=new Move(in+destination);
      index++;
      if(in.charAt(1)=='2'+game.turn*5){
        destination= Square.shift(in,0,2-(color*4));
        if(game.getSquare(destination).piece.repChar()=='.'){
          moves[index]=new Move(in+destination);
          index++;
        }
      }
    }
    
    if(in.charAt(0)>'A'){
      destination= Square.shift(in,-1,1-(color*2));
      if(game.getSquare(destination).piece.color==(color+1)%2){
        moves[index]=new Move(in+destination);
        index++;
      }else if(game.enpasent!=null){
        if(game.enpasent.equals(destination)){
          moves[index]=new Move(in+destination);
          index++;
        }
      }
      if(in.charAt(0)<'H'){
        destination= Square.shift(in,1,1-(color*2));
        if(game.getSquare(destination).piece.color==(color+1)%2){
          moves[index]=new Move(in+destination);
          index++;
        }else if(game.enpasent!=null){
          if(game.enpasent.equals(destination)){
            moves[index]=new Move(in+destination);
            index++;
          }
        }
      }
    }else{
      destination= Square.shift(in,1,1-(color*2));
      if(game.getSquare(destination).piece.color==(color+1)%2){
        moves[index]=new Move(in+destination);
        index++;
      }else if(game.enpasent!=null){
        if(game.enpasent.equals(destination)){
          moves[index]=new Move(in+destination);
          index++;
        }
      }
    }
    
    if(index>0){
      Move[] out;
      if(in.charAt(1)=='7'-color*5){
        out=new Move[index*4];
        for(int c=0;c<index;c++){
          out[c*4]=new Move(moves[c].move+'Q');
          out[c*4+1]=new Move(moves[c].move+'N');
          out[c*4+2]=new Move(moves[c].move+'R');
          out[c*4+3]=new Move(moves[c].move+'B');
        }
      }else{
        out=new Move[index];
        for(int c=0;c<index;c++){
          out[c]=moves[c];
        }
      }
      return out;
    }
    return new Move[0];
  }
  
  public Move[] getMovesLegal(QuickChess game, String in){
    Move[] moves=new Move[4];
    Move tempVar=null;
    int index=0;
    String destination="";
    destination= Square.shift(in,0,1-(color*2));
    if(game.getSquare(destination).piece.repChar()=='.'){
      tempVar=new Move(in+destination);
      if(tempVar.isLegal(game)){
        moves[index]=tempVar;
        index++;
      }
      if(in.charAt(1)=='2'+color*5){
        destination= Square.shift(in,0,2-(color*4));
        if(game.getSquare(destination).piece.repChar()=='.'){
          tempVar=new Move(in+destination);
          if(tempVar.isLegal(game)){
            moves[index]=tempVar;
            index++;
          }
        }
      }
    }
    if(in.charAt(0)>'A'){
      destination= Square.shift(in,-1,1-(color*2));
      if(game.getSquare(destination).piece.color==(color+1)%2){
        tempVar=new Move(in+destination);
        if(tempVar.isLegal(game)){
          moves[index]=tempVar;
          index++;
        }
      }else if(game.enpasent!=null){
        if(game.enpasent.equals(destination)){
          tempVar=new Move(in+destination);
          if(tempVar.isLegal(game)){
            moves[index]=tempVar;
            index++;
          }
        }
      }
      if(in.charAt(0)<'H'){
        destination= Square.shift(in,1,1-(color*2));
        if(game.getSquare(destination).piece.color==(color+1)%2){
          tempVar=new Move(in+destination);
          if(tempVar.isLegal(game)){
            moves[index]=tempVar;
            index++;
          }
        }else if(game.enpasent!=null){
          if(game.enpasent.equals(destination)){
            tempVar=new Move(in+destination);
            if(tempVar.isLegal(game)){
              moves[index]=tempVar;
              index++;
            }
          }
        }
      }
    }else{
      destination= Square.shift(in,1,1-(color*2));
      if(game.getSquare(destination).piece.color==(color+1)%2){
        tempVar=new Move(in+destination);
        if(tempVar.isLegal(game)){
          moves[index]=tempVar;
          index++;
        }
      }else if(game.enpasent!=null){
        if(game.enpasent.equals(destination)){
          tempVar=new Move(in+destination);
          if(tempVar.isLegal(game)){
            moves[index]=tempVar;
            index++;
          }
        }
      }
    }
    if(index>0){
      Move[] out;
      if(in.charAt(1)=='7'-color*5){
        out=new Move[index*4];
        for(int c=0;c<index;c++){
          out[c*4]=new Move(moves[c].move+'Q');
          out[c*4+1]=new Move(moves[c].move+'N');
          out[c*4+2]=new Move(moves[c].move+'R');
          out[c*4+3]=new Move(moves[c].move+'B');
        }
      }else{
        out=new Move[index];
        for(int c=0;c<index;c++){
          out[c]=moves[c];
        }
      }
      return out;
    }
    return new Move[0];
  }
  
  public boolean canMove(QuickChess game, String in){//promotion not handeled, add later
    String destination="";
    destination= Square.shift(in,0,1-(color*2));
    if(game.getSquare(destination).piece.repChar()=='.'){
      if(new Move(in+destination).isLegal(game)){
        return true;
      }
      if(in.charAt(1)=='2'+game.turn*5){
        destination= Square.shift(in,0,2-(color*4));
        if(game.getSquare(destination).piece.repChar()=='.'){
          if(new Move(in+destination).isLegal(game)){
            return true;
          }
        }
      }
    }
    if(in.charAt(0)>'A'){
      destination= Square.shift(in,-1,1-(color*2));
      if(game.getSquare(destination).piece.color==(color+1)%2){
        if(new Move(in+destination).isLegal(game)){
          return true;
        }
      }else if(game.enpasent!=null){
        if(game.enpasent.equals(destination)){
          if(new Move(in+destination).isLegal(game)){
            return true;
          }
        }
      }
      if(in.charAt(0)<'H'){
        destination= Square.shift(in,1,1-(color*2));
        if(game.getSquare(destination).piece.color==(color+1)%2){
          if(new Move(in+destination).isLegal(game)){
            return true;
          }
        }else if(game.enpasent!=null){
          if(game.enpasent.equals(destination)){
            if(new Move(in+destination).isLegal(game)){
              return true;
            }
          }
        }
      }
    }else{
      destination= Square.shift(in,1,1-(color*2));
      if(game.getSquare(destination).piece.color==(color+1)%2){
        if(new Move(in+destination).isLegal(game)){
          return true;
        }
      }else if(game.enpasent!=null){
        if(game.enpasent.equals(destination)){
          if(new Move(in+destination).isLegal(game)){
            return true;
          }
        }
      }
    }
    return false;
  }
  
  public boolean isChecking(QuickChess game, String in){
    Piece destination;
    
    if(in.charAt(0)>'A'){
      destination=game.getSquare(Square.shift(in,-1,1-(color*2))).piece;
      if(destination.color==(game.turn+1)%2){
        if(destination.repChar()=='K'){
          return true;
        }
      }
      if(in.charAt(0)<'H'){
        destination=game.getSquare(Square.shift(in,1,1-(color*2))).piece;
        if(destination.color==(game.turn+1)%2){
          if(destination.repChar()=='K'){
            return true;
          }
        }
      }
    }else{
      destination=game.getSquare(Square.shift(in,1,1-(color*2))).piece;
      if(destination.color==(game.turn+1)%2){
        if(destination.repChar()=='K'){
          return true;
        }
      }
    }
    return false;
  }
  
  public static void main(String[] args) { 
    
  }
}
