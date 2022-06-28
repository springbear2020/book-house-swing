package com.springbear.ebrss.model;

import javax.swing.table.AbstractTableModel;

/**
 * Custom table model class
 *
 * @author Spring-_-Bear
 * @date 2021-12-18 23:42
 */
public class CustomTableModel extends AbstractTableModel {
    private final Object[][] rowData;
    private final String[] columnNames;

    public CustomTableModel(Object[][] rowData, String[] columnNames) {
        this.rowData = rowData;
        this.columnNames = columnNames;
    }

    @Override
    public int getRowCount() {
        return rowData.length;
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        return rowData[rowIndex][columnIndex];
    }

    @Override
    public String getColumnName(int column) {
        return columnNames[column];
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return true;
    }
}
