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
	//�����Լ�дschema
	private RuntimeSchema<Seckill> schema =RuntimeSchema.createFrom(Seckill.class);
	
	public RedisDao(String ip, int port){
		jedisPool = new JedisPool(ip, port);
	}
	
	//�� seckillʵ���redis��ȡ����
	public Seckill getSeckill(long seckillId){
		try {
			//���ݿ����ӳ�
			Jedis jedis = jedisPool.getResource();
			
			try{
				String key = "seckill:"+ seckillId;
				//�Ƕ��󣬾�ľ���ڲ����л�
					//get-->byte[];��������һ��java���󣬻���PHP����ͼƬ��������
					//-->�����л�--Object(seckill)
				/**
				 * �߲����Ż������£�
				 * ���л���
				 * 	jdk�����⻯��
				 * 	�Զ������л�������
				 * 		��������ṹ�����ڲ��и�Schema��������������Ǹ�pojo��get/set��
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
	
	//��seckil ʵ��ŵ�redis��ȥ
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
