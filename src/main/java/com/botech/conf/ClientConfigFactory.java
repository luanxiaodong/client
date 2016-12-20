package com.botech.conf;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathFactory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Node;


public class ClientConfigFactory {
	private static final Logger log = LoggerFactory.getLogger(ClientConfigFactory.class);
	private ClientConfigFactory(){
		try {
			config=new ClientConfig();
			
			DocumentBuilderFactory domFactory = DocumentBuilderFactory.newInstance();
		    domFactory.setNamespaceAware(true); // never forget this!
		    DocumentBuilder builder = domFactory.newDocumentBuilder();
		    Document doc = builder.parse(ClientConfigFactory.class.getResourceAsStream("/client-config.xml"));

		    XPathFactory factory = XPathFactory.newInstance();
		    XPath xpath = factory.newXPath();
		    
		    XPathExpression ipExpr = xpath.compile("/config/ip");
		    Node ip = (Node) ipExpr.evaluate(doc, XPathConstants.NODE);
		    //System.out.println(ip.getTextContent());
		    config.setIp(ip.getTextContent());
		    
		    XPathExpression portExpr = xpath.compile("/config/port");
		    Node port = (Node) portExpr.evaluate(doc, XPathConstants.NODE);
		    //System.out.println(port.getTextContent());
		    try {
		    	config.setPort(Integer.valueOf(port.getTextContent()));
			} catch (Exception e) {config.setPort(7397);}
		    
		} catch (Exception e) {
			e.printStackTrace();
			log.error("config error",e);
		}
	}
	private static ClientConfigFactory instance=new ClientConfigFactory();
	public static ClientConfigFactory getInstance(){
		return instance;
	}
	
	private ClientConfig config;//配置
	
	public ClientConfig getConfig() {//配置
		return config;
	}

}
