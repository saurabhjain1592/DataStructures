package graph;

import java.util.ArrayList;
import java.util.Deque;
import java.util.HashSet;
import java.util.LinkedList;

public class GraphAndBFS {
    private int v;
    private int e;
    private boolean directed;
    private ArrayList<ArrayList<Integer>> graph;

    public GraphAndBFS(int v) {
   	 this.v = v;
   	 this.e = 0;
   	 this.directed = false;
   	 this.graph = new ArrayList<ArrayList<Integer>>();
   	 for(int i=0; i<v; i++){
   		 graph.add(new ArrayList<Integer>());
   	 }
    }

    public GraphAndBFS(int v, boolean directed) {
   	 this.v = v;
   	 this.e = 0;
   	 this.directed = directed;
   	 this.graph = new ArrayList<ArrayList<Integer>>();
   	 for(int i=0; i<v; i++){
   		 graph.add(new ArrayList<Integer>());
   	 }
    }

    public GraphAndBFS(GraphAndBFS g) {
   	 this.v = g.v;
   	 this.e = g.e;
   	 this.directed = g.directed;
   	 this.graph = new ArrayList<ArrayList<Integer>>();
   	 for (ArrayList<Integer> al : g.graph) {
   		 ArrayList<Integer> bl = new ArrayList<Integer>();
   		 bl.addAll(al);
   		 this.graph.add(bl);
   	 }
    }

    public void addEdge(int x, int y) {
   	 if (directed) {
   		 graph.get(x).add(y);
   	 } else {
   		 graph.get(x).add(y);
   		 graph.get(y).add(x);
   	 }
   	 this.e++;
    }

    public boolean anyPathThere(int x, int y){
    	if(x==y)
        	return true;
    	boolean b[] = new boolean[v];
    	Deque<Integer> q = new LinkedList<Integer>();
    	q.addLast(x);
    	b[x] = true;
    	while(!q.isEmpty()){
        	int c = q.removeFirst();
        	for(int p: this.graph.get(c)){
            	if(b[p]){
                	// do nothing
            	}else{
                	if(p==y)
                    	return true;
                	b[p] = true;
                	q.addLast(p);
            	}
        	}
    	}
    	return false;
}

    public boolean bfs(int x) {
   	 boolean b[] = new boolean[v];
   	 for (int i = 0; i < v; i++) {
   		 if (b[i]) {
   			 // do nothing
   		 } else {
   			 Deque<Integer> q = new LinkedList<Integer>();
   			 b[i] = true;
   			 q.add(i);
   			 while (!q.isEmpty()) {
   				 int c = q.removeFirst();
   				 for (int p : graph.get(c)) {
   					 if (b[p]) {
   						 // do nothing
   					 } else {
   						 if (p == x)
   							 return true;
   						 b[p] = true;
   						 q.addLast(p);
   					 }
   				 }
   			 }
   		 }
   	 }
   	 return false;
    }

    public int shortestPath(int x, int y) {
   	 int dist[] = new int[v];
   	 for (int i = 0; i < v; i++) {
   		 dist[i] = Integer.MAX_VALUE;
   	 }
   	 dist[x] = 0;
   	 boolean b[] = new boolean[v];
   	 LinkedList<Integer> q = new LinkedList<Integer>();
   	 q.addLast(x);
   	 while (!q.isEmpty()) {
   		 int c = q.removeFirst();
   		 for (int p : this.graph.get(c)) {
   			 if(b[p]==true){
   				 // do nothing
   			 }else{
   				 b[p] = true;
   				 q.addLast(p);
   				 dist[p] = dist[c] + 1;
   				 if (p == y)
   					 return dist[p];
   			 }
   		 }
   	 }
   	 return dist[y]; // or return Integer.MAX_VALUE
    }

    public HashSet<HashSet<Integer>> connectedComponents() {
   	 HashSet<HashSet<Integer>> bigSet = new HashSet<HashSet<Integer>>();
   	 boolean b[] = new boolean[v];
   	 for (int i = 0; i < v; i++) {
   		 if (b[i] == true) {
   			 // do nothing
   		 } else {
   			 HashSet<Integer> set = new HashSet<Integer>();
   			 LinkedList<Integer> q = new LinkedList<Integer>();
   			 b[i] = true;
   			 q.addLast(i);
   			 set.add(i);
   			 while (!q.isEmpty()) {
   				 int c = q.removeFirst();
   				 for (int p : graph.get(c)) {
   					 if (b[p] == true) {
   						 // do nothing
   					 } else {
   						 b[p] = true;
   						 q.addLast(p);
   						 set.add(p);
   					 }
   				 }
   			 }
   			 bigSet.add(set);
   		 }
   	 }
   	 return bigSet;
    }

}