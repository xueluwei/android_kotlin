//package com.example.lib;
//
//import org.w3c.dom.Document;
//import org.w3c.dom.Element;
//import org.w3c.dom.Node;
//import org.w3c.dom.NodeList;
//
//import javax.xml.parsers.DocumentBuilder;
//import javax.xml.parsers.DocumentBuilderFactory;
//
//
///**
// *将XML转为stringname 用,号分隔  方便从服务器上进行多语言下载
// */
//public class XmlStringToService {
//    //将XML转为stringname 用,号分隔
//
//
//
//    public static void main(String args[]){
//        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
//        dbf.setIgnoringElementContentWhitespace(false);
//        String result = "";
//        try {
//            DocumentBuilder db = dbf.newDocumentBuilder();
//            Document xmldoc = db.parse("E:\\kotlin\\class\\lib\\src\\main\\java\\com\\example\\lib\\strings.xml");
//            NodeList nodeList  = xmldoc.getElementsByTagName("string");
//            System.out.println(nodeList.getLength());
//
//            for (int i = 0; i < nodeList.getLength(); i++) {
//                Node node = nodeList.item(i);
//                Element element = ((Element) node);
//
//                result+= element.getAttribute("name") + ",";
//            }
//
//
//        }catch (Exception e){
//            e.printStackTrace();
//        }
//
//        System.out.println(result);
//    }
//
//
//
//}
