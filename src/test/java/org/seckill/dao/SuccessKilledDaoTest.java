package org.seckill.dao;

import static org.junit.Assert.*;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.seckill.entity.SuccessKilled;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;


/**
 * ����spring�� junit���ϣ�junit������ʱ�򣬼���sprigIOC����
 * @author Administrator
 *
 */
//����Spring��Junit����,junit����ʱ����SpringIOC����
@RunWith(SpringJUnit4ClassRunner.class)
//����junit spring�����ļ�
@ContextConfiguration({"classpath:spring/spring-dao.xml"})
public class SuccessKilledDaoTest {
	
	
	@Resource
    private SuccessKilledDao successKilledDao; 
	
	@Test
	public void testInsertSuccessKilled() {
		long id =1002L;
		long phone = 18565815836L ;
		 int rows = successKilledDao.insertSuccessKilled(id,phone);
	        System.out.println("rows=" + rows);
	}

	@Test
	public void testQueryByIdWithSeckill() {
		 SuccessKilled successKilled = successKilledDao.queryByIdWithSeckill(1002L,18565815836L);
	        System.out.println(successKilled);
	        System.out.println(successKilled.getSeckill());
	}

}
