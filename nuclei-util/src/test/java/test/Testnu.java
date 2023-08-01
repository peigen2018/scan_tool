package test;

import com.pg.nuclei.util.Requests;
import com.pg.nuclei.util.TemplateUtils;
import com.pg.nuclei.util.TransformedRequest;
import com.pg.nuclei.util.YamlUtil;
import org.junit.jupiter.api.Test;

public class Testnu {
    @Test
    public void test(){
        final String request = "GET / HTTP/1.1\n" +
                "Host: localhost\n" +
                "User-Agent: §Mozilla/5.0§ (Macintosh; Intel Mac OS X 10.15; rv:96.0) Gecko/20100101 Firefox/96.0\n" +
                "Accept-Language: en-US,en;§q=0.5\n" +
                "Accept§-Encoding: §gzip, deflate§\n" +
                "Pragma: no-cache\n" +
                "Cache-Control: no-cache\n";

        final TransformedRequest transformedRequest = TemplateUtils.transformRequestWithPayloads(Requests.AttackType.batteringram, request);
        final Requests requests = new Requests();
        requests.setTransformedRequest(transformedRequest);
        requests.addPayloads(Requests.AttackType.batteringram, "param1", "Chrome");
        requests.addPayloads(Requests.AttackType.batteringram, "param3", "compress");



        System.out.println(YamlUtil.dump(requests));
        System.out.println("----------------------");
    }
}
