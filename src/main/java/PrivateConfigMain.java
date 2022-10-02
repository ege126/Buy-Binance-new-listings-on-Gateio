import java.util.Arrays;
import java.util.List;

public class PrivateConfigMain {
    public static final List<String> ENDPOINTSLIST= Arrays.asList(
            "https://www.binance.com/bapi/composite/v1/public/cms/article/list/query?type=1&pageNo=1&pageSize=1",
            "https://www.binance.com/gateway-api/v1/public/cms/article/list/query?type=1&pageNo=1&pageSize=1",
            "https://www.binance.com/gateway-api/v1/public/cms/article/catalog/list/query?catalogId=48&pageNo=1&pageSize=1",
            "https://www.binance.com/bapi/composite/v1/public/cms/article/catalog/list/query?catalogId=48&pageNo=1&pageSize=1"
    );
    /*
    * theese endpoints can also be added to the PrivateConfigMain, if the DNS works
    * https://www.binancezh.com/gateway-api/v1/public/cms/article/list/query?type=1&pageNo=1&pageSize=1
    * https://www.binancezh.com/bapi/composite/v1/public/cms/article/catalog/list/query?catalogId=48&pageNo=1&pageSize=1
    * https://www.binancezh.com/gateway-api/v1/public/cms/article/catalog/list/query?catalogId=48&pageNo=1&pageSize=1
    * https://www.binancezh.com/bapi/composite/v1/public/cms/article/latest/query
    */

    public static final double BUDGET = 0.0;//budget must be <= to the amount of USDT in your wallet
    public static final String GATE_API_KEY = "";
    public static final String GATE_SECRET_KEY = "";

}
