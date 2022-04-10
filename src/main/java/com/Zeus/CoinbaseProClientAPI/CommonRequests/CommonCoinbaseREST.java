package com.Zeus.CoinbaseProClientAPI.CommonRequests;

import com.Zeus.CoinbaseProClientAPI.CoinbaseAPIResponse;
import com.Zeus.CoinbaseProClientAPI.CoinbaseProClient;

public class CommonCoinbaseREST {

	private CoinbaseProClient ACCOUNT;
	private String PROFILE_ID;
	
	public CommonCoinbaseREST(CoinbaseProClient pACCOUNT, String pPROFILE) { // CONSTRUCTOR
		
		this.ACCOUNT = pACCOUNT;
		this.PROFILE_ID = pPROFILE;
	}
	
	public CoinbaseAPIResponse[] getProfiles() { // GET ALL PROFILES
		
		return this.ACCOUNT.getRequestArray("/profiles", "");
	}
	
	public String cancelOrder(String orderID) { // CANCEL ORDER BY ID
		
		return this.ACCOUNT.deleteRequest("/orders", "/" + orderID + "?profile_id=" + this.PROFILE_ID);
	}
	
	public CoinbaseAPIResponse getAccount(String accountID) { // SINGLE CURRENCY ACCOUNT STATS BY ACCOUNT ID
		
		return this.ACCOUNT.getRequest("/accounts", "/" + accountID + "?profile_id=" + this.PROFILE_ID);
	}
	
	public CoinbaseAPIResponse getOrder(String orderID) { // SINGLE ORDER STATS
		
		return this.ACCOUNT.getRequest("/orders", "/" + orderID + "?profile_id=" + this.PROFILE_ID);
	}
	
	public CoinbaseAPIResponse[] getFills(String orderID) { // GET ORDER FILLS
		
		return this.ACCOUNT.getRequestArray("/fills", "?order_id=" + orderID + "&profile_id=" + this.PROFILE_ID);
	}
	
	public CoinbaseAPIResponse marketBuyOrder(String productID, String balance) { // EXECUTE MARKET ORDER
		
		return this.ACCOUNT.postRequest("/orders", String.join("", "{\"profile_id\":\"", this.PROFILE_ID,
				"\",\"type\":\"market\",\"side\":\"buy\",\"post_only\":\"false\",\"product_id\":\"", productID , "\",\"funds\":\"", balance ,"\"}"));
	}
	
	public CoinbaseAPIResponse marketSellOrder(String productID, String balance) { // EXECUTE MARKET ORDER
		
		return this.ACCOUNT.postRequest("/orders", String.join("", "{\"profile_id\":\"", this.PROFILE_ID,
				"\",\"type\":\"market\",\"side\":\"sell\",\"post_only\":\"false\",\"product_id\":\"", productID , "\",\"size\":\"", balance ,"\"}"));
	}
	
	public CoinbaseAPIResponse stopEntryLimitOrder(String productID, String trigger, String value, String balance) { // STOP ENTRY LIMIT SELL ORDER
		
		return this.ACCOUNT.postRequest("/orders", String.join("", "{\"profile_id\":\"", this.PROFILE_ID,
				"\",\"type\":\"limit\",\"side\":\"sell\",\"stop\":\"entry\",\"time_in_force\":\"GTC\",\"post_only\":\"false\",\"product_id\":\"", 
				productID, "\",\"stop_price\":\"", trigger, "\",\"price\":\"", value, "\",\"size\":\"", balance, "\"}"));
	}
	
	public CoinbaseAPIResponse stopLossLimitOrder(String productID, String trigger, String value, String balance) { // STOP LOSS LIMIT SELL ORDER
		
		return this.ACCOUNT.postRequest("/orders", String.join("", "{\"profile_id\":\"", this.PROFILE_ID,
				"\",\"type\":\"limit\",\"side\":\"sell\",\"stop\":\"loss\",\"time_in_force\":\"GTC\",\"post_only\":\"false\",\"product_id\":\"", 
				productID , "\",\"stop_price\":\"", trigger, "\",\"price\":\"", value, "\",\"size\":\"", balance, "\"}"));
	}
	
	public String getAccountID(String currency) { // GET ACCOUNT ID FOR PROVIDED CURRENCY

		CoinbaseAPIResponse[] ledger = this.ACCOUNT.getRequestArray("/accounts", "?profile_id=" + this.PROFILE_ID);

		for(CoinbaseAPIResponse eachAccount : ledger) {

			if(eachAccount.getCurrency().compareTo(currency) == 0) {
				return eachAccount.getID();
			}
		}
		return null;
	}
}
