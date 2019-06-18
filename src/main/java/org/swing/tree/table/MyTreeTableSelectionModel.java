package org.swing.tree.table;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.tree.DefaultTreeSelectionModel;

public class MyTreeTableSelectionModel extends DefaultTreeSelectionModel {

	public MyTreeTableSelectionModel() {
		super();

		getListSelectionModel().addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent e) {
			}
		});
	}

	ListSelectionModel getListSelectionModel() {
		return listSelectionModel;
	}
}
