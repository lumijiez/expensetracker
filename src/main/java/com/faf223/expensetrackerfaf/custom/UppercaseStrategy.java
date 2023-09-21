package com.faf223.expensetrackerfaf.custom;

import org.hibernate.boot.model.naming.Identifier;
import org.hibernate.boot.model.naming.PhysicalNamingStrategy;
import org.hibernate.engine.jdbc.env.spi.JdbcEnvironment;

public class UppercaseStrategy implements PhysicalNamingStrategy {

    @Override
    public Identifier toPhysicalCatalogName(Identifier identifier, JdbcEnvironment jdbcEnvironment) {
        return identifier; // No modification for catalog name
    }

    @Override
    public Identifier toPhysicalSchemaName(Identifier identifier, JdbcEnvironment jdbcEnvironment) {
        return identifier; // No modification for schema name
    }

    @Override
    public Identifier toPhysicalTableName(Identifier name, JdbcEnvironment context) {
        String tableName = name.getText();
        return new Identifier(tableName, name.isQuoted());
    }

    @Override
    public Identifier toPhysicalSequenceName(Identifier identifier, JdbcEnvironment jdbcEnvironment) {
        return identifier; // No modification for sequence name
    }

    @Override
    public Identifier toPhysicalColumnName(Identifier identifier, JdbcEnvironment jdbcEnvironment) {
        return identifier; // No modification for column name
    }

    @Override
    public Identifier toPhysicalTypeName(Identifier logicalName, JdbcEnvironment jdbcEnvironment) {
        return PhysicalNamingStrategy.super.toPhysicalTypeName(logicalName, jdbcEnvironment);
    }
}
