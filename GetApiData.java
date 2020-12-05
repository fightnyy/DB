package GoodHiking;
import java.net.URL;


import java.net.URLEncoder;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class GetApiData {
	static ArrayList<mountain> mountain = new ArrayList<>();
	static ArrayList<topHundred> topHundred = new ArrayList<>();
	static ArrayList<mSurroundings> mSurroundings = new ArrayList<>();

	public static String getTagValue(String tag, Element eElement) {
	    NodeList nlList = eElement.getElementsByTagName(tag).item(0).getChildNodes();
	    Node nValue = (Node) nlList.item(0);
	    if(nValue == null) 
	        return null;
	    return nValue.getNodeValue();
	}

	
	public static void getApiData() {		
		int page=1;	// 페이지 초기값 
		String[]region= {"강원","경기","충청","전라","경상","제주"};
		int i=0;
		int c=0;
		
		try{
			while(true) {
				    String ppage=Integer.toString(page);
				    StringBuilder urlBuilder = new StringBuilder("http://openapi.forest.go.kr/openapi/service/cultureInfoService/gdTrailInfoOpenAPI"); /*URL*/
			        urlBuilder.append("?" + URLEncoder.encode("ServiceKey","UTF-8") + "=FcK49eKlzLeL4uF4CSUBzcTHOCyFvnQzIjizFD25GdO2BDYLoqqsqdklMN5Fjlj92YnUotm89yD3vo54z6%2BQBw%3D%3D"); /*Service Key*/
			        urlBuilder.append("&" + URLEncoder.encode("searchArNm","UTF-8") + "=" + URLEncoder.encode(region[i], "UTF-8"));
			        urlBuilder.append("&" + URLEncoder.encode("pageNo","UTF-8") + "=" + URLEncoder.encode(ppage, "UTF-8")); /*페이지 번호*/
			        String url = urlBuilder.toString();
			        
			        DocumentBuilderFactory dbFactoty = DocumentBuilderFactory.newInstance();
					DocumentBuilder dBuilder = dbFactoty.newDocumentBuilder();
					Document doc = dBuilder.parse(url);
					
					// root tag 
					doc.getDocumentElement().normalize();
					//System.out.println("Root element :" + doc.getDocumentElement().getNodeName());
					
					// 파싱할 tag
					NodeList nList = doc.getElementsByTagName("item");
					//System.out.println("파싱할 리스트 수 : "+ nList.getLength());
					
					for(int temp = 0; temp < nList.getLength(); temp++){
						Node nNode = nList.item(temp);
						mountain mt=null;
						topHundred th=null;
						mSurroundings ms=null;
						if(nNode.getNodeType() == Node.ELEMENT_NODE){
							Element eElement = (Element) nNode;
							mt=new mountain(getTagValue("mntnm",eElement),getTagValue("mntheight",eElement),getTagValue("areanm",eElement));
							th=new topHundred(getTagValue("mntnm",eElement),getTagValue("aeatreason",eElement));
							ms=new mSurroundings(getTagValue("mntnm",eElement),getTagValue("tourisminf",eElement), getTagValue("etccourse",eElement));
							/*
							System.out.println("산 이름 :"+ getTagValue("mntnm",eElement));
							System.out.println("산 높이 : "+ getTagValue("mntheight",eElement));
							System.out.println("산 정보 소재지 : "+ getTagValue("areanm",eElement));
							System.out.println("100대 명산 선정 이유 : "+ getTagValue("aeatreason",eElement));
							
							System.out.println("산 이름 : "+ getTagValue("mntnm",eElement));
							System.out.println("주변 관광 정보 : "+ getTagValue("tourisminf",eElement));
							System.out.println("산 정보 주면 관광 정보 기타 코스 설명 : "+ getTagValue("etccourse",eElement));
							*/

							mountain.add(mt);
							topHundred.add(th);
							mSurroundings.add(ms);
							
							
						}
						
			}
					page+=1;
					if(page>=5){
						i+=1;
						page=1;
						
					}
					if(i>=region.length) {
						break;
					}
									
			
		}
			//System.out.println(c);
	 }catch(Exception e){
		 e.printStackTrace();
	 }
  }
}

