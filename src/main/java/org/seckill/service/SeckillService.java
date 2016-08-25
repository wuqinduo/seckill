package org.seckill.service;

import java.util.List;

import org.seckill.dto.Exposer;
import org.seckill.dto.SeckillExecution;
import org.seckill.entity.Seckill;
import org.seckill.exception.RepeatKillException;
import org.seckill.exception.SeckillCloseException;
import org.seckill.exception.SeckillException;


/**
 * 站在“使用者”的角度去设计接口
 * 三个方面：
 * 		方法定义粒度
 * 		参数简单
 * 		返回类型：return /类型/异常/
 * 		
 * 		
 * @author Administrator
 *
 */
public interface SeckillService {
		
		List<Seckill> getSeckillList();
		
		Seckill getById(long seckillId);
		
		Exposer exprotSeckillUrl(long seckillId);
		
		SeckillExecution executeSeckill(long seckillId, long userPhone, String md5)
		throws SeckillException,RepeatKillException,SeckillCloseException;
		
		
		
		
}
