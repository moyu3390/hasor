/*
 * Copyright 2008-2009 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package net.hasor.db.dialect;
import net.hasor.db.JdbcUtils;
import net.hasor.test.db.AbstractDbTest;
import org.junit.Test;

import java.sql.JDBCType;

/***
 * 方言
 * @version : 2014-1-13
 * @author 赵永春 (zyc@hasor.net)
 */
public class DialectTest extends AbstractDbTest {
    private final BoundSql queryBoundSql  = new BoundSql() {
        @Override
        public String getSqlString() {
            return "select * from tb_user where age > 12 and sex = ?";
        }

        @Override
        public Object[] getArgs() {
            return new Object[] { 'F' };
        }
    };
    private final BoundSql queryBoundSql2 = new BoundSql() {
        @Override
        public String getSqlString() {
            return "select * from tb_user where age > 12 and sex = ? order by a desc";
        }

        @Override
        public Object[] getArgs() {
            return new Object[] { 'F' };
        }
    };

    @Test
    public void dialect_default_1() {
        SqlDialect dialect = SqlDialectRegister.findOrCreate("");
        String buildSelect = dialect.buildSelect("", "tb_user", "userUUID", JDBCType.VARCHAR, String.class);
        String buildTableName1 = dialect.buildTableName("", "tb_user");
        String buildTableName2 = dialect.buildTableName("abc", "tb_user");
        String buildCondition = dialect.buildColumnName("", "tb_user", "userUUID", JDBCType.VARCHAR, String.class);
        //
        assert buildSelect.equals("userUUID");
        assert buildTableName1.equals("tb_user");
        assert buildTableName2.equals("abc.tb_user");
        assert buildCondition.equals("userUUID");
        //
        try {
            dialect.getCountSql(this.queryBoundSql);
            assert false;
        } catch (Exception e) {
            assert true;
        }
        try {
            dialect.getPageSql(this.queryBoundSql, 1, 3);
            assert false;
        } catch (Exception e) {
            assert true;
        }
    }

    @Test
    public void dialect_mysql_1() {
        SqlDialect dialect = SqlDialectRegister.findOrCreate(JdbcUtils.MYSQL);
        String buildSelect = dialect.buildSelect("", "tb_user", "userUUID", JDBCType.VARCHAR, String.class);
        String buildTableName1 = dialect.buildTableName("", "tb_user");
        String buildTableName2 = dialect.buildTableName("abc", "tb_user");
        String buildCondition = dialect.buildColumnName("", "tb_user", "userUUID", JDBCType.VARCHAR, String.class);
        //
        assert buildSelect.equals("`userUUID`");
        assert buildTableName1.equals("`tb_user`");
        assert buildTableName2.equals("`abc`.`tb_user`");
        assert buildCondition.equals("`userUUID`");
        //
        BoundSql countSql = dialect.getCountSql(this.queryBoundSql);
        assert countSql.getSqlString().equals("SELECT COUNT(*) FROM (select * from tb_user where age > 12 and sex = ?) as TEMP_T");
        assert countSql.getArgs().length == 1;
        //
        BoundSql pageSql = dialect.getPageSql(this.queryBoundSql, 1, 3);
        assert pageSql.getSqlString().equals("select * from tb_user where age > 12 and sex = ? LIMIT ?, ?");
        assert pageSql.getArgs().length == 3;
        assert pageSql.getArgs()[0].equals('F');
        assert pageSql.getArgs()[1].equals(1);
        assert pageSql.getArgs()[2].equals(3);
        //
        BoundSql pageSql2 = dialect.getPageSql(this.queryBoundSql, 0, 3);
        assert pageSql2.getSqlString().equals("select * from tb_user where age > 12 and sex = ? LIMIT ?");
        assert pageSql2.getArgs().length == 2;
        assert pageSql2.getArgs()[0].equals('F');
        assert pageSql2.getArgs()[1].equals(3);
    }

