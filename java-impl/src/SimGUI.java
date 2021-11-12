import java.awt.*;
import java.awt.event.*;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

import javax.swing.*;

public class SimGUI extends JFrame {

	// gui variables used throughout class
    private static JLabel rowsLabel;
    private static JLabel colsLabel;
    private static JTextField rowsTextField;
    private static JTextField colsTextField;
 
    private static JButton runButton;
    
    private static JLabel stopTime;
    private static JTextField stopTimeTextField;
    
    private static JLabel numOfAgentsLabel;
    private static JTextField numOfAgentsTextField;
    
    private static JLabel stageDistributionLabel;
    private static JRadioButton uniformButton;
    private static JRadioButton normalButton;
    
    private static JLabel PrctOfRunLabel;
    private static JTextField PrctOfRunTextField;
    
    /**
     * Creates & displays window.
     */
    public SimGUI() {
        initComponents();
        
        // show the window
        pack();
        setVisible(true);
    }
	
    /**
     * Initializes GUI window by adding components.
     */
    private void initComponents() {
    	
    	// set up the window
        setTitle("Tau Calculator");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setPreferredSize(new Dimension(300, 300));
        
        // set up n and h
        rowsTextField = new JTextField(4);
        rowsLabel = new JLabel("Enter Rows:  ");
        colsTextField = new JTextField(4);
        colsLabel = new JLabel("Enter Cols:");
        
        // set up operation choice
        stageDistributionLabel = new JLabel("Stage Distribution:");
        ButtonGroup operationGroup = new ButtonGroup();
        uniformButton = new JRadioButton("Uniform");
        operationGroup.add(uniformButton);
        normalButton = new JRadioButton("Normal");
        operationGroup.add(normalButton);
        uniformButton.setSelected(true);
        
        
        // set up output
        runButton = new JButton("              Run Sim               ");
        stopTime = new JLabel("Stop Time: ");
        stopTimeTextField = new JTextField(4);
        numOfAgentsLabel = new JLabel("# Of Agents: ");
        numOfAgentsTextField = new JTextField(4);
        
        PrctOfRunLabel = new JLabel("Percentage of Run Complete: ");
        PrctOfRunTextField = new JTextField(4);
        
        
        
        // initialize window layout & add components
        setLayout(new FlowLayout(FlowLayout.CENTER));
        getContentPane().add(rowsLabel);
        getContentPane().add(rowsTextField);
        rowsTextField.setText("100");
        getContentPane().add(colsLabel);
        getContentPane().add(colsTextField);
        colsTextField.setText("100");
        
        getContentPane().add(stageDistributionLabel);
        getContentPane().add(uniformButton);
        getContentPane().add(normalButton);
        
        getContentPane().add(stopTime);
        getContentPane().add(stopTimeTextField);
        stopTimeTextField.setText("100");
        getContentPane().add(numOfAgentsLabel);
        getContentPane().add(numOfAgentsTextField);
        numOfAgentsTextField.setText("400");
        getContentPane().add(runButton);
        getContentPane().add(PrctOfRunLabel);
        getContentPane().add(PrctOfRunTextField);
        
        // create & assign action listener for button
        runButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
            	calcButtonActionPerformed(evt);
            }
            
        });
    }
    
    /**
     * Calls method to calculate Tau.
     * 
     * @param evt Button event detected
     */
    private void calcButtonActionPerformed(ActionEvent evt) {
    	
    	// parseints 
    	int rows = Integer.parseInt(rowsTextField.getText());
    	int cols = Integer.parseInt(colsTextField.getText());
    	int stopTime = Integer.parseInt(stopTimeTextField.getText());
    	int numOfAgents = Integer.parseInt(numOfAgentsTextField.getText());
    	
    	// parse distribution choice
    	int distributionChoice;
    	if (uniformButton.isSelected())
    	{
    		distributionChoice = 0;
    	}
    	else
    	{
    		distributionChoice = 1;
    	}
    	// run sim
    	Simulation sim = new Simulation(rows, cols, numOfAgents, stopTime, distributionChoice);
		for (int i=0; i<sim.stopTime; i++)
		{
			sim.next();

		}
		try {
			PrintWriter out = new PrintWriter(sim.outputFilename);
			out.println(sim.modelState);
			out.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	
    }
    
    // brings up main window when run
    public static void main(String [] args)
    {
    	SimGUI gui = new SimGUI();
    }
}