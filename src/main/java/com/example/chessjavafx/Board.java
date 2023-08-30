package com.example.chessjavafx;

import java.util.Arrays;
/**
 *Board class handles all the interactions on the board, Such as moving pieces, checking for checks and checkmates
 * @author owg5 , jmb26
 * @version 1.0
 */
public class Board {
   Piece[][] board;

   /**
    * Constructor for board class with a custom board state
    *
    * @param board This is the board state that we want it to start with. It's an array of piece objects
    */
   public Board(Piece[][] board) {
      this.board = board;
   }

   /**
    * constructor for board class - makes a default board state
    */
   public Board() {
      board = new Piece[8][8];
      for (int i = 0; i < 8; i++) {
         board[1][i] = new Pawn(i, 1, WhiteOrBlack.BLACK);
         board[6][i] = new Pawn(i, 6, WhiteOrBlack.WHITE);
      }
      board[0] = new Piece[]{new Rook(0, 0, WhiteOrBlack.BLACK), new Knight(1, 0, WhiteOrBlack.BLACK), new Bishop(2, 0, WhiteOrBlack.BLACK), new Queen(3, 0, WhiteOrBlack.BLACK), new King(4, 0, WhiteOrBlack.BLACK), new Bishop(5, 0, WhiteOrBlack.BLACK), new Knight(6, 0, WhiteOrBlack.BLACK), new Rook(7, 0, WhiteOrBlack.BLACK)};
      board[7] = new Piece[]{new Rook(0, 7, WhiteOrBlack.WHITE), new Knight(1, 7, WhiteOrBlack.WHITE), new Bishop(2, 7, WhiteOrBlack.WHITE), new Queen(3, 7, WhiteOrBlack.WHITE), new King(4, 7, WhiteOrBlack.WHITE), new Bishop(5, 7, WhiteOrBlack.WHITE), new Knight(6, 7, WhiteOrBlack.WHITE), new Rook(7, 7, WhiteOrBlack.WHITE)};
   }

   /**
    * sets a specific square on the board to be a new piece
    *
    * @param rank         the type of piece
    * @param x            x coordinate that the new piece will be at
    * @param y            y coordinate that the new piece will be at
    * @param whiteOrBlack the colour of the piece
    */
   public void setPiece(Rank rank, int x, int y, WhiteOrBlack whiteOrBlack) {
      if (x < 0 || x > 7 || y < 0 || y > 7) {
         return;
      }
      switch (rank) {
         case ROOK -> board[y][x] = new Rook(x, y, whiteOrBlack);
         case PAWN -> board[y][x] = new Pawn(x, y, whiteOrBlack);
         case BISHOP -> board[y][x] = new Bishop(x, y, whiteOrBlack);
         case KING -> board[y][x] = new King(x, y, whiteOrBlack);
         case KNIGHT -> board[y][x] = new Knight(x, y, whiteOrBlack);
         case QUEEN -> board[y][x] = new Queen(x, y, whiteOrBlack);
         default -> throw new IllegalStateException("Unexpected value: " + rank);
      }
   }

   /**
    * getter for a piece at a certain coordinate
    *
    * @param x x coordinate
    * @param y y coordinate
    * @return the piece in question
    */
   public Piece getPiece(int x, int y) {
      return board[y][x];
   }

   /**
    * getter for the 2d array of pieces
    *
    * @return board as a 2d array of pieces
    */
   public Piece[][] getBoard() {
      return board;
   }

   /**
    * sets the board to be a certain array of pieces
    *
    * @param board 2d array of pieces
    */
   public void setBoard(Piece[][] board) {
      this.board = board;
   }

