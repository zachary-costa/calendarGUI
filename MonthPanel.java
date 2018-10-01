import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Calendar;
import java.util.GregorianCalendar;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 * Creates a JPanel that shows a calendar where each day is a clickable button to move the calendar to.
 * @author Zack
 *
 */
public class MonthPanel extends JPanel
{
	private MyCalendar c;
	private CalendarView v;
	private String[] daysOfWeek = {"Su", "Mo", "Tu", "We", "Th", "Fr", "Sa"};
	Component[] components = this.getComponents();

	/**
	 * Constructor for the month panel
	 * @param c Calendar the model that is being used by the MonthPanel
	 * @param v the CalendarView the monthpanel is being added to.
	 */
	public MonthPanel(MyCalendar c, CalendarView v)
	{
		this.v = v;
		this.c = c;
		int cols = 7;
		int rows = 0;
		setLayout(new GridLayout(rows,cols));
		setVisible(true);
	}
	
	/**
	 * Goes through all the dayButtons in the MonthPanel and updates them.
	 */
	public void refresh()
	{
		 for (int i = 0; i < components.length; i++) {

		        if (components[i] instanceof JButton) {
		           JButton button = (JButton) components[i];
		            String s = button.getText();
		            int bDay = Integer.parseInt(s);
		            if (c.getDayOfMonth() == bDay)
		            {
		            	button.setBackground(Color.RED);
		            }
		            else
		            {
		            	button.setBackground(Color.WHITE);
		            }
		        
		        }

		    }
	}
	/**
	 * Builds the shape of the monthpanel
	 */
	public void construct()
	{
		for (int i=0; i <= 6; i++)
		{
			JLabel weekDay = new JLabel();
			weekDay.setText(" " + daysOfWeek[i]);
			weekDay.setFont(new Font("default", Font.PLAIN, 30));

			add(weekDay);

		}
		GregorianCalendar tempFirstWeek = new GregorianCalendar(c.getYear(), c.currentMonth, 1);
		int blankDays = tempFirstWeek.get(Calendar.DAY_OF_WEEK);
		for (int  i = 1; i < blankDays; i++)
		{
			JLabel blankDay = new JLabel();
			blankDay.setFont(new Font("default", Font.PLAIN, 30));
			add(blankDay);
		}
		for (int day = 1; day <= c.getDaysInMonth();)
		{
			String dayString = ""+day;
			JButton dayButton = new JButton(dayString);

			dayButton.setFont(new Font("default", Font.PLAIN, 30));

			if (day == c.getCalendar().get(Calendar.DATE))
			{
				dayButton.setBackground(Color.red);
			}
			else
			{
				dayButton.setBackground(Color.white);
			}
			add(dayButton);
			final int bday = day;
			dayButton.addActionListener(new 
					ActionListener()
			{
				public void actionPerformed(ActionEvent e)
				{
					int moveDays;
					if (bday > c.getDayOfMonth())
					{
						moveDays = bday - c.getDayOfMonth();
						for (int i = 1; i <= moveDays; i++)
						{
							c.nextDay();
						}
					}
					else
					{
						moveDays = c.getDayOfMonth() - bday;
						for (int i = 1; i <= moveDays; i++)
						{
							c.previousDay();
						}
					}
					v.drawState(0);
					
				}
			}
					);

			day++;
		}


	}
}
