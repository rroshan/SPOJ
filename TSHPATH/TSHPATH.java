import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import java.util.HashMap;
import java.util.Map;

class Vertex {
    private int weight;
    private Vertex[] adj;
    private int[] adjWeights;
    private int index; //index in the heap
    private boolean visited;


    public Vertex(int weight, int index, boolean visited) {
        this.weight = weight;
        this.index = index;
        this.visited = visited;
    }

    public void setAdjacentVertices(Vertex[] adj) {
        this.adj = adj;
    }

    public void setAdjWeights(int[] adjWeights) {
        this.adjWeights = adjWeights;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public int getWeight() {
        return weight;
    }

    public Vertex[] getAdj() {
        return adj;
    }

    public int getAdjWeight(int i) {
        return adjWeights[i];
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public int getIndex() {
        return index;
    }

    public void setVisited(boolean visited) {
        this.visited = visited;
    }

    public boolean getVisited() {
        return visited;
    }
}

class TSHPATH {
    private static int heap_size;

    private static void min_heapify(Vertex[] v, int i) {
        int smallest = i;
        Vertex temp;
        int tempIndex;

        int l = 2 * i;
        int r = (2 * i) + 1;

        if (l <= TSHPATH.heap_size) {
            if (v[l].getWeight() < v[i].getWeight()) {
                smallest = l;
            } else {
                smallest = i;
            }
        }

        if (r <= TSHPATH.heap_size) {
            if (v[r].getWeight() < v[l].getWeight()) {
                smallest = r;
            }
        }

        if (smallest != i) {
            temp = v[i];
            v[i] = v[smallest];
            v[smallest] = temp;

            //exchange index
            tempIndex = v[i].getIndex();
            v[i].setIndex(v[smallest].getIndex());
            v[smallest].setIndex(tempIndex);

            min_heapify(v, smallest);
        }
    }

    private static Vertex extract_min(Vertex[] v) {
        Vertex temp;
        int tempIndex;
        Vertex min;

        min = v[1];

        temp = v[1];
        v[1] = v[TSHPATH.heap_size];
        v[TSHPATH.heap_size] = temp;

        tempIndex = v[1].getIndex();
        v[1].setIndex(v[TSHPATH.heap_size].getIndex());
        v[TSHPATH.heap_size].setIndex(tempIndex);

        TSHPATH.heap_size--;
        min_heapify(v, 1);
        return min;
    }

    private static void build_min_heap(Vertex[] v) {
        TSHPATH.heap_size = v.length - 1;
        for (int i = (v.length - 1) / 2; i >= 1; i--) {
            min_heapify(v, i);
        }
    }

    private static void relax(Vertex[] A, Vertex u, Vertex v, int w) {
        if (v.getWeight() > (u.getWeight() + w)) {
            v.setWeight(u.getWeight() + w);
            //need to call heap-decrease-key
            heap_decrease_key(A, v.getIndex(), v.getWeight());
        }
    }

    private static void heap_decrease_key(Vertex[] A, int i, int key) {
        Vertex temp;
        int tempIndex;

        if (key > A[i].getWeight()) {
            System.out.println("New key greater than current key");
        } else {
            A[i].setWeight(key);
            while (i > 1 && A[i / 2].getWeight() > A[i].getWeight()) {
                temp = A[i];
                A[i] = A[i / 2];
                A[i / 2] = temp;

                //exchange indexes
                tempIndex = A[i].getIndex();
                A[i].setIndex(A[i / 2].getIndex());
                A[i / 2].setIndex(tempIndex);

                i = i / 2;
            }
        }
    }

    public static void main(String[] args) {
        //declaration
        int s = 0, n = 0, P = 0, nr = 0, cost = 0, r = 0;
        String name = null, name1 = null, name2 = null;
        Vertex[] v = null, adj = null;
        int[] adjWeights = null;
        Vertex src = null, dest = null, u = null;
        Map<String, Vertex> cities = new HashMap<String, Vertex>();
        String[] split = new String[2];

        InputStreamReader isr = new InputStreamReader(System.in);
        BufferedReader br = new BufferedReader(isr);


        try {
            s = Integer.parseInt(br.readLine());
        } catch (IOException e) {
            e.printStackTrace();
        }
        while (s-- > 0) {
            //cleaning up data structure for different test cases
            cities.clear();

            //creating n vertices
            try {
                n = Integer.parseInt(br.readLine());
            } catch (IOException e) {
                e.printStackTrace();
            }
            v = new Vertex[n + 1];
            for (int i = 1; i <= n; i++) {
                v[i] = new Vertex(200000, i, false);
            }

            for (int i = 1; i <= n; i++) {
                try {
                    name = br.readLine();
                    P = Integer.parseInt(br.readLine());
                } catch (IOException e) {
                    e.printStackTrace();
                }
                adj = new Vertex[P + 1];
                adjWeights = new int[P + 1];
                for (int j = 1; j <= P; j++) {
                    try {
                        split = br.readLine().split(" ");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    nr = Integer.parseInt(split[0]);
                    cost = Integer.parseInt(split[1]);
                    adjWeights[j] = cost;
                    adj[j] = v[nr];
                }

                //setting adjacent vertices
                v[i].setAdjacentVertices(adj);
                //set weights of adjacent vertices
                v[i].setAdjWeights(adjWeights);
                //setting name in hash map
                cities.put(name, v[i]);
            }

            try {
                r = Integer.parseInt(br.readLine());
            } catch (IOException e) {
                e.printStackTrace();
            }
            while (r-- > 0) {
                try {
                    split = br.readLine().split(" ");
                } catch (IOException e) {
                    e.printStackTrace();
                }
                name1 = split[0];
                name2 = split[1];

                //cleaning up
                for (int i = 1; i <= n; i++) {
                    v[i].setWeight(200000);
                    v[i].setVisited(false);
                }

                //Dijkstra
                //identifying source with name
                src = cities.get(name1);
                //set source weight to 0
                src.setWeight(0);
                //build the Min heap
                build_min_heap(v);

                while (TSHPATH.heap_size > 0) {
                    u = extract_min(v);

                    adj = u.getAdj();
                    for (int k = 1; k < adj.length; k++) {
                        if (!adj[k].getVisited()) {
                            relax(v, u, adj[k], u.getAdjWeight(k));
                        }
                    }
                    u.setVisited(true);
                }

                dest = cities.get(name2);
                System.out.println(dest.getWeight());
            }
            
            if (s > 0) {
                try {
                    br.readLine();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
