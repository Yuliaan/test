package com.demo.gavant.sudoku;

import java.util.HashSet;
import java.util.Set;

/**
 * 
 * @author Yulia
 * Cell class implements Comparable to be able to order the collection of cells by possible values count
 */
public class Cell implements Comparable<Cell>{

	private int value;
	
	/**
	 * Stores all possible values for a Cell 
	 */
	private Set<Integer> possibleValues = new HashSet<Integer>();
	
	/**
	 * Sections' objects
	 */
	private Box box;
	private Row row;
	private Column column;
	
	public Cell(Row row, Column column, int value, Box box) {
		this.row = row;
		this.column = column;
		this.value = value;
		this.box = box;
	}
   
	/**
	 * 
	 * @return the value
	 */
	public Integer getValue()
	{
		return value;
	}
	   
	/**
	 * 
	 * @param value -  the value to set
	 */
	public void setValue(Integer value)
	{
		this.value = value;
	}
	
	/**
	 * 
	 * @return the row
	 */
	public Row getRow()
	{
		return row;
	}
	   
	/**
	 * 
	 * @return the column
	 */
	public Column getColumn()
	{
		return column;
	}
	
	/**
	 * 
	 * @return the box
	 */
	public Box getBox()
	{
	return box;
	}
	   
	/**
	 * 
	 * @param value -  the box to set
	 */
	public void setBox(Box box)
	{
		this.box = box;
	}

	/* Create a HashSet of all possible values that can be 
	 * filled in this Cell.
	 */
	public Set<Integer> initPossibleValues(int matrixSize) {
		if (this.value != 0) return null;

		/* temporarily assign all 9 numbers */
		for (int i = 1; i <= matrixSize; i++)
			possibleValues.add(i);
		
		/* Remove all the values that cannot be placed at this Spot */
		possibleValues.removeAll(this.row.getValues());
		possibleValues.removeAll(this.column.getValues());
		possibleValues.removeAll(this.box.getValues());
		return possibleValues;
	}

	/**
	 * 
	 * @return all possible values
	 */
	public Set<Integer> getPossibleValues() {
		return possibleValues;
	}
	
	public int getNumberOfPossibleValues() {
		return possibleValues.size();
	}

	/**
	 * Implementing the compared interface
	 */
	public int compareTo(Cell comparedCell) {
		return this.possibleValues.size() - comparedCell.possibleValues.size();
	}
	
    @Override
	public String toString() {
		return String.valueOf(value);
	}
}
