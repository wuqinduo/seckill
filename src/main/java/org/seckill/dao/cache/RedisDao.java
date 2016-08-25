package org.seckill.dao.cache;


import org.seckill.entity.Seckill;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dyuproject.protostuff.LinkedBuffer;
import com.dyuproject.protostuff.ProtobufIOUtil;
import com.dyuproject.protostuff.runtime.RuntimeSchema;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

public class RedisDao {
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	private final JedisPool jedisPool ;
	//不用自己写schema
	private RuntimeSchema<Seckill> schema =RuntimeSchema.createFrom(Seckill.class);
	
	public RedisDao(String ip, int port){
		jedisPool = new JedisPool(ip, port);
	}
	
	//将 seckill实体从redis中取出来
	public Seckill getSeckill(long seckillId){
		try {
			//数据库连接池
			Jedis jedis = jedisPool.getResource();
			
			try{
				String key = "seckill:"+ seckillId;
				//是对象，就木有内部序列化
					//get-->byte[];不关心是一个java对象，还是PHP对象，图片，。。。
					//-->反序列化--Object(seckill)
				/**
				 * 高并发优化到极致：
				 * 序列化：
				 * 	jdk的虚拟化：
				 * 	自定义序列化：工具
				 * 		告诉他类结构，他内部有个Schema来描述这个对象。是个pojo（get/set）
				 */
				byte[] bytes = jedis.get(key.getBytes());
				if(bytes != null){
					Seckill seckill = schema.newMessage();
					ProtobufIOUtil.mergeFrom(bytes, seckill, schema);
					return seckill;
				}
			}finally{
				jedis.close();
			}
			
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
		}
		
		return null;
		
	}
	
	//将seckil 实体放到redis中去
	public String putSeckill(Seckill seckill){
		try{
			//object(seckill) -byte[]
			Jedis jedis = jedisPool.getResource();
			try{
				String key = "seckill:" +seckill.getSeckillId();
				byte[] bytes = ProtobufIOUtil.toByteArray(seckill, schema, 
						LinkedBuffer.allocate(LinkedBuffer.DEFAULT_BUFFER_SIZE)); 
				int timeout = 60*60;
				String result = jedis.setex(key.getBytes(), timeout, bytes);
			}finally{
				jedis.close();
			}
			
		}catch(Exception e){
			logger.error(e.getMessage(), e);
		}
		
		
		return null; 
	}
}
