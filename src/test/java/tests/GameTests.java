package tests;

import com.example.chessjavafx.*;
import org.junit.jupiter.api.*;

import java.io.File;

/**
 * Test class to test the Game class, to make sure that when methods are run, all the elements taken from different classes run together well as a whole.
 *
 * @author owg5
 * @version 1.0
 */
public class GameTests {

   Game testGame; //these will be used for testing the game methods
   Board testBoard;

   /**
    * Before all the tests happen, the PFile folder contents are deleted to make way for test files and avoid any potential errors with older files
    */
   @BeforeAll
   public static void clearFolder() {
      String defaultFilename = "pfiles/pFile";
      String tmpname = defaultFilename;

      int iterator = 1;
      File file = new File(defaultFilename + ".csv");
      while (file.isFile()) {
         boolean checking = file.delete();
         tmpname = defaultFilename + iterator;
         iterator++;
         file = new File(tmpname + ".csv");
      }
   }

   /**
    * Before each test, the test board is reset, resetting the positions of pieces that have potentially been changed during other tests.
    * A new game object is created using that board an a new PFile along with it
    */
   @BeforeEach
   void initTestGame() {
      testBoard = new Board(new Piece[][]{
              {null, null, null, null, null, null, null, null},
              {null, new Pawn(1, 1, WhiteOrBlack.WHITE), null, null, null, null, null, null},
              {null, null, null, null, null, null, null, null},
              {new King(0, 3, WhiteOrBlack.WHITE), null, null, null, null, null, null, new King(7, 3, WhiteOrBlack.BLACK)},
              {null, null, null, null, null, null, null, null},
              {null, null, null, null, null, null, null, null},
              {new Bishop(0, 6, WhiteOrBlack.BLACK), null, null, null, null, null, null, null},
              {null, null, new Pawn(2, 7, WhiteOrBlack.BLACK), null, null, null, null, null}
      });
      testGame = new Game();
      testGame.setBoard(testBoard);
      testGame.setPFile(new PFile());
   }

   /**
    * This tests for when a valid MakeMove() function is called, if the necessary things are changed i.e. is the update logged in the file, is the board changed as expected.
    */
   @Test
   public void testMakeMove() {
      //test if move happens in board
      //test if update saved in pfile
      Piece[][] expectedBoard = new Piece[][]{
              {null, new Pawn(1, 0, WhiteOrBlack.WHITE), null, null, null, null, null, null},
              {null, null, null, null, null, null, null, null},
              {null, null, null, null, null, null, null, null},
              {new King(0, 3, WhiteOrBlack.WHITE), null, null, null, null, null, null, new King(7, 3, WhiteOrBlack.BLACK)},
              {null, null, null, null, null, null, null, null},
              {null, null, null, null, null, null, null, null},
              {new Bishop(0, 6, WhiteOrBlack.BLACK), null, null, null, null, null, null, null},
              {null, null, new Pawn(2, 7, WhiteOrBlack.BLACK), null, null, null, null, null},
      };

      testGame.makeMove(1, 1, 1, 0);
      String[] fileread = testGame.getpFile().getContents("pfiles/pFile.csv");
      Assertions.assertAll(
              () -> Assertions.assertArrayEquals(new String[]{"1110"}, fileread),
              () -> Assertions.assertArrayEquals(expectedBoard, testBoard.getBoard())
      );

   }

   /**
    * This tests for when a MakeMove() function is called on a null object, if nothing is changed i.e. is the file not updated, is the board unchanged.
    */
   @Test
   public void testMakeMoveNullPiece() {
      Piece[][] expectedBoard = new Piece[][]{
              {null, null, null, null, null, null, null, null},
              {null, new Pawn(1, 1, WhiteOrBlack.WHITE), null, null, null, null, null, null},
              {null, null, null, null, null, null, null, null},
              {new King(0, 3, WhiteOrBlack.WHITE), null, null, null, null, null, null, new King(7, 3, WhiteOrBlack.BLACK)},
              {null, null, null, null, null, null, null, null},
              {null, null, null, null, null, null, null, null},
              {new Bishop(0, 6, WhiteOrBlack.BLACK), null, null, null, null, null, null, null},
              {null, null, new Pawn(2, 7, WhiteOrBlack.BLACK), null, null, null, null, null}
      };

      testGame.makeMove(0, 2, 1, 2);
      String[] fileread = testGame.getpFile().getContents("pfiles/pFile.csv");
      Assertions.assertAll(
              () -> Assertions.assertArrayEquals(new String[]{}, fileread),
              () -> Assertions.assertArrayEquals(expectedBoard, testBoard.getBoard())
      );

   }

   /**
    * This tests for when a MakeMove() function is called on an object that moves it out of bound, if nothing is changed i.e. is the file not updated, is the board unchanged.
    */
   @Test
   public void testMakeMoveOutOfBounds() {
      Piece[][] expectedBoard = new Piece[][]{
              {null, null, null, null, null, null, null, null},
              {null, new Pawn(1, 1, WhiteOrBlack.WHITE), null, null, null, null, null, null},
              {null, null, null, null, null, null, null, null},
              {new King(0, 3, WhiteOrBlack.WHITE), null, null, null, null, null, null, new King(7, 3, WhiteOrBlack.BLACK)},
              {null, null, null, null, null, null, null, null},
              {null, null, null, null, null, null, null, null},
              {new Bishop(0, 6, WhiteOrBlack.BLACK), null, null, null, null, null, null, null},
              {null, null, new Pawn(2, 7, WhiteOrBlack.BLACK), null, null, null, null, null}
      };

      testGame.makeMove(0, 6, -2, 8);
      String[] fileread = testGame.getpFile().getContents("pfiles/pFile.csv");
      Assertions.assertAll(
              () -> Assertions.assertArrayEquals(new String[]{}, fileread),
              () -> Assertions.assertArrayEquals(expectedBoard, testBoard.getBoard())
      );

   }

