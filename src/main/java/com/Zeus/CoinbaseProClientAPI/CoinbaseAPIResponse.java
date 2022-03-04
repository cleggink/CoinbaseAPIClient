package com.Zeus.CoinbaseProClientAPI;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

// DATA CONTAINER FOR ALL COINBASE PRO API RESPONSES
// ALL VARIABLES, SETTERS, AND GETTERS GROUPED TOGETHER FOR EASY ACCESS
// ANYTHING EVEN CLOSE TO A PRICE VALUE CAN BE RETRIVED AS A DOUBLE, A STRING WITH A SPECIFIED NUMBER OF DIGITS,
// OR A BigDecimal

public class CoinbaseAPIResponse { 
	
	public static class Channels { // NESTED SUB-CONTAINER CLASS
		
		private String Name;
		public void setName(String passVar) { this.Name = passVar; }
		public String getName() { return this.Name; }
		
		private String[] Product_ids;
		public void setProduct_ids(String[] passVar) { this.Product_ids = passVar; }
		public String[] getProduct_ids() { return this.Product_ids; }
	}
	private Channels[] Channels; // SUB-CONTAINER DATA
	public void setChannels(Channels[] passVar) { this.Channels = passVar; }
	public Channels[] getChannels() { return this.Channels; }
	
	public static class Currency { // NESTED SUB-CONTAINER CLASS
		
		private Integer Precision;
		public void setPrecision(String passVar) { this.Precision = passVar.length() - passVar.indexOf(".") - 1; }
		public Integer getPrecision() { return this.Precision; }
		
		private String ID;
		public void setID(String passVar) { this.ID = passVar; }
		public String getID() { return this.ID; }
		
		private String Name;
		public void setName(String passVar) { this.Name = passVar; }
		public String getName() { return this.Name; }
		
		private Double Min_size;
		public void setMin_size(Double passVar) { this.Min_size = passVar; }
		public Double getMin_size() { return this.Min_size; }
		public String getMin_size(int digits) { return new BigDecimal(this.Min_size).round(new MathContext(digits, RoundingMode.UP)).toPlainString(); }
		public BigDecimal getMin_size_asBigDecimal() { return new BigDecimal(this.Min_size); }
		
		private String Max_precision; // PAIN IN THE ASS FLOATING POINT BULLSHIT
		public void setMax_precision(String passVar) { this.Max_precision = passVar; setPrecision(passVar); }
		public Double getMax_precision() { return Double.valueOf(this.Max_precision); }
		public String getMax_precision(int digits) { return new BigDecimal(this.Max_precision).round(new MathContext(digits, RoundingMode.UP)).toPlainString(); }
		public BigDecimal getMax_precision_asBigDecimal() { return new BigDecimal(this.Max_precision); }
		
		private String Status;
		public void setStatus(String passVar) { this.Status = passVar; }
		public String getStatus() { return this.Status; }
		
		private String Message;
		public void setMessage(String passVar) { this.Message = passVar; }
		public String getMessage() { return this.Message; }
		
		private String[] Convertible_to;
		public void setConvertible_to(String[] passVar) { this.Convertible_to = passVar; }
		public String[] getConvertible_to() { return this.Convertible_to; }
		
		private Details Details; // SUB-CONTAINER DATA
		public void setDetails(Details passVar) { this.Details = passVar; }
		public Details getDetails() { return this.Details; }
		
		private String Funding_account_id;
		public void setFunding_account_id(String passVar) { this.Funding_account_id = passVar; }
		public String getFunding_account_id() { return this.Funding_account_id; }
		
		private String Status_message;
		public void setStatus_message(String passVar) { this.Status_message = passVar; }
		public String getStatus_message() { return this.Status_message; }
	}
	private Currency[] Currencies; // SUB-CONTAINER DATA
	public void setCurrencies(Currency[] passVar) { this.Currencies = passVar; }
	public Currency[] getCurrencies() { return this.Currencies; }
	
	public static class Products { // NESTED SUB-CONTAINER CLASS
		
		private String ID;
		public void setID(String passVar) { this.ID = passVar; }
		public String getID() { return this.ID; }
		
		private String Base_currency;
		public void setBase_currency(String passVar) { this.Base_currency = passVar; }
		public String getBase_currency() { return this.Base_currency; }
		
		private String Quote_currency;
		public void setQuote_currency(String passVar) { this.Quote_currency = passVar; }
		public String getQuote_currency() { return this.Quote_currency; }
		
		private Double Base_min_size;
		public void setBase_min_size(Double passVar) { this.Base_min_size = passVar; }
		public Double getBase_min_size() { return this.Base_min_size; }
		public String getBase_min_size(int digits) { return new BigDecimal(this.Base_min_size).round(new MathContext(digits, RoundingMode.UP)).toPlainString(); }
		public BigDecimal getBase_min_size_asBigDecimal() { return new BigDecimal(this.Base_min_size); }
		
		private Double Base_max_size;
		public void setBase_max_size(Double passVar) { this.Base_max_size = passVar; }
		public Double getBase_max_size() { return this.Base_max_size; }
		public String getBase_max_size(int digits) { return new BigDecimal(this.Base_max_size).round(new MathContext(digits, RoundingMode.UP)).toPlainString(); }
		public BigDecimal getBase_max_size_asBigDecimal() { return new BigDecimal(this.Base_max_size); }
		
		private String Base_increment;
		public void setBase_increment(String passVar) { this.Base_increment = passVar; setQuote_precision(passVar); }
		public String getBase_increment() { return this.Base_increment; }
		
		private Integer Quote_precision;
		public void setQuote_precision(String passVar) { this.Quote_precision = passVar.length() - passVar.indexOf(".") - 1; }
		public Integer getQuote_precision() { return this.Quote_precision; }

		private Integer Base_precision;
		public void setBase_precision(String passVar) { this.Base_precision = passVar.length() - passVar.indexOf(".") - 1; }
		public Integer getBase_precision() { return this.Base_precision; }

		private String Quote_increment;
		public void setQuote_increment(String passVar) { this.Quote_increment = passVar; setBase_precision(passVar); }
		public String getQuote_increment() { return this.Quote_increment; }
		
		private String Display_name;
		public void setDisplay_name(String passVar) { this.Display_name = passVar; }
		public String getDisplay_name() { return this.Display_name; }
		
		private String Status;
		public void setStatus(String passVar) { this.Status = passVar; }
		public String getStatus() { return this.Status; }
		
		private String Status_message;
		public void setStatus_message(String passVar) { this.Status_message = passVar; }
		public String getStatus_message() { return this.Status_message; }
		
		private Double Min_market_funds;
		public void setMin_market_funds(Double passVar) { this.Min_market_funds = passVar; }
		public Double getMin_market_funds() { return this.Min_market_funds; }
		public String getMin_market_funds(int digits) { return new BigDecimal(this.Min_market_funds).round(new MathContext(digits, RoundingMode.UP)).toPlainString(); }
		public BigDecimal getMin_market_funds_asBigDecimal() { return new BigDecimal(this.Min_market_funds); }
		
		private Double Max_market_funds;
		public void setMax_market_funds(Double passVar) { this.Max_market_funds = passVar; }
		public Double getMax_market_funds() { return this.Max_market_funds; }
		public String getMax_market_funds(int digits) { return new BigDecimal(this.Max_market_funds).round(new MathContext(digits, RoundingMode.UP)).toPlainString(); }
		public BigDecimal getMax_market_funds_asBigDecimal() { return new BigDecimal(this.Max_market_funds); }
		
		private Boolean Post_only;
		public void setPost_only(Boolean passVar) { this.Post_only = passVar; }
		public Boolean getPost_only() { return this.Post_only; }
		
		private Boolean Limit_only;
		public void setLimit_only(Boolean passVar) { this.Limit_only = passVar; }
		public Boolean getLimit_only() { return this.Limit_only; }
		
		private Boolean Cancel_only;
		public void setCancel_only(Boolean passVar) { this.Cancel_only = passVar; }
		public Boolean getCancel_only() { return this.Cancel_only; }
		
		private Boolean Fx_stablecoin;
		public void setFx_stablecoin(Boolean passVar) { this.Fx_stablecoin = passVar; }
		public Boolean getFx_stablecoin() { return this.Fx_stablecoin; }
		
		private Boolean Margin_enabled;
		public void setMargin_enabled(Boolean passVar) { this.Margin_enabled = passVar; }
		public Boolean getMargin_enabled() { return this.Margin_enabled; }

		private Boolean Auction_mode;
		public void setAuction_mode(Boolean passVar) { this.Auction_mode = passVar; }
		public Boolean getAuction_mode() { return this.Auction_mode; }
		
		private String Type;
		public void setType(String passVar) { this.Type = passVar; }
		public String getType() { return this.Type; }
		
		private Double Max_slippage_percentage;
		public void setMax_slippage_percentage(Double passVar) { this.Max_slippage_percentage = passVar; }
		public Double getMax_slippage_percentage() { return this.Max_slippage_percentage; }
		public String getMax_slippage_percentage(int digits) { return new BigDecimal(this.Max_slippage_percentage).round(new MathContext(digits, RoundingMode.UP)).toPlainString(); }
		public BigDecimal getMax_slippage_percentage_asBigDecimal() { return new BigDecimal(this.Max_slippage_percentage); }	
	}
	private Products[] Products; // SUB-CONTAINER DATA
	public void setProducts(Products[] passVar) { this.Products = passVar; }
	public Products[] getProducts() { return this.Products; }

	public static class Details { // NESTED SUB-CONTAINER CLASS
		
		private String Type;
		public void setType(String passVar) { this.Type = passVar; }
		public String getType() { return this.Type; }
		
		private String Symbol;
		public void setSymbol(String passVar) { this.Symbol = passVar; }
		public String getSymbol() { return this.Symbol; }
		
		private String Sort_order;
		public void setSort_order(String passVar) { this.Sort_order = passVar; }
		public String getSort_order() { return this.Sort_order; }
		
		private String[] Push_payment_methods;
		public void setPush_payment_methods(String[] passVar) { this.Push_payment_methods = passVar; }
		public String[] getPush_payment_methods() { return this.Push_payment_methods; }
		
		private String Display_name;
		public void setDisplay_name(String passVar) { this.Display_name = passVar; }
		public String getDisplay_name() { return this.Display_name; }
		
		private String[] Group_types;
		public void setGroup_types(String[] passVar) { this.Group_types = passVar; }
		public String[] getGroup_types() { return this.Group_types; }
		
		private String Network_confirmations;
		public void setNetwork_confirmations(String passVar) { this.Network_confirmations = passVar; }
		public String getNetwork_confirmations() { return this.Network_confirmations; }
		
		private String Crypto_address_link;
		public void setCrypto_address_link(String passVar) { this.Crypto_address_link = passVar; }
		public String getCrypto_address_link() { return this.Crypto_address_link; }
		
		private String Crypto_transaction_link;
		public void setCrypto_transaction_link(String passVar) { this.Crypto_transaction_link = passVar; }
		public String getCrypto_transaction_link() { return this.Crypto_transaction_link; }
		
		private String Processing_time_seconds;
		public void setProcessing_time_seconds(String passVar) { this.Processing_time_seconds = passVar; }
		public String getProcessing_time_seconds() { return this.Processing_time_seconds; }
		public Integer getProcessing_time_seconds_asInteger() { return Integer.valueOf(this.Processing_time_seconds); }
		
		private Double Min_withdrawal_amount;
		public void setMin_withdrawal_amount(Double passVar) { this.Min_withdrawal_amount = passVar; }
		public Double getMin_withdrawal_amount() { return this.Min_withdrawal_amount; }
		public String getMin_withdrawal_amount(int digits) { return new BigDecimal(this.Min_withdrawal_amount).round(new MathContext(digits, RoundingMode.UP)).toPlainString(); }
		public BigDecimal getMin_withdrawal_amount_asBigDecimal() { return new BigDecimal(this.Min_withdrawal_amount); }
		
		private Double Max_withdrawal_amount;
		public void setMax_withdrawal_amount(Double passVar) { this.Max_withdrawal_amount = passVar; }
		public Double getMax_withdrawal_amount() { return this.Max_withdrawal_amount; }
		public String getMax_withdrawal_amount(int digits) { return new BigDecimal(this.Max_withdrawal_amount).round(new MathContext(digits, RoundingMode.UP)).toPlainString(); }
		public BigDecimal getMax_withdrawal_amount_asBigDecimal() { return new BigDecimal(this.Max_withdrawal_amount); }	
	}
	private Details Details; // SUB-CONTAINER DATA
	public void setDetails(Details passVar) { this.Details = passVar; }
	public Details getDetails() { return this.Details; }

	public static class Data { // NESTED SUB-CONTAINER CLASS
		
		private String Currency;
		public void setCurrency(String passVar) { this.Currency = passVar; }
		public String getCurrency() { return this.Currency; }
		
		public static class Rates { // SECOND NESTED SUB-CONTAINER CLASS
			
			private Double RLC;
			public void setRLC(Double passVar) { this.RLC = passVar; }
			public Double getRLC() { return this.RLC; }
			public String getRLC(int digits) { return new BigDecimal(this.RLC).round(new MathContext(digits, RoundingMode.UP)).toPlainString(); }
			public BigDecimal getRLC_asBigDecimal() { return new BigDecimal(this.RLC); }
			
			private Double AMP;
			public void setAMP(Double passVar) { this.AMP = passVar; }
			public Double getAMP() { return this.AMP; }
			public String getAMP(int digits) { return new BigDecimal(this.AMP).round(new MathContext(digits, RoundingMode.UP)).toPlainString(); }
			public BigDecimal getAMP_asBigDecimal() { return new BigDecimal(this.AMP); }
			
			private Double KEEP;
			public void setKEEP(Double passVar) { this.KEEP = passVar; }
			public Double getKEEP() { return this.KEEP; }
			public String getKEEP(int digits) { return new BigDecimal(this.KEEP).round(new MathContext(digits, RoundingMode.UP)).toPlainString(); }
			public BigDecimal getKEEP_asBigDecimal() { return new BigDecimal(this.KEEP); }
			
			private Double MKR;
			public void setMKR(Double passVar) { this.MKR = passVar; }
			public Double getMKR() { return this.MKR; }
			public String getMKR(int digits) { return new BigDecimal(this.MKR).round(new MathContext(digits, RoundingMode.UP)).toPlainString(); }
			public BigDecimal getMKR_asBigDecimal() { return new BigDecimal(this.MKR); }
			
			private Double DASH;
			public void setDASH(Double passVar) { this.DASH = passVar; }
			public Double getDASH() { return this.DASH; }
			public String getDASH(int digits) { return new BigDecimal(this.DASH).round(new MathContext(digits, RoundingMode.UP)).toPlainString(); }
			public BigDecimal getDASH_asBigDecimal() { return new BigDecimal(this.DASH); }
			
			private Double COMP;
			public void setCOMP(Double passVar) { this.COMP = passVar; }
			public Double getCOMP() { return this.COMP; }
			public String getCOMP(int digits) { return new BigDecimal(this.COMP).round(new MathContext(digits, RoundingMode.UP)).toPlainString(); }
			public BigDecimal getCOMP_asBigDecimal() { return new BigDecimal(this.COMP); }
			
			private Double SNX;
			public void setSNX(Double passVar) { this.SNX = passVar; }
			public Double getSNX() { return this.SNX; }
			public String getSNX(int digits) { return new BigDecimal(this.SNX).round(new MathContext(digits, RoundingMode.UP)).toPlainString(); }
			public BigDecimal getSNX_asBigDecimal() { return new BigDecimal(this.SNX); }
			
			private Double USD;
			public void setUSD(Double passVar) { this.USD = passVar; }
			public Double getUSD() { return this.USD; }
			public String getUSD(int digits) { return new BigDecimal(this.USD).round(new MathContext(digits, RoundingMode.UP)).toPlainString(); }
			public BigDecimal getUSD_asBigDecimal() { return new BigDecimal(this.USD); }
			
			private Double SHPING;
			public void setSHPING(Double passVar) { this.SHPING = passVar; }
			public Double getSHPING() { return this.SHPING; }
			public String getSHPING(int digits) { return new BigDecimal(this.SHPING).round(new MathContext(digits, RoundingMode.UP)).toPlainString(); }
			public BigDecimal getSHPING_asBigDecimal() { return new BigDecimal(this.SHPING); }
			
			private Double INV;
			public void setINV(Double passVar) { this.INV = passVar; }
			public Double getINV() { return this.INV; }
			public String getINV(int digits) { return new BigDecimal(this.INV).round(new MathContext(digits, RoundingMode.UP)).toPlainString(); }
			public BigDecimal getINV_asBigDecimal() { return new BigDecimal(this.INV); }
			
			private Double DIA;
			public void setDIA(Double passVar) { this.DIA = passVar; }
			public Double getDIA() { return this.DIA; }
			public String getDIA(int digits) { return new BigDecimal(this.DIA).round(new MathContext(digits, RoundingMode.UP)).toPlainString(); }
			public BigDecimal getDIA_asBigDecimal() { return new BigDecimal(this.DIA); }
			
			private Double AXS;
			public void setAXS(Double passVar) { this.AXS = passVar; }
			public Double getAXS() { return this.AXS; }
			public String getAXS(int digits) { return new BigDecimal(this.AXS).round(new MathContext(digits, RoundingMode.UP)).toPlainString(); }
			public BigDecimal getAXS_asBigDecimal() { return new BigDecimal(this.AXS); }
			
			private Double CLV;
			public void setCLV(Double passVar) { this.CLV = passVar; }
			public Double getCLV() { return this.CLV; }
			public String getCLV(int digits) { return new BigDecimal(this.CLV).round(new MathContext(digits, RoundingMode.UP)).toPlainString(); }
			public BigDecimal getCLV_asBigDecimal() { return new BigDecimal(this.CLV); }
			
			private Double XLM;
			public void setXLM(Double passVar) { this.XLM = passVar; }
			public Double getXLM() { return this.XLM; }
			public String getXLM(int digits) { return new BigDecimal(this.XLM).round(new MathContext(digits, RoundingMode.UP)).toPlainString(); }
			public BigDecimal getXLM_asBigDecimal() { return new BigDecimal(this.XLM); }
			
			private Double BCH;
			public void setBCH(Double passVar) { this.BCH = passVar; }
			public Double getBCH() { return this.BCH; }
			public String getBCH(int digits) { return new BigDecimal(this.BCH).round(new MathContext(digits, RoundingMode.UP)).toPlainString(); }
			public BigDecimal getBCH_asBigDecimal() { return new BigDecimal(this.BCH); }
			
			private Double BAND;
			public void setBAND(Double passVar) { this.BAND = passVar; }
			public Double getBAND() { return this.BAND; }
			public String getBAND(int digits) { return new BigDecimal(this.BAND).round(new MathContext(digits, RoundingMode.UP)).toPlainString(); }
			public BigDecimal getBAND_asBigDecimal() { return new BigDecimal(this.BAND); }
			
			private Double ADA;
			public void setADA(Double passVar) { this.ADA = passVar; }
			public Double getADA() { return this.ADA; }
			public String getADA(int digits) { return new BigDecimal(this.ADA).round(new MathContext(digits, RoundingMode.UP)).toPlainString(); }
			public BigDecimal getADA_asBigDecimal() { return new BigDecimal(this.ADA); }
			
			private Double JASMY;
			public void setJASMY(Double passVar) { this.JASMY = passVar; }
			public Double getJASMY() { return this.JASMY; }
			public String getJASMY(int digits) { return new BigDecimal(this.JASMY).round(new MathContext(digits, RoundingMode.UP)).toPlainString(); }
			public BigDecimal getJASMY_asBigDecimal() { return new BigDecimal(this.JASMY); }
			
			private Double ZEN;
			public void setZEN(Double passVar) { this.ZEN = passVar; }
			public Double getZEN() { return this.ZEN; }
			public String getZEN(int digits) { return new BigDecimal(this.ZEN).round(new MathContext(digits, RoundingMode.UP)).toPlainString(); }
			public BigDecimal getZEN_asBigDecimal() { return new BigDecimal(this.ZEN); }
			
			private Double GFI;
			public void setGFI(Double passVar) { this.GFI = passVar; }
			public Double getGFI() { return this.GFI; }
			public String getGFI(int digits) { return new BigDecimal(this.GFI).round(new MathContext(digits, RoundingMode.UP)).toPlainString(); }
			public BigDecimal getGFI_asBigDecimal() { return new BigDecimal(this.GFI); }
			
			private Double GODS;
			public void setGODS(Double passVar) { this.GODS = passVar; }
			public Double getGODS() { return this.GODS; }
			public String getGODS(int digits) { return new BigDecimal(this.GODS).round(new MathContext(digits, RoundingMode.UP)).toPlainString(); }
			public BigDecimal getGODS_asBigDecimal() { return new BigDecimal(this.GODS); }
			
			private Double MPL;
			public void setMPL(Double passVar) { this.MPL = passVar; }
			public Double getMPL() { return this.MPL; }
			public String getMPL(int digits) { return new BigDecimal(this.MPL).round(new MathContext(digits, RoundingMode.UP)).toPlainString(); }
			public BigDecimal getMPL_asBigDecimal() { return new BigDecimal(this.MPL); }
			
			private Double IOTX;
			public void setIOTX(Double passVar) { this.IOTX = passVar; }
			public Double getIOTX() { return this.IOTX; }
			public String getIOTX(int digits) { return new BigDecimal(this.IOTX).round(new MathContext(digits, RoundingMode.UP)).toPlainString(); }
			public BigDecimal getIOTX_asBigDecimal() { return new BigDecimal(this.IOTX); }
			
			private Double RARI;
			public void setRARI(Double passVar) { this.RARI = passVar; }
			public Double getRARI() { return this.RARI; }
			public String getRARI(int digits) { return new BigDecimal(this.RARI).round(new MathContext(digits, RoundingMode.UP)).toPlainString(); }
			public BigDecimal getRARI_asBigDecimal() { return new BigDecimal(this.RARI); }
			
			private Double FX;
			public void setFX(Double passVar) { this.FX = passVar; }
			public Double getFX() { return this.FX; }
			public String getFX(int digits) { return new BigDecimal(this.FX).round(new MathContext(digits, RoundingMode.UP)).toPlainString(); }
			public BigDecimal getFX_asBigDecimal() { return new BigDecimal(this.FX); }
			
			private Double WCFG;
			public void setWCFG(Double passVar) { this.WCFG = passVar; }
			public Double getWCFG() { return this.WCFG; }
			public String getWCFG(int digits) { return new BigDecimal(this.WCFG).round(new MathContext(digits, RoundingMode.UP)).toPlainString(); }
			public BigDecimal getWCFG_asBigDecimal() { return new BigDecimal(this.WCFG); }
			
			private Double XTZ;
			public void setXTZ(Double passVar) { this.XTZ = passVar; }
			public Double getXTZ() { return this.XTZ; }
			public String getXTZ(int digits) { return new BigDecimal(this.XTZ).round(new MathContext(digits, RoundingMode.UP)).toPlainString(); }
			public BigDecimal getXTZ_asBigDecimal() { return new BigDecimal(this.XTZ); }
			
			private Double LQTY;
			public void setLQTY(Double passVar) { this.LQTY = passVar; }
			public Double getLQTY() { return this.LQTY; }
			public String getLQTY(int digits) { return new BigDecimal(this.LQTY).round(new MathContext(digits, RoundingMode.UP)).toPlainString(); }
			public BigDecimal getLQTY_asBigDecimal() { return new BigDecimal(this.LQTY); }
			
