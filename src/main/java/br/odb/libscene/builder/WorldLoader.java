package br.odb.libscene.builder;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import br.odb.libscene.World;
import br.odb.utils.Utils;

public class WorldLoader {
	
	static int END_OF_STREAM = -1;
	
	
	public static World build( InputStream is ) throws IOException {
		World world = new World();

		BufferedReader br = new BufferedReader(new InputStreamReader(is));
		String line;
		
		String[] tokens;
		
		while ((line = br.readLine()) != null) {
			tokens = Utils.tokenize( line, " " );
		}
		
		br.close();
		
		return world;
	}
}
