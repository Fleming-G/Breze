package com.breze.common.event;

import com.breze.entity.pojo.logdo.HandleLog;
import org.springframework.context.ApplicationEvent;

/**
 * @Author LUCIFER-LGX
 * @Date 2022/7/11 9:05
 * @Description 用于AOP记录日志的事件
 * @Copyright(c) 2022 , 青枫网络工作室
 */
public class LogEvent extends ApplicationEvent {

    private static final long serialVersionUID = 8096724581793846916L;

    public LogEvent(HandleLog source) {
        super(source);
    }
}
