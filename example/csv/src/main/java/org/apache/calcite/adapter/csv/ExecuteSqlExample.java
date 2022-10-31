/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to you under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.calcite.adapter.csv;

import java.sql.*;

/**
 * an example to debug sql
 * sql parse; sql validate; sql optimize
 *
 * 加个中文试下
 */
public class ExecuteSqlExample {

  public static void main(String[] args) {

    try(Connection connection = DriverManager.getConnection("jdbc:calcite:model=E:\\code\\calcite" +
        "\\example\\csv\\src\\test\\resources" +
        "\\model.json")) {
      Statement statement = connection.createStatement();
      String sql = "SELECT NAME, COUNT(DEPTNO) FROM (SELECT DEPTNO, NAME FROM DEPTS D1 inner join DEPTS D2 using(DEPTNO) ORDER BY NAME) WHERE NAME = 'sunshy'";


      ResultSet resultSet = statement.executeQuery(
          sql);

      ResultSetMetaData metaData = resultSet.getMetaData();

      System.out.println(metaData.getColumnName(1));
      System.out.println(metaData.getColumnName(2));

    } catch (SQLException sqlException) {

      // ignore
    }

  }

}
