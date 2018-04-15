package edu.nchu.cs.ai.evaluator;

import java.util.List;

import edu.nchu.cs.ai.bean.Item;
import edu.nchu.cs.ai.solution.Solution;

public class ItemEvaluator implements Evaluator {
	private List<Item> items;
	private int limitSize;

	public ItemEvaluator(List<Item> items, int limitSize) {
		this.items = items;
		this.limitSize = limitSize;
	}

	@Override
	public Solution evaluate(Solution solution) {
		int cost = 0;
		int benefit = 0;
		int[] ans = (int[]) solution.getAns();
		for (int i=0;i<ans.length;i++) {
			if (ans[i] == 1) {
				Item item = this.items.get(i);
				cost += item.getCost();
				benefit += item.getBenefit();
			}
		}
		if (cost > this.limitSize) {
			benefit = 1;
		}
		solution.setCost(cost);;
		solution.setBenefit(benefit);
		return solution;
	}

}
