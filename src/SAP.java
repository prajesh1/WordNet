

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import edu.princeton.cs.algs4.BreadthFirstDirectedPaths;
import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

   public class SAP {
	  private  Digraph G;
	 private  int ancestor;
	 private boolean preVisited[];
	 private boolean postVisited[];


   // constructor takes a digraph (not necessarily a DAG)
   public SAP(Digraph G)
   {
	   this.G = new Digraph(G);
	   if(!isTree(G))
		   throw new java.lang.IllegalArgumentException();
	
   }
   
   private boolean isTree(Digraph G)
   {
	   preVisited= new boolean[G.V()];
	   postVisited= new boolean[G.V()];
	   for(int i=0;i<G.V();i++)
	   {
		   preVisited[i] = false;
		   postVisited[i] =false;
	   }
	   
	   for(int i=0;i<G.V();i++)
	   {
		   if(!preVisited[i])
		   {
			   if(i!=0) return false; //false on 2nd path
			   return DFS(i);
		   }
	   }
	   
	   return true;
   }
   
   private boolean DFS(int i)
   {
	   preVisited[i] =true;
	   
	   for(int s:G.adj(i))
	   {
		   if(!preVisited[s])
			   DFS(s);
		
	   }
	   
	   postVisited[i] =true;
	   return true;
   }
   // length of shortest ancestral path between v and w; -1 if no such path
   public int length(int v, int w)
   {
	  
	   
	   HashMap<Integer,Integer> hashMap = new HashMap<Integer,Integer>();
	   BreadthFirstDirectedPaths bfs = new BreadthFirstDirectedPaths(G,v);
	   for(Integer i=0;i<G.V();i++) //For each vertex in G
		   if(bfs.hasPathTo(i))
			  { hashMap.put(i,i);
			  // System.out.println("bfs"+i);
			  }
			   
	   BreadthFirstDirectedPaths bfsw = new BreadthFirstDirectedPaths(G,w);
	   int shortestAncestor =-1,sumOFDistances = -1;
	   for(Integer i=0;i<G.V();i++) //For each vertex in G"
		   if(bfsw.hasPathTo(i))
		   {
		  //	System.out.println("bfsw has path to "+i);
			   if(hashMap.containsValue(i))
					   {
				   		 //	System.out.println("bfsw distTo "+bfsw.distTo(i)+ " and i is "+i);
				   		 //	System.out.println("bfs distTo "+bfs.distTo(i)+ " and i is "+i);
				   		// 	System.out.println("sumOFDistances"+sumOFDistances);
				   		if((sumOFDistances > (bfsw.distTo(i) + bfs.distTo(i))) ||sumOFDistances ==-1)
				   			{
				   				//System.out.println("True");
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
	   length(v,w);
	   return this.ancestor;
   }

  // length of shortest ancestral path between any vertex in v and any vertex in w; -1 if no such path
  public int length(Iterable<Integer> v, Iterable<Integer> w)
  {
	  if(v == null||w == null)
		   throw new java.lang.NullPointerException();
	   int minLength = (int)Double.POSITIVE_INFINITY;
	   int shortestX=-1;
	   int shortestY =-1;
	   for(int x:v)
		   for(int y:w)
		   { 
			   int tempminLength =length(x,y);
			   if((minLength >tempminLength)||(minLength ==-1))
				   {
				   minLength = tempminLength;
				   shortestX = x; shortestY =y;
				   }
		   }
	  return length(shortestX,shortestY); // for fixing ancestor
  }

  //  a common ancestor that participates in shortest ancestral path; -1 if no such path
  public int ancestor(Iterable<Integer> v, Iterable<Integer> w)
  {
	  if(v == null||w == null)
		   throw new java.lang.NullPointerException();
	  	length(v,w);
	  	return this.ancestor;
  }

   // do unit testing of this class
   public static void main(String[] args) {
    In in = new In("files/digraph-wordnet.txt");
    Digraph G = new Digraph(in);
    SAP sap = new SAP(G);
    List<Integer> v = new ArrayList<Integer>();
   	v.add(77401);
    List<Integer> w = new ArrayList<Integer>();
    w.add(33587);w.add(40679);
    System.out.println(sap.length(v, w));
   /* while (!StdIn.isEmpty()) {
        int v = StdIn.readInt();
        int w = StdIn.readInt();
        int length   = sap.length(v, w);
        int ancestor = sap.ancestor(v, w);
        StdOut.printf("length = %d, ancestor = %d\n", length, ancestor);
    }*/
}
}