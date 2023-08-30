package com.example.chessjavafx;

import java.util.Arrays;
/**
 * King class - The class that represents the King piece, mainly responsible for generating the available moves for the individual King
 * this class extends piece
 * @author owg5 , jmb26
 * @version 1.0
 */
public class King extends Piece {
   /**
    * Constructor for the king piece
    * @param x the piece's initial x coordinate
    * @param y the piece's initial y coordinate
    * @param whiteOrBlack the piece's Color
    */
   public King(int x, int y, WhiteOrBlack whiteOrBlack) {
      this.setRank(Rank.KING);
      this.setPos(x, y);
      this.setWhiteOrBlack(whiteOrBlack);
      this.setHasMoved(false);
   }
   /**
    * Generates the moves that the piece can make
    * @param board The board we are checking valid moves on
    * @return 2d array of boolean values saying where it can be moved
    */
   @Override
   public boolean[][] generateMoves(Board board) {
      boolean[][] validMoves = new boolean[8][8];
      int[][] vectors = {{1, 1}, {1, -1}, {-1, 1}, {-1, -1}, {0, 1}, {1, 0}, {-1, 0}, {0, -1}};
      for (int[] vector : vectors) {
         int x = this.pos[0] + vector[0];
         int y = this.pos[1] + vector[1];
         if ((x >= 0 && x <= 7) && (y >= 0 && y <= 7)) {
            if (board.getPiece((x), (y)) == null) {
               if (validPosForPiece(x, y, board)) {
                  validMoves[y][x] = true;
               }
            } else {
               if (board.getPiece((x), (y)).getWhiteOrBlack() != this.whiteOrBlack) {
                  if (validPosForPiece(x, y, board)) {
                     validMoves[y][x] = true;
                  }
               }
            }
         }
      }
      int[] startLocBlackKing = {4, 0};
      int[] startLocWhiteKing = {4, 7};
      if ((!this.getHasMoved()) && (Arrays.equals(this.getPos(), startLocWhiteKing) || Arrays.equals(this.getPos(), startLocBlackKing))) {
         if (rightSideCastle(board)) {
            validMoves[pos[1]][pos[0] + 2] = true;
         }
         if (leftSideCastle(board)) {
            validMoves[pos[1]][pos[0] - 2] = true;
         }
      }
      return validMoves;
   }

   /**
    * Checks if the king can castle to the right side
    * @param board the board that we are working on
    * @return boolean of whether the king can castle to the right side
    */
   private boolean rightSideCastle(Board board) {
      if (board.getPiece((pos[0] + 3), (pos[1])) == null) {
         return false;
      }
      if (board.getPiece((pos[0] + 2), (pos[1])) != null || board.getPiece((pos[0] + 1), (pos[1])) != null) {
         return false;
      }
      if (board.getPiece((pos[0] + 3), (pos[1])).getRank() == Rank.ROOK && !board.getPiece(pos[0] + 3, (pos[1])).getHasMoved()) {
         if (validPosForPiece((pos[0] + 1), (pos[1]), board)) {
            return validPosForPiece((pos[0] + 2), (pos[1]), board);
         }
      }
      return false;
   }
   /**
    * Checks if the king can castle to the left side
    * @param board the board that we are working on
    * @return boolean of whether the king can castle to the left side
    */
   private boolean leftSideCastle(Board board) {
      if (board.getPiece((pos[0] - 4), (pos[1])) == null) {
         return false;
      }
      if (board.getPiece((pos[0] - 2), (pos[1])) != null || board.getPiece((pos[0] - 1), (pos[1])) != null || board.getPiece((pos[0] - 3), (pos[1])) != null) {
         return false;
      }
      if (board.getPiece((pos[0] - 4), (pos[1])).getRank() == Rank.ROOK && !board.getPiece((pos[0] - 4), pos[1]).getHasMoved()) {
         if (validPosForPiece((pos[0] - 1), (pos[1]), board)) {
            return validPosForPiece((pos[0] - 2), (pos[1]), board);
         }
      }
      return false;
   }
}
