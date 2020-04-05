package com.simple.algorithm;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Stack;

public class SubwayGraph {
	
	int stationCount;//站点总数量
	
	LinkedList<Integer>[] stationTable;//站点邻接表
	
	public SubwayGraph(List<Station> stationList) {
		int size = stationList.size();
		this.stationCount = size;
		this.stationTable = new LinkedList[size];
		
		for(Station s : stationList) {
			stationTable[s.stationId] = new LinkedList<>();
		}
		
	}
	
	public void addEdge(int stationId1, int stationId2) {
		stationTable[stationId1].add(stationId2);
		stationTable[stationId1].add(stationId2);
	}
	
	public int distance(int station1, int station2) {
		int distance = new StationSearch(this, station1).distanceTo(station2);
		System.out.println(String.format("站点id为[%1$s]到站点id为[%2$s]的距离为:%3$s", station1, station2, distance));
		return distance;
	}

	/**
	 *站点搜索
	 */
	static class StationSearch{
		boolean[] visited;//已经经过的站点
		
		Queue<Integer> preToVisit;//准备经过的站点
		
		int sourceStationId;//起点
		
		int prevStationId[];//数组下标为站点id，元素为当前下标站点的上一个站点
		
		public StationSearch(SubwayGraph g, int sourceStationId) {
			int bound = g.stationCount;
			this.visited = new boolean[bound];
			this.prevStationId = new int[bound];
			this.sourceStationId = sourceStationId;
			preToVisit = new LinkedList<>();
			bfs(g,sourceStationId);//广度优先搜索
		}
		
		/**
		 * 广度优先搜索
		 * @param g
		 * @param sourceStationId 起点
		 */
	    private void bfs(SubwayGraph g, int sourceStationId) {
	    	visited[sourceStationId] = true;
	    	
	    	preToVisit.add(sourceStationId);
	    	
	    	while(!preToVisit.isEmpty()) {
	    		int toVisit = preToVisit.poll();
	    		
	    		for(int i : g.stationTable[toVisit]) {
	    			if(!visited[i]) {
	    				visited[i] = true;
	    				prevStationId[i] = toVisit;
	    				preToVisit.add(i);
	    			}
	    		}
	    	}
	    }
	    
	    /**
	     * 是否可达站点
	     * @param targetStationId
	     * @return
	     */
	    public boolean hasPathTo(int targetStationId) {
	    	return visited[targetStationId];
	    }
	    
	    /**
	     * 到指定的站点距离
	     */
	    public int distanceTo(int targetStationId) {
	    	
	    	if(!hasPathTo(targetStationId)) return 0;
	    	
	    	int distance = 0;
	    	Stack<Integer> path = new Stack<>();
	    	for(int i = targetStationId;i != this.sourceStationId;i = prevStationId[i]) {
	    		distance += 1;
	    		path.push(i);
	    	}
	    	path.push(this.sourceStationId);
	    	
	    	System.out.print("经过站点：");
	    	int size = path.size();
	    	for(int p = 0;p<size;p++) {
	    		System.out.print(path.pop() + " ");
	    	}
	    	System.out.println();
	    	return distance;
	    }
		
	}
	
	/**
	 * 站点
	 * @author hxm
	 *
	 */
	static class Station{
		int stationId;//站点id
		String stationName;//站名
		
		public Station(int stationId,String stationName) {
			this.stationId = stationId;
			this.stationName = stationName;
		}

	}
	
	
	public static void main(String[] args) {
		List<Station> stationList = new ArrayList<SubwayGraph.Station>();
		//建立站点
		stationList.add(new Station(0, "A"));
		stationList.add(new Station(1, "B"));
		stationList.add(new Station(2, "C"));
		stationList.add(new Station(3, "D"));
		stationList.add(new Station(4, "E"));
		stationList.add(new Station(5, "F"));
		
		//构造地铁线路图
		SubwayGraph g = new SubwayGraph(stationList);
		g.addEdge(0, 1);//A-B
		g.addEdge(1, 2);//B-C
		g.addEdge(1, 4);//B-E
		g.addEdge(2, 3);//C-D
		g.addEdge(3, 4);//D-E
		g.addEdge(4, 5);//E-F
		
		g.distance(0, 5);//计算站点0到5的距离
	}

}
