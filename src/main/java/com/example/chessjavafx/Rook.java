package com.example.chessjavafx;
/**
 * Rook class - The class that represents the Rook piece mainly responsible for generating the available moves for the individual Rook
 * this class extends piece
 * @author owg5 , jmb26
 * @version 1.0
 */
public class Rook extends Piece {

   /**
    * Constructor for the rook piece
    * @param x the piece's initial x coordinate
    * @param y the piece's initial y coordinate
    * @param whiteOrBlack the piece's Color
    */
   public Rook(int x, int y, WhiteOrBlack whiteOrBlack) {
      this.setRank(Rank.ROOK);
      this.setPos(x, y);
      this.setWhiteOrBlack(whiteOrBlack);
      this.setHasMoved(false);
   }

   /**
    * Generates the moves that the piece has (by running the super class with movement vectors)
    * @param board The board we are the checking valid moves on
    * @return 2d array of boolean values saying where it can be moved
    */
   @Override
   public boolean[][] generateMoves(Board board) {
      int[][] vectors = {{0, 1}, {1, 0}, {-1, 0}, {0, -1}};
      return super.generateMovesMany(this, board, vectors);
   }
}
