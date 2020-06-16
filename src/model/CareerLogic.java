package model;

import java.sql.Connection;
import java.util.List;

import javax.servlet.ServletException;

public class CareerLogic {

	/** データベースへの接続オブジェクト */
	Connection connection;

	/**
	 * コンストラクタ
	 * @param connection データベースへの接続オブジェクト
	 */
	public CareerLogic(Connection connection) {
		this.connection = connection;
	}

	/**
	 * 従業員一覧データを取得するメソッド
	 * @return 従業員一覧データ
	 * @throws ServletException
	 */
	public List<Career> getCareerList() throws ServletException{
		return null;
	}

	/**
	 * 従業員を登録するメソッド
	 * @param employeeNumber 従業員番号
	 * @param employeeName 氏名
	 * @param employeeProfile プロフィール
	 * @return 登録エラー有無（true：登録失敗（既に同じ従業員番号のデータが存在）, false：登録成功）
	 * @throws ServletException
	 */
	public void registerCareer() throws ServletException{

	}

	/**
	 * 従業員を更新するメソッド
	 * @param employeeNumber 従業員番号
	 * @param employeeName 氏名
	 * @param employeeProfile プロフィール
	 * @throws ServletException
	 */
	public void updateCareer() throws ServletException{

	}

	/**
	 * 従業員を削除するメソッド
	 * @param employeeNumber 従業員番号
	 * @throws ServletException
	 */
	public void deleteCareer() throws ServletException{

	}

}
