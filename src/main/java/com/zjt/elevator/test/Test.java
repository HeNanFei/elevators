package com.zjt.elevator.test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

/**
 * @author hyh.
 * @version 1.0
 * @Date: 2021/5/9 7:16
 */
public class Test {
    public static void main(String[] args) throws InterruptedException {
        int curr = 1;// 当前楼层
        List<Integer> up = new ArrayList<Integer>();// 上
        List<Integer> down = new ArrayList<Integer>();// 下

        int go = 0;//目标楼层

     /*   while (true) {*/
            System.out.println("input the number");
            Scanner sc = new Scanner(System.in);

            while (go != -1) {
                go = sc.nextInt();
                if (go > curr && go != -1)
                    up.add(go);
                if (go < curr && go != -1)
                    down.add(go);
            //}
            up.sort(null);
            Collections.sort(down, Collections.reverseOrder());
            for (int i = 0; i < up.size(); i++) {
                curr = runUp(curr, up.get(i));
            }
            up.clear();
            for (int i = 0; i < down.size(); i++) {
                curr = runDown(curr, down.get(i));
            }
            down.clear();
            go = curr;
        }
    }

    private static int runUp(int curr, int go) throws InterruptedException {
        int temp = curr;
        for (int i = 0; i < go - temp; i++) {
            System.out.println("-" + curr + "楼-");
            for (int j = 0; j < 3; j++) {
                System.out.println("···");
                Thread.sleep(500);
            }

            curr++;
        }
        if (curr == go) {
            System.err.println("到达" + go + "楼");
        }
        return curr;
    }

    private static int runDown(int curr, int go) throws InterruptedException {
        int temp = curr;
        for (int i = 0; i < temp - go; i++) {
            System.out.println("-" + curr + "楼-");
            for (int j = 0; j < 3; j++) {
                System.out.println("···");
                Thread.sleep(500);
            }

            curr--;

        }
        if (curr == go) {
            System.err.println("到达" + go + "楼");
        }
        return curr;
    }
}
