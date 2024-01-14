package com.Zeus.CoinbaseAdvClientAPI.CommonRequests;

import java.util.UUID;

import com.Zeus.CoinbaseAdvClientAPI.CoinbaseAPIResponse;
import com.Zeus.CoinbaseAdvClientAPI.CoinbaseAdvClient;

public class CommonCoinbaseREST {

	private CoinbaseAdvClient ACCOUNT;
	
	public CommonCoinbaseREST(CoinbaseAdvClient pACCOUNT/*, String pPROFILE*/) { // CONSTRUCTOR
		
		this.ACCOUNT = pACCOUNT;
	}
	
	public CoinbaseAPIResponse stopDownLimitOrder(String productID, String trigger, String value, String balance, String time) { // STOP ENTRY LIMIT SELL ORDER
		
		return this.ACCOUNT.postRequest("/orders", String.join("", 
				"{\"client_order_id\":\"", UUID.randomUUID().toString(),
				"\",\"product_id\":\"", productID,
				"\",\"side\":\"BUY\",\"order_configuration\":{\"stop_limit_stop_limit_gtd\":{\"base_size\":\"", balance,
				"\",\"limit_price\":\"", value,
				"\",\"stop_price\":\"", trigger,
				"\",\"end_time\":\"", time,
				"\",\"stop_direction\":\"STOP_DIRECTION_STOP_DOWN\"}}}"));
	}
	
	public CoinbaseAPIResponse stopUpLimitOrder(String productID, String trigger, String value, String balance, String time) { // STOP LOSS LIMIT SELL ORDER

		return this.ACCOUNT.postRequest("/orders", String.join("", 
				"{\"client_order_id\":\"", UUID.randomUUID().toString(),
				"\",\"product_id\":\"", productID,
				"\",\"side\":\"SELL\",\"order_configuration\":{\"stop_limit_stop_limit_gtd\":{\"base_size\":\"", balance,
				"\",\"limit_price\":\"", value,
				"\",\"stop_price\":\"", trigger,
				"\",\"end_time\":\"", time,
				"\",\"stop_direction\":\"STOP_DIRECTION_STOP_UP\"}}}"));
	}

	public CoinbaseAPIResponse stopDownLimitOrder(String productID, String trigger, String value, String balance) { // STOP ENTRY LIMIT SELL ORDER
		
		return this.ACCOUNT.postRequest("/orders", String.join("", 
				"{\"client_order_id\":\"", UUID.randomUUID().toString(),
				"\",\"product_id\":\"", productID,
				"\",\"side\":\"BUY\",\"order_configuration\":{\"stop_limit_stop_limit_gtc\":{\"base_size\":\"", balance,
				"\",\"limit_price\":\"", value,
				"\",\"stop_price\":\"", trigger,
				"\",\"stop_direction\":\"STOP_DIRECTION_STOP_DOWN\"}}}"));
	}
	
	public CoinbaseAPIResponse stopLossLimitOrder(String productID, String trigger, String value, String balance) { // STOP ENTRY LIMIT SELL ORDER

		return this.ACCOUNT.postRequest("/orders", String.join("", 
				"{\"client_order_id\":\"", UUID.randomUUID().toString(),
				"\",\"product_id\":\"", productID,
				"\",\"side\":\"SELL\",\"order_configuration\":{\"stop_limit_stop_limit_gtc\":{\"base_size\":\"", balance,
				"\",\"limit_price\":\"", value,
				"\",\"stop_price\":\"", trigger,
				"\",\"stop_direction\":\"STOP_DIRECTION_STOP_DOWN\"}}}"));
	}
	
	public CoinbaseAPIResponse stopUpLimitOrder(String productID, String trigger, String value, String balance) { // STOP LOSS LIMIT SELL ORDER

		return this.ACCOUNT.postRequest("/orders", String.join("", 
				"{\"client_order_id\":\"", UUID.randomUUID().toString(),
				"\",\"product_id\":\"", productID,
				"\",\"side\":\"SELL\",\"order_configuration\":{\"stop_limit_stop_limit_gtc\":{\"base_size\":\"", balance,
				"\",\"limit_price\":\"", value,
				"\",\"stop_price\":\"", trigger,
				"\",\"stop_direction\":\"STOP_DIRECTION_STOP_UP\"}}}"));
	}
	
	public CoinbaseAPIResponse marketBuyOrder(String productID, String balance) { // EXECUTE MARKET ORDER

		return this.ACCOUNT.postRequest("/orders", String.join("", 
				"{\"product_id\":\"", 
				productID , 
				"\",\"client_order_id\":\"",
				UUID.randomUUID().toString(),
				"\",\"side\":\"BUY\",\"order_configuration\":{\"market_market_ioc\":{\"quote_size\":\"",
				balance,
				"\"}}}"));
	}
	
	public CoinbaseAPIResponse marketSellOrder(String productID, String balance) { // EXECUTE MARKET ORDER

		return this.ACCOUNT.postRequest("/orders", String.join("", 
				"{\"product_id\":\"", 
				productID , 
				"\",\"client_order_id\":\"",
				UUID.randomUUID().toString(),
				"\",\"side\":\"SELL\",\"order_configuration\":{\"market_market_ioc\":{\"base_size\":\"",
				balance,
				"\"}}}"));
	}

	public CoinbaseAPIResponse getAccount(String accountID) { // SINGLE CURRENCY ACCOUNT STATS BY ACCOUNT ID
		
		return this.ACCOUNT.getRequest("/accounts/" + accountID, "");// + "?profile_id=" + this.PROFILE_ID);
	}

	public String getAccountID(String currency) { // GET ACCOUNT ID FOR PROVIDED CURRENCY

		CoinbaseAPIResponse[] ledger = this.ACCOUNT.getRequestArray("/accounts", "?limit=200"); //"?profile_id=" + this.PROFILE_ID);

		for(CoinbaseAPIResponse eachPage : ledger) {

			for(CoinbaseAPIResponse.Accounts eachAccount : eachPage.getAccounts()) {
			
				if(eachAccount.getCurrency().compareTo(currency) == 0 && !eachAccount.getName().contains("Vault")) {
					return eachAccount.getUuid();
				}
			}
		}
		return null;
	}
	
	public CoinbaseAPIResponse getOrder(String orderID, String clientOrderID) { // SINGLE ORDER STATS
		
		return this.ACCOUNT.getRequest(
				"/orders/historical/" + orderID, 
				"?client_order_id=" + clientOrderID + "&user_native_currency=USD");// + "?profile_id=" + this.PROFILE_ID);
	}
}