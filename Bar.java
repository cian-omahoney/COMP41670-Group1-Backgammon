// Team 1 Backgammon Project
// By 
/***@author Cian O'Mahoney Github:cian-omahoney 
 *  @author Ciarán Cullen  Github:TangentSplash
*/

// A class that stores a particular players checkers when they are sent to the Bar
public class Bar extends Point {
    private Checker _barColour;

    public Bar(Checker barColour){
        //One 'bar per player', one of the bars will be shown on the top, the other on the bottom
        super(Board.BAR_PIP_NUMBER);
        this._barColour = barColour;
    }

    //Overide addCheckers to add the relevant colour each time
    public void addCheckers() {
        super.addCheckers(_barColour);
    }

    public Checker getResidentColour(){
        return _barColour;
    }
}
