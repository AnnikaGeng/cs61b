package byog.Core;

import byog.TileEngine.TERenderer;
import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;
import edu.princeton.cs.introcs.StdDraw;

import java.awt.Color;
import java.awt.Font;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Arrays;
import java.util.Random;

public class Game {
    static TERenderer ter = new TERenderer();
    /* Feel free to change the width and height. */
    public static final int WIDTH = 80;
    public static final int HEIGHT = 40;
    static boolean gameOver = false;
    boolean playerTurn = false;
    int mouseX = 0;
    int mouseY = 0;

    int xMid = WIDTH / 2;

    private static TETile[][] finalWorldFrame;

    private static MapGenerator map;

    /**
     * Method used for playing a fresh game. The game should start from the main menu.
     */

    public static void initialization(TETile[][] world) {
        for (int x = 0; x < WIDTH; x += 1) {
            for (int y = 0; y < HEIGHT; y += 1) {
                world[x][y] = Tileset.NOTHING;
            }
        }
    }


    public void clearFrame(Position player) {
        Tileset.FLOOR.draw(player.x, player.y);
        StdDraw.show();
    }

    public void drawFrame(Position player) {
        System.out.print(player.y + " ");
        System.out.print(player.x + "\n");
        Tileset.PLAYER.draw(player.x, player.y);
        StdDraw.show();
    }

    public boolean checkNextTile(char key) {
        Position next = new Position(map.player.x, map.player.y);
        key = Character.toLowerCase(key);
        switch (key) {
            case 'w':
                next = new Position(map.player.x, map.player.y + 1);
                break;
            case 'a':
                next = new Position(map.player.x - 1, map.player.y);
                break;
            case 's':
                next = new Position(map.player.x, map.player.y - 1);
                break;
            case 'd':
                next = new Position(map.player.x + 1, map.player.y);
                break;
            default:
        }

        return finalWorldFrame[next.x][next.y].description().equals("wall");
    }

    public TETile[][] move(char key) {
        key = Character.toLowerCase(key);
        if (map.door.x != map.player.x || map.door.y != map.player.y) {
            if (!checkNextTile(key)) {
                switch (key) {
                    case 'w':
                        finalWorldFrame[map.player.x][map.player.y] = Tileset.FLOOR;
                        finalWorldFrame[map.player.x][map.player.y + 1] = Tileset.PLAYER;
                        map.player.y += 1;
                        //ter.renderFrame(finalWorldFrame);
                        return finalWorldFrame;
                    case 'a':
                        finalWorldFrame[map.player.x][map.player.y] = Tileset.FLOOR;
                        finalWorldFrame[map.player.x - 1][map.player.y] = Tileset.PLAYER;
                        map.player.x -= 1;
                        //ter.renderFrame(finalWorldFrame);
                        return finalWorldFrame;
                    case 's':
                        finalWorldFrame[map.player.x][map.player.y] = Tileset.FLOOR;
                        finalWorldFrame[map.player.x][map.player.y - 1] = Tileset.PLAYER;
                        map.player.y -= 1;
                        //ter.renderFrame(finalWorldFrame);
                        return finalWorldFrame;
                    case 'd':
                        finalWorldFrame[map.player.x][map.player.y] = Tileset.FLOOR;
                        finalWorldFrame[map.player.x + 1][map.player.y] = Tileset.PLAYER;
                        map.player.x += 1;
                        //ter.renderFrame(finalWorldFrame);
                        return finalWorldFrame;
                    default:
                }
            }
        }
        gameOver = true;
        return finalWorldFrame;
    }

