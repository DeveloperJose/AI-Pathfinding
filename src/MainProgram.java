/**
 * Class: Artificial Intelligence
 * Author: Jose G Perez (UTEP ID: 80473954)
 * Contact: <josegperez@mail.com> or <jperez50@miners.utep.edu>
 */
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class MainProgram {
    public static long maxRunningTime = 3 * 60 * 1000; // 3 min in milliseconds

    public static void main(String[] args) {
        System.out.println("-----===== Pathfinding by Jose Perez");
        // Process commandline arguments
        if (args.length < 2) {
            System.out.println("You need to specify a map and algorithm");
            printUsage();
            return;
        } else if (args.length > 2) {
            System.out.println("Only two arguments are required. The map and the algorithm.");
            printUsage();
            return;
        }

        // Check if the file is valid
        String textMapFilename = args[0];
        File fileMap = new File(textMapFilename);

        if (!fileMap.exists()) {
            System.out.printf("File %s doesn't exist.\n", textMapFilename);
            printUsage();
            return;
        }

        // Check if the algorithm is valid
        String textAlgorithm = args[1];
        if (!SearchAlgorithm.isValidAlgorithmName(textAlgorithm)) {
            System.out.println("Invalid algorithm.");
            printUsage();
            return;
        }

        // Attempt to load file contents
        String rawFileContents;
        try {
            rawFileContents = new String(Files.readAllBytes(Paths.get(textMapFilename)));
        } catch (IOException e) {
            System.out
                    .println("Couldn't read the contents of the file. Perhaps we don't have permission to access it.");
            return;
        }

        // Attempt to parse map from file contents
        Map mainMap;
        try {
            mainMap = Map.importFromText(rawFileContents);
        } catch (MapImportException e) {
            System.out.println(e.getMessage());
            return;
        }

        // Here you can change the heuristic for A*, currently only Manhattan
        // Distance is available
        Heuristic heuristic = new ManhattanHeuristic();
        SearchAlgorithm search = SearchAlgorithm.fromName(textAlgorithm, mainMap, heuristic);
        search.run(maxRunningTime);

        System.out.printf("----------========== Results\n");
        System.out.printf("State: %s\n", search.getCurrentState());
        System.out.printf("Cost: %s\n", search.getCurrentPathCost());
        System.out.printf("Expanded Nodes: %s\n", search.getCurrentExpandedNodes());
        System.out.printf("Max Nodes in Memory: %s\n", search.getCurrentMaxNodesInMemory());
        System.out.printf("Runtime: %s ms\n", search.getRuntime());
        System.out.printf("Path: %s\n", search.getPathString());
    }

    public static void printUsage() {
        System.out.println("Usage: java -jar pathfinding.jar <map> <algorithm>");
        System.out.println("<map> - Filename of the map file to use");
        System.out.println("<algorithm> - Algorithm to use for pathfinding");
        System.out.println("     BFS = Breadth-first search");
        System.out.println("     IDS = Iterative deepening search");
        System.out.println("     AS  = A* Search");
    }
}
