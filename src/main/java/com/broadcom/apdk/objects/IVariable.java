package com.broadcom.apdk.objects;

import java.util.List;

public interface IVariable extends IAutomicObject {
	
	void setSortColumn(Integer sortColumn);
	
	Integer getSortColumn();
	
	void setSortDirection(SortDirection sortDirection);
	
	SortDirection getSortDirection();
	
	void setIfKeyNotFoundAction(KeyNotFoundAction action); 
	
	KeyNotFoundAction getKeyNotFoundAction();
	
    <T> List<KeyValueGroup<T>> getValues();
	
}
