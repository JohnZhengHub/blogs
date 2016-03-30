	
	public ArrayList<Integer> GetLeastNumbers_Solution(int [] input, int k) {
		ArrayList<Integer> result = new ArrayList<Integer>();
		if( input == null || input.length < k || input.length == 0 || k <= 0) //这边需要注意的是 k=0 的情况, 如果对这种情况不处理的化后面就会报错
		    return result;
		
		int start = 0;
		int end = input.length-1;
		int index = partition(input,start,end);
		int target = k-1;
		while(index != target){
		    if(index < target){
		        start = index+1;
		    }else{
		        end = index -1;
		    }
		    index = partition(input,start,end);
		 }
		for(int i=0;i<k;i++){
		    result.add(input[i]);
		}
		return result;
     }
    
	    private int partition(int[] input,int left,int right){
		int val = input[left];
		while(left < right){
		    while(left < right && input[right] >= val)
		        right --;
		    if(left < right) 
		        input[left++] = input[right];
		    
		    while(left < right && input[left] <= val)
		        left++;
		    if(left < right ) 
		        input[right--] = input[left];
		} 
		input[left] = val;
		return left;
	    }
