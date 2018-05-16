package com.greyu.ysj.model;

import org.omg.PortableInterceptor.INACTIVE;

/**
 * @Description:
 * @Author: gre_yu@163.com
 * @Date: Created in 13:16 2018/5/14.
 */
public class StatisticsOrder {
    private Integer success;

    private Integer successToday;

    private Integer wait;

    private Integer waitToday;

    private Integer dispatching;

    private Integer refunding;

    public Integer getSuccessToday() {
        return successToday;
    }

    public void setSuccessToday(Integer successToday) {
        this.successToday = successToday;
    }

    public Integer getWaitToday() {
        return waitToday;
    }

    public void setWaitToday(Integer waitToday) {
        this.waitToday = waitToday;
    }

    public Integer getDispatching() {
        return dispatching;
    }

    public void setDispatching(Integer dispatching) {
        this.dispatching = dispatching;
    }

    public Integer getSuccess() {
        return success;
    }

    public void setSuccess(Integer success) {
        this.success = success;
    }

    public Integer getWait() {
        return wait;
    }

    public void setWait(Integer wait) {
        this.wait = wait;
    }

    public Integer getRefunding() {
        return refunding;
    }

    public void setRefunding(Integer refunding) {
        this.refunding = refunding;
    }
}
