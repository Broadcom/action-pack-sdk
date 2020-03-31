package com.broadcom.apdk.api;

public interface IActionPack {

	public String getName();

	public void setName(String name);

	public String getBuildNumber();

	public void setBuildNumber(String buildNumber);

	public String getCategory();

	public void setCategory(String category);

	public String getCompany();

	public void setCompany(String company);

	public String getDependencies();

	public void setDependencies(String dependencies);

	public String getDescription();

	public void setDescription(String description);

	public String getHomepage();

	public void setHomepage(String homepage);

	public String getLicense();

	public void setLicense(String license);

	public String getPackageFormatVersion();

	public void setPackageFormatVersion(String packageFormatVersion);

	public String getTitle();

	public void setTitle(String title);

	public String getVersion();

	public void setVersion(String version);
	
	public String getDocumentation();
	
	public void setDocumentation(String documentation);
	
	public String getLicenses();

	public void setLicenses(String licenses);
}