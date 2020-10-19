package com.company;

public class AI2 {
  
  public AI2() { 
    
  }
  
  public static int rail(QuickChess game, int depth){
    int win=game.win();
    if(win!=2){
      return win;
    }
    Move[] moves=game.getMovesLegal();
    int end=moves.length;
    QuickChess[] simGame=new QuickChess[end];
    if(depth%2==0){
      for(int c=0;c<end;c++){
        simGame[c]=new QuickChess(game);
        simGame[c].move(moves[c]);
        if(forceWin(simGame[c],depth-2,game.turn)){
          return game.turn;
        }
      }
      for(int c=0;c<end;c++){
        if(forceWin(simGame[c],depth-1,(game.turn+1)%2)){
          end--;
        }
      }
      if (end==0){
        return (game.turn+1)%2;
      }
    }else{
      for(int c=0;c<end;c++){
        simGame[c]=new QuickChess(game);
        simGame[c].move(moves[c]);
        if(forceWin(simGame[c],depth-2,(game.turn+1)%2)){
          end--;
        }
      }
      if (end==0){
        return (game.turn+1)%2;
      }
      for(int c=0;c<end;c++){
        if(forceWin(simGame[c],depth-1,game.turn)){
          return game.turn;
        }
      }
    }
    return 2;
  }
  
  public static boolean forceWin(QuickChess game, int depth, int player){
    int win=game.win();
    if(win!=2){
      if (win==player){
        return true;
      }
      return false;
    }
    if(depth>0){
      if(game.turn==player){
        Move[] moves=game.getMovesLegal();
        int end=moves.length;
        int[] indexes=new int[end];
        QuickChess[] simGame=new QuickChess[end];
        for(int c=0;c<end;c++){
          indexes[c]=c;
          simGame[c]=new QuickChess(game);
          simGame[c].move(moves[c]);
        }
        for(int d=0;d<depth;d++){
          if(d%2==0){
            for(int c=0;c<end;c++){
              if(forceWin(simGame[indexes[c]],d,player)){
                return true;
              }
            }
          }else{
            for(int c=0;c<end;c++){
              if(forceWin(simGame[indexes[c]],d,(player+1)%2)){//change to force draw?
                simGame[indexes[c]]=null;
                end--;
                for(int i=c;i<end;i++){
                  indexes[i]=indexes[i+1];
                }
                c--;
              }
            }
            if(end==0){
              return false;
            }
          }
        }
        return false;
      }//else if(player!=game.turn)
      Move[] moves=game.getMovesLegal();
      int end=moves.length;
      int[] indexes=new int[end];
      QuickChess[] simGame=new QuickChess[end];
      for(int c=0;c<end;c++){
        indexes[c]=c;
        simGame[c]=new QuickChess(game);
        simGame[c].move(moves[c]);
      }
      for(int d=0;d<depth;d++){
        if(d%2==0){
          for(int c=0;c<end;c++){
            if(forceWin(simGame[indexes[c]],d,game.turn)){//change to force draw?
              return false;
            }
          }
        }else{
          for(int c=0;c<end;c++){
            if(forceWin(simGame[indexes[c]],d,player)){//change to force draw?
              simGame[indexes[c]]=null;
              end--;
              for(int i=c;i<end;i++){
                indexes[i]=indexes[i+1];
              }
              c--;
            }
          }
          if(end==0){
            return true;
          }
        }
      }
      return false;
    }
    return false;
  }
  
  public static Move chooseMove(QuickChess game, int depth){
    return null;
  }
  
  public static void main(String[] args) { 
    
  }
}
