package model;

import java.io.Serializable;

/**
 * Copyright 2020 FUJITSU SOCIAL SCIENCE LABORATORY LIMITED<br>
 * Copyright 2020 AitecTreasure＆Toraja Co.,Ltd<br>
 * システム名：自己紹介システム<br>
 * クラス概要：スキルのJavaBeansクラス<br>
 */


public class Skill implements Serializable {

	private String employeeNumber,genreCode,genreName,skillName;
	private int ownedId;

	//スキル登録(4)
	/**
	 * コンストラクタ(スキルの登録用)[4要素]
	 * @param employeeNumber 従業員番号
	 * @param genreCode スキルジャンルコード
	 * @param genreName スキルジャンル名
	 * @param skillName スキル名
	 */
	public Skill(String employeeNumber,String genreCode,String genreName,String skillName) {
		this.employeeNumber=employeeNumber;
		this.genreCode=genreCode;
		this.genreName=genreName;
		this.skillName=skillName;
	}

	/**
	 * コンストラクタ(スキルの表示/編集用)[5要素]
	 * @param ownedId スキル保有ID
	 * @param employeeNumber 従業員番号
	 * @param genreCode スキルジャンルコード
	 * @param genreName スキルジャンル名
	 * @param skillName スキル名
	 */
	public Skill(int ownedId,String employeeNumber,String genreCode,String genreName,String skillName) {
		this.ownedId=ownedId;
		this.employeeNumber=employeeNumber;
		this.genreCode=genreCode;
		this.genreName=genreName;
		this.skillName=skillName;
	}

	//スキルジャンル一覧取得用
	/**
	 * コンストラクタ(スキルジャンル一覧取得用)
	 * @param genreCode スキルジャンルコード
	 * @param genreName スキルジャンル名
	 */
	public Skill(String genreCode,String genreName) {
		this.genreCode=genreCode;
		this.genreName=genreName;
	}



	//以下、getter
	/**
	 * スキル保有IDを取得
	 * @return スキル保有ID
	 */
	public int getOwnedId() {
		return ownedId;
	}

	/**
	 * 従業員番号を取得
	 * @return 従業員番号
	 */
	public String getEmployeeNumber() {
		return employeeNumber;
	}

	/**
	 * スキルジャンルコードを取得
	 * @return スキルジャンルコード
	 */
	public String getGenreCode() {
		return genreCode;
	}

	/**
	 * スキルジャンル名を取得
	 * @return スキルジャンル名
	 */
	public String getGenreName() {
		return genreName;
	}

	/**
	 * スキル名を取得
	 * @return スキル名
	 */
	public String getSkillName() {
		return skillName;
	}

	//以下、setter
	/**
	 * 従業員番号を設定
	 * @param employeeNumber 従業員番号
	 */
	public void setEmployeeNumber(String employeeNumber) {
		this.employeeNumber = employeeNumber;
	}

	/**
	 * スキル保有IDを設定
	 * @param ownedId スキル保有ID
	 */
	public void setOwnedId(int ownedId) {
		this.ownedId = ownedId;
	}

	/**
	 * スキルジャンルコードを設定
	 * @param genreCode スキルジャンルコード
	 */
	public void setGenreCode(String genreCode) {
		this.genreCode = genreCode;
	}

	/**
	 * スキルジャンル名を設定
	 * @param genreName スキルジャンル名
	 */
	public void setGenreName(String genreName) {
		this.genreName = genreName;
	}

	/**
	 * スキル名を設定
	 * @param skillName スキル名
	 */
	public void setSkillName(String skillName) {
		this.skillName = skillName;
	}

}
