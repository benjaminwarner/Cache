import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class CacheSimulator {

	public static void main(String[] args) {
		try {
			if (args.length < 3 || args.length > 4) {
				throw new Exception("invalid number of arguments");
			} else if (args.length == 3 && args[0].equals("2")) {
				throw new Exception("missing arg: L2 cache size");
			} else if (args.length == 4 && Integer.parseInt(args[2]) < Integer.parseInt(args[1])) {
				throw new Exception(String.format("invalid L2 cache size %s: must be >= L1 cache size %s", args[2], args[1]));
			}
		} catch (Exception ex) {
			usage();
			System.out.println(ex.getMessage());
			return;
		}
		
		String filepath = args[args.length - 1];

		Cache<String> lOneCache = new Cache<String>(Integer.parseInt(args[1]));
		System.out.printf("L1 cache with %s entries created\n", args[1]);
		Cache<String> lTwoCache = null;
		if (args.length == 4) {
			lTwoCache = new Cache<String>(Integer.parseInt(args[2]));
			System.out.printf("L2 cache with %s entries created\n", args[2]);
		}
		System.out.println(". . .");

		ArrayList<String> words;
		try {
			words = parseWordsFromFile(filepath);
		} catch (FileNotFoundException ex) {
			System.out.printf("Couldn't find %s\n", filepath);
			return;
		}

		for (String word : words) {
			lOneCache.add(word);
			if (lTwoCache != null)
				lTwoCache.add(word);
		}

		for (String word : words) {
			String data = lOneCache.get(word);
			if (data == null && lTwoCache != null)
				lTwoCache.get(word);
		}

		System.out.printf("L1 hit count: %d\n", lOneCache.getHitCount());
		System.out.printf("L1 hit rate:  %.3f%%\n", lOneCache.getHitRate() * 100);

		int hitCount = lOneCache.getHitCount();
		if (lTwoCache != null) {
			System.out.printf("\nL2 hit count: %d\n", lTwoCache.getHitCount());
			System.out.printf("L2 hit rate:  %.3f%%\n", lTwoCache.getHitRate() * 100);
			hitCount += lTwoCache.getHitCount();
		}

		System.out.printf("\nTotal number of accesses: %d\n", words.size());
		System.out.printf("Total number of hits: %d\n", hitCount);
		System.out.printf("Overall hit rate: %.3f%%\n", ((double) hitCount / (double) words.size()) * 100);
	}

	public static void usage() {
		System.out.println("CacheSimulator [1|2] [L1 cache size] [|L2 cache size] filename");
	}

	public static ArrayList<String> parseWordsFromFile(String filepath) throws FileNotFoundException {
		ArrayList<String> words = new ArrayList<String>();
		Scanner sc = new Scanner(new File(filepath));
		while (sc.hasNext()) {
			words.add(sc.next());
		}
		sc.close();
		return words;
	}
}
