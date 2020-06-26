package com.fsm;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.model.Packet;

public class FxTradeFeeder {
	
	private FSMChart fsmChart;
	private String stockTradeFile; 
	private int timeToEmulate;
	private ExecutorService executorService;
	
	public FxTradeFeeder(FSMChart fsmChart, String stockTradeFile, int timeToEmulate) {
		this.fsmChart = fsmChart;
		this.stockTradeFile = stockTradeFile;
		this.timeToEmulate = timeToEmulate;
		this.executorService = Executors.newCachedThreadPool();
	}
	
	public void run() {
		executorService.execute(() -> read());
	}
	
	private void read() {
		ObjectMapper mapper = new ObjectMapper();
		try (BufferedReader br = new BufferedReader(new InputStreamReader(this.getClass().getResourceAsStream(stockTradeFile)))) {
			while(true) {
				Thread.sleep(timeToEmulate);
				String currentLine = br.readLine();
				if (currentLine != null) {
					Packet packet = mapper.readValue(currentLine, Packet.class);
					fsmChart.onTrade(packet);
				}else {
					executorService.shutdown();
					break;
				}
			}
			
		} catch (IOException e) {
			e.printStackTrace();			
		} catch (InterruptedException e) {
			e.printStackTrace();
		} finally {
			executorService.shutdown();
		}
	}

}
