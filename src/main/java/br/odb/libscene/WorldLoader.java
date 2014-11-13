package br.odb.libscene;

import java.io.IOException;
import java.io.InputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class WorldLoader {
	
	public static String toXML( World world ) {
		
		String toReturn = "";
		
		toReturn += "<world>\n";
		
		toReturn += GroupSectorBuilder.toXML( world.masterSector );

		toReturn += "\n</world>";
		
		return toReturn;
	}

	public static World build(InputStream is) throws IOException,
			SAXException, ParserConfigurationException {

		World world;
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder db = dbf.newDocumentBuilder();
		Document doc = db.parse(is);
		doc.getDocumentElement().normalize();

		NodeList nodeLst;
		nodeLst = doc.getElementsByTagName("world");

		world = new World(
				(GroupSector) new GroupSectorBuilder().build(nodeLst.item( 0 ) ) );

		return world;
	}
}
