//
// ElementaryPi.java
// Elementary Pi Graphical User Interface
//
// See LICENSE for licensing details
//

import java.awt.*;
import java.io.*;
import java.awt.event.*;
import javax.swing.*;

public class ElementaryPi extends JPanel {

  // private variables
  private int programSelection;
  private int numPis;
  private JFrame mainFrame;
  private JLabel headerLabel;
  private JLabel statusLabel;
  private JPanel controlPanel;

  // executeProgram
  private int executePiProgram(){
	String data = "Error executing program!";
	if( (programSelection == -1 ) ||
	    (numPis == -1 ) ){
		statusLabel.setText(data);
		return -1;
	}
	
	String Cmd = "";

	if( programSelection == 0 ){
		Cmd = "/visit/bin/visit -gui -np " + numPis + " -nn "
			+ numPis + " -l mpirun -machinefile ~/pi_mpihostsfile -s " + "/visit/data/tutorial_data/ElemPy_IsoSurface.py";
	}else if( programSelection == 1 ){
		Cmd = "/visit/bin/visit -gui -np " + numPis + " -nn "
			+ numPis + " -l mpirun -machinefile ~/pi_mpihostsfile -s " + "/visit/data/ElemPy_Aneurysm.py";
	}else if( programSelection == 2 ){
		Cmd = "/visit/bin/visit -gui -np " + numPis + " -nn "
			+ numPis + " -l mpirun -machinefile ~/pi_mpihostsfile -s " + "/visit/data/ElemPy_DBreak3d.py";
	}

	try{
		final Process p = Runtime.getRuntime().exec(Cmd);
		System.out.println(Cmd);
	}catch(IOException e){
		data = "Error executing program : " + Cmd;
		statusLabel.setText(data);
		e.printStackTrace();
	}
	
	return 0;
  }

  // prepareGUI function
  private void prepareGUI(){
	mainFrame = new JFrame("ElementaryPi");
	mainFrame.setSize ( 400,400);
	mainFrame.setLayout ( new GridLayout (3, 1) );
	mainFrame.addWindowListener( new WindowAdapter( ) {
	public void windowClosing(WindowEvent windowEvent){
		System.exit(0);
	}
	});
	headerLabel = new JLabel("", JLabel.CENTER);
	statusLabel = new JLabel("",JLabel.CENTER);
	statusLabel.setSize(350,100);
	controlPanel = new JPanel ();
	controlPanel.setLayout(new FlowLayout());
	mainFrame.add(headerLabel);
	mainFrame.add(controlPanel);
	mainFrame.add(statusLabel);
	mainFrame.setVisible(true);
  }

  // showList function
  private void showList(){
	headerLabel.setText("Select a program and the number of Raspberry Pi's");

	// program names
	final DefaultListModel programName = new DefaultListModel();
	programName.addElement( "IsoSurface" );
	programName.addElement( "Heart Aneurysm" );
	programName.addElement( "Water Slosh" );
	
	final JList programList = new JList(programName);
	programList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
	programList.setSelectedIndex(0);
	programList.setVisibleRowCount(4);

	JScrollPane programListScrollPane  = new JScrollPane(programList);
	
	//number of cores
	final DefaultListModel numCores = new DefaultListModel();

	numCores.addElement( "1" );
	numCores.addElement( "2" );
	numCores.addElement( "3" );
	numCores.addElement( "4" );

	final JList coreList = new JList(numCores);
	coreList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
	coreList.setSelectedIndex(0);
	coreList.setVisibleRowCount(4);
	
	JScrollPane coreListScrollPane = new JScrollPane(coreList);

	// Execute button
	JButton execButton = new JButton("Execute");

	execButton.addActionListener(new ActionListener() {
	public void actionPerformed(ActionEvent e) {
	String data = "";
	if( (programList.getSelectedIndex() != -1) &&
	(coreList.getSelectedIndex() != 1) ){
  	data = "Executing " + programList.getSelectedValue() +
		" on " + coreList.getSelectedValue() + " cores";
		statusLabel.setText(data);
		programSelection = programList.getSelectedIndex();
		numPis = coreList.getSelectedIndex()+1;

		}
	int Rtn = executePiProgram();
	}
	});

	controlPanel.add(programListScrollPane);
	controlPanel.add(coreListScrollPane);
	controlPanel.add(execButton);

	mainFrame.setVisible(true);
}	

  // constructor
  public ElementaryPi(){
    programSelection = -1;
    numPis = -1;
    prepareGUI();
  }

  // main function
  public static void main(String[] argv){
    ElementaryPi ElemPi = new ElementaryPi();
    ElemPi.showList();
  }
} // ElementaryPi
