package serial_demo;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.fazecast.jSerialComm.*;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class LCD_clock {
    static  SerialPort selectedPort;
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		JFrame window = new JFrame();
		window.setSize(400, 75);
		window.setTitle("Nokia LCD 5110");
		window.setLayout(new BorderLayout());
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JComboBox<String> portList = new JComboBox<String>();
		JPanel topPanel = new JPanel();
		JButton connect = new JButton("Connect");
		topPanel.add(portList);
		topPanel.add(connect);
		
		SerialPort[] portNames = SerialPort.getCommPorts();
		for(int i = 0; i < portNames.length; i++) {
			portList.addItem(portNames[i].getSystemPortName());
			System.out.println(portNames[i].getDescriptivePortName());
		}
		window.add(topPanel, BorderLayout.NORTH);
		
		connect.addActionListener(new ActionListener() {
			
			@Override 
			public void actionPerformed(ActionEvent e) {
				if(connect.getText().equals("Connect")) {
				
					selectedPort = SerialPort.getCommPort(portList.getSelectedItem().toString());
					selectedPort.setComPortTimeouts(SerialPort.TIMEOUT_SCANNER, 0, 0);
					selectedPort.setComPortParameters(19200, 8,1,0);
					if(selectedPort.openPort()) {
						connect.setText("Disconnect");
						portList.setEnabled(false);
						
						
						Thread thread = new Thread(){
							@Override public void run() {
								
								try {Thread.sleep(100); } catch(Exception e) {}
								PrintWriter output = new PrintWriter(selectedPort.getOutputStream());
								while(true) {
									SimpleDateFormat formatter =new SimpleDateFormat("E,dd MMM yy h:mm:ss a");
									String str =formatter.format(new Date());
									System.out.println(str);
									output.print(str);
									output.flush();
									try {Thread.sleep(100); } catch(Exception e) {}
								}
							}
						};
						thread.start();
					}
				} else {
					// disconnect from the serial port
					selectedPort.closePort();
					portList.setEnabled(true);
					connect.setText("Connect");
				}
			}
		});
		
		
		window.setVisible(true);
	}
	

}
