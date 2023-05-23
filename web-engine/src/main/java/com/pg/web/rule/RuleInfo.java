package com.pg.web.rule;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;
import java.util.Map;

@Getter
@Setter
@ToString
public class RuleInfo {
    private Request request;
    private List<Payload> payloads;
    private List<CheckInfo> checks;
    private List<Response> response;



    @Getter
    @Setter
    @ToString
    public static class Request{
        private String url;
        public String method;
        public List<KeyAndValue> heads;
    }

    @Getter
    @Setter
    @ToString
    public static class Payload{
        private String type;
    }
    @Getter
    @Setter
    @ToString
    public static class CheckInfo{
        private String place;
        private String rel;
        private List<String> desired;
    }

    @Getter
    @Setter
    @ToString
    public static class Response{
        private String type; //status
    }
}
