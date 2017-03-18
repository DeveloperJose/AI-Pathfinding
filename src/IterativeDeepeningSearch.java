/**
 * Class: Artificial Intelligence
 * Author: Jose G Perez (UTEP ID: 80473954)
 * Contact: <josegperez@mail.com> or <jperez50@miners.utep.edu>
 */
public class IterativeDeepeningSearch extends SearchAlgorithm {

    private enum IDSResult {
        Good, Cutoff, Failure
    }

    public IterativeDeepeningSearch(Map map) {
        super(map);
    }

    @Override
    public void setup() {

    }

    public IDSResult DLS(int depthLimit) {
        currentMap.resetExploration();
        return RecursiveDLS(currentMap.getStart(), depthLimit);
    }

    public IDSResult RecursiveDLS(Node n, int depthLimit) {
        this.currentNodesInMemory++; // Node n is in memory
        this.currentExpandedNodes++; // We expand Node n
        n.isExplored = true;

        if (n.equals(currentMap.getGoal())) { // Check if we got to the goal
            finalNode = n;
            currentState = State.SUCCESS;
            return IDSResult.Good;
        } else if (depthLimit <= 0) { // Check if we finished searching this
                                      // depth
            return IDSResult.Cutoff;

        } else { // Go to the next depth and search
            boolean isCutoff = false;
            for (Node child : currentMap.neighborsAround(n.position)) {
                currentNodesInMemory++; // Node child is in memory

                // Can only go to node if it's not explored and not blocked
                if (!child.isExplored && child.cost != 0) {
                    child.parent = n; // Save the path
                    IDSResult result = RecursiveDLS(child, depthLimit - 1);
                    currentNodesInMemory++; // Node result is in memory

                    if (result == IDSResult.Cutoff)
                        isCutoff = true;
                    else if (result != IDSResult.Failure)
                        return IDSResult.Good;

                }
            }

            if (isCutoff)
                return IDSResult.Cutoff;
            else
                return IDSResult.Failure;
        }
    }

    private int depth = 0;
    private Node finalNode = null;

    @Override
    public void loop() {
        currentNodesInMemory += 1; // finalNode is in memory
        IDSResult result = DLS(depth);

        if (currentState == State.SUCCESS) // Check if we found goal
            return;

        if (result == IDSResult.Failure || depth >= currentMap.getMaxDepth()) { // Check
                                                                                // if
                                                                                // we
                                                                                // have
                                                                                // completely
                                                                                // failed
            currentState = State.FAILURE;
            return;
        }
        depth++;
    }

    @Override
    public void finish() {
        // Calculate the final path cost
        int finalCost = 0;
        Node temp = finalNode;
        while (temp != null) {
            finalCost += temp.cost;
            temp = temp.parent;
        }

        this.currentPathCost = finalCost;
    }

    @Override
    public Node getGoalWithPath() {
        return finalNode;
    }
}
