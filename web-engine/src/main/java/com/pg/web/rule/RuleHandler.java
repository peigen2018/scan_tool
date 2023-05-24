package com.pg.web.rule;

import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.pg.web.util.CompareUtils;
import com.pg.web.util.HttpUtils;
import org.apache.http.StatusLine;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.*;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;

import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class RuleHandler {
    private int idx;

    protected RuleHandler next;

    private RuleInfo ruleInfo;

    private RuleResult result;

    public RuleHandler(RuleInfo ruleInfo) {
        this.ruleInfo = ruleInfo;
    }

    public void setNext(RuleHandler next) {
        this.next = next;
    }

    public void printResult() {
        System.out.println(result);
        RuleHandler next = this.next;
        if (!Objects.isNull(next)) {
            next.printResult();
        }
    }

    public void check() throws UnsupportedEncodingException, URISyntaxException {
        this.check(null);
    }

    public void check(RuleResult preRuleResult) throws URISyntaxException, UnsupportedEncodingException {

        RuleInfo.Request request = ruleInfo.getRequest();

        MethodEnum method = MethodEnum.from(request.getMethod());

        HttpUriRequest httpRequest;
        switch (method) {
            case GET:
                httpRequest = buildGetMethod(request);
                break;

            case POST:
                httpRequest = buildPostMethod(request);
                break;
            case PUT:
                httpRequest = buildPutMethod(request);
                break;
            case DELETE:
                httpRequest = buildDeleteMethod(request);
                break;
            default:
                throw new UnsupportedOperationException();
        }


        try (CloseableHttpClient closeableHttpClient = HttpUtils.wrapClient(true)) {


            List<RuleInfo.CheckInfo> checks = ruleInfo.getChecks();
            StatusLine statusLine = closeableHttpClient.execute(httpRequest).getStatusLine();


            RuleResult.RuleResultBuilder<?, ?> pass = RuleResult.builder().pass(true);
            checks.stream().forEach(check -> {


                switch (check.getPlace().toLowerCase()) {
                    case "status":
                        statusCheck(statusLine, check);
                        break;
                }
            });


            this.result = pass.build();


        } catch (Exception e) {
            e.printStackTrace();
        }


        if (!Objects.isNull(next)) {
            next.check(this.result);
        }
    }


    private RuleResult.ResultInfo statusCheck(StatusLine statusLine, RuleInfo.CheckInfo check) {
        RuleResult.ResultInfo.ResultInfoBuilder<?, ?> builder = RuleResult.ResultInfo.builder();

        builder.pass(true);

        boolean pass = CompareUtils.compareNumber(check.getRel(), check.getDesired(), String.valueOf(statusLine.getStatusCode()));

        if (!pass){
            builder.pass(false)
                    .content(MessageFormat.format("expect:{0} actual:{0}", check.getDesired(), statusLine.getStatusCode()));
        }

        return builder.build();
    }



    private HttpUriRequest buildDeleteMethod(RuleInfo.Request request) {
        HttpUriRequest httpRequest;
        httpRequest = new HttpDelete("https://10.92.2.196/authn/base/LOCAL_UP");
        return httpRequest;
    }

    private HttpUriRequest buildPutMethod(RuleInfo.Request request) {
        HttpUriRequest httpRequest;
        httpRequest = new HttpPut("https://10.92.2.196/authn/base/LOCAL_UP");
        return httpRequest;
    }

    private HttpUriRequest buildPostMethod(RuleInfo.Request request) throws UnsupportedEncodingException {
        HttpUriRequest httpRequest;
        HttpPost httpRequest1 = new HttpPost("https://10.92.2.196/authn/base/LOCAL_UP");
        HttpPost httppost = new HttpPost(request.getUrl());
        List<BasicNameValuePair> nvps2 = new ArrayList<BasicNameValuePair>();
        nvps2.add(new BasicNameValuePair("__VIEWSTATE", "参数值"));
        //省略n个参数名和值
        httppost.setEntity(new UrlEncodedFormEntity(nvps2, "utf-8"));


        HttpPost httpPost = new HttpPost(request.getUrl());

        JSONObject param = new JSONObject();
        param.put("birthDates", new JSONArray());//添加参数

        httpPost.setEntity(new StringEntity(param.toString(), "UTF-8"));


        httpRequest = httpRequest1;
        return httpRequest;
    }

    private HttpUriRequest buildGetMethod(RuleInfo.Request request) throws URISyntaxException {

        HttpGet httpGet = new HttpGet(request.getUrl());

        BasicHeader[] basicHeaders = Optional.ofNullable(request.getHeads()).orElseGet(ArrayList::new)
                .stream().map(e -> new BasicHeader(e.getKey(), e.getValue())).toArray(BasicHeader[]::new);
        httpGet.setHeaders(basicHeaders);
        return httpGet;
    }
}
