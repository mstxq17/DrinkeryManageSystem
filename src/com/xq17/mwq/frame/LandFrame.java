package com.xq17.mwq.frame;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.net.URL;
import java.util.Vector;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;

import org.junit.Test;

import com.xq17.mwq.dao.Dao;
import com.xq17.mwq.frame.LandFrame.ExitButtonActionListener;
import com.xq17.mwq.frame.LandFrame.LoginButtonActionListener;
import com.xq17.mwq.frame.LandFrame.usernameComboBoxActionListener;
import com.xq17.mwq.frame.LandFrame.passwordFieldFocusListener;
import com.xq17.mwq.frame.LandFrame.ResetButtonActionListener;
import com.xq17.mwq.mwing.MPanel;

// 继承 JFrame
public class LandFrame extends JFrame {

	@Test
	public void LandTest() {
		// 创建一个LandFrame窗口实例
		// 设置可见
		new LandFrame().setVisible(true);
	}

	public static void main(String args[]) {
		new LandFrame().setVisible(true);
	}

	// 用户框
	private JComboBox usernameComboBox;

	// 密码框
	private JPasswordField passwordField;

	// 登录按钮
	private JButton loginButton;

	// 重置按钮
	private JButton resetButton;

	// 退出按钮
	private JButton exitButton;

	// 构造函数
	public LandFrame() {
		/**
		 * 登录界面UI设计
		 */
		// 1.标题：登录窗口
		this.setTitle("登录窗口");
		// 2.设置窗口不可变
		this.setResizable(false);
		// 3.获取屏幕大小 screenSize Tookit 类获取
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		// 4.设置位置Bounds 428 292
		this.setBounds((screenSize.width - 423) / 2, (screenSize.height - 283) / 2, 423, 283);
		// 5.设置关闭提示窗口
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// 6. 自定义Jpanel, 通过绘制方式添加背景图片
		URL imgUrl = this.getClass().getResource("/img/land_background.jpg");
		// System.out.println(imgUrl);
		MPanel panel = new MPanel(imgUrl);
		// 7.添加面板到当前界面, 居中
		this.add(panel, BorderLayout.CENTER);

		// 8.设置用户名下拉列表
		usernameComboBox = new JComboBox<String>();
		// 8.1 5个选项限制
		usernameComboBox.setMaximumRowCount(5);
		// 8.2 添加选项
		usernameComboBox.addItem("请选择");
		// 8.3 查询数据库获取用户名,查询在职状态
		Vector<Object> userNameV = Dao.getInstance().sUserNameOfNotFreeze();
		if (userNameV.size() == 0) {
			usernameComboBox.addItem("Tsoft");
		} else {
			for (int i = 0; i < userNameV.size(); i++) {
				usernameComboBox.addItem(userNameV.get(i).toString());
			}
		}
		// 8.4 添加监听事件
		usernameComboBox.addActionListener(new usernameComboBoxActionListener());
		// 8.5 设置位置
		usernameComboBox.setBounds(245, 155, 90, 20);
		// usernameComboBox.set
		// 8.6 添加进当前面板
		panel.add(usernameComboBox);

		// 9、创建并设置密码框
		// 9.1 创建密码框
		passwordField = new JPasswordField();
		// 9.2 设置长度
		passwordField.setColumns(20);
		// 9.3 设置位置
		passwordField.setBounds(245, 180, 100, 20);
		// 9。4 添加监听事件
		passwordField.addFocusListener(new passwordFieldFocusListener());
		panel.add(passwordField);

		// 10. 设置按钮
		// 10.1 创建一个面板容纳3个按钮
		JPanel buttonPanel = new JPanel();
		// 10.2 设置面板透明
		buttonPanel.setOpaque(false);
		// 10.3 设置位置
		buttonPanel.setBounds(185, 205, 200, 30);
		panel.add(buttonPanel);
		loginButton = new JButton();
		// 10.x 设置边距为0
		loginButton.setMargin(new Insets(0, 0, 0, 0));
		// 10.x 设置不绘制按钮的内容区域
		loginButton.setContentAreaFilled(false);
		// 10.x 设置不绘制按钮边框
		loginButton.setBorderPainted(false);
		// 10.x 插入图标
		URL landUrl = this.getClass().getResource("/img/land_submit.png");
		URL landOverUrl = this.getClass().getResource("/img/land_submit_over.png");
		URL landPressedUrl = this.getClass().getResource("/img/land_submit_pressed.png");
		loginButton.setIcon(new ImageIcon(landUrl));
		loginButton.setRolloverIcon(new ImageIcon(landOverUrl));
		loginButton.setPressedIcon(new ImageIcon(landPressedUrl));
		// 10.x 设置监听器
		loginButton.addActionListener(new LoginButtonActionListener());
		buttonPanel.add(loginButton);
		resetButton = new JButton();
		resetButton.setMargin(new Insets(0, 0, 0, 0));
		resetButton.setContentAreaFilled(false);
		resetButton.setBorderPainted(false);
		URL resetUrl = this.getClass().getResource("/img/land_reset.png");
		URL resetOverUrl = this.getClass().getResource("/img/land_reset_over.png");
		URL resetPressedUrl = this.getClass().getResource("/img/land_reset_pressed.png");
		resetButton.setIcon(new ImageIcon(resetUrl));
		resetButton.setRolloverIcon(new ImageIcon(resetOverUrl));
		resetButton.setPressedIcon(new ImageIcon(resetPressedUrl));
		// 10.x 设置reset的 监听器
		resetButton.addActionListener(new ResetButtonActionListener());
		buttonPanel.add(resetButton);

		exitButton = new JButton();
		exitButton.setMargin(new Insets(0, 0, 0, 0));
		exitButton.setContentAreaFilled(false);
		exitButton.setBorderPainted(false);
		URL exitUrl = this.getClass().getResource("/img/land_exit.png");
		URL exitOverUrl = this.getClass().getResource("/img/land_exit_over.png");
		URL exitPressedUrl = this.getClass().getResource("/img/land_exit_pressed.png");
		exitButton.setIcon(new ImageIcon(exitUrl));
		exitButton.setRolloverIcon(new ImageIcon(exitOverUrl));
		exitButton.setPressedIcon(new ImageIcon(exitPressedUrl));
		// 10.x 设置exit的 监听器
		exitButton.addActionListener(new ExitButtonActionListener());
		buttonPanel.add(exitButton);
	}

