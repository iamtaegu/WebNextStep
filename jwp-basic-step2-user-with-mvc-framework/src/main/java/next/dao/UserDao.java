package next.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import core.jdbc.ConnectionManager;
import next.model.User;
import org.h2.command.dml.Select;

public class UserDao {
    public void insert(User user) throws SQLException {
        /**
         * [2] 추상 클래스의 인스턴스를 생성하기 위해 2개의 추상 메소드를 구현
         *  1. 상속받는 새로운 클래스를 추가하거나
         *  2. 이름을 가지지 않는 익명의 클래스를 추가
         *      - 익명 클래스는 다른 곳에서 재사용할 필요가 없는 경우 사용하기 적합
         *
         *  [3] 메소드 통합에 따른 수정
         */
        JdbcTemplate jdbcTemplate = new JdbcTemplate();
        PreparedStatementSetter pss = new PreparedStatementSetter() {
            public void setValues(PreparedStatement pstmt)
                    throws SQLException {
                pstmt.setString(1, user.getUserId());
                pstmt.setString(2, user.getPassword());
                pstmt.setString(3, user.getName());
                pstmt.setString(4, user.getEmail());
            }
        };
        String sql = "INSERT INTO USERS VALUES (?, ?, ?, ?)";
        jdbcTemplate.insert(sql, pss);
    }

    public void update(User user) throws SQLException {
        JdbcTemplate jdbcTemplate = new JdbcTemplate();

        PreparedStatementSetter pss = new PreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement pstmt)
                    throws SQLException {
                pstmt.setString(4, user.getUserId());
                pstmt.setString(1, user.getPassword());
                pstmt.setString(2, user.getName());
                pstmt.setString(3, user.getEmail());
            }
        };
        String sql = "UPDATE USERS SET password=?, name=?, email=? WHERE userid=?";
        jdbcTemplate.update(sql, pss);
    }

    // findAll(TOBE), findByUserId(ASIS)
    public List<User> findAll() throws SQLException {
        JdbcTemplate jdbcTemplate = new JdbcTemplate();

        PreparedStatementSetter pss = new PreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement pstmt)
                    throws SQLException {
            }
        };
        //RowMapper rowMapper = new RowMapper() {
        RowMapper<User> rowMapper = new RowMapper<User>() {
            @Override
            //public Object mapRow(ResultSet rs) throws SQLException {
            public User mapRow(ResultSet rs) throws SQLException {
                return new User(
                    rs.getString("userId"),
                    rs.getString("password"),
                    rs.getString("name"),
                    rs.getString("email")
                );
            }
        };
        String sql = "SELECT userId, password, name, email FROM USERS";
        //return (List<User>) jdbcTemplate.query(sql, pss, rowMapper);
        return jdbcTemplate.query(sql, pss, rowMapper);
    }

    public User findByUserId(String userId) throws SQLException {
        JdbcTemplate jdbcTemplate = new JdbcTemplate();

        PreparedStatementSetter pss = new PreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement pstmt)
                    throws SQLException {
                pstmt.setString(1, userId);
            }
        };

        String sql = "SELECT userId, password, name, email FROM USERS WHERE userId = ?";
        return jdbcTemplate.queryForObject(sql, pss, (ResultSet rs) -> {
            return new User(
                    rs.getString("userId"),
                    rs.getString("password"),
                    rs.getString("name"),
                    rs.getString("email"));
        });
    }

    /*
    // 파라미터 Setting
    public void setValuesForInsert(User user, PreparedStatement pstmt)
            throws SQLException {
        pstmt.setString(1, user.getUserId());
        pstmt.setString(2, user.getPassword());
        pstmt.setString(3, user.getName());
        pstmt.setString(4, user.getEmail());
    }

    // 파라미터 Setting
    public void setValuesForUpdate(User user, PreparedStatement pstmt)
            throws SQLException {
        pstmt.setString(4, user.getUserId());
        pstmt.setString(1, user.getPassword());
        pstmt.setString(2, user.getName());
        pstmt.setString(3, user.getEmail());
    }

    public String createQueryForInsert() {
        return "INSERT INTO USERS VALUES (?, ?, ?, ?)";
    }

    public String createQueryForUpdate() {
        return "UPDATE USERS SET password=?, name=?, email=? WHERE userid=?";
    }
    */
}
