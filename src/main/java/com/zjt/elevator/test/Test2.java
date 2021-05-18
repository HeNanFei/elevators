package com.zjt.elevator.test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

/**
 * @author hyh.
 * @version 1.0
 * @Date: 2021/5/9 7:51
 */
public class Test2 {
    public static void main(String[] args) throws InterruptedException {
        int curr = 1;// 当前楼层
        List<Integer> up = new ArrayList<Integer>();// 上
        List<Integer> down = new ArrayList<Integer>();// 下

        int go = 0;//目标楼层


        System.out.println("input the number");
        Scanner sc = new Scanner(System.in);

        while (go != -22) {
            go = sc.nextInt();
            if (go > curr )
                up.add(go);
            if (go < curr)
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
            if(curr == 0){
                curr = 1;
                continue;
            }else {
                System.out.println("-" + curr + "楼-");
                for (int j = 0; j < 3; j++) {
                    System.out.println("···");
                    Thread.sleep(500);
                }

                curr++;
            }
        }
        if (curr == go) {
            System.out.println("到达" + go + "楼");
            openTheDoor();
            closeTheDoor();
        }
        return curr;
    }

    private static int runDown(int curr, int go) throws InterruptedException {
        int temp = curr;
        for (int i = 0; i < temp - go; i++) {
            if(curr == 0){
                curr = -1;
                continue;
            }else {
                System.out.println("-" + curr + "楼-");
                for (int j = 0; j < 3; j++) {
                    System.out.println("···");
                    Thread.sleep(500);
                }

                curr--;
            }
        }
        if (curr == go) {
            System.out.println("到达" + go + "楼");
            openTheDoor();
            closeTheDoor();
        }
        return curr;
    }

    public static void openTheDoor() throws InterruptedException {
        System.out.println("电梯门开放中.....持续五秒");
        Thread.sleep(5000);
    }

    public static void closeTheDoor() throws InterruptedException {
        System.out.println("电梯门关闭.....");
        //Thread.sleep(5000);
    }
}

