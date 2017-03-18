/**
 * Class: Artificial Intelligence
 * Author: Jose G Perez (UTEP ID: 80473954)
 * Contact: <josegperez@mail.com> or <jperez50@miners.utep.edu>
 */
public class Vector2 {
    public int X, Y;

    public static Node Zero = new Node(0, 0);

    public Vector2(int x, int y) {
        X = x;
        Y = y;
    }

    public Vector2 left() {
        return new Vector2(X - 1, Y);
    }

    public Vector2 right() {
        return new Vector2(X + 1, Y);
    }

    public Vector2 up() {
        return new Vector2(X, Y - 1);
    }

    public Vector2 down() {
        return new Vector2(X, Y + 1);
    }

    public boolean equals(Vector2 other) {
        return X == other.X && Y == other.Y;
    }

    public String toString() {
        return String.format("(X=%s,Y=%s)", X, Y);
    }
}
