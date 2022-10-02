import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ParseNewListings {

    private List<String> endpointList = new ArrayList<>();

    public ParseNewListings(){
        this.endpointList = PrivateConfigMain.ENDPOINTSLIST;
    }

    public ParseNewListings(String ... endpoints){
        this.endpointList = new ArrayList<>(Arrays.asList(endpoints));
    }

    public List<String> parseAll(){

        List<String> responses = new ArrayList<>();

        for (int i=0; i<this.endpointList.size(); i++){
            String response = parseFromEndpoint(endpointList.get(i));
            responses.add(response);
        }
        return responses;
    }

    public String parseFromEndpoint(String endpoint){
        try {
            URL url = new URL(endpoint);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            BufferedReader br = new BufferedReader( new InputStreamReader(connection.getInputStream()));

            String outputLine;
            String output = "";
            while ((outputLine = br.readLine()) != null){
                output += outputLine;
            }
            connection.disconnect();
            return output;
        }
        catch (IOException e) {
            System.out.println("Exception catched when parsing from: " + endpoint);
            //throw new RuntimeException(e);
        }
        return "NO DATA";
    }

    /*
     * finds the ticker from the given JSONObject
     * searches for the first "title", so just the latest anouncement by Binance
     * returns an empty string if there are no new listings
     */
    public String findNewListingTicker(JSONObject jsonResponse) {
        String ticker = null;
        try {
            ticker = "";
            if (jsonResponse.toString().contains("title")) {
                int indexOfTitle = jsonResponse.toString().indexOf("title");
                String title = jsonResponse.toString().substring(indexOfTitle+8);
                title = title.substring(0,title.indexOf('"'));
                //System.out.println("The title of the last announcement is:" +  title);
                int indexOfFirstParanthesis = title.indexOf("(");

                String searchText = title.substring(indexOfFirstParanthesis);
                for (int e = 1; e < searchText.length(); e++) {
                    if (searchText.charAt(e) == ')') {
                        System.out.println("ticker found: " + ticker);
                        return ticker;
                    }
                    ticker += searchText.charAt(e);
                }
            }
        }
        //if exception is catched: there is no new listing, just an irrelevant title
        //can be ignored
        catch (IndexOutOfBoundsException ie) {
        }
        return ticker;
    }

}

