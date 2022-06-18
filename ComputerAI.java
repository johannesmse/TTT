import java.util.Random;

public class ComputerAI {
  Square[][] squares;
  Group[] groups;
  Random rn = new Random();
  int level = 1;

  ComputerAI(Square[][] squares, Group[] groups){
    this.squares = squares;
    this.groups = groups;
  }

  public void makeMove(){
    if (this.level == 1){
      levelOneComputerMove();
    } else if (this.level == 2){
      levelTwoComputerMove();
    }
  }

  // Level 1 computer moves
  // Computer makes a random move
  private void levelOneComputerMove(){
    int randomNumber1 = rn.nextInt(3);
    int randomNumber2 = rn.nextInt(3);

    while (squares[randomNumber1][randomNumber2].isTaken()) {
      randomNumber1 = rn.nextInt(3);
      randomNumber2 = rn.nextInt(3);
    }
    squares[randomNumber1][randomNumber2].playMove('X');
  }

  // Level 2 computer moves
  // Plays winning move, otherwise prevents opponents winning move
  // Otherwise plays random move
  private void levelTwoComputerMove(){
    // Checks for winning move
    for (Group g : groups) {
      if (g.possibleWin('X') != null) {
        Square s = g.possibleWin('X');
        s.playMove('X');
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
    levelOneComputerMove();
  }
}
