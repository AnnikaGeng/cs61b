package byog.Core;
import byog.TileEngine.TETile;
import java.io.Serializable;
import java.util.List;
import java.util.Random;

public class MapGenerator implements Serializable {

    private static final long serialVersionUID = -8544742824885108041L;
    Random random;
    TETile[][] world;
    protected Position door;
    protected Position player;

    public MapGenerator(Random random, TETile[][] world) {
        this.random = random;
        this.world = world;
    }

    public TETile[][] worldGenerator() {
        List<Room> rooms = Room.roomGenerator(random, world);
        Room.connectRooms(world, rooms);
        Room.connectRooms(world, rooms);
        Room.beautify(world);
        door = Room.addDoor(world);
        player = Room.addPlayer(world);
        return world;
    }

}
