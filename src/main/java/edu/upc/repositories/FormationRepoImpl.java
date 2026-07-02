package edu.upc.repositories;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import edu.upc.models.Formation;
import edu.upc.utils.JdbcSfmHelper;

@Repository
public class FormationRepoImpl implements FormationRepoCustom {

	private static final String SQL_INSERT = "INSERT INTO formations (description, duree) VALUES (?, ?)";
	private static final String SQL_UPDATE = "UPDATE formations SET description=?, duree=? WHERE id=?";
	private static final String SQL_DELETE = "DELETE FROM formations WHERE id=?";
	private static final String SQL_BY_ID = "SELECT id, description, duree FROM formations WHERE id=?";
	private static final String SQL_ALL = "SELECT id, description, duree FROM formations ORDER BY description";

	@Autowired
	private JdbcClient jdbcClient;

	@Autowired
	private JdbcSfmHelper sfmHelper;

	@Override
	public long create(Formation entity) {
		Object[] params = new Object[] { entity.getDescription(), entity.getDuree() };
		KeyHolder holder = new GeneratedKeyHolder();
		jdbcClient.sql(SQL_INSERT).params(params).update(holder, "id");
		return holder.getKey().longValue();
	}

	@Override
	public void update(long id, Formation entity) {
		jdbcClient.sql(SQL_UPDATE).params(entity.getDescription(), entity.getDuree(), id).update();
	}

	@Override
	public void delete(long id) {
		jdbcClient.sql(SQL_DELETE).params(id).update();
	}

	@Override
	public Formation getById(long id) {
		return sfmHelper.queryOne(SQL_BY_ID, Formation.class, new Object[] { id });
	}

	@Override
	public List<Formation> get() {
		return sfmHelper.query(SQL_ALL, Formation.class);
	}

}
