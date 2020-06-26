package com.ohlc;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.BlockingQueue;

import com.client.OhlcClient;
import com.fsm.FSMChart;
import com.fsm.FxTradeFeeder;
import com.model.Ohlc;

public class OhlcApplication {
	
	public static final int PORT = 9000;
	public static final int EMULATION_TIME = 120;
	public static final String TRADES_JSON = "/trades.json";
	
	
	public static void main(String[] args) throws InterruptedException {
		Map<String, BlockingQueue<Ohlc>> seriesDataMap = new HashMap<>();
		
		FSMChart fsmChart = new FSMChart(seriesDataMap);
		FxTradeFeeder fxTradeThread = new FxTradeFeeder(fsmChart, TRADES_JSON, EMULATION_TIME);
		fxTradeThread.run();
		
		OhlcClient interactiveServer = new OhlcClient(PORT, seriesDataMap);
		new Thread(interactiveServer).start();
	}

}
