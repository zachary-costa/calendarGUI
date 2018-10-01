import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.GregorianCalendar;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

/**
 * Creates an interface for the user to set the start time and end time of an event and adds the event to the MyCalendar.
 * @author Zack
 *
 */
public class EventAdder extends JFrame{

	int DEFAULT_WIDTH = 1000;
	int DEFAULT_HEIGHT = 500;
	MyCalendar c;
	public EventAdder(MyCalendar c)
	{
		this.setLayout(new GridLayout(0,3));
		this.c = c;
		setTitle("Create Event");
		setSize(DEFAULT_WIDTH,DEFAULT_HEIGHT);
		JLabel instruction = new JLabel("Enter Event Name: ");
		instruction.setFont(new Font("default", Font.PLAIN, 30));
		JTextArea eventName = new JTextArea("Untitled Event");
		eventName.setFont(new Font("default", Font.PLAIN, 30));
		int monthInt = c.currentMonth+1;
		JLabel date = new JLabel("Date: " + monthInt + "/" + c.getDayOfMonth() + "/" + c.getYear());
		date.setFont(new Font("default", Font.PLAIN, 30));
		JButton save = new JButton("Save");
		save.setFont(new Font("default", Font.PLAIN, 40));
		JPanel timePanelStart = new JPanel(new GridLayout(0,2));
		JPanel timePanelEnd = new JPanel(new GridLayout(0,2));

		JLabel startlabel = new JLabel("Start: ");
		startlabel.setFont(new Font("default", Font.PLAIN, 30));
		startlabel.setBackground(Color.WHITE);
		startlabel.setOpaque(true);
		JLabel endlabel = new JLabel("End: ");
		endlabel.setFont(new Font("default", Font.PLAIN, 30));
		endlabel.setBackground(Color.WHITE);
		endlabel.setOpaque(true);
		JTextArea startTime = new JTextArea("12:00PM");
		JTextArea endTime = new JTextArea("2:00PM");
		startTime.setFont(new Font("default", Font.PLAIN, 30));
		endTime.setFont(new Font("default", Font.PLAIN, 30));

		timePanelStart.add(startlabel);
		timePanelStart.add(startTime);
		timePanelEnd.add(endlabel);
		timePanelEnd.add(endTime);
		add(instruction);
		add(eventName);
		add(save);
		add(date);
		add(timePanelStart);
		add(timePanelEnd);

		save.addActionListener(new 
				ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				String sT = startTime.getText();
				String[] splitTime = sT.split(":");
				if (splitTime[1].toLowerCase().contains("p"))
				{
					int hour = Integer.parseInt(splitTime[0]);
					if (!(hour == 12))
					{
						hour = hour + 12;
					}
					splitTime[0] = "" + hour;
					splitTime[1] = splitTime[1].substring(0, 2);
				}
				else
				{
					if (splitTime[0].contains("12"))
					{
						splitTime[0] = "00";
					}
					splitTime[1] = splitTime[1].substring(0, 2);
				}

				int eventStartHour = Integer.parseInt(splitTime[0]);
				int eventStartMinute = Integer.parseInt(splitTime[1]);
				int inputMonth = monthInt - 1;
				GregorianCalendar startTimeCalendar = new GregorianCalendar(c.getYear(), inputMonth, c.getDayOfMonth(), eventStartHour, eventStartMinute);
				String eT = endTime.getText();
				BufferedWriter writer = null;
				try {
					writer = new BufferedWriter(new FileWriter("events.txt", true));
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

				boolean conflict = false;

				if (eT.equals(null))
				{
					Event newEvent = new Event(eventName.getText(),startTimeCalendar);
					for (Event conflictEvent : c.getEvents())
					{
						if (conflictEvent.getEnd() != null)
						{
							if (newEvent.getStart().compareTo(conflictEvent.getStart()) >= 0 && newEvent.getStart().compareTo(conflictEvent.getEnd()) <= 0)
							{
								conflict = true;
							}
						}
						else
						{
							if (newEvent.getStart().compareTo(conflictEvent.getStart()) == 0)
							{
								conflict = true;
							}
						}
					}
					if (conflict == false)
					{
						eT = null;
						c.addEvent(newEvent);
						try {
							writer.append(eventName.getText() + "-" + c.getYear() + "-" + inputMonth + "-" + c.getDayOfMonth() + "-" + eventStartHour + "-" + eventStartMinute + "\n");
						} catch (IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						try {
							writer.close();
						} catch (IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						c.remake();
						dispose();

					}
					else
					{
						instruction.setText("Event Time Conflict");
					}

				}
				else
				{
					String[] splitTimeEnd = eT.split(":");
					if (splitTimeEnd[1].toLowerCase().contains("p"))
					{
						int hour = Integer.parseInt(splitTimeEnd[0]);
						if (!(hour == 12))
						{
							hour = hour + 12;
						}
						splitTimeEnd[0] = "" + hour;
						splitTimeEnd[1] = splitTimeEnd[1].substring(0, 2);
					}
					else
					{
						if (splitTimeEnd[0].contains("12"))
						{
							splitTimeEnd[0] = "00";
						}
						splitTimeEnd[1] = splitTimeEnd[1].substring(0, 2);
					}
					int eventEndHour = Integer.parseInt(splitTimeEnd[0]);
					int eventEndMinute = Integer.parseInt(splitTimeEnd[1]);
					GregorianCalendar endTimeCalendar = new GregorianCalendar(c.getYear(), inputMonth, c.getDayOfMonth(), eventEndHour, eventEndMinute);

					Event newEvent = new Event(eventName.getText(), startTimeCalendar, endTimeCalendar);
					for (Event conflictEvent : c.getEvents())
					{
						if (conflictEvent.getEnd() != null)
						{
							if (newEvent.getStart().compareTo(conflictEvent.getStart()) >= 0 && newEvent.getStart().compareTo(conflictEvent.getEnd()) <= 0)
							{
								conflict = true;
							}
							else if (newEvent.getEnd().compareTo(conflictEvent.getStart()) >= 0 && newEvent.getEnd().compareTo(conflictEvent.getEnd()) <= 0)
							{
								conflict = true;
							}
						}
						else
						{
							if (newEvent.getStart().compareTo(conflictEvent.getStart()) == 0 || newEvent.getEnd().compareTo(conflictEvent.getStart()) == 0)
							{
								conflict = true;
							}
							else if (conflictEvent.getStart().compareTo(newEvent.getStart()) >= 0 && conflictEvent.getStart().compareTo(newEvent.getEnd()) <= 0)
							{
								conflict = true;
							}
						}
					}
					if (conflict == false)
					{
						c.addEvent(newEvent);
						try {
							writer.append(eventName.getText() + "-" + c.getYear() + "-" + inputMonth + "-" + c.getDayOfMonth() + "-" + eventStartHour + "-" + eventStartMinute + "-" + eventEndHour + "-" + eventEndMinute + "\n");
						} catch (IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						try {
							writer.close();
						} catch (IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						c.remake();
						dispose();

					}
					else
					{
						instruction.setText("Event Time Conflict");

					}

				}
			}
		}
				);
		setVisible(true);

	}
}

