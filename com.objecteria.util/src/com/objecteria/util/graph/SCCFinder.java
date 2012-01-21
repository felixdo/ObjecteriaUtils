/* 
 * Copyright 2011 Felix Dorner
 * 
 * This file is part of ObjecteriaUtils (com.objecteria.util)
 * ObjecteriaUtils is free software: you can redistribute it 
 * and/or modify it under the terms of the GNU Lesser General Public License 
 * as published by the Free Software Foundation, either version 3 of the 
 * License, or (at your option) any later version.
 *
 * ObjecteriaUtils is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with ObjecteriaUtils.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.objecteria.util.graph;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Stack;


/** 
 * An implementation of Tarjan«s algorithm to find the strongly connected
 * components in a directed graph. The implementation is based on the pseudocode
 * provided by http://en.wikipedia.org/wiki/Tarjan's_strongly_connected_components_algorithm
 * 
 * @author Felix Dorner
 *
 * @param <T>
 */
public class SCCFinder<T> {

	private final class Properties {
		public Properties(int index, int lowlink){
			this.index = index;
			this.lowlink = lowlink;
		}
		int index;    // index at which the corresponding element was discovered 
		int lowlink;  // minimal index for some other node reachable from this one
	}
	
	private EdgeAdvisor<T> advisor;
	private int index;
	private Map<T, Properties> propertyMap;
	private Stack<T> stack;
	private List<List<T>> result;
	
	/**
	 * Find the strongly connected components of a graph.
	 * @param contents An iterator over the graph's nodes.
	 * @param advisor provides informations about the graphs edges.
	 * @return A list of strongly connected components of the graph.
	 */
	public List<List<T>> findSCC(Iterator<T> contents, EdgeAdvisor<T> advisor){
		
		result = new ArrayList<List<T>>();
		this.advisor = advisor;
		index = 0;
		propertyMap = new HashMap<T, Properties>();
		stack = new Stack<T>();
		result = new ArrayList<List<T>>();
		
		while (contents.hasNext()){
			T current = contents.next();
			if (propertyMap.get(current) == null){
				strongconnect(current);
			}
		}
		
		return result;
	}
	
	
	private void strongconnect(T current){
		Properties properties = new Properties(index, index);
		propertyMap.put(current, properties);
		index++;
		stack.push(current);
		for (T successor : advisor.getSucessors(current)){
			if (propertyMap.get(successor) == null){
				// have not seen successor before
				strongconnect(successor);
				properties.lowlink = Math.min(properties.lowlink, propertyMap.get(successor).lowlink);
			} else if (stack.contains(successor)){
				// seen successor in the currently handled scc
				properties.lowlink = Math.min(properties.lowlink, propertyMap.get(successor).lowlink);
			}
		}
		
		if (properties.lowlink == properties.index){
			List<T> scc = new ArrayList<T>();
			T t;
			do {
				t = stack.pop();
				scc.add(t);
			} while (t != current);
			result.add(scc);
		}
	}
	
}
