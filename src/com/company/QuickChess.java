package com.company;//import java.awt.Dimension;//may be obsolete
//import javax.swing.*;
//import java.awt.*;
import java.lang.reflect.Array;
public class QuickChess {
  public static QuickChess main;//for debugging, remove later
  public static final String[] color={"White","Black"};
  public static final int startPoints=(Pawn.points*8)+(Rook.points*2)+(Bishop.points*2)+(King.points)+(Queen.points)+(Knight.points*2);
  public static final int maxLastCap=20;
  //public static Move dataBase=new Move().createDataBase((new QuickChess()).reset(),7);
  public Player[] player=new Player[2];
  public Square[][] board=new Square[8][8];
  public String[][] alive=new String[2][16];//uncomment when will need
  public String[] king={"E1","E8"};
  public ArtourGui.ChessGame display;
  //public Move move=null;//uncomment when will need
  public int[] points=new int[2];
  public int turn=0;//white=0
  public int lastCap=0;
  public int movesMade=0;
  public String enpasent=null;
  public String lastMove2="";
  public String lastMove="";
  public boolean[] canCastle={true,true};
  public boolean[] kingCastle={true,true};
  public boolean[] queenCastle={true,true};
  public int[] pieces={2,2,2,1,8};//R,N,B,Q,P
  
  public QuickChess(Player player1,Player player2) {
    this();
    player[0]=player1;
    player[1]=player2;
  }
  
  public QuickChess(QuickChess game) {
    points=new int[]{game.points[0],game.points[1]};
    lastCap=game.lastCap;
    alive=new String[game.alive.length][];
    for (int i=0;i<=1;i++){
      alive[i]=new String[game.alive[i].length];
      for(int c=0;c<alive[i].length;c++){
        alive[i][c]=game.alive[i][c];
      }
    }
    king=new String[]{game.king[0],game.king[1]};
    turn=game.turn;
    for(int x=0;x<board.length;x++){
      for(int y=0;y<board[0].length;y++){
        board[x][y]=new Square();
        board[x][y].piece=game.board[x][y].piece;
      }
    }
    lastMove2=game.lastMove2;
    lastMove=game.lastMove;
    enpasent=game.enpasent;
    movesMade=game.movesMade;
    canCastle=new boolean[]{game.canCastle[0],game.canCastle[1]};
    kingCastle=new boolean[]{game.kingCastle[0],game.kingCastle[1]};
    queenCastle=new boolean[]{game.queenCastle[0],game.queenCastle[1]};
  }
  
  public QuickChess() {
    for (int x=0;x<board.length;x++){
      for(int y=0;y<board[0].length;y++){
        board[x][y]=new Square();
      }
    }
  }
  
  public QuickChess reset(){
    turn=0;
    points[0]=startPoints;
    points[1]=startPoints;
    for (int x=0;x<board.length;x++){//constants instead of new instances(use less memory)
      board[x][1].piece=Pawn.PIECE[0];//white pawn
      //middle row
      for(int y=2;y<6;y++){
        board[x][y].piece=Piece.PIECE;
      }
      board[x][6].piece=Pawn.PIECE[1];//black pawn
    }
    
    for (int sq=0;sq<2;sq++){
      board[0][sq*7].piece= Rook.PIECE[sq];
      board[1][sq*7].piece=Knight.PIECE[sq];
      board[2][sq*7].piece=Bishop.PIECE[sq];
      board[3][sq*7].piece=Queen.PIECE[sq];
      board[4][sq*7].piece=King.PIECE[sq];
      board[5][sq*7].piece=Bishop.PIECE[sq];
      board[6][sq*7].piece=Knight.PIECE[sq];
      board[7][sq*7].piece= Rook.PIECE[sq];
    }
    //alive initialization,can be optimized
    int[] aliveIndex=new int[2];
    for (int i=0;i<2;i++){
      for (int x=0;x<board.length;x++){
        for (int y=0;y<board[0].length;y++){
          if(getSquare(Square.shift("A1",x,y)).piece.color==i){
            alive[i][aliveIndex[i]]= Square.shift("A1",x,y);
            aliveIndex[i]++;
          }
        }
      }
    }
    return this;
  }
  
//  public Square getSquare(Dimension cordinates){
//    return board[cordinates.width][cordinates.height];
//  }
  
  public Square getSquare(int x, int y){
    return board[x][y];
  }
  
  public Square getSquare(String cord){
    
    int x=cord.charAt(0)-97;
    if (x<0){
      x+=32;
    }
    try{
      return board[x][cord.charAt(1)-'1'];
    }catch(Exception e){
      //printBoardInfo();
      System.out.println(cord);
      return board[x][cord.charAt(1)-'1'];
    }
  }
  
