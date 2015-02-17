//package br.odb.libscene.tests;
//
//import org.junit.Assert;
//import org.junit.Test;
//
//import br.odb.libscene.GroupSector;
//import br.odb.libscene.Sector;
//import br.odb.libscene.util.SceneTesselator;
//import br.odb.libstrip.GeneralTriangleFactory;
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
//		Assert.assertEquals(6 * TRIANGLES_PER_WALL, sr1.mesh.faces.size());
//	}
//
//	@Test
//	public final void testExtractPolysParentAndSubSectorsWithForeignLinksInHierarchy() {
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
//		s2.links[ Direction.FLOOR.ordinal() ] = s2.id;
//
//		for (Direction d : Direction.values()) {
//
//			if (d == Direction.FLOOR) {
//				Assert.assertEquals(1,
//						tesselator.foreignLinksInDirection(d, sr1));
//			} else {
//				Assert.assertEquals(0,
//						tesselator.foreignLinksInDirection(d, sr1));
//			}
//		}
//
//		tesselator.generateSubSectorMeshForSector(sr1);
//		Assert.assertEquals(6 * TRIANGLES_PER_WALL, sr1.mesh.faces.size());
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
//				Assert.assertEquals(1,
//						tesselator.foreignLinksInDirection(d, sr1));
//			} else {
//				Assert.assertEquals(0,
//						tesselator.foreignLinksInDirection(d, sr1));
//			}
//		}
//
//		tesselator.generateSubSectorMeshForSector(sr1);
//		Assert.assertEquals(8 * TRIANGLES_PER_WALL, sr1.mesh.faces.size());
//	}
//
//}
