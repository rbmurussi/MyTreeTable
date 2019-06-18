package org.swing.tree.table;

import javax.swing.*;
import java.awt.*;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class TreeTableMain extends JFrame {

	private MyAbstractTreeTableModel treeTableModel;

	public TreeTableMain() {
		super("Tree Table Demo");

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		setLayout(new GridLayout(0, 1));

		treeTableModel = new MyDataModel(createDataStructure());

		MyTreeTable myTreeTable = new MyTreeTable(treeTableModel);
		//setLayout(new BorderLayout());

		JMenuBar jMenuBar = new JMenuBar();
		setJMenuBar(jMenuBar);

		JMenu fileMenu = new JMenu("File");
		jMenuBar.add(fileMenu);
		JMenuItem openAction = new JMenuItem("Open");
		fileMenu.add(openAction);

		add(new JScrollPane(myTreeTable));

		setSize(800, 600);
	}

	private String get(String name, Object obj) {
		try {
			name = name.substring(0,1).toUpperCase().concat(name.substring(1));
			Method method = obj.getClass().getMethod("get" + name);
			Object ret = method.invoke(obj);
			return ret != null ? String.valueOf(ret): null;
		} catch (Exception e) {
			return e.getMessage();
		}
	}

	private List<MyDataNode> parseMyDataNode(Object obj) {
		List<MyDataNode> listMyDataNode = new ArrayList<>();
		List<Field> listField = defFields(obj.getClass());
		for (Field field: listField) {
			String value = get(field.getName(), obj);
			String description = "";
			listMyDataNode.add(new MyDataNode(field.getName(), value, description, null));
		}
		return listMyDataNode;
	}

	private List<Field> defFields(Class<?> tClass) {
		List<Field> list = new ArrayList<>();;
		if(!tClass.equals(Object.class)) {
			list = defFields(tClass.getSuperclass());
		}
		Field[] declaredFields = tClass.getDeclaredFields();
		for(Field field : declaredFields) {
			list.add(field);
		}
		return list;
	}

	private MyDataNode create(List<Object> listObject) {
		List<MyDataNode> rootNodes = new ArrayList<>();
		for(Object object: listObject) {
			List<MyDataNode> children = parseMyDataNode(object);
			rootNodes.add(new MyDataNode(object.getClass().getName(), object.toString(), "", children));
		}
		MyDataNode root = new MyDataNode("", "", "", rootNodes);
		return root;
	}

	private static MyDataNode createDataStructure() {
		List<MyDataNode> children1 = new ArrayList<>();
		children1.add(new MyDataNode("field1", "value1", "comment1", null));
		children1.add(new MyDataNode("field2", "value2", "comment2", null));
		children1.add(new MyDataNode("field3", "value3", "comment3", null));
		children1.add(new MyDataNode("field4", "value4", "comment4", null));

		List<MyDataNode> rootNodes = new ArrayList<>();
		rootNodes.add(new MyDataNode("line1", "values", "", children1));
		rootNodes.add(new MyDataNode("line2", "values", "", children1));
		rootNodes.add(new MyDataNode("line3", "values", "", children1));
		rootNodes.add(new MyDataNode("line4", "values", "", children1));
		rootNodes.add(new MyDataNode("line5", "values", "", children1));
		rootNodes.add(new MyDataNode("line6", "values", "", children1));
		rootNodes.add(new MyDataNode("line7", "values", "", children1));

		MyDataNode root = new MyDataNode("root", "", "", rootNodes);
		return root;
	}

	public static void main(final String[] args) {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
			e.printStackTrace();
		}
		new TreeTableMain().setVisible(true);
	}
}
