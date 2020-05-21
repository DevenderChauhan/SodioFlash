package com.thinker.basethinker.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class CountryUtil {

	private static Map<String, Country> map=new HashMap();
	private static  List<Country> listEn=new ArrayList<Country>(Arrays.asList(
			new Country(Continent.Asia,"China","86"),
			new Country(Continent.Asia,"Malaysia","60"),
			new Country(Continent.Asia,"Singapore","65")
	));
	private static List<Country> list=new ArrayList<Country>(Arrays.asList(
			new Country(Continent.Asia,"中国大陆","86"),
			new Country(Continent.Asia,"马来西亚","60"),
			new Country(Continent.Asia,"新加坡","65")
	));
//	private static List<Country> list=new ArrayList<Country>(Arrays.asList(
//		new Country(Continent.Asia,"中国大陆","86"),
//		new Country(Continent.Asia,"香港","852"),
//		new Country(Continent.Asia,"澳门","853"),
//		new Country(Continent.Asia,"台湾","886"),
//		new Country(Continent.Asia,"马来西亚","60"),
//		new Country(Continent.Asia,"印度尼西亚","62"),
//		new Country(Continent.Asia,"菲律宾","63"),
//		new Country(Continent.Asia,"新加坡","65"),
//		new Country(Continent.Asia,"泰国","66"),
//		new Country(Continent.Asia,"日本","81"),
//		new Country(Continent.Asia,"韩国","82"),
//		new Country(Continent.Asia,"塔吉克斯坦","7"),
//		new Country(Continent.Asia,"哈萨克斯坦","7"),
//		new Country(Continent.Asia,"越南","84"),
//		new Country(Continent.Asia,"土耳其","90"),
//		new Country(Continent.Asia,"印度","91"),
//		new Country(Continent.Asia,"巴基斯坦","92"),
//		new Country(Continent.Asia,"阿富汗","93"),
//		new Country(Continent.Asia,"斯里兰卡","94"),
//		new Country(Continent.Asia,"缅甸","95"),
//		new Country(Continent.Asia,"伊朗","98"),
//		new Country(Continent.Asia,"亚美尼亚","374"),
//		new Country(Continent.Asia,"东帝汶","670"),
//		new Country(Continent.Asia,"文莱","673"),
//		new Country(Continent.Asia,"朝鲜","850"),
//		new Country(Continent.Asia,"柬埔寨","855"),
//		new Country(Continent.Asia,"老挝","856"),
//		new Country(Continent.Asia,"孟加拉国","880"),
//		new Country(Continent.Asia,"马尔代夫","960"),
//		new Country(Continent.Asia,"黎巴嫩","961"),
//		new Country(Continent.Asia,"约旦","962"),
//		new Country(Continent.Asia,"叙利亚","963"),
//		new Country(Continent.Asia,"伊拉克","964"),
//		new Country(Continent.Asia,"科威特","965"),
//		new Country(Continent.Asia,"沙特阿拉伯","966"),
//		new Country(Continent.Asia,"也门","967"),
//		new Country(Continent.Asia,"阿曼","968"),
//		new Country(Continent.Asia,"巴勒斯坦","970"),
//		new Country(Continent.Asia,"阿联酋","971"),
//		new Country(Continent.Asia,"以色列","972"),
//		new Country(Continent.Asia,"巴林","973"),
//		new Country(Continent.Asia,"卡塔尔","974"),
//		new Country(Continent.Asia,"不丹","975"),
//		new Country(Continent.Asia,"蒙古","976"),
//		new Country(Continent.Asia,"尼泊尔","977"),
//		new Country(Continent.Asia,"土库曼斯坦","993"),
//		new Country(Continent.Asia,"阿塞拜疆","994"),
//		new Country(Continent.Asia,"乔治亚","995"),
//		new Country(Continent.Asia,"吉尔吉斯斯坦","996"),
//		new Country(Continent.Asia,"乌兹别克斯坦","998"),
//		new Country(Continent.Europe,"英国","44"),
//		new Country(Continent.Europe,"德国","49"),
//		new Country(Continent.Europe,"意大利","39"),
//		new Country(Continent.Europe,"法国","33"),
//		new Country(Continent.Europe,"俄罗斯","7"),
//		new Country(Continent.Europe,"希腊","30"),
//		new Country(Continent.Europe,"荷兰","31"),
//		new Country(Continent.Europe,"比利时","32"),
//		new Country(Continent.Europe,"西班牙","34"),
//		new Country(Continent.Europe,"匈牙利","36"),
//		new Country(Continent.Europe,"罗马尼亚","40"),
//		new Country(Continent.Europe,"瑞士","41"),
//		new Country(Continent.Europe,"奥地利","43"),
//		new Country(Continent.Europe,"丹麦","45"),
//		new Country(Continent.Europe,"瑞典","46"),
//		new Country(Continent.Europe,"挪威","47"),
//		new Country(Continent.Europe,"波兰","48"),
//		new Country(Continent.Europe,"圣马力诺","223"),
//		new Country(Continent.Europe,"匈牙利","336"),
//		new Country(Continent.Europe,"南斯拉夫","338"),
//		new Country(Continent.Europe,"直布罗陀","350"),
//		new Country(Continent.Europe,"葡萄牙","351"),
//		new Country(Continent.Europe,"卢森堡","352"),
//		new Country(Continent.Europe,"爱尔兰","353"),
//		new Country(Continent.Europe,"冰岛","354"),
//		new Country(Continent.Europe,"阿尔巴尼亚","355"),
//		new Country(Continent.Europe,"马耳他","356"),
//		new Country(Continent.Europe,"塞浦路斯","357"),
//		new Country(Continent.Europe,"芬兰","358"),
//		new Country(Continent.Europe,"保加利亚","359"),
//		new Country(Continent.Europe,"立陶宛","370"),
//		new Country(Continent.Europe,"拉脱维亚","371"),
//		new Country(Continent.Europe,"爱沙尼亚","372"),
//		new Country(Continent.Europe,"摩尔多瓦","373"),
//		new Country(Continent.Europe,"安道尔共和国","376"),
//		new Country(Continent.Europe,"乌克兰","380"),
//		new Country(Continent.Europe,"南斯拉夫","381"),
//		new Country(Continent.Europe,"克罗地亚","385"),
//		new Country(Continent.Europe,"斯洛文尼亚","386"),
//		new Country(Continent.Europe,"波黑","387"),
//		new Country(Continent.Europe,"马其顿","389"),
//		new Country(Continent.Europe,"梵蒂冈","396"),
//		new Country(Continent.Europe,"捷克","420"),
//		new Country(Continent.Europe,"斯洛伐克","421"),
//		new Country(Continent.Europe,"列支敦士登","423"),
//		new Country(Continent.South_America,"秘鲁","51"),
//		new Country(Continent.South_America,"墨西哥","52"),
//		new Country(Continent.South_America,"古巴","53"),
//		new Country(Continent.South_America,"阿根廷","54"),
//		new Country(Continent.South_America,"巴西","55"),
//		new Country(Continent.South_America,"智利","56"),
//		new Country(Continent.South_America,"哥伦比亚","57"),
//		new Country(Continent.South_America,"委内瑞拉","58"),
//		new Country(Continent.South_America,"福克兰群岛","500"),
//		new Country(Continent.South_America,"伯利兹","501"),
//		new Country(Continent.South_America,"危地马拉","502"),
//		new Country(Continent.South_America,"萨尔瓦多","503"),
//		new Country(Continent.South_America,"洪都拉斯","504"),
//		new Country(Continent.South_America,"尼加拉瓜","505"),
//		new Country(Continent.South_America,"哥斯达黎加","506"),
//		new Country(Continent.South_America,"巴拿马","507"),
//		new Country(Continent.South_America,"圣彼埃尔","508"),
//		new Country(Continent.South_America,"海地","509"),
//		new Country(Continent.South_America,"瓜德罗普","590"),
//		new Country(Continent.South_America,"玻利维亚","591"),
//		new Country(Continent.South_America,"圭亚那","592"),
//		new Country(Continent.South_America,"厄瓜多尔","593"),
//		new Country(Continent.South_America,"法属圭亚那","594"),
//		new Country(Continent.South_America,"巴拉圭","595"),
//		new Country(Continent.South_America,"马提尼克","596"),
//		new Country(Continent.South_America,"苏里南","597"),
//		new Country(Continent.South_America,"乌拉圭","598"),
//		new Country(Continent.Africa,"埃及","20"),
//		new Country(Continent.Africa,"南非","27"),
//		new Country(Continent.Africa,"摩洛哥","212"),
//		new Country(Continent.Africa,"阿尔及利亚","213"),
//		new Country(Continent.Africa,"突尼斯","216"),
//		new Country(Continent.Africa,"利比亚","218"),
//		new Country(Continent.Africa,"冈比亚","220"),
//		new Country(Continent.Africa,"塞内加尔","221"),
//		new Country(Continent.Africa,"毛里塔尼亚","222"),
//		new Country(Continent.Africa,"马里","223"),
//		new Country(Continent.Africa,"几内亚","224"),
//		new Country(Continent.Africa,"科特迪瓦","225"),
//		new Country(Continent.Africa,"布基拉法索","226"),
//		new Country(Continent.Africa,"尼日尔","227"),
//		new Country(Continent.Africa,"多哥","228"),
//		new Country(Continent.Africa,"贝宁","229"),
//		new Country(Continent.Africa,"毛里求斯","230"),
//		new Country(Continent.Africa,"利比里亚","231"),
//		new Country(Continent.Africa,"塞拉利昂","232"),
//		new Country(Continent.Africa,"加纳","233"),
//		new Country(Continent.Africa,"尼日利亚","234"),
//		new Country(Continent.Africa,"乍得","235"),
//		new Country(Continent.Africa,"中非","236"),
//		new Country(Continent.Africa,"喀麦隆","237"),
//		new Country(Continent.Africa,"佛得角","238"),
//		new Country(Continent.Africa,"圣多美","239"),
//		new Country(Continent.Africa,"普林西比","239"),
//		new Country(Continent.Africa,"赤道几内亚","240"),
//		new Country(Continent.Africa,"加蓬","241"),
//		new Country(Continent.Africa,"刚果","242"),
//		new Country(Continent.Africa,"扎伊尔","243"),
//		new Country(Continent.Africa,"安哥拉","244"),
//		new Country(Continent.Africa,"几内亚比绍","245"),
//		new Country(Continent.Africa,"阿森松","247"),
//		new Country(Continent.Africa,"塞舌尔","248"),
//		new Country(Continent.Africa,"苏丹","249"),
//		new Country(Continent.Africa,"卢旺达","250"),
//		new Country(Continent.Africa,"埃塞俄比亚","251"),
//		new Country(Continent.Africa,"索马里","252"),
//		new Country(Continent.Africa,"吉布提","253"),
//		new Country(Continent.Africa,"肯尼亚","254"),
//		new Country(Continent.Africa,"坦桑尼亚","255"),
//		new Country(Continent.Africa,"乌干达","256"),
//		new Country(Continent.Africa,"布隆迪","257"),
//		new Country(Continent.Africa,"莫桑比克","258"),
//		new Country(Continent.Africa,"赞比亚","260"),
//		new Country(Continent.Africa,"马达加斯加","261"),
//		new Country(Continent.Africa,"留尼旺岛","262"),
//		new Country(Continent.Africa,"津巴布韦","263"),
//		new Country(Continent.Africa,"纳米比亚","264"),
//		new Country(Continent.Africa,"马拉维","265"),
//		new Country(Continent.Africa,"莱索托","266"),
//		new Country(Continent.Africa,"博茨瓦纳","267"),
//		new Country(Continent.Africa,"斯威士兰","268"),
//		new Country(Continent.Africa,"科摩罗","269"),
//		new Country(Continent.Africa,"圣赫勒拿","290"),
//		new Country(Continent.Africa,"厄立特里亚","291"),
//		new Country(Continent.Africa,"阿鲁巴岛","297"),
//		new Country(Continent.Africa,"法罗群岛","298"),
//		new Country(Continent.Africa,"摩纳哥","377"),
//		new Country(Continent.Oceania,"澳大利亚","61"),
//		new Country(Continent.Oceania,"新西兰","64"),
//		new Country(Continent.Oceania,"关岛","671"),
//		new Country(Continent.Oceania,"瑙鲁","674"),
//		new Country(Continent.Oceania,"汤加","676"),
//		new Country(Continent.Oceania,"所罗门群岛","677"),
//		new Country(Continent.Oceania,"瓦努阿图","678"),
//		new Country(Continent.Oceania,"斐济","679"),
//		new Country(Continent.Oceania,"科克群岛","682"),
//		new Country(Continent.Oceania,"纽埃岛","683"),
//		new Country(Continent.Oceania,"东萨摩亚","684"),
//		new Country(Continent.Oceania,"西萨摩亚","685"),
//		new Country(Continent.Oceania,"基里巴斯","686"),
//		new Country(Continent.Oceania,"图瓦卢","688"),
//		new Country(Continent.Oceania,"科科斯岛","619162"),
//		new Country(Continent.Oceania,"诺福克岛","6723"),
//		new Country(Continent.Oceania,"圣诞岛","619164"),
//		new Country(Continent.North_America,"美国","1"),
//		new Country(Continent.North_America,"加拿大","1"),
//		new Country(Continent.North_America,"夏威夷","1808"),
//		new Country(Continent.North_America,"阿拉斯加","1907"),
//		new Country(Continent.North_America,"格陵兰岛","299"),
//		new Country(Continent.North_America,"中途岛","1808"),
//		new Country(Continent.North_America,"威克岛","1808"),
//		new Country(Continent.North_America,"维尔京群岛","1809"),
//		new Country(Continent.North_America,"波多黎各","1809"),
//		new Country(Continent.North_America,"巴哈马","1809"),
//		new Country(Continent.North_America,"安圭拉岛","1809"),
//		new Country(Continent.North_America,"圣卢西亚","1809"),
//		new Country(Continent.North_America,"巴巴多斯","1809"),
//		new Country(Continent.North_America,"牙买加","1876"),
//		new Country(Continent.Antarctica,"南极洲","64672"))
//	);
	
	static{
		for (Country country : list) {
			map.put(country.getCode(), country);
		}
	}

	public static List<Country> getList() {
		return list;
	}

	public static void setList(List<Country> list) {
		CountryUtil.list = list;
	}

	/**
	 * 得到国家对象
	 * @param code
	 * @return
	 */
	public static Country get(String code){
		return map.get(code);
	}
	
	/**
	 * 验证是否存在
	 * @param code
	 * @return
	 */
	public static boolean validate(String code){
		return map.containsKey(code);
	}

	public static List<Country> getListEn() {
		return listEn;
	}
}
