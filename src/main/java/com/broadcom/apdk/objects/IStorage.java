package com.broadcom.apdk.objects;

import java.util.List;

public interface IStorage extends IAutomicObject {
	
	public List<StoredFile> getStoredFiles();

	public void setStoredFiles(List<StoredFile> storedFiles);

}
