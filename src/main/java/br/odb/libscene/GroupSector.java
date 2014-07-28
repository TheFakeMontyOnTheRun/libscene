package br.odb.libscene;

import java.util.ArrayList;
import java.util.HashMap;

import br.odb.utils.Direction;

public class GroupSector extends SpaceRegion {
	public final HashMap< Direction, String > materials = new HashMap< Direction, String >();
	public final ArrayList< Sector > sons = new ArrayList< Sector >();
	String id;
}
