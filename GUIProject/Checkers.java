/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package checkers;

/**
 *
 * @author vince
 */
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.ArrayList;


/**
 * two uses play checkers against each other
 * red always starts the game 
 * if player can jump opponent's piece, then player must jump 
 * when player can make no more moves, game ends
 * 
 */
public class Checkers extends JPanel {
    
   public static void main(String[] args) {
      JFrame window = new JFrame("Checkers");
      Checkers content = new Checkers();
      window.setContentPane(content);
      window.pack();
      Dimension screensize = Toolkit.getDefaultToolkit().getScreenSize();
      window.setLocation( (screensize.width - window.getWidth())/2,
            (screensize.height - window.getHeight())/2 );
      window.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
      window.setResizable(false);  
      window.setVisible(true);
   }
   
  
   
   private JButton newGameButton;  // new game btn
   private JButton resignButton;   // resign btn
   
   private JLabel message;  // message for user(s)
   
   
   //constructor creates board
   public Checkers() {
      
      setLayout(null);  
      setPreferredSize( new Dimension(350,250) );
      
      setBackground(new Color(0,150,0));  // dark green
      
      Board board = new Board();  
      add(board);
      add(newGameButton);
      add(resignButton);
      add(message);
      
      // set position and size
      board.setBounds(20,20,164,164); // size be 164-by-164
      newGameButton.setBounds(210, 60, 120, 30);
      resignButton.setBounds(210, 120, 120, 30);
      message.setBounds(0, 200, 350, 30);
      
   } // end constructor
   
   /**
    * CheckersMove object represents a move in game
    * holds the row and column of the piece that is to be moved
    * and holds the row and column of the square to which it is to be moved.
    */
   private static class CheckersMove {
      int fromRow, fromCol; 
      int toRow, toCol;      
      CheckersMove(int r1, int c1, int r2, int c2) {
         // set the values of the instance variables.
         fromRow = r1;
         fromCol = c1;
         toRow = r2;
         toCol = c2;
      }
      boolean isJump() {
             // test whether move is jump  
         return (fromRow - toRow == 2 || fromRow - toRow == -2);
      }
   }  // end class CheckersMove
   
   
   
   /**
    * displays a 160-by-160 checkerboard pattern with 2-pixel black border.  
    * let the users play checkers
    * displays checkerboard
    */
   private class Board extends JPanel implements ActionListener, MouseListener {
      
      
      CheckersData board;  // data for checkers board is kept here
      
      boolean gameInProgress; // is game in progress?
      
      int currentPlayer;      // whose turn
      
      // if current player selects piece to move, 
      // these give the row and column containing that piece
      // if no piece isyet selected, then selectedRow is -1.
      int selectedRow, selectedCol;  
      
      CheckersMove[] legalMoves;  // array of legal moves for player
      
      /**
       * create button and label
       * listens for mouse clicks and buttons clicks
       * create board and start first game
       */
      Board() {
         setBackground(Color.BLACK);
         addMouseListener(this);
         resignButton = new JButton("Resign");
         resignButton.addActionListener(this);
         newGameButton = new JButton("New Game");
         newGameButton.addActionListener(this);
         message = new JLabel("",JLabel.CENTER);
         message.setFont(new  Font("Serif", Font.BOLD, 14));
         message.setForeground(Color.green);
         board = new CheckersData();
         doNewGame();
      }
      
