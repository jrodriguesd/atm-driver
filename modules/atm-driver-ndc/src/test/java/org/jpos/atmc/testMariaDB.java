/*
 * This file is part of atm-driver.
 * Copyright (C) 2021-2022
 *
 * atm-driver is free software: you can redistribute it and/or modify it
 * under the terms of the GNU Affero General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or (at
 * your option) any later version.
 *
 * atm-driver is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public
 * License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with atm-driver. If not, see <http://www.gnu.org/licenses/>.
 */

/**
 * @author <a href="mailto:j@rodriguesd.org">Jose Rodrigues D.</a>
 */
package org.jpos.atmc;

import java.sql.*;  

class testMariaDB
{
    public static void main(String[] args) 
	{
        String url = "jdbc:mariadb://localhost:3306"; //pointing to no database.
        String username = "weg";
        String password = "lorena_01";
		// Class.forName("org.mariadb.jdbc.Driver")
    
        System.out.println("Connecting to server...");
    
        try (Connection connection = DriverManager.getConnection(url, username, password)) {
            System.out.println("Server connected!");
            Statement stmt = null;
            ResultSet resultset = null;
    
            try {
                stmt = connection.createStatement();
                resultset = stmt.executeQuery("SHOW DATABASES;");
    
                if (stmt.execute("SHOW DATABASES;")) {
                    resultset = stmt.getResultSet();
                }
    
                while (resultset.next()) {
                    System.out.println(resultset.getString("Database"));
                }
            }
            catch (SQLException ex){
                // handle any errors
                ex.printStackTrace();
            }
            finally {
                // release resources
                if (resultset != null) {
                    try {
                        resultset.close();
                    } catch (SQLException sqlEx) { }
                    resultset = null;
                }
    
                if (stmt != null) {
                    try {
                        stmt.close();
                    } catch (SQLException sqlEx) { }
                    stmt = null;
                }
    
                if (connection != null) {
                    connection.close();
                }
            }
        } catch (SQLException e) {
            throw new IllegalStateException("Cannot connect the server!", e);
        }
    }
	
}
