package tests;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import com.example.chessjavafx.*;


/**
 * This class is a test class, used for testing all the piece subclasses under the abstract class Piece, as well as the abstract class itself.
 *
 * @author owg5 , ldw10
 * @version 1.0
 */
public class PieceTests {

   static Board testBoard; //This will be used by many of the test methods

   /**
    * Resetting the test board object back to default setup before all the tests.
    * This will be used by the tests for generateMoves, to make sure that the pieces generate moves depending on the locations of the other peices on the board
    */
   @BeforeAll
   static void initBoard() {
      testBoard = new Board(new Piece[][]{
              {null, null, null, null, new King(4, 0, WhiteOrBlack.BLACK), null, null, null},
              {new Pawn(0, 1, WhiteOrBlack.BLACK), new Pawn(1, 1, WhiteOrBlack.BLACK), new Pawn(2, 1, WhiteOrBlack.BLACK), new Rook(3, 1, WhiteOrBlack.BLACK), null, null, null, null},
              {null, null, new Queen(2, 2, WhiteOrBlack.WHITE), new Pawn(3, 2, WhiteOrBlack.WHITE), null, new Rook(5, 2, WhiteOrBlack.BLACK), null, null},
              {null, new Pawn(1, 3, WhiteOrBlack.WHITE), null, new Pawn(3, 3, WhiteOrBlack.BLACK), new Pawn(4, 3, WhiteOrBlack.BLACK), null, null, new Pawn(7, 3, WhiteOrBlack.BLACK)},
              {null, null, null, null, null, null, new Bishop(6, 4, WhiteOrBlack.WHITE), null},
              {null, null, new Pawn(2, 5, WhiteOrBlack.WHITE), null, new Pawn(4, 5, WhiteOrBlack.WHITE), new Knight(5, 5, WhiteOrBlack.WHITE), null, null},
              {null, null, null, new Pawn(3, 6, WhiteOrBlack.WHITE), null, null, new Pawn(6, 6, WhiteOrBlack.BLACK), null},
              {new Rook(0, 7, WhiteOrBlack.WHITE), null, null, null, new King(4, 7, WhiteOrBlack.WHITE), null, null, new Rook(7, 7, WhiteOrBlack.WHITE)}
      });
   }

   /**
    * Test that makes sure that when a pawn is created, it has the exact properties that we want upon creation
    */
   @Test
   public void testCreatePawn() {
      Pawn testPawn = new Pawn(2, 0, WhiteOrBlack.WHITE);
      Assertions.assertAll(
              () -> Assertions.assertArrayEquals(new int[]{2, 0}, testPawn.getPos()),
              () -> Assertions.assertEquals(WhiteOrBlack.WHITE, testPawn.getWhiteOrBlack()),
              () -> Assertions.assertFalse(testPawn.getHasMoved()),
              () -> Assertions.assertFalse(testPawn.getHasDoubleMoved()),
              () -> Assertions.assertEquals(Rank.PAWN, testPawn.getRank())
      );
   }

   /**
    * Test that makes sure that when a knight is created, it has the exact properties that we want upon creation
    */
   @Test
   public void testCreateKnight() {
      Knight testKnight = new Knight(4, 2, WhiteOrBlack.BLACK);
      Assertions.assertAll(
              () -> Assertions.assertArrayEquals(new int[]{4, 2}, testKnight.getPos()),
              () -> Assertions.assertEquals(WhiteOrBlack.BLACK, testKnight.getWhiteOrBlack()),
              () -> Assertions.assertFalse(testKnight.getHasMoved()),
              () -> Assertions.assertFalse(testKnight.getHasDoubleMoved()),
              () -> Assertions.assertEquals(Rank.KNIGHT, testKnight.getRank())
      );
   }

   /**
    * Test that makes sure that when a bishop is created, it has the exact properties that we want upon creation
    */
   @Test
   public void testCreateBishop() {
      Bishop testBishop = new Bishop(6, 6, WhiteOrBlack.WHITE);
      Assertions.assertAll(
              () -> Assertions.assertArrayEquals(new int[]{6, 6}, testBishop.getPos()),
              () -> Assertions.assertEquals(WhiteOrBlack.WHITE, testBishop.getWhiteOrBlack()),
              () -> Assertions.assertFalse(testBishop.getHasMoved()),
              () -> Assertions.assertFalse(testBishop.getHasDoubleMoved()),
              () -> Assertions.assertEquals(Rank.BISHOP, testBishop.getRank())
      );
   }

