import java.util.Random;
import javafx.application.Application;
import javafx.event.*;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class TTT extends Application {

  Square squares[][];
  Text t;
  boolean gameOver;
  Text playerText;
  Text computerText;
  int computerScore = 0;
  int playerScore = 0;

  // Computer makes a random move
  // Used for level 1 computer moves
  private void randomComputerMove() {
    Random rn = new Random();
    int randomNumber1 = rn.nextInt(3);
    int randomNumber2 = rn.nextInt(3);

    while (squares[randomNumber1][randomNumber2].isTaken()) {
      randomNumber1 = rn.nextInt(3);
      randomNumber2 = rn.nextInt(3);
    }
    squares[randomNumber1][randomNumber2].playMove('X');
    checkWinner('X');
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
    return;
  }

  private void checkWinner(char symbol) {
    int rowCount, colCount, winValue;
    if (symbol == 'O') {
      winValue = 3;
    } else {
      winValue = -3;
    }

    // Checks for winner in each row and column
    for (int row = 0; row < 3; row++) {
      colCount = 0;
      rowCount = 0;
      for (int col = 0; col < 3; col++) {
        rowCount += squares[row][col].getValue();
        colCount += squares[col][row].getValue();
      }
      if (rowCount == winValue || colCount == winValue) {
        announceWinner(symbol);
        return;
      }
    }

    // Checks for diagonal or antidiagonal winner
    int diaCount = 0;
    int antiCount = 0;
    for (int i = 0; i < 3; i++) {
      diaCount += squares[i][i].getValue();
      antiCount += squares[2 - i][i].getValue();
    }
    if (diaCount == winValue || antiCount == winValue) {
      announceWinner(symbol);
    }
  }

  private void announceWinner(char symbol) {
    if (symbol == 'O') {
      playerScore++;
      playerText.setText("Player: " + playerScore);
      t.setText("Player won!");
    } else {
      computerScore++;
      computerText.setText("Computer: " + computerScore);
      t.setText("Computer won!");
    }
    gameOver = true;
  }

  private class SquareClickHandler implements EventHandler<ActionEvent> {
    @Override
    public void handle(ActionEvent event) {
      if (!gameOver) {
        if (!((Square) event.getSource()).isTaken()) {
          ((Square) event.getSource()).playMove('O');
          checkWinner('O');
          if (!gameOver) {
            checkDraw();
          }
          if (!gameOver) {
            randomComputerMove();
          }
        }
      }
    }
  }

  @Override
  public void start(Stage primarystage) {
    initialize(primarystage);
  }

  // Draws the grid, adds clickable buttons and prepares the game
  public void initialize(Stage stage) {
    gameOver = false;
    VBox parent = new VBox();
    HBox info = new HBox();
    GridPane grid = new GridPane();

    Button restartButton = new Button("New Game");
    restartButton.setMinSize(100, 50);
    restartButton.setTranslateX(498);
    restartButton.setTranslateY(-57);
    restartButton.setOnAction(
        e -> {
          initialize(stage);
        });

    computerText = new Text("Computer: " + computerScore);
    computerText.setFont(new Font(20));
    computerText.setTranslateX(486);
    computerText.setTranslateY(80);

    playerText = new Text("Player: " + playerScore);
    playerText.setFont(new Font(20));
    playerText.setTranslateX(413);
    playerText.setTranslateY(55);

    t = new Text("Pick a square");
    t.setFont(new Font(50));
    t.setTranslateX(-177);
    t.setTranslateY(-8);

    squares = new Square[3][3];
    for (int row = 0; row < 3; row++) {
      for (int col = 0; col < 3; col++) {
        Square button = new Square(' ');
        button.setOnAction(new SquareClickHandler());
        grid.add(button, col, row);
        squares[col][row] = button;
      }
    }

    info.getChildren().add(restartButton);
    info.getChildren().add(computerText);
    info.getChildren().add(playerText);
    info.getChildren().add(t);
    parent.getChildren().add(grid);
    parent.getChildren().add(info);
    parent.getChildren().add(restartButton);
    grid.setGridLinesVisible(true);
    Scene scene = new Scene(parent);
    stage.setScene(scene);
    stage.setTitle("Tic tac toe");
    stage.show();
  }
}
