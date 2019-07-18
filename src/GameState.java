/**
 *
 */
public class GameState {

    public final int ROW_NUMBER = 10;
    public final int COL_NUMBER = 10;
    public GameState(){

        //storing the game board, 0 is empty, 1 is Black and 2 is White.
        int[][] gameBoard = new int[ROW_NUMBER][COL_NUMBER];

        //initial position
        gameBoard[3][3] = 2;
        gameBoard[4][4] = 2;
        gameBoard[3][4] = 1;
        gameBoard[4][3] = 1;
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

        for(int i = 0; i < ROW_NUMBER; i++){
            for(int j = 0; j < COL_NUMBER; j++){
                switch(this.gameBoard[i][j]){
                    case 0:
                        System.out.print("  .  ");
                        break;
                    case 1:
                        System.out.print("  #  ");
                        break;
                    case 2:
                        System.out.print("  *  ");
                        break;
                }
                //next line
                System.out.println();
            }
        }

    }
}
