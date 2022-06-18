public class Group {
  Square group[] = new Square[3];

  Group(Square square1, Square square2, Square square3) {
    this.group[0] = square1;
    this.group[1] = square2;
    this.group[2] = square3;
  }

  public Square[] getGroup() {
    return this.group;
  }

  public int countSymbol(char sym){
    int count = 0;
    for (int i = 0; i < 3; i++){
      if (this.group[i].symbol == sym){
        count++;
      }
    }
    return count;
  }

  public char checkWin() {
    if (countSymbol('O') == 3){
      return 'O';
    } else if (countSymbol('X') == 3){
      return 'X';
    } else {
      return ' ';
    }
  }

  // Checks if it is possible to make a winning move
  public Square possibleWin(char sym) {
    if (countSymbol(sym) == 2 && countSymbol(' ') == 1){
      for (int i = 0; i < 3; i++){
        if (this.group[i].symbol == ' '){
          return this.group[i];
        }
      }
    }
    return null;
  }
}
