package model;

import java.io.Serializable;

/**
 * Copyright 2020 FUJITSU SOCIAL SCIENCE LABORATORY LIMITED<br>
 * システム名：自己紹介システム<br>
 * クラス概要：<br>
 *  業務経歴のJavaBeansクラス<br>
 */


public class Career implements Serializable {

	private String  businessNumber, employeeNumber, businessName, businessStart, businessEnd;

	/**
	 * コンストラクタ
	 * @param employeeNumber 従業員番号
	 * @param businessNumber 業務経歴番号
	 * @param businessNname 業務名
	 * @param businessStart 業務開始日
	 * @param businessEnd 業務終了日
	 * @return
	 */
	public  Career(String businessNumber, String employeeNumber, String businessName, String businessStart, String businessEnd) {
		this.businessNumber = businessNumber;
		this.employeeNumber = employeeNumber;
		this.businessName = businessName;
		this.businessStart = businessStart;
		this.businessEnd = businessEnd;
	}

	/**
	 * 業務経歴番号を取得
	 * @return 業務名
	 */
	public String getBusinessNumber() {
		return businessNumber;
	}

	/**
	 * 従業員番号を取得
	 * @return 従業員番号
	 */
	public String getEmployeeNumber() {
		return employeeNumber;
	}

	/**
	 * 業務名を取得
	 * @return 業務名
	 */
	public String getBusinessName() {
		return businessName;
	}

	/**
	 * 業務開始日を取得
	 * @return 業務名
	 */
	public String getBusinessStart() {
		return businessStart;
	}

	/**
	 * 業務終了日を取得
	 * @return 業務名
	 */
	public String getBusinessEnd() {
		return businessEnd;
	}


	/**
	 * 業務経歴番号を設定
	 * @param businessNumber 業務名
	 */
	public void setBusinessNumber(String businessNumber) {
		this.businessNumber = businessNumber;
	}

	/**
	 * 従業員番号を設定
	 * @param employeeNumber 従業員番号
	 */
	public void setEmployeeNumber(String employeeNumber) {
		this.employeeNumber = employeeNumber;
	}

	/**
	 * 業務名を設定
	 * @param businessName 業務名
	 */
	public void setBusinessName(String businessName) {
		this.businessName = businessName;
	}

	/**
	 * 業務終了日を設定
	 * @param businessName 業務名
	 */
	public void setBusinessStart(String businessStart) {
		this.businessStart = businessStart;
	}

	/**
	 * 業務開始日を設定
	 * @param businessName 業務名
	 */
	public void setBusinessEnd(String businessEnd) {
		this.businessEnd = businessEnd;
	}



}