    @Test
    public void dialect_postgresql_1() {
        SqlDialect dialect = SqlDialectRegister.findOrCreate(JdbcUtils.POSTGRESQL);
        String buildSelect = dialect.buildSelect("", "tb_user", "userUUID", JDBCType.VARCHAR, String.class);
        String buildTableName1 = dialect.buildTableName("", "tb_user");
        String buildTableName2 = dialect.buildTableName("abc", "tb_user");
        String buildCondition = dialect.buildColumnName("", "tb_user", "userUUID", JDBCType.VARCHAR, String.class);
        //
        assert buildSelect.equals("\"userUUID\"");
        assert buildTableName1.equals("\"tb_user\"");
        assert buildTableName2.equals("\"abc\".\"tb_user\"");
        assert buildCondition.equals("\"userUUID\"");
        //
        BoundSql countSql = dialect.getCountSql(this.queryBoundSql);
        assert countSql.getSqlString().equals("SELECT COUNT(*) FROM (select * from tb_user where age > 12 and sex = ?) as TEMP_T");
        assert countSql.getArgs().length == 1;
        //
        BoundSql pageSql = dialect.getPageSql(this.queryBoundSql, 1, 3);
        assert pageSql.getSqlString().equals("select * from tb_user where age > 12 and sex = ? LIMIT ? OFFSET ?");
        assert pageSql.getArgs().length == 3;
        assert pageSql.getArgs()[0].equals('F');
        assert pageSql.getArgs()[1].equals(3);
        assert pageSql.getArgs()[2].equals(1);
    }

    @Test
    public void dialect_oracle_1() {
        SqlDialect dialect = SqlDialectRegister.findOrCreate(JdbcUtils.ORACLE);
        String buildSelect = dialect.buildSelect("", "tb_user", "userUUID", JDBCType.VARCHAR, String.class);
        String buildTableName1 = dialect.buildTableName("", "tb_user");
        String buildTableName2 = dialect.buildTableName("abc", "tb_user");
        String buildCondition = dialect.buildColumnName("", "tb_user", "userUUID", JDBCType.VARCHAR, String.class);
        //
        assert buildSelect.equals("\"userUUID\"");
        assert buildTableName1.equals("\"tb_user\"");
        assert buildTableName2.equals("\"abc\".\"tb_user\"");
        assert buildCondition.equals("\"userUUID\"");
        //
        BoundSql countSql = dialect.getCountSql(this.queryBoundSql);
        assert countSql.getSqlString().equals("SELECT COUNT(*) FROM (select * from tb_user where age > 12 and sex = ?) TEMP_T");
        assert countSql.getArgs().length == 1;
        //
        BoundSql pageSql = dialect.getPageSql(this.queryBoundSql, 1, 3);
        assert pageSql.getSqlString().equals("SELECT * FROM ( SELECT TMP.*, ROWNUM ROW_ID FROM ( select * from tb_user where age > 12 and sex = ? ) TMP WHERE ROWNUM <= ? ) WHERE ROW_ID > ?");
        assert pageSql.getArgs().length == 3;
        assert pageSql.getArgs()[0].equals('F');
        assert pageSql.getArgs()[1].equals(4);
        assert pageSql.getArgs()[2].equals(1);
    }

    @Test
    public void dialect_h2_1() {
        SqlDialect dialect = SqlDialectRegister.findOrCreate(JdbcUtils.H2);
        String buildSelect = dialect.buildSelect("", "tb_user", "userUUID", JDBCType.VARCHAR, String.class);
        String buildTableName1 = dialect.buildTableName("", "tb_user");
        String buildTableName2 = dialect.buildTableName("abc", "tb_user");
        String buildCondition = dialect.buildColumnName("", "tb_user", "userUUID", JDBCType.VARCHAR, String.class);
        //
        assert buildSelect.equals("\"userUUID\"");
        assert buildTableName1.equals("\"tb_user\"");
        assert buildTableName2.equals("\"abc\".\"tb_user\"");
        assert buildCondition.equals("\"userUUID\"");
        //
        BoundSql countSql = dialect.getCountSql(this.queryBoundSql);
        assert countSql.getSqlString().equals("SELECT COUNT(*) FROM (select * from tb_user where age > 12 and sex = ?) as TEMP_T");
        assert countSql.getArgs().length == 1;
        //
        BoundSql pageSql = dialect.getPageSql(this.queryBoundSql, 1, 3);
        assert pageSql.getSqlString().equals("select * from tb_user where age > 12 and sex = ? LIMIT ? OFFSET ?");
        assert pageSql.getArgs().length == 3;
        assert pageSql.getArgs()[0].equals('F');
        assert pageSql.getArgs()[1].equals(3);
        assert pageSql.getArgs()[2].equals(1);
    }

