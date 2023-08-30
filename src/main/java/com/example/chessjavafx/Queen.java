package com.example.chessjavafx;
/**
 * Queen class - The class that represents the Queen piece mainly responsible for generating the available moves for the individual Queen
 * this class extends piece
 * @author owg5 , jmb26
 * @version 1.0
 */
public class Queen extends Piece {
   /**
    * Constructor for the Queen piece
    * @param x the piece's initial x coordinate
    * @param y the piece's initial y coordinate
    * @param whiteOrBlack the piece's Color
    */
   public Queen(int x, int y, WhiteOrBlack whiteOrBlack) {
      this.setRank(Rank.QUEEN);
      this.setPos(x, y);
      this.setWhiteOrBlack(whiteOrBlack);
   }
   /**
    * Generates the moves that the piece has (by running the super class with movement vectors)
    * @param board The board we are checking the valid moves on
    * @return 2d array of boolean values saying where it can be moved
    */
   @Override
   public boolean[][] generateMoves(Board board) {
      int[][] vectors = {{1, 1}, {1, -1}, {-1, 1}, {-1, -1}, {0, 1}, {1, 0}, {-1, 0}, {0, -1}};
      return super.generateMovesMany(this, board, vectors);
   }
}