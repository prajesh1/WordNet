package wordnet;

import java.util.ArrayList;
import java.util.HashMap;

import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.In;

public class WordNet {

	private ArrayList<Synset> synsets;
	Digraph G;
   // constructor takes the name of the two input files
   public WordNet(String s, String h)
   {
	  
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
    System.out.println("V is "+(synsets.size()));
    // Process for edges
    In hypernymsFile = new In(h);
   
    String hypernymsLine;

    while((hypernymsLine=hypernymsFile.readLine())!=null)
    {
  	 String[] hypernymnFields =hypernymsLine.split(",");
  	 for(int i =1;i<hypernymnFields.length;i++)
  	 {
  		G.addEdge(Integer.valueOf(hypernymnFields[0]), Integer.valueOf(hypernymnFields[i]));
  	 }

       //System.out.println(synsetLine);
     }
      
   }

   // returns all WordNet nouns
  public Iterable<String> nouns()
  {
	  ArrayList<String> nouns = new ArrayList<>();
	  for(int i =0;i<synsets.size();i++)
		  for(String s:synsets.get(i).synset)
			  nouns.add(s);
	  System.out.println("Number of nouns = "+nouns.size());
	  return nouns;
  }
  
//is the word a WordNet noun?
   public boolean isNoun(String word)
   {
	   for(String s:nouns())
		   if(word ==s) return true;
	   return false;
   }
   public int distance(String nounA, String nounB)
   {
	   SAP s = new SAP(G);
	   int v=-1,u=-1;
	   System.out.println("Sixxse is "+synsets.size());
	   for(int i=0;i<synsets.size();i++ )
	   {
		   for(String t:synsets.get(i).synset)
		  	if(t.equals(nounA))
			   u =synsets.get(i).synsetId;
		   for(String k:synsets.get(i).synset)
			  	if(k.equals(nounB))
				   v =synsets.get(i).synsetId;
	   }
	   System.out.println("value is "+synsets.get(2134).synset);
	   System.err.println("v,u ="+v+"  "+u);
	   return s.length(v, u);
   }
/*
   // a synset (second field of synsets.txt) that is the common ancestor of nounA and nounB
   // in a shortest ancestral path (defined below)
   public String sap(String nounA, String nounB)

   // do unit testing of this class*/	
   public static void main(String[] args)
   {
      WordNet word = new WordNet("synsets.txt", "hypernyms.txt");
      HashMap<String,Boolean> test = new HashMap<String,Boolean>();

      for(String m:word.nouns())
      {
    	  if(!test.containsKey(m))
    			  test.put(m,true);
    	
      }
      if(test.containsKey("region"))
      System.out.println("Total Nouns"+test.size());
  System.out.println("The distance = "+ word.distance("Brown_Swiss","barrel_roll"));
      
   }
}
