/**
 *
 */
public class GameBoard {

    private final int ROW_NUMBER = 8;
    private final int COL_NUMBER = 8;
    private final char BLACK = '@';
    private final char WHITE = 'O';


    private int[][] gameBoard;

    public GameBoard(){

        //storing the game board, 0 is empty, 1 is Black and 2 is White.
        this.gameBoard = new int[ROW_NUMBER][COL_NUMBER];

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
        //looking for
        for(int i = 0; i<ROW_NUMBER; i++){
            if(i == row) continue;

            if(this.gameBoard[i][col] == player){
                updated = 0;

                if(i < row){
                    for(int j = i; j<row; j++)
                        this.gameBoard[j][col] = player;
                }
                else{
                    for(int j = row; j<i; j++)
                        this.gameBoard[j][col] = player;
                }

            }
        }


        return updated;

    }

    /**
     * This function calculates all legal moves for current player and returns them in a 2D int array
     * @param player the player to check for
     * @return a 2D int array in which illegal moves are 0 and legal moves are assigned to 'player'
     */
    public int[][] getLegalMoves(int player){
        int[][] legalMoves = new int[ROW_NUMBER][COL_NUMBER];

        for(int row = 0; row < ROW_NUMBER; row++){
            for(int col = 0; col< COL_NUMBER; col++){

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
                        legalMoves[col][row] = player;
                    }
                }
            }

        }

        return legalMoves;
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

        if(this.gameBoard[current_row + row_offset][current_col + col_offset] == op){
            while((current_col + col_offset >=0 && current_row + row_offset >=0)
                    && (current_col + col_offset < COL_NUMBER && current_row + row_offset <COL_NUMBER)){
                if(this.gameBoard[current_row + row_offset][current_col + col_offset] == player)
                    return true;
                current_col = current_col+=col_offset;
                current_row = current_row+=row_offset;
            }
        }

        return false;

    }

    /**
     * This function prints the object to console. Black player's represented by # and While player's represented by *
     */
    public void printBoardToConsole(){
        //printing row by row
        for(int i = 0; i < ROW_NUMBER; i++){
            //legend for each column at the top
            if(i == 0)    System.out.println("   A    B    C    D    E    F    G    H  ");
            //legend for each row
            System.out.print(i);
            //printing columns
            for(int j = 0; j < COL_NUMBER; j++){
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
