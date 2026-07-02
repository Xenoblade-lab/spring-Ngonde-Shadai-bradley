package edu.upc.utils;

import java.util.List;

import org.simpleflatmapper.jdbc.spring.JdbcTemplateMapperFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

@Component
public class JdbcSfmHelper {

	private final JdbcTemplate jdbcTemplate;

	public JdbcSfmHelper(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	public <T> List<T> query(String sql, Class<T> target) {
		RowMapper<T> mapper = JdbcTemplateMapperFactory.newInstance().newRowMapper(target);
		return jdbcTemplate.query(sql, mapper);
	}

	public <T> List<T> query(String sql, Class<T> target, Object[] params) {
		RowMapper<T> mapper = JdbcTemplateMapperFactory.newInstance().newRowMapper(target);
		return jdbcTemplate.query(sql, mapper, params);
	}

	public <T> T queryOne(String sql, Class<T> target, Object[] params) {
		List<T> rows = query(sql, target, params);
		return rows.isEmpty() ? null : rows.get(0);
	}

	public <T> List<T> queryJoin(String sql, Class<T> target, String... keys) {
		ResultSetExtractor<List<T>> extractor = JdbcTemplateMapperFactory.newInstance()
				.addKeys(keys)
				.newResultSetExtractor(target);
		return jdbcTemplate.query(sql, extractor);
	}

	public <T> List<T> queryJoin(String sql, Class<T> target, Object[] params, String... keys) {
		ResultSetExtractor<List<T>> extractor = JdbcTemplateMapperFactory.newInstance()
				.addKeys(keys)
				.newResultSetExtractor(target);
		return jdbcTemplate.query(sql, extractor, params);
	}

	public <T> T queryJoinOne(String sql, Class<T> target, Object[] params, String... keys) {
		List<T> rows = queryJoin(sql, target, params, keys);
		return rows.isEmpty() ? null : rows.get(0);
	}

}
