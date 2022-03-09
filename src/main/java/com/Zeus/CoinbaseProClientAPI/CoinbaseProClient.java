package com.Zeus.CoinbaseProClientAPI;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URL;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.time.Instant;
import java.util.Base64;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import javax.net.ssl.HttpsURLConnection;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

public class CoinbaseProClient extends Thread {

	// API SECURITY CREDENTIALS
	private String coinbaseSecretKey; // COINBASE API SECRET KEY
	private String coinbaseAPIKey; // COINBASE API KEY
	private String coinbasePassphrase; // COINBASE API PASSPHRASE
	
	// API URLS
	private String coinbaseProBaseURL; // COINBASE PRO API URL
	private String coinbaseWebsocketFeedURL; // COINBASE WEBSOCKET FEED URL

	// DATA MAPPING
	public class SubscriptionMapper {
		
		String identifier;
		WebSocketClient wsClient;
	}	
	public class SubscriptionOptionsMapper {
		
		String[] productIDList;
		Boolean useStatus;
		Boolean useHeartbeat;
		Boolean useLevel2;
		Boolean useFull;
		Boolean useUser;
		Boolean useTicker;
		Boolean useAuction;
		Boolean useMatches;
	}
	private Map<String, SubscriptionOptionsMapper> subscriptionOptionsMap;
	private Map<String, SubscriptionMapper> subscriptionMap;
	private Map<String, CoinbaseAPIResponse.Currency> currencyMap;
	private Map<String, CoinbaseAPIResponse.Products> productMap;
	private Map<String, CoinbaseAPIResponse> priceMap;
	private Map<String, CoinbaseAPIResponse> orderBook;
	private Map<String, String> orderLegend;
	
	// CLIENT CONTROL VARIABLES
	private Boolean LOGGER_LEVEL;
	private Boolean DATA_RECEIPT; 
	private Boolean DESERIALIZATION_IGNORE;
	private Boolean PULSE;
	private Boolean QUIET_HEARTBEAT; 
	private Integer SAURON_TIMER;
	private Boolean IS_STREAMING;

	// CONSTRUCTION
	public CoinbaseProClient(String baseURL, String wsFeedURL, String secretKey, String apiKey, String passphrase) { // PASS API CREDENTIALS AND URLS

		this.coinbaseWebsocketFeedURL = wsFeedURL;
		this.coinbaseProBaseURL = baseURL;
		this.coinbaseSecretKey = secretKey;
		this.coinbaseAPIKey = apiKey;
		this.coinbasePassphrase = passphrase;
		this.commonConstructor();
	}

	public CoinbaseProClient(String baseURL, String wsFeedURL) { // CONSTRUCTION WITHOUT SETTING API SECURITY CREDENTIALS

		this.coinbaseWebsocketFeedURL = wsFeedURL;
		this.coinbaseProBaseURL = baseURL;
		this.coinbaseAPIKey = null;
		this.coinbasePassphrase = null;
		this.coinbaseSecretKey = null;
		this.commonConstructor();
	}
	
	private void commonConstructor() { // COMMON CONSTRUCTION METHOD
		
		this.SAURON_TIMER = 10000;
		this.PULSE = true;
		this.IS_STREAMING = false;
		this.setName("COINBASE PRO API CLIENT by Zeus");
		this.setDaemon(true);
		this.enableLogger();
		this.disableDataMessages();
		this.enableDeserializationIgnoreExeceptions();
		this.disablePulseCheck();
		this.subscriptionOptionsMap = new HashMap<String, SubscriptionOptionsMapper>();
		this.subscriptionMap = new HashMap<String, SubscriptionMapper>();
		this.currencyMap = new HashMap<String, CoinbaseAPIResponse.Currency>();
		this.productMap = new HashMap<String, CoinbaseAPIResponse.Products>();
		this.priceMap = new HashMap<String, CoinbaseAPIResponse>();
		this.orderBook = new HashMap<String, CoinbaseAPIResponse>();
		this.orderLegend = new HashMap<String, String>();
	}

	public void setAPIKey(String pKEY) { // SET API KEY SECURITY CREDENTIAL
		
		this.coinbaseAPIKey = pKEY;
	}

	public void setAPISecretKey(String pSecretKEY) { // SET API SECRET KEY SECURITY CREDENTIAL
		
		this.coinbaseSecretKey = pSecretKEY;
	}

	public void setAPIPassphrase(String pPassphrase) { // SET API PASSPHRASE SECURITY CREDENTIAL
		
		this.coinbasePassphrase = pPassphrase;
	}
	
	// THREAD
	@Override
	public void run() { // WATCH SUBSCRIPTION MAP AND RESUBSCRIBE ON CLOSED CONNECTION
		
		while(this.PULSE) {
			
			try {
				Thread.sleep(this.SAURON_TIMER); // WAIT 10 SEC AND CHECK AGAIN SAURON
				
				this.subscriptionMap.forEach((key, value) -> {
					
					if(!value.wsClient.isOpen()) {
						
						this.IS_STREAMING = false;
						this.logger("Status", "SUBSCRIPTION: " + value.identifier + " RESTARTING", null);
						value.wsClient = subscriptionListener(value.identifier);
						this.subscriptionMap.replace(key, value);
						
						if(key.contains("matches") ||
								key.contains("full") ||
								key.contains("user")) { // CLEAR ORDER BOOK AND LEGEND TO REDOWNLOAD
							this.orderBook = new HashMap<String, CoinbaseAPIResponse>();
							this.orderLegend = new HashMap<String, String>();
						}
						value.wsClient.connect();
						String jsonMessageFinal = this.subscriptionAuth(key);
						jsonMessageFinal = String.join("", jsonMessageFinal, "\"}");
						
						while (!value.wsClient.isOpen()) { // WAIT FOR CONNECTION
						
							try {
								Thread.sleep(60);
							
							} catch (InterruptedException e) {
								this.logger("EXCEPTION", e.toString(), e);
								this.end();
							}
						}
						value.wsClient.send(jsonMessageFinal);
					}
				});
				
			} catch (Exception e) {
				this.logger("EXCEPTION", e.toString(), e);
				this.end();
			}
		}
		this.unsubscribeAll();
	}

