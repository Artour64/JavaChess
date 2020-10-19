package com.company;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.concurrent.TimeUnit;
import javax.swing.BoxLayout;
public class ArtourGui extends JPanel{
  public static boolean isMenu=true;
  public static Window frame;
  public static class Window extends JFrame{
    JPanel panel;
    Window(String name){
      super(name);
      setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      setSize(MainMenu.size);
      setResizable(true);
      setVisible(true);
    }
    
    public void add(JPanel panelIn){
      super.getContentPane().add(panelIn);
      panel=panelIn;
      repaint();
    }
    
    public void swap(JPanel panelIn){
      super.getContentPane().remove(panel);
      add(panelIn);
      repaint();
    }
  }
  
  public static class MainMenu extends JPanel{
    public static Dimension size=new Dimension(190,220);
    public int difficultyAI=3;
    public JLabel difficultyLabel=new JLabel();
    public JButton[] toggle=new JButton[2];
    public boolean[] player=new boolean[2];
    
    public QuickChess newGame(){
      Player[] playerTemp=new Player[2];
      
      for(int c=0;c<2;c++){
        if(player[c]){
          playerTemp[c]=new Human();
        }else{
          if(difficultyAI>0){
            playerTemp[c]=new AdvancedAI(difficultyAI);
          }else{
            playerTemp[c]=new RandomAI();
          }
        }
      }
      QuickChess board=new QuickChess(playerTemp[0],playerTemp[1]);
      board.reset();
      return board;
      
    }
    
    public static String player(boolean in){
      if(in){
        return "Human";
      }
      return "AI";
    }
    
    private void toggle(int c){
      player[c]=!player[c];
      toggle[c].setText(QuickChess.color[c]+": "+player(player[c]));
    }
    
    public MainMenu(){
      
      super();
      setLayout(new BoxLayout(this,BoxLayout.PAGE_AXIS));
      
      JPanel temp=new JPanel();
      temp.add(new JLabel("AI Difficulty:"));
      add(temp);
      
      temp=new JPanel();
      
      JButton decrease=new JButton("-");
      decrease.addActionListener(this.new CustomListenerDecrease(this));
      temp.add(decrease);
      
      difficultyLabel.setText(""+difficultyAI);
      temp.add(difficultyLabel);
      
      JButton increase=new JButton("+");
      increase.addActionListener(this.new CustomListenerIncrease(this));
      temp.add(increase);
      
      JButton info=new JButton("?");
      info.addActionListener(this.new CustomListenerInfo(this));
      temp.add(info);
      
      add(temp);
      
      for(int c=0;c<2;c++){
        toggle[c]=new JButton(QuickChess.color[c]+": "+player(player[c]));
        toggle[c].addActionListener(this.new CustomListenerToggle(c,this));
        temp=new JPanel();
        temp.add(toggle[c]);
        add(temp);
      }
      
      JButton start=new JButton("Start Game!");
      start.addActionListener(this.new CustomListenerStart(frame));
      temp=new JPanel();
      temp.add(start);
      add(temp);
      //setVisible(true);
    }
    
    public class CustomListenerIncrease implements ActionListener{
      MainMenu menu;
      public CustomListenerIncrease(MainMenu menu){
        super();
        this.menu=menu;
      }
      @Override
      public void actionPerformed(ActionEvent ae){
        menu.difficultyAI++;
        menu.difficultyLabel.setText(""+difficultyAI);
      }
    }
    
    public class CustomListenerDecrease implements ActionListener{
      MainMenu menu;
      public CustomListenerDecrease(MainMenu menu){
        super();
        this.menu=menu;
      }
      @Override
      public void actionPerformed(ActionEvent ae){
        if(menu.difficultyAI>0){
          menu.difficultyAI--;
          menu.difficultyLabel.setText(""+difficultyAI);
        }
      }
    }
    
    public class CustomListenerInfo implements ActionListener{
      MainMenu menu;
      public CustomListenerInfo(MainMenu menu){
        super();
        this.menu=menu;
      }
      @Override
      public void actionPerformed(ActionEvent ae){
        frame.swap(new InfoPage(menu));
        frame.setSize(550,210);
      }
      
      private class InfoPage extends JPanel{
        public InfoPage(MainMenu menu){
          super();
          setLayout(new BoxLayout(this,BoxLayout.PAGE_AXIS));
          JPanel temp=new JPanel();
          String[] txt=
          {"The higher the difficulty number, the more inteligent the AI is.\n",
            "It will however take longer to make moves.\n",
            "Difficulty level 5 was the one that was demonstrated during the tournament.\n",
            "A higher level of difficulty is not recommended as it takes very long to make moves.\n",
            "Difficulty level 4 is significantly faster than 5 and may be used if 5 is deemed to take too long."};
          for(int c=0;c<txt.length;c++){
            temp.add(new JLabel(txt[c]));
            add(temp);
            temp=new JPanel();
          }
          JButton back=new JButton("Go Back");
          back.addActionListener(new CustomListenerBack(menu));
          temp.add(back);
          add(temp);
        }
        private class CustomListenerBack implements ActionListener{
          MainMenu menu;
          public CustomListenerBack(MainMenu menu){
            super();
            this.menu=menu;
          }
          @Override
          public void actionPerformed(ActionEvent ae){
            frame.swap(menu);
            frame.setSize(MainMenu.size);
          }
        }
      }
    }
    
