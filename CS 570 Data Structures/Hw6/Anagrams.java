//package Assignment6;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

/**
 * 
 * @author Kunj Desai
 *
 */
public class Anagrams {

	/**
	 * Variables required
	 */
	final Integer[] primes = {2, 3, 5, 7, 11, 13, 17, 19, 23, 29, 31, 37, 41, 43, 47, 53, 59, 61, 
			67, 71, 73, 79, 83, 89, 97, 101};
	Map<Character,Integer> letterTable; 
	Map<Long,ArrayList<String>> anagramTable;
	
	/**
	 * Constructor - It initializes anagramTable and call buildLetterTable()
	 */
	public Anagrams ()
	{
		anagramTable = new HashMap<Long,ArrayList<String>>();
		buildLetterTable();
	}
	
	/**
	 * Build a map with key(a-z) and values as primes array respectively.
	 */
	private void buildLetterTable()
	{
		letterTable = new Hashtable<Character, Integer>();
		char key ='a';
		for(int i = 0; i< primes.length ; i++)
		{
			letterTable.put(key, primes[i]);
			key+=1;
		}
		
	}
	
	/**
	 * Takes a string and add it to array list on basis of generated hash code from myHashCode()
	 * Also add that pair to anagramTable.
	 * 
	 * @param s String to be added
	 */
	private void addWord(String s)
	{
		ArrayList<String> addAnagram = new ArrayList<String>();
		Long generatedHashCode = myHashCode(s);
		
		if(anagramTable.get(generatedHashCode) != null)
		{
			addAnagram = anagramTable.get(generatedHashCode);
		}
		
		addAnagram.add(s);
		anagramTable.put(generatedHashCode, addAnagram);
		
	}
	
	/**
	 * Generates hash code from the given string.
	 * 
	 * @param s String value
	 * @return Long hashCode generated.
	 */
	private Long myHashCode(String s) 
	{
		Long hashCode = (long) 1;
		for(int i= 0 ; i< s.length(); i++)
		{
			hashCode = hashCode * letterTable.get(s.charAt(i));
		}
		
		return hashCode;
	}
	
	/**
	 * Process the file
	 * @param s String value.
	 * @throws IOException
	 */
	public void processFile(String s) throws IOException
	{
		FileInputStream fstream = new FileInputStream(s);
		BufferedReader br = new BufferedReader(new InputStreamReader(fstream)); 
		String strLine;
		while ((strLine = br.readLine()) != null) 
		{ 
			this.addWord(strLine);
		} 
		br.close();
	}
	
	/**
	 * Add pair of key and value/s if they are maximum.
	 * 
	 * @return maxAnagrams List containing maximum number of anagrams
	 */
	private ArrayList<Map.Entry<Long,ArrayList<String>>> getMaxEntries()
	{
		Long maxHash = (long) 0;
		ArrayList<Map.Entry<Long,ArrayList<String>>>  maxAnagrams = new ArrayList<Map.Entry<Long,ArrayList<String>>>();
		
		for(Map.Entry<Long,ArrayList<String>> anagramPair: anagramTable.entrySet())
		{
			if(anagramPair.getValue().size()> maxHash)
			{
				maxHash = (long) anagramPair.getValue().size();
				maxAnagrams.clear();
				maxAnagrams.add(anagramPair);
			}
			else if(anagramPair.getValue().size() == maxHash)
			{
				maxAnagrams.add(anagramPair);
			}
			
		}
		
		return maxAnagrams;
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		Anagrams a = new Anagrams();
		final long startTime = System.nanoTime();
		try 
		{
			a.processFile("words_alpha.txt");
		} 
		catch (IOException e1) 
		{
			e1.printStackTrace();
		}
		
		ArrayList<Map.Entry<Long, ArrayList<String>>> maxEntries = a.getMaxEntries();
		final long estimatedTime = System.nanoTime() - startTime;
		final double seconds = ((double) estimatedTime / 1000000000);
		System.out.println("Time: " + seconds);
		System.out.println("List of max anagrams: " + maxEntries);

	}

}
