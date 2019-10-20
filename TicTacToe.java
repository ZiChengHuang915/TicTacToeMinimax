import java.util.Scanner;
import javax.swing.JOptionPane;
import java.awt.GridLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.ImageIcon;
import java.awt.Dimension;
import java.awt.Color;
import java.awt.event.*;
import java.awt.Font;
import javax.swing.UIManager;

public class TicTacToe extends JFrame {
   static int nextRow = -1, nextCol = -1;
   
   public static void resetBoard(byte board[][]) {
      for (int i = 0; i < board.length; i++) {
         for (int j = 0; j < board[i].length; j++) {
            board[i][j] = 8;
         }
      }
   }
   
   public static void printBoard(byte board[][]) {
      for (int i = 0; i < board.length; i++) {
         for (int j = 0; j < board[i].length; j++) {
            System.out.print(board[i][j]);
         }
         System.out.println();
      }
   }
   
   public static String checkWinner(byte board[][]) {
      int sum;
           
      sum = board[0][0] + board[0][1] + board[0][2];
      if (sum == 3) {
         return "X";
      } else if (sum == 0) {
         return "O";
      }
      
      sum = board[1][0] + board[1][1] + board[1][2];
      if (sum == 3) {
         return "X";
      } else if (sum == 0) {
         return "O";
      }
      
      sum = board[2][0] + board[2][1] + board[2][2];
      if (sum == 3) {
         return "X";
      } else if (sum == 0) {
         return "O";
      }
      
      sum = board[0][0] + board[1][0] + board[2][0];
      if (sum == 3) {
         return "X";
      } else if (sum == 0) {
         return "O";
      }
      
      sum = board[0][1] + board[1][1] + board[2][1];
      if (sum == 3) {
         return "X";
      } else if (sum == 0) {
         return "O";
      }
      
      sum = board[0][2] + board[1][2] + board[2][2];
      if (sum == 3) {
         return "X";
      } else if (sum == 0) {
         return "O";
      }
      
      sum = board[0][0] + board[1][1] + board[2][2];
      if (sum == 3) {
         return "X";
      } else if (sum == 0) {
         return "O";
      }
      
      sum = board[2][0] + board[1][1] + board[0][2];
      if (sum == 3) {
         return "X";
      } else if (sum == 0) {
         return "O";
      }
      
      boolean hasPiece = true;
      for (int i = 0; i < board.length; i++) {
         for (int j = 0; j < board[i].length; j++) {
            if (board[i][j] == 8) {
               hasPiece = false;
            }
         }
      }
      if (hasPiece) {
         return "Draw";
      }
      
      return "8";
   } 
   
   public static int countPiece(byte board[][], int player) {
      int count = 0;
      for (int i = 0; i < board.length; i++) {
         for (int j = 0; j < board[i].length; j++) {
            if (board[i][j] == player) {
               count++;
            }
         }
      }
      return count;
   }
   
   public static String nextPlayer(byte board[][]) {
      if (countPiece(board, 1) == countPiece(board, 0)) {
         return "X";
      } else {
         return "O";
      }
   }
   
   public static boolean isValidMove(byte board[][], int row, int col) {
      if (board[row][col] == 8) {
         return true;
      }
      return false;
   }
   
   public static void updateBoard(byte board[][], int row, int col, int move) {
      board[row][col] = (byte) move;
   }
   
   public static int minimax(byte board[][], boolean isMaximizer) {
      if (checkWinner(board).equals("X")) {
         return 1;
      } else if (checkWinner(board).equals("O")) {
         return -1;
      } else if (checkWinner(board).equals("Draw")) {
         return 0;
      }
      
      byte tempBoard[][] = new byte[3][3];
      for (int i = 0; i < 3; i++) {
         for (int j = 0; j < 3; j++) {
            tempBoard[i][j] = board[i][j];
         }
      }
      
      if (isMaximizer) {
         int bestValue = -10;
         for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
               if (isValidMove(board, i, j)) {              
                  updateBoard(tempBoard, i, j, 1);
                  bestValue = Math.max(bestValue, minimax(tempBoard, !isMaximizer));  
                  updateBoard(tempBoard, i, j, 8);            
               }
            }
         }
         
