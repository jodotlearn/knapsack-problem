package edu.nchu.cs.util;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CountDownLatch;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.stage.Stage;

public class Line extends Application{
	private static final CountDownLatch latch = new CountDownLatch(1);
	private static Line line;
	private String winTitle;
	private String chartTitle;
	private String axisXName;
	private String axisYName;
	private double width;
	private double height;
	private Stage stage;
	private Map<String,List<Integer>> data;

	public Line() {
		this.setInstance(this);
	}
	private static void setInstance(Line line0) {
		line = line0;
		latch.countDown();
	}
	public static Line getInstance() {
		try {
			latch.await();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return line;
	}
	public void setInfo(String winTitle, double width, double height, String chartTitle, String axisXName, String axisYName, Map<String,List<Integer>> data) {
		this.winTitle = winTitle;
		this.chartTitle = chartTitle;
		this.axisXName = axisXName;
		this.axisYName = axisYName;
		this.width = width;
		this.height = height;
		this.data = data;
		try {
			this.draw();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
//	public void setSeriesData(Map<String,List<Integer>> data) {
//		this.data = data;
//	}

	@Override
	public void start(Stage stage) throws Exception {
		this.stage = stage;
	}

	public void draw() throws Exception {
		this.stage.setTitle(this.winTitle);
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
			XYChart.Series series = new XYChart.Series();
			series.setName(seriesName);
			List<Integer> seriesData = this.data.get(seriesName);
			for (int i=0;i<seriesData.size();i++) {
				series.getData().add(new XYChart.Data(i+1, seriesData.get(i)));
			}
			lineChart.getData().add(series);
		}
		Scene scene  = new Scene(lineChart,this.width,this.height);
		this.stage.setScene(scene);
		this.stage.show();
	}

}