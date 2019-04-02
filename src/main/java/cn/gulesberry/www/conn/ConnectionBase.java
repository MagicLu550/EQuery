package cn.gulesberry.www.conn;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

import org.apache.commons.dbcp.BasicDataSource;
/**
 * <p>
 * 	This is the base class for database connections.
 * 	When a custom database connection is required,the
 * 	class can be inherited,the class can be inherited
 * 	Customzation is generally used in exporting xml.
 * </p>
 * @author magiclu550
 * @since EQuery 020
 * @since JDK 1.8
 * 
 * @see BasicDataSource
 */
public abstract class ConnectionBase {
	/**the dataSource object
	 * */
	protected BasicDataSource dataSource;
	/**
	 * <p>
	 * 	This constructor can read all the information
	 * 	of the specified configuration file.The configutation
	 * 	file standard must follow the standards provided by 
	 * 	EQuery.Each subclass that inherits the class must have
	 * 	a corresponding configuration file. 
	 * </p>
	 * @param connFile the confih file name
	 * @see BasicDataSource
	 * */
	public ConnectionBase(String connFile) {
		dataSource = new BasicDataSource();
		//读取数据
		Properties prop = new Properties();
		InputStream ips = 
				ConnectVersionDB.class.getClassLoader()
				.getResourceAsStream(connFile);
		try {
			prop.load(ips);
		String driver = prop.getProperty("driver");
		String url = prop.getProperty("url");
		String username = prop.getProperty("username");
		String password = prop.getProperty("password");
		String initSize = prop.getProperty("initSize");
		String maxSize = prop.getProperty("maxSize");
		dataSource.setDriverClassName(driver);
		dataSource.setUrl(url);
		dataSource.setUsername(username);
		dataSource.setPassword(password);
		dataSource.setInitialSize(Integer.parseInt(initSize));
		dataSource.setMaxActive(Integer.parseInt(maxSize));
		
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	/**
	 * all of class can use this method to get the
	 * 	connection.
	 * 	
	 * @return the Connection Object
	 * @throws SQLException
	 */
	public Connection getConn() throws SQLException{
		return dataSource.getConnection();
	}
}
