package creatures;
import org.junit.Test;
import static org.junit.Assert.*;
import java.util.HashMap;
import java.awt.Color;
import huglife.Direction;
import huglife.Action;
import huglife.Occupant;
import huglife.Impassible;
import huglife.Empty;

/** Tests the Clorus class
 * @author Sam Ebstein
 */

public class TestClorus  {

    @Test
    public void testBasics() {
        Clorus c = new Clorus(1);
        assertEquals(c.name(), "clorus");
        assertEquals(1, c.energy(), 0.01);
        assertEquals(new Color(34, 0, 231), c.color());
        c.move();
        assertEquals(.97, c.energy(), 0.01);
        c.move();
        assertEquals(.94, c.energy(), 0.01);
        c.stay();
        assertEquals(.93, c.energy(), 0.01);
        c.stay();
        assertEquals(.92, c.energy(), 0.01);
    }

    @Test
    public void testChoose() {
        // 1) No empty adjacent spaces; stay.
        Clorus c1 = new Clorus(1);
        HashMap<Direction, Occupant> surrounded = new HashMap<Direction, Occupant>();
        surrounded.put(Direction.TOP, new Impassible());
        surrounded.put(Direction.BOTTOM, new Impassible());
        surrounded.put(Direction.LEFT, new Impassible());
        surrounded.put(Direction.RIGHT, new Impassible());

        Action actual = c1.chooseAction(surrounded);
        Action expected = new Action(Action.ActionType.STAY);
        assertEquals(expected, actual);

        // 2) If any plips in surrounded (also, there must be an empty adjacent space)
        Clorus c2 = new Clorus(1);
        HashMap<Direction, Occupant> twoEmptyTwoPlip = new HashMap<Direction, Occupant>();
        twoEmptyTwoPlip.put(Direction.TOP, new Empty());
        twoEmptyTwoPlip.put(Direction.BOTTOM, new Empty());
        twoEmptyTwoPlip.put(Direction.LEFT, new Empty());
        twoEmptyTwoPlip.put(Direction.RIGHT, new Plip());
        Action actual2 = c2.chooseAction(twoEmptyTwoPlip);
        Action expected2 = new Action(Action.ActionType.ATTACK, Direction.RIGHT);
        assertEquals(actual2, expected2);


        // 3) If no plips, 1 or more empty spaces, energy >= 1, will replicated to random empty square
        Clorus c3 = new Clorus(1);
        HashMap<Direction, Occupant> emptySpaceNoPlip = new HashMap<Direction, Occupant>();
        emptySpaceNoPlip.put(Direction.TOP, new Empty());
        emptySpaceNoPlip.put(Direction.BOTTOM, new Empty());
        emptySpaceNoPlip.put(Direction.LEFT, new Empty());
        emptySpaceNoPlip.put(Direction.RIGHT, new Empty());
        Action actual3 = c3.chooseAction(emptySpaceNoPlip);
        assertEquals(Action.ActionType.REPLICATE, actual3.type);

        // 4) energy < 1, empty spaces, no plips, will move to random empty square
        Clorus c4 = new Clorus(0.5);
        HashMap<Direction, Occupant> emptySpaceNoEnergy = new HashMap<Direction, Occupant>();
        emptySpaceNoEnergy.put(Direction.TOP, new Empty());
        emptySpaceNoEnergy.put(Direction.BOTTOM, new Empty());
        emptySpaceNoEnergy.put(Direction.LEFT, new Empty());
        emptySpaceNoEnergy.put(Direction.RIGHT, new Empty());
        Action actual4 = c4.chooseAction(emptySpaceNoPlip);
        assertEquals(actual4.type, Action.ActionType.MOVE);
    }

    @Test
    public void testReplicate() {
        Clorus c = new Clorus(1);
        Clorus childOfC= c.replicate();
        assertEquals(0.5, c.energy(), 0.01);
        assertEquals(0.5, childOfC.energy(), 0.01);
        Clorus childOfChild = childOfC.replicate();
        assertEquals(0.25, childOfChild.energy(), 0.01);
        assertNotEquals(c, childOfC);
    }







}
