package model;

import java.io.Serializable;

/**
 * Copyright 2020 FUJITSU SOCIAL SCIENCE LABORATORY LIMITED<br>
 * システム名：自己紹介システム<br>
 * クラス概要： 業務経歴のJavaBeansクラス<br>
 */

public class Career implements Serializable {

	private String  businessNumber, employeeNumber, businessStart, businessEnd, businessName, situation;

	/**
	 * コンストラクタ
	 * @param businessNumber 業務経歴番号
	 * @param employeeNumber 従業員番号
	 * @param businessStart 業務開始日
	 * @param businessEnd 業務終了日
	 * @param businessName 業務名
	 * @param situation 状況
	 */
	public  Career(String businessNumber, String employeeNumber, String businessStart, String businessEnd, String businessName, String situation) {
		this.businessNumber = businessNumber;
		this.employeeNumber = employeeNumber;
		this.businessStart = businessStart;
		this.businessEnd = businessEnd;
		this.businessName = businessName;
		this.situation = situation;
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
	 * 業務名を取得
	 * @return 業務名
	 */
	public String getBusinessName() {
		return businessName;
	}


	/**
	 * 状況を取得
	 * @return 状況
	 */
	public String getSituation() {
		return situation;
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
	 * 業務開始日を設定
	 * @param businessStart 開始日
	 */
	public void setBusinessStart(String businessStart) {
		this.businessStart = businessStart;
	}

	/**
	 * 業務終了日を設定
	 * @param businessEnd 終了日
	 */
	public void setBusinessEnd(String businessEnd) {
		this.businessEnd = businessEnd;
	}

	/**
	 * 業務名を設定
	 * @param businessName 業務名
	 */
	public void setBusinessName(String businessName) {
		this.businessName = businessName;
	}

	/**
	 * 状況を設定
	 * @param situation 状況
	 */
	public void setSituation(String situation) {
		this.situation = situation;
	}



}
