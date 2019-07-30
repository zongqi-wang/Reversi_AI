/**
 *
 */
public class GameBoard {

    private final int MAX_ROW = 8;
    private final int MAX_COL = 8;
    private final char BLACK = '@';
    private final char WHITE = 'O';


    private boolean playerOneNoMove = false;
    private boolean playerTwoNoMove = false;

    private int[][] gameBoard;

    private int legalMovesCount;

    public GameBoard(){

        //storing the game board, 0 is empty, 1 is Black and 2 is White.
        this.gameBoard = new int[MAX_ROW][MAX_COL];

        //initial position
        this.gameBoard[3][3] = 2;
        this.gameBoard[4][4] = 2;
        this.gameBoard[3][4] = 1;
        this.gameBoard[4][3] = 1;
    }

    /**
     *
     * @param row: the row position
     * @param col: the column position
     * @param player: 1 for Black and 2 for White
     * @return 0 if successful, 1 otherwise. only a play that captures at lease one point is allowed.
     */
    public int updatePosition(int row, int col, int player){

        if(this.gameBoard[row][col] != 0)
            return -1;
        int updated = 1;
        gameBoard[row][col]=player;
        boolean nw = findToken(player, -1, -1, row, col);
        boolean nn = findToken(player, -1, 0, row, col);
        boolean ne = findToken(player, -1, 1, row, col);

        boolean w = findToken(player, 0, -1, row, col);
        boolean e = findToken(player, 0, 1, row, col);

        boolean sw = findToken(player, 1, -1, row, col);
        boolean ss = findToken(player, 1, 0, row, col);
        boolean se = findToken(player, 1, 1, row, col);


        if(nw){
            System.out.println("nw");
            updateLine(player, -1, -1, row-1, col-1);
        }
        if(nn){
            System.out.println("nn");
            updateLine(player, -1, 0, row-1, col);
        }
        if(ne){
            System.out.println("ne");
            updateLine(player, -1, 1, row-1, col+1);
        }
        if(w){
            System.out.println("w");
            updateLine(player, 0, -1, row, col-1);
        }
        if(e){
            System.out.println("s");
            updateLine(player, 0, 1, row, col+1);
        }
        if(sw){
            System.out.println("sw");
            updateLine(player, 1, -1, row+1, col-1);
        }
        if(ss){
            System.out.println("ss");
            updateLine(player, 1, 0, row+1, col);
        }
        if(se){
            System.out.println("se");
            updateLine(player, 1, 1, row+1, col+1);
        }


        return updated;

    }

    private void updateLine(int player, int row_offset, int col_offset, int row, int col){
        if(gameBoard[row][col] == player) return;

        gameBoard[row][col] = player;
        updateLine(player, row_offset, col_offset, row + row_offset, col+col_offset);
    }

    /**
     *
     * @param row
     * @param col
     * @param row_offset
     * @param col_offset
     * @param player
     * @return
     */
    private boolean findToken(int player, int row_offset, int col_offset, int row, int col){
        if((col + col_offset >= 0 && row + row_offset >= 0)
                && (col + col_offset < MAX_COL && row + row_offset < MAX_COL)){

            //empty cell
            if(gameBoard[row+row_offset][col+col_offset] == 0) return false;
            else if(gameBoard[row+row_offset][col+col_offset] == player) return true;
            else
                return findToken(player, row_offset, col_offset, row+row_offset, col+col_offset);
        }
        else
            return false;

    }

    public int[][] getGameBoard(){
        return gameBoard;
    }
    /**
     * This function checks the board for the current scores of both players and return them in a 2 length array.
     * @return a 2 length array of which the first cell stores the current score for black player and the second for
     * white.
     */
    public int[] getScore() {

        int[] scores = {0,0};

        for(int row = 0; row < MAX_ROW; row++){
            for(int col = 0; col < MAX_COL; col++){
                if(this.gameBoard[row][col] == 1){
                    scores[0]++;
                }
                else if(this.gameBoard[row][col] == 2){
                    scores[1]++;
                }
            }
        }

        return scores;
    }

    /**
     * standard helper function
     * @return count of legal moves of current player after updated by getLegalMoves()
     */
    public int getLegalMovesCount(){
        return legalMovesCount;
    }