    public void drawFrame() {
        if (StdDraw.isMousePressed()) {
            StdDraw.clear();
            ter.renderFrame(finalWorldFrame);
            mouseX = (int) StdDraw.mouseX();
            mouseY = (int) StdDraw.mouseY();
            Font smallFont = new Font("Monaco", Font.BOLD, 20);
            StdDraw.setFont(smallFont);
            StdDraw.setPenColor(Color.WHITE);
            if (finalWorldFrame[mouseX][mouseY] == Tileset.WALL) {
                StdDraw.textLeft(1, HEIGHT - 1, "This is wall");
            } else if (finalWorldFrame[mouseX][mouseY] == Tileset.FLOOR) {
                StdDraw.textLeft(1, HEIGHT - 1, "This is floor");
            } else if (finalWorldFrame[mouseX][mouseY] == Tileset.PLAYER) {
                StdDraw.textLeft(1, HEIGHT - 1, "This is player");
            } else if (finalWorldFrame[mouseX][mouseY] == Tileset.NOTHING) {
                StdDraw.textLeft(1, HEIGHT - 1, "This is nothing");
            } else {
                StdDraw.textLeft(1, HEIGHT - 1, "This is locked door");
            }

        }
        StdDraw.show();
    }

    public void operation() {
        String input = "";
        while (true) {
            if (StdDraw.hasNextKeyTyped()) {
                Character key = StdDraw.nextKeyTyped();
                key = Character.toLowerCase(key);
                input += String.valueOf(key);
                if (key == 'q') {
                    if (input.charAt(input.length() - 2) == ':') {
                        saveWorld();
                        System.exit(0);
                        break;
                    }
                }
                finalWorldFrame = move(key);
                ter.renderFrame(finalWorldFrame);
            }
            if (StdDraw.isMousePressed()) {
                drawFrame();
            }
        }
    }


    public void drawStart() {
        StdDraw.clear(Color.black);
        int y = HEIGHT / 3;
        Font smallFont = new Font("Monaco", Font.BOLD, 20);
        StdDraw.setFont(smallFont);
        StdDraw.setPenColor(Color.WHITE);
        StdDraw.text(xMid, y + 10, "New(N)");
        StdDraw.text(xMid, y + 5, "Load(L)");
        StdDraw.text(xMid, y, "Quit(Q)");
        StdDraw.show();
    }

    public void drawSeed(String input) {
        StdDraw.clear(Color.black);
        Font smallFont = new Font("Monaco", Font.BOLD, 20);
        StdDraw.setFont(smallFont);
        StdDraw.setPenColor(Color.WHITE);
        StdDraw.text(xMid, 10, "Enter a Seed, end with S:" + input);
        StdDraw.show();
    }

    public void getSeed() {
        StdDraw.clear(Color.black);
        String input = "";
        drawSeed(input);
        while (true) {
            if (StdDraw.hasNextKeyTyped()) {
                char key = StdDraw.nextKeyTyped();
                if (key == 'S') {
                    break;
                }
                input += String.valueOf(key);
                drawSeed(input);
            }
        }

        startANewGame(input);
        ter.initialize(WIDTH, HEIGHT);
        ter.renderFrame(finalWorldFrame);
        operation();
    }

    public void handleKeyEvent() {
        while (true) {
            if (StdDraw.hasNextKeyTyped()) {
                char key = StdDraw.nextKeyTyped();
                key = Character.toLowerCase(key);
                switch (key) {
                    case 'n':
                        getSeed();
                        break;
                    case 'q':
                        System.exit(0);
                        break;
                    case 'l':
                        loadWorld();
                        ter.renderFrame(finalWorldFrame);
                        operation();
                        break;
                    default:
                }
            }
        }
    }

    private void loadWorld() {
        File f = new File("./game.ser");
        File f2 = new File("./map.ser");
        if (f.exists()) {
            try {
                FileInputStream fs = new FileInputStream(f);
                ObjectInputStream os = new ObjectInputStream(fs);
                FileInputStream fs2 = new FileInputStream(f2);
                ObjectInputStream os2 = new ObjectInputStream(fs2);

                TETile[][] w = (TETile[][]) os.readObject();
                finalWorldFrame = w;
                MapGenerator map1 = (MapGenerator) os2.readObject();
                map = map1;

                os.close();
                os2.close();

                //ter.renderFrame(w);
            } catch (FileNotFoundException e) {
                System.out.println("file not found");
                System.exit(0);
            } catch (IOException e) {
                System.out.println(e);
                System.exit(0);
            } catch (ClassNotFoundException e) {
                System.out.println("class not found");
                System.exit(0);
            }
        } else {
            getSeed();
        }
    }

