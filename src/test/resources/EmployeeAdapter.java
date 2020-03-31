package com.broadcom;

import com.broadcom.apdk.api.ParamAdapter;

public class EmployeeAdapter extends ParamAdapter<Employee> {

	@Override
	public String convertToString(Employee value) {
		String firstname = value.getFirstname() != null ? value.getFirstname() : "";
		String lastname = value.getLastname() != null ? value.getLastname() : "";
		return "\"" + firstname + "\",\"" + lastname + "\"";
	}

	@Override
	public Employee convertToType(String value) {
		if (value != null && !value.isEmpty()) {
			String[] fragments = value.split(",");
			if (fragments.length == 2) {
				String firstname = null;
				String lastname = null;
				if (fragments[0].startsWith("\"") && fragments[0].endsWith("\"") && fragments[0].length() > 2) {
					firstname = fragments[0].substring(1, fragments[0].length() - 1);
					firstname = firstname.isEmpty() ? null : firstname;
				}
				if (fragments[1].startsWith("\"") && fragments[1].endsWith("\"") && fragments[1].length() > 2) {
					lastname = fragments[1].substring(1, fragments[1].length() - 1);
					lastname = lastname.isEmpty() ? null : lastname;
				}
				return new Employee(firstname, lastname);
			}
		}
		return null;
	}

}
