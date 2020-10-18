
public class AI3 {
  
  public AI3() { 
    
  }
  
  public static void win(Node base,int depth,int player){
    /*if(base.win[0]){
      return;
    }if(base.win[1]){
      return;
    }if(base.draw[0]){
      return;
    }if(base.draw[1]){
      return;
    }*/
    int win=base.game.win();
    if(win!=2){
      if(win==-1){
        base.draw[base.game.turn]=true;
        return;
      }
      base.win[base.game.turn]=true;
      return;
    }
    if(depth>0){
      if(base.next==null){
        base.createNext();
      }
      boolean other=true;
      boolean[] draw=new boolean[2];
      draw[(player+1)%2]=true;
      if(player==base.game.turn){
        for(int d=0;d<depth;d++){
          if(d%2==0){
            for(int c=0;c<base.next.length;c++){
              win(base.next[c],d,player);
              if(base.next[c].win[player]){
                base.win[player]=true;
                base.depth=base.next[c].depth+1;
              }
            }
          }else{
            
          }
        }
        return;
      }//else if player!=base.game.turn
      for(int d=0;d<depth;d++){
        if(d%2==0){
          
        }else{
          
        }
      }
    }
  }
  
  public static void main(String[] args) { 
    
  }
}
