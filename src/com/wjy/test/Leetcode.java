package com.wjy.test;

import com.alibaba.druid.util.ListDG;
import com.wjy.web.BaseServlet;
import org.junit.Test;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DecimalFormat;
import java.util.*;
import java.util.stream.LongStream;

/**
 * @Author: Wan Jiangyuan
 * @Description:
 * @Date: Created in 9:10 2021/1/3
 * @E-mail: 15611562852@163.com
 */

public class Leetcode {

    @Test
    public void reconstructQueue() {

        int[][] people = new int[6][2];
        people[0][0] = 7;
        people[0][1] = 0;
        people[1][0] = 4;
        people[1][1] = 4;

        people[2][0] = 7;
        people[2][1] = 1;
        people[3][0] = 5;
        people[3][1] = 0;

        people[4][0] = 6;
        people[4][1] = 1;
        people[5][0] = 5;
        people[5][1] = 2;

        //按照身高降序 K升序排序
        Arrays.sort(people, new Comparator<int[]>() {
            @Override
            public int compare(int[] o1, int[] o2) {
                return o1[0] == o2[0] ? o1[1] - o2[1] : o2[0] - o1[0];
            }
        });
        for (int[] i : people) {
            System.out.println(i[0]+" "+i[1]);
        }

        List<int[]> list = new ArrayList<>();
        //K值定义为 排在h前面且身高大于或等于h的人数
        //因为从身高降序开始插入，此时所有人身高都大于等于h
        //因此K值即为需要插入的位置
        for (int[] i : people) {
            list.add(i[1], i);
        }
        System.out.println(list.toArray(new int[list.size()][]));
    }

    @Test
    public void test01() {
        ArrayList<Integer> list = new ArrayList<Integer>();
        list.add(1,2);
        list.add(2,3);
        list.add(0,1);
        System.out.println(list);
    }

}






