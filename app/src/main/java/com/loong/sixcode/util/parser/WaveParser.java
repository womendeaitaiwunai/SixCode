package com.loong.sixcode.util.parser;

import com.loong.sixcode.bean.AnimalBean;
import com.loong.sixcode.bean.WaveBean;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

/**
 * Created by lxl on 2017/7/12.
 */

public class WaveParser {
    public static List<WaveBean> parse(InputStream is) throws Exception {
    List<WaveBean> animals = new ArrayList<>();
    DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();  //取得DocumentBuilderFactory实例
    DocumentBuilder builder = factory.newDocumentBuilder(); //从factory获取DocumentBuilder实例
    Document doc = builder.parse(is);   //解析输入流 得到Document实例
    Element rootElement = doc.getDocumentElement();
    NodeList items = rootElement.getElementsByTagName("wave");
    for (int i = 0; i < items.getLength(); i++) {
        WaveBean animal = new WaveBean();
        Node item = items.item(i);
        NodeList properties = item.getChildNodes();
        for (int j = 0; j < properties.getLength(); j++) {
            Node property = properties.item(j);
            String nodeName = property.getNodeName();
            if (nodeName.equals("no")) {
                animal.setNo(Integer.parseInt(property.getFirstChild().getNodeValue()));
            } else if (nodeName.equals("name")) {
                animal.setName(property.getFirstChild().getNodeValue());
            } else if (nodeName.equals("code")) {
                animal.setCode(property.getFirstChild().getNodeValue());
            }
        }
        animals.add(animal);
    }
    return animals;
}
}
