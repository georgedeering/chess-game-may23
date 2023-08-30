package com.example.chessjavafx;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class ReplayGameController extends MainGameController{
   @FXML
   public GridPane replayChessboard;

   public GridPane getReplayChessboard(){
      return replayChessboard;
   }

   /**
    * @param e mouse click
    */
   public void exitGame(ActionEvent e) throws IOException {
      Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("StartMenu.fxml")));
      Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
      Scene scene = new Scene(root);
      String css = Objects.requireNonNull(this.getClass().getResource("StartMenuStyling.css")).toExternalForm();
      scene.getStylesheets().add(css);
      stage.setScene(scene);
      stage.show();
   }

   /**
    * @param e mouse click
    */
   public void nextBoard(ActionEvent e) {
      replayGame.board.setBoard(replayGame.replay.getNext());
      chessBoard.getChildren().clear();
      createNextBoard(replayGame.board, replayChessboard);

   }

   /**
    * @param e mouse click
    */
   public void previousBoard(ActionEvent e) {
      replayGame.board.setBoard(replayGame.replay.getPrevious());
      chessBoard.getChildren().clear();
      createNextBoard(replayGame.board, replayChessboard);
   }
}
