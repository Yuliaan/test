package com.demo.gavant.sudoku;

import java.util.HashSet;
import java.util.Set;

public abstract class Section {

	private int id;
	
	// list of value cells
	private Set<Integer> values = new HashSet<Integer>();
		
	/**
	 * 
	 * @return the value
	 */
	public int getId()
	{
	return id;
	}
	
	public void setId(int id)
	{
		this.id = id;
	}
	
	public Set<Integer> getValues()
	{
		return values;
	}
	
	public void addCellValue(Integer value)
	{
		values.add(value);
	}
}
