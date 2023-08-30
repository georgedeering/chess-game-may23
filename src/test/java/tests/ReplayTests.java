package tests;

import org.junit.jupiter.api.*;
import com.example.chessjavafx.*;

import java.io.File;

/**
 * Class to test the replay methods, to make sure that they work as expected
 *
 * @author jmb26
 * @version 1.0
 */
public class ReplayTests {

   /**
    * Before all the tests happen, the PFile folder contents are deleted to make way for test files and avoid any potential errors with older files
    */
   @BeforeAll
   public static void clearFolder() {
      String defaultFilename = "pfiles/pFile";
      String tmpName = defaultFilename;

      int iterator = 1;
      File file = new File(defaultFilename + ".csv");
      while (file.isFile()) {
         file.delete();
         tmpName = defaultFilename + iterator;
         iterator++;
         file = new File(tmpName + ".csv");
      }
   }

   /**
    * Test to make sure that when the replay function is called, the first board state is shown correctly
    */
   @Test
   public void replayTestFirstBoard() {
      Board board = new Board();
      PFile pFile = new PFile();
      pFile.saveMove(0, 6, 0, 4);
      pFile.saveMove(0, 1, 0, 3);
      Replay replay = new Replay();
      replay.getFileBoards("pfiles/pFile.csv");
      Assertions.assertArrayEquals(board.getBoard(), replay.getBoardState());
   }

   /**
    * Test to make sure that when the replay function is called, the second board state shows correctly after getNext() is used
    */
   @Test
   public void replayTestSecondBoard() {
      Board board = new Board();
      PFile pFile = new PFile();
      board.movePiece(0, 6, 0, 4);
      pFile.saveMove(0, 6, 0, 4);
      pFile.saveMove(0, 1, 0, 3);
      Replay replay = new Replay();
      replay.getFileBoards("pfiles/pFile.csv");
      Assertions.assertArrayEquals(board.getBoard(), replay.getNext());
   }

   /**
    * Test to make sure that the replay class can play forwards correctly and then play backwards to revert to the previous played move correctly
    */
   @Test
   public void replayTestNextThenPreviousBoard() {
      Board board = new Board();
      PFile pFile = new PFile();
      pFile.saveMove(0, 6, 0, 4);
      pFile.saveMove(0, 1, 0, 3);
      Replay replay = new Replay();
      replay.getFileBoards("pfiles/pFile.csv");
      replay.getNext();
      Assertions.assertArrayEquals(board.getBoard(), replay.getPrevious());
   }

   /**
    * Test to make sure that trying to get a previous board state when the board state is already at the start results in no change
    */
   @Test
   public void replayTestPreviousBoardWhenAtStart() {
      Board board = new Board();
      PFile pFile = new PFile();
      pFile.saveMove(0, 6, 0, 4);
      pFile.saveMove(0, 1, 0, 3);
      Replay replay = new Replay();
      replay.getFileBoards("pfiles/pFile.csv");
      Assertions.assertArrayEquals(board.getBoard(), replay.getPrevious());
   }

   /**
    * Test to make sure that trying to get a next board state when the board state is already at the end results in no change
    */
   @Test
   public void replayTestNextAtEnd() {
      Board board = new Board();
      PFile pFile = new PFile();
      board.movePiece(0, 6, 0, 4);
      board.movePiece(0, 1, 0, 3);
      pFile.saveMove(0, 6, 0, 4);
      pFile.saveMove(0, 1, 0, 3);
      Replay replay = new Replay();
      replay.getFileBoards("pfiles/pFile.csv");
      replay.getNext();
      replay.getNext();
      Assertions.assertArrayEquals(board.getBoard(), replay.getNext());
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