    /**
     * This function calculates all legal moves for current player and returns them in a 2D int array
     * @param player the player to check for
     * @return a 2D int array in which illegal moves are 0 and legal moves are assigned to 'player'
     */
    public int[][] getLegalMoves(int player){
        int[][] legalMoves = new int[MAX_ROW][MAX_COL];

        legalMovesCount = 0;

        //assume player has no moves
        if(player == 1) playerOneNoMove = true;
        if(player == 2) playerTwoNoMove = true;

        for(int row = 0; row < MAX_ROW; row++){
            for(int col = 0; col< MAX_COL; col++){

                if(this.gameBoard[row][col] == 0){
                    //checking all 8 directions of the cell
                    boolean nw = legal(player, -1, -1, row, col);
                    boolean nn = legal(player, -1, 0, row, col);
                    boolean ne = legal(player, -1, 1, row, col);

                    boolean w = legal(player, 0, -1, row, col);
                    boolean e = legal(player, 0, 1, row, col);

                    boolean sw = legal(player, 1, -1, row, col);
                    boolean ss = legal(player, 1, 0, row, col);
                    boolean se = legal(player, 1, 1, row, col);

                    if(nw || nn || ne || w || e || sw || ss || se){
                        legalMoves[row][col] = player;
                        legalMovesCount++;
                        //if player has move; update it
                        if(player == 1) playerOneNoMove = false;
                        if(player == 2) playerTwoNoMove = false;
                    }
                }
            }

        }

        return legalMoves;
    }


    /**
     * This function checks the the game has ended
     * @return true if game ended
     */
    public boolean gameEnd(){
        return playerOneNoMove && playerTwoNoMove;
    }
    /**
     * This function takes a single cell and calculates if it is a legal move.
     * A legal move is defiend as: if a cell immediately adjacent to the cell we are checking is occupied by
     * the opponent, and following the line indicated by row and column offsets eventually reacha another cell occupied
     * by the player we are checking for. This cell is deemed a legal move.
     * @param player the player to check for
     * @param row_offset the row offset of line
     * @param col_offset the column offset of line
     * @param current_row the current row position of cell to check
     * @param current_col the current column position of cell to check
     * @return boolean; true if legal.
     */
    private boolean legal(int player, int row_offset, int col_offset, int current_row, int current_col){
        int op;
        if(player == 1) op = 2;
        else if(player ==2) op = 1;
        else{
            System.out.println("Problem with player checking legal move. Current player: " + player);
            return false;
        }

        //not out of bound
        if((current_col + col_offset >= 0 && current_row + row_offset >= 0)
                && (current_col + col_offset < MAX_COL && current_row + row_offset < MAX_COL)){

            current_col += col_offset;
            current_row += row_offset;

        }
        else{
            return false;
        }


        //if opponent token immediately next to it
        if(gameBoard[current_row][current_col] == op){

            while((current_col + col_offset >= 0 && current_row + row_offset >= 0)
                    && (current_col + col_offset < MAX_COL && current_row + row_offset < MAX_COL)){

                current_col += col_offset;
                current_row +=row_offset;

                if(gameBoard[current_row][current_col] == 0) return false;
                else if(gameBoard[current_row][current_col] == player) return true;
                else continue;
            }


        }



        return false;

    }

    /**
     * This function prints the object to console. Black player's represented by # and While player's represented by *
     */
    public void printBoardToConsole(){
        //printing row by row
        for(int i = 0; i < MAX_ROW; i++){
            //legend for each column at the top
            if(i == 0)    System.out.println("   A    B    C    D    E    F    G    H  ");
            //legend for each row
            System.out.print(i);
            //printing columns
            for(int j = 0; j < MAX_COL; j++){
                switch(this.gameBoard[i][j]){
                    case 0:
                        System.out.print("  .  ");
                        break;
                    case 1:
                        System.out.printf("  %c  ", BLACK);
                        break;
                    case 2:
                        System.out.printf("  %c  ", WHITE);
                        break;
                }

            }

            switch(i){
                case 0:
                    System.out.println("  Legend");
                    break;
                case 1:
                    System.out.printf("  Black Player (Player 1): %c\n", BLACK);
                    break;
                case 2:
                    System.out.printf("  White Player (Player 2): %c\n", WHITE);
                    break;

                case 3:
                    System.out.println("  Empty Spaces: .");
                    break;

                case 4:
                    System.out.println("  Available plays: _");
                    break;

                    default:
                        System.out.println();
            }
        }

    }
}
