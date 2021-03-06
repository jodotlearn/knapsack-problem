package edu.nchu.cs.ai;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;

import edu.nchu.cs.ai.algorithms.Genetic;
import edu.nchu.cs.ai.algorithms.SelectionType;
import edu.nchu.cs.ai.bean.Item;
import edu.nchu.cs.ai.evaluator.ItemEvaluator;
import edu.nchu.cs.ai.solution.OptimumSolution;
import edu.nchu.cs.ai.solution.Solution;
import edu.nchu.cs.ai.transitor.CrossoverTransitor;
import edu.nchu.cs.ai.transitor.MutationTransitor;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.stage.Stage;

/**
 * An application for testing Genetic Algorithm
 * 1. running Roulettewheel and Tournament and collecting the objective every iteration.
 * 2. averaging the value per iteration.
 * 3. draw the convergence chart.
 * @author Jo
 *
 */
public class KnapsackProblem extends Application{
	private static String winTitle;
	private static String chartTitle;
	private static String axisXName;
	private static String axisYName;
	private static double width;
	private static double height;
	private static Map<String,List<Integer>> data;

	/**
	 * java -jar knapsack.jar item_count population_size iterations run_times
	 * sample:
	 * 	10 items
	 * 	4 population
	 * 	2000 iterations
	 * 	execute 30 times
	 * java -jar knapsack.jar 10 4 2000 30
	 */
	public static void main(String[] args) {
		List<Item> items = new ArrayList<Item>();

		List<List<Solution>> totalDetail = new ArrayList<>();
		int itemCount = Integer.valueOf(args[0]);
		int population = Integer.valueOf(args[1]);
		int iteration = Integer.valueOf(args[2]);
		int runTimes = Integer.valueOf(args[3]);
		int cnt = 0;

		Random rnd = new Random();
		int weight = 0;
		int value = 0;
		int totalWeight = 0;
		for (int i=0;i<itemCount;i++) {
			weight = rnd.nextInt(10);
			value = rnd.nextInt(10) * 100;
			items.add(new Item("item"+(i+1), weight, value));
			totalWeight += weight;
		}
		int limit = (int) Math.round(totalWeight * 0.5);

		SelectionType[] types = {SelectionType.ROULETTEWHEEL,SelectionType.TOURNAMENT};
		Map<String, List<Integer>> chartData = new HashMap<>();
		List<Integer> avgData;
		List<Integer> tmp;
		String categoryName;
		for (SelectionType type: types) {
			totalDetail = new ArrayList<>();
			cnt = 0;
			while (cnt < runTimes) {
				Genetic ga = new Genetic(items.size(), iteration, population, type);
				ga.setEvaluator(new ItemEvaluator(items, limit));
				ga.setCrossoverTransitor(new CrossoverTransitor(0.6));
				ga.setMutationTransitor(new MutationTransitor(0.1));
				OptimumSolution os = ga.run();
				totalDetail.add(os.getExecuteDetail());
				cnt++;
			}
			avgData = new ArrayList<>();
			for (int i=0;i<totalDetail.get(0).size();i++) {
				tmp = new ArrayList<>();
				for (int j=0;j<totalDetail.size();j++) {
					tmp.add(totalDetail.get(j).get(i).getBenefit());
				}
				avgData.add((int) tmp.stream().mapToInt(val -> val).average().getAsDouble());
			}
			if (type == SelectionType.ROULETTEWHEEL) {
				categoryName = "ROULETTEWHEEL";
			}else {
				categoryName = "TOURNAMENT";
			}
			chartData.put(categoryName, avgData);
		}
		winTitle = "Convergence Chart";
		width = 800;
		height = 600;
		chartTitle = itemCount+" items, " + population + " population";
		axisXName = "Iteration";
		axisYName = "Objective Value";
		data = chartData;
		launch();
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		primaryStage.setTitle(this.winTitle);
		//defining the axis
		final NumberAxis xAxis = new NumberAxis();
		final NumberAxis yAxis = new NumberAxis();
		xAxis.setLabel(this.axisXName);
		yAxis.setLabel(this.axisYName);

		//creating the chart
		final LineChart<Number,Number> lineChart = new LineChart<Number,Number>(xAxis,yAxis);

		lineChart.setTitle(this.chartTitle);
		Iterator<String> ite = this.data.keySet().iterator();
		while(ite.hasNext()) {
			String seriesName = ite.next();
			XYChart.Series<Number,Number> series = new XYChart.Series<>();
			series.setName(seriesName);
			List<Integer> seriesData = this.data.get(seriesName);
			for (int i=0;i<seriesData.size();i++) {
				series.getData().add(new XYChart.Data<Number,Number>(i+1, seriesData.get(i)));
			}
			lineChart.getData().add(series);
		}
		//not show node
		for (XYChart.Series<Number,Number> series:lineChart.getData()) {
			for (XYChart.Data<Number,Number> data:series.getData()) {
				data.getNode().setVisible(false);
			}
		}
		Scene scene  = new Scene(lineChart,this.width,this.height);
		primaryStage.setScene(scene);
		primaryStage.show();
	}

}
