package popup.problemsession1;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class BeatTheSpreadTest {
	BeatTheSpread bts;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		bts = new BeatTheSpread();
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void test00() {
		int sum = 0;
		int diff = 0;
		assertArrayEquals(new int[]{0,0},  bts.getScores(sum, diff));
	}

}
