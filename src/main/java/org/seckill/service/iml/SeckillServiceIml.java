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
	
	//��־����
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    //mapper��ʼ��ע�ᵽ������
    /*ʹ��spring�Դ���ע�뷽ʽ������ע�룬��ȥ�����в���secKillDao��ʵ�����о�ע��*/
    @Autowired
	private SeckillDao seckillDao;
	
    @Autowired
	private SuccessKilledDao successKilledDao;
    @Autowired
    private RedisDao redisDao;
	
	//������ֵ,md5��ֵ�ַ��������ڻ���MD5
    private final String salt = "1234567890)(*&^%$#@!";
	
	public List<Seckill> getSeckillList() {
		
		return seckillDao.queryAll(0, 5);
	}

	public Seckill getById(long seckillId) {
		
		return seckillDao.queryById(seckillId);
	}

	public Exposer exprotSeckillUrl(long seckillId) {
		
		//�����Ż�;
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
	        //ϵͳ��ǰʱ��
	        Date nowTime = new Date();

	        if(nowTime.getTime() < startTime.getTime()
	                || nowTime.getTime() > endTime.getTime()){
	            //��ǰʱ�䲻����ɱʱ�䷶Χ��
	            return new Exposer(false, seckillId, nowTime.getTime(), startTime.getTime(), endTime.getTime());
	        }

	        //TODO ת���ض��ַ����Ĺ���,������
	        String md5 = getMD5(seckillId);
	        return new Exposer(true, md5, seckillId);
		
	}
 	
	@Transactional
	//��ע��Ϊ���񷽷������߳���������һ�����񷽷�
	/**
     * @param seckillId
     * @param userPhone
     * @param md5
     * @Desc ִ����ɱ����
     * @Author feizi
     * @Date 2016/6/8 16:12
     * ʹ��ע��������񷽷����ŵ㣺
     * 1�������ŶӴ��һ��Լ������ȷ��ע���񷽷��ı�̷��
     * 2: ��֤���񷽷���ִ��ʱ�価���ܶ�,��Ҫ��������������� RPC/HTTP������߰��뵽���񷽷��ⲿ
     * 3���������еķ�����������Ҫ�������,��ֻ��һ���޸Ĳ�����ֻ����������Ҫ�������
     */
	public SeckillExecution executeSeckill(long seckillId, long userPhone,
			String md5) throws SeckillException, RepeatKillException,
			SeckillCloseException {
		if(null == md5 || !md5.equals(getMD5(seckillId))){
            throw new SeckillException("seckill data rewrite.");
          
        }
		
		//ִ����ɱ����������棬��¼��ɱ����
        Date killTime = new Date();
        try {
            //��¼��ɱ��Ϊ
            int insertCount = successKilledDao.insertSuccessKilled(seckillId, userPhone);
            //Ψһ��֤:seckillId + userPhone
            if(insertCount <= 0){
                //�ظ���ɱ
                throw new RepeatKillException("repeat kill........" );
//                return new SeckillExecution(seckillId,SeckillStateConstantEnum.REPEAT_KILL);
            }else{
                //����棬�ȵ���Ʒ����
                int updateCount = seckillDao.reduceNumber(seckillId, killTime);
                if(updateCount <= 0){
                    //û�и��µ���¼����ɱ����
                    throw new SeckillCloseException("seckill has ended........" );
//                    return new SeckillExecution(seckillId, SeckillStateConstantEnum.END);
                }else{
                    //��ɱ�ɹ�
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
            //���б������쳣ת��Ϊ�������쳣
            throw new SeckillException("seckill inner error: " + e.getMessage());
//            return new SeckillExecution(seckillId,SeckillStateConstantEnum.INNER_ERROR);
        }
	}
	
	/**
     * @Desc ��ȡMD5�����ַ���
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
