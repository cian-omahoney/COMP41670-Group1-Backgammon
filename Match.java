import java.nio.file.attribute.UserPrincipal;
import java.util.ArrayList;
import java.util.List;

public class Match {
    UI _userInterface;
    Player _playerRed;
    Player _playerWhite;

    public Match(Player playerWhite, Player playerRed, UI userInterface) {
        this._playerWhite = playerWhite;
        this._playerRed = playerRed;
        this._userInterface = userInterface;
    }

    public void playMatch(int matchNumber, int gameLength){
        Board board=new Board();

        List<Integer> moveSequence = new ArrayList<>();
        Player activePlayer;
        Command currentCommand;

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

                while(activePlayer.availableMovesRemaining()) {
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

                // After the active player finishes their turn, switch to the next player:
                activePlayer = switch(activePlayer.getColour()) {
                    case WHITE -> _playerRed;
                    case RED -> _playerWhite;
                    default -> activePlayer;
                };
                _userInterface.finishTurn();
            }

            _userInterface.printBoard(board, _playerRed, _playerWhite,activePlayer.getNumber(), matchNumber, gameLength);

            if(!board.isMatchOver(_playerRed, _playerWhite)) {
                _userInterface.printDashboard(activePlayer);
                currentCommand = _userInterface.getCommand(activePlayer);
            }
        }while(!currentCommand.isQuit() && !board.isMatchOver(_playerRed, _playerWhite));

        if(currentCommand.isQuit()){
            _userInterface.printQuit();
        }

        if(board.isMatchOver(_playerRed, _playerWhite)) {
            _userInterface.printMatchWinner(_playerRed, _playerWhite, board, matchNumber);
        }
    }
}
