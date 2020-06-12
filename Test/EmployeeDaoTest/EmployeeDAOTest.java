package EmployeeDaoTest;

import static org.junit.Assert.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.junit.FixMethodOrder;
import org.junit.Test;
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
public class EmployeeDAOTest {

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
            empDAO.registerOneEmployee("666666", "０１２３４５６７８９０１２３４５６７８９０１２３４５６７８９", "０１２３４５６７８９０１２３４５６７８９０１２３４５６７８９０１２３４５６７８９０１２３４５６７８９０１２３４５６７８９０１２３４５６７８９０１２３４５６７８９０１２３４５６７８９０１２３４５６７８９");
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
        try {
            connection_findOne = ConnectionManagerTest.getConnection();
            String employeeNumber = "666666";
            emp = findOneEmployeeTest.findOneEmployee(employeeNumber, connection_findOne);
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

        assertEquals(expectedEmployeeName, actualEmployeeName);
        assertEquals(expectedEmployeeComment, actualEmployeeComment);
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
                "\n  詳細: 失敗した行は(null, 氏名, プロフィール)を含みます";						//2020.05.27 エラーメッセージを日本語に修正

        //テスト実行
        try {
            connection = ConnectionManagerTest.getConnection();
            EmployeeDAO empDAO = new EmployeeDAO(connection);
            empDAO.registerOneEmployee(null, "氏名", "プロフィール");

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
            empDAO.registerOneEmployee("0123456", "氏名", "プロフィール");

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
            empDAO.registerOneEmployee("666666", "０１２３４５６７８９０１２３４５６７８９０１２３４５６７８９０", "プロフィール");			//2020.05.28 11→31桁に変更

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
            empDAO.registerOneEmployee("666666", "氏名", "０１２３４５６７８９０１２３４５６７８９０１２３４５６７８９０１２３４５６７８９０１２３４５６７８９０１２３４５６７８９０１２３４５６７８９０１２３４５６７８９０１２３４５６７８９０１２３４５６７８９０");
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
            empDAO.updateOneEmployee("666666", "９８７６５４３２１０９８７６５４３２１０９８７６５４３２１０", "９８７６５４３２１０９８７６５４３２１０９８７６５４３２１０９８７６５４３２１０９８７６５４３２１０９８７６５４３２１０９８７６５４３２１０９８７６５４３２１０９８７６５４３２１０９８７６５４３２１０");
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

        assertEquals(expectedEmployeeName, actualEmployeeName);
        assertEquals(expectedEmployeeComment, actualEmployeeComment);
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
            empDAO.updateOneEmployee("686033", "０１２３４５６７８９０１２３４５６７８９０１２３４５６７８９０", "テスト");			//2020.05.28 11→31桁に変更
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
            empDAO.updateOneEmployee("686033", "テスト", "０１２３４５６７８９０１２３４５６７８９０１２３４５６７８９０１２３４５６７８９０１２３４５６７８９０１２３４５６７８９０１２３４５６７８９０１２３４５６７８９０１２３４５６７８９０１２３４５６７８９０");
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
        Connection connection_findOne = null;

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
            connection_findOne = ConnectionManagerTest.getConnection();
            EmployeeDAO empDAO = new EmployeeDAO(connection_findOne);
            Employee emp = null;
            emp = empDAO.findOneEmployee("666666");
            assertNull(emp);
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
        ;
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

                    // 結果を格納
                    emp = new Employee(employee_number, employee_name, employee_profile);
                }
            }

            return emp;
        }

    }
}