			private Double UNI;
			public void setUNI(Double passVar) { this.UNI = passVar; }
			public Double getUNI() { return this.UNI; }
			public String getUNI(int digits) { return new BigDecimal(this.UNI).round(new MathContext(digits, RoundingMode.UP)).toPlainString(); }
			public BigDecimal getUNI_asBigDecimal() { return new BigDecimal(this.UNI); }
			
			private Double BADGER;
			public void setBADGER(Double passVar) { this.BADGER = passVar; }
			public Double getBADGER() { return this.BADGER; }
			public String getBADGER(int digits) { return new BigDecimal(this.BADGER).round(new MathContext(digits, RoundingMode.UP)).toPlainString(); }
			public BigDecimal getBADGER_asBigDecimal() { return new BigDecimal(this.BADGER); }
			
			private Double ENS;
			public void setENS(Double passVar) { this.ENS = passVar; }
			public Double getENS() { return this.ENS; }
			public String getENS(int digits) { return new BigDecimal(this.ENS).round(new MathContext(digits, RoundingMode.UP)).toPlainString(); }
			public BigDecimal getENS_asBigDecimal() { return new BigDecimal(this.ENS); }
			
			private Double UNFI;
			public void setUNFI(Double passVar) { this.UNFI = passVar; }
			public Double getUNFI() { return this.UNFI; }
			public String getUNFI(int digits) { return new BigDecimal(this.UNFI).round(new MathContext(digits, RoundingMode.UP)).toPlainString(); }
			public BigDecimal getUNFI_asBigDecimal() { return new BigDecimal(this.UNFI); }
			
			private Double CTX;
			public void setCTX(Double passVar) { this.CTX = passVar; }
			public Double getCTX() { return this.CTX; }
			public String getCTX(int digits) { return new BigDecimal(this.CTX).round(new MathContext(digits, RoundingMode.UP)).toPlainString(); }
			public BigDecimal getCTX_asBigDecimal() { return new BigDecimal(this.CTX); }
			
			private Double ZEC;
			public void setZEC(Double passVar) { this.ZEC = passVar; }
			public Double getZEC() { return this.ZEC; }
			public String getZEC(int digits) { return new BigDecimal(this.ZEC).round(new MathContext(digits, RoundingMode.UP)).toPlainString(); }
			public BigDecimal getZEC_asBigDecimal() { return new BigDecimal(this.ZEC); }
			
			private Double WLUNA;
			public void setWLUNA(Double passVar) { this.WLUNA = passVar; }
			public Double getWLUNA() { return this.WLUNA; }
			public String getWLUNA(int digits) { return new BigDecimal(this.WLUNA).round(new MathContext(digits, RoundingMode.UP)).toPlainString(); }
			public BigDecimal getWLUNA_asBigDecimal() { return new BigDecimal(this.WLUNA); }
			
			private Double AUCTION;
			public void setAUCTION(Double passVar) { this.AUCTION = passVar; }
			public Double getAUCTION() { return this.AUCTION; }
			public String getAUCTION(int digits) { return new BigDecimal(this.AUCTION).round(new MathContext(digits, RoundingMode.UP)).toPlainString(); }
			public BigDecimal getAUCTION_asBigDecimal() { return new BigDecimal(this.AUCTION); }
			
			private Double PERP;
			public void setPERP(Double passVar) { this.PERP = passVar; }
			public Double getPERP() { return this.PERP; }
			public String getPERP(int digits) { return new BigDecimal(this.PERP).round(new MathContext(digits, RoundingMode.UP)).toPlainString(); }
			public BigDecimal getPERP_asBigDecimal() { return new BigDecimal(this.PERP); }
			
			private Double USDC;
			public void setUSDC(Double passVar) { this.USDC = passVar; }
			public Double getUSDC() { return this.USDC; }
			public String getUSDC(int digits) { return new BigDecimal(this.USDC).round(new MathContext(digits, RoundingMode.UP)).toPlainString(); }
			public BigDecimal getUSDC_asBigDecimal() { return new BigDecimal(this.USDC); }
			
			private Double SUSHI;
			public void setSUSHI(Double passVar) { this.SUSHI = passVar; }
			public Double getSUSHI() { return this.SUSHI; }
			public String getSUSHI(int digits) { return new BigDecimal(this.SUSHI).round(new MathContext(digits, RoundingMode.UP)).toPlainString(); }
			public BigDecimal getSUSHI_asBigDecimal() { return new BigDecimal(this.SUSHI); }
			
			private Double ATOM;
			public void setATOM(Double passVar) { this.ATOM = passVar; }
			public Double getATOM() { return this.ATOM; }
			public String getATOM(int digits) { return new BigDecimal(this.ATOM).round(new MathContext(digits, RoundingMode.UP)).toPlainString(); }
			public BigDecimal getATOM_asBigDecimal() { return new BigDecimal(this.ATOM); }
			
			private Double BLZ;
			public void setBLZ(Double passVar) { this.BLZ = passVar; }
			public Double getBLZ() { return this.BLZ; }
			public String getBLZ(int digits) { return new BigDecimal(this.BLZ).round(new MathContext(digits, RoundingMode.UP)).toPlainString(); }
			public BigDecimal getBLZ_asBigDecimal() { return new BigDecimal(this.BLZ); }
			
			private Double YFII;
			public void setYFII(Double passVar) { this.YFII = passVar; }
			public Double getYFII() { return this.YFII; }
			public String getYFII(int digits) { return new BigDecimal(this.YFII).round(new MathContext(digits, RoundingMode.UP)).toPlainString(); }
			public BigDecimal getYFII_asBigDecimal() { return new BigDecimal(this.YFII); }
			
			private Double DESO;
			public void setDESO(Double passVar) { this.DESO = passVar; }
			public Double getDESO() { return this.DESO; }
			public String getDESO(int digits) { return new BigDecimal(this.DESO).round(new MathContext(digits, RoundingMode.UP)).toPlainString(); }
			public BigDecimal getDESO_asBigDecimal() { return new BigDecimal(this.DESO); }
			
			private Double NCT;
			public void setNCT(Double passVar) { this.NCT = passVar; }
			public Double getNCT() { return this.NCT; }
			public String getNCT(int digits) { return new BigDecimal(this.NCT).round(new MathContext(digits, RoundingMode.UP)).toPlainString(); }
			public BigDecimal getNCT_asBigDecimal() { return new BigDecimal(this.NCT); }
			
			private Double UMA;
			public void setUMA(Double passVar) { this.UMA = passVar; }
			public Double getUMA() { return this.UMA; }
			public String getUMA(int digits) { return new BigDecimal(this.UMA).round(new MathContext(digits, RoundingMode.UP)).toPlainString(); }
			public BigDecimal getUMA_asBigDecimal() { return new BigDecimal(this.UMA); }
			
			private Double GALA;
			public void setGALA(Double passVar) { this.GALA = passVar; }
			public Double getGALA() { return this.GALA; }
			public String getGALA(int digits) { return new BigDecimal(this.GALA).round(new MathContext(digits, RoundingMode.UP)).toPlainString(); }
			public BigDecimal getGALA_asBigDecimal() { return new BigDecimal(this.GALA); }
			
			private Double WBTC;
			public void setWBTC(Double passVar) { this.WBTC = passVar; }
			public Double getWBTC() { return this.WBTC; }
			public String getWBTC(int digits) { return new BigDecimal(this.WBTC).round(new MathContext(digits, RoundingMode.UP)).toPlainString(); }
			public BigDecimal getWBTC_asBigDecimal() { return new BigDecimal(this.WBTC); }
			
			private Double USDT;
			public void setUSDT(Double passVar) { this.USDT = passVar; }
			public Double getUSDT() { return this.USDT; }
			public String getUSDT(int digits) { return new BigDecimal(this.USDT).round(new MathContext(digits, RoundingMode.UP)).toPlainString(); }
			public BigDecimal getUSDT_asBigDecimal() { return new BigDecimal(this.USDT); }
			
			private Double MASK;
			public void setMASK(Double passVar) { this.MASK = passVar; }
			public Double getMASK() { return this.MASK; }
			public String getMASK(int digits) { return new BigDecimal(this.MASK).round(new MathContext(digits, RoundingMode.UP)).toPlainString(); }
			public BigDecimal getMASK_asBigDecimal() { return new BigDecimal(this.MASK); }
			
			private Double CTSI;
			public void setCTSI(Double passVar) { this.CTSI = passVar; }
			public Double getCTSI() { return this.CTSI; }
			public String getCTSI(int digits) { return new BigDecimal(this.CTSI).round(new MathContext(digits, RoundingMode.UP)).toPlainString(); }
			public BigDecimal getCTSI_asBigDecimal() { return new BigDecimal(this.CTSI); }
			
			private Double STORJ;
			public void setSTORJ(Double passVar) { this.STORJ = passVar; }
			public Double getSTORJ() { return this.STORJ; }
			public String getSTORJ(int digits) { return new BigDecimal(this.STORJ).round(new MathContext(digits, RoundingMode.UP)).toPlainString(); }
			public BigDecimal getSTORJ_asBigDecimal() { return new BigDecimal(this.STORJ); }
			
			private Double RAI;
			public void setRAI(Double passVar) { this.RAI = passVar; }
			public Double getRAI() { return this.RAI; }
			public String getRAI(int digits) { return new BigDecimal(this.RAI).round(new MathContext(digits, RoundingMode.UP)).toPlainString(); }
			public BigDecimal getRAI_asBigDecimal() { return new BigDecimal(this.RAI); }
			
			private Double STX;
			public void setSTX(Double passVar) { this.STX = passVar; }
			public Double getSTX() { return this.STX; }
			public String getSTX(int digits) { return new BigDecimal(this.STX).round(new MathContext(digits, RoundingMode.UP)).toPlainString(); }
			public BigDecimal getSTX_asBigDecimal() { return new BigDecimal(this.STX); }
			
			private Double GNT;
			public void setGNT(Double passVar) { this.GNT = passVar; }
			public Double getGNT() { return this.GNT; }
			public String getGNT(int digits) { return new BigDecimal(this.GNT).round(new MathContext(digits, RoundingMode.UP)).toPlainString(); }
			public BigDecimal getGNT_asBigDecimal() { return new BigDecimal(this.GNT); }
			
			private Double TRU;
			public void setTRU(Double passVar) { this.TRU = passVar; }
			public Double getTRU() { return this.TRU; }
			public String getTRU(int digits) { return new BigDecimal(this.TRU).round(new MathContext(digits, RoundingMode.UP)).toPlainString(); }
			public BigDecimal getTRU_asBigDecimal() { return new BigDecimal(this.TRU); }
			
			private Double TRB;
			public void setTRB(Double passVar) { this.TRB = passVar; }
			public Double getTRB() { return this.TRB; }
			public String getTRB(int digits) { return new BigDecimal(this.TRB).round(new MathContext(digits, RoundingMode.UP)).toPlainString(); }
			public BigDecimal getTRB_asBigDecimal() { return new BigDecimal(this.TRB); }
			
			private Double SUKU;
			public void setSUKU(Double passVar) { this.SUKU = passVar; }
			public Double getSUKU() { return this.SUKU; }
			public String getSUKU(int digits) { return new BigDecimal(this.SUKU).round(new MathContext(digits, RoundingMode.UP)).toPlainString(); }
			public BigDecimal getSUKU_asBigDecimal() { return new BigDecimal(this.SUKU); }
			
			private Double EOS;
			public void setEOS(Double passVar) { this.EOS = passVar; }
			public Double getEOS() { return this.EOS; }
			public String getEOS(int digits) { return new BigDecimal(this.EOS).round(new MathContext(digits, RoundingMode.UP)).toPlainString(); }
			public BigDecimal getEOS_asBigDecimal() { return new BigDecimal(this.EOS); }
			
			private Double GTC;
			public void setGTC(Double passVar) { this.GTC = passVar; }
			public Double getGTC() { return this.GTC; }
			public String getGTC(int digits) { return new BigDecimal(this.GTC).round(new MathContext(digits, RoundingMode.UP)).toPlainString(); }
			public BigDecimal getGTC_asBigDecimal() { return new BigDecimal(this.GTC); }
			
			private Double EUR;
			public void setEUR(Double passVar) { this.EUR = passVar; }
			public Double getEUR() { return this.EUR; }
			public String getEUR(int digits) { return new BigDecimal(this.EUR).round(new MathContext(digits, RoundingMode.UP)).toPlainString(); }
			public BigDecimal getEUR_asBigDecimal() { return new BigDecimal(this.EUR); }
			
			private Double VGX;
			public void setVGX(Double passVar) { this.VGX = passVar; }
			public Double getVGX() { return this.VGX; }
			public String getVGX(int digits) { return new BigDecimal(this.VGX).round(new MathContext(digits, RoundingMode.UP)).toPlainString(); }
			public BigDecimal getVGX_asBigDecimal() { return new BigDecimal(this.VGX); }
			
			private Double MUSD;
			public void setMUSD(Double passVar) { this.MUSD = passVar; }
			public Double getMUSD() { return this.MUSD; }
			public String getMUSD(int digits) { return new BigDecimal(this.MUSD).round(new MathContext(digits, RoundingMode.UP)).toPlainString(); }
			public BigDecimal getMUSD_asBigDecimal() { return new BigDecimal(this.MUSD); }
			
			private Double MATIC;
			public void setMATIC(Double passVar) { this.MATIC = passVar; }
			public Double getMATIC() { return this.MATIC; }
			public String getMATIC(int digits) { return new BigDecimal(this.MATIC).round(new MathContext(digits, RoundingMode.UP)).toPlainString(); }
			public BigDecimal getMATIC_asBigDecimal() { return new BigDecimal(this.MATIC); }
			
			private Double REN;
			public void setREN(Double passVar) { this.REN = passVar; }
			public Double getREN() { return this.REN; }
			public String getREN(int digits) { return new BigDecimal(this.REN).round(new MathContext(digits, RoundingMode.UP)).toPlainString(); }
			public BigDecimal getREN_asBigDecimal() { return new BigDecimal(this.REN); }
			
			private Double GRT;
			public void setGRT(Double passVar) { this.GRT = passVar; }
			public Double getGRT() { return this.GRT; }
			public String getGRT(int digits) { return new BigDecimal(this.GRT).round(new MathContext(digits, RoundingMode.UP)).toPlainString(); }
			public BigDecimal getGRT_asBigDecimal() { return new BigDecimal(this.GRT); }
			
			private Double LCX;
			public void setLCX(Double passVar) { this.LCX = passVar; }
			public Double getLCX() { return this.LCX; }
			public String getLCX(int digits) { return new BigDecimal(this.LCX).round(new MathContext(digits, RoundingMode.UP)).toPlainString(); }
			public BigDecimal getLCX_asBigDecimal() { return new BigDecimal(this.LCX); }
			
			private Double LOOM;
			public void setLOOM(Double passVar) { this.LOOM = passVar; }
			public Double getLOOM() { return this.LOOM; }
			public String getLOOM(int digits) { return new BigDecimal(this.LOOM).round(new MathContext(digits, RoundingMode.UP)).toPlainString(); }
			public BigDecimal getLOOM_asBigDecimal() { return new BigDecimal(this.LOOM); }
			
			private Double ASM;
			public void setASM(Double passVar) { this.ASM = passVar; }
			public Double getASM() { return this.ASM; }
			public String getASM(int digits) { return new BigDecimal(this.ASM).round(new MathContext(digits, RoundingMode.UP)).toPlainString(); }
			public BigDecimal getASM_asBigDecimal() { return new BigDecimal(this.ASM); }
			
			private Double ICP;
			public void setICP(Double passVar) { this.ICP = passVar; }
			public Double getICP() { return this.ICP; }
			public String getICP(int digits) { return new BigDecimal(this.ICP).round(new MathContext(digits, RoundingMode.UP)).toPlainString(); }
			public BigDecimal getICP_asBigDecimal() { return new BigDecimal(this.ICP); }
			
			private Double REP;
			public void setREP(Double passVar) { this.REP = passVar; }
			public Double getREP() { return this.REP; }
			public String getREP(int digits) { return new BigDecimal(this.REP).round(new MathContext(digits, RoundingMode.UP)).toPlainString(); }
			public BigDecimal getREP_asBigDecimal() { return new BigDecimal(this.REP); }
			
			private Double TBTC;
			public void setTBTC(Double passVar) { this.TBTC = passVar; }
			public Double getTBTC() { return this.TBTC; }
			public String getTBTC(int digits) { return new BigDecimal(this.TBTC).round(new MathContext(digits, RoundingMode.UP)).toPlainString(); }
			public BigDecimal getTBTC_asBigDecimal() { return new BigDecimal(this.TBTC); }
			
			private Double YFI;
			public void setYFI(Double passVar) { this.YFI = passVar; }
			public Double getYFI() { return this.YFI; }
			public String getYFI(int digits) { return new BigDecimal(this.YFI).round(new MathContext(digits, RoundingMode.UP)).toPlainString(); }
			public BigDecimal getYFI_asBigDecimal() { return new BigDecimal(this.YFI); }
			
			private Double UST;
			public void setUST(Double passVar) { this.UST = passVar; }
			public Double getUST() { return this.UST; }
			public String getUST(int digits) { return new BigDecimal(this.UST).round(new MathContext(digits, RoundingMode.UP)).toPlainString(); }
			public BigDecimal getUST_asBigDecimal() { return new BigDecimal(this.UST); }
			
			private Double XYO;
			public void setXYO(Double passVar) { this.XYO = passVar; }
			public Double getXYO() { return this.XYO; }
			public String getXYO(int digits) { return new BigDecimal(this.XYO).round(new MathContext(digits, RoundingMode.UP)).toPlainString(); }
			public BigDecimal getXYO_asBigDecimal() { return new BigDecimal(this.XYO); }
			
			private Double FOX;
			public void setFOX(Double passVar) { this.FOX = passVar; }
			public Double getFOX() { return this.FOX; }
			public String getFOX(int digits) { return new BigDecimal(this.FOX).round(new MathContext(digits, RoundingMode.UP)).toPlainString(); }
			public BigDecimal getFOX_asBigDecimal() { return new BigDecimal(this.FOX); }
			
			private Double LRC;
			public void setLRC(Double passVar) { this.LRC = passVar; }
			public Double getLRC() { return this.LRC; }
			public String getLRC(int digits) { return new BigDecimal(this.LRC).round(new MathContext(digits, RoundingMode.UP)).toPlainString(); }
			public BigDecimal getLRC_asBigDecimal() { return new BigDecimal(this.LRC); }
			
			private Double BOND;
			public void setBOND(Double passVar) { this.BOND = passVar; }
			public Double getBOND() { return this.BOND; }
			public String getBOND(int digits) { return new BigDecimal(this.BOND).round(new MathContext(digits, RoundingMode.UP)).toPlainString(); }
			public BigDecimal getBOND_asBigDecimal() { return new BigDecimal(this.BOND); }
			
			private Double DDX;
			public void setDDX(Double passVar) { this.DDX = passVar; }
			public Double getDDX() { return this.DDX; }
			public String getDDX(int digits) { return new BigDecimal(this.DDX).round(new MathContext(digits, RoundingMode.UP)).toPlainString(); }
			public BigDecimal getDDX_asBigDecimal() { return new BigDecimal(this.DDX); }
			
			private Double MIR;
			public void setMIR(Double passVar) { this.MIR = passVar; }
			public Double getMIR() { return this.MIR; }
			public String getMIR(int digits) { return new BigDecimal(this.MIR).round(new MathContext(digits, RoundingMode.UP)).toPlainString(); }
			public BigDecimal getMIR_asBigDecimal() { return new BigDecimal(this.MIR); }
			
			private Double RBN;
			public void setRBN(Double passVar) { this.RBN = passVar; }
			public Double getRBN() { return this.RBN; }
			public String getRBN(int digits) { return new BigDecimal(this.RBN).round(new MathContext(digits, RoundingMode.UP)).toPlainString(); }
			public BigDecimal getRBN_asBigDecimal() { return new BigDecimal(this.RBN); }
			
			private Double SKL;
			public void setSKL(Double passVar) { this.SKL = passVar; }
			public Double getSKL() { return this.SKL; }
			public String getSKL(int digits) { return new BigDecimal(this.SKL).round(new MathContext(digits, RoundingMode.UP)).toPlainString(); }
			public BigDecimal getSKL_asBigDecimal() { return new BigDecimal(this.SKL); }
			
			private Double BTC;
			public void setBTC(Double passVar) { this.BTC = passVar; }
			public Double getBTC() { return this.BTC; }
			public String getBTC(int digits) { return new BigDecimal(this.BTC).round(new MathContext(digits, RoundingMode.UP)).toPlainString(); }
			public BigDecimal getBTC_asBigDecimal() { return new BigDecimal(this.BTC); }
			
			private Double MLN;
			public void setMLN(Double passVar) { this.MLN = passVar; }
			public Double getMLN() { return this.MLN; }
			public String getMLN(int digits) { return new BigDecimal(this.MLN).round(new MathContext(digits, RoundingMode.UP)).toPlainString(); }
			public BigDecimal getMLN_asBigDecimal() { return new BigDecimal(this.MLN); }
			
			private Double TRAC;
			public void setTRAC(Double passVar) { this.TRAC = passVar; }
			public Double getTRAC() { return this.TRAC; }
			public String getTRAC(int digits) { return new BigDecimal(this.TRAC).round(new MathContext(digits, RoundingMode.UP)).toPlainString(); }
			public BigDecimal getTRAC_asBigDecimal() { return new BigDecimal(this.TRAC); }
			
			private Double POWR;
			public void setPOWR(Double passVar) { this.POWR = passVar; }
			public Double getPOWR() { return this.POWR; }
			public String getPOWR(int digits) { return new BigDecimal(this.POWR).round(new MathContext(digits, RoundingMode.UP)).toPlainString(); }
			public BigDecimal getPOWR_asBigDecimal() { return new BigDecimal(this.POWR); }
			
			private Double GBP;
			public void setGBP(Double passVar) { this.GBP = passVar; }
			public Double getGBP() { return this.GBP; }
			public String getGBP(int digits) { return new BigDecimal(this.GBP).round(new MathContext(digits, RoundingMode.UP)).toPlainString(); }
			public BigDecimal getGBP_asBigDecimal() { return new BigDecimal(this.GBP); }
			
			private Double PRO;
			public void setPRO(Double passVar) { this.PRO = passVar; }
			public Double getPRO() { return this.PRO; }
			public String getPRO(int digits) { return new BigDecimal(this.PRO).round(new MathContext(digits, RoundingMode.UP)).toPlainString(); }
			public BigDecimal getPRO_asBigDecimal() { return new BigDecimal(this.PRO); }
			
			private Double BICO;
			public void setBICO(Double passVar) { this.BICO = passVar; }
			public Double getBICO() { return this.BICO; }
			public String getBICO(int digits) { return new BigDecimal(this.BICO).round(new MathContext(digits, RoundingMode.UP)).toPlainString(); }
			public BigDecimal getBICO_asBigDecimal() { return new BigDecimal(this.BICO); }
			
			private Double NU;
			public void setNU(Double passVar) { this.NU = passVar; }
			public Double getNU() { return this.NU; }
			public String getNU(int digits) { return new BigDecimal(this.NU).round(new MathContext(digits, RoundingMode.UP)).toPlainString(); }
			public BigDecimal getNU_asBigDecimal() { return new BigDecimal(this.NU); }
			
