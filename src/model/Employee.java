package model;

import java.io.Serializable;

/**
 * Copyright 2020 FUJITSU SOCIAL SCIENCE LABORATORY LIMITED<br>
 * システム名：自己紹介システム<br>
 * クラス概要：<br>
 *  従業員のJavaBeansクラス<br>
 */
public class Employee implements Serializable {

	private String employeeNumber, employeeName, employeeProfile;

	/**
	 * コンストラクタ
	 * @param employeeNumber 従業員番号
	 * @param employeeName 氏名
	 * @param employeeProfile プロフィール
	 */
	public Employee(String employeeNumber, String employeeName, String employeeProfile) {
		this.employeeNumber = employeeNumber;
		this.employeeName = employeeName;
		this.employeeProfile = employeeProfile;
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
	 * 氏名を取得
	 * @return 氏名
	 */
	public String getEmployeeName() {
		return employeeName;
	}

	/**
	 * 氏名を設定
	 * @param employeeName 氏名
	 */
	public void setEmployeeName(String employeeName) {
		this.employeeName = employeeName;
	}

	/**
	 * プロフィールを取得
	 * @return プロフィール
	 */
	public String getEmployeeProfile() {
		return employeeProfile;
	}

	/**
	 * プロフィールを設定
	 * @param employeeProfile プロフィール
	 */
	public void setEmployeeProfile(String employeeProfile) {
		this.employeeProfile = employeeProfile;
	}
}
