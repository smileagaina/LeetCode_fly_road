package 算法思想.排序.桶排序;

import java.util.*;

public class Leetcode347 {

    public int[] topKFrequent(int[] nums, int k) {

        // 设置若干个桶，每个痛存储出现频率相同的树，并且桶的下标代码桶中数出现的频率
        Map<Integer, Integer> frequencyMap = new HashMap<>();

        for (int num : nums) {
            frequencyMap.put(num, frequencyMap.getOrDefault(num, 0) + 1);
        }

        List<Integer>[] bucket = new ArrayList[nums.length + 1];

        for (int key : frequencyMap.keySet()) {
            int frequency = frequencyMap.get(key);
            if (bucket[frequency] == null) {
                bucket[frequency] = new ArrayList<>();
            }
            bucket[frequency].add(key);
        }
        // res结果
        List<Integer> res = new ArrayList<>();
        for (int i = bucket.length - 1; i >= 0 && res.size() < k; i--) {
            if (bucket[i] == null) continue;
            if (bucket[i].size() <= (k - res.size())) {
                res.addAll(bucket[i]);
            } else {
                res.addAll(bucket[i].subList(0, k - res.size()));
            }
        }
        // JDK 1.8 list数组转int[]
        return res.stream().mapToInt(Integer::valueOf).toArray();
//        return res.stream().mapToInt(i -> i).toArray();
    }
}
