package com.Zeus.CoinbaseProClientAPI;

public class CoinbaseAPIResponseComparison {

	private CoinbaseAPIResponse origin;
	private CoinbaseAPIResponse comparator;
	private CoinbaseAPIResponse result;

	public CoinbaseAPIResponseComparison(CoinbaseAPIResponse pOrigin, CoinbaseAPIResponse pComparator) {

		origin = pOrigin;
		comparator = pComparator;
		result = new CoinbaseAPIResponse();
		compare();
	}

	public CoinbaseAPIResponse getResult() {
		return result;
	}

	private void compare() { // COMPARISON OF ALL THESE GD VARIABLES

		if (comparator.getFile_count() != null) {
			result.setFile_count(comparator.getFile_count());
		
		} else {
			result.setFile_count(origin.getFile_count());
		}

		if (comparator.getLimit_currency() != null) {
			result.setLimit_currency(comparator.getLimit_currency());
		
		} else {
			result.setLimit_currency(origin.getLimit_currency());
		}

		if (comparator.getTransfer_limits() != null) {
			result.setTransfer_limits(comparator.getTransfer_limits());
		
		} else {
			result.setTransfer_limits(origin.getTransfer_limits());
		}

		if (comparator.getParams() != null) {
			result.setParams(comparator.getParams());
		
		} else {
			result.setParams(origin.getParams());
		}

		if (comparator.getExpires_at() != null) {
			result.setExpires_at(comparator.getExpires_at());
		
		} else {
			result.setExpires_at(origin.getExpires_at());
		}

		if (comparator.getFile_url() != null) {
			result.setFile_url(comparator.getFile_url());
		
		} else {
			result.setFile_url(origin.getFile_url());
		}

		if (comparator.getCompleted_at() != null) {
			result.setCompleted_at(comparator.getCompleted_at());
		
		} else {
			result.setCompleted_at(origin.getCompleted_at());
		}

		if (comparator.getHas_margin() != null) {
			result.setHas_margin(comparator.getHas_margin());
		
		} else {
			result.setHas_margin(origin.getHas_margin());
		}
		
		if (comparator.getIs_default() != null) {
			result.setIs_default(comparator.getIs_default());
		
		} else {
			result.setIs_default(origin.getIs_default());
		}

		if (comparator.getActive() != null) {
			result.setActive(comparator.getActive());
		
		} else {
			result.setActive(origin.getActive());
		}
		
		if (comparator.getAddress() != null) {
			result.setAddress(comparator.getAddress());
		
		} else {
			result.setAddress(origin.getAddress());
		}
		
		if (comparator.getAddress_info() != null) {
			result.setAddress_info(comparator.getAddress_info());
		
		} else {
			result.setAddress_info(origin.getAddress_info());
		}
		
		if (comparator.getAllow_buy() != null) {
			result.setAllow_buy(comparator.getAllow_buy());
		
		} else {
			result.setAllow_buy(origin.getAllow_buy());
		}
		
		if (comparator.getAllow_deposit() != null) {
			result.setAllow_deposit(comparator.getAllow_deposit());
		
		} else {
			result.setAllow_deposit(origin.getAllow_deposit());
		}
		
		if (comparator.getAllow_sell() != null) {
			result.setAllow_sell(comparator.getAllow_sell());
		
		} else {
			result.setAllow_sell(origin.getAllow_sell());
		}
		
		if (comparator.getAllow_withdraw() != null) {
			result.setAllow_withdraw(comparator.getAllow_withdraw());
		
		} else {
			result.setAllow_withdraw(origin.getAllow_withdraw());
		}
		
		if (comparator.getAmount() != null) {
			result.setAmount(comparator.getAmount());
		
		} else {
			result.setAmount(origin.getAmount());
		}
		
		if (comparator.getAsks() != null) {
			result.setAsks(comparator.getAsks());
		
		} else {
			result.setAsks(origin.getAsks());
		}
		
		if (comparator.getAvailable() != null) {
			result.setAvailable(comparator.getAvailable());
		
		} else {
			result.setAvailable(origin.getAvailable());
		}
		
		if (comparator.getAvailable_balance() != null) {
			result.setAvailable_balance(comparator.getAvailable_balance());
		
		} else {
			result.setAvailable_balance(origin.getAvailable_balance());
		}
		
		if (comparator.getAvailable_on_consumer() != null) {
			result.setAvailable_on_consumer(comparator.getAvailable_on_consumer());
		
		} else {
			result.setAvailable_on_consumer(origin.getAvailable_on_consumer());
		}
		
		if (comparator.getBalance() != null) {
			result.setBalance(comparator.getBalance());
		
		} else {
			result.setBalance(origin.getBalance());
		}
		
		if (comparator.getBest_ask() != null) {
			result.setBest_ask(comparator.getBest_ask());
		
		} else {
			result.setBest_ask(origin.getBest_ask());
		}
		
		if (comparator.getBest_bid() != null) {
			result.setBest_bid(comparator.getBest_bid());
		
		} else {
			result.setBest_bid(origin.getBest_bid());
		}
		
		if (comparator.getBids() != null) {
			result.setBids(comparator.getBids());
		
		} else {
			result.setBids(origin.getBids());
		}
		
		if (comparator.getCallback_url() != null) {
			result.setCallback_url(comparator.getCallback_url());
		
		} else {
			result.setCallback_url(origin.getCallback_url());
		}
		
		if (comparator.getChanges() != null) {
			result.setChanges(comparator.getChanges());
		
		} else {
			result.setChanges(origin.getChanges());
		}
		
		if (comparator.getChannels() != null) {
			result.setChannels(comparator.getChannels());
		
		} else {
			result.setChannels(origin.getChannels());
		}
		
		if (comparator.getClient_oid() != null) {
			result.setClient_oid(comparator.getClient_oid());
		
		} else {
			result.setClient_oid(origin.getClient_oid());
		}
		
		if (comparator.getConvertible_to() != null) {
			result.setConvertible_to(comparator.getConvertible_to());
		
		} else {
			result.setConvertible_to(origin.getConvertible_to());
		}
		
		if (comparator.getCreated_at() != null) {
			result.setCreated_at(comparator.getCreated_at());
		
		} else {
			result.setCreated_at(origin.getCreated_at());
		}
		
		if (comparator.getCrypto_account() != null) {
			result.setCrypto_account(comparator.getCrypto_account());
		
		} else {
			result.setCrypto_account(origin.getCrypto_account());
		}
		
		if (comparator.getCurrencies() != null) {
			result.setCurrencies(comparator.getCurrencies());
		
		} else {
			result.setCurrencies(origin.getCurrencies());
		}
		
		if (comparator.getData() != null) {
			result.setData(comparator.getData());
		
		} else {
			result.setData(origin.getData());
		}
		
		if (comparator.getDeposit_uri() != null) {
			result.setDeposit_uri(comparator.getDeposit_uri());
		
		} else {
			result.setDeposit_uri(origin.getDeposit_uri());
		}
		
		if (comparator.getDestination_tag() != null) {
			result.setDestination_tag(comparator.getDestination_tag());
		
		} else {
			result.setDestination_tag(origin.getDestination_tag());
		}
		
		if (comparator.getDestination_tag_name() != null) {
			result.setDestination_tag_name(comparator.getDestination_tag_name());
		
		} else {
			result.setDestination_tag_name(origin.getDestination_tag_name());
		}
		
		if (comparator.getDestination_tag_regex() != null) {
			result.setDestination_tag_regex(comparator.getDestination_tag_regex());
		
		} else {
			result.setDestination_tag_regex(origin.getDestination_tag_regex());
		}
		
		if (comparator.getDetails() != null) {
			result.setDetails(comparator.getDetails());
		
		} else {
			result.setDetails(origin.getDetails());
		}
		
		if (comparator.getDone_at() != null) {
			result.setDone_at(comparator.getDone_at());
		
		} else {
			result.setDone_at(origin.getDone_at());
		}
		
		if (comparator.getDone_reason() != null) {
			result.setDone_reason(comparator.getDone_reason());
		
		} else {
			result.setDone_reason(origin.getDone_reason());
		}
		
		if (comparator.getExecuted_value() != null) {
			result.setExecuted_value(comparator.getExecuted_value());
		
		} else {
			result.setExecuted_value(origin.getExecuted_value());
		}
		
		if (comparator.getExpire_time() != null) {
			result.setExpire_time(comparator.getExpire_time());
		
		} else {
			result.setExpire_time(origin.getExpire_time());
		}
		
		if (comparator.getFee() != null) {
			result.setFee(comparator.getFee());
		
		} else {
			result.setFee(origin.getFee());
		}
		
		if (comparator.getFiat_account() != null) {
			result.setFiat_account(comparator.getFiat_account());
		
		} else {
			result.setFiat_account(origin.getFiat_account());
		}
		
		if (comparator.getFill_fees() != null) {
			result.setFill_fees(comparator.getFill_fees());
		
		} else {
			result.setFill_fees(origin.getFill_fees());
		}
		
		if (comparator.getFilled_size() != null) {
			result.setFilled_size(comparator.getFilled_size());
		
		} else {
			result.setFilled_size(origin.getFilled_size());
		}
		
		if (comparator.getFrom() != null) {
			result.setFrom(comparator.getFrom());
		
		} else {
			result.setFrom(origin.getFrom());
		}
		
		if (comparator.getFrom_account_id() != null) {
			result.setFrom_account_id(comparator.getFrom_account_id());
		
		} else {
			result.setFrom_account_id(origin.getFrom_account_id());
		}
		
		if (comparator.getFunding_account_id() != null) {
			result.setFunding_account_id(comparator.getFunding_account_id());
		
		} else {
			result.setFunding_account_id(origin.getFunding_account_id());
		}
		
		if (comparator.getFunding_amount() != null) {
			result.setFunding_amount(comparator.getFunding_amount());
		
		} else {
			result.setFunding_amount(origin.getFunding_amount());
		}
		
		if (comparator.getFunds() != null) {
			result.setFunds(comparator.getFunds());
		
		} else {
			result.setFunds(origin.getFunds());
		}
		
		if (comparator.getAuction() != null) {
			result.setAuction(comparator.getAuction());
		
		} else {
			result.setAuction(origin.getAuction());
		}
		
		if (comparator.getAuction_mode() != null) {
			result.setAuction_mode(comparator.getAuction_mode());
		
		} else {
			result.setAuction_mode(origin.getAuction_mode());
		}
		
		if (comparator.getHigh_24h() != null) {
			result.setHigh_24h(comparator.getHigh_24h());
		
		} else {
			result.setHigh_24h(origin.getHigh_24h());
		}
		
		if (comparator.getHold() != null) {
			result.setHold(comparator.getHold());
		
		} else {
			result.setHold(origin.getHold());
		}
		
		if (comparator.getHold_balance() != null) {
			result.setHold_balance(comparator.getHold_balance());
		
		} else {
			result.setHold_balance(origin.getHold_balance());
		}
		
		if (comparator.getHold_currency() != null) {
			result.setHold_currency(comparator.getHold_currency());
		
		} else {
			result.setHold_currency(origin.getHold_currency());
		}
		
		if (comparator.getID() != null) {
			result.setID(comparator.getID());
		
		} else {
			result.setID(origin.getID());
		}
		
		if (comparator.getInstant_buy() != null) {
			result.setInstant_buy(comparator.getInstant_buy());
		
		} else {
			result.setInstant_buy(origin.getInstant_buy());
		}
		
		if (comparator.getInstant_sell() != null) {
			result.setInstant_sell(comparator.getInstant_sell());
		
		} else {
			result.setInstant_sell(origin.getInstant_sell());
		}
		
		if (comparator.getLast_size() != null) {
			result.setLast_size(comparator.getLast_size());
		
		} else {
			result.setLast_size(origin.getLast_size());
		}
		
		if (comparator.getLast_trade_id() != null) {
			result.setLast_trade_id(comparator.getLast_trade_id());
		
		} else {
			result.setLast_trade_id(origin.getLast_trade_id());
		}
		
		if (comparator.getLegacy_address() != null) {
			result.setLegacy_address(comparator.getLegacy_address());
		
		} else {
			result.setLegacy_address(origin.getLegacy_address());
		}
		
		if (comparator.getLimits() != null) {
			result.setLimits(comparator.getLimits());
		
		} else {
			result.setLimits(origin.getLimits());
		}
		
		if (comparator.getLiquidity() != null) {
			result.setLiquidity(comparator.getLiquidity());
		
		} else {
			result.setLiquidity(origin.getLiquidity());
		}
		
		if (comparator.getLow_24h() != null) {
			result.setLow_24h(comparator.getLow_24h());
		
		} else {
			result.setLow_24h(origin.getLow_24h());
		}
		
		if (comparator.getMaker_fee_rate() != null) {
			result.setMaker_fee_rate(comparator.getMaker_fee_rate());
		
		} else {
			result.setMaker_fee_rate(origin.getMaker_fee_rate());
		}
		
		if (comparator.getMaker_order_id() != null) {
			result.setMaker_order_id(comparator.getMaker_order_id());
		
		} else {
			result.setMaker_order_id(origin.getMaker_order_id());
		}
		
		if (comparator.getMaker_profile_id() != null) {
			result.setMaker_profile_id(comparator.getMaker_profile_id());
		
		} else {
			result.setMaker_profile_id(origin.getMaker_profile_id());
		}
		
		if (comparator.getMaker_user_id() != null) {
			result.setMaker_user_id(comparator.getMaker_user_id());
		
		} else {
			result.setMaker_user_id(origin.getMaker_user_id());
		}
		
		if (comparator.getMax_precision() != null) {
			result.setMax_precision(comparator.getMax_precision());
		
		} else {
			result.setMax_precision(origin.getMax_precision());
		}
		
		if (comparator.getMessage() != null) {
			result.setMessage(comparator.getMessage());
		
		} else {
			result.setMessage(origin.getMessage());
		}
		
		if (comparator.getMessages() != null) {
			result.setMessages(comparator.getMessages());
		
		} else {
			result.setMessages(origin.getMessages());
		}
		
		if (comparator.getMin_size() != null) {
			result.setMin_size(comparator.getMin_size());
		
		} else {
			result.setMin_size(origin.getMin_size());
		}
		
		if (comparator.getName() != null) {
			result.setName(comparator.getName());
		
		} else {
			result.setName(origin.getName());
		}
		
		if (comparator.getNetwork() != null) {
			result.setNetwork(comparator.getNetwork());
		
		} else {
			result.setNetwork(origin.getNetwork());
		}
		
		if (comparator.getOpen_24h() != null) {
			result.setOpen_24h(comparator.getOpen_24h());
		
		} else {
			result.setOpen_24h(origin.getOpen_24h());
		}
		
		if (comparator.getOrder_id() != null) {
			result.setOrder_id(comparator.getOrder_id());
		
		} else {
			result.setOrder_id(origin.getOrder_id());
		}
		
		if (comparator.getOrder_type() != null) {
			result.setOrder_type(comparator.getOrder_type());
		
		} else {
			result.setOrder_type(origin.getOrder_type());
		}
		
		if (comparator.getPayout_at() != null) {
			result.setPayout_at(comparator.getPayout_at());
		
		} else {
			result.setPayout_at(origin.getPayout_at());
		}
		
		if (comparator.getPicker_data() != null) {
			result.setPicker_data(comparator.getPicker_data());
		
		} else {
			result.setPicker_data(origin.getPicker_data());
		}
		
		if (comparator.getPost_only() != null) {
			result.setPost_only(comparator.getPost_only());
		
		} else {
			result.setPost_only(origin.getPost_only());
		}
		
		if (comparator.getPrice() != null) {
			result.setPrice(comparator.getPrice());
		
		} else {
			result.setPrice(origin.getPrice());
		}
		
		if (comparator.getPrices() != null) {
			result.setPrices(comparator.getPrices());
		
		} else {
			result.setPrices(origin.getPrices());
		}
		
		if (comparator.getPrimary() != null) {
			result.setPrimary(comparator.getPrimary());
		
		} else {
			result.setPrimary(origin.getPrimary());
		}
		
		if (comparator.getProduct_id() != null) {
			result.setProduct_id(comparator.getProduct_id());
		
		} else {
			result.setProduct_id(origin.getProduct_id());
		}
		
		if (comparator.getProducts() != null) {
			result.setProducts(comparator.getProducts());
		
		} else {
			result.setProducts(origin.getProducts());
		}
		
		if (comparator.getProfile_id() != null) {
			result.setProfile_id(comparator.getProfile_id());
		
		} else {
			result.setProfile_id(origin.getProfile_id());
		}
		
		if (comparator.getReady() != null) {
			result.setReady(comparator.getReady());
		
		} else {
			result.setReady(origin.getReady());
		}
		
		if (comparator.getReason() != null) {
			result.setReason(comparator.getReason());
		
		} else {
			result.setReason(origin.getReason());
		}
		
		if (comparator.getRecurring_options() != null) {
			result.setRecurring_options(comparator.getRecurring_options());
		
		} else {
			result.setRecurring_options(origin.getRecurring_options());
		}
		
		if (comparator.getReject_reason() != null) {
			result.setReject_reason(comparator.getReject_reason());
		
		} else {
			result.setReject_reason(origin.getReject_reason());
		}
		
		if (comparator.getRemaining_size() != null) {
			result.setRemaining_size(comparator.getRemaining_size());
		
		} else {
			result.setRemaining_size(origin.getRemaining_size());
		}
		
		if (comparator.getResource() != null) {
			result.setResource(comparator.getResource());
		
		} else {
			result.setResource(origin.getResource());
		}
		
		if (comparator.getResource_path() != null) {
			result.setResource_path(comparator.getResource_path());
		
		} else {
			result.setResource_path(origin.getResource_path());
		}
		
		if (comparator.getSepa_deposit_information() != null) {
			result.setSepa_deposit_information(comparator.getSepa_deposit_information());
		
		} else {
			result.setSepa_deposit_information(origin.getSepa_deposit_information());
		}
		
		if (comparator.getSequence() != null) {
			result.setSequence(comparator.getSequence());
		
		} else {
			result.setSequence(origin.getSequence());
		}
		
		if (comparator.getSettled() != null) {
			result.setSettled(comparator.getSettled());
		
		} else {
			result.setSettled(origin.getSettled());
		}
		
		if (comparator.getSide() != null) {
			result.setSide(comparator.getSide());
		
		} else {
			result.setSide(origin.getSide());
		}
		
		if (comparator.getSignatures() != null) {
			result.setSignatures(comparator.getSignatures());
		
		} else {
			result.setSignatures(origin.getSignatures());
		}
		
		if (comparator.getSize() != null) {
			result.setSize(comparator.getSize());
		
		} else {
			result.setSize(origin.getSize());
		}
		
		if (comparator.getSpecified_funds() != null) {
			result.setSpecified_funds(comparator.getSpecified_funds());
		
		} else {
			result.setSpecified_funds(origin.getSpecified_funds());
		}
		
		if (comparator.getStatus() != null) {
			result.setStatus(comparator.getStatus());
		
		} else {
			result.setStatus(origin.getStatus());
		}
		
		if (comparator.getStatus_message() != null) {
			result.setStatus_message(comparator.getStatus_message());
		
		} else {
			result.setStatus_message(origin.getStatus_message());
		}
		
		if (comparator.getStop() != null) {
			result.setStop(comparator.getStop());
		
		} else {
			result.setStop(origin.getStop());
		}
		
		if (comparator.getStp() != null) {
			result.setStp(comparator.getStp());
		
		} else {
			result.setStp(origin.getStp());
		}
		
		if (comparator.getSubtotal() != null) {
			result.setSubtotal(comparator.getSubtotal());
		
		} else {
			result.setSubtotal(origin.getSubtotal());
		}
		
		if (comparator.getSwift_deposit_information() != null) {
			result.setSwift_deposit_information(comparator.getSwift_deposit_information());
		
		} else {
			result.setSwift_deposit_information(origin.getSwift_deposit_information());
		}
		
		if (comparator.getTaker_fee_rate() != null) {
			result.setTaker_fee_rate(comparator.getTaker_fee_rate());
		
		} else {
			result.setTaker_fee_rate(origin.getTaker_fee_rate());
		}
		
		if (comparator.getTaker_order_id() != null) {
			result.setTaker_order_id(comparator.getTaker_order_id());
		
		} else {
			result.setTaker_order_id(origin.getTaker_order_id());
		}
		
		if (comparator.getTaker_profile_id() != null) {
			result.setTaker_profile_id(comparator.getTaker_profile_id());
		
		} else {
			result.setTaker_profile_id(origin.getTaker_profile_id());
		}
		
		if (comparator.getTaker_user_id() != null) {
			result.setTaker_user_id(comparator.getTaker_user_id());
		
		} else {
			result.setTaker_user_id(origin.getTaker_user_id());
		}
		
		if (comparator.getTime() != null) {
			result.setTime(comparator.getTime());
		
		} else {
			result.setTime(origin.getTime());
		}
		
		if (comparator.getTimestamp() != null) {
			result.setTimestamp(comparator.getTimestamp());
		
		} else {
			result.setTimestamp(origin.getTimestamp());
		}
		
		if (comparator.getTo() != null) {
			result.setTo(comparator.getTo());
		
		} else {
			result.setTo(origin.getTo());
		}
		
		if (comparator.getTrade_id() != null) {
			result.setTrade_id(comparator.getTrade_id());
		
		} else {
			result.setTrade_id(origin.getTrade_id());
		}
		
		if (comparator.getTrading_enabled() != null) {
			result.setTrading_enabled(comparator.getTrading_enabled());
		
		} else {
			result.setTrading_enabled(origin.getTrading_enabled());
		}
		
		if (comparator.getType() != null) {
			result.setType(comparator.getType());
		
		} else {
			result.setType(origin.getType());
		}
		
		if (comparator.getUK_deposit_information() != null) {
			result.setUK_deposit_information(comparator.getUK_deposit_information());
		
		} else {
			result.setUK_deposit_information(origin.getUK_deposit_information());
		}
		
		if (comparator.getUpdated_at() != null) {
			result.setUpdated_at(comparator.getUpdated_at());
		
		} else {
			result.setUpdated_at(origin.getUpdated_at());
		}
		
		if (comparator.getURI_scheme() != null) {
			result.setURI_scheme(comparator.getURI_scheme());
		
		} else {
			result.setURI_scheme(origin.getURI_scheme());
		}
		
		if (comparator.getUSD_volume() != null) {
			result.setUSD_volume(comparator.getUSD_volume());
		
		} else {
			result.setUSD_volume(origin.getUSD_volume());
		}
		
		if (comparator.getUser_id() != null) {
			result.setUser_id(comparator.getUser_id());
		
		} else {
			result.setUser_id(origin.getUser_id());
		}
		
		if (comparator.getVerified() != null) {
			result.setVerified(comparator.getVerified());
		
		} else {
			result.setVerified(origin.getVerified());
		}
		
		if (comparator.getVolume_24h() != null) {
			result.setVolume_24h(comparator.getVolume_24h());
		
		} else {
			result.setVolume_24h(origin.getVolume_24h());
		}
		
		if (comparator.getVolume_30d() != null) {
			result.setVolume_30d(comparator.getVolume_30d());
		
		} else {
			result.setVolume_30d(origin.getVolume_30d());
		}
		
		if (comparator.getWarnings() != null) {
			result.setWarnings(comparator.getWarnings());
		
		} else {
			result.setWarnings(origin.getWarnings());
		}
		
		if (comparator.getWire_deposit_information() != null) {
			result.setWire_deposit_information(comparator.getWire_deposit_information());
		
		} else {
			result.setWire_deposit_information(origin.getWire_deposit_information());
		}
	}
}
