import java.sql.*;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Scanner;

public class dbProject {
	private static String wdivcon(String str) {	
		String result="";	
		if(str.indexOf("시")>=0){
			result=str.substring(0,str.lastIndexOf("시")+1);
			
		}
		
		else if(str.indexOf("군")>=0){
			result=str.substring(0,str.lastIndexOf("군")+1);
		}
						
		
		return result;
	}
	private static String sdivcon(String str) {
		String[] strr= str.split("'");
		String result="";
		for(int i=0; i<strr.length;i++) {
			if(i!=0){
				result+=" "+strr[i];
			}
			else {
				result+=strr[i];
			}
		}
		
		return result;
	}
		
		
	

	
	public static void main(String[] args) throws  SQLException{

		/*
		각자 자기 환경에 맞게 세팅해주면 됨
		 */
		String url = "jdbc:postgresql://localhost/goodHiking";
		String userId = "fbduddn97";
		String password= "1357";

		Connection conn = null;
		Statement stmt = null; 
		PreparedStatement pstm = null;
		ResultSet rs = null;
		Scanner scan = null;
		GetApiData.getApiData();
		ArrayList<mountain> mt=GetApiData.mountain;
		ArrayList<mSurroundings> ms=GetApiData.mSurroundings;
//		ArrayList<Weather> wt = WeatherAPI.mweather;
		
	  try {
		  scan = new Scanner(System.in);
		  
		  //connection 
		  System.out.println("Connection PostgreSQL database");
		  conn = DriverManager.getConnection(url, userId,password);
		  System.out.println("Success Connection");
		  stmt = conn.createStatement();
		  
		  
		  stmt.executeUpdate("drop Table Mountain");
		  stmt.executeUpdate("drop Table mSurroundings");
		  stmt.executeUpdate("drop Table mWeather");

			  
		 
		  //create table
		  System.out.println("Creating Mountain,mSurroundings,Weather relations");	
			  
		  stmt.executeUpdate("create table Mountain (mntnm varchar(20),mnheight varchar(20),areanm varchar(100),aeatreason varchar(400))");
		  stmt.executeUpdate("create table mSurroundings (mntnm varchar(20),tourisminf varchar(800),etccourse varchar(800))");
		  stmt.executeUpdate("create table Weather(mntnm varchar(20),wns varchar(10), tmp varchar(10), rainfall varchar(10))");


		  for(int i=0;i<113 ;i++) {
			  String name=mt.get(i).mntnm; //산 이름 
			  String height=mt.get(i).mnheight;// 산 높이 
			  String areanm=wdivcon(mt.get(i).areanm);// 산 소재지 
			  String reason=sdivcon(mt.get(i).aeatreason);// 100대 명산 선정 이유 
			  
			  String nname=ms.get(i).mntnm; //산 이름 
			  String tour=sdivcon(ms.get(i).tourisminf);//산 주변 관광지 정보 
			  String tcourse=sdivcon(ms.get(i).etccourse);// 산 주변 관광지 코스 정보 
			  
			  System.out.println(areanm);
			  String msInsert= "insert into mSurroundings values ("+"'"+nname+"'"+","+"'"+tour+"'"+ ","+"'"+tcourse+"'"+")";
			  String mtInsert= "insert into Mountain values ("+"'"+name+"'"+","+"'"+height+"'"+","+"'"+areanm+"'"+","+"'"+reason+"'"+")";
			  
			  
			  
			 stmt.executeUpdate(mtInsert); 
			 stmt.executeUpdate(msInsert); 

			  
		  }
			
	  }catch(Exception e) 
	  {
		  System.out.println(e.getMessage());
	  }finally {
		  try {
			  if (stmt != null){
				  stmt.close();
			  }
			  if(conn!=null) {
				  conn.close();
			  }
			  
		  }catch(Exception e) {
			  System.out.println(e.getMessage());
		  }
	  }		
		
		
	}

}



