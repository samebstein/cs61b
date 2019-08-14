package byow.Core;

import byow.TileEngine.TERenderer;
import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;
import byow.lab12.Position;

import java.util.ArrayList;
import java.util.NoSuchElementException;

public class MapGenerator {

    public static final TETile WALL = Tileset.WALL;
    public static final TETile FLOOR = Tileset.FLOOR;
    private TETile[][] world;
    ArrayList<Position> arr = new ArrayList<>();

    public MapGenerator(TETile[][] world){
        this.world = world;
    }

    /** Creates an enclosed space rectangle in the two dimensional array world,
     * with the position parameter corresponding to the bottom left point. Also,
     * fills in the enclosed space with FLOOR tiles.
     * @param world
     * @param p
     * @param width
     * @param height
     */
    public void createEnclosure(TETile[][] world, Position p,
                                       int width, int height) {
        Position topRight = new Position(p.x + width - 1, p.y + height -1);
        line(world, p, height, Direction.UP, WALL);
        line(world, p, width, Direction.RIGHT, WALL);
        line(world, topRight, height, Direction.DOWN, WALL);
        line(world, topRight, width, Direction.LEFT, WALL);

        Position bottomLeftFloor = new Position(p.x + 1, p.y + 1);
        for (int i = 0; i < width - 2; i += 1) {
            line(world, bottomLeftFloor, height - 2, Direction.UP, FLOOR);
            bottomLeftFloor = new Position(bottomLeftFloor.x + 1, bottomLeftFloor.y);
        }
        arr.add(p);
    }

    /**
     * Creates a hallway that takes in a direction of the hallway. If the direction is up,
     * the position is bottom left. if the direction is down, the position is the top left.
     * If the direction is right, the position is bottom left. If the direction is left,
     * the position is bottom right.
     * @param world
     * @param p
     * @param length
     * @param d
     */
    public void hallway(TETile[][] world, Position p, int length, Direction d) {
        Position start = p;
        Position middle;
        Position opposite;
        if (d == Direction.UP || d == Direction.DOWN) {
            middle = new Position(p.x + 1, p.y);
            opposite = new Position(p.x + 2, p.y);
        } else {
            middle = new Position(p.x, p.y - 1);
            opposite = new Position(p.x, p.y - 2);
        }
        line(world, start, length, d, WALL);
        line(world, middle, length, d, FLOOR);
        line(world, opposite, length, d, WALL);
    }

    /** A descendant of createEnclosure method that creates an enclosure of equal sides. */
    public void createSquare(TETile[][] world, Position p,
                                    int sideLength) {
        createEnclosure(world, p, sideLength, sideLength);
    }

    /**
     * Creates a line that goes either up, down, left or right, depending on the direction parameter.
     * @param world
     * @param p
     * @param length
     * @param d
     * @param t
     */
    public void line(TETile[][] world, Position p, int length, Direction d, TETile t) {
        Position temp = p;
        for (int i = 0; i < length; i += 1) {
            world[temp.x][temp.y] = t;
            if (d == Direction.UP) {;
                temp = new Position(temp.x, temp.y + 1);
            } else if (d == Direction.DOWN) {
                temp = new Position(temp.x, temp.y - 1);
            } else if (d == Direction.RIGHT) {
                temp = new Position(temp.x + 1, temp.y);
            } else {
                temp = new Position(temp.x - 1, temp.y);
            }
        }
    }

}
