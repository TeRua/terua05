package InsightEYE.TwitterAnalyzer;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Scanner;

import InsightEYE.DB.DBManager;
import twitter4j.Query.ResultType;
/*
 * InsightEYE.IssueKeywordAnalyzer
 * 작성일자 : 2016 년 9월 15일
 * 작성자 : 곽민석
 * 기타 내역 : mariaDB SELECT, INSERT 사용
 */
public class main {
	public static void main(String[] args) {
		String startDate = ""; // 시작일
		String endDate = ""; // 종료일
		int quantity = 0;
		ResultSet rs = null;
		Hashtable<String, Integer> ht = null;
		if (args.length == 3) {// CUI 환경에서 실행 시 실행 인자로 시작일 종료일 포함
			startDate = args[0];
			endDate = args[1];
			quantity = Integer.parseInt(args[2]);
		} else {// 실행 인자에 날짜 없을 경우
			Scanner input = new Scanner(System.in);
			while (startDate.isEmpty() | endDate.isEmpty()) {
				System.out.print("시작일 입력(yyyy-mm-dd) \n> ");
				startDate = input.nextLine();
				System.out.print("종료일 입력(yyyy-mm-dd) \n> ");
				endDate = input.nextLine();
				System.out.print("최대 키워드 갯수 입력 (int) \n> ");
				quantity = input.nextInt();
			}
			input.close();
		}
		DBManager dbm = new DBManager();
		TwitterAnalyzer ta = new TwitterAnalyzer(dbm);
		ArrayList<String> issuedKwd = new ArrayList<>();
		issuedKwd = dbm.getIssuedKeyword(startDate, endDate, quantity);
		for (String string : issuedKwd) {
			System.out.println("이슈 키워드 : " + string);
		}
		for (String kwd : issuedKwd) {
			try{
//				ta.insertDB(ta.makeQuery(kwd, ResultType.popular));
				ta.insertDB(ta.makeQuery(kwd, ResultType.mixed));
			}catch(Exception e){
				
			}
			
			
		}
	}
}
