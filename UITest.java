import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class UITest {
    private UI _UI;
    private static final String CLEAR_SCREEN 		= "\033[H\033[2J";
    public static final String  CLEAR_COLOURS 		= "\033[0m";
    private static final String YELLOW_TEXT_COLOUR 	= "\033[1;33m";
    private static final String CYAN_TEXT_COLOUR 	= "\033[1;36m";
    private static final String MAGENTA_TEXT_COLOUR = "\033[1;35m";
    private static final String UNDERLINE_TEXT      = "\033[1;4m";
    private static final String BOLD_TEXT			= "\033[1;1m";
    private static final String DASH_LINE = YELLOW_TEXT_COLOUR + "=".repeat(85) + CLEAR_COLOURS;

    @BeforeEach
    void setUp(){
        _UI=new UI();
    }

    @Test
    void testGetCommand(Player player) {
        Command command;
    }

    @Test //No input???
    void getLine() {
        String userInputLine;
    }

    @Test
    void testSelectValidMove(List<ArrayList<Integer>> validMoveList){

        List<Integer> chosenValidMove;
    }

    @Test
    void testGetFirstRoll(Player playerRed, Player playerWhite) {
        Player selectedPlayer;

    }
}
