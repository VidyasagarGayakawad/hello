package com.example.demo;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DemoApplicationReadInput {

    public static void main(String[] args) {
        String input = "[1,2][2,4][7,9]";
        String regex = "(\\[\\d+,\\d+\\])";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(input);

        List<Range> ranges = new ArrayList<Range>();
        while (matcher.find()) {
            String rangeStr = matcher.group();
            rangeStr = rangeStr.replaceAll("\\[", "");
            rangeStr = rangeStr.replaceAll("\\]", "");
            rangeStr = rangeStr.replaceAll("[ ]+]", "");
            String r[] = rangeStr.split(",");
            ranges.add(new Range(Integer.parseInt(r[0]), Integer.parseInt(r[1])));
        }

        ranges.sort(Comparator.comparing(Range::getLower));

        Range start = ranges.get(0);
        List<Range> finalRanges = new ArrayList<Range>(4);
        boolean lastRangeAdded = false;
        for (Range r : ranges) {
            if (start.getLower() <= r.getLower() && start.getHigher() >= r.getLower()) {
                start.setHigher(r.getHigher());
            } else {
                finalRanges.add(new Range(start.getLower(), start.getHigher()));
                start = r;
                lastRangeAdded = true;
            }
        }

        if (lastRangeAdded) {
            finalRanges.add(new Range(start.getLower(), start.getHigher()));
        }
        finalRanges.forEach(System.out::println);
    }
}

class Range {
    int lower;
    int higher;

    public Range(int lower, int heigher) {
        this.lower = lower;
        this.higher = heigher;
    }

    public int getLower() {
        return lower;
    }

    @Override
    public String toString() {
        return "[" + lower + "," + higher + "]";
    }

    public void setLower(int lower) {
        this.lower = lower;
    }

    public int getHigher() {
        return higher;
    }

    public void setHigher(int higher) {
        this.higher = higher;
    }
}

