package com.broadcom.apdk.api;

import com.broadcom.apdk.api.annotations.ActionPack;

/**
 * <p>
 * Provides a basic implementation of {@link com.broadcom.apdk.api.IActionPack IActionPack} that
 * provides setters and getters for all fields. Furthermore it populates the fields with the 
 * values that were defined using an {@link com.broadcom.apdk.api.annotations.ActionPack ActionPack} 
 * annotation. 
 * </p>
 * <p>
 * <b>Note:</b> If the values for the fields <i>name</i>, <i>packageFormatVersion</i> and 
 * <i>version</i> are not overwritten they'll be initialized automatically. The field <i>name</i>
 * will contain the name of your class with the prefix "PCK.CUSTOM_", the field
 * <i>packageFormatVersion</i> will per default contain the value "0.3.0" and the field
 * <i>version</i> will contain the version defined in your project's POM file (the latter may 
 * not work in your IDE unless there's a MANIFEST file that contains this information) or "1.0.0" 
 * if no version information was found.
 * </p>
 * <b>Example:</b><br>
 * <pre>
 * &#64;ActionPack(
 *   name = "PCK.CUSTOM_SIMPLE",
 *   title = "My first Java action pack",
 *   category = "Custom Actions",
 *   company = "Broadcom",
 *   homepage = "http://www.broadcom.com"
 * )
 * public class CustomActionPack extends BaseActionPack {
 *   // You can leave the body empty...
 * }
 * </pre>
 */
public abstract class BaseActionPack implements IActionPack {
	
	private String name;
	private String buildNumber;
	private String category;
	private String company;
	private String dependencies;
	private String description;
	private String homepage;
	private String license;
	private String packageFormatVersion;
	private String title;
	private String version;
	private String documentation;
	private String licenses;
	

	public BaseActionPack() {
		ActionPack actionPackAnnotation = this.getClass().getAnnotation(ActionPack.class);
		if (actionPackAnnotation != null) {
			setName(!actionPackAnnotation.name().isEmpty() ? 
					actionPackAnnotation.name() : 
					"PCK.CUSTOM_" + getClass().getSimpleName().toUpperCase());
			setTitle(!actionPackAnnotation.title().isEmpty() ? 
					actionPackAnnotation.title() : null);
			setBuildNumber(!actionPackAnnotation.buildNumber().isEmpty() ? 
					actionPackAnnotation.buildNumber() : null);
			setCompany(!actionPackAnnotation.company().isEmpty() ? 
					actionPackAnnotation.company() : null);
			setHomepage(!actionPackAnnotation.homepage().isEmpty() ? 
					actionPackAnnotation.homepage() : null);
			setLicense(!actionPackAnnotation.license().isEmpty() ? 
					actionPackAnnotation.license() : null);
			setDependencies(!actionPackAnnotation.dependencies().isEmpty() ? 
					actionPackAnnotation.dependencies() : null);
			setDescription(!actionPackAnnotation.description().isEmpty() ? 
					actionPackAnnotation.description() : null);
			setCategory(!actionPackAnnotation.category().isEmpty() ? 
					actionPackAnnotation.category() : null);
			setPackageFormatVersion(!actionPackAnnotation.packageFormatVersion().isEmpty() ? 
					actionPackAnnotation.packageFormatVersion() : "0.3.0");
			setName(!actionPackAnnotation.name().isEmpty() ? 
					actionPackAnnotation.name() : null);
		}	
		else {
			setName("PCK.CUSTOM_" + getClass().getSimpleName().toUpperCase());
			setPackageFormatVersion("0.3.0");
		}
		if (version == null) {
			String version = this.getClass().getPackage().getImplementationVersion();
			setVersion(version != null ? version : "1.0.0");
		}
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}

	public String getBuildNumber() {
		return buildNumber;
	}

	public void setBuildNumber(String buildNumber) {
		this.buildNumber = buildNumber;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public String getDependencies() {
		return dependencies;
	}

	public void setDependencies(String dependencies) {
		this.dependencies = dependencies;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getHomepage() {
		return homepage;
	}

	public void setHomepage(String homepage) {
		this.homepage = homepage;
	}

	public String getLicense() {
		return license;
	}

	public void setLicense(String license) {
		this.license = license;
	}

	public String getPackageFormatVersion() {
		return packageFormatVersion;
	}

	public void setPackageFormatVersion(String packageFormatVersion) {
		this.packageFormatVersion = packageFormatVersion;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getDocumentation() {
		return documentation;
	}

	public void setDocumentation(String documentation) {
		this.documentation = documentation;
	}

	public String getLicenses() {
		return licenses;
	}

	public void setLicenses(String licenses) {
		this.licenses = licenses;
	}
}
