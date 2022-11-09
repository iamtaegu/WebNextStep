package next.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

@FunctionalInterface
public interface RowMapper<T> {
   // ASIS
   // Object mapRow(ResultSet rs) throws SQLException;
   T mapRow(ResultSet rs) throws SQLException;
}