      // respond to user's click of button
      public void actionPerformed(ActionEvent evt) {
         Object src = evt.getSource();
         if (src == newGameButton)
            doNewGame();
         else if (src == resignButton)
            doResign();
      }
      
      
      // start new game
      void doNewGame() {
         if (gameInProgress == true) {
            message.setText("Finish the current game first!");
            return;
         }
         board.setUpGame();   // set up pieces
         currentPlayer = CheckersData.RED;   // red goes first
         legalMoves = board.getLegalMoves(CheckersData.RED);  // check legal
         selectedRow = -1;   // red has not chosen piece yet
         message.setText("Red:  Make your move.");
         gameInProgress = true;
         newGameButton.setEnabled(false);
         resignButton.setEnabled(true);
         repaint();
      }
      
      
      /**
       * current player resigns  
       * game ends  
       * opponent wins
       */
      void doResign() {
         if (gameInProgress == false) {
            message.setText("There is no game in progress!");
            return;
         }
         if (currentPlayer == CheckersData.RED)
            gameOver("RED resigns.  BLACK wins.");
         else
            gameOver("BLACK resigns.  RED wins.");
      }
      
      
      /**
       * game ends 
       */
      void gameOver(String str) {
         message.setText(str);
         newGameButton.setEnabled(true);
         resignButton.setEnabled(false);
         gameInProgress = false;
      }
      
      
      /**
       * called by mousePressed() when player clicks on 
       * square in specified row and col
       */
      void doClickSquare(int row, int col) {
         
         for (int i = 0; i < legalMoves.length; i++)
            if (legalMoves[i].fromRow == row && legalMoves[i].fromCol == col) {
               selectedRow = row;
               selectedCol = col;
               if (currentPlayer == CheckersData.RED)
                  message.setText("RED:  Make your move.");
               else
                  message.setText("BLACK:  Make your move.");
               repaint();
               return;
            }
         
         // if no piece has been selected to be moved, user must first select a piece
         // show an error message and return
         
         if (selectedRow < 0) {
            message.setText("Click the piece you want to move.");
            return;
         }
         
         // if the user clicked on a squre where the selected piece can be
         // legally moved, then make the move and return
         
         for (int i = 0; i < legalMoves.length; i++)
            if (legalMoves[i].fromRow == selectedRow && legalMoves[i].fromCol == selectedCol
                  && legalMoves[i].toRow == row && legalMoves[i].toCol == col) {
               doMakeMove(legalMoves[i]);
               return;
            }
         
         message.setText("Click the square you want to move to.");
         
      }  // end doClickSquare()
      
      
      /**
       * called when the current player has chosen the specified move
       * make the move, and then either end or continue the game appropriately
       */
      void doMakeMove(CheckersMove move) {
         
         board.makeMove(move);
         
         /* check for legal jumps starting from the square that the player
          just moved to.  if there are any, the player must jump.  the same
          player continues moving.
          */
         
         if (move.isJump()) {
            legalMoves = board.getLegalJumpsFrom(currentPlayer,move.toRow,move.toCol);
            if (legalMoves != null) {
               if (currentPlayer == CheckersData.RED)
                  message.setText("RED:  You must continue jumping.");
               else
                  message.setText("BLACK:  You must continue jumping.");
               selectedRow = move.toRow;  // since only one piece can be moved, select it
               selectedCol = move.toCol;
               repaint();
               return;
            }
         }
         
         /* current player's turn is ended, so change to the other player.
          get that player's legal moves.  if the player has no legal moves,
          then the game ends. */
         
         if (currentPlayer == CheckersData.RED) {
            currentPlayer = CheckersData.BLACK;
            legalMoves = board.getLegalMoves(currentPlayer);
            if (legalMoves == null)
               gameOver("BLACK has no moves.  RED wins.");
            else if (legalMoves[0].isJump())
               message.setText("BLACK:  Make your move.  You must jump.");
            else
               message.setText("BLACK:  Make your move.");
         }
         else {
            currentPlayer = CheckersData.RED;
            legalMoves = board.getLegalMoves(currentPlayer);
            if (legalMoves == null)
               gameOver("RED has no moves.  BLACK wins.");
            else if (legalMoves[0].isJump())
               message.setText("RED:  Make your move.  You must jump.");
            else
               message.setText("RED:  Make your move.");
         }
         
         /* set selectedRow = -1 to record that player has not yet selected
            piece to move. */
         
         selectedRow = -1;
         
         /* if all legal moves use the same piece, then
          select that piece automatically so the use won't have to click on it
          to select it. */
         
         if (legalMoves != null) {
            boolean sameStartSquare = true;
            for (int i = 1; i < legalMoves.length; i++)
               if (legalMoves[i].fromRow != legalMoves[0].fromRow
                     || legalMoves[i].fromCol != legalMoves[0].fromCol) {
                  sameStartSquare = false;
                  break;
               }
            if (sameStartSquare) {
               selectedRow = legalMoves[0].fromRow;
               selectedCol = legalMoves[0].fromCol;
            }
         }
         
         // update board
         repaint();
         
      }  // end doMakeMove();
      
      
      /**
       * draw checkerboard pattern in grey and lightGrey
       * draw the checkers
       */
      public void paintComponent(Graphics g) {
         
         // border
         g.setColor(Color.black);
         g.drawRect(0,0,getSize().width-1,getSize().height-1);
         g.drawRect(1,1,getSize().width-3,getSize().height-3);
         
         // squares of checkerboard anc checkers
         for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
               if ( row % 2 == col % 2 )
                  g.setColor(Color.LIGHT_GRAY);
               else
                  g.setColor(Color.GRAY);
               g.fillRect(2 + col*20, 2 + row*20, 20, 20);
               switch (board.pieceAt(row,col)) {
               case CheckersData.RED:
                  g.setColor(Color.RED);
                  g.fillOval(4 + col*20, 4 + row*20, 15, 15);
                  break;
               case CheckersData.BLACK:
                  g.setColor(Color.BLACK);
                  g.fillOval(4 + col*20, 4 + row*20, 15, 15);
                  break;
               case CheckersData.RED_KING:
                  g.setColor(Color.RED);
                  g.fillOval(4 + col*20, 4 + row*20, 15, 15);
                  g.setColor(Color.WHITE);
                  g.drawString("K", 7 + col*20, 16 + row*20);
                  break;
               case CheckersData.BLACK_KING:
                  g.setColor(Color.BLACK);
                  g.fillOval(4 + col*20, 4 + row*20, 15, 15);
                  g.setColor(Color.WHITE);
                  g.drawString("K", 7 + col*20, 16 + row*20);
                  break;
               }
            }
         }
         
