import sun.misc.IOUtils;

import java.io.*;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;


/*
* 1 —> The tweet is positive toward Boğaziçi University.
*
* -1 —> The tweet is negative toward Boğaziçi University.
*
* 0 —> The tweet doesn’t contain any sentiment toward Boğaziçi University.
*
* 2 —> The tweet is not relevant to Boğaziçi University.
*
*
* */


public class Main {
	public static final String ANNOTATED_FILE = "10_annotated.txt";
	public static final int EXIT_CODE = 444;
	public static final int NOT_ANNOTATED = 333;
	public static ArrayList<Integer> arr = new ArrayList<Integer>(Arrays.asList(1,2,3));
	public static void main(String[] args) {
		System.out.println("Press 1 for annotation\nPress 2 to review an annotated file\nPress 3 to check diff between two files");
		Scanner scan = new Scanner(System.in);
		int temp = scan.nextInt();
		if(arr.contains(temp)){
			if (temp==1){
				annotate();
			}
			else if(temp==2){
				review();
			}
			else if(temp==3){
				checkDiff();
			}
		}else{
			System.out.println("Aborting!");
		}
	}

	private static void review() {
		System.out.println("Collecting file : "+ANNOTATED_FILE);
		ArrayList<Tweet> tweets = new ArrayList<>();
		try {
			Scanner in = new Scanner(new File(ANNOTATED_FILE));
			while (in.hasNextLine()) {
				tweets.add(new Tweet(in.nextLine()));
			}
			System.out.println("Annotated file is found.");
			in.close();
		} catch (FileNotFoundException e) {
			System.out.println("File is not found!");
		}
		int starting_point = 0;
		Scanner point_checker = new Scanner(System.in);
		System.out.println("Type starting point, to start from beggining, type 0 and hit enter");
		starting_point = point_checker.nextInt();
		System.out.println("If you don't want to change annotation, hit enter, else type new annotation , 444 to quit");
		Scanner scan = new Scanner(System.in);
		String temp = "";
		for (int i = starting_point;i<tweets.size();i++){
			System.out.println("Line : "+i);
			System.out.println(tweets.get(i).getAnnotation() + "\t" + tweets.get(i).getText());
			temp = scan.nextLine();
			if(temp.equals(""))
				continue;
			else if(Integer.parseInt(temp)== 444){
				break;
			}
			else {
				tweets.get(i).setAnnotation(Integer.parseInt(temp));
			}
		}
		replaceFile(tweets);

	}

	private static void replaceFile(ArrayList<Tweet> tweets) {
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


	private static void checkDiff() {
	}

	public static void annotate(){
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
