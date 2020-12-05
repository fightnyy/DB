package GoodHiking;
import java.sql.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Scanner;

public class dbProject {
	private static String wdivcon(String str) {
		String result = "";
		if (str.indexOf("시") >= 0) {
			result = str.substring(0, str.lastIndexOf("시") + 1);

		} else if (str.indexOf("군") >= 0) {
			result = str.substring(0, str.lastIndexOf("군") + 1);
		}


		return result;
	}

	private static String sdivcon(String str) {
		String[] strr = str.split("'");
		String result = "";
		for (int i = 0; i < strr.length; i++) {
			if (i != 0) {
				result += " " + strr[i];
			} else {
				result += strr[i];
			}
		}

		return result;
	}
	private static String sendivone(String str) {
		String[] strr = str.split("<br>");
		String result = "";
		for (int i = 0; i < strr.length; i++) {
			if (i != 0) {
				result += " " + strr[i];
			} else {
				result += strr[i];
			}
		}

		return result;
	}
	private static String sendivtwo(String str) {
		String[] strr = str.split("<p>");
		String result = "";
		for (int i = 0; i < strr.length; i++) {
			if (i != 0) {
				result += " " + strr[i];
			} else {
				result += strr[i];
			}
		}

		return result;
	}

	


	public static void main(String[] args) throws SQLException {

		/*
		각자 자기 환경에 맞게 세팅해주면 됨
		 */
		String url = "jdbc:postgresql://localhost/goodHiking";
		String userId = "fbduddn97";
		String password = "1357";

		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		Scanner scan = null;

		GetApiData.getApiData();
		//WeatherAPI.GetWeatherAPI();
		
		ArrayList<mountain> mt = GetApiData.mountain;
		ArrayList<topHundred>th=GetApiData.topHundred;
		ArrayList<mSurroundings> ms = GetApiData.mSurroundings;
		

		try {
			scan = new Scanner(System.in);
			//connection
			//System.out.println("Connection PostgreSQL database");
			conn = DriverManager.getConnection(url, userId, password);
			//System.out.println("Success Connection");
			stmt = conn.createStatement();

            /*
			stmt.executeUpdate("drop Table Mountain");
			stmt.executeUpdate("drop Table topHundred");
			stmt.executeUpdate("drop Table mSurroundings");
			*/
			
			

			/*
			//create table
			//System.out.println("Creating Mountain,topHundred,mSurroundings relations");

			stmt.executeUpdate("create table Mountain (mntnm varchar(20),mnheight int,areanm varchar(100))");
			stmt.executeUpdate("create table topHundred (mntnm varchar(20),aeatreason varchar(400))");
			stmt.executeUpdate("create table mSurroundings (mntnm varchar(20),tourisminf varchar(800),etccourse varchar(800))");


			//insert tuples
			for (int i = 0; i < 113; i++) {
				String name = mt.get(i).mntnm; //산 이름
				int height = Integer.parseInt(mt.get(i).mnheight);// 산 높이
				String areanm = wdivcon(mt.get(i).areanm);// 산 소재지
								
				String tname = mt.get(i).mntnm; //산 이름
				String reason = sdivcon(th.get(i).aeatreason);// 100대 명산 선정 이유
				
				String nname = ms.get(i).mntnm; //산 이름
				String tour = sdivcon(ms.get(i).tourisminf);//산 주변 관광지 정보
				String tcourse = sdivcon(ms.get(i).etccourse);// 산 주변 관광지 코스 정보

				//System.out.println(areanm);
				String mtInsert = "insert into Mountain values (" + "'" + name + "'" + "," + "'" + height + "'" + "," + "'" + areanm + "'"  + ")";
				String thInsert = "insert into topHundred values (" + "'" + tname + "'" + "," + "'" + reason + "'" + ")";
				String msInsert = "insert into mSurroundings values (" + "'" + nname + "'" + "," + "'" + tour + "'" + "," + "'" + tcourse + "'" + ")";

				stmt.executeUpdate(mtInsert);
				stmt.executeUpdate(thInsert);
				stmt.executeUpdate(msInsert);
			}
			
			*/
			
			
			
			//콘솔 			   
			System.out.println("안녕하세요.Good Hiking입니다.\n");
			while(true) {
				String info="";
				System.out.println("1.전국 8도 명산 Top 100보기   2.원하는 산정보 검색    3.난이도 추천 받기   4.종료");
				info=scan.next();
				//1.전국 8도 명산 Top 100보기 
				if (info.equals("1")){
					String query= "select mntnm,areanm from Mountain;";
					rs = stmt.executeQuery(query);
					while(rs.next()) {
						  String mntnm=rs.getString("mntnm");
						  String areanm=rs.getString("areanm");
						  
						  System.out.println("산이름:"+mntnm);
						  System.out.println("산위치:"+areanm+'\n');
						 }
					
				}
				//2.원하는 산정보 검색 
				else if(info.equals("2")){
					System.out.println("원하는 산을 입력해 주세요");
					String mountain=scan.next();
					String query1= "select Mountain.mntnm,mnheight,areanm,aeatreason from Mountain join topHundred on Mountain.mntnm = topHundred.mntnm where Mountain.mntnm ="
									+"'"+mountain+"'"+";";
					rs = stmt.executeQuery(query1);
					if(rs.isBeforeFirst()) {
						System.out.println("\n");
						while(rs.next()) {
							  String mntnm=rs.getString("mntnm");
							  String mnheight=rs.getString("mnheight");
							  String areanm=rs.getString("areanm");
							  String aeatreason=rs.getString("aeatreason");

							  
							  System.out.println("산이름:"+mntnm);
							  System.out.println("산높이:"+mnheight+'m');
							  System.out.println("산위치:"+areanm);
							  System.out.println("100대 명산 선정 이유:"+aeatreason); 
							  
							 }
						System.out.println("1.돌아가기   2.주변 관광지 정보 보기   3.종료");
						String a=scan.next();
						if(a.equals("2")) {
							System.out.println("\n");
							String query2= "select tourisminf,etccourse from Mountain join mSurroundings on Mountain.mntnm = mSurroundings.mntnm where Mountain.mntnm ="
											+"'"+mountain+"'"+";";
							rs = stmt.executeQuery(query2);
							while(rs.next()) {
								  String tourisminf=rs.getString("tourisminf");
								  String etccourse=rs.getString("etccourse");
								  tourisminf=sendivtwo(sendivone(tourisminf));
								  etccourse=sendivtwo(sendivone(etccourse));
								  System.out.println("주변 관광지 정보:"+tourisminf);
								  System.out.println("주변 관광지 코스 :"+etccourse);					  
								 }
							System.out.println("1.돌아가기  2.종료");
							String b=scan.next();
							System.out.println("\n");
							if(b.equals("2")) {
								break;
							}
							
						}									
						if(a.equals("3")) {
							break;
						}
					}
					else {
						System.out.println("일치하는 산이 없습니다.");
					}
										
				}
				//3.난이도 추천 받기
				else if(info.equals("3")){
					System.out.println("1.초급   2.중급   3.고급");
					String g=scan.next();
					if(g.equals("1")) {
						System.out.println("\n");
						String query1= "select * from Mountain where mnheight<=500";
						rs = stmt.executeQuery(query1);
						while(rs.next()) {
							  String mntnm=rs.getString("mntnm");
							  String mnheight=rs.getString("mnheight");
							  String areanm=rs.getString("areanm");
							  
							  System.out.println("산이름:"+mntnm);
							  System.out.println("산높이:"+mnheight+'m');
							  System.out.println("산위치:"+areanm+'\n');
							  
					    }
					}
					else if(g.equals("2")){
						System.out.println("\n");
						String query1= "select * from Mountain where mnheight>500 and mnheight<1000 ";
						rs = stmt.executeQuery(query1);
						while(rs.next()) {
							  String mntnm=rs.getString("mntnm");
							  String mnheight=rs.getString("mnheight");
							  String areanm=rs.getString("areanm");
							  
							  System.out.println("산이름:"+mntnm);
							  System.out.println("산높이:"+mnheight+'m');
							  System.out.println("산위치:"+areanm+'\n');
						
						}
					}
					else if(g.equals("3")) {
						System.out.println("\n");
						String query1= "select * from Mountain where  mnheight>1000 ";
						rs = stmt.executeQuery(query1);
						while(rs.next()) {
							  String mntnm=rs.getString("mntnm");
							  String mnheight=rs.getString("mnheight");
							  String areanm=rs.getString("areanm");
							  
							  System.out.println("산이름:"+mntnm);
							  System.out.println("산높이:"+mnheight+'m');
							  System.out.println("산위치:"+areanm+'\n');
						}
					}
					else{
						
					}
					
				}
				//4.종료
				else if(info.equals("4")) {
					break;
				}
				//기타 
				else {
					System.out.println("다시 입력해 주세요.");
				}
			}
				
			}catch(Exception e)
			{
				System.out.println(e.getMessage());
			}finally{
				try {
					if (stmt != null) {
						stmt.close();
					}
					if (conn != null) {
						conn.close();
					}

				} catch (Exception e) {
					System.out.println(e.getMessage());
				}
			}


		}
}








