package tests;

import org.junit.jupiter.api.Test;
import com.example.chessjavafx.* ;
import org.junit.jupiter.api.Assertions;

/**
 * This is a test class designed to test the Board class, making sure that its functions work as expected.
 * @author owg5 , jmb26
 * @version 1.0
 */
public class BoardTests {

   /**
    * Test to make sure that everytime a new board is created in its newest form,
    * it is always created in the same format in the constructor.
    */
   @Test
   public void testNewBoard() {
      Piece[][] testArray = new Piece[8][8];
      //manually create a board of pieces
      //for every single item in that board, make sure that they are equal
      for (int i = 0; i < 8; i++) {
         testArray[1][i] = new Pawn(i, 1, WhiteOrBlack.BLACK);
         testArray[6][i] = new Pawn(i, 6, WhiteOrBlack.WHITE);
      }
      testArray[0][0] = new Rook(0, 0, WhiteOrBlack.BLACK);
      testArray[0][7] = new Rook(7, 0, WhiteOrBlack.BLACK);
      testArray[0][1] = new Knight(1, 0, WhiteOrBlack.BLACK);
      testArray[0][6] = new Knight(6, 0, WhiteOrBlack.BLACK);
      testArray[0][2] = new Bishop(2, 0, WhiteOrBlack.BLACK);
      testArray[0][5] = new Bishop(5, 0, WhiteOrBlack.BLACK);
      testArray[0][3] = new Queen(3, 0, WhiteOrBlack.BLACK);
      testArray[0][4] = new King(4, 0, WhiteOrBlack.BLACK);

      testArray[7][0] = new Rook(0, 7, WhiteOrBlack.WHITE);
      testArray[7][7] = new Rook(7, 7, WhiteOrBlack.WHITE);
      testArray[7][1] = new Knight(1, 7, WhiteOrBlack.WHITE);
      testArray[7][6] = new Knight(6, 7, WhiteOrBlack.WHITE);
      testArray[7][2] = new Bishop(2, 7, WhiteOrBlack.WHITE);
      testArray[7][5] = new Bishop(5, 7, WhiteOrBlack.WHITE);
      testArray[7][3] = new Queen(3, 7, WhiteOrBlack.WHITE);
      testArray[7][4] = new King(4, 7, WhiteOrBlack.WHITE);

      for (int i = 16; i < 48; i++) {
         testArray[i / 8][i % 8] = null;
      }

      Board controlBoard = new Board(testArray);
      Board testBoard = new Board();

      Assertions.assertArrayEquals(controlBoard.getBoard(), testBoard.getBoard());
   }

   /**
    * Test to make sure that when a move function is called on a piece, it is moved to that new location,
    * the change is updated in the piece properties, and the old location is now empty.
    */
   @Test
   public void testMovePieceStandard() {
      Board testBoard = new Board();
      Piece testPiece = testBoard.getPiece(0, 1);
      testBoard.movePiece(0, 1, 0, 3);
      Assertions.assertAll("Piece location",
              () -> Assertions.assertArrayEquals(new int[]{0, 3}, testPiece.getPos()),
              () -> Assertions.assertNull(testBoard.getPiece(0, 1)),
              () -> Assertions.assertEquals(testPiece, testBoard.getPiece(0, 3)),
              () -> Assertions.assertTrue(testBoard.getPiece(0, 3).getHasMoved())
      );
   }

   /**
    * Test to make sure that if a piece is tried to move out of bounds, nothing happens.
    */
   @Test
   public void testMovePieceOutOfBounds() {
      Board testBoard = new Board();
      Piece testPiece = testBoard.getPiece(0, 1);
      testBoard.movePiece(0, 1, 10, 16);
      Assertions.assertAll("Piece location",
              () -> Assertions.assertArrayEquals(new int[]{0, 1}, testPiece.getPos()),
              () -> Assertions.assertEquals(testPiece, testBoard.getPiece(0, 1))
              );
   }

   /**
    * Test to make sure that if the movePiece function is called on a null object, nothing happens.
    * Also, that if there is a piece in the desired move location, that piece isn't lost once the function is called.
    */
   @Test
   public void testMoveNullPiece(){
      Board testBoard = new Board();
      Piece originalPiece = testBoard.getPiece(0, 1);
      testBoard.movePiece(5, 6, 0, 1);
      Assertions.assertAll("Check piece integrity",
              () -> Assertions.assertNotNull(testBoard.getPiece(0, 1)),
              () -> Assertions.assertEquals(originalPiece, testBoard.getPiece(0, 1))
      );
   }

