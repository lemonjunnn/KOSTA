package com.my.dto;

import java.util.Date;
import java.util.List;

/*
 * 주문기본정보
 */
public class OrderInfo {
	private int orderNo;
	private String orderId;
	private Date orderDate;
	private List<OrderLine> lines; //OrderInfo has an OrderLine relationship
	
	public OrderInfo() {
	}
	
	public OrderInfo(String orderId, List<OrderLine> lines) {
		this.orderId = orderId;
		this.lines = lines;
	}

	public OrderInfo(int orderNo, String orderId, Date orderDate, List<OrderLine> lines) {
		this.orderNo = orderNo;
		this.orderId = orderId;
		this.orderDate = orderDate;
		this.lines = lines;
	}	
	public int getOrderNo() {
		return orderNo;
	}
	public void setOrderNo(int orderNo) {
		this.orderNo = orderNo;
	}
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public Date getOrderDate() {
		return orderDate;
	}
	public void setOrderDate(Date orderDate) {
		this.orderDate = orderDate;
	}
	public List<OrderLine> getLines() {
		return lines;
	}
	public void setLines(List<OrderLine> lines) {
		this.lines = lines;
	}

	@Override
	public String toString() {
		return "OrderInfo [orderNo=" + orderNo + ", orderId=" + orderId + ", orderDate=" + orderDate + ", lines=" + lines
				+ "]";
	}
	
}
