package next.dao;

import core.jdbc.ConnectionManager;
import next.model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class JdbcTemplate {

    public void update(String sql, PreparedStatementSetter pss) throws SQLException {
        Connection con = null;
        PreparedStatement pstmt = null;
        try {
            con = ConnectionManager.getConnection();
            //String sql = createQuery();
            pstmt = con.prepareStatement(sql);
            //ASIS
            //setValues(pstmt);
            pss.setValues(pstmt); //todo

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

    public <T> List<T> query(String sql, PreparedStatementSetter pss
            , RowMapper<T> rowMapper) throws SQLException {

        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            con = ConnectionManager.getConnection();
            pstmt = con.prepareStatement(sql);
            pss.setValues(pstmt);

            rs = pstmt.executeQuery(); // SELECT문에 대한 추가 과정

            // [RowMapper.mapRow] ASIS
            // List<Object> result = new ArrayList<Object>();
            List<T> result = new ArrayList<T>();
            while (rs.next()) {
                //asis
                //result.add(mapRow(rs));
                result.add(rowMapper.mapRow(rs)); // tobe
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

    public <T> T queryForObject(String sql, PreparedStatementSetter pss
            , RowMapper<T> rowMapper) throws SQLException {
        List<T> result = query(sql, pss, rowMapper);
        if (result.isEmpty()) {
            return null;
        }
        return result.get(0);
    }

    //protected abstract Object mapRow(ResultSet rs) throws SQLException;

    //protected abstract void setValues(PreparedStatement pstmt) throws SQLException;

}
