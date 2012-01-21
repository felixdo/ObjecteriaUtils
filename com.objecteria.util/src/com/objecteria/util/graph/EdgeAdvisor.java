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

import java.util.List;

/**
 * An EdgeAdvisor provides information about the 
 * successors of a given node in a directed graph
 * @author Felix Dorner
 *
 * @param <T>
 */
public interface EdgeAdvisor<T> {
	
	/**
	 * Given a node 'source', which are this nodes successors, i.e.
	 * the nodes that are directly reachable from the source node
	 * @param source the source node
	 * @return
	 */
	List<T> getSucessors(T source);
}
