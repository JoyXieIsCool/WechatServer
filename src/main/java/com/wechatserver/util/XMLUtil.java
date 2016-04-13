package com.wechatserver.util;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.ByteArrayInputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * Author: Joey
 * Date: 2016/4/13.
 * XML处理类，负责处理与微信服务器交互的XML片段，包括解析XML片段和根据 bean 生成 XML 片段
 */
public class XMLUtil {

    private static final Logger logger = LoggerFactory.getLogger(XMLUtil.class);

    /**
     * 解析 XML 片段，将解析结果存放到 HashMap 中
     * @param xmlString
     * @return
     * @throws Exception
     */
    public static Map<String, Object> getMapFromXML(String xmlString) throws Exception {
        Map<String, Object> resultMap = new HashMap<String, Object>();

        DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = builderFactory.newDocumentBuilder();
        Document document = null;
        if(StringUtils.isNotBlank(xmlString)) {
            ByteArrayInputStream inputStream = new ByteArrayInputStream(xmlString.getBytes("utf-8"));
            document = builder.parse(inputStream);
        }

        if (null != document) {
            // 获取根节点下面的所有节点，因为微信服务器发送过来的XML片段格式都是将数据包裹在<xml></xml>标签里面的
            NodeList allNodes = document.getFirstChild().getChildNodes();
            for(int i = 0; i < allNodes.getLength(); i++) {
                Node node = allNodes.item(i);
                if(node.getNodeType() == Node.ELEMENT_NODE) {
                    // 只有 ELEMENT 类型的node才添加，因为 allNodes 中包含有空白符号，它们也被算作node，我们只需要真正的标签元素的值
                    resultMap.put(node.getNodeName(), node.getTextContent());
                }
            }

        }

        // 返回解析结果，若XML片段为空则返回空的map，而不是null
        return resultMap;
    }
}
