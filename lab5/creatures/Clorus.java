package creatures;
import huglife.*;
import org.junit.Test;
import static org.junit.Assert.*;
import static huglife.HugLifeUtils.random;
import static huglife.HugLifeUtils.randomEntry;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashMap;
import java.awt.Color;
import java.util.Map;

/** Implementing Clorus, the blue predator that kills plips */

public class Clorus extends Creature {

    /**
     * red color.
     */
    private int r;
    /**
     * green color.
     */
    private int g;
    /**
     * blue color.
     */
    private int b;

    /**
     * creates clorus with energy equal to e.
     */
    public Clorus(double e) {
        super("clorus");
        r = 34;
        g = 0;
        b = 231;
        energy = e;
    }


    /**
     * Returns color of r=34, g=0, b=231.
     */
    public Color color() {
        r = 34;
        g = 0;
        b = 231;
        return color(r, g, b);
    }

    public void attack(Creature c) {
        energy += c.energy();
    }

    /**
     * Lose 0.03 energy when clorus moves
     */
    public void move() {
        energy -= 0.03;
    }

    /**
     * Lose 0.01 energy when clorus stays
     */
    public void stay() {
        energy -= 0.01;
    }

    public Clorus replicate() {
        energy = energy * 0.5;
        return new Clorus(energy);
    }

    public Action chooseAction(Map<Direction, Occupant> neighbors) {
        Deque<Direction> emptyNeighbors = new ArrayDeque<>();
        Deque<Direction> plipNeighbors = new ArrayDeque<>();


        // 1) No empty adjacent spaces; stay.
        for (Map.Entry<Direction, Occupant> entry : neighbors.entrySet()) {
            if (entry.getValue().name().equals("empty")) {
                emptyNeighbors.add(entry.getKey());
            } else if (entry.getValue().name().equals("plip")) {
                plipNeighbors.add(entry.getKey());
            }
        }

        if (emptyNeighbors.size() == 0) {
            return new Action(Action.ActionType.STAY);
        }

        // 2) If any plips in surrounded (also, there must be an empty adjacent space), will attack
        else if (plipNeighbors.size() > 0) {
            return new Action(Action.ActionType.ATTACK, randomEntry(plipNeighbors));
        }

        // 3) If no plips, 1 or more empty spaces, energy >= 1, will replicated to random empty square
        else if (energy() >= 1) {
            return new Action(Action.ActionType.REPLICATE, randomEntry(emptyNeighbors));
        }

        // 4) energy < 1, empty spaces, no plips, will move to random empty square
        // if (energy() < 1 && !anyPlip && emptyNeighbors.size() > 0)
        else {
            return new Action(Action.ActionType.MOVE, randomEntry(emptyNeighbors));
        }
    }
}
