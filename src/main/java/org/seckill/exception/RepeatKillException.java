package org.seckill.exception;


/**
 *  重复秒杀的异常(运行期异常)事务就是接受的运行期异常
 * @author Administrator
 *
 */
public class RepeatKillException extends RuntimeException {

	public RepeatKillException(String message){
		super(message);
	}
	
	public RepeatKillException(String message ,Throwable cause){
		super(message,cause);
	}
		
}
