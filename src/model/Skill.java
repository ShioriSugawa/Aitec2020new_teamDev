package model;

import java.io.Serializable;

/**
 * Copyright 2020 FUJITSU SOCIAL SCIENCE LABORATORY LIMITED<br>
 * システム名：自己紹介システム<br>
 * クラス概要：スキルのJavaBeansクラス<br>
 */


public class Skill implements Serializable {

	private String employeeNumber,genreCode,genreName,skillName;
	private int ownedId;

	//スキル登録(4)
	public Skill(String employeeNumber,String genreCode,String genreName,String skillName) {
		this.employeeNumber=employeeNumber;
		this.genreCode=genreCode;
		this.genreName=genreName;
		this.skillName=skillName;
	}

	//スキル表示/更新(5)（更新時はnumberは無視）
	public Skill(int ownedId,String employeeNumber,String genreCode,String genreName,String skillName) {
		this.ownedId=ownedId;
		this.employeeNumber=employeeNumber;
		this.genreCode=genreCode;
		this.genreName=genreName;
		this.skillName=skillName;
	}

	//スキルジャンル一覧取得用
	public Skill(String genreCode,String genreName) {
		this.genreCode=genreCode;
		this.genreName=genreName;
	}



	//以下、getter
	public int getOwnedId() {
		return ownedId;
	}

	public String getEmployeeNumber() {
		return employeeNumber;
	}

	public String getGenreCode() {
		return genreCode;
	}

	public String getGenreName() {
		return genreName;
	}

	public String getSkillName() {
		return skillName;
	}

	//以下、setter
	/**
	 * @param employeeNumber セットする employeeNumber
	 */
	public void setEmployeeNumber(String employeeNumber) {
		this.employeeNumber = employeeNumber;
	}

	/**
	 * @param ownedId セットする ownedId
	 */
	public void setOwnedId(int ownedId) {
		this.ownedId = ownedId;
	}

	/**
	 * @param genreCode セットする genreCode
	 */
	public void setGenreCode(String genreCode) {
		this.genreCode = genreCode;
	}

	/**
	 * @param genreName セットする genreName
	 */
	public void setGenreName(String genreName) {
		this.genreName = genreName;
	}

	/**
	 * @param skillName セットする skillName
	 */
	public void setSkillName(String skillName) {
		this.skillName = skillName;
	}

}
