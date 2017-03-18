/**
 * Class: Artificial Intelligence
 * Author: Jose G Perez (UTEP ID: 80473954)
 * Contact: <josegperez@mail.com> or <jperez50@miners.utep.edu>
 */
public final class ManhattanHeuristic implements Heuristic {
    @Override
    public int getHeuristic(Node a, Node b) {
        return Math.abs(a.position.X - b.position.X) + Math.abs(a.position.Y - b.position.Y);
    }

}