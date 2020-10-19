package com.company;

import java.util.Scanner;
public class Human extends Player {
  public static final Scanner console=new Scanner(System.in);
  public static boolean busy=true;
  private Scanner in=console;
  public ArtourGui.ChessGame game;
  
  public Human(ArtourGui.ChessGame game) { 
    this.game=game;
  }
  
  public Human() { 
    
  }
  
  public String getMove(ArtourGui.ChessGame board){
    
    if(game==null){//console input
      System.out.print("Input move:");
      return in.nextLine().trim().toUpperCase();
    }
    //Gui input
    game.first=null;
    game.destination=null;
    while(game.first==null||game.destination==null){
      try{
        Thread.sleep(5);
      }catch(Exception e){}
    }
    //System.out.println("AAAAAAAAAAAAAAAAAAAAAAAA");
    game.toggle=true;
    String promotion="";
    if(game.destination.charAt(1)=='8'-7*game.game.turn){
      if(game.game.getSquare(game.first).piece== Pawn.PIECE[game.game.turn]){
        promotion="Q";
      }
    }
    //System.out.println("Destination:"+game.destination);
    if(Integer.parseInt(""+game.destination.charAt(1))==6-(game.game.turn*5)){
      //choosepromotion
    }
    String str1=game.first,str2=game.destination;
    game.first=null;
    game.destination=null;
    return str1+str2+promotion;
  }
  
  
  public static void main(String[] args) { 
    
  }
}
