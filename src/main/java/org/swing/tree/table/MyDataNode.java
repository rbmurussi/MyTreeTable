package org.swing.tree.table;

import java.util.Collections;
import java.util.List;

public class MyDataNode {

	private String field;
	private String value;
	private String description;

	private List<MyDataNode> children;

	public MyDataNode(String field, String value, String description, List<MyDataNode> children) {
		this.field = field;
		this.value = value;
		this.description = description;
		this.children = children;

		if (this.children == null) {
			this.children = Collections.emptyList();
		}
	}

	public void setField(String field) {
		this.field = field;
	}

	public String getField() {
		return field;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getDescription() {
		return description;
	}

	public List<MyDataNode> getChildren() {
		return children;
	}

	public String toString() {
		return field;
	}
}
