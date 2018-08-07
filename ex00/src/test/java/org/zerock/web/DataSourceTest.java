package org.zerock.web;

import java.sql.Connection;

import javax.inject.Inject;
import javax.sql.DataSource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(
		locations= {"file:src/main/webapp/WEB-INF/spring/**/*.xml"})
public class DataSourceTest {
// junit 踰꾩쟾�씠 �몢媛쒖씤媛� pom.xml�뿉�꽌�솗�씤�븯湲� 諛붾엺 �몢媛쒕㈃ �떎�뻾 x	
	@Inject
	private DataSource ds;
	
	@Test
	public void testConection()throws Exception{
		try(Connection con = ds.getConnection()){
			System.out.println(con);
			con.close();
		}catch (Exception e) {
			e.printStackTrace();
		}
		
	}
}
