package org.seckill.dao.cache;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.seckill.dao.SeckillDao;
import org.seckill.entity.Seckill;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:spring/spring-dao.xml"})
public class RedisDaoTest {

	@Autowired
	private RedisDao redisDao;
	@Autowired
	private SeckillDao seckillDao;
	
	private long id = 1000;
	@Test
	public void testGetSeckill() {
		Seckill seckill = redisDao.getSeckill(id);
        if(null == seckill){
            seckill = seckillDao.queryById(id);
            if(null != seckill){
                String result = redisDao.putSeckill(seckill);
                System.out.println(result);

                seckill = redisDao.getSeckill(id);
                System.out.println(seckill);
            }
        }
	}

	

}
