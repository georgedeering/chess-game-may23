package com.example.chessjavafx;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
/**
 * The Pfile class handles the saving and continuing of games
 * @author owg5 , jmb26
 * @version 1.0
 */
public class PFile {
   private WhiteOrBlack currentTurn;


   public PFile() {
      String defaultFilename = "pfiles/pFile";
      String tmpname = defaultFilename;

      int iterator = 1;
      File file = new File(defaultFilename + ".csv");
      while (file.isFile()) {
         tmpname = defaultFilename + iterator;
         iterator++;
         file = new File(tmpname + ".csv");
      }
      pFilename = tmpname + ".csv";
   }

   public String pFilename;

   public String getpFilename() {
      return pFilename;
   }

   public void setpFilename(String pFilename) {
      this.pFilename = pFilename;
   }

   /**
    * gets the final state of the board after all the actions within the file for the default file name
    *
    * @return the final state of the board after all the actions within the file
    */
   public Board getFileBoard() {
      return getFileBoard(pFilename);
   }

   /**
    * gets whose turn it is after the operation of getfileboard
    *
    * @return white or black
    */
   public WhiteOrBlack getCurrentTurn() {
      return currentTurn;
   }

   /**
    * gets the final state of the board after all the actions within the file
    *
    * @return the final state of the board after all the actions within the file
    */
   public Board getFileBoard(String filename) {
      pFilename = filename;
      String[] fileContents = getContents(filename);
      Board board = new Board();
      for (String i : fileContents) {
         int startX = Integer.parseInt(i.substring(0, 1));
         int startY = Integer.parseInt(i.substring(1, 2));
         int endX = Integer.parseInt(i.substring(2, 3));
         int endY = Integer.parseInt(i.substring(3, 4));
         WhiteOrBlack whiteOrBlack = board.getPiece(startX, startY).getWhiteOrBlack();
         board.movePiece(startX, startY, endX, endY);
         if (i.length() != 4) {
            switch (i.substring(4, 5)) {
               case "K" -> board.setPiece(Rank.KING, endX, endY, whiteOrBlack);
               case "R" -> board.setPiece(Rank.ROOK, endX, endY, whiteOrBlack);
               case "P" -> board.setPiece(Rank.PAWN, endX, endY, whiteOrBlack);
               case "N" -> board.setPiece(Rank.KNIGHT, endX, endY, whiteOrBlack);
               case "Q" -> board.setPiece(Rank.QUEEN, endX, endY, whiteOrBlack);
               case "B" -> board.setPiece(Rank.BISHOP, endX, endY, whiteOrBlack);
            }
         }
      }
      if (fileContents.length % 2 == 0) {
         currentTurn = WhiteOrBlack.BLACK;
      } else {
         currentTurn = WhiteOrBlack.WHITE;
      }
      return board;
   }

   /**
    * gets the file contents
    *
    * @return string array of all the moves made
    */
   public String[] getContents() {
      return getContents(pFilename);
   }

   public String[] getContents(String filename) {
      this.pFilename = filename;
      try {
         File file = new File(filename);
         file.createNewFile();
         Scanner scanner = new Scanner(file);
         List<String> fileContents = new ArrayList<>();
         scanner.useDelimiter(",");
         while (scanner.hasNext()) {
            fileContents.add(scanner.next());
         }
         scanner.close();
         return fileContents.toArray(new String[0]);
      } catch (IOException e) {
         throw new RuntimeException(e);
      }

   }

   /**
    * saves the move made with the parameters below
    *
    * @param startX start x coordinate of the piece
    * @param startY start y coordinate of the piece
    * @param endX   end x coordinate of the piece
    * @param endY   end y coordinate of the piece
    */
   public void saveMove(int startX, int startY, int endX, int endY) {
      if (startX > 7 || startY > 7 || endX > 7 || endY > 7 || startX < 0 || startY < 0 || endX < 0 || endY < 0) {
         return;
      }
      try {
         String toAppend;
         File file = new File(pFilename);
         file.createNewFile();
         FileWriter writer = new FileWriter(file, true);
         if (file.length() == 0) {
            toAppend = "" + startX + startY + endX + endY;
         } else {
            toAppend = "," + startX + startY + endX + endY;
         }
         writer.append(toAppend);
         writer.close();
      } catch (IOException e) {
         throw new RuntimeException(e);
      }
   }

   /**
    * saves a promotion
    *
    * @param startX start x coordinate of the piece
    * @param startY start y coordinate of the piece
    * @param endX   end x coordinate of the piece
    * @param endY   end y coordinate of the piece
    * @param rank   the type of piece
    */
   public void saveMove(int startX, int startY, int endX, int endY, Rank rank) {
      if (startX > 7 || startY > 7 || endX > 7 || endY > 7 || startX < 0 || startY < 0 || endX < 0 || endY < 0) {
         return;
      }
      String rankID = null;
      switch (rank) {
         case ROOK -> rankID = "R";
         case PAWN -> rankID = "P";
         case BISHOP -> rankID = "B";
         case KING -> rankID = "K";
         case KNIGHT -> rankID = "N";
         case QUEEN -> rankID = "Q";
      }
      try {
         String toAppend;
         File file = new File(pFilename);
         file.createNewFile();
         FileWriter writer = new FileWriter(file, true);
         if (file.length() == 0) {
            toAppend = "" + startX + startY + endX + endY + rankID;
         } else {
            toAppend = "," + startX + startY + endX + endY + rankID;

         }
         writer.append(toAppend);
         writer.close();
      } catch (IOException e) {
         throw new RuntimeException(e);
      }
   }
}
