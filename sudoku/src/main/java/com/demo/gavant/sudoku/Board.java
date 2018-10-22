package com.demo.gavant.sudoku;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

public class Board {

	/**
	 * Sudoku matrix
	 */
	private Cell[][] matrix;
	
	/**
	 * Size of matrix
	 */
	private int size;
	
	/**
	 * List to keep already filled cells
	 */
	private List<Cell> filledCells = new ArrayList<Cell>();

	/**
	 * List to save empty cells
	 */
	private List<Cell> emptyCells = new ArrayList<Cell>();

	/**
	 * List of Rows	
	 */
	private List<Row> rows = new ArrayList<Row>();
	
	/**
	 * List of Column
	 */
	private List<Column> columns = new ArrayList<Column>();
	
	// 
	/**
	 *  List of 3X3 boxes
	 */
	private List<Box> boxes = new ArrayList<Box>();

	/**
	 * Constructor
	 * @param matrixFile
	 */
	public Board(String matrixFile) {
		
		setUpBoardFromFile(matrixFile);
	}
	
	/**
	 * Create board with values from provided file
	 * @param matrixFile
	 */
	private void setUpBoardFromFile(String matrixFile) {
		
		Scanner sc;
		try {
			sc = new Scanner(new BufferedReader(new FileReader(matrixFile)));
			if (sc.hasNextLine()) {
	            String line = sc.nextLine().trim();
	            this.size = line.length();
	            int i = 0;
		        matrix = new Cell[size][size];
		        
		        // Create Row, Column and Boxes objects
		        createBoxes();
		        createRows();
		        createColumns();
		        
		        // Fill the matrix and all collections
		        while(i < this.size) {
	              for (int j=0; j<line.length(); j++) {
	            	  Box box = findBox(i, j);
	            	  Cell newCell = new Cell(rows.get(i), columns.get(j), size, box);
	            	  if (line.charAt(j) == 'X') {
	            		  newCell.setValue(0);
	            		  emptyCells.add(newCell);
	            	  }else {
	            		  int value = Character.getNumericValue(line.charAt(j));
	            		  newCell.setValue(value);
	            		  filledCells.add(newCell);
	            		  box.addCellValue(value);
	            		  columns.get(j).addCellValue(value);
	            		  rows.get(i).addCellValue(value);
	            	  }
	            	  matrix[i][j] = newCell;
	              }
	              if (sc.hasNextLine())
	            	  line = sc.nextLine().trim();
	              i++;
		        } 
			}
			
			// Calculate all possible cells values to reduce number of iterations
			calculateCellsPossibleValues();
      
		} catch (FileNotFoundException e) {
		
			System.out.println("Sudoku file is not found");
		}
	}
	
	
	/**
	 * Create 3X3 Boxes objects
	 */
	private void createBoxes() {
		for (int i=1; i<=size; i++) {
			Box newBox = new Box();
			newBox.setId(i);
			boxes.add(newBox);
		}
	}
	
	/**
	 * Create Rows objects
	 */
	private void createRows() {
		for (int i=0; i<size; i++) {
			Row newRow = new Row();
			newRow.setId(i);
			rows.add(newRow);
		}
	}
	
	/**
	 * Create Columns objects
	 */
	private void createColumns() {
		for (int i=0; i<size; i++) {
			Column newColumn = new Column();
			newColumn.setId(i);
			columns.add(newColumn);
		}
	}
	
	/**
	 * Retrieve box object by row and column numbers
	 * TO DO : make it dynamic
	 * @param x
	 * @param y
	 * @return
	 */
	private Box findBox(int x, int y) {
		int boxIndex = 0;
		if (x < 3) {
            if (y < 3) boxIndex = 0;
            else if (y < 6) boxIndex = 3;
            else boxIndex = 6;
        }else if (x < 6) {
            if (y < 3)  boxIndex = 1;
            else if (y < 6)  boxIndex = 4;
            else boxIndex = 7;
        } else {
            if (y < 3) boxIndex = 2;
            else if (y < 6) boxIndex = 5;
            else boxIndex = 8;
        }   
		return boxes.get(boxIndex);
	}
	

	/**
	 * Solve method
	 */
	public boolean solve() {
		
		// Reorder empty cell collection by minimum of potencial fields
		Collections.sort(emptyCells);
		
		return solvePuzzle(0);
		
	}
	
	/**
	 * Solve puzzle	
	 * @param id
	 * @return
	 */
	private boolean solvePuzzle(int id) {
		// no empty cells left
		if (emptyCells.size() == 0) {
			return true;
		}

		if (id >= emptyCells.size()) {
			// Not all cells were filled
			if(filledCells.size() < size*size) {
				System.out.println("There is no solution to this puzzle");
				return false;
			}else				
				return true;
        }
		
        /* Current emptySpot that is being considered to be filled */
		Cell cell = emptyCells.get(id);
		
		Set<Integer> cellPossibleValues = cell.getPossibleValues();
		 
		// unsolvable
		if (cellPossibleValues.size() == 0)
			return false;

		/* Try all the possible values for this empty cell */
		for (int value : cellPossibleValues) {

			/* Check if the value is valid */
			if (valueIsValid(value, cell)) {
				cell.setValue(value);
				updateCollections(value, cell);

				filledCells.add(cell);

				int newId = id + 1;
				if(solvePuzzle(newId)) {
					return true;
	                
				}else {
					
	                /* Backtrack the solution */
					emptyCells.get(id).setValue(0);
					filledCells.remove(cell);
					updateCollections(value, cell);
				}
			}
		}

		 return false;
	        
	}

	/**
	 * Update all collections
	 * @param value
	 * @param cell
	 */
	private void updateCollections(int value, Cell cell) {
		Set<Integer> currentRowValues = cell.getRow().getValues();
        Set<Integer> currentColumnValues = cell.getColumn().getValues();
        Set<Integer> currentBoxValues = cell.getBox().getValues();

        if (currentRowValues.contains(value))
        	currentRowValues.remove(value);
        else 
        	currentRowValues.add(value);

        if (currentColumnValues.contains(value))
        	currentColumnValues.remove(value);
        else 
        	currentColumnValues.add(value);

        if (currentBoxValues.contains(value))
        	currentBoxValues.remove(value);
        else 
        	currentBoxValues.add(value);
	}
	
	/**
	 * Check if the cell value is valid by 
     * checking it against all values in the relevant sections
	 * @param value
	 * @param cell
	 * @return
	 */
    private boolean valueIsValid(int value, Cell cell) {
        return  (!cell.getRow().getValues().contains(value)) &&
                (!cell.getColumn().getValues().contains(value)) &&
                (!cell.getBox().getValues().contains(value));
    }
 		
    /**
     * Calculate all possible cells values to reduce number of iterations
     */
    private void calculateCellsPossibleValues() {
		for (Cell cell: emptyCells) {
			cell.initPossibleValues(size);
		}
	}
    
    @Override
    public String toString() {
    	StringBuffer sb = new StringBuffer();
        for (Cell[] sArr : this.matrix) {
            for (Cell s : sArr)
            	sb.append(s.toString());
            sb.append("\n");
        }
        return sb.toString();
    }
}
