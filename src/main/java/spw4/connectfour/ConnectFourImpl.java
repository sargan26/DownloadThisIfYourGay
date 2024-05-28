package spw4.connectfour;

public class ConnectFourImpl implements ConnectFour {
    Player current;
    final int rows = 6;
    final int cols = 7;
    private char [][] board;
    private int coinsCount;

    public ConnectFourImpl(Player playerOnTurn) {

        current= playerOnTurn;
        board = new char[rows][cols];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                board[i][j] = '.';
            }
        }
        coinsCount = 0;
    }

    public Player getPlayerAt(int row, int col) {
        return switch (board[row][col]) {
            case 'R' -> Player.red;
            case 'Y' -> Player.yellow;
            default -> Player.none;
        };

    }

    public Player getPlayerOnTurn() {

        return current;
    }

    public boolean isGameOver() {
        if(checkWin(current)){
            System.out.println(current + " wins!");
            return true;
        }
        if(coinsCount== 42){
            System.out.println("Game over. It's a draw!");
            current = Player.none;
            return true;
        }
        return false;
    }

    public Player getWinner() {
        return current;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("\n");
        sb.append("Player: ");
        if(current == Player.red)
            sb.append( "RED");
        else
            sb.append( "YELLOW");
        sb.append("\n");
        for (int i = 0; i < rows; i++) {
            sb.append("|");
            for (int j = 0; j <cols; j++) {
                sb.append(" ").append(board[i][j]).append(" ");
            }
            sb.append(" |\n");

        }

        return sb.toString();

    }

    public void reset(Player playerOnTurn) {
        System.out.println("Game reset");
        current= playerOnTurn;
        board = new char[rows][cols];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                board[i][j] = '.';
            }
        }
        coinsCount = 0;
    }
    public boolean checkWin(Player player) {
        char token = player == Player.red ? 'R' : 'Y';

        // check horizontal lines
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols - 3; j++) {
                if (board[i][j] == token && board[i][j+1] == token && board[i][j+2] == token && board[i][j+3] == token) {
                    return true;
                }
            }
        }

        // check vertical lines
        for (int i = 0; i < rows - 3; i++) {
            for (int j = 0; j < cols; j++) {
                if (board[i][j] == token && board[i+1][j] == token && board[i+2][j] == token && board[i+3][j] == token) {
                    return true;
                }
            }
        }

        // check diagonals from bottom-left to top-right
        for (int i = 3; i < rows; i++) {
            for (int j = 0; j < cols - 3; j++) {
                if (board[i][j] == token && board[i-1][j+1] == token && board[i-2][j+2] == token && board[i-3][j+3] == token) {
                    return true;
                }
            }
        }

        // check diagonals from top-left to bottom-right
        for (int i = 0; i < rows - 3; i++) {
            for (int j = 0; j < cols - 3; j++) {
                if (board[i][j] == token && board[i+1][j+1] == token && board[i+2][j+2] == token && board[i+3][j+3] == token) {
                    return true;
                }
            }
        }

        return false;
    }

    public void drop(int col) {
        if (isColumnFull(col)) {
            System.out.println("Column is full. Choose another column");
            return;
        }
        if(isGameOver()) {
            System.out.println("Game is over. Please restart the game");
            return;
        }

        int row = rows - 1;
        while(row >= 0 && board[row][col] != '.'){
            row--;
        }
        if(row >= 0 ) {
            board[row][col] = current == Player.red ? 'R' : 'Y';
            coinsCount++;
        }
        if (checkWin(current)) {

            return;
        }
        current= Player.red == current ? Player.yellow : Player.red;

    }
    public char getCharAt(int row, int col){
        return board[row][col];
    }

    public boolean isColumnFull(int col) {
        return board[0][col] != '.';
    }
}
