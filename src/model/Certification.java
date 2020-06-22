package model;

import java.io.Serializable;

/**全体的にまだ途中です
 * Copyright 2020 FUJITSU SOCIAL SCIENCE LABORATORY LIMITED<br>
 * システム名：自己紹介システム<br>
 * クラス概要：資格(マスタ・その他)のJavaBeansクラス<br>
 */


public class Certification implements Serializable {

	private String employeeNumber,certiCode,certiGenre,certiName,certiDate;
	private int ownedId;


	//資格[コード&名]一覧用（ジャンル・資格名共用）（マスタその他共用）
	public Certification(String certiCode,String certiName) {
		this.certiCode=certiCode;
		this.certiName=certiName;
	}


	/**
	 * コンストラクタ(マスタ資格用)
	 * @param ownedId 保有資格ID
	 * @param employeeNumber 従業員番号
	 * @param certiCode 資格コード
	 * @param certiDate 認定日
	 */

	public Certification(int ownedId,String employeeNumber,String certiCode,String certiDate) {
		this.ownedId=ownedId;
		this.employeeNumber=employeeNumber;
		this.certiCode=certiCode;
		this.certiDate=certiDate;
	}

	/**
	 * コンストラクタ(その他資格用)
	 * @param certiId 保有その他資格ID
	 * @param employeeNumber 従業員番号
	 * @param certiCode 資格コード
	 * @param certiName その他資格名
	 * @param certiDate 認定日
	 */
	/*	いったん保留で。
	public Certification(String certiId,String employeeNumber,String certiCode,String certiName,String certiDate) {
		this.certiId=certiId;
		this.employeeNumber=employeeNumber;
		this.certiCode=certiCode;
		this.certiName=certiName;
		this.certiDate=certiDate;
	}//*/

	/**
	 * 保有資格(マスタ・その他)IDを取得
	 * @return 保有資格(マスタ・その他)ID
	 */
	public int getCertiId() {
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
	 * 資格(マスタ)コードを取得
	 * @return 資格(マスタ)コード
	 */
	public String getCertiCode() {
		return certiCode;
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
	 * @return ownedId
	 */
	public int getOwnedId() {
		return ownedId;
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
