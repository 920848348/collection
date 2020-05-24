/**
 * @ClassName BaseController
 * @Description TODO
 * @Author Sonrisa
 * @Date 2020/5/16 14:41
 */
package cn.sonrisa.controller;

import cn.sonrisa.result.ExceptionMsg;
import cn.sonrisa.result.Response;

public class BaseController {

    public Response result(){
        return new Response();
    }

    public Response result(ExceptionMsg exceptionMsg){
        return new Response(exceptionMsg);
    }
}
