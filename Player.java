import java.util.*;

public class Player {
    private String _name;
    private final Checker _playerColour;
    private Dice _firstDie;
    private Dice _secondDie;
    private List<Integer> _availableMoves;
    private int _number;

    public Player(Checker playerColour,int number){
        this._name="";
        this._firstDie = new Dice();
        this._secondDie = new Dice();
        this._playerColour = playerColour;
        this._availableMoves = new ArrayList<Integer>();
        this._number=number;
    }

    //TODO pip count - https://www.bkgm.com/gloss/lookup.cgi?pip+count
    public void rollDice(){
        _availableMoves.add(_firstDie.roll());
        _availableMoves.add(_secondDie.roll());
        if(_secondDie.getRollValue() == _firstDie.getRollValue()) {
            _availableMoves.add(_firstDie.getRollValue());
            _availableMoves.add(_firstDie.getRollValue());
        }
    }

    public List<Integer> getAvailableMoves() {
        return _availableMoves;
    }

    public boolean availableMovesRemaining() {
        return !_availableMoves.isEmpty();
    }

    public void updateAvailableMoves(List<Integer> moveSequence) {
        int diceValueUsed = 0;
        int sourcePoint = moveSequence.get(0);
        for(int destinationPoint : moveSequence) {
            diceValueUsed = sourcePoint - destinationPoint;
            sourcePoint = destinationPoint;
            if(diceValueUsed > 0) {
                if(availableMovesRemaining()) {
                    _availableMoves.remove(Integer.valueOf(diceValueUsed));
                }
            }
        }
    }

    public String getName(){
        return _name;
    }
    
    public Checker getColour() {
    	return _playerColour;
    }

    public void setName(String name) {
        _name = name;
    }

    public int getNumber(){
        return _number;
    }
}