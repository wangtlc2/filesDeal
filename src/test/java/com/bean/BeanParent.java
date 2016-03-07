package com.bean;

public class BeanParent {
	public String go() {
		BeanChild t=(BeanChild)this;
		return t.getName();
	}
}