	/**
	 * 监视器处理事件
	 * 
	 * @author xq17
	 *
	 */
	public class usernameComboBoxActionListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			// 获取选中的用户名,如果是"Tsoft" 就自动填充密码111
			String name = (String) usernameComboBox.getSelectedItem();
			if (name.equals("Tsoft")) {
				passwordField.setText("111");
			}
		}

	}

	public class passwordFieldFocusListener implements FocusListener {

		@Override
		public void focusGained(FocusEvent e) {
			// TODO Auto-generated method stub
			// 再次获得焦点的时候清空
			passwordField.setText("");
		}

		@Override
		public void focusLost(FocusEvent e) {
			// TODO Auto-generated method stub
			// pass
		}

	}

	public class LoginButtonActionListener implements ActionListener {

		@Override
		/**
		 * 1.判断是否选择了登录用户，如果没有给出信息提示 2.判断是否为默认用户TSoft登录,判断密码，给出相应提示
		 * 3.如果不是默认用户，首先判断输入密码，未输入给出提示 4.查询数据库，判断登录
		 */
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			String username = usernameComboBox.getSelectedItem().toString();
			if (username.equals("请选择")) {
				JOptionPane.showMessageDialog(null, "用户名不正确，请选择其他用户", "友情提示", JOptionPane.INFORMATION_MESSAGE);
			} else {
				String password = String.valueOf(passwordField.getPassword());
				if (username.equals("TSoft")) {
					// 开始判断密码
					if (password.equals("111")) {
						land(null);
						// 默认提示信息
						String infos[] = { "请立刻添加一个admin用户", "添加用户后需要重新登录，才能正常使用" };
						JOptionPane.showMessageDialog(null, infos, "友情提示", JOptionPane.INFORMATION_MESSAGE);

					} else {
						JOptionPane.showMessageDialog(null, "默认用户“Soft”密码为:“111”", "友情提示",
								JOptionPane.INFORMATION_MESSAGE);
						passwordField.setText("111");
					}

				} else {
					// 判断是否输入密码
					if (password.length() == 0) {
						JOptionPane.showMessageDialog(null, "请输入登录密码", "友情提示", JOptionPane.INFORMATION_MESSAGE);
					}
					Vector user = Dao.getInstance().sUserNameByName(username);
					// System.out.println(user.get(6).toString());
					// System.out.println(user);
					// System.out.println(password);
					if (password.equals(user.get(6).toString())) {
						land(user);
					} else {
						JOptionPane.showMessageDialog(null, "密码错误,请重新输入", "友情提示", JOptionPane.INFORMATION_MESSAGE);
					}
				}
			}
		}

	}

	public class ResetButtonActionListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			// 重置用户名和密码框
			usernameComboBox.setSelectedIndex(0);
			passwordField.setText("");
		}

	}

	public class ExitButtonActionListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			// 退出程序
			System.exit(0);
		}

	}

	public void land(Vector user) {
		// TODO Auto-generated method stub
		TipWizardFrame tipWizardFrame = new TipWizardFrame(user);
		tipWizardFrame.setVisible(true);
		// 隐藏登录窗口
		this.setVisible(false);
	}
}