   /**
    * Test to make sure that if the setPiece function is called correctly, all happens as desired.
    */
   @Test
   public void testSetPieceStandard() {
      Board testBoard = new Board();
      testBoard.setPiece(Rank.BISHOP,0,1,WhiteOrBlack.BLACK);
      Assertions.assertAll("Checking if all is changed correctly",
            () -> Assertions.assertEquals(Rank.BISHOP, testBoard.getPiece(0, 1).getRank()),
            () -> Assertions.assertArrayEquals(new int[]{0,1}, testBoard.getPiece(0, 1).getPos())
      );

   }

   /**
    * Test to make sure that, given the right conditions, a black checkmate can occur and is discovered correctly.
    */
   @Test
   public void testBlackCheckmate() {
      Board testBoard =new Board();
      testBoard.movePiece(4,6,4,4);
      testBoard.movePiece(4,1,4,3);
      testBoard.movePiece(5,7,2,4);
      testBoard.movePiece(1,0,2,2);
      testBoard.movePiece(3,7,7,3);
      testBoard.movePiece(6,0,5,2);
      testBoard.movePiece(7,3,5,1);
      Assertions.assertTrue(testBoard.checkForMate(WhiteOrBlack.BLACK));
   }

   /**
    * Test to make sure that, given the right conditions, a white checkmate can occur and is discovered correctly.
    */
   @Test
   public void testWhiteCheckmate() {
      Board testBoard =new Board();
      testBoard.movePiece(5,6,5,5);
      testBoard.movePiece(4,1,4,2);
      testBoard.movePiece(6,6,6,4);
      testBoard.movePiece(3,0,7,4);
      Assertions.assertTrue(testBoard.checkForMate(WhiteOrBlack.WHITE));
   }

   /**
    * Test to make sure that, given conditions for no checkmate whatsoever, the program recognises this as a non-checkmate situation
    */
   @Test
   public void testNonCheckmate() {
      Board testBoard =new Board();
      testBoard.movePiece(4,6,4,4);
      testBoard.movePiece(5,1,5,2);
      testBoard.movePiece(3,7,7,3);
      Assertions.assertAll("Check Non check mate",
              () -> Assertions.assertFalse(testBoard.checkForMate(WhiteOrBlack.BLACK)),
              () -> Assertions.assertTrue(testBoard.checkForCheck(WhiteOrBlack.BLACK,testBoard.getBoard())));
   }

   /**
    * Test to make sure that the deep copy function works as expected.
    * In this I made a copy of the start board.
    * I then moved a piece in the start board and checked that this move did not happen in the copied board
    */
   @Test
   public void testDeepCopy() {
      Board testBoard = new Board();

      Piece[][] copyBoard = testBoard.getBoardCopy();

      testBoard.movePiece(0, 1, 0, 2);

      Assertions.assertNotEquals(testBoard.getBoardCopy()[1][0], copyBoard[1][0]);

   }

   /**
    * Test to make sure that, given the right conditions, a white check can occur and is discovered correctly.
    */
   @Test
   public void testWhiteCheck() {
      Board testBoard =new Board();
      testBoard.movePiece(5,6,5,5);
      testBoard.movePiece(4,1,4,3);
      testBoard.movePiece(6,7,7,5);
      testBoard.movePiece(3,0,7,4);
      Assertions.assertTrue(testBoard.checkForCheck(WhiteOrBlack.WHITE,testBoard.getBoard()));
   }

   /**
    * Test to make sure that, given the right conditions, a black check can occur and is discovered correctly.
    */
   @Test
   public void testBlackCheck() {
      Board testBoard =new Board();
      testBoard.movePiece(4,6,4,4);
      testBoard.movePiece(5,1,5,2);
      testBoard.movePiece(3,7,7,3);
      Assertions.assertTrue(testBoard.checkForCheck(WhiteOrBlack.BLACK,testBoard.getBoard()));
   }

   /**
    * Test to make sure that, given conditions for no check whatsoever, the program recognises this as a non-check situation
    */
   @Test
   public void testNonCheck() {
      Board testBoard =new Board();
      testBoard.movePiece(5,6,5,5);
      testBoard.movePiece(4,1,4,3);
      testBoard.movePiece(6,7,7,5);
      testBoard.movePiece(3,0,7,4);
      testBoard.movePiece(7,5,5,6);
      Assertions.assertFalse(testBoard.checkForCheck(WhiteOrBlack.WHITE,testBoard.getBoard()));
   }
}
