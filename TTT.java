import java.util.ArrayList;
import java.util.Random;
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

  Square squares[][];
  ArrayList<Group> groups = new ArrayList<Group>();
  Text t;
  boolean gameOver;
  Text playerText;
  Text computerText;
  int computerScore = 0;
  int playerScore = 0;
  int computerLevel = 1;
  Random rn = new Random();
  boolean player1Turn = true;
  char nextMoveSymbol;
  String BMPhrases[] = {"Horrible", "Awful", "Disgraceful", "Terrible", "Shameful"};

  // Level 1 computer moves
  // Computer makes a random move
  private void randomComputerMove() {
    int randomNumber1 = rn.nextInt(3);
    int randomNumber2 = rn.nextInt(3);

    while (squares[randomNumber1][randomNumber2].isTaken()) {
      randomNumber1 = rn.nextInt(3);
      randomNumber2 = rn.nextInt(3);
    }
    squares[randomNumber1][randomNumber2].playMove('X');
    checkForWinner();
  }

  // Level 2 computer moves
  // Plays winning move, otherwise prevents opponents winning move
  // Otherwise plays random move
  private void levelTwoComputerMove() {
    // Checks for winning move
    for (Group g : groups) {
      if (g.possibleWin('X') != null) {
        Square s = g.possibleWin('X');
        s.playMove('X');
        checkForWinner();
        return;
      }
    }
    // Checks for opponent winning move
    for (Group g : groups) {
      if (g.possibleWin('O') != null) {
        Square s = g.possibleWin('O');
        s.playMove('X');
        return;
      }
    }
    randomComputerMove();
  }

  private void checkDraw() {
    for (Square[] row : squares) {
      for (Square s : row) {
        if (!s.isTaken()) {
          return;
        }
      }
    }
    t.setText("It's a draw!");
    gameOver = true;
  }

  private void checkForWinner() {
    for (Group g : groups) {
      if (g.checkWin() != ' ') {
        char symbol = g.checkWin();
        if (symbol == 'O') {
          playerScore++;
          playerText.setText("Player O : " + playerScore);
          t.setText("Lucky");
        } else {
          computerScore++;
          computerText.setText("Player X : " + computerScore);
          t.setText("Easy");
        }
        gameOver = true;
      }
    }
  }

  private void resetBoard() {
    for (Square[] row : squares) {
      for (Square s : row) {
        s.resetSquare();
      }
    }
    player1Turn = true;
    gameOver = false;
    t.setText("Pick a square");
  }

  private class SquareClickHandler implements EventHandler<ActionEvent> {
    @Override
    public void handle(ActionEvent event) {
      if (player1Turn) {
        nextMoveSymbol = 'O';
      } else {
        nextMoveSymbol = 'X';
      }
      if (!gameOver) {
        if (!((Square) event.getSource()).isTaken()) {
          ((Square) event.getSource()).playMove(nextMoveSymbol);
          checkForWinner();
          if (!gameOver) {
            checkDraw();
          }
          if (!gameOver) {
            int rand = rn.nextInt(5);
            t.setText(BMPhrases[rand]);
            if (computerLevel == 1) {
              randomComputerMove();
            } else if (computerLevel == 2) {
              levelTwoComputerMove();
            } else if (computerLevel == 0) {
              player1Turn = !player1Turn;
            }
          }
        }
      }
    }
  }

  // Draws the grid, adds clickable buttons and prepares the game
  @Override
  public void start(Stage stage) {
    gameOver = false;
    VBox parent = new VBox();
    HBox userInterface = new HBox();
    GridPane grid = new GridPane();

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
            computerLevel = 1;
          } else if (dropDownMenu.getValue() == "Medium") {
            computerLevel = 2;
          } else {
            computerLevel = 0;
          }
        });

    computerText = new Text("Player X : " + computerScore);
    computerText.setFont(new Font(20));
    computerText.setTranslateX(376);
    computerText.setTranslateY(80);

    playerText = new Text("Player O : " + playerScore);
    playerText.setFont(new Font(20));
    playerText.setTranslateX(281);
    playerText.setTranslateY(55);

    t = new Text("Pick a square");
    t.setFont(new Font(50));
    t.setTranslateX(-304);
    t.setTranslateY(-8);

    squares = new Square[3][3];
    for (int row = 0; row < 3; row++) {
      for (int col = 0; col < 3; col++) {
        Square button = new Square(' ');
        button.setOnAction(new SquareClickHandler());
        grid.add(button, col, row);
        squares[row][col] = button;
      }
    }
    groups.add(new Group(squares[0][0], squares[0][1], squares[0][2]));
    groups.add(new Group(squares[1][0], squares[1][1], squares[1][2]));
    groups.add(new Group(squares[2][0], squares[2][1], squares[2][2]));

    groups.add(new Group(squares[0][0], squares[1][0], squares[2][0]));
    groups.add(new Group(squares[0][1], squares[1][1], squares[2][1]));
    groups.add(new Group(squares[0][2], squares[1][2], squares[2][2]));

    groups.add(new Group(squares[0][0], squares[1][1], squares[2][2]));
    groups.add(new Group(squares[2][0], squares[1][1], squares[0][2]));

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
