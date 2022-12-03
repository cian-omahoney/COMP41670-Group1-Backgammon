// Team 1 Backgammon Project
/**
 * Class representing a game (Which can include multiple matches).
 * @author Cian O'Mahoney  GitHub:cian-omahoney  SN:19351611
 * @author Ciarán Cullen   GitHub:TangentSplash  SN:19302896
 * @version 1 2022-12-03
 */
public class Game {
    private UI _userInterface;
    private Player _playerWhite;
    private Player _playerRed;
    private int _gameLength;
    private int _currentMatchNumber;
    private Match _currentMatch;

    /**
     * Game constructor.
     * @param userInterface Current user interface object.
     */
    public Game (UI userInterface) {
        this._userInterface = userInterface;
        this._playerRed = new Player(Checker.RED,1);
        this._playerWhite = new Player(Checker.WHITE,0);
        this._currentMatch = new Match(_playerWhite, _playerRed, userInterface);
        this._currentMatchNumber = 1;
        this._gameLength = 1;
    }

    /**
     * Play full backgammon game between two players, consisting of multiple matches.
     * @return True if another game is to be player, false otherwise.
     */
    public boolean play() {
        boolean isPlayAgain;
        int maximumScore;

        _userInterface.printBackgammonIntro();
        _userInterface.getPlayerNames(_playerRed, _playerWhite);
        _gameLength = _userInterface.getGameLength();
        _userInterface.printGameIntro(_playerRed, _playerWhite);

        // While no player has reached the required score to win, play another match.
        do{
            maximumScore = _currentMatch.playMatch(_currentMatchNumber, _gameLength);
            _currentMatchNumber++;
        }while(_currentMatchNumber <= _gameLength && maximumScore < _gameLength && maximumScore > 0);

        // If the maximumScore returned is -1, the match was quit and the whole game must be quit.
        if(maximumScore <= 0) {
            isPlayAgain = false;
        }
        else{
            // Ask the user if they want to play another game.
            isPlayAgain = _userInterface.playAnotherGame();
        }
        return  isPlayAgain;
    }
}
