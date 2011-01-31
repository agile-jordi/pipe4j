package pipe4j.pipe.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Collection;

import junit.framework.TestCase;

import org.hsqldb.jdbcDriver;

import pipe4j.core.Pipeline;
import pipe4j.pipe.adaptor.CollectionInAdaptor;

public class JdbcTest extends TestCase {
	private Connection conn;

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		Class.forName(jdbcDriver.class.getName());
		conn = DriverManager.getConnection("jdbc:hsqldb:mem:mydb", "SA", "");
		conn.setAutoCommit(false);
		conn.prepareCall("create table x (y integer)")
				.execute();
		PreparedStatement prepareStatement = conn
				.prepareStatement("insert into x values(?)");
		for (int i = 1; i <= 10; i++) {
			prepareStatement.setInt(1, i);
			prepareStatement.addBatch();
		}
		prepareStatement.executeBatch();
		conn.commit();
	}

	@Override
	protected void tearDown() throws Exception {
		super.tearDown();
		conn.prepareCall("drop table x").execute();
		conn.close();
	}

	public void testSingleObject() throws Exception {
		PreparedStatement ps = conn.prepareStatement("insert into x values(?)");
		Collection<Integer> coll = new ArrayList<Integer>();
		for (int i = 1; i <= 10; i++) {
			coll.add(i);
		}
		Pipeline.run(new CollectionInAdaptor(coll), new PreparedStatementOut(ps));
		ps.close();
		
		ResultSet rs = conn.prepareStatement("select count(*), sum(y) from x").executeQuery();
		rs.next();
		assertEquals(20, rs.getInt(1));
		assertEquals(110, rs.getInt(2));
		rs.close();
	}
	
	public void testCollection() throws Exception {
		PreparedStatement ps = conn.prepareStatement("insert into x values(?)");
		Collection<Collection<Integer>> coll = new ArrayList<Collection<Integer>>();
		for (int i = 1; i <= 10; i++) {
			Collection<Integer> innerColl = new ArrayList<Integer>();
			innerColl.add(i);
			coll.add(innerColl);
		}
		
		Pipeline.run(new CollectionInAdaptor(coll), new PreparedStatementOut(ps));
		ps.close();
		
		ResultSet rs = conn.prepareStatement("select count(*), sum(y) from x").executeQuery();
		rs.next();
		assertEquals(20, rs.getInt(1));
		assertEquals(110, rs.getInt(2));
		rs.close();
	}
	
	public void testAutoCommit() throws Exception {
		ResultSet rs = conn.prepareStatement("select y from x").executeQuery();
		PreparedStatement ps = conn.prepareStatement("insert into x values(?)");
		Pipeline.run(new ResultSetIn(rs), new PreparedStatementOut(ps, 3, true));
		rs.close();
		ps.close();
		conn.rollback(); // all data should have been committed by
							// PreparedStatementOut
		
		rs = conn.prepareStatement("select count(*), sum(y) from x").executeQuery();
		rs.next();
		assertEquals(20, rs.getInt(1));
		assertEquals(110, rs.getInt(2));
		rs.close();
	}
	
	public void testInvalidCommitInterval() throws Exception {
		try {
			new PreparedStatementOut(null, 0, true);
			fail("Should throw IllegalArgumentException!");
		} catch (IllegalArgumentException expected) {
		}
		
		try {
			new PreparedStatementOut(null, -1, true);
			fail("Should throw IllegalArgumentException!");
		} catch (IllegalArgumentException expected) {
		}
		
		new PreparedStatementOut(null, 1, true);
	}
	
	public void testNoAutoCommit() throws Exception {
		ResultSet rs = conn.prepareStatement("select count(*), sum(y) from x").executeQuery();
		rs.next();
		assertEquals(10, rs.getInt(1));
		assertEquals(55, rs.getInt(2));
		rs.close();
		
		rs = conn.prepareStatement("select y from x").executeQuery();
		PreparedStatement ps = conn.prepareStatement("insert into x values(?)");
		Pipeline.run(new ResultSetIn(rs), new PreparedStatementOut(ps));
		rs.close();
		ps.close();
		conn.rollback(); // all data should be lost
		
		rs = conn.prepareStatement("select count(*), sum(y) from x").executeQuery();
		rs.next();
		assertEquals(10, rs.getInt(1));
		assertEquals(55, rs.getInt(2));
		rs.close();
	}
}
