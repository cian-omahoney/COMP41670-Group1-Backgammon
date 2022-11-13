public class Bar extends Point {
    private Checker _barColour;

    public Bar(Checker barColour){ //One 'bar per player', one of the bars will be shown on the top, the other on the bottom
        super(Board.BAR_PIP_NUMBER);
        this._barColour = barColour;
    }

    public void addCheckers() {
        super.addCheckers(_barColour);
    }

    public Checker getResidentColour(){
        return _barColour;
    }
}
