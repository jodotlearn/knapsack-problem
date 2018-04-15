package edu.nchu.cs.ai;

import edu.nchu.cs.ai.algorithms.Genetic;
import edu.nchu.cs.ai.algorithms.SelectionType;
import edu.nchu.cs.ai.evaluator.BinaryBitEvaluator;
import edu.nchu.cs.ai.solution.OptimumSolution;
import edu.nchu.cs.ai.transitor.CrossoverTransitor;
import edu.nchu.cs.ai.transitor.MutationTransitor;

public class OneMaxProblem {

	public static void main(String[] args) {
//		Genetic ga = new Genetic(5, 1, 4, SelectionType.ROULETTEWHEEL);
		Genetic ga = new Genetic(10, 100, 4, SelectionType.TOURNAMENT);
		ga.setEvaluator(new BinaryBitEvaluator());
		ga.setCrossoverTransitor(new CrossoverTransitor(0.6));
		ga.setMutationTransitor(new MutationTransitor(0.1));
		OptimumSolution os = ga.run();
		System.out.println();
		System.out.println("best:");
		System.out.println(os.getSolution());
	}

}
