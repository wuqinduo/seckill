package org.seckill.dto;

/**
 * ��װjson
 * @author Administrator
 *
 * @param <T>
 */

public class SeckillResult<T> {
	//��������Ƿ�ɹ�������ʾҵ������ɹ�
    private boolean success;

    private T data;

    private String error;

    public SeckillResult(T data) {
        this.data = data;
    }

    public SeckillResult(boolean success, String error) {
        this.success = success;
        this.error = error;
    }

    public SeckillResult(boolean success, T data) {
        this.success = success;
        this.data = data;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
	
}