         // if game in progress, hilight legal moves      
         
         if (gameInProgress) {
               
            g.setColor(Color.cyan);
            for (int i = 0; i < legalMoves.length; i++) {
               g.drawRect(2 + legalMoves[i].fromCol*20, 2 + legalMoves[i].fromRow*20, 19, 19);
               g.drawRect(3 + legalMoves[i].fromCol*20, 3 + legalMoves[i].fromRow*20, 17, 17);
            }
            
            if (selectedRow >= 0) {
               g.setColor(Color.white);
               g.drawRect(2 + selectedCol*20, 2 + selectedRow*20, 19, 19);
               g.drawRect(3 + selectedCol*20, 3 + selectedRow*20, 17, 17);
               g.setColor(Color.green);
               for (int i = 0; i < legalMoves.length; i++) {
                  if (legalMoves[i].fromCol == selectedCol && legalMoves[i].fromRow == selectedRow) {
                     g.drawRect(2 + legalMoves[i].toCol*20, 2 + legalMoves[i].toRow*20, 19, 19);
                     g.drawRect(3 + legalMoves[i].toCol*20, 3 + legalMoves[i].toRow*20, 17, 17);
                  }
               }
            }
         }

      }  // end paintComponent()
      
      
      /**
       * respond to a user click on the board
       */
      public void mousePressed(MouseEvent evt) {
         if (gameInProgress == false)
            message.setText("Click \"New Game\" to start a new game.");
         else {
            int col = (evt.getX() - 2) / 20;
            int row = (evt.getY() - 2) / 20;
            if (col >= 0 && col < 8 && row >= 0 && row < 8)
               doClickSquare(row,col);
         }
      }
      
      
      public void mouseReleased(MouseEvent evt) { }
      public void mouseClicked(MouseEvent evt) { }
      public void mouseEntered(MouseEvent evt) { }
      public void mouseExited(MouseEvent evt) { }
      
      
   }  // end class Board
   
   
   
   /**
    * object that holds data about game of checkers.
    * knows what kind of piece is on each square of the checkerboard
    * RED moves "up" the board (i.e. row number decreases)
    * BLACK moves "down" the board (i.e. row number increases).
    * Methods are provided to return lists of available legal moves.
    */
   private static class CheckersData {
       
      // contants reperesenting possible contents of a square
      static final int
                EMPTY = 0,
                RED = 1,
                RED_KING = 2,
                BLACK = 3,
                BLACK_KING = 4;
      
      
      int[][] board;  // board[r][c] is the contents of row r, column c.  
      
      
      /**
       * create the board and set it up for a new game.
       */
      CheckersData() {
         board = new int[8][8];
         setUpGame();
      }
      
      
      /**
       * set up the board with checkers in position for the beginning of game
       */
      void setUpGame() {
         for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
               if ( row % 2 == col % 2 ) {
                  if (row < 3)
                     board[row][col] = BLACK;
                  else if (row > 4)
                     board[row][col] = RED;
                  else
                     board[row][col] = EMPTY;
               }
               else {
                  board[row][col] = EMPTY;
               }
            }
         }
      }  // end setUpGame()
      
      
      /**
       * return the contents of square in specified row and column.
       */
      int pieceAt(int row, int col) {
         return board[row][col];
      }
      
      /**
       * set the contents of the square in the specified row and column
       */
      void setPieceAt(int row, int col, int piece) {
         board[row][col] = piece;
      }
      
      
      /**
       * make the specified move
       */
      void makeMove(CheckersMove move) {
         makeMove(move.fromRow, move.fromCol, move.toRow, move.toCol);
      }
      
      
      /**
       * make the move from (fromRow,fromCol) to (toRow,toCol)
       */
      void makeMove(int fromRow, int fromCol, int toRow, int toCol) {
         board[toRow][toCol] = board[fromRow][fromCol];
         board[fromRow][fromCol] = EMPTY;
         if (fromRow - toRow == 2 || fromRow - toRow == -2) {
            // if move is jump,  remove jumped piece from the board.
            int jumpRow = (fromRow + toRow) / 2;  // row of jumped piece
            int jumpCol = (fromCol + toCol) / 2;  // col of jumped piece
            board[jumpRow][jumpCol] = EMPTY;
         }
         if (toRow == 0 && board[toRow][toCol] == RED)
            board[toRow][toCol] = RED_KING;
         if (toRow == 7 && board[toRow][toCol] == BLACK)
            board[toRow][toCol] = BLACK_KING;
      }
      
      /**
       * return an array containing all legal CheckersMoves
       * for specified player on current board
       */
      CheckersMove[] getLegalMoves(int player) {
         
         if (player != RED && player != BLACK)
            return null;
         
         int playerKing;  
         if (player == RED)
            playerKing = RED_KING;
         else
            playerKing = BLACK_KING;
         
         // moves stored in list
         ArrayList<CheckersMove> moves = new ArrayList<CheckersMove>();  
         
         /*  First, check for any possible jumps.  Look at each square on the board.
          If that square contains one of the player's pieces, look at a possible
          jump in each of the four directions from that square.  If there is 
          a legal jump in that direction, put it in the moves ArrayList.
          */
         
         for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
               if (board[row][col] == player || board[row][col] == playerKing) {
                  if (canJump(player, row, col, row+1, col+1, row+2, col+2))
                     moves.add(new CheckersMove(row, col, row+2, col+2));
                  if (canJump(player, row, col, row-1, col+1, row-2, col+2))
                     moves.add(new CheckersMove(row, col, row-2, col+2));
                  if (canJump(player, row, col, row+1, col-1, row+2, col-2))
                     moves.add(new CheckersMove(row, col, row+2, col-2));
                  if (canJump(player, row, col, row-1, col-1, row-2, col-2))
                     moves.add(new CheckersMove(row, col, row-2, col-2));
               }
            }
         }
         
         /*  If any jump moves were found, then the user must jump, so we don't 
          add any regular moves.  However, if no jumps were found, check for
          any legal regualar moves.  Look at each square on the board.
          If that square contains one of the player's pieces, look at a possible
          move in each of the four directions from that square.  If there is 
          a legal move in that direction, put it in the moves ArrayList.
          */
         
         if (moves.size() == 0) {
            for (int row = 0; row < 8; row++) {
               for (int col = 0; col < 8; col++) {
                  if (board[row][col] == player || board[row][col] == playerKing) {
                     if (canMove(player,row,col,row+1,col+1))
                        moves.add(new CheckersMove(row,col,row+1,col+1));
                     if (canMove(player,row,col,row-1,col+1))
                        moves.add(new CheckersMove(row,col,row-1,col+1));
                     if (canMove(player,row,col,row+1,col-1))
                        moves.add(new CheckersMove(row,col,row+1,col-1));
                     if (canMove(player,row,col,row-1,col-1))
                        moves.add(new CheckersMove(row,col,row-1,col-1));
                  }
               }
            }
         }
         
         /* If no legal moves have been found, return null.  Otherwise, create
          an array just big enough to hold all the legal moves, copy the
          legal moves from the ArrayList into the array, and return the array. */
         
         if (moves.size() == 0)
            return null;
         else {
            CheckersMove[] moveArray = new CheckersMove[moves.size()];
            for (int i = 0; i < moves.size(); i++)
               moveArray[i] = moves.get(i);
            return moveArray;
         }
         
      }  // end getLegalMoves
      
      
      /**
       * Return a list of the legal jumps that the specified player can
       * make starting from the specified row and column.  If no such
       * jumps are possible, null is returned.  The logic is similar
       * to the logic of the getLegalMoves() method.
       */
      CheckersMove[] getLegalJumpsFrom(int player, int row, int col) {
         if (player != RED && player != BLACK)
            return null;
         int playerKing;
         if (player == RED)
            playerKing = RED_KING;
         else
            playerKing = BLACK_KING;
         ArrayList<CheckersMove> moves = new ArrayList<CheckersMove>(); 
         if (board[row][col] == player || board[row][col] == playerKing) {
            if (canJump(player, row, col, row+1, col+1, row+2, col+2))
               moves.add(new CheckersMove(row, col, row+2, col+2));
            if (canJump(player, row, col, row-1, col+1, row-2, col+2))
               moves.add(new CheckersMove(row, col, row-2, col+2));
            if (canJump(player, row, col, row+1, col-1, row+2, col-2))
               moves.add(new CheckersMove(row, col, row+2, col-2));
            if (canJump(player, row, col, row-1, col-1, row-2, col-2))
               moves.add(new CheckersMove(row, col, row-2, col-2));
         }
         if (moves.size() == 0)
            return null;
         else {
            CheckersMove[] moveArray = new CheckersMove[moves.size()];
            for (int i = 0; i < moves.size(); i++)
               moveArray[i] = moves.get(i);
            return moveArray;
         }
      }  // end getLegalMovesFrom()
      
      
      /**
       * This is called by the two previous methods to check whether the
       * player can legally jump from (r1,c1) to (r3,c3).  
       */
      private boolean canJump(int player, int r1, int c1, int r2, int c2, int r3, int c3) {
         
         if (r3 < 0 || r3 >= 8 || c3 < 0 || c3 >= 8)
            return false;  
         
         if (board[r3][c3] != EMPTY)
            return false; 
         
         if (player == RED) {
            if (board[r1][c1] == RED && r3 > r1)
               return false; 
            if (board[r2][c2] != BLACK && board[r2][c2] != BLACK_KING)
               return false;  
            return true;  
         }
         else {
            if (board[r1][c1] == BLACK && r3 < r1)
               return false; 
            if (board[r2][c2] != RED && board[r2][c2] != RED_KING)
               return false;
            return true; 
         }
         
      }  // end canJump()
      
      
      /**
       * This is called by the getLegalMoves() method to determine whether
       * the player can legally move from (r1,c1) to (r2,c2).  It is
       * assumed that (r1,r2) contains one of the player's pieces and
       * that (r2,c2) is a neighboring square.
       */
      private boolean canMove(int player, int r1, int c1, int r2, int c2) {
         
         if (r2 < 0 || r2 >= 8 || c2 < 0 || c2 >= 8)
            return false;  // (r2,c2) is off the board.
         
         if (board[r2][c2] != EMPTY)
            return false;  // (r2,c2) already contains a piece.
         
         if (player == RED) {
            if (board[r1][c1] == RED && r2 > r1)
               return false;  // Regualr red piece can only move down.
            return true;  // The move is legal.
         }
         else {
            if (board[r1][c1] == BLACK && r2 < r1)
               return false;  // Regular black piece can only move up.
            return true;  // The move is legal.
         }
         
      }  // end canMove()
      
      
   } // end class CheckersData
   
   
} // end class Checkers

