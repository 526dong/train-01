package dong.utils;

import com.alibaba.fastjson.JSONArray;
/**
 * @author Created by xzd on 2017/12/5.
 * @Description 假如出现100个线程争夺一个资源，如何加锁处理
 */
public class DealMoreThread {
    public static void main(String[] args) {
        String json1 = "[{\"enhanceCreditName\":\"123\",\"enhanceCreditCode\":\"123\",\"quantity\":\"1231\",\"enhanceCreditValue\":\"43\"}]";

        String json2 = "[{\"enhanceCreditName\":\"123\",\"enhanceCreditCode\":\"123\",\"quantity\":\"1231\",\"enhanceCreditValue\":\"43\"}]";

        String json = "{'menuIndex1':['0'],'menuIndex2':['0|manage-10']}";

        JSONArray jsonArray = JSONArray.parseArray(json2);

        System.out.println(jsonArray);
        for (int i = 0; i < jsonArray.size(); i++) {
            Object o = jsonArray.get(i);
            System.out.println(o);
        }
    }
}
