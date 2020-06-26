package com.reader;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.concurrent.BlockingQueue;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.model.Packet;

public class ReaderThread implements Runnable {
	
	private final BlockingQueue<Packet> queue;
	
	public ReaderThread(BlockingQueue<Packet> queue){
		this.queue = queue;
	}

	public void run() {
		String currentLine;		
		Packet packet;
		ObjectMapper mapper = new ObjectMapper();
		try {
	         ClassLoader classLoader = getClass().getClassLoader();
	         File file = new File(classLoader.getResource("trades.json").getFile());
	         BufferedReader reader = new BufferedReader(new FileReader(file));
	             while ((currentLine = reader.readLine()) != null) {	            	
	            	packet = mapper.readValue(currentLine, Packet.class);
	                queue.add(packet);
	             }
	             reader.close();	             
		} catch(Exception ex){
			System.err.print("Exception " + ex);
		}
	}

	
}
