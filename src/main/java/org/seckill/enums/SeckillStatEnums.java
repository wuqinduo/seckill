package org.seckill.enums;

/**
 * 常量字段
 * @author Administrator
 *
 */

public enum SeckillStatEnums {
	
	SUCCESS(1, "秒杀成功"),
    END(0, "秒杀结束"),
    REPEAT_KILL(-1, "重复秒杀"),
    INNER_ERROR(-2, "系统异常"),
    DATE_REWRITE(-3, "数据篡改")
    ;
	
	private  String  stateInfo;
	private int state;
	
	
	SeckillStatEnums(int state, String stateInfo) {
		
		this.state = state;
		this.stateInfo = stateInfo;
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

	public static SeckillStatEnums stateOf(int index){
        for (SeckillStatEnums seckillStateConstant : values()){
            if(seckillStateConstant.getState() == index){
                return seckillStateConstant;
            }
        }
        return null;
    }
	
	
}
