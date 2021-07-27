package com.neko.seed;

import com.alibaba.fastjson.JSONObject;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

public class AuthCheckTests {

    private static final Logger LOG = LoggerFactory.getLogger(AuthCheckTests.class);

    @Test
    public void testCheck() throws Exception {
        CloseableHttpClient client = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost("http://47.101.65.157/zjgs/admin/third/check");

        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("token", "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ1c2VyIiwiZXhwIjoxNjIzMTE3MTYwfQ.-3fmZanKF4MOzs2P_ivbA45iwaoDIbTa7FGB3aGPxUBYoAxWO3d3xw8heJkDuUGqaVazrqxY9e-Akdgr5HJqQA"));
        httpPost.setEntity(new UrlEncodedFormEntity(params));

        CloseableHttpResponse response = client.execute(httpPost);

        HttpEntity entity = response.getEntity();
        JSONObject jsonResult = null;

        /**请求发送成功，并得到响应**/
        if (response.getStatusLine().getStatusCode() == 200) {
            String str = "";
            try {
                /**读取服务器返回过来的json字符串数据**/
                str = EntityUtils.toString(response.getEntity());
                /**把json字符串转换成json对象**/
                jsonResult = JSONObject.parseObject(str);
            } catch (Exception e) {
                LOG.error("post请求提交失败:", e);
            }
        }
        System.out.println(jsonResult.toJSONString());
        assertThat(response.getStatusLine().getStatusCode(), equalTo(200));
        client.close();
    }
}
