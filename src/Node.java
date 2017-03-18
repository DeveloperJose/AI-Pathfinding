/**
 * Class: Artificial Intelligence
 * Author: Jose G Perez (UTEP ID: 80473954)
 * Contact: <josegperez@mail.com> or <jperez50@miners.utep.edu>
 */
public class Node implements Comparable<Node> {
    public Vector2 position;
    public int cost = -1;
    public boolean isExplored = false;
    public Node parent = null;

    public int f = 0;
    public int g = 0;

    public Node(Vector2 position) {
        this.position = position;
    }

    public Node(int x, int y) {
        this(new Vector2(x, y));
    }

    public boolean equals(Node other) {
        return position.equals(other.position);
    }

    public String toString() {
        return String.format("(p=%s,c=%s,e=%s)", position, cost, isExplored);
    }

    @Override
    public int compareTo(Node otherNode) {
        if (f == otherNode.f)
            return 0;
        else if (f > otherNode.f) // First one is more costly
            return 1; // Pick the second one
        else
            return -1;
    }
}
