package com.pg.web.rule;

import java.util.Objects;

public class RuleHandler {
    private int idx;

    protected RuleHandler next;

    private ResultInfo result;


    public  void setNext(RuleHandler next){
        this.next = next;
    }

    public  void printResult(){
        System.out.println(result);
        RuleHandler next = this.next;
        if(!Objects.isNull(next)){
            next.printResult();
        }
    }

    public  ResultInfo check(){
        result = ResultInfo.builder().responseBody("hello").build();
        ResultInfo check = this.check(result);
        return check;
    }

    public  ResultInfo check(ResultInfo preRuleResult){



        this.result= ResultInfo.builder().responseBody("hello").build();
        if(!Objects.isNull(next)){
            return next.check(this.result);
        }
        return result;
    }
}
