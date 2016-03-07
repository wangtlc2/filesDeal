package com.app.updatefile.v2.bean;

/**
 * 传引用型的bool
 * @Package: com.app.updatefile.v2.bean
 * @ClassName: BooleanValue
 * @author 王陶林 wangtlc@si-tech.com.cn
 * @date 2013-4-15 上午10:13:50
 * @Copyright © SI-TECH 2013. All rights reserved
 * @version: V1.0
 *
 * 修改日期    修改人    修改目的
 *
 */
public class BooleanValue {
	public boolean value;

	public static void main(String[] args) {
		BooleanValue isto = new BooleanValue();
		isto.value = true;
		test(isto);
		System.out.println(isto.value);
	}

	private static void test(BooleanValue isto) {
		isto.value = false;
	}
	
}
