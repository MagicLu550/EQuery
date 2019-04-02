/*GulesBerry Tech. Co. Ltd. (C) noyark-system Hector Group
 * (GHG China) @Freedom Web of java
 * @noyark - system group for xml
 * @github magiclu550 author
 * @github K.J author (English Noter)
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
 * you can learn more from this class
 * 
 * 
 * 
 * 
 */
package cn.gulesberry.www.use;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import cn.gulesberry.www.conn.ConnectVersionDB;
import cn.gulesberry.www.core.EQuery;
import cn.gulesberry.www.core.MainProperties;
import cn.gulesberry.www.properties.SimpleProperties;
import net.noyark.www.console.Console;
/**
 * This class can check the new version
 * @author magiclu550
 *
 */
public class VersionManager {
	/**
	 * This method can connect the version database,and
	 * check with it.
	 * @param check
	 * @throws IOException
	 */
	public static void connectionVersionDB() throws IOException {
		registerVersion();
		boolean check = MainProperties.getInstance().getVersionCheck();
		if(check) {
			Connection conn;
			try {
				conn = new ConnectVersionDB().getConn();
				PreparedStatement statement = conn.prepareStatement("select * from version");
				ResultSet resultSet = statement.executeQuery();
				while(resultSet.next()) {
					String version = resultSet.getString(1);//version表第一个元素:version
					if(version.equals(EQuery.VERSION)) {
						Console.log("您目前是最新版本:"+EQuery.VERSION+":newest Version");
					}else {
						Console.err("您版本可能过低，或者是内测版本,目前最新版本:"+version+"您的版本"+EQuery.VERSION+":the old version");
					}
				}
			} catch (SQLException e) {
				Console.err("请检查下您的网络:check your network");
			}
		}
	}
	/**
	 * This method can get version from the version properties file
	 * @throws IOException
	 */
	private static void registerVersion() throws IOException{
		SimpleProperties prop = new SimpleProperties(SetMavenJar.getInstance(), MainProperties.getInstance().getVersionInfo());
		EQuery.VERSION = prop.getString("version", "");
	}
}