			private Double ETH;
			public void setETH(Double passVar) { this.ETH = passVar; }
			public Double getETH() { return this.ETH; }
			public String getETH(int digits) { return new BigDecimal(this.ETH).round(new MathContext(digits, RoundingMode.UP)).toPlainString(); }
			public BigDecimal getETH_asBigDecimal() { return new BigDecimal(this.ETH); }
			
			private Double MDT;
			public void setMDT(Double passVar) { this.MDT = passVar; }
			public Double getMDT() { return this.MDT; }
			public String getMDT(int digits) { return new BigDecimal(this.MDT).round(new MathContext(digits, RoundingMode.UP)).toPlainString(); }
			public BigDecimal getMDT_asBigDecimal() { return new BigDecimal(this.MDT); }
			
			private Double OMG;
			public void setOMG(Double passVar) { this.OMG = passVar; }
			public Double getOMG() { return this.OMG; }
			public String getOMG(int digits) { return new BigDecimal(this.OMG).round(new MathContext(digits, RoundingMode.UP)).toPlainString(); }
			public BigDecimal getOMG_asBigDecimal() { return new BigDecimal(this.OMG); }
			
			private Double FIL;
			public void setFIL(Double passVar) { this.FIL = passVar; }
			public Double getFIL() { return this.FIL; }
			public String getFIL(int digits) { return new BigDecimal(this.FIL).round(new MathContext(digits, RoundingMode.UP)).toPlainString(); }
			public BigDecimal getFIL_asBigDecimal() { return new BigDecimal(this.FIL); }
			
			private Double ZRX;
			public void setZRX(Double passVar) { this.ZRX = passVar; }
			public Double getZRX() { return this.ZRX; }
			public String getZRX(int digits) { return new BigDecimal(this.ZRX).round(new MathContext(digits, RoundingMode.UP)).toPlainString(); }
			public BigDecimal getZRX_asBigDecimal() { return new BigDecimal(this.ZRX); }
			
			private Double RGT;
			public void setRGT(Double passVar) { this.RGT = passVar; }
			public Double getRGT() { return this.RGT; }
			public String getRGT(int digits) { return new BigDecimal(this.RGT).round(new MathContext(digits, RoundingMode.UP)).toPlainString(); }
			public BigDecimal getRGT_asBigDecimal() { return new BigDecimal(this.RGT); }
			
			private Double PLU;
			public void setPLU(Double passVar) { this.PLU = passVar; }
			public Double getPLU() { return this.PLU; }
			public String getPLU(int digits) { return new BigDecimal(this.PLU).round(new MathContext(digits, RoundingMode.UP)).toPlainString(); }
			public BigDecimal getPLU_asBigDecimal() { return new BigDecimal(this.PLU); }
			
			private Double LTC;
			public void setLTC(Double passVar) { this.LTC = passVar; }
			public Double getLTC() { return this.LTC; }
			public String getLTC(int digits) { return new BigDecimal(this.LTC).round(new MathContext(digits, RoundingMode.UP)).toPlainString(); }
			public BigDecimal getLTC_asBigDecimal() { return new BigDecimal(this.LTC); }
			
			private Double ALGO;
			public void setALGO(Double passVar) { this.ALGO = passVar; }
			public Double getALGO() { return this.ALGO; }
			public String getALGO(int digits) { return new BigDecimal(this.ALGO).round(new MathContext(digits, RoundingMode.UP)).toPlainString(); }
			public BigDecimal getALGO_asBigDecimal() { return new BigDecimal(this.ALGO); }
			
			private Double LINK;
			public void setLINK(Double passVar) { this.LINK = passVar; }
			public Double getLINK() { return this.LINK; }
			public String getLINK(int digits) { return new BigDecimal(this.LINK).round(new MathContext(digits, RoundingMode.UP)).toPlainString(); }
			public BigDecimal getLINK_asBigDecimal() { return new BigDecimal(this.LINK); }
			
			private Double CGLD;
			public void setCGLD(Double passVar) { this.CGLD = passVar; }
			public Double getCGLD() { return this.CGLD; }
			public String getCGLD(int digits) { return new BigDecimal(this.CGLD).round(new MathContext(digits, RoundingMode.UP)).toPlainString(); }
			public BigDecimal getCGLD_asBigDecimal() { return new BigDecimal(this.CGLD); }
			
			private Double CRO;
			public void setCRO(Double passVar) { this.CRO = passVar; }
			public Double getCRO() { return this.CRO; }
			public String getCRO(int digits) { return new BigDecimal(this.CRO).round(new MathContext(digits, RoundingMode.UP)).toPlainString(); }
			public BigDecimal getCRO_asBigDecimal() { return new BigDecimal(this.CRO); }
			
			private Double KNC;
			public void setKNC(Double passVar) { this.KNC = passVar; }
			public Double getKNC() { return this.KNC; }
			public String getKNC(int digits) { return new BigDecimal(this.KNC).round(new MathContext(digits, RoundingMode.UP)).toPlainString(); }
			public BigDecimal getKNC_asBigDecimal() { return new BigDecimal(this.KNC); }
			
			private Double CRV;
			public void setCRV(Double passVar) { this.CRV = passVar; }
			public Double getCRV() { return this.CRV; }
			public String getCRV(int digits) { return new BigDecimal(this.CRV).round(new MathContext(digits, RoundingMode.UP)).toPlainString(); }
			public BigDecimal getCRV_asBigDecimal() { return new BigDecimal(this.CRV); }
			
			private Double REQ;
			public void setREQ(Double passVar) { this.REQ = passVar; }
			public Double getREQ() { return this.REQ; }
			public String getREQ(int digits) { return new BigDecimal(this.REQ).round(new MathContext(digits, RoundingMode.UP)).toPlainString(); }
			public BigDecimal getREQ_asBigDecimal() { return new BigDecimal(this.REQ); }
			
			private Double FET;
			public void setFET(Double passVar) { this.FET = passVar; }
			public Double getFET() { return this.FET; }
			public String getFET(int digits) { return new BigDecimal(this.FET).round(new MathContext(digits, RoundingMode.UP)).toPlainString(); }
			public BigDecimal getFET_asBigDecimal() { return new BigDecimal(this.FET); }
			
			private Double SUPER;
			public void setSUPER(Double passVar) { this.SUPER = passVar; }
			public Double getSUPER() { return this.SUPER; }
			public String getSUPER(int digits) { return new BigDecimal(this.SUPER).round(new MathContext(digits, RoundingMode.UP)).toPlainString(); }
			public BigDecimal getSUPER_asBigDecimal() { return new BigDecimal(this.SUPER); }
			
			private Double OGN;
			public void setOGN(Double passVar) { this.OGN = passVar; }
			public Double getOGN() { return this.OGN; }
			public String getOGN(int digits) { return new BigDecimal(this.OGN).round(new MathContext(digits, RoundingMode.UP)).toPlainString(); }
			public BigDecimal getOGN_asBigDecimal() { return new BigDecimal(this.OGN); }
			
			private Double LPT;
			public void setLPT(Double passVar) { this.LPT = passVar; }
			public Double getLPT() { return this.LPT; }
			public String getLPT(int digits) { return new BigDecimal(this.LPT).round(new MathContext(digits, RoundingMode.UP)).toPlainString(); }
			public BigDecimal getLPT_asBigDecimal() { return new BigDecimal(this.LPT); }
			
			private Double QUICK;
			public void setQUICK(Double passVar) { this.QUICK = passVar; }
			public Double getQUICK() { return this.QUICK; }
			public String getQUICK(int digits) { return new BigDecimal(this.QUICK).round(new MathContext(digits, RoundingMode.UP)).toPlainString(); }
			public BigDecimal getQUICK_asBigDecimal() { return new BigDecimal(this.QUICK); }
			
			private Double DNT;
			public void setDNT(Double passVar) { this.DNT = passVar; }
			public Double getDNT() { return this.DNT; }
			public String getDNT(int digits) { return new BigDecimal(this.DNT).round(new MathContext(digits, RoundingMode.UP)).toPlainString(); }
			public BigDecimal getDNT_asBigDecimal() { return new BigDecimal(this.DNT); }
			
			private Double ORN;
			public void setORN(Double passVar) { this.ORN = passVar; }
			public Double getORN() { return this.ORN; }
			public String getORN(int digits) { return new BigDecimal(this.ORN).round(new MathContext(digits, RoundingMode.UP)).toPlainString(); }
			public BigDecimal getORN_asBigDecimal() { return new BigDecimal(this.ORN); }
			
			private Double NKN;
			public void setNKN(Double passVar) { this.NKN = passVar; }
			public Double getNKN() { return this.NKN; }
			public String getNKN(int digits) { return new BigDecimal(this.NKN).round(new MathContext(digits, RoundingMode.UP)).toPlainString(); }
			public BigDecimal getNKN_asBigDecimal() { return new BigDecimal(this.NKN); }
			
			private Double PLA;
			public void setPLA(Double passVar) { this.PLA = passVar; }
			public Double getPLA() { return this.PLA; }
			public String getPLA(int digits) { return new BigDecimal(this.PLA).round(new MathContext(digits, RoundingMode.UP)).toPlainString(); }
			public BigDecimal getPLA_asBigDecimal() { return new BigDecimal(this.PLA); }
			
			private Double ARPA;
			public void setARPA(Double passVar) { this.ARPA = passVar; }
			public Double getARPA() { return this.ARPA; }
			public String getARPA(int digits) { return new BigDecimal(this.ARPA).round(new MathContext(digits, RoundingMode.UP)).toPlainString(); }
			public BigDecimal getARPA_asBigDecimal() { return new BigDecimal(this.ARPA); }
			
			private Double BTRST;
			public void setBTRST(Double passVar) { this.BTRST = passVar; }
			public Double getBTRST() { return this.BTRST; }
			public String getBTRST(int digits) { return new BigDecimal(this.BTRST).round(new MathContext(digits, RoundingMode.UP)).toPlainString(); }
			public BigDecimal getBTRST_asBigDecimal() { return new BigDecimal(this.BTRST); }
			
			private Double AGLD;
			public void setAGLD(Double passVar) { this.AGLD = passVar; }
			public Double getAGLD() { return this.AGLD; }
			public String getAGLD(int digits) { return new BigDecimal(this.AGLD).round(new MathContext(digits, RoundingMode.UP)).toPlainString(); }
			public BigDecimal getAGLD_asBigDecimal() { return new BigDecimal(this.AGLD); }
			
			private Double PAX;
			public void setPAX(Double passVar) { this.PAX = passVar; }
			public Double getPAX() { return this.PAX; }
			public String getPAX(int digits) { return new BigDecimal(this.PAX).round(new MathContext(digits, RoundingMode.UP)).toPlainString(); }
			public BigDecimal getPAX_asBigDecimal() { return new BigDecimal(this.PAX); }
			
			private Double POLS;
			public void setPOLS(Double passVar) { this.POLS = passVar; }
			public Double getPOLS() { return this.POLS; }
			public String getPOLS(int digits) { return new BigDecimal(this.POLS).round(new MathContext(digits, RoundingMode.UP)).toPlainString(); }
			public BigDecimal getPOLS_asBigDecimal() { return new BigDecimal(this.POLS); }
			
			private Double SPELL;
			public void setSPELL(Double passVar) { this.SPELL = passVar; }
			public Double getSPELL() { return this.SPELL; }
			public String getSPELL(int digits) { return new BigDecimal(this.SPELL).round(new MathContext(digits, RoundingMode.UP)).toPlainString(); }
			public BigDecimal getSPELL_asBigDecimal() { return new BigDecimal(this.SPELL); }
			
			private Double CHZ;
			public void setCHZ(Double passVar) { this.CHZ = passVar; }
			public Double getCHZ() { return this.CHZ; }
			public String getCHZ(int digits) { return new BigDecimal(this.CHZ).round(new MathContext(digits, RoundingMode.UP)).toPlainString(); }
			public BigDecimal getCHZ_asBigDecimal() { return new BigDecimal(this.CHZ); }
			
			private Double BNT;
			public void setBNT(Double passVar) { this.BNT = passVar; }
			public Double getBNT() { return this.BNT; }
			public String getBNT(int digits) { return new BigDecimal(this.BNT).round(new MathContext(digits, RoundingMode.UP)).toPlainString(); }
			public BigDecimal getBNT_asBigDecimal() { return new BigDecimal(this.BNT); }
			
			private Double BAT;
			public void setBAT(Double passVar) { this.BAT = passVar; }
			public Double getBAT() { return this.BAT; }
			public String getBAT(int digits) { return new BigDecimal(this.BAT).round(new MathContext(digits, RoundingMode.UP)).toPlainString(); }
			public BigDecimal getBAT_asBigDecimal() { return new BigDecimal(this.BAT); }
			
			private Double AVAX;
			public void setAVAX(Double passVar) { this.AVAX = passVar; }
			public Double getAVAX() { return this.AVAX; }
			public String getAVAX(int digits) { return new BigDecimal(this.AVAX).round(new MathContext(digits, RoundingMode.UP)).toPlainString(); }
			public BigDecimal getAVAX_asBigDecimal() { return new BigDecimal(this.AVAX); }
			
			private Double BAL;
			public void setBAL(Double passVar) { this.BAL = passVar; }
			public Double getBAL() { return this.BAL; }
			public String getBAL(int digits) { return new BigDecimal(this.BAL).round(new MathContext(digits, RoundingMode.UP)).toPlainString(); }
			public BigDecimal getBAL_asBigDecimal() { return new BigDecimal(this.BAL); }
			
			private Double AAVE;
			public void setAAVE(Double passVar) { this.AAVE = passVar; }
			public Double getAAVE() { return this.AAVE; }
			public String getAAVE(int digits) { return new BigDecimal(this.AAVE).round(new MathContext(digits, RoundingMode.UP)).toPlainString(); }
			public BigDecimal getAAVE_asBigDecimal() { return new BigDecimal(this.AAVE); }
			
			private Double QNT;
			public void setQNT(Double passVar) { this.QNT = passVar; }
			public Double getQNT() { return this.QNT; }
			public String getQNT(int digits) { return new BigDecimal(this.QNT).round(new MathContext(digits, RoundingMode.UP)).toPlainString(); }
			public BigDecimal getQNT_asBigDecimal() { return new BigDecimal(this.QNT); }
			
			private Double COTI;
			public void setCOTI(Double passVar) { this.COTI = passVar; }
			public Double getCOTI() { return this.COTI; }
			public String getCOTI(int digits) { return new BigDecimal(this.COTI).round(new MathContext(digits, RoundingMode.UP)).toPlainString(); }
			public BigDecimal getCOTI_asBigDecimal() { return new BigDecimal(this.COTI); }
			
			private Double DOT;
			public void setDOT(Double passVar) { this.DOT = passVar; }
			public Double getDOT() { return this.DOT; }
			public String getDOT(int digits) { return new BigDecimal(this.DOT).round(new MathContext(digits, RoundingMode.UP)).toPlainString(); }
			public BigDecimal getDOT_asBigDecimal() { return new BigDecimal(this.DOT); }
			
			private Double CVC;
			public void setCVC(Double passVar) { this.CVC = passVar; }
			public Double getCVC() { return this.CVC; }
			public String getCVC(int digits) { return new BigDecimal(this.CVC).round(new MathContext(digits, RoundingMode.UP)).toPlainString(); }
			public BigDecimal getCVC_asBigDecimal() { return new BigDecimal(this.CVC); }
			
			private Double FORTH;
			public void setFORTH(Double passVar) { this.FORTH = passVar; }
			public Double getFORTH() { return this.FORTH; }
			public String getFORTH(int digits) { return new BigDecimal(this.FORTH).round(new MathContext(digits, RoundingMode.UP)).toPlainString(); }
			public BigDecimal getFORTH_asBigDecimal() { return new BigDecimal(this.FORTH); }
			
			private Double NMR;
			public void setNMR(Double passVar) { this.NMR = passVar; }
			public Double getNMR() { return this.NMR; }
			public String getNMR(int digits) { return new BigDecimal(this.NMR).round(new MathContext(digits, RoundingMode.UP)).toPlainString(); }
			public BigDecimal getNMR_asBigDecimal() { return new BigDecimal(this.NMR); }
			
			private Double OXT;
			public void setOXT(Double passVar) { this.OXT = passVar; }
			public Double getOXT() { return this.OXT; }
			public String getOXT(int digits) { return new BigDecimal(this.OXT).round(new MathContext(digits, RoundingMode.UP)).toPlainString(); }
			public BigDecimal getOXT_asBigDecimal() { return new BigDecimal(this.OXT); }
			
			private Double ENJ;
			public void setENJ(Double passVar) { this.ENJ = passVar; }
			public Double getENJ() { return this.ENJ; }
			public String getENJ(int digits) { return new BigDecimal(this.ENJ).round(new MathContext(digits, RoundingMode.UP)).toPlainString(); }
			public BigDecimal getENJ_asBigDecimal() { return new BigDecimal(this.ENJ); }
			
			private Double POLY;
			public void setPOLY(Double passVar) { this.POLY = passVar; }
			public Double getPOLY() { return this.POLY; }
			public String getPOLY(int digits) { return new BigDecimal(this.POLY).round(new MathContext(digits, RoundingMode.UP)).toPlainString(); }
			public BigDecimal getPOLY_asBigDecimal() { return new BigDecimal(this.POLY); }
			
			private Double RLY;
			public void setRLY(Double passVar) { this.RLY = passVar; }
			public Double getRLY() { return this.RLY; }
			public String getRLY(int digits) { return new BigDecimal(this.RLY).round(new MathContext(digits, RoundingMode.UP)).toPlainString(); }
			public BigDecimal getRLY_asBigDecimal() { return new BigDecimal(this.RLY); }
			
			private Double ACH;
			public void setACH(Double passVar) { this.ACH = passVar; }
			public Double getACH() { return this.ACH; }
			public String getACH(int digits) { return new BigDecimal(this.ACH).round(new MathContext(digits, RoundingMode.UP)).toPlainString(); }
			public BigDecimal getACH_asBigDecimal() { return new BigDecimal(this.ACH); }
			
			private Double COVAL;
			public void setCOVAL(Double passVar) { this.COVAL = passVar; }
			public Double getCOVAL() { return this.COVAL; }
			public String getCOVAL(int digits) { return new BigDecimal(this.COVAL).round(new MathContext(digits, RoundingMode.UP)).toPlainString(); }
			public BigDecimal getCOVAL_asBigDecimal() { return new BigDecimal(this.COVAL); }
			
			private Double SOL;
			public void setSOL(Double passVar) { this.SOL = passVar; }
			public Double getSOL() { return this.SOL; }
			public String getSOL(int digits) { return new BigDecimal(this.SOL).round(new MathContext(digits, RoundingMode.UP)).toPlainString(); }
			public BigDecimal getSOL_asBigDecimal() { return new BigDecimal(this.SOL); }
			
			private Double DAI;
			public void setDAI(Double passVar) { this.DAI = passVar; }
			public Double getDAI() { return this.DAI; }
			public String getDAI(int digits) { return new BigDecimal(this.DAI).round(new MathContext(digits, RoundingMode.UP)).toPlainString(); }
			public BigDecimal getDAI_asBigDecimal() { return new BigDecimal(this.DAI); }
			
			private Double TRIBE;
			public void setTRIBE(Double passVar) { this.TRIBE = passVar; }
			public Double getTRIBE() { return this.TRIBE; }
			public String getTRIBE(int digits) { return new BigDecimal(this.TRIBE).round(new MathContext(digits, RoundingMode.UP)).toPlainString(); }
			public BigDecimal getTRIBE_asBigDecimal() { return new BigDecimal(this.TRIBE); }
			
			private Double MCO2;
			public void setMCO2(Double passVar) { this.MCO2 = passVar; }
			public Double getMCO2() { return this.MCO2; }
			public String getMCO2(int digits) { return new BigDecimal(this.MCO2).round(new MathContext(digits, RoundingMode.UP)).toPlainString(); }
			public BigDecimal getMCO2_asBigDecimal() { return new BigDecimal(this.MCO2); }
			
			private Double IDEX;
			public void setIDEX(Double passVar) { this.IDEX = passVar; }
			public Double getIDEX() { return this.IDEX; }
			public String getIDEX(int digits) { return new BigDecimal(this.IDEX).round(new MathContext(digits, RoundingMode.UP)).toPlainString(); }
			public BigDecimal getIDEX_asBigDecimal() { return new BigDecimal(this.IDEX); }
			
			private Double DOGE;
			public void setDOGE(Double passVar) { this.DOGE = passVar; }
			public Double getDOGE() { return this.DOGE; }
			public String getDOGE(int digits) { return new BigDecimal(this.DOGE).round(new MathContext(digits, RoundingMode.UP)).toPlainString(); }
			public BigDecimal getDOGE_asBigDecimal() { return new BigDecimal(this.DOGE); }
			
			private Double FARM;
			public void setFARM(Double passVar) { this.FARM = passVar; }
			public Double getFARM() { return this.FARM; }
			public String getFARM(int digits) { return new BigDecimal(this.FARM).round(new MathContext(digits, RoundingMode.UP)).toPlainString(); }
			public BigDecimal getFARM_asBigDecimal() { return new BigDecimal(this.FARM); }
			
			private Double ANKR;
			public void setANKR(Double passVar) { this.ANKR = passVar; }
			public Double getANKR() { return this.ANKR; }
			public String getANKR(int digits) { return new BigDecimal(this.ANKR).round(new MathContext(digits, RoundingMode.UP)).toPlainString(); }
			public BigDecimal getANKR_asBigDecimal() { return new BigDecimal(this.ANKR); }
			
			private Double XRP;
			public void setXRP(Double passVar) { this.XRP = passVar; }
			public Double getXRP() { return this.XRP; }
			public String getXRP(int digits) { return new BigDecimal(this.XRP).round(new MathContext(digits, RoundingMode.UP)).toPlainString(); }
			public BigDecimal getXRP_asBigDecimal() { return new BigDecimal(this.XRP); }
			
			private Double GYEN;
			public void setGYEN(Double passVar) { this.GYEN = passVar; }
			public Double getGYEN() { return this.GYEN; }
			public String getGYEN(int digits) { return new BigDecimal(this.GYEN).round(new MathContext(digits, RoundingMode.UP)).toPlainString(); }
			public BigDecimal getGYEN_asBigDecimal() { return new BigDecimal(this.GYEN); }
			
			private Double ALCX;
			public void setALCX(Double passVar) { this.ALCX = passVar; }
			public Double getALCX() { return this.ALCX; }
			public String getALCX(int digits) { return new BigDecimal(this.ALCX).round(new MathContext(digits, RoundingMode.UP)).toPlainString(); }
			public BigDecimal getALCX_asBigDecimal() { return new BigDecimal(this.ALCX); }
			
			private Double IMX;
			public void setIMX(Double passVar) { this.IMX = passVar; }
			public Double getIMX() { return this.IMX; }
			public String getIMX(int digits) { return new BigDecimal(this.IMX).round(new MathContext(digits, RoundingMode.UP)).toPlainString(); }
			public BigDecimal getIMX_asBigDecimal() { return new BigDecimal(this.IMX); }
			
			private Double ETC;
			public void setETC(Double passVar) { this.ETC = passVar; }
			public Double getETC() { return this.ETC; }
			public String getETC(int digits) { return new BigDecimal(this.ETC).round(new MathContext(digits, RoundingMode.UP)).toPlainString(); }
			public BigDecimal getETC_asBigDecimal() { return new BigDecimal(this.ETC); }
			
