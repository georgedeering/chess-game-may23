package com.example.chessjavafx;

/**
 * Pawn class - The class that represents the pawn piece mainly responsible for generating the available moves for the individual pawn
 * this class extends piece
 * @author owg5 , jmb26
 * @version 1.0
 */
public class Pawn extends Piece {
   private int fwd;
   /**
    * Constructor for the pawn piece
    * @param x the piece's initial x coordinate
    * @param y the piece's initial y coordinate
    * @param whiteOrBlack the piece's Color
    */
   public Pawn(int x, int y, WhiteOrBlack whiteOrBlack) {
      this.setRank(Rank.PAWN);

      this.setPos(x, y);
      this.setWhiteOrBlack(whiteOrBlack);
      this.setHasMoved(false);
      this.setHasDoubleMoved(false);
      if (whiteOrBlack == WhiteOrBlack.WHITE) {
         fwd = -1;
      } else {
         fwd = 1;
      }
   }
   /**
    * Generates the moves that the piece can make
    * @param board The board we are checking valid moves on
    * @return 2d array of boolean values saying where it can be moved
    */
   @Override
   public boolean[][] generateMoves(Board board) {
      boolean[][] validMoves = new boolean[8][8];
      int x = pos[0];
      int y = pos[1];
      if (y + fwd <= 7 && y + fwd >= 0) {
         if (board.getPiece((x), (y + fwd)) == null) {
            validMoves[y + fwd][x] = validPosForPiece(x, y + fwd, board);
         }

         if (x + 1 <= 7) {
            if ((board.getPiece((x + 1), (y + fwd)) != null) && (board.getPiece((x + 1), (y + fwd)).getWhiteOrBlack() != this.whiteOrBlack)) {
               validMoves[y + fwd][x + 1] = validPosForPiece(x + 1, y + fwd, board);
            }
         }
         if (x - 1 >= 0) {
            if (board.getPiece((x - 1), (y + fwd)) != null && (board.getPiece((x - 1), (y + fwd)).getWhiteOrBlack() != this.whiteOrBlack)) {
               validMoves[y + fwd][x - 1] = validPosForPiece(x - 1, y + fwd, board);
            }
         }
      }
      if (!this.hasMoved && y+(2*fwd)>=0&&y+(2*fwd)<=7) { //stops index going out of bounds
         if ((board.getPiece((x), (y + fwd)) == null) && (board.getPiece((x), (y + (2 * fwd))) == null)) {
            validMoves[y + (2 * fwd)][x] = validPosForPiece(x, (y + (2 * fwd)), board);
         }
      }
      if (enPassentR(board)) {
         Piece[][] tmpBoard = (board.getBoardCopy());
         tmpBoard[y + fwd][x + 1] = tmpBoard[this.pos[1]][this.pos[0]];
         tmpBoard[this.pos[1]][this.pos[0]] = null;
         tmpBoard[this.pos[1]][this.pos[0] + 1] = null;
         if (!(board.checkForCheck(this.whiteOrBlack, tmpBoard))) {
            validMoves[y + fwd][x + 1] = true;
         }

      }
      if (enPassentL(board)) {
         Piece[][] tmpBoard = (board.getBoardCopy());
         tmpBoard[y + fwd][x - 1] = tmpBoard[this.pos[1]][this.pos[0]];
         tmpBoard[this.pos[1]][this.pos[0]] = null;
         tmpBoard[this.pos[1]][this.pos[0] - 1] = null;
         if (!(board.checkForCheck(this.whiteOrBlack, tmpBoard))) {
            validMoves[y + fwd][x - 1] = true;
         }
      }
      return validMoves;
   }

   /**
    * Checks if this pawn can perform en passant to the right
    * @param board the board we are working on
    * @return boolean of whether this piece can en passant to the right
    */
   private boolean enPassentR(Board board) {
      if (pos[0] + 1 <= 7) {
         if (board.getPiece((pos[0] + 1), (pos[1])) != null) {
            return (board.getPiece((pos[0] + 1), (pos[1])).getRank() == Rank.PAWN && board.getPiece((pos[0] + 1), (pos[1])).getHasDoubleMoved() && board.getPiece((pos[0] + 1), (pos[1])).getWhiteOrBlack() != this.whiteOrBlack);
         } else {
            return false;
         }
      } else {
         return false;
      }
   }
   /**
    * Checks if this pawn can perform en passant to the left
    * @param board the board we are working on
    * @return boolean of whether this piece can en passant to the left
    */
   private boolean enPassentL(Board board) {
      if (pos[0] - 1 >= 0) {
         if (board.getPiece((pos[0] - 1), (pos[1])) != null) {
            return (board.getPiece((pos[0] - 1), (pos[1])).getRank() == Rank.PAWN && board.getPiece((pos[0] - 1), (pos[1])).getHasDoubleMoved() && board.getPiece((pos[0] - 1), (pos[1])).getWhiteOrBlack() != this.whiteOrBlack);
         } else {
            return false;
         }
      } else {
         return false;
      }
   }
}
