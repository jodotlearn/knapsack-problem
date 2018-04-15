package edu.nchu.cs.ai.bean;

public class Item {
	private String name;
	private int cost;
	private int benefit;
	public Item(String name, int cost, int benefit) {
		this.name = name;
		this.cost = cost;
		this.benefit = benefit;
	}
	public String getName() {
		return this.name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getCost() {
		return this.cost;
	}
	public void setCost(int cost) {
		this.cost = cost;
	}
	public int getBenefit() {
		return this.benefit;
	}
	public void setBenefit(int benefit) {
		this.benefit = benefit;
	}

}
