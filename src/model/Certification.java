package model;

import java.io.Serializable;

/**
 * Copyright 2020 FUJITSU SOCIAL SCIENCE LABORATORY LIMITED<br>
 * システム名：自己紹介システム<br>
 * クラス概要：資格(マスタ・その他)のJavaBeansクラス<br>
 * 変数名等の『certi』は「certification」の意味です（長いので省略）
 */


public class Certification implements Serializable {

	private String certiId,employeeNumber,certiCode,certiGenre,certiName,certiDate;

	/**
	 * コンストラクタ(マスタ資格用)
	 * @param certiId 保有資格ID
	 * @param employeeNumber 従業員番号
	 * @param certiCode 資格コード
	 * @param certiDate 認定日
	 */
	public Certification(String certiId,String employeeNumber,String certiCode,String certiDate) {
		this.certiId=certiId;
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
	public Certification(String certiId,String employeeNumber,String certiCode,String certiName,String certiDate) {
		this.certiId=certiId;
		this.employeeNumber=employeeNumber;
		this.certiCode=certiCode;
		this.certiName=certiName;
		this.certiDate=certiDate;
	}

	/**
	 * 保有資格(マスタ・その他)IDを取得
	 * @return 保有資格(マスタ・その他)ID
	 */
	public String getCertiId() {
		return certiId;
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

}
