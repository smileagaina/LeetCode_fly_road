
# 题目描述

给你一个长度为 n 的整数数组 nums，其中 n > 1，返回输出数组 output ，其中 output[i] 等于 nums 中除 nums[i] 之外其余各元素的乘积。


## 解题思路

首先不考虑有被除数0的情况，一次遍历求出所有的和，然后再次遍历，每次除以当前的数就行，但是这时会发现有错误，就是被除数为0的情况，也就是原数组有0的情况；
接下来有0的情况我们分析一下：
分为只要一个0和多个0
比如[1, 0, 1]和[1, 0, 0]，
当遍历到第一个1的时候，你需要正常情况下的结果就是sum/nums[i]，但是如果有0，那么结果就是0；
当遍历到某个0的时候，你需要判断0的个数（所以在第一次遍历求和的时候，需要巧妙的避过所有的0求和并且记录0的个数），如果0的个数大于1，那么结果就是0，反之这种情况的结果就是sum/nums[i];

## 代码

```
class Solution {
    public int[] productExceptSelf(int[] nums) {
        // 要考虑有0的情况，那么只要把0单独判断就行
        int sum = 1;
        int ansZero = 0;
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] != 0) {
                sum = sum*nums[i];
            }
            else {
                ansZero++;
            }
        }

        int[] work = new int[nums.length];
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] !=0 ) {
                work[i] = (ansZero > 0) ? 0 : sum/nums[i];
            }
            else {
                work[i] = (ansZero > 1) ? 0 : sum;
            }
        }
        return work;
    }
}

```