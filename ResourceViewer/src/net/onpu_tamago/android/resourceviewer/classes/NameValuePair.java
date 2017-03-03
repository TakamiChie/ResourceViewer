package net.onpu_tamago.android.resourceviewer.classes;

/**
 * 名前と値のペア
 * 
 * @author 知英
 * 
 */
public class NameValuePair {

	public String name;
	public int value;

	public NameValuePair(String name, int value) {
		super();
		this.name = name;
		this.value = value;
	}

	@Override
	public String toString() {
		return name;
	}
}
