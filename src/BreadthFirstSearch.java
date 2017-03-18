/**
 * Class: Artificial Intelligence
 * Author: Jose G Perez (UTEP ID: 80473954)
 * Contact: <josegperez@mail.com> or <jperez50@miners.utep.edu>
 */
import java.util.LinkedList;

public class BreadthFirstSearch extends SearchAlgorithm {

    public BreadthFirstSearch(Map map) {
        super(map);
    }

    private LinkedList<Node> frontier;
    private Node currentNode = null;

    @Override
    public void setup() {
        Node startingNode = currentMap.getStart();
        frontier = new LinkedList<Node>();
        frontier.add(startingNode);
    }

    @Override
    public void loop() {
        if (frontier.isEmpty()) {
            currentState = State.FAILURE;
            return;
        }
        currentNode = frontier.pop();
        currentNode.isExplored = true;
        this.currentExpandedNodes++;
        this.currentNodesInMemory++; // The current node is in memory

        // Get the neighbors and add them to the frontier
        for (Node next : currentMap.neighborsAround(currentNode.position)) {
            this.currentNodesInMemory++; // Add Node next to memory count

            // Can only go to node if it's not explored and not blocked
            if (!next.isExplored && next.cost != 0) {
                next.parent = currentNode;

                // Check if we reached the goal
                if (next.equals(currentMap.getGoal())) {
                    currentNode = next;
                    currentState = State.SUCCESS;
                    break;
                }

                frontier.push(next);
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
