package com.company; /**
 * Graph.java
 * @author John Lam
 * CIS 22C, Final Project
 */

import java.util.ArrayList;

public class Graph {
    private int vertices;
    private int edges;
    private ArrayList<User> user;
    private ArrayList<List<Integer>> adj;
    private ArrayList<Character> color;
    private ArrayList<Integer> distance;
    private ArrayList<Integer> parent;

    /**Constructors*/

    /**
     * initializes an empty graph, with n vertices
     * and 0 edges
     * @param n the number of vertices in the graph
     */
    public Graph(int n) {
        this.vertices = n;
        this.edges = 0;
        this.user = new ArrayList<User>();
        this.adj = new ArrayList<List<Integer>>();
        this.color = new ArrayList<Character>();
        this.distance = new ArrayList<Integer>();
        this.parent = new ArrayList<Integer>();

        for (int i = 0; i < n; i++) {
            adj.add(new List<Integer>());
        }
    }


    /*** Accessors ***/

    /**
     * Returns the number of edges in the graph
     * @return the number of edges
     */
    public int getNumEdges() {
        return edges;
    }

    /**
     * Returns the number of vertices in the graph
     * @return the number of vertices
     */
    public int getNumVertices() {
        return vertices;
    }

    /**
     * returns whether the graph is empty (no edges)
     * @return whether the graph is empty
     */
    public boolean isEmpty() {
        return vertices == 0;
    }

    public int getUser(String userName) throws IndexOutOfBoundsException {
        for(int i = 0; i < user.size(); i++) {
            if (user.get(i).getUserName().equals(userName)) {
                return i;
            }
        }
        return -1;
    }

    /**
     * Returns the value of the distance[v]
     * @param v a vertex in the graph
     * @precondition 0 < v <= vertices
     * @return the distance of vertex v
     * @throws IndexOutOfBoundsException when
     * the precondition is violated
     */
    public int getDistance(int v) throws IndexOutOfBoundsException{
        if (v <= 0 && v > vertices) {
            throw new IndexOutOfBoundsException("getDistance(): " + v + " is out of bounds.");
        }
        return distance.get(v);
    }

    /**
     * Returns the value of the parent[v]
     * @param v a vertex in the graph
     * @precondition v <= vertices
     * @return the parent of vertex v
     * @throws IndexOutOfBoundsException when the precondition is violated
     */
    public int getParent(int v) throws IndexOutOfBoundsException {
        if (v > vertices) {
            throw new IndexOutOfBoundsException("getParent(): " + v + " is out of bounds.");
        }
        return parent.get(v);
    }

    /**
     * Returns the value of the color[v]
     * @param v a vertex in the graph
     * @precondition 0 < v <= vertices
     * @return the color of vertex v
     * @throws IndexOutOfBoundsException when the precondition is violated
     */
    public Character getColor(int v) throws IndexOutOfBoundsException {
        if (v <= 0 && v > vertices) {
            throw new IndexOutOfBoundsException("getColor(): " + v + " is out of bounds.");
        }
        return color.get(v);
    }

    /*** Mutators ***/

    public void removeDirectedEdge(int vertex, int newVertex) throws IndexOutOfBoundsException {
        if (vertex < 0 && newVertex > this.vertices) {
            throw new IndexOutOfBoundsException("getColor(): " + newVertex + " is out of bounds.");
        } else {
            adj.get(vertex).placeIterator();
            while(adj.get(vertex).getIterator() < adj.get(vertex).getLength()) {
                if (adj.get(vertex).getIterator() == newVertex) {
                    adj.get(vertex).removeIterator();
                    break;
                }
                adj.get(vertex).advanceIterator();
            }
        }
    }

    public void removeUndirectedEdge(int vertex, int newVertex) {
        removeDirectedEdge(vertex, newVertex);
        removeDirectedEdge(newVertex, vertex);
        edges--;
    }