    private static void saveWorld() {
        File f = new File("./game.ser");
        File f2 = new File("./map.ser");
        try {
            if (!f.exists()) {
                f.createNewFile();
            }
            if (!f2.exists()) {
                f2.createNewFile();
            }
            FileOutputStream fs = new FileOutputStream(f);
            ObjectOutputStream os = new ObjectOutputStream(fs);
            FileOutputStream fs2 = new FileOutputStream(f2);
            ObjectOutputStream os2 = new ObjectOutputStream(fs2);

            os.writeObject(finalWorldFrame);
            os2.writeObject(map);
            os.close();
            os2.close();

        }  catch (FileNotFoundException e) {
            System.out.println("file not found");
            System.exit(0);
        } catch (IOException e) {
            System.out.println(e);
            System.exit(0);
        }
    }

    public TETile[][] startANewGame(String input) {
        boolean newGame = false;

        if (input.charAt(0) == 'L') {
            loadWorld();
        } else {
            newGame = true;
            Long seed = Long.parseLong(input.replaceAll("[^0-9]", ""));
            Random random = new Random(seed);
            //ter.initialize(WIDTH, HEIGHT);
            finalWorldFrame = new TETile[WIDTH][HEIGHT];
            initialization(finalWorldFrame);
            map = new MapGenerator(random, finalWorldFrame);
            finalWorldFrame = map.worldGenerator();
            //ter.renderFrame(finalWorldFrame);
        }

        char[] c = input.toCharArray();
        char[] step = new char[c.length];

        for (int i = 0; i < c.length; i++) {
            if (c[0] == 'L') {
                step = Arrays.copyOfRange(c, 1, c.length);
                break;
            }
            if (newGame & c[i] == 'S' && i != c.length - 1) {
                i += 1;
                step = Arrays.copyOfRange(c, i, c.length);
                break;
            }
        }

        if (step != null) {
            if (step.length == 1) {
                move(step[0]);
            } else {
                for (int i = 0; i < step.length - 1; i++) {
                    if (step[i] != ':' && step[i + 1] != 'Q') {
                        move(step[i]);
                    } else if (step[i] == ':' && step[i + 1] == 'Q') {
                        saveWorld();
                        //System.exit(0);
                        return finalWorldFrame;
                    }
                }
            }
        }

        //operation();

        return finalWorldFrame;
    }

    public void playWithKeyboard() {
        ter.initialize(WIDTH, HEIGHT);
        finalWorldFrame = new TETile[WIDTH][HEIGHT];
        initialization(finalWorldFrame);
        ter.renderFrame(finalWorldFrame);
        drawStart();
        handleKeyEvent();
    }


    /**
     * Method used for autograding and testing the game code. The input string will be a series
     * of characters (for example, "n123sswwdasdassadwas", "n123sss:q", "lwww". The game should
     * behave exactly as if the user typed these characters into the game after playing
     * playWithKeyboard. If the string ends in ":q", the same world should be returned as if the
     * string did not end with q. For example "n123sss" and "n123sss:q" should return the same
     * world. However, the behavior is slightly different. After playing with "n123sss:q", the game
     * should save, and thus if we then called playWithInputString with the string "l", we'd expect
     * to get the exact same world back again, since this corresponds to loading the saved game.
     * @param input the input string to feed to your program
     * @return the 2D TETile[][] representing the state of the world
     */
    public TETile[][] playWithInputString(String input) {
        // and return a 2D tile representation of the world that would have been
        // drawn if the same inputs had been given to playWithKeyboard().
        //ter.initialize(WIDTH, HEIGHT);
        finalWorldFrame = new TETile[WIDTH][HEIGHT];
        initialization(finalWorldFrame);
        //ter.renderFrame(finalWorldFrame);
        finalWorldFrame = startANewGame(input);
        return finalWorldFrame;
    }

}
