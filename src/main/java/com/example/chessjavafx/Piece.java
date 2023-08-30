package com.example.chessjavafx;

import java.util.Arrays;
/**
 * The piece class is the abstract piece that all other pieces extends it also has some code that is reused by different pieces
 * @author owg5 , jmb26
 * @version 1.0
 */
public abstract class Piece {
   protected int[] pos;
   protected WhiteOrBlack whiteOrBlack;
   protected Rank rank;
   boolean hasMoved;
   boolean hasDoubleMoved;

   /**
    * returns the moves the piece can make by default an array of false
    *
    * @param board The board we are working on
    * @return a blank board since it is only in the abstract class
    */
   public boolean[][] generateMoves(Board board) {
      return new boolean[8][8];
   }

   /**
    * gets if the piece has moved
    *
    * @return returns whether the piece has moved
    */
   public boolean getHasMoved() {
      return hasMoved;
   }

   /**
    * sets whether the piece has moved
    *
    * @param hasMoved whether the piece has moved
    */
   public void setHasMoved(boolean hasMoved) {
      this.hasMoved = hasMoved;
   }

   /**
    * gets whether a piece has just double moved (used for pawns)
    *
    * @return boolean of whether they have or not
    */
   public boolean getHasDoubleMoved() {
      return hasDoubleMoved;
   }

   /**
    * sets whether a piece has just double moved (used for pawns)
    *
    * @param hasDoubleMoved whether they have double moved
    */
   public void setHasDoubleMoved(boolean hasDoubleMoved) {
      this.hasDoubleMoved = hasDoubleMoved;
   }

   /**
    * getter for the pieces position
    *
    * @return 2d array of the x and y coordinates
    */
   public int[] getPos() {
      return pos;
   }

   /**
    * generates whether the pieces position on the board is valid
    *
    * @param newX  where the piece is trying to get to x coordinate
    * @param newY  where the piece is trying to get to y coordinate
    * @param board the board
    * @return returns
    */
   protected boolean validPosForPiece(int newX, int newY, Board board) {
      boolean validPos;
      Piece[][] tmpBoard = (board.getBoardCopy());
      tmpBoard[newY][newX] = tmpBoard[this.pos[1]][this.pos[0]];
      tmpBoard[newY][newX].setPos(newX,newY);
      tmpBoard[this.pos[1]][this.pos[0]] = null;
      validPos = (!(board.checkForCheck(this.whiteOrBlack, tmpBoard)));
      return validPos;
   }

   /**
    * setter for the position using x and y instead of an array of ints
    *
    * @param x the x cord of the piece
    * @param y the y cord of the piece
    */
   public void setPos(int x, int y) {
      int[] pos = {x, y};
      this.pos = pos;
   }
//saveGameNameSavedUnderPanelId
   //saveGameSavedNameId

   /**
    * getter for the piece color
    * @return the enum of the piece color
    */
   public WhiteOrBlack getWhiteOrBlack() {
      return whiteOrBlack;
   }

   /**
    * setter for the piece color
    *
    * @param whiteOrBlack The enum of whether the piece color
    */
   public void setWhiteOrBlack(WhiteOrBlack whiteOrBlack) {
      this.whiteOrBlack = whiteOrBlack;
   }

   /**
    * getter for the pieces rank
    *
    * @return pieces type
    */
   public Rank getRank() {
      return rank;
   }

   /**
    * sets the pieces rank
    *
    * @param rank pieces type that we want to set it to
    */
   public void setRank(Rank rank) {
      this.rank = rank;
   }

   /**
    * gets the moves a piece can move given a set of movement vectors (directions it can move)
    *
    * @param piece   the piece that we are referring to
    * @param board   the board we are looking at
    * @param vectors the movement vectors
    * @return a 2d array of true values where the piece can move and false where it cant
    */
   public boolean[][] generateMovesMany(Piece piece, Board board, int[][] vectors) {//generic generate moves that is used by the rook bishop and queen
      boolean[][] validMoves = new boolean[8][8];
      boolean checkOnBoard = false;
      if ((board.checkForCheck(piece.whiteOrBlack, board.getBoardCopy()))) {
         checkOnBoard = true;
      }
      for (int[] vector : vectors) {
         int x = piece.pos[0];
         int y = piece.pos[1];
         boolean pass = false;// whether the loop should stop
         boolean first = true;// whether it's the first time the loop is being run
         while (!pass) {
            x = x + vector[0];
            y = y + vector[1];//get the next coordinates in that movement vector
            if ((x >= 0 && x <= 7) && (y >= 0 && y <= 7)) {//checking if it is withing the board
               if (board.getPiece((x), (y)) == null) {
                  if (first) {
                     if (validPosForPiece(x, y, board)) {//if making that move wouldn't put it in check
                        validMoves[y][x] = true;
                     }
                  } else {
                     if (validMoves[y - vector[1]][x - vector[0]]) { // if the last move was true checked in that direction
                        validMoves[y][x] = true;
                     } else {
                        pass = true;
                     }
                  }
               } else {
                  if (board.getPiece((x), (y)).getWhiteOrBlack() != piece.whiteOrBlack) { // if it is a checking a piece it could potentially take
                     if (first) {
                        if (validPosForPiece(x, y, board)) {
                           validMoves[y][x] = true;
                           pass = true;
                        } else {
                           pass = true;
                        }
                     } else {
                        if (validMoves[y - vector[1]][x - vector[0]]) {
                           validMoves[y][x] = true;
                        }
                        pass = true;
                     }
                  } else {
                     pass = true;
                  }
               }
            } else {
               pass = true;
            }
            if (!checkOnBoard) { //if not in check then after first move do different
               first = false;
            }
         }
      }
      return validMoves;
   }

   @Override
   /**
    * basic to string
    */
   public String toString() {
      return Arrays.toString(pos) +
              whiteOrBlack + rank;
   }

   @Override
   public boolean equals(Object o) {
      if (this == o) return true;  // Are they the same object?
      if (o == null || getClass() != o.getClass()) return false; // Are they the same class?
      Piece piece = (Piece) o;  // Do the cast to Piece
      if (this.getWhiteOrBlack() != piece.getWhiteOrBlack()) return false; //same colour?
      // Now just check the locations
      return Arrays.equals(getPos(), piece.getPos()); // Another way of checking equality. Also checks for nulls
   }
}