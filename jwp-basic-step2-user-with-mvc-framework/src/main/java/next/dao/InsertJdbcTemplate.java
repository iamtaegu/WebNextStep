package next.dao;

import core.jdbc.ConnectionManager;
import next.model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * InsertJdbcTemplate는 UserDao에 의존관계를 가지는데,
 * [1] 메소드는 존재하지만 구현을 담당하지 않으려면 두 개의 메소드를 추상 메소드로 구현하면 됨
 */

public abstract class InsertJdbcTemplate {
    public void insert(User user, UserDao userDao) throws SQLException {

        Connection con = null;
        PreparedStatement pstmt = null;

        try {
            con = ConnectionManager.getConnection();
            String sql = createQueryForInsert();

            pstmt = con.prepareStatement(sql);
            setValuesForInsert(user, pstmt);

            pstmt.executeUpdate();
        } finally {
            if (pstmt != null) {
                pstmt.close();
            }

            if (con != null) {
                con.close();
            }
        }
    }

    // [1]
    abstract String createQueryForInsert();

    abstract void setValuesForInsert(User user, PreparedStatement pstmt) throws SQLException;

}
