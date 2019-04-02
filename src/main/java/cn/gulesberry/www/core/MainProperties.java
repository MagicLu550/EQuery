package cn.gulesberry.www.core;

import java.io.IOException;

import cn.gulesberry.www.properties.SimpleProperties;

public class MainProperties {
	SimpleProperties properties;
	static MainProperties mp;
	static {
		try {
			mp = new MainProperties();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public MainProperties() throws IOException {
		properties = new SimpleProperties(this,"properties/driver");
	}
	
	public String getInstancePath() {
		return properties.getString("equery.driver.instance.path", "");
	}
	public String getMavenPath() {
		return properties.getString("equery.driver.maven.path","");
	}
	public String getTestPath() {
		return properties.getString("equery.test.path","");
	}
	public String getVersionConnectionPath() {
		return properties.getString("equery.version.connection.path", "");
	}
	public String getApplicationPath() {
		return properties.getString("equery.application.connection.path","");
	}
	public String getVersionInfo() {
		return properties.getString("equery.version.info.path","");
	}
	public String getStartDTD() {
		return properties.getString("equery.application.dtd.path", "");
	}
	public boolean getVersionCheck() {
		return properties.getBoolean("equery.driver.checkversion");
	}
	public static MainProperties getInstance() {
		return mp;
	}
}
