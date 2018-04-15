package edu.nchu.cs.ai.transitor;

public interface Transitor<S> {
	public void setCurrent(S solution);
	public S transit();
}
