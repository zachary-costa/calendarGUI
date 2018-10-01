import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

/**
 * Creates a JFrame to display a dayview and a monthview of an inputted MyCalendar. Also allows for the traversal through the calendar and the creation
 * of new events for the calendar.
 * @author Zack
 *
 */
public class CalendarView extends JFrame
{
	private MyCalendar calendar;
	int DEFAULT_WIDTH = 1500;
	int DEFAULT_HEIGHT = 1000;
	int state = 0;
	JButton create = new JButton("Create");
	JButton nextDayButton = new JButton(">");
	JButton prevDayButton = new JButton("<");
	JButton exitButton = new JButton("QUIT");
	JPanel dayControls = new JPanel();
	JPanel monthAndYear = new JPanel();
	boolean createActive = false;
	public CalendarView(MyCalendar calendar)
	{
		setSize(DEFAULT_WIDTH,DEFAULT_HEIGHT);
		setTitle("Calendar");
		//setResizable(false);
		this.calendar = calendar;
		calendar.addComponents(this);
		create.setFont(new Font("default", Font.PLAIN, 40));
		nextDayButton.setFont(new Font("default", Font.PLAIN, 40));
		prevDayButton.setFont(new Font("default", Font.PLAIN, 40));
		exitButton.setFont(new Font("roman", Font.ROMAN_BASELINE, 40));

		nextDayButton.addActionListener(new 
				ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				calendar.nextDay();
				drawState(state);
			}
		}
				);
		prevDayButton.addActionListener(new 
				ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				calendar.previousDay();
				drawState(state);
			}
		}
				);
		
		exitButton.addActionListener(e -> System.exit(0));
		
		create.addActionListener(new 
				ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
					EventAdder event = new EventAdder(calendar);
					
			}
		}
				);
		
		setVisible(true);
		drawState(state);
	}


	/**
	 * drawState draws/adds components to the CalendarView depending on the state which is the input
	 * @param state the current view for the CalendarView.
	 */
	public void drawState(int state)
	{
		if (state == 0)
		{
			JLabel monthYear = new JLabel();
			monthYear.setFont(new Font("default", Font.ROMAN_BASELINE, 40));
			monthYear.setText(calendar.getMonth() + " " + calendar.getYear());
			monthAndYear.setLayout(new GridLayout(2,0));
			monthAndYear.removeAll();
			dayControls.setLayout(new GridLayout());
			dayControls.add(create);
			dayControls.add(prevDayButton);
			dayControls.add(nextDayButton);
			dayControls.add(exitButton);
			MonthPanel monthView = new MonthPanel(calendar, this);
			monthView.construct();
			monthAndYear.add(monthYear);
			add(dayControls, BorderLayout.NORTH);
			monthAndYear.add(monthView);
			add(monthAndYear, BorderLayout.LINE_START);
			DayViewPanel dayView = new DayViewPanel(calendar);
			add(dayView, BorderLayout.CENTER);
			revalidate();
			setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		}
	}

}	
