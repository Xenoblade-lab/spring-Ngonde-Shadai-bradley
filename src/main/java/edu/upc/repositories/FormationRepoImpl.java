package edu.upc.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import edu.upc.models.Formation;

@Repository
public class FormationRepoImpl implements FormationRepoCustom {
	
	@Autowired
	private JdbcClient jdbcClient;

	@Override
	public long create(Formation entity) {
		String sql = "INSERT INTO formations (description, duree) VALUES (?, ?)";
		
		Object[] params = new Object[] { entity.getDescription(), entity.getDuree() };
		
		final KeyHolder holder = new GeneratedKeyHolder();
		
		jdbcClient.sql(sql).params(params).update(holder, "id");
		
		return holder.getKey().longValue();
	}

	@Override
	public void update(long id, Formation entity) {
		String sql = "UPDATE formations SET description=?, duree=? WHERE id=?";
		
		Object[] params = new Object[] { entity.getDescription(), entity.getDuree(), id };
		
		jdbcClient.sql(sql).params(params).update();
	}

	@Override
	public void delete(long id) {
		String sql = "DELETE FROM formations WHERE id=?";
		
		Object[] params = new Object[] { id };
		
		jdbcClient.sql(sql).params(params).update();
	}

	@Override
	public Formation getById(long id) {
		String sql = "SELECT * FROM formations WHERE id=?";
		
		Object[] params = new Object[] { id };
		
		Optional<Formation> row = jdbcClient.sql(sql).params(params).query(Formation.class).optional();
		
		return row != null ? row.get() : null;
	}

	@Override
	public List<Formation> get() {
		String sql = "SELECT * FROM formations ORDER BY description";
		
		return jdbcClient.sql(sql).query(Formation.class).list();
	}
	
	

}
