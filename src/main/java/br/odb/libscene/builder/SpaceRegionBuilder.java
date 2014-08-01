package br.odb.libscene.builder;

import java.io.IOException;
import java.util.HashMap;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

import br.odb.libscene.SpaceRegion;

public class SpaceRegionBuilder implements SpatialDivisionBuilder {
	

	public SpaceRegion build( XMLStreamReader streamReader ) throws NumberFormatException, IOException, XMLStreamException {
		
		
		SpaceRegion region = new SpaceRegion();
		
		HashMap< String, SpatialDivisionBuilder > builders = new HashMap< String, SpatialDivisionBuilder >();
		builders.put( "size", new Vec3Builder() );
		builders.put( "position", new Vec3Builder() );		
		SpatialDivisionBuilder builder;
		
		while (streamReader.hasNext()) {
            if (streamReader.isStartElement()) {
            	
            	builder = builders.get(  streamReader.getLocalName() );
            	

            }
            streamReader.next();
        }		
		
		return region;
	}
}
