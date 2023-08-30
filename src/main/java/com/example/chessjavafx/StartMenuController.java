package com.example.chessjavafx;


import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.Objects;


public class StartMenuController extends Main{
   private Stage stage;
   private Scene scene;
   private Parent root;

   /**
    * @param e mouse click
    */
   public void startNewGame(ActionEvent e) throws IOException {
      //sets private scene variable as MainGame scene, adds the stylesheets to the scene, and shows the new scene
      root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("MainGame.fxml")));
      stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
      scene = new Scene(root);
      String css = Objects.requireNonNull(this.getClass().getResource("StartMenuStyling.css")).toExternalForm();
      scene.getStylesheets().add(css);
      stage.setScene(scene);
      stage.show();

      //load default chessboard layout
      game.newGame();
      game.board.getBoard();
   }

   /**
    * @param e mouse click
    */
   public void continueGame(ActionEvent e) throws IOException {

      FXMLLoader mainGameLoader = new FXMLLoader(getClass().getResource("MainGame.fxml"));
      root = mainGameLoader.load();

      MainGameController mainGameController = mainGameLoader.getController();
      mainGameController.continueGame();

      stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
      scene = new Scene(root);
      String css = Objects.requireNonNull(this.getClass().getResource("StartMenuStyling.css")).toExternalForm();
      scene.getStylesheets().add(css);
      stage.setScene(scene);
      stage.show();
   }

   /**
    * @param e mouse click
    */
   public void replayGame(ActionEvent e)
           throws IOException {
      //load ScrollPane
      //get String saveGameNames
      //onMouseClick -> game.replayGame(NAMEOFSAVEGAME);

      FXMLLoader mainGameLoader = new FXMLLoader(getClass().getResource("MainGame.fxml"));
      root = mainGameLoader.load();

      MainGameController mainGameController = mainGameLoader.getController();
      mainGameController.replayGame();


      stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
      scene = new Scene(root);
      String css = Objects.requireNonNull(this.getClass().getResource("StartMenuStyling.css")).toExternalForm();
      scene.getStylesheets().add(css);
      stage.setScene(scene);
      stage.show();
   }

   /**
    * closes program
    */
   public void exitGame() {
      System.exit(0);
   }


}