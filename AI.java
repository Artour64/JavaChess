
public class AI {
  
  public AI() { 
    
  }
  
  public static int rail(QuickChess game,int depth){
    int win=game.win();
    if(depth>0&&win==2){
      QuickChess.main=game;
      QuickChess next=null;
      boolean enemy=true;
      Move[] moves=null;
      int rail2=2;
      for (int i=0;i<game.alive[game.turn].length;i++){
        moves=game.getSquare(game.alive[game.turn][i]).piece.getMovesLegal(game,game.alive[game.turn][i]);
        for(int c=0;c<moves.length;c++){
          next=new QuickChess(game);
          next.move(moves[c]);
          rail2=rail(next,depth-1);
          if(rail2==game.turn){
            return rail2;
          }else if(rail2!=(game.turn+1)%2){
            enemy=false;
          }
        }
      }
      if(enemy){
        return (game.turn+1)%2;
      }
    }
    return win;
  }
  
  public static int avoid(QuickChess game,int color, int depth){
    int win=game.win();
    if(depth>0&&win==2){
      QuickChess next=null;
      Move[] moves=null;
      int rail2=2;
      for (int i=0;i<game.alive[game.turn].length;i++){
        moves=game.getSquare(game.alive[game.turn][i]).piece.getMovesLegal(game,game.alive[game.turn][i]);
        for(int c=0;c<moves.length;c++){
          next=new QuickChess(game);
          next.move(moves[c]);
          rail2=avoid(next,color,depth-1);
          if(color==game.turn&&rail2!=(color+1)%2){
            return rail2;
          }else if(rail2!=color){
            return rail2;
          }
        }
      }
    }
    return win;
  }
  
  public static Move chooseMove(QuickChess game,int depth){
    
    //int rail=rail(game,depth);
    Move[] moves=game.getMovesLegal();//moves contains all legal moves
    if(moves.length==1){
      return moves[0]; 
    }
    QuickChess[] next=new QuickChess[moves.length];
    for(int c=0;c<moves.length;c++){
      next[c]=new QuickChess(game);
      next[c].move(moves[c]);
    }
    for(int c=0;c<moves.length;c++){
      
    }
    Move[] noLossMoves=new Move[moves.length];
    int noLossMovesindex=0;
    int rail=-1;
    int positionTemp=0;
    int[] position2=new int[moves.length],position3=new int[moves.length];
    boolean[] used=new boolean[moves.length];
    /*//new stuff, may cause problems, not yet tested
    int level=0;
    while(level<depth&&!stop){
      for(int c=0;c<moves.length&&!stop;c++){
        stop=stop||(game.turn)%2+1!=avoid(next[c],game.turn,level);
      }
      if(!stop){
        level++;
      }
    }
    //level--;
    for(int c=0;c<moves.length;c++){
      if((game.turn)%2+1!=avoid(next[c],game.turn,level)){
        noLossMoves[noLossMovesIndex]=moves[c];
        noLossMovesIndex++;
      }
    }
    if(noLossMovesIndex>0){
      moves=new Move[noLossMovesIndex];
      for(int c=0;c<noLossMovesIndex;c++){
        moves[c]=noLossMoves[c];
      }
      if(moves.length==1){
        return moves[0];
      }
    }
    /**/
    
    for(int c=0;c<moves.length;c++){
      if(rail(next[c],(depth-1)/2)==game.turn){
        return moves[c];//ideal move
      }
    }
    
    for(int c=0;c<moves.length;c++){
      rail=rail(next[c],depth-1);
      if(rail==game.turn){
        return moves[c];//ideal move (2nd priority)
      }else {
        if(rail!=(game.turn+1)%2){//not loose
          positionTemp=position(next[c],depth-1);
          noLossMoves[noLossMovesindex]=moves[c];
          position2[noLossMovesindex]=positionTemp;
          noLossMovesindex++;
          position3[c]=positionTemp;
          used[c]=true;
        }
      }
    }
    int[] position=null;
    if(noLossMovesindex>0){
      position=new int[noLossMovesindex];
      moves=new Move[noLossMovesindex];
      for(int c=0;c<noLossMovesindex;c++){
        moves[c]=noLossMoves[c];
        position[c]=position2[c];
      }
      if(moves.length==1){
        return moves[0]; 
      }
    }else{
      position=position3;
      for(int c=0;c<position.length;c++){
        if(!used[c]){;
          position[c]=position(next[c],depth-1);
        }
      }
    }
    
    //moves contails all legal moves that do not lead to garanteed loss within depth turns(assuming enemy perfect play)(unless no other moves)
    /*
     int[] position=new int[moves.length];
     for(int c=0;c<moves.length;c++){
     next=new QuickChess(game);
     next.move(moves[c]);
     position[c]=position(next,depth-1);
     }
     /**/
    int preferable=0;
    int tie=1;
    if(game.turn==0){
      for(int c=1;c<position.length;c++){
        if(position[preferable]<position[c]){
          preferable=c;
          tie=1;
        }else if(position[preferable]==position[c]){
          tie++;
        }
      }
    }else{
      for(int c=1;c<position.length;c++){
        if(position[preferable]>position[c]){
          preferable=c;
          tie=1;
        }else if(position[preferable]==position[c]){
          tie++;
        }
      }
    }
    for(int rand=(int)(Math.random()*tie),c=preferable;rand>1;){
      c++;
      for(;position[c]!=position[preferable];c++){}
      preferable=c;
      rand--;
    }
    return moves[preferable];
  }
  
  public static int position(QuickChess game,int depth){
    if(depth>0){
      Move[] moves=game.getMovesLegal();
      int[] position=new int[moves.length];
      if(position.length==0){
        if(game.isCheck()){
          if (game.turn==0){
            return -1000;
          }else{
            return 1000;
          }
        }
        if(game.points[0]<game.points[1]){
          return -1000;
        }else if(game.points[0]>game.points[1]){
          return 1000;
        }
        return 0;
      }
      QuickChess next=null;
      for(int c=0;c<moves.length;c++){
        next=new QuickChess(game);
        next.move(moves[c]);
        position[c]=position(next,depth-1);
      }
      //compare position
      int preferable=0;
      if(game.turn==0){
        for(int c=1;c<position.length;c++){
          if(position[preferable]<position[c]){
            preferable=c;
          }
        }
      }else{
        for(int c=1;c<position.length;c++){
          if(position[preferable]>position[c]){
            preferable=c;
          }
        }
      }
      return position[preferable];
    }
    return positionValue(game);
  }
  
  public static int positionValue(QuickChess game){//compare positions
    final int pointsMultiplier=1;
    int totalValue=(game.points[0]-game.points[1])*pointsMultiplier;
    return totalValue;
  }
  
  public static int[] attackRange(QuickChess game){//not yet implemented
    int[] out=new int[2];
    String cord="";
    for(int x='A';x<='E';x++){
      for(int y=1;y<=6;y++){
        cord=x+""+y;
      }
    }
    if(cord==""){}//warning suppression
    return out;
  }
  
  public static void main(String[] args) { 
    QuickChess game=new QuickChess();
    game.reset();
    int x=0;
    System.out.println();
    for(int c=0;c<10;c++)
      x=avoid(game,game.turn,6);
    System.out.println(x);
    for(int c=0;c<10;c++)
      x=rail(game,4);
    System.out.println(x);
  }
}