			private Double MANA;
			public void setMANA(Double passVar) { this.MANA = passVar; }
			public Double getMANA() { return this.MANA; }
			public String getMANA(int digits) { return new BigDecimal(this.MANA).round(new MathContext(digits, RoundingMode.UP)).toPlainString(); }
			public BigDecimal getMANA_asBigDecimal() { return new BigDecimal(this.MANA); }
			
			private Double RAD;
			public void setRAD(Double passVar) { this.RAD = passVar; }
			public Double getRAD() { return this.RAD; }
			public String getRAD(int digits) { return new BigDecimal(this.RAD).round(new MathContext(digits, RoundingMode.UP)).toPlainString(); }
			public BigDecimal getRAD_asBigDecimal() { return new BigDecimal(this.RAD); }
			
			private Double KRL;
			public void setKRL(Double passVar) { this.KRL = passVar; }
			public Double getKRL() { return this.KRL; }
			public String getKRL(int digits) { return new BigDecimal(this.KRL).round(new MathContext(digits, RoundingMode.UP)).toPlainString(); }
			public BigDecimal getKRL_asBigDecimal() { return new BigDecimal(this.KRL); }
			
			private Double API3;
			public void setAPI3(Double passVar) { this.API3 = passVar; }
			public Double getAPI3() { return this.API3; }
			public String getAPI3(int digits) { return new BigDecimal(this.API3).round(new MathContext(digits, RoundingMode.UP)).toPlainString(); }
			public BigDecimal getAPI3_asBigDecimal() { return new BigDecimal(this.API3); }
			
			private Double SHIB;
			public void setSHIB(Double passVar) { this.SHIB = passVar; }
			public Double getSHIB() { return this.SHIB; }
			public String getSHIB(int digits) { return new BigDecimal(this.SHIB).round(new MathContext(digits, RoundingMode.UP)).toPlainString(); }
			public BigDecimal getSHIB_asBigDecimal() { return new BigDecimal(this.SHIB); }
			
			private Double AED;
			public void setAED(Double passVar) { this.AED = passVar; }
			public Double getAED() { return this.AED; }
			public String getAED(int digits) { return new BigDecimal(this.AED).round(new MathContext(digits, RoundingMode.UP)).toPlainString(); }
			public BigDecimal getAED_asBigDecimal() { return new BigDecimal(this.AED); }
			
			private Double AFN;
			public void setAFN(Double passVar) { this.AFN = passVar; }
			public Double getAFN() { return this.AFN; }
			public String getAFN(int digits) { return new BigDecimal(this.AFN).round(new MathContext(digits, RoundingMode.UP)).toPlainString(); }
			public BigDecimal getAFN_asBigDecimal() { return new BigDecimal(this.AFN); }
			
			private Double ALL;
			public void setALL(Double passVar) { this.ALL = passVar; }
			public Double getALL() { return this.ALL; }
			public String getALL(int digits) { return new BigDecimal(this.ALL).round(new MathContext(digits, RoundingMode.UP)).toPlainString(); }
			public BigDecimal getALL_asBigDecimal() { return new BigDecimal(this.ALL); }
			
			private Double AMD;
			public void setAMD(Double passVar) { this.AMD = passVar; }
			public Double getAMD() { return this.AMD; }
			public String getAMD(int digits) { return new BigDecimal(this.AMD).round(new MathContext(digits, RoundingMode.UP)).toPlainString(); }
			public BigDecimal getAMD_asBigDecimal() { return new BigDecimal(this.AMD); }
			
			private Double ANG;
			public void setANG(Double passVar) { this.ANG = passVar; }
			public Double getANG() { return this.ANG; }
			public String getANG(int digits) { return new BigDecimal(this.ANG).round(new MathContext(digits, RoundingMode.UP)).toPlainString(); }
			public BigDecimal getANG_asBigDecimal() { return new BigDecimal(this.ANG); }
			
			private Double AOA;
			public void setAOA(Double passVar) { this.AOA = passVar; }
			public Double getAOA() { return this.AOA; }
			public String getAOA(int digits) { return new BigDecimal(this.AOA).round(new MathContext(digits, RoundingMode.UP)).toPlainString(); }
			public BigDecimal getAOA_asBigDecimal() { return new BigDecimal(this.AOA); }
			
			private Double ARS;
			public void setARS(Double passVar) { this.ARS = passVar; }
			public Double getARS() { return this.ARS; }
			public String getARS(int digits) { return new BigDecimal(this.ARS).round(new MathContext(digits, RoundingMode.UP)).toPlainString(); }
			public BigDecimal getARS_asBigDecimal() { return new BigDecimal(this.ARS); }
			
			private Double AUD;
			public void setAUD(Double passVar) { this.AUD = passVar; }
			public Double getAUD() { return this.AUD; }
			public String getAUD(int digits) { return new BigDecimal(this.AUD).round(new MathContext(digits, RoundingMode.UP)).toPlainString(); }
			public BigDecimal getAUD_asBigDecimal() { return new BigDecimal(this.AUD); }
			
			private Double AWG;
			public void setAWG(Double passVar) { this.AWG = passVar; }
			public Double getAWG() { return this.AWG; }
			public String getAWG(int digits) { return new BigDecimal(this.AWG).round(new MathContext(digits, RoundingMode.UP)).toPlainString(); }
			public BigDecimal getAWG_asBigDecimal() { return new BigDecimal(this.AWG); }
			
			private Double AZN;
			public void setAZN(Double passVar) { this.AZN = passVar; }
			public Double getAZN() { return this.AZN; }
			public String getAZN(int digits) { return new BigDecimal(this.AZN).round(new MathContext(digits, RoundingMode.UP)).toPlainString(); }
			public BigDecimal getAZN_asBigDecimal() { return new BigDecimal(this.AZN); }
			
			private Double BAM;
			public void setBAM(Double passVar) { this.BAM = passVar; }
			public Double getBAM() { return this.BAM; }
			public String getBAM(int digits) { return new BigDecimal(this.BAM).round(new MathContext(digits, RoundingMode.UP)).toPlainString(); }
			public BigDecimal getBAM_asBigDecimal() { return new BigDecimal(this.BAM); }
			
			private Double BBD;
			public void setBBD(Double passVar) { this.BBD = passVar; }
			public Double getBBD() { return this.BBD; }
			public String getBBD(int digits) { return new BigDecimal(this.BBD).round(new MathContext(digits, RoundingMode.UP)).toPlainString(); }
			public BigDecimal getBBD_asBigDecimal() { return new BigDecimal(this.BBD); }
			
			private Double BDT;
			public void setBDT(Double passVar) { this.BDT = passVar; }
			public Double getBDT() { return this.BDT; }
			public String getBDT(int digits) { return new BigDecimal(this.BDT).round(new MathContext(digits, RoundingMode.UP)).toPlainString(); }
			public BigDecimal getBDT_asBigDecimal() { return new BigDecimal(this.BDT); }
			
			private Double BGN;
			public void setBGN(Double passVar) { this.BGN = passVar; }
			public Double getBGN() { return this.BGN; }
			public String getBGN(int digits) { return new BigDecimal(this.BGN).round(new MathContext(digits, RoundingMode.UP)).toPlainString(); }
			public BigDecimal getBGN_asBigDecimal() { return new BigDecimal(this.BGN); }
			
			private Double BHD;
			public void setBHD(Double passVar) { this.BHD = passVar; }
			public Double getBHD() { return this.BHD; }
			public String getBHD(int digits) { return new BigDecimal(this.BHD).round(new MathContext(digits, RoundingMode.UP)).toPlainString(); }
			public BigDecimal getBHD_asBigDecimal() { return new BigDecimal(this.BHD); }
			
			private Double BIF;
			public void setBIF(Double passVar) { this.BIF = passVar; }
			public Double getBIF() { return this.BIF; }
			public String getBIF(int digits) { return new BigDecimal(this.BIF).round(new MathContext(digits, RoundingMode.UP)).toPlainString(); }
			public BigDecimal getBIF_asBigDecimal() { return new BigDecimal(this.BIF); }
			
			private Double BMD;
			public void setBMD(Double passVar) { this.BMD = passVar; }
			public Double getBMD() { return this.BMD; }
			public String getBMD(int digits) { return new BigDecimal(this.BMD).round(new MathContext(digits, RoundingMode.UP)).toPlainString(); }
			public BigDecimal getBMD_asBigDecimal() { return new BigDecimal(this.BMD); }
			
			private Double BND;
			public void setBND(Double passVar) { this.BND = passVar; }
			public Double getBND() { return this.BND; }
			public String getBND(int digits) { return new BigDecimal(this.BND).round(new MathContext(digits, RoundingMode.UP)).toPlainString(); }
			public BigDecimal getBND_asBigDecimal() { return new BigDecimal(this.BND); }
			
			private Double BOB;
			public void setBOB(Double passVar) { this.BOB = passVar; }
			public Double getBOB() { return this.BOB; }
			public String getBOB(int digits) { return new BigDecimal(this.BOB).round(new MathContext(digits, RoundingMode.UP)).toPlainString(); }
			public BigDecimal getBOB_asBigDecimal() { return new BigDecimal(this.BOB); }
			
			private Double BRL;
			public void setBRL(Double passVar) { this.BRL = passVar; }
			public Double getBRL() { return this.BRL; }
			public String getBRL(int digits) { return new BigDecimal(this.BRL).round(new MathContext(digits, RoundingMode.UP)).toPlainString(); }
			public BigDecimal getBRL_asBigDecimal() { return new BigDecimal(this.BRL); }
			
			private Double BSD;
			public void setBSD(Double passVar) { this.BSD = passVar; }
			public Double getBSD() { return this.BSD; }
			public String getBSD(int digits) { return new BigDecimal(this.BSD).round(new MathContext(digits, RoundingMode.UP)).toPlainString(); }
			public BigDecimal getBSD_asBigDecimal() { return new BigDecimal(this.BSD); }
			
			private Double BTN;
			public void setBTN(Double passVar) { this.BTN = passVar; }
			public Double getBTN() { return this.BTN; }
			public String getBTN(int digits) { return new BigDecimal(this.BTN).round(new MathContext(digits, RoundingMode.UP)).toPlainString(); }
			public BigDecimal getBTN_asBigDecimal() { return new BigDecimal(this.BTN); }
			
			private Double BWP;
			public void setBWP(Double passVar) { this.BWP = passVar; }
			public Double getBWP() { return this.BWP; }
			public String getBWP(int digits) { return new BigDecimal(this.BWP).round(new MathContext(digits, RoundingMode.UP)).toPlainString(); }
			public BigDecimal getBWP_asBigDecimal() { return new BigDecimal(this.BWP); }
			
			private Double BYN;
			public void setBYN(Double passVar) { this.BYN = passVar; }
			public Double getBYN() { return this.BYN; }
			public String getBYN(int digits) { return new BigDecimal(this.BYN).round(new MathContext(digits, RoundingMode.UP)).toPlainString(); }
			public BigDecimal getBYN_asBigDecimal() { return new BigDecimal(this.BYN); }
			
			private Double BYR;
			public void setBYR(Double passVar) { this.BYR = passVar; }
			public Double getBYR() { return this.BYR; }
			public String getBYR(int digits) { return new BigDecimal(this.BYR).round(new MathContext(digits, RoundingMode.UP)).toPlainString(); }
			public BigDecimal getBYR_asBigDecimal() { return new BigDecimal(this.BYR); }
			
			private Double BZD;
			public void setBZD(Double passVar) { this.BZD = passVar; }
			public Double getBZD() { return this.BZD; }
			public String getBZD(int digits) { return new BigDecimal(this.BZD).round(new MathContext(digits, RoundingMode.UP)).toPlainString(); }
			public BigDecimal getBZD_asBigDecimal() { return new BigDecimal(this.BZD); }
			
			private Double CAD;
			public void setCAD(Double passVar) { this.CAD = passVar; }
			public Double getCAD() { return this.CAD; }
			public String getCAD(int digits) { return new BigDecimal(this.CAD).round(new MathContext(digits, RoundingMode.UP)).toPlainString(); }
			public BigDecimal getCAD_asBigDecimal() { return new BigDecimal(this.CAD); }
			
			private Double CDF;
			public void setCDF(Double passVar) { this.CDF = passVar; }
			public Double getCDF() { return this.CDF; }
			public String getCDF(int digits) { return new BigDecimal(this.CDF).round(new MathContext(digits, RoundingMode.UP)).toPlainString(); }
			public BigDecimal getCDF_asBigDecimal() { return new BigDecimal(this.CDF); }
			
			private Double CHF;
			public void setCHF(Double passVar) { this.CHF = passVar; }
			public Double getCHF() { return this.CHF; }
			public String getCHF(int digits) { return new BigDecimal(this.CHF).round(new MathContext(digits, RoundingMode.UP)).toPlainString(); }
			public BigDecimal getCHF_asBigDecimal() { return new BigDecimal(this.CHF); }
			
			private Double CLF;
			public void setCLF(Double passVar) { this.CLF = passVar; }
			public Double getCLF() { return this.CLF; }
			public String getCLF(int digits) { return new BigDecimal(this.CLF).round(new MathContext(digits, RoundingMode.UP)).toPlainString(); }
			public BigDecimal getCLF_asBigDecimal() { return new BigDecimal(this.CLF); }
			
			private Double CLP;
			public void setCLP(Double passVar) { this.CLP = passVar; }
			public Double getCLP() { return this.CLP; }
			public String getCLP(int digits) { return new BigDecimal(this.CLP).round(new MathContext(digits, RoundingMode.UP)).toPlainString(); }
			public BigDecimal getCLP_asBigDecimal() { return new BigDecimal(this.CLP); }
			
			private Double CNH;
			public void setCNH(Double passVar) { this.CNH = passVar; }
			public Double getCNH() { return this.CNH; }
			public String getCNH(int digits) { return new BigDecimal(this.CNH).round(new MathContext(digits, RoundingMode.UP)).toPlainString(); }
			public BigDecimal getCNH_asBigDecimal() { return new BigDecimal(this.CNH); }
			
			private Double CNY;
			public void setCNY(Double passVar) { this.CNY = passVar; }
			public Double getCNY() { return this.CNY; }
			public String getCNY(int digits) { return new BigDecimal(this.CNY).round(new MathContext(digits, RoundingMode.UP)).toPlainString(); }
			public BigDecimal getCNY_asBigDecimal() { return new BigDecimal(this.CNY); }
			
			private Double COP;
			public void setCOP(Double passVar) { this.COP = passVar; }
			public Double getCOP() { return this.COP; }
			public String getCOP(int digits) { return new BigDecimal(this.COP).round(new MathContext(digits, RoundingMode.UP)).toPlainString(); }
			public BigDecimal getCOP_asBigDecimal() { return new BigDecimal(this.COP); }
			
			private Double CRC;
			public void setCRC(Double passVar) { this.CRC = passVar; }
			public Double getCRC() { return this.CRC; }
			public String getCRC(int digits) { return new BigDecimal(this.CRC).round(new MathContext(digits, RoundingMode.UP)).toPlainString(); }
			public BigDecimal getCRC_asBigDecimal() { return new BigDecimal(this.CRC); }
			
			private Double CUC;
			public void setCUC(Double passVar) { this.CUC = passVar; }
			public Double getCUC() { return this.CUC; }
			public String getCUC(int digits) { return new BigDecimal(this.CUC).round(new MathContext(digits, RoundingMode.UP)).toPlainString(); }
			public BigDecimal getCUC_asBigDecimal() { return new BigDecimal(this.CUC); }
			
			private Double CVE;
			public void setCVE(Double passVar) { this.CVE = passVar; }
			public Double getCVE() { return this.CVE; }
			public String getCVE(int digits) { return new BigDecimal(this.CVE).round(new MathContext(digits, RoundingMode.UP)).toPlainString(); }
			public BigDecimal getCVE_asBigDecimal() { return new BigDecimal(this.CVE); }
			
			private Double CZK;
			public void setCZK(Double passVar) { this.CZK = passVar; }
			public Double getCZK() { return this.CZK; }
			public String getCZK(int digits) { return new BigDecimal(this.CZK).round(new MathContext(digits, RoundingMode.UP)).toPlainString(); }
			public BigDecimal getCZK_asBigDecimal() { return new BigDecimal(this.CZK); }
			
			private Double DJF;
			public void setDJF(Double passVar) { this.DJF = passVar; }
			public Double getDJF() { return this.DJF; }
			public String getDJF(int digits) { return new BigDecimal(this.DJF).round(new MathContext(digits, RoundingMode.UP)).toPlainString(); }
			public BigDecimal getDJF_asBigDecimal() { return new BigDecimal(this.DJF); }
			
			private Double DKK;
			public void setDKK(Double passVar) { this.DKK = passVar; }
			public Double getDKK() { return this.DKK; }
			public String getDKK(int digits) { return new BigDecimal(this.DKK).round(new MathContext(digits, RoundingMode.UP)).toPlainString(); }
			public BigDecimal getDKK_asBigDecimal() { return new BigDecimal(this.DKK); }
			
			private Double DOP;
			public void setDOP(Double passVar) { this.DOP = passVar; }
			public Double getDOP() { return this.DOP; }
			public String getDOP(int digits) { return new BigDecimal(this.DOP).round(new MathContext(digits, RoundingMode.UP)).toPlainString(); }
			public BigDecimal getDOP_asBigDecimal() { return new BigDecimal(this.DOP); }
			
			private Double DZD;
			public void setDZD(Double passVar) { this.DZD = passVar; }
			public Double getDZD() { return this.DZD; }
			public String getDZD(int digits) { return new BigDecimal(this.DZD).round(new MathContext(digits, RoundingMode.UP)).toPlainString(); }
			public BigDecimal getDZD_asBigDecimal() { return new BigDecimal(this.DZD); }
			
			private Double EGP;
			public void setEGP(Double passVar) { this.EGP = passVar; }
			public Double getEGP() { return this.EGP; }
			public String getEGP(int digits) { return new BigDecimal(this.EGP).round(new MathContext(digits, RoundingMode.UP)).toPlainString(); }
			public BigDecimal getEGP_asBigDecimal() { return new BigDecimal(this.EGP); }
			
			private Double ETB;
			public void setETB(Double passVar) { this.ETB = passVar; }
			public Double getETB() { return this.ETB; }
			public String getETB(int digits) { return new BigDecimal(this.ETB).round(new MathContext(digits, RoundingMode.UP)).toPlainString(); }
			public BigDecimal getETB_asBigDecimal() { return new BigDecimal(this.ETB); }
			
			private Double FJD;
			public void setFJD(Double passVar) { this.FJD = passVar; }
			public Double getFJD() { return this.FJD; }
			public String getFJD(int digits) { return new BigDecimal(this.FJD).round(new MathContext(digits, RoundingMode.UP)).toPlainString(); }
			public BigDecimal getFJD_asBigDecimal() { return new BigDecimal(this.FJD); }
			
			private Double FKP;
			public void setFKP(Double passVar) { this.FKP = passVar; }
			public Double getFKP() { return this.FKP; }
			public String getFKP(int digits) { return new BigDecimal(this.FKP).round(new MathContext(digits, RoundingMode.UP)).toPlainString(); }
			public BigDecimal getFKP_asBigDecimal() { return new BigDecimal(this.FKP); }
			
			private Double GBX;
			public void setGBX(Double passVar) { this.GBX = passVar; }
			public Double getGBX() { return this.GBX; }
			public String getGBX(int digits) { return new BigDecimal(this.GBX).round(new MathContext(digits, RoundingMode.UP)).toPlainString(); }
			public BigDecimal getGBX_asBigDecimal() { return new BigDecimal(this.GBX); }
			
			private Double GGP;
			public void setGGP(Double passVar) { this.GGP = passVar; }
			public Double getGGP() { return this.GGP; }
			public String getGGP(int digits) { return new BigDecimal(this.GGP).round(new MathContext(digits, RoundingMode.UP)).toPlainString(); }
			public BigDecimal getGGP_asBigDecimal() { return new BigDecimal(this.GGP); }
			
			private Double GHS;
			public void setGHS(Double passVar) { this.GHS = passVar; }
			public Double getGHS() { return this.GHS; }
			public String getGHS(int digits) { return new BigDecimal(this.GHS).round(new MathContext(digits, RoundingMode.UP)).toPlainString(); }
			public BigDecimal getGHS_asBigDecimal() { return new BigDecimal(this.GHS); }
			
			private Double GIP;
			public void setGIP(Double passVar) { this.GIP = passVar; }
			public Double getGIP() { return this.GIP; }
			public String getGIP(int digits) { return new BigDecimal(this.GIP).round(new MathContext(digits, RoundingMode.UP)).toPlainString(); }
			public BigDecimal getGIP_asBigDecimal() { return new BigDecimal(this.GIP); }
			
			private Double GMD;
			public void setGMD(Double passVar) { this.GMD = passVar; }
			public Double getGMD() { return this.GMD; }
			public String getGMD(int digits) { return new BigDecimal(this.GMD).round(new MathContext(digits, RoundingMode.UP)).toPlainString(); }
			public BigDecimal getGMD_asBigDecimal() { return new BigDecimal(this.GMD); }
			
			private Double GNF;
			public void setGNF(Double passVar) { this.GNF = passVar; }
			public Double getGNF() { return this.GNF; }
			public String getGNF(int digits) { return new BigDecimal(this.GNF).round(new MathContext(digits, RoundingMode.UP)).toPlainString(); }
			public BigDecimal getGNF_asBigDecimal() { return new BigDecimal(this.GNF); }
			
			private Double GTQ;
			public void setGTQ(Double passVar) { this.GTQ = passVar; }
			public Double getGTQ() { return this.GTQ; }
			public String getGTQ(int digits) { return new BigDecimal(this.GTQ).round(new MathContext(digits, RoundingMode.UP)).toPlainString(); }
			public BigDecimal getGTQ_asBigDecimal() { return new BigDecimal(this.GTQ); }
			
			private Double GYD;
			public void setGYD(Double passVar) { this.GYD = passVar; }
			public Double getGYD() { return this.GYD; }
			public String getGYD(int digits) { return new BigDecimal(this.GYD).round(new MathContext(digits, RoundingMode.UP)).toPlainString(); }
			public BigDecimal getGYD_asBigDecimal() { return new BigDecimal(this.GYD); }
			
			private Double HKD;
			public void setHKD(Double passVar) { this.HKD = passVar; }
			public Double getHKD() { return this.HKD; }
			public String getHKD(int digits) { return new BigDecimal(this.HKD).round(new MathContext(digits, RoundingMode.UP)).toPlainString(); }
			public BigDecimal getHKD_asBigDecimal() { return new BigDecimal(this.HKD); }
			
			private Double HNL;
			public void setHNL(Double passVar) { this.HNL = passVar; }
			public Double getHNL() { return this.HNL; }
			public String getHNL(int digits) { return new BigDecimal(this.HNL).round(new MathContext(digits, RoundingMode.UP)).toPlainString(); }
			public BigDecimal getHNL_asBigDecimal() { return new BigDecimal(this.HNL); }
			
			private Double HRK;
			public void setHRK(Double passVar) { this.HRK = passVar; }
			public Double getHRK() { return this.HRK; }
			public String getHRK(int digits) { return new BigDecimal(this.HRK).round(new MathContext(digits, RoundingMode.UP)).toPlainString(); }
			public BigDecimal getHRK_asBigDecimal() { return new BigDecimal(this.HRK); }
			
			private Double HTG;
			public void setHTG(Double passVar) { this.HTG = passVar; }
			public Double getHTG() { return this.HTG; }
			public String getHTG(int digits) { return new BigDecimal(this.HTG).round(new MathContext(digits, RoundingMode.UP)).toPlainString(); }
			public BigDecimal getHTG_asBigDecimal() { return new BigDecimal(this.HTG); }
			
