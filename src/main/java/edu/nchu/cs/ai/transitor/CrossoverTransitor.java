package edu.nchu.cs.ai.transitor;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import edu.nchu.cs.ai.solution.ItemSolution;
import edu.nchu.cs.ai.solution.Solution;

public class CrossoverTransitor implements Transitor<List<Solution>> {
	private List<Solution> current;
	private double rate;

	public CrossoverTransitor(double allowRate) {
		this.rate = allowRate;
	}

	@Override
	public void setCurrent(List<Solution> solution) {
		this.current = new ArrayList(solution);
	}

	@Override
	public List<Solution> transit() {
		Random rnd = new Random();
		int parentSize = this.current.size();
		int range = ((int[]) this.current.get(0).getAns()).length;
		List<Solution> childs = new ArrayList<Solution>();
		int[] parent1,parent2;
		Solution child;
		int[] childAns;

		while (childs.size() < this.current.size()) {
			int parent1Idx = rnd.nextInt(parentSize);
			int parent2Idx = rnd.nextInt(parentSize);
			if (rnd.nextDouble() <= this.rate) {
				parent1 = (int[]) this.current.get(parent1Idx).getAns();
				parent2 = (int[]) this.current.get(parent1Idx).getAns();
				int crossPos = rnd.nextInt(range) + 1;
				childAns = new int[range];
				System.arraycopy(parent1, 0, childAns, 0, crossPos);
				System.arraycopy(parent2, crossPos, childAns, crossPos, range - crossPos);
				childs.add(new ItemSolution(childAns));
				childAns = new int[range];
				System.arraycopy(parent2, 0, childAns, 0, crossPos);
				System.arraycopy(parent1, crossPos, childAns, crossPos, range - crossPos);
				childs.add(new ItemSolution(childAns));
			}
		}
		return childs;
	}

}
