package org.seckill.service;

import java.util.List;

import org.seckill.dto.Exposer;
import org.seckill.dto.SeckillExecution;
import org.seckill.entity.Seckill;
import org.seckill.exception.RepeatKillException;
import org.seckill.exception.SeckillCloseException;
import org.seckill.exception.SeckillException;


/**
 * վ�ڡ�ʹ���ߡ��ĽǶ�ȥ��ƽӿ�
 * �������棺
 * 		������������
 * 		������
 * 		�������ͣ�return /����/�쳣/
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
