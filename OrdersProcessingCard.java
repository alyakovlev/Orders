import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.swing.AbstractButton;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;

public class OrdersProcessingCard {

	private static final String SHOW_CURRENT_ORDERS_LIST = "showCurrentOrdersList";
	private static final String SHOW_ITEMS_NUMBER = "showItemsNumber";
	private static final String REMOVE_BY_ORDER_ID = "removeByOrderId";
	private static final String SEARCH_BY_ADDRESS = "searchByAddress";
	private static final String SEARCH_BY_COMPANY = "searchByCompany";
	private static final String DISABLE_INPUT_TEXT_FIELD = "disableInputTextField";
	private static final String ENABLE_INPUT_TEXT_FIELD = "enableInputTextField";

	private JFrame frame;
	private JTextArea ordersListFromFileTextArea;
	private JTextField inputText;
	private ButtonGroup selectActionGroup;
	private JTextArea resultArea;

	private List<Order> orders = new ArrayList<>();

	public static void main(String[] args) throws IOException {
		OrdersProcessingCard ordersProcessingCard = new OrdersProcessingCard();
		ordersProcessingCard.go();
	}

	// initialization all GUI elements
	private void go() {

		frame = new JFrame("OrdersProcessingCard");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Font font = new Font("sansirif", Font.BOLD, 12);

		JPanel ordersListFromFilePanel = initOrdersListFromFilePanel(font);
		JPanel selectActionGroupPanel = initSelectActionGroupPanel(font);

		JButton actionButton = new JButton("GO!");
		actionButton.addActionListener(new MainButtonActionListener());

		JPanel resultAreaPanel = initResultAreaPanel(font);

		JMenuBar menuBar = new JMenuBar();
		JMenu fileMenu = new JMenu("File");
		JMenuItem loadMenuItem = new JMenuItem("Load Order Set");
		loadMenuItem.addActionListener(new OpenMenuListener());
		fileMenu.add(loadMenuItem);
		menuBar.add(fileMenu);

		frame.setJMenuBar(menuBar);
		frame.getContentPane().add(BorderLayout.NORTH, ordersListFromFilePanel);
		frame.getContentPane().add(BorderLayout.WEST, selectActionGroupPanel);
		frame.getContentPane().add(BorderLayout.EAST, actionButton);
		frame.getContentPane().add(BorderLayout.CENTER, resultAreaPanel);
		frame.setSize(740, 430);
		frame.setVisible(true);
	}

	private JPanel initResultAreaPanel(Font font) {
		JPanel resultAreaPanel = new JPanel();
		resultAreaPanel.setLayout(new BoxLayout(resultAreaPanel, BoxLayout.Y_AXIS));
		JLabel resultAreaLabel = new JLabel("Result Area");
		resultAreaLabel.setFont(font);
		resultAreaLabel.setForeground(Color.RED);

		resultArea = new JTextArea();
		resultArea.setEditable(false);
		JScrollPane resultAreaScroller = new JScrollPane(resultArea);
		resultAreaScroller.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		resultAreaScroller.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

		resultAreaPanel.add(resultAreaLabel);
		resultAreaPanel.add(resultAreaScroller);
		return resultAreaPanel;
	}

	private JPanel initOrdersListFromFilePanel(Font font) {
		JPanel ordersListFromFilePanel = new JPanel();
		JLabel ordersListFromFileLabel = new JLabel("Orders List From File");
		ordersListFromFileLabel.setFont(font);
		ordersListFromFileLabel.setForeground(Color.RED);

		ordersListFromFileTextArea = new JTextArea(10, 60);
		ordersListFromFileTextArea.setFont(font);

		ordersListFromFileTextArea.setLineWrap(true);
		ordersListFromFileTextArea.setEditable(false);

		JScrollPane scroller = new JScrollPane(ordersListFromFileTextArea);
		scroller.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scroller.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		ordersListFromFilePanel.setLayout(new BoxLayout(ordersListFromFilePanel, BoxLayout.Y_AXIS));
		ordersListFromFilePanel.add(ordersListFromFileLabel);
		ordersListFromFilePanel.add(scroller);
		return ordersListFromFilePanel;
	}

	private JPanel initSelectActionGroupPanel(Font font) {
		JRadioButton searchByCompany = new JRadioButton("Search By Company");
		searchByCompany.setSelected(true);
		searchByCompany.setName(SEARCH_BY_COMPANY);
		searchByCompany.setActionCommand(ENABLE_INPUT_TEXT_FIELD);
		searchByCompany.addActionListener(new RadioButtonActionListener());
		JRadioButton searchByAddress = new JRadioButton("Search By Address");
		searchByAddress.setName(SEARCH_BY_ADDRESS);
		searchByAddress.setActionCommand(ENABLE_INPUT_TEXT_FIELD);
		searchByAddress.addActionListener(new RadioButtonActionListener());
		JRadioButton removeByOrderId = new JRadioButton("Remove By OrderId");
		removeByOrderId.setName(REMOVE_BY_ORDER_ID);
		removeByOrderId.setActionCommand(ENABLE_INPUT_TEXT_FIELD);
		removeByOrderId.addActionListener(new RadioButtonActionListener());
		JRadioButton showItemsNumber = new JRadioButton("Show The Number Of Items");
		showItemsNumber.setName(SHOW_ITEMS_NUMBER);
		showItemsNumber.setActionCommand(DISABLE_INPUT_TEXT_FIELD);
		showItemsNumber.addActionListener(new RadioButtonActionListener());
		JRadioButton showCurrentOrdersList = new JRadioButton("Show Current Orders List");
		showCurrentOrdersList.setName(SHOW_CURRENT_ORDERS_LIST);
		showCurrentOrdersList.setActionCommand(DISABLE_INPUT_TEXT_FIELD);
		showCurrentOrdersList.addActionListener(new RadioButtonActionListener());

		selectActionGroup = new ButtonGroup();
		selectActionGroup.add(searchByCompany);
		selectActionGroup.add(searchByAddress);
		selectActionGroup.add(removeByOrderId);
		selectActionGroup.add(showItemsNumber);
		selectActionGroup.add(showCurrentOrdersList);

		JLabel selectActionGroupLabel = new JLabel("Select Action");
		selectActionGroupLabel.setFont(font);
		selectActionGroupLabel.setForeground(Color.RED);

		JPanel selectActionGroupPanel = new JPanel();
		selectActionGroupPanel.setLayout(new BoxLayout(selectActionGroupPanel, BoxLayout.Y_AXIS));

		selectActionGroupPanel.add(selectActionGroupLabel);
		selectActionGroupPanel.add(searchByCompany);
		selectActionGroupPanel.add(searchByAddress);
		selectActionGroupPanel.add(removeByOrderId);
		selectActionGroupPanel.add(showItemsNumber);
		selectActionGroupPanel.add(showCurrentOrdersList);

		inputText = new JTextField(20);
		JLabel inputTextLabel = new JLabel("Enter the input string");
		inputTextLabel.setFont(font);
		inputTextLabel.setForeground(Color.RED);

		selectActionGroupPanel.add(inputTextLabel);
		selectActionGroupPanel.add(inputText);

		return selectActionGroupPanel;
	}

	// open and load file
	public class OpenMenuListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent arg0) {
			JFileChooser fileOpen = new JFileChooser();
			fileOpen.showOpenDialog(frame);
			loadFile(fileOpen.getSelectedFile());
		}

		private void loadFile(File file) {
			orders.clear();
			try {
				BufferedReader reader = new BufferedReader(new FileReader(file));
				String line = null;
				while ((line = reader.readLine()) != null) {
					makeOrder(line);
				}
				reader.close();

			} catch (IOException ex) {
				System.out.println("couldn't read this file");
				ex.printStackTrace();
			}

			showOrders();
		}

		private void makeOrder(String lineToParse) {
			String[] result = lineToParse.split(",");
			orders.add(new Order(result[0].trim(), result[1].trim(), result[2].trim(), result[3].trim()));
			System.out.println("created an order");
		}

		private void showOrders() {
			StringBuilder result = new StringBuilder();
			for (Order order : orders) {
				result.append(order.toString()).append("\n");
			}
			ordersListFromFileTextArea.setText(result.toString());
		}

	}

	// change editable of inputText depending on selected action
	public class RadioButtonActionListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent ev) {

			switch (ev.getActionCommand()) {
			case ENABLE_INPUT_TEXT_FIELD:
				inputText.setEditable(true);
				break;
			case DISABLE_INPUT_TEXT_FIELD:
				inputText.setEditable(false);
				break;
			}

		}
	}

	// implement all logic
	public class MainButtonActionListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent ev) {

			StringBuilder result = new StringBuilder();
			// do something depending on selected action
			switch (getSelectedButtonText(selectActionGroup)) {
			case SEARCH_BY_COMPANY:

				String companyName = inputText.getText().trim();
				for (Order order : orders) {
					if (order.getCompanyName().equals(companyName))
						result.append(order.toString()).append("\n");
				}

				break;
			case SEARCH_BY_ADDRESS:

				String customerAdress = inputText.getText().trim();
				for (Order order : orders) {
					if (order.getCustomerAdress().equals(customerAdress))
						result.append(order.toString()).append("\n");
				}

				break;
			case REMOVE_BY_ORDER_ID:

				String orderId = inputText.getText().trim();
				Order orderForRemove = null;
				for (Order order : orders) {
					if (order.getOrderId().equals(orderId))
						orderForRemove = order;
					else
						result.append(order.toString()).append("\n");
				}

				if (orderForRemove != null)
					orders.remove(orderForRemove);
				break;
			case SHOW_ITEMS_NUMBER:

				Map<String, Integer> orderItemCount = new HashMap<>();

				for (Order order : orders) {
					String currentOrderItem = order.getOrderItem();
					if (orderItemCount.containsKey(currentOrderItem))
						orderItemCount.put(currentOrderItem, orderItemCount.get(currentOrderItem) + 1);
					else
						orderItemCount.put(currentOrderItem, 1);
				}

				for (Entry entry : entriesSortedByValues(orderItemCount)) {
					result.append(entry.toString()).append("\n");
				}
				break;
			case SHOW_CURRENT_ORDERS_LIST:
				for (Order order : orders)
					result.append(order.toString()).append("\n");
				break;

			}
			resultArea.setText(result.toString());
		}

		private String getSelectedButtonText(ButtonGroup buttonGroup) {
			for (Enumeration<AbstractButton> buttons = buttonGroup.getElements(); buttons.hasMoreElements();) {
				AbstractButton button = buttons.nextElement();

				if (button.isSelected()) {
					return button.getName();
				}
			}

			return null;
		}

		private <K, V extends Comparable<? super V>> List<Entry<K, V>> entriesSortedByValues(Map<K, V> map) {

			List<Entry<K, V>> sortedEntries = new ArrayList<Entry<K, V>>(map.entrySet());

			Collections.sort(sortedEntries, new Comparator<Entry<K, V>>() {
				@Override
				public int compare(Entry<K, V> e1, Entry<K, V> e2) {
					return e2.getValue().compareTo(e1.getValue());
				}
			});

			return sortedEntries;
		}

	}

}