   /**
    * Test that makes sure that when a rook is created, it has the exact properties that we want upon creation
    */
   @Test
   public void testCreateRook() {
      Rook testRook = new Rook(4, 0, WhiteOrBlack.BLACK);
      Assertions.assertAll(
              () -> Assertions.assertArrayEquals(new int[]{4, 0}, testRook.getPos()),
              () -> Assertions.assertEquals(WhiteOrBlack.BLACK, testRook.getWhiteOrBlack()),
              () -> Assertions.assertFalse(testRook.getHasMoved()),
              () -> Assertions.assertFalse(testRook.getHasDoubleMoved()),
              () -> Assertions.assertEquals(Rank.ROOK, testRook.getRank())
      );
   }

   /**
    * Test that makes sure that when a queen is created, it has the exact properties that we want upon creation
    */
   @Test
   public void testCreateQueen() {
      Queen testQueen = new Queen(0, 0, WhiteOrBlack.WHITE);
      Assertions.assertAll(
              () -> Assertions.assertArrayEquals(new int[]{0, 0}, testQueen.getPos()),
              () -> Assertions.assertEquals(WhiteOrBlack.WHITE, testQueen.getWhiteOrBlack()),
              () -> Assertions.assertFalse(testQueen.getHasMoved()),
              () -> Assertions.assertFalse(testQueen.getHasDoubleMoved()),
              () -> Assertions.assertEquals(Rank.QUEEN, testQueen.getRank())
      );
   }

   /**
    * Test that makes sure that when a king is created, it has the exact properties that we want upon creation
    */
   @Test
   public void testCreateKing() {
      King testKing = new King(2, 3, WhiteOrBlack.BLACK);
      Assertions.assertAll(
              () -> Assertions.assertArrayEquals(new int[]{2, 3}, testKing.getPos()),
              () -> Assertions.assertEquals(WhiteOrBlack.BLACK, testKing.getWhiteOrBlack()),
              () -> Assertions.assertFalse(testKing.getHasMoved()),
              () -> Assertions.assertFalse(testKing.getHasDoubleMoved()),
              () -> Assertions.assertEquals(Rank.KING, testKing.getRank())
      );
   }

   /**
    * Test that makes sure that when a pawn's moves are generated, the generated boolean array is the exact same as expected in accordance with the conditions of the test board.
    * This means that it cannot move in spaces where there are pieces of the same colour, and can go in empty spaces or ones with enemy colours in.
    * In this case, the pawn in question is blocked forwards but can take a piece diagonal from it.
    */
   @Test
   public void testMovesPawn() {
      boolean[][] generatedMoves = testBoard.getPiece(2, 1).generateMoves(testBoard);
      boolean[][] expectedMoves =
              new boolean[][]{
                      {false, false, false, false, false, false, false, false},
                      {false, false, false, false, false, false, false, false},
                      {false, false, false, true, false, false, false, false},
                      {false, false, false, false, false, false, false, false},
                      {false, false, false, false, false, false, false, false},
                      {false, false, false, false, false, false, false, false},
                      {false, false, false, false, false, false, false, false},
                      {false, false, false, false, false, false, false, false},
              };
      Assertions.assertArrayEquals(expectedMoves, generatedMoves);
   }

   /**
    * Test that makes sure that when a bishop's moves are generated, the generated boolean array is the exact same as expected in accordance with the conditions of the test board.
    * This means that it cannot move in spaces where there are pieces of the same colour, and can go in empty spaces or ones with enemy colours in.
    */
   @Test
   public void testMovesBishop() {
      boolean[][] generatedMoves = testBoard.getPiece(6, 4).generateMoves(testBoard);
      boolean[][] expectedMoves =
              new boolean[][]{
                      {false, false, false, false, false, false, false, false},
                      {false, false, false, true, false, false, false, false},
                      {false, false, false, false, true, false, false, false},
                      {false, false, false, false, false, true, false, true},
                      {false, false, false, false, false, false, false, false},
                      {false, false, false, false, false, false, false, true},
                      {false, false, false, false, false, false, false, false},
                      {false, false, false, false, false, false, false, false},
              };
      Assertions.assertArrayEquals(expectedMoves, generatedMoves);
   }

   /**
    * Test that makes sure that when a knight's moves are generated, the generated boolean array is the exact same as expected in accordance with the conditions of the test board.
    * This means that it cannot move in spaces where there are pieces of the same colour, and can go in empty spaces or ones with enemy colours in.
    */
   @Test
   public void testMovesKnight() {
      boolean[][] generatedMoves = testBoard.getPiece(5, 5).generateMoves(testBoard);
      boolean[][] expectedMoves =
              new boolean[][]{
                      {false, false, false, false, false, false, false, false},
                      {false, false, false, false, false, false, false, false},
                      {false, false, false, false, false, false, false, false},
                      {false, false, false, false, true, false, true, false},
                      {false, false, false, true, false, false, false, true},
                      {false, false, false, false, false, false, false, false},
                      {false, false, false, false, false, false, false, true},
                      {false, false, false, false, false, false, true, false},
              };
      Assertions.assertArrayEquals(expectedMoves, generatedMoves);
   }

