package day0109;

import java.awt.Font;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.TitledBorder;

import day0107.CpEmp2AllVO;
import day0107.CpEmp2OneVO;
import day0107.CpEmp2VO;
import day0110.TestProcAllVO;
import day0110.TestProcOneVO;
import day0110.TestProcUpdateVO;

public class RunUseCallabelStatement {

	public void addTestProc() {
		String tempData = JOptionPane.showInputDialog("������� �߰�\n�Է� ��)�����ȣ, �����, ����, ����");
		if (tempData != null && !tempData.equals("")) {
			String[] data = tempData.split(",");
			if (data.length != 4) {
				JOptionPane.showMessageDialog(null, "�Է������� Ȯ���� �ּ���.");
				return;
			} // end if

			int empno = 0, sal = 0;
			String ename = "", job = "";
			
			try {
				empno = Integer.parseInt(data[0].trim());
				ename = data[1].trim();
				sal = Integer.parseInt(data[2].trim());
				job = data[3].trim();
			} catch (NumberFormatException nfe) {
				JOptionPane.showMessageDialog(null, "�����ȣ�� ������ �����Դϴ�.");
				return;
			} // end catch
				// ó���� �Է� �����͸� VO�� �����ϰ�
			TestProcVO tpvo = new TestProcVO(empno, sal, ename, job);

			// VO�� ���� DB�� insert �Ѵ�.
			try {
				String msg = "";
				
				msg = UseCallabelStatementDAO.getInstance().insertProc(tpvo);
				
				JOptionPane.showMessageDialog(null, msg);
			} catch (SQLException se) {// ����
				JOptionPane.showMessageDialog(null, "DB �۾� �� ���� �߻�");
				se.printStackTrace();
			} // end catch
		} // end if

	} // addCpEmp2

	public void modifyTestProc() {
		
		String tempData = JOptionPane.showInputDialog("������� ����\n�����ȣ�� ��ġ�ϴ� ������ ������ �����մϴ�.\n�Է� ��)�����ȣ, ����, ����");
		if (tempData != null && !tempData.equals("")) {
			String[] data = tempData.split(",");
			if (data.length != 3) {
				JOptionPane.showMessageDialog(null, "�Է������� Ȯ���� �ּ���.");
				return;
			} // end if

			int empno = 0, sal = 0;
			String job = "";

			try {
				empno = Integer.parseInt(data[0].trim());
				job = data[1].trim();
				sal = Integer.parseInt(data[2].trim());
			} catch (NumberFormatException nfe) {
				JOptionPane.showMessageDialog(null, "�����ȣ�� ������ ���� �Դϴ�.");
				return;
			} // end catch
				// ó���� �Է� �����͸� VO�� �����ϰ�
			TestProcUpdateVO tpuvo = new TestProcUpdateVO(empno, sal, job);
			// VO�� ���� DB�� update �Ѵ�.
			try {
				String msg = "";
				
				msg = UseCallabelStatementDAO.getInstance().updateProc(tpuvo);
				
				JOptionPane.showMessageDialog(null, msg);
			} catch (SQLException se) {// ����
				JOptionPane.showMessageDialog(null, "����! ����� �ٽ� �õ�");
				se.printStackTrace();
			} // end catch

		} // end if
		
	}//modifyTestProc
	
	public void removeTestProc() {
		String inputData = JOptionPane.showInputDialog("�������\n������ �����ȣ �Է�");
		if (inputData != null && !inputData.equals("")) {
			int empno = 0;
			try {
				empno = Integer.parseInt(inputData);
			} catch (NumberFormatException nfe) {
				JOptionPane.showMessageDialog(null, "�����ȣ�� �����̾�� �մϴ�.");
				return;
			} // end catch

			try {
				String msg="";
				
				msg=UseCallabelStatementDAO.getInstance().deleteProc(empno);
				
				JOptionPane.showMessageDialog(null, msg);
				
			} catch (SQLException sqle) {
				JOptionPane.showMessageDialog(null, "�˼��մϴ�. ������ �߻��Ͽ����ϴ�.");
				sqle.printStackTrace();
			} // end catch

		} // end if
	}//removeTestProc
	
