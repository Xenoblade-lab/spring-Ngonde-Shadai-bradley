package edu.upc.repositories;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import edu.upc.models.CandidatFormation;
import edu.upc.utils.JdbcSfmHelper;

@Repository
public class CandidatFormationRepoImpl implements CandidatFormationRepoCustom {

	private static final String SQL_INSERT = """
			INSERT INTO candidats_formations (candidat_pk, formation_pk) VALUES (?, ?)
			""";

	private static final String SQL_DELETE = "DELETE FROM candidats_formations WHERE id=?";

	private static final String SQL_BY_ID = """
			SELECT cf.id, cf.candidat_pk, cf.formation_pk,
			       c.id candidat_id, c.noms candidat_noms,
			       f.id formation_id, f.description formation_description, f.duree formation_duree
			FROM candidats_formations cf
			INNER JOIN candidats c ON cf.candidat_pk = c.id
			INNER JOIN formations f ON cf.formation_pk = f.id
			WHERE cf.id = ?
			""";

	private static final String SQL_ALL = """
			SELECT cf.id, cf.candidat_pk, cf.formation_pk,
			       c.id candidat_id, c.noms candidat_noms,
			       f.id formation_id, f.description formation_description, f.duree formation_duree
			FROM candidats_formations cf
			INNER JOIN candidats c ON cf.candidat_pk = c.id
			INNER JOIN formations f ON cf.formation_pk = f.id
			ORDER BY c.noms, f.description
			""";

	@Autowired
	private JdbcClient jdbcClient;

	@Autowired
	private JdbcSfmHelper sfmHelper;

	@Override
	public long create(CandidatFormation entity) {
		Object[] params = new Object[] { entity.getCandidatPk(), entity.getFormationPk() };
		KeyHolder holder = new GeneratedKeyHolder();
		jdbcClient.sql(SQL_INSERT).params(params).update(holder, "id");
		return holder.getKey().longValue();
	}

	@Override
	public void delete(long id) {
		jdbcClient.sql(SQL_DELETE).params(id).update();
	}

	@Override
	public CandidatFormation getById(long id) {
		return sfmHelper.queryJoinOne(SQL_BY_ID, CandidatFormation.class, new Object[] { id }, "id", "candidat_id",
				"formation_id");
	}

	@Override
	public List<CandidatFormation> get() {
		return sfmHelper.queryJoin(SQL_ALL, CandidatFormation.class, "id", "candidat_id", "formation_id");
	}

}
