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
package org.jpos.atmc.ndc;

public class NDCCommand 
{
	public static final String GO_IN_SERVICE                  = "1";
	public static final String GO_OUT_SERVICE                 = "2";
	public static final String SEND_CONFIGURATION_ID          = "3";
	public static final String SEND_SUPPLY_COUNTERS           = "4";
	public static final String SEND_TALLY_INFORMATION         = "5";
	public static final String SEND_CONFIGURATION_INFORMATION = "7";
	public static final String SEND_DATE_AND_TIME_INFORMATION = "8";
}
