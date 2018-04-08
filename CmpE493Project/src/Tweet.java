
public class Tweet {
	String id;
	String text;
	int annotation;
	public Tweet(String tweetLine) {
		// lines are in form "\t<id>\t<tweet>".
		String[] parts = tweetLine.split("\t");
		id = parts[1];
		text = parts[2];
	}
	
	public String tweetToString() {
		return annotation + "\t" + id + "\t" + text;
	}
}
