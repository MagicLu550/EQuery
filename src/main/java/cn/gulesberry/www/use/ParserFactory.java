package cn.gulesberry.www.use;

import net.noyark.www.interf.Parser;
/**
 * the parser's object factory
 * @author magiclu550
 * @since EQuery 020
 * @since JDK1.8
 * @see XParser
 *
 */
public class ParserFactory {
	private static Parser xParser;
	static {
		xParser = new XParser();
	}
	/**
	 * it can get the Parser Instance,only use inside.
	 * @return the Parser Instance
	 */
	public static Parser newInstance() {
		return xParser;
	}
}
