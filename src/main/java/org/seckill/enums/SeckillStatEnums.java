package org.seckill.enums;

/**
 * �����ֶ�
 * @author Administrator
 *
 */

public enum SeckillStatEnums {
	
	SUCCESS(1, "��ɱ�ɹ�"),
    END(0, "��ɱ����"),
    REPEAT_KILL(-1, "�ظ���ɱ"),
    INNER_ERROR(-2, "ϵͳ�쳣"),
    DATE_REWRITE(-3, "���ݴ۸�")
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
