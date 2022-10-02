# Installation
Clone the repository to your device

# Setup
You will find the following code block in PrivateConfigMain:
```
    public static final double BUDGET = 0.0;//budget must be <= to the amount of USDT in your wallet
    public static final String GATE_API_KEY = "";
    public static final String GATE_SECRET_KEY = "";

```
Paste your api key and secret key to ```GATE_API_KEY``` and ```GATE_SECRET_KEY```.
Set your ```BUDGET``` in USDTs. You should have enough USDTs in your Gateio spot wallet, your ```BUDGET``` must be smaller or equal to the amount of USDTs in your spot wallet.

# Execution
Run ```src/main/java/StrategyExecution.java```



