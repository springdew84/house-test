package com.cassey.house.thread;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * 20221027
 * 甲乙轮流说数，谁先说到winnerNumber，谁就胜利，每人每次最多说perTimeMax个数字，不可以不说
 */
public class NumberWinner {
    public static void main(String[] args) {
        //int winnerNumber = inputInteger("请输入胜者的数字");
        //int perTimeMax = inputInteger("请输入每人最多说的数字个数");
        int winnerNumber = 30;
        int perTimeMax = 4;
        winner(winnerNumber, perTimeMax);
    }

    /**
     * 永远胜利的甲
     *
     * @param winnerNumber
     * @param perTimeMax
     */
    private static void winner(int winnerNumber, int perTimeMax) {
        int groupNum = perTimeMax + 1;
        int startNum = winnerNumber % groupNum;
        String strategy = "";
        if (startNum == 0) {
            strategy = "谦虚一点，让乙先说吧~~";
        } else {
            String nums = "";
            for (int i = 0; i < startNum; i++) {
                nums += (i + 1) + ",";
            }
            strategy = "自告奋勇，我甲先说~~:" + nums.substring(0, nums.length() - 1);
        }
        System.out.println("----使用的策略：" + strategy);

        test(winnerNumber, perTimeMax, startNum);
    }

    private static int inputInteger(String desc) {
        System.out.println(desc + "...(输入后请按回车)");
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        String input = null;
        try {
            input = br.readLine();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return Integer.parseInt(input.trim());
    }

    private static void test(int winnerNumber, int perTimeMax, int startNum) {
        NumberThread Number = new NumberThread(winnerNumber, perTimeMax, startNum);
        Thread thread1 = new Thread(Number);
        thread1.setName("甲");
        thread1.start();

        Thread thread2 = new Thread(Number);
        thread2.setName("乙");
        thread2.start();
    }

    static class NumberThread implements Runnable {
        private final int winnerNumber;
        private final int perTimeMax;
        private final int startNum;
        private int lastNumber = 0;
        private volatile List<Integer> nums = new ArrayList<>();

        public NumberThread(int winnerNumber, int perTimeMax, int startNum) {
            this.winnerNumber = winnerNumber;
            this.perTimeMax = perTimeMax;
            this.startNum = startNum;
        }

        @Override
        public void run() {
            synchronized (this) {
                while (nums.size() < winnerNumber) {
                    String name = Thread.currentThread().getName();

                    if (nums.isEmpty()) { //开头一个谁来说
                        System.out.println(name + ": 我开始表演了。。。");

                        if ((startNum == 0 && "甲".equals(name))) {
                            try {
                                System.out.println(name + ": 谦虚一点，让乙先说吧~~");
                                notify();
                                wait();
                            } catch (InterruptedException e) {
                                throw new RuntimeException(e);
                            }
                        } else if (startNum > 0 && "乙".equals(name)) {
                            try {
                                System.out.println(name + ": 谦虚一点，让甲先说吧~~");
                                notify();
                                wait();
                            } catch (InterruptedException e) {
                                throw new RuntimeException(e);
                            }
                        }
                    }

                    boolean res = say(name);
                    this.notify();
                    if (res) {
                        break;
                    } else {
                        try {
                            this.wait();
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }
            }
        }

        private boolean say(String name) {
            if ("甲".equals(name)) {
                return clever(name);
            }

            return stupid(name);
        }


        private boolean clever(String name) {
            int needSayNums;
            if (nums.isEmpty()) {
                needSayNums = startNum;
            } else {
                needSayNums = perTimeMax + 1 - lastNumber;
            }

            String numbers = "";
            int temp = 0;
            if (!nums.isEmpty()) temp = nums.get(nums.size() - 1);

            for (int i = 0; i < needSayNums; i++) {
                temp++;
                nums.add(temp);
                numbers += temp + ",";
                if (temp == winnerNumber) {
                    break;
                }
            }

            if (numbers.length() > 0) {
                System.out.println(name + ": 说了" + numbers.substring(0, numbers.length() - 1));
            } else {
                System.out.println(name + ": 说了个寂寞");
            }

            if (nums.size() == winnerNumber) {
                System.out.println("......" + name + "胜利了......!!!");
                return true;
            }

            return false;
        }

        private boolean stupid(String name) {
            Random random = new Random();
            int randomNum = Math.abs(random.nextInt() % perTimeMax) + 1;
            String numbers = "";
            int temp = 0;
            if (!nums.isEmpty()) temp = nums.get(nums.size() - 1);

            for (int i = 0; i < randomNum; i++) {
                temp++;
                nums.add(temp);
                numbers += temp + ",";
                if (temp == winnerNumber) {
                    break;
                }
            }
            lastNumber = randomNum;
            if (numbers.length() > 0) {
                System.out.println(name + ": 说了" + numbers.substring(0, numbers.length() - 1));
            }

            if (lastNumber == winnerNumber) {
                System.out.println("......" + name + "胜利了......!!!");
                return true;
            }

            return false;
        }
    }
}
