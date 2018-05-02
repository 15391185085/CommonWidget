package com.ieds.gis.base.po;

import javax.persistence.Transient;
import javax.persistence.Id;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.sql.Blob;
import java.io.Serializable;

@Entity
@Table(name = "GDB_Table")
public class GDB_Table implements Serializable {
	@Id
	private String table_name;

	public void setTable_name(String table_name) {
		this.table_name = table_name;
	}

	public String getTable_name() {
		return table_name;
	}

	private String table_label;

	public void setTable_label(String table_label) {
		this.table_label = table_label;
	}

	public String getTable_label() {
		return table_label;
	}

	public GDB_Table() {
		super();
	}
}
