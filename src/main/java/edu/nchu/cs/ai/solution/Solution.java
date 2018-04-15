package edu.nchu.cs.ai.solution;

public abstract class Solution<T> {
	private T answer;
	private int cost;
	private int benefit;

	public Solution(T answer) {
		this.answer = answer;
	}

	public void setAns(T answer) {
		this.answer = answer;
	}

	public T getAns() {
		return this.answer;
	}

	public void setCost(int cost) {
		this.cost = cost;
	}

	public int getCost() {
		return this.cost;
	}

	public void setBenefit(int benefit) {
		this.benefit = benefit;
	}

	public int getBenefit() {
		return this.benefit;
	}

	@Override
	public abstract String toString();
}
