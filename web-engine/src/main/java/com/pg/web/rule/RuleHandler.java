package com.pg.web.rule;

import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.pg.web.common.exception.UnsupportedTypeException;
import com.pg.web.util.CompareUtils;
import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.*;
import org.apache.http.entity.StringEntity;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.MessageFormat;
import java.util.*;
import java.util.stream.Collectors;

public class RuleHandler {
    private int idx;

    protected RuleHandler next;

    private final RuleInfo ruleInfo;

    private RuleResult result;

    private final HttpClient httpClient;


    public RuleHandler(RuleInfo ruleInfo, HttpClient httpClient) {
        this.ruleInfo = ruleInfo;
        this.httpClient = httpClient;
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

    public void check() throws Exception {
        this.check(null);
    }

    public void check(RuleResult preRuleResult) throws Exception {

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

        RuleResult.RuleResultBuilder<?, ?> pass = RuleResult.builder().pass(true);

        List<RuleResult.ResultInfo> checkResult = new ArrayList<>();
        pass.checkResult(checkResult);

        try {

            List<RuleInfo.CheckInfo> checks = ruleInfo.getChecks();
            HttpResponse response = httpClient.execute(httpRequest);
            StatusLine statusLine = response.getStatusLine();


            checks.stream().forEach(check -> {
                String place = check.getPlace().toLowerCase();

                RuleResult.ResultInfo resultInfo;
                switch (place) {
                    case "status":
                        resultInfo = checkStatus(response, check);
                        break;
                    case "response_body":
                        resultInfo = checkBody(response, check);
                        break;
                    case "header":
                        resultInfo = checkHeader(response, check);
                        break;
                    default:
                        throw new UnsupportedTypeException("un supported type" + place);
                }

                if (!resultInfo.isPass()) {
                    pass.pass(false);
                }
                checkResult.add(resultInfo);
            });

        } catch (Exception e) {
            e.printStackTrace();
        }

        this.result = pass.build();

        if (!Objects.isNull(next)) {
            next.check(this.result);
        }
    }


    private RuleResult.ResultInfo checkStatus(HttpResponse response, RuleInfo.CheckInfo check) {


        int statusCode = response.getStatusLine().getStatusCode();
        boolean pass = CompareUtils.compareNumber(check.getRel(), check.getDesired(), String.valueOf(statusCode));

        return RuleResult.ResultInfo.builder()
                .pass(pass)
                .content(MessageFormat.format("expect:{0} actual:{1}", check.getDesired(), statusCode)).build();
    }

    private RuleResult.ResultInfo checkBody(HttpResponse response, RuleInfo.CheckInfo check) {

        String responseBody  = null;
        try {
            responseBody = EntityUtils.toString(response.getEntity(), "UTF-8");
        } catch (IOException e) {
            e.printStackTrace();
        }

        boolean pass = CompareUtils.compareString(check.getRel(), check.getDesired().get(0), responseBody);

        return RuleResult.ResultInfo.builder()
                .pass(pass)
                .content(MessageFormat.format("expect:{0} actual:{1}", check.getDesired(), responseBody)).build();


    }


    private RuleResult.ResultInfo checkHeader(HttpResponse response, RuleInfo.CheckInfo check) {


        List<String> desired = check.getDesired();

        if(Objects.isNull(desired) || desired.size() != 2){
            throw new UnsupportedTypeException("desired must have two values");
        }


        Header[] headers = response.getHeaders(desired.get(0));
        boolean isPass = false;
        for (Header header : headers) {
            //If the header meets the specified condition, pass.
            if(header.getName().equals(desired.get(0))){
                if(CompareUtils.compareString(check.getRel(), check.getDesired().get(1), header.getValue())){
                    isPass =true;
                    break;
                }
            }
        }

        String content = null;
        if(!isPass){
            List<KeyAndValue> actual = Arrays.stream(headers).map(h -> new KeyAndValue(h.getName(), h.getValue())).collect(Collectors.toList());
            content = MessageFormat.format("expect:{0} actual:{1}", check.getDesired(), actual);
        }
        return RuleResult.ResultInfo.builder()
                .pass(isPass).content(content).build();
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

    private HttpUriRequest buildGetMethod(RuleInfo.Request request) {

        HttpGet httpGet = new HttpGet(request.getUrl());
        BasicHeader[] basicHeaders = Optional.ofNullable(request.getHeads()).orElseGet(ArrayList::new)
                .stream().map(e -> new BasicHeader(e.getKey(), e.getValue())).toArray(BasicHeader[]::new);
        httpGet.setHeaders(basicHeaders);
        return httpGet;
    }
}
