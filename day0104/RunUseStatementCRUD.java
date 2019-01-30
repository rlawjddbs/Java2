package day0104;

import java.awt.Font;
import java.awt.HeadlessException;
import java.sql.SQLException;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.TitledBorder;

public class RunUseStatementCRUD {

	private UseStatementCRUD us_crud;

	public RunUseStatementCRUD() {
		us_crud = new UseStatementCRUD();
	}// RunUseStatementCRUD

	public void addCpDept() {
		String tempData = JOptionPane.showInputDialog("�μ����� �߰�\n�Է� ��)�μ���ȣ, �μ���, ��ġ");
		if (tempData != null && !tempData.equals("")) {
			String[] data = tempData.split(",");
			if (data.length != 3) {
				JOptionPane.showMessageDialog(null, "�Է������� Ȯ���� �ּ���.");
				return;
			} // end if

			int deptno = 0;
			String dname = "";
			String loc = "";

			try {
				deptno = Integer.parseInt(data[0]);
				dname = data[1];
				loc = data[2];
			} catch (NumberFormatException nfe) {
				JOptionPane.showMessageDialog(null, "�μ���ȣ�� ���� �Դϴ�.");
				return;
			} // end catch
				// ó���� �Է� �����͸� VO�� �����ϰ�
			CpDeptVO cdvo = new CpDeptVO(deptno, dname, loc);
			// VO�� ���� DB�� insert �Ѵ�.
			try {
				us_crud.insertCpDept(cdvo);// �߰�����
				JOptionPane.showMessageDialog(null, deptno + "�� �μ����� �߰�");
			} catch (SQLException se) {// ����
				String errMsg = "";
				switch (se.getErrorCode()) {
				case 1:
					errMsg = deptno + "�� �μ��� �̹� �����մϴ�.";
					break; // ����� �Ǽ�
				case 1438:
					errMsg = deptno + "? �μ���ȣ�� �� �ڸ� �Դϴ�.";
					break; // ����� �Ǽ�
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

	}// addCpDept

	public void modifyCpDept() {
		String tempData = JOptionPane.showInputDialog("�μ����� ����\n�μ���ȣ�� ��ġ�ϴ� �μ���� ��ġ�� �����մϴ�.\n�Է� ��)�μ���ȣ, �μ���, ��ġ");
		if (tempData != null && !tempData.equals("")) {
			String[] data = tempData.split(",");
			if (data.length != 3) {
				JOptionPane.showMessageDialog(null, "�Է������� Ȯ���� �ּ���.");
				return;
			} // end if

			int deptno = 0;
			String dname = "";
			String loc = "";

			try {
				deptno = Integer.parseInt(data[0]);
				dname = data[1];
				loc = data[2];
			} catch (NumberFormatException nfe) {
				JOptionPane.showMessageDialog(null, "�μ���ȣ�� ���� �Դϴ�.");
				return;
			} // end catch
				// ó���� �Է� �����͸� VO�� �����ϰ�
			CpDeptVO cdvo = new CpDeptVO(deptno, dname, loc);
			// VO�� ���� DB�� update �Ѵ�.
			try {
				String msg = "";
				if (us_crud.updateCpDept(cdvo)) {// ����� ���ڵ� ����
					msg = deptno + "�� �μ��� ������ �����Ͽ����ϴ�.";
				} else {// ����� ���ڵ� �������� ����
					msg = deptno + "�� �μ��� �������� �ʽ��ϴ�.";
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
	}// modifyCpDept

	public void removeCpDept() {
		String inputData = JOptionPane.showInputDialog("�μ�����\n������ �μ���ȣ �Է�");
		if (inputData != null && !inputData.equals("")) {
			int deptno = 0;
			try {
				deptno = Integer.parseInt(inputData);
			} catch (NumberFormatException nfe) {
				JOptionPane.showMessageDialog(null, "�μ���ȣ�� �����̾�� �մϴ�.");
				return;
			} // end catch

			try {
				String msg = deptno + " �� �μ��� �������� �ʽ��ϴ�.";

				if (us_crud.deleteCpDept(deptno)) {
					msg = deptno + " �� �μ� ������ �����Ͽ����ϴ�.";
				} // end if

				JOptionPane.showMessageDialog(null, msg);

			} catch (SQLException sqle) {
				JOptionPane.showMessageDialog(null, "�˼��մϴ�. ������ �߻��Ͽ����ϴ�.");
				sqle.printStackTrace();
			} // end catch

		} // end if
	}// removeCpDept

	public void searchAllCpDept() {
		StringBuilder viewCpDept = new StringBuilder();
		viewCpDept.append(
				"----------------------------------------------------------------------------------------------------------------\n")
				.append("��ȣ\t�μ���ȣ\t\t�μ���\t��ġ\n")
				.append("----------------------------------------------------------------------------------------------------------------\n");

		int rowCount = 0;

		try {
			// DB���� ��ȸ�� ��� �ޱ�
			List<CpDeptVO> list = us_crud.selectAllCpDept();
			CpDeptVO cdv = null;

			rowCount = list.size(); // ��ȸ�� �μ��� ������
			for (int i = 0; i < list.size(); i++) {
				cdv = list.get(i);
				viewCpDept.append(i + 1).append("\t").append(cdv.getDeptno()).append("\t\t").append(cdv.getDname())
						.append("\t").append(cdv.getLoc()).append("\n");
			} // end for

			if (list.isEmpty()) {// list.size() == 0 �� ����.
				viewCpDept.append("�Էµ� �μ� ������ �������� �ʽ��ϴ�.\n");
			} // end if

		} catch (SQLException e) {
			viewCpDept.append("DBMS���� ������ �߻��߽��ϴ�. �˼��մϴ�.\n");
			e.printStackTrace();
		} // end catch

		viewCpDept.append(
				"----------------------------------------------------------------------------------------------------------------\n")
				.append("\t\t\t��").append(rowCount).append("���� �μ������� ��ȸ�Ǿ����ϴ�.\n");

		JTextArea jta = new JTextArea(30, 50);
		jta.setEditable(false);

		jta.setText(viewCpDept.toString());// ������� ��µ����͸� T.A�� �����Ѵ�.

		JScrollPane jsp = new JScrollPane(jta);
		jsp.setBorder(new TitledBorder("��ü �μ� ��ȸ���"));
		JOptionPane.showMessageDialog(null, jsp);

	}// searchAllCpDept

	public void searchOneCpDept() {
		String inputData = JOptionPane.showInputDialog(null, "�μ���ȸ\n��ȸ �� �μ���ȣ �Է�");

		if (inputData != null && !inputData.equals("")) {
			try {
				int deptno = Integer.parseInt(inputData);
				
				// �μ���ȣ�� �Է��Ͽ� �μ���ȣ�� �ش��ϴ� �μ������� ��ȸ.
				// ��ȸ�� �μ��� �ִٸ� ������ ��ü�� ��ȯ�ǰ� ��ȸ�� �μ��� ���ٸ�
				// null�� ��ȯ�ȴ�.
				OneCpDeptVO ocd_vo = us_crud.selectCpDept(deptno);

				StringBuilder viewData = new StringBuilder();
				
				viewData
				.append("�μ��� : ").append(ocd_vo.getDname())
				.append(", ��ġ : ").append(ocd_vo.getLoc());

				JLabel lbl = new JLabel(viewData.toString());
				lbl.setFont(new Font("SansSerif", Font.BOLD, 15));

				JOptionPane.showMessageDialog(null, lbl);
			} catch (NullPointerException npe) {
				
				// �����ϴ� �μ���ȣ�� ����
				StringBuilder deptno = new StringBuilder();
				
				try {
					List<Integer> list = us_crud.selectAllCpDeptNo();
					for(Integer i : list) {
						deptno.append( i.intValue() ).append(", ");
					}//end for
				} catch (SQLException e) {
					e.printStackTrace();
				}//end catch
				
				JOptionPane.showMessageDialog(null, inputData + "�� �μ��� �������� �ʽ��ϴ�. "+deptno.toString().substring(0, deptno.toString().length()-2)+"�� �����մϴ�.");
				
			} catch (NumberFormatException nfe) {
				JOptionPane.showMessageDialog(null, "�μ���ȣ�� ���� ���·� �Է��ϼž� �մϴ�.");
				nfe.printStackTrace();
			} catch (SQLException e) {
				JOptionPane.showMessageDialog(null, "�������� �����߻�!!");
				e.printStackTrace();
			} // end catch

		} // end if
	}// searchOneCpDept

	public static void main(String[] args) {
		RunUseStatementCRUD rus_crud = new RunUseStatementCRUD();

		boolean exitFlag = false;
		do {
			String inputMenu = JOptionPane.showInputDialog("�޴�����\n1.�μ��߰� 2.�μ����� 3.�μ����� 4.��ü�μ���ȸ 5.Ư���μ���ȸ 6.����");
			if (inputMenu != null) {
				switch (inputMenu) {
				case "1":
					rus_crud.addCpDept();
					break;
				case "2":
					rus_crud.modifyCpDept();
					break;
				case "3":
					rus_crud.removeCpDept();
					break;
				case "4":
					rus_crud.searchAllCpDept();
					break;
				case "5":
					rus_crud.searchOneCpDept();
					break;
				case "6":
					exitFlag = true;
					break;
				default:
					JOptionPane.showMessageDialog(null, inputMenu + "�� �����Ǵ� ���񽺰� �ƴմϴ�.");
					break;
				}// end switch
			} else {
				exitFlag=true;
			}// end else
			
		} while (!exitFlag);
		JOptionPane.showMessageDialog(null, "����� �ּż� �����մϴ�.");
	}// main

}// class
