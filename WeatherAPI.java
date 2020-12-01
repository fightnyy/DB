


import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;

public class WeatherAPI {

    static int i = 1890;

    private static String getTagValue(String tag, Element eElement) {
        System.out.println("여기냐" + eElement.getElementsByTagName(tag).item(0).toString());
        NodeList nlList = eElement.getElementsByTagName(tag).item(0).getChildNodes();
        System.out.println("여기냐1");
        Node nValue = (Node) nlList.item(0);
        System.out.println("여기냐2");
        System.out.println("nValue" + nValue);
        System.out.println("nValue.getnode" + nValue.getNodeValue());
        if (nValue == null) {
            System.out.println("들어왔다");
            return null;
        }

        return nValue.getNodeValue();
    }


    public static void main(String[] args) {
        try {

            while (true) {

                Date nowDate = new Date();
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddHHmm");
                String time = simpleDateFormat.format(nowDate);

                String num = Integer.toString(i);
                StringBuilder urlBuilder = new StringBuilder("http://know.nifos.go.kr/openapi/mtweather/mountListSearch.do"); /*URL*/
                urlBuilder.append("?" + URLEncoder.encode("keyValue","UTF-8") + "=GTV1ptFOh2KvJM4BZTc0ErxkmooB2x0qUj1TPfXFsfg%3D"); /*API key*/
                urlBuilder.append("&" + URLEncoder.encode("version","UTF-8") + "=" + URLEncoder.encode("1", "UTF-8")); /*버전*/
                urlBuilder.append("&" + URLEncoder.encode("obsid","UTF-8") + "=" + URLEncoder.encode(num, "UTF-8"));
                urlBuilder.append("&" + URLEncoder.encode("tm","UTF-8") + "=" + URLEncoder.encode("201508280900", "UTF-8")); /*시간*/
                String url = urlBuilder.toString();
                System.out.println(url);
                i++;
                DocumentBuilderFactory dbFactoty = DocumentBuilderFactory.newInstance();
                DocumentBuilder dBuilder = dbFactoty.newDocumentBuilder();
                Document doc = dBuilder.parse(url);

                // root tag
                doc.getDocumentElement().normalize();
                System.out.println("Root element :" + doc.getDocumentElement().getNodeName());

                // 파싱할 tag
                NodeList nList = doc.getElementsByTagName("outputData");
                System.out.println("여기였냐!");
                for (int temp = 0; temp < nList.getLength(); temp++) {
                    Node nNode = nList.item(temp);
                    if (nNode.getNodeType() == Node.ELEMENT_NODE) {

                        Element eElement = (Element) nNode;

                        System.out.println("eElement : " + eElement.toString());

                        System.out.println("######################");
                        //System.out.println(eElement.getTextContent());
                        System.out.println("산이름  : " + getTagValue("obsname", eElement));
                        System.out.println("산날씨  : " + getTagValue("tm10m", eElement));
                        System.out.println("풍속 : " + getTagValue("ws10m", eElement));
                        System.out.println("강수량  : " + getTagValue("rn", eElement));
                    }    // for end
                }

                if (i>9914)
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
