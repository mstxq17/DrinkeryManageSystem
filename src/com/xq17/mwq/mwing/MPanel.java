package com.xq17.mwq.mwing;

import java.awt.Graphics;
import java.awt.Image;
import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

public class MPanel extends JPanel {

	// 1.设置一个 ImageIcon
	private ImageIcon imageIcon;

	// 2.构造方法
	public MPanel(URL imgUrl) {
		// 2.1 设置布局方式
		this.setLayout(null);
		// 2.2 生成ImageIcon实例
		imageIcon = new ImageIcon(imgUrl);
		// 设置面板大小同图片高宽
		// System.out.print(imageIcon.getIconWidth() + "：" + imageIcon.getIconHeight()
		// );
		this.setSize(imageIcon.getIconWidth(), imageIcon.getIconHeight());
	}

	// 3. 重写父类的绘制方法 PaintComponent
	protected void paintComponent(Graphics g) {
		// 3.1 调用父类的方法重绘界面(作用清空屏幕)
		super.paintComponent(g);
		// 3.2 获取图像对象
		Image image = imageIcon.getImage();
		// 3.3 将图片绘制到面板中, 不允许伸缩
		//g.drawImage(image, 0, 0, null);
		g.drawImage(image, 0, 0, this.getWidth(), this.getHeight(), null);
	}
}
