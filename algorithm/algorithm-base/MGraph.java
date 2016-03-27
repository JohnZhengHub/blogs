package graph;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

/**
 * 高分笔记版本
 * @author joe
 *
 */
public class MGraph {
    private ArrayList vexList;//存储点的链表
    private int[][] edges;//邻接矩阵，用来存储边
    private int numOfEdges;//边的数目
    
    public MGraph(int n) {
        //初始化矩阵，一维数组，和边的数目
        edges=new int[n][n];
        vexList=new ArrayList(n);
        numOfEdges=0;
    }

    //私有函数，深度优先遍历
    private void depthFirstSearch(boolean[] isVisited,int  i) {
        //首先访问该结点，在控制台打印出来
        System.out.print(visit(i)+"  ");
        //置该结点为已访问
        isVisited[i]=true;
        int w=getFirstNeighbor(i);//
        while (w!=-1) {
            if (!isVisited[w]) {
                depthFirstSearch(isVisited,w);
            }
            w=getNextNeighbor(i, w);
        }
    }

    //对外公开函数，深度优先遍历，与其同名私有函数属于方法重载
    public void depthFirstSearch() {
        boolean[] isVisited=new boolean[getNumOfVertex()];
        //记录结点是否已经被访问的数组
        for (int i=0;i<getNumOfVertex();i++) {
            isVisited[i]=false;//把所有节点设置为未访问
        }
        for(int i=0;i<getNumOfVertex();i++) {
            //因为对于非连通图来说，并不是通过一个结点就一定可以遍历所有结点的。
            if (!isVisited[i]) {
                depthFirstSearch(isVisited,i);
            }
        }
    }

    //私有函数，广度优先遍历
    private void broadFirstSearch(boolean[] isVisited,int i) {
        int u,w;
        Queue<Integer> queue = new LinkedList<Integer>();
        //访问结点i
        System.out.print(visit(i)+"  ");
        isVisited[i]=true;
        //结点入队列
        queue.offer(i);
        while (!queue.isEmpty()) {
            u=queue.poll();
            w=getFirstNeighbor(u);
            while(w!=-1) {
                if(!isVisited[w]) {
                        //访问该结点
                        System.out.print(visit(w)+"  ");
                        //标记已被访问
                        isVisited[w]=true;
                        //入队列
                        queue.offer(w);
                }
                //寻找下一个邻接结点
                w=getNextNeighbor(u, w);
            }
        }
    }

    /**
     * 广度优先遍历
     */
    public void broadFirstSearch() {
        boolean[] isVisited=new boolean[getNumOfVertex()];
        for (int i=0;i<getNumOfVertex();i++) {
            isVisited[i]=false;//把所有节点设置为未访问
        }
        for(int i=0;i<getNumOfVertex();i++) {
            if(!isVisited[i]) {
                broadFirstSearch(isVisited, i);
            }
        }
    }
  //得到第一个邻接结点的下标
    public int getFirstNeighbor(int index) {
        for(int j=0;j<vexList.size();j++) {
            if (edges[index][j]>0) {
                return j;
            }
        }
        return -1;
    }
  //根据前一个邻接结点的下标来取得下一个邻接结点
    public int getNextNeighbor(int v1,int v2) {
        for (int j=v2+1;j<vexList.size();j++) {
            if (edges[v1][j]>0) {
                return j;
            }
        }
        return -1;
    }
  //得到结点的个数
    public int getNumOfVertex() {
        return vexList.size();
    }

    //得到边的数目
    public int getNumOfEdges() {
        return numOfEdges;
    }

    //返回结点i的数据
    public Object visit(int i) {
        return vexList.get(i);
    }

    //返回v1,v2的权值
    public int getWeight(int v1,int v2) {
        return edges[v1][v2];
    }

    //插入结点
    public void insertVertex(Object vertex) {
        vexList.add(vexList.size(),vertex);
    }

    //插入结点
    public void insertEdge(int v1,int v2,int weight) {
        edges[v1][v2]=weight;
        numOfEdges++;
    }

    //删除结点
    public void deleteEdge(int v1,int v2) {
        edges[v1][v2]=0;
        numOfEdges--;
    }

    
}