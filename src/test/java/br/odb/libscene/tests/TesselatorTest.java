//package br.odb.libscene.tests;
//
//import org.junit.Assert;
//import org.junit.Test;
//
//import br.odb.libscene.GroupSector;
//import br.odb.libscene.Sector;
//import br.odb.libscene.util.SceneTesselator;
//import br.odb.libstrip.builders.GeneralTriangleFactory;
//import br.odb.utils.Direction;
//
//public class TesselatorTest {
//
//	public static final int TRIANGLES_PER_WALL = 2;
//	public static final SceneTesselator tesselator = new SceneTesselator( new GeneralTriangleFactory() );
//	
//	
//	@Test
//	public final void testExtractPolysOnlyFromParentInHierarchy() {
//		GroupSector sr1 = new GroupSector("test1");
//		Sector s1 = new Sector(sr1);
//		sr1.addChild(s1);
//
//		for (Direction d : Direction.values()) {
//			Assert.assertEquals(0,
//					tesselator.foreignLinksInDirection(d, sr1));
//		}
//
//		tesselator.generateSubSectorMeshForSector(sr1);
//		Assert.assertEquals(6 * TRIANGLES_PER_WALL, sr1.mesh.faces.size());
//	}
//
//	@Test
//	public final void testExtractPolysParentAndSubSectorsInHierarchy() {
//		GroupSector sr1 = new GroupSector("test1");
//		sr1.size.set(3.0f, 3.0f, 3.0f);
//		Sector s1 = new Sector(sr1);
//		Sector s2 = new Sector(sr1);
//		sr1.addChild(s1);
//		sr1.addChild(s2);
//
//		s2.localPosition.z = sr1.size.z / 2.0f;
//		s2.size.z /= 2.0f;
//		s1.size.z /= 2.0f;
//
//		s1.links[ Direction.S.ordinal() ] = s2.id;
//		s2.links[ Direction.N.ordinal() ] = s1.id;
//		
//		for (Direction d : Direction.values()) {
//			Assert.assertEquals(0,
//					tesselator.foreignLinksInDirection(d, sr1));
//		}
//
//		tesselator.generateSubSectorMeshForSector(sr1);
//		Assert.assertEquals( 6 * TRIANGLES_PER_WALL, sr1.mesh.faces.size());
//	}
//
//	@Test
//	public final void testExtractPolysParentAndSubSectorsWithForeignLinksInHierarchy() {
//		GroupSector gs1 = new GroupSector("test1");
//		GroupSector gs2 = new GroupSector("test2");
//		gs1.size.set(3.0f, 3.0f, 3.0f);
//		gs2.size.set(3.0f, 3.0f, 3.0f);
//		
//		gs2.localPosition.y = 3;
//
//		Sector s1 = new Sector( "s1" );
//		Sector s2 = new Sector( "s2");
//		Sector s3 = new Sector( "s3" );
//		Sector s4 = new Sector( "s4" );
//		
//		s1.localPosition.set( gs1.localPosition );
//		s2.localPosition.set( gs1.localPosition );
//		s1.size.set( gs1.size );
//		s2.size.set( gs1.size );
//		
//		s3.localPosition.set( gs2.localPosition );
//		s4.localPosition.set( gs2.localPosition );
//		s3.size.set( gs2.size );
//		s4.size.set( gs2.size );
//
//		gs1.addChild(s1);
//		gs1.addChild(s2);
//
//		gs2.addChild(s3);
//		gs2.addChild(s4);		
//
//		s2.localPosition.z = gs1.size.z / 2.0f;
//		s2.size.z /= 2.0f;
//		s1.size.z /= 2.0f;
//		
//		s3.localPosition.z = gs2.size.z / 2.0f;
//		s3.size.z /= 2.0f;
//		s4.size.z /= 2.0f;
//
//		s1.links[ Direction.S.ordinal() ] = s2.id;
//		s2.links[ Direction.N.ordinal() ] = s1.id;
//		
//		s3.links[ Direction.S.ordinal() ] = s4.id;
//		s4.links[ Direction.N.ordinal() ] = s3.id;
//		
//		s1.links[ Direction.CEILING.ordinal() ] = s3.id;
//		s2.links[ Direction.CEILING.ordinal() ] = s4.id;
//		
//		s3.links[ Direction.FLOOR.ordinal() ] = s1.id;
//		s4.links[ Direction.FLOOR.ordinal() ] = s2.id;
//
//		for (Direction d : Direction.values()) {
//
//			if (d == Direction.CEILING) {
//				Assert.assertEquals(2,
//						tesselator.foreignLinksInDirection(d, gs1));
//			} else {
//				Assert.assertEquals(0,
//						tesselator.foreignLinksInDirection(d, gs1));
//			}
//		}
//
//		tesselator.generateSubSectorMeshForSector(gs1);
//		Assert.assertEquals(5 * TRIANGLES_PER_WALL, gs1.mesh.faces.size());
//	}
//
//	@Test
//	public final void testExtractPolysParentAndSubSectorsWithSmallForeignLinksInHierarchy() {
//		GroupSector sr1 = new GroupSector("test1");
//		sr1.size.set(3.0f, 3.0f, 3.0f);
//		Sector s1 = new Sector(sr1);
//		Sector s2 = new Sector(sr1);
//		Sector s3 = new Sector(sr1);
//		Sector s4 = new Sector(sr1);
//		sr1.addChild(s1);
//		sr1.addChild(s2);
//		sr1.addChild(s3);
//		sr1.addChild(s4);
//
//		s2.localPosition.z = sr1.size.z / 2.0f;
//		s4.localPosition.z = sr1.size.z / 2.0f;
//		s2.size.z /= 2.0f;
//		s1.size.z /= 2.0f;
//		s3.size.z /= 2.0f;
//		s4.size.z /= 2.0f;
//
//		s3.localPosition.x = sr1.size.x / 2.0f;
//		s4.localPosition.x = sr1.size.x / 2.0f;
//
//		s2.links[ Direction.FLOOR.ordinal() ] = s2.id;
//		
//
//		for (Direction d : Direction.values()) {
//
//			if (d == Direction.FLOOR) {
////				Assert.assertEquals(1,
////						tesselator.foreignLinksInDirection(d, sr1));
//			} else {
//				Assert.assertEquals(0,
//						tesselator.foreignLinksInDirection(d, sr1));
//			}
//		}
//
//		tesselator.generateSubSectorMeshForSector(sr1);
////		Assert.assertEquals(8 * TRIANGLES_PER_WALL, sr1.mesh.faces.size());
//	}
//
//}
