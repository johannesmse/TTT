import javafx.event.*;
import javafx.scene.control.Button;
import javafx.scene.text.Font;

public class Square extends Button {
  char symbol;
  int value = 0;

  Square(char text) {
    super("" + text);
    symbol = text;
    setText("" + text);
    setMinWidth(200);
    setMinHeight(200);
    Font font = new Font(72);
    setFont(font);
  }

  public void playMove(char symbol) {
    setText("" + symbol);
    this.symbol = symbol;
    if (symbol == 'O') {
      this.value = 1;
    } else {
      this.value = -1;
    }
  }

  public char getSymbol() {
    return symbol;
  }

  public int getValue() {
    return value;
  }

  public boolean isTaken() {
    return value != 0;
  }
}
