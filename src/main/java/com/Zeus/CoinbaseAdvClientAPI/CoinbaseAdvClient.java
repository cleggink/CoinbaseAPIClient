package com.Zeus.CoinbaseAdvClientAPI;

import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URL;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.time.Instant;
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

public class CoinbaseAdvClient {

	// API SECURITY CREDENTIALS
	private String coinbaseSecretKey; // COINBASE API SECRET KEY
	private String coinbaseAPIKey; // COINBASE API KEY

	// API URLS
	private String coinbaseProBaseURL; // COINBASE PRO API URL
	private String coinbaseAdvExtendedURL;
	private String coinbaseWebsocketFeedURL; // COINBASE WEBSOCKET FEED URL

	// DATA MAPPING
	public class SubscriptionMapper {

		String identifier;
		WebSocketClient wsClient;
		String wsMessage;
		String[] wsProductList;
		String wsChannel;
	}
	
	public class SubscriptionOptionsMapper {

		String[] productIDList;
		String channel;
		String wsMessage;
		WebSocketClient wsClient;
	}

	private Map<String, SubscriptionOptionsMapper> subscriptionOptionsMap;
	private Map<String, SubscriptionMapper> subscriptionMap;
	private Map<String, CoinbaseAPIResponse.Currency> currencyMap;
	private Map<String, CoinbaseAPIResponse.Products> productMap;
	private Map<String, CoinbaseAPIResponse> priceMap;

	// CLIENT CONTROL VARIABLES
	private Boolean LOGGER_LEVEL;
	private Boolean DATA_RECEIPT;
	private Boolean DESERIALIZATION_IGNORE;
	private Boolean QUIET_HEARTBEAT;
	private Boolean IS_STREAMING;
	private Boolean ANDROIID_COMPAT;
	private Boolean SUB_HOLD;
	private Integer RATE_LIMIT_TIMER;
	private Boolean nullBLOCKING;
	private Boolean errLOGGER;
	
	// MISC
	private PrintTime printTime = new PrintTime();
	private TickerRateTracking rateTracker = new TickerRateTracking();

	// CONSTRUCTION
	public CoinbaseAdvClient(String baseURL, String eURL, String wsFeedURL, String secretKey, String apiKey) { // PASS API CREDENTIALS AND URLS 

		this.coinbaseWebsocketFeedURL = wsFeedURL;
		this.coinbaseProBaseURL = baseURL;
		this.coinbaseAdvExtendedURL = eURL;
		this.coinbaseSecretKey = secretKey;
		this.coinbaseAPIKey = apiKey;
		this.commonConstructor();
	}

	public CoinbaseAdvClient(String baseURL, String eURL, String wsFeedURL) { // CONSTRUCTION WITHOUT SETTING API SECURITY CREDENTIALS

		this.coinbaseWebsocketFeedURL = wsFeedURL;
		this.coinbaseProBaseURL = baseURL;
		this.coinbaseAdvExtendedURL = eURL;
		this.coinbaseAPIKey = null;
		this.coinbaseSecretKey = null;
		this.commonConstructor();
	}

	private void commonConstructor() { // COMMON CONSTRUCTION METHOD

		this.RATE_LIMIT_TIMER = 1000;
		this.errLOGGER = false;
		this.IS_STREAMING = false;
		this.SUB_HOLD = false;
		this.nullBLOCKING = false;
		this.enableLogger();
		this.disableDataMessages();
		this.enableDeserializationIgnoreExeceptions();
		this.disablePulseCheck();
		this.disableAndroid();
		this.subscriptionOptionsMap = new HashMap<String, SubscriptionOptionsMapper>();
		this.subscriptionMap = new HashMap<String, SubscriptionMapper>();
		this.currencyMap = new HashMap<String, CoinbaseAPIResponse.Currency>();
		this.productMap = new HashMap<String, CoinbaseAPIResponse.Products>();
		this.priceMap = new HashMap<String, CoinbaseAPIResponse>();
	}

	// SETTERS
	public void setAPIKey(String pKEY) { // SET API KEY SECURITY CREDENTIAL

		this.coinbaseAPIKey = pKEY;
	}

	public void setAPISecretKey(String pSecretKEY) { // SET API SECRET KEY SECURITY CREDENTIAL

		this.coinbaseSecretKey = pSecretKEY;
	}
	
	public void setRATE_LIMIT_TIMER(Integer passVar) {
		
		this.RATE_LIMIT_TIMER = passVar;
	}

