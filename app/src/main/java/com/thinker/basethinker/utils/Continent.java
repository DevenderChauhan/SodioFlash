package com.thinker.basethinker.utils;

/**
 * 大洲
 * @author james
 *
 */
public enum Continent {
	Asia("亚洲"),Europe("欧洲"),South_America("南美洲"),Africa(""),Oceania("大洋洲"),North_America("北美洲")
	,Antarctica("南极洲");
	
	private String zhName;
	
	private Continent(String zhName){
		this.zhName=zhName;
	}
	
	public String getZhName() {
		return zhName;
	}

	public void setZhName(String zhName) {
		this.zhName = zhName;
	}
}
