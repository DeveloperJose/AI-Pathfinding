/**
 * Class: Artificial Intelligence
 * Author: Jose G Perez (UTEP ID: 80473954)
 * Contact: <josegperez@mail.com> or <jperez50@miners.utep.edu>
 */
public abstract class SearchAlgorithm {
    private static String[] validAlgorithmNames = new String[] { "BFS", "IDS", "AS" };

    public static boolean isValidAlgorithmName(String algorithmName) {
        for (String validName : validAlgorithmNames) {
            if (algorithmName.equalsIgnoreCase(validName))
                return true;
        }

        return false;
    }

    public static SearchAlgorithm fromName(String algorithmName, Map map, Heuristic heuristic) {
        // Assume the algorithmName and map have been validated
        if (algorithmName.equalsIgnoreCase("BFS"))
            return new BreadthFirstSearch(map);
        else if (algorithmName.equalsIgnoreCase("IDS"))
            return new IterativeDeepeningSearch(map);
        else if (algorithmName.equalsIgnoreCase("AS"))
            return new AStarSearch(map, heuristic);
        else
            return null;
    }

    protected enum State {
        WAITING, RUNNING, SUCCESS, FAILURE, TIMEOUT
    }

    public abstract void setup();

    public abstract void loop();

    public abstract void finish();

    public abstract Node getGoalWithPath();

    protected State currentState;
    protected Map currentMap;
    protected int currentPathCost;
    protected int currentExpandedNodes;
    protected int currentNodesInMemory;

    private int currentMaxNodesInMemory;
    private long currentRuntime;

    public SearchAlgorithm(Map map) {
        currentState = State.WAITING;
        this.currentMap = map;
        currentPathCost = 0;
        currentExpandedNodes = 0;
        currentNodesInMemory = 0;

        currentMaxNodesInMemory = 0;
        currentRuntime = 0;
    }

    public void run(long cutoffTime) {
        currentState = State.RUNNING;
        // Setup won't consume the deadline time
        setup();

        // Check if the goal state is the starting state
        if (currentState == State.SUCCESS)
            return;

        // Start the timer
        long startTime = System.currentTimeMillis();
        long runningTime = System.currentTimeMillis() - startTime;
        // Loop until the time runs out
        while (runningTime < cutoffTime) {
            // Run an iteration of the algorithm
            currentNodesInMemory = 0;
            loop();

            // Update the running time
            runningTime = System.currentTimeMillis() - startTime;

            // Update max nodes in memory
            currentMaxNodesInMemory = Math.max(currentMaxNodesInMemory, currentNodesInMemory);

            if (currentState == State.SUCCESS || currentState == State.FAILURE) {
                currentRuntime = runningTime;
                finish();
                return;
            }
        }
        currentState = State.TIMEOUT;
        currentRuntime = cutoffTime;
        finish();
    }

    public String getPathString() {
        if (currentState == State.SUCCESS) {
            String pathString = "{";

            Node temp = getGoalWithPath();
            while (temp != null) {
                pathString += temp.position.toString() + " -> ";
                temp = temp.parent;
            }

            pathString += "}";
            return pathString;
        } else
            return "NULL";
    }

    public State getCurrentState() {
        return currentState;
    }

    public int getCurrentPathCost() {
        if (currentState == State.SUCCESS)
            return currentPathCost;
        else
            return -1;
    }

    public int getCurrentExpandedNodes() {
        return currentExpandedNodes;
    }

    public int getCurrentMaxNodesInMemory() {
        return currentMaxNodesInMemory;
    }

    public long getRuntime() {
        return currentRuntime;
    }
}