	public void searchAllTestProc() {
		StringBuilder viewTestProc = new StringBuilder();
		viewTestProc
		.append("-------------------------------------------------------------------------------------------------------------------------------\n")
		.append("��ȣ\t�����ȣ\t�����\t����\t����\t�Ի���\n")
		.append("-------------------------------------------------------------------------------------------------------------------------------\n");

		int rowCount = 0; // ��� ��ȣ
		try {
			// DB���� ��ȸ�� ��� �ޱ�
			List<TestProcAllVO> list = UseCallabelStatementDAO.getInstance().selectAllTestProc();
			TestProcAllVO tpavo = null;

			rowCount = list.size(); // ��ȸ�� �μ��� ������
			for (int i = 0; i < list.size(); i++) {
				tpavo = list.get(i);
				viewTestProc.append(i + 1).append("\t")
				.append(tpavo.getEmpno()).append("\t")
				.append(tpavo.getEname()).append("\t")
				.append(tpavo.getSal()).append("\t")
				.append(tpavo.getJob()).append("\t")
				.append(tpavo.getHiredate()).append("\n");
			} // end for

			if (list.isEmpty()) {// list.size() == 0 �� ����.
				viewTestProc.append("�Էµ� ��� ������ �������� �ʽ��ϴ�.\n");
			} // end if

		} catch (SQLException e) {
			viewTestProc.append("DBMS���� ������ �߻��߽��ϴ�. �˼��մϴ�.\n");
			e.printStackTrace();
		} // end catch

		viewTestProc.append(
				"-------------------------------------------------------------------------------------------------------------------------------\n")
				.append("\t\t\t��").append(rowCount).append("���� �μ������� ��ȸ�Ǿ����ϴ�.\n");

		JTextArea jta = new JTextArea(30, 50);
		jta.setEditable(false);

		jta.setText(viewTestProc.toString());// ������� ��µ����͸� T.A�� �����Ѵ�.

		JScrollPane jsp = new JScrollPane(jta);
		jsp.setBorder(new TitledBorder("��ü �μ� ��ȸ���"));
		JOptionPane.showMessageDialog(null, jsp);
	}//selectAllTestProc
	
	public void searchOneTestProc() {
		String inputData = JOptionPane.showInputDialog(null, "�����ȸ\n��ȸ �� �����ȣ �Է�");
		if (inputData != null && !inputData.equals("")) {
			try {
				int empno = Integer.parseInt(inputData);
				// �����ȣ�� �Է��Ͽ� �����ȣ�� �ش��ϴ� ��������� ��ȸ.
				// ��ȸ�� ����� �ִٸ� ������ ��ü(TestProcOneVO)�� ��ȯ�ǰ� ��ȸ�� ����� ���ٸ�
				// null�� ��ȯ�ȴ�.
				TestProcOneVO tpovo = UseCallabelStatementDAO.getInstance().selectOneTestProc(empno);
				
				StringBuilder viewData = new StringBuilder();
				viewData
				.append("����� : ").append(tpovo.getEname())
				.append(", ���� : ").append(tpovo.getSal())
				.append(", ���� : ").append(tpovo.getJob())
				.append(", �Ի��� : ").append( tpovo.getHiredate());

				JLabel lbl = new JLabel(viewData.toString());
				lbl.setFont(new Font("SansSerif", Font.BOLD, 15));

				JOptionPane.showMessageDialog(null, lbl);
			} catch (NullPointerException npe) {
				JOptionPane.showMessageDialog(null, inputData + "�� �����ȣ�� �������� �ʽ��ϴ�.");
			} catch (NumberFormatException nfe) {
				JOptionPane.showMessageDialog(null, "�����ȣ�� ���� ���·� �Է��ϼž� �մϴ�.");
				nfe.printStackTrace();
			} catch (SQLException e) {
				JOptionPane.showMessageDialog(null, "�������� �����߻�!!");
				e.printStackTrace();
			} // end catch

		} // end if
	}//searchOneTestProc
	
	public static void main(String[] args) {
		RunUseCallabelStatement rucs = new RunUseCallabelStatement();
		boolean exitFlag = false;
		do {
			String inputMenu = JOptionPane.showInputDialog("�޴�����\n1.����߰� 2.������� 3.������� 4.��ü�����ȸ 5.Ư�������ȸ 6.����");
			if (inputMenu != null) {
				switch (inputMenu) {
				case "1":
					rucs.addTestProc();
					break;
				case "2":
					rucs.modifyTestProc();
					break;
				case "3":
					rucs.removeTestProc();
					break;
				case "4":
					rucs.searchAllTestProc();
					break;
				case "5":
					rucs.searchOneTestProc();
					break;
				case "6":
					exitFlag = true;
					break;
				default:
					JOptionPane.showMessageDialog(null, inputMenu + "�� �����Ǵ� ���񽺰� �ƴմϴ�.");
					break;
				}// end switch
			} else {
				exitFlag = true;
			} // end else

		} while (!exitFlag);
		JOptionPane.showMessageDialog(null, "����� �ּż� �����մϴ�.");
	}//main

}//class
