package next.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import core.jdbc.ConnectionManager;
import next.model.User;

public class UserDao {
    public void insert(User user) throws SQLException {
        /**
         * [2] 추상 클래스의 인스턴스를 생성하기 위해 2개의 추상 메소드를 구현
         *  1. 상속받는 새로운 클래스를 추가하거나
         *  2. 이름을 가지지 않는 익명의 클래스를 추가
         *      - 익명 클래스는 다른 곳에서 재사용할 필요가 없는 경우 사용하기 적합
         *
         */
        InsertJdbcTemplate jdbcTemplate = new InsertJdbcTemplate() {
            void setValuesForInsert(User user, PreparedStatement pstmt)
                    throws SQLException {
                pstmt.setString(1, user.getUserId());
                pstmt.setString(2, user.getPassword());
                pstmt.setString(3, user.getName());
                pstmt.setString(4, user.getEmail());
            }

            String createQueryForInsert() {
                return "INSERT INTO USERS VALUES (?, ?, ?, ?)";
            }
        };
        jdbcTemplate.insert(user, this);
    }

    public void update(User user) throws SQLException {
        UpdateJdbcTemplate jdbcTemplate = new UpdateJdbcTemplate() {

            void setValuesForUpdate(User user, PreparedStatement pstmt)
                    throws SQLException {
                pstmt.setString(4, user.getUserId());
                pstmt.setString(1, user.getPassword());
                pstmt.setString(2, user.getName());
                pstmt.setString(3, user.getEmail());
            }

            String createQueryForUpdate() {
                return "UPDATE USERS SET password=?, name=?, email=? WHERE userid=?";
            }
        };
        jdbcTemplate.update(user, this);
    }

    public List<User> findAll() throws SQLException {
        List<User> userList = new ArrayList<>();
        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            con = ConnectionManager.getConnection();
            String sql = "SELECT userId, password, name, email FROM USERS";
            pstmt = con.prepareStatement(sql);

            rs = pstmt.executeQuery();

            while (rs.next()) {
                User user = new User(rs.getString("userId"), rs.getString("password"), rs.getString("name"),
                        rs.getString("email"));
                userList.add(user);
            }

        } finally {
            if (rs != null) {
                rs.close();
            }
            if (pstmt != null) {
                pstmt.close();
            }
            if (con != null) {
                con.close();
            }
        }

        return userList;
    }

    public User findByUserId(String userId) throws SQLException {
        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            con = ConnectionManager.getConnection();
            String sql = "SELECT userId, password, name, email FROM USERS WHERE userid=?";
            pstmt = con.prepareStatement(sql);
            pstmt.setString(1, userId);

            rs = pstmt.executeQuery();

            User user = null;
            if (rs.next()) {
                user = new User(rs.getString("userId"), rs.getString("password"), rs.getString("name"),
                        rs.getString("email"));
            }

            return user;
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (pstmt != null) {
                pstmt.close();
            }
            if (con != null) {
                con.close();
            }
        }
    }

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
}