    @Test
    public void dialect_hive_1() {
        SqlDialect dialect = SqlDialectRegister.findOrCreate(JdbcUtils.HIVE);
        String buildSelect = dialect.buildSelect("", "tb_user", "userUUID", JDBCType.VARCHAR, String.class);
        String buildTableName1 = dialect.buildTableName("", "tb_user");
        String buildTableName2 = dialect.buildTableName("abc", "tb_user");
        String buildCondition = dialect.buildColumnName("", "tb_user", "userUUID", JDBCType.VARCHAR, String.class);
        //
        assert buildSelect.equals("\"userUUID\"");
        assert buildTableName1.equals("\"tb_user\"");
        assert buildTableName2.equals("\"abc\".\"tb_user\"");
        assert buildCondition.equals("\"userUUID\"");
        //
        //
        try {
            dialect.getCountSql(this.queryBoundSql);
            assert false;
        } catch (Exception e) {
            assert true;
        }
        try {
            dialect.getPageSql(this.queryBoundSql, 1, 3);
            assert false;
        } catch (Exception e) {
            assert true;
        }
    }

    @Test
    public void dialect_sqllite_1() {
        SqlDialect dialect = SqlDialectRegister.findOrCreate(JdbcUtils.SQLITE);
        String buildSelect = dialect.buildSelect("", "tb_user", "userUUID", JDBCType.VARCHAR, String.class);
        String buildTableName1 = dialect.buildTableName("", "tb_user");
        String buildTableName2 = dialect.buildTableName("abc", "tb_user");
        String buildCondition = dialect.buildColumnName("", "tb_user", "userUUID", JDBCType.VARCHAR, String.class);
        //
        assert buildSelect.equals("`userUUID`");
        assert buildTableName1.equals("`tb_user`");
        assert buildTableName2.equals("`abc`.`tb_user`");
        assert buildCondition.equals("`userUUID`");
        //
        BoundSql countSql = dialect.getCountSql(this.queryBoundSql);
        assert countSql.getSqlString().equals("SELECT COUNT(*) FROM (select * from tb_user where age > 12 and sex = ?) as TEMP_T");
        assert countSql.getArgs().length == 1;
        //
        BoundSql pageSql = dialect.getPageSql(this.queryBoundSql, 1, 3);
        assert pageSql.getSqlString().equals("select * from tb_user where age > 12 and sex = ? LIMIT ?, ?");
        assert pageSql.getArgs().length == 3;
        assert pageSql.getArgs()[0].equals('F');
        assert pageSql.getArgs()[1].equals(1);
        assert pageSql.getArgs()[2].equals(3);
        //
        BoundSql pageSql2 = dialect.getPageSql(this.queryBoundSql, 0, 3);
        assert pageSql2.getSqlString().equals("select * from tb_user where age > 12 and sex = ? LIMIT ?");
        assert pageSql2.getArgs().length == 2;
        assert pageSql2.getArgs()[0].equals('F');
        assert pageSql2.getArgs()[1].equals(3);
    }

    @Test
    public void dialect_herddb_1() {
        SqlDialect dialect = SqlDialectRegister.findOrCreate(JdbcUtils.HERDDB);
        String buildSelect = dialect.buildSelect("", "tb_user", "userUUID", JDBCType.VARCHAR, String.class);
        String buildTableName1 = dialect.buildTableName("", "tb_user");
        String buildTableName2 = dialect.buildTableName("abc", "tb_user");
        String buildCondition = dialect.buildColumnName("", "tb_user", "userUUID", JDBCType.VARCHAR, String.class);
        //
        assert buildSelect.equals("`userUUID`");
        assert buildTableName1.equals("`tb_user`");
        assert buildTableName2.equals("`abc`.`tb_user`");
        assert buildCondition.equals("`userUUID`");
        //
        BoundSql countSql = dialect.getCountSql(this.queryBoundSql);
        assert countSql.getSqlString().equals("SELECT COUNT(*) FROM (select * from tb_user where age > 12 and sex = ?) as TEMP_T");
        assert countSql.getArgs().length == 1;
        //
        BoundSql pageSql = dialect.getPageSql(this.queryBoundSql, 1, 3);
        assert pageSql.getSqlString().equals("select * from tb_user where age > 12 and sex = ? LIMIT ?, ?");
        assert pageSql.getArgs().length == 3;
        assert pageSql.getArgs()[0].equals('F');
        assert pageSql.getArgs()[1].equals(1);
        assert pageSql.getArgs()[2].equals(3);
        //
        BoundSql pageSql2 = dialect.getPageSql(this.queryBoundSql, 0, 3);
        assert pageSql2.getSqlString().equals("select * from tb_user where age > 12 and sex = ? LIMIT ?");
        assert pageSql2.getArgs().length == 2;
        assert pageSql2.getArgs()[0].equals('F');
        assert pageSql2.getArgs()[1].equals(3);
    }

