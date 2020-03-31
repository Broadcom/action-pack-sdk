package com.broadcom.apdk.objects;

import java.util.List;

public interface IVariable extends IAutomicObject {
	
	public void setSortColumn(Integer sortColumn);
	
	public Integer getSortColumn();
	
	public void setSortDirection(SortDirection sortDirection);
	
	public SortDirection getSortDirection();
	
	public void setIfKeyNotFoundAction(KeyNotFoundAction action); 
	
	public KeyNotFoundAction getKeyNotFoundAction();
	
    public <T> List<KeyValueGroup<T>> getValues();
	
}
