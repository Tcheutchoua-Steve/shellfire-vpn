package de.shellfire.vpn.gui;

public interface IConsole {

	void append(String string);

	StringBuffer getNewAppends();

	void append(StringBuffer newLines);

	void setVisible(boolean b);

}