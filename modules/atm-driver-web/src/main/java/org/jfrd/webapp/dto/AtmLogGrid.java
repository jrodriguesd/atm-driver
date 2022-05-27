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
package org.jfrd.webapp.dto;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.ws.rs.core.MultivaluedMap;

import org.jpos.atmc.dao.ATMLogManager;
import org.jpos.atmc.model.ATMLog;
import org.jpos.atmc.model.Screen;
import org.jpos.atmc.util.Log;
import org.jpos.atmc.util.Util;
import org.jpos.ee.DB;
import org.json.JSONArray;
import org.json.JSONObject;
import org.jpos.atmc.dao.ATMLogManager;

import org.jpos.atmc.model.ATMLog;

import com.fasterxml.jackson.annotation.JsonProperty;

public class AtmLogGrid {
    @JsonProperty("page")
    private Integer page;

    @JsonProperty("total")
    private Integer totalPages;

    @JsonProperty("records")
    private Integer recordCount;

    @JsonProperty("rows")
    private List<AtmLogGridEntry> rows;
    
	public AtmLogGrid() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @return the page
	 */
	public Integer getPage() {
		return page;
	}

	/**
	 * @param page the page to set
	 */
	public void setPage(Integer page) {
		this.page = page;
	}

	/**
	 * @return the total
	 */
	public Integer getTotalPages() {
		return totalPages;
	}

	/**
	 * @param total the total to set
	 */
	public void setTotalPages(Integer totalPages) {
		this.totalPages = totalPages;
	}

	/**
	 * @return the records
	 */
	public Integer getRecordCount() {
		return recordCount;
	}

	/**
	 * @param records the records to set
	 */
	public void setRecordCount(Integer recordCount) {
		this.recordCount = recordCount;
	}

	/**
	 * @return the rows
	 */
	public List<AtmLogGridEntry> getRows() {
		return rows;
	}

	/**
	 * @param rows the rows to set
	 */
	public void setRows(List<AtmLogGridEntry> rows) {
		this.rows = rows;
	}

	// private static List<String> reservedParamNames = Arrays.asList("nd", "page", "sord", "rows", "_search", "sidx");
	
	private static String buildWhereCondition(String filtersStr)
	{
		if (filtersStr == null || filtersStr.length() <= 1 ) return "";

        JSONObject filters = new JSONObject(filtersStr);
        JSONArray rules = filters.getJSONArray("rules");
        
		StringBuilder sb = new StringBuilder();
        for (int i = 0; i < rules.length(); i++)
        {
            String field = rules.getJSONObject(i).getString("field");
    		Log.staticPrintln("JFRD " + Util.fileName() + " Line " + Util.lineNumber() + " " + Util.methodName() + " field " + field );
            String op = rules.getJSONObject(i).getString("op");
    		Log.staticPrintln("JFRD " + Util.fileName() + " Line " + Util.lineNumber() + " " + Util.methodName() + " op " + op );
            String data = rules.getJSONObject(i).getString("data");
    		Log.staticPrintln("JFRD " + Util.fileName() + " Line " + Util.lineNumber() + " " + Util.methodName() + " data " + data );

    		if (sb.length() > 0)
    			sb.append(" AND ");
        
    		sb.append(field + " LIKE '%" + data + "%'");
        }        
		if (sb.length() > 0)
            return " where " + sb.toString();
		else
			return "";

        /*		
		StringBuilder sb = new StringBuilder();
        for (String paramName: queryParams.keySet()) {
        	
        	if (! reservedParamNames.contains(paramName))
        	{
        		if (sb.length() > 0)
        			sb.append(" AND ");
            	String paramValue = queryParams.getFirst(paramName);
        		sb.append(paramName + " LIKE '" + paramValue + "%'");
        		Log.staticPrintln("JFRD " + Util.fileName() + " Line " + Util.lineNumber() + " " + Util.methodName() + " paramName " + paramName + " paramValue " + paramValue );
        	}
        }
		Log.staticPrintln("JFRD " + Util.fileName() + " Line " + Util.lineNumber() + " " + Util.methodName() + " sb.length() " + sb.length() );
		if (sb.length() > 0)
            return " where " + sb.toString();
		else
			return "";
		*/
	}
	
	
	public static AtmLogGrid getAtmLogGrid(MultivaluedMap<String, String> queryParams) {
        String sidx = queryParams.getFirst("sidx");                        // $_GET['sidx']; 
        String sord = queryParams.getFirst("sord");                        // $_GET['sord'];
        String filtersStr = queryParams.getFirst("filters");
        String orderBy = sidx + " " + sord; 
        
        String whereCondition = buildWhereCondition(filtersStr);

        int page        = Util.str2Int( queryParams.getFirst("page") );    // $_GET['page']; 
        int limit       = Util.str2Int(  queryParams.getFirst("rows") );   // $_GET['rows'];
        int recordCount = 0;
        int totalPages  = 0;

    	try 
		{
		    recordCount = DB.exec(db -> new ATMLogManager(db).getItemCount(whereCondition) );

	        if( recordCount > 0 && limit > 0) totalPages = (int) Math.ceil( (double) recordCount/limit ); 

	        if ( page > totalPages) page = totalPages;
	        
	        int wrk = (limit * page) - limit;

	        int start;
	        if(wrk < 0) 
	        	start = 0;
	        else
	        	start = wrk;

	        List<ATMLog> atmLogs = DB.exec(db -> new ATMLogManager(db).getAll(whereCondition, orderBy, start, limit) );
    		Log.staticPrintln("JFRD " + Util.fileName() + " Line " + Util.lineNumber() + " " + Util.methodName() + " count " + recordCount );

    		AtmLogGrid atmLogGrid = new AtmLogGrid();
    		List<AtmLogGridEntry> rows = new ArrayList<AtmLogGridEntry>();
	        for (int i = 0; i < atmLogs.size(); i++)
	        {
	        	ATMLog atmLog = atmLogs.get(i);
	        	AtmLogGridEntry atmLogGridEntry = new AtmLogGridEntry();
	        	atmLogGridEntry.setId( atmLog.getId() );
	        	atmLogGridEntry.setIp( atmLog.getIp() );
	        	atmLogGridEntry.setLuno( atmLog.getLuno() );
	        	atmLogGridEntry.setMessageClass( atmLog.getMessageClass() );
	        	atmLogGridEntry.setAtmRequestDt( atmLog.getAtmRequestDt() );
	        	atmLogGridEntry.setCard( atmLog.getCard() );
	        	atmLogGridEntry.setAmount( atmLog.getAmount() );
	        	rows.add(atmLogGridEntry);
	        	
	    		Log.staticPrintln("JFRD " + Util.fileName() + " Line " + Util.lineNumber() + " " + Util.methodName() + " atmLog " + atmLog.toString() );
	        }

	        atmLogGrid.setPage(page);
	        atmLogGrid.setTotalPages(totalPages);
	        atmLogGrid.setRecordCount(recordCount);
	        atmLogGrid.setRows(rows);
	        return atmLogGrid;
		} 
		catch (Exception e) 
		{
			e.printStackTrace(Log.out);
		}
		return null;
	}

}
