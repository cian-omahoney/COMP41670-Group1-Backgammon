// Team 1 Backgammon Project
// By 
/***@author Cian O'Mahoney Github:cian-omahoney 
 *  @author Ciarán Cullen  Github:TangentSplash
*/

//Class representing a game (Which can include multiple matches)
public class Game {
    private UI _userInterface;
    private Player _playerWhite;
    private Player _playerRed;
    private int _gameLength;
    private int _currentMatchNumber;
    private Match _currentMatch;

    public Game (UI userInterface) {
        this._userInterface = userInterface;
        this._playerRed = new Player(Checker.RED,1);
        this._playerWhite = new Player(Checker.WHITE,0);
        this._currentMatch = new Match(_playerWhite, _playerRed, userInterface);
        this._currentMatchNumber = 1;
        this._gameLength = 1;
    }

    public boolean play() {
        boolean isPlayAgain;
        int maximumScore;

        _userInterface.printBackgammonIntro();
        _userInterface.getPlayerNames(_playerRed, _playerWhite);
        _gameLength = _userInterface.getGameLength();
        _userInterface.printGameIntro(_playerRed, _playerWhite);

        do{
            maximumScore = _currentMatch.playMatch(_currentMatchNumber, _gameLength);
            _currentMatchNumber++;
        }while(_currentMatchNumber <= _gameLength && maximumScore < _gameLength && maximumScore > 0);

        if(maximumScore <= 0) {
            isPlayAgain = false;
        }
        else{
            isPlayAgain = _userInterface.playAnotherGame();
        }
        return  isPlayAgain;
    }
}
