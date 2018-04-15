package edu.nchu.cs.ai.solution;

public class ItemSolution extends Solution<int[]> {

	public ItemSolution(int[] answer) {
		super(answer);
	}

	@Override
	public String toString() {
		StringBuilder buf = new StringBuilder();
		for (int i = 0; i < this.getAns().length; i++) {
			buf.append(this.getAns()[i]);
		}
		buf.append(", benefit:").append(this.getBenefit()).append(", cost:").append(this.getCost());
		return buf.toString();
	}

}
