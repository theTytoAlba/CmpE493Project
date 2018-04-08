import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {
	public static final int EXIT_CODE = 444;
	public static final int NOT_ANNOTATED = 333;

	public static void main(String[] args) {
		System.out.println("To save current annotation and exit, write " + EXIT_CODE + " during annotation.");
		ArrayList<Tweet> tweets = new ArrayList<>();
		// Check if there is a partially done annotation.
		try {
			Scanner in = new Scanner(new File("10_partial.txt"));
			while (in.hasNextLine()) {
				tweets.add(new Tweet(in.nextLine()));
			}
			System.out.println("Found partially done file. Collected " + tweets.size() + " tweets.");
			in.close();
		} catch (FileNotFoundException e) {}
		
		// Read the remaining tweets.
		try {
			Scanner in = new Scanner(new File("10.txt"));
			// Do not re-read the already done tweets.
			for (int i = 0; i < tweets.size(); i++) {
				in.nextLine();
			}
			while (in.hasNextLine()) {
				tweets.add(new Tweet(in.nextLine()));
			}
			in.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return;
		}
		
		// Annotate the tweets.
		Scanner in = new Scanner(System.in);
		int annotatedLastTweet = -1;
		for (int i = 0; i < tweets.size(); i++) {
			// Skip the already annotated tweets.
			if (tweets.get(i).annotation != NOT_ANNOTATED) {
				continue;
			}
			System.out.println("Tweet " + (i+1) + "/" + tweets.size() + ":");
			System.out.println(tweets.get(i).text);
			int annotation = in.nextInt();
			// Exit code. Ignore rest of the annotation and save what we have.
			if (annotation == EXIT_CODE) {
				annotatedLastTweet = i-1;
				break;
			} else {
				tweets.get(i).annotation = annotation;
				annotatedLastTweet = i;	
			}
		}
		in.close();
		
		// Write the annotated tweets.
		PrintWriter writer;
		try {
			if (annotatedLastTweet == tweets.size()-1) {
				writer = new PrintWriter("10_annotated.txt");	
			} else {
				writer = new PrintWriter("10_partial.txt");
			}
			for (int i = 0; i <= annotatedLastTweet; i++) {
				writer.println(tweets.get(i).tweetToString());
			}
			writer.close();			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

}
