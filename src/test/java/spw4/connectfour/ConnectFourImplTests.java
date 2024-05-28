package spw4.connectfour;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.*;
import org.mockito.junit.jupiter.*;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class ConnectFourImplTests {
    private ConnectFourImpl connectFour;
    @BeforeEach
    void setUp() {
        connectFour = new ConnectFourImpl(Player.red);
    }

    @Test
    void playerIsRedOnInit(){

        assertEquals(Player.red, connectFour.getPlayerOnTurn());
    }
    @Test
    void toStringNotEmptyAndLengthGreaterZero(){

        String result = connectFour.toString();
        assertNotNull(result);
        assertFalse(result.isEmpty());

    }
    @Test
    void toStringReturnCorrectRowsCount(){

        String result = connectFour.toString();
        String[] lines = result.split("\n");
        assertEquals(8, lines.length);

    }
    @Test
    void playerSwitchesOnDrop(){

        connectFour.drop(0);
        assertEquals(Player.yellow, connectFour.getPlayerOnTurn());
    }
    @Test
    void playerSwitchesOnRepeatedDrops(){
        int randomDrops = new Random().nextInt(10);
        for (int i = 0; i < randomDrops; i++) {
            connectFour.drop(i%7);
            if(i%2==0){
                assertEquals(Player.yellow, connectFour.getPlayerOnTurn());
            }else {
                assertEquals(Player.red, connectFour.getPlayerOnTurn());
            }
        }
    }
    @Test
    void dropCoinInBoard(){
        connectFour.drop(0);
        assertEquals('R', connectFour.getCharAt(5,0));
    }
    @Test
    void dropCoinColumnFull(){
        dropMany(0 , 5);
        assertFalse(connectFour.isColumnFull(0));
        dropMany(0 , 1);
        assertTrue(connectFour.isColumnFull(0));
    }
    @Test
    void dropCoinColumnOverflow(){
        dropMany(0 , 6);
        assertTrue(connectFour.isColumnFull(0));
        dropMany(0 , 1);
        assertTrue(connectFour.isColumnFull(0));
    }
    @Test
    void dropCoinColumnOverflowAndPlayerSwitchesRow(){
        dropMany(0 , 6);
        assertEquals(Player.red,connectFour.getPlayerOnTurn());
        dropMany(0 , 1);
        assertEquals(Player.red,connectFour.getPlayerOnTurn());
        dropMany(1 , 1);
        assertEquals(Player.yellow,connectFour.getPlayerOnTurn());

    }
    @Test
    void emptyBoardPlaceReturnUnknown(){
        assertEquals(Player.none, connectFour.getPlayerAt(0,0));
    }

    @Test
    void dropCoinReturnCorrectPlayerRed(){
        connectFour.drop(0);
        assertEquals(Player.red, connectFour.getPlayerAt(5,0));
    }

    @Test
    void dropCoinReturnCorrectPlayerYellow(){
        connectFour.drop(0);
        connectFour.drop(1);
        assertEquals(Player.yellow, connectFour.getPlayerAt(5,1));
    }

    @Test
    void boardIsFullReturnGameOver(){
        for (int i = 0; i < 7; i++) {
            dropMany(i, 6);
        }
        assertTrue(connectFour.isGameOver());
    }

    @Test
    void boardIsFullAndNoOneWins(){
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                dropMany(j, 2);
            }
        }

        for (int i = 0; i < 2; i++) {
            for (int j = 4; j < 7; j++) {
                dropMany(j, 2);
            }
        }
        dropMany(4, 2);
        dropMany(5, 2);
        dropMany(6, 1);
        dropMany(3, 6);
        dropMany(6, 1);

        assertTrue(connectFour.isGameOver());
        assertEquals(Player.none, connectFour.getWinner());
    }


    @Test
    void playerRedWinsWithFullRow(){
        for (int i = 0; i < 3; i++) {
            dropMany(i, 2);
        }
        dropMany(3, 1);

        assertEquals(Player.red, connectFour.getWinner());
        assertTrue(connectFour.isGameOver());
    }

    @Test
    void playerYellowWinsWithFullRow(){
        dropMany(5, 1);
        for (int i = 0; i < 4; i++) {
            dropMany(i, 2);
        }

        assertEquals(Player.yellow, connectFour.getWinner());
        assertTrue(connectFour.isGameOver());
    }

    @Test
    void playerRedWinsWithFullColumn(){
        for (int i = 0; i < 3; i++) {
            dropMany(0, 1);
            dropMany(1, 1);
        }
        dropMany(0, 1);

        assertEquals(Player.red, connectFour.getWinner());
        assertTrue(connectFour.isGameOver());
    }

    @Test
    void playerYellowWinsWithFullColumn(){
        dropMany(5, 1);
        for (int i = 0; i < 3; i++) {
            dropMany(0, 1);
            dropMany(1, 1);
        }
        dropMany(0, 1);

        assertEquals(Player.yellow, connectFour.getWinner());
        assertTrue(connectFour.isGameOver());
    }

    @Test
    void playerRedWinsWithDiagonal(){
        dropMany(0, 1);
        dropMany(1, 1);
        dropMany(1, 1);
        dropMany(2, 1);
        dropMany(2, 1);
        dropMany(3, 1);
        dropMany(2, 1);
        dropMany(3, 1);
        dropMany(3,1);
        dropMany(5,1);
        dropMany(3,1);

        assertEquals(Player.red, connectFour.getWinner());
        assertTrue(connectFour.isGameOver());
    }

    @Test
    void playerRedWinsWithInvertedDiagonal(){
        dropMany(3, 1);
        dropMany(2, 1);
        dropMany(2, 1);
        dropMany(1, 1);
        dropMany(1, 1);
        dropMany(0, 1);
        dropMany(1, 1);
        dropMany(0, 1);
        dropMany(0, 1);
        dropMany(5, 1);
        dropMany(0, 1);

        assertEquals(Player.red, connectFour.getWinner());
        assertTrue(connectFour.isGameOver());
    }

    @Test
    void playerYellowWinsWithDiagonal(){
        dropMany(6, 1);
        dropMany(0, 1);
        dropMany(1, 1);
        dropMany(1, 1);
        dropMany(2, 1);
        dropMany(2, 1);
        dropMany(3, 1);
        dropMany(2, 1);
        dropMany(3, 1);
        dropMany(3,1);
        dropMany(5,1);
        dropMany(3,1);

        assertEquals(Player.yellow, connectFour.getWinner());
        assertTrue(connectFour.isGameOver());
    }

    @Test
    void playerYellowWinsWithInvertedDiagonal(){
        dropMany(5, 1);
        dropMany(3, 1);
        dropMany(2, 1);
        dropMany(2, 1);
        dropMany(1, 1);
        dropMany(1, 1);
        dropMany(0, 1);
        dropMany(1, 1);
        dropMany(0, 1);
        dropMany(0, 1);
        dropMany(5, 1);
        dropMany(0, 1);

        System.out.println(connectFour);


        assertEquals(Player.yellow, connectFour.getWinner());
        assertTrue(connectFour.isGameOver());
    }

    @Test
    void resetGameRedStarts(){
        connectFour.drop(0);
        connectFour.reset(Player.red);
        assertEquals(Player.red, connectFour.getPlayerOnTurn());
        assertEquals(Player.none, connectFour.getPlayerAt(5,0));
    }

    private void dropMany(int col, int times) {
        for (int i = 0; i < times; i++) {
            connectFour.drop(col);
        }
    }
    
}
