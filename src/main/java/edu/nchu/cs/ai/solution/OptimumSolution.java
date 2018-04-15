package edu.nchu.cs.ai.solution;

import java.util.List;

public class OptimumSolution {
	private Solution solution;
	private int objectiveValue;
	private List detail;

	public void setSolution(Solution solution) {
		this.solution = solution;
	}

	public Solution getSolution() {
		return this.solution;
	}

	public void setExecuteDetail(List detail) {
		this.detail = detail;
	}

	public List getExecuteDetail() {
		return this.detail;
	}
}
