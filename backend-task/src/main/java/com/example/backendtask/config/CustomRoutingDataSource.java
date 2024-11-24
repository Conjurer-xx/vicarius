package com.example.backendtask.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;
import javax.sql.DataSource;
import java.time.LocalTime;

public class CustomRoutingDataSource extends AbstractRoutingDataSource {

    private String previousDbKey = "";

    @Override
    protected Object determineCurrentLookupKey() {
        LocalTime currentTime = LocalTime.now();
        String currentDbKey = isMySqlActive(currentTime) ? "MYSQL" : "MOCK_DB";

        // Check if the database has changed
        if (!currentDbKey.equals(previousDbKey)) {
            previousDbKey = currentDbKey;
            // Print out the active database for logging purposes
            System.out.println("Switched to: " + currentDbKey);
        }

        return currentDbKey;
    }

    private boolean isMySqlActive(LocalTime currentTime) {
        return currentTime.isBefore(LocalTime.of(18, 0)); // MySQL is active before 6:00 PM
    }
}
