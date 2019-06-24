package org.swing.tree.table;

import org.clearingio.ipm.MsgIpm;
import org.clearingio.ipm.file.RdwDataInputStream;
import org.clearingio.iso8583.annotation.enumeration.Encode;
import org.clearingio.iso8583.builder.MsgBuilder;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class TreeTableMain extends JFrame {

	public TreeTableMain() {
		super("Tree Table Demo");

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		setLayout(new GridLayout(0, 1));

		JMenuBar jMenuBar = new JMenuBar();
		setJMenuBar(jMenuBar);

		JMenu fileMenu = new JMenu("File");
		jMenuBar.add(fileMenu);
		JMenuItem openAction = new JMenuItem("Open ISO-8583");
		openAction.addActionListener((e) -> openISO8583());
		fileMenu.add(openAction);

		setSize(800, 600);
	}

	private void openISO8583() {
		JFileChooser jFileChooser = new JFileChooser();
		jFileChooser.showOpenDialog(this);
		File file = jFileChooser.getSelectedFile();
		System.out.println(file.getAbsolutePath());

		List<Object> list = new ArrayList<>();

		try(RdwDataInputStream in = new RdwDataInputStream(new FileInputStream(file))) {
			MsgBuilder<MsgIpm> msgBuilder = new MsgBuilder<>(MsgIpm.class, Encode.EBCDIC);
			while(in.hasNext()) {
				list.add(msgBuilder.unpack(in.next()));
			}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(this, e.getMessage(), parseException(e), JOptionPane.ERROR_MESSAGE);
		}

		MyDataNode myDataNode = load(list.iterator(), file.getAbsolutePath());
		MyAbstractTreeTableModel treeTableModel = new MyDataModel(myDataNode);
		MyTreeTable myTreeTable = new MyTreeTable(treeTableModel);
		add(new JScrollPane(myTreeTable));
		pack();

		repaint();
	}

	private String parseException(Exception e) {
		StackTraceElement st[] = e.getStackTrace();
		String err = "";
		for(int i = 0; i < st.length; i++){
			err += st[i].toString() + '\n';
		}
		return err;
	}

	private static String get(String name, Object obj) {
		try {
			name = name.substring(0,1).toUpperCase().concat(name.substring(1));
			Method method = obj.getClass().getMethod("get" + name);
			Object ret = method.invoke(obj);
			return ret != null ? String.valueOf(ret): null;
		} catch (Exception e) {
			return e.getMessage();
		}
	}

	private static List<MyDataNode> parseMyDataNode(Object obj) {
		List<MyDataNode> listMyDataNode = new ArrayList<MyDataNode>();
		List<Field> listField = defFields(obj.getClass());
		for (Field field: listField) {
			String value = get(field.getName(), obj);
			String description = "";
			listMyDataNode.add(new MyDataNode(field.getName(), value, description, null));
		}
		return listMyDataNode;
	}

	private static List<Field> defFields(Class<?> tClass) {
		List<Field> list = new ArrayList<Field>();
		if(!tClass.equals(Object.class)) {
			list = defFields(tClass.getSuperclass());
		}
		Field[] declaredFields = tClass.getDeclaredFields();
		for(Field field : declaredFields) {
			list.add(field);
		}
		return list;
	}

	private static MyDataNode load(Iterator<Object> itObject, String fileName) {
		List<MyDataNode> rootNodes = new ArrayList<MyDataNode>();
		while(itObject.hasNext()) {
			Object object = itObject.next();
			List<MyDataNode> children = parseMyDataNode(object);
			rootNodes.add(new MyDataNode(object.getClass().getName(), object.toString(), "", children));
		}
		MyDataNode root = new MyDataNode(fileName, "", "", rootNodes);
		return root;
	}

	private static MyDataNode createDataStructure() {
		List<MyDataNode> children1 = new ArrayList<MyDataNode>();
		children1.add(new MyDataNode("field1", "value1", "comment1", null));
		children1.add(new MyDataNode("field2", "value2", "comment2", null));
		children1.add(new MyDataNode("field3", "value3", "comment3", null));
		children1.add(new MyDataNode("field4", "value4", "comment4", null));

		List<MyDataNode> rootNodes = new ArrayList<MyDataNode>();
		rootNodes.add(new MyDataNode("line1", "values", "", children1));
		rootNodes.add(new MyDataNode("line2", "values", "", children1));
		rootNodes.add(new MyDataNode("line3", "values", "", children1));
		rootNodes.add(new MyDataNode("line4", "values", "", children1));
		rootNodes.add(new MyDataNode("line5", "values", "", children1));
		rootNodes.add(new MyDataNode("line6", "values", "", children1));
		rootNodes.add(new MyDataNode("line7", "values", "", children1));

		MyDataNode root = new MyDataNode(" root", "", "", rootNodes);
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
