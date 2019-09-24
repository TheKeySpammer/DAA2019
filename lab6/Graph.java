package lab6;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.TreeMap;
import java.io.*;

public class Graph {

    TreeMap<Integer, LinkedList<Edge>> graph;
    
    Graph() {
        graph = new TreeMap<>();
    }

    static class Edge {
        private int from;
        private int to;
        private int weight;
        Edge(int to) {
            this.to = to;
            this.from = 0;
            this.weight = 0;
        }

        Edge(int to, int from) {
            this(to);
            this.from = from;
        }

        Edge(int to, int from, int weight) {
            this(to, from);
            this.weight = weight;
        }

        public int getFrom() {
            return from;
        }


        public int getTo() {
            return to;
        }


        public int getWeight() {
            return weight;
        }        

    }

    public static Graph fromFile(String filename)throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(filename)));
        Graph gh = new Graph();
        while(true) {
            String input = br.readLine();
            if (input == null) break;
            StringTokenizer st =new StringTokenizer(input);
            int root = Integer.parseInt(st.nextToken());
            LinkedList<Edge> ll = new LinkedList<>();
            while (st.hasMoreTokens()) {
                ll.add(processEdge(st.nextToken()));
            }
            gh.graph.put(root, ll);
        }
        br.close();
        return gh;
    }

    public void printGraph() {
        for (Map.Entry<Integer, LinkedList<Edge>> entry : graph.entrySet()){
            System.out.print(entry.getKey()+": ");
            for (Edge edge : entry.getValue()) {
                System.out.print("("+edge.getTo()+", "+edge.getWeight()+"), ");
            }
            System.out.println();
        }
    }

    public ArrayList<Integer> DFS(int start) {
        ArrayList<Integer> path = new ArrayList<>();
        boolean[] visited = new boolean[this.graph.size()];
        DFS(start, path, visited);
        return path;
    }

    private void DFS(int parent, ArrayList<Integer> path, boolean[] visited)  {
        path.add(parent);
        visited[parent-1] = true;
        if (this.graph.containsKey(parent)) {
            LinkedList<Edge> edges = this.graph.get(parent);
            for (Edge edge : edges) {
                if (!visited[edge.to-1]){
                    DFS(edge.to, path, visited);
                }
            }
        }
    }


    private static Edge processEdge(String rawEdge) {
        int to = Integer.parseInt(rawEdge.substring(0, rawEdge.indexOf(',')).trim());
        int weight = Integer.parseInt(rawEdge.substring(rawEdge.indexOf(',') + 1).trim());
        return new Edge(to, 0, weight);
    }
}