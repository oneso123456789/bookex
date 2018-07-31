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
// junit 버전이 두개인가 pom.xml에서확인하길 바람 두개면 실행 x	
	@Inject
	private DataSource ds;
	
	@Test
	public void testConection()throws Exception{
		try(Connection con = ds.getConnection()){
			System.out.println(con);
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
}