    @Test
    public void dialect_sqlserver2012_1() {
        SqlDialect dialect = SqlDialectRegister.findOrCreate("sqlserver2012");
        String buildSelect = dialect.buildSelect("", "tb_user", "userUUID", JDBCType.VARCHAR, String.class);
        String buildTableName1 = dialect.buildTableName("", "tb_user");
        String buildTableName2 = dialect.buildTableName("abc", "tb_user");
        String buildCondition = dialect.buildColumnName("", "tb_user", "userUUID", JDBCType.VARCHAR, String.class);
        //
        assert buildSelect.equals("[userUUID]");
        assert buildTableName1.equals("[tb_user]");
        assert buildTableName2.equals("[abc].[tb_user]");
        assert buildCondition.equals("[userUUID]");
        //
        BoundSql countSql = dialect.getCountSql(this.queryBoundSql);
        assert countSql.getSqlString().equals("SELECT COUNT(*) FROM (select * from tb_user where age > 12 and sex = ?) as TEMP_T");
        assert countSql.getArgs().length == 1;
        //
        BoundSql pageSql = dialect.getPageSql(this.queryBoundSql, 1, 3);
        assert pageSql.getSqlString().equals("select * from tb_user where age > 12 and sex = ? ORDER BY CURRENT_TIMESTAMP offset ? rows fetch next ? rows only");
        assert pageSql.getArgs().length == 3;
        assert pageSql.getArgs()[0].equals('F');
        assert pageSql.getArgs()[1].equals(1);
        assert pageSql.getArgs()[2].equals(3);
        //
        BoundSql countSql2 = dialect.getCountSql(this.queryBoundSql2);
        assert countSql2.getSqlString().equals("SELECT COUNT(*) FROM (SELECT * FROM tb_user WHERE age > 12 AND sex = ?) as TEMP_T");
        assert countSql2.getArgs().length == 1;
        BoundSql pageSql2 = dialect.getPageSql(this.queryBoundSql2, 1, 3);
        assert pageSql2.getSqlString().equals("select * from tb_user where age > 12 and sex = ? order by a desc offset ? rows fetch next ? rows only");
        assert pageSql2.getArgs().length == 3;
        assert pageSql2.getArgs()[0].equals('F');
        assert pageSql2.getArgs()[1].equals(1);
        assert pageSql2.getArgs()[2].equals(3);
    }

    @Test
    public void dialect_informix_1() {
        SqlDialect dialect = SqlDialectRegister.findOrCreate(JdbcUtils.INFORMIX);
        String buildSelect = dialect.buildSelect("", "tb_user", "userUUID", JDBCType.VARCHAR, String.class);
        String buildTableName1 = dialect.buildTableName("", "tb_user");
        String buildTableName2 = dialect.buildTableName("abc", "tb_user");
        String buildCondition = dialect.buildColumnName("", "tb_user", "userUUID", JDBCType.VARCHAR, String.class);
        //
        assert buildSelect.equals("\"userUUID\"");
        assert buildTableName1.equals("\"tb_user\"");
        assert buildTableName2.equals("\"abc\".\"tb_user\"");
        assert buildCondition.equals("\"userUUID\"");
        //
        BoundSql countSql = dialect.getCountSql(this.queryBoundSql);
        assert countSql.getSqlString().equals("SELECT COUNT(*) FROM (select * from tb_user where age > 12 and sex = ?) as TEMP_T");
        assert countSql.getArgs().length == 1;
        //
        BoundSql pageSql = dialect.getPageSql(this.queryBoundSql, 1, 3);
        assert pageSql.getSqlString().equals("SELECT  SKIP ?  FIRST ?  * FROM ( select * from tb_user where age > 12 and sex = ? ) TEMP_T");
        assert pageSql.getArgs().length == 3;
        assert pageSql.getArgs()[0].equals(1);
        assert pageSql.getArgs()[1].equals(3);
        assert pageSql.getArgs()[2].equals('F');
    }

