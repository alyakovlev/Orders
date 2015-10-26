public class Order {

	private String orderId;
	private String companyName;
	private String customerAdress;
	private String orderItem;

	public Order(String orderId, String companyName, String customerAdress, String orderItem) {
		super();
		this.orderId = orderId;
		this.companyName = companyName;
		this.customerAdress = customerAdress;
		this.orderItem = orderItem;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getCustomerAdress() {
		return customerAdress;
	}

	public void setCustomerAdress(String customerAdress) {
		this.customerAdress = customerAdress;
	}

	public String getOrderItem() {
		return orderItem;
	}

	public void setOrderItem(String orderItem) {
		this.orderItem = orderItem;
	}

	@Override
	public String toString() {
		return String.format("%s, %s, %s, %s", orderId, companyName, customerAdress, orderItem);
	}

}
