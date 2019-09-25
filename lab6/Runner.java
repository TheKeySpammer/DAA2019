package lab6;

import java.io.IOException;
import java.util.ArrayList;

class Main {
    public static void main(String[] args)throws IOException {
        Graph gh = Graph.fromFile("./lab6/graphData");
        gh.printGraph();
        ArrayList<Integer> path = gh.DFS(1);
        System.out.println("DFS from 1: ");
        System.out.println(path);
        Graph mst = gh.kruskalMST();
        System.out.println("Minimum spanning tree: ");
        mst.printGraph();
    }
}