         return bestValue;
      } else {
         int bestValue = 10;
         for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
               if (isValidMove(board, i, j)) {              
                  updateBoard(tempBoard, i, j, 0);
                  bestValue = Math.min(bestValue, minimax(tempBoard, !isMaximizer)); 
                  updateBoard(tempBoard, i, j, 8);                 
               }
            }
         }
         
         return bestValue;
      }     
   }
   
   public static String findBestMove(byte board[][], boolean isMax) {
      String bestMove = "";
      int bestMoveValueSoFar;
      
      if (isMax) {
         bestMoveValueSoFar = -10;
      } else {
         bestMoveValueSoFar = 10;
      }
      
      byte tempBoard[][] = new byte[3][3];
      for (int i = 0; i < 3; i++) {
         for (int j = 0; j < 3; j++) {
            tempBoard[i][j] = board[i][j];
         }
      }
      
      for (int i = 0; i < 3; i++) {
         for (int j = 0; j < 3; j++) {
            if (isValidMove(board, i, j)) {
               if (isMax) {
                  updateBoard(tempBoard, i, j, 1);
                  if (minimax(tempBoard, false) > bestMoveValueSoFar) {
                     bestMoveValueSoFar = minimax(tempBoard, false); // the false is for is minimizer, because the maximizer has just played a virtual move
                     bestMove = String.valueOf(i) + String.valueOf(j);
                  //System.out.println(bestMove);
                  }
                  updateBoard(tempBoard, i, j, 8);
               } else {
                  updateBoard(tempBoard, i, j, 0);
                  if (minimax(tempBoard, true) < bestMoveValueSoFar) {
                     bestMoveValueSoFar = minimax(tempBoard, true); // the true is for is maximizer, because the minimizer has just played a virtual move
                     bestMove = String.valueOf(i) + String.valueOf(j);
                  //System.out.println(bestMove);
                  }
                  updateBoard(tempBoard, i, j, 8);
               }
            }
         }
      }
      
      return bestMove;
   }
   
   public static void main(String[] args) throws InterruptedException {  
      JFrame window = new JFrame();
      window.getContentPane().setBackground(Color.BLACK);
      window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      window.setSize(400, 400);
      window.setLocationRelativeTo(null);
      window.setVisible(true);
      window.setTitle("Tic Tac Toe");
      window.setLayout(new GridLayout(3, 3, 10, 10));
       
      // Initializing buttons      
      JButton[][] square = new JButton[3][3];
      for (int i = 0; i < 3; i++) {
         for (int j = 0; j < 3; j++) {
            square[i][j] = new JButton();
            square[i][j].setText("");
            square[i][j].setPreferredSize(new Dimension(50, 50));
            square[i][j].setFont(new Font("Arial", Font.BOLD, 60));
            window.getContentPane().add(square[i][j]);       
         }
      }
      
      // Adding button functionalities      
      for (int i = 0; i < 3; i++) {
         for (int j = 0; j < 3; j++) {
            final int r = i, c = j;    
            ActionListener listener = 
               new ActionListener (){
                  public void actionPerformed(ActionEvent e){
                     TicTacToe.nextRow = r;
                     TicTacToe.nextCol = c;
                  }
               }; 
            square[i][j].addActionListener(listener);         
         }
      }
      
      // Refreshing window
      window.revalidate();
      
      UIManager.put("Button.disabledText", Color.BLACK);
      UIManager.put("Button.foreground", Color.BLACK);
      
      String ai = JOptionPane.showInputDialog("Type: \n1 for AI\n2 for multiplayer\n3 to see AI play against each other");
      while (!ai.equals("1") && !ai.equals("2") && !ai.equals("3")) {
         ai = JOptionPane.showInputDialog("Invalid Option! \nType: \n1 for AI\n2 for multiplayer\n3 to see AI play against each other");
      }
           
      // X is 1
      // O is 0
      // 8 is blank
      Scanner sc = new Scanner(System.in);
      byte board[][] = new byte[3][3];
      boolean shouldRestart = true;
         
      resetBoard(board);
      //printBoard(board);
      
      // Game loop ////////////////////////////////////////////////////////
      while (shouldRestart) {
         while (checkWinner(board).equals("8")) {
         // System.out.println("It is " + nextPlayer(board) + "'s turn:");
            long startTime = System.nanoTime();
            if (ai.equals("1")) {
               if (nextPlayer(board).equals("X")) {
                  String nextMove = findBestMove(board, true);
                  nextRow = Integer.parseInt(nextMove.substring(0, 1));
                  nextCol = Integer.parseInt(nextMove.substring(1, 2));
                  updateBoard(board, nextRow, nextCol, 1);
                  square[nextRow][nextCol].setText("X"); 
                  square[nextRow][nextCol].setEnabled(false);
                  System.out.println("Time elapsed for thinking: " + ((System.nanoTime() - startTime)/1000000) + "ms");                                   
               } else {
                  while (nextRow == -1 && nextCol == -1) {
                     Thread.sleep(50);
                  }
               
                  updateBoard(board, nextRow, nextCol, 0);
                  square[nextRow][nextCol].setText("O");
                  square[nextRow][nextCol].setEnabled(false); 
               }
                           
               nextRow = -1;
               nextCol = -1;  
            } else if (ai.equals("2")){
               while (nextRow == -1 && nextCol == -1) {
                  Thread.sleep(50);
               }
            
            // System.out.println(nextRow + " " + nextCol);
            
               if (nextPlayer(board).equals("X")) {
                  updateBoard(board, nextRow, nextCol, 1);
                  square[nextRow][nextCol].setText("X");  
                  square[nextRow][nextCol].setEnabled(false);      
               } else {
                  updateBoard(board, nextRow, nextCol, 0);
                  square[nextRow][nextCol].setText("O");
                  square[nextRow][nextCol].setEnabled(false); 
               }
            
            // printBoard(board);    
               nextRow = -1;
               nextCol = -1;
            } else if (ai.equals("3")) {               
               if (nextPlayer(board).equals("X")) {
                  String nextMove = findBestMove(board, true);
                  nextRow = Integer.parseInt(nextMove.substring(0, 1));
                  nextCol = Integer.parseInt(nextMove.substring(1, 2));
                  updateBoard(board, nextRow, nextCol, 1);
                  square[nextRow][nextCol].setText("X"); 
                  square[nextRow][nextCol].setEnabled(false);
                  System.out.println("Time elapsed for thinking: " + ((System.nanoTime() - startTime)/1000000) + "ms");                                   
               } else {
                  String nextMove = findBestMove(board, false);
                  nextRow = Integer.parseInt(nextMove.substring(0, 1));
                  nextCol = Integer.parseInt(nextMove.substring(1, 2));
                  updateBoard(board, nextRow, nextCol, 0);
                  square[nextRow][nextCol].setText("O"); 
                  square[nextRow][nextCol].setEnabled(false);
                  System.out.println("Time elapsed for thinking: " + ((System.nanoTime() - startTime)/1000000) + "ms"); 
               }
               Thread.sleep(500);
            }
         }
      
         if (checkWinner(board).equals("Draw")) {
            System.out.println("It is a draw!");
            JOptionPane.showMessageDialog(null, "It's a draw!", "Game Over", JOptionPane.PLAIN_MESSAGE);
         } else if (checkWinner(board).equals("X")) {
            System.out.println("X has won!");
            JOptionPane.showMessageDialog(null, "You're bad!", "Game Over", JOptionPane.PLAIN_MESSAGE);
         } else {
            System.out.println("O has won!");
            JOptionPane.showMessageDialog(null, "You're bad!", "Game Over", JOptionPane.PLAIN_MESSAGE);
         }
         String restart = JOptionPane.showInputDialog("Press: \n1 to restart\n2 to terminate");
         while (!restart.equals("1") && !restart.equals("2")) {
            restart = JOptionPane.showInputDialog("Invalid Option! \nPress: \n1 to restart\n2 to terminate");
         }
         
         if (restart.equals("1")) {
            shouldRestart = true;
            resetBoard(board);
            for (int i = 0; i < 3; i++) {
               for (int j = 0; j < 3; j++) {        
                  square[i][j].setText("");
                  square[i][j].setEnabled(true);     
               }
            }
         } else {
            shouldRestart = false;
         }
      }
      System.exit(0);
   }
}

 // String fn = JOptionPane.showInputDialog("Enter first number");
//       String sn = JOptionPane.showInputDialog("Enter second number");
//       
//       int num = Integer.parseInt(fn) + Integer.parseInt(sn);
//       JOptionPane.showMessageDialog(null, "Sum is " + num, "Title", JOptionPane.PLAIN_MESSAGE);

         // nextRow = sc.nextInt();
//          nextCol = sc.nextInt();
        //  sc.nextLine();
         
         // while (!isValidMove(board, nextRow, nextCol)) {
//             System.out.println("Invalid move.");
//             System.out.println("It is still " + nextPlayer(board) + "'s turn:");
//             System.out.println(nextRow + " " + nextCol);
//             // nextRow = sc.nextInt();
// //             nextCol = sc.nextInt();
//              sc.nextLine();
//          }


   