	public void end() { // END THREAD
		
		this.PULSE = false;
	}
	
	// WEBSOCKET SUBSCRIPTION
	public WebSocketClient subscribe(String identifier, String[] productIDList, Boolean useStatus, Boolean useHeartbeat,
			Boolean useLevel2, Boolean useFull, Boolean useUser, Boolean useTicker, Boolean useAuction, Boolean useMatches) { 
		
		SubscriptionOptionsMapper optionsHold = new SubscriptionOptionsMapper();
		optionsHold.productIDList = productIDList;
		optionsHold.useAuction = useAuction;
		optionsHold.useFull = useFull;
		optionsHold.useHeartbeat = useHeartbeat;
		optionsHold.useLevel2 = useLevel2;
		optionsHold.useMatches = useMatches;
		optionsHold.useStatus = useStatus;
		optionsHold.useTicker = useTicker;
		optionsHold.useUser = useUser;
		this.subscriptionOptionsMap.put(identifier, optionsHold);
		
		WebSocketClient subscription = subscriptionListener(identifier);
		// SUBSCRIPTION MESSAGE BUILDER REGEXES
		String jsonMessagePrefix = "{\"type\":\"subscribe\",\"product_ids\":[";
		String jsonMessageAfterProductIDs = "],\"channels\":[";
		String jsonMessageAfterChannels = "]";
		String jsonMessageSuffix = "\"}"; // DON'T FORGET ABOUT THE RUN METHOD
		String jsonMessageFinal = jsonMessagePrefix;

		if(productIDList != null) {

			for (String each : productIDList) { // SERIALIZE PRODUCT ID LIST
				jsonMessageFinal = String.join("", jsonMessageFinal, "\"", each, "\",");
			}
			jsonMessageFinal = jsonMessageFinal.substring(0, jsonMessageFinal.length() - 1); // REMOVE TRAILING COMMA
		}
		jsonMessageFinal = String.join("", jsonMessageFinal, jsonMessageAfterProductIDs);

		if(useStatus) {
			jsonMessageFinal = String.join("", jsonMessageFinal, "\"status\",");
		}

		if(useHeartbeat) {
			jsonMessageFinal = String.join("", jsonMessageFinal, "\"heartbeat\",");
		}

		if(useLevel2) {
			jsonMessageFinal = String.join("", jsonMessageFinal, "\"level2\",");
		}

		if(useTicker) {
			jsonMessageFinal = String.join("", jsonMessageFinal, "\"ticker\",");
		}

		if(useUser) {
			jsonMessageFinal = String.join("", jsonMessageFinal, "\"user\",");
		}

		if(useFull) {
			jsonMessageFinal = String.join("", jsonMessageFinal, "\"full\",");
		}

		if(useAuction) {
			jsonMessageFinal = String.join("", jsonMessageFinal, "\"auctionfeed\",");
		}

		if(useMatches) {
			jsonMessageFinal = String.join("", jsonMessageFinal, "\"matches\",");
		}

		if(useStatus || useHeartbeat || useLevel2 || useTicker) {
			jsonMessageFinal = jsonMessageFinal.substring(0, jsonMessageFinal.length() - 1); // REMOVE TRAILING COMMA
		}
		
		jsonMessageFinal = String.join("", jsonMessageFinal, jsonMessageAfterChannels);
		SubscriptionMapper entry = new SubscriptionMapper();
		entry.identifier = identifier;
		entry.wsClient = subscription;
		
		if(this.subscriptionMap.containsKey(jsonMessageFinal)) { // RECYCLED CONNECTION
			this.subscriptionMap.replace(jsonMessageFinal, entry);
			
		} else { // NEW CLEAN ENTRY
			this.subscriptionMap.put(jsonMessageFinal, entry);
		}
		subscription.connect();
		
		if(this.coinbaseAPIKey != null) { // SECURITY CREDENTIALS PRESENT
			jsonMessageFinal = subscriptionAuth(jsonMessageFinal);
		}
		jsonMessageFinal = String.join("", jsonMessageFinal, jsonMessageSuffix);
		
		while (!subscription.isOpen()) { // WAIT FOR CONNECTION
			
			try {
				Thread.sleep(60);
				
			} catch (InterruptedException e) {
				this.logger("EXCEPTION", e.toString(), e);
				this.end();
			}
		}
		subscription.send(jsonMessageFinal);
		return subscription;
	}
	
	public void unsubscribeAll() { // END ALL SUBSCRIPTIONS AND CLOSE WebSocketClient
		
		this.subscriptionMap.forEach((key, value) -> {		
			value.wsClient.close();
			subscriptionMap.remove(key);
		});
		this.orderBook = new HashMap<String, CoinbaseAPIResponse>();
		this.orderLegend = new HashMap<String, String>();
	}

	public void unsubscribe(WebSocketClient killBillVol1) { // END SINGULAR SUBSCRIPTION

		this.subscriptionMap.forEach((key, value) -> {

			if(killBillVol1 == value.wsClient) {
				value.wsClient.close();
				subscriptionMap.remove(key);
				return;
			}
		});
	}

