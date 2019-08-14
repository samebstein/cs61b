package byow.lab13;

import edu.princeton.cs.introcs.StdDraw;

import java.awt.Color;
import java.awt.Font;
import java.util.Random;

public class MemoryGame {
    private int width;
    private int height;
    private int round;
    private Random rand;
    private boolean gameOver;
    private boolean playerTurn;
    private static final char[] CHARACTERS = "abcdefghijklmnopqrstuvwxyz".toCharArray();
    private static final String[] ENCOURAGEMENT = {"You can do this!", "I believe in you!",
                                                   "You got this!", "You're a star!", "Go Bears!",
                                                   "Too easy for you!", "Wow, so impressive!"};

    public static void main(String[] args) {
        if (args.length < 1) {
            System.out.println("Please enter a seed");
            return;
        }

        int seed = Integer.parseInt(args[0]);
        MemoryGame game = new MemoryGame(40, 40, seed);
        game.startGame();
    }

    public MemoryGame(int width, int height, int seed) {
        /* Sets up StdDraw so that it has a width by height grid of 16 by 16 squares as its canvas
         * Also sets up the scale so the top left is (0,0) and the bottom right is (width, height)
         */
        this.width = width;
        this.height = height;
        StdDraw.setCanvasSize(this.width * 16, this.height * 16);
        Font font = new Font("Monaco", Font.BOLD, 30);
        StdDraw.setFont(font);
        StdDraw.setXscale(0, this.width);
        StdDraw.setYscale(0, this.height);
        StdDraw.clear(Color.BLACK);
        StdDraw.enableDoubleBuffering();
        rand = new Random(seed);

    }

    public String generateRandomString(int n) {
        String ranString = "";
        for (int i = 0; i < n; i += 1) {
            char c = CHARACTERS[rand.nextInt(26)];
            ranString += c;
        }
        return ranString;
    }

    public void drawFrame(String s) {
        //TODO: If game is not over, display relevant game information at the top of the screen
        if (!gameOver) {


            StdDraw.clear(Color.BLACK);
            Font font = new Font("Monaco", Font.BOLD, 30);
            StdDraw.setPenColor(Color.GREEN);

            Font font1 = new Font("Monaco", Font.BOLD, 15);
            StdDraw.setFont(font1);
            StdDraw.line(0, height - 3, width, height - 3);
            StdDraw.text(5, height - 2, "Round: " + round);

            if (playerTurn) {
                StdDraw.text(width / 2, height - 2, "Type!");
            } else {
                StdDraw.text(width / 2, height - 2, "Watch!");
            }

            int r = rand.nextInt(6);
            StdDraw.text(width - 5, height - 2, ENCOURAGEMENT[r]);

            StdDraw.setFont(font);
            StdDraw.text(width / 2, height / 2, s);
            StdDraw.show();
        }
    }

    public void flashSequence(String letters) {

        for (int i = 0; i < letters.length(); i += 1) {
            String s = "";
            s += letters.charAt(i);
            drawFrame(s);
            StdDraw.pause(1000);
            StdDraw.clear(Color.BLACK);
            StdDraw.show();
            StdDraw.pause(500);
        }
    }

    public String solicitNCharsInput(int n) {
        //TODO: Read n letters of player input
        String s = "";
        while (s.length() != n) {
            if (StdDraw.hasNextKeyTyped()) {
                char c = StdDraw.nextKeyTyped();
                s += c;
                drawFrame(s);
            }
        }
        return s;
        }

    public void startGame() {


        round = 1;
        gameOver = false;
       // playerTurn
        while (!gameOver) {
            playerTurn = false;
            drawFrame("Round: " + round);
            StdDraw.pause(1000);
            StdDraw.clear(Color.BLACK);
            StdDraw.show();
            StdDraw.pause(500);
            String expected = generateRandomString(round);
            flashSequence(expected);
            playerTurn = true;
            String actual = solicitNCharsInput(round);

            if (!actual.equals(expected)) {
                playerTurn = false;
                drawFrame("Game Over! You made it to round: " + round);
                gameOver = true;
            } else {
                round += 1;
            }
        }
    }


}
