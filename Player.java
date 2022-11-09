import java.util.*;

public class Player {
    private String _name;
    private final Checker _playerColour;
    private Dice _firstDie;
    private Dice _secondDie;
    private List<Integer> _availableMoves;
    private int _pipCount;

    public Player(Checker playerColour){
        this._name="";
        this._firstDie = new Dice();
        this._secondDie = new Dice();
        this._playerColour = playerColour;
        this._availableMoves = new ArrayList<Integer>();
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


    public String getName(){
        return _name;
    }
    
    public Checker getColour() {
    	return _playerColour;
    }

    public void setName(String name) {
        _name = name;
    }
}