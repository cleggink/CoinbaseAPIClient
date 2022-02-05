package com.Zeus.CoinbaseProClientAPI;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.time.Instant;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
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
	private class SubscriptionMapper {
		
		String identifier;
		WebSocketClient wsClient;
	}	
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
		this.subscriptionMap = new HashMap<String, SubscriptionMapper>();
		this.currencyMap = new HashMap<String, CoinbaseAPIResponse.Currency>();
		this.productMap = new HashMap<String, CoinbaseAPIResponse.Products>();
		this.priceMap = new HashMap<String, CoinbaseAPIResponse>();
		this.orderBook = new HashMap<String, CoinbaseAPIResponse>();
		this.orderLegend = new HashMap<String, String>();
		this.subscribe("STATUS CHANNEL", null, true, false, false, false, false, false, false, false);
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
							}
						}
						value.wsClient.send(jsonMessageFinal);
					}
				});
				
			} catch (Exception e) {
				this.logger("EXCEPTION", e.toString(), e);
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
		
		WebSocketClient subscription = subscriptionListener(identifier);
		// SUBSCRIPTION MESSAGE BUILDER REGEXES
		String jsonMessagePrefix = "{\"type\":\"subscribe\",\"product_ids\":[";
		String jsonMessageAfterProductIDs = "],\"channels\":[";
		String jsonMessageAfterChannels = "]";
		String jsonMessageSuffix = "\"}"; // DON'T FORGET ABOUT THE RUN METHOD
		String jsonMessageFinal = jsonMessagePrefix;

		if (productIDList != null) {

			for (String each : productIDList) { // SERIALIZE PRODUCT ID LIST
				jsonMessageFinal = String.join("", jsonMessageFinal, "\"", each, "\",");
			}
			jsonMessageFinal = jsonMessageFinal.substring(0, jsonMessageFinal.length() - 1); // REMOVE TRAILING COMMA
		}
		jsonMessageFinal = String.join("", jsonMessageFinal, jsonMessageAfterProductIDs);

		if (useStatus) {
			jsonMessageFinal = String.join("", jsonMessageFinal, "\"status\",");
		}

		if (useHeartbeat) {
			jsonMessageFinal = String.join("", jsonMessageFinal, "\"heartbeat\",");
		}

		if (useLevel2) {
			jsonMessageFinal = String.join("", jsonMessageFinal, "\"level2\",");
		}

		if (useTicker) {
			jsonMessageFinal = String.join("", jsonMessageFinal, "\"ticker\",");
		}

		if (useUser) {
			jsonMessageFinal = String.join("", jsonMessageFinal, "\"user\",");
		}

		if (useFull) {
			jsonMessageFinal = String.join("", jsonMessageFinal, "\"full\",");
		}

		if (useAuction) {
			jsonMessageFinal = String.join("", jsonMessageFinal, "\"auctionfeed\",");
		}

		if (useMatches) {
			jsonMessageFinal = String.join("", jsonMessageFinal, "\"matches\",");
		}

		if (useStatus || useHeartbeat || useLevel2 || useTicker) {
			jsonMessageFinal = jsonMessageFinal.substring(0, jsonMessageFinal.length() - 1); // REMOVE TRAILING COMMA
		}
		
		jsonMessageFinal = String.join("", jsonMessageFinal, jsonMessageAfterChannels);
		SubscriptionMapper entry = new SubscriptionMapper();
		entry.identifier = identifier;
		entry.wsClient = subscription;
		this.subscriptionMap.put(jsonMessageFinal, entry);
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

			if (pData.getType().compareTo("status") == 0) { // CURRENCIES STATUS MESSAGE
				statusMessageRecieved(pData);
				return;
			}

			if (pData.getType().compareTo("ticker") == 0) { // INDIVIDUAL PRODUCT TICKER MESSAGE
				tickerMessageRecieved(pData);
				return;
			}

			if (pData.getType().compareTo("l2update") == 0) { // INDIVIDUAL LEVEL2 UPDATE MESSAGE
				l2updateMessageRecieved(pData);
				return;
			}

			if (pData.getType().compareTo("snapshot") == 0) { // IRRELIVANT SNAPSHOT MESSAGE
				snapshotMessageRecieved(pData);
				return;
			}

			if (pData.getType().compareTo("heartbeat") == 0) { // HEARTBEAT MESSAGE
				heartbeatMessageRecieved(pData);
				return;
			}

			if (pData.getType().compareTo("open") == 0) { // HEARTBEAT MESSAGE
				openMessageRecieved(pData);
				return;
			}

			if (pData.getType().compareTo("received") == 0) { // HEARTBEAT MESSAGE
				receivedMessageRecieved(pData);
				return;
			}

			if (pData.getType().compareTo("done") == 0) { // HEARTBEAT MESSAGE
				doneMessageRecieved(pData);
				return;
			}

			if (pData.getType().compareTo("match") == 0) { // HEARTBEAT MESSAGE
				matchMessageRecieved(pData);
				return;
			}

			if (pData.getType().compareTo("subscriptions") == 0) { // SUBSCRIPTION MESSAGE
				subscriptionsMessageRecieved(pData);
				return;
			}

			if (pData.getType().compareTo("last_match") == 0) { // SUBSCRIPTION MESSAGE
				last_matchMessageRecieved(pData);
				return;
			}

			if(this.DATA_RECEIPT) {
				this.logger("ERROR", "UNRECOGNIZED DATA TYPE RECIEVED: " + pData.getType(), null);
			}

		} catch (Exception e) {
			this.logger("EXCEPTION", e.toString(), e);
		}
	}

	private WebSocketClient subscriptionListener(String identifier) { // LLAMBDA STYLE SUBSCRIPTION LISTENER

		WebSocketClient coinbaseSubscription = null;
		String subIndent = identifier;

		try {
			coinbaseSubscription = new WebSocketClient(new URI(this.coinbaseWebsocketFeedURL)) {

				@Override
				public void onMessage(String pMessage) { // SUBSCRIBED MESSAGES RETURNED HERE

					try {
						CoinbaseAPIResponse returnData = jsonDeserializationSingle(pMessage);

						if (returnData.getType().compareTo("error") == 0) { // RECIEVED ERROR RESPONSE
							logger("\n\n*** ERROR", "SUBSCRIPTION ERROR RECIEVED:\n***** " + returnData.getMessage() + ":\n***** " + returnData.getReason() + "\n\n", null);

						} else { // INJECT DATA
							dataDecision(returnData);
						}

					} catch (Exception e) {
						logger("EXCEPTION", e.toString(), e);
					}
				}

				@Override
				public void onOpen(ServerHandshake handshake) {

					logger("Status", "SUBSCRIPTION: " + subIndent + " STARTED", null);
				}

				@Override
				public void onClose(int code, String reason, boolean remote) {

					logger("Status", "SUBSCRIPTION: " + subIndent + " CLOSED", null);
				}

				@Override
				public void onError(Exception e) {

					logger("ERROR", "SUBSCRIPTION: " + subIndent + ": " + e.toString(), e);
				}
			};

		} catch (Exception e) {
			this.logger("EXCEPTION", e.toString(), e);
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
		}
		return jsonMessageFinal;
	}
	
	// SUBSCRIPTION MESSAGE RECIEVERS
	private void statusMessageRecieved(CoinbaseAPIResponse pData) { // STATUS MESSAGE RECIEVED

		if(this.DATA_RECEIPT) {
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
			this.logger("Status", "DATA RECEIPT: " + pData.getProduct_id().toUpperCase() +" TICKER", null);
		}
		this.addPriceMapEntry(pData.getProduct_id(), pData);
		this.IS_STREAMING = true;
	}

	private void l2updateMessageRecieved(CoinbaseAPIResponse pData) { // L2UPDATE MESSAGE RECIEVED

		if(this.DATA_RECEIPT && !this.QUIET_HEARTBEAT) {
			this.logger("Status", "DATA RECEIPT: " + pData.getProduct_id().toUpperCase() + " L2UPDATE", null);
		}
		this.addPriceMapEntry(pData.getProduct_id(), pData);
		
		for (String[] each : pData.getChanges()) {
			this.priceMap.get(pData.getProduct_id()).setSide(each[0]);
			this.priceMap.get(pData.getProduct_id()).setPrice(Double.valueOf(each[1]));
			this.priceMap.get(pData.getProduct_id()).setSize(Double.valueOf(each[2]));
			this.priceMap.get(pData.getProduct_id()).setTime(pData.getTime());
		}
		this.IS_STREAMING = true;
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
			HttpRequest request;
			
			if(this.coinbaseAPIKey != null) { // SECURITY CREDENTIALS PRESENT
				String timeStamp = Instant.now().getEpochSecond() + "";
				request = HttpRequest.newBuilder()
						.uri(URI.create(String.join("", coinbaseProBaseURL, endpoint, pathParams)))
						.header("CB-ACCESS-KEY", coinbaseSecretKey)
						.header("CB-ACCESS-PASSPHRASE", coinbasePassphrase)
						.header("CB-ACCESS-SIGN", signMessage(timeStamp, "GET", String.join("", endpoint, pathParams), ""))
						.header("CB-ACCESS-TIMESTAMP", timeStamp).header("Accept", "application/json")
						.header("Content-Type", "application/json")
						.header("Accept", "application/json")
						.method("GET", HttpRequest.BodyPublishers.noBody())
						.build();
				
			} else {
				request = HttpRequest.newBuilder()
						.uri(URI.create(String.join("", coinbaseProBaseURL, endpoint, pathParams)))
						.header("Content-Type", "application/json")
						.header("Accept", "application/json")
						.method("GET", HttpRequest.BodyPublishers.noBody())
						.build();
			}
			HttpResponse<String> response = HttpClient.newHttpClient().send(request,
					HttpResponse.BodyHandlers.ofString());

			if (response.statusCode() == 200) { // CLEAN RESPONSE RECIEVED
				returnData = jsonDeserializationSingle(response.body());

			} else { // ERROR RECEIVED
				this.logger("\n\nERROR", "REST GET FAILURE: " + response.body() + "\n\n", null);
			}

		} catch (Exception e) {
			this.logger("EXCEPTION", e.toString(), e);
		}
		return returnData;
	}

	public CoinbaseAPIResponse[] getRequestArray(String endpoint, String pathParams) { // PERFORM REST GET REQUEST

		CoinbaseAPIResponse[] returnData = null;

		try {
			HttpRequest request;
			
			if(this.coinbaseAPIKey != null) { // SECURITY CREDENTIALS PRESENT
				String timeStamp = Instant.now().getEpochSecond() + "";
				request = HttpRequest.newBuilder()
						.uri(URI.create(String.join("", coinbaseProBaseURL, endpoint, pathParams)))
						.header("CB-ACCESS-KEY", coinbaseSecretKey)
						.header("CB-ACCESS-PASSPHRASE", coinbasePassphrase)
						.header("CB-ACCESS-SIGN", signMessage(timeStamp, "GET", String.join("", endpoint, pathParams), ""))
						.header("CB-ACCESS-TIMESTAMP", timeStamp).header("Accept", "application/json")
						.header("Content-Type", "application/json")
						.header("Accept", "application/json")
						.method("GET", HttpRequest.BodyPublishers.noBody())
						.build();
				
			} else {
				request = HttpRequest.newBuilder()
						.uri(URI.create(String.join("", coinbaseProBaseURL, endpoint, pathParams)))
						.header("Content-Type", "application/json")
						.header("Accept", "application/json")
						.method("GET", HttpRequest.BodyPublishers.noBody())
						.build();
			}
			HttpResponse<String> response = HttpClient.newHttpClient().send(request,
					HttpResponse.BodyHandlers.ofString());

			if (response.statusCode() == 200) { // CLEAN RESPONSE RECIEVED
				returnData = jsonDeserializationArray(response.body());

			} else { // ERROR RECEIVED
				this.logger("\n\nERROR", "REST GET FAILURE: " + response.body() + "\n\n", null);
			}

		} catch (Exception e) {
			this.logger("EXCEPTION", e.toString(), e);
		}
		return returnData;
	}

	public CoinbaseAPIResponse postRequest(String endpoint, String requestBody) { // PERFORM REST POST REQUEST

		CoinbaseAPIResponse returnData = null;

		try {
			HttpRequest request;

			if(this.coinbaseAPIKey != null) { // SECURITY CREDENTIALS PRESENT
				String timeStamp = Instant.now().getEpochSecond() + "";
				request = HttpRequest.newBuilder()
						.uri(URI.create(String.join("", coinbaseProBaseURL, endpoint)))
						.header("CB-ACCESS-KEY", coinbaseSecretKey)
						.header("CB-ACCESS-PASSPHRASE", coinbasePassphrase)
						.header("CB-ACCESS-SIGN", signMessage(timeStamp, "POST", endpoint, requestBody))
						.header("CB-ACCESS-TIMESTAMP", timeStamp).header("Accept", "application/json")
						.header("Content-Type", "application/json")
						.header("Accept", "application/json")
						.method("POST", HttpRequest.BodyPublishers.ofString(requestBody))
						.build();
			
			} else {
				request = HttpRequest.newBuilder()
						.uri(URI.create(String.join("", coinbaseProBaseURL, endpoint)))
						.header("Content-Type", "application/json")
						.header("Accept", "application/json")
						.method("POST", HttpRequest.BodyPublishers.ofString(requestBody))
						.build();
			}
			HttpResponse<String> response = HttpClient.newHttpClient().send(request,
					HttpResponse.BodyHandlers.ofString());

			if (response.statusCode() == 200) { // CLEAN RESPONSE RECIEVED
				returnData = jsonDeserializationSingle(response.body());

			} else { // ERROR RECEIVED
				this.logger("\n\nERROR", "REST POST FAILURE: " + response.body() + "\n\n", null);
			}

		} catch (Exception e) {
			this.logger("EXCEPTION", e.toString(), e);
		}
		return returnData;
	}

	public CoinbaseAPIResponse[] postRequestArray(String endpoint, String requestBody) { // PERFORM REST POST REQUEST

		CoinbaseAPIResponse[] returnData = null;

		try {
			HttpRequest request;

			if(this.coinbaseAPIKey != null) { // SECURITY CREDENTIALS PRESENT
				String timeStamp = Instant.now().getEpochSecond() + "";
				request = HttpRequest.newBuilder()
						.uri(URI.create(String.join("", coinbaseProBaseURL, endpoint)))
						.header("CB-ACCESS-KEY", coinbaseSecretKey)
						.header("CB-ACCESS-PASSPHRASE", coinbasePassphrase)
						.header("CB-ACCESS-SIGN", signMessage(timeStamp, "POST", endpoint, requestBody))
						.header("CB-ACCESS-TIMESTAMP", timeStamp).header("Accept", "application/json")
						.header("Content-Type", "application/json")
						.header("Accept", "application/json")
						.method("POST", HttpRequest.BodyPublishers.ofString(requestBody))
						.build();
			
			} else {
				request = HttpRequest.newBuilder()
						.uri(URI.create(String.join("", coinbaseProBaseURL, endpoint)))
						.header("Content-Type", "application/json")
						.header("Accept", "application/json")
						.method("POST", HttpRequest.BodyPublishers.ofString(requestBody))
						.build();
			}
			HttpResponse<String> response = HttpClient.newHttpClient().send(request,
					HttpResponse.BodyHandlers.ofString());

			if (response.statusCode() == 200) { // CLEAN RESPONSE RECIEVED
				returnData = jsonDeserializationArray(response.body());

			} else { // ERROR RECEIVED
				this.logger("\n\nERROR", "REST POST FAILURE: " + response.body() + "\n\n", null);
			}

		} catch (Exception e) {
			this.logger("EXCEPTION", e.toString(), e);
		}
		return returnData;
	}

	public CoinbaseAPIResponse deleteRequest(String endpoint, String pathParams) { // PERFORM REST DELETE REQUEST

		CoinbaseAPIResponse returnData = null;

		try {
			HttpRequest request;

			if(this.coinbaseAPIKey != null) { // SECURITY CREDENTIALS PRESENT
				String timeStamp = Instant.now().getEpochSecond() + "";
				request = HttpRequest.newBuilder()
						.uri(URI.create(String.join("", coinbaseProBaseURL, endpoint, pathParams)))
						.header("CB-ACCESS-KEY", coinbaseSecretKey)
						.header("CB-ACCESS-PASSPHRASE", coinbasePassphrase)
						.header("CB-ACCESS-SIGN", signMessage(timeStamp, "DELETE", String.join("", endpoint, pathParams), ""))
						.header("CB-ACCESS-TIMESTAMP", timeStamp).header("Accept", "application/json")
						.header("Content-Type", "application/json")
						.header("Accept", "application/json")
						.method("DELETE", HttpRequest.BodyPublishers.noBody())
						.build();
			
			} else {
				request = HttpRequest.newBuilder()
						.uri(URI.create(String.join("", coinbaseProBaseURL, endpoint, pathParams)))
						.header("Content-Type", "application/json")
						.header("Accept", "application/json")
						.method("DELETE", HttpRequest.BodyPublishers.noBody())
						.build();
			}
			HttpResponse<String> response = HttpClient.newHttpClient().send(request,
					HttpResponse.BodyHandlers.ofString());

			if (response.statusCode() == 200) { // CLEAN RESPONSE RECIEVED
				returnData = jsonDeserializationSingle(response.body());

			} else { // ERROR RECEIVED
				this.logger("\n\nERROR", "REST DELETE FAILURE: " + response.body() + "\n\n", null);
			}

		} catch (Exception e) {
			this.logger("EXCEPTION", e.toString(), e);
		}
		return returnData;
	}

	public CoinbaseAPIResponse[] deleteRequestArray(String endpoint, String pathParams) { // PERFORM REST DELETE REQUEST

		CoinbaseAPIResponse[] returnData = null;

		try {
			HttpRequest request;

			if(this.coinbaseAPIKey != null) { // SECURITY CREDENTIALS PRESENT
				String timeStamp = Instant.now().getEpochSecond() + "";
				request = HttpRequest.newBuilder()
						.uri(URI.create(String.join("", coinbaseProBaseURL, endpoint, pathParams)))
						.header("CB-ACCESS-KEY", coinbaseSecretKey)
						.header("CB-ACCESS-PASSPHRASE", coinbasePassphrase)
						.header("CB-ACCESS-SIGN", signMessage(timeStamp, "DELETE", String.join("", endpoint, pathParams), ""))
						.header("CB-ACCESS-TIMESTAMP", timeStamp).header("Accept", "application/json")
						.header("Content-Type", "application/json")
						.header("Accept", "application/json")
						.method("DELETE", HttpRequest.BodyPublishers.noBody())
						.build();
			
			} else {
				request = HttpRequest.newBuilder()
						.uri(URI.create(String.join("", coinbaseProBaseURL, endpoint, pathParams)))
						.header("Content-Type", "application/json")
						.header("Accept", "application/json")
						.method("DELETE", HttpRequest.BodyPublishers.noBody())
						.build();
			}
			HttpResponse<String> response = HttpClient.newHttpClient().send(request,
					HttpResponse.BodyHandlers.ofString());

			if (response.statusCode() == 200) { // CLEAN RESPONSE RECIEVED
				returnData = jsonDeserializationArray(response.body());

			} else { // ERROR RECEIVED
				this.logger("\n\nERROR", "REST DELETE FAILURE: " + response.body() + "\n\n", null);
			}

		} catch (Exception e) {
			this.logger("EXCEPTION", e.toString(), e);
		}
		return returnData;
	}

	public CoinbaseAPIResponse putRequest(String endpoint, String pathParams, String requestBody) { // PERFORM REST POST REQUEST

		CoinbaseAPIResponse returnData = null;

		try {
			HttpRequest request;

			if(this.coinbaseAPIKey != null) { // SECURITY CREDENTIALS PRESENT
				String timeStamp = Instant.now().getEpochSecond() + "";
				request = HttpRequest.newBuilder()
						.uri(URI.create(String.join("", coinbaseProBaseURL, endpoint, pathParams)))
						.header("CB-ACCESS-KEY", coinbaseSecretKey)
						.header("CB-ACCESS-PASSPHRASE", coinbasePassphrase)
						.header("CB-ACCESS-SIGN", signMessage(timeStamp, "PUT", endpoint + pathParams, requestBody))
						.header("CB-ACCESS-TIMESTAMP", timeStamp).header("Accept", "application/json")
						.header("Content-Type", "application/json")
						.header("Accept", "application/json")
						.method("PUT", HttpRequest.BodyPublishers.ofString(requestBody))
						.build();
			
			} else {
				request = HttpRequest.newBuilder()
						.uri(URI.create(String.join("", coinbaseProBaseURL, endpoint, pathParams)))
						.header("Content-Type", "application/json")
						.header("Accept", "application/json")
						.method("PUT", HttpRequest.BodyPublishers.ofString(requestBody))
						.build();
			}
			HttpResponse<String> response = HttpClient.newHttpClient().send(request,
					HttpResponse.BodyHandlers.ofString());

			if (response.statusCode() == 200) { // CLEAN RESPONSE RECIEVED
				returnData = jsonDeserializationSingle(response.body());

			} else { // ERROR RECEIVED
				this.logger("\n\nERROR", "REST PUT FAILURE: " + response.body() + "\n\n", null);
			}

		} catch (Exception e) {
			this.logger("EXCEPTION", e.toString(), e);
		}
		return returnData;
	}

	public CoinbaseAPIResponse[] putRequestArray(String endpoint, String pathParams, String requestBody) { // PERFORM REST POST REQUEST

		CoinbaseAPIResponse[] returnData = null;

		try {
			HttpRequest request;

			if(this.coinbaseAPIKey != null) { // SECURITY CREDENTIALS PRESENT
				String timeStamp = Instant.now().getEpochSecond() + "";
				request = HttpRequest.newBuilder()
						.uri(URI.create(String.join("", coinbaseProBaseURL, endpoint, pathParams)))
						.header("CB-ACCESS-KEY", coinbaseSecretKey)
						.header("CB-ACCESS-PASSPHRASE", coinbasePassphrase)
						.header("CB-ACCESS-SIGN", signMessage(timeStamp, "PUT", endpoint + pathParams, requestBody))
						.header("CB-ACCESS-TIMESTAMP", timeStamp).header("Accept", "application/json")
						.header("Content-Type", "application/json")
						.header("Accept", "application/json")
						.method("PUT", HttpRequest.BodyPublishers.ofString(requestBody))
						.build();
			
			} else {
				request = HttpRequest.newBuilder()
						.uri(URI.create(String.join("", coinbaseProBaseURL, endpoint, pathParams)))
						.header("Content-Type", "application/json")
						.header("Accept", "application/json")
						.method("PUT", HttpRequest.BodyPublishers.ofString(requestBody))
						.build();
			}
			HttpResponse<String> response = HttpClient.newHttpClient().send(request,
					HttpResponse.BodyHandlers.ofString());

			if (response.statusCode() == 200) { // CLEAN RESPONSE RECIEVED
				returnData = jsonDeserializationArray(response.body());

			} else { // ERROR RECEIVED
				this.logger("\n\nERROR", "REST PUT FAILURE: " + response.body() + "\n\n", null);
			}

		} catch (Exception e) {
			this.logger("EXCEPTION", e.toString(), e);
		}
		return returnData;
	}

	// DATA MAPPING
	private Boolean addCurrencyMapping(String addThisKEY, CoinbaseAPIResponse.Currency addThisVALUE) { // ADD TO CURRENCY MAP

		if (this.currencyMap.containsKey(addThisKEY)) { // ENTRY DETECTED
			this.currencyMap.replace(addThisKEY, addThisVALUE);
			
		} else {
			this.currencyMap.put(addThisKEY, addThisVALUE);
		}
		return this.currencyMap.containsKey(addThisKEY);
	}

	private Boolean addProductMapping(String addThisKEY, CoinbaseAPIResponse.Products addThisVALUE) { // ADD TO PRODUCT MAP

		if (this.productMap.containsKey(addThisKEY)) { // ENTRY DETECTED
			this.productMap.replace(addThisKEY, addThisVALUE);
			
		} else {
			this.productMap.put(addThisKEY, addThisVALUE);
		}
		return this.productMap.containsKey(addThisKEY);
	}

	private Boolean addPriceMapEntry(String addThisKEY, CoinbaseAPIResponse addThisVALUE) { // ADD TO PRICE MAP

		if (!this.priceMap.containsKey(addThisKEY)) { // NO ENTRY DETECTED
			this.priceMap.put(addThisKEY, addThisVALUE);
		
		} else {
			this.priceMap.replace(addThisKEY, new CoinbaseAPIResponseComparison(this.priceMap.get(addThisKEY), addThisVALUE).getResult());
		}
		return this.priceMap.containsKey(addThisKEY);
	}

	private Boolean addOrderBookEntry(String addThisKEY, CoinbaseAPIResponse addThisVALUE) { // ADD ORDER BOOK ENTRY

		if (!this.orderBook.containsKey(addThisKEY)) { // NO ENTRY DETECTED
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

	/* UNUSED DATA MAPPING REMOVAL METHODS NOT READY TO DELETE THESE JUST YET
	private Boolean removeProductMapping(String KEY) { // REMOVE PRODUCT MAPPING

		this.productMap.remove(KEY);
		return !this.productMap.containsKey(KEY);
	}

	private Boolean removeCurrencyMapping(String KEY) { // REMOVE CURRENCY MAPPING

		this.currencyMap.remove(KEY);
		return !this.currencyMap.containsKey(KEY);
	}

	private Boolean removePriceMapEntry(String KEY) { // REMOVE PRICE MAPPING

		this.priceMap.remove(KEY);
		return !this.priceMap.containsKey(KEY);
	}*/

	// DATA RETREIVAL
	public Map<String, CoinbaseAPIResponse.Currency> getCurrencyMap() { // RETRIEVE CURRENCY MAP

		return this.currencyMap;
	}

	public Map<String, CoinbaseAPIResponse.Products> getProductMap() { // RETRIEVE PRODUCT MAP

		return this.productMap;
	}
	
	public Map<String, SubscriptionMapper> getSubscriptionMap() { // RETRIEVE SUBSCRIPTION MAP
		
		return this.subscriptionMap;
	}
	
	public Map<String, CoinbaseAPIResponse> getPriceMap() { // RETRIEVE PRICE MAP
		
		return this.priceMap;
	}

	public Map<String, CoinbaseAPIResponse> getOrderBook() { // RETRIEVE ORDER BOOK
		
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
