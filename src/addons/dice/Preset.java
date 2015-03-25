package addons.dice;

public class Preset {
	
	private int sides, mod, count;
	private String name;
	private Boolean fastValue;
	public int getSides() {
		return sides;
	}
	public void setSides(int sides) {
		this.sides = sides;
	}
	public int getMod() {
		return mod;
	}
	public void setMod(int mod) {
		this.mod = mod;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Boolean getFastValue() {
		return fastValue;
	}
	public void setFastValue(Boolean fastValue) {
		this.fastValue = fastValue;
	}
	public Preset(int sides, int mod, int count, String name, boolean fast){
		this.sides = sides;
		this.mod = mod;
		this.count = count;
		this.setName(name);
		this.setFastValue(fast);
	}
	public Preset(int sides, int mod, int count){
		this(sides,mod,count,generateName(sides,mod,count),false);
	}
	public Preset(int sides, int mod, int count, boolean fast){
		this(sides, mod, count, generateName(sides,mod,count), fast);
	}
	public Preset(int sides, int mod, int count, String name){
		this(sides, mod, count, name, false);
	}
	private static String generateName(int sides, int mod, int count) {
		String name = "";
		if(count>1){
			name+=count+"*";
		}
		name+="["+sides;
		if(mod!=0){
			name+=";"+mod;
		}
		name+="]";
		return name;
	}

}