    @Test
    public void dialect_db2_1() {
        SqlDialect dialect = SqlDialectRegister.findOrCreate(JdbcUtils.DB2);
        String buildSelect = dialect.buildSelect("", "tb_user", "userUUID", JDBCType.VARCHAR, String.class);
        String buildTableName1 = dialect.buildTableName("", "tb_user");
        String buildTableName2 = dialect.buildTableName("abc", "tb_user");
        String buildCondition = dialect.buildColumnName("", "tb_user", "userUUID", JDBCType.VARCHAR, String.class);
        //
        assert buildSelect.equals("\"userUUID\"");
        assert buildTableName1.equals("\"tb_user\"");
        assert buildTableName2.equals("\"abc\".\"tb_user\"");
        assert buildCondition.equals("\"userUUID\"");
        //
        BoundSql countSql = dialect.getCountSql(this.queryBoundSql);
        assert countSql.getSqlString().equals("SELECT COUNT(*) FROM (select * from tb_user where age > 12 and sex = ?) as TEMP_T");
        assert countSql.getArgs().length == 1;
        //
        BoundSql pageSql = dialect.getPageSql(this.queryBoundSql, 1, 3);
        assert pageSql.getSqlString().equals("SELECT * FROM (SELECT TMP_PAGE.*,ROWNUMBER() OVER() AS ROW_ID FROM ( select * from tb_user where age > 12 and sex = ? ) AS TMP_PAGE) TMP_PAGE WHERE ROW_ID BETWEEN ? AND ?");
        assert pageSql.getArgs().length == 3;
        assert pageSql.getArgs()[0].equals('F');
        assert pageSql.getArgs()[1].equals(1);
        assert pageSql.getArgs()[2].equals(3);
    }

    @Test
    public void dialect_hsql_1() {
        SqlDialect dialect = SqlDialectRegister.findOrCreate(JdbcUtils.HSQL);
        String buildSelect = dialect.buildSelect("", "tb_user", "userUUID", JDBCType.VARCHAR, String.class);
        String buildTableName1 = dialect.buildTableName("", "tb_user");
        String buildTableName2 = dialect.buildTableName("abc", "tb_user");
        String buildCondition = dialect.buildColumnName("", "tb_user", "userUUID", JDBCType.VARCHAR, String.class);
        //
        assert buildSelect.equals("\"userUUID\"");
        assert buildTableName1.equals("\"tb_user\"");
        assert buildTableName2.equals("\"abc\".\"tb_user\"");
        assert buildCondition.equals("\"userUUID\"");
        //
        BoundSql countSql = dialect.getCountSql(this.queryBoundSql);
        assert countSql.getSqlString().equals("SELECT COUNT(*) FROM (select * from tb_user where age > 12 and sex = ?) as TEMP_T");
        assert countSql.getArgs().length == 1;
        //
        BoundSql pageSql = dialect.getPageSql(this.queryBoundSql, 1, 3);
        assert pageSql.getSqlString().equals("select * from tb_user where age > 12 and sex = ? LIMIT ? OFFSET ?");
        assert pageSql.getArgs().length == 3;
        assert pageSql.getArgs()[0].equals('F');
        assert pageSql.getArgs()[1].equals(3);
        assert pageSql.getArgs()[2].equals(1);
    }

