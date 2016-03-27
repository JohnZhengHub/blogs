package graph;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

/**
 * �߷ֱʼǰ汾
 * @author joe
 *
 */
public class MGraph {
    private ArrayList vexList;//�洢�������
    private int[][] edges;//�ڽӾ��������洢��
    private int numOfEdges;//�ߵ���Ŀ
    
    public MGraph(int n) {
        //��ʼ������һά���飬�ͱߵ���Ŀ
        edges=new int[n][n];
        vexList=new ArrayList(n);
        numOfEdges=0;
    }

    //˽�к�����������ȱ���
    private void depthFirstSearch(boolean[] isVisited,int  i) {
        //���ȷ��ʸý�㣬�ڿ���̨��ӡ����
        System.out.print(visit(i)+"  ");
        //�øý��Ϊ�ѷ���
        isVisited[i]=true;
        int w=getFirstNeighbor(i);//
        while (w!=-1) {
            if (!isVisited[w]) {
                depthFirstSearch(isVisited,w);
            }
            w=getNextNeighbor(i, w);
        }
    }

    //���⹫��������������ȱ���������ͬ��˽�к������ڷ�������
    public void depthFirstSearch() {
        boolean[] isVisited=new boolean[getNumOfVertex()];
        //��¼����Ƿ��Ѿ������ʵ�����
        for (int i=0;i<getNumOfVertex();i++) {
            isVisited[i]=false;//�����нڵ�����Ϊδ����
        }
        for(int i=0;i<getNumOfVertex();i++) {
            //��Ϊ���ڷ���ͨͼ��˵��������ͨ��һ������һ�����Ա������н��ġ�
            if (!isVisited[i]) {
                depthFirstSearch(isVisited,i);
            }
        }
    }

    //˽�к�����������ȱ���
    private void broadFirstSearch(boolean[] isVisited,int i) {
        int u,w;
        Queue<Integer> queue = new LinkedList<Integer>();
        //���ʽ��i
        System.out.print(visit(i)+"  ");
        isVisited[i]=true;
        //��������
        queue.offer(i);
        while (!queue.isEmpty()) {
            u=queue.poll();
            w=getFirstNeighbor(u);
            while(w!=-1) {
                if(!isVisited[w]) {
                        //���ʸý��
                        System.out.print(visit(w)+"  ");
                        //����ѱ�����
                        isVisited[w]=true;
                        //�����
                        queue.offer(w);
                }
                //Ѱ����һ���ڽӽ��
                w=getNextNeighbor(u, w);
            }
        }
    }

    /**
     * ������ȱ���
     */
    public void broadFirstSearch() {
        boolean[] isVisited=new boolean[getNumOfVertex()];
        for (int i=0;i<getNumOfVertex();i++) {
            isVisited[i]=false;//�����нڵ�����Ϊδ����
        }
        for(int i=0;i<getNumOfVertex();i++) {
            if(!isVisited[i]) {
                broadFirstSearch(isVisited, i);
            }
        }
    }
  //�õ���һ���ڽӽ����±�
    public int getFirstNeighbor(int index) {
        for(int j=0;j<vexList.size();j++) {
            if (edges[index][j]>0) {
                return j;
            }
        }
        return -1;
    }
  //����ǰһ���ڽӽ����±���ȡ����һ���ڽӽ��
    public int getNextNeighbor(int v1,int v2) {
        for (int j=v2+1;j<vexList.size();j++) {
            if (edges[v1][j]>0) {
                return j;
            }
        }
        return -1;
    }
  //�õ����ĸ���
    public int getNumOfVertex() {
        return vexList.size();
    }

    //�õ��ߵ���Ŀ
    public int getNumOfEdges() {
        return numOfEdges;
    }

    //���ؽ��i������
    public Object visit(int i) {
        return vexList.get(i);
    }

    //����v1,v2��Ȩֵ
    public int getWeight(int v1,int v2) {
        return edges[v1][v2];
    }

    //������
    public void insertVertex(Object vertex) {
        vexList.add(vexList.size(),vertex);
    }

    //������
    public void insertEdge(int v1,int v2,int weight) {
        edges[v1][v2]=weight;
        numOfEdges++;
    }

    //ɾ�����
    public void deleteEdge(int v1,int v2) {
        edges[v1][v2]=0;
        numOfEdges--;
    }

    
}