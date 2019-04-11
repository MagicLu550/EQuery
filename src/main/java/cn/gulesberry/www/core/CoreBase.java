package cn.gulesberry.www.core;

import cn.gulesberry.www.core.EQuery;
import net.noyark.www.interf.Queryer;
/**
 * This interface can get the EQuery instance
 * @author magiclu550
 * @since EQuery 032 
 */
public interface CoreBase {
	/**This is the EQuery instance
	 * */
	Queryer QUERY = new EQuery();
}