  public void move(Move move){
    String start=move.move.substring(0,2).toUpperCase();
    String end=move.move.substring(2,4).toUpperCase();
    Square piece=getSquare(start);
    Square target=getSquare(end);
    int c=0;
    lastCap++;
    movesMade++;
    for(;c<alive[turn].length&&!alive[turn][c].equalsIgnoreCase(start);c++){
    }
    alive[turn][c]=end.toUpperCase();
    turn=(turn+1)%2;//change turn
    if (target.piece!=Piece.PIECE){//capture
      points[target.piece.color]-=target.piece.points();
      alive[target.piece.color]=remove(alive[target.piece.color],end);
      enpasent=null;
    }
    target.piece=piece.piece;
    piece.piece=Piece.PIECE;
    if(target.piece.repChar()=='P'){
      lastCap=0;
      if(Math.abs(end.charAt(1)-start.charAt(1))==2){
        enpasent=start.charAt(0)+""+((char)(start.charAt(1)+1-((turn+1)%2)*2));
      }else if(enpasent!=null){
        if(end.equals(enpasent)){
          String pawn= Square.shift(end,0,1-turn*2);
          
          Square pawnSquare=getSquare(pawn);
          try{
            alive[pawnSquare.piece.color]=remove(alive[pawnSquare.piece.color],pawn);
          }catch(Exception e){
            printBoardInfo();
            System.out.println(move.move);
            System.out.println(pawn);
            alive[pawnSquare.piece.color]=remove(alive[pawnSquare.piece.color],pawn);
          }
          pawnSquare.piece=Piece.PIECE;
          points[turn]--;
          enpasent=null;
        }
        enpasent=null;
      }else{//if(end.charAt(1)=='8'-7*(turn+1)%2)
        if(move.move.length()>4){//if promotion to specified and at end(the later not yet implemented)
          target.promote(this,move.move.charAt(4));
        }
        enpasent=null;
      }
    }else{
      enpasent=null;
      if(target.piece.repChar()=='K'){//if moving a king,update king position(used for checking for check;
        king[(turn+1)%2]=end;
        canCastle[(turn+1)%2]=false;
        if(end.charAt(0)-start.charAt(0)==2){//kingside castle
          turn=(turn+1)%2;
          char rank=(char)('1'+turn*7);
          move(new Move("H"+rank+"F"+rank));
        }else if(end.charAt(0)-start.charAt(0)==-2){//queenside castle
          turn=(turn+1)%2;
          char rank=(char)('1'+turn*7);
          move(new Move("A"+rank+"D"+rank));
        }
      }else if(target.piece.repChar()=='R'){
        if(start.charAt(0)=='A'){
          queenCastle[(turn+1)%2]=false;
        }else if(start.charAt(0)=='H'){
          kingCastle[(turn+1)%2]=false;
        }
      }
    }
    lastMove2=lastMove;
    lastMove=move.move;
  }
  
  public static boolean inBounds(String cord){
    char y=cord.charAt(1);//49
    if(y<'1'||y>'8'){
      return false;
    }
    int x=cord.charAt(0)-97;
    if (x<0){
      x+=32;
    }
    if(x<0||x>7){
      return false;
    }
    //System.out.println(cord);
    return true;
  }
  
  public void printBoard(){
    String out="  ";
    for(char x='A';x<='H';x++){
      out+=" "+x+" ";
    }
    out+="\n";
    for(int y=board[0].length-1;y>-1;y--){
      out+=(y+1)+" ";
      for(int x=0;x<board.length;x++){
        out+=getSquare(x,y).piece.strRep()+" ";
      }
      out+="\n";
    }
    System.out.println(out);
  }
  
  public void printBoardInfo(){
    String out="\n  ";
    for(char x='A';x<='H';x++){
      out+=" "+x+" ";
    }
    out+="\n";
    
    for(int y=board[0].length-1;y>-1;y--){
      out+=(y+1)+" ";
      for(int x=0;x<board.length;x++){
        out+=getSquare(x,y).piece.strRep()+" ";
      }
      out+="\n";
    }
    //Move[] moves=getMovesLegal();
    //out+=Move.arrayToString(moves)+"\n";
    out+="LastMoves: "+lastMove2+","+lastMove+"\n";
    if(isCheck()){
      out+="Check!\n";
    }
    out+="LastCap:"+lastCap+"\n";
    out+="MovesMade:"+movesMade+"\n";
    out+="Score:"+points[0]+"-"+points[1]+"\n";
    out+=color[turn]+", go.";
    System.out.println(out);
  }
  
  public <T> T[] remove(T[] array, T item){
    T[] newArray=(T[])Array.newInstance(array.getClass().getComponentType(),array.length-1);
    int c=0;
    for (;array[c]!=item;c++){
      newArray[c]=array[c];
    }
    
    for (;c<newArray.length;c++){
      newArray[c]=array[c+1];
    }
    return newArray;
  }
  
