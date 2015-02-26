package addons.dice;

public class Preset {
	
	private int sides, mod, count;
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
	}
	public Preset(int sides, int mod, int count){
		this.sides = sides;
		this.mod = mod;
		this.count = count;
	}
}
