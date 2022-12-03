// Team 1 Backgammon Project
import java.util.List;

/**
 * Class representing a match which ends with one player bearing all checkers off the board,
 * or with one player refusing a double.  Multiple matches can make up a game.
 *
 * Design Pattern: This class is implemented similar to a Facade deign pattern.
 *                 A Facade pattern provides a unified interface to a set of interfaces
 *                 in a subsystem, in this case to the board, points, players and UI.
 *
 * @author Cian O'Mahoney  GitHub:cian-omahoney  SN:19351611
 * @author Ciarán Cullen   GitHub:TangentSplash  SN:19302896
 * @version 1 2022-12-03
 */
public class Match {
    private UI _userInterface;
    private Player _playerRed;
    private Player _playerWhite;
    private DoublingCube _doublingCube;

    /**
     * Match constructor.
     * @param playerWhite White player object.
     * @param playerRed Red player object.
     * @param userInterface Current User interface object.
     */
    public Match(Player playerWhite, Player playerRed, UI userInterface) {
        this._playerWhite = playerWhite;
        this._playerRed = playerRed;
        this._doublingCube = new DoublingCube();
        this._userInterface = userInterface;
    }

    /**
     * Play entire match which ends with one player bearing all checkers off the board,
     * or with one player refusing a double.
     * @param matchNumber Number of current match.
     * @param gameLength Total number of matches to play in the game.
     * @return Final winning score of the match. -1 if the game is quit.
     */
    public int playMatch(int matchNumber, int gameLength){
        Board board=new Board();
        List<Integer> moveSequence;
        Player activePlayer;
        Player winningPlayer;
        Command currentCommand;
        int finalScore = 1;

        // First roll determines which player will be the active player first.
        activePlayer = _userInterface.getFirstRoll(_playerRed, _playerWhite, matchNumber);
        _userInterface.printBoard(board, _playerRed, _playerWhite, activePlayer.getNumber(), matchNumber, gameLength, _doublingCube);
        _userInterface.printDashboard(activePlayer);

        currentCommand = new Command("FIRST");
        do{
            if(currentCommand.isHint()) {
                _userInterface.printHint();
            }
            else if(currentCommand.isPip()) {
                _userInterface.printPipCount(_playerWhite, _playerRed, board);
            }
            else if(currentCommand.isDouble()) {
                // If command is 'double', determine if active player has possession of the doubling cube.
                if(_doublingCube.isDoublingCubeOwner(activePlayer)) {
                    // If the current player has possession, they offer a double/
                    // The other player may accept, or refuse and forfeit the current match.
                    if(_userInterface.offerDouble(activePlayer, _playerRed, _playerWhite)) {
                        _doublingCube.doubleStakes();
                    }
                    else {
                        _doublingCube.doubleRefused();
                    }
                }
                else {
                    _userInterface.printNotDoublingCubeOwner();
                }
            }
            else if(currentCommand.isRoll() || currentCommand.isFirst() || currentCommand.isDice()) {
                if(currentCommand.isRoll()) {
                    activePlayer.rollBothDice();
                }
                else if(currentCommand.isDice()) {
                    activePlayer.addAvailableMovesMultiple(currentCommand.getForcedDiceValues());
                }

                // While the active player still has available dice moves and neither player has won,
                // they can make another move:
                while(activePlayer.availableMovesRemaining() && !board.isMatchOver(_playerRed, _playerWhite, _doublingCube)) {
                    _userInterface.printDice(activePlayer);
                    // If the player has checkers on the bar, they must move these checkers back onto the board
                    // first before they can move any other checkers.
                    if(board.isBarEmpty(activePlayer)) {
                        // The selection of the valid move is handeled by the user interface.
                        // The returned move sequence correspond to the valid move selected by the user.
                        moveSequence = _userInterface.selectValidMove(board.getValidMoves(activePlayer));
                    }
                    else {
                        moveSequence = _userInterface.selectValidMove(board.getValidBarMoves(activePlayer));
                    }

                    board.moveChecker(moveSequence, activePlayer);
                    activePlayer.updateAvailableMoves(moveSequence);
                    _userInterface.printMoves(moveSequence);
                    _userInterface.printBoard(board, _playerRed, _playerWhite,activePlayer.getNumber(), matchNumber, gameLength, _doublingCube);
                    _userInterface.printDashboard(activePlayer);
                }

                if(!board.isMatchOver(_playerRed, _playerWhite, _doublingCube)) {
                    // After the active player finishes their turn, switch to the next player:
                    activePlayer = switch (activePlayer.getColour()) {
                        case WHITE -> _playerRed;
                        case RED -> _playerWhite;
                        default -> activePlayer;
                    };
                    _userInterface.finishTurn();
                }
            }

            _userInterface.printBoard(board, _playerRed, _playerWhite,activePlayer.getNumber(), matchNumber, gameLength, _doublingCube);

            // Check has one player won the match by bearing off all their checkers:
            if(!board.isMatchOver(_playerRed, _playerWhite, _doublingCube)) {
                _userInterface.printDashboard(activePlayer);
                currentCommand = _userInterface.getCommand(activePlayer);
            }
        }while(!currentCommand.isQuit() && !board.isMatchOver(_playerRed, _playerWhite, _doublingCube));

        _playerWhite.clearAvailableMoves();
        _playerRed.clearAvailableMoves();

        if(currentCommand.isQuit()){
            _userInterface.printQuit(activePlayer);
            finalScore = -1;
        }
        // If match ended with a winner, print the winner and winning score.
        else if(board.isMatchOver(_playerRed, _playerWhite, _doublingCube)) {
            winningPlayer = board.getWinner(_playerRed,_playerWhite, activePlayer);
            winningPlayer.addScore(board.getMatchScore(_playerRed, _playerWhite, _doublingCube));
            _userInterface.printWinner(winningPlayer, _playerRed, _playerWhite, matchNumber, gameLength, board);
            finalScore = winningPlayer.getScore();
        }
        return finalScore;
    }
}
