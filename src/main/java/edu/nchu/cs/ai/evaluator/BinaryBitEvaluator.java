package edu.nchu.cs.ai.evaluator;

import edu.nchu.cs.ai.solution.Solution;

public class BinaryBitEvaluator implements Evaluator {

	@Override
	public Solution evaluate(Solution solution) {
		int cost = 0;
		int benefit = 0;
		int[] ans = (int[]) solution.getAns();
		for (int i=0;i<ans.length;i++) {
			benefit += ans[i];
		}
		solution.setCost(cost);;
		solution.setBenefit(benefit);
		return solution;
	}

}
