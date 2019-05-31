package com.cheroliv.fiber.dao.jdbc

import com.cheroliv.fiber.domain.Inter
import org.springframework.dao.DataAccessException
import org.springframework.jdbc.core.ResultSetExtractor

import java.sql.ResultSet
import java.sql.SQLException

class InterResultSetExtractor implements ResultSetExtractor<Inter> {
    @Override
    Inter extractData(ResultSet rs) throws SQLException, DataAccessException {
        new Inter(
                id: rs.getInt(1),
                nd: rs.getString(2),
                nom: rs.getString(3),
                prenom: rs.getString(4),
                heure: rs.getDate(5).toLocalDateTime().hour,
                date: rs.getDate(6).toLocalDate(),
                contrat: rs.getString(7),
                type: rs.getString(8))
    }
}