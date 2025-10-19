package com.example.cabs;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class DatasourceSmokeTest {

    @Autowired
    DataSource dataSource;

    @Test
    void canListTablesInCurrentSchema() throws Exception {
        String sql = """
            SELECT table_name
            FROM information_schema.tables
            WHERE table_schema = DATABASE()
            ORDER BY table_name
            """;

        try (Connection c = dataSource.getConnection();
             PreparedStatement ps = c.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            List<String> tables = new ArrayList<>();
            while (rs.next()) tables.add(rs.getString(1));

            System.out.println("Tables: " + tables);

            assertThat(tables.size()).isGreaterThan(0);
        }
    }
}
