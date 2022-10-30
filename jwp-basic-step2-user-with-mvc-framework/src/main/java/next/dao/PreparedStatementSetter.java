package next.dao;

import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * [5] I/F 활용을 통한 JdbcTemplate.mapRow, setValues 분리
 *  변화 시점이 다른 부분을 서로 다른 인터페이스로 분리함으로써 공통 라이브러리에 대한 유연함을 높임
 *  이러한 유형의 인터페이스를 콜백 인터페이스라고 부름
 * */
public interface PreparedStatementSetter {
    void setValues(PreparedStatement pstmt) throws SQLException;
}
