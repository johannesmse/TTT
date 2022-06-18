import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.*;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class TTT extends Application {
  ComputerAI computer;
  Square[][] squares = new Square[3][3];
  Group[] groups = new Group[8];
  Text t;
  Text playerText;
  Text computerText;
  int computerScore = 0;
  int playerScore = 0;
  boolean gameOver = false;
  boolean player1Turn = true;
  char nextMoveSymbol;

  private void checkDraw(){
    for (Square[] row : squares){
      for (Square s : row){
        if (!s.isTaken()){
          return;
        }
      }
    }
    t.setText("It's a draw!");
    gameOver = true;
  }

  private void checkForWinner(){
    for (Group g : groups){
      char symbol = g.checkWin();
      if (symbol != ' '){
        if (symbol == 'O'){
          playerScore++;
          playerText.setText("Player O : " + playerScore);
          t.setText("O wins!");
          for (Square s : g.getGroup()){
            s.setStyle("-fx-background-color: red");
          }
        } else {
          computerScore++;
          computerText.setText("Player X : " + computerScore);
          t.setText("X wins!");
          for (Square s : g.getGroup()){
            s.setStyle("-fx-background-color: blue");
          }
        }
        gameOver = true;
        return;
      }
    }
  }

  private void resetBoard(){
    for (Square[] row : squares){
      for (Square s : row){
        s.resetSquare();
      }
    }
    player1Turn = true;
    gameOver = false;
    t.setText("Pick a square");
  }

  private class SquareClickHandler implements EventHandler<ActionEvent> {
    @Override
    public void handle(ActionEvent event){
      if (player1Turn){
        nextMoveSymbol = 'O';
      } else {
        nextMoveSymbol = 'X';
      }
      if (!gameOver){
        if (!((Square) event.getSource()).isTaken()){
          ((Square) event.getSource()).playMove(nextMoveSymbol);
          checkForWinner();
          if (!gameOver){
            checkDraw();
          }
          if (!gameOver){
            if (computer.level == 0) {
              player1Turn = !player1Turn;
            } else {
              computer.makeMove();
              checkForWinner();
            }
          }
        }
      }
    }
  }

  // Draws the grid, adds clickable buttons and prepares the game
  @Override
  public void start(Stage stage) {
    VBox parent = new VBox();
    HBox userInterface = new HBox();
    GridPane grid = new GridPane();
    computer = new ComputerAI(squares, groups);

    Button restartButton = new Button("New Game");
    restartButton.setMinSize(100, 50);
    restartButton.setTranslateX(498);
    restartButton.setTranslateY(-57);
    restartButton.setOnAction(
        e -> {
          resetBoard();
        });

    ObservableList<String> options =
        FXCollections.observableArrayList("Easy", "Medium", "Friendly battle");
    ComboBox<String> dropDownMenu = new ComboBox<>(options);

    dropDownMenu.setTranslateX(371);
    dropDownMenu.setTranslateY(2);
    dropDownMenu.getSelectionModel().selectFirst();
    dropDownMenu.setOnAction(
        e -> {
          resetBoard();
          if (dropDownMenu.getValue() == "Easy") {
            computer.level = 1;
          } else if (dropDownMenu.getValue() == "Medium") {
            computer.level = 2;
          } else {
            computer.level = 0;
          }
        });

    playerText = new Text("Player O : " + playerScore);
    playerText.setFont(new Font(20));
    playerText.setTranslateX(281);
    playerText.setTranslateY(55);

    computerText = new Text("Player X : " + computerScore);
    computerText.setFont(new Font(20));
    computerText.setTranslateX(378);
    computerText.setTranslateY(80);

    t = new Text("Pick a square");
    t.setFont(new Font(50));
    t.setTranslateX(-304);
    t.setTranslateY(-8);


    for (int row = 0; row < 3; row++) {
      for (int col = 0; col < 3; col++) {
        Square button = new Square();
        button.setOnAction(new SquareClickHandler());
        grid.add(button, col, row);
        squares[row][col] = button;
      }
    }
    groups[0] = new Group(squares[0][0], squares[0][1], squares[0][2]);
    groups[1] = new Group(squares[1][0], squares[1][1], squares[1][2]);
    groups[2] = new Group(squares[2][0], squares[2][1], squares[2][2]);

    groups[3] = new Group(squares[0][0], squares[1][0], squares[2][0]);
    groups[4] = new Group(squares[0][1], squares[1][1], squares[2][1]);
    groups[5] = new Group(squares[0][2], squares[1][2], squares[2][2]);

    groups[6] = new Group(squares[0][0], squares[1][1], squares[2][2]);
    groups[7] = new Group(squares[2][0], squares[1][1], squares[0][2]);

    userInterface.getChildren().add(dropDownMenu);
    userInterface.getChildren().add(computerText);
    userInterface.getChildren().add(playerText);
    userInterface.getChildren().add(t);
    parent.getChildren().add(grid);
    parent.getChildren().add(userInterface);
    parent.getChildren().add(restartButton);
    grid.setGridLinesVisible(true);
    Scene scene = new Scene(parent);
    stage.setScene(scene);
    stage.setTitle("Tic tac toe");
    stage.show();
  }
}
