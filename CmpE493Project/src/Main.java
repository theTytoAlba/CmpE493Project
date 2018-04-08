import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {

	public static void main(String[] args) {
		// Read the tweets.
		ArrayList<Tweet> tweets = new ArrayList<>();
		try {
			Scanner in = new Scanner(new File("10.txt"));
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
		for (int i = 0; i < tweets.size(); i++) {
			System.out.println("Tweet " + (i+1) + "/" + tweets.size() + ":");
			System.out.println(tweets.get(i).text);
			tweets.get(i).annotation = in.nextInt();
		}
		in.close();
		
		// Write the annotated tweets.
		PrintWriter writer;
		try {
			writer = new PrintWriter("10_annotated.txt");
			for (int i = 0; i < tweets.size(); i++) {
				writer.println(tweets.get(i).tweetToString());
			}
			writer.close();			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

}
