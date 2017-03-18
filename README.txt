******************************************
******************************************
        AI Pathfinding Program
******************************************
******************************************

Author: Jose Perez <josegperez@mail.com>

About:
    This program reads a map from a file and then proceeds to find a path 
    from the specified starting coordinates to the end goal coordinates
    using the specified algorithm for graph searching.
    
    3 sample maps are provided (map5x5.txt, map10x10.txt, map20x20.txt)

Usage: 
    Use the commandline to run the application jar file as follows
    <> denotes parameters which are required

    java -jar pathfinding.jar <map> <algorithm>
        <map> - Filename of the map file to use
        <algorithm> - Algorithm to use for pathfinding
            BFS = Breadth-first search
            IDS = Iterative deepening search
            AS  = A* Search

Notes:  
    A* Search uses Manhattan Distance as its heuristic