package wordnet;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.In;

public class WordNet {

	private ArrayList<Synset> synsets;
	//private HashMap<Integer,String[]> synsets;
	private Digraph G;
	 private boolean preVisited[];
	 private boolean postVisited[];
   // constructor takes the name of the two input files
   public WordNet(String s, String h)
   {
	   if(s==null||h==null)
		   throw new java.lang.NullPointerException();
	  
      In synsetsFile = new In(s);
    
   synsets = new ArrayList<Synset>();
   
    
      String synsetLine;
      String[] synsetFields ;
      while((synsetLine=synsetsFile.readLine())!=null)
      {
    	 synsetFields=synsetLine.split(",");
    	 String[] sets =synsetFields[1].split(" ");
    	 Synset synset = new Synset(Integer.valueOf(synsetFields[0]),sets);
    	 synsets.add(synset);
         //System.out.println(synsetLine);
       }
      
    G = new Digraph(synsets.size());
  
   //System.out.println("V is "+(synsets.size()));
    // Process for edges
    In hypernymsFile = new In(h);
   
    String hypernymsLine;

    while((hypernymsLine=hypernymsFile.readLine())!=null)
    {
  	 String[] hypernymnFields =hypernymsLine.split(",");
  	 for(int i =1;i<hypernymnFields.length;i++)
  	 {
  		G.addEdge(Integer.parseInt(hypernymnFields[0]), Integer.parseInt(hypernymnFields[i]));
  	 }

       
     }
    //System.out.println("Number of edges "+G.E());
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

   // returns all WordNet nouns
   public Iterable<String> nouns()
   {
 	  ArrayList<String> nouns = new ArrayList<>();

 	  for(int i =0;i<synsets.size();i++)
 		  for(String s:synsets.get(i).synset)
 		  {
 			  nouns.add(s);
 			 
 		  }
 	  //System.out.println("Total Number of nouns = "+nouns.size()+" and Unique nouns are "+dups.size());
 	 
 	  return nouns;
   }
  
//is the word a WordNet noun?
   public boolean isNoun(String word)
   {
	   if(word==null)
		   throw new java.lang.NullPointerException();
	   
	   for(int i =0;i<synsets.size();i++)
			  for(String s:synsets.get(i).synset)
				  	if(word.equals(s)) return true;
	   return false;
   }
   public int distance(String nounA, String nounB)
   {
	   if(nounA==null||nounB==null)
		   throw new java.lang.NullPointerException();
	   
	   SAP s = new SAP(G);
	   int v=-1,u=-1;
	   //System.out.println("Sixxse is "+synsets.size());
	   List<Integer> aIndexes = new ArrayList<Integer>();
	   List<Integer> bIndexes = new ArrayList<Integer>();
	   for(int i=0;i<synsets.size();i++ )
	   {
	
		 
		   for(String t:synsets.get(i).synset)
		  	{
			   if(t.equals(nounA))
				   aIndexes.add(synsets.get(i).synsetId);
			   if(t.equals(nounB))
				   bIndexes.add(synsets.get(i).synsetId);
		  	}
		  
	   }
	   //System.out.println("value is "+synsets.get(2134).synset);
	   //System.err.println("v,u ="+v+"  "+u);

	   int min = (int)Double.POSITIVE_INFINITY;
	   int  length=0;
	   for(int a:aIndexes)
		   for(int b:bIndexes)
		   {
			  length = s.length(a, b);
			   if(min>length)
				   min=length;
		   }
	   
	   if(aIndexes.size()==0||bIndexes.size()==0)
		   {
		   System.out.println(nounA+" and "+nounB);
		   throw new java.lang.IllegalArgumentException();
		 
		   }
				 return  min;
	   
   }

   // a synset (second field of synsets.txt) that is the common ancestor of nounA and nounB
   // in a shortest ancestral path (defined below)
   public String sap(String nounA, String nounB)
   {
	   if(nounA==null||nounB==null)
		   throw new java.lang.NullPointerException();
	   
	   SAP s = new SAP(G);
	   int v=-1,u=-1;
	   //System.out.println("Sixxse is "+synsets.size());
	   List<Integer> aIndexes = new ArrayList<Integer>();
	   List<Integer> bIndexes = new ArrayList<Integer>();
	   for(int i=0;i<synsets.size();i++ )
	   {
	
		 
		   for(String t:synsets.get(i).synset)
		  	{
			   if(t.equals(nounA))
				   aIndexes.add(synsets.get(i).synsetId);
			   if(t.equals(nounB))
				   bIndexes.add(synsets.get(i).synsetId);
		  	}
		  
	   }
	   //System.out.println("value is "+synsets.get(2134).synset);
	   //System.err.println("v,u ="+v+"  "+u);

	   int min = (int)Double.POSITIVE_INFINITY;
	   int  length=0;int su=-1,sv=-1;
	   for(int a:aIndexes)
		   for(int b:bIndexes)
		   {
			  length = s.length(a, b);
			   if(min>length)
			   {
				   su = a;sv=b;
				   min=length;
			   }
		   }
	   //System.out.println("value is "+synsets.get(2134).synset);
	   //System.err.println("v,u ="+v+"  "+u);
	   int ancestor = s.ancestor(su, sv);
	   String val = "";
	  
	   for(int i=0;i<synsets.size();i++)
	   {
		   if(synsets.get(i).synsetId==ancestor)
			   {
			   val = synsets.get(i).synset[0];
			   
			   for(int k =1;k<synsets.get(i).synset.length;k++)
				   val= val.concat(synsets.get(i).synset[k]);
			 }
		 
	 }
	   if(aIndexes.size()==0||bIndexes.size()==0)
	   
	   throw new java.lang.IllegalArgumentException();
	 
	  
	   return val;
	}

   // do unit testing of this class
   public static void main(String[] args)
   {
      WordNet word = new WordNet("files/synsets100-subgraph.txt", "files/hypernyms100-subgraph.txt");
      HashMap<String,Boolean> test = new HashMap<String,Boolean>();

      for(String m:word.nouns())
      {
    	  if(!test.containsKey(m))
    			  test.put(m,true);
    	
      }
      
     System.out.println("Total Nouns "+test.size());
  System.out.println("The distance = "+ word.sap("glutelin","filaggrin"));
      
   }
}
