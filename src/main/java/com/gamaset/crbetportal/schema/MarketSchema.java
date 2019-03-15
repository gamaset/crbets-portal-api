package com.gamaset.crbetportal.schema;

import java.util.ArrayList;
import java.util.List;

import com.gamaset.crbetportal.integration.betfair.aping.entities.MarketCatalogue;
import com.gamaset.crbetportal.integration.betfair.aping.entities.Runner;
import com.gamaset.crbetportal.integration.betfair.aping.entities.RunnerCatalog;

public class MarketSchema {

	private String marketId;
	private String marketName;
	private List<PriceMarketSchema> prices;

	public MarketSchema(String marketId, String marketName) {
		this.marketId = marketId;
		this.marketName = marketName;
		this.prices = new ArrayList<>();
	}

	public MarketSchema withPrices(List<Runner> runners, List<MarketCatalogue> listMarketCatalogue) {
		for (Runner runner : runners) {
			for (MarketCatalogue mc : listMarketCatalogue) {
				if (mc.getMarketId().equals(marketId)) {
					
					for (RunnerCatalog rn : mc.getRunners()) {
						if (runner.getSelectionId().equals(rn.getSelectionId())) {
							PriceMarketSchema pm = new PriceMarketSchema();
							if(runner.getEx().getAvailableToBack().size() > 0) {
								pm.setOdd(runner.getEx().getAvailableToBack()
									.get(runner.getEx().getAvailableToBack().size() - 1).getPrice());
							}
							pm.setSelectionId(rn.getSelectionId());
							pm.setSelectionName(rn.getRunnerName());
							this.prices.add(pm);
						}
						
					}
				}
			}		
		}
		return this;
	}

	public String getMarketId() {
		return marketId;
	}

	public void setMarketId(String marketId) {
		this.marketId = marketId;
	}

	public String getMarketName() {
		return marketName;
	}

	public void setMarketName(String marketName) {
		this.marketName = marketName;
	}

	public List<PriceMarketSchema> getPrices() {
		return prices;
	}

	public void setPrices(List<PriceMarketSchema> prices) {
		this.prices = prices;
	}
}
