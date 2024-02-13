package com.Zeus.CoinbaseAdvClientAPI;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Map;
import java.util.HashMap;

public class TickerRateTracking {
	
	public class TickerData {
		
		Integer ticks;
		Double rate;
		Long timeStart;
		Boolean stalled;
		Integer stalledTimer;
	}
	private Map<String, TickerData> data;
	private Boolean hold;
	private Integer minTicks;
	private Double stallMultiplier;

 	public TickerRateTracking() {
		
		this.data = new HashMap<String, TickerData>();
		this.hold = false;
		this.minTicks = 100;
		this.stallMultiplier = 2.0;
	}
	
	public void resetData(String product) throws InterruptedException {

		if(this.data.isEmpty()) {
			return;
		}
		this.blocking();
		this.data.get(product).rate = 0.0;
		this.data.get(product).ticks = 0;
		this.data.get(product).timeStart = Instant.now().getEpochSecond();;
		this.hold = false;
	}

	public void setMinTicks(Integer min) {
		
		this.minTicks = min;
	}
	
	public void setStallMultiplier(Double passVar) {
		
		this.stallMultiplier = passVar;
	}
	
	private void setStalledTimer(String product, Integer seconds) {
		
		this.data.get(product).stalledTimer = seconds;
	}
	
	public Double getRatePerSec(String product) throws InterruptedException {
				
		this.data.get(product).stalled = false;
		long testTime = Instant.now().getEpochSecond();
		
		while(this.data.get(product).ticks < this.minTicks) {
			
			if(Instant.now().getEpochSecond() > testTime + this.data.get(product).stalledTimer) {
				this.data.get(product).stalled = true;
				return -1.0;		
			}
			Thread.sleep(100);
		}
		this.blocking();
		this.data.get(product).rate = this.calcRate(product);
		this.hold = false;
		Double returnRate = this.data.get(product).rate;
		this.calcStallTimer(product);
		this.resetData(product);
		return returnRate;
	}
	
	private void calcStallTimer(String product) {

		if(this.data.get(product).rate > 0.0) {
			this.setStalledTimer(product, 
					new BigDecimal((this.minTicks / this.data.get(product).rate) * this.stallMultiplier).intValue());
		}
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
		returnVal.stalled = false;
		returnVal.stalledTimer = 600;
		returnVal.timeStart = Instant.now().getEpochSecond();
		return returnVal;
	}
}