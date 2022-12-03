// Team 1 Backgammon Project
/**
 * A {@code Bar} object models the bar.
 * @author Cian O'Mahoney  GitHub:cian-omahoney  SN:19351611
 * @author Ciarán Cullen   GitHub:TangentSplash  SN:19302896
 * @version 1 2022-12-03
*/

// This class stores a particular players checkers when they are sent to the Bar.
// This class extends the concrete Point class.
public class Bar extends Point {
    private Checker _barColour;

    /**
     * Bar constructor.
     * @param barColour Colour of checkers assigned to this bar.
     */
    public Bar(Checker barColour){
        //One 'bar per player', one of the bars will be shown on the top, the other on the bottom
        super(Board.BAR_PIP_NUMBER);
        this._barColour = barColour;
    }

    /**
     * Overrides addCheckers to add the same relevant colour each time
     */
    public void addCheckers() {
        super.addCheckers(_barColour);
    }

    /**
     * Return resident colour of bar.
     * @return Resident Colour of bar checker.
     */
    public Checker getResidentColour(){
        return _barColour;
    }
}
