package com.cassey.house.command;

import com.cassey.house.env.Command;

import java.util.Arrays;
import java.util.List;

public class FundCommand extends Command {
    private String[] sourceFileNames = {"指数型-股票.csv", "混合型-平衡.csv", "债券型-可转债.csv", "商品（不含QDII）.csv", "QDII.csv", "混合型-偏债.csv", "混合型-灵活.csv", "债券型-混合债.csv", "债券型-长债.csv", "混合型-偏股.csv", "债券型-中短债.csv", "股票型.csv"};

    @Override
    public void run(String[] args) throws Exception {
        getTop();
    }

    private void getTop() {
        Arrays.stream(sourceFileNames).forEach(name -> {
            List<String> lines = environment.readFileByLines("fund", name);
            lines.forEach(System.out::println);
        });
    }
}
