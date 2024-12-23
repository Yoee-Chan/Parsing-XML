package com.pxml.main;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.File;
import java.io.FileInputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class XmlUtils {
    public static Map<String, Map<String, Object>> getObjectFromXML(String path) {
        File file = new File(path);
        FileInputStream fileInputStream = null;
        Map<String, Map<String, Object>> map = new HashMap<>();
        try {
            fileInputStream = new FileInputStream(file);
            SAXReader saxReader = new SAXReader();
            Document read = saxReader.read(fileInputStream);
            Element rootElement = read.getRootElement();
            getElement(rootElement, map);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;
    }

    private static void getElement(Element element, Map<String, Map<String, Object>> map) {
        List<Element> elements = element.elements();
        if (elements.size() == 0) {
            return;
        }
        Map<String, Object> subMap = new HashMap<>();
        String name = element.getName();
        for (Element el : elements) {
            if (!el.getData().toString().trim().equals("")) {
                subMap.put(el.getName(), el.getData().toString().trim().replace("\n", ""));
                map.put(name, subMap);
            }
            getElement(el, map);
        }
    }
}

