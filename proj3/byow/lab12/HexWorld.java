package byow.lab12;
import org.junit.Test;
import static org.junit.Assert.*;

import byow.TileEngine.TERenderer;
import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;

import java.util.HashMap;
import java.util.Random;

/**
 * Draws a world consisting of hexagonal regions.
 */
public class HexWorld {
    private static final int WIDTH = 80;
    private static final int HEIGHT = 80;

    public static void addHexagon(TETile[][] world, Position p, int s, TETile t) {
        insertBottomHalf(world, p, s, t);
        insertTopHalf(world, p, s, t);
    }

    public static void addTesselation(TETile[][] world, Position p, int s) {
        leftHalf(world, p, s);
        rightHalf(world, p, s);
    }


    private static void rightHalf(TETile[][] world, Position p, int s) {
        HashMap<Integer, TETile> hashMap = new HashMap<>();
        hashMap.put(0, Tileset.AVATAR);
        hashMap.put(1, Tileset.TREE);

        Position temp = new Position(p.x + 20, p.y);
        int height = s;
        for (int i = 0; i < s - 1; i += 1) {
            addColumn(world, temp, s, hashMap.get(i), height);
            temp = new Position(temp.x - 5, temp.y - 3);
            height += 1;
        }

    }

    private static void leftHalf(TETile[][] world, Position p, int s) {
        HashMap<Integer, TETile> hashMap = new HashMap<>();
        hashMap.put(0, Tileset.AVATAR);
        hashMap.put(1, Tileset.TREE);
        hashMap.put(2, Tileset.FLOWER);

        int height = s;
        Position temp = p;
        for (int i = 0; i < s; i += 1) {
            addColumn(world, temp, s, hashMap.get(i),height );
            temp = new Position(temp.x + (2 * s - 1), temp.y - s);
            height += 1;
        }
    }

    private static void addColumn(TETile[][] world, Position p, int s, TETile t, int height) {
        Position temp = p;
        int hexIncrement = s;
        for (int i = 0; i < height; i += 1) {
            addHexagon(world, temp, hexIncrement, t);
            temp = new Position(temp.x, temp.y + 2 * hexIncrement);
        }
    }

    private static void insertBottomHalf(TETile[][] world, Position p, int s, TETile t) {
        Position tempP = p;
        int length = s;
        for (int i = 0; i < s; i += 1){
            addRow(world, tempP, length, t);
            tempP = new Position(tempP.x - 1, tempP.y + 1);
            length += 2;
        }
    }

    private static void insertTopHalf(TETile[][] world, Position p, int s, TETile t) {
        Position tempP = new Position(p.x, p.y + 2 * s - 1);
        int length = s;
        for (int i = 0; i < s; i += 1) {
            addRow(world, tempP, length, t);
            tempP = new Position(tempP.x - 1, tempP.y - 1);
            length += 2;
        }
    }


    /** This function will add the same length rows to their symmetrical spots in the hexagon. */
    private static void addRow(TETile[][] world, Position p, int length, TETile t) {
        Position temp = p;
        for (int i = 0; i < length; i += 1) {
            world[temp.x][temp.y] = t;
            temp = new Position(temp.x + 1, temp.y);
        }
    }








    public static void main(String[] args) {
        // initialize the tile rendering engine with a window of size WIDTH x HEIGHT
        TERenderer ter = new TERenderer();
        ter.initialize(WIDTH, HEIGHT);

        // initialize tiles
        TETile[][] world = new TETile[WIDTH][HEIGHT];
        for (int x = 0; x < WIDTH; x += 1) {
            for (int y = 0; y < HEIGHT; y += 1) {
                world[x][y] = Tileset.NOTHING;
            }
        }
        Position p = new Position(30, 45);
        HexWorld.addTesselation(world, p, 3);

        // draws the world to the screen
        ter.renderFrame(world);
    }
}

