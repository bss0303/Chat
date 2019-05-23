package util;

public class StringUtil {
	public static boolean validateNull(String args) {
		if(args==null || args.length()==0)
			return true;
		else return false;
	}
	public static String changeNull(String source, String target) {
		if(source == null || source.length()==0 || source.equalsIgnoreCase("null")){
			return target;
		}
		else return source;
	}
	public static String filterHtml(String value) {
		if(value==null) return null;
		if(value.length()==0) return value;
		value=value.replaceAll("&", "&amp");
		value=value.replaceAll("<", "&lt");
		value=value.replaceAll(">", "&gt");
		value=value.replaceAll(" ", "&nbsp");
		value=value.replaceAll("'", "&#39;");
		value=value.replaceAll("\"", "&quot");
		value=value.replaceAll("\n", "<br>");
		return value;
	}
}