   /**
    * code to move piece at coordinate startx,starty to endx,endy
    *
    * @param startX start x coordinate of the piece
    * @param startY start y coordinate of the piece
    * @param endX   end x coordinate of the piece
    * @param endY   end y coordinate of the piece
    */
   public void movePiece(int startX, int startY, int endX, int endY) {
      if (startX > 7 || startY > 7 || endX > 7 || endY > 7 || startX < 0 || startY < 0 || endX < 0 || endY < 0) {
         return;
      }
      if (board[startY][startX] == null) {
         return;
      }
      if (!this.getPiece(startX, startY).generateMoves(this)[endY][endX]) {
         return;
      }
      switch (board[startY][startX].getRank()) {
         case KING -> {
            if (startX - endX == 2) { //if its queen side castling
               board[endY][endX] = board[startY][startX];
               board[endY][endX].setHasMoved(true);
               board[endY][endX].setPos(endX, endY);
               board[startY][startX] = null;
               board[startY][startX - 4].setHasMoved(true);
               board[endY][endX + 1] = board[startY][startX - 4];
               board[endY][endX + 1].setPos(endX+1,endY);
               board[startY][startX - 4] = null; //logic to get the left side rook since that rook is 4 away
            } else if (startX - endX == -2) {//if its king side castling
               board[endY][endX] = board[startY][startX];
               board[endY][endX].setHasMoved(true);
               board[endY][endX].setPos(endX, endY);
               board[startY][startX] = null;
               board[startY][startX + 3].setHasMoved(true);
               board[endY][endX - 1] = board[startY][startX + 3];
               board[endY][endX - 1].setPos(endX-1,endY);
               board[startY][startX + 3] = null; //logic to get the right side rook since that rook is 3 away
            } else {
               defaultMove(startX, startY, endX, endY); // else just move it like any other piece
            }
         }
         case PAWN -> {
            int fwd;
            if (board[startY][startX].getWhiteOrBlack() == WhiteOrBlack.WHITE) { // gets the forward direction for said pawn
               fwd = -1;
            } else {
               fwd = 1;
            }
            if (startX != endX && board[endY][endX] == null) {//does en passant
               board[endY][endX] = board[startY][startX];
               board[endY][endX].setHasMoved(true);
               board[endY][endX].setPos(endX, endY);
               board[endY - fwd][endX] = null;
               board[startY][startX] = null;

            } else if (endY - startY == 2 * fwd) { //does double move
               board[endY][endX] = board[startY][startX];
               board[endY][endX].setHasMoved(true);
               board[endY][endX].setHasDoubleMoved(true);
               board[endY][endX].setPos(endX, endY);
               board[startY][startX] = null;
            } else {
               defaultMove(startX, startY, endX, endY);//else move it like a normal piece
            }
         }
         default -> defaultMove(startX, startY, endX, endY);// normal piece movement
      }
      for (Piece[] row :board) {
         for (Piece piece:row){
            if (piece != null && piece.getRank()==Rank.PAWN&&piece.getWhiteOrBlack()!=board[endY][endX].getWhiteOrBlack()){
               piece.setHasDoubleMoved(false);
            }
         }

      }
   }

   /**
    * the default move preformed by every piece when it is not doing a special case
    *
    * @param startX start x coordinate of the piece
    * @param startY start y coordinate of the piece
    * @param endX   end x coordinate of the piece
    * @param endY   end y coordinate of the piece
    */
   private void defaultMove(int startX, int startY, int endX, int endY) {
      board[endY][endX] = board[startY][startX];
      board[endY][endX].setHasMoved(true);
      board[endY][endX].setPos(endX, endY);
      board[startY][startX] = null;
   }

