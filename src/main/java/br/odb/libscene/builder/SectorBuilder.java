package br.odb.libscene.builder;

import java.io.IOException;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import br.odb.libscene.Sector;
import br.odb.libscene.SpaceRegion;


public class SectorBuilder extends SpaceRegionBuilder {

	@Override
	public SpaceRegion build(Node node) {
		
//		NodeList nodeLst;
//		nodeLst = node.getChildNodes();

//		for (int s = 0; s < nodeLst.getLength(); s++) {
//
//			Node fstNode = nodeLst.item(s);
//			
//			if (fstNode != null) {
//
//				if (fstNode.getNodeType() == Node.ELEMENT_NODE ) {
//
////					builder = builders.get(fstNode.getNodeName());
////
////					if (builder != null) {
////						masterSector.sons.add(builder.build(node));
////					}
//					
//				}
//			}
//		}

		
		SpaceRegion region = super.build(node);
		
		Sector sector = new Sector( region );
		
		return sector;
	}

	public static String toXML( Sector s) {
		String toReturn = "";
		
		toReturn += SpaceRegionBuilder.toXML( ( SpaceRegion )s );
		
		return toReturn;
	}
}
