#最小的K个数
##题目描述
输入n个整数，找出其中最小的K个数。例如输入4,5,1,6,2,7,3,8这8个数字，则最小的4个数字是1,2,3,4。
## 基于堆的算法 

     public ArrayList<Integer> GetLeastNumbers_Solution(int[] input, int k) {
        ArrayList<Integer> result = new ArrayList<Integer>();
    	if(input == null || input.length == 0 || input.length < k || k <= 0)//k的范围要注意
            return result;
        int[] maxPQ = new int[k+1];
        for(int i = 0; i < k ; i++)
            maxPQ[i+1] = input[i];
         
        for(int i = k/2; i>=1; i--)
            sink(maxPQ,k,i);
        
        for(int i=k;i<input.length;i++){
            if(input[i] < maxPQ[1]){
                maxPQ[1] = input[i];
                sink(maxPQ,k,1);
            }
        }
        for(int i=1;i<=k;i++)
            result.add(maxPQ[i]);
        return result;
    }
    private void sink(int[] maxPQ,int N,int k){
        while(k*2 <= N){//这边是等号
            int j = k*2;
            if(j< N && maxPQ[j+1] > maxPQ[j]) j++;
            if(maxPQ[j] < maxPQ[k]) break;
            swap(maxPQ,k,j);
            k = j;
        }
    }
    private void swap(int[] array,int i,int j){
        int temp = array[i];
        array[i] = array[j];
        array[j] = temp;
    }
