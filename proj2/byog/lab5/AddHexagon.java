package byog.lab5;

import byog.TileEngine.TERenderer;
import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;
import java.util.Random;
public class AddHexagon {
    private static final int WIDTH = 50;
    private static final int HEIGHT = 50;
    private static final long SEED = 2873123;
    private static final Random RANDOM = new Random(SEED);

    /**
     * Computes the width of row i for a size s hexagon.
     * @param s The size of the hex.
     * @param i The row number where i = 0 is the bottom row.
     * @return
     */
    public static int hexRowWidth(int s, int i) {
        int effectiveI = i;
        if (i >= s) {
            effectiveI = 2 * s - 1 - effectiveI;
        }
        return s + effectiveI * 2;
    }

    /**
     * Computesrelative x coordinate of the leftmost tile in the ith
     * row of a hexagon, assuming that the bottom row has an x-coordinate
     * of zero. For example, if s = 3, and i = 2, this function
     * returns -2, because the row 2 up from the bottom starts 2 to the left
     * of the start position, e.g.
     *   xxxx
     *  xxxxxx
     * xxxxxxxx
     * xxxxxxxx <-- i = 2, starts 2 spots to the left of the bottom of the hex
     *  xxxxxx
     *   xxxx
     *
     * @param s size of the hexagon
     * @param i row num of the hexagon, where i = 0 is the bottom
     * @return
     */
    public static int hexRowOffset(int s, int i) {
        int effectiveI = i;
        if (i >= s) {
            effectiveI = 2 * s - 1 - effectiveI;
        }
        return -effectiveI;
    }

    /** Adds a row of the same tile.
     * @param world the world to draw on
     * @param p the leftmost position of the row
     * @param width the number of tiles wide to draw
     * @param t the tile to draw
     */
    public static void addRow(TETile[][] world, Position p, int width, TETile t) {
        for (int xi = 0; xi < width; xi += 1) {
            int xCoord = p.x + xi;
            int yCoord = p.y;
            world[xCoord][yCoord] = TETile.colorVariant(t, 32, 32, 32, RANDOM);
        }
    }

    /**
     * Adds a hexagon to the world.
     * @param world the world to draw on
     * @param p the bottom left coordinate of the hexagon
     * @param s the size of the hexagon
     * @param t the tile to draw
     */
    public static void addHexagon(TETile[][] world, Position p, int s, TETile t) {

        if (s < 2) {
            throw new IllegalArgumentException("Hexagon must be at least size 2.");
        }

        // hexagons have 2*s rows. this code iterates up from the bottom row,
        // which we call row 0.
        for (int yi = 0; yi < 2 * s; yi += 1) {
            int thisRowY = p.y + yi;
            int xRowStart = p.x + hexRowOffset(s, yi);
            int rowWidth = hexRowWidth(s, yi);
            Position newP = new Position(xRowStart, thisRowY);

            addRow(world, newP, rowWidth, t);
        }
    }

    public static void initialization(TETile[][] world) {
        for (int x = 0; x < WIDTH; x += 1) {
            for (int y = 0; y < HEIGHT; y += 1) {
                world[x][y] = Tileset.NOTHING;
            }
        }
    }

    /**
     * calulate the number of hexagons in row i
     * @param s the size of hexagon
     * @param i the index of row, start from the bottom row 0
     */
    public static int rowNumber(int s, int i) {
        if (i == 0 || i == 8) {
            return 1;
        } else if (i % 2 > 0) {
            return 2;
        } else {
            return 3;
        }
    }

    /**
     *
     * @param s the size of hexagon
     * @param i the index of row, start from the bottom row 0
     * @param j the index of hexagon in the same row, start from left, 0-indexed
     * @return
     */

    public static int rowOffSet(int s, int i, int j) {
        if (i == 0 || i == 8) {
            return 0;
        } else if (i % 2 > 0) {
            if (j == 0) {
                return -2 * s + 1;
            } else {
                return 2 * s - 1;
            }
        } else {
            if (j == 0) {
                return -4 * s + 2;
            } else if (j == 1) {
                return 0;
            } else {
                return 4 * s - 2;
            }
        }
    }

    public static int colOffSet(int s, int i) {
        if (i == 0) {
            return 0;
        } else {
            return s * i;
        }
    }

    private static TETile randomTile() {
        int tileNum = RANDOM.nextInt(3);
        switch (tileNum) {
            case 0: return Tileset.WALL;
            case 1: return Tileset.FLOWER;
            case 2: return Tileset.PLAYER;
            default: return Tileset.GRASS;
        }
    }

    public static void drawTesselation(TETile[][] world, Position p, int s) {
        if (s < 2) {
            throw new IllegalArgumentException("Hexagon must be at least size 2.");
        }

        for (int i = 0; i < 9; i++) {
            int numOfRow = rowNumber(s, i);
            for (int j = 0; j < numOfRow; j++) {
                int posX = rowOffSet(s, i, j) + p.x;
                int posY = colOffSet(s, i) + p.y;
                Position newP = new Position(posX, posY);
                addHexagon(world, newP, s, randomTile());
            }
        }
    }

    public static void main(String[] args) {

        TERenderer ter = new TERenderer();
        ter.initialize(WIDTH, HEIGHT);
        TETile[][] world = new TETile[WIDTH][HEIGHT];

        initialization(world);
        Position p = new Position(20, 10);
        drawTesselation(world, p, 3);

        ter.renderFrame(world);

    }
}
