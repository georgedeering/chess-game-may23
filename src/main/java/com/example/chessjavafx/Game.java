package com.example.chessjavafx;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Game class handles the interactions between the different classes
 *
 * @author owg5 , jmb26
 * @version 1.0
 */
public class Game {
   //jfx object
   Board board;
   WhiteOrBlack currentTurn;
   PFile pFile;
   Replay replay = new Replay();

   /**
    * Saves the current game as a replay, meant for when games are over
    *
    * @param newFilename The name of the file where it should be saved
    */
   public void saveReplay(String newFilename) {
      try {
         String[] movesMade = pFile.getContents();
         File file = new File(newFilename);
         file.createNewFile();
         FileWriter writer = new FileWriter(file, false);
         StringBuilder toWrite = new StringBuilder();
         for (String moves : movesMade) {
            toWrite.append(moves);
            toWrite.append(",");
         }
         toWrite.delete(toWrite.length() - 1, toWrite.length());
         writer.append(toWrite);
         writer.close();

      } catch (IOException e) {
         throw new RuntimeException(e);
      }

   }

   /**
    * Getter method for the pFile object
    *
    * @return returns the pFile object
    */
   public PFile getpFile() {
      return pFile;
   }

   /**
    * initialise a new game from scratch
    */
   public void newGame() {
      this.board = new Board();
      this.currentTurn = WhiteOrBlack.WHITE;
      this.pFile = new PFile();
   }

   /**
    * opens an existing game
    *
    * @param filename the filename of the pFile
    */
   public void continueGame(String filename) {
      this.pFile = new PFile();
      board = pFile.getFileBoard(filename);
      currentTurn = pFile.getCurrentTurn();
   }

   /**
    * gets the board
    *
    * @return the board object
    */
   public Board getBoard() {
      return board;
   }

   /**
    * runs all the logic for a turn (to be used by the ui program)
    *
    * @param startX start x coordinate of the piece
    * @param startY start y coordinate of the piece
    * @param endX   end x coordinate of the piece
    * @param endY   end y coordinate of the piece
    */
   public void makeMove(int startX, int startY, int endX, int endY) {
      if (startX <= 7 && startY <= 7 && endX <= 7 && endY <= 7 && startX >= 0 && startY >= 0 && endX >= 0 && endY >= 0) {
         if (board.getPiece(startX, startY) != null && board.getPiece(startX, startY).generateMoves(board)[endY][endX]) {
            board.movePiece(startX, startY, endX, endY);
            pFile.saveMove(startX, startY, endX, endY);
         }
      }
   }

   /**
    * Setter method for setting the board object in the class
    *
    * @param board holds the board that you want to set
    */
   public void setBoard(Board board) {
      this.board = board;
   }

   /**
    * Setter method to set the current PFile of the game
    *
    * @param pFile holds the current PFile to set
    */
   public void setPFile(PFile pFile) {
      this.pFile = pFile;
   }

   /**
    * code to be run in the occasion of a pawn being promoted
    *
    * @param startX start x coordinate of the piece
    * @param startY start y coordinate of the piece
    * @param endX   end x coordinate of the piece
    * @param endY   end y coordinate of the piece
    * @param rank   the type of piece
    */
   public void promote(int startX, int startY, int endX, int endY, Rank rank) {
      if (startX <= 7 && startY <= 7 && endX <= 7 && endY <= 7 && startX >= 0 && startY >= 0 && endX >= 0 && endY >= 0) {
         if (board.getPiece(startX, startY) != null && board.getPiece(startX, startY).generateMoves(board)[endY][endX] && board.getPiece(startX, startY).getRank() == Rank.PAWN) {
            switch (rank) {
               case PAWN, KING -> {
               }
               default -> {
                  WhiteOrBlack whiteOrBlack = board.getPiece(startX, startY).getWhiteOrBlack();
                  board.movePiece(startX, startY, endX, endY);
                  board.setPiece(rank, endX, endY, whiteOrBlack);
                  pFile.saveMove(startX, startY, endX, endY, rank);
               }
            }

         }
      }
   }
   /**
    *  method that deletes the current pFile when it's not passed a filename
    */
   public void quit() {
      File file = new File(pFile.getpFilename());
      file.delete();
   }

   /**
    * method that saves the current file under a new name then deletes the old one
    * @param newFilename the new filename for the savefile
    */
   public void quit(String newFilename) {
      if (!newFilename.equals(pFile.getpFilename())) {
         saveReplay(newFilename);
         File file = new File(pFile.getpFilename());
         file.delete();
         pFile.setpFilename(newFilename);
      }
   }
}
