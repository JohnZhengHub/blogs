
## 孩子们的游戏(圆圈中最后剩下的数)

	    class ListNode{
		int val;
	       	ListNode next;
		public ListNode(int val){
		    this.val = val;
		}
	    }
	    public int LastRemaining_Solution(int n, int m) {
		if(m <= 0 || n <= 0)
		    return -1;
		if(n == 1)
		    return -1;
		
		ListNode head = new ListNode(0);
		ListNode cur = head;
		int idx = 1;
		while(idx < n){
		    ListNode node = new ListNode(idx);
		    cur.next = node;
		    cur = cur.next;
		    idx ++;
		}
		ListNode pre = cur;
		cur.next = head;
		cur = head;
		while(pre != cur){
		    int num = 1;
		    while( num < m){
		        pre = cur;
		        cur = cur.next;
		        num++;    
		    }
		    if(pre != cur){
		        pre.next = cur.next;
		        cur = pre.next;
		    }
		}
		return cur.val;
	    }
