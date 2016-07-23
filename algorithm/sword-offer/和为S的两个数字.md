#和为S的两个数字
## 描述
输入一个递增排序的数组和一个数字s, 在数组中查找两个数，使它们的和正好是s,如果有多对和等于s,输出任意一对即可。
## 思路
定义两个指针small, big分别指向数组的头尾， 如果两个指针的和大于指定值，则减少big的值否则增大small的值。直到指针和为指定数或两指针相遇。
## 代码实现
      public ArrayList<Integer> FindNumbersWithSum(int [] array,int sum) {
        ArrayList<Integer> result = new ArrayList<Integer>();
        if(array == null || array.length == 0)
            return result;
        
        int small = 0;
        int big = array.length -1;
        while (small < big) {
            int val = array[small] + array[big];
            if ( val == sum) {
               result.add(array[small]);
                result.add(array[big]);
 				return result;
            } else if (val < sum) {
                small ++;
            } else {
                big --;
            }
        }
        return result;
    }
