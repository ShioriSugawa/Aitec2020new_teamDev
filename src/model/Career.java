package model;

import java.io.Serializable;

/**
 * Copyright 2020 FUJITSU SOCIAL SCIENCE LABORATORY LIMITED<br>
 * システム名：自己紹介システム<br>
 * クラス概要：<br>
 *  業務経歴のJavaBeansクラス<br>
 */

/*
 * 6/15
 * 業務経歴クラス追加
 */

public class Career implements Serializable {

	private String employeeNumber, businessNumber, businessName;

	/**
	 * コンストラクタ
	 * @param employeeNumber 従業員番号
	 * @param employeeName 氏名
	 * @param employeeProfile プロフィール
	 * @return
	 */
	public  Career(String employeeNumber, String businessNumber, String businessName) {
		this.employeeNumber = employeeNumber;
		this.businessNumber = businessNumber;
		this.businessName = businessName;
	}

	/**
	 * 従業員番号を取得
	 * @return 従業員番号
	 */
	public String getEmployeeNumber() {
		return employeeNumber;
	}

	/**
	 * 従業員番号を設定
	 * @param employeeNumber 従業員番号
	 */
	public void setEmployeeNumber(String employeeNumber) {
		this.employeeNumber = employeeNumber;
	}

	/**
	 * 業務名を取得
	 * @return 業務名
	 */
	public String getBusinessNumber() {
		return businessNumber;
	}

	/**
	 * 業務名を設定
	 * @param businessName 業務名
	 */
	public void setBusinessNumber(String businessNumber) {
		this.businessNumber = businessNumber;
	}

	/**
	 * 業務名を取得
	 * @return 業務名
	 */
	public String getBusinessName() {
		return businessName;
	}

	/**
	 * 業務名を設定
	 * @param businessName 業務名
	 */
	public void setBusinessName(String businessName) {
		this.businessName = businessName;
	}

}
