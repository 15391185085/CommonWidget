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

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteException;

import com.lidroid.xutils.db.sqlite.SqlInfo;
import com.lidroid.xutils.db.sqlite.WhereBuilder;
import com.lidroid.xutils.db.table.KeyValue;
import com.lidroid.xutils.db.table.MyId;
import com.lidroid.xutils.db.table.MyTable;
import com.lidroid.xutils.exception.DbException;
import com.lidroid.xutils.util.LogUtils;
import com.lidroid.xutils.util.StringUtil;

/**
 * po类必须有构造函数 带外键的表不能用复合主键
 * 
 * 注意: 复合主键需要用saveOrUpdateDoubleKey来做“增，改”的业务 复合主键需要用deleteDoubleKey来做“删除”的业务
 * 
 * @update 2014-11-12 上午11:08:31<br>
 * @author <a href="mailto:lihaoxiang@ieds.com.cn">李昊翔</a>
 * 
 */
public interface IDbUtils {

	public ISelector from(Class<?> entityType) throws DbException;

	public List<List<String>> execListQuery(SqlInfo sqlInfo) throws DbException;

	/**
	 * 前List是查询结果的列, 后List<String>是查询结果的行
	 * 
	 * @param sql
	 * @return
	 * @throws DbException
	 */
	public List<List<String>> execListQuery(String sql) throws DbException;

	public IDbUtils configDebug(boolean debug);

	public IDbUtils configAllowTransaction(boolean allowTransaction);

	public void ignore(Object entity) throws DbException;

	public <T> void ignore(List<T> entities) throws DbException;

	/**
	 * @param entity
	 * @return
	 * @throws DbException
	 */
	public <T> T findFirstByIdEnableNull(T entity) throws DbException;

	/**
	 * @param entity
	 * @return
	 * @throws DbException
	 */
	public <T> T findFirstById(T entity) throws DbException;

	public void replace(Object entity) throws DbException;

	public <T> void replace(List<T> entities) throws DbException;

	public void save(Object entity) throws DbException;

	public <T> void save(List<T> entities) throws DbException;

	public void delete(Object entity) throws DbException;

	public void deleteById(Object entity) throws DbException;

	public <T> void deleteById(List<T> entities) throws DbException;

	public <T> void delete(List<T> entities) throws DbException;

	public void delete(Class<?> entityType, WhereBuilder whereBuilder)
			throws DbException;

	/**
	 * 根据id更新对象数据
	 * 
	 * @param entity
	 * @throws DbException
	 */
	public void updateById(Object entity) throws DbException;

	/**
	 * 根据id更新所有对象数据
	 * 
	 * @param entity
	 * @throws DbException
	 */
	public <T> void updateById(List<T> entities) throws DbException;

	public void updateByWhere(Object entity, WhereBuilder whereBuilder)
			throws DbException;

	/**
	 * 查询结果不能为空，为空时抛出异常
	 * 
	 * @param entity
	 * @return
	 * @throws DbException
	 */
	public <T> T findFirstEnableNull(Object entity) throws DbException;

	public <T> T findFirstEnableNull(ISelector selector) throws DbException;

	/**
	 * 查询结果不能为空，为空时抛出异常
	 * 
	 * @param selector
	 * @return
	 * @throws DbException
	 */
	public <T> T findFirst(ISelector selector) throws DbException;

	/**
	 * 查询结果不能为空，为空时抛出异常
	 * 
	 * @param entity
	 * @return
	 * @throws DbException
	 */
	public <T> T findFirst(Object entity) throws DbException;

	public <T> List<T> findAll(ISelector selector) throws DbException;

	public <T> List<T> findAll(Object entity) throws DbException;

	/**
	 * 根据对象的属性查询该对象的完整属性
	 * 
	 * @param entity
	 * @return
	 * @throws DbException
	 */
	public ISelector getSelector(Object entity) throws DbException;

	/**
	 * 根据对象的id属性查询该对象的完整属性
	 * 
	 * @param entity
	 * @return
	 */
	public ISelector getSelectorById(Object entity) throws DbException;

	public void dropDb() throws DbException;

	public void dropTable(Class<?> entityType) throws DbException;

	public void beginTransaction() throws DbException;

	public void setTransactionSuccessful();

	public void endTransaction() throws DbException;

	public void execNonQuery(SqlInfo sqlInfo) throws DbException;

	public void execNonQuery(String sql) throws DbException;
}