    @Test
    public void dialect_phoenix_1() {
        SqlDialect dialect = SqlDialectRegister.findOrCreate(JdbcUtils.PHOENIX);
        String buildSelect = dialect.buildSelect("", "tb_user", "userUUID", JDBCType.VARCHAR, String.class);
        String buildTableName1 = dialect.buildTableName("", "tb_user");
        String buildTableName2 = dialect.buildTableName("abc", "tb_user");
        String buildCondition = dialect.buildColumnName("", "tb_user", "userUUID", JDBCType.VARCHAR, String.class);
        //
        assert buildSelect.equals("\"userUUID\"");
        assert buildTableName1.equals("\"tb_user\"");
        assert buildTableName2.equals("\"abc\".\"tb_user\"");
        assert buildCondition.equals("\"userUUID\"");
        //
        BoundSql countSql = dialect.getCountSql(this.queryBoundSql);
        assert countSql.getSqlString().equals("SELECT COUNT(*) FROM (select * from tb_user where age > 12 and sex = ?) as TEMP_T");
        assert countSql.getArgs().length == 1;
        //
        BoundSql pageSql = dialect.getPageSql(this.queryBoundSql, 1, 3);
        assert pageSql.getSqlString().equals("select * from tb_user where age > 12 and sex = ? LIMIT ? OFFSET ?");
        assert pageSql.getArgs().length == 3;
        assert pageSql.getArgs()[0].equals('F');
        assert pageSql.getArgs()[1].equals(3);
        assert pageSql.getArgs()[2].equals(1);
    }

    @Test
    public void dialect_impala_1() {
        SqlDialect dialect = SqlDialectRegister.findOrCreate(JdbcUtils.IMPALA);
        String buildSelect = dialect.buildSelect("", "tb_user", "userUUID", JDBCType.VARCHAR, String.class);
        String buildTableName1 = dialect.buildTableName("", "tb_user");
        String buildTableName2 = dialect.buildTableName("abc", "tb_user");
        String buildCondition = dialect.buildColumnName("", "tb_user", "userUUID", JDBCType.VARCHAR, String.class);
        //
        assert buildSelect.equals("\"userUUID\"");
        assert buildTableName1.equals("\"tb_user\"");
        assert buildTableName2.equals("\"abc\".\"tb_user\"");
        assert buildCondition.equals("\"userUUID\"");
        //
        BoundSql countSql = dialect.getCountSql(this.queryBoundSql);
        assert countSql.getSqlString().equals("SELECT COUNT(*) FROM (select * from tb_user where age > 12 and sex = ?) as TEMP_T");
        assert countSql.getArgs().length == 1;
        //
        BoundSql pageSql = dialect.getPageSql(this.queryBoundSql, 1, 3);
        assert pageSql.getSqlString().equals("select * from tb_user where age > 12 and sex = ? LIMIT ? OFFSET ?");
        assert pageSql.getArgs().length == 3;
        assert pageSql.getArgs()[0].equals('F');
        assert pageSql.getArgs()[1].equals(3);
        assert pageSql.getArgs()[2].equals(1);
    }

    @Test
    public void dialect_mariadb_1() {
        SqlDialect dialect = SqlDialectRegister.findOrCreate(JdbcUtils.MARIADB);
        String buildSelect = dialect.buildSelect("", "tb_user", "userUUID", JDBCType.VARCHAR, String.class);
        String buildTableName1 = dialect.buildTableName("", "tb_user");
        String buildTableName2 = dialect.buildTableName("abc", "tb_user");
        String buildCondition = dialect.buildColumnName("", "tb_user", "userUUID", JDBCType.VARCHAR, String.class);
        //
        assert buildSelect.equals("`userUUID`");
        assert buildTableName1.equals("`tb_user`");
        assert buildTableName2.equals("`abc`.`tb_user`");
        assert buildCondition.equals("`userUUID`");
        //
        BoundSql countSql = dialect.getCountSql(this.queryBoundSql);
        assert countSql.getSqlString().equals("SELECT COUNT(*) FROM (select * from tb_user where age > 12 and sex = ?) as TEMP_T");
        assert countSql.getArgs().length == 1;
        //
        BoundSql pageSql = dialect.getPageSql(this.queryBoundSql, 1, 3);
        assert pageSql.getSqlString().equals("select * from tb_user where age > 12 and sex = ? LIMIT ?, ?");
        assert pageSql.getArgs().length == 3;
        assert pageSql.getArgs()[0].equals('F');
        assert pageSql.getArgs()[1].equals(1);
        assert pageSql.getArgs()[2].equals(3);
        //
        BoundSql pageSql2 = dialect.getPageSql(this.queryBoundSql, 0, 3);
        assert pageSql2.getSqlString().equals("select * from tb_user where age > 12 and sex = ? LIMIT ?");
        assert pageSql2.getArgs().length == 2;
        assert pageSql2.getArgs()[0].equals('F');
        assert pageSql2.getArgs()[1].equals(3);
    }

