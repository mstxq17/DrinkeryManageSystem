package com.xq17.mwq.mwing;

import java.awt.Insets;

import javax.swing.JButton;

public class MButton extends JButton {
	public MButton() {
		setContentAreaFilled(false);
		setMargin(new Insets(0, 0 ,0 ,0));
		setBorderPainted(false);
		setFocusPainted(false);
	}
}
