package byog.Core;


import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;


import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.Collections;

public class Room implements Comparable {
    private int height;
    private int width;
    private final Position position;

    private static Random RANDOM;

    public Room(int width, int height, Position position) {
        this.height = height;
        this.width = width;
        this.position = position;
    }

    /**
     * print the room, when meet the edge, break
     * @param world
     */
    public void printRoom(TETile[][] world) {
        for (int n = 1; n < height; n++) {
            if (n + position.y == world[0].length - 4) {
                height = n + 1;
                break;
            }
            world[position.x][n + position.y] = Tileset.WALL;
            if (position.x + width - 1 < world.length) {
                world[position.x + width - 1][n + position.y] = Tileset.WALL;
            } else {
                width = world.length - position.x;
            }
        }

        for (int r = 0; r < width; r++) {
            world[r + position.x][position.y] = Tileset.WALL;
            world[r + position.x][position.y + height - 1] = Tileset.WALL;
        }

        for (int i = 1; i < width - 1; i++) {
            for (int j = 1; j < height - 1; j++) {
                world[position.x + i][position.y + j] = Tileset.FLOOR;
            }
        }
    }

    private static Room randomRoom(TETile[][] world) {
        int x = RANDOM.nextInt(2, world.length - 8);
        int y = RANDOM.nextInt(1, world[0].length - 8);
        Position p = new Position(x, y);

        int width = 4 + RANDOM.nextInt(8);
        int height = 4 + RANDOM.nextInt(8);
        Room room = new Room(width, height, p);
        return room;
    }

    /**
     * calculate 4 corner position of a room
     * @param room
     * @return
     * 3 **** 2
     * *      *
     * *      *
     * 0 **** 1
     */
    private static Position[] cornerPosition(Room room) {
        Position[] pArray = new Position[4];
        pArray[0] = new Position(room.position.x, room.position.y);
        pArray[1] = new Position(room.position.x + room.width, room.position.y);
        pArray[2] = new Position(room.position.x + room.width, room.position.y + room.height);
        pArray[3] = new Position(room.position.x, room.position.y + room.height);
        return pArray;
    }

    /**
     * return the tileset in  4 direction
     *    1
     *4   p  2
     *    3
     * @param p
     * @return
     */

    private static Position[] fourDirection(Position p) {
        Position[] pArray = new Position[4];
        pArray[0] = new Position(p.x, p.y + 1);
        pArray[1] = new Position(p.x + 1, p.y);
        pArray[2] = new Position(p.x, p.y - 1);
        pArray[3] = new Position(p.x - 1, p.y);
        return pArray;
    }

    private boolean containsPosition(Position p) {
        return (p.x > position.x && p.x < position.x + width - 1)
                && (p.y > position.y && p.y < position.y + height - 1);

    }

    public boolean hasOverlap(Room room) {
        Position[] cornerPosition = cornerPosition(room);
        for (Position p: cornerPosition) {
            if (containsPosition(p)) {
                return true;
            }
        }
        return false;
    }

    public static void removeOverlap(List<Room> rooms) {
        for (int i = 0; i < rooms.size(); i++) {
            for (int j = i + 1; j < rooms.size(); j++) {
                if (rooms.get(i).hasOverlap(rooms.get(j))
                        || rooms.get(j).hasOverlap(rooms.get(i))) {
                    rooms.remove(j);
                    j--;
                }
            }
        }
    }

    private static void removeOutOfBoundary(TETile[][] world, List<Room> rooms) {
        for (int i = 0; i < rooms.size(); i++) {
            Position[] cornerPosition = cornerPosition(rooms.get(i));
            for (Position p: cornerPosition) {
                if (outOfBoundary(world, p)) {
                    rooms.remove(i);
                    break;
                }
            }
        }
    }

    @Override
    public int compareTo(Object o) {

        if (o instanceof Room) {
            Room r = (Room) o;
            if (this.position.x > r.position.x) {
                return 1;
            } else if (this.position.x == r.position.x) {
                return 0;
            } else {
                return -1;
            }
        }
        return 0;
    }

    public static Position pickARandomPosition(Room r) {
        int x = RANDOM.nextInt(1, r.width - 1);
        int y = RANDOM.nextInt(1, r.height - 1);
        return new Position(r.position.x + x, r.position.y + y);
    }

    public static void drawCols(TETile[][] world, Position p, int height, TETile t) {
        int x = p.x;
        int y = p.y;
        for (int i = 0; i < height; i++) {
            if (t == Tileset.WALL) {
                if (world[x][y] == Tileset.NOTHING) {
                    world[x][y] = Tileset.WALL;
                }
                y += 1;
            }
            if (t == Tileset.FLOOR) {
                world[x][y] = Tileset.FLOOR;
                y += 1;
            }
            if (y >= world[0].length) {
                break;
            }
        }
    }


    public static void drawRows(TETile[][] world, Position p, int direction, int width, TETile t) {
        int x = p.x;
        int y = p.y;
        for (int i = 0; i < width; i++) {
            if (t == Tileset.FLOOR) {
                world[x][y] = Tileset.FLOOR;
                x += direction;
            }
            if (t == Tileset.WALL) {
                if (world[x][y] == Tileset.NOTHING) {
                    world[x][y] = Tileset.WALL;
                }
                x += direction;
            }
            if (x >= world.length) {
                break;
            }
        }
    }