   /**
    * checks for a check on a certain colored king on any array of pieces (used in generate moves)
    *
    * @param whiteOrBlack holds the color of the side to check a check for
    * @param boardCopy holds a copy of the game board to be checked o
    * @return a true if a check situation, false if not
    */
   public boolean checkForCheck(WhiteOrBlack whiteOrBlack, Piece[][] boardCopy) {
      Piece king = null;
      int kingX = 0;
      int kingY = 0;
      for (int i = 0; i < 8; i++) {
         for (int j = 0; j < 8; j++) {
            Piece piece = boardCopy[i][j];
            if (piece != null) {
               if (piece.getRank() == Rank.KING && piece.getWhiteOrBlack() == whiteOrBlack) {
                  king = piece;
                  kingX = j;
                  kingY = i;
                  break;
               }
            }
         }
         if (king != null) {
            break;
         }
      }
      int[][] vectors = {{0, 1}, {1, 0}, {-1, 0}, {0, -1}};
      for (int[] vector : vectors) {
         int x = kingX;
         int y = kingY;
         boolean pass = false;
         x = x + vector[0];
         y = y + vector[1];
         Piece workingPiece;
         if ((x >= 0 && x <= 7) && (y >= 0 && y <= 7)) {
            workingPiece = boardCopy[y][x];

            if (workingPiece != null) {
               pass = true;
               if (workingPiece.getWhiteOrBlack() != whiteOrBlack) {
                  Rank rank = workingPiece.getRank();
                  if (rank == Rank.ROOK || rank == Rank.QUEEN || rank == Rank.KING) {
                     return true;
                  }
               }
            }
         }
         while (!pass) {
            x = x + vector[0];
            y = y + vector[1];
            if ((x >= 0 && x <= 7) && (y >= 0 && y <= 7)) {
               workingPiece = boardCopy[y][x];
               if (workingPiece != null) {
                  pass = true;
                  if (workingPiece.getWhiteOrBlack() != whiteOrBlack) {
                     Rank rank = workingPiece.getRank();
                     if (rank == Rank.ROOK || rank == Rank.QUEEN) {
                        return true;
                     }
                  }
               }
            } else {
               pass = true;
            }
         }
      }
      vectors = new int[][]{{1, 1}, {1, -1}, {-1, 1}, {-1, -1}};
      for (int[] vector : vectors) {
         int x = kingX;
         int y = kingY;
         boolean pass = false;
         x = x + vector[0];
         y = y + vector[1];
         Piece workingPiece;
         if ((x >= 0 && x <= 7) && (y >= 0 && y <= 7)) {
            workingPiece = boardCopy[y][x];
            if (workingPiece != null) {
               pass = true;
               if (workingPiece.getWhiteOrBlack() != whiteOrBlack) {
                  Rank rank = workingPiece.getRank();
                  if (rank == Rank.BISHOP || rank == Rank.QUEEN || rank == Rank.KING) {
                     return true;
                  }
                  if (rank == Rank.PAWN) {
                     int fwd;
                     if (whiteOrBlack == WhiteOrBlack.WHITE) {
                        fwd = -1;
                     } else {
                        fwd = 1;
                     }
                     if (y - fwd == king.getPos()[1]) {
                        return true;
                     }
                  }
               }
            }
         }
         while (!pass) {
            x = x + vector[0];
            y = y + vector[1];
            if ((x >= 0 && x <= 7) && (y >= 0 && y <= 7)) {
               workingPiece = boardCopy[y][x];
               if (workingPiece != null) {
                  pass = true;
                  if (workingPiece.getWhiteOrBlack() != whiteOrBlack) {
                     Rank rank = workingPiece.getRank();
                     if (rank == Rank.BISHOP || rank == Rank.QUEEN) {
                        return true;
                     }
                  }
               }
            } else {
               pass = true;
            }
         }
      }
      vectors = new int[][]{{-2, -1}, {-1, -2}, {1, -2}, {2, -1}, {2, 1}, {1, 2}, {-1, 2}, {-2, 1}};
      for (int[] vector : vectors) {
            int x = kingX;
            int y = kingY;
            x = x + vector[0];
            y = y + vector[1];
            Piece workingPiece;
            if ((x >= 0 && x <= 7) && (y >= 0 && y <= 7)) {
               workingPiece = boardCopy[y][x];
               if (workingPiece != null) {
                  if (workingPiece.getWhiteOrBlack() != whiteOrBlack) {
                     Rank rank = workingPiece.getRank();
                     if (rank == Rank.KNIGHT) {
                        return true;
                     }
                  }
               }
            }
      }
      return false;
   }

