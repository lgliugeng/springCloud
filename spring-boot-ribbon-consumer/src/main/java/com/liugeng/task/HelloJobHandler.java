package com.liugeng.task;

import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.IJobHandler;
import com.xxl.job.core.handler.annotation.JobHandler;
import com.xxl.job.core.log.XxlJobLogger;
import org.springframework.stereotype.Component;

@Component
@JobHandler("helloTask")
public class HelloJobHandler extends IJobHandler {

    @Override
    public ReturnT<String> execute(String s) {
        XxlJobLogger.log("hello Task");
        System.out.println("xxljob>>>>hello Task");
        return SUCCESS;
    }
}
