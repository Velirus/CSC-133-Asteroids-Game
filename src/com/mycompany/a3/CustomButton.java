package com.mycompany.a3;

import com.codename1.ui.Button;
import com.codename1.charts.util.ColorUtil;

public class CustomButton extends Button {
	public CustomButton(String text) {
		super(text);
		this.getAllStyles().setPadding(1, 1, 1, 1);
		this.getAllStyles().setBgColor(ColorUtil.BLACK);
		this.getAllStyles().setFgColor(ColorUtil.GREEN);
		this.getAllStyles().setBgTransparency(100);
		this.getDisabledStyle().setBgTransparency(255);
		this.getDisabledStyle().setBgColor(ColorUtil.BLACK);
		this.getDisabledStyle().setFgColor(ColorUtil.WHITE);
		
	}
	
}
