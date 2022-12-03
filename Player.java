// Team 1 Backgammon Project
import java.util.*;

/**
 * Class representation of a Player - holds the name, colour checker used, score and rolls dice.
 * @author Cian O'Mahoney  GitHub:cian-omahoney  SN:19351611
 * @author Ciarán Cullen   GitHub:TangentSplash  SN:19302896
 * @version 1 2022-12-03
 */
public class Player{
    private String _name;
    private final Checker _playerColour;
    private Dice _firstDie;
    private Dice _secondDie;
    private List<Integer> _availableMoves;
    private int _number;
    private int _score;

    public Player(Checker playerColour,int number){
        this._name="";
        this._firstDie = new Dice();
        this._secondDie = new Dice();
        this._playerColour = playerColour;
        this._availableMoves = new ArrayList<>();
        this._number = number;
        this._score = 0;
    }

    public void rollBothDice(){
        _availableMoves.add(_firstDie.roll());
        _availableMoves.add(_secondDie.roll());
        if(_secondDie.getRollValue() == _firstDie.getRollValue()) {
            _availableMoves.add(_firstDie.getRollValue());
            _availableMoves.add(_firstDie.getRollValue());
        }
        _availableMoves.sort(Collections.reverseOrder());
    }

    public void addAvailableMove(Integer diceValue){
        _availableMoves.add(diceValue);
        _availableMoves.sort(Collections.reverseOrder());
    }

    public void addAvailableMovesMultiple(List<Integer> diceValues) {
        _availableMoves.addAll(diceValues);
        // If double:
        if(Objects.equals(diceValues.get(0), diceValues.get(1))) {
            _availableMoves.addAll(diceValues);
        }
        _availableMoves.sort(Collections.reverseOrder());
    }

    public List<Integer> getAvailableMoves() {
        return _availableMoves;
    }

    public boolean availableMovesRemaining() {
        return !_availableMoves.isEmpty();
    }

    public void clearAvailableMoves() {
        _availableMoves = new ArrayList<>();
    }

    // Update the available dice moves after using dice to complete moveSequence.
    public void updateAvailableMoves(List<Integer> moveSequence) {
        int diceValueUsed;
        int sourcePoint;

        if(moveSequence.size() > 0) {
            sourcePoint = moveSequence.get(0);
            for(int destinationPoint : moveSequence) {
                diceValueUsed = sourcePoint - destinationPoint;
                sourcePoint = destinationPoint;
                if(diceValueUsed > 0) {
                    if(availableMovesRemaining()) {
                        if(_availableMoves.contains(diceValueUsed)) {
                            _availableMoves.remove(Integer.valueOf(diceValueUsed));
                        }
                        else{
                            _availableMoves.remove(Collections.max(_availableMoves));
                        }
                    }
                }
            }
        }
        else {
            _availableMoves = new ArrayList<>();
        }
    }

    public String getName(){
        return _name;
    }
    
    public Checker getColour() {
    	return _playerColour;
    }

    /**
     * Set the players name
	 * @param name the name of the player  
	 * */
    public void setName(String name) {
        _name = name;
    }

    public int getNumber(){
        return _number;
    }

    public int getScore() {
        return _score;
    }

    public void addScore(int gainedPoints) {
        _score += gainedPoints;
    }
}