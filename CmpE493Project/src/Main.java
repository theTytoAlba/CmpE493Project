import java.io.*;
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
	public static final String IRMAK = "10_irmak_annotated.txt";
	public static final String IRMAK_REVISED = "10_annotated_irmak_revised.txt";
	public static final String SALIH = "10_annotated_salih.txt";
	public static final String SALIH_REVISED = "10_annotated_salih_revised.txt";
	public static final String FINAL_FILE = "10_annotated_final.txt";
	public static final int EXIT_CODE = 444;
	public static final int NOT_ANNOTATED = 333;
	public static ArrayList<Integer> arr = new ArrayList<Integer>(Arrays.asList(1,2,3, 4));
	public static void main(String[] args) {
		System.out.println("Press 1 for annotation\nPress 2 to review an annotated file\nPress 3 to check diff between two files\nPress 4 to calculate kappa score.");
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
			} else if (temp == 4) {
				kappa();
			}
		}else{
			System.out.println("Aborting!");
		}
		scan.close();
	}

	private static void kappa() {
		// TODO Auto-generated method stub
		System.out.println("Collecting file : "+IRMAK + " and "+ SALIH);
		ArrayList<Tweet> tweetsIrmak = new ArrayList<>();
		ArrayList<Tweet> tweetsSalih = new ArrayList<>();
		try {
			Scanner inIrmak = new Scanner(new File(IRMAK_REVISED));
			Scanner inSalih = new Scanner(new File(SALIH_REVISED));
			while (inIrmak.hasNextLine()) {
				tweetsIrmak.add(new Tweet(inIrmak.nextLine()));
				tweetsSalih.add(new Tweet(inSalih.nextLine()));
			}
			System.out.println("Annotated file is found.");
			inIrmak.close();
			inSalih.close();
		} catch (FileNotFoundException e) {
			System.out.println("File is not found!");
		}
		
		double pA = 0;
		for (int i = 0; i < 600; i++) {
			if (tweetsIrmak.get(i).annotation == tweetsSalih.get(i).annotation) {
				pA++;
			}
		}
		pA = pA/600;
		
		double pE = 0;
		
		double ip1=0, ip_1=0, ip2=0, ip0=0;
		double sp1=0, sp_1=0, sp2=0, sp0=0;
		for (int i = 0; i < 600; i++) {
			switch(tweetsIrmak.get(i).annotation) {
				case 0: 
					ip0++;
					break;
				case 1: 
					ip1++;
					break;
				case 2: 
					ip2++;
					break;
				case -1: 
					ip_1++;
					break;
			}
			switch(tweetsSalih.get(i).annotation) {
				case 0: 
					sp0++;
					break;
				case 1: 
					sp1++;
					break;
				case 2: 
					sp2++;
					break;
				case -1: 
					sp_1++;
					break;
			}
		}
		pE = ((ip1/600)*(sp1/600) + (ip_1/600)*(sp_1/600) + (ip0/600)*(sp0/600) + (ip2/600)*(sp2/600));
		System.out.println("pA " + pA + ", pE " + pE);
		System.out.println("Kappa " + (pA-pE)/(1-pE));
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
			writer = new PrintWriter(FINAL_FILE);

			for (int i = 0; i < tweets.size(); i++) {
				writer.println(tweets.get(i).tweetToString());
			}
			writer.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	private static void replaceFileDiff(ArrayList<Tweet> tweetsIrmak,ArrayList<Tweet> tweetsSalih) {
		PrintWriter writerIrmak;
		PrintWriter writerSalih;

		try {
			writerIrmak = new PrintWriter(IRMAK_REVISED);
			writerSalih = new PrintWriter(SALIH_REVISED);

			for (int i = 0; i < tweetsIrmak.size(); i++) {
				writerIrmak.println(tweetsIrmak.get(i).tweetToString());
				writerSalih.println(tweetsSalih.get(i).tweetToString());
			}
			writerIrmak.close();
			writerSalih.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	private static void checkDiff() {
		System.out.println("Collecting file : "+IRMAK + " and "+ SALIH);
		ArrayList<Tweet> tweetsIrmak = new ArrayList<>();
		ArrayList<Tweet> tweetsSalih = new ArrayList<>();
		try {
			Scanner inIrmak = new Scanner(new File(IRMAK_REVISED));
			Scanner inSalih = new Scanner(new File(SALIH_REVISED));
			while (inIrmak.hasNextLine()) {
				tweetsIrmak.add(new Tweet(inIrmak.nextLine()));
				tweetsSalih.add(new Tweet(inSalih.nextLine()));
			}
			System.out.println("Annotated file is found.");
			inIrmak.close();
			inSalih.close();
		} catch (FileNotFoundException e) {
			System.out.println("File is not found!");
		}
		System.out.println("If you don't want to change annotation, hit enter, else type new annotation , 444 to quit");
		Scanner scan = new Scanner(System.in);
		int temp;
		for (int i = 0;i<tweetsIrmak.size();i++){
			if(tweetsIrmak.get(i).getAnnotation()!=tweetsSalih.get(i).getAnnotation()) {
				System.out.println("Tweet number: "+ (i+1) + "/600");
				System.out.println(tweetsIrmak.get(i).getText());
				System.out.println("Irmak says: "+ tweetsIrmak.get(i).getAnnotation() + "\tSalih says: " + tweetsSalih.get(i).getAnnotation());
				System.out.println("Irmak's new annotation : ");
				temp = scan.nextInt();
				if (temp == 444) {
					break;
				} else {
					tweetsIrmak.get(i).setAnnotation(temp);
				}
				/*System.out.println("Salih's new annotation : ");
				temp = scan.nextInt();
				if (temp == 444) {
					break;
				} else {
					tweetsSalih.get(i).setAnnotation(temp);
				}*/
			}
		}
		replaceFile(tweetsIrmak);
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
