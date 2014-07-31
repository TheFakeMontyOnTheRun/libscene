package br.odb.libscene.builder;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.xml.crypto.Data;

import br.odb.libscene.SpaceRegion;
import br.odb.libscene.World;

public class WorldLoader {
	
	static int END_OF_STREAM = -1;
	
	public static List<SpaceRegion> desserialize( InputStream is ) throws IOException {
		
		ArrayList< SpaceRegion > toReturn = new ArrayList< SpaceRegion >();
		HashMap< String, SpatialDivisionBuilder > builders = new HashMap< String, SpatialDivisionBuilder >(); 

		builders.put( "m", new GroupSectorBuilder() );
		builders.put( "s", new SectorBuilder() );
		
		char c = 0;
		SpatialDivisionBuilder builder;
		BufferedReader br = new BufferedReader( new InputStreamReader( is ) );

		DataInputStream dis = new DataInputStream( is );
		
		while ( c != END_OF_STREAM ) {
			
			br.mark(255);
			c = dis.readChar();
			br.reset();
			System.out.println( "got: " + c );
			builder = builders.get( "" + c );
			toReturn.add( builder.build( is ) );
		}
		
		return toReturn;
	}
	
	public static World build( InputStream is ) throws IOException {
		World world = new World();
		ArrayList< SpaceRegion > sectors = new ArrayList< SpaceRegion >();
		
		sectors.addAll( desserialize( is ) );
		buildHierarchy( sectors, world );
		
		return world;
	}

	private static void buildHierarchy(ArrayList<SpaceRegion> sectors, World world) {
		// TODO Auto-generated method stub
		
	}
}
