package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Copyright 2020 FUJITSU SOCIAL SCIENCE LABORATORY LIMITED<br>
 * システム名：自己紹介システム<br>
 * クラス概要：<br>
 *  データベースへの接続を管理するクラス<br>
 */
public class ConnectionManager {

	/** データベース接続URL */
	private static final String URL = "jdbc:postgresql://localhost:5432/orion";
	//private static final String URL = "jdbc:postgresql://192.168.10.10:5432/orion";
	/** ユーザー名 */
	private static final String USER = "postgres";
	/** パスワード */
	private static final String PASSWORD = "sgwaitec";

	/**
	 * データベースへの接続オブジェクトを取得
	 * @return データベースへの接続オブジェクト
	 * @throws SQLException
	 */
	public static Connection getConnection() throws SQLException {

		Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
		connection.setAutoCommit(false);
		return connection;
	}

}
