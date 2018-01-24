package dong.utils;

import org.junit.Test;
import org.springframework.beans.factory.BeanFactory;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.util.*;
import java.util.function.Predicate;

/**
 * @author Created by xzd on 2017/12/5.
 * @Description Java 8 lambda特性
 */
public class LambdaTest {
    @Test
    public void test01(){
        // Java 8之前：
        new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("Before Java8, too much code for too little to do");
            }
        }).start();
        //Java 8方式：
        new Thread(() -> System.out.println("In Java8, Lambda expression rocks !!") ).start();
    }

    public void test(){
    }


    public void test02(){
        JButton show =  new JButton("Show");

        // Java 8之前：
        show.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Event handling without lambda expression is boring");
            }
        });

        // Java 8方式：
        show.addActionListener((e) -> {
            System.out.println("Light, Camera, Action !! Lambda expressions Rocks");
        });
    }

    public List<Integer> getList(){
        List<Integer> list = new ArrayList<>();

        list.add(34);
        list.add(23);
        list.add(13);
        list.add(57);
        list.add(72);
        list.add(3);
        list.add(15);
        list.add(20);
        list.add(27);

        return list;
    }

    @Test
    public void test02_01(){
        List<Integer> list = getList();
        /*Collections.sort(list, new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                return o1 - o2;
            }
        });*/

        Collections.sort(list, (o1, o2) -> o1 - o2);

        int[] array = {1,33,6,56,16,27,37,8};

        Arrays.sort(array);

        System.out.println(list);

    }

    /**
     * 集合循环
     */
    @Test
    public void test03() {
        List<Integer> list = getList();
        //list.forEach(n -> System.out.println(n));
        list.forEach(System.out::println);
    }

    public static void filter(List<String> names, Predicate condition) {
        for(String name: names)  {
            if(condition.test(name)) {
                System.out.println(name + " ");
            }
        }
    }

    @Test
    public void test04(){
        List<String> languages = Arrays.asList("Java", "Scala", "C++", "Haskell", "Lisp");

        /*System.out.println("Languages which starts with J :");
        filter(languages, (Object str) ->str.startsWith("J"));

        System.out.println("Languages which ends with a ");
        filter(languages, (str)->str.endsWith("a"));*/

        System.out.println("Print all languages :");
        filter(languages, (str)->true);

        System.out.println("Print no language : ");
        filter(languages, (str)->false);

        /*System.out.println("Print language whose length greater than 4:");
        filter(languages, (str)->str.length() > 4);*/
    }

    @Test
    public void test05() {
        //不使用lambda表达式为每个订单加上12%的税
        List<Integer> costBeforeTax = Arrays.asList(100, 200, 300, 400, 500);
        for (Integer cost : costBeforeTax) {
            double price = cost + .12*cost;
            System.out.println(price);
        }

        // 使用lambda表达式
        costBeforeTax.stream().map((cost) -> cost + .12*cost).forEach(System.out::println);
    }

    @Test
    public void test05_01() {
        // 为每个订单加上12%的税
        // 老方法：
        List<Integer> costBeforeTax = Arrays.asList(100, 200, 300, 400, 500);
        double total = 0;
        for (Integer cost : costBeforeTax) {
            double price = cost + .12*cost;
            total = total + price;
        }
        System.out.println("Total : " + total);

        costBeforeTax.stream().map((cost) -> cost + .12*cost).reduce((sum, cost) -> sum + cost).get();

        // 新方法：
        double bill = costBeforeTax.stream().map((cost) -> cost + .12*cost).reduce((sum, cost) -> sum + cost).get();
        System.out.println("Total : " + bill);
    }

    @Test
    public void test06(){
        String b = "0";
        String a = "0.01";

        System.out.println(Double.valueOf(a).equals(Double.valueOf(b)));
        System.out.println(new BigDecimal(a).compareTo(new BigDecimal(b)));

        new Comparator<Integer>(){
            @Override
            public int compare(Integer o1, Integer o2) {
                return o1 - o2;
            }
        };
    }
}
