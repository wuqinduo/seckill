package org.seckill.entity;

import java.util.Date;

/**
 * 
 * @author Administrator
 *
 */
public class Seckill {
	//��ɱ��Ʒid
    private long seckillId;

    //��Ʒ����
    private String name;

    //�������
    private int number;

    //��ɱ��ʼʱ��
    private Date startTime;

    //��ɱ����ʱ��
    private Date endTime;

    //����ʱ��
    private Date createTime;

	public long getSeckillId() {
		return seckillId;
	}

	public void setSeckillId(long seckillId) {
		this.seckillId = seckillId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	
	
	//��Ԫ������
	@Override
	public String toString() {
		return "Seckill [seckillId=" + seckillId + ", name=" + name
				+ ", number=" + number + ", startTime=" + startTime
				+ ", endTime=" + endTime + ", createTime=" + createTime + "]";
	}
}
