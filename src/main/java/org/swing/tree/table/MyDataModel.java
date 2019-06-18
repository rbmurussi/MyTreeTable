package org.swing.tree.table;

public class MyDataModel extends MyAbstractTreeTableModel {

	static protected String[] columnNames = { "Field", "Value", "Description"};

	static protected Class<?>[] columnTypes = { MyTreeTableModel.class, String.class, String.class };

	public MyDataModel(MyDataNode rootNode) {
		super(rootNode);
		root = rootNode;
	}

	public Object getChild(Object parent, int index) {
		return ((MyDataNode) parent).getChildren().get(index);
	}

	public int getChildCount(Object parent) {
		return ((MyDataNode) parent).getChildren().size();
	}

	public int getColumnCount() {
		return columnNames.length;
	}

	public String getColumnName(int column) {
		return columnNames[column];
	}

	public Class<?> getColumnClass(int column) {
		return columnTypes[column];
	}

	public Object getValueAt(Object node, int column) {
		switch (column) {
			case 0:
				return ((MyDataNode) node).getField();
			case 1:
				return ((MyDataNode) node).getValue();
			case 2:
				return ((MyDataNode) node).getDescription();
			default:
				break;
		}
		return null;
	}

	public boolean isCellEditable(Object node, int column) {
		return true;
	}

	public void setValueAt(Object aValue, Object node, int column) {
	}

}
