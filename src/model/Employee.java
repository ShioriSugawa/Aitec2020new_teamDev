package model;

import java.io.Serializable;
import java.util.List;

/**
 * Copyright 2020 FUJITSU SOCIAL SCIENCE LABORATORY LIMITED<br>
 * システム名：自己紹介システム<br>
 * クラス概要：<br>
 *  従業員のJavaBeansクラス<br>
 */

/*
 * 修正内容まとめ
 * 2020/6/15 メンバ変数,setter,getterに所属追加、それに伴いコンストラクタ変更
 * 2020/6/16 詳細情報画面で利用するためメンバ変数、コンストラクタ、getter追加
 * 2020/6/16 一覧画面で現在の業務表示のためコンストラクタ追加(資格所持数は途中）
 *
 *
 */
public class Employee implements Serializable {

	private String employeeNumber, employeeName, employeeProfile,employeeDeployment,
					genreName,certificationOrSkillName,certificationDate;
	private int ownedId,count;
	private List<String> careerList;

	/**
	 * コンストラクタ　基本情報
	 * @param employeeNumber 従業員番号
	 * @param employeeName 氏名
	 * @param employeeProfile プロフィール
	 * @param employeeDeployment 所属
	 */
	public Employee(String employeeNumber, String employeeName, String employeeProfile, String employeeDeployment) {
		this.employeeNumber = employeeNumber;
		this.employeeName = employeeName;
		this.employeeProfile = employeeProfile;
		this.employeeDeployment = employeeDeployment;
	}

	/**
	 * コンストラクタ　資格スキル
	 * @param genreName 資格ジャンル
	 * @param certificationOrSkillName マスター登録資格名
	 * @param ownedId 保有資格ID
	 * @param certificationDate マスター登録資格認定日
	 */
	public Employee(String genreName, String certificationOrSkillName, int ownedId, String certificationDate) {
		this.genreName = genreName;
		this.certificationOrSkillName = certificationOrSkillName;
		this.ownedId = ownedId;
		this.certificationDate = certificationDate;
	}

	/**
	 * コンストラクタ　一覧画面
	 * @param employeeNumber 従業員番号
	 * @param employeeName 氏名
	 * @param employeeProfile プロフィール
	 * @param employeeDeployment 所属
	 * @param count 資格所持数
	 * @param careerList 現在の業務一覧
	 */
	public Employee(String employeeNumber, String employeeName, String employeeProfile, String employeeDeployment, int count, List<String> careerList) {
		this.employeeNumber = employeeNumber;
		this.employeeName = employeeName;
		this.employeeProfile = employeeProfile;
		this.employeeDeployment = employeeDeployment;
		this.count = count;
		this.careerList = careerList;
	}
	// 2020/6/16　仮追加
	public List<String> getCareerList(){
		return this.careerList;
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

	/**
	 * 所属部署を取得
	 * 	@return 所属部署
	 */
	public String getEmployeeDeployment() {
		return this.employeeDeployment;
	}

	/**
	 * 所属部署を設定
	 * @param employeeDeployment 所属部署
	 */
	public void setEmployeeDeployment(String employeeDeployment) {
		this.employeeDeployment = employeeDeployment;
	}

	/**
	 * 資格ジャンルを取得
	 * @return 資格ジャンル
	 */
	public String getGenreName() {
		return genreName;
	}
	/**
	 * 資格名orスキル名を取得
	 * @return 資格名orスキル名
	 */
	public String getCertificationOrSkillName() {
		return certificationOrSkillName;
	}
	/**
	 * 保有資格IDを取得
	 * @return 保有資格ID
	 */
	public int getOwnedId() {
		return ownedId;
	}
	/**
	 * 資格認定日を取得
	 * @return 登録資格認定日
	 */
	public String getCertificationDate() {
		return certificationDate;
	}
	/**
	 * 資格所持数を取得
	 * @return　資格所持数
	 */
	public int  getCount() {
		return count;
	}
}
