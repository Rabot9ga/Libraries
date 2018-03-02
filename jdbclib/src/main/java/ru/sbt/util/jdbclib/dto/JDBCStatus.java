package ru.sbt.util.jdbclib.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class JDBCStatus {
    Status status;
    String message;
}