    @Test
    public void dialect_aliyun_ads_1() {
        SqlDialect dialect = SqlDialectRegister.findOrCreate(JdbcUtils.ALIYUN_ADS);
        String buildSelect = dialect.buildSelect("", "tb_user", "userUUID", JDBCType.VARCHAR, String.class);
        String buildTableName1 = dialect.buildTableName("", "tb_user");
        String buildTableName2 = dialect.buildTableName("abc", "tb_user");
        String buildCondition = dialect.buildColumnName("", "tb_user", "userUUID", JDBCType.VARCHAR, String.class);
        //
        assert buildSelect.equals("`userUUID`");
        assert buildTableName1.equals("`tb_user`");
        assert buildTableName2.equals("`abc`.`tb_user`");
        assert buildCondition.equals("`userUUID`");
        //
        BoundSql countSql = dialect.getCountSql(this.queryBoundSql);
        assert countSql.getSqlString().equals("SELECT COUNT(*) FROM (select * from tb_user where age > 12 and sex = ?) as TEMP_T");
        assert countSql.getArgs().length == 1;
        //
        BoundSql pageSql = dialect.getPageSql(this.queryBoundSql, 1, 3);
        assert pageSql.getSqlString().equals("select * from tb_user where age > 12 and sex = ? LIMIT ?, ?");
        assert pageSql.getArgs().length == 3;
        assert pageSql.getArgs()[0].equals('F');
        assert pageSql.getArgs()[1].equals(1);
        assert pageSql.getArgs()[2].equals(3);
        //
        BoundSql pageSql2 = dialect.getPageSql(this.queryBoundSql, 0, 3);
        assert pageSql2.getSqlString().equals("select * from tb_user where age > 12 and sex = ? LIMIT ?");
        assert pageSql2.getArgs().length == 2;
        assert pageSql2.getArgs()[0].equals('F');
        assert pageSql2.getArgs()[1].equals(3);
    }

    @Test
    public void dialect_aliyun_drds_1() {
        SqlDialect dialect = SqlDialectRegister.findOrCreate(JdbcUtils.ALIYUN_DRDS);
        String buildSelect = dialect.buildSelect("", "tb_user", "userUUID", JDBCType.VARCHAR, String.class);
        String buildTableName1 = dialect.buildTableName("", "tb_user");
        String buildTableName2 = dialect.buildTableName("abc", "tb_user");
        String buildCondition = dialect.buildColumnName("", "tb_user", "userUUID", JDBCType.VARCHAR, String.class);
        //
        assert buildSelect.equals("`userUUID`");
        assert buildTableName1.equals("`tb_user`");
        assert buildTableName2.equals("`abc`.`tb_user`");
        assert buildCondition.equals("`userUUID`");
        //
        BoundSql countSql = dialect.getCountSql(this.queryBoundSql);
        assert countSql.getSqlString().equals("SELECT COUNT(*) FROM (select * from tb_user where age > 12 and sex = ?) as TEMP_T");
        assert countSql.getArgs().length == 1;
        //
        BoundSql pageSql = dialect.getPageSql(this.queryBoundSql, 1, 3);
        assert pageSql.getSqlString().equals("select * from tb_user where age > 12 and sex = ? LIMIT ?, ?");
        assert pageSql.getArgs().length == 3;
        assert pageSql.getArgs()[0].equals('F');
        assert pageSql.getArgs()[1].equals(1);
        assert pageSql.getArgs()[2].equals(3);
        //
        BoundSql pageSql2 = dialect.getPageSql(this.queryBoundSql, 0, 3);
        assert pageSql2.getSqlString().equals("select * from tb_user where age > 12 and sex = ? LIMIT ?");
        assert pageSql2.getArgs().length == 2;
        assert pageSql2.getArgs()[0].equals('F');
        assert pageSql2.getArgs()[1].equals(3);
    }
}