    private static boolean outOfBoundary(TETile[][] world, Position p) {
        int x = p.x;
        int y = p.y;
        if (x < world.length || x >= 0 || y >= 0 || y < world[0].length) {
            return true;
        }
        return false;
    }


    public static void drawShapeL(TETile[][] world, Position p,
                                  int width, int height, int direction, TETile t) {
        int x = p.x;
        int y = p.y;
        drawCols(world, p, height, t);
        Position newP = new Position(p.x, p.y);
        drawRows(world, newP, direction, width, t);

    }

    public static void drawHallway(TETile[][] world, Position p,
                                   int width, int height, int direction) {
        width = Math.abs(width);
        drawShapeL(world, p, width, height, direction, Tileset.FLOOR);
        if (direction == -1) {
            p = new Position(p.x - direction, p.y + direction);
            width += 1;
            height += 1;
            drawShapeL(world, p, width, height, direction, Tileset.WALL);

            p = new Position(p.x + 2 * direction, p.y - 2 * direction);
            width -= 2;
            height -= 2;
            drawShapeL(world, p, width, height, direction, Tileset.WALL);
        } else {
            p = new Position(p.x - direction, p.y - direction);
            width += 1;
            height += 1;
            drawShapeL(world, p, width, height, direction, Tileset.WALL);

            p = new Position(p.x + 2 * direction, p.y + 2 * direction);
            width -= 2;
            height -= 2;
            drawShapeL(world, p, width, height, direction, Tileset.WALL);
        }

    }

    public static int calculateXOffset(Position p1, Position p2) {
        return p1.x - p2.x;
    }

    public static int calculateYOffset(Position p1, Position p2) {
        return Math.abs(p1.y - p2.y);
    }

    public static void connectPosition(TETile[][] world, Position p1, Position p2) {
        int startX = Math.min(p1.x, p2.x);
        int startY = Math.min(p1.y, p2.y);
        int direction = 1;
        if (p1.y - p2.y < 0) {
            if (p1.x - p2.x < 0) {
                startX = Math.max(p1.x, p2.x);
                direction = -1;
            }
        } else {
            if (p1.x - p2.x >= 0) {
                startX = Math.max(p1.x, p2.x);
                direction = -1;
            }
        }

        Position p = new Position(startX, startY);
        int width = calculateXOffset(p1, p2);
        int height = calculateYOffset(p1, p2);
        drawHallway(world, p, width, height, direction);
    }

    public static void connectRooms(TETile[][] world, List<Room> rooms) {
        Collections.sort(rooms);
        int i, j;
        for (i = 0, j = 1; i < rooms.size() - 1; i++, j++) {
            Position p1 = pickARandomPosition(rooms.get(i));
            Position p2 = pickARandomPosition(rooms.get(j));
            connectPosition(world, p1, p2);
        }
    }

    public static void beautify(TETile[][] world) {
        for (int i = 1; i < world.length - 1; i++) {
            for (int j = 1; j < world[0].length - 1; j++) {
                if (world[i][j] == Tileset.WALL) {
                    Position p = new Position(i, j);
                    Position[] ps = fourDirection(p);
                    int count = 0;
                    for (Position np: ps) {
                        if (world[np.x][np.y] == Tileset.NOTHING) {
                            count += 1;
                        }
                    }
                    if (count == 0) {
                        world[i][j] = Tileset.FLOOR;
                    }
                }
            }
        }
    }


    public static List<Room> roomGenerator(Random random, TETile[][] world) {
        RANDOM = random;
        int roomNum = 90 + RANDOM.nextInt(10);
        List<Room> rooms = new LinkedList<>();
        for (int i = 0; i < roomNum; i++) {
            rooms.add(Room.randomRoom(world));
        }
        removeOverlap(rooms);
        for (Room r: rooms) {
            r.printRoom(world);
        }
        return rooms;
    }

    private static boolean validDoor(Position p, TETile[][] world) {
        int x = p.x;
        int y = p.y;
        boolean floor = false;
        boolean nothing = false;
        Position[] ps = fourDirection(p);
        for (Position newP: ps) {
            if (world[newP.x][newP.y] == Tileset.FLOOR) {
                floor = true;
            }
            if (world[newP.x][newP.y] == Tileset.NOTHING) {
                nothing = true;
            }
        }
        return floor && nothing;
    }

    public static Position addDoor(TETile[][] world) {
        int x;
        int y;
        while (true) {
            x = RANDOM.nextInt(world.length - 4);
            y = RANDOM.nextInt(world[0].length - 4);
            if (world[x][y] == Tileset.WALL) {
                Position p = new Position(x, y);
                if (validDoor(p, world)) {
                    world[p.x][p.y] = Tileset.LOCKED_DOOR;
                    break;
                }
            }
        }
        return new Position(x, y);
    }

    public static Position addPlayer(TETile[][] world) {
        int x;
        int y;
        while (true) {
            x = RANDOM.nextInt(world.length - 5);
            y = RANDOM.nextInt(world[0].length - 5);
            if (world[x][y] == Tileset.FLOOR) {
                world[x][y] = Tileset.PLAYER;
                break;
            }
        }
        return new Position(x, y);
    }
}

