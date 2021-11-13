package com.tcc.analisys.histogram;

import org.apache.commons.math3.stat.Frequency;

import java.util.*;

public class Histogram {
    List<Integer> datasetList;
    private Map distributionMap;
    private int classWidth;

    public Histogram() {
        this.distributionMap = new TreeMap();
        this.datasetList = Arrays.asList(
                36, 25, 38, 46, 55, 68, 72,
                55, 36, 38, 67, 45, 22, 48,
                91, 46, 52, 61, 58, 55);

        this.classWidth = doaneFormula(datasetList.size());

        Map distributionMap = processRawData();
        List yData = new ArrayList();
        yData.addAll(distributionMap.values());
        List xData = Arrays.asList(distributionMap.keySet().toArray());
    }

    public Histogram(List<Integer> datasetList) {
        this.distributionMap = new TreeMap();
        this.datasetList = datasetList;

        this.classWidth = doaneFormula(this.datasetList.size());

        Map distributionMap = processRawData();
        List yData = new ArrayList();
        yData.addAll(distributionMap.values());
        List xData = Arrays.asList(distributionMap.keySet().toArray());
    }

    public static void main(String[] args) {
        Histogram a = new Histogram();
        System.out.println(a.distributionMap);
    }

    public int howManyClasses() {
        return distributionMap.size();
    }

    public int whatsTheClass(int n) {
        return (int) (n / classWidth);
    }

    private Map processRawData() {
        Frequency frequency = new Frequency();
        datasetList.forEach(d -> frequency.addValue(Double.parseDouble(d.toString())));

        datasetList.stream()
                .map(d -> Double.parseDouble(d.toString()))
                .distinct()
                .forEach(observation -> {
                    long observationFrequency = frequency.getCount(observation);
                    int upperBoundary = (observation > classWidth)
                            ? Math.multiplyExact((int) Math.ceil(observation / classWidth), (int) classWidth)
                            : (int) classWidth;
                    int lowerBoundary = (upperBoundary > classWidth)
                            ? Math.subtractExact(upperBoundary, (int) classWidth)
                            : 0;
                    String bin = lowerBoundary + "-" + upperBoundary;

                    updateDistributionMap(lowerBoundary, bin, observationFrequency);

                });

        return distributionMap;
    }

    private void updateDistributionMap(int lowerBoundary, String bin, long observationFrequency) {

        int prevLowerBoundary = (lowerBoundary > classWidth) ? (int) (lowerBoundary - classWidth) : 0;
        String prevBin = prevLowerBoundary + "-" + lowerBoundary;
        if ((!prevBin.equals("0-0")) && (!distributionMap.containsKey(prevBin))) {
            System.out.println(!prevBin.equals("0-0"));
            distributionMap.put(prevBin, 0);
        }


        if (!distributionMap.containsKey(bin)) {
            distributionMap.put(bin, observationFrequency);
        } else {
            long oldFrequency = Long.parseLong(distributionMap.get(bin).toString());
            distributionMap.replace(bin, oldFrequency + observationFrequency);
        }
    }

    private int doaneFormula(int n) {
        double parteDeCimaDaDivisao = 6 * (n - 2);
        double parteDeBaixoDaDivisao = (n + 1) * (n + 3);

        double estimativaDistorcaoTerceiroMomento = Math.sqrt(parteDeCimaDaDivisao / parteDeBaixoDaDivisao);
        return (int) (1 + log2(n) + log2(1 + Math.abs(estimativaDistorcaoTerceiroMomento) / estimativaDistorcaoTerceiroMomento));
    }

    private double log2(double n) {
        return (Math.log(n) / Math.log(2));
    }
}
