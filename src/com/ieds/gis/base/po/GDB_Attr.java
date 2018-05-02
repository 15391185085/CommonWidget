package com.ieds.gis.base.po;

import javax.persistence.Transient;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.sql.Blob;
import java.io.Serializable;

@Entity
@Table(name = "GDB_Attr")
public class GDB_Attr implements Serializable {
	@Id
	private Integer id;

	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	private String table_name;

	public void setTable_name(String table_name) {
		this.table_name = table_name;
	}

	public String getTable_name() {
		return table_name;
	}

	private String attr_label;

	public void setAttr_label(String attr_label) {
		this.attr_label = attr_label;
	}

	public String getAttr_label() {
		return attr_label;
	}

	private String field_key;

	public String getField_key() {
		return field_key;
	}

	public void setField_key(String field_key) {
		this.field_key = field_key;
	}

	private Integer max_value;

	public void setMax_value(Integer max_value) {
		this.max_value = max_value;
	}

	public Integer getMax_value() {
		return max_value;
	}

	private String data_show;

	public void setData_show(String data_show) {
		this.data_show = data_show;
	}

	public String getData_show() {
		return data_show;
	}

	private String data_insert;

	public void setData_insert(String data_insert) {
		this.data_insert = data_insert;
	}

	public String getData_insert() {
		return data_insert;
	}

	private Integer show_style;

	public void setShow_style(Integer show_style) {
		this.show_style = show_style;
	}

	public Integer getShow_style() {
		return show_style;
	}

	private Integer edit_style;

	public void setEdit_style(Integer edit_style) {
		this.edit_style = edit_style;
	}

	public Integer getEdit_style() {
		return edit_style;
	}

	private Integer is_edit;

	public void setIs_edit(Integer is_edit) {
		this.is_edit = is_edit;
	}

	public Integer getIs_edit() {
		return is_edit;
	}

	private Integer enable_null;

	public void setEnable_null(Integer enable_null) {
		this.enable_null = enable_null;
	}

	public Integer getEnable_null() {
		return enable_null;
	}

	private Integer order_num;

	public void setOrder_num(Integer order_num) {
		this.order_num = order_num;
	}

	public Integer getOrder_num() {
		return order_num;
	}

	private Integer isvisible;

	public void setIsvisible(Integer isvisible) {
		this.isvisible = isvisible;
	}

	public Integer getIsvisible() {
		return isvisible;
	}

	public Integer is_oneline;

	public Integer getIs_oneline() {
		return is_oneline;
	}

	public void setIs_oneline(Integer is_oneline) {
		this.is_oneline = is_oneline;
	}

	public GDB_Attr() {
		super();
	}
}