			private Double HUF;
			public void setHUF(Double passVar) { this.HUF = passVar; }
			public Double getHUF() { return this.HUF; }
			public String getHUF(int digits) { return new BigDecimal(this.HUF).round(new MathContext(digits, RoundingMode.UP)).toPlainString(); }
			public BigDecimal getHUF_asBigDecimal() { return new BigDecimal(this.HUF); }

			private Double IDR;
			public void setIDR(Double passVar) { this.IDR = passVar; }
			public Double getIDR() { return this.IDR; }
			public String getIDR(int digits) { return new BigDecimal(this.IDR).round(new MathContext(digits, RoundingMode.UP)).toPlainString(); }
			public BigDecimal getIDR_asBigDecimal() { return new BigDecimal(this.IDR); }
			
			private Double ILS;
			public void setILS(Double passVar) { this.ILS = passVar; }
			public Double getILS() { return this.ILS; }
			public String getILS(int digits) { return new BigDecimal(this.ILS).round(new MathContext(digits, RoundingMode.UP)).toPlainString(); }
			public BigDecimal getILS_asBigDecimal() { return new BigDecimal(this.ILS); }
			
			private Double IMP;
			public void setIMP(Double passVar) { this.IMP = passVar; }
			public Double getIMP() { return this.IMP; }
			public String getIMP(int digits) { return new BigDecimal(this.IMP).round(new MathContext(digits, RoundingMode.UP)).toPlainString(); }
			public BigDecimal getIMP_asBigDecimal() { return new BigDecimal(this.IMP); }
			
			private Double INR;
			public void setINR(Double passVar) { this.INR = passVar; }
			public Double getINR() { return this.INR; }
			public String getINR(int digits) { return new BigDecimal(this.INR).round(new MathContext(digits, RoundingMode.UP)).toPlainString(); }
			public BigDecimal getINR_asBigDecimal() { return new BigDecimal(this.INR); }
			
			private Double IQD;
			public void setIQD(Double passVar) { this.IQD = passVar; }
			public Double getIQD() { return this.IQD; }
			public String getIQD(int digits) { return new BigDecimal(this.IQD).round(new MathContext(digits, RoundingMode.UP)).toPlainString(); }
			public BigDecimal getIQD_asBigDecimal() { return new BigDecimal(this.IQD); }
			
			private Double ISK;
			public void setISK(Double passVar) { this.ISK = passVar; }
			public Double getISK() { return this.ISK; }
			public String getISK(int digits) { return new BigDecimal(this.ISK).round(new MathContext(digits, RoundingMode.UP)).toPlainString(); }
			public BigDecimal getISK_asBigDecimal() { return new BigDecimal(this.ISK); }
			
			private Double JEP;
			public void setJEP(Double passVar) { this.JEP = passVar; }
			public Double getJEP() { return this.JEP; }
			public String getJEP(int digits) { return new BigDecimal(this.JEP).round(new MathContext(digits, RoundingMode.UP)).toPlainString(); }
			public BigDecimal getJEP_asBigDecimal() { return new BigDecimal(this.JEP); }
			
			private Double JMD;
			public void setJMD(Double passVar) { this.JMD = passVar; }
			public Double getJMD() { return this.JMD; }
			public String getJMD(int digits) { return new BigDecimal(this.JMD).round(new MathContext(digits, RoundingMode.UP)).toPlainString(); }
			public BigDecimal getJMD_asBigDecimal() { return new BigDecimal(this.JMD); }
			
			private Double JOD;
			public void setJOD(Double passVar) { this.JOD = passVar; }
			public Double getJOD() { return this.JOD; }
			public String getJOD(int digits) { return new BigDecimal(this.JOD).round(new MathContext(digits, RoundingMode.UP)).toPlainString(); }
			public BigDecimal getJOD_asBigDecimal() { return new BigDecimal(this.JOD); }
			
			private Double JPY;
			public void setJPY(Double passVar) { this.JPY = passVar; }
			public Double getJPY() { return this.JPY; }
			public String getJPY(int digits) { return new BigDecimal(this.JPY).round(new MathContext(digits, RoundingMode.UP)).toPlainString(); }
			public BigDecimal getJPY_asBigDecimal() { return new BigDecimal(this.JPY); }
			
			private Double KES;
			public void setKES(Double passVar) { this.KES = passVar; }
			public Double getKES() { return this.KES; }
			public String getKES(int digits) { return new BigDecimal(this.KES).round(new MathContext(digits, RoundingMode.UP)).toPlainString(); }
			public BigDecimal getKES_asBigDecimal() { return new BigDecimal(this.KES); }
			
			private Double KGS;
			public void setKGS(Double passVar) { this.KGS = passVar; }
			public Double getKGS() { return this.KGS; }
			public String getKGS(int digits) { return new BigDecimal(this.KGS).round(new MathContext(digits, RoundingMode.UP)).toPlainString(); }
			public BigDecimal getKGS_asBigDecimal() { return new BigDecimal(this.KGS); }
			
			private Double KHR;
			public void setKHR(Double passVar) { this.KHR = passVar; }
			public Double getKHR() { return this.KHR; }
			public String getKHR(int digits) { return new BigDecimal(this.KHR).round(new MathContext(digits, RoundingMode.UP)).toPlainString(); }
			public BigDecimal getKHR_asBigDecimal() { return new BigDecimal(this.KHR); }
			
			private Double KMF;
			public void setKMF(Double passVar) { this.KMF = passVar; }
			public Double getKMF() { return this.KMF; }
			public String getKMF(int digits) { return new BigDecimal(this.KMF).round(new MathContext(digits, RoundingMode.UP)).toPlainString(); }
			public BigDecimal getKMF_asBigDecimal() { return new BigDecimal(this.KMF); }
			
			private Double KRW;
			public void setKRW(Double passVar) { this.KRW = passVar; }
			public Double getKRW() { return this.KRW; }
			public String getKRW(int digits) { return new BigDecimal(this.KRW).round(new MathContext(digits, RoundingMode.UP)).toPlainString(); }
			public BigDecimal getKRW_asBigDecimal() { return new BigDecimal(this.KRW); }
			
			private Double KWD;
			public void setKWD(Double passVar) { this.KWD = passVar; }
			public Double getKWD() { return this.KWD; }
			public String getKWD(int digits) { return new BigDecimal(this.KWD).round(new MathContext(digits, RoundingMode.UP)).toPlainString(); }
			public BigDecimal getKWD_asBigDecimal() { return new BigDecimal(this.KWD); }
			
			private Double KYD;
			public void setKYD(Double passVar) { this.KYD = passVar; }
			public Double getKYD() { return this.KYD; }
			public String getKYD(int digits) { return new BigDecimal(this.KYD).round(new MathContext(digits, RoundingMode.UP)).toPlainString(); }
			public BigDecimal getKYD_asBigDecimal() { return new BigDecimal(this.KYD); }
			
			private Double KZT;
			public void setKZT(Double passVar) { this.KZT = passVar; }
			public Double getKZT() { return this.KZT; }
			public String getKZT(int digits) { return new BigDecimal(this.KZT).round(new MathContext(digits, RoundingMode.UP)).toPlainString(); }
			public BigDecimal getKZT_asBigDecimal() { return new BigDecimal(this.KZT); }
			
			private Double LAK;
			public void setLAK(Double passVar) { this.LAK = passVar; }
			public Double getLAK() { return this.LAK; }
			public String getLAK(int digits) { return new BigDecimal(this.LAK).round(new MathContext(digits, RoundingMode.UP)).toPlainString(); }
			public BigDecimal getLAK_asBigDecimal() { return new BigDecimal(this.LAK); }
			
			private Double LBP;
			public void setLBP(Double passVar) { this.LBP = passVar; }
			public Double getLBP() { return this.LBP; }
			public String getLBP(int digits) { return new BigDecimal(this.LBP).round(new MathContext(digits, RoundingMode.UP)).toPlainString(); }
			public BigDecimal getLBP_asBigDecimal() { return new BigDecimal(this.LBP); }
			
			private Double LKR;
			public void setLKR(Double passVar) { this.LKR = passVar; }
			public Double getLKR() { return this.LKR; }
			public String getLKR(int digits) { return new BigDecimal(this.LKR).round(new MathContext(digits, RoundingMode.UP)).toPlainString(); }
			public BigDecimal getLKR_asBigDecimal() { return new BigDecimal(this.LKR); }
			
			private Double LRD;
			public void setLRD(Double passVar) { this.LRD = passVar; }
			public Double getLRD() { return this.LRD; }
			public String getLRD(int digits) { return new BigDecimal(this.LRD).round(new MathContext(digits, RoundingMode.UP)).toPlainString(); }
			public BigDecimal getLRD_asBigDecimal() { return new BigDecimal(this.LRD); }
			
			private Double LSL;
			public void setLSL(Double passVar) { this.LSL = passVar; }
			public Double getLSL() { return this.LSL; }
			public String getLSL(int digits) { return new BigDecimal(this.LSL).round(new MathContext(digits, RoundingMode.UP)).toPlainString(); }
			public BigDecimal getLSL_asBigDecimal() { return new BigDecimal(this.LSL); }
			
			private Double LYD;
			public void setLYD(Double passVar) { this.LYD = passVar; }
			public Double getLYD() { return this.LYD; }
			public String getLYD(int digits) { return new BigDecimal(this.LYD).round(new MathContext(digits, RoundingMode.UP)).toPlainString(); }
			public BigDecimal getLYD_asBigDecimal() { return new BigDecimal(this.LYD); }
			
			private Double MAD;
			public void setMAD(Double passVar) { this.MAD = passVar; }
			public Double getMAD() { return this.MAD; }
			public String getMAD(int digits) { return new BigDecimal(this.MAD).round(new MathContext(digits, RoundingMode.UP)).toPlainString(); }
			public BigDecimal getMAD_asBigDecimal() { return new BigDecimal(this.MAD); }
			
			private Double MDL;
			public void setMDL(Double passVar) { this.MDL = passVar; }
			public Double getMDL() { return this.MDL; }
			public String getMDL(int digits) { return new BigDecimal(this.MDL).round(new MathContext(digits, RoundingMode.UP)).toPlainString(); }
			public BigDecimal getMDL_asBigDecimal() { return new BigDecimal(this.MDL); }
			
			private Double MGA;
			public void setMGA(Double passVar) { this.MGA = passVar; }
			public Double getMGA() { return this.MGA; }
			public String getMGA(int digits) { return new BigDecimal(this.MGA).round(new MathContext(digits, RoundingMode.UP)).toPlainString(); }
			public BigDecimal getMGA_asBigDecimal() { return new BigDecimal(this.MGA); }
			
			private Double MKD;
			public void setMKD(Double passVar) { this.MKD = passVar; }
			public Double getMKD() { return this.MKD; }
			public String getMKD(int digits) { return new BigDecimal(this.MKD).round(new MathContext(digits, RoundingMode.UP)).toPlainString(); }
			public BigDecimal getMKD_asBigDecimal() { return new BigDecimal(this.MKD); }
			
			private Double MMK;
			public void setMMK(Double passVar) { this.MMK = passVar; }
			public Double getMMK() { return this.MMK; }
			public String getMMK(int digits) { return new BigDecimal(this.MMK).round(new MathContext(digits, RoundingMode.UP)).toPlainString(); }
			public BigDecimal getMMK_asBigDecimal() { return new BigDecimal(this.MMK); }
			
			private Double MNT;
			public void setMNT(Double passVar) { this.MNT = passVar; }
			public Double getMNT() { return this.MNT; }
			public String getMNT(int digits) { return new BigDecimal(this.MNT).round(new MathContext(digits, RoundingMode.UP)).toPlainString(); }
			public BigDecimal getMNT_asBigDecimal() { return new BigDecimal(this.MNT); }
			
			private Double MOP;
			public void setMOP(Double passVar) { this.MOP = passVar; }
			public Double getMOP() { return this.MOP; }
			public String getMOP(int digits) { return new BigDecimal(this.MOP).round(new MathContext(digits, RoundingMode.UP)).toPlainString(); }
			public BigDecimal getMOP_asBigDecimal() { return new BigDecimal(this.MOP); }
			
			private Double MRO;
			public void setMRO(Double passVar) { this.MRO = passVar; }
			public Double getMRO() { return this.MRO; }
			public String getMRO(int digits) { return new BigDecimal(this.MRO).round(new MathContext(digits, RoundingMode.UP)).toPlainString(); }
			public BigDecimal getMRO_asBigDecimal() { return new BigDecimal(this.MRO); }
			
			private Double MUR;
			public void setMUR(Double passVar) { this.MUR = passVar; }
			public Double getMUR() { return this.MUR; }
			public String getMUR(int digits) { return new BigDecimal(this.MUR).round(new MathContext(digits, RoundingMode.UP)).toPlainString(); }
			public BigDecimal getMUR_asBigDecimal() { return new BigDecimal(this.MUR); }
			
			private Double MVR;
			public void setMVR(Double passVar) { this.MVR = passVar; }
			public Double getMVR() { return this.MVR; }
			public String getMVR(int digits) { return new BigDecimal(this.MVR).round(new MathContext(digits, RoundingMode.UP)).toPlainString(); }
			public BigDecimal getMVR_asBigDecimal() { return new BigDecimal(this.MVR); }
			
			private Double MWK;
			public void setMWK(Double passVar) { this.MWK = passVar; }
			public Double getMWK() { return this.MWK; }
			public String getMWK(int digits) { return new BigDecimal(this.MWK).round(new MathContext(digits, RoundingMode.UP)).toPlainString(); }
			public BigDecimal getMWK_asBigDecimal() { return new BigDecimal(this.MWK); }
			
			private Double MXN;
			public void setMXN(Double passVar) { this.MXN = passVar; }
			public Double getMXN() { return this.MXN; }
			public String getMXN(int digits) { return new BigDecimal(this.MXN).round(new MathContext(digits, RoundingMode.UP)).toPlainString(); }
			public BigDecimal getMXN_asBigDecimal() { return new BigDecimal(this.MXN); }
			
			private Double MYR;
			public void setMYR(Double passVar) { this.MYR = passVar; }
			public Double getMYR() { return this.MYR; }
			public String getMYR(int digits) { return new BigDecimal(this.MYR).round(new MathContext(digits, RoundingMode.UP)).toPlainString(); }
			public BigDecimal getMYR_asBigDecimal() { return new BigDecimal(this.MYR); }
			
			private Double MZN;
			public void setMZN(Double passVar) { this.MZN = passVar; }
			public Double getMZN() { return this.MZN; }
			public String getMZN(int digits) { return new BigDecimal(this.MZN).round(new MathContext(digits, RoundingMode.UP)).toPlainString(); }
			public BigDecimal getMZN_asBigDecimal() { return new BigDecimal(this.MZN); }
			
			private Double NAD;
			public void setNAD(Double passVar) { this.NAD = passVar; }
			public Double getNAD() { return this.NAD; }
			public String getNAD(int digits) { return new BigDecimal(this.NAD).round(new MathContext(digits, RoundingMode.UP)).toPlainString(); }
			public BigDecimal getNAD_asBigDecimal() { return new BigDecimal(this.NAD); }
			
			private Double NGN;
			public void setNGN(Double passVar) { this.NGN = passVar; }
			public Double getNGN() { return this.NGN; }
			public String getNGN(int digits) { return new BigDecimal(this.NGN).round(new MathContext(digits, RoundingMode.UP)).toPlainString(); }
			public BigDecimal getNGN_asBigDecimal() { return new BigDecimal(this.NGN); }
			
			private Double NIO;
			public void setNIO(Double passVar) { this.NIO = passVar; }
			public Double getNIO() { return this.NIO; }
			public String getNIO(int digits) { return new BigDecimal(this.NIO).round(new MathContext(digits, RoundingMode.UP)).toPlainString(); }
			public BigDecimal getNIO_asBigDecimal() { return new BigDecimal(this.NIO); }
			
			private Double NOK;
			public void setNOK(Double passVar) { this.NOK = passVar; }
			public Double getNOK() { return this.NOK; }
			public String getNOK(int digits) { return new BigDecimal(this.NOK).round(new MathContext(digits, RoundingMode.UP)).toPlainString(); }
			public BigDecimal getNOK_asBigDecimal() { return new BigDecimal(this.NOK); }
			
			private Double NPR;
			public void setNPR(Double passVar) { this.NPR = passVar; }
			public Double getNPR() { return this.NPR; }
			public String getNPR(int digits) { return new BigDecimal(this.NPR).round(new MathContext(digits, RoundingMode.UP)).toPlainString(); }
			public BigDecimal getNPR_asBigDecimal() { return new BigDecimal(this.NPR); }
			
			private Double NZD;
			public void setNZD(Double passVar) { this.NZD = passVar; }
			public Double getNZD() { return this.NZD; }
			public String getNZD(int digits) { return new BigDecimal(this.NZD).round(new MathContext(digits, RoundingMode.UP)).toPlainString(); }
			public BigDecimal getNZD_asBigDecimal() { return new BigDecimal(this.NZD); }
			
			private Double OMR;
			public void setOMR(Double passVar) { this.OMR = passVar; }
			public Double getOMR() { return this.OMR; }
			public String getOMR(int digits) { return new BigDecimal(this.OMR).round(new MathContext(digits, RoundingMode.UP)).toPlainString(); }
			public BigDecimal getOMR_asBigDecimal() { return new BigDecimal(this.OMR); }
			
			private Double PAB;
			public void setPAB(Double passVar) { this.PAB = passVar; }
			public Double getPAB() { return this.PAB; }
			public String getPAB(int digits) { return new BigDecimal(this.PAB).round(new MathContext(digits, RoundingMode.UP)).toPlainString(); }
			public BigDecimal getPAB_asBigDecimal() { return new BigDecimal(this.PAB); }
			
			private Double PEN;
			public void setPEN(Double passVar) { this.PEN = passVar; }
			public Double getPEN() { return this.PEN; }
			public String getPEN(int digits) { return new BigDecimal(this.PEN).round(new MathContext(digits, RoundingMode.UP)).toPlainString(); }
			public BigDecimal getPEN_asBigDecimal() { return new BigDecimal(this.PEN); }
			
			private Double PGK;
			public void setPGK(Double passVar) { this.PGK = passVar; }
			public Double getPGK() { return this.PGK; }
			public String getPGK(int digits) { return new BigDecimal(this.PGK).round(new MathContext(digits, RoundingMode.UP)).toPlainString(); }
			public BigDecimal getPGK_asBigDecimal() { return new BigDecimal(this.PGK); }
			
			private Double PHP;
			public void setPHP(Double passVar) { this.PHP = passVar; }
			public Double getPHP() { return this.PHP; }
			public String getPHP(int digits) { return new BigDecimal(this.PHP).round(new MathContext(digits, RoundingMode.UP)).toPlainString(); }
			public BigDecimal getPHP_asBigDecimal() { return new BigDecimal(this.PHP); }
			
			private Double PKR;
			public void setPKR(Double passVar) { this.PKR = passVar; }
			public Double getPKR() { return this.PKR; }
			public String getPKR(int digits) { return new BigDecimal(this.PKR).round(new MathContext(digits, RoundingMode.UP)).toPlainString(); }
			public BigDecimal getPKR_asBigDecimal() { return new BigDecimal(this.PKR); }
			
			private Double PLN;
			public void setPLN(Double passVar) { this.PLN = passVar; }
			public Double getPLN() { return this.PLN; }
			public String getPLN(int digits) { return new BigDecimal(this.PLN).round(new MathContext(digits, RoundingMode.UP)).toPlainString(); }
			public BigDecimal getPLN_asBigDecimal() { return new BigDecimal(this.PLN); }
			
			private Double PYG;
			public void setPYG(Double passVar) { this.PYG = passVar; }
			public Double getPYG() { return this.PYG; }
			public String getPYG(int digits) { return new BigDecimal(this.PYG).round(new MathContext(digits, RoundingMode.UP)).toPlainString(); }
			public BigDecimal getPYG_asBigDecimal() { return new BigDecimal(this.PYG); }
			
			private Double QAR;
			public void setQAR(Double passVar) { this.QAR = passVar; }
			public Double getQAR() { return this.QAR; }
			public String getQAR(int digits) { return new BigDecimal(this.QAR).round(new MathContext(digits, RoundingMode.UP)).toPlainString(); }
			public BigDecimal getQAR_asBigDecimal() { return new BigDecimal(this.QAR); }
			
			private Double RON;
			public void setRON(Double passVar) { this.RON = passVar; }
			public Double getRON() { return this.RON; }
			public String getRON(int digits) { return new BigDecimal(this.RON).round(new MathContext(digits, RoundingMode.UP)).toPlainString(); }
			public BigDecimal getRON_asBigDecimal() { return new BigDecimal(this.RON); }
			
			private Double RSD;
			public void setRSD(Double passVar) { this.RSD = passVar; }
			public Double getRSD() { return this.RSD; }
			public String getRSD(int digits) { return new BigDecimal(this.RSD).round(new MathContext(digits, RoundingMode.UP)).toPlainString(); }
			public BigDecimal getRSD_asBigDecimal() { return new BigDecimal(this.RSD); }
			
			private Double RUB;
			public void setRUB(Double passVar) { this.RUB = passVar; }
			public Double getRUB() { return this.RUB; }
			public String getRUB(int digits) { return new BigDecimal(this.RUB).round(new MathContext(digits, RoundingMode.UP)).toPlainString(); }
			public BigDecimal getRUB_asBigDecimal() { return new BigDecimal(this.RUB); }
			
			private Double RWF;
			public void setRWF(Double passVar) { this.RWF = passVar; }
			public Double getRWF() { return this.RWF; }
			public String getRWF(int digits) { return new BigDecimal(this.RWF).round(new MathContext(digits, RoundingMode.UP)).toPlainString(); }
			public BigDecimal getRWF_asBigDecimal() { return new BigDecimal(this.RWF); }
			
			private Double SAR;
			public void setSAR(Double passVar) { this.SAR = passVar; }
			public Double getSAR() { return this.SAR; }
			public String getSAR(int digits) { return new BigDecimal(this.SAR).round(new MathContext(digits, RoundingMode.UP)).toPlainString(); }
			public BigDecimal getSAR_asBigDecimal() { return new BigDecimal(this.SAR); }
			
			private Double SBD;
			public void setSBD(Double passVar) { this.SBD = passVar; }
			public Double getSBD() { return this.SBD; }
			public String getSBD(int digits) { return new BigDecimal(this.SBD).round(new MathContext(digits, RoundingMode.UP)).toPlainString(); }
			public BigDecimal getSBD_asBigDecimal() { return new BigDecimal(this.SBD); }
			
			private Double SCR;
			public void setSCR(Double passVar) { this.SCR = passVar; }
			public Double getSCR() { return this.SCR; }
			public String getSCR(int digits) { return new BigDecimal(this.SCR).round(new MathContext(digits, RoundingMode.UP)).toPlainString(); }
			public BigDecimal getSCR_asBigDecimal() { return new BigDecimal(this.SCR); }
			
			private Double SEK;
			public void setSEK(Double passVar) { this.SEK = passVar; }
			public Double getSEK() { return this.SEK; }
			public String getSEK(int digits) { return new BigDecimal(this.SEK).round(new MathContext(digits, RoundingMode.UP)).toPlainString(); }
			public BigDecimal getSEK_asBigDecimal() { return new BigDecimal(this.SEK); }
			
			private Double SGD;
			public void setSGD(Double passVar) { this.SGD = passVar; }
			public Double getSGD() { return this.SGD; }
			public String getSGD(int digits) { return new BigDecimal(this.SGD).round(new MathContext(digits, RoundingMode.UP)).toPlainString(); }
			public BigDecimal getSGD_asBigDecimal() { return new BigDecimal(this.SGD); }
			
			private Double SHP;
			public void setSHP(Double passVar) { this.SHP = passVar; }
			public Double getSHP() { return this.SHP; }
			public String getSHP(int digits) { return new BigDecimal(this.SHP).round(new MathContext(digits, RoundingMode.UP)).toPlainString(); }
			public BigDecimal getSHP_asBigDecimal() { return new BigDecimal(this.SHP); }
			
			private Double SKK;
			public void setSKK(Double passVar) { this.SKK = passVar; }
			public Double getSKK() { return this.SKK; }
			public String getSKK(int digits) { return new BigDecimal(this.SKK).round(new MathContext(digits, RoundingMode.UP)).toPlainString(); }
			public BigDecimal getSKK_asBigDecimal() { return new BigDecimal(this.SKK); }
			
			private Double SLL;
			public void setSLL(Double passVar) { this.SLL = passVar; }
			public Double getSLL() { return this.SLL; }
			public String getSLL(int digits) { return new BigDecimal(this.SLL).round(new MathContext(digits, RoundingMode.UP)).toPlainString(); }
			public BigDecimal getSLL_asBigDecimal() { return new BigDecimal(this.SLL); }
			
			private Double SOS;
			public void setSOS(Double passVar) { this.SOS = passVar; }
			public Double getSOS() { return this.SOS; }
			public String getSOS(int digits) { return new BigDecimal(this.SOS).round(new MathContext(digits, RoundingMode.UP)).toPlainString(); }
			public BigDecimal getSOS_asBigDecimal() { return new BigDecimal(this.SOS); }
			
			private Double SRD;
			public void setSRD(Double passVar) { this.SRD = passVar; }
			public Double getSRD() { return this.SRD; }
			public String getSRD(int digits) { return new BigDecimal(this.SRD).round(new MathContext(digits, RoundingMode.UP)).toPlainString(); }
			public BigDecimal getSRD_asBigDecimal() { return new BigDecimal(this.SRD); }
			
			private Double SSP;
			public void setSSP(Double passVar) { this.SSP = passVar; }
			public Double getSSP() { return this.SSP; }
			public String getSSP(int digits) { return new BigDecimal(this.SSP).round(new MathContext(digits, RoundingMode.UP)).toPlainString(); }
			public BigDecimal getSSP_asBigDecimal() { return new BigDecimal(this.SSP); }
			
			private Double STD;
			public void setSTD(Double passVar) { this.STD = passVar; }
			public Double getSTD() { return this.STD; }
			public String getSTD(int digits) { return new BigDecimal(this.STD).round(new MathContext(digits, RoundingMode.UP)).toPlainString(); }
			public BigDecimal getSTD_asBigDecimal() { return new BigDecimal(this.STD); }
			
			private Double SVC;
			public void setSVC(Double passVar) { this.SVC = passVar; }
			public Double getSVC() { return this.SVC; }
			public String getSVC(int digits) { return new BigDecimal(this.SVC).round(new MathContext(digits, RoundingMode.UP)).toPlainString(); }
			public BigDecimal getSVC_asBigDecimal() { return new BigDecimal(this.SVC); }
			
			private Double SZL;
			public void setSZL(Double passVar) { this.SZL = passVar; }
			public Double getSZL() { return this.SZL; }
			public String getSZL(int digits) { return new BigDecimal(this.SZL).round(new MathContext(digits, RoundingMode.UP)).toPlainString(); }
			public BigDecimal getSZL_asBigDecimal() { return new BigDecimal(this.SZL); }
			
			private Double THB;
			public void setTHB(Double passVar) { this.THB = passVar; }
			public Double getTHB() { return this.THB; }
			public String getTHB(int digits) { return new BigDecimal(this.THB).round(new MathContext(digits, RoundingMode.UP)).toPlainString(); }
			public BigDecimal getTHB_asBigDecimal() { return new BigDecimal(this.THB); }
			
			private Double TJS;
			public void setTJS(Double passVar) { this.TJS = passVar; }
			public Double getTJS() { return this.TJS; }
			public String getTJS(int digits) { return new BigDecimal(this.TJS).round(new MathContext(digits, RoundingMode.UP)).toPlainString(); }
			public BigDecimal getTJS_asBigDecimal() { return new BigDecimal(this.TJS); }
			
			private Double TMM;
			public void setTMM(Double passVar) { this.TMM = passVar; }
			public Double getTMM() { return this.TMM; }
			public String getTMM(int digits) { return new BigDecimal(this.TMM).round(new MathContext(digits, RoundingMode.UP)).toPlainString(); }
			public BigDecimal getTMM_asBigDecimal() { return new BigDecimal(this.TMM); }
			
			private Double TMT;
			public void setTMT(Double passVar) { this.TMT = passVar; }
			public Double getTMT() { return this.TMT; }
			public String getTMT(int digits) { return new BigDecimal(this.TMT).round(new MathContext(digits, RoundingMode.UP)).toPlainString(); }
			public BigDecimal getTMT_asBigDecimal() { return new BigDecimal(this.TMT); }
			
			private Double TND;
			public void setTND(Double passVar) { this.TND = passVar; }
			public Double getTND() { return this.TND; }
			public String getTND(int digits) { return new BigDecimal(this.TND).round(new MathContext(digits, RoundingMode.UP)).toPlainString(); }
			public BigDecimal getTND_asBigDecimal() { return new BigDecimal(this.TND); }
			
			private Double TOP;
			public void setTOP(Double passVar) { this.TOP = passVar; }
			public Double getTOP() { return this.TOP; }
			public String getTOP(int digits) { return new BigDecimal(this.TOP).round(new MathContext(digits, RoundingMode.UP)).toPlainString(); }
			public BigDecimal getTOP_asBigDecimal() { return new BigDecimal(this.TOP); }
			
			private Double TRY;
			public void setTRY(Double passVar) { this.TRY = passVar; }
			public Double getTRY() { return this.TRY; }
			public String getTRY(int digits) { return new BigDecimal(this.TRY).round(new MathContext(digits, RoundingMode.UP)).toPlainString(); }
			public BigDecimal getTRY_asBigDecimal() { return new BigDecimal(this.TRY); }
			
			private Double TTD;
			public void setTTD(Double passVar) { this.TTD = passVar; }
			public Double getTTD() { return this.TTD; }
			public String getTTD(int digits) { return new BigDecimal(this.TTD).round(new MathContext(digits, RoundingMode.UP)).toPlainString(); }
			public BigDecimal getTTD_asBigDecimal() { return new BigDecimal(this.TTD); }
			
			private Double TWD;
			public void setTWD(Double passVar) { this.TWD = passVar; }
			public Double getTWD() { return this.TWD; }
			public String getTWD(int digits) { return new BigDecimal(this.TWD).round(new MathContext(digits, RoundingMode.UP)).toPlainString(); }
			public BigDecimal getTWD_asBigDecimal() { return new BigDecimal(this.TWD); }
			
			private Double TZS;
			public void setTZS(Double passVar) { this.TZS = passVar; }
			public Double getTZS() { return this.TZS; }
			public String getTZS(int digits) { return new BigDecimal(this.TZS).round(new MathContext(digits, RoundingMode.UP)).toPlainString(); }
			public BigDecimal getTZS_asBigDecimal() { return new BigDecimal(this.TZS); }
			
			private Double UAH;
			public void setUAH(Double passVar) { this.UAH = passVar; }
			public Double getUAH() { return this.UAH; }
			public String getUAH(int digits) { return new BigDecimal(this.UAH).round(new MathContext(digits, RoundingMode.UP)).toPlainString(); }
			public BigDecimal getUAH_asBigDecimal() { return new BigDecimal(this.UAH); }
			
			private Double UGX;
			public void setUGX(Double passVar) { this.UGX = passVar; }
			public Double getUGX() { return this.UGX; }
			public String getUGX(int digits) { return new BigDecimal(this.UGX).round(new MathContext(digits, RoundingMode.UP)).toPlainString(); }
			public BigDecimal getUGX_asBigDecimal() { return new BigDecimal(this.UGX); }
			
			private Double UYU;
			public void setUYU(Double passVar) { this.UYU = passVar; }
			public Double getUYU() { return this.UYU; }
			public String getUYU(int digits) { return new BigDecimal(this.UYU).round(new MathContext(digits, RoundingMode.UP)).toPlainString(); }
			public BigDecimal getUYU_asBigDecimal() { return new BigDecimal(this.UYU); }
			
			private Double UZS;
			public void setUZS(Double passVar) { this.UZS = passVar; }
			public Double getUZS() { return this.UZS; }
			public String getUZS(int digits) { return new BigDecimal(this.UZS).round(new MathContext(digits, RoundingMode.UP)).toPlainString(); }
			public BigDecimal getUZS_asBigDecimal() { return new BigDecimal(this.UZS); }
			
			private Double VES;
			public void setVES(Double passVar) { this.VES = passVar; }
			public Double getVES() { return this.VES; }
			public String getVES(int digits) { return new BigDecimal(this.VES).round(new MathContext(digits, RoundingMode.UP)).toPlainString(); }
			public BigDecimal getVES_asBigDecimal() { return new BigDecimal(this.VES); }
			
			private Double VND;
			public void setVND(Double passVar) { this.VND = passVar; }
			public Double getVND() { return this.VND; }
			public String getVND(int digits) { return new BigDecimal(this.VND).round(new MathContext(digits, RoundingMode.UP)).toPlainString(); }
			public BigDecimal getVND_asBigDecimal() { return new BigDecimal(this.VND); }
			
			private Double VUV;
			public void setVUV(Double passVar) { this.VUV = passVar; }
			public Double getVUV() { return this.VUV; }
			public String getVUV(int digits) { return new BigDecimal(this.VUV).round(new MathContext(digits, RoundingMode.UP)).toPlainString(); }
			public BigDecimal getVUV_asBigDecimal() { return new BigDecimal(this.VUV); }
			
			private Double WST;
			public void setWST(Double passVar) { this.WST = passVar; }
			public Double getWST() { return this.WST; }
			public String getWST(int digits) { return new BigDecimal(this.WST).round(new MathContext(digits, RoundingMode.UP)).toPlainString(); }
			public BigDecimal getWST_asBigDecimal() { return new BigDecimal(this.WST); }
			
			private Double XAF;
			public void setXAF(Double passVar) { this.XAF = passVar; }
			public Double getXAF() { return this.XAF; }
			public String getXAF(int digits) { return new BigDecimal(this.XAF).round(new MathContext(digits, RoundingMode.UP)).toPlainString(); }
			public BigDecimal getXAF_asBigDecimal() { return new BigDecimal(this.XAF); }
			
			private Double XAG;
			public void setXAG(Double passVar) { this.XAG = passVar; }
			public Double getXAG() { return this.XAG; }
			public String getXAG(int digits) { return new BigDecimal(this.XAG).round(new MathContext(digits, RoundingMode.UP)).toPlainString(); }
			public BigDecimal getXAG_asBigDecimal() { return new BigDecimal(this.XAG); }
			
			private Double XAU;
			public void setXAU(Double passVar) { this.XAU = passVar; }
			public Double getXAU() { return this.XAU; }
			public String getXAU(int digits) { return new BigDecimal(this.XAU).round(new MathContext(digits, RoundingMode.UP)).toPlainString(); }
			public BigDecimal getXAU_asBigDecimal() { return new BigDecimal(this.XAU); }
			
			private Double XCD;
			public void setXCD(Double passVar) { this.XCD = passVar; }
			public Double getXCD() { return this.XCD; }
			public String getXCD(int digits) { return new BigDecimal(this.XCD).round(new MathContext(digits, RoundingMode.UP)).toPlainString(); }
			public BigDecimal getXCD_asBigDecimal() { return new BigDecimal(this.XCD); }
			
			private Double XDR;
			public void setXDR(Double passVar) { this.XDR = passVar; }
			public Double getXDR() { return this.XDR; }
			public String getXDR(int digits) { return new BigDecimal(this.XDR).round(new MathContext(digits, RoundingMode.UP)).toPlainString(); }
			public BigDecimal getXDR_asBigDecimal() { return new BigDecimal(this.XDR); }
			
			private Double XOF;
			public void setXOF(Double passVar) { this.XOF = passVar; }
			public Double getXOF() { return this.XOF; }
			public String getXOF(int digits) { return new BigDecimal(this.XOF).round(new MathContext(digits, RoundingMode.UP)).toPlainString(); }
			public BigDecimal getXOF_asBigDecimal() { return new BigDecimal(this.XOF); }
			
			private Double XPD;
			public void setXPD(Double passVar) { this.XPD = passVar; }
			public Double getXPD() { return this.XPD; }
			public String getXPD(int digits) { return new BigDecimal(this.XPD).round(new MathContext(digits, RoundingMode.UP)).toPlainString(); }
			public BigDecimal getXPD_asBigDecimal() { return new BigDecimal(this.XPD); }
			
			private Double XPF;
			public void setXPF(Double passVar) { this.XPF = passVar; }
			public Double getXPF() { return this.XPF; }
			public String getXPF(int digits) { return new BigDecimal(this.XPF).round(new MathContext(digits, RoundingMode.UP)).toPlainString(); }
			public BigDecimal getXPF_asBigDecimal() { return new BigDecimal(this.XPF); }
			
			private Double XPT;
			public void setXPT(Double passVar) { this.XPT = passVar; }
			public Double getXPT() { return this.XPT; }
			public String getXPT(int digits) { return new BigDecimal(this.XPT).round(new MathContext(digits, RoundingMode.UP)).toPlainString(); }
			public BigDecimal getXPT_asBigDecimal() { return new BigDecimal(this.XPT); }
			
			private Double YER;
			public void setYER(Double passVar) { this.YER = passVar; }
			public Double getYER() { return this.YER; }
			public String getYER(int digits) { return new BigDecimal(this.YER).round(new MathContext(digits, RoundingMode.UP)).toPlainString(); }
			public BigDecimal getYER_asBigDecimal() { return new BigDecimal(this.YER); }
			
			private Double ZAR;
			public void setZAR(Double passVar) { this.ZAR = passVar; }
			public Double getZAR() { return this.ZAR; }
			public String getZAR(int digits) { return new BigDecimal(this.ZAR).round(new MathContext(digits, RoundingMode.UP)).toPlainString(); }
			public BigDecimal getZAR_asBigDecimal() { return new BigDecimal(this.ZAR); }
			
			private Double ZMW;
			public void setZMW(Double passVar) { this.ZMW = passVar; }
			public Double getZMW() { return this.ZMW; }
			public String getZMW(int digits) { return new BigDecimal(this.ZMW).round(new MathContext(digits, RoundingMode.UP)).toPlainString(); }
			public BigDecimal getZMW_asBigDecimal() { return new BigDecimal(this.ZMW); }
			
			private Double ZWL;
			public void setZWL(Double passVar) { this.ZWL = passVar; }
			public Double getZWL() { return this.ZWL; }
			public String getZWL(int digits) { return new BigDecimal(this.ZWL).round(new MathContext(digits, RoundingMode.UP)).toPlainString(); }
			public BigDecimal getZWL_asBigDecimal() { return new BigDecimal(this.ZWL); }
			
			private Double BSV;
			public void setBSV(Double passVar) { this.BSV = passVar; }
			public Double getBSV() { return this.BSV; }
			public String getBSV(int digits) { return new BigDecimal(this.BSV).round(new MathContext(digits, RoundingMode.UP)).toPlainString(); }
			public BigDecimal getBSV_asBigDecimal() { return new BigDecimal(this.BSV); }
			
			private Double ETH2;
			public void setETH2(Double passVar) { this.ETH2 = passVar; }
			public Double getETH2() { return this.ETH2; }
			public String getETH2(int digits) { return new BigDecimal(this.ETH2).round(new MathContext(digits, RoundingMode.UP)).toPlainString(); }
			public BigDecimal getETH2_asBigDecimal() { return new BigDecimal(this.ETH2); }
			
			private Double REPV2;
			public void setREPV2(Double passVar) { this.REPV2 = passVar; }
			public Double getREPV2() { return this.REPV2; }
			public String getREPV2(int digits) { return new BigDecimal(this.REPV2).round(new MathContext(digits, RoundingMode.UP)).toPlainString(); }
			public BigDecimal getREPV2_asBigDecimal() { return new BigDecimal(this.REPV2); }
			
			private Double _1inch;
			public void set1inch(Double passVar) { this._1inch = passVar; }
			public Double get1inch() { return this._1inch; }
			public String get1inch(int digits) { return new BigDecimal(this._1inch).round(new MathContext(digits, RoundingMode.UP)).toPlainString(); }
			public BigDecimal get1inch_asBigDecimal() { return new BigDecimal(this._1inch); }
		}
		private Rates Rates;
		public void setRates(Rates passVar) { this.Rates = passVar; }
		public Rates getRates() { return this.Rates; }
	}
	private Data Data; // SUB-CONTAINER DATA
	public void setData(Data passVar) { this.Data = passVar; }
	public Data getData() { return this.Data; }
	
	public static class Wire_deposit_information { // NESTED SUB-CONTAINER CLASS
		
		public static class Bank_country {
			
			private String Name;
			public void setName(String passVar) { this.Name = passVar; }
			public String getName() { return this.Name; }

			private String Code;
			public void setCode(String passVar) { this.Code = passVar; }
			public String getCode() { return this.Code; }
		}
		private Bank_country Bank_country;
		public void setBank_country(Bank_country passVar) { this.Bank_country = passVar; }
		public Bank_country getBank_country() { return this.Bank_country; }

		private String Account_number;
		public void setAccount_number(String passVar) { this.Account_number = passVar; }
		public String getAccount_number() { return this.Account_number; }

		private String Routing_number;
		public void setRouting_number(String passVar) { this.Routing_number = passVar; }
		public String getRouting_number() { return this.Routing_number; }

		private String Bank_name;
		public void setBank_name(String passVar) { this.Bank_name = passVar; }
		public String getBank_name() { return this.Bank_name; }

		private String Account_name;
		public void setAccount_name(String passVar) { this.Account_name = passVar; }
		public String getAccount_name() { return this.Account_name; }

		private String Account_address;
		public void setAccount_address(String passVar) { this.Account_address = passVar; }
		public String getAccount_address() { return this.Account_address; }

		private String Reference;
		public void setReference(String passVar) { this.Reference = passVar; }
		public String getReference() { return this.Reference; }
		
		private String Sort_code;
		public void setSort_code(String passVar) { this.Sort_code = passVar; }
		public String getSort_code() { return this.Sort_code; }
		
		private String Iban;
		public void setIban(String passVar) { this.Iban = passVar; }
		public String getIban() { return this.Iban; }
		
		private String Swift;
		public void setSwift(String passVar) { this.Swift = passVar; }
		public String getSwift() { return this.Swift; }
		
		private String Bank_address;
		public void setBank_address(String passVar) { this.Bank_address = passVar; }
		public String getBank_address() { return this.Bank_address; }
		
		private String Symbol;
		public void setSymbol(String passVar) { this.Symbol = passVar; }
		public String getSymbol() { return this.Symbol; }

		private String Customer_name;
		public void setCustomer_name(String passVar) { this.Customer_name = passVar; }
		public String getCustomer_name() { return this.Customer_name; }

		private String Account_type;
		public void setAccount_type(String passVar) { this.Account_type = passVar; }
		public String getAccount_type() { return this.Account_type; }

		private String Institution_code;
		public void setInstitution_code(String passVar) { this.Institution_code = passVar; }
		public String getInstitution_code() { return this.Institution_code; }

		private String Institution_name;
		public void setInstitution_name(String passVar) { this.Institution_name = passVar; }
		public String getInstitution_name() { return this.Institution_name; }
		
		private String Paypal_email;
		public void setPaypal_email(String passVar) { this.Paypal_email = passVar; }
		public String getPaypal_email() { return this.Paypal_email; }
		
		private String Paypal_owner;
		public void setPaypal_owner(String passVar) { this.Paypal_owner = passVar; }
		public String getPaypal_owner() { return this.Paypal_owner; }
		
		private String Institutional_identifier;
		public void setInstitutional_identifier(String passVar) { this.Institutional_identifier = passVar; }
		public String getInstitutional_identifier() { return this.Institutional_identifier; }
		
		private String Branch_name;
		public void setBranch_name(String passVar) { this.Branch_name = passVar; }
		public String getBranch_name() { return this.Branch_name; }
		
		private String Icon_url;
		public void setIcon_url(String passVar) { this.Icon_url = passVar; }
		public String getIcon_url() { return this.Icon_url; }
		
		private Available_balance Balance;
		public void setBalance(Available_balance passVar) { this.Balance = passVar; }
		public Available_balance getBalance() { return this.Balance; }

		private Double Hold_business_days;
		public void setHold_business_days(Double passVar) { this.Hold_business_days = passVar; }
		public Double getHold_business_days() { return this.Hold_business_days; }
		public String getHold_business_days(int digits) { return new BigDecimal(this.Hold_business_days).round(new MathContext(digits, RoundingMode.UP)).toPlainString(); }
		public BigDecimal getHold_business_days_asBigDecimal() { return new BigDecimal(this.Hold_business_days); }

		private Double Hold_days;
		public void setHold_days(Double passVar) { this.Hold_days = passVar; }
		public Double getHold_days() { return this.Hold_days; }
		public String getHold_days(int digits) { return new BigDecimal(this.Hold_days).round(new MathContext(digits, RoundingMode.UP)).toPlainString(); }
		public BigDecimal getHold_days_asBigDecimal() { return new BigDecimal(this.Hold_days); }

		private String VerificationMethod;
		public void setVerificationMethod(String passVar) { this.VerificationMethod = passVar; }
		public String getVerificationMethod() { return this.VerificationMethod; }
		
		private String cdvStatus;
		public void setcdvStatus(String passVar) { this.cdvStatus = passVar; }
		public String getcdvStatus() { return this.cdvStatus; }
	}
	private Wire_deposit_information Wire_deposit_information; // SUB-CONTAINER DATA
	public void setWire_deposit_information(Wire_deposit_information passVar) { this.Wire_deposit_information = passVar; }
	public Wire_deposit_information getWire_deposit_information() { return this.Wire_deposit_information; }

	public static class Address_info { // NESTED SUB-CONTAINER CLASS
		
		private String Address;
		public void setAddress(String passVar) { this.Address = passVar; }
		public String getAddress() { return this.Address; }

		private String Destination_tag;
		public void setDestination_tag(String passVar) { this.Destination_tag = passVar; }
		public String getDestination_tag() { return this.Destination_tag; }
	}
	private Address_info Address_info; // SUB-CONTAINER DATA
	public void setAddress_info(Address_info passVar) { this.Address_info = passVar; }
	public Address_info getAddress_info() { return this.Address_info; }

	public static class Warnings { // NESTED SUB-CONTAINER CLASS
		
		private String Title;
		public void setTitle(String passVar) { this.Title = passVar; }
		public String getTitle() { return this.Title; }

		private String Details;
		public void setDetails(String passVar) { this.Details = passVar; }
		public String getDetails() { return this.Details; }

		private String Image_url;
		public void setImage_url(String passVar) { this.Image_url = passVar; }
		public String getImage_url() { return this.Image_url; }
	}
	private Warnings Warnings; // SUB-CONTAINER DATA
	public void setWarnings(Warnings passVar) { this.Warnings = passVar; }
	public Warnings getWarnings() { return this.Warnings; }
	
	public static class Limits { // NESTED SUB-CONTAINER CLASS
		
		private String Type;
		public void setType(String passVar) { this.Type = passVar; }
		public String getType() { return this.Type; }
		
		private String Name;
		public void setName(String passVar) { this.Name = passVar; }
		public String getName() { return this.Name; }
	}
	private Limits Limits; // SUB-CONTAINER DATA
	public void setLimits(Limits passVar) { this.Limits = passVar; }
	public Limits getLimits() { return this.Limits; }
	
	public static class Fiat_account { // NESTED SUB-CONTAINER CLASS
		
		private String ID;
		public void setID(String passVar) { this.ID = passVar; }
		public String getID() { return this.ID; }

		private String Resource;
		public void setResource(String passVar) { this.Resource = passVar; }
		public String getResource() { return this.Resource; }

		private String Resource_path;
		public void setResource_path(String passVar) { this.Resource_path = passVar; }
		public String getResource_path() { return this.Resource_path; }
	}
	private Fiat_account Fiat_account; // SUB-CONTAINER DATA
	public void setFiat_account(Fiat_account passVar) { this.Fiat_account = passVar; }
	public Fiat_account getFiat_account() { return this.Fiat_account; }
	
	public static class Recurring_options { // NESTED SUB-CONTAINER CLASS
		
		private String Period;
		public void setPeriod(String passVar) { this.Period = passVar; }
		public String getPeriod() { return this.Period; }

		private String Label;
		public void setLabel(String passVar) { this.Label = passVar; }
		public String getLabel() { return this.Label; }
	}
	private Recurring_options Recurring_options; // SUB-CONTAINER DATA
	public void setRecurring_options(Recurring_options passVar) { this.Recurring_options = passVar; }
	public Recurring_options getRecurring_options() { return this.Recurring_options; }
	
	public static class Available_balance { // NESTED SUB-CONTAINER CLASS
		
		private Double Amount;
		public void setAmount(Double passVar) { this.Amount = passVar; }
		public Double getAmount() { return this.Amount; }
		public String getAmount(int digits) { return new BigDecimal(this.Amount).round(new MathContext(digits, RoundingMode.UP)).toPlainString(); }
		public BigDecimal getAmount_asBigDecimal() { return new BigDecimal(this.Amount); }

		private String Currency;
		public void setCurrency(String passVar) { this.Currency = passVar; }
		public String getCurrency() { return this.Currency; }
		
		private String Scale;
		public void setScale(String passVar) { this.Scale = passVar; }
		public String getScale() { return this.Scale; }
	}
	private Available_balance Available_balance; // SUB-CONTAINER DATA
	public void setAvailable_balance(Available_balance passVar) { this.Available_balance = passVar; }
	public Available_balance getAvailable_balance() { return this.Available_balance; }
	
	public static class Params { // NESTED SUB-CONTAINER CLASS
		
		public static class User { // SECOND LEVEL NESTED SUB-CONTAINER CLASS
			
			public static class Roles { // THIRD LEVEL NESTED SUB-CONTAINER CLASS
				
			}
			private Roles[] Roles; // SUB-CONTAINER DATA
			public void setRoles(Roles[] passVar) { this.Roles = passVar; }
			public Roles[] getRoles() { return this.Roles; }
			
			public static class Flags { // THIRD LEVEL NESTED SUB-CONTAINER CLASS
				
			}
			private Flags Flags; // SUB-CONTAINER DATA
			public void setFlags(Flags passVar) { this.Flags = passVar; }
			public Flags getFlags() { return this.Flags; }
			
			public static class Details { // THIRD LEVEL NESTED SUB-CONTAINER CLASS
				
			}
			private Details Details; // SUB-CONTAINER DATA
			public void setDetails(Details passVar) { this.Details = passVar; }
			public Details getDetails() { return this.Details; }
			
			public static class Preferences { // THIRD LEVEL NESTED SUB-CONTAINER CLASS
				
			}
			private Preferences Preferences; // SUB-CONTAINER DATA
			public void setPreferences(Preferences passVar) { this.Preferences = passVar; }
			public Preferences getPreferences() { return this.Preferences; }
			
			private Boolean Is_banned;
			public void setIs_banned(Boolean passVar) { this.Is_banned = passVar; }
			public Boolean getIs_banned() { return this.Is_banned; }
		
			private Boolean Fulfills_new_requirements;
			public void setFulfills_new_requirements(Boolean passVar) { this.Fulfills_new_requirements = passVar; }
			public Boolean getFulfills_new_requirements() { return this.Fulfills_new_requirements; }
		
			private Boolean Has_default;
			public void setHas_default(Boolean passVar) { this.Has_default = passVar; }
			public Boolean getHas_default() { return this.Has_default; }
		
			private Boolean CD_data_from_cache;
			public void setCD_data_from_cache(Boolean passVar) { this.CD_data_from_cache = passVar; }
			public Boolean getCD_data_from_cache() { return this.CD_data_from_cache; }
		
			private Boolean Has_clawback_payment_pending;
			public void setHas_clawback_payment_pending(Boolean passVar) { this.Has_clawback_payment_pending = passVar; }
			public Boolean getHas_clawback_payment_pending() { return this.Has_clawback_payment_pending; }
		
			private Boolean Has_restricted_assets;
			public void setHas_restricted_assets(Boolean passVar) { this.Has_restricted_assets = passVar; }
			public Boolean getHas_restricted_assets() { return this.Has_restricted_assets; }
		
			private String Two_factor_method;
			public void setTwo_factor_method(String passVar) { this.Two_factor_method = passVar; }
			public String getTwo_factor_method() { return this.Two_factor_method; }
			
			private String Legal_name;
			public void setLegal_name(String passVar) { this.Legal_name = passVar; }
			public String getLegal_name() { return this.Legal_name; }
			
			private String Terms_accepted;
			public void setTerms_accepted(String passVar) { this.Terms_accepted = passVar; }
			public String getTerms_accepted() { return this.Terms_accepted; }
			
			private String State_code;
			public void setState_code(String passVar) { this.State_code = passVar; }
			public String getState_code() { return this.State_code; }
			
			private String User_type;
			public void setUser_type(String passVar) { this.User_type = passVar; }
			public String getUser_type() { return this.User_type; }
			
			private String ID;
			public void setID(String passVar) { this.ID = passVar; }
			public String getID() { return this.ID; }
			
			private String Created_at;
			public void setCreated_at(String passVar) { this.Created_at = passVar; }
			public String getCreated_at() { return this.Created_at; }
			
			private String Active_at;
			public void setActive_at(String passVar) { this.Active_at = passVar; }
			public String getActive_at() { return this.Active_at; }
			
			private String Name;
			public void setName(String passVar) { this.Name = passVar; }
			public String getName() { return this.Name; }
			
			private String Email;
			public void setEmail(String passVar) { this.Email = passVar; }
			public String getEmail() { return this.Email; }	
		}
		private User User; // SECOND LEVEL SUB-CONTAINER DATA
		public void setUser(User passVar) { this.User = passVar; }
		public User getUser() { return this.User; }
		
		private String Start_date;
		public void setStart_date(String passVar) { this.Start_date = passVar; }
		public String getStart_date() { return this.Start_date; }
		
		private String File_count;
		public void setFile_count(String passVar) { this.File_count = passVar; }
		public String getFile_count() { return this.File_count; }
		
		private String End_date;
		public void setEnd_date(String passVar) { this.End_date = passVar; }
		public String getEnd_date() { return this.End_date; }
		
		private String Format;
		public void setFormat(String passVar) { this.Format = passVar; }
		public String getFormat() { return this.Format; }
		
		private String Product_id;
		public void setProduct_id(String passVar) { this.Product_id = passVar; }
		public String getProduct_id() { return this.Product_id; }
		
		private String Account_id;
		public void setAccount_id(String passVar) { this.Account_id = passVar; }
		public String getAccount_id() { return this.Account_id; }
		
		private String Profile_id;
		public void setProfile_id(String passVar) { this.Profile_id = passVar; }
		public String getProfile_id() { return this.Profile_id; }
		
		private String Email;
		public void setEmail(String passVar) { this.Email = passVar; }
		public String getEmail() { return this.Email; }
		
		private Boolean New_york_state;
		public void setNew_york_state(Boolean passVar) { this.New_york_state = passVar; }
		public Boolean getNew_york_state() { return this.New_york_state; }
	}
	private Params Params; // SUB-CONTAINER DATA
	public void setParams(Params passVar) { this.Params = passVar; }
	public Params getParams() { return this.Params; }
	
	public static class Transfer_limits { // NESTED SUB-CONTAINER CLASS

		public static class Buy { // SECOND LEVEL NESTED SUB-CONTAINER CLASS
			
		}
		private Buy Buy; // SUB-CONTAINER DATA
		public void setBuy(Buy passVar) { this.Buy = passVar; }
		public Buy getBuy() { return this.Buy; }

		public static class Sell { // SECOND LEVEL NESTED SUB-CONTAINER CLASS
			
		}
		private Sell Sell; // SUB-CONTAINER DATA
		public void setSell(Sell passVar) { this.Sell = passVar; }
		public Sell getSell() { return this.Sell; }

		public static class Exchange_withdraw { // SECOND LEVEL NESTED SUB-CONTAINER CLASS
			
		}
		private Exchange_withdraw Exchange_withdraw; // SUB-CONTAINER DATA
		public void setExchange_withdraw(Exchange_withdraw passVar) { this.Exchange_withdraw = passVar; }
		public Exchange_withdraw getExchange_withdraw() { return this.Exchange_withdraw; }

		public static class Ach { // SECOND LEVEL NESTED SUB-CONTAINER CLASS
			
		}
		private Ach Ach; // SUB-CONTAINER DATA
		public void setAch(Ach passVar) { this.Ach = passVar; }
		public Ach getAch() { return this.Ach; }

		public static class Ach_no_balance { // SECOND LEVEL NESTED SUB-CONTAINER CLASS
			
		}
		private Ach_no_balance Ach_no_balance; // SUB-CONTAINER DATA
		public void setAch_no_balance(Ach_no_balance passVar) { this.Ach_no_balance = passVar; }
		public Ach_no_balance getAch_no_balance() { return this.Ach_no_balance; }

		public static class Credit_debit_card { // SECOND LEVEL NESTED SUB-CONTAINER CLASS
			
		}
		private Credit_debit_card Credit_debit_card; // SUB-CONTAINER DATA
		public void setCredit_debit_card(Credit_debit_card passVar) { this.Credit_debit_card = passVar; }
		public Credit_debit_card getCredit_debit_card() { return this.Credit_debit_card; }

		public static class Secure3d_buy { // SECOND LEVEL NESTED SUB-CONTAINER CLASS
			
		}
		private Secure3d_buy Secure3d_buy; // SUB-CONTAINER DATA
		public void setSecure3d_buy(Secure3d_buy passVar) { this.Secure3d_buy = passVar; }
		public Secure3d_buy getSecure3d_buy() { return this.Secure3d_buy; }

		public static class Paypal_buy { // SECOND LEVEL NESTED SUB-CONTAINER CLASS
			
		}
		private Paypal_buy Paypal_buy; // SUB-CONTAINER DATA
		public void setPaypal_buy(Paypal_buy passVar) { this.Paypal_buy = passVar; }
		public Paypal_buy getPaypal_buy() { return this.Paypal_buy; }

		public static class Paypal_withdraw { // SECOND LEVEL NESTED SUB-CONTAINER CLASS
			
		}
		private Paypal_withdraw Paypal_withdraw; // SUB-CONTAINER DATA
		public void setPaypal_withdraw(Paypal_withdraw passVar) { this.Paypal_withdraw = passVar; }
		public Paypal_withdraw getPaypal_withdraw() { return this.Paypal_withdraw; }

		public static class Ideal_deposit { // SECOND LEVEL NESTED SUB-CONTAINER CLASS
			
		}
		private Ideal_deposit Ideal_deposit; // SUB-CONTAINER DATA
		public void setIdeal_deposit(Ideal_deposit passVar) { this.Ideal_deposit = passVar; }
		public Ideal_deposit getIdeal_deposit() { return this.Ideal_deposit; }

		public static class Sofort_deposit { // SECOND LEVEL NESTED SUB-CONTAINER CLASS
			
		}
		private Sofort_deposit Sofort_deposit; // SUB-CONTAINER DATA
		public void setSofort_deposit(Sofort_deposit passVar) { this.Sofort_deposit = passVar; }
		public Sofort_deposit getSofort_deposit() { return this.Sofort_deposit; }

		public static class Instant_ach_withdraw { // SECOND LEVEL NESTED SUB-CONTAINER CLASS
			
		}
		private Instant_ach_withdraw Instant_ach_withdraw; // SUB-CONTAINER DATA
		public void setInstant_ach_withdraw(Instant_ach_withdraw passVar) { this.Instant_ach_withdraw = passVar; }
		public Instant_ach_withdraw getInstant_ach_withdraw() { return this.Instant_ach_withdraw; }
	}
	private Transfer_limits Transfer_limits; // SUB-CONTAINER DATA
	public void setTransfer_limits(Transfer_limits passVar) { this.Transfer_limits = passVar; }
	public Transfer_limits getTransfer_limits() { return this.Transfer_limits; }
		
	private Data.Rates Prices; // SUB-CONTAINER DATA
	public void setPrices(Data.Rates passVar) { this.Prices = passVar; }
	public Data.Rates getPrices() { return this.Prices; }
	
	private Wire_deposit_information Picker_data; // SUB-CONTAINER DATA
	public void setPicker_data(Wire_deposit_information passVar) { this.Picker_data = passVar; }
	public Wire_deposit_information getPicker_data() { return this.Picker_data; }
	
	private Fiat_account Crypto_account; // SUB-CONTAINER DATA
	public void setCrypto_account(Fiat_account passVar) { this.Crypto_account = passVar; }
	public Fiat_account getCrypto_account() { return this.Crypto_account; }

	private Wire_deposit_information Swift_desposit_information; // SUB-CONTAINER DATA
	public void setSwift_deposit_information(Wire_deposit_information passVar) { this.Swift_desposit_information = passVar; }
	public Wire_deposit_information getSwift_deposit_information() { return this.Swift_desposit_information; }

	private Wire_deposit_information Sepa_deposit_information; // SUB-CONTAINER DATA
	public void setSepa_deposit_information(Wire_deposit_information passVar) { this.Sepa_deposit_information = passVar; }
	public Wire_deposit_information getSepa_deposit_information() { return this.Sepa_deposit_information; }

	private Wire_deposit_information UK_deposit_information; // SUB-CONTAINER DATA
	public void setUK_deposit_information(Wire_deposit_information passVar) { this.UK_deposit_information = passVar; }
	public Wire_deposit_information getUK_deposit_information() { return this.UK_deposit_information; }

	private Double Base_min_size;
	public void setBase_min_size(Double passVar) { this.Base_min_size = passVar; }
	public Double getBase_min_size() { return this.Base_min_size; }
	public String getBase_min_size(int digits) { return new BigDecimal(this.Base_min_size).round(new MathContext(digits, RoundingMode.UP)).toPlainString(); }
	public BigDecimal getBase_min_size_asBigDecimal() { return new BigDecimal(this.Base_min_size); }
	
	private Double Base_max_size;
	public void setBase_max_size(Double passVar) { this.Base_max_size = passVar; }
	public Double getBase_max_size() { return this.Base_max_size; }
	public String getBase_max_size(int digits) { return new BigDecimal(this.Base_max_size).round(new MathContext(digits, RoundingMode.UP)).toPlainString(); }
	public BigDecimal getBase_max_size_asBigDecimal() { return new BigDecimal(this.Base_max_size); }
	
	private String Base_increment;
	public void setBase_increment(String passVar) { this.Base_increment = passVar; }
	public String getBase_increment() { return this.Base_increment; }

	private String Quote_increment;
	public void setQuote_increment(String passVar) { this.Quote_increment = passVar; }
	public String getQuote_increment() { return this.Quote_increment; }
	
	private String Display_name;
	public void setDisplay_name(String passVar) { this.Display_name = passVar; }
	public String getDisplay_name() { return this.Display_name; }
	
	private Double Open;
	public void setOpen(Double passVar) { this.Open = passVar; }
	public Double getOpen() { return this.Open; }
	public String getOpen(int digits) { return new BigDecimal(this.Open).round(new MathContext(digits, RoundingMode.UP)).toPlainString(); }
	public BigDecimal getOpen_asBigDecimal() { return new BigDecimal(this.Open); }
	
	private Double High;
	public void setHigh(Double passVar) { this.High = passVar; }
	public Double getHigh() { return this.High; }
	public String getHigh(int digits) { return new BigDecimal(this.High).round(new MathContext(digits, RoundingMode.UP)).toPlainString(); }
	public BigDecimal getHigh_asBigDecimal() { return new BigDecimal(this.High); }
	
	private Double Low;
	public void setLow(Double passVar) { this.Low = passVar; }
	public Double getLow() { return this.Low; }
	public String getLow(int digits) { return new BigDecimal(this.Low).round(new MathContext(digits, RoundingMode.UP)).toPlainString(); }
	public BigDecimal getLow_asBigDecimal() { return new BigDecimal(this.Low); }
	
	private Double Last;
	public void setLast(Double passVar) { this.Last = passVar; }
	public Double getLast() { return this.Last; }
	public String getLast(int digits) { return new BigDecimal(this.Last).round(new MathContext(digits, RoundingMode.UP)).toPlainString(); }
	public BigDecimal getLast_asBigDecimal() { return new BigDecimal(this.Last); }
	
	private Double Volume;
	public void setVolume(Double passVar) { this.Volume = passVar; }
	public Double getVolume() { return this.Volume; }
	public String getVolume(int digits) { return new BigDecimal(this.Volume).round(new MathContext(digits, RoundingMode.UP)).toPlainString(); }
	public BigDecimal getVolume_asBigDecimal() { return new BigDecimal(this.Volume); }
	
	private Double Min_market_funds;
	public void setMin_market_funds(Double passVar) { this.Min_market_funds = passVar; }
	public Double getMin_market_funds() { return this.Min_market_funds; }
	public String getMin_market_funds(int digits) { return new BigDecimal(this.Min_market_funds).round(new MathContext(digits, RoundingMode.UP)).toPlainString(); }
	public BigDecimal getMin_market_funds_asBigDecimal() { return new BigDecimal(this.Min_market_funds); }
	
	private Double Max_market_funds;
	public void setMax_market_funds(Double passVar) { this.Max_market_funds = passVar; }
	public Double getMax_market_funds() { return this.Max_market_funds; }
	public String getMax_market_funds(int digits) { return new BigDecimal(this.Max_market_funds).round(new MathContext(digits, RoundingMode.UP)).toPlainString(); }
	public BigDecimal getMax_market_funds_asBigDecimal() { return new BigDecimal(this.Max_market_funds); }
	
	private Boolean Limit_only;
	public void setLimit_only(Boolean passVar) { this.Limit_only = passVar; }
	public Boolean getLimit_only() { return this.Limit_only; }

	private Boolean Trading_disabled;
	public void setTrading_disabled(Boolean passVar) { this.Trading_disabled = passVar; }
	public Boolean getTrading_disabled() { return this.Trading_disabled; }

	private Boolean Cancel_only;
	public void setCancel_only(Boolean passVar) { this.Cancel_only = passVar; }
	public Boolean getCancel_only() { return this.Cancel_only; }
	
	private Boolean Fx_stablecoin;
	public void setFx_stablecoin(Boolean passVar) { this.Fx_stablecoin = passVar; }
	public Boolean getFx_stablecoin() { return this.Fx_stablecoin; }
	
	private Boolean Margin_enabled;
	public void setMargin_enabled(Boolean passVar) { this.Margin_enabled = passVar; }
	public Boolean getMargin_enabled() { return this.Margin_enabled; }

	private Double Max_slippage_percentage;
	public void setMax_slippage_percentage(Double passVar) { this.Max_slippage_percentage = passVar; }
	public Double getMax_slippage_percentage() { return this.Max_slippage_percentage; }
	public String getMax_slippage_percentage(int digits) { return new BigDecimal(this.Max_slippage_percentage).round(new MathContext(digits, RoundingMode.UP)).toPlainString(); }
	public BigDecimal getMax_slippage_percentage_asBigDecimal() { return new BigDecimal(this.Max_slippage_percentage); }	

	private Boolean Auction_mode;
	public void setAuction_mode(Boolean passVar) { this.Auction_mode = passVar; }
	public Boolean getAuction_mode() { return this.Auction_mode; }

	private String File_count;
	public void setFile_count(String passVar) { this.File_count = passVar; }
	public String getFile_count() { return this.File_count; }

	private String Quote_currency;
	public void setQuote_currency(String passVar) { this.Quote_currency = passVar; }
	public String getQuote_currency() { return this.Quote_currency; }

	private String Base_currency;
	public void setBase_currency(String passVar) { this.Base_currency = passVar; }
	public String getBase_currency() { return this.Base_currency; }

	private String Limit_currency;
	public void setLimit_currency(String passVar) { this.Limit_currency = passVar; }
	public String getLimit_currency() { return this.Limit_currency; }

	private String Auction;
	public void setAuction(String passVar) { this.Auction = passVar; }
	public String getAuction() { return this.Auction; }
	
	private String Payout_at;
	public void setPayout_at(String passVar) { this.Payout_at = passVar; }
	public String getPayout_at() { return this.Payout_at; }
	
	private String Fee;
	public void setFee(String passVar) { this.Fee = passVar; }
	public String getFee() { return this.Fee; }
	
	private String User_id;
	public void setUser_id(String passVar) { this.User_id = passVar; }
	public String getUser_id() { return this.User_id; }
	
	private String Timestamp;
	public void setTimestamp(String passVar) { this.Timestamp = passVar; }
	public String getTimestamp() { return this.Timestamp; }
	
	private String[][] Messages;
	public void setMessages(String[][] passVar) { this.Messages = passVar; }
	public String[][] getMessages() { return this.Messages; }
	
	private String[][] Signatures;
	public void setSignatures(String[][] passVar) { this.Signatures = passVar; }
	public String[][] getSignatures() { return this.Signatures; }
	
	private String Liquidity;
	public void setLiquidity(String passVar) { this.Liquidity = passVar; }
	public String getLiquidity() { return this.Liquidity; }
	
	private Double Subtotal;
	public void setSubtotal(Double passVar) { this.Subtotal = passVar; }
	public Double getSubtotal() { return this.Subtotal; }
	public String getSubtotal(int digits) { return new BigDecimal(this.Subtotal).round(new MathContext(digits, RoundingMode.UP)).toPlainString(); }
	public BigDecimal getSubtotal_asBigDecimal() { return new BigDecimal(this.Subtotal); }
	
	private Boolean Primary_buy;
	public void setPrimary_buy(Boolean passVar) { this.Primary_buy = passVar; }
	public Boolean getPrimary_buy() { return this.Primary_buy; }

	private Boolean Primary_sell;
	public void setPrimary_sell(Boolean passVar) { this.Primary_sell = passVar; }
	public Boolean getPrimary_sell() { return this.Primary_sell; }
	
	private Boolean Instant_buy;
	public void setInstant_buy(Boolean passVar) { this.Instant_buy = passVar; }
	public Boolean getInstant_buy() { return this.Instant_buy; }
	
