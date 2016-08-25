package org.seckill.dao;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.seckill.entity.Seckill;



public interface SeckillDao {
	
	/**
	 * 减库存
	 * @param seckillId
	 * @param killTime
	 * @return =1 表示减少的库存
	 */
	int reduceNumber(@Param("seckillId")long seckillId,@Param("killTime")Date killTime);
	
	/**
	 * 根据ID查询seckill
	 * @param seckillId
	 * @return
	 */
	Seckill queryById(long seckillId);
	
	
	/**
	 *  根据偏移量 查询秒杀产品
	 * @param offset
	 * @param limit
	 * @return
	 */
	List<Seckill> queryAll(@Param("offset") int offset , @Param("limit")int limit);//告诉哪个位置的参数叫什么
}
