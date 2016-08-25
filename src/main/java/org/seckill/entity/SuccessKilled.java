package org.seckill.entity;

import java.util.Date;

public class SuccessKilled {
	//秒杀商品id
    private long seckillId;

    //用户手机号
    private long user_phone;

    //状态标识（-1:无效，0:成功,1:已付款）
    private short state;

    //创建时间
    private Date createTime;
    
    //变通 ： 多对一 
    private Seckill seckill; //成功秒杀的产品，一种产品对应多个秒杀记录
    
	public long getSeckillId() {
		return seckillId;
	}

	public void setSeckillId(long seckillId) {
		this.seckillId = seckillId;
	}

	public long getUser_phone() {
		return user_phone;
	}

	public void setUser_phone(long user_phone) {
		this.user_phone = user_phone;
	}

	public short getState() {
		return state;
	}

	public void setState(short state) {
		this.state = state;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
	public Seckill getSeckill() {
		return seckill;
	}

	public void setSeckill(Seckill seckill) {
		this.seckill = seckill;
	}

	@Override
	public String toString() {
		return "SuccessKilled [seckillId=" + seckillId + ", user_phone="
				+ user_phone + ", state=" + state + ", createTime="
				+ createTime + "]";
	}
    
    
}
