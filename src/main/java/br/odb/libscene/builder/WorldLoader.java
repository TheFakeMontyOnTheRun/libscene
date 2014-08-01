package br.odb.libscene.builder;

import java.io.IOException;
import java.io.InputStream;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

import br.odb.libscene.GroupSector;
import br.odb.libscene.World;

public class WorldLoader {
	
	public static World build( InputStream is ) throws IOException, XMLStreamException {
		
		World world;
		XMLInputFactory inputFactory = XMLInputFactory.newInstance();
		XMLStreamReader streamReader = inputFactory.createXMLStreamReader(is);
		
		world = new World( (GroupSector) new GroupSectorBuilder().build( streamReader ) );
		
		return world;
	}
}
