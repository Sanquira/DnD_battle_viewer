package hexapaper.graphicCore.controls;

public interface IControls {
	
	public void AddControl(boolean isShift,boolean isCrtl, boolean isAlt, ControlAPI obj);
	
	public void AddMouseControl(boolean isShift,boolean isCrtl, boolean isAlt, ControlAPI obj);
	
}
