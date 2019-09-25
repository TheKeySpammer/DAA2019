package lab6;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.TreeMap;
import java.util.TreeSet;
import java.io.*;

public class Graph {

    private static final boolean DEBUG = false;

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

        public Edge getReverse() {
            Edge rev = new Edge(this.from, this.to, this.weight);
            return rev;
        }

        public int getFrom() {
            return from;
        }

        public void setFrom(int from) {
            this.from = from;
        }

        public int getTo() {
            return to;
        }

        public void setTo(int to) {
            this.to = to;
        }

        public int getWeight() {
            return weight;
        }

        public void setWeight(int weight) {
            this.weight = weight;
        }

        @Override
        public String toString() {
            return this.from+" --"+this.weight+"--> "+this.to;
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
                Edge edge = processEdge(st.nextToken());
                edge.setFrom(root);
                ll.add(edge);
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

    public Graph kruskalMST() {
        Graph gh = new Graph();
        for (int key : this.graph.keySet()) {
            gh.graph.put(key, new LinkedList<Edge>());
        }

        TreeSet<Edge> ts = new TreeSet<>(new Comparator<Edge>() {
            @Override
            public int compare(Edge edge1, Edge edge2) {
                if (edge1.weight != edge2.weight) {
                    return edge1.weight - edge2.weight;
                }else {
                    if (edge1.from == edge2.to && edge1.to == edge2.from) {
                        return 0;
                    }
                    return 1;
                }
            }
        });

        for (Map.Entry<Integer, LinkedList<Edge>> entry : this.graph.entrySet()) {
            for (Edge edge : entry.getValue()) {
                ts.add(edge); 
            }
        }

        // ts contains edges ordered according to weight
        // gh is our new graph
        // Kruskal algorithm starts
        int edgeCount = 0;
        int vertexCount = this.graph.size();
        UnionFind uf = new UnionFind(vertexCount);
        // Key Padding for uf to bring it to 0
        int padding = this.graph.firstKey();
        if (DEBUG) {
            System.out.printf("VertexCount: %d\nPadding: %d\n", vertexCount, padding);
            System.out.println("Initial UF: ");
            System.out.println(Arrays.toString(uf.parent));
        }
        for (Edge edge : ts) {
            if (edgeCount == vertexCount - 1) break;
            try {
                
                uf.union(edge.from - padding, edge.to - padding);
                gh.graph.get(edge.getFrom()).add(edge);
                // Bidirection support
                Edge revEdge = edge.getReverse();
                gh.graph.get(revEdge.getFrom()).add(revEdge);
                edgeCount++;
                if (DEBUG) {
                    System.out.println("Adding edge "+edge);
                    System.out.println("Current Edge Count "+edgeCount);
                    System.out.println("Union Find set: "+Arrays.toString(uf.parent));
                }
            }catch(Exception ex) {
                // Skip edge that causes cycle
                if (DEBUG) {
                    System.out.println("Edge: "+edge+" makes a cycle");
                }
                continue;
            }
        }

        if (DEBUG) {
            System.out.println("Sorted edges order: ");
            System.out.println(ts);
        }
        return gh;
    }

    private static Edge processEdge(String rawEdge) {
        int to = 0, weight = 0;
        if (rawEdge.indexOf(',') != -1) {
            to = Integer.parseInt(rawEdge.substring(0, rawEdge.indexOf(',')).trim());
            weight = Integer.parseInt(rawEdge.substring(rawEdge.indexOf(',') + 1).trim());
        }
        return new Edge(to, 0, weight);
    }

    static class UnionFind {
        private int[] parent;
        private int[] rank;

        UnionFind(int n) {
            this.parent = new int[n];
            this.rank = new int[n];
            makeSet();
        }

        private void makeSet() {
            for (int i = 0; i < this.parent.length; i++) {
                this.parent[i] = i;
            }
        }

        int find(int i) {
            if (this.parent[i] != i) {
                this.parent[i] = find(this.parent[i]);
            }
            return this.parent[i];
        }

        int findWithoutCache(int i) {
            if (this.parent[i] == i) return i;
            return findWithoutCache(this.parent[i]);
        }

        private boolean checkIfsameParent(int i, int j) {
            return findWithoutCache(i) == findWithoutCache(j);
        }

        void union (int i, int j) throws ExceptionCycleCheck {
            if (checkIfsameParent(i, j)) throw new ExceptionCycleCheck();
            int xroot = find(i), yroot = find(j);
            if (xroot == yroot) return;

            if (this.rank[xroot] < this.rank[yroot]) {
                this.parent[xroot] = yroot;
            }
            else if (this.rank[xroot] > this.rank[yroot]) {
                this.parent[yroot] = xroot;
            }
            else {
                this.parent[yroot] = xroot;
                this.rank[xroot]++;
            }
        }

        class ExceptionCycleCheck extends Exception{
            private static final long serialVersionUID = 1L;
            ExceptionCycleCheck() {
                super("Cycle deteceted");
            }
        }
    }
}