	private void dataDecision(CoinbaseAPIResponse pData) { // DATA INSERTION DECISION TREE

		try {
			this.IS_STREAMING = true;

			if(pData.getType().compareTo("status") == 0) { // CURRENCIES STATUS MESSAGE
				statusMessageRecieved(pData);
				return;
			}

			if(pData.getType().compareTo("ticker") == 0) { // INDIVIDUAL PRODUCT TICKER MESSAGE
				tickerMessageRecieved(pData);
				return;
			}

			if(pData.getType().compareTo("l2update") == 0) { // INDIVIDUAL LEVEL2 UPDATE MESSAGE
				l2updateMessageRecieved(pData);
				return;
			}

			if(pData.getType().compareTo("snapshot") == 0) { // IRRELIVANT SNAPSHOT MESSAGE
				snapshotMessageRecieved(pData);
				return;
			}

			if(pData.getType().compareTo("heartbeat") == 0) { // HEARTBEAT MESSAGE
				heartbeatMessageRecieved(pData);
				return;
			}

			if(pData.getType().compareTo("open") == 0) { // OPEN MESSAGE
				openMessageRecieved(pData);
				return;
			}

			if(pData.getType().compareTo("received") == 0) { // RECIEVED MESSAGE
				receivedMessageRecieved(pData);
				return;
			}

			if(pData.getType().compareTo("done") == 0) { // DONE MESSAGE
				doneMessageRecieved(pData);
				return;
			}

			if(pData.getType().compareTo("match") == 0) { // MATCH MESSAGE
				matchMessageRecieved(pData);
				return;
			}

			if(pData.getType().compareTo("subscriptions") == 0) { // SUBSCRIPTIONS MESSAGE
				subscriptionsMessageRecieved(pData);
				return;
			}

			if(pData.getType().compareTo("last_match") == 0) { // LAST MATCH MESSAGE
				last_matchMessageRecieved(pData);
				return;
			}

			if(this.DATA_RECEIPT) { // UNKNOWN SUBSCRIPTION MESSAGE RECIEVED
				this.logger("ERROR", "UNRECOGNIZED SUBSCRIPTION MESSAGE RECIEVED: " + pData.getType(), null);
			}

		} catch (Exception e) {
			this.logger("EXCEPTION", e.toString(), e);
			this.end();
		}
	}

	private WebSocketClient subscriptionListener(String identifier) { // LLAMBDA STYLE SUBSCRIPTION LISTENER

		WebSocketClient coinbaseSubscription = null;
		String subIdent = identifier;

		try {
			coinbaseSubscription = new WebSocketClient(new URI(this.coinbaseWebsocketFeedURL)) {

				@Override
				public void onMessage(String pMessage) { // SUBSCRIBED MESSAGES RETURNED HERE

					try {
						CoinbaseAPIResponse returnData = jsonDeserializationSingle(pMessage);

						if(returnData.getType().compareTo("error") == 0) { // RECIEVED ERROR RESPONSE
							logger("***** ERROR", "SUBSCRIPTION ERROR RECIEVED: " + returnData.getMessage() + ":\n***** " + returnData.getReason(), null);

						} else { // INJECT DATA
							dataDecision(returnData);
						}

					} catch (Exception e) {
						logger("EXCEPTION", e.toString(), e);
						end();
					}
				}

				@Override
				public void onOpen(ServerHandshake handshake) {

					logger("Status", "SUBSCRIPTION: " + subIdent + " STARTED", null);
				}

				@Override
				public void onClose(int code, String reason, boolean remote) {

					logger("Status", "SUBSCRIPTION: " + subIdent + " CLOSED", null);
				}

				@Override
				public void onError(Exception e) {

					logger("ERROR", "SUBSCRIPTION: " + subIdent + ": " + e.toString() + " RECONNECTING", null);
					subscribe(subIdent, subscriptionOptionsMap.get(subIdent).productIDList, subscriptionOptionsMap.get(subIdent).useStatus, subscriptionOptionsMap.get(subIdent).useHeartbeat, 
							subscriptionOptionsMap.get(subIdent).useLevel2, subscriptionOptionsMap.get(subIdent).useFull, subscriptionOptionsMap.get(subIdent).useUser, 
							subscriptionOptionsMap.get(subIdent).useTicker, subscriptionOptionsMap.get(subIdent).useAuction, subscriptionOptionsMap.get(subIdent).useMatches);
				}
			};

		} catch (Exception e) {
			this.logger("EXCEPTION", e.toString(), e);
			this.end();
		}
		return coinbaseSubscription;
	}

	private String subscriptionAuth(String pMessage) { // SUBSCRIPTION SIGNATURE BUIDER
		String jsonMessageFinal = pMessage;
		
		try {
			String timeStamp = Instant.now().getEpochSecond() + "";
			jsonMessageFinal = String.join("", jsonMessageFinal, ",\"signature\":\"",
					signMessage(timeStamp, "GET", "/users/self/verify", ""), "\",\"key\":\"", coinbaseSecretKey,
					"\",\"passphrase\":\"", coinbasePassphrase, "\",\"timestamp\":\"", timeStamp);

		} catch (Exception e) {
			this.logger("EXCEPTION", e.toString(), e);
			this.end();
		}
		return jsonMessageFinal;
	}
	
	// SUBSCRIPTION MESSAGE RECIEVERS
	private void statusMessageRecieved(CoinbaseAPIResponse pData) { // STATUS MESSAGE RECIEVED

		if(this.DATA_RECEIPT && !this.QUIET_HEARTBEAT) {
			this.logger("Status", "DATA RECEIPT: PRODUCT STATUSES UPDATED", null);
		}

		for (CoinbaseAPIResponse.Currency eachCurrency : pData.getCurrencies()) { // INDIVIDUAL CURRENCY DATA
			this.addCurrencyMapping(eachCurrency.getID(), eachCurrency);
		}

		for (CoinbaseAPIResponse.Products eachProduct : pData.getProducts()) { // ALL PRODUCTS STATUS MESSAGE
			this.addProductMapping(eachProduct.getID(), eachProduct);
		}
	}

	private void tickerMessageRecieved(CoinbaseAPIResponse pData) { // TICKER MESSAGE RECIEVED

		if(this.DATA_RECEIPT && !this.QUIET_HEARTBEAT) {
			this.logger("Status", "DATA RECEIPT: " + pData.getProduct_id().toUpperCase() +" TICKER: " + pData.getProduct_id() + " " + pData.getPrice(), null);
		}
		this.addPriceMapEntry(pData.getProduct_id(), pData);
	}

	private void l2updateMessageRecieved(CoinbaseAPIResponse pData) { // L2UPDATE MESSAGE RECIEVED

		if(this.DATA_RECEIPT && !this.QUIET_HEARTBEAT) {
			this.logger("Status", "DATA RECEIPT: " + pData.getProduct_id().toUpperCase() + " L2UPDATE: " + pData.getProduct_id() + " " + pData.getChanges()[0][1], null);
		}
		this.addPriceMapEntry(pData.getProduct_id(), pData);
	}

	private void snapshotMessageRecieved(CoinbaseAPIResponse pData) { // SNAPSHOT MESSAGE RECIEVED

		if(this.DATA_RECEIPT) {
			this.logger("Status", "DATA RECEIPT: " + pData.getProduct_id().toUpperCase() + " SNAPSHOT", null);
		}
		this.addPriceMapEntry(pData.getProduct_id(), pData);
	}

	private void heartbeatMessageRecieved(CoinbaseAPIResponse pData) { // HEARTBEAT MESSAGE RECIEVED

		if(this.DATA_RECEIPT && !this.QUIET_HEARTBEAT) {
			this.logger("Status", "DATA RECEIPT: " + pData.getProduct_id().toUpperCase() + " HEARTBEAT", null);
		}
		this.addPriceMapEntry(pData.getProduct_id(), pData);
	}
	
	private void openMessageRecieved(CoinbaseAPIResponse pData) { // OPEN MESSAGE RECIEVED

		if(this.DATA_RECEIPT) {
			this.logger("Status", "DATA RECEIPT: ORDER " + pData.getOrder_id() + " OPEN", null);
		}
		this.addOrderBookEntry(pData.getOrder_id(), pData);
	}
	
	private void receivedMessageRecieved(CoinbaseAPIResponse pData) { // RECEIVED MESSAGE RECIEVED

		if(this.DATA_RECEIPT) {
			this.logger("Status", "DATA RECEIPT: ORDER " + pData.getOrder_id() + " RECEIVED", null);
		}
		this.addOrderBookEntry(pData.getOrder_id(), pData);
	}

	private void doneMessageRecieved(CoinbaseAPIResponse pData) { // DONE MESSAGE RECIEVED

		if(this.DATA_RECEIPT) {
			this.logger("Status", "DATA RECEIPT: ORDER " + pData.getOrder_id() + " DONE", null);
		}
		this.removeOrderBookEntry(pData.getOrder_id());
	}

	private void matchMessageRecieved(CoinbaseAPIResponse pData) { // MATCH MESSAGE RECIEVED

		if(this.DATA_RECEIPT) {
			this.logger("Status", "DATA RECEIPT: ORDER " + pData.getMaker_order_id() + " - " + pData.getTaker_order_id() + " MATCH", null);
		} 
		this.priceMap.get(pData.getProduct_id()).setPrice(pData.getPrice());
		this.priceMap.get(pData.getProduct_id()).setSequence(pData.getSequence());
		this.priceMap.get(pData.getProduct_id()).setSide(pData.getSide());
		this.addOrderBookEntry(pData.getMaker_order_id(), pData);
		this.addOrderBookEntry(pData.getTaker_order_id(), pData);
	}

	private void last_matchMessageRecieved(CoinbaseAPIResponse pData) { // LAST MATCH MESSAGE RECIEVED

		if(this.DATA_RECEIPT) {
			this.logger("Status", "DATA RECEIPT: ORDER " + pData.getMaker_order_id() + " - " + pData.getTaker_order_id() + " LAST MATCH", null);
		} 
		this.priceMap.get(pData.getProduct_id()).setPrice(pData.getPrice());
		this.priceMap.get(pData.getProduct_id()).setSequence(pData.getSequence());
		this.priceMap.get(pData.getProduct_id()).setSide(pData.getSide());
		this.addOrderBookEntry(pData.getMaker_order_id(), pData);
		this.addOrderBookEntry(pData.getTaker_order_id(), pData);
	}

	private void subscriptionsMessageRecieved(CoinbaseAPIResponse pData) { // SUBSCRIPTIONS MESSAGE RECIEVED

		if(this.DATA_RECEIPT) {
			
			for(CoinbaseAPIResponse.Channels eachChannel : pData.getChannels()) {
				
				for(String eachProduct : eachChannel.getProduct_ids()) {
					this.logger("Status", "DATA RECEIPT: " + eachProduct + " " + eachChannel.getName().toUpperCase() + " SUBSCRIPTION STARTED", null);
				}
			}
		}
		
		// RETRIEVE ORDER BOOK FOR ANY SUBSCRIPTIONS RETURNING ORDER RELATED DATA
		for(CoinbaseAPIResponse.Channels eachChannel : pData.getChannels()) {
			
			for(String eachProduct : eachChannel.getProduct_ids()) {

				if(eachChannel.getName().compareTo("matches") == 0 ||
						eachChannel.getName().compareTo("full") == 0 ||
						eachChannel.getName().compareTo("user") == 0) {

					if(!this.orderLegend.containsKey(eachProduct)) { // NO ENTRY DETECTED

						if(this.DATA_RECEIPT) {
							this.logger("Status", "DOWNLOADING ORDERS: " + eachProduct, null);
						}
						CoinbaseAPIResponse response =this.getRequest("/products", String.join("", "/", eachProduct ,"/book?level=3"));


						for(String[] eachOrder : response.getAsks()) {
							CoinbaseAPIResponse entry = new CoinbaseAPIResponse();
							entry.setPrice(Double.valueOf(eachOrder[0]));
							entry.setSize(Double.valueOf(eachOrder[1]));
							entry.setSide("sell");
							this.addOrderBookEntry(eachOrder[2], entry);
						}

						for(String[] eachOrder : response.getBids()) {
							CoinbaseAPIResponse entry = new CoinbaseAPIResponse();
							entry.setPrice(Double.valueOf(eachOrder[0]));
							entry.setSize(Double.valueOf(eachOrder[1]));
							entry.setSide("buy");
							this.addOrderBookEntry(eachOrder[2], entry);
						}
						this.orderLegend.put(eachProduct, eachChannel.getName());
					}
				}
			}
		}
	}

	// REST REQUESTS
	public CoinbaseAPIResponse getRequest(String endpoint, String pathParams) { // PERFORM REST GET REQUEST

		CoinbaseAPIResponse returnData = null;

		try {
			String[] response = this.commonREST("GET", endpoint, pathParams, "");
			String responseBody = response[0];
			Integer responseCode = Integer.valueOf(response[1]);

			if(responseCode == 200) { // CLEAN RESPONSE RECIEVED
				returnData = jsonDeserializationSingle(responseBody);

			} else { // ERROR RECEIVED
				
				if(this.restErrorHandler("getRequest", responseCode, responseBody, endpoint + pathParams)) {
					this.logger("RETRYING", "GET REQUEST: " + endpoint + pathParams, null);
					return getRequest(endpoint, pathParams);
				}
			}

		} catch (Exception e) {
			this.logger("EXCEPTION", e.toString(), e);
			this.end();
		}
		return returnData;
	}

	public CoinbaseAPIResponse[] getRequestArray(String endpoint, String pathParams) { // PERFORM REST GET REQUEST

		CoinbaseAPIResponse[] returnData = null;

		try {
			String[] response = this.commonREST("GET", endpoint, pathParams, "");
			String responseBody = response[0];
			Integer responseCode = Integer.valueOf(response[1]);

			if(responseCode == 200) { // CLEAN RESPONSE RECIEVED
				returnData = jsonDeserializationArray(responseBody);

			} else { // ERROR RECEIVED
				
				if(this.restErrorHandler("getRequestArray", responseCode, responseBody, endpoint + pathParams)) {
					this.logger("RETRYING", "GET REQUEST: " + endpoint + pathParams, null);
					return getRequestArray(endpoint, pathParams);
				}
			}

		} catch (Exception e) {
			this.logger("EXCEPTION", e.toString(), e);
			this.end();
		}
		return returnData;
	}

	public CoinbaseAPIResponse postRequest(String endpoint, String requestBody) { // PERFORM REST POST REQUEST

		CoinbaseAPIResponse returnData = null;

		try {
			String[] response = this.commonREST("POST", endpoint, "", requestBody);
			String responseBody = response[0];
			Integer responseCode = Integer.valueOf(response[1]);

			if(responseCode == 200) { // CLEAN RESPONSE RECIEVED
				returnData = jsonDeserializationSingle(responseBody);

			} else { // ERROR RECEIVED
				
				if(this.restErrorHandler("postRequest", responseCode, responseBody, endpoint + requestBody)) {
					this.logger("RETRYING", "POST REQUEST: " + endpoint + requestBody , null);
					return postRequest(endpoint, requestBody);
				}
			}

		} catch (Exception e) {
			this.logger("EXCEPTION", e.toString(), e);
			this.end();
		}
		return returnData;
	}

	public CoinbaseAPIResponse[] postRequestArray(String endpoint, String requestBody) { // PERFORM REST POST REQUEST

		CoinbaseAPIResponse[] returnData = null;

		try {
			String[] response = this.commonREST("POST", endpoint, "", requestBody);
			String responseBody = response[0];
			Integer responseCode = Integer.valueOf(response[1]);

			if(responseCode == 200) { // CLEAN RESPONSE RECIEVED
				returnData = jsonDeserializationArray(responseBody);

			} else { // ERROR RECEIVED
				
				if(this.restErrorHandler("postRequestArray", responseCode, responseBody, endpoint + requestBody)) {
					this.logger("RETRYING", "POST REQUEST: " + endpoint + requestBody , null);
					return postRequestArray(endpoint, requestBody);
				}
			}

		} catch (Exception e) {
			this.logger("EXCEPTION", e.toString(), e);
			this.end();
		}
		return returnData;
	}

	public String deleteRequest(String endpoint, String pathParams) { // PERFORM REST DELETE REQUEST

		String returnData = null;

		try {
			String[] response = this.commonREST("DELETE", endpoint, pathParams, "");
			String responseBody = response[0];
			Integer responseCode = Integer.valueOf(response[1]);

			if(responseCode == 200) { // CLEAN RESPONSE RECIEVED
				returnData = responseBody;
						;
			} else { // ERROR RECEIVED
				
				if(this.restErrorHandler("deleteRequest", responseCode, responseBody, endpoint + pathParams)) {
					this.logger("RETRYING", "DELETE REQUEST: " + endpoint + pathParams , null);
					return deleteRequest(endpoint, pathParams);
				}
			}

		} catch (Exception e) {
			this.logger("EXCEPTION", e.toString(), e);
			this.end();
		}
		return returnData;
	}

	public CoinbaseAPIResponse putRequest(String endpoint, String pathParams, String requestBody) { // PERFORM REST POST REQUEST

		CoinbaseAPIResponse returnData = null;

		try {
			String[] response = this.commonREST("PUT", endpoint, pathParams, requestBody);
			String responseBody = response[0];
			Integer responseCode = Integer.valueOf(response[1]);

			if(responseCode == 200) { // CLEAN RESPONSE RECIEVED
				returnData = jsonDeserializationSingle(responseBody);

			} else { // ERROR RECEIVED
				if(this.restErrorHandler("putRequest", responseCode, responseBody, endpoint + pathParams + "{" + requestBody + "}")) {
					this.logger("RETRYING", "PUT REQUEST: " + endpoint + pathParams + "{" + requestBody + "}", null);
					return putRequest(endpoint, pathParams, requestBody);
				}
			}

		} catch (Exception e) {
			this.logger("EXCEPTION", e.toString(), e);
			this.end();
		}
		return returnData;
	}

	public CoinbaseAPIResponse[] putRequestArray(String endpoint, String pathParams, String requestBody) { // PERFORM REST POST REQUEST

		CoinbaseAPIResponse[] returnData = null;

		try {
			String[] response = this.commonREST("PUT", endpoint, pathParams, requestBody);
			String responseBody = response[0];
			Integer responseCode = Integer.valueOf(response[1]);

			if(responseCode == 200) { // CLEAN RESPONSE RECIEVED
				returnData = jsonDeserializationArray(responseBody);

			} else { // ERROR RECEIVED
				if(this.restErrorHandler("putRequestArray", responseCode, responseBody, endpoint + pathParams + "{" + requestBody + "}")) {
					this.logger("RETRYING", "PUT REQUEST: " + endpoint + pathParams + "{" + requestBody + "}", null);
					return putRequestArray(endpoint, pathParams, requestBody);
				}
			}

		} catch (Exception e) {
			this.logger("EXCEPTION", e.toString(), e);
			this.end();
		}
		return returnData;
	}
	
	private String[] commonREST(String method, String endpoint, String pathParams, String requestBody) // COMMON REST REQUEST IMPLIMENTATION
			throws InvalidKeyException, NoSuchAlgorithmException, IOException {
		
		HttpsURLConnection connection =
				(HttpsURLConnection) new URL(String.join("", coinbaseProBaseURL, endpoint + pathParams)).openConnection();
		connection.setRequestMethod(method);
		connection.setRequestProperty("Content-Type", "application/json");
		connection.setRequestProperty("Accept", "application/json");
		
		if(this.coinbaseAPIKey != null) { // SECURITY CREDENTIALS PRESENT
			String timeStamp = String.valueOf(Instant.now().getEpochSecond());
			connection.setRequestProperty("CB-ACCESS-KEY", coinbaseSecretKey);
			connection.setRequestProperty("CB-ACCESS-PASSPHRASE", coinbasePassphrase);
			connection.setRequestProperty("CB-ACCESS-SIGN", signMessage(timeStamp, method, endpoint + pathParams, requestBody));
			connection.setRequestProperty("CB-ACCESS-TIMESTAMP", timeStamp);
		}
		
		if(method.compareTo("PUT") == 0 || method.compareTo("POST") == 0) { // HANDLE BODY MESSAGE
			connection.setDoOutput(true);
			connection.getOutputStream().write(requestBody.getBytes());
		}
		String[] response = new String[3];
		response[0] = "";
		InputStreamReader responseReader = new InputStreamReader(connection.getInputStream(), "UTF-8");

		for(int nextChar = responseReader.read(); nextChar >= 0; nextChar = responseReader.read()) {
			response[0] = response[0] + (char)nextChar;
		} // READING STREAM ONE CHAR AT A TIME
		response[1] = String.valueOf(connection.getResponseCode());
		response[2] = connection.getResponseMessage();
		connection.disconnect();
		return response;
	}

	private Boolean restErrorHandler(String callingMethod, Integer statusCode, String responseBody, String request) { // REST SUB-SYSTEM ERROR HANDLING (RETURNS TRUE IS RETRY IS CALLED FOR)
		
		this.logger("ERROR", "REST REQUEST: " + request, null);
		
		Boolean messageHandled = false;
		Boolean attemptResend = false;
		
		if(callingMethod.compareTo("putRequestArray") == 0) {
			this.logger("ERROR", "REST PUT FAILURE: " + statusCode + ": " + responseBody, null);
			messageHandled = true;
		}
		
		if(callingMethod.compareTo("putRequest") == 0) {
			this.logger("ERROR", "REST PUT FAILURE: " + statusCode + ": " + responseBody, null);
			messageHandled = true;			
		}

		if(callingMethod.compareTo("deleteRequest") == 0) {
			this.logger("ERROR", "REST DELETE FAILURE: " + statusCode + ": " + responseBody, null);
			messageHandled = true;
		}
		
		if(callingMethod.compareTo("postRequestArray") == 0) {
			this.logger("ERROR", "REST POST FAILURE: " + statusCode + ": " + responseBody, null);
			messageHandled = true;
		}
		
		if(callingMethod.compareTo("postRequest") == 0) {
			this.logger("ERROR", "REST POST FAILURE: " + statusCode + ": " + responseBody, null);
			messageHandled = true;
		}

		if(callingMethod.compareTo("getRequestArray") == 0) {
			this.logger("ERROR", "REST GET FAILURE: " + statusCode + ": " + responseBody, null);
			messageHandled = true;
		}

		if(callingMethod.compareTo("getRequest") == 0) {
			this.logger("ERROR", "REST GET FAILURE: " + statusCode + ": " + responseBody, null);
			messageHandled = true;
		}
		
		if(!messageHandled) {
			this.logger("ERROR", "UNHANDLED MESSAGE: METHOD: " + callingMethod + ": " + statusCode + ": " + responseBody, null);
		}
		
		if(responseBody.contains("request timestamp expired") || responseBody.contains("502 Bad Gateway")) {
			attemptResend = true;
		}
		
		return attemptResend;
	}
	
