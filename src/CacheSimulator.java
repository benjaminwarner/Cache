import java.util.ArrayList;
import java.util.Arrays;

public class CacheSimulator {

    public static void main(String []args) {
        if (args.length < 3 || args.length > 4) {
	    usage();
            System.out.println("invalid number of arguments");
	    return;
        } else if (args.length == 3 && args[0].equals("2")) {
	    usage();
            System.out.println("missing arg: L2 cache size");
	    return;
        } else if (args.length == 4 && Integer.parseInt(args[2]) < Integer.parseInt(args[1])) {
	    usage();
	    System.out.printf("invalid L2 cache size %s: must be >= L1 cache size %s\n", args[2], args[1]);
	    return;
	}

	String filepath = args[args.length - 1]; 
	// ArrayList<String> words = parseWordsFromFile(filepath);
	ArrayList<String> words = new ArrayList<String>(
		Arrays.asList(
		    "this", "words", "very", "big",
		    "many", "words", "this", "tiny",
		    "testing", "words", "can", "be", "very",
		    "hard", "this", "may", "be", "very", "easy"
		)
	);

	Cache<String> lOneCache = new Cache<String>(Integer.parseInt(args[1])); 
	System.out.printf("initialized L1 cache with %s capacity\n", args[1]);
	Cache<String> lTwoCache = null;
	if (args.length == 4) {
	    lTwoCache = new Cache<String>(Integer.parseInt(args[2]));
	    System.out.printf("initialized L2 cache with %s capacity\n", args[2]);
	}
	
	for (String word : words) {
	    lOneCache.add(word);
	    if (lTwoCache != null)
	        lTwoCache.add(word);
	}
	System.out.printf("Test data: %s\n", words.toString());
	System.out.printf("L1 cache contents: %s\n", lOneCache.toString());
	if (lTwoCache != null)
	    System.out.printf("L2 cache contents: %s\n", lTwoCache.toString());

	for (String word : words) {
	    String data = lOneCache.get(word);
	    if (data == null && lTwoCache != null)
	        lTwoCache.get(word);
	}

	System.out.println("\nResults");
	System.out.println("=====================================");

	System.out.printf("L1 hit count: %d\n", lOneCache.getHitCount());
	System.out.printf("L1 hit rate:  %.3f%%\n", lOneCache.getHitRate() * 100);
	System.out.printf("L1 cache contents: %s\n", lOneCache.toString());

	if (lTwoCache != null) {
	    System.out.printf("L2 hit count: %d\n", lTwoCache.getHitCount());
	    System.out.printf("L2 hit rate:  %.3f%%\n", lTwoCache.getHitRate() * 100);
	    System.out.printf("L2 cache contents: %s\n", lTwoCache.toString());
	}
    }

    public static void usage() {
    	System.out.println("CacheSimulator [1|2] [L1 cache size] [|L2 cache size] filename");
    }

    public static ArrayList<String> parseWordsFromFile(String filepath) {
        ArrayList<String> words = new ArrayList<String>();
	return words;
    }
}
