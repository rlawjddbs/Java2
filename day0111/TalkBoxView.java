package day0111;

import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;


public class TalkBoxView extends JFrame {

	private JButton btn1, btn2, btn3, btn4, btn5, btn6, btn7, btn8, btn9, btn10;
	
	public TalkBoxView() {
		btn1 = new JButton("������");
		btn2 = new JButton("�ڿ���");
		btn3 = new JButton("������");
		btn4 = new JButton("����ö");
		btn5 = new JButton("������");
		btn6 = new JButton("������");
		btn7 = new JButton("������");
		btn8 = new JButton("������");
		btn9 = new JButton("�����");
		btn10 = new JButton("������");
		
		setLayout(new GridLayout(5, 2));
		add(btn1);
		add(btn2);
		add(btn3);
		add(btn4);
		add(btn5);
		add(btn6);
		add(btn7);
		add(btn8);
		add(btn9);
		add(btn10);
		
		setBounds(300, 400, 200, 200);
		setVisible(true);
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}//TalkBoxView
	
	public JButton getBtn1() {
		return btn1;
	}

	public JButton getBtn2() {
		return btn2;
	}

	public JButton getBtn3() {
		return btn3;
	}

	public JButton getBtn4() {
		return btn4;
	}

	public JButton getBtn5() {
		return btn5;
	}

	public JButton getBtn6() {
		return btn6;
	}

	public JButton getBtn7() {
		return btn7;
	}

	public JButton getBtn8() {
		return btn8;
	}

	public JButton getBtn9() {
		return btn9;
	}

	public JButton getBtn10() {
		return btn10;
	}

	public static void main(String[] args) {
		new TalkBoxView();
	}//main
	
	
}//class
