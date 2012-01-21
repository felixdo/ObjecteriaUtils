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

package com.objecteria.util.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Test;

import com.objecteria.util.graph.EdgeAdvisor;
import com.objecteria.util.graph.SCCFinder;


public class SCCFinderTest {

	// Set of nodes is always the same 
	static final Integer[] nodes = new Integer[] {0,1,2,3,4,5,6,7,8,9};
	
	@Test
	public void testOne() {
		
		// build edges
		EdgeAdvisor<Integer> advisor = new EdgeAdvisor<Integer>() {
			@Override
			public List<Integer> getSucessors(Integer source) {	
				switch(source){
				case 0: return create(1, 2);
				case 1: return create(0);
				case 2: return create(0);
				default: return Collections.emptyList();
				}
			}
		};
		
		List<List<Integer>> expected = new ArrayList<List<Integer>>();
		expected.add(create(0,1,2));
		runTest(advisor, expected, 8);
	}
	
	@Test
	public void testTwo() {
		
		// build edges
		EdgeAdvisor<Integer> advisor = new EdgeAdvisor<Integer>() {
			@Override
			public List<Integer> getSucessors(Integer source) {	
				switch(source){
				case 0: return create(1);
				case 1: return create(2);
				case 2: return create(3);
				case 3: return create(4);
				case 4: return create(5);
				case 5: return create(6);
				case 6: return create(7);
				case 7: return create(8);
				case 8: return create(9);
				case 9: return create(0);
				default: return Collections.emptyList();
				}
			}
		};
		
		List<List<Integer>> expected = new ArrayList<List<Integer>>();
		expected.add(create(0,1,2,3,4,5,6,7,8,9));
		runTest(advisor, expected, 1);
	}
	
	
	@Test
	public void testThree() {
		
		// build edges
		EdgeAdvisor<Integer> advisor = new EdgeAdvisor<Integer>() {
			@Override
			public List<Integer> getSucessors(Integer source) {	
				switch(source){
				// cycle 1
				case 0: return create(1);
				case 1: return create(2);
				case 2: return create(3);
				case 3: return create(4);
				case 4: return create(0);
				// cycle 2
				case 5: return create(6);
				case 6: return create(7);
				case 7: return create(8);
				case 8: return create(9);
				case 9: return create(5);
				default: return Collections.emptyList();
				}
			}
		};
		
		List<List<Integer>> expected = new ArrayList<List<Integer>>();
		expected.add(create(0,1,2,3,4));
		expected.add(create(5,6,7,8,9));
		runTest(advisor, expected, 2);
	}
	
	
	private List<Integer> create(Integer... args) {
		List<Integer> s = new ArrayList<Integer>();
		for (Integer x : args){
			s.add(x);
		}
		return s;
	}
	
	
	private void runTest(EdgeAdvisor<Integer> edges, List<List<Integer>> expectedScc, int expectedSccCount){
		
		List<List<Integer>> result = new SCCFinder<Integer>().findSCC(Arrays.asList(nodes).iterator(), edges);
		
		// check if all expectedSccs are in fact in the result list
		// makes no assumption about the other scc in the result
		for (List<Integer> scc : expectedScc){
			boolean found = false;
			for (List<Integer> resultScc : result){
				if (resultScc.containsAll(scc) && scc.containsAll(resultScc)){
					found = true; 
					break;
				}
			}
			assertTrue(found);
		}
		
		// test if number of result SCCs equals number of expected SCCs
		if (expectedSccCount > 0){
			assertEquals(expectedSccCount, result.size());
		}
	}
	

}
