二叉搜索树的第k个结点

## 题目描述
给定一颗二叉搜索树，请找出其中的第k大的结点。例如， 5 / \ 3 7 /\ /\ 2 4 6 8 中，按结点数值大小顺序第三个结点的值为4。


	public class Solution {
	    int count = 0;
	    TreeNode KthNode(TreeNode pRoot, int k)
	    {
	       
		if(pRoot == null || k <= 0)
		    return null;
		return kThNode(pRoot,k);
	    }
	    TreeNode kThNode(TreeNode pRoot, int k){
		TreeNode target = null;
		if(pRoot.left != null)
		    target = kThNode(pRoot.left,k);
		
		count++;
		if(target == null){
		    if(count == k)
		        return pRoot;
		}
		if(target == null && pRoot.right != null)
		    target = kThNode(pRoot.right,k);
		return target;
	    }


	}
