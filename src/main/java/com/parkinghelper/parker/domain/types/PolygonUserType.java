package com.parkinghelper.parker.domain.types;

import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.usertype.UserType;
import org.postgresql.geometric.PGpolygon;

import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;


//Hibernate custom type
public class PolygonUserType implements UserType {

    @Override
    public Object nullSafeGet(ResultSet resultSet, String[] names, SharedSessionContractImplementor sharedSessionContractImplementor, Object owner)
            throws HibernateException, SQLException {
        if (resultSet.wasNull()) {
            return null;
        }
        final PGpolygon polygon = new PGpolygon(resultSet.getObject(names[0]).toString());
        return polygon;
    }

    @Override
    public void nullSafeSet(PreparedStatement statement, Object value, int index, SharedSessionContractImplementor sharedSessionContractImplementor)
            throws HibernateException, SQLException {
        statement.setObject(index, value);
    }

    @Override
    public int[] sqlTypes() {
        return new int[]
                {
                        Types.VARCHAR
                };
    }

    @SuppressWarnings("rawtypes")
    @Override
    public Class returnedClass() {
        return PGpolygon.class;
    }

    @Override
    public boolean equals(Object o, Object o1) throws HibernateException {
        if (o == o1) {
            return true;
        }
        if (o == null || o1 == null) {
            return false;
        } else {
            return o.equals(o1);
        }
    }

    @Override
    public int hashCode(Object o) throws HibernateException {
        return o.hashCode();
    }

    @Override
    public Object deepCopy(Object o) throws HibernateException {
        return o;
    }

    @Override
    public boolean isMutable() {
        return true;
    }

    @Override
    public Serializable disassemble(Object o) throws HibernateException {
        return (Serializable) o;
    }

    @Override
    public Object assemble(Serializable srlzbl, Object o) throws HibernateException {
        return srlzbl;
    }

    @Override
    public Object replace(final Object original, final Object target, final Object owner) throws HibernateException {
        return original;
    }

}