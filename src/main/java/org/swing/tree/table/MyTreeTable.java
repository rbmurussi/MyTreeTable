package org.swing.tree.table;

import javax.swing.*;
import java.awt.*;

public class MyTreeTable extends JTable {
	private MyTreeTableCellRenderer tree;

	public MyTreeTable(MyAbstractTreeTableModel treeTableModel) {
		super();
		tree = new MyTreeTableCellRenderer(this, treeTableModel);
		super.setModel(new MyTreeTableModelAdapter(treeTableModel, tree));
		MyTreeTableSelectionModel selectionModel = new MyTreeTableSelectionModel();
		tree.setSelectionModel(selectionModel);
		setSelectionModel(selectionModel.getListSelectionModel());
		setDefaultRenderer(MyTreeTableModel.class, tree);
		setDefaultEditor(MyTreeTableModel.class, new MyTreeTableCellEditor(tree, this));
		setShowGrid(false);
		setIntercellSpacing(new Dimension(0, 0));

	}
}
