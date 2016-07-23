#和为S的连续正数序列
##题目
输入一个正数s, 打印出所有和为s的连续整数序列（至少包含两个数字）。例如输入15，输出1~5，4~6 和 7~8

## 思路
定义两个指针small和big，分别指向1，2。 如果两个指针之间的数的和大于指定整数，则small往右移动，否则big往后移动。如果相等则将两指针数字存放，并右移small并递归以上动作。

     public ArrayList<ArrayList<Integer> > FindContinuousSequence(int sum) {
        ArrayList<ArrayList<Integer> > result = new  ArrayList<ArrayList<Integer> >();
        if (sum < 3)
            return result;
        int small = 1;
        int big = 2;
        int curSum = small + big;
        int mid = (1+sum) >> 1; // 停止条件 注
        while (small < mid) {
            if (curSum == sum ){
                ArrayList<Integer> list = new ArrayList<Integer>();
                for (int i = small; i <= big; i++) {
                    list.add(i);
                }
                result.add(list);
                big++;   //
                curSum += big; // 
            } else if (curSum < sum){
                big ++; 
                curSum += big;  //注
            } else{
                curSum -= small; // 注
                small ++;
                
            }
        }
        return result;
    }

