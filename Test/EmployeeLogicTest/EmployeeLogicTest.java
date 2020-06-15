package EmployeeLogicTest;

import static org.junit.Assert.*;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;

import org.junit.Test;

import ConnectionManagerForTest.ConnectionManagerTest;
import dao.EmployeeDAO;
import mockit.Expectations;
import mockit.Mocked;
import model.Employee;
import model.EmployeeLogic;

/**
 * Copyright 2020 FUJITSU SOCIAL SCIENCE LABORATORY LIMITED<br>
 * システム名：自己紹介システム<br>
 * クラス概要：<br>
 * EmployeeLogicの一覧/登録/更新/削除の単体テストクラス<br>
 */
/*
 * 修正内容まとめ
 * 6/15 登録関係既存テスト所属追加対応
 */
public class EmployeeLogicTest {
    @Mocked
    EmployeeDAO employeeDAO;

    /**
     * 従業員の登録テスト(正常)
     *
     * @throws SQLException
     * @throws ServletException
     */
    @Test
    public void registerEmployee() throws SQLException, ServletException {
        Connection connection = null;
        Boolean hasError = false;

        //DAOのJMockit
        new Expectations() {
            {
            	// 2020/6/15 所属追加
                employeeDAO.registerOneEmployee("666660", "０１２３４５", "０１２３４５６７８９０１２３４５６７８９０１２３４５６７８９０１２３４５６７８９０１２３４５６７８９０１２３４５６７８９０１２３４５６７８９０１２３４５６７８９０１２３４５６７８９０１２３４５６７８９","部署1");
                employeeDAO.findOneEmployee("666660");
                result = null;
            }
        };

        //実行
        try {
            connection = ConnectionManagerTest.getConnection();
            EmployeeLogic employeeLogic = new EmployeeLogic(connection);
            // 2020/6/15 所属追加
            hasError = employeeLogic.registerEmployee("666660", "０１２３４５", "０１２３４５６７８９０１２３４５６７８９０１２３４５６７８９０１２３４５６７８９０１２３４５６７８９０１２３４５６７８９０１２３４５６７８９０１２３４５６７８９０１２３４５６７８９０１２３４５６７８９", "部署1");

            // ------------
            // 結果チェック
            // ------------
            assertFalse(hasError);
        } catch (ServletException e) {
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
     * 従業員(全員)の取得テスト(正常)
     *
     * @throws SQLException
     */
    @Test
    public void getEmployeeList() throws SQLException {
        Connection connection = null;

        //DAOのJMockit
        new Expectations() {{
            List<Employee> empList = new ArrayList<>();
            // 2020/6/15 所属追加
            Employee emp1 = new Employee("666661", "氏名1", "プロフィール1","部署1");
            Employee emp2 = new Employee("666662", "氏名2", "プロフィール2", "部署2");
            empList.add(emp1);
            empList.add(emp2);
            employeeDAO.findAllEmployee();
            result = empList;
        }};

        //実行
        try {
            connection = ConnectionManagerTest.getConnection();
            EmployeeLogic employeeLogic = new EmployeeLogic(connection);
            List<Employee> empList = employeeLogic.getEmployeeList();

            // ------------
            // 結果チェック
            // ------------
            assertNotNull(empList);
        } catch (SQLException | ServletException e) {
            e.printStackTrace();
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
     * 従業員(一人)の取得テスト(正常)
     *
     * @throws SQLException
     */
    @Test
    public void getEmployee() throws SQLException {
        Connection connection = null;

        //DAOのJMockit
        new Expectations() {{
        	// 2020/6/15 所属追加
            Employee emp = new Employee("666666", "氏名", "プロフィール", "部署1");
            employeeDAO.findOneEmployee("666666");
            result = emp;
        }};
        Employee emp = null;

        //実行
        try {
            connection = ConnectionManagerTest.getConnection();
            EmployeeLogic employeeLogic = new EmployeeLogic(connection);
            emp = employeeLogic.getEmployee("666666");
        } catch (SQLException | ServletException e) {
            e.printStackTrace();
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

        }
        final String expectedEmployeeName = "氏名";
        final String actualEmployeeName = emp.getEmployeeName();
        final String expectedEmployeeProfile = "プロフィール";
        final String actualEmployeeProfile = emp.getEmployeeProfile();
        // 2020/6/15　追加
        final String expectedEmployeeDeployment = "部署1";
        final String actualEmployeeDeployment = emp.getEmployeeDeployment();

        // ------------
        // 結果チェック
        // ------------
        assertEquals(expectedEmployeeName, actualEmployeeName);
        assertEquals(expectedEmployeeProfile, actualEmployeeProfile);
        // 2020/6/15 追加
        assertEquals(expectedEmployeeDeployment, actualEmployeeDeployment);
    }

    /**
     * 従業員の登録テスト(異常系：従業員番号6桁より大きい)
     *
     * @throws SQLException
     */
    @Test
    public void registerEmployeeIDOverError() throws SQLException {
        Connection connection = null;
        @SuppressWarnings("unused")
		Boolean hasError = false;

        //テスト実行
        try {
            connection = ConnectionManagerTest.getConnection();
            EmployeeLogic employeeLogic = new EmployeeLogic(connection);
            // 2020/6/15 所属追加
            hasError = employeeLogic.registerEmployee("0123456", "氏名", "プロフィール", "部署1");

            //Exceptionが発生しなければ失敗
            fail();
        } catch (ServletException | IllegalArgumentException e) {

            // ------------
            // 結果チェック
            // ------------
            assertEquals("java.lang.IllegalArgumentException: 従業員番号が6桁ではありません。", e.getMessage());
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
     * 従業員の登録テスト(異常系：従業員番号がnullの場合)
     *
     * @throws SQLException
     */
    @Test
    public void registerEmployeeIDNullError() throws SQLException {
        Connection connection = null;
        @SuppressWarnings("unused")
		Boolean hasError = false;

        //テスト実行
        try {
            connection = ConnectionManagerTest.getConnection();
            EmployeeLogic employeeLogic = new EmployeeLogic(connection);
            // 2020/6/15 所属追加
            hasError = employeeLogic.registerEmployee(null, "氏名", "プロフィール", "部署1");

            //Exceptionが発生しなければ失敗
            fail();
        } catch (ServletException | IllegalArgumentException e) {

            // ------------
            // 結果チェック
            // ------------
            assertEquals("java.lang.IllegalArgumentException: 従業員番号に不正な値が入力されています。", e.getMessage());
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
     * 従業員の登録テスト(異常系：従業員番号が空白の場合)
     *
     * @throws SQLException
     */
    @Test
    public void registerEmployeeIDBlankError() throws SQLException {
        Connection connection = null;
        @SuppressWarnings("unused")
		Boolean hasError = false;

        //テスト実行
        try {
            connection = ConnectionManagerTest.getConnection();
            EmployeeLogic employeeLogic = new EmployeeLogic(connection);
            // 2020/6/15 所属追加
            hasError = employeeLogic.registerEmployee("", "氏名", "プロフィール", "部署1");
            //Exceptionが発生しなければ失敗
            fail();
        } catch (ServletException | IllegalArgumentException e) {

            // ------------
            // 結果チェック
            // ------------
            assertEquals("java.lang.IllegalArgumentException: 従業員番号が6桁ではありません。", e.getMessage());
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
     * 従業員の登録テスト(異常系：従業員番号が5桁の場合)
     *
     * @throws SQLException
     */
    @Test
    public void registerEmployeeIDLackError() throws SQLException {
        Connection connection = null;
        @SuppressWarnings("unused")
		Boolean hasError = false;

        //テスト実行
        try {
            connection = ConnectionManagerTest.getConnection();
            EmployeeLogic employeeLogic = new EmployeeLogic(connection);
            // 2020/6/15 所属追加
            hasError = employeeLogic.registerEmployee("01234", "氏名", "プロフィール", "部署1");

            //Exceptionが発生しなければ失敗
            fail();
        } catch (ServletException | IllegalArgumentException e) {

            // ------------
            // 結果チェック
            // ------------
            assertEquals("java.lang.IllegalArgumentException: 従業員番号が6桁ではありません。", e.getMessage());
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
     * 従業員の登録テスト（異常系：従業員番号重複エラー）
     *
     * @throws SQLException
     * @throws ServletException
     */
    @Test
    public void registerEmployeeIDExistingError() throws SQLException, ServletException {
        Connection connection = null;
        Boolean hasError = false;

        //DAOのJMockit
        new Expectations() {{
        	// 2020/6/15 所属追加
            Employee emp = new Employee("666666", "氏名", "プロフィール", "部署1");
            employeeDAO.findOneEmployee("666666");
            result = emp;
        }};

        //テスト実行
        try {
            connection = ConnectionManagerTest.getConnection();
            EmployeeLogic employeeLogic = new EmployeeLogic(connection);
            // 2020/6/15 所属追加
            hasError = employeeLogic.registerEmployee("666666", "氏名", "プロフィール", "部署1");

            // ------------
            // 結果チェック
            // ------------
            assertTrue(hasError);
        } catch (ServletException | IllegalArgumentException e) {
            e.printStackTrace();
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
     * 従業員の登録テスト(異常系：氏名30桁より大きい)
     *
     * @throws SQLException
     */
    @Test
    public void registerEmployeeNameOverError() throws SQLException {
        Connection connection = null;
        @SuppressWarnings("unused")
		Boolean hasError = false;

        //テスト実行
        try {
            connection = ConnectionManagerTest.getConnection();
            EmployeeLogic employeeLogic = new EmployeeLogic(connection);
            // 2020/6/15 所属追加
            hasError = employeeLogic.registerEmployee("666666", "０１２３４５６７８９０１２３４５６７８９０１２３４５６７８９０", "プロフィール", "部署1");			//2020.05.28 11→31桁に変更

            //Exceptionが発生しなければ失敗
            fail();
        } catch (ServletException | IllegalArgumentException e) {

            // ------------
            // 結果チェック
            // ------------
            assertEquals("java.lang.IllegalArgumentException: 氏名が30桁を超えています。", e.getMessage());			//2020.05.28 10→30桁に変更
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
     * 従業員の登録テスト(異常系：氏名がnull)
     *
     * @throws SQLException
     */
    @Test
    public void registerEmployeeNameNullError() throws SQLException {
        Connection connection = null;
        @SuppressWarnings("unused")
		Boolean hasError = false;

        //テスト実行
        try {
            connection = ConnectionManagerTest.getConnection();
            EmployeeLogic employeeLogic = new EmployeeLogic(connection);
            // 2020/6/15 所属追加
            hasError = employeeLogic.registerEmployee("666666", null, "プロフィール", "部署1");

            //Exceptionが発生しなければ失敗
            fail();
        } catch (ServletException | IllegalArgumentException e) {

            // ------------
            // 結果チェック
            // ------------
            assertEquals("java.lang.IllegalArgumentException: 氏名に不正な値が入力されています。", e.getMessage());
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
     * 従業員の登録テスト(異常系：氏名が空)
     *
     * @throws SQLException
     */
    @Test
    public void registerEmployeeNameBlankError() throws SQLException {
        Connection connection = null;
        @SuppressWarnings("unused")
		Boolean hasError = false;

        //テスト実行
        try {
            connection = ConnectionManagerTest.getConnection();
            EmployeeLogic employeeLogic = new EmployeeLogic(connection);
            //2020/6/15 所属追加
            hasError = employeeLogic.registerEmployee("666666", "", "プロフィール", "部署1");

            //Exceptionが発生しなければ失敗
            fail();
        } catch (ServletException | IllegalArgumentException e) {

            // ------------
            // 結果チェック
            // ------------
            assertEquals("java.lang.IllegalArgumentException: 氏名に値が入力されていません。", e.getMessage());
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
     * 従業員の登録テスト(異常系：プロフィール100桁より大きい)
     *
     * @throws SQLException
     */
    @Test
    public void registerEmployeeProfileOverError() throws SQLException {
        Connection connection = null;
        @SuppressWarnings("unused")
		Boolean hasError = false;

        //テスト実行
        try {
            connection = ConnectionManagerTest.getConnection();
            EmployeeLogic employeeLogic = new EmployeeLogic(connection);
            // 2020/6/15　所属追加
            hasError = employeeLogic.registerEmployee("666666", "氏名", "０１２３４５６７８９０１２３４５６７８９０１２３４５６７８９０１２３４５６７８９０１２３４５６７８９０１２３４５６７８９０１２３４５６７８９０１２３４５６７８９０１２３４５６７８９０１２３４５６７８９０", "部署1");

            //Exceptionが発生しなければ失敗
            fail();
        } catch (ServletException | IllegalArgumentException e) {

            // ------------
            // 結果チェック
            // ------------
            assertEquals("java.lang.IllegalArgumentException: プロフィールが100桁を超えています。", e.getMessage());
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
     * 従業員の登録テスト(異常系：プロフィールがnull)
     *
     * @throws SQLException
     */
    @Test
    public void registerEmployeeProfileNullError() throws SQLException {
        Connection connection = null;
        @SuppressWarnings("unused")
		Boolean hasError = false;

        //テスト実行
        try {
            connection = ConnectionManagerTest.getConnection();
            EmployeeLogic employeeLogic = new EmployeeLogic(connection);
            // 2020/6/15 所属追加
            hasError = employeeLogic.registerEmployee("666666", "氏名", null, "部署1");

            //Exceptionが発生しなければ失敗
            fail();
        } catch (ServletException | IllegalArgumentException e) {

            // ------------
            // 結果チェック
            // ------------
            assertEquals("java.lang.IllegalArgumentException: プロフィールに不正な値が入力されています。", e.getMessage());
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
     * 従業員の登録テスト(異常系：プロフィールが空)
     *
     * @throws SQLException
     */
    @Test
    public void registerEmployeeProfileBlankError() throws SQLException {
        Connection connection = null;
        @SuppressWarnings("unused")
		Boolean hasError = false;

        //テスト実行
        try {
            connection = ConnectionManagerTest.getConnection();
            EmployeeLogic employeeLogic = new EmployeeLogic(connection);
            // 2020/6/15 所属追加
            hasError = employeeLogic.registerEmployee("666666", "氏名", "", "部署1");

            //Exceptionが発生しなければ失敗
            fail();
        } catch (ServletException | IllegalArgumentException e) {

            // ------------
            // 結果チェック
            // ------------
            assertEquals("java.lang.IllegalArgumentException: プロフィールに値が入力されていません。", e.getMessage());
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
     * 従業員の更新テスト(正常)
     *
     * @throws SQLException
     * @throws ServletException
     */
    @Test
    public void updateEmployee() throws SQLException, ServletException {
        Connection connection = null;

        //DAOのJMockit
        new Expectations() {
            {
                employeeDAO.updateOneEmployee("686033", "０１２３４５６７８９０１２３４５６７８９０１２３４５６７８９", "０１２３４５６７８９０１２３４５６７８９０１２３４５６７８９０１２３４５６７８９０１２３４５６７８９０１２３４５６７８９０１２３４５６７８９０１２３４５６７８９０１２３４５６７８９０１２３４５６７８９");
            }												//2020.05.28 10→30桁に変更
        };

        //テスト実行
        try {
            connection = ConnectionManagerTest.getConnection();
            EmployeeLogic employeeLogic = new EmployeeLogic(connection);
            employeeLogic.updateEmployee("686033", "０１２３４５６７８９０１２３４５６７８９０１２３４５６７８９", "０１２３４５６７８９０１２３４５６７８９０１２３４５６７８９０１２３４５６７８９０１２３４５６７８９０１２３４５６７８９０１２３４５６７８９０１２３４５６７８９０１２３４５６７８９０１２３４５６７８９");
            											//2020.05.28 10→30桁に変更
        } catch (ServletException e) {
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
     * 従業員の更新テスト(異常系：氏名30桁より大きい)
     *
     * @throws SQLException
     */
    @Test
    public void updateEmployeeNameOverError() throws SQLException {
        Connection connection = null;

        //テスト実行
        try {
            connection = ConnectionManagerTest.getConnection();
            EmployeeLogic employeeLogic = new EmployeeLogic(connection);
            employeeLogic.updateEmployee("012345", "０１２３４５６７８９０１２３４５６７８９０１２３４５６７８９０", "テスト");			//2020.05.28 11→31桁に変更

            //Exceptionが発生しなければ失敗
            fail();
        } catch (ServletException | IllegalArgumentException e) {

            // ------------
            // 結果チェック
            // ------------
            assertEquals("java.lang.IllegalArgumentException: 氏名が30桁を超えています。", e.getMessage());		//2020.05.28 10→30桁に変更
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
     * 従業員の更新テスト(異常系：氏名がnull)
     *
     * @throws SQLException
     */
    @Test
    public void updateEmployeeNameNullError() throws SQLException {
        Connection connection = null;

        //テスト実行
        try {
            connection = ConnectionManagerTest.getConnection();
            EmployeeLogic employeeLogic = new EmployeeLogic(connection);
            employeeLogic.updateEmployee("012345", null, "テスト");

            //Exceptionが発生しなければ失敗
            fail();
        } catch (ServletException | IllegalArgumentException e) {

            // ------------
            // 結果チェック
            // ------------
            assertEquals("java.lang.IllegalArgumentException: 氏名に不正な値が入力されています。", e.getMessage());
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
     * 従業員の更新テスト(異常系：氏名が空)
     *
     * @throws SQLException
     */
    @Test
    public void updateEmployeeNameBlankError() throws SQLException {
        Connection connection = null;

        //テスト実行
        try {
            connection = ConnectionManagerTest.getConnection();
            EmployeeLogic employeeLogic = new EmployeeLogic(connection);
            employeeLogic.updateEmployee("012345", "", "テスト");

            //Exceptionが発生しなければ失敗
            fail();
        } catch (ServletException | IllegalArgumentException e) {

            // ------------
            // 結果チェック
            // ------------
            assertEquals("java.lang.IllegalArgumentException: 氏名に値が入力されていません。", e.getMessage());
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
     * 従業員の更新テスト(異常系：プロフィール100桁より大きい)
     *
     * @throws SQLException
     */
    @Test
    public void updateEmployeeProfileOverError() throws SQLException {
        Connection connection = null;

        //テスト実行
        try {
            connection = ConnectionManagerTest.getConnection();
            EmployeeLogic employeeLogic = new EmployeeLogic(connection);
            employeeLogic.updateEmployee("686033", "氏名", "０１２３４５６７８９０１２３４５６７８９０１２３４５６７８９０１２３４５６７８９０１２３４５６７８９０１２３４５６７８９０１２３４５６７８９０１２３４５６７８９０１２３４５６７８９０１２３４５６７８９０");

            //Exceptionが発生しなければ失敗
            fail();
        } catch (ServletException | IllegalArgumentException e) {

            // ------------
            // 結果チェック
            // ------------
            assertEquals("java.lang.IllegalArgumentException: プロフィールが100桁を超えています。", e.getMessage());
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
     * 従業員の更新テスト(異常系：プロフィールがnull)
     *
     * @throws SQLException
     */
    @Test
    public void updateEmployeeProfileNullError() throws SQLException {
        Connection connection = null;

        //テスト実行
        try {
            connection = ConnectionManagerTest.getConnection();
            EmployeeLogic employeeLogic = new EmployeeLogic(connection);
            employeeLogic.updateEmployee("686033", "氏名", null);

            //Exceptionが発生しなければ失敗
            fail();
        } catch (ServletException | IllegalArgumentException e) {

            // ------------
            // 結果チェック
            // ------------
            assertEquals("java.lang.IllegalArgumentException: プロフィールに不正な値が入力されています。", e.getMessage());
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
     * 従業員の更新テスト(異常系：プロフィールが空)
     *
     * @throws SQLException
     */
    @Test
    public void updateEmployeeProfileBlankError() throws SQLException {
        Connection connection = null;

        //テスト実行
        try {
            connection = ConnectionManagerTest.getConnection();
            EmployeeLogic employeeLogic = new EmployeeLogic(connection);
            employeeLogic.updateEmployee("686033", "氏名", "");

            //Exceptionが発生しなければ失敗
            fail();
        } catch (ServletException | IllegalArgumentException e) {

            // ------------
            // 結果チェック
            // ------------
            assertEquals("java.lang.IllegalArgumentException: プロフィールに値が入力されていません。", e.getMessage());
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
     * 従業員の削除テスト(正常)
     *
     * @throws SQLException
     */
    @Test
    public void deleteEmployee() throws SQLException {
        Connection connection = null;

        //DAOのJMockit
        new Expectations() {
            {
                employeeDAO.deleteOneEmployee("686033");
            }
        };

        //テスト実行
        try {
            connection = ConnectionManagerTest.getConnection();
            EmployeeLogic employeeLogic = new EmployeeLogic(connection);
            employeeLogic.deleteEmployee("686033");
        } catch (ServletException e) {
            e.printStackTrace();
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
}
