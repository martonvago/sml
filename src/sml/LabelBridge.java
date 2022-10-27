package sml;

public class LabelBridge {
	private Machine m;

	public LabelBridge(Machine m) {
		this.m = m;
	}

	public int indexOf(String lab) {
		return m.getLabels().indexOf(lab);
	}
}
