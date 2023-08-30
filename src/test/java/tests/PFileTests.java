package tests;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import com.example.chessjavafx.*;

import java.io.File;

/**
 * This class is a test class that tests the PFile class, making sure that the persistence functionality works as required
 *
 * @author jmb26
 * @version 1.0
 */
public class PFileTests {

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
    * Test to make sure that when a move is made, the move is logged in the persistence file
    */
   @Test
   public void testSaveMove() {
      PFile pFile = new PFile();
      pFile.saveMove(0, 6, 0, 4);
      String[] fileread = pFile.getContents("pfiles/pFile.csv");
      Assertions.assertArrayEquals(new String[]{"0604"}, fileread);
   }

   /**
    * Test to make sure that when a promotion happens, the move is logged in the persistence file
    */
   @Test
   public void testSavePromote() {
      PFile pFile = new PFile();
      pFile.saveMove(0, 6, 0, 4, Rank.QUEEN);
      String[] fileread = pFile.getContents("pfiles/pFile.csv");
      Assertions.assertArrayEquals(new String[]{"0604Q"}, fileread);
   }

   /**
    * test to make sure that if a move that makes a piece go out of bounds attempts to be saved, the move isn't logged in the persistence file
    */
   @Test
   public void testSaveOutOfBounds() {
      PFile pFile = new PFile();
      pFile.saveMove(0, -6, 0, 4, Rank.QUEEN);
      String[] fileread = pFile.getContents("pfiles/pFile.csv");
      Assertions.assertArrayEquals(new String[]{}, fileread);
   }

   /**
    * Test to make sure that the getFileBoard retrieves the current board from a persistence file correctly
    */
   @Test
   public void getFileBoardTest() {
      Board board = new Board();
      board.movePiece(0, 6, 0, 4);
      PFile pFile = new PFile();
      pFile.saveMove(0, 6, 0, 4);
      Board testBoard = pFile.getFileBoard();
      Assertions.assertArrayEquals(board.getBoard(), testBoard.getBoard());
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
