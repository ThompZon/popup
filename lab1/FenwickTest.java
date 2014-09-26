package popup.lab1;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class FenwickTest {
	Fenwick f;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		System.out.println("================ NEW CASE ================");
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testUpdate() {
		f = new Fenwick(9);
		f.update(3, 3);
		System.out.println(f);
		assertEquals(3, f.getVal(3));
	}
	
	/*
10 4
+ 7 23
? 8
+ 3 17
? 8
	 */
	@Test
	public void testSample1(){
		f = new Fenwick(10);
		f.update(7, 23);
		assertEquals(23, f.query(8));
		f.update(3, 17);
		assertEquals(40, f.query(8));
	}
	
	/*
5 4
+ 0 -43
+ 4 1
? 0
? 5
	 */
	@Test
	public void testSample2(){
		f = new Fenwick(5);
		f.update(0, -43);
		f.update(4, 1);
		assertEquals(0, f.query(0));
		assertEquals(-42, f.query(5));
	}

	@Test
	public void testQuery() {
		f = new Fenwick(9);
		f.update(3, 3);
		f.update(2, 2);
		f.update(1, 1);
		System.out.println(f);
		assertEquals(6, f.query(4));
	}

}
