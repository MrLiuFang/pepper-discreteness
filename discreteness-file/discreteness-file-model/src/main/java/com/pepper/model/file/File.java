package com.pepper.model.file;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicUpdate;

import com.pepper.core.base.BaseModel;

/**
 *
 * @author mrliu
 *
 */
@Entity()
@Table(name = "t_file")
@DynamicUpdate(true)
public class File extends BaseModel {

	/**
	 *
	 */
	private static final long serialVersionUID = -6880972629931509178L;

	@Column(name = "file_id")
	private String fileId;

	@Column(name = "storage_type_name")
	private String storageTypeName;

	@Column(name = "name")
	private String name;

	@Column(name = "size")
	private Integer size;

	@Column(name = "url")
	private String url;

	public String getStorageTypeName() {
		return storageTypeName;
	}

	public void setStorageTypeName(String storageTypeName) {
		this.storageTypeName = storageTypeName;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getSize() {
		return size;
	}

	public void setSize(Integer size) {
		this.size = size;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getFileId() {
		return fileId;
	}

	public void setFileId(String fileId) {
		this.fileId = fileId;
	}



}
