/**
 * Class: Artificial Intelligence
 * Author: Jose G Perez (UTEP ID: 80473954)
 * Contact: <josegperez@mail.com> or <jperez50@miners.utep.edu>
 */
import java.util.PriorityQueue;

public class AStarSearch extends SearchAlgorithm {

    private Heuristic heuristic;

    public AStarSearch(Map map, Heuristic heuristic) {
        super(map);
        this.heuristic = heuristic;
    }

    private PriorityQueue<Node> frontier;
    private Node currentNode = null;

    @Override
    public void setup() {
        frontier = new PriorityQueue<Node>();
        Node start = currentMap.getStart();
        frontier.offer(start);
    }

    @Override
    public void loop() {
        if (frontier.isEmpty()) {
            currentState = State.FAILURE;
            return;
        }

        currentNode = frontier.poll();
        currentNode.isExplored = true;
        this.currentExpandedNodes++;
        this.currentNodesInMemory++; // currentNode

        if (currentNode.equals(currentMap.getGoal())) {
            currentState = State.SUCCESS;
            return;
        }

        // Get the neighbors and add them to the frontier
        for (Node next : currentMap.neighborsAround(currentNode.position)) {
            this.currentNodesInMemory++; // Add Node next to memory count

            // Can only go to node if it's not explored and not blocked
            if (!next.isExplored && next.cost != 0) {
                next.parent = currentNode;
                next.g = currentNode.g + next.cost;
                int h = heuristic.getHeuristic(next, currentMap.getGoal());
                next.f = next.g + h;

                // Check if we reached the goal
                if (next.equals(currentMap.getGoal())) {
                    currentNode = next;
                    currentState = State.SUCCESS;
                    break;
                }

                frontier.offer(next);
            }
        }

        // Add the frontier nodes that are in memory
        this.currentNodesInMemory += frontier.size();
    }

    @Override
    public void finish() {
        // Calculate the final path cost
        int finalCost = 0;
        Node temp = currentNode;
        while (temp != null) {
            finalCost += temp.cost;
            temp = temp.parent;
        }

        this.currentPathCost = finalCost;
    }

    @Override
    public Node getGoalWithPath() {
        return currentNode;
    }
}