	// WEBSOCKET
	public WebSocketClient subscribe(String identifier, String[] productIDList, String channel, Boolean recycle) throws InterruptedException {
		
		WebSocketClient subscription = subscriptionListener(identifier, recycle);
		SubscriptionOptionsMapper optionsHold = new SubscriptionOptionsMapper();
		// SUBSCRIPTION MESSAGE BUILDER REGEXES
		String jsonMessagePrefix = "{\"type\":\"subscribe\",\"product_ids\":[";
		String jsonMessageAfterProductIDs = "],\"channel\":\"";
		String jsonMessageAfterChannels = "\"";
		String jsonMessageSuffix = "}";
		String jsonMessageFinal = jsonMessagePrefix;

		if (productIDList != null) {

			for (String each : productIDList) { // SERIALIZE PRODUCT ID LIST
				jsonMessageFinal = String.join("", jsonMessageFinal, "\"", each, "\",");
			}
			jsonMessageFinal = jsonMessageFinal.substring(0, jsonMessageFinal.length() - 1); // REMOVE TRAILING COMMA
		}
		jsonMessageFinal = String.join("", jsonMessageFinal, jsonMessageAfterProductIDs, channel, jsonMessageAfterChannels);
		SubscriptionMapper entry = new SubscriptionMapper();
		entry.identifier = identifier;
		entry.wsClient = subscription;
		entry.wsChannel = channel;
		entry.wsMessage = jsonMessageFinal;
		entry.wsProductList = productIDList;
		optionsHold.productIDList = productIDList;
		optionsHold.channel = channel;
		optionsHold.wsClient = subscription;
		optionsHold.wsMessage = jsonMessageFinal;
		subscriptionOptionsMap.put(identifier, optionsHold);

		if (this.subscriptionMap.containsKey(jsonMessageFinal)) { // RECYCLED CONNECTION
			this.subscriptionMap.replace(jsonMessageFinal, entry);

		} else { // NEW CLEAN ENTRY
			this.subscriptionMap.put(jsonMessageFinal, entry);
		}

		if (this.ANDROIID_COMPAT) {
			subscription.setConnectionLostTimeout(-1);
		}
		
		try {
			subscription.connectBlocking();
			
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		if (this.coinbaseAPIKey != null) { // SECURITY CREDENTIALS PRESENT
			jsonMessageFinal = subscriptionAuth(jsonMessageFinal, productIDList, channel);
		}
		jsonMessageFinal = String.join("", jsonMessageFinal, jsonMessageSuffix);
		
		try {
			subscription.send(jsonMessageFinal);
			
		} catch (Exception e) {
			this.unsubscribeAll();
			return null;
		}
		
		if(channel.compareTo("ticker") == 0) {

			for (String each : productIDList) {
				
				this.rateTracker.addProduct(each);
			}
		}
		return subscription;
	}

	public void unsubscribeAll() { // END ALL SUBSCRIPTIONS AND CLOSE WebSocketClient
		
		while(SUB_HOLD) {
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		SUB_HOLD = true;
		this.subscriptionMap.forEach((key, value) -> {
			value.wsClient.close();
		});
		this.subscriptionOptionsMap = new HashMap<String, SubscriptionOptionsMapper>();
		this.rateTracker = new TickerRateTracking();
		SUB_HOLD = false;
	}
	
	private void dataDecision(CoinbaseAPIResponse pData) { // DATA INSERTION DECISION TREE

		try {
			this.IS_STREAMING = true;

			for(CoinbaseAPIResponse.Events event : pData.getEvents()) { // ADVANCED TRADE MIGRATION

				if(event.getType() != null) {
					
					// TICKER UPDATE
					if(pData.getChannel().compareTo("ticker") == 0 && 
							(event.getType().compareTo("update") == 0 || event.getType().compareTo("snapshot") == 0)) { 
						tickerMessageRecieved(event, pData);	
						return;
					}
					
					// STATUS UPDATE
					if(pData.getChannel().compareTo("status") == 0 && event.getType().compareTo("snapshot") == 0) {
						 statusMessageRecieved(event, pData);
						 return;
						
					} // UNHANDELED MESSAGE CATCH
					this.logger("UNHANDLED WEBSOCKET MESSAGE", "UPDATE NEEDED", null);
				}
			}

			/* OLD COINBASE PRO THROWBACK
			if(pData.getType() != null) { 

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

				if (pData.getType().compareTo("open") == 0) { // OPEN MESSAGE
					openMessageRecieved(pData);
					return;
				}

				if (pData.getType().compareTo("received") == 0) { // RECIEVED MESSAGE
					receivedMessageRecieved(pData);
					return;
				}

				if (pData.getType().compareTo("done") == 0) { // DONE MESSAGE
					doneMessageRecieved(pData);
					return;
				}

				if (pData.getType().compareTo("match") == 0) { // MATCH MESSAGE
					matchMessageRecieved(pData);
					return;
				}

				if (pData.getType().compareTo("subscriptions") == 0) { // SUBSCRIPTIONS MESSAGE
					subscriptionsMessageRecieved(pData);
					return;
				}

				if (pData.getType().compareTo("last_match") == 0) { // LAST MATCH MESSAGE
					last_matchMessageRecieved(pData);
					return;
				}

				if (this.DATA_RECEIPT) { // UNKNOWN SUBSCRIPTION MESSAGE RECIEVED
					this.logger("ERROR", "UNRECOGNIZED SUBSCRIPTION MESSAGE RECIEVED: " + pData.getType(), null);
				}

			}*/
		} catch (Exception e) {
			this.logger("EXCEPTION", e.toString(), e);
		}
	}

	private WebSocketClient subscriptionListener(String identifier, Boolean recycle) { // LLAMBDA STYLE SUBSCRIPTION LISTENER

		WebSocketClient coinbaseSubscription = null;
		String subIdent = identifier;

		try {
			coinbaseSubscription = new WebSocketClient(new URI(this.coinbaseWebsocketFeedURL)) {

				@Override
				public void onMessage(String pMessage) { // SUBSCRIBED MESSAGES RETURNED HERE

					try {
						CoinbaseAPIResponse returnData = jsonDeserializationSingle(pMessage);

						if(returnData.getType() != null) { // OLD PRO THROWBACK

							if (returnData.getType().compareTo("error") == 0) { // RECIEVED ERROR RESPONSE
								logger("***** ERROR", "SUBSCRIPTION ERROR RECIEVED: " + returnData.getMessage()
								+ ":\n***** " + returnData.getReason(), null);

							} else { // INJECT DATA
								dataDecision(returnData);
							}
							
						} else {
							dataDecision(returnData);
						}

					} catch (Exception e) {
						logger("EXCEPTION", e.toString(), e);
					}
				}

				@Override
				public void onOpen(ServerHandshake handshake) {
					
					logger("Status", "SUBSCRIPTION: " + subIdent + " STARTED", null);
					setName();
				}
				
				@Override
				public void onClose(int code, String reason, boolean remote) {

					logger("Status", "SUBSCRIPTION: " + subIdent + " CLOSED: " + code + ": " + reason, null);
					
					if(code == 1002 || code == -1) { // BAD GATEWAY || CONNECTION TIMED OUT
						unsubscribeAll();
						
					} else {
						recycleConnection();
					}
				}

				@Override
				public void onError(Exception e) {
					
					recycleConnection();
				}
				
				public void setName() {

					Thread.currentThread().setName(subIdent + " WebSocket Listener");
				}
				
				public void recycleConnection() {

					if (!recycle) {
						return;
					}
					IS_STREAMING = false;
					int x =  0;
					
					while(SUB_HOLD) {
						
						try {
							Thread.sleep(1000);
							x++;
							
							if(x < 300) {
								SUB_HOLD = false;
								unsubscribeAll();
								return;
							}		
						} catch (InterruptedException e) {
							// TODO: TURN THIS LIGHT RED
						}
					}
					SUB_HOLD = true;
					this.getConnection().close();
					String[] list = subscriptionOptionsMap.get(subIdent).productIDList;
					String channel = subscriptionOptionsMap.get(subIdent).channel;
					subscriptionMap.remove(subscriptionOptionsMap.get(subIdent).wsMessage);
					subscriptionOptionsMap.remove(subIdent);
					
					try {
						
						if(subscribe(subIdent, list, channel, recycle) == null) {
							return;
						}
						
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					SUB_HOLD = false;
					setName();
				}
			};

		} catch (Exception e) {
			this.logger("EXCEPTION", e.toString(), e);
		}
		return coinbaseSubscription;
	}
	
	// SUBSCRIPTION MESSAGE RECIEVERS
	private void statusMessageRecieved(CoinbaseAPIResponse.Events event, CoinbaseAPIResponse pData) { // STATUS MESSAGE RECIEVED

		if (this.DATA_RECEIPT && !this.QUIET_HEARTBEAT) {
			this.logger("Status", "DATA RECEIPT: PRODUCT STATUSES UPDATED", null);
		}

		for (CoinbaseAPIResponse.Products eachProduct : event.getProducts()) { // ALL PRODUCTS STATUS MESSAGE
			this.addProductMapping(eachProduct.getID(), eachProduct);
		}
	}
	
	private void tickerMessageRecieved(CoinbaseAPIResponse.Events pEvent, CoinbaseAPIResponse pData) throws InterruptedException { // ADV TRADE TICKER MESSAGE RECIEVED
		
		for(CoinbaseAPIResponse.Events.Tickers ticker : pEvent.getTickers()) {
			
			if (this.DATA_RECEIPT && !this.QUIET_HEARTBEAT) {
				this.logger("Status", "DATA RECEIPT: " + ticker.getProduct_id().toUpperCase() + " TICKER: "
						+ ticker.getProduct_id() + " " + ticker.getPrice(), null);
			}
			CoinbaseAPIResponse returnData = pData;
			returnData.setProduct_id(ticker.getProduct_id());
			returnData.setTickerUpdate(true);
			returnData.setHigh_24h(ticker.getHigh_24_h());
			returnData.setLow_24h(ticker.getLow_24_h());
			returnData.setPrice(ticker.getPrice());
			this.addPriceMapEntry(returnData.getProduct_id(), returnData);
			this.rateTracker.addTick(ticker.getProduct_id()	);
		}
	}
		
	// REST REQUESTS
	public CoinbaseAPIResponse getRequest(String endpoint, String pathParams) { // PERFORM REST GET REQUEST

		CoinbaseAPIResponse returnData = null;
		try {
			String[] response = this.commonREST("GET", coinbaseAdvExtendedURL + endpoint, pathParams, "");
			String responseBody = response[0];
			Integer responseCode = Integer.valueOf(response[1]);

			if (responseCode == 200) { // CLEAN RESPONSE RECIEVED
				returnData = jsonDeserializationSingle(responseBody);

			} else { // ERROR RECEIVED

				if (this.restErrorHandler("getRequest", responseCode, responseBody, coinbaseAdvExtendedURL + endpoint + pathParams)) {
					this.logger("RETRYING", "GET REQUEST: " + coinbaseAdvExtendedURL + endpoint + pathParams, null);
					return getRequest(endpoint, pathParams);
				}
			}

		} catch (Exception e) {
			this.logger("EXCEPTION", e.toString(), e);
		}
		
		if(this.nullBLOCKING && returnData == null) {
			return getRequest(endpoint, pathParams);
		}
		return returnData;
	}
	
	public CoinbaseAPIResponse[] getRequestArray(String endpoint, String pathParams) { // PERFORM REST GET REQUEST

		CoinbaseAPIResponse[] returnData = null;

		try {
			String[] response = this.commonREST("GET", coinbaseAdvExtendedURL + endpoint, pathParams, "");
			String responseBody = response[0];
			Integer responseCode = Integer.valueOf(response[1]);

			if (responseCode == 200) { // CLEAN RESPONSE RECIEVED
				returnData = jsonDeserializationArray(responseBody);

			} else { // ERROR RECEIVED

				if (this.restErrorHandler("getRequestArray", responseCode, responseBody, coinbaseAdvExtendedURL + endpoint + pathParams)) {
					this.logger("RETRYING", "GET REQUEST: " + coinbaseAdvExtendedURL + endpoint + pathParams, null);
					return getRequestArray(endpoint, pathParams);
				}
			}

		} catch (Exception e) {
			this.logger("EXCEPTION", e.toString(), e);
		}
		
		if(this.nullBLOCKING && returnData == null) {
			return getRequestArray(endpoint, pathParams);
		}
		return returnData;
	}

	public CoinbaseAPIResponse postRequest(String endpoint, String requestBody) { // PERFORM REST POST REQUEST

		CoinbaseAPIResponse returnData = null;

		try {
			String[] response = this.commonREST("POST", coinbaseAdvExtendedURL + endpoint, "", requestBody);
			String responseBody = response[0];
			Integer responseCode = Integer.valueOf(response[1]);

			if (responseCode == 200) { // CLEAN RESPONSE RECIEVED
				returnData = jsonDeserializationSingle(responseBody);

			} else { // ERROR RECEIVED

				if (this.restErrorHandler("postRequest", responseCode, responseBody, coinbaseAdvExtendedURL + endpoint + requestBody)) {
					this.logger("RETRYING", "POST REQUEST: " + coinbaseAdvExtendedURL + endpoint + requestBody, null);
					return postRequest(endpoint, requestBody);
				}
			}

		} catch (Exception e) {
			this.logger("EXCEPTION", e.toString(), e);
		}
		
		if(this.nullBLOCKING && returnData == null) {
			return postRequest(endpoint, requestBody);
		}
		return returnData;
	}
	
	public CoinbaseAPIResponse[] postRequestArray(String endpoint, String requestBody) { // PERFORM REST POST REQUEST

		CoinbaseAPIResponse[] returnData = null;

		try {
			String[] response = this.commonREST("POST", coinbaseAdvExtendedURL + endpoint, "", requestBody);
			String responseBody = response[0];
			Integer responseCode = Integer.valueOf(response[1]);

			if (responseCode == 200) { // CLEAN RESPONSE RECIEVED
				returnData = jsonDeserializationArray(responseBody);

			} else { // ERROR RECEIVED

				if (this.restErrorHandler("postRequestArray", responseCode, responseBody, coinbaseAdvExtendedURL + endpoint + requestBody)) {
					this.logger("RETRYING", "POST REQUEST: " + coinbaseAdvExtendedURL + endpoint + requestBody, null);
					return postRequestArray(endpoint, requestBody);
				}
			}

		} catch (Exception e) {
			this.logger("EXCEPTION", e.toString(), e);
		}
		
		if(this.nullBLOCKING && returnData == null) {
			return postRequestArray(endpoint, requestBody);
		}
		return returnData;
	}
	
	public String deleteRequest(String endpoint, String pathParams) { // PERFORM REST DELETE REQUEST

		String returnData = null;

		try {
			String[] response = this.commonREST("DELETE", coinbaseAdvExtendedURL + endpoint, pathParams, "");
			String responseBody = response[0];
			Integer responseCode = Integer.valueOf(response[1]);

			if (responseCode == 200) { // CLEAN RESPONSE RECIEVED
				returnData = responseBody;
				;
			} else { // ERROR RECEIVED

				if (this.restErrorHandler("deleteRequest", responseCode, responseBody, coinbaseAdvExtendedURL + endpoint + pathParams)) {
					this.logger("RETRYING", "DELETE REQUEST: " + coinbaseAdvExtendedURL + endpoint + pathParams, null);
					return deleteRequest(endpoint, pathParams);
				}
			}

		} catch (Exception e) {
			this.logger("EXCEPTION", e.toString(), e);
		}
		
		if(this.nullBLOCKING && returnData == null) {
			return deleteRequest(endpoint, pathParams);
		}
		return returnData;
	}
	
	public CoinbaseAPIResponse putRequest(String endpoint, String pathParams, String requestBody) { // PERFORM REST POST REQUEST

		CoinbaseAPIResponse returnData = null;

		try {
			String[] response = this.commonREST("PUT", coinbaseAdvExtendedURL + endpoint, pathParams, requestBody);
			String responseBody = response[0];
			Integer responseCode = Integer.valueOf(response[1]);

			if (responseCode == 200) { // CLEAN RESPONSE RECIEVED
				returnData = jsonDeserializationSingle(responseBody);

			} else { // ERROR RECEIVED
				if (this.restErrorHandler("putRequest", responseCode, responseBody,
						coinbaseAdvExtendedURL + endpoint + pathParams + "{" + requestBody + "}")) {
					this.logger("RETRYING", "PUT REQUEST: " + coinbaseAdvExtendedURL + endpoint + pathParams + "{" + requestBody + "}", null);
					return putRequest(endpoint, pathParams, requestBody);
				}
			}

		} catch (Exception e) {
			this.logger("EXCEPTION", e.toString(), e);
		}
		
		if(this.nullBLOCKING && returnData == null) {
			return putRequest(endpoint, pathParams, requestBody);
		}
		return returnData;
	}
	
	public CoinbaseAPIResponse[] putRequestArray(String endpoint, String pathParams, String requestBody) { // PERFORM REST POST REQUEST

		CoinbaseAPIResponse[] returnData = null;

		try {
			String[] response = this.commonREST("PUT", coinbaseAdvExtendedURL + endpoint, pathParams, requestBody);
			String responseBody = response[0];
			Integer responseCode = Integer.valueOf(response[1]);

			if (responseCode == 200) { // CLEAN RESPONSE RECIEVED
				returnData = jsonDeserializationArray(responseBody);

			} else { // ERROR RECEIVED
				
				if (this.restErrorHandler("putRequestArray", responseCode, responseBody,
						coinbaseAdvExtendedURL + endpoint + pathParams + "{" + requestBody + "}")) {
					this.logger("RETRYING", "PUT REQUEST: " + coinbaseAdvExtendedURL + endpoint + pathParams + "{" + requestBody + "}", null);
					return putRequestArray(endpoint, pathParams, requestBody);
				}
			}

		} catch (Exception e) {
			this.logger("EXCEPTION", e.toString(), e);
		}
		
		if(this.nullBLOCKING && returnData == null) {
			return putRequestArray(endpoint, pathParams, requestBody);
		}
		return returnData;
	}
	
	private String[] commonREST(String method, String endpoint, String pathParams, String requestBody) throws InterruptedException { // COMMON REST REQUEST IMPLIMENTATION

		String[] response = new String[3];
		
		try {
			HttpsURLConnection connection = (HttpsURLConnection) new URL(
					String.join("", coinbaseProBaseURL, endpoint + pathParams)).openConnection();
			connection.setRequestMethod(method);
			connection.setRequestProperty("Content-Type", "application/json");
			connection.setRequestProperty("Accept", "application/json");

			if (this.coinbaseAPIKey != null) { // SECURITY CREDENTIALS PRESENT
				String timeStamp = Long.toString(Instant.now().getEpochSecond());
				connection.setRequestProperty("CB-ACCESS-KEY", coinbaseAPIKey);
				connection.setRequestProperty("CB-ACCESS-SIGN",
						signMessage(timeStamp, method, endpoint, requestBody));
				connection.setRequestProperty("CB-ACCESS-TIMESTAMP", timeStamp);
			}

			if (method.compareTo("PUT") == 0 || method.compareTo("POST") == 0) { // HANDLE BODY MESSAGE
				connection.setDoOutput(true);
				connection.getOutputStream().write(requestBody.getBytes());
			}
			response[0] = "";
			InputStreamReader responseReader = new InputStreamReader(connection.getInputStream(), "UTF-8");

			for (int nextChar = responseReader.read(); nextChar >= 0; nextChar = responseReader.read()) {
				response[0] = response[0] + (char) nextChar;
			} // READING STREAM ONE CHAR AT A TIME
			response[1] = String.valueOf(connection.getResponseCode());
			response[2] = connection.getResponseMessage();
			connection.disconnect();

		} catch (Exception e) {
			response[0] = "BROKEN PIPE!";
			response[1] = "187";
			response[2] = e.toString();
			
			if(e.toString().contains("HTTP response code: 429")) {
				this.logger("Warning!", "Rate Limit execeded!", null);
				Thread.sleep(RATE_LIMIT_TIMER);
			}
		}
		return response;
	}
	
	private Boolean restErrorHandler(String callingMethod, Integer statusCode, String responseBody, String request) { 

		Boolean messageHandled = false;
		Boolean attemptResend = false;

		if (callingMethod.compareTo("putRequestArray") == 0) {
			this.logger("ERROR", "REST PUT FAILURE: " + statusCode + ": " + responseBody, null);
			messageHandled = true;
		}

		if (callingMethod.compareTo("putRequest") == 0) {
			this.logger("ERROR", "REST PUT FAILURE: " + statusCode + ": " + responseBody, null);
			messageHandled = true;
		}

		if (callingMethod.compareTo("deleteRequest") == 0) {
			this.logger("ERROR", "REST DELETE FAILURE: " + statusCode + ": " + responseBody, null);
			messageHandled = true;
		}

		if (callingMethod.compareTo("postRequestArray") == 0) {
			this.logger("ERROR", "REST POST FAILURE: " + statusCode + ": " + responseBody, null);
			messageHandled = true;
		}

		if (callingMethod.compareTo("postRequest") == 0) {
			this.logger("ERROR", "REST POST FAILURE: " + statusCode + ": " + responseBody, null);
			messageHandled = true;
		}

		if (callingMethod.compareTo("getRequestArray") == 0) {
			this.logger("ERROR", "REST GET FAILURE: " + statusCode + ": " + responseBody, null);
			messageHandled = true;
		}

		if (callingMethod.compareTo("getRequest") == 0) {
			this.logger("ERROR", "REST GET FAILURE: " + statusCode + ": " + responseBody, null);
			messageHandled = true;
		}

		if (!messageHandled) {
			this.logger("ERROR",
					"UNHANDLED MESSAGE: METHOD: " + callingMethod + ": " + statusCode + ": " + responseBody, null);
		}

		if (responseBody.contains("request timestamp expired") || responseBody.contains("502 Bad Gateway") || statusCode == 187) {
			attemptResend = true;
		}
		return attemptResend;
	}
	
	// DATA HANDLING
	private Boolean addProductMapping(String addThisKEY, CoinbaseAPIResponse.Products addThisVALUE) { // ADD TO PRODUCT
																										// MAP

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
			this.priceMap.replace(addThisKEY,
					new CoinbaseAPIResponseComparison(this.priceMap.get(addThisKEY), addThisVALUE).getResult());
		}
		return this.priceMap.containsKey(addThisKEY);
	}
	
	// DATA RETREIVAL
	public Map<String, CoinbaseAPIResponse.Currency> getCurrencyMap() // RETRIEVE CURRENCY MAP
			throws InterruptedException {

		while (this.currencyMap.isEmpty() || !this.isStreaming()) {
			Thread.sleep(60);
		}
		return this.currencyMap;
	}

	public Map<String, CoinbaseAPIResponse.Products> getProductMap() // RETRIEVE PRODUCT MAP
			throws InterruptedException {

		while (this.productMap.isEmpty() || !this.isStreaming()) {
			Thread.sleep(60);
		}
		return this.productMap;
	}
	
	public Map<String, SubscriptionMapper> getSubscriptionMap() // RETRIEVE SUBSCRIPTION MAP
			throws InterruptedException {

		while (this.subscriptionMap.isEmpty() || !this.isStreaming()) {
			Thread.sleep(60);
		}
		return this.subscriptionMap;
	}
	
	public Map<String, CoinbaseAPIResponse> getPriceMap() // RETRIEVE PRICE MAP
			throws InterruptedException {
		
		Integer count = 0;

		while (this.priceMap.isEmpty() || !this.isStreaming()) {
			Thread.sleep(60);
			count++;
			
			if(count > this.RATE_LIMIT_TIMER * 5) {
				return null;
			}
		}
		return this.priceMap;
	}

	public TickerRateTracking getRateData() {
		
		return this.rateTracker;
	}
	
	public Boolean isStreaming() { // DATA STREAMING STATUS

		return this.IS_STREAMING;
	}
	
	public void setBlocking(Boolean passVar) {
		
		this.nullBLOCKING = passVar;
	}
	
	// ENCRYPTION
	private String subscriptionAuth(String pMessage, String[] productIDList, String channel) { // SUBSCRIPTION SIGNATURE
																								// BUIDER
		String jsonMessageFinal = pMessage;

		try {
			String timeStamp = Long.toString(Instant.now().getEpochSecond());
			jsonMessageFinal = String.join("", jsonMessageFinal, ",\"api_key\":\"", coinbaseAPIKey,
					"\",\"timestamp\":\"", timeStamp, "\",\"signature\":\"",
					this.hash(String.join("", timeStamp, channel, String.join(",", productIDList)),
							coinbaseSecretKey), "\"");

		} catch (Exception e) {
			this.logger("EXCEPTION", e.toString(), e);
		}
		return jsonMessageFinal;
	}
	
	private String signMessage(String timestamp, String method, String path, String body) // ENCRYPTED HASH SECURITY
																							// SIGNATURE BUILDER
			throws InvalidKeyException, NoSuchAlgorithmException, IllegalStateException, UnsupportedEncodingException {

		String prehash = timestamp + method.toUpperCase() + path + body;
		return hash(prehash, this.coinbaseSecretKey);
	}
	
	private String hash(String prehash, String key) throws NoSuchAlgorithmException, InvalidKeyException, IllegalStateException, UnsupportedEncodingException {

		Mac sha256_HMAC = Mac.getInstance("HmacSHA256");
		sha256_HMAC.init(new SecretKeySpec(key.getBytes("UTF-8"), sha256_HMAC.getAlgorithm()));
		byte[] hash = sha256_HMAC.doFinal(prehash.getBytes());
		StringBuilder hexString = new StringBuilder();
		
		for (byte aHash : hash) {
			String hex = Integer.toHexString(0xff & aHash);
			
			if (hex.length() == 1) {
				hexString.append('0');
			}
			hexString.append(hex);
		}
		return hexString.toString();
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

		if (DESERIALIZATION_IGNORE) {
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

		if (DESERIALIZATION_IGNORE) {
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
		
		if(!this.errLOGGER && (reason.compareTo("ERROR") == 0 || reason.compareTo("RETRYING") == 0)) {
			return;
		}
		Boolean cont = true;

		if (e != null) {

			if (e.toString().contains("FileNotFound")) {
				cont = false;
			}
		}

		if (LOGGER_LEVEL == true && cont == true) {
			printTime.now(0);
			System.out.println(reason + ": " + message);

			if (e != null) {
				e.printStackTrace();
			}
		}
	}
	
	public void enableErrMsgs() { // ENABLE CONSOLE ERROR LOGGING
		
		this.errLOGGER = true;
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
	
	public void enableDeserializationIgnoreExeceptions() { // DISABLE JACKSON DATABIND DESERIALIZATION EXCEPTIONS
															// FAILURE

		this.DESERIALIZATION_IGNORE = true;
	}
	
	public void disableDeserializationIgnoreExeceptions() { // ENABLE JACKSON DATABIND DESERIALIZATION EXCEPTIONS
															// FAILURE

		this.DESERIALIZATION_IGNORE = false;
	}
	
	public void enablePulseCheck() { // ENABLE HEARTBEAT DATA RECEIPT MESSAGES

		this.QUIET_HEARTBEAT = false;
	}
	
	public void disablePulseCheck() { // DISABLE HEARTBEAT DATA RECEIPT MESSAGES

		this.QUIET_HEARTBEAT = true;
	}
	
	// ANDROIID COMPATIBILITY TIMER
	public void enableAndroid() {

		this.ANDROIID_COMPAT = true;
	}
	
	public void disableAndroid() {

		this.ANDROIID_COMPAT = false;
	}
}

// OLD COINBASEPRO THROWBACK FUNCTIONS

/*private Boolean addOrderBookEntry(String addThisKEY, CoinbaseAPIResponse addThisVALUE) { // ADD ORDER BOOK ENTRY

	if (!this.orderBook.containsKey(addThisKEY)) { // NO ENTRY DETECTED
		this.orderBook.put(addThisKEY, addThisVALUE);

	} else {
		this.orderBook.replace(addThisKEY,
				new CoinbaseAPIResponseComparison(this.orderBook.get(addThisKEY), addThisVALUE).getResult());
	}
	return this.orderBook.containsKey(addThisKEY);
}

private Boolean removeOrderBookEntry(String KEY) { // REMOVE ORDER BOOK ENTRY

	if (this.orderBook.containsKey(KEY)) {
		this.orderBook.remove(KEY);
	}
	return !this.orderBook.containsKey(KEY);
}*/


/* COINBASE PRO THROWBACKS
private void tickerMessageRecieved(CoinbaseAPIResponse pData) { // TICKER MESSAGE RECIEVED

	if (this.DATA_RECEIPT && !this.QUIET_HEARTBEAT) {
		this.logger("Status", "DATA RECEIPT: " + pData.getProduct_id().toUpperCase() + " TICKER: "
				+ pData.getProduct_id() + " " + pData.getPrice(), null);
	}
	pData.setTickerUpdate(true);
	this.addPriceMapEntry(pData.getProduct_id(), pData);
}

private void statusMessageRecieved(CoinbaseAPIResponse pData) { // STATUS MESSAGE RECIEVED

	if (this.DATA_RECEIPT && !this.QUIET_HEARTBEAT) {
		this.logger("Status", "DATA RECEIPT: PRODUCT STATUSES UPDATED", null);
	}

	for (CoinbaseAPIResponse.Currency eachCurrency : pData.getCurrencies()) { // INDIVIDUAL CURRENCY DATA
		this.addCurrencyMapping(eachCurrency.getID(), eachCurrency);
	}

	for (CoinbaseAPIResponse.Products eachProduct : pData.getProducts()) { // ALL PRODUCTS STATUS MESSAGE
		this.addProductMapping(eachProduct.getID(), eachProduct);
	}
}

private void l2updateMessageRecieved(CoinbaseAPIResponse pData) { // L2UPDATE MESSAGE RECIEVED

	if (this.DATA_RECEIPT && !this.QUIET_HEARTBEAT) {
		this.logger("Status", "DATA RECEIPT: " + pData.getProduct_id().toUpperCase() + " L2UPDATE: "
				+ pData.getProduct_id() + " " + pData.getChanges()[0][1], null);
	}
	this.addPriceMapEntry(pData.getProduct_id(), pData);
}

private void snapshotMessageRecieved(CoinbaseAPIResponse pData) { // SNAPSHOT MESSAGE RECIEVED

	if (this.DATA_RECEIPT) {
		this.logger("Status", "DATA RECEIPT: " + pData.getProduct_id().toUpperCase() + " SNAPSHOT", null);
	}
	this.addPriceMapEntry(pData.getProduct_id(), pData);
}

private void heartbeatMessageRecieved(CoinbaseAPIResponse pData) { // HEARTBEAT MESSAGE RECIEVED

	if (this.DATA_RECEIPT && !this.QUIET_HEARTBEAT) {
		this.logger("Status", "DATA RECEIPT: " + pData.getProduct_id().toUpperCase() + " HEARTBEAT", null);
	}
	this.addPriceMapEntry(pData.getProduct_id(), pData);
}

private void openMessageRecieved(CoinbaseAPIResponse pData) { // OPEN MESSAGE RECIEVED

	if (this.DATA_RECEIPT) {
		this.logger("Status", "DATA RECEIPT: ORDER " + pData.getOrder_id() + " OPEN", null);
	}
	this.addOrderBookEntry(pData.getOrder_id(), pData);
}

private void receivedMessageRecieved(CoinbaseAPIResponse pData) { // RECEIVED MESSAGE RECIEVED

	if (this.DATA_RECEIPT) {
		this.logger("Status", "DATA RECEIPT: ORDER " + pData.getOrder_id() + " RECEIVED", null);
	}
	this.addOrderBookEntry(pData.getOrder_id(), pData);
}

private void doneMessageRecieved(CoinbaseAPIResponse pData) { // DONE MESSAGE RECIEVED

	if (this.DATA_RECEIPT) {
		this.logger("Status", "DATA RECEIPT: ORDER " + pData.getOrder_id() + " DONE", null);
	}
	this.removeOrderBookEntry(pData.getOrder_id());
}

private void matchMessageRecieved(CoinbaseAPIResponse pData) { // MATCH MESSAGE RECIEVED

	if (this.DATA_RECEIPT) {
		this.logger("Status",
				"DATA RECEIPT: ORDER " + pData.getMaker_order_id() + " - " + pData.getTaker_order_id() + " MATCH",
				null);
	}
	this.priceMap.get(pData.getProduct_id()).setPrice(pData.getPrice());
	this.priceMap.get(pData.getProduct_id()).setSequence(pData.getSequence());
	this.priceMap.get(pData.getProduct_id()).setSide(pData.getSide());
	this.addOrderBookEntry(pData.getMaker_order_id(), pData);
	this.addOrderBookEntry(pData.getTaker_order_id(), pData);
}

private void last_matchMessageRecieved(CoinbaseAPIResponse pData) { // LAST MATCH MESSAGE RECIEVED

	if (this.DATA_RECEIPT) {
		this.logger("Status", "DATA RECEIPT: ORDER " + pData.getMaker_order_id() + " - " + pData.getTaker_order_id()
				+ " LAST MATCH", null);
	}
	this.priceMap.get(pData.getProduct_id()).setPrice(pData.getPrice());
	this.priceMap.get(pData.getProduct_id()).setSequence(pData.getSequence());
	this.priceMap.get(pData.getProduct_id()).setSide(pData.getSide());
	this.addOrderBookEntry(pData.getMaker_order_id(), pData);
	this.addOrderBookEntry(pData.getTaker_order_id(), pData);
}

private void subscriptionsMessageRecieved(CoinbaseAPIResponse pData) { // SUBSCRIPTIONS MESSAGE RECIEVED

	if (this.DATA_RECEIPT) {

		for (CoinbaseAPIResponse.Channels eachChannel : pData.getChannels()) {

			for (String eachProduct : eachChannel.getProduct_ids()) {
				this.logger("Status", "DATA RECEIPT: " + eachProduct + " " + eachChannel.getName().toUpperCase()
						+ " SUBSCRIPTION STARTED", null);
			}
		}
	}

	// RETRIEVE ORDER BOOK FOR ANY SUBSCRIPTIONS RETURNING ORDER RELATED DATA
	for (CoinbaseAPIResponse.Channels eachChannel : pData.getChannels()) {

		for (String eachProduct : eachChannel.getProduct_ids()) {

			if (eachChannel.getName().compareTo("matches") == 0 || eachChannel.getName().compareTo("full") == 0
					|| eachChannel.getName().compareTo("user") == 0) {

				if (!this.orderLegend.containsKey(eachProduct)) { // NO ENTRY DETECTED

					if (this.DATA_RECEIPT) {
						this.logger("Status", "DOWNLOADING ORDERS: " + eachProduct, null);
					}
					CoinbaseAPIResponse response = this.getRequest("/products",
							String.join("", "/", eachProduct, "/book?level=3"));

					for (String[] eachOrder : response.getAsks()) {
						CoinbaseAPIResponse entry = new CoinbaseAPIResponse();
						entry.setPrice(Double.valueOf(eachOrder[0]));
						entry.setSize(Double.valueOf(eachOrder[1]));
						entry.setSide("sell");
						this.addOrderBookEntry(eachOrder[2], entry);
					}

					for (String[] eachOrder : response.getBids()) {
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
}*/
// DATA HANDLING
/*private Boolean addCurrencyMapping(String addThisKEY, CoinbaseAPIResponse.Currency addThisVALUE) { // ADD TO
																									// CURRENCY MAP

	if (this.currencyMap.containsKey(addThisKEY)) { // ENTRY DETECTED
		this.currencyMap.replace(addThisKEY, addThisVALUE);

	} else {
		this.currencyMap.put(addThisKEY, addThisVALUE);
	}
	return this.currencyMap.containsKey(addThisKEY);
}*/
/*public Map<String, CoinbaseAPIResponse> getOrderBook() // RETRIEVE ORDER BOOK
		throws InterruptedException {

	while (this.orderBook.isEmpty() || !this.isStreaming()) {
		Thread.sleep(60);
	}
	return this.orderBook;
}*/

