package ru.sbt.util.jdbclib.dto;

public class JDBCPojoFactory {

   public static JDBCPojo getPojo(){
       return new JDBCPojoImpl();
   }
}
