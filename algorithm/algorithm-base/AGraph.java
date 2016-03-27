package graph;

import java.util.LinkedList;
import java.util.Queue;

/**
 * 邻接表
 * @author joe
 *
 */
public class AGraph {
	private VNode[] adjlist;
	private int n,e;
	private static class VNode{
		int data; //顶点信息
		ArcNode firstarc;
		
	}
	private static class ArcNode{
		int adjvex;
		ArcNode nextarc;
		int info; //边的相关信息，如全职
	}
	/**
	 * 深度优先遍历
	 * @param g
	 * @param v
	 */
	public void dfs(AGraph g,int v){
		if(g == null)
			return;
		boolean[] visit = new boolean[g.n];
		doDFS(g,v,visit);
	}
	private void doDFS(AGraph g, int v, boolean[] visit) {
		visit[v] = true;
		visit(g,v);
		ArcNode node = g.adjlist[v].firstarc;
		while(node != null){
			if(visit[node.adjvex] == false)
			{
				doDFS(g,node.adjvex,visit);
				node = node.nextarc;
			}
		}
	}
	/**
	 * 广度优先遍历
	 * @param g
	 * @param v
	 */
	public void bfs(AGraph g,int v){
		if( g== null || g.adjlist == null || g.adjlist.length <= v)
			return;
		boolean[] visit = new boolean[g.n];
		doBFS(g,v,visit);
		
	}
	
	private void doBFS(AGraph g, int v, boolean[] visit) {
		ArcNode node;
		Queue<Integer> queue = new LinkedList<Integer>();
		visit[v] = true;
		queue.offer(v);
		while(!queue.isEmpty()){
			int idx = queue.poll();
			node = g.adjlist[idx].firstarc;
			while(node != null){
				if(visit[node.adjvex] == false){
					visit(g,node.adjvex);
					visit[node.adjvex] = true;
					queue.offer(node.adjvex);
				}
				node = node.nextarc;
			}
		}
	}
	private void visit(AGraph g,int v){
		System.out.print(v+" "+g.adjlist[v].data);
	}
	
}
