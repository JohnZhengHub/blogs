# 包含min函数的栈
##题目描述
定义栈的数据结构，请在该类型中实现一个能够得到栈最小元素的min函数。

  public class Solution {
    Stack<Integer> stack = new Stack<Integer>();
    Stack<Integer> minStack = new Stack<Integer>();
    
    public void push(int node) {
        stack.push(node);
        if (minStack.isEmpty()) {
            minStack.push(node);
        } else {
            if (node < min()) {
                minStack.push(node);
            } else {
                minStack.push(min());
            }
        }
    }
    
    public void pop() {
        stack.pop();
        minStack.pop();
    }
    
    public int top() {
        return stack.peek(); // 这边是peek 不是top
    }
    
    public int min() {
        return minStack.peek();
    }
}
