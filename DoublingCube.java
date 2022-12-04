// Team 1 Backgammon Project
/**
 * Class modelling the doubling cube.
 * @author Cian O'Mahoney  GitHub:cian-omahoney  SN:19351611
 * @author Ciarán Cullen   GitHub:TangentSplash  SN:19302896
 * @version 1 2022-12-03
 */
public class DoublingCube {
    private int _doublingCubeValue;
    private boolean _isDoubleRefused;
    private Checker _doublingCubeOwner;

    /**
     * DoublingCube constructor. Initial value is 1.
     */
    public DoublingCube() {
        this._doublingCubeValue = 1;
        this._isDoubleRefused = false;
        this._doublingCubeOwner = Checker.EMPTY;
    }

    /**
     * Double stakes on doubling cube.
     */
    public void doubleStakes() {
        _doublingCubeOwner = switch (_doublingCubeOwner) {
            case RED -> Checker.WHITE;
            case WHITE -> Checker.RED;
            default -> Checker.EMPTY;
        };
        _doublingCubeValue = _doublingCubeValue*2;
    }

    /**
     * Check is the activePlayer the current owner of the doubling cube.
     * @param activePlayer Current player.
     * @return If active player is doubling cube owner, return true. Otherwise return false.
     */
    public boolean isDoublingCubeOwner(Player activePlayer) {
        boolean isDoublingCubeOwner = false;
        if(_doublingCubeOwner == activePlayer.getColour()){
            isDoublingCubeOwner = true;
        }
        else if(_doublingCubeOwner == Checker.EMPTY) {
            isDoublingCubeOwner = true;
            _doublingCubeOwner = activePlayer.getColour();
        }
        return isDoublingCubeOwner;
    }

    /**
     * Set the _isDoubleRefused field if the double is refused by the other player.
     */
    public void doubleRefused() {
        _isDoubleRefused = true;
    }

    /**
     * ToString method for doubling cube.
     * @param player One of two players. Will check if player is cube owner.
     * @return String representing doubling cube.
     */
    public String doublingCubeToString(Player player) {
        String doublingCubeString = "[" + _doublingCubeValue + "]";
        if(player.getColour() != _doublingCubeOwner) {
            doublingCubeString = "---";
        }
        return doublingCubeString;
    }

    /**
     * ToString method for doubling cube.
     * @return String representing doubling cube.
     */
    public String doublingCubeToString() {
        String doublingCubeString = "[" + _doublingCubeValue + "]";
        if(_doublingCubeOwner != Checker.EMPTY) {
            doublingCubeString = "   ";
        }
        return doublingCubeString;
    }

    /**
     * Return doubling cube value.
     * @return Current doubling cube value.
     */
    public int getDoublingCubeValue() {
        return _doublingCubeValue;
    }

    /**
     * Check has doubling been refused.
     * @return True if doubling has been refused. False otherwise.
     */
    public boolean isDoublingRefused() {
        return _isDoubleRefused;
    }
}
