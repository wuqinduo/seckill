package org.seckill.dao;

import static org.junit.Assert.*;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.seckill.entity.Seckill;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.sun.org.apache.bcel.internal.util.ClassPath;


/**
 * 配置spring与 junit整合，junit启动的时候，加载sprigIOC容器
 * @author Administrator
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:spring/spring-dao.xml"})
public class SeckillDaoTest {

	@Resource
	private SeckillDao seckillDao;
	
	@Test
	public void testReduceNumber() {
		Date killTime = new Date();
        int updateCount = seckillDao.reduceNumber(1000L, killTime);
        System.out.println("updateCount=" + updateCount);
	}

	@Test
	public void testQueryById() {

		long id = 1000;
		Seckill seckill = seckillDao.queryById(id);
		System.out.println(seckill.getName());
		System.out.println(seckill);
	}

	@Test
	public void testQueryAll() {
		/***
         * Parameter 'seckillId' not found. Available parameters are [1, 0, param1, param2]
         * List<Seckill> queryAll(int offset, int limit);
         * java没有保存形参的记录：在运行期间的时候 queryAll(int offset, int limit) -> queryAll(arg0, arg1) 
         * 
         * 传递一个参数没有问题，传递多个参数就不可以了
         */
		List<Seckill> seckills = seckillDao.queryAll(0, 100);
		
		for (Seckill seckill : seckills) {
            System.out.println(seckill);
        }
		
	}

}
