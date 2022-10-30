package next.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * 제네렉을 할용한 개선
 * 조회할 때 매번 캐스팅을 해야 하는데,
 * 제네릭을 적용해 캐스팅을 하지 않도록 개선
 * 
 * 람다 표현식 사용을 위한 애노테이션 추가
 * 
 * */
@FunctionalInterface
public interface RowMapper<T> {
    //Object mapRow(ResultSet rs) throws SQLException;
    T mapRow(ResultSet rs) throws SQLException;
}
