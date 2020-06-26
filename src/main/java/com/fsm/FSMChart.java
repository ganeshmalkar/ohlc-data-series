package com.fsm;

import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import com.model.Ohlc;
import com.model.Packet;
import com.util.MathUtils;
import com.util.TimeUtils;

public class FSMChart{
	//Every 15 seconds
	private int timeInterval = 15;	
	private Packet firstPacket = null;
	private double open = 0.0;
	private double close = 0.0;
	private double low = 0.0;
	private double high = 0.0;
	private double volume = 0.0;
	
	private long firstPacketTime = 0;	
	private Map<String, BlockingQueue<Ohlc>> seriesData;
	
	public FSMChart(Map<String, BlockingQueue<Ohlc>> seriesData){
		this.seriesData = seriesData;
	}
	
	public void printBar(long time, double open, double high, double low, double close, double volume, String key){
		BlockingQueue<Ohlc> queue = null;
		if(seriesData.containsKey(key)) {				
			BlockingQueue<Ohlc> ohlcQueue = seriesData.get(key);
			ohlcQueue.add(new Ohlc(open, high, low, close, volume, TimeUtils.convertToReadableFormat(time)));
		} else {
			queue = new LinkedBlockingQueue<Ohlc>();
			queue.add(new Ohlc(open, high, low, close, volume, TimeUtils.convertToReadableFormat(time)));
			seriesData.put(key, queue);
		}
	}
	
	public void onTrade(Packet t) {
		double price = t.getP();
		if (firstPacket != null) {
			long time = t.getCurrentTimeInSeconds();			
			if (timeInterval == (int) (time - firstPacketTime)) {
				// Set the period close price
				close = MathUtils.roundDouble(price, MathUtils.TWO_DEC_DOUBLE_FORMAT);
				System.out.println("System " + t.getSym() + " Time " + TimeUtils.convertToReadableFormat(time) + " Open " + open + " High " + high + " Low " + low + " Close " + close + " Volume " + volume);
				// Add new candle
				printBar(time, open, high, low, close, volume, t.getSym());
				// Reset the intervalFirstPrint to null
				firstPacket = null;
			} else {
				// Set the current low price
				if (MathUtils.roundDouble(price, MathUtils.TWO_DEC_DOUBLE_FORMAT) < low)
					low = MathUtils.roundDouble(price, MathUtils.TWO_DEC_DOUBLE_FORMAT);

				// Set the current high price
				if (MathUtils.roundDouble(price, MathUtils.TWO_DEC_DOUBLE_FORMAT) > high)
					high = MathUtils.roundDouble(price, MathUtils.TWO_DEC_DOUBLE_FORMAT);

				volume += t.getQ();
			}
			
		} else {
			// Set intervalFirstPrint
			firstPacket = t;
			// the first trade price in the day (day open price)
			open = MathUtils.roundDouble(price, MathUtils.TWO_DEC_DOUBLE_FORMAT);
			// the interval low
			low = MathUtils.roundDouble(price, MathUtils.TWO_DEC_DOUBLE_FORMAT);
			// the interval high
			high = MathUtils.roundDouble(price, MathUtils.TWO_DEC_DOUBLE_FORMAT);
			// set the initial volume
			volume = t.getQ();
			
			firstPacketTime = t.getCurrentTimeInSeconds();
		}
	}

}
