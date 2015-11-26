package wordnet;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

public class Outcast {
	private WordNet wordnet;
	   public Outcast(WordNet wordnet)         // constructor takes a WordNet object
	   {
		   this.wordnet=wordnet;
	   }
	   public String outcast(String[] nouns)   // given an array of WordNet nouns, return an outcast
	   {
		   int[] totalDistances = new int[nouns.length];
		   
		   for(int i=0;i<nouns.length;i++)
		   {
			   for(int j=0;j<nouns.length;j++)
			   {
				   if(i!=j)
					   totalDistances[i]=wordnet.distance(nouns[i], nouns[j]);
			   }
			   
		   }
		   int maximum = totalDistances[0];
		   int maxIndex = 0;
		   for(int i =1;i<nouns.length;i++)
		   {
			   if(totalDistances[i]>maximum)
			   {
				   totalDistances[i]=maximum;
				   maxIndex = i;
			   }
		   }
		   return nouns[maxIndex];
	   }
	   public static void main(String[] args)  // see test client below
	   {
		   System.out.println(args[0]+ args[1]);
		    WordNet wordnet = new WordNet(args[0], args[1]);
		    Outcast outcast = new Outcast(wordnet);
		    for (int t = 2; t < args.length; t++) {
		        In in = new In(args[t]);
		        String[] nouns = in.readAllStrings();
		        StdOut.println(args[t] + ": " + outcast.outcast(nouns));
		  }
	   }
	}