package com.bean;

public class BeanChild extends BeanParent {
	private String name="fds";

	public String getName() {
		return this.name;
	}

	public static void main(String[] args) {
		BeanParent bp = new BeanChild();
		;
		System.out.println(bp.go());
	}
}
