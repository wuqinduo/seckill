package org.seckill.dao;

import org.apache.ibatis.annotations.Param;
import org.seckill.entity.SuccessKilled;

public interface SuccessKilledDao {
	
	
	int insertSuccessKilled(@Param("seckillId") long seckillId ,@Param("userPhone")long userPhone);
	
	/**
	 *  ����ID��ѯ successKilled ����ʵ�������
	 * @param seckillId
	 * @return
	 */
	/*��������*/
	SuccessKilled queryByIdWithSeckill(@Param("seckillId") long seckillId ,@Param("userPhone")long userPhone);
}
