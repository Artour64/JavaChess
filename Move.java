
public class Move {
  public String move;
  //public String[] comment;
  //public Move[] moves;
  //public QuickChess previos;
  
  public Move(String name) { 
    move=name;
  }
  
  public Move() { 
    
  }
 
  public static String arrayToString(Move[] moves){
    String out="{";
    if(moves.length>0){
      //System.out.println(moves[0].move);
      out+=moves[0].move;
    }
    for(int c=1;c<moves.length;c++){
      //System.out.println(moves[c].move);
      out+=","+moves[c].move;
    }
    return out+"}";
  }
  
  public static Move[] removeIlligal(QuickChess game,Move[] in){//obsolete
    int index=0;
    Move[] moves=new Move[in.length];
    for(int c=0;c<in.length;c++){
      if(in[c].isLegal(game)){
        moves[index]=in[c];
        index++;
      }
    }
    Move[] out=new Move[index];
    for(int c=0;c<index;c++){
      out[c]=moves[c];
    }
    return out;
  }
  
  public boolean isLegal(QuickChess game){
    QuickChess nextTurn=new QuickChess(game);
    nextTurn.move(this);
    nextTurn.turn=(nextTurn.turn+1)%2;
    if(nextTurn.isCheck()){
      //nextTurn.turn=(nextTurn.turn+1)%2;
      return false; 
    }
    return true;
  }
  
  public static Move match(Move[] array, String move){
    move=move.toUpperCase();
    for(int c=0;c<array.length;c++){
      if(array[c].move.equalsIgnoreCase(move)){
        return array[c];
      }
    }
    return null;
  }
  
  public static void main(String[] args) { 
    
  }
}
//