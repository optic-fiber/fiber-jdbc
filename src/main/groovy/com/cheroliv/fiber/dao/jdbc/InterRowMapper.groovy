package com.cheroliv.fiber.dao.jdbc

import com.cheroliv.fiber.domain.Inter
import org.springframework.jdbc.core.RowMapper

import java.sql.ResultSet
import java.sql.SQLException

class InterRowMapper implements RowMapper<Inter> {

    @Override
    Inter mapRow(ResultSet rs, int line) throws SQLException {
        new InterResultSetExtractor().extractData rs
    }

}