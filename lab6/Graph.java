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

    private static final boolean DEBUG = true;

    TreeMap<Integer, LinkedList<Edge>> graph;

    Graph() {
        this.graph = new TreeMap<>();
    }

    Graph(Graph gh) {
        this.graph = new TreeMap<>();
        for (Map.Entry<Integer, LinkedList<Edge>> entry : gh.graph.entrySet()) {
            LinkedList<Edge> ll = entry.getValue();
            LinkedList<Edge> copy = new LinkedList<>();
            for (Edge edge : ll) {
                copy.add(new Edge(edge));
            }
            this.graph.put((int)entry.getKey(), copy);
        }
    }

    static class Edge implements Comparable<Edge> {
        private int from;
        private int to;
        private int weight;
        private boolean bi;

        Edge(int to) {
            this.to = to;
            this.from = 0;
            this.weight = 0;
            this.bi = true;
        }

        Edge(int to, int from) {
            this(to);
            this.from = from;
        }

        Edge(int to, int from, int weight) {
            this(to, from);
            this.weight = weight;
        }

        Edge(int to, int from, int weight, boolean bi) {
            this(to, from ,weight);
            this.bi = bi;
        }

        Edge (Edge edge) {
            this.to = edge.to;
            this.weight = edge.weight;
            this.from = edge.from;
            this.bi = edge.bi;
        } 

        @Override
        public int compareTo(Edge edge) {
            if (this.weight != edge.weight) {
                return this.weight - edge.weight;
            } else {
                // Check if same direction edge are same
                if (this.from == edge.from && this.to == edge.to) {
                    return 0;
                }
                // Ceck if reverse direction edge are same
                if (this.bi && this.from == edge.to && this.to == edge.from) {
                    return 0;
                }
                return 1;
            }
        }

        public Edge getReverse() {
            Edge rev = new Edge(this.from, this.to, this.weight);
            return rev;
        }

        public boolean isParallel(Edge other) {
            return (this.from == other.from && this.to == other.to);
        }

        public boolean isLoop() {
            return this.from == this.to;
        }

        public static Comparator<Edge> getComparator() {
            return new Comparator<Edge>() {
                @Override
                public int compare(Edge edge1, Edge edge2) {
                    if (edge1.weight != edge2.weight) {
                        return edge1.weight - edge2.weight;
                    } else {
                        if (edge1.from == edge2.to && edge1.to == edge2.from) {
                            return 0;
                        }
                        if ((edge1.bi || edge2.bi) && edge1.from == edge2.to && edge1.to == edge2.from) {
                            return 0;
                        }
                        return 1;
                    }
                }
            };
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
            return this.from + " --" + this.weight + "--> " + this.to;
        }
    }

    public static Graph fromFile(String filename) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(filename)));
        Graph gh = new Graph();
        while (true) {
            String input = br.readLine();
            if (input == null) break;
            StringTokenizer st = new StringTokenizer(input);
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

    public Graph makePureGraph() {
        Graph gh = new Graph(this);
        for (Map.Entry<Integer, LinkedList<Edge>> entry : gh.graph.entrySet()) {
            LinkedList<Edge> edges = entry.getValue();
            LinkedList<Edge> toRemove = new LinkedList<>();
            for (int i = 0; i < edges.size(); i++) {
                if (edges.get(i).isLoop()) {
                    toRemove.add(edges.get(i));
                    continue;
                }
                Edge edgeA = edges.get(i);
                for (int j = i + 1; j < edges.size(); j++) {
                    Edge edgeB = edges.get(j);
                    if (edgeA.isParallel(edgeB)) {
                        toRemove.add(edges.get(edgeA.weight > edgeB.weight ? i : j));
                    }
                }
            }
            edges.removeAll(toRemove);
        }
        return gh;
    }

    public void printGraph() {
        for (Map.Entry<Integer, LinkedList<Edge>> entry : graph.entrySet()) {
            System.out.print(entry.getKey() + ": ");
            for (Edge edge : entry.getValue()) {
                System.out.print("(" + edge.getTo() + ", " + edge.getWeight() + "), ");
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

    private void DFS(int parent, ArrayList<Integer> path, boolean[] visited) {
        path.add(parent);
        visited[parent - 1] = true;
        if (this.graph.containsKey(parent)) {
            LinkedList<Edge> edges = this.graph.get(parent);
            for (Edge edge : edges) {
                if (!visited[edge.to - 1]) {
                    DFS(edge.to, path, visited);
                }
            }
        }
    }

    public Graph kruskalMST() {

        if (DEBUG) {
            System.out.println();
            System.out.println("-------------DEBUG LOG (KURSKAL) START-----------------");
        }
        Graph gh = new Graph();
        for (int key : this.graph.keySet()) {
            gh.graph.put(key, new LinkedList<Edge>());
        }

        TreeSet<Edge> ts = new TreeSet<>(Edge.getComparator());

        for (Map.Entry<Integer, LinkedList<Edge>> entry : this.graph.entrySet()) {
            for (Edge edge : entry.getValue()) {
                ts.add(new Edge(edge));
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
                gh.graph.get(edge.getFrom()).add(new Edge(edge));
                // Bidirection support
                Edge revEdge = edge.getReverse();
                gh.graph.get(revEdge.getFrom()).add(new Edge(revEdge));
                edgeCount++;
                if (DEBUG) {
                    System.out.println("Adding edge " + edge);
                    System.out.println("Current Edge Count " + edgeCount);
                    System.out.println("Union Find set: " + Arrays.toString(uf.parent));
                }
            } catch (Exception ex) {
                // Skip edge that causes cycle
                if (DEBUG) {
                    System.out.println("Edge: " + edge + " makes a cycle");
                }
                continue;
            }
        }

        if (DEBUG) {
            System.out.println("Sorted edges order: ");
            System.out.println(ts);
        }


        if (DEBUG) {
            System.out.println("-------------DEBUG LOG (KURSKAL) END-----------------");
            System.out.println();
        }
        return gh;
    }

    public Graph primsMst() {

        if (DEBUG) {
            System.out.println();
            System.out.println("-------------DEBUG LOG (PRIMS) START-----------------");
        }
        Graph gh = new Graph();
//        Remove all the loops and parellel edges
        Graph pg = this.makePureGraph();
//      Keep a set of Standing Edges
        TreeSet<Edge> stnd = new TreeSet<>(Edge.getComparator());
        int numberOfnodes = pg.graph.size();
        if (DEBUG) {
            System.out.println("Number of nodes: "+numberOfnodes);
        }
        // Initialy put first vertex
        gh.graph.put(pg.graph.firstKey(), new LinkedList<Edge>());
        for (Edge edge : pg.graph.firstEntry().getValue()) {
            stnd.add(edge);
        }
        
        if (DEBUG)
            System.out.println("Initially Standing edges: "+stnd);
        
        while (stnd.size() > 0 && gh.graph.size() < numberOfnodes) {
            // While removing is possible and loops are formed
            Edge smallest = stnd.first();
            Edge copyOfSmall = new Edge(smallest);
            stnd.removeIf(e -> e.compareTo(copyOfSmall) == 0);
            
            if (DEBUG)
                System.out.println("Current smallest Edge: "+smallest);

            while (stnd.size() > 0 && gh.graph.containsKey(smallest.to)) {                
                if (DEBUG)
                    System.out.println("Since cycle is formed finding new smallest");
                smallest = stnd.first();
                Edge copyOfSmall2 = new Edge(smallest);
                stnd.removeIf(e -> e.compareTo(copyOfSmall2) == 0);
            
                if (DEBUG)
                    System.out.println("Current Smallest Edge: "+smallest);
            }
            // Include the smallest edge in new graph
            gh.graph.get(smallest.from).add(new Edge(smallest));
            LinkedList<Edge> temp = new LinkedList<>();
            temp.add(new Edge(smallest.getReverse()));
            gh.graph.put(smallest.to, temp);
            if (DEBUG) {
                System.out.println("Current Graph: ");
                gh.printGraph();
                System.out.println();
            }

            // Remove the smallest edge from the original graph
            Edge copyOFSmall = new Edge(smallest);
            pg.graph.get(smallest.from).removeIf(e -> e.compareTo(copyOFSmall) == 0);
            pg.graph.get(smallest.to).removeIf(e -> e.compareTo(copyOFSmall) == 0);
            
            if (DEBUG) {
                System.out.println("Original Graph: ");
                pg.printGraph();
                System.out.println();
            }

            // Update standing with edges of the new node
            for (Edge edge : pg.graph.get(smallest.to)) {
                stnd.add(edge);
            }
            if (DEBUG) {
                System.out.println("Current Standing: "+stnd);
            }
        }

        if (DEBUG) {
            System.out.println("-------------DEBUG LOG (PRIMS) END-----------------");
            System.out.println();
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

        void union(int i, int j) throws ExceptionCycleCheck {
            if (checkIfsameParent(i, j)) throw new ExceptionCycleCheck();
            int xroot = find(i), yroot = find(j);
            if (xroot == yroot) return;

            if (this.rank[xroot] < this.rank[yroot]) {
                this.parent[xroot] = yroot;
            } else if (this.rank[xroot] > this.rank[yroot]) {
                this.parent[yroot] = xroot;
            } else {
                this.parent[yroot] = xroot;
                this.rank[xroot]++;
            }
        }

        class ExceptionCycleCheck extends Exception {
            private static final long serialVersionUID = 1L;

            ExceptionCycleCheck() {
                super("Cycle deteceted");
            }
        }
    }
}