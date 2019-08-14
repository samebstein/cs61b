package byow.Core;

import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;
import byow.lab12.Position;
import edu.princeton.cs.algs4.RunLength;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

import java.util.ArrayList;
import java.util.Random;

public class RandomGen {
    private static final int WIDTH = 80;
    private static final int HEIGHT = 30;

    private static final long SEED = 1123;
    public static long seed = 1412;
    private static final Random RANDOM = new Random(seed);


    public static void initialize(TETile[][] world) {
        MapGenerator m = new MapGenerator(world);
        generateEnclosures(m, world);
        connectAll(m.arr, world, m);
        handleBugs(world);
    }


    private static void connectAll(ArrayList<Position> arr, TETile[][] world, MapGenerator m) {
        for (int i = 0; i < arr.size() - 1; i += 1) {
            for (int j = 0; j < arr.size(); j += 1)
            connectHallway(arr.get(i), arr.get(j), m, world);
        }
    }

    /** Connects two positions on one plane with a hallway. */
    private static void connectHallway(Position p1, Position p2, MapGenerator m, TETile[][] world) {
        int diff;
        if (p1.x - p2.x == 0 && p1.y - p2.y != 0) {
            diff = p1.y - p2.y;
            if (diff > 0) {
                m.hallway(world, p1, diff, Direction.DOWN);
            } else {
                m.hallway(world, p1, -diff, Direction.UP);
            }
        } else if (p1.y - p2.y == 0 && p1.x - p2.x != 0) {
            diff = p1.x - p2.x;
            if (diff > 0) {
                m.hallway(world, p1, diff, Direction.LEFT);
            } else {
                m.hallway(world, p1, -diff, Direction.RIGHT);
            }
        } else {
            connectDoubleHallway(p1, p2, m, world);
        }
    }

    /** Connects two points that are not on either of the same x, y planes */
    private static void connectDoubleHallway(Position p1, Position p2, MapGenerator m, TETile[][] world) {
        int xDiff = p1.x - p2.x;
        int yDiff = p1.y - p2.y;
        Position temp;

        if (xDiff > 0) {

            m.hallway(world, p1, xDiff, Direction.LEFT);
            if (yDiff > 0) {
                temp = new Position(p2.x, p1.y);
                m.hallway(world, p2, yDiff, Direction.UP);
            } else {
                m.hallway(world, p2, -yDiff, Direction.DOWN);
            }
        } else {
            m.hallway(world, p1, -xDiff, Direction.RIGHT);
            if (yDiff > 0) {
                m.hallway(world, p2, yDiff, Direction.UP);
            } else {
                m.hallway(world, p2, -yDiff, Direction.DOWN);
            }
        }
    }



    private static void handleBugs(TETile[][] world) {
        handleOverlapping(world);
        handleConnection(world);
    }

    private static void handleConnection(TETile[][] world) {
        for (int y = 1; y < HEIGHT - 5; y += 1) {
            for (int x = 1; x < WIDTH - 5; x += 1) {
                if (world[x + 1][y] == Tileset.WALL && world[x + 2][y] == Tileset.WALL
                        && world[x + 3][y] == Tileset.FLOOR && world[x][y] == Tileset.FLOOR) {
                    world[x + 1][y] = Tileset.FLOOR;
                    world[x + 2][y] = Tileset.FLOOR;
                }
                if (world[x][y + 1] == Tileset.WALL && world[x][y + 2] == Tileset.WALL
                        && world[x][y + 3] == Tileset.FLOOR && world[x][y] == Tileset.FLOOR) {
                    world[x][y + 1] = Tileset.FLOOR;
                    world[x][y + 2] = Tileset.FLOOR;
                }
                if (world[x][y] == Tileset.FLOOR) {
                    if (world[x][y + 1] == Tileset.NOTHING || world[x][y - 1] == Tileset.NOTHING
                            || world[x + 1][y] == Tileset.NOTHING || world[x - 1][y] == Tileset.NOTHING) {
                        world[x][y] = Tileset.WALL;
                    }
                }
            }
        }
    }
    private static void handleOverlapping(TETile[][] world) {
        for (int y = 1; y < HEIGHT - 5; y += 1) {
            for (int x = 1; x < WIDTH - 5; x += 1) {
                if (world[x + 1][y] == Tileset.FLOOR && world[x - 1][y] == Tileset.FLOOR
                        && world[x][y] == Tileset.WALL) {
                    world[x][y] = Tileset.FLOOR;
                }
                if (world[x][y + 1] == Tileset.FLOOR && world[x][y - 1] == Tileset.FLOOR
                        && world[x][y] == Tileset.WALL) {
                    world[x][y] = Tileset.FLOOR;
                }
            }
        }
    }

    private static void generateEnclosures(MapGenerator m, TETile[][] world) {
        int numEnclosures = RandomUtils.uniform(RANDOM, 7,15);
        for (int i = 0; i < numEnclosures; i += 1) {
            m.createEnclosure(world, randomPosition(), randomLength(), randomLength());
        }
    }

    private static int randomLength() {
        return RandomUtils.uniform(RANDOM, 4, 10);
    }

    private static Position randomPosition() {
        int x = RandomUtils.uniform(RANDOM, 5,WIDTH - 8);
        int y = RandomUtils.uniform(RANDOM, 5,HEIGHT - 8);
        return new Position(x, y);
    }


    private static Direction randomDirection() {
        int direction = RANDOM.nextInt(4);
        switch (direction) {
            case 0: return Direction.UP;
            case 1: return Direction.DOWN;
            case 2: return Direction.RIGHT;
            case 3: return Direction.LEFT;
            default: return Direction.LEFT;
        }
    }
}