	// DATA MAPPING
	private Boolean addCurrencyMapping(String addThisKEY, CoinbaseAPIResponse.Currency addThisVALUE) { // ADD TO CURRENCY MAP

		if(this.currencyMap.containsKey(addThisKEY)) { // ENTRY DETECTED
			this.currencyMap.replace(addThisKEY, addThisVALUE);
			
		} else {
			this.currencyMap.put(addThisKEY, addThisVALUE);
		}
		return this.currencyMap.containsKey(addThisKEY);
	}

	private Boolean addProductMapping(String addThisKEY, CoinbaseAPIResponse.Products addThisVALUE) { // ADD TO PRODUCT MAP

		if(this.productMap.containsKey(addThisKEY)) { // ENTRY DETECTED
			this.productMap.replace(addThisKEY, addThisVALUE);
			
		} else {
			this.productMap.put(addThisKEY, addThisVALUE);
		}
		return this.productMap.containsKey(addThisKEY);
	}

	private Boolean addPriceMapEntry(String addThisKEY, CoinbaseAPIResponse addThisVALUE) { // ADD TO PRICE MAP

		if(!this.priceMap.containsKey(addThisKEY)) { // NO ENTRY DETECTED
			this.priceMap.put(addThisKEY, addThisVALUE);
		
		} else {
			this.priceMap.replace(addThisKEY, new CoinbaseAPIResponseComparison(this.priceMap.get(addThisKEY), addThisVALUE).getResult());
		}
		return this.priceMap.containsKey(addThisKEY);
	}

	private Boolean addOrderBookEntry(String addThisKEY, CoinbaseAPIResponse addThisVALUE) { // ADD ORDER BOOK ENTRY

		if(!this.orderBook.containsKey(addThisKEY)) { // NO ENTRY DETECTED
			this.orderBook.put(addThisKEY, addThisVALUE);
		
		} else {
			this.orderBook.replace(addThisKEY, new CoinbaseAPIResponseComparison(this.orderBook.get(addThisKEY), addThisVALUE).getResult());
		}
		return this.orderBook.containsKey(addThisKEY);
	}

	private Boolean removeOrderBookEntry(String KEY) { // REMOVE ORDER BOOK ENTRY

		if(this.orderBook.containsKey(KEY)) {
			this.orderBook.remove(KEY);
		}
		return !this.orderBook.containsKey(KEY);
	}

	// DATA RETREIVAL
	public Map<String, CoinbaseAPIResponse.Currency> getCurrencyMap() // RETRIEVE CURRENCY MAP 
			throws InterruptedException {

		while(this.currencyMap.isEmpty() || !this.isStreaming()) {
			Thread.sleep(60);
		}
		return this.currencyMap;
	}

	public Map<String, CoinbaseAPIResponse.Products> getProductMap() // RETRIEVE PRODUCT MAP 
			throws InterruptedException { 
		
		while(this.productMap.isEmpty() || !this.isStreaming()) {
			Thread.sleep(60);
		}
		return this.productMap;
	}
	
	public Map<String, SubscriptionMapper> getSubscriptionMap() // RETRIEVE SUBSCRIPTION MAP 
			throws InterruptedException {
		
		while(this.subscriptionMap.isEmpty() || !this.isStreaming()) {
			Thread.sleep(60);
		}
		return this.subscriptionMap;
	}
	
	public Map<String, CoinbaseAPIResponse> getPriceMap() // RETRIEVE PRICE MAP 
			throws InterruptedException { 
		
		while(this.priceMap.isEmpty() || !this.isStreaming()) {
			Thread.sleep(60);
		}
		return this.priceMap;
	}

	public Map<String, CoinbaseAPIResponse> getOrderBook() // RETRIEVE ORDER BOOK 
			throws InterruptedException {
		
		while(this.orderBook.isEmpty() || !this.isStreaming()) {
			Thread.sleep(60);
		}
		return this.orderBook;
	}
	
	public Boolean isStreaming() { // DATA STREAMING STATUS
		
		 return this.IS_STREAMING;
	}
	
	// ENCRYPTION
	private String signMessage(String timestamp, String method, String path, String body) // ENCRYPTED HASH SECURITY SIGNATURE BUILDER
			throws NoSuchAlgorithmException, InvalidKeyException {

		String prehash = timestamp + method + path + body;
		Mac sha256_HMAC = Mac.getInstance("HmacSHA256");
		byte[] secretDecoded = Base64.getDecoder().decode(this.coinbaseAPIKey);
		SecretKeySpec secret_key = new SecretKeySpec(secretDecoded, "HmacSHA256");
		sha256_HMAC.init(secret_key);
		return Base64.getEncoder().encodeToString(sha256_HMAC.doFinal(prehash.getBytes()));
	}
	