  public String[] remove(String[] array, String item){
    String[] newArray=new String[array.length-1];
    int c=0;
    for (;!array[c].equalsIgnoreCase(item);c++){
      newArray[c]=array[c];
    }
    for (;c<newArray.length;c++){
      newArray[c]=array[c+1];
    }
    return newArray;
  }
  
  public String getMove(){
    return player[turn].getMove(display);
  }
  /*
   public Move[] getMoves(String in){
   Square location=getSquare(in);
   return location.piece.getMoves(this,in);
   }
   
   public Move[] getMoves(){
   Move[][] moves=new Move[alive[turn].length][];
   for(int c=0;c<alive[turn].length;c++){
   moves[c]=getMoves(alive[turn][c]);
   }
   return combine(moves);
   }
   */
  public Move[] getMovesLegal(){
    Move[][] moves=new Move[alive[turn].length][];
    for(int c=0;c<alive[turn].length;c++){
      moves[c]=getMovesLegal(alive[turn][c]);
    }
    return combine(moves);
  }
  
  public Move[] getMovesLegal(String in){
    Square location=getSquare(in);
    return location.piece.getMovesLegal(this,in);
  }
  
  public boolean isCheck2(){
    turn=(turn+1)%2;
    for (int c=0;c<alive[turn].length;c++){
      if(this.getSquare(alive[turn][c]).piece.isChecking(this,alive[turn][c])){
        turn=(turn+1)%2;
        return true;
      }
    }
    turn=(turn+1)%2;
    return false;
  }
  public boolean isCheck(){
    Move[] moves=Knight.PIECE[turn].getMoves(this,king[turn]);
    for(int c=0;c<moves.length;c++){
      if(getSquare(moves[c].move.substring(2,4)).piece==Knight.PIECE[(turn+1)%2]){
        return true; 
      }
    }
    Piece temp=null;
    moves=Bishop.PIECE[turn].getMoves(this,king[turn]);
    for(int c=0;c<moves.length;c++){
      temp=getSquare(moves[c].move.substring(2,4)).piece;
      //System.out.println(temp.strRep());
      if(temp==Bishop.PIECE[(turn+1)%2]||temp==Queen.PIECE[(turn+1)%2]){
        return true; 
      }
    }
    moves= Rook.PIECE[turn].getMoves(this,king[turn]);
    for(int c=0;c<moves.length;c++){
      temp=getSquare(moves[c].move.substring(2,4)).piece;
      if(temp== Rook.PIECE[(turn+1)%2]||temp==Queen.PIECE[(turn+1)%2]){
        return true; 
      }
    }
    moves=King.PIECE[turn].getMoves(this,king[turn]);
    for(int c=0;c<moves.length;c++){
      temp=getSquare(moves[c].move.substring(2,4)).piece;
      if(temp==King.PIECE[(turn+1)%2]){
        return true; 
      }
    }
    if(king[turn].charAt(1)!='8'-turn*7){//fix x
      //System.out.println((char)(king[turn].charAt(1)+2*turn-1));
      if(king[turn].charAt(0)>'A'){
        //--------------------------------------------
        if(getSquare((char)(king[turn].charAt(0)-1)+""+(char)(king[turn].charAt(1)-2*turn+1)).piece==Pawn.PIECE[(turn+1)%2]){
          return true;
        }
        if(king[turn].charAt(0)<'H'){
          //--------------------------------------------
          if(getSquare((char)(king[turn].charAt(0)+1)+""+(char)(king[turn].charAt(1)-2*turn+1)).piece==Pawn.PIECE[(turn+1)%2]){
            return true;
          }
        }
      }else{
        //--------------------------------------------
        if(getSquare((char)(king[turn].charAt(0)-1)+""+(char)(king[turn].charAt(1)-2*turn+1)).piece==Pawn.PIECE[(turn+1)%2]){
          return true;
        }
      }
    }
    return false;
  }
  
  public boolean noMoves(){
    for (int c=0;c<alive[turn].length;c++){
      if(getSquare(alive[turn][c]).piece.canMove(this,alive[turn][c])){
        return false;
      }
    }
    return true;
  }
  
  public boolean isCheckMate(){
    return noMoves()&&isCheck();
  }
  
  public int win(){
    if(noMoves()){
      if(isCheck()){//if checkmate, other player wins
        return (turn+1)%2;
      }
      return -1;//stalemate, tie game
    }
    //insufficient material, not yet implemented
    return 2;//game not over
  }
  
  public static <T>void printArray(T[] array){
    String out="{";
    if(array.length>0){
      out+=array[0]; 
    }
    for(int c=1;c<array.length;c++){
      out+=","+array[c];
    }
    out+="}";
    System.out.println(out);
  }
  
