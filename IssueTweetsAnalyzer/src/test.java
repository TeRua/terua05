import java.util.List;

import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterFactory;
import twitter4j.auth.AccessToken;

public class test {
	public static void main(String[] args) {
		try {
			AccessToken accesstoken = new AccessToken("746867608536784897-SKj7R1jxhB8IAhHedhDuAKMmcpAdeKN",
					"GfbiNa2X2iwEWqX0aY3vD8qOEBd9z70f32h3c7JRn2p6R");
			Twitter twitter = TwitterFactory.getSingleton();
			twitter.setOAuthConsumer("ROVYQ3Unil0Las3ezHIXSfLQy", "eA9jUmMec5BMp8Uxsok3FuDGeAzyDxFAlkInWOUMc00mBaH8JK");
			twitter.setOAuthAccessToken(accesstoken);

			Query query = new Query("단국대");
			query.resultType(Query.MIXED);
			
			query.setCount(100);
			QueryResult result;
			int i = 1;
			int j = 1;
			do {
				System.out.println("=================="+j+"================");
				result = twitter.search(query);
				List<Status> tweets = result.getTweets() ;

				for (Status tweet : tweets) {
					System.out.println("[" + i + "]" + "[" + tweet.getCreatedAt() + "]" + "@"
							+ tweet.getUser().getScreenName() + " - " + tweet.getText());
					i++;
				}
				j++;
			} while ((query = result.nextQuery()) != null);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