   /**
    * Test that makes sure that when a rook's moves are generated, the generated boolean array is the exact same as expected in accordance with the conditions of the test board.
    * This means that it cannot move in spaces where there are pieces of the same colour, and can go in empty spaces or ones with enemy colours in.
    */
   @Test
   public void testMovesRook() {
      boolean[][] generatedMoves = testBoard.getPiece(5, 2).generateMoves(testBoard);
      boolean[][] expectedMoves =
              new boolean[][]{
                      {false, false, false, false, false, true, false, false},
                      {false, false, false, false, false, true, false, false},
                      {false, false, false, true, true, false, true, true},
                      {false, false, false, false, false, true, false, false},
                      {false, false, false, false, false, true, false, false},
                      {false, false, false, false, false, true, false, false},
                      {false, false, false, false, false, false, false, false},
                      {false, false, false, false, false, false, false, false},
              };
      Assertions.assertArrayEquals(expectedMoves, generatedMoves);
   }

   /**
    * Test that makes sure that when a king's moves are generated, the generated boolean array is the exact same as expected in accordance with the conditions of the test board.
    * This means that it cannot move in spaces where there are pieces of the same colour, and can go in empty spaces or ones with enemy colours in.
    */
   @Test
   public void testMovesKing() {
      boolean[][] generatedMoves = testBoard.getPiece(4, 7).generateMoves(testBoard);
      boolean[][] expectedMoves =
              new boolean[][]{
                      {false, false, false, false, false, false, false, false},
                      {false, false, false, false, false, false, false, false},
                      {false, false, false, false, false, false, false, false},
                      {false, false, false, false, false, false, false, false},
                      {false, false, false, false, false, false, false, false},
                      {false, false, false, false, false, false, false, false},
                      {false, false, false, false, true, true, false, false},
                      {false, false, true, true, false, false, false, false},
              };
      Assertions.assertArrayEquals(expectedMoves, generatedMoves);
   }

   /**
    * Test that makes sure that when a queen's moves are generated, the generated boolean array is the exact same as expected in accordance with the conditions of the test board.
    * This means that it cannot move in spaces where there are pieces of the same colour, and can go in empty spaces or ones with enemy colours in.
    */
   @Test
   public void testMovesQueen() {
      boolean[][] generatedMoves = testBoard.getPiece(2, 2).generateMoves(testBoard);
      boolean[][] expectedMoves =
              new boolean[][]{
                      {false, false, false, false, false, false, false, false},
                      {false, true, true, true, false, false, false, false},
                      {true, true, false, false, false, false, false, false},
                      {false, false, true, true, false, false, false, false},
                      {false, false, true, false, false, false, false, false},
                      {false, false, false, false, false, false, false, false},
                      {false, false, false, false, false, false, false, false},
                      {false, false, false, false, false, false, false, false},
              };
      Assertions.assertArrayEquals(expectedMoves, generatedMoves);
   }

   /**
    * Test that makes sure that a piece cannot move if, in doing so, puts the king in check.
    * This means that all the generated moves result in false values
    */
   @Test
   public void testMovesPutKingInCheck() {
      boolean[][] generatedMoves = testBoard.getPiece(3, 1).generateMoves(testBoard);
      boolean[][] expectedMoves =
              new boolean[][]{
                      {false, false, false, false, false, false, false, false},
                      {false, false, false, false, false, false, false, false},
                      {false, false, false, false, false, false, false, false},
                      {false, false, false, false, false, false, false, false},
                      {false, false, false, false, false, false, false, false},
                      {false, false, false, false, false, false, false, false},
                      {false, false, false, false, false, false, false, false},
                      {false, false, false, false, false, false, false, false},
              };
      Assertions.assertArrayEquals(expectedMoves, generatedMoves);
   }

   /**
    * Test that makes sure that the enPessant move works
    * In this case, the two pawns are next to eachother, but the pawn doing enpessant is able to take the location behind the other piece
    */
   @Test
   public void testEnPessant() {
      testBoard.movePiece(0, 1, 0, 3);

      boolean[][] generatedMoves = testBoard.getPiece(1, 3).generateMoves(testBoard);
      boolean[][] expectedMoves =
              new boolean[][]{
                      {false, false, false, false, false, false, false, false},
                      {false, false, false, false, false, false, false, false},
                      {true, true, false, false, false, false, false, false},
                      {false, false, false, false, false, false, false, false},
                      {false, false, false, false, false, false, false, false},
                      {false, false, false, false, false, false, false, false},
                      {false, false, false, false, false, false, false, false},
                      {false, false, false, false, false, false, false, false},
              };
      Assertions.assertArrayEquals(expectedMoves, generatedMoves);
   }
}
