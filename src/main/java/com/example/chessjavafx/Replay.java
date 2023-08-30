package com.example.chessjavafx;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
/**
 * The Replay class gets all the board states that happened in the board
 * @author owg5 , jmb26
 * @version 1.0
 */
public class Replay {
   int position = 0;
   ArrayList<Piece[][]> boardStates = new ArrayList<>();

   /**
    * gets the board at every move in the game and saves them to an arraylist
    *
    * @param filename the filename of the file in question
    */
   public void getFileBoards(String filename) {
      String[] fileContents = getContents(filename);
      Board board = new Board();
      boardStates.add(board.getBoardCopy());
      for (String i : fileContents) {
         int startX = Integer.valueOf(i.substring(0, 1));
         int startY = Integer.valueOf(i.substring(1, 2));
         int endX = Integer.valueOf(i.substring(2, 3));
         int endY = Integer.valueOf(i.substring(3, 4)); //gets the first 4 chars as ints
         WhiteOrBlack whiteOrBlack = board.getPiece(startX, startY).getWhiteOrBlack();
         board.movePiece(startX, startY, endX, endY);
         if (i.length() != 4) { //if there's move that 4 chars get the fifth letter since it's a promotion
            switch (i.substring(4, 5)) {
               case "K" -> board.setPiece(Rank.KING, endX, endY, whiteOrBlack);
               case "R" -> board.setPiece(Rank.ROOK, endX, endY, whiteOrBlack);
               case "P" -> board.setPiece(Rank.PAWN, endX, endY, whiteOrBlack);
               case "N" -> board.setPiece(Rank.KNIGHT, endX, endY, whiteOrBlack);
               case "Q" -> board.setPiece(Rank.QUEEN, endX, endY, whiteOrBlack);
               case "B" -> board.setPiece(Rank.BISHOP, endX, endY, whiteOrBlack);
            }
         }
         boardStates.add(board.getBoardCopy());
      }
   }

   /**
    * gets the content as an array of moves that are made
    *
    * @param filename
    * @return
    */
   public String[] getContents(String filename) {
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
    * gets the current board state
    *
    * @return returns array of pieces that correspond to the current turn
    */
   public Piece[][] getBoardState() {
      return boardStates.get(position);
   }

   /**
    * gets the next turns board
    *
    * @return returns array of pieces that correspond to the next turn
    */
   public Piece[][] getNext() {
      if (position+1 == boardStates.size()) {
         return boardStates.get(position);
      }
      position++;
      return boardStates.get(position);

   }

   /**
    * gets the next turns board
    *
    * @return returns array of pieces that correspond to the previous turn
    */
   public Piece[][] getPrevious() {
      if (position != 0) {
         position--;
         return boardStates.get(position);
      }
      return boardStates.get(position);
   }
}