   /**
    * checks if the specified color is in checkmate
    *
    * @param whiteOrBlack specified color
    * @return true if checkmate found, false if not
    */
   public boolean checkForMate(WhiteOrBlack whiteOrBlack) {
      boolean[][] falseSample = new boolean[8][8];
      for (Piece[] pieces : board) {
         for (Piece piece : pieces) {
            if (piece != null) {
               if (piece.getWhiteOrBlack() == whiteOrBlack) {
                  if (!(Arrays.deepEquals(piece.generateMoves(this), falseSample))) {
                     return false;
                  }
               }
            }
         }
      }
      return true;
   }

   /**
    * returns a deep copy of the 2d array of pieces
    *
    * @return a piece array that is a deep copy of the board piece array
    */
   public Piece[][] getBoardCopy() {//this function is so we get a discrete copy of board and arnt working on the original
      Piece[][] tmpBoard = new Piece[8][8];
      for (int i = 0; i < 8; i++) {
         for (int j = 0; j < 8; j++) {
            if (board[i][j] == null) {
               tmpBoard[i][j] = null;
            }else {
               switch (board[i][j].getRank()) {
                  case ROOK -> {
                     tmpBoard[i][j] = new Rook(j, i, board[i][j].getWhiteOrBlack());
                     tmpBoard[i][j].setHasMoved(board[i][j].getHasMoved());
                     tmpBoard[i][j].setHasDoubleMoved((board[i][j].getHasDoubleMoved()));
                  }
                  case PAWN -> {
                     tmpBoard[i][j] = new Pawn(j, i, board[i][j].getWhiteOrBlack());
                     tmpBoard[i][j].setHasMoved(board[i][j].getHasMoved());
                     tmpBoard[i][j].setHasDoubleMoved((board[i][j].getHasDoubleMoved()));
                  }
                  case BISHOP -> {
                     tmpBoard[i][j] = new Bishop(j, i, board[i][j].getWhiteOrBlack());
                     tmpBoard[i][j].setHasMoved(board[i][j].getHasMoved());
                     tmpBoard[i][j].setHasDoubleMoved((board[i][j].getHasDoubleMoved()));
                  }
                  case KING -> {
                     tmpBoard[i][j] = new King(j, i, board[i][j].getWhiteOrBlack());
                     tmpBoard[i][j].setHasMoved(board[i][j].getHasMoved());
                     tmpBoard[i][j].setHasDoubleMoved((board[i][j].getHasDoubleMoved()));
                  }
                  case KNIGHT -> {
                     tmpBoard[i][j] = new Knight(j, i, board[i][j].getWhiteOrBlack());
                     tmpBoard[i][j].setHasMoved(board[i][j].getHasMoved());
                     tmpBoard[i][j].setHasDoubleMoved((board[i][j].getHasDoubleMoved()));
                  }
                  case QUEEN -> {
                     tmpBoard[i][j] = new Queen(j, i, board[i][j].getWhiteOrBlack());
                     tmpBoard[i][j].setHasMoved(board[i][j].getHasMoved());
                     tmpBoard[i][j].setHasDoubleMoved((board[i][j].getHasDoubleMoved()));
                  }
               }
            }
         }
      }
      return tmpBoard;
   }

   /**
    * Function that outputs the board in a readable way if called to print
    * @return a string representation of the board
    */
   @Override
   public String toString() {
      StringBuilder stringBuilder = new StringBuilder();

      for (int i = 0; i < 8; i++) {
         stringBuilder.append("\n");
         stringBuilder.append("[");
         for (int j = 0; j < 8; j++) {
            if (board[i][j] == null) {
               stringBuilder.append("null");
            } else {
               stringBuilder.append(board[i][j].toString());
            }
            stringBuilder.append(",");
         }
         stringBuilder.delete(stringBuilder.length() - 1, stringBuilder.length());

         stringBuilder.append("]");
      }
      return stringBuilder.toString();
   }
}