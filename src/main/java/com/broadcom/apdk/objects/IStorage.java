package com.broadcom.apdk.objects;

import java.util.List;

public interface IStorage extends IAutomicObject {
	
	List<StoredFile> getStoredFiles();

	void setStoredFiles(List<StoredFile> storedFiles);

}
