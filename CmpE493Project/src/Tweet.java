
public class Tweet {
	String id;
	String text;
	int annotation;
	public Tweet(String tweetLine) {
		// lines are in form "<annotation>\t<id>\t<tweet>".
		String[] parts = tweetLine.split("\t");
		try {
			// Tweet may be already annotated.
			annotation = Integer.parseInt(parts[0]);
		} catch (Exception e) {
			// If not, mark it with 333.
			annotation = 333;
		}
		// Other values always exist.
		id = parts[1];
		text = parts[2];
	}
	
	public String tweetToString() {
		return annotation + "\t" + id + "\t" + text;
	}
}
