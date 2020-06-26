package com.model;

import java.util.concurrent.TimeUnit;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({"sym", "T", "P", "Q", "TS", "side", "TS2"})
public class Packet {
	@JsonProperty("sym")
	private String sym;
	@JsonProperty("T")
	private String T;
	@JsonProperty("P")
	private double P;
	@JsonProperty("Q")
	private double Q;
	@JsonProperty("TS")
	private String TS;
	@JsonProperty("side")
	private String side;
	@JsonProperty("TS2")
	private String TS2;
	
	public Packet() {}
	
	public Packet(String sym, String t, double p, double q, String tS, String side, String tS2) {
		super();
		this.sym = sym;
		T = t;
		P = p;
		Q = q;
		TS = tS;
		this.side = side;
		TS2 = tS2;
	}
	
	public String getSym() {
		return sym;
	}
	public void setSym(String sym) {
		this.sym = sym;
	}
	public String getT() {
		return T;
	}
	public void setT(String t) {
		T = t;
	}
	public double getP() {
		return P;
	}
	public void setP(double p) {
		P = p;
	}
	public double getQ() {
		return Q;
	}
	public void setQ(double q) {
		Q = q;
	}
	public String getTS() {
		return TS;
	}
	public void setTS(String tS) {
		TS = tS;
	}
	public String getSide() {
		return side;
	}
	public void setSide(String side) {
		this.side = side;
	}
	public String getTS2() {
		return TS2;
	}
	public void setTS2(String tS2) {
		TS2 = tS2;
	}
	
	public long getCurrentTimeInSeconds() {		
		return TimeUnit.MILLISECONDS.toSeconds(System.currentTimeMillis());
	}
	
	@Override
	public String toString() {
		return "Packet [sym=" + sym + ", T=" + T + ", P=" + P + ", Q=" + Q + ", TS=" + TS + ", side=" + side + ", TS2="
				+ TS2 + "]";
	}
	

}
