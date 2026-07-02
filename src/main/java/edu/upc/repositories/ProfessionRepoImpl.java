package edu.upc.repositories;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import edu.upc.models.Profession;
import edu.upc.utils.JdbcSfmHelper;

@Repository
public class ProfessionRepoImpl implements ProfessionRepoCustom {

	private static final String SQL_INSERT = "INSERT INTO professions (description) VALUES (?)";
	private static final String SQL_UPDATE = "UPDATE professions SET description=? WHERE id=?";
	private static final String SQL_DELETE = "DELETE FROM professions WHERE id=?";
	private static final String SQL_BY_ID = "SELECT id, description FROM professions WHERE id=?";
	private static final String SQL_ALL = "SELECT id, description FROM professions ORDER BY description";

	@Autowired
	private JdbcClient jdbcClient;

	@Autowired
	private JdbcSfmHelper sfmHelper;

	@Override
	public long create(Profession entity) {
		Object[] params = new Object[] { entity.getDescription() };
		KeyHolder holder = new GeneratedKeyHolder();
		jdbcClient.sql(SQL_INSERT).params(params).update(holder, "id");
		return holder.getKey().longValue();
	}

	@Override
	public void update(long id, Profession entity) {
		jdbcClient.sql(SQL_UPDATE).params(entity.getDescription(), id).update();
	}

	@Override
	public void delete(long id) {
		jdbcClient.sql(SQL_DELETE).params(id).update();
	}

	@Override
	public Profession getById(long id) {
		return sfmHelper.queryOne(SQL_BY_ID, Profession.class, new Object[] { id });
	}

	@Override
	public List<Profession> get() {
		return sfmHelper.query(SQL_ALL, Profession.class);
	}

}
