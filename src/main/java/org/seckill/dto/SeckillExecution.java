package org.seckill.dto;

import org.seckill.entity.SuccessKilled;
import org.seckill.enums.SeckillStatEnums;

public class SeckillExecution {
	//��Ʒid
	private long seckillId;
	//״̬
	private int state;
	//״̬��Ϣ
	private String stateInfo;
	//��ɱ�ɹ����سɹ�����
	private SuccessKilled succedssKilled;

	//��ɱ�ɹ��󷵻صĶ���ṹ
	public SeckillExecution(long seckillId, SeckillStatEnums stateEnum,
			SuccessKilled succedssKilled) {
		super();
		this.seckillId = seckillId;
		this.state =stateEnum.getState();
		this.stateInfo = stateEnum.getStateInfo();
		this.succedssKilled = succedssKilled;
	}
	//��ɱʧ�ܺ󷵻صĶ���ṹ
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
