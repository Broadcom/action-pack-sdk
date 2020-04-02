package com.broadcom.apdk.api;

public interface IActionPack {

	String getName();

	void setName(String name);

	String getBuildNumber();

	void setBuildNumber(String buildNumber);

	String getCategory();

	void setCategory(String category);

	String getCompany();

	void setCompany(String company);

	String getDependencies();

	void setDependencies(String dependencies);

	String getDescription();

	void setDescription(String description);

	String getHomepage();

	void setHomepage(String homepage);

	String getLicense();

	void setLicense(String license);

	String getPackageFormatVersion();

	void setPackageFormatVersion(String packageFormatVersion);

	String getTitle();

	void setTitle(String title);

	String getVersion();

	void setVersion(String version);
	
	String getDocumentation();
	
	void setDocumentation(String documentation);
	
	String getLicenses();

	void setLicenses(String licenses);
}