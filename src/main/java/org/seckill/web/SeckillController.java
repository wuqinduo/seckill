package org.seckill.web;

import java.util.Date;
import java.util.List;

import org.seckill.dto.Exposer;
import org.seckill.dto.SeckillExecution;
import org.seckill.dto.SeckillResult;
import org.seckill.entity.Seckill;
import org.seckill.enums.SeckillStatEnums;
import org.seckill.exception.RepeatKillException;
import org.seckill.exception.SeckillCloseException;
import org.seckill.service.SeckillService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/seckill")//url:/模块/资源/{id}/细分 /seckill/list
public class SeckillController {
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	@Autowired
	private SeckillService seckillService ;
	

    /**
     * @param
     * @return
     * @Desc 秒杀列表页接口
     * @Author feizi
     * @Date 2016/6/20 17:20
     */
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public String list(Model model) {
        //获取列表页
        List<Seckill> list = seckillService.getSeckillList();
        model.addAttribute("list", list);

        //list.jsp + model = ModelAndView
        return "list";// /WEB-INF/jsp/list.jsp
    }
	
	@RequestMapping(value = "/{seckillId}/detail", method = RequestMethod.GET)
	public String detail(@PathVariable("seckillId") Long seckillId,Model model){
		if (null == seckillId) {
            return "redirect:/seckill/list";
        }

        Seckill seckill = seckillService.getById(seckillId);
        if (null == seckill) {
            return "forward:/seckill/list";
        }

        model.addAttribute("seckill", seckill);
        return "detail";
	}
	
	/**
     * @param
     * @return
     * @Desc AJAX调用，返回json格式数据,秒杀结果
     * @Author feizi
     * @Date 2016/6/20 17:21
     */
    @RequestMapping(value = "/{seckillId}/exposer",
            method = RequestMethod.POST,
            produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public SeckillResult<Exposer> exposer(@PathVariable("seckillId") Long seckillId) {
        SeckillResult<Exposer> result;
        try {
            Exposer exposer = seckillService.exprotSeckillUrl(seckillId);
            result = new SeckillResult<Exposer>(true, exposer);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            result = new SeckillResult<Exposer>(false, e.getMessage());
        }
        return result;
    }
    
    /**
     * @Desc 执行秒杀操作
     * @Author feizi
     * @Date 2016/6/20 19:24
     * @param
     * @return
     */
    @RequestMapping(value = "/{seckillId}/{md5}/execution",
            method = RequestMethod.POST,
            produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public SeckillResult<SeckillExecution> execute(@PathVariable("seckillId") Long seckillId,
                                                   @PathVariable("md5") String md5,
                                                   @CookieValue(value = "killPhone", required = false) Long userPhone) {
        //springMVC valid
        if(null == userPhone){//cookie里面没有，说明没有登陆
            return new SeckillResult<SeckillExecution>(false, "未注册");
        }

        SeckillResult<SeckillExecution> result;
        try {
           SeckillExecution execution = seckillService.executeSeckill(seckillId, userPhone, md5);
            //通过调用存储过程执行秒杀操作
           /* SeckillExecution execution = seckillService.executeSeckillByProcedure(seckillId, userPhone, md5);*/
            return  new SeckillResult<SeckillExecution>(true, execution);
        }catch (RepeatKillException e){
            SeckillExecution seckillExecution = new SeckillExecution(seckillId, SeckillStatEnums.REPEAT_KILL);
            return   new SeckillResult<SeckillExecution>(true, seckillExecution);
        }catch (SeckillCloseException e){
            SeckillExecution seckillExecution = new SeckillExecution(seckillId, SeckillStatEnums.END);
            return  new SeckillResult<SeckillExecution>(true, seckillExecution);
        }catch (Exception e) {
            logger.error(e.getMessage(), e);
            SeckillExecution seckillExecution = new SeckillExecution(seckillId, SeckillStatEnums.INNER_ERROR);
            return  new SeckillResult<SeckillExecution>(true, seckillExecution);
        }
        
    }
    
    
    /**
     * @Desc 获取当前系统时间
     * @Author feizi
     * @Date 2016/6/20 19:27
     * @param
     * @return
     */
    @RequestMapping(value = "/time/now",
            method = RequestMethod.GET)
    @ResponseBody
    public SeckillResult<Long> time(){
        Date now = new Date();
        return new SeckillResult(true, now.getTime());
    }
}
