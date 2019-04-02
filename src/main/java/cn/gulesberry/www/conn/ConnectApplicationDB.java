package cn.gulesberry.www.conn;

import cn.gulesberry.www.core.MainProperties;

/**
 * This method is all of the connection class's
 * 	model.
 * @author magiclu550
 *
 */
public class ConnectApplicationDB extends ConnectionBase{
	public ConnectApplicationDB() {
		super(MainProperties.getInstance().getApplicationPath());
	}
}
