package edu.nchu.cs.ai.algorithms;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import edu.nchu.cs.ai.evaluator.Evaluator;
import edu.nchu.cs.ai.solution.ItemSolution;
import edu.nchu.cs.ai.solution.OptimumSolution;
import edu.nchu.cs.ai.solution.Solution;
import edu.nchu.cs.ai.transitor.Transitor;

/**
 * Genetic Algorithm
 * @author Jo
 *
 */
public class Genetic {
	private int spaceCount;
	private int iteration;
	private SelectionType selectType;
	private List<Solution> population;
	private Solution optimum;
	private int populationSize;

	private Transitor crossoverTransitor;
	private Transitor mutationTransitor;
	private Evaluator evaluator;

	private Genetic() {
		//not allow initialize a constructor without arguments
	}

	public Genetic(int spaceCount, int iteration, int populationSize, SelectionType type) {
		this.spaceCount = spaceCount;
		this.iteration = iteration;
		this.selectType = type;
		this.populationSize = populationSize;
	}

	public void setCrossoverTransitor(Transitor transitor) {
		this.crossoverTransitor = transitor;
	}
	public void setMutationTransitor(Transitor transitor) {
		this.mutationTransitor = transitor;
	}
	public void setEvaluator(Evaluator evaluator) {
		this.evaluator = evaluator;
	}

	public OptimumSolution run() {
		int cnt = 0;
		int best = -1;
		List<Solution> detail = new ArrayList<Solution>();
		List<Solution> candidates, childs, mutationChilds;
		OptimumSolution os = new OptimumSolution();
		//1. initial population
		this.population = this.initializePopulation();
		while (cnt < this.iteration) {
			//2. selection (determination)
			this.population = this.fitness(this.population);
			candidates = this.selection(this.selectType, this.population);

			//3. crossover
			this.crossoverTransitor.setCurrent(candidates);
			childs = (List<Solution>) this.crossoverTransitor.transit();

			//4. mutation
			this.mutationTransitor.setCurrent(childs);
			mutationChilds = (List<Solution>) this.mutationTransitor.transit();

			//5. determination
			for (Solution child:mutationChilds) {
				this.evaluator.evaluate(child);
				if (child.getBenefit() > best) {
					best = child.getBenefit();
					os.setSolution(child);
					this.optimum = child;
				}
			}
			detail.add(this.optimum);
			os.setExecuteDetail(detail);
			//update population
			this.population = new ArrayList(mutationChilds);
			cnt++;
		}

		return os;
	}
	private List<Solution> initializePopulation() {
		Random rnd = new Random();
		List<Solution> list = new ArrayList<Solution>();
		Solution solution;
		for (int i=0;i<this.populationSize;i++) {
			int[] gene = new int[this.spaceCount];
			for (int j=0;j<gene.length;j++) {
				gene[j] = rnd.nextInt(2);
			}
			solution = new ItemSolution(gene);
			list.add(solution);
		}
		return list;
	}
	private List<Solution> fitness(List<Solution> population){
		for (Solution solution:population) {
			solution = this.evaluator.evaluate(solution);
		}
		return population;
	}
	private List<Solution> selection(SelectionType selectType, List<Solution> candidates) {
		switch(selectType) {
			case ROULETTEWHEEL:
				return this.rouletteWheelSelect(candidates);
			case TOURNAMENT:
				return this.tournamentSelect(candidates);
			default:
				return this.tournamentSelect(candidates);//default
		}
	}

	private List<Solution> rouletteWheelSelect(List<Solution> candidates){
		int total = 0;
		List<Solution> newCandidates = new ArrayList<Solution>();
		float[] cdf = new float[candidates.size()];
		for (Solution candidate:candidates) {
			total += candidate.getBenefit();
		}
		for (int i=0;i<candidates.size();i++){
			if (total == 0) {
				cdf[i] = 0.25f * (i+1);
			}else {
				Solution candidate = candidates.get(i);
				if (i > 0) {
					cdf[i] = ((float) candidate.getBenefit() / total) + cdf[i-1];
					continue;
				}
				cdf[i] = (float) candidate.getBenefit() / total;
			}
		}
		for (int m=0;m<candidates.size();m++) {
			_loop:
			for (int n=0;n<cdf.length;n++) {
				if (Math.random() <= cdf[n]) {
					newCandidates.add(candidates.get(n));
					break _loop;
				}
			}
		}
		return newCandidates;
	}

	private List<Solution> tournamentSelect(List<Solution> candidates){
		List<Solution> newCandidates = new ArrayList<Solution>();
		Random rnd = new Random();
		int range = candidates.size();
		Solution op1, op2;
		for (Solution candidate:candidates) {
			op1 = candidates.get(rnd.nextInt(range));
			op2 = candidates.get(rnd.nextInt(range));
			newCandidates.add(op1.getBenefit()>op2.getBenefit()?op1:op2);
		}
		return newCandidates;
	}
}