	// DESERIALIZATION
	@SuppressWarnings("deprecation")
	private CoinbaseAPIResponse jsonDeserializationSingle(String responseBody) // SERIAL DATA STREAM PROCESSING FOR SINGULAR RESPONSE
			throws JsonMappingException, JsonProcessingException {
		
		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(DeserializationFeature.USE_JAVA_ARRAY_FOR_JSON_ARRAY, true);
		mapper.configure(DeserializationFeature.EAGER_DESERIALIZER_FETCH, true);
		mapper.configure(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT, true);
		mapper.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);
		mapper.configure(MapperFeature.ACCEPT_CASE_INSENSITIVE_ENUMS, true);
		mapper.configure(MapperFeature.ACCEPT_CASE_INSENSITIVE_PROPERTIES, true);
		mapper.configure(MapperFeature.ACCEPT_CASE_INSENSITIVE_VALUES, true);
		
		if(DESERIALIZATION_IGNORE) {
			mapper.configure(DeserializationFeature.FAIL_ON_IGNORED_PROPERTIES, false);
			mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
			mapper.configure(DeserializationFeature.FAIL_ON_UNRESOLVED_OBJECT_IDS, false);
			mapper.configure(DeserializationFeature.FAIL_ON_MISSING_CREATOR_PROPERTIES, false);
			mapper.configure(DeserializationFeature.FAIL_ON_MISSING_EXTERNAL_TYPE_ID_PROPERTY, false);
			mapper.configure(DeserializationFeature.FAIL_ON_NULL_CREATOR_PROPERTIES, false);
		}
		return mapper.readValue(responseBody, CoinbaseAPIResponse.class);
	}
	
	@SuppressWarnings("deprecation")
	private CoinbaseAPIResponse[] jsonDeserializationArray(String responseBody) // SERIAL DATA STREAM PROCESSING FOR ARRAYED RESPONSE
			throws JsonMappingException, JsonProcessingException {
		
		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(DeserializationFeature.USE_JAVA_ARRAY_FOR_JSON_ARRAY, true);
		mapper.configure(DeserializationFeature.EAGER_DESERIALIZER_FETCH, true);
		mapper.configure(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT, true);
		mapper.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);
		mapper.configure(MapperFeature.ACCEPT_CASE_INSENSITIVE_ENUMS, true);
		mapper.configure(MapperFeature.ACCEPT_CASE_INSENSITIVE_PROPERTIES, true);
		mapper.configure(MapperFeature.ACCEPT_CASE_INSENSITIVE_VALUES, true);
		
		if(DESERIALIZATION_IGNORE) {
			mapper.configure(DeserializationFeature.FAIL_ON_IGNORED_PROPERTIES, false);
			mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
			mapper.configure(DeserializationFeature.FAIL_ON_UNRESOLVED_OBJECT_IDS, false);
			mapper.configure(DeserializationFeature.FAIL_ON_MISSING_CREATOR_PROPERTIES, false);
			mapper.configure(DeserializationFeature.FAIL_ON_MISSING_EXTERNAL_TYPE_ID_PROPERTY, false);
			mapper.configure(DeserializationFeature.FAIL_ON_NULL_CREATOR_PROPERTIES, false);
		}
		return mapper.readValue(responseBody, CoinbaseAPIResponse[].class);
	}
	
	// LOGGER
	private void logger(String reason, String message, Exception e) { // CONSOLE MESSAGING
		
		if(LOGGER_LEVEL == true) {
			Calendar c = Calendar.getInstance();
			System.out.print(c.get(Calendar.YEAR) + "-" + (c.get(Calendar.MONTH) + 1) + "-" + c.get(Calendar.DAY_OF_MONTH) + " " + 
					c.get(Calendar.HOUR_OF_DAY) + ":" + c.get(Calendar.MINUTE) + ":" + c.get(Calendar.SECOND) + " ");
			System.out.println(reason + ": " + message);
			
			if(e != null) {
				 e.printStackTrace();
			}
		}
	}
	
	public void enableLogger() { // LOGGER ON ENABLE CONSOLE OUTPUT
		
		this.LOGGER_LEVEL = true;
	}
	
	public void disableLogger() { // LOGGER OFF DISABLE CONSOLE OUTPUT
		
		this.LOGGER_LEVEL = false;
	}
	
	public void enableDataMessages() { // ENABLE DATA RECEIPT MESSAGES FOR DEBUGGING CONNECTIONS
		
		this.DATA_RECEIPT = true;
	}
	
	public void disableDataMessages() { // DISABLE DATA RECEIPT MESSAGES FOR DEBUGGING CONNECTIONS
		
		this.DATA_RECEIPT = false;
	}
	
	public void enableDeserializationIgnoreExeceptions() { // DISABLE JACKSON DATABIND DESERIALIZATION EXCEPTIONS FAILURE
		
		this.DESERIALIZATION_IGNORE = true;
	}
	
	public void disableDeserializationIgnoreExeceptions() { // ENABLE JACKSON DATABIND DESERIALIZATION EXCEPTIONS FAILURE
		
		this.DESERIALIZATION_IGNORE = false;
	}

	public void enablePulseCheck() { // ENABLE HEARTBEAT DATA RECEIPT MESSAGES
		
		this.QUIET_HEARTBEAT = false;
	}
	
	public void disablePulseCheck() { // DISABLE HEARTBEAT DATA RECEIPT MESSAGES
		
		this.QUIET_HEARTBEAT = true;
	}
}