   /**
    * This tests for when a promote function is called on a pawn, if the necessary things are changed i.e. is the update logged in the file, is the board changed as expected.
    */
   @Test
   public void testPromote() {
      Piece[][] expectedBoard = new Piece[][]{
              {null, new Knight(1, 0, WhiteOrBlack.WHITE), null, null, null, null, null, null},
              {null, null, null, null, null, null, null, null},
              {null, null, null, null, null, null, null, null},
              {new King(0, 3, WhiteOrBlack.WHITE), null, null, null, null, null, null, new King(7, 3, WhiteOrBlack.BLACK)},
              {null, null, null, null, null, null, null, null},
              {null, null, null, null, null, null, null, null},
              {new Bishop(0, 6, WhiteOrBlack.BLACK), null, null, null, null, null, null, null},
              {null, null, new Pawn(2, 7, WhiteOrBlack.BLACK), null, null, null, null, null}
      };

      testGame.promote(1, 1, 1, 0, Rank.KNIGHT);
      String[] fileread = testGame.getpFile().getContents("pfiles/pFile.csv");
      Assertions.assertAll(
              () -> Assertions.assertArrayEquals(new String[]{"1110N"}, fileread),
              () -> Assertions.assertArrayEquals(expectedBoard, testBoard.getBoard())
      );
   }

   /**
    * This tests for when a promote function is called on a pawn that promotes it to an invalid piece (i.e. King), if nothing is changed i.e. is the file not updated, is the board unchanged.
    */
   @Test
   public void testPromoteInvalidRank() {
      Piece[][] expectedBoard = new Piece[][]{
              {null, null, null, null, null, null, null, null},
              {null, new Pawn(1, 1, WhiteOrBlack.WHITE), null, null, null, null, null, null},
              {null, null, null, null, null, null, null, null},
              {new King(0, 3, WhiteOrBlack.WHITE), null, null, null, null, null, null, new King(7, 3, WhiteOrBlack.BLACK)},
              {null, null, null, null, null, null, null, null},
              {null, null, null, null, null, null, null, null},
              {new Bishop(0, 6, WhiteOrBlack.BLACK), null, null, null, null, null, null, null},
              {null, null, new Pawn(2, 7, WhiteOrBlack.BLACK), null, null, null, null, null}
      };

      testGame.promote(1, 1, 1, 0, Rank.KING);
      String[] fileread = testGame.getpFile().getContents("pfiles/pFile.csv");
      Assertions.assertAll(
              () -> Assertions.assertArrayEquals(new String[]{}, fileread),
              () -> Assertions.assertArrayEquals(expectedBoard, testBoard.getBoard())
      );

   }

   /**
    * This tests for when a promote function is called on an object that moves it out of bound, if nothing is changed i.e. is the file not updated, is the board unchanged.
    */
   @Test
   public void testPromoteIndexOutOfBounds() {
      Piece[][] expectedBoard = new Piece[][]{
              {null, null, null, null, null, null, null, null},
              {null, new Pawn(1, 1, WhiteOrBlack.WHITE), null, null, null, null, null, null},
              {null, null, null, null, null, null, null, null},
              {new King(0, 3, WhiteOrBlack.WHITE), null, null, null, null, null, null, new King(7, 3, WhiteOrBlack.BLACK)},
              {null, null, null, null, null, null, null, null},
              {null, null, null, null, null, null, null, null},
              {new Bishop(0, 6, WhiteOrBlack.BLACK), null, null, null, null, null, null, null},
              {null, null, new Pawn(2, 7, WhiteOrBlack.BLACK), null, null, null, null, null}
      };

      testGame.promote(2, 7, 2, 8, Rank.QUEEN);
      String[] fileread = testGame.getpFile().getContents("pfiles/pFile.csv");
      Assertions.assertAll(
              () -> Assertions.assertArrayEquals(new String[]{}, fileread),
              () -> Assertions.assertArrayEquals(expectedBoard, testBoard.getBoard())
      );
   }

   /**
    * This tests for when a promote function is called on a piece other than a pawn, if nothing is changed i.e. is the file not updated, is the board unchanged.
    */
   @Test
   public void testPromoteInvalidPiecePromotion() {
      Piece[][] expectedBoard = new Piece[][]{
              {null, null, null, null, null, null, null, null},
              {null, new Pawn(1, 1, WhiteOrBlack.WHITE), null, null, null, null, null, null},
              {null, null, null, null, null, null, null, null},
              {new King(0, 3, WhiteOrBlack.WHITE), null, null, null, null, null, null, new King(7, 3, WhiteOrBlack.BLACK)},
              {null, null, null, null, null, null, null, null},
              {null, null, null, null, null, null, null, null},
              {new Bishop(0, 6, WhiteOrBlack.BLACK), null, null, null, null, null, null, null},
              {null, null, new Pawn(2, 7, WhiteOrBlack.BLACK), null, null, null, null, null}
      };

      testGame.promote(0, 6, 1, 7, Rank.QUEEN);
      String[] fileread = testGame.getpFile().getContents("pfiles/pFile.csv");
      Assertions.assertAll(
              () -> Assertions.assertArrayEquals(new String[]{}, fileread),
              () -> Assertions.assertArrayEquals(expectedBoard, testBoard.getBoard())
      );
   }

   /**
    * After each test, delete the created test file
    */
   @AfterEach
   public void deleteFile() {
      File file = new File("pfiles/pFile.csv");
      file.delete();

   }
}
