import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class MinesweeperTest {
	
	MineMap m;
	
	Ranking myRanking;
	
	@Before
	public void setUp() {
		myRanking = new Ranking();
		m = new MineMap(9, 9, 10, myRanking);
		myRanking.addMap(m);
		myRanking.inSerialize();
	}
	
	@Test
	public void testMapX() {
		int mapx = m.getMapX();
		Assert.assertEquals(9, mapx);
	}
	
	@Test
	public void testMapBombs() {
		int bombs = m.getBombs();
		Assert.assertEquals(10, bombs);
	}
	
	@Test
	public void testSkintype() {
		m.setSkintype("win98");
		String type = m.getSkintype();
		Assert.assertEquals("win98", type);
	}
	
	@Test
	public void testGametype() {
		m.setGametype(3);
		int type = m.getGametype();
		Assert.assertNotEquals(2, type);
	}
	
	@Test
	public void testNewGame() {
		m.newGame(20, 10, 20);
		int bombs = m.getBombs();
		Assert.assertEquals(20, bombs);
	}
	
	@Test
	public void testGameEnded() {
		boolean end = m.getGameended();
		Assert.assertFalse(end);
	}
	
	@Test
	public void testTimes() {
		int[] testarray = {999, 999, 999};
		int[] getarray = {myRanking.getTime(0), myRanking.getTime(1), myRanking.getTime(2)};
		Assert.assertArrayEquals(testarray, getarray);
	}
	
	@Test
	public void testNames() {
		String[] testarray = {"Anonymous", "Anonymous", "Anonymous"};
		String[] getarray = {myRanking.getName(0), myRanking.getName(1), myRanking.getName(2)};
		Assert.assertArrayEquals(testarray, getarray);
	}
	
	@Test
	public void testFieldIsBomb() {
		Field f = m.getField(3, 3);
		boolean isBomb = f.getBomb();
		Assert.assertFalse(isBomb);
	}
	
	@Test
	public void testFieldGetY() {
		Field f = m.getField(1, 5);
		int fieldY = f.getY();
		Assert.assertEquals(5, fieldY);
	}
	
	@Test
	public void testSmileyHolddown() {
		boolean b = MySmileyMouseListener.getHolddown();
		Assert.assertFalse(b);
	}
	
	@Test
	public void testMouseFirstclick() {
		boolean fc = MyMouseListener.firstclick;
		Assert.assertTrue(fc);
	}
	
	@Test
	public void testNewGameBomb() {
		m.newGame(5, 5, 30);
		int bombs = m.getBombs();
		Assert.assertNotEquals(30, bombs);
	}
}
