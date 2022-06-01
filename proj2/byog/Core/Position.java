package byog.Core;

import java.io.Serializable;

public class Position implements Serializable {

    private static final long serialVersionUID = 3828223529611834930L;
    protected int x;
    protected int y;

    public Position(int x, int y) {
        this.x = x;
        this.y = y;
    }
}
