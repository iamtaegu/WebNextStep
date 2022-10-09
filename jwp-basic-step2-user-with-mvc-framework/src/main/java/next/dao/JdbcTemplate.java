package next.dao;

import core.jdbc.ConnectionManager;
import next.model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * [3] 공통 라이브러리를 담당할 클래스를 분리하니
 * 굳이 메소드 이름을 구분짓지 않아도 되고
 * 추상 메소드로 createQuery/setValues로 통합
 * 
 * [4] User에 대한 의존관계 제거
 * User를 인자로 전달하지 않고, User 인스턴스에 직접 접근하도록 리팩토링
 *
 * [5] 매번 2개의 추상 메소드를 구현할 필요가 있을까 ?
 * JdbcTemplate abstract > class
 * I/F 추가
 *
 * */
public class JdbcTemplate {

    public void insert(String sql, PreparedStatementSetter pss) throws SQLException {

        Connection con = null;
        PreparedStatement pstmt = null;

        try {
            con = ConnectionManager.getConnection();
            pstmt = con.prepareStatement(sql);
            //setValues(user, pstmt);
            pss.setValues(pstmt);

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

    public void update(String sql, PreparedStatementSetter pss) throws SQLException {

        Connection con = null;
        PreparedStatement pstmt = null;

        try {
            con = ConnectionManager.getConnection();
            pstmt = con.prepareStatement(sql);
            pss.setValues(pstmt);

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

    @SuppressWarnings("rawtypes")
    public List query(String sql, PreparedStatementSetter pss, RowMapper rowMapper) throws SQLException {
        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            con = ConnectionManager.getConnection();
            pstmt = con.prepareStatement(sql);
            pss.setValues(pstmt);

            rs = pstmt.executeQuery();

            List<Object> result = new ArrayList<>();
            while (rs.next()) {
                result.add(rowMapper.mapRow(rs));
            }

            return result;
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

    public Object queryForObject(String sql, PreparedStatementSetter pss, RowMapper rowMapper) throws SQLException {
        List result = query(sql, pss, rowMapper);
        if (result.isEmpty()) {
            return null;
        }
        return result.get(0);
    }

    //abstract Object mapRow(ResultSet rs) throws SQLException;

    //abstract void setValues(PreparedStatement pstmt) throws SQLException;

}
