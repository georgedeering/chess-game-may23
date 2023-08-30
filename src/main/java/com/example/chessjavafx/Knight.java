package com.example.chessjavafx;
/**
 * Knight class - The class that represents the Knight piece mainly responsible for generating the available moves for the individual Knight
 * this class extends piece
 * @author owg5 , jmb26
 * @version 1.0
 */
public class Knight extends Piece {
   /**
    * Constructor for the Knight piece
    * @param x the piece's initial x coordinate
    * @param y the piece's initial y coordinate
    * @param whiteOrBlack the piece's Color
    */
   public Knight(int x, int y, WhiteOrBlack whiteOrBlack) {
      this.setRank(Rank.KNIGHT);
      this.setPos(x, y);
      this.setWhiteOrBlack(whiteOrBlack);
   }

   /**
    * Generates the moves that the piece can make
    * @param board The board we are checking valid moves on
    * @return 2d array of boolean values saying where it can be moved
    */
   public boolean[][] generateMoves(Board board) {
      boolean[][] validMoves = new boolean[8][8];
      int[][] vectors = {{-2, -1}, {-1, -2}, {1, -2}, {2, -1}, {2, 1}, {1, 2}, {-1, 2}, {-2, 1}};
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
      return validMoves;
   }
}