package day0107;

import java.awt.Font;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.TitledBorder;

public class RunPreparedStatement {

	private UsePreparedStatementDAO ups_dao;

	public RunPreparedStatement() {
		ups_dao = new UsePreparedStatementDAO();
	}// RunPreparedStatement

	public void addCpEmp2() {
		String tempData = JOptionPane.showInputDialog("������� �߰�\n�Է� ��)�����ȣ, �����, ����");
		if (tempData != null && !tempData.equals("")) {
			String[] data = tempData.split(",");
			if (data.length != 3) {
				JOptionPane.showMessageDialog(null, "�Է������� Ȯ���� �ּ���.");
				return;
			} // end if

			int empno = 0, sal = 0;
			String ename = "";

			try {
				empno = Integer.parseInt(data[0]);
				ename = data[1];
				sal = Integer.parseInt(data[2]);
			} catch (NumberFormatException nfe) {
				JOptionPane.showMessageDialog(null, "�����ȣ�� ������ �����Դϴ�.");
				return;
			} // end catch
				// ó���� �Է� �����͸� VO�� �����ϰ�
			CpEmp2VO cevo = new CpEmp2VO(empno, sal, ename);

			// VO�� ���� DB�� insert �Ѵ�.
			try {
				ups_dao.insertCpEmp2(cevo);// �߰�����
				JOptionPane.showMessageDialog(null, empno + "�� ��������� �ԷµǾ����ϴ�.");
			} catch (SQLException se) {// ����

				String errMsg = "";
				switch (se.getErrorCode()) {
				case 1438:
					errMsg = "�����ȣ�� 4�ڸ��̰� ������ 5�ڸ� �Դϴ�.";
					break;
				case 12899:
					errMsg = "������� �ʹ� ��ϴ�.";
					break;
				default:
					errMsg = "���E�մϴ�. �ý��ۿ� ������ �߻� �߽��ϴ�. ��� �� �ٽ� �õ����ּ���."; // ������ �Ǽ�
				}// end switch
				JOptionPane.showMessageDialog(null, errMsg);
				se.printStackTrace();
			} // end catch
		} // end if

	} // addCpEmp2

	public void modifyCpEmp2() {
		String tempData = JOptionPane.showInputDialog("������� ����\n�����ȣ�� ��ġ�ϴ� ������ ������ �����մϴ�.\n�Է� ��)�����ȣ, �����, ����");
		if (tempData != null && !tempData.equals("")) {
			String[] data = tempData.split(",");
			if (data.length != 3) {
				JOptionPane.showMessageDialog(null, "�Է������� Ȯ���� �ּ���.");
				return;
			} // end if

			int empno = 0, sal = 0;
			String ename = "";

			try {
				empno = Integer.parseInt(data[0].trim());
				ename = data[1].trim();
				sal = Integer.parseInt(data[2].trim());
			} catch (NumberFormatException nfe) {
				JOptionPane.showMessageDialog(null, "�����ȣ�� ������ ���� �Դϴ�.");
				return;
			} // end catch
				// ó���� �Է� �����͸� VO�� �����ϰ�
			CpEmp2VO cevo = new CpEmp2VO(empno, sal, ename);
			// VO�� ���� DB�� update �Ѵ�.
			try {
				String msg = "";
				if (ups_dao.updateCpEmp2(cevo)) {// ����� ���ڵ� ����
					msg = empno + "�� ����� ������ �����Ͽ����ϴ�.";
				} else {// ����� ���ڵ� �������� ����
					msg = empno + "�� ����� �������� �ʽ��ϴ�.";
				} // end else
				JOptionPane.showMessageDialog(null, msg);
			} catch (SQLException se) {// ����

				String errMsg = "";
				switch (se.getErrorCode()) {
				case 12899:
					errMsg = "�μ����̳� ��ġ�� �ʹ� ��ϴ�..";
					break; // ����� �Ǽ�
				default:
					errMsg = "���E�մϴ�. �ý��ۿ� ������ �߻� �߽��ϴ�. ��� �� �ٽ� �õ����ּ���."; // ������ �Ǽ�
				}// end switch
				JOptionPane.showMessageDialog(null, errMsg);
				se.printStackTrace();
			} // end catch

		} // end if
	} // modifyCpEmp2

	public void removeCpEmp2() {
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
				String msg = empno + " �� ����� �������� �ʽ��ϴ�.";

				if (ups_dao.deleteCpEmp2(empno)) {
					msg = empno + " �� ��� ������ �����Ͽ����ϴ�.";
				} // end if

				JOptionPane.showMessageDialog(null, msg);

			} catch (SQLException sqle) {
				JOptionPane.showMessageDialog(null, "�˼��մϴ�. ������ �߻��Ͽ����ϴ�.");
				sqle.printStackTrace();
			} // end catch

		} // end if
	} // removeCpEmp2

	public void searchAllCpEmp2() {
		StringBuilder viewCpEmp = new StringBuilder();
		viewCpEmp.append(
				"----------------------------------------------------------------------------------------------------------------\n")
				.append("��ȣ\t�����ȣ\t�����\t����\t�Ի���\n")
				.append("----------------------------------------------------------------------------------------------------------------\n");

		int rowCount = 0; // ��� ��ȣ

		try {
			// DB���� ��ȸ�� ��� �ޱ�
			List<CpEmp2AllVO> list = ups_dao.selectAllCpEmp2();
			CpEmp2AllVO cevo = null;

			rowCount = list.size(); // ��ȸ�� �μ��� ������
			for (int i = 0; i < list.size(); i++) {
				cevo = list.get(i);
				viewCpEmp.append(i + 1).append("\t")
				.append(cevo.getEmpno()).append("\t")
				.append(cevo.getName()).append("\t")
				.append(cevo.getSal()).append("\t")
				.append(cevo.getHiredate()).append("\n");
			} // end for

			if (list.isEmpty()) {// list.size() == 0 �� ����.
				viewCpEmp.append("�Էµ� ��� ������ �������� �ʽ��ϴ�.\n");
			} // end if

		} catch (SQLException e) {
			viewCpEmp.append("DBMS���� ������ �߻��߽��ϴ�. �˼��մϴ�.\n");
			e.printStackTrace();
		} // end catch

		viewCpEmp.append(
				"----------------------------------------------------------------------------------------------------------------\n")
				.append("\t\t\t��").append(rowCount).append("���� �μ������� ��ȸ�Ǿ����ϴ�.\n");

		JTextArea jta = new JTextArea(30, 50);
		jta.setEditable(false);

		jta.setText(viewCpEmp.toString());// ������� ��µ����͸� T.A�� �����Ѵ�.

		JScrollPane jsp = new JScrollPane(jta);
		jsp.setBorder(new TitledBorder("��ü �μ� ��ȸ���"));
		JOptionPane.showMessageDialog(null, jsp);
	} // searchAllCpEmp2

	public void searchOneCpEmp2() {
		String inputData = JOptionPane.showInputDialog(null, "�����ȸ\n��ȸ �� �����ȣ �Է�");
		if (inputData != null && !inputData.equals("")) {
			try {
				int empno = Integer.parseInt(inputData);
				// �����ȣ�� �Է��Ͽ� �����ȣ�� �ش��ϴ� ��������� ��ȸ.
				// ��ȸ�� ����� �ִٸ� ������ ��ü(CpEmp2OneVO)�� ��ȯ�ǰ� ��ȸ�� ����� ���ٸ�
				// null�� ��ȯ�ȴ�.
				CpEmp2OneVO ceo_vo = ups_dao.selectOneCpEmp2(empno);
				
				SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy EEEEE");
				
				StringBuilder viewData = new StringBuilder();
				viewData
				.append("����� : ").append(ceo_vo.getEname())
				.append(", ���� : ").append(ceo_vo.getSal())
				.append(", �Ի��� : ").append( sdf.format(ceo_vo.getHiredate()));

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
	} // searchOneCpEmp2

	public static void main(String[] args) {
		RunPreparedStatement rps = new RunPreparedStatement();

		boolean exitFlag = false;
		do {
			String inputMenu = JOptionPane.showInputDialog("�޴�����\n1.����߰� 2.������� 3.������� 4.��ü�����ȸ 5.Ư�������ȸ 6.����");
			if (inputMenu != null) {
				switch (inputMenu) {
				case "1":
					rps.addCpEmp2();
					break;
				case "2":
					rps.modifyCpEmp2();
					break;
				case "3":
					rps.removeCpEmp2();
					break;
				case "4":
					rps.searchAllCpEmp2();
					break;
				case "5":
					rps.searchOneCpEmp2();
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
	} // main

} // class
