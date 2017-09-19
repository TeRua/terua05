package InsightEYE.DB;

import java.sql.BatchUpdateException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;

import twitter4j.Status;

public class DBManager {
	String driver = "org.mariadb.jdbc.Driver";
	//String url = "jdbc:mariadb://14.63.226.93:3306/ArticleScraper?characterEncoding=utf8mb4";
	String url = "jdbc:mysql://127.0.0.1:3306/articlescraper?characterEncoding=utf8mb4";
	String uId = "root";
	String uPwd = "kms02580";
	public Connection con;
	PreparedStatement pstmt;
	ResultSet rs;
	String sql = "INSERT INTO Tweets(Date, Keyword, User, Text) VALUES(?, ?, ?, ?)";
	String sql2 = "SELECT Keyword, Count FROM Issued_Kwd WHERE StartDate = ? AND EndDate = ? ORDER BY Count DESC LIMIT ?";
	Calendar cal = Calendar.getInstance();
	
	public DBManager() {
		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, uId, uPwd);
		} catch (ClassNotFoundException e) {
			System.out.println("Couldn't load driver.");
		} catch (SQLException e) {
			System.out.println("Couldn't connect to DB server.");
		} finally {
			System.out.println("DBManager construction has done.");
		}
	}

	public ArrayList<String> getIssuedKeyword(String _startDate, String _endDate, int _quantity) {
		ArrayList<String> kwd = new ArrayList<>();
		try {
			pstmt = con.prepareStatement(sql2);
			pstmt.setString(1, _startDate);
			pstmt.setString(2, _endDate);
			pstmt.setInt(3, _quantity);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				kwd.add(rs.getString("Keyword"));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return kwd;

	}

	public void insertTweet(Status tweet, String _kwd) {
		try {
			cal.setTime(tweet.getCreatedAt());
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, cal.get(Calendar.YEAR)+"-"+(cal.get(Calendar.MONTH)+1)+"-"+cal.get(Calendar.DAY_OF_MONTH));
			pstmt.setString(2, _kwd);
			pstmt.setString(3, tweet.getUser().getScreenName());
			pstmt.setString(4, tweet.getText());
			pstmt.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			
		} catch (Exception e) {
			// TODO: handle exception
			
		}

	}

}
