package com.epam.esm.persistence.mappers;

import com.epam.esm.entity.User;
import com.epam.esm.persistence.constant.CertificateTableColumnName;
import com.epam.esm.persistence.constant.UserTableColumnName;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Instant;

public class UserMapper implements RowMapper<User> {
    @Override
    public User mapRow(ResultSet rs, int rowNum) throws SQLException {
        User user = new User();

        user.setId(rs.getInt(UserTableColumnName.ID));
        user.setName(rs.getString(UserTableColumnName.NAME));
        user.setSurname(rs.getString(UserTableColumnName.SURNAME));
        user.setCreateDate(Instant.ofEpochMilli(rs.getLong(CertificateTableColumnName.CREATE_DATE)));
        user.setLastUpdateDate(Instant.ofEpochMilli(rs.getLong(CertificateTableColumnName.LAST_UPDATE_DATE)));
        return user;
    }
}
