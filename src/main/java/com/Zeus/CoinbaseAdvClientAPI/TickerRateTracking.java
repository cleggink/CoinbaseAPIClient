package com.Zeus.CoinbaseAdvClientAPI;

import java.time.Instant;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Map;
import java.util.HashMap;

public class TickerRateTracking {
	
	public class TickerData {
		
		Integer ticks;
		Double rate;
		Long timeStart;
	}
	private Map<String, TickerData> data;
	Boolean hold;

	public TickerRateTracking() {
		
		this.data = new HashMap<String, TickerData>();
		this.hold = false;
	}
	
	public void resetData(String product) throws InterruptedException {

		if(this.data.isEmpty()) {
			return;
		}
		this.blocking();
		this.data.replace(product, this.dataInit());
		this.hold = false;
	}
	
	public Double getRatePerSec(String product) throws InterruptedException {
				
		while(this.data.get(product).ticks < 100) {
			Thread.sleep(100);
		}
		this.blocking();
		this.data.get(product).rate = this.calcRate(product);
		this.hold = false;
		Double returnRate = this.data.get(product).rate;
		this.resetData(product);
		return returnRate;
	}
	
	private Double calcRate(String product) throws InterruptedException {
		
		int _ticks = this.data.get(product).ticks;
		long _secs = Instant.now().getEpochSecond() - this.data.get(product).timeStart + 1;
		Double returnVal = (double)_ticks / (double)_secs;
		return returnVal;
	}
	
	private void blocking() throws InterruptedException {
		
		while(this.hold) {
			Thread.sleep(100);
		}
	}
	
	public void addTick(String product) throws InterruptedException {
		
		this.blocking();
		
		if(this.data.containsKey(product)) {
			this.data.get(product).ticks++;
		}
		this.hold = false;
	}
	
	public void addProduct(String product) throws InterruptedException {
		
		this.blocking();
		this.data.put(product, dataInit());
		this.hold = false;
	}

	public void delProduct(String product) throws InterruptedException {
		
		this.blocking();
		
		if(this.data.containsKey(product)) {
			this.data.remove(product);
		}
		this.hold = false;
	}
	
	private TickerData dataInit() {
		
		TickerData returnVal = new TickerData();
		returnVal.ticks = 0;
		returnVal.rate = 0.0;
		returnVal.timeStart = Instant.now().getEpochSecond();
		return returnVal;
	}	
}