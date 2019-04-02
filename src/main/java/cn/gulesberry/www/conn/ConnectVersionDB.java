package cn.gulesberry.www.conn;

import cn.gulesberry.www.core.MainProperties;

/**
 * This class can get the version connection
 * @author magiclu550
 *
 */
public class ConnectVersionDB extends ConnectionBase{

	public ConnectVersionDB() {
		super(MainProperties.getInstance().getVersionConnectionPath());
	}
}






