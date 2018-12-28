package com.parkinghelper.parker.domain.types;

import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.usertype.UserType;

import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;


//Hibernate custom type
public class BoxUserType implements UserType {

    @Override
    public Object nullSafeGet(ResultSet resultSet, String[] names, SharedSessionContractImplementor sharedSessionContractImplementor, Object owner)
            throws HibernateException, SQLException {
        assert names.length == 1;
        if (resultSet.wasNull()) {
            return null;
        }
        final Zone box = new Zone(resultSet.getObject(names[0]).toString());
        return box;
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
        return Zone.class;
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