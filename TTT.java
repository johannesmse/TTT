import java.util.Random;
import javafx.application.Application;
import javafx.event.*;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class TTT extends Application {

  Square squares[][];
  Text t;
  boolean finished = false;

  @Override
  public void start(Stage stage) {
    VBox parent = new VBox();
    GridPane grid = new GridPane();
    t = new Text("Velg en rute");
    t.setFont(new Font(60));
    grid.setGridLinesVisible(true);

    squares = new Square[3][3];
    for (int i = 0; i < 3; i++) {
      for (int k = 0; k < 3; k++) {
        Square button = new Square(' ');
        button.setOnAction(new SquareClickHandler());
        grid.add(button, i, k);
        squares[i][k] = button;
      }
    }

    parent.getChildren().add(grid);
    parent.getChildren().add(t);
    Scene scene = new Scene(parent);
    stage.setScene(scene);
    stage.setTitle("Tre på rad");
    stage.show();
  }

  private void advanceGame() {
    if (fullBoard()) {
      gameDraw();
    } else {
      Random rn = new Random();
      int number1 = rn.nextInt(3) + 0;
      int number2 = rn.nextInt(3) + 0;

      while (squares[number1][number2].isTaken()) {
        number1 = rn.nextInt(3) + 0;
        number2 = rn.nextInt(3) + 0;
      }
      squares[number1][number2].playMove('X');
      checkWinner('X');
    }
  }

  private boolean fullBoard() {
    int counter = 0;

    for (Square[] ss : squares) {
      for (Square s : ss) {
        if (s.isTaken()) {
          counter++;
        }
      }
    }
    return counter == 9;
  }

  private void gameDraw() {
    t.setText("Det ble uavgjort!");
    finished = true;
  }

  private boolean checkWinner(char symbol) {
    if (squares[0][0].getSymbol() == symbol
        && squares[0][1].getSymbol() == symbol
        && squares[0][2].getSymbol() == symbol) {
      t.setText(symbol + " vant!");
      finished = true;
      return true;
    } else if (squares[1][0].getSymbol() == symbol
        && squares[1][1].getSymbol() == symbol
        && squares[1][2].getSymbol() == symbol) {
      t.setText(symbol + " vant!");
      finished = true;
      return true;
    } else if (squares[2][0].getSymbol() == symbol
        && squares[2][1].getSymbol() == symbol
        && squares[2][2].getSymbol() == symbol) {
      t.setText(symbol + " vant!");
      finished = true;
      return true;
    } else if (squares[0][0].getSymbol() == symbol
        && squares[1][0].getSymbol() == symbol
        && squares[2][0].getSymbol() == symbol) {
      t.setText(symbol + " vant!");
      finished = true;
      return true;
    } else if (squares[0][1].getSymbol() == symbol
        && squares[1][1].getSymbol() == symbol
        && squares[2][1].getSymbol() == symbol) {
      t.setText(symbol + " vant!");
      finished = true;
      return true;
    } else if (squares[0][2].getSymbol() == symbol
        && squares[1][2].getSymbol() == symbol
        && squares[2][2].getSymbol() == symbol) {
      t.setText(symbol + " vant!");
      finished = true;
      return true;
    } else if (squares[0][0].getSymbol() == symbol
        && squares[1][1].getSymbol() == symbol
        && squares[2][2].getSymbol() == symbol) {
      t.setText(symbol + " vant!");
      finished = true;
      return true;
    } else if (squares[2][0].getSymbol() == symbol
        && squares[1][1].getSymbol() == symbol
        && squares[0][2].getSymbol() == symbol) {
      t.setText(symbol + " vant!");
      finished = true;
      return true;
    }

    return false;
  }

  private class SquareClickHandler implements EventHandler<ActionEvent> {
    @Override
    public void handle(ActionEvent event) {

      if (!finished) {
        if (!((Square) event.getSource()).isTaken()) {
          ((Square) event.getSource()).playMove('O');
          if (!checkWinner('O')) {
            advanceGame();
          }
        }
      }
    }
  }
}
