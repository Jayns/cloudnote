package org.tedu.cloudnote.entity;

import java.io.Serializable;

public class Emp implements Serializable{
	private int no;
	private String name;
	private Double sal;
	public int getNo() {
		return no;
	}
	public void setNo(int no) {
		this.no = no;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Double getSal() {
		return sal;
	}
	public void setSal(Double sal) {
		this.sal = sal;
	}
	
}
