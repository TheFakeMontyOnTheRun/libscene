package br.odb.libscene.builder;

import java.io.IOException;
import java.util.HashMap;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

import br.odb.libscene.GroupSector;
import br.odb.libscene.SpaceRegion;

public class GroupSectorBuilder extends SpaceRegionBuilder {
	
	public SpaceRegion build( XMLStreamReader streamReader ) throws NumberFormatException, IOException, XMLStreamException {
		
		
		SpaceRegion region = super.build( streamReader );
		GroupSector masterSector = new GroupSector( region );

		
		HashMap< String, SpatialDivisionBuilder > builders = new HashMap< String, SpatialDivisionBuilder >();
		builders.put( "place", new GroupSectorBuilder() );
		builders.put( "sector", new SectorBuilder() );		
		SpatialDivisionBuilder builder;
		
		while (streamReader.hasNext()) {
            if (streamReader.isStartElement()) {
            	
            	builder = builders.get(  streamReader.getLocalName() );
            	
            	if ( builder != null ) {
            		masterSector.sons.add( builder.build( streamReader ) );
            	}
            }
            streamReader.next();
        }
		
		
		return masterSector;
	}
}
