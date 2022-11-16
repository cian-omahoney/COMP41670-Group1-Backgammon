 /* UI cannot be tested using JUnit as each method uses get line
 *  This Class prints a basic board in the starting position for 
 * testing purposes.
 */

public class UITest {  
    public static void main(String[] args) {
        //UI ui = new UI();
        Board board =new Board();
        String boardString=board.toString(0);
        System.out.println(boardString);
    }
}
