package org.seckill.dto;

import org.seckill.entity.SuccessKilled;
import org.seckill.enums.SeckillStatEnums;

public class SeckillExecution {
	//商品id
	private long seckillId;
	//状态
	private int state;
	//状态信息
	private String stateInfo;
	//秒杀成功返回成功对象
	private SuccessKilled succedssKilled;

	//秒杀成功后返回的对象结构
	public SeckillExecution(long seckillId, SeckillStatEnums stateEnum,
			SuccessKilled succedssKilled) {
		super();
		this.seckillId = seckillId;
		this.state =stateEnum.getState();
		this.stateInfo = stateEnum.getStateInfo();
		this.succedssKilled = succedssKilled;
	}
	//秒杀失败后返回的对象结构
	public SeckillExecution(long seckillId, SeckillStatEnums stateEnum) {
		super();
		this.seckillId = seckillId;
		this.state =stateEnum.getState();
		this.stateInfo = stateEnum.getStateInfo();
	}

	public long getSeckillId() {
		return seckillId;
	}

	public void setSeckillId(long seckillId) {
		this.seckillId = seckillId;
	}

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

	public String getStateInfo() {
		return stateInfo;
	}

	public void setStateInfo(String stateInfo) {
		this.stateInfo = stateInfo;
	}

	public SuccessKilled getSuccedssKilled() {
		return succedssKilled;
	}

	public void setSuccedssKilled(SuccessKilled succedssKilled) {
		this.succedssKilled = succedssKilled;
	}
	@Override
	public String toString() {
		return "SeckillExecution [seckillId=" + seckillId + ", state=" + state
				+ ", stateInfo=" + stateInfo + ", succedssKilled="
				+ succedssKilled + "]";
	}
	
	
	
}
