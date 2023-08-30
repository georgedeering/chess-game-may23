package com.example.chessjavafx;

/**
 * Bishop class - The class that represents the Bishop piece, mainly responsible for generating the available moves for the individual Bishop
 * this class extends piece
 * @author owg5 , jmb26
 * @version 1.0
 */
public class Bishop extends Piece {
   /**
    * Constructor for the Bishop piece
    * @param x the piece's initial x coordinate
    * @param y the piece's initial y coordinate
    * @param whiteOrBlack the piece's Color
    */
   public Bishop(int x, int y, WhiteOrBlack whiteOrBlack) {
      this.setRank(Rank.BISHOP);
      this.setPos(x, y);
      this.setWhiteOrBlack(whiteOrBlack);
   }
   /**
    * Generates the moves that the pieces (by running the super class with movement vectors)
    * @param board The board we are checking the valid moves on
    * @return 2d array of boolean values, saying where it can be moved
    */
   @Override
   public boolean[][] generateMoves(Board board) {
      int[][] vectors = {{1, 1}, {1, -1}, {-1, 1}, {-1, -1}};
      return super.generateMovesMany(this, board, vectors);
   }
}