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
	/**
	 * コンストラクタ(一覧取得/マスタ資格登録用){資格ジャンル/資格名で共用}
	 * @param certiCode 資格ジャンルコード/資格コード
	 * @param certiName 資格ジャンル名/資格名
	 */
	public Certification(String certiCode,String certiName) {
		this.certiCode=certiCode;
		this.certiName=certiName;
	}


	/**
	 * コンストラクタ(マスタ資格の表示/編集用)[7要素]
	 * @param ownedId 保有資格ID
	 * @param employeeNumber 従業員番号
	 * @param certiCode 資格ジャンルコード
	 * @param certiGenre 資格ジャンル名
	 * @param masterCode 資格コード
	 * @param certiName 資格名
	 * @param certiDate 取得日
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
	 * コンストラクタ(その他資格の表示/編集用)[6要素]
	 * @param ownedId 保有その他資格ID
	 * @param employeeNumber 従業員番号
	 * @param certiCode 資格ジャンルコード
	 * @param certiGenre 資格ジャンル名
	 * @param certiDate 取得日
	 * @param certiName その他資格名
	 */
	public Certification(int ownedId,String employeeNumber,String certiCode,String certiGenre,String certiDate,String certiName) {
		this.ownedId=ownedId;
		this.employeeNumber=employeeNumber;
		this.certiCode=certiCode;
		this.certiGenre=certiGenre;
		this.certiDate=certiDate;
		this.certiName=certiName;
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
	 * 資格ジャンルコード/マスタ資格コードを取得
	 * @return （資格ジャンルかマスタ資格の）コード
	 */
	public String getCertiCode() {
		return certiCode;
	}

	/**
	 * マスタ資格コードを取得（IDによる表示・編集用）(後から追加)
	 * @return マスタ資格コード
	 */
	public String getMasterCode() {
		return masterCode;
	}

	/**
	 * 資格ジャンル名/資格名を取得
	 * @return (資格ジャンルか資格の)名称
	 */
	public String getCertiName() {
		return certiName;
	}

	/**
	 * 取得日を取得
	 * @return 取得日
	 */
	public String getCertiDate() {
		return certiDate;
	}

	/**
	 * 資格ジャンル名を取得(表示・編集時用)
	 * @return certiGenre
	 */
	public String getCertiGenre() {
		return certiGenre;
	}

	/**
	 * 従業員番号を設定
	 * @param employeeNumber 従業員番号
	 */
	public void setEmployeeNumber(String employeeNumber) {
		this.employeeNumber = employeeNumber;
	}

	/**
	 * 資格ジャンルコード/マスタ資格コードを設定
	 * @param certiCode 資格ジャンルコード/マスタ資格コード
	 */
	public void setCertiCode(String certiCode) {
		this.certiCode = certiCode;
	}

	/**
	 * 資格ジャンル名を設定(表示・編集時用)
	 * @param certiGenre 資格ジャンル名
	 */
	public void setCertiGenre(String certiGenre) {
		this.certiGenre = certiGenre;
	}


	/**
	 * マスタ資格コードを設定(表示・編集時用)
	 * @param masterCode マスタ資格コード
	 */
	public void setMasterCode(String masterCode) {
		this.masterCode = masterCode;
	}

	/**
	 * 資格ジャンル名/資格名を設定
	 * @param certiName 資格ジャンル名/資格名
	 */
	public void setCertiName(String certiName) {
		this.certiName = certiName;
	}

	/**
	 * 取得日を設定
	 * @param certiDate 取得日
	 */
	public void setCertiDate(String certiDate) {
		this.certiDate = certiDate;
	}

	/**
	 * 保有IDを設定
	 * @param ownedId 保有ID
	 */
	public void setOwnedId(int ownedId) {
		this.ownedId = ownedId;
	}

}
