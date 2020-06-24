package EmployeeDaoTest;

import static org.junit.Assert.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.junit.FixMethodOrder;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.Timeout;
import org.junit.runners.MethodSorters;

import ConnectionManagerForTest.ConnectionManagerTest;
import dao.EmployeeDAO;
import model.Employee;


/**
 * Copyright 2020 FUJITSU SOCIAL SCIENCE LABORATORY LIMITED<br>
 * システム名：自己紹介システム<br>
 * クラス概要：<br>
 * EmployeeDAOの一覧/登録/更新/削除の単体テストクラス<br>
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)

/*
 * 修正内容まとめ
 * 2020/6/15 既存テスト所属追加対応
 * 2020/6/15 資格所持数取得テスト追加
 * 2020/6/23 検索テスト追加
 *
 */
public class EmployeeDAOTest {

	@Rule
	 public Timeout globalTimeout = Timeout.seconds(5);

    /**
     * 従業員一覧の登録テスト(正常)
     *
     * @throws SQLException
     */
    @Test
    public void test0_registerOneEmployee() throws SQLException {
        Connection connection_register = null;

        //登録テスト実行
        try {
            connection_register = ConnectionManagerTest.getConnection();
            EmployeeDAO empDAO = new EmployeeDAO(connection_register);
            // 6/15 所属追加
            empDAO.registerOneEmployee("666666", "０１２３４５６７８９０１２３４５６７８９０１２３４５６７８９", "０１２３４５６７８９０１２３４５６７８９０１２３４５６７８９０１２３４５６７８９０１２３４５６７８９０１２３４５６７８９０１２３４５６７８９０１２３４５６７８９０１２３４５６７８９０１２３４５６７８９", "部署1");
            connection_register.commit();				//2020.05.28 10→30桁に変更
        } catch (SQLException e) {
            connection_register.rollback();
            e.printStackTrace();
            throw e;
        } finally {
            try {
                if (connection_register != null) {
                    connection_register.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        //登録テストで登録した従業員情報の取得
        Employee emp = null;
        Connection connection_findOne = null;
        FindOneEmployee findOneEmployeeTest = new FindOneEmployee();
        int actualEmployment;
        try {
            connection_findOne = ConnectionManagerTest.getConnection();
            String employeeNumber = "666666";
            emp = findOneEmployeeTest.findOneEmployee(employeeNumber, connection_findOne);
            // 2020/6/24 追加
            actualEmployment = new FindOneEmployee().findEmployment("666666", connection_findOne);
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        } finally {
            try {
                if (connection_findOne != null) {
                    connection_findOne.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        // ------------
        // 結果チェック
        // ------------
        final String expectedEmployeeName = "０１２３４５６７８９０１２３４５６７８９０１２３４５６７８９";			//2020.05.28 10→30桁に変更
        final String actualEmployeeName = emp.getEmployeeName().trim();
        final String expectedEmployeeComment = "０１２３４５６７８９０１２３４５６７８９０１２３４５６７８９０１２３４５６７８９０１２３４５６７８９０１２３４５６７８９０１２３４５６７８９０１２３４５６７８９０１２３４５６７８９０１２３４５６７８９";
        final String actualEmployeeComment = emp.getEmployeeProfile().trim();
        // 2020/6/15 追加
        final String expectedEmployeeDeployment = "部署1";
        final String actualEmployeeDeployement = emp.getEmployeeDeployment().trim();
        // 2020/6/24 追加
        final int exceptedEmployment = 1;

        assertEquals(expectedEmployeeName, actualEmployeeName);
        assertEquals(expectedEmployeeComment, actualEmployeeComment);
        // 2020/6/15 追加
        assertEquals(expectedEmployeeDeployment, actualEmployeeDeployement);
        // 2020/6/24 追加
        assertEquals(exceptedEmployment, actualEmployment);
    }

    /**
     * 従業員一覧(全員)の取得テスト(正常)
     *
     * @throws SQLException
     */
    @Test
    public void test1_findAllEmployee() throws SQLException {
        Connection connection = null;

        //テスト実行
        try {
            connection = ConnectionManagerTest.getConnection();
            EmployeeDAO empDAO = new EmployeeDAO(connection);
            List<Employee> empList = null;
            empList = empDAO.findAllEmployee();

            // ------------
            // 結果チェック
            // ------------
            assertNotNull(empList);
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

        }
    }

    /**
     * 従業員一覧(一人)の取得テスト(正常)
     *
     * @throws SQLException
     */
    @Test
    public void test2_findOneEmployee() throws SQLException {
        Connection connection = null;
        Employee emp = null;

        //テスト実行
        try {
            connection = ConnectionManagerTest.getConnection();
            EmployeeDAO empDAO = new EmployeeDAO(connection);
            emp = empDAO.findOneEmployee("666666");
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        // ------------
        // 結果チェック
        // ------------
        final String expected = "０１２３４５６７８９０１２３４５６７８９０１２３４５６７８９";		//2020.05.28 10→30桁に変更
        final String actual = emp.getEmployeeName().trim();

        assertEquals(expected, actual);
    }

    /**
     * 従業員一覧の登録テスト(異常系：従業員番号がnull)
     *
     * @throws SQLException
     */
    @Test
    public void test31_registerOneEmployeeIDNullError() throws SQLException {
        Connection connection = null;
        String actual = "";
        String expected = "ERROR: 列\"employee_number\"内のNULL値はNOT NULL制約違反です" +
                "\n  詳細: 失敗した行は(null, 氏名, プロフィール, 部署1, null, 1)を含みます";						//2020.05.27 エラーメッセージを日本語に修正

        //テスト実行
        try {
            connection = ConnectionManagerTest.getConnection();
            EmployeeDAO empDAO = new EmployeeDAO(connection);
            // 6/15 所属追加
            empDAO.registerOneEmployee(null, "氏名", "プロフィール", "部署1");

            //Exceptionが発生しなければ失敗
            fail();
            connection.commit();
        } catch (SQLException e) {
            connection.rollback();
            actual = e.getMessage();
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        // ------------
        // 結果チェック
        // ------------
        assertEquals(expected, actual);
    }

    /**
     * 従業員一覧の登録テスト(異常系：従業員番号6桁より大きい)
     *
     * @throws SQLException
     */
    @Test
    public void test3_registerOneEmployeeIDOverError() throws SQLException {
        Connection connection = null;
        String actual = "";
        String expected = "ERROR: 値は型character varying(6)としては長すぎます";		//2020.05.27 エラーメッセージを日本語に修正

        //テスト実行
        try {
            connection = ConnectionManagerTest.getConnection();
            EmployeeDAO empDAO = new EmployeeDAO(connection);
            // 6/15 所属追加
            empDAO.registerOneEmployee("0123456", "氏名", "プロフィール", "部署1");

            //Exceptionが発生しなければ失敗
            fail();
            connection.commit();
        } catch (SQLException e) {
            connection.rollback();
            actual = e.getMessage();
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        // ------------
        // 結果チェック
        // ------------
        assertEquals(expected, actual);
    }

    /**
     * 従業員一覧の登録テスト(異常系：氏名30桁より大きい)
     *
     * @throws SQLException
     */
    @Test
    public void test4_registerOneEmployeeNameOverError() throws SQLException {
        Connection connection = null;
        String actual = "";
        String expected = "ERROR: 値は型character varying(30)としては長すぎます";			//2020.05.27 エラーメッセージを日本語に修正、varying(10)→varying(30)に変更

        //テスト実行
        try {
            connection = ConnectionManagerTest.getConnection();
            EmployeeDAO empDAO = new EmployeeDAO(connection);
            // 6/15 所属追加
            empDAO.registerOneEmployee("666666", "０１２３４５６７８９０１２３４５６７８９０１２３４５６７８９０", "プロフィール", "部署1");			//2020.05.28 11→31桁に変更

            //Exceptionが発生しなければ失敗
            fail();
            connection.commit();
        } catch (SQLException e) {
            connection.rollback();
            actual = e.getMessage();
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        // ------------
        // 結果チェック
        // ------------
        assertEquals(expected, actual);
    }

    /**
     * 従業員一覧の登録テスト(異常系：プロフィール100桁より大きい)
     *
     * @throws SQLException
     */
    @Test
    public void test5_registerOneEmployeeProfileOverError() throws SQLException {
        Connection connection = null;
        String actual = "";
        String expected = "ERROR: 値は型character varying(100)としては長すぎます";			//2020.05.27 エラーメッセージを日本語に修正

        //テスト実行
        try {
            connection = ConnectionManagerTest.getConnection();
            EmployeeDAO empDAO = new EmployeeDAO(connection);
            // 6/15　所属追加
            empDAO.registerOneEmployee("666666", "氏名", "０１２３４５６７８９０１２３４５６７８９０１２３４５６７８９０１２３４５６７８９０１２３４５６７８９０１２３４５６７８９０１２３４５６７８９０１２３４５６７８９０１２３４５６７８９０１２３４５６７８９０", "部署1");
            //Exceptionが発生しなければ失敗
            fail();
            connection.commit();
        } catch (SQLException e) {
            connection.rollback();
            actual = e.getMessage();
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        // ------------
        // 結果チェック
        // ------------
        assertEquals(expected, actual);
    }

    /**
     * 従業員一覧の更新テスト(正常)
     *
     * @throws SQLException
     */
    @Test
    public void test6_updateOneEmployee() throws SQLException {
        Connection connection_update = null;

        //更新テスト実行
        try {
            connection_update = ConnectionManagerTest.getConnection();
            EmployeeDAO empDAO = new EmployeeDAO(connection_update);
            // 2020/6/16 所属追加
            empDAO.updateOneEmployee("666666", "９８７６５４３２１０９８７６５４３２１０９８７６５４３２１０", "９８７６５４３２１０９８７６５４３２１０９８７６５４３２１０９８７６５４３２１０９８７６５４３２１０９８７６５４３２１０９８７６５４３２１０９８７６５４３２１０９８７６５４３２１０９８７６５４３２１０","更新部署");
            connection_update.commit();				//2020.05.28 10→30桁に変更
        } catch (SQLException e) {
            connection_update.rollback();
            e.printStackTrace();
            throw e;
        } finally {
            try {
                if (connection_update != null) {
                    connection_update.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        //更新テストで更新したデータの従業員情報の取得
        Employee emp = null;
        Connection connection_findOne = null;
        try {
            connection_findOne = ConnectionManagerTest.getConnection();
            EmployeeDAO empDAO = new EmployeeDAO(connection_findOne);
            emp = empDAO.findOneEmployee("666666");
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        } finally {
            try {
                if (connection_findOne != null) {
                    connection_findOne.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        // ------------
        // 結果チェック
        // ------------
        final String expectedEmployeeName = "９８７６５４３２１０９８７６５４３２１０９８７６５４３２１０";			//2020.05.28 10→30桁に変更
        final String actualEmployeeName = emp.getEmployeeName().trim();
        final String expectedEmployeeComment = "９８７６５４３２１０９８７６５４３２１０９８７６５４３２１０９８７６５４３２１０９８７６５４３２１０９８７６５４３２１０９８７６５４３２１０９８７６５４３２１０９８７６５４３２１０９８７６５４３２１０";
        final String actualEmployeeComment = emp.getEmployeeProfile().trim();
        // 2020/6/16 追加
        final String expectedEmployeeDeployment = "更新部署";
        final String actualEmployeeDeployment = emp.getEmployeeDeployment().trim();

        assertEquals(expectedEmployeeName, actualEmployeeName);
        assertEquals(expectedEmployeeComment, actualEmployeeComment);
        // 2020/6/16 追加
        assertEquals(expectedEmployeeDeployment,actualEmployeeDeployment);
    }

    /**
     * 従業員一覧の更新テスト(異常系：氏名30桁より大きい)
     *
     * @throws SQLException
     */
    @Test
    public void test7_updateOneEmployeeNameOverError() throws SQLException {
        Connection connection = null;
        String actual = "";
        String expected = "ERROR: 値は型character varying(30)としては長すぎます";			//2020.05.27 エラーメッセージを日本語に修正、varying(10)→varying(30)に変更

        //テスト実行
        try {
            connection = ConnectionManagerTest.getConnection();
            EmployeeDAO empDAO = new EmployeeDAO(connection);
            // 2020/6/16 所属追加
            empDAO.updateOneEmployee("686033", "０１２３４５６７８９０１２３４５６７８９０１２３４５６７８９０", "テスト", "部署1");			//2020.05.28 11→31桁に変更
            //Exceptionが発生しなければ失敗
            fail();
            connection.commit();
        } catch (SQLException e) {
            connection.rollback();
            actual = e.getMessage();
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        // ------------
        // 結果チェック
        // ------------
        assertEquals(expected, actual);
    }

    /**
     * 従業員一覧の更新テスト(異常系：プロフィール100桁より大きい)
     * @throws SQLException
     */
    @Test
    public void test8_updateOneEmployeeProfileOverError() throws SQLException {
        Connection connection = null;
        String actual = "";
        String expected = "ERROR: 値は型character varying(100)としては長すぎます";			//2020.05.27 エラーメッセージを日本語に修正

        //テスト実行
        try {
            connection = ConnectionManagerTest.getConnection();
            EmployeeDAO empDAO = new EmployeeDAO(connection);
            // 2020/6/16 所属追加
            empDAO.updateOneEmployee("686033", "テスト", "０１２３４５６７８９０１２３４５６７８９０１２３４５６７８９０１２３４５６７８９０１２３４５６７８９０１２３４５６７８９０１２３４５６７８９０１２３４５６７８９０１２３４５６７８９０１２３４５６７８９０", "部署1");
            //Exceptionが発生しなければ失敗
            fail();
            connection.commit();
        } catch (SQLException e) {
            connection.rollback();
            actual = e.getMessage();
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        // ------------
        // 結果チェック
        // ------------
        assertEquals(expected, actual);
    }

    /**
     * 従業員一覧の削除テスト(正常)
     * @throws SQLException
     */
    @Test
    public void test9_deleteOneEmployee() throws SQLException {
        Connection connection_delete = null;
        Connection connection_findEmployment = null;

        //削除テスト実行
        try {
            connection_delete = ConnectionManagerTest.getConnection();
            EmployeeDAO empDAO = new EmployeeDAO(connection_delete);
            empDAO.deleteOneEmployee("666666");
            connection_delete.commit();
        } catch (SQLException e) {
            connection_delete.rollback();
            e.printStackTrace();
            throw e;
        } finally {
            try {
                if (connection_delete != null) {
                    connection_delete.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        //削除テストで削除した従業員データの取得
        try {
            connection_findEmployment = ConnectionManagerTest.getConnection();
            int actual = new FindOneEmployee().findEmployment("666666",connection_findEmployment);
            int excepted = 0;
            assertEquals(actual,excepted);
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        } finally {
            try {
                if (connection_findEmployment != null) {
                    connection_findEmployment.close();
                }
                //テストデータ削除
                new DeleteTestData().deleteTestData();
            } catch (SQLException e) {
                e.printStackTrace();
            }

        }
    }

    /**
     * 従業員一覧検索（所属）テスト（正常）
     *
     * @throws SQLException
     */
    @Test
    public void test10_searchEmployeeDeploymentSearch() throws SQLException {
    	Connection connection = null;

    	//テスト実行
    	try {

    		connection = ConnectionManagerTest.getConnection();
    		EmployeeDAO empDAO = new EmployeeDAO(connection);
    		List<Employee> empList = null;
    		empList = empDAO.searchEmployee("部署1",null,null,"","ジャンルを選択してください",null);

    		//-----------------
    		// 結果チェック
    		//-----------------

    		final String excepted = "部署1";
    		for(Employee emp : empList) {
    			final String actual = emp.getEmployeeDeployment();
    			assertEquals(excepted, actual);
    		}

    	}catch(SQLException e) {
    		e.printStackTrace();
    		throw e;
    	}finally {
    		try {
    			if(connection != null) {
    				connection.close();
    			}
    		}catch(SQLException e) {
    			e.printStackTrace();
    		}
    	}
    }


    /**
     * 資格ジャンル検索テスト（正常）
     *
     * @throws SQLException
     */
	@Test
    public void test11_searchEmployeeCertificationGenreSearch() throws SQLException {
    	Connection connection = null;
		List<Employee> empList = null;

		//テストデータ追加
		new FindOneEmployee().registerInfo();

    	//テスト実行
    	try {

    		connection = ConnectionManagerTest.getConnection();
    		EmployeeDAO empDAO = new EmployeeDAO(connection);
    		empList = empDAO.searchEmployee("所属を選択してください","SAM", null,"","ジャンルを選択してください",null);

    		//-----------------
    		// 結果チェック
    		//-----------------

        	//抽出された従業員の全員が対象の資格を持っているか確認
        	for(Employee emp : empList) {
        		int hasTargetCertification = 0;
        		String employeeNumber = emp.getEmployeeNumber();
        		List<String> allCertificationGenreList = new ArrayList<>();
        		List<String> certificationCodeList = new FindOneEmployee().getCertificationCodeList(employeeNumber, connection);
        		List<String> otherGenreList = new FindOneEmployee().getOtherGenreCodeList(employeeNumber, connection);

        		allCertificationGenreList.addAll(certificationCodeList);
        		allCertificationGenreList.addAll(otherGenreList);

        		//対象の従業員が持っているすべての資格のうち該当ジャンルの資格があればフラグを+1
        		for(String code : allCertificationGenreList) {
        			if(code.startsWith("SAM")) {		//資格コードは資格ジャンルコード+数字3桁で構成されているため
        				hasTargetCertification++;
        			}
        		}
            	assertTrue(hasTargetCertification >= 1);
        	}

    	}catch(SQLException e) {
    		e.printStackTrace();
    		throw e;
    	}finally {
    		try {
    			if(connection != null) {
    				connection.close();
    			}
    		}catch(SQLException e) {
    			e.printStackTrace();
    		}
    	}
    }

	/**
	 * マスター登録有資格名検索テスト（正常）
	 *
	 * @throws SQLException
	 */
    @Test
    public void test12_searchEmployeeCertificationNameSearch() throws SQLException {
    	Connection connection = null;
		List<Employee> empList = null;

		//テストデータ追加
		new FindOneEmployee().registerInfo();

    	//テスト実行
    	try {

    		connection = ConnectionManagerTest.getConnection();
    		EmployeeDAO empDAO = new EmployeeDAO(connection);
    		empList = empDAO.searchEmployee("所属を選択してください", null, "SAM001","","ジャンルを選択してください",null);

        	//-----------------
    		// 結果チェック
    		//-----------------

    		//抽出された従業員の全員が対象の資格を持っているか確認
        	for(Employee emp : empList) {
        		int hasTargetCertification = 0;
        		String employeeNumber = emp.getEmployeeNumber();
        		List<String> certificationCodeList = new FindOneEmployee().getCertificationCodeList(employeeNumber, connection);

        		//対象の従業員が持っているすべての資格のうち該当の資格があればフラグを+1
        		for(String code : certificationCodeList) {
        			if(code.equals("SAM001")) {
        				hasTargetCertification++;
        			}
        		}
            	assertTrue(hasTargetCertification >= 1);
        	}


    	}catch(SQLException e) {
    		e.printStackTrace();
    		throw e;
    	}finally {
    		try {
    			if(connection != null) {
    				connection.close();
    			}
    		}catch(SQLException e) {
    			e.printStackTrace();
    		}
    	}

    }

    /**
     * その他資格名検索テスト（正常）
     *
     * @throws SQLException
     */
    @Test
    public void test13_searchEmployeeOtherCertificationNameSearch() throws SQLException {

    	Connection connection = null;
		List<Employee> empList = null;

		//テストデータ追加
		new FindOneEmployee().registerInfo();

    	//テスト実行
    	try {

    		connection = ConnectionManagerTest.getConnection();
    		EmployeeDAO empDAO = new EmployeeDAO(connection);
    		empList = empDAO.searchEmployee("所属を選択してください", null, null,"その他資格","ジャンルを選択してください",null);

        	//-----------------
    		// 結果チェック
    		//-----------------

    		//抽出された従業員の全員が対象の資格を持っているか確認
        	for(Employee emp : empList) {
            	int hasTargetCertification = 0;
        		String employeeNumber = emp.getEmployeeNumber();
        		List<String> otherNameList = new FindOneEmployee().getOtherNameList(employeeNumber, connection);

        		//対象の従業員が持っているすべての資格のうち該当の資格があればフラグを+1
        		for(String name : otherNameList) {
        			if(name.matches(".*" + "その他資格" + ".*" )) {
        				hasTargetCertification++;
        			}
        		}
            	assertTrue(hasTargetCertification >= 1);
        	}


    	}catch(SQLException e) {
    		e.printStackTrace();
    		throw e;
    	}finally {
    		try {
    			if(connection != null) {
    				connection.close();
    			}
    		}catch(SQLException e) {
    			e.printStackTrace();
    		}
    	}

    }

    /**
     * スキルジャンル検索テスト（正常）
     *
     * @throws SQLException
     */
    @Test
    public void test14_searchEmployeeSkillGenreSearch() throws SQLException {

    	Connection connection = null;
		List<Employee> empList = null;

		//テストデータ追加
		new FindOneEmployee().registerInfo();

    	//テスト実行
    	try {

    		connection = ConnectionManagerTest.getConnection();
    		EmployeeDAO empDAO = new EmployeeDAO(connection);
    		empList = empDAO.searchEmployee("所属を選択してください", null, null,"","EXS",null);

        	//-----------------
    		// 結果チェック
    		//-----------------

    		//抽出された従業員の全員が対象のスキルを持っているか確認
        	for(Employee emp : empList) {
        		int hasTargetSkill = 0;
        		String employeeNumber = emp.getEmployeeNumber();
        		List<String> skillGenreList = new FindOneEmployee().getSkillGenreList(employeeNumber, connection);

        		//対象の従業員が持っているすべての資格のうち該当の資格があればフラグを+1
        		for(String code : skillGenreList) {
        			if(code.equals("EXS")) {
        				hasTargetSkill++;
        			}
        		}
            	assertTrue(hasTargetSkill >= 1);
        	}


    	}catch(SQLException e) {
    		e.printStackTrace();
    		throw e;
    	}finally {
    		try {
    			if(connection != null) {
    				connection.close();
    			}
    		}catch(SQLException e) {
    			e.printStackTrace();
    		}
    	}

    }


    /**
     * スキル名検索テスト（正常）
     *
     * @throws SQLException
     */
    @Test
    public void test15_searchEmployeeSkillNameSearch() throws SQLException {

    	Connection connection = null;
		List<Employee> empList = null;

		//テストデータ追加
		new FindOneEmployee().registerInfo();

    	//テスト実行
    	try {

    		connection = ConnectionManagerTest.getConnection();
    		EmployeeDAO empDAO = new EmployeeDAO(connection);
    		empList = empDAO.searchEmployee("所属を選択してください", null, null,"","ジャンルを選択してください", "スキルテスト");

        	//-----------------
    		// 結果チェック
    		//-----------------

    		//抽出された従業員の全員が対象のスキルを持っているか確認
        	for(Employee emp : empList) {
        		int hasTargetSkill = 0;
        		String employeeNumber = emp.getEmployeeNumber();
        		List<String> skillNameList = new FindOneEmployee().getSkillNameList(employeeNumber, connection);

        		//対象の従業員が持っているすべての資格のうち該当の資格があればフラグを+1
        		for(String name : skillNameList) {
        			if(name.matches(".*" + "スキルテスト" + ".*" )) {
        				hasTargetSkill++;
        			}
        		}
            	assertTrue(hasTargetSkill >= 1);
        	}


    	}catch(SQLException e) {
    		e.printStackTrace();
    		throw e;
    	}finally {
    		try {
    			if(connection != null) {
    				connection.close();
    			}
    		}catch(SQLException e) {
    			e.printStackTrace();
    		}
    	}

    }

    /**
     * 全項目検索テスト（正常）
     *
     * @throws SQLException
     */
    @Test
    public void test16_searchEmployeeALLItemSearch() throws SQLException {

    	Connection connection = null;

    	//テスト実行
    	try {

    		connection = ConnectionManagerTest.getConnection();
    		EmployeeDAO empDAO = new EmployeeDAO(connection);
    		List<Employee> empList = null;
    		empList = empDAO.searchEmployee("部署1", "SAM", "SAM001", "その他資格", "EXS", "スキルテスト");


    		//-------------------
    		// 結果チェック
    		//-------------------


    		//抽出された従業員全員の部署確認
    		final String excepted = "部署1";
    		for(Employee emp : empList) {
    			final String actual = emp.getEmployeeDeployment();
    			assertEquals(excepted, actual);
    		}


    		//抽出された従業員の全員が対象の資格ジャンルの資格を持っているか確認
        	for(Employee emp : empList) {
        		int hasTargetCertification = 0;
        		String employeeNumber = emp.getEmployeeNumber();
        		List<String> allCertificationGenreList = new ArrayList<>();
        		List<String> certificationCodeList = new FindOneEmployee().getCertificationCodeList(employeeNumber, connection);
        		List<String> otherGenreList = new FindOneEmployee().getOtherGenreCodeList(employeeNumber, connection);

        		allCertificationGenreList.addAll(certificationCodeList);
        		allCertificationGenreList.addAll(otherGenreList);

        		//対象の従業員が持っているすべての資格のうち該当ジャンルの資格があればフラグを+1
        		for(String code : allCertificationGenreList) {
        			if(code.startsWith("SAM")) {		//資格コードは資格ジャンルコード+数字3桁で構成されているため
        				hasTargetCertification++;
        			}
        		}
            	assertTrue(hasTargetCertification >= 1);
        	}


        	//抽出された従業員の全員が対象の資格名のマスター登録有資格を持っているか確認
        	for(Employee emp : empList) {
        		int hasTargetCertification = 0;
        		String employeeNumber = emp.getEmployeeNumber();
        		List<String> certificationCodeList = new FindOneEmployee().getCertificationCodeList(employeeNumber, connection);

        		//対象の従業員が持っているすべての資格のうち該当の資格があればフラグを+1
        		for(String code : certificationCodeList) {
        			if(code.equals("SAM001")) {
        				hasTargetCertification++;
        			}
        		}
            	assertTrue(hasTargetCertification >= 1);
        	}


        	//抽出された従業員の全員が対象の資格名のその他資格を持っているか確認
        	for(Employee emp : empList) {
            	int hasTargetCertification = 0;
        		String employeeNumber = emp.getEmployeeNumber();
        		List<String> otherNameList = new FindOneEmployee().getOtherNameList(employeeNumber, connection);

        		//対象の従業員が持っているすべての資格のうち該当の資格があればフラグを+1
        		for(String name : otherNameList) {
        			if(name.matches(".*" + "その他資格" + ".*" )) {
        				hasTargetCertification++;
        			}
        		}
            	assertTrue(hasTargetCertification >= 1);
        	}



    		//抽出された従業員の全員が対象のスキルジャンルのスキルを持っているか確認
        	for(Employee emp : empList) {
        		int hasTargetSkill = 0;
        		String employeeNumber = emp.getEmployeeNumber();
        		List<String> skillGenreList = new FindOneEmployee().getSkillGenreList(employeeNumber, connection);

        		//対象の従業員が持っているすべての資格のうち該当の資格があればフラグを+1
        		for(String code : skillGenreList) {
        			if(code.equals("EXS")) {
        				hasTargetSkill++;
        			}
        		}
            	assertTrue(hasTargetSkill >= 1);
        	}


        	//抽出された従業員の全員が対象のスキル名のスキルを持っているか確認
        	for(Employee emp : empList) {
        		int hasTargetSkill = 0;
        		String employeeNumber = emp.getEmployeeNumber();
        		List<String> skillNameList = new FindOneEmployee().getSkillNameList(employeeNumber, connection);

        		//対象の従業員が持っているすべての資格のうち該当の資格があればフラグを+1
        		for(String name : skillNameList) {
        			if(name.matches(".*" + "スキルテスト" + ".*" )) {
        				hasTargetSkill++;
        			}
        		}
            	assertTrue(hasTargetSkill >= 1);
        	}

    	}catch(SQLException e) {
    		e.printStackTrace();
    		throw e;
    	}finally {
    		try {
    			if(connection != null) {
    				connection.close();
    			}
    		}catch(SQLException e) {
    			e.printStackTrace();
    		}
    	}



    }
}

/**
 * テスト用の従業員情報取得クラス(1人)
 */
class FindOneEmployee {

    /**
     * 従業員取得
     * @param employeeNumber 従業員番号
     * @param connection DB接続用コネクション
     * @return 従業員データ
     * @throws SQLException
     */
    public Employee findOneEmployee(String employeeNumber, Connection connection) throws SQLException {

        Employee emp = null;
        String sql = "SELECT * FROM employee WHERE employee_number=?";

        // -------------------
        // SQL発行
        // -------------------
        try (PreparedStatement pStmt = connection.prepareStatement(sql)) {

            pStmt.setString(1, employeeNumber);

            try (ResultSet resultSet = pStmt.executeQuery()) {
                // -------------------
                // 値の取得
                // -------------------
                if (resultSet.next()) {

                    // ResultSetから各値を取得
                    String employee_number = resultSet.getString("employee_number");
                    String employee_name = resultSet.getString("employee_name");
                    String employee_profile = resultSet.getString("employee_profile");
                    // 2020/6/15 追加
                    String employee_deployment = resultSet.getString("employee_deployment");

                    // 結果を格納
                    // 6/15 所属追加
                    emp = new Employee(employee_number, employee_name, employee_profile, employee_deployment);
                }
            }

            return emp;
        }

    }

    /**
     * テストデータ追加
     *
     * @throws SQLException
     */
    public void registerInfo() throws SQLException {

    	Connection connection = ConnectionManagerTest.getConnection();

    	//-----------------------------------------------
    	//マスター登録有資格登録
    	//-----------------------------------------------
    	final String masterSql = 	"INSERT INTO owned_certification (employee_number, certification_code, certification_date)\r\n" +
    								"VALUES('666666', 'SAM001', '2000/01')";
    	try(PreparedStatement pStmt = connection.prepareStatement(masterSql)){

    		pStmt.executeUpdate();
    		connection.commit();
    	}

    	//------------------------------------------------
    	//その他資格登録
    	//------------------------------------------------
    	final String otherSql = 	"INSERT INTO owned_other_certification (employee_number, certification_genre_code, other_certification_date, other_certification_name)\r\n" +
    								"VALUES('666666', 'SAM', '2000/01','その他資格')";
    	try(PreparedStatement pStmt = connection.prepareStatement(otherSql)){

    		pStmt.executeUpdate();
    		connection.commit();
    	}

    	//-------------------------------------------------
    	//スキル登録
    	//-------------------------------------------------
    	final String skillSql = 	"INSERT INTO owned_skill (employee_number, skill_genre_code, skill_name)\r\n" +
    								"VALUES('666666', 'EXS','スキルテスト')";
    	try(PreparedStatement pStmt = connection.prepareStatement(skillSql)){

    		pStmt.executeUpdate();
    		connection.commit();
    	}
    }

    /**
     * 従業員の在職フラグ取得
     * @param employeeNumber　従業員番号
     * @param connection　DB接続オブジェクト
     * @return　従業員の在職フラグ　0 or 1
     * @throws SQLException
     */
    public int findEmployment(String employeeNumber, Connection connection) throws SQLException {

    	int employment = 0;
    	final String sql = "SELECT employment FROM employee WHERE employee_number = ?";

    	// -------------------
        // SQL発行
        // -------------------
        try (PreparedStatement pStmt = connection.prepareStatement(sql)) {

            pStmt.setString(1, employeeNumber);

            try (ResultSet resultSet = pStmt.executeQuery()) {
                // -------------------
                // 値の取得
                // -------------------
                if (resultSet.next()) {

                    // ResultSetから各値を取得
                    employment = resultSet.getInt("employment");
                }
            }
        }
        return employment;
    }

    /**
     * 従業員の所持資格コード一覧取得
     * @param employeeNumber　従業員番号
     * @param connection　DB接続オブジェクト
     * @return　所持資格コード一覧
     * @throws SQLException
     */
    public List<String> getCertificationCodeList(String employeeNumber, Connection connection) throws SQLException{

    	List<String> certificationCodeList = new ArrayList<>();
    	final String sql =		"SELECT certification_code FROM owned_certification\n" +
    							"WHERE employee_number = ?";

    	 try (PreparedStatement pStmt = connection.prepareStatement(sql)) {

             pStmt.setString(1, employeeNumber);

             try (ResultSet resultSet = pStmt.executeQuery()) {
                 // -------------------
                 // 値の取得
                 // -------------------
                 while(resultSet.next()) {

                	 String certificationCode = resultSet.getString("certification_code");
                	 certificationCodeList.add(certificationCode);

                 }
             }
    	 }

    	return certificationCodeList;
    }

    /**
     * その他資格ジャンルコード一覧取得
     *
     * @param employeeNumber　従業員番号
     * @param connection　DB接続オブジェクト
     * @return　その他資格ジャンル一覧
     * @throws SQLException
     */
    public List<String> getOtherGenreCodeList(String employeeNumber, Connection connection) throws SQLException{

    	List<String> otherGenreCodeList = new ArrayList<>();
    	final String sql =		"SELECT certification_genre_code FROM owned_other_certification\n" +
    							"WHERE employee_number = ?";

    	try (PreparedStatement pStmt = connection.prepareStatement(sql)) {

    		pStmt.setString(1, employeeNumber);

    		try (ResultSet resultSet = pStmt.executeQuery()) {
    			// -------------------
    			// 値の取得
    			// -------------------
    			while(resultSet.next()) {

    				String certificationGenreCode = resultSet.getString("certification_genre_code");
    				otherGenreCodeList.add(certificationGenreCode);

    			}
    		}
    	}
    	return otherGenreCodeList;
    }

    /**
     * その他資格名一覧取得
     *
     * @param employeeNumber　従業員番号
     * @param connection　DB接続オブジェクト
     * @return　その他資格名一覧
     * @throws SQLException
     */
    public List<String> getOtherNameList(String employeeNumber, Connection connection) throws SQLException{

    	List<String> otherNameList = new ArrayList<>();
    	final String sql =		"SELECT other_certification_name FROM owned_other_certification\n" +
    							"WHERE employee_number = ?";

    	try (PreparedStatement pStmt = connection.prepareStatement(sql)) {

    		pStmt.setString(1, employeeNumber);

    		try (ResultSet resultSet = pStmt.executeQuery()) {
    			// -------------------
    			// 値の取得
    			// -------------------
    			while(resultSet.next()) {

    				String otherName = resultSet.getString("other_certification_name");
    				otherNameList.add(otherName);

    			}
    		}
    	}
    	return otherNameList;
    }

    /**
     * スキルジャンルコード一覧取得
     *
     * @param employeeNumber　従業員番号
     * @param connection　DB接続オブジェクト
     * @return　スキルジャンルコード一覧
     * @throws SQLException
     */
    public List<String> getSkillGenreList(String employeeNumber, Connection connection) throws SQLException{

    	List<String> skillGenreList = new ArrayList<>();
    	final String sql =		"SELECT skill_genre_code FROM owned_skill\n" +
    							"WHERE employee_number = ?";

    	try (PreparedStatement pStmt = connection.prepareStatement(sql)) {

    		pStmt.setString(1, employeeNumber);

    		try (ResultSet resultSet = pStmt.executeQuery()) {
    			// -------------------
    			// 値の取得
    			// -------------------
    			while(resultSet.next()) {

    				String skillGenreCode = resultSet.getString("skill_genre_code");
    				skillGenreList.add(skillGenreCode);

    			}
    		}
    	}
    	return skillGenreList;
    }

    /**
     * スキル名一覧取得
     *
     * @param employeeNumber　従業員番号
     * @param connection　DB接続オブジェクト
     * @return　スキル名一覧
     * @throws SQLException
     */
    public List<String> getSkillNameList(String employeeNumber, Connection connection) throws SQLException{

    	List<String> skillNameList = new ArrayList<>();
    	final String sql =		"SELECT skill_name FROM owned_skill\n" +
    							"WHERE employee_number = ?";

    	try (PreparedStatement pStmt = connection.prepareStatement(sql)) {

    		pStmt.setString(1, employeeNumber);

    		try (ResultSet resultSet = pStmt.executeQuery()) {
    			// -------------------
    			// 値の取得
    			// -------------------
    			while(resultSet.next()) {

    				String skillName = resultSet.getString("skill_name");
    				skillNameList.add(skillName);

    			}
    		}
    	}
    	return skillNameList;
    }
}

//テストデータ削除
class DeleteTestData{
    void deleteTestData() {

    	Connection conn;
		try {
			conn = ConnectionManagerTest.getConnection();
			final String sql = "DELETE FROM employee WHERE employee_number = '666666'";
    		PreparedStatement ps = conn.prepareStatement(sql);
    		ps.executeUpdate();
    		conn.commit();
    	}catch(SQLException e) {
    		e.printStackTrace();
    	}
    }
}
