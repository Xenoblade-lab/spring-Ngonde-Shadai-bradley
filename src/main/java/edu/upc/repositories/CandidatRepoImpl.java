package edu.upc.repositories;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import edu.upc.models.Candidat;
import edu.upc.models.Formation;
import edu.upc.utils.JdbcSfmHelper;

@Repository
public class CandidatRepoImpl implements CandidatRepoCustom {

	private static final String SQL_INSERT = """
			INSERT INTO candidats (profession_pk, noms, genre, etat_civil, lieu_nais, date_nais)
			VALUES (?, ?, ?, ?, ?, ?)
			""";

	private static final String SQL_UPDATE = """
			UPDATE candidats
			SET profession_pk=?, noms=?, genre=?, etat_civil=?, lieu_nais=?, date_nais=?
			WHERE id=?
			""";

	private static final String SQL_DELETE = "DELETE FROM candidats WHERE id=?";

	private static final String SQL_BY_ID = """
			SELECT c.id, c.profession_pk, c.noms, c.genre, c.etat_civil, c.lieu_nais, c.date_nais,
			       p.id profession_id, p.description profession_description
			FROM candidats c
			INNER JOIN professions p ON c.profession_pk = p.id
			WHERE c.id = ?
			""";

	private static final String SQL_WITH_PROFESSION = """
			SELECT c.id, c.profession_pk, c.noms, c.genre, c.etat_civil, c.lieu_nais, c.date_nais,
			       p.id profession_id, p.description profession_description
			FROM candidats c
			INNER JOIN professions p ON c.profession_pk = p.id
			ORDER BY c.noms
			""";

	private static final String SQL_SEARCH = """
			SELECT c.id, c.profession_pk, c.noms, c.genre, c.etat_civil, c.lieu_nais, c.date_nais,
			       p.id profession_id, p.description profession_description
			FROM candidats c
			INNER JOIN professions p ON c.profession_pk = p.id
			WHERE c.noms LIKE ?
			ORDER BY c.noms
			""";

	private static final String SQL_FORMATIONS = """
			SELECT f.id, f.description, f.duree
			FROM formations f
			INNER JOIN candidats_formations cf ON cf.formation_pk = f.id
			WHERE cf.candidat_pk = ?
			ORDER BY f.description
			""";

	@Autowired
	private JdbcClient jdbcClient;

	@Autowired
	private JdbcSfmHelper sfmHelper;

	@Override
	public long create(Candidat entity) {
		Object[] params = new Object[] {
				entity.getProfessionPk(),
				entity.getNoms(),
				entity.getGenre(),
				entity.getEtatCivil(),
				entity.getLieuNais(),
				entity.getDateNais()
		};
		KeyHolder holder = new GeneratedKeyHolder();
		jdbcClient.sql(SQL_INSERT).params(params).update(holder, "id");
		return holder.getKey().longValue();
	}

	@Override
	public void update(long id, Candidat entity) {
		jdbcClient.sql(SQL_UPDATE).params(
				entity.getProfessionPk(),
				entity.getNoms(),
				entity.getGenre(),
				entity.getEtatCivil(),
				entity.getLieuNais(),
				entity.getDateNais(),
				id).update();
	}

	@Override
	public void delete(long id) {
		jdbcClient.sql(SQL_DELETE).params(id).update();
	}

	@Override
	public Candidat getById(long id) {
		return getWithProfessionById(id);
	}

	@Override
	public List<Candidat> getWithProfession() {
		return sfmHelper.queryJoin(SQL_WITH_PROFESSION, Candidat.class, "id", "profession_id");
	}

	@Override
	public Candidat getWithProfessionById(long id) {
		Candidat row = sfmHelper.queryJoinOne(SQL_BY_ID, Candidat.class, new Object[] { id }, "id", "profession_id");
		if (row != null) {
			row.setFormations(getFormations(id));
		}
		return row;
	}

	@Override
	public List<Formation> getFormations(long candidatId) {
		return sfmHelper.query(SQL_FORMATIONS, Formation.class, new Object[] { candidatId });
	}

	@Override
	public List<Candidat> search(String keyword) {
		if (keyword == null || keyword.isBlank()) {
			return getWithProfession();
		}
		return sfmHelper.queryJoin(SQL_SEARCH, Candidat.class, new Object[] { "%" + keyword.trim() + "%" }, "id",
				"profession_id");
	}

}
