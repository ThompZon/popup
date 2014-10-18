package popup.problemsession1;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class LutnaTest {
	Lutna l;
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		l = new Lutna();
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testGetAnger() {
		//					1,1,1,2,2 = 4+4+3 = 11
		//int[] a = new int[]{5,5,5,5,5};
		int sum = 25;
		int c = 18;
		
		//assertEquals(11, l.getAnger(a, sum, a.length, c));
	}

}
