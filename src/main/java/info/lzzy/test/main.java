package info.lzzy.test;

import java.util.ArrayList;
import java.util.List;

public class main {
    public static void main(String[] args) {
        List<String> list = new ArrayList<String>();
        list.add("a");
        list.add("b");
        list.add("c");
        System.out.println("这个list的长度为：" + list.size());
        String str = "HelloWorld";
        System.out.println("这个字符串的长度为：" + str.length());

    }
}
