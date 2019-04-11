package net.noyark.www.interf;


import cn.gulesberry.www.core.EQuery;

/**
 * This class implements the StartCase interface,
 * which can be used to synthesize the driver-initiated
 * operation and issue the main information,which will
 * be used in the equery main driver.
 * @author magiclu550
 * @since EQuery 019
 * @since JDK 1.8
 * @see EQuery 
 */
public interface StartCase {
	/**
	 * This is the main driver core,which can
	 *  detect if a class can be loaded,preform Maven assembly
	 *  ,and detect the latest version and current version
	 */
	void start();
}
