package com.Zeus.CoinbaseProClientAPI.CommonRequests;

import com.Zeus.CoinbaseProClientAPI.CoinbaseAPIResponse;
import com.Zeus.CoinbaseProClientAPI.CoinbaseProClient;

public class CommonCoinbaseREST {

	private CoinbaseProClient ACCOUNT;
	
	public CommonCoinbaseREST(CoinbaseProClient pACCOUNT) { // CONSTRUCTOR
		
		this.ACCOUNT = pACCOUNT;
	}
	
	public CoinbaseAPIResponse getAccount(String accountID) { // SINGLE CURRENCY ACCOUNT STATS BY ACCOUNT ID
		
		return this.ACCOUNT.getRequest("/accounts", "/" + accountID);
	}
	
	public CoinbaseAPIResponse getOrder(String orderID) { // SINGLE ORDER STATS
		
		return this.ACCOUNT.getRequest("/orders", "/" + orderID);
	}
	
	public CoinbaseAPIResponse[] getFills(String orderID) { // GET ORDER FILLS ARRAY
		
		return this.ACCOUNT.getRequestArray("/fills", "?order_id=" + orderID + "&profile_id=default&limit=1000");
	}
	
	public CoinbaseAPIResponse marketBuyOrder(String productID, String balance) { // EXECUTE MARKET ORDER
		
		return this.ACCOUNT.postRequest("/orders", String.join("", "{\"type\":\"market\",\"side\":\"buy\",\"post_only\":\"false\",\"product_id\":\"", 
						productID , "\",\"funds\":\"", balance ,"\"}"));
	}
	
	public CoinbaseAPIResponse stopEntryLimitOrder(String productID, String trigger, String value, String balance) { // STOP ENTRY LIMIT SELL ORDER
		
		return this.ACCOUNT.postRequest("/orders", String.join("",
				"{\"type\":\"limit\",\"side\":\"sell\",\"stop\":\"entry\",\"time_in_force\":\"GTC\",\"post_only\":\"false\",\"product_id\":\"", 
				productID, "\",\"stop_price\":\"", trigger, "\",\"price\":\"", value, "\",\"size\":\"", balance, "\"}"));
	}
	
	public CoinbaseAPIResponse stopLossLimitOrder(String productID, String trigger, String value, String balance) { // STOP LOSS LIMIT SELL ORDER
		
		return this.ACCOUNT.postRequest("/orders", String.join("", 
				"{\"type\":\"limit\",\"side\":\"sell\",\"stop\":\"loss\",\"time_in_force\":\"GTC\",\"post_only\":\"false\",\"product_id\":\"", 
				productID , "\",\"stop_price\":\"", trigger, "\",\"price\":\"", value, "\",\"size\":\"", balance, "\"}"));
	}
	
	public String getAccountID(String currency) { // GET ACCOUNT ID FOR PROVIDED CURRENCY

		CoinbaseAPIResponse[] ledger = this.ACCOUNT.getRequestArray("/accounts", "");

		for(CoinbaseAPIResponse eachAccount : ledger) {

			if(eachAccount.getCurrency().compareTo(currency) == 0) {
				return eachAccount.getID();
			}
		}
		return null;
	}
}