    /**
     * Inserts vertex v into the adjacency list of vertex u
     * (i.e. inserts v into the list at index u)
     * @precondition, 0 < u, v <= vertices
     * @throws IndexOutOfBoundsException when the precondition
     * is violated
     */
    public void addDirectedEdge(int vertex, int newVertex) throws IndexOutOfBoundsException {
        if (vertex < 0 && newVertex > this.vertices) {
            throw new IndexOutOfBoundsException("getColor(): " + newVertex + " is out of bounds.");
        } else {
            if (adj.get(vertex).linearSearch(newVertex) == -1) {
                if (adj.get(vertex).isEmpty()) {
                    adj.get(vertex).addLast(newVertex);
                    edges++;

                } else if (!adj.isEmpty() && adj.get(vertex).getLast() >= newVertex) {
                    if (adj.get(vertex).getFirst() >= newVertex) {
                        adj.get(vertex).addFirst(newVertex);
                        edges++;
                    } else {
                        adj.get(vertex).placeIterator();
                        while (adj.get(vertex).getIterator() < adj.get(vertex).getLength()) {
                            if (adj.get(vertex).getIterator() > newVertex) {
                                adj.get(vertex).reverseIterator();
                                adj.get(vertex).addIterator(newVertex);
                                edges++;

                                break;
                            } else {
                                adj.get(vertex).advanceIterator();
                            }
                        }
                    }
                } else {
                    adj.get(vertex).addLast(newVertex);
                    edges++;
                }
            }
        }
    }

    /**
     * Inserts vertex v into the adjacency list of vertex u
     * (i.e. inserts v into the list at index u)
     * and inserts u into the adjacent vertex list of v
     * @precondition, 0 < u, v <= vertices
     *
     */
    public void addUndirectedEdge(int vertex, int newVertex) throws IndexOutOfBoundsException {
        if (vertex < 0 && newVertex > vertices) {
            throw new IndexOutOfBoundsException("getColor(): " + newVertex + " is out of bounds.");
        }
        addDirectedEdge(vertex, newVertex);
        addDirectedEdge(newVertex, vertex);
        edges++;
    }

    /*** Additional Operations ***/

    /**
     * Creates a String representation of the Graph
     * Prints the adjacency list of each vertex in the graph,
     * vertex: <space separated list of adjacent vertices>
     */
    @Override public String toString() {
        StringBuilder result = new StringBuilder();
        for(int i = 0; i < adj.size(); i++) {
            result.append(i).append(": ").append(adj.get(i)).append("\n");
        }
        return result.toString();
    }

    /**
     * Prints the current values in the parallel ArrayLists
     * after executing BFS. First prints the heading:
     * v <tab> c <tab> p <tab> d
     * Then, prints out this information for each vertex in the graph
     * Note that this method is intended purely to help you debug your code
     */
    public void printBFS() {
        System.out.println("id \t\t\t v \t\t\t c \t\t\t p \t\t\t d");
        for (int i = 0; i < adj.size(); i++) {
            System.out.println(i + "\t\t\t" + adj.get(i) + "\t\t\t" + color.get(i) + "\t\t\t" + parent.get(i) + "\t\t\t" + distance.get(i));
        }
    }

    /**
     * Performs breath first search on this Graph give a source vertex
     * @param source
     * @precondition graph must not be empty
     * @precondition source is a vertex in the graph
     * @throws IllegalStateException if the graph is empty
     * @throws IndexOutOfBoundsException when the source vertex
     * is not a vertex in the graph
     */

    public void BFS(int source) throws IllegalStateException, IndexOutOfBoundsException {
        Queue<Integer> queue = new Queue<Integer>();
        if (isEmpty()) {
            throw new IllegalStateException("BFS(): graph is empty");
        }
        if (source > adj.size()) {
            throw new IndexOutOfBoundsException("BFS(): " + source + "is not in range." );
        }
        for (int i = 0; i < adj.size(); i++) {
            color.add(i, 'W');
            distance.add(i, -1);
            parent.add(i, null);
        }
        color.set(source, 'G');
        distance.set(source, 0);
        queue.enqueue(source);
        while(!queue.isEmpty()){
            int x = queue.getFront();
            queue.dequeue();
            adj.get(x).placeIterator();
            for (int i = 0; i < adj.get(x).getLength(); i++) {
                int iter = adj.get(x).getIterator();
                if (color.get(iter) == 'W') {
                    color.set(iter, 'G');
                    distance.set(iter, distance.get(x) + 1);
                    parent.set(iter, x);
                    queue.enqueue(iter);
                }
                adj.get(x).advanceIterator();
            }
            color.set(x, 'B');
        }
    }
}