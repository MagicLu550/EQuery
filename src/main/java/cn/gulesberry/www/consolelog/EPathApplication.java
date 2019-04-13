package cn.gulesberry.www.consolelog;

import static net.noyark.www.console.Console.*;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import org.dom4j.Attribute;
import org.dom4j.DocumentException;
import org.dom4j.Element;

import cn.gulesberry.www.exception.IllegalMappingException;
import cn.gulesberry.www.exception.IndexLengthException;
import cn.gulesberry.www.exception.SynaxException;
import cn.gulesberry.www.helper.InstanceQueryer;
import cn.gulesberry.www.utils.table.NodeTable;
import net.noyark.www.interf.EPathAbastractApplication;
import net.noyark.www.interf.XMLDomFile;
/**
 * this class will start the epath shell environment,
 * and this is a thread.
 * @author magiclu550
 */
public class EPathApplication implements EPathAbastractApplication{
	//变量存储
	Map<String,List<Element>> alias = new HashMap<String, List<Element>>();
	//函数存储
	Map<String,Function> name_function = new HashMap<>();
	
	public EPathApplication() throws IllegalMappingException, IndexLengthException, DocumentException, IOException {
		thenInput(firstInPut());
	}
	public EPathApplication(int i) {
		
	}
	private XMLDomFile firstInPut() throws IllegalMappingException, IndexLengthException, DocumentException, IOException {
		log("------------epath selector v 0.0.3---------------");
		log("please show your file name,if it is in classpath,you can write start with 'classpath:',resource is 'resource:'");
		XMLDomFile xmlDomFile;
		while(true) {
			start("epath>[] ");
			String fileName = in();
			fileName = setResource(fileName);
			if(fileName.equals("exit();")) {
				System.exit(0);
				log("bye");
			}
			try {
				xmlDomFile = InstanceQueryer.getDefaultXml(fileName,this);
				break;
			}catch(Exception e) {
				err("the file name is illegal");
				err(e.getMessage()+":"+e.getClass().getName());
				continue;
			}
		}
		return xmlDomFile;
	}
	
	public void thenInput(XMLDomFile xmlDomFile) throws IOException, IllegalMappingException, IndexLengthException, DocumentException {
		xmlDomFile.clearMapping();
		getPut(xmlDomFile);	
	}
	public boolean commonPrint(StringBuilder builder,String excuteAfter,String lang,List<Element> list,XMLDomFile xmlDomFile) {
		if(builder.toString().startsWith("print")||excuteAfter.startsWith("print")) {
			String things = lang.toString();
			if(things.substring(things.indexOf("t")+1).trim().startsWith("alias")) {
				aliasSet(lang, list);
			}
			String ifAlias = builder.toString().replace(" ","").substring(builder.indexOf("t"));
			if(ifAlias.indexOf("alias")!=-1&&!ifAlias.startsWith("alias")) {
				list = getAlias(lang,new boolean[2]);
			}
			log("query "+list.size()+" elements");	
			print(lang,list,xmlDomFile);
			return true;
		}
		return false;
	}
	
	
	private void getPut(XMLDomFile xmlDomFile) throws IllegalMappingException, IndexLengthException, IOException, DocumentException {
		log("you can input the epath expressions[and 'use in' to use other file]");
		
		while(true) {
			StringBuilder builder = inputCode(xmlDomFile);
			long start = System.currentTimeMillis();
			try {
				List<Element> list = excuteShell(xmlDomFile, builder);
				long end = System.currentTimeMillis();
				log("use "+(end-start)+" ms");
				if(list!=null)
					doNext(list, xmlDomFile);
			}catch(SynaxException e) {
				err("error,the expressions have synax error!:"+e.getMessage());
				if(builder.indexOf("print")!=-1) {
					err("may be you print the method that have 'print'");
				}
			}catch(Exception e) {
				e.printStackTrace();
				if(builder.indexOf("source to")!=-1||builder.indexOf("use in")!=-1) {
					err("maybe your file name is error");
				}
				err("synax error:"+e.getMessage());
			}	
		}
	}
	public List<Element> excuteShell(XMLDomFile xmlDomFile,StringBuilder builder) throws IOException, IllegalMappingException, IndexLengthException, DocumentException {
			List<Element> list = null;
			String lang = builder.toString();
			//getInput
			boolean showed = showFunc(xmlDomFile,lang);
			boolean exited = exited(xmlDomFile, lang);
			boolean printMethod = printMethod(lang);
			if(showed) {
				return list;
			}
			if(exited) {
				System.exit(0);
			}
			//是否打印方法
			if(printMethod==false) {
				List<String> funces = readAll(lang);
				//控制几个指令
				boolean createdFunc = createFunction(funces,xmlDomFile);
				boolean haveUseIn = useIn(lang, xmlDomFile);
				boolean isSourced = sourceTo(lang, xmlDomFile);
				boolean startAlias = aliased(lang);
				lang = excuteFunc(lang);
				String excuteAfter = lang;
				lang = setIf(lang, xmlDomFile);
				lang = excuteFunc(lang);
				String alias = lang;
				//getAlias
				boolean[] isAliasHave = new boolean[2];
				list = getAlias(lang,isAliasHave);
				boolean isContinue = isAliasHave[0]; 
				boolean isAlias = isAliasHave[1];
				lang = setSelect(lang);
				PrintCase printCase = howPrint(lang);
				boolean justPrint = printCase.isJustPrint();
				lang = printCase.getLang();
				if(haveUseIn||createdFunc||isSourced||isContinue||lang.startsWith("seek")) {
					if(lang.startsWith("seek")) {
						log("seek successfully");
					}
					return list;
				}
				list = new ArrayList<>();
				boolean printOver = excuteEPath(justPrint, isAlias, createdFunc, startAlias, list, xmlDomFile, lang, builder);
				
				if(printOver) {
					return null;
				}
				boolean printed = commonPrint(builder, excuteAfter, lang, list, xmlDomFile);
				if(printed) {
					return null;
				}
				aliasSet(alias, list);
			}else {
				printFunction(lang, xmlDomFile, list);
				return null;
			}
			return list;
	}
	private void doNext(List<Element> list,XMLDomFile xmlDomFile) throws IOException {
		log("query "+list.size()+" elements");
		log("---what do you want to use these elements?"
+ "\n[setText to text/delete/addAttribute to key=value,key=value/getText/getAllAttributes/no]");
		String use = in();
		use(use, list, xmlDomFile);
		if(!use.equals("no"))
		saveThat(xmlDomFile);
	}
	private void printFunction(String lang,XMLDomFile xmlDomFile,List<Element> list) {
		lang = excuteFunc(lang.replace(" ","").substring(lang.indexOf("t")+1));
		List<Element> all ;
		if(lang.indexOf("alias")!=-1&&!lang.startsWith("alias")) {
			all = getAlias(lang,new boolean[2]);
		}else {
			all =xmlDomFile.EPathSelector(lang);
		}
		list = all;
		print(lang, list, xmlDomFile);
	}
	private void print(String lang,List<Element> list,XMLDomFile xmlDomFile) {
		boolean getPath = false;
		if(lang.toString().indexOf("pathes")!=-1) {
			getPath = true;
		}
		boolean getIndex = false;
		if(lang.toString().indexOf("indexs")!=-1) {
			getIndex = true;
		}
		for(Element e:list) {
			if(getPath==true&&getIndex==true) {
				log(xmlDomFile.getPointPath(e)+":"+Arrays.toString(xmlDomFile.getIndexs(e)));
			}else	if(getPath==true) {
				log(xmlDomFile.getPointPath(e));
			}else if(getIndex==true){
				log(Arrays.toString(xmlDomFile.getIndexs(e)));
			}else {
				log(e.toString().substring(e.toString().indexOf("[")).replaceAll("org.dom4j.tree.DefaultAttribute@[\\s\\S]+\\[",""));
			}
			
		}
	}
	private void saveThat(XMLDomFile xmlDomFile) throws IOException {
		log("use OK");
		log("save?y/n");
		String save = in().toLowerCase();
		if(save.equals("y")) {
			xmlDomFile.save();
		}
	}
	private void aliasSet(String builder,List<Element> list) {
		String alias = builder.toString().split("on")[0].trim();
		String name = alias.substring(alias.indexOf("<")+1,alias.indexOf(">")).trim();
		this.alias.put(name,list);
	}
	private String excuteFunc(String lang) {
		if(lang.matches("[a-zA-Z_0-9\\$]+\\(([a-zA-Z_0-9\\$,]+|)\\);")) {
			String name = lang.substring(0,lang.indexOf("("));
			String[] newArgs = lang.substring(lang.indexOf("(")+1,lang.indexOf(")")).split(",");
			Function func = name_function.get(name);
			if(func!=null) {
				String[] args = func.getArgs();//形式参数
				if(newArgs.length!=0) {
					String field = func.getField();
					int i = 0;
					for(String arg:args) {
						field = field.replace("@"+arg,newArgs[i]);
						i++;
					}
					lang = field;//TODO ALIAS
				}else {
					lang = func.getField();
				}
			}else {
				err("function is not found");
			}
			return lang;
		}
		return lang;
	}
	private String setIf(String lang,XMLDomFile xmlDomFile) {
		boolean[] excuteNotSuccess = new boolean[1];
		String ex = lang;
		ex = ex.replace(";","").replace(" ","");
		if(ex.indexOf("?")!=-1&&ex.indexOf(":")!=-1) {
			String [] strings = ex.split("\\?");
			String bool = ex.substring(0,ex.indexOf("?"));
			String option = strings[1];
			String[] options = option.split(":");
			String first = options[0];
			String second = options[1];
			List<Element> l1 = null;
			List<Element> l2 = null;
			if(bool.indexOf("empty")==-1) {
				String op1;
				String op2;
				try {
					String[] withOp = bool.split("(sizebig|sizesmall|==)");
					op1 = excuteFunc(withOp[0].replaceAll("[\\(\\)]",""));
					op2 = excuteFunc(withOp[1].replaceAll("[\\(\\)]",""));
				}catch(IndexOutOfBoundsException e) {
					err("no boolean near '?'");
					return lang;
				}
				excuteNotSuccess[0] = false;
				if(op1.indexOf("alias")!=-1) {
					op1 = op1.substring(op1.indexOf("alias")+"alias".length()).replace(";","").trim();
					l1 = alias.get(op1);
				}else {
					l1 = xmlDomFile.EPathSelector(op1.trim());
				}
				if(op2.indexOf("alias")!=-1) {
					op2 = op2.substring(op2.indexOf("alias")+"alias".length()).replace(";","").trim();
					l2 = alias.get(op2);
				}else {
					l2 = xmlDomFile.EPathSelector(op2.trim());
				}
				if(l1==null||l2==null) {
					err("no such alias");
				}
			}
			if(bool.indexOf("sizebig")!=-1) {
				lang = l1.size()>l2.size()?first:second;
			}else if(bool.indexOf("sizesmall")!=-1) {
				lang = l1.size()<l2.size()?first:second;
			}else if(bool.indexOf("==")!=-1){
				lang = l1.containsAll(l2)&&l1.size()==l2.size()?first:second;
			}else if(bool.indexOf("!empty")!=-1) {
				String select = bool.replaceAll("!empty","").trim();
				lang = xmlDomFile.EPathSelector(select).size()!=0?first:second;
			}else if(bool.indexOf("empty")!=-1) {
				String select = bool.replaceAll("empty","").trim();
				lang = xmlDomFile.EPathSelector(select).size()==0?first:second;
			}
		}else {
			lang = ex;
		}
		return lang;
	}
	private StringBuilder inputCode(XMLDomFile xmlDomFile) {
		//Input all
		StringBuilder builder = new StringBuilder();
		int start = 0;
		while(true) {
			if(start==0) {
				start("epath>["+xmlDomFile.getFile()+"] ");
			}else {
				start("\t-"+builder.substring(builder.length()-2)+">");
			}
			String epathExpressions = in();
			if(epathExpressions.indexOf(";")==-1) {
				builder.append(epathExpressions+" ");
			}else {
				builder.append(epathExpressions);
			}
			if(builder.toString().endsWith(";")||builder.toString().indexOf(";")!=-1) {
				start = 0;
				break;
			}
			start++;
		}
		return builder;
	}
	private List<String> readAll(String lang) throws IOException {
		List<String> funces = new ArrayList<>();
		funces.add(lang);
		if(lang.indexOf("sh")!=-1) {
			String filename = lang.substring(lang.replace(" ","").indexOf("h")+1).replace(";","");
			BufferedReader reader  = new BufferedReader(new InputStreamReader(new FileInputStream(filename.trim())));
			String expression = null;
			StringBuilder builder2 = new StringBuilder();
			while((expression=reader.readLine())!=null) {
				if(expression.startsWith("#")) {
					continue;
				}
				builder2.append(expression.replace("\n","").replace("\r",""));
				if(builder2.indexOf(";")!=-1) {
					funces.add(builder2.toString().replaceAll("\n","").replace(" ",""));
					builder2 = new StringBuilder();
				}
			}
			reader.close();
		}
		return funces;
	}
	private boolean createFunction(List<String> funces,XMLDomFile xmlDomFile) {
		boolean createdFunc = false;
		int index = 0;
		for(int i = 0;i<funces.size();i++) {
			boolean excuteNow = false;
			String fun = funces.get(i);
			if(fun.indexOf("func")!=-1) {
				String func = fun.replaceAll(" ","").replaceAll(";","").replace("\"","'");
				if(func.indexOf("{")==-1) {
					continue;
				}
				if(func.indexOf(".excute")!=-1) {
					excuteNow = true;
				}
				if(!func.substring(func.indexOf("{")+1,func.lastIndexOf("}")).trim().isEmpty()) {
					createdFunc = true;
					String funcName = func.substring(func.indexOf("c")+1,func.indexOf("("));
					String[] args = func.substring(func.indexOf("(")+1,func.indexOf(")")).split(",");
					String field = func.substring(func.indexOf("{")+1,func.indexOf("}"));
					Function function = new Function(field, args);
					name_function.put(funcName,function);
					log("function created successfully");
					if(excuteNow) {
						String excuteFunc = func.substring(func.indexOf(".")+1).replaceAll("excute",funcName);
						String that = excuteFunc(excuteFunc+";");
						that = setIf(that,xmlDomFile);
						String excu = that;
						PrintCase printCase = howPrint(that);//判断是否
						List<Element> list = xmlDomFile.EPathSelector(printCase.getLang()); 
						if(printCase.isJustPrint()) {
							log(that.substring(that.indexOf("\"")+1,that.lastIndexOf("\"")));
							continue;
						}else if(excu.indexOf("print")!=-1) {
							print(that.substring(that.replace(" ","").indexOf("t")+1), list, xmlDomFile);
							continue;
						}
						if(funces.get(i).indexOf("alias")!=-1) {
							log("query "+list.size()+" elements");
						}
						log("Simple excute function:"
								+ "\n[setText to text/delete/addAttribute to key=value,key=value/getText/getAllAttributes/no]\"");
						String use = in();
						use(use, list, xmlDomFile);
					}
				}else {
					if(funces.size()!=1&&index!=0) {
						err("function's synax is error\n");
					}
				}
			}
			index++;
		}
		return createdFunc;
	}
	private void use(String use,List<Element> list,XMLDomFile xmlDomFile) {
		if(use.startsWith("setText")) {
			String[] strings = use.split("to");
			for(Element e:list) {
				e.setText(strings[1].trim().replace(";",""));
			}
		}else if(use.startsWith("delete")) {
			for(Element e:list) {
				e.remove(e);
			}
		}else if(use.startsWith("addAttribute")) {
			String[] strings = use.split("to");
			String[] attributes = strings[1].split(",");
			for(Element e:list) {
				for(String attribute:attributes) {
					String[] keyAndValue = attribute.split("=");
					e.addAttribute(keyAndValue[0],keyAndValue[1]);
				}
			}
		}else if(use.startsWith("getText")) {
			log("The Point Path:the Indexs:the Text");
			for(Element e:list) {
				log(xmlDomFile.getPointPath(e)+":"+Arrays.toString(xmlDomFile.getIndexs(e))+":"+e.getText());
			}
		}else if(use.startsWith("getAllAttributes")) {
			log("The Point Path:the Indexs:the attribute name:the attribute value");
			for(Element e:list) {
				@SuppressWarnings("unchecked")
				List<Attribute> as = e.attributes();
				for(Attribute a:as) {
					log(xmlDomFile.getPointPath(e)+":"+Arrays.toString(xmlDomFile.getIndexs(e))+":"+a.getName()+":"+a.getValue());
				}
			}
		}
	}
	
	private String setResource(String fileName) {
		if(fileName.startsWith("resource:")) {
			fileName = fileName.replace("resource:","");
			fileName = "./src/main/resources/"+fileName;
			return fileName;
		}
		return fileName;
	}
	
	
	private boolean showFunc(XMLDomFile xmlDomFile,String lang) {
		if(lang.replace(" ","").equals("showfunc;")) {
			log(name_function.keySet());
			return true;
		}
		return false;
	}
	private boolean exited(XMLDomFile xmlDomFile,String lang) {
		if(lang.toString().equals("exit();")) {
			log("Good Bye");
			return true;
		}
		return false;
	}
	private boolean printMethod(String lang) {
		if(lang.toString().indexOf("(")!=-1&&lang.indexOf(")")!=-1&&lang.indexOf("print")!=-1&&lang.indexOf("func")==-1) {
			return true;
		}
		return false;
	}
	private boolean useIn(String lang,XMLDomFile xmlDomFile) {
		if(lang.startsWith("use in")) {
			String filename = lang.split("in")[1].replace(";","").trim();
			filename = setResource(filename).trim();
			try {
				xmlDomFile = InstanceQueryer.getDefaultXml(filename,false);
			}catch(Exception e) {
				err("the file is illegal");
			}
			return true;
		}
		return false;
	}
	private boolean sourceTo(String lang,XMLDomFile xmlDomFile) throws IOException, IllegalMappingException, IndexLengthException, DocumentException {
		if(lang.startsWith("source to")) {
			String filename = lang.split("to")[1].replace(";","").trim();
			NodeTable<Integer, Element> table = xmlDomFile.sourceEPathFile(filename);
			for(int i = 0;i<table.getPathesLength();i++) {
				List<Element> exs = table.getAll(i);
				log("line "+(i+1)+" query "+exs.size()+" elements");
				log("---what do you want to use these elements?"
						+ "\n[setText to text/delete/addAttribute to key=value,key=value/getText/getAllAttributes/no]");
				String use = in();
				use(use, exs, xmlDomFile);
				saveThat(xmlDomFile);
			}
			return true;
		}
		return false;
	}
	private PrintCase howPrint(String lang) {
		boolean justPrint = false;
		if(lang.startsWith("print")) {
			if(lang.toString().contains("\"")||lang.contains("'")) {
				justPrint = true;
			}
			lang = lang.toString().substring(lang.toString().indexOf("t")+1).trim();	
		}else {
			lang = lang.toString();
		}
		return new PrintCase(lang,justPrint);
	}
	private boolean aliased(String lang) {
		if(lang.startsWith("alias")) {
			return true;
		}
		return false;
	}
	private String setSelect(String lang) {
		if(lang.indexOf("select")!=-1) {
			lang = lang.substring(lang.indexOf("select"));
		}
		return lang;
	}
	private List<Element> getAlias(String lang,boolean[] isAliasHave) {
		List<Element> list = null;
		if(lang.indexOf("alias")!=-1&&!lang.startsWith("alias")) {
			//select in alias aliasname
			list = alias.get(lang.substring(lang.indexOf("alias")+"alias".length()).replace(";","").trim());
			if(list==null) {
				err("no such alias");
				isAliasHave[0] = true;
				//continue
			}
			if(list!=null) {
				log("query "+list.size()+" elements");
			}
			isAliasHave[1] = true;
		}
		return list;
	}
	private boolean excuteEPath(boolean justPrint,boolean isAlias,boolean createdFunc,boolean startAlias,List<Element> list,XMLDomFile xmlDomFile,String lang,StringBuilder builder) {
		if(justPrint == false) {
			if(!isAlias&&!createdFunc) {
				//The Main
				lang = lang.replace(";","").replace("pathes","").replace("indexs","");
				list.addAll(xmlDomFile.EPathSelector(lang));
				if(startAlias) {
					aliasSet(builder.toString(), list);
				}
			}
		}else {
			String message;
			if(lang.indexOf("\"")!=-1) {
				message = lang.substring(lang.indexOf("\"")+1,lang.lastIndexOf("\""));
			}else {
				message = lang.substring(lang.indexOf("'")+1,lang.lastIndexOf("'"));
			}
			if(message.equals("--version")) {
				 log("EPATH SHELL VERSION 0.0.3");
				 log("Gulesberry technolegy java epath shell");
				 log("with equery and dom4j");
			}
			if(!message.equals("--help")) {
				log(message);
			}else {
				log("please see epath.noyark.xml");
			}
			return true;
		}
		return false;
	}
}
