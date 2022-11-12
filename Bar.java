public class Bar extends Point {
    private Checker _barColour;
    public static final int BAR_POINT_NUMBER = 25;
    public Bar(Checker barColour){ //One 'bar per player', one of the bars will be shown on the top, the other on the bottom
        super(BAR_POINT_NUMBER);
        this._barColour = barColour;
    }

    public void addCheckers() {
        super.addCheckers(_barColour);
    }
}
