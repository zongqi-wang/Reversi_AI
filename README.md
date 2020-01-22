# Reversi_AI
## What is Reversi
Reversi is a strategy board game for two players, played on an 8×8 uncheckered board. There are sixty-four identical game pieces called disks (often spelled "discs"), which are light on one side and dark on the other. Players take turns placing disks on the board with their assigned color facing up. During a play, any disks of the opponent's color that are in a straight line and bounded by the disk just placed and another disk of the current player's color are turned over to the current player's color.

The object of the game is to have the majority of disks turned to display your color when the last playable empty square is filled.

## Menu Overview


## Game Overview
I have chosen to implement the version of Reversi in which the first player is black, and the game’s starting position includes a square of two white pieces and two black pieces in the middle, with the white pieces in the upper left and bottom right positions.
Game would end when both players do not have a legal move to make. A game can end in a tie, unlike some other more sophisticated versions of Reversi/Othello. When the game ends, the program would stop. I did not have time to implement a restart screen.

## AI Overview
There are 2 types of AI. Easy (pure Monte Carlo Tree Search) and Hard (MiniMax with Alpha Beta Pruning)

### MCTS
I have decided to limit the time of each search to 1.5 seconds for each move, and all playout are played by randomly choosing a move from all the legal moves present in the current game state. To help the performance of the tree search and improve its win rate. The tree is kept from the beginning and the simulated data are kept in the tree. Every time the opponent makes a move the tree moves to the Node corresponding to the new game state and starts selection from there. 

### Minimax
I have implemented a vanilla version of MinMax search algorithm with Alpha Beta Pruning. The max search depth is set to 5. The evaluation algorithm uses a combination of 5 different factors:
1. Disc difference: the difference in number of discs on board between black and white players.
2. Mobility: the number of legal plays available to current player.
3. Parity: estimates who is the last player to play last. 
4. Corners: the weight of corners. If they are captured. 
5. Sum of disc weights: The sum of weights of placed discs belonging to that player currently on board

These factors are used differently depending on the number of discs played. In other words, the early game, mid game, as well as late game have different evaluation formulas. The specific weights are in appendices A and B. 