	private Boolean Instant_sell;
	public void setInstant_sell(Boolean passVar) { this.Instant_sell = passVar; }
	public Boolean getInstant_sell() { return this.Instant_sell; }
	
	private String Verified;
	public void setVerified(String passVar) { this.Verified = passVar; }
	public String getVerified() { return this.Verified; }
	
	private Boolean Allow_buy;
	public void setAllow_buy(Boolean passVar) { this.Allow_buy = passVar; }
	public Boolean getAllow_buy() { return this.Allow_buy; }
	
	private Boolean Allow_sell;
	public void setAllow_sell(Boolean passVar) { this.Allow_sell = passVar; }
	public Boolean getAllow_sell() { return this.Allow_sell; }
	
	private Boolean Allow_deposit;
	public void setAllow_deposit(Boolean passVar) { this.Allow_deposit = passVar; }
	public Boolean getAllow_deposit() { return this.Allow_deposit; }
	
	private Boolean Allow_withdraw;
	public void setAllow_withdraw(Boolean passVar) { this.Allow_withdraw = passVar; }
	public Boolean getAllow_withdraw() { return this.Allow_withdraw; }
	
	private String Address;
	public void setAddress(String passVar) { this.Address = passVar; }
	public String getAddress() { return this.Address; }

	private String Updated_at;
	public void setUpdated_at(String passVar) { this.Updated_at = passVar; }
	public String getUpdated_at() { return this.Updated_at; }
	
	private String Network;
	public void setNetwork(String passVar) { this.Network = passVar; }
	public String getNetwork() { return this.Network; }

	private String URI_scheme;
	public void setURI_scheme(String passVar) { this.URI_scheme = passVar; }
	public String getURI_scheme() { return this.URI_scheme; }

	private String Resource;
	public void setResource(String passVar) { this.Resource = passVar; }
	public String getResource() { return this.Resource; }

	private String Resource_path;
	public void setResource_path(String passVar) { this.Resource_path = passVar; }
	public String getResource_path() { return this.Resource_path; }

	private String Legacy_address;
	public void setLegacy_address(String passVar) { this.Legacy_address = passVar; }
	public String getLegacy_address() { return this.Legacy_address; }

	private String Destination_tag;
	public void setDestination_tag(String passVar) { this.Destination_tag = passVar; }
	public String getDestination_tag() { return this.Destination_tag; }

	private String Deposit_uri;
	public void setDeposit_uri(String passVar) { this.Deposit_uri = passVar; }
	public String getDeposit_uri() { return this.Deposit_uri; }

	private String Callback_url;
	public void setCallback_url(String passVar) { this.Callback_url = passVar; }
	public String getCallback_url() { return this.Callback_url; }

	private String Primary;
	public void setPrimary(String passVar) { this.Primary = passVar; }
	public String getPrimary() { return this.Primary; }

	private String Active;
	public void setActive(String passVar) { this.Active = passVar; }
	public String getActive() { return this.Active; }

	private String Available_on_consumer;
	public void setAvailable_on_consumer(String passVar) { this.Available_on_consumer = passVar; }
	public String getAvailable_on_consumer() { return this.Available_on_consumer; }

	private Double Hold_balance;
	public void setHold_balance(Double passVar) { this.Hold_balance = passVar; }
	public Double getHold_balance() { return this.Hold_balance; }
	public String getHold_balance(int digits) { return new BigDecimal(this.Hold_balance).round(new MathContext(digits, RoundingMode.UP)).toPlainString(); }
	public BigDecimal getHold_balance_asBigDecimal() { return new BigDecimal(this.Hold_balance); }

	private String Hold_currency;
	public void setHold_currency(String passVar) { this.Hold_currency = passVar; }
	public String getHold_currency() { return this.Hold_currency; }

	private String Destination_tag_name;
	public void setDestination_tag_name(String passVar) { this.Destination_tag_name = passVar; }
	public String getDestination_tag_name() { return this.Destination_tag_name; }

	private String Destination_tag_regex;
	public void setDestination_tag_regex(String passVar) { this.Destination_tag_regex = passVar; }
	public String getDestination_tag_regex() { return this.Destination_tag_regex; }

	private String Ready;
	public void setReady(String passVar) { this.Ready = passVar; }
	public String getReady() { return this.Ready; }

	private String Reason;
	public void setReason(String passVar) { this.Reason = passVar; }
	public String getReason() { return this.Reason; }

	private String Order_id;
	public void setOrder_id(String passVar) { this.Order_id = passVar; }
	public String getOrder_id() { return this.Order_id; }

	private String Order_type;
	public void setOrder_type(String passVar) { this.Order_type = passVar; }
	public String getOrder_type() { return this.Order_type; }

	private String Maker_order_id;
	public void setMaker_order_id(String passVar) { this.Maker_order_id = passVar; }
	public String getMaker_order_id() { return this.Maker_order_id; }

	private String Taker_order_id;
	public void setTaker_order_id(String passVar) { this.Taker_order_id = passVar; }
	public String getTaker_order_id() { return this.Taker_order_id; }

	private String Maker_user_id;
	public void setMaker_user_id(String passVar) { this.Maker_user_id = passVar; }
	public String getMaker_user_id() { return this.Maker_user_id; }

	private String Taker_user_id;
	public void setTaker_user_id(String passVar) { this.Taker_user_id = passVar; }
	public String getTaker_user_id() { return this.Taker_user_id; }

	private String Maker_profile_id;
	public void setMaker_profile_id(String passVar) { this.Maker_profile_id = passVar; }
	public String getMaker_profile_id() { return this.Maker_profile_id; }

	private String Taker_profile_id;
	public void setTaker_profile_id(String passVar) { this.Taker_profile_id = passVar; }
	public String getTaker_profile_id() { return this.Taker_profile_id; }

	private Double Taker_fee_rate;
	public void setTaker_fee_rate(Double passVar) { this.Taker_fee_rate = passVar; }
	public Double getTaker_fee_rate() { return this.Taker_fee_rate; }
	public String getTaker_fee_rate(int digits) { return new BigDecimal(this.Taker_fee_rate).round(new MathContext(digits, RoundingMode.UP)).toPlainString(); }
	public BigDecimal getTaker_fee_rate_asBigDecimal() { return new BigDecimal(this.Taker_fee_rate); }

	private Double Remaining_size;
	public void setRemaining_size(Double passVar) { this.Remaining_size = passVar; }
	public Double getRemaining_size() { return this.Remaining_size; }
	public String getRemaining_size(int digits) { return new BigDecimal(this.Remaining_size).round(new MathContext(digits, RoundingMode.UP)).toPlainString(); }
	public BigDecimal getRemaining_size_asBigDecimal() { return new BigDecimal(this.Remaining_size); }
	
	private Double Maker_fee_rate;
	public void setMaker_fee_rate(Double passVar) { this.Maker_fee_rate = passVar; }
	public Double getMaker_fee_rate() { return this.Maker_fee_rate; }
	public String getMaker_fee_rate(int digits) { return new BigDecimal(this.Maker_fee_rate).round(new MathContext(digits, RoundingMode.UP)).toPlainString(); }
	public BigDecimal getMaker_fee_rate_asBigDecimal() { return new BigDecimal(this.Maker_fee_rate); }
	
	private Double USD_volume;
	public void setUSD_volume(Double passVar) { this.USD_volume = passVar; }
	public Double getUSD_volume() { return this.USD_volume; }
	public String getUSD_volume(int digits) { return new BigDecimal(this.USD_volume).round(new MathContext(digits, RoundingMode.UP)).toPlainString(); }
	public BigDecimal getUSD_volume_asBigDecimal() { return new BigDecimal(this.USD_volume); }

	private String[][] Changes;
	public void setChanges(String[][] passVar) { this.Changes = passVar; }
	public String[][] getChanges() { return this.Changes; }
	
	private String Time;
	public void setTime(String passVar) { this.Time = passVar; }
	public String getTime() { return this.Time; }
	
	private Double Sequence;
	public void setSequence(Double passVar) { this.Sequence = passVar; }
	public Double getSequence() { return this.Sequence; }
	public String getSequence(int digits) { return new BigDecimal(this.Sequence).round(new MathContext(digits, RoundingMode.UP)).toPlainString(); }
	public BigDecimal getSequence_asBigDecimal() { return new BigDecimal(this.Sequence); }
	
	private Double Open_24h;
	public void setOpen_24h(Double passVar) { this.Open_24h = passVar; }
	public Double getOpen_24h() { return this.Open_24h; }
	public String getOpen_24h(int digits) { return new BigDecimal(this.Open_24h).round(new MathContext(digits, RoundingMode.UP)).toPlainString(); }
	public BigDecimal getOpen_24h_asBigDecimal() { return new BigDecimal(this.Open_24h); }
	
	private Double Volume_24h;
	public void setVolume_24h(Double passVar) { this.Volume_24h = passVar; }
	public Double getVolume_24h() { return this.Volume_24h; }
	public String getVolume_24h(int digits) { return new BigDecimal(this.Volume_24h).round(new MathContext(digits, RoundingMode.UP)).toPlainString(); }
	public BigDecimal getVolume_24h_asBigDecimal() { return new BigDecimal(this.Volume_24h); }
	
	private Double Low_24h;
	public void setLow_24h(Double passVar) { this.Low_24h = passVar; }
	public Double getLow_24h() { return this.Low_24h; }
	public String getLow_24h(int digits) { return new BigDecimal(this.Low_24h).round(new MathContext(digits, RoundingMode.UP)).toPlainString(); }
	public BigDecimal getLow_24h_asBigDecimal() { return new BigDecimal(this.Low_24h); }
	
	private Double High_24h;
	public void setHigh_24h(Double passVar) { this.High_24h = passVar; }
	public Double getHigh_24h() { return this.High_24h; }
	public String getHigh_24h(int digits) { return new BigDecimal(this.High_24h).round(new MathContext(digits, RoundingMode.UP)).toPlainString(); }
	public BigDecimal getHigh_24h_asBigDecimal() { return new BigDecimal(this.High_24h); }
	
	private Double Volume_30day;
	public void setVolume_30day(Double passVar) { this.Volume_30day = passVar; }
	public Double getVolume_30day() { return this.Volume_30day; }
	public String getVolume_30day(int digits) { return new BigDecimal(this.Volume_30day).round(new MathContext(digits, RoundingMode.UP)).toPlainString(); }
	public BigDecimal getVolume_30day_asBigDecimal() { return new BigDecimal(this.Volume_30day); }
	
	private Double Volume_30d;
	public void setVolume_30d(Double passVar) { this.Volume_30d = passVar; }
	public Double getVolume_30d() { return this.Volume_30d; }
	public String getVolume_30d(int digits) { return new BigDecimal(this.Volume_30d).round(new MathContext(digits, RoundingMode.UP)).toPlainString(); }
	public BigDecimal getVolume_30d_asBigDecimal() { return new BigDecimal(this.Volume_30d); }
	
	private Double Best_bid;
	public void setBest_bid(Double passVar) { this.Best_bid = passVar; }
	public Double getBest_bid() { return this.Best_bid; }
	public String getBest_bid(int digits) { return new BigDecimal(this.Best_bid).round(new MathContext(digits, RoundingMode.UP)).toPlainString(); }
	public BigDecimal getBest_bid_asBigDecimal() { return new BigDecimal(this.Best_bid); }
	
	private Double Best_ask;
	public void setBest_ask(Double passVar) { this.Best_ask = passVar; }
	public Double getBest_ask() { return this.Best_ask; }
	public String getBest_ask(int digits) { return new BigDecimal(this.Best_ask).round(new MathContext(digits, RoundingMode.UP)).toPlainString(); }
	public BigDecimal getBest_ask_asBigDecimal() { return new BigDecimal(this.Best_ask); }
	
	private String Trade_id;
	public void setTrade_id(String passVar) { this.Trade_id = passVar; }
	public String getTrade_id() { return this.Trade_id; }
	
	private Double Last_size;
	public void setLast_size(Double passVar) { this.Last_size = passVar; }
	public Double getLast_size() { return this.Last_size; }
	public String getLast_size(int digits) { return new BigDecimal(this.Last_size).round(new MathContext(digits, RoundingMode.UP)).toPlainString(); }
	public BigDecimal getLast_size_asBigDecimal() { return new BigDecimal(this.Last_size); }
	
	private String[][] Bids;
	public void setBids(String[][] passVar) { this.Bids = passVar; }
	public String[][] getBids() { return this.Bids; }
	
	private String[][] Asks;
	public void setAsks(String[][] passVar) { this.Asks = passVar; }
	public String[][] getAsks() { return this.Asks; }
	
	private String Last_trade_id;
	public void setLast_trade_id(String passVar) { this.Name = passVar; }
	public String getLast_trade_id() { return this.Last_trade_id; }

	private String Name;
	public void setName(String passVar) { this.Name = passVar; }
	public String getName() { return this.Name; }
	
	private Double Min_size;
	public void setMin_size(Double passVar) { this.Min_size = passVar; }
	public Double getMin_size() { return this.Min_size; }
	public String getMin_size(int digits) { return new BigDecimal(this.Min_size).round(new MathContext(digits, RoundingMode.UP)).toPlainString(); }
	public BigDecimal getMin_size_asBigDecimal() { return new BigDecimal(this.Min_size); }
	
	private Double Max_precision;
	public void setMax_precision(Double passVar) { this.Max_precision = passVar; }
	public Double getMax_precision() { return this.Max_precision; }
	public String getMax_precision(int digits) { return new BigDecimal(this.Max_precision).round(new MathContext(digits, RoundingMode.UP)).toPlainString(); }
	public BigDecimal getMax_precision_asBigDecimal() { return new BigDecimal(this.Max_precision); }
	
	private String Message;
	public void setMessage(String passVar) { this.Message = passVar; }
	public String getMessage() { return this.Message; }
	
	private String[] Convertible_to;
	public void setConvertible_to(String[] passVar) { this.Convertible_to = passVar; }
	public String[] getConvertible_to() { return this.Convertible_to; }
	
	private String Funding_account_id;
	public void setFunding_account_id(String passVar) { this.Funding_account_id = passVar; }
	public String getFunding_account_id() { return this.Funding_account_id; }
	
	private String Status_message;
	public void setStatus_message(String passVar) { this.Status_message = passVar; }
	public String getStatus_message() { return this.Status_message; }

	private Double Amount;
	public void setAmount(Double passVar) { this.Amount = passVar; }
	public Double getAmount() { return this.Amount; }
	public String getAmount(int digits) { return new BigDecimal(this.Amount).round(new MathContext(digits, RoundingMode.UP)).toPlainString(); }
	public BigDecimal getAmount_asBigDecimal() { return new BigDecimal(this.Amount); }
	
	private String From_account_id;
	public void setFrom_account_id(String passVar) { this.From_account_id = passVar; }
	public String getFrom_account_id() { return this.From_account_id; }
	
	private String To_account_id;
	public void setTo_account_id(String passVar) { this.To_account_id = passVar; }
	public String getTo_account_id() { return this.To_account_id; }
	
	private String From;
	public void setFrom(String passVar) { this.From = passVar; }
	public String getFrom() { return this.From; }
	
	private String To;
	public void setTo(String passVar) { this.To = passVar; }
	public String getTo() { return this.To; }

	private Double Price;
	public void setPrice(Double passVar) { this.Price = passVar; }
	public Double getPrice() { return this.Price; }
	public String getPrice(int digits) { return new BigDecimal(this.Price).round(new MathContext(digits, RoundingMode.UP)).toPlainString(); }
	public BigDecimal getPrice_asBigDecimal() { return new BigDecimal(this.Price); }
	
	private Double Size;
	public void setSize(Double passVar) { this.Size = passVar; }
	public Double getSize() { return this.Size; }
	public String getSize(int digits) { return new BigDecimal(this.Size).round(new MathContext(digits, RoundingMode.UP)).toPlainString(); }
	public BigDecimal getSize_asBigDecimal() { return new BigDecimal(this.Size); }
	
	private String Product_id;
	public void setProduct_id(String passVar) { this.Product_id = passVar; }
	public String getProduct_id() { return this.Product_id; }
	
	private String Side;
	public void setSide(String passVar) { this.Side = passVar; }
	public String getSide() { return this.Side; }
	
	private Double Funds;
	public void setFunds(Double passVar) { this.Funds = passVar; }
	public Double getFunds() { return this.Funds; }
	public String getFunds(int digits) { return new BigDecimal(this.Funds).round(new MathContext(digits, RoundingMode.UP)).toPlainString(); }
	public BigDecimal getFunds_asBigDecimal() { return new BigDecimal(this.Funds); }
	
	private Double Specified_funds;
	public void setSpecified_funds(Double passVar) { this.Specified_funds = passVar; }
	public Double getSpecified_funds() { return this.Specified_funds; }
	public String getSpecified_funds(int digits) { return new BigDecimal(this.Specified_funds).round(new MathContext(digits, RoundingMode.UP)).toPlainString(); }
	public BigDecimal getSpecified_funds_asBigDecimal() { return new BigDecimal(this.Specified_funds); }
	
	private String Type;
	public void setType(String passVar) { this.Type = passVar; }
	public String getType() { return this.Type; }
	
	private String Time_in_force;
	public void setTime_in_force(String passVar) { this.Time_in_force = passVar; }
	public String getTime_in_force() { return this.Time_in_force; }
	
	private String Expire_time;
	public void setExpire_time(String passVar) { this.Expire_time = passVar; }
	public String getExpire_time() { return this.Expire_time; }

	private Boolean Post_only;
	public void setPost_only(Boolean passVar) { this.Post_only = passVar; }
	public Boolean getPost_only() { return this.Post_only; }

	private String Created_at;
	public void setCreated_at(String passVar) { this.Created_at = passVar; }
	public String getCreated_at() { return this.Created_at; }
	
	private String Done_at;
	public void setDone_at(String passVar) { this.Done_at = passVar; }
	public String getDone_at() { return this.Done_at; }
	
	private String Done_reason;
	public void setDone_reason(String passVar) { this.Done_reason = passVar; }
	public String getDone_reason() { return this.Done_reason; }
	
	private String Reject_reason;
	public void setReject_reason(String passVar) { this.Reject_reason = passVar; }
	public String getReject_reason() { return this.Reject_reason; }
	
	private Double Fill_fees;
	public void setFill_fees(Double passVar) { this.Fill_fees = passVar; }
	public Double getFill_fees() { return this.Fill_fees; }
	public String getFill_fees(int digits) { return new BigDecimal(this.Fill_fees).round(new MathContext(digits, RoundingMode.UP)).toPlainString(); }
	public BigDecimal getFill_fees_asBigDecimal() { return new BigDecimal(this.Fill_fees); }
	
	private Double Filled_size;
	public void setFilled_size(Double passVar) { this.Filled_size = passVar; }
	public Double getFilled_size() { return this.Filled_size; }
	public String getFilled_size(int digits) { return new BigDecimal(this.Filled_size).round(new MathContext(digits, RoundingMode.UP)).toPlainString(); }
	public BigDecimal getFilled_size_asBigDecimal() { return new BigDecimal(this.Filled_size); }
	
	private Double Executed_value;
	public void setExecuted_value(Double passVar) { this.Executed_value = passVar; }
	public Double getExecuted_value() { return this.Executed_value; }
	public String getExecuted_value(int digits) { return new BigDecimal(this.Executed_value).round(new MathContext(digits, RoundingMode.UP)).toPlainString(); }
	public BigDecimal getExecuted_value_asBigDecimal() { return new BigDecimal(this.Executed_value); }
	
	private String Status;
	public void setStatus(String passVar) { this.Status = passVar; }
	public String getStatus() { return this.Status; }

	private Boolean Settled;
	public void setSettled(Boolean passVar) { this.Settled = passVar; }
	public Boolean getSettled() { return this.Settled; }

	private Boolean Is_default;
	public void setIs_default(Boolean passVar) { this.Is_default = passVar; }
	public Boolean getIs_default() { return this.Is_default; }

	private Boolean Has_margin;
	public void setHas_margin(Boolean passVar) { this.Has_margin = passVar; }
	public Boolean getHas_margin() { return this.Has_margin; }

	private String Stop;
	public void setStop(String passVar) { this.Stop = passVar; }
	public String getStop() { return this.Stop; }

	private String Completed_at;
	public void setCompleted_at(String passVar) { this.Completed_at = passVar; }
	public String getCompleted_at() { return this.Completed_at; }

	private String Expires_at;
	public void setExpires_at(String passVar) { this.Expires_at = passVar; }
	public String getExpires_at() { return this.Expires_at; }

	private String File_url;
	public void setFile_url(String passVar) { this.File_url = passVar; }
	public String getFile_url() { return this.File_url; }
	
	private Double Stop_price;
	public void setStop_price(Double passVar) { this.Stop_price = passVar; }
	public Double getStop_price() { return this.Stop_price; }
	public String getStop_price(int digits) { return new BigDecimal(this.Stop_price).round(new MathContext(digits, RoundingMode.UP)).toPlainString(); }
	public BigDecimal getStop_price_asBigDecimal() { return new BigDecimal(this.Stop_price); }
	
	private Double Funding_amount;
	public void setFunding_amount(Double passVar) { this.Funding_amount = passVar; }
	public Double getFunding_amount() { return this.Funding_amount; }
	public String getFunding_amount(int digits) { return new BigDecimal(this.Funding_amount).round(new MathContext(digits, RoundingMode.UP)).toPlainString(); }
	public BigDecimal getFunding_amount_asBigDecimal() { return new BigDecimal(this.Funding_amount); }
	
	private String Client_oid;
	public void setClient_oid(String passVar) { this.Client_oid = passVar; }
	public String getClient_oid() { return this.Client_oid; }
	
	private String Stp;
	public void setStp(String passVar) { this.Stp = passVar; }
	public String getStp() { return this.Stp; }
	
	private String ID;
	public void setID(String passVar) { this.ID = passVar; }
	public String getID() { return this.ID; }
	
	private String Currency;
	public void setCurrency(String passVar) { this.Currency = passVar; }
	public String getCurrency() { return this.Currency; }
	
	private Double Balance;
	public void setBalance(Double passVar) { this.Balance = passVar; }
	public Double getBalance() { return this.Balance; }
	public String getBalance(int digits) { return new BigDecimal(this.Balance).round(new MathContext(digits, RoundingMode.UP)).toPlainString(); }
	public BigDecimal getBalance_asBigDecimal() { return new BigDecimal(this.Balance); }
	
	private Double Available;
	public void setAvailable(Double passVar) { this.Available = passVar; }
	public Double getAvailable() { return this.Available; }
	public String getAvailable(int digits) { return new BigDecimal(this.Available).round(new MathContext(digits, RoundingMode.UP)).toPlainString(); }
	public BigDecimal getAvailable_asBigDecimal() { return new BigDecimal(this.Available); }
	
	private Double Hold;
	public void setHold(Double passVar) { this.Hold = passVar; }
	public Double getHold() { return this.Hold; }
	public String getHold(int digits) { return new BigDecimal(this.Hold).round(new MathContext(digits, RoundingMode.UP)).toPlainString(); }
	public BigDecimal getHold_asBigDecimal() { return new BigDecimal(this.Hold); }
	
	private String Profile_id;
	public void setProfile_id(String passVar) { this.Profile_id = passVar; }
	public String getProfile_id() { return this.Profile_id; }
	
	private Boolean Trading_enabled;
	public void setTrading_enabled(Boolean passVar) { this.Trading_enabled = passVar; }
	public Boolean getTrading_enabled() { return this.Trading_enabled; }
}
