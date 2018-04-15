package edu.nchu.cs.ai.transitor;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import edu.nchu.cs.ai.solution.Solution;

public class MutationTransitor implements Transitor<List<Solution>> {
	private List<Solution> current;
	private double rate;

	public MutationTransitor(double allowRate) {
		this.rate = allowRate;
	}

	@Override
	public void setCurrent(List<Solution> current) {
		this.current = new ArrayList(current);
	}

	@Override
	public List<Solution> transit() {
		List<Solution> mutationChilds = new ArrayList(this.current);
		int[] childAns;
		int range = ((int[]) this.current.get(0).getAns()).length;
		Random rnd = new Random();
		for (int i=0;i<this.current.size();i++) {
			if (rnd.nextDouble() <= this.rate) {
				int idx = rnd.nextInt(range);
				childAns = (int[]) mutationChilds.get(i).getAns();
				childAns[idx] ^= 1;
				mutationChilds.get(i).setAns(childAns);
			}
		}
		return mutationChilds;
	}

}
