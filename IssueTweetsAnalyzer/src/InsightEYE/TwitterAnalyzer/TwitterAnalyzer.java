package InsightEYE.TwitterAnalyzer;

import java.util.List;

import InsightEYE.DB.DBManager;
import twitter4j.Query;
import twitter4j.Query.ResultType;
import twitter4j.QueryResult;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.auth.AccessToken;

public class TwitterAnalyzer {

	private final int MAX_TWEETS_PAGE = 3; // 트윗 페이지 수(조회용)
	private final int TWEETS_PER_PAGE = 50; // 페이지 당 트윗수
	private final AccessToken accesstoken = new AccessToken("746867608536784897-SKj7R1jxhB8IAhHedhDuAKMmcpAdeKN",
			"GfbiNa2X2iwEWqX0aY3vD8qOEBd9z70f32h3c7JRn2p6R"); // 트위터 API키
	Twitter twitter = null;
	private QueryResult qr = null;
	List<Status> tweets = null;
	DBManager dbm = null;

	public TwitterAnalyzer(DBManager _dbm) {
		twitter = TwitterFactory.getSingleton();
		twitter.setOAuthConsumer("ROVYQ3Unil0Las3ezHIXSfLQy", "eA9jUmMec5BMp8Uxsok3FuDGeAzyDxFAlkInWOUMc00mBaH8JK");
		twitter.setOAuthAccessToken(accesstoken);
		// API 키를 이용한 트위터 API 클래스 생성
		dbm = _dbm;
	}

	public void printTweets(Query query, int page) throws TwitterException {
		// 트윗 콘솔 출력용 메소드
		int i = 1;
		int j = 1;
		do {
			qr = twitter.search(query);
			tweets = qr.getTweets();
			System.out.println("==================" + j + "================");
			for (Status tweet : tweets) {
				System.out.println("[" + i + "]" + "[" + tweet.getCreatedAt() + "]" + "@"
						+ tweet.getUser().getScreenName() + " - " + tweet.getText());
				i++;
			}
			j++;
		} while ((query = qr.nextQuery()) != null && j <= page);
	}

	public void insertDB(Query query) {
		// 트윗 조회 및 결과 DB 입력 메소드
		int j = 1;
		try {
			do {
				System.out.println("쿼리 : " + query);
				qr = twitter.search(query);
				tweets = qr.getTweets();
				for (Status tweet : tweets) {
					System.out.println(tweet.getText());
					System.out.println("====================");
					dbm.insertTweet(tweet, query.getQuery());
				}
				j++;
			} while ((query = qr.nextQuery()) != null && j <= MAX_TWEETS_PAGE);
		} catch (TwitterException e) {
			// TODO Auto-generated catch block

		} catch (Exception e) {
			// TODO: handle exception

		}
	}

	public Query makeQuery(String _kwd, ResultType _type) {
		// 트위터 API 검색용 쿼리 생성 메소드
		// ResultType = [RECENT (최근) / POPULAR (인기) / MIXED (혼합)]
		Query query = new Query(_kwd);
		query.setCount(TWEETS_PER_PAGE);
		query.setResultType(_type);

		return query;
	}
}
