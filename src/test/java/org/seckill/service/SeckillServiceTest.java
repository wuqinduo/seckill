package org.seckill.service;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.seckill.dto.Exposer;
import org.seckill.dto.SeckillExecution;
import org.seckill.entity.Seckill;
import org.seckill.exception.RepeatKillException;
import org.seckill.exception.SeckillCloseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

//启动的时候加载这些东西，否则不会被调用
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({
	"classpath:spring/spring-dao.xml",
	
	"classpath:spring/spring-service.xml"
	})
public class SeckillServiceTest {
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private SeckillService seckillService;
	
	@Test
	public void testGetSeckillList() {
		List<Seckill> seckillList = seckillService.getSeckillList();
        logger.info("seckillList={}", seckillList);
	}

	@Test
	public void testGetById() {
		 long id = 1000;
	        Seckill seckill = seckillService.getById(id);
	        logger.info("seckill={}", seckill);
	}

	@Test
	public void testExprotSeckillUrl() {
		/*long id = 1004;
        Exposer exposer = seckillService.exprotSeckillUrl(id);

        //[exposed=true, md5=f20b7ab79736dd720264ad0c7bbffc39, seckillId=1004, now=0, start=0, end=0]
        logger.info("exposer={}",exposer);*/
		 long id = 1004;
	        Exposer exposer = this.seckillService.exprotSeckillUrl(id);
	        if(exposer.isExposed()){
	            logger.info("exposer={}",exposer);

	            long userPhone = 18798789897L;
	            String md5 = exposer.getMd5();
	            try {
	                SeckillExecution seckillExecution = this.seckillService.executeSeckill(id, userPhone, md5);
	                logger.info("seckillExecution={}",seckillExecution);
	            } catch (RepeatKillException e) {
	                logger.error(e.getMessage());
	            }catch (SeckillCloseException e){
	                logger.error(e.getMessage());
	            }
	        }else{
	            //秒杀未开启
	            logger.warn("exposer={}",exposer);
	        }
	}

	@Test
	public void testExecuteSeckill() {
		/**
Releasing transactional SqlSession [org.apache.ibatis.session.defaults.DefaultSqlSession@2ee9c4]
Transaction synchronization committing SqlSession [org.apache.ibatis.session.defaults.DefaultSqlSession@2ee9c4]
Transaction synchronization deregistering SqlSession [org.apache.ibatis.session.defaults.DefaultSqlSession@2ee9c4]
Transaction synchronization closing SqlSession [org.apache.ibatis.session.defaults.DefaultSqlSession@2ee9c4]
INFO  o.seckill.service.SeckillServiceTest - seckillExecution=org.seckill.dto.SeckillExecution@90ab74
		 */
		   long id = 1004;
	        long userPhone = 18798789896L;
	        String md5 = "f20b7ab79736dd720264ad0c7bbffc39";

	        try {
	            SeckillExecution seckillExecution = this.seckillService.executeSeckill(id, userPhone, md5);
	            //seckillExecution=SeckillExecution{seckillId=1001, state=1, stateInfo='秒杀成功', successKilled=SuccessKilled{seckillId=1001, user_phone=0, state=0, createTime=Wed Jun 15 17:49:26 CST 2016}}
	            logger.info("seckillExecution={}",seckillExecution);
	        } catch (RepeatKillException e) {
	            logger.error(e.getMessage());
	        }catch (SeckillCloseException e){
	            logger.error(e.getMessage());
	        }
	        
	}

}
