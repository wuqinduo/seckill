package org.seckill.dao;

import org.apache.ibatis.annotations.Param;
import org.seckill.entity.SuccessKilled;

public interface SuccessKilledDao {
	
	
	int insertSuccessKilled(@Param("seckillId") long seckillId ,@Param("userPhone")long userPhone);
	
	/**
	 *  根据ID查询 successKilled 并把实体带回来
	 * @param seckillId
	 * @return
	 */
	/*联合主键*/
	SuccessKilled queryByIdWithSeckill(@Param("seckillId") long seckillId ,@Param("userPhone")long userPhone);
}
