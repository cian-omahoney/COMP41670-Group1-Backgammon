// Team 1 Backgammon Project
// By 
/***@author Cian O'Mahoney Github:cian-omahoney 
 *  @author Ciarán Cullen  Github:TangentSplash
*/

import java.util.List;

// Class representing a match which ends with one player bearing all checkers off the board
// (Multiple matches can make up a game)
public class Match {
    UI _userInterface;
    Player _playerRed;
    Player _playerWhite;

    public Match(Player playerWhite, Player playerRed, UI userInterface) {
        this._playerWhite = playerWhite;
        this._playerRed = playerRed;
        this._userInterface = userInterface;
    }

    public int playMatch(int matchNumber, int gameLength){
        Board board=new Board();

        List<Integer> moveSequence;
        Player activePlayer;
        Player winningPlayer;
        Command currentCommand;
        int finalScore = 1;

        activePlayer = _userInterface.getFirstRoll(_playerRed, _playerWhite, matchNumber);
        _userInterface.printBoard(board, _playerRed, _playerWhite, activePlayer.getNumber(), matchNumber, gameLength);
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
                if(board.isDoublingCubeOwner(activePlayer)) {
                    if(_userInterface.offerDouble(activePlayer, _playerRed, _playerWhite)) {
                        board.doubleStakes();
                    }
                    else {
                        board.doubleRefused();
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

                while(activePlayer.availableMovesRemaining() && !board.isMatchOver(_playerRed, _playerWhite)) {
                    _userInterface.printDice(activePlayer);
                    if(board.isBarEmpty(activePlayer)) {
                        moveSequence = _userInterface.selectValidMove(board.getValidMoves(activePlayer));
                    }
                    else {
                        moveSequence = _userInterface.selectValidMove(board.getValidBarMoves(activePlayer));
                    }

                    board.moveChecker(moveSequence, activePlayer);
                    activePlayer.updateAvailableMoves(moveSequence);
                    _userInterface.printMoves(moveSequence);
                    _userInterface.printBoard(board, _playerRed, _playerWhite,activePlayer.getNumber(), matchNumber, gameLength);
                    _userInterface.printDashboard(activePlayer);
                }

                if(!board.isMatchOver(_playerRed, _playerWhite)) {
                    // After the active player finishes their turn, switch to the next player:
                    activePlayer = switch (activePlayer.getColour()) {
                        case WHITE -> _playerRed;
                        case RED -> _playerWhite;
                        default -> activePlayer;
                    };
                    _userInterface.finishTurn();
                }
            }

            _userInterface.printBoard(board, _playerRed, _playerWhite,activePlayer.getNumber(), matchNumber, gameLength);

            if(!board.isMatchOver(_playerRed, _playerWhite)) {
                _userInterface.printDashboard(activePlayer);
                currentCommand = _userInterface.getCommand(activePlayer);
            }
        }while(!currentCommand.isQuit() && !board.isMatchOver(_playerRed, _playerWhite));

        _playerWhite.clearAvailableMoves();
        _playerRed.clearAvailableMoves();

        if(currentCommand.isQuit()){
            _userInterface.printQuit(activePlayer);
            finalScore = -1;
        }
        else if(board.isMatchOver(_playerRed, _playerWhite)) {
            winningPlayer = board.getWinner(_playerRed,_playerWhite, activePlayer);
            winningPlayer.addScore(board.getMatchScore(_playerRed, _playerWhite));
            _userInterface.printWinner(winningPlayer, _playerRed, _playerWhite, matchNumber, gameLength, board);
            finalScore = winningPlayer.getScore();
        }

        return finalScore;
    }
}
