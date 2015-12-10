import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

class Vertex {
	private Vertex next;
	private DisjointSet set;

	public Vertex() {
		next = null;
		set = null;
	}

	public DisjointSet find_set() {
		return set;
	}

	public void make_set() {
		DisjointSet ds = new DisjointSet(this);
		set = ds;
	}

	public Vertex getNext() {
		return next;
	}

	public void setNext(Vertex v) {
		next = v;
	}

	public void setSet(DisjointSet set) {
		this.set = set;
	}

	public void union(Vertex y) {
		//combining y with x
		Vertex temp_tail=null;
		Vertex t = set.getTail();
		Vertex h = y.find_set().getHead();
		while(h != null) {
			t.setNext(h);
			h.setSet(set);
			temp_tail = h;
			h = h.getNext();
			t = t.getNext();
		}
		//set the tail...forgot
		set.setTail(temp_tail);
	}
}

class DisjointSet {
	private static int id = 1;
	private Vertex head, tail;
	private int set_id;

	public DisjointSet(Vertex x) {
		head = x;
		tail = x;
		set_id = id++;
	}

	public Vertex getHead() {
		return head;
	}

	public Vertex getTail() {
		return tail;
	}

	public int getSetId() {
		return set_id;
	}

	public void setTail(Vertex v) {
		tail = v;
	}

	public boolean equals(Object o) {
		DisjointSet s = (DisjointSet)o;
		if(s.getSetId() == set_id) {
			return true;
		}
		return false;
	}
}

class Edge implements Comparable<Edge> {
	private Vertex v1, v2;
	private long weight;

	public Edge(Vertex v1, Vertex v2, long weight) {
		this.v1 = v1;
		this.v2 = v2;
		this.weight = weight;
	}

	public Vertex getVertexV1() {
		return v1;
	}

	public Vertex getVertexV2() {
		return v2;
	}

	public long getWeight() {
		return weight;
	}

	public int compareTo(Edge e) {
		long weight = e.getWeight();
		if(this.weight > weight) {
			return 1;
		} else if(this.weight < weight) {
			return -1;
		}
		return 0;
	}
}

public class CSTREET {
	public static void main(String[] args) {
		int n=0,m=0,i,j,t,p=0;
		long k;
		String[] inp;
		Vertex[] v;
		Edge[] e = null;
		long weight=0;

		InputStreamReader isr = new InputStreamReader(System.in);
		BufferedReader br = new BufferedReader(isr);
		try {
			t = Integer.parseInt(br.readLine());
			while(t-- > 0) {
				p = Integer.parseInt(br.readLine());
				n = Integer.parseInt(br.readLine());
				m = Integer.parseInt(br.readLine());
			}

			v = new Vertex[n+1];
			e = new Edge[m];

			for(int r=1;r<=n;r++) {
				//initializing vertices
				v[r] = new Vertex();
				v[r].make_set();
			}

			for(int l=0;l<m;l++) {
				inp = br.readLine().split(" ");
				i = Integer.parseInt(inp[0]);
				j = Integer.parseInt(inp[1]);
				k = Long.parseLong(inp[2]);
				e[l] = new Edge(v[i], v[j], k);
			}


			//sorting edges in ascending order
			Arrays.sort(e);

			for(int l=0;l<m;l++) {
				if(!e[l].getVertexV1().find_set().equals(e[l].getVertexV2().find_set())) {
					e[l].getVertexV1().union(e[l].getVertexV2());
					weight = weight + e[l].getWeight();
				}
			}

			System.out.println(weight*p);
		} catch (IOException ioe) {
			// TODO Auto-generated catch block
			ioe.printStackTrace();
		}
	}
}

