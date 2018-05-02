/*
 * Copyright (c) 2013. wyouflf (wyouflf@gmail.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.ieds.gis.base.dao;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import com.lidroid.xutils.db.sqlite.WhereBuilder;
import com.lidroid.xutils.db.table.MyTable;
import com.lidroid.xutils.exception.DbException;
import com.lidroid.xutils.util.StringUtil;

/**
 * Author: wyouflf Date: 13-8-9 Time: 下午10:19
 */
public interface ISelector {
	 
	public WhereBuilder getWhereBuilder();

	public ISelector where(WhereBuilder whereBuilder);
	public ISelector where(String columnName, String op, Object value);

	public ISelector and(String columnName, String op, Object value);

	public ISelector or(String columnName, String op, Object value);

	public ISelector orderBy(String columnName);

	public ISelector orderBy(String columnName, boolean desc);

	public ISelector limit(int limit);

	public ISelector offset(int offset);
 
	public String getSelectSql() ;

	public Class<?> getEntityType() ;

}