  public static <T>T[] combine(T[][] array){
    int size=0;
    for(int c=0;c<array.length;c++){
      size+=array[c].length;
    }
    T[] newArray=(T[])Array.newInstance(array.getClass().getComponentType().getComponentType(),size);
    int index=0;
    for(int c=0;c<array.length;c++){
      for(int i=0;i<array[c].length;i++){
        newArray[index]=array[c][i];
        index++;
      }
    }
    return newArray;
  }
  
  public void play(ArtourGui.ChessGame board) { //plays a game---------------------------------------------------
    
    String moveInput="";
    
    //main=game;
    int win=2;
    Move[] moves=null;
    Move move=null;
    String out="";
    boolean valid=false;
    while(win==2){
      
      out="";
      printBoard();
      board.updateBoard();
      moves=getMovesLegal();
      out+=Move.arrayToString(moves)+"\n";
      if(isCheck()){
        out+="Check!\n";
      }
      double time1=System.nanoTime();
      out+=AI.rail(board.game,3)+"\n";
      double time2=System.nanoTime();
      out+=AI2.rail(board.game,3)+"\n";
      double time3=System.nanoTime();
      out+="Time:"+((time2-time1)/1000000)+"\n";
      out+="Time:"+((time3-time2)/1000000)+"\n";
      out+="LastCap:"+lastCap+"\n";
      out+="MovesMade:"+movesMade+"\n";
      out+="Score:"+points[0]+"-"+points[1]+"\n";
      int difference=(points[0]-points[1]);
      out+="Difference:"+difference+"\n";
      double ratio=(18.0*((double)(points[0]+1)/(double)(points[1]+1)-(double)(points[1]+1)/(double)(points[0]+1)));
      out+="Ratio:"+ratio+"\n";
      double average=(difference+ratio)/2.0;
      out+="Average:"+average+"\n";
      out+=color[turn]+", go.";
      System.out.println(out);
      valid=false;
      while(!valid){
        board.toggle=true;
        board.first=null;
        board.destination=null;
        //try{
        moveInput=getMove();
        move=Move.match(moves,moveInput);
        if(move!=null){
          valid=true;
        }else{
          System.out.println("Invalid move(1)");
        }
        //}catch (Exception e){
        //  System.out.println("Invalid move(2)");
        //}
      }
      move(move);
      win=win();
    }
    out="";
    printBoard();
    board.updateBoard();
    //moves=game.getMovesLegal();
    //out+=Move.arrayToString(moves)+"\n";
    if(isCheck()){
      out+="Check!\n";
    }
    out+="LastCap:"+lastCap+"\n";
    out+="MovesMade:"+movesMade+"\n";
    out+="Score:"+points[0]+"-"+points[1]+"\n";
    out+=color[turn]+"'s turn.\n";
    try{
      out+=color[win]+" won.";
    }catch(Exception e){
      out+="Tie game.";
    }
    System.out.println(out);
  }
  
  public static void main(String[] args) { //for testing purposes
    
    String moveInput="";
    QuickChess game=new QuickChess(new AdvancedAI(4),new AdvancedAI(3));
    game.reset();
    ArtourGui.Window window=new ArtourGui.Window("Java Quick chess");
    //ArtourGui.frame=window;
    ArtourGui.ChessGame board=new ArtourGui.ChessGame(game);
    window.add(board);
    //JFrame frame=new JFrame();
    //frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    //frame.setSize(350, 420);
    //frame.setResizable(true);
    //frame.setVisible(true);
    //frame.add(board);
    //frame.setVisible(true);
    //AI.rail(game,3);
    main=game;
    int win=2;
    Move[] moves=null;
    Move move=null;
    String out="";
    while(win==2){
      out="";
      game.printBoard();
      board.updateBoard();
      moves=game.getMovesLegal();
      out+=Move.arrayToString(moves)+"\n";
      if(game.isCheck()){
        out+="Check!\n";
      }
      out+="LastCap:"+game.lastCap+"\n";
      out+="MovesMade:"+game.movesMade+"\n";
      out+="Score:"+game.points[0]+"-"+game.points[1]+"\n";
      out+=color[game.turn]+", go.";
      System.out.println(out);
      moveInput=game.getMove();
      move=Move.match(moves,moveInput);
      game.move(move);
      win=game.win();
    }
    out="";
    game.printBoard();
    board.updateBoard();
    //moves=game.getMovesLegal();
    //out+=Move.arrayToString(moves)+"\n";
    if(game.isCheck()){
      out+="Check!\n";
    }
    out+="LastCap:"+game.lastCap+"\n";
    out+="MovesMade:"+game.movesMade+"\n";
    out+="Score:"+game.points[0]+"-"+game.points[1]+"\n";
    out+=color[game.turn]+"'s turn.\n";
    try{
      out+=color[win]+" won.";
    }catch(Exception e){
      out+="Tie game.";
    }
    System.out.println(out);
  }
}
