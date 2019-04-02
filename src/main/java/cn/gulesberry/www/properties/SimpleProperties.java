/*GulesBerry Tech. Co. Ltd. (C) noyark-system Hector Group
 * (GHG China) @Freedom Web of java
 * @noyark - system group for xml
 * @github magiclu550 author
 * @noter K.J author 
 * @qq 843983728
 * @using dom4j
 * @school: JiaoNan No.1 middle School
 * 	override this none
 * 	please see our website:
 * 	###############################################
 * 						
 * 					     www.noyark.net/index.html
 * 
 * 	###############################################
 * 
 * @where China shandong qingdao
 * 
 * author is a middle school student,so he doesn't have much time.If you think his code is good for you,you
 * can touch the 'star',thanks
 * 
 * 
 * 
 * 
 */
package cn.gulesberry.www.properties;

import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.Properties;

/**
 * <p>
 * This is a simple "properties" that comes with
 * <code>sxml</code>,which simplifies the operation
 * of throwing exceptions,Currently,only read 
 * operations are supported.
 * 	<p>
 * but supports type conversion.
 * </p>
 * </p>
 * @author magiclu550
 * @see java.util.Properties
 * @since EQuery 020
 * @since JDK 1.8
 */
public class SimpleProperties {
	Properties properties;
	/**
	 * <p>
	 * When using the <code>properties</code> file,use this 
	 * <code>constructor</code>,you can omit the operation
	 * of getting the stream,you can directly
	 * pass this,the file name is omitted from
	 * the suffix.
	 * </p>
	 * @param o the object such as "this"
	 * @param name the file name
	 * @throws IOException when your properties is null and so on...
	 * 
	 */
	public SimpleProperties(Object o,String name) throws IOException {
		InputStream in = o.getClass().getClassLoader().getResourceAsStream(name+".properties");
		properties = new Properties();
		properties.load(in);
	}
	/**
	 * You can use it to read data of <code>strings</code> type
	 * @param key the property key(left of the '=')
	 * @param def the default value(if the key don't exist)
	 * @return the value of the property(right of the '=')
	 * @see java.util.Properties#getProperty(String)
	 */
	public String getString(String key,String def) {
		return properties.getProperty(key,def);
	}
	/**
	 * You can use it to read data of <code>int</code> type
	 * @param key the property key(left of the '=')
	 * @param def the default value(if the key don't exist)
	 * @return the value of the property(right of the '=')
	 * @see java.util.Properties#getProperty(String)
	 */
	public Integer getInt(String key,String def) {
		return Integer.parseInt(properties.getProperty(key,def));
	}
	/**
	 * You can use it to read data of <code>double</code> type
	 * @param key the property key(left of the '=')
	 * @param def the default value(if the key don't exist)
	 * @return the value of the property(right of the '=')
	 * @see java.util.Properties#getProperty(String)
	 */
	public Double getDouble(String key,String def) {
		return Double.parseDouble(properties.getProperty(key,def));
	}
	/**
	 * You can use it to read data of <code>boolean</code> type
	 * @param key the property key(left of the '=')
	 * @return the value of the property(right of the '=')
	 * @see java.util.Properties#getProperty(String)
	 */
	public Boolean getBoolean(String key) {
		return Boolean.parseBoolean(properties.getProperty(key,"false"));
	}
	/**
	 * you can get all Property form by this method 
	 * @return the propertyName lists
	 * @see java.util.Properties#propertyNames()
	 */
	public Enumeration<?> getAllProperty() {
		return properties.propertyNames();
	}
}