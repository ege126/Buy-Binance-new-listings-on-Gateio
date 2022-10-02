import io.gate.gateapi.ApiClient;
import io.gate.gateapi.ApiException;
import io.gate.gateapi.Configuration;
import io.gate.gateapi.GateApiException;
import io.gate.gateapi.api.SpotApi;
import io.gate.gateapi.models.Order;
import io.gate.gateapi.models.Ticker;
import org.json.JSONObject;
import java.util.List;

public class StrategyExecution {
    public static void main(String[] args) {
        while (true) {

            System.out.println("executing");
            ParseNewListings parseNewListings = new ParseNewListings();
            List<String> responses = parseNewListings.parseAll();//responses from the endpoints in PrivateConfigMain

            int listSize = responses.size();
            String newListingTicker = null;
            for (int i = 0; i < listSize; i++) {
                String ticker = parseNewListings.findNewListingTicker(new JSONObject(responses.get(i)));
                if (!ticker.equals("")) {
                    newListingTicker = ticker;
                    System.out.println("Found a new listing, the ticker is: " + newListingTicker);
                    break;
                }
                System.out.println("The response has been searched for ticker");
            }

            if (newListingTicker != null) {
                ApiClient defaultClient = Configuration.getDefaultApiClient();
                defaultClient.setBasePath("https://api.gateio.ws/api/v4");

                // Configure APIv4 authorization: apiv4
                defaultClient.setApiKeySecret(PrivateConfigMain.GATE_API_KEY, PrivateConfigMain.GATE_SECRET_KEY);

                SpotApi apiInstance = new SpotApi(defaultClient);
                String currencyPair = newListingTicker + "_USDT"; // String | Currency pair
                String timezone = "utc0"; // String | Timezone
                try {
                    List<Ticker> result_ticker = apiInstance.listTickers()
                            .currencyPair(currencyPair)
                            .timezone(timezone)
                            .execute();

                    double buyingPrice = Double.parseDouble(result_ticker.get(0).getLowestAsk()) * 1.01;
                    double buyingAmount = buyingPrice / PrivateConfigMain.BUDGET;
                    System.out.println("Buying price is: " + buyingPrice);
                    System.out.println("Buying amount is: " + buyingAmount);

                    Order order = new Order();
                    order.currencyPair(newListingTicker + "_USDT");
                    order.type(Order.TypeEnum.LIMIT);
                    order.account(Order.AccountEnum.SPOT);
                    order.side(Order.SideEnum.BUY);
                    order.autoBorrow(false);
                    order.price(Double.toString(buyingPrice));
                    order.amount(Double.toString(buyingAmount));

                    Order result_order = apiInstance.createOrder(order);
                    System.out.println(result_order);
                }
                catch (GateApiException e) {
                    System.err.println(String.format("Gate api exception, label: %s, message: %s", e.getErrorLabel(), e.getMessage()));
                    e.printStackTrace();
                }
                catch (ApiException e) {
                    System.err.println("Exception when calling SpotApi#createOrder");
                    System.err.println("Status code: " + e.getCode());
                    System.err.println("Response headers: " + e.getResponseHeaders());
                    e.printStackTrace();
                }
                catch (RuntimeException re){
                    throw new RuntimeException();
                }
                return;
            }
            try {
                Thread.sleep(1500);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