    public class CustomListenerToggle implements ActionListener{
      int c;
      MainMenu menu;
      public CustomListenerToggle(int c,MainMenu menu){
        super();
        this.c=c;
        this.menu=menu;
      }
      @Override
      public void actionPerformed(ActionEvent ae){
        menu.toggle(c);
      }
    }
    private class CustomListenerStart implements ActionListener{
      Window frame;
      public CustomListenerStart(Window frame){
        super();
        this.frame=frame;
      }
      @Override
      public void actionPerformed(ActionEvent ae){
        QuickChess game=((MainMenu)(frame.panel)).newGame();
        ChessGame board=new ChessGame(game);
        for(int c=0;c<2;c++){
          if(game.player[c] instanceof Human){
            ((Human)game.player[c]).game=board;//-----------------------------------------------------------------------------------------
          }
        }
        board.updateBoard();
        frame.swap(board);
        //int x=frame.getSize().width,y=frame.getSize().height;
        //frame.setSize(x+1,y+1);
        //frame.setSize(x,y);
        //game.play(board);
        isMenu=false;
      }
    }
  }
  
  
  public static class ChessGame extends JPanel{
    QuickChess game;
    public String first=null,destination=null;
    public Boolean toggle=true;
    public JButton[][] buttons=new JButton[8][8];
    public ChessGame(QuickChess gameIn) {
      //super();
      game=gameIn;
      game.display=this;
      GridLayout layout = new GridLayout(9,9);
      setLayout(layout);
      add(new JButton());
      for(char x='A';x<='H';x++){
        add(new JButton(""+x));
      }
      boolean odd=false;
      for(int y=7;y>-1;y--){
        add(new JButton(""+(y+1)));
        for(int x=0;x<8;x++){
          
          buttons[x][y]=new JButton();
          if(odd){
            buttons[x][y].setBackground(new Color(100, 50, 0));//150,100,50
          }else{
            buttons[x][y].setBackground(new Color(255, 214, 160));//255, 204, 150
          }
          odd=!odd;
          //buttons[x][y].setVisible(true);
          buttons[x][y].addActionListener(new CustomListener(this,x,y));
          
          add(buttons[x][y]);
        }
        odd=!odd;
      }
      
      //updateBoard();
      //setVisible(true);
    }
    
    public void updateBoard(){
      for(int x=0;x<8;x++){
        for(int y=0;y<8;y++){
          //System.out.println(getClass());
          //System.out.println(game.board[x][y].piece);
          try{
            buttons[x][y].setIcon(new ImageIcon(getClass().getResource(game.board[x][y].piece.image())));
          }catch(Exception e){
            buttons[x][y].setIcon(null);
          }
        }
      }
    }
    
    public class CustomListener implements ActionListener{
      Window frame;
      ChessGame game;
      int x,y;
      public CustomListener(ChessGame game,int x,int y){
        super();
        this.game=game;
        this.x=x;
        this.y=y;
      }
      @Override
      public void actionPerformed(ActionEvent ae){
        if(game.first==null){//first move
          String temp= Square.shift("A1",x,y);
          if(game.game.getSquare(temp).piece.color==game.game.turn){
            game.first=temp;
            toggle=false;
          }
        }else if(game.destination==null){//second move
          game.destination= Square.shift("A1",x,y);
          toggle=null;
        }else{
          System.out.println(Square.shift("A1",x,y));
          game.first=null;
          game.destination=null;
          //promotion
          //toggle=true;
        }
        //game.toggle=!game.toggle;
        //System.out.println(Square.shift("A1",x,y));
      }
    }
  }
  
  public static void mainMethod(){
    Window window=new Window("Java Quick chess");
    ArtourGui.frame=window;
    MainMenu menu=new MainMenu();
    window.add((JPanel)menu);
    int x=window.getSize().width,y=window.getSize().height;
    window.setSize(x+1,y+1);
    window.setSize(x,y);
    while(isMenu){
      try {
        TimeUnit.MILLISECONDS.sleep(1);
      }catch (Exception e){}
    }
    ChessGame panel=((ChessGame)(frame.panel));
    QuickChess game=panel.game;
    window.setSize(670, 700);
    QuickChess.main=game;
    game.play(panel);
  }
  
  public static void main(String[] args) { 
    ArtourGui.mainMethod();
  }
}
