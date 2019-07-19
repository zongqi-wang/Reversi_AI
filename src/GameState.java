/**
 *
 */
public class GameState {

    private final int ROW_NUMBER = 8;
    private final int COL_NUMBER = 8;
    private final char BLACK = '@';
    private final char WHITE = 'O';


    private int[][] gameBoard;

    public GameState(){

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
