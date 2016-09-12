package org.seckill.service.iml;

import java.util.Date;
import java.util.List;

import org.seckill.dao.SeckillDao;
import org.seckill.dao.SuccessKilledDao;
import org.seckill.dao.cache.RedisDao;
import org.seckill.dto.Exposer;
import org.seckill.dto.SeckillExecution;
import org.seckill.entity.Seckill;
import org.seckill.entity.SuccessKilled;
import org.seckill.enums.SeckillStatEnums;
import org.seckill.exception.RepeatKillException;
import org.seckill.exception.SeckillCloseException;
import org.seckill.exception.SeckillException;
import org.seckill.service.SeckillService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;


@Service
public class SeckillServiceIml  implements SeckillService{
	
	//日志对象
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    //mapper初始化注册到容器中
    /*使用spring自带的注入方式，自由注入，会去容器中查找secKillDao的实例，有就注入*/
    @Autowired
	private SeckillDao seckillDao;
	
    @Autowired
	private SuccessKilledDao successKilledDao;
    @Autowired
    private RedisDao redisDao;
	
	//加入盐值,md5盐值字符串，用于混淆MD5
    private final String salt = "1234567890)(*&^%$#@!";
	
	public List<Seckill> getSeckillList() {
		
		return seckillDao.queryAll(0, 5);
	}

	public Seckill getById(long seckillId) {
		
		return seckillDao.queryById(seckillId);
	}

	public Exposer exprotSeckillUrl(long seckillId) {
		
		//缓存优化;
		/*Seckill seckill =redisDao.getSeckill(seckillId);
		if(seckill == null){*/
		Seckill 	seckill = seckillDao.queryById(seckillId);
			if(seckill == null){
				return new Exposer(false, seckillId);
			}else{
				redisDao.putSeckill(seckill);
			}
			
		/*}
		 */
		
		/*if(seckill == null ){
			return new Exposer(false, seckillId);
		}*/
		
		    Date startTime = seckill.getStartTime();
	        Date endTime = seckill.getEndTime();
	        //系统当前时间
	        Date nowTime = new Date();

	        if(nowTime.getTime() < startTime.getTime()
	                || nowTime.getTime() > endTime.getTime()){
	            //当前时间不在秒杀时间范围内
	            return new Exposer(false, seckillId, nowTime.getTime(), startTime.getTime(), endTime.getTime());
	        }

	        //TODO 转化特定字符串的过程,不可逆
	        String md5 = getMD5(seckillId);
	        return new Exposer(true, md5, seckillId);
		
	}
 	
	@Transactional
	//标注其为事务方法，告诉程序，这里是一个事务方法
	/**
     * @param seckillId
     * @param userPhone
     * @param md5
     * @Desc 执行秒杀操作
     * @Author feizi
     * @Date 2016/6/8 16:12
     * 使用注解控制事务方法的优点：
     * 1：开发团队达成一致约定，明确标注事务方法的编程风格
     * 2: 保证事务方法的执行时间尽可能短,不要穿插其他网络操作 RPC/HTTP请求或者剥离到事务方法外部
     * 3：不是所有的方法操作都需要事务控制,如只有一条修改操作，只读操作不需要事务控制
     */
	public SeckillExecution executeSeckill(long seckillId, long userPhone,
			String md5) throws SeckillException, RepeatKillException,
			SeckillCloseException {
		if(null == md5 || !md5.equals(getMD5(seckillId))){
            throw new SeckillException("seckill data rewrite.");
          
        }
		
		//执行秒杀操作：减库存，记录秒杀操作
        Date killTime = new Date();
        try {
            //记录秒杀行为
            int insertCount = successKilledDao.insertSuccessKilled(seckillId, userPhone);
            //唯一验证:seckillId + userPhone
            if(insertCount <= 0){
                //重复秒杀
                throw new RepeatKillException("repeat kill........" );
//                return new SeckillExecution(seckillId,SeckillStateConstantEnum.REPEAT_KILL);
            }else{
                //减库存，热点商品竞争
                int updateCount = seckillDao.reduceNumber(seckillId, killTime);
                if(updateCount <= 0){
                    //没有更新到记录，秒杀结束
                    throw new SeckillCloseException("seckill has ended........" );
//                    return new SeckillExecution(seckillId, SeckillStateConstantEnum.END);
                }else{
                    //秒杀成功
                    SuccessKilled successKilled = successKilledDao.queryByIdWithSeckill(seckillId ,userPhone);
                    return new SeckillExecution(seckillId, SeckillStatEnums.SUCCESS, successKilled);
                }
            }
        }catch (SeckillCloseException e1){
            throw e1;
           
        }catch (RepeatKillException e2){
            throw e2;
        }catch (Exception e) {
        	logger.error(e.getMessage(), e);
            //所有编译期异常转化为运行期异常
            throw new SeckillException("seckill inner error: " + e.getMessage());
//            return new SeckillExecution(seckillId,SeckillStateConstantEnum.INNER_ERROR);
        }
	}
	
	/**
     * @Desc 获取MD5加密字符串
     * @Author feizi
     * @Date 2016/6/8 17:42
     * @param seckillId
     * @return
     */
    private String getMD5(long seckillId){
        String baseStr = seckillId + "/" + salt;
        String md5 = DigestUtils.md5DigestAsHex(baseStr.getBytes());
        return md5;
    }

}
