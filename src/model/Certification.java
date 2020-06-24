package model;

import java.io.Serializable;

/**
 * Copyright 2020 FUJITSU SOCIAL SCIENCE LABORATORY LIMITED<br>
 * システム名：自己紹介システム<br>
 * クラス概要：資格(マスタ・その他)のJavaBeansクラス<br>
 */


public class Certification implements Serializable {

	private String employeeNumber,certiCode,certiGenre,masterCode,certiName,certiDate;
	private int ownedId;


	//資格[コード&名]一覧用（ジャンル・資格名共用）（マスタその他共用）
	public Certification(String certiCode,String certiName) {
		this.certiCode=certiCode;
		this.certiName=certiName;
	}

		//登録用コンストラクタあとで追加！！

	/**
	 * コンストラクタ(マスタ資格の表示/編集用)(7)
	 * @param ownedId 保有資格ID
	 * @param employeeNumber 従業員番号
	 * @param certiCode 資格ジャンルコード
	 * @param certiGenre 資格ジャンル名
	 * @param masterCode 資格コード
	 * @param certiName 資格名
	 * @param certiDate 認定日
	 */

	public Certification(int ownedId,String employeeNumber,String certiCode,String certiGenre,String masterCode,String certiName,String certiDate) {
		this.ownedId=ownedId;
		this.employeeNumber=employeeNumber;
		this.certiCode=certiCode;
		this.certiGenre=certiGenre;
		this.masterCode=masterCode;
		this.certiName=certiName;
		this.certiDate=certiDate;
	}

	/**
	 * コンストラクタ(その他資格の表示/編集用)(6)
	 * @param ownedId 保有その他資格ID
	 * @param employeeNumber 従業員番号
	 * @param certiCode 資格ジャンルコード
	 * @param certiGenre 資格ジャンル名
	 * @param certiDate 認定日
	 * @param certiName その他資格名
	 */

	public Certification(int ownedId,String employeeNumber,String certiCode,String certiGenre,String certiName,String certiDate) {
		this.ownedId=ownedId;
		this.employeeNumber=employeeNumber;
		this.certiCode=certiCode;
		this.certiGenre=certiGenre;
		this.certiName=certiName;
		this.certiDate=certiDate;
	}

	/**
	 * 保有資格(マスタ・その他)IDを取得
	 * @return 保有資格(マスタ・その他)ID
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
	 * コード(資格ジャンル/マスタ資格)を取得
	 * @return （資格ジャンルかマスタ資格の）コード
	 */
	public String getCertiCode() {
		return certiCode;
	}

	/**
	 * マスタ資格コードを取得（IDによる1件表示専用）（後から追加した為）
	 * @return マスタ資格コード
	 */
	public String getMasterCode() {
		return masterCode;
	}

	/**
	 * 資格名（その他のみ）を取得
	 * @return その他資格名
	 */
	public String getCertiName() {
		return certiName;
	}

	/**
	 * 認定日を取得
	 * @return 認定日
	 */
	public String getCertiDate() {
		return certiDate;
	}

	/**
	 * @return certiGenre
	 */
	public String getCertiGenre() {
		return certiGenre;
	}

	/**
	 * @param employeeNumber セットする employeeNumber
	 */
	public void setEmployeeNumber(String employeeNumber) {
		this.employeeNumber = employeeNumber;
	}

	/**
	 * @param certiCode セットする certiCode
	 */
	public void setCertiCode(String certiCode) {
		this.certiCode = certiCode;
	}

	/**
	 * @param certiGenre セットする certiGenre
	 */
	public void setCertiGenre(String certiGenre) {
		this.certiGenre = certiGenre;
	}


	/**
	 * @param masterCode セットする masterCode
	 */
	public void setMasterCode(String masterCode) {
		this.masterCode = masterCode;
	}

	/**
	 * @param certiName セットする certiName
	 */
	public void setCertiName(String certiName) {
		this.certiName = certiName;
	}

	/**
	 * @param certiDate セットする certiDate
	 */
	public void setCertiDate(String certiDate) {
		this.certiDate = certiDate;
	}

	/**
	 * @param ownedId セットする ownedId
	 */
	public void setOwnedId(int ownedId) {
		this.ownedId = ownedId;
	}

}
