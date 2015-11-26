package wordnet;

import java.util.HashMap;

import edu.princeton.cs.algs4.BreadthFirstDirectedPaths;
import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

   public class SAP {
	   Digraph G;
	   int ancestor;

   // constructor takes a digraph (not necessarily a DAG)
   public SAP(Digraph G)
   {
	   this.G = G; 
	   // Need to check DAG
	
   }
   // length of shortest ancestral path between v and w; -1 if no such path
   public int length(int v, int w)
   {
	   HashMap<Integer,Integer> hashMap = new HashMap<Integer,Integer>();
	   BreadthFirstDirectedPaths bfs = new BreadthFirstDirectedPaths(G,v);
	   for(Integer i=0;i<G.V();i++) //For each vertex in G
		   if(bfs.hasPathTo(i))
			  { hashMap.put(i,i); System.out.println("bfs"+i);}
			   
	   BreadthFirstDirectedPaths bfsw = new BreadthFirstDirectedPaths(G,w);
	   int shortestAncestor =-1,sumOFDistances = -1;
	   for(Integer i=0;i<G.V();i++) //For each vertex in G"
		   if(bfsw.hasPathTo(i))
		   {
		   	System.out.println("bfsw has path to "+i);
			   if(hashMap.containsValue(i))
					   {
				   		 	System.out.println("bfsw distTo "+bfsw.distTo(i)+ " and i is "+i);
				   		 	System.out.println("bfs distTo "+bfs.distTo(i)+ " and i is "+i);
				   		 	System.out.println("sumOFDistances"+sumOFDistances);
				   		if((sumOFDistances > (bfsw.distTo(i) + bfs.distTo(i))) ||sumOFDistances ==-1)
				   			{
				   				System.out.println("True");
				   			sumOFDistances=bfsw.distTo(i) + bfs.distTo(i);
				   			shortestAncestor = i;
				   			}
					   }
		   }
	   this.ancestor = shortestAncestor;
	   return sumOFDistances;
	   
   }

   // a common ancestor of v and w that participates in a shortest ancestral path; -1 if no such path
   public int ancestor(int v, int w)
   {
	   return this.ancestor;
   }

  // length of shortest ancestral path between any vertex in v and any vertex in w; -1 if no such path
  public int length(Iterable<Integer> v, Iterable<Integer> w)
  {
	   int minLength = -1;
	   for(int x:v)
		   for(int y:w)
			   if((minLength <length(x,y))||(minLength ==-1))
				   minLength = length(x,y);
	  return minLength;
  }

  //  a common ancestor that participates in shortest ancestral path; -1 if no such path
  //public int ancestor(Iterable<Integer> v, Iterable<Integer> w)

   // do unit testing of this class
   public static void main(String[] args) {
    In in = new In(args[0]);
    Digraph G = new Digraph(in);
    SAP sap = new SAP(G);
    while (!StdIn.isEmpty()) {
        int v = StdIn.readInt();
        int w = StdIn.readInt();
        int length   = sap.length(v, w);
        int ancestor = sap.ancestor(v, w);
        StdOut.printf("length = %d, ancestor = %d\n", length, ancestor);
    }
}
}