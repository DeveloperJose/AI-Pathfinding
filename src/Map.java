/**
 * Class: Artificial Intelligence
 * Author: Jose G Perez (UTEP ID: 80473954)
 * Contact: <josegperez@mail.com> or <jperez50@miners.utep.edu>
 */
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class Map {
    private int width;
    private int height;
    private Node[][] grid;
    private Vector2 posStart;
    private Vector2 posGoal;

    private Map(int width, int height, Node[][] grid, Vector2 posStart, Vector2 posGoal) {
        this.width = width;
        this.height = height;
        this.grid = grid;
        this.posStart = posStart;
        this.posGoal = posGoal;
    }

    public int getMaxDepth() {
        return width * height;
    }

    public void resetExploration() {
        for (int i = 0; i < grid.length; i++)
            for (int j = 0; j < grid[i].length; j++)
                grid[i][j].isExplored = false;
    }

    public List<Node> neighborsAround(Vector2 pos) {
        List<Node> neighbors = new ArrayList<Node>();

        // Try to add the 4 neighbors
        neighbors.add(nodeAt(pos.left()));
        neighbors.add(nodeAt(pos.right()));
        neighbors.add(nodeAt(pos.up()));
        neighbors.add(nodeAt(pos.down()));

        // Remove invalid (null) neighbors
        neighbors.removeAll(Collections.singleton(null));

        return neighbors;
    }

    public Node getStart() {
        return nodeAt(posStart);
    }

    public Node getGoal() {
        return nodeAt(posGoal);
    }

    public boolean isValidPos(Vector2 pos) {
        return !(pos.X < 0 || pos.X >= width || pos.Y < 0 || pos.Y >= height);
    }

    public Node nodeAt(Vector2 pos) {
        if (isValidPos(pos))
            return grid[pos.X][pos.Y];
        else
            return null;
    }

    public static Map importFromText(String textMap) throws MapImportException {
        if (textMap == null || textMap.isEmpty())
            throw new MapImportException("Map file contents are null or empty");

        Scanner input = new Scanner(textMap);

        // Read height
        if (!input.hasNextInt()) {
            input.close();
            throw new MapImportException("Expected height but didn't find it. Possibly invalid format.");
        }
        int tempHeight = input.nextInt();

        // Read width
        if (!input.hasNextInt()) {
            input.close();
            throw new MapImportException("Expected width but didn't find it. Possibly invalid format.");
        }
        int tempWidth = input.nextInt();

        // Read starting position X
        if (!input.hasNextInt()) {
            input.close();
            throw new MapImportException("Expected starting position x but didn't find it. Possibly invalid format.");
        }
        int tempStartX = input.nextInt();

        // Read starting position Y
        if (!input.hasNextInt()) {
            input.close();
            throw new MapImportException("Expected starting position y but didn't find it. Possibly invalid format.");
        }
        int tempStartY = input.nextInt();

        // Read goal position X
        if (!input.hasNextInt()) {
            input.close();
            throw new MapImportException("Expected goal position x but didn't find it. Possibly invalid format.");
        }
        int tempGoalX = input.nextInt();

        // Read goal position Y
        if (!input.hasNextInt()) {
            input.close();
            throw new MapImportException("Expected goal position y but didn't find it. Possibly invalid format.");
        }
        int tempGoalY = input.nextInt();

        // Read cost grid
        Node[][] tempGrid = new Node[tempWidth][tempHeight];
        for (int x = 0; x < tempWidth; x++) {
            for (int y = 0; y < tempHeight; y++) {
                // Check if the next value exists
                if (!input.hasNextInt()) {
                    input.close();
                    throw new MapImportException(
                            "Expected a cost at {" + x + "," + y + "} but didn't find it. Possibly invalid format.");
                }
                Node temp = new Node(x, y);
                temp.cost = input.nextInt();
                tempGrid[x][y] = temp;
            }
        }

        input.close();
        return new Map(tempWidth, tempHeight, tempGrid, new Vector2(tempStartX, tempStartY),
                new Vector2(tempGoalX, tempGoalY));
    }
}
