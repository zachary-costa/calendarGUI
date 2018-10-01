import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 * Creates a JPanel that shows all the events on a given calendar day organized by hour and displayed. Yellow if the event has an end time
 * Green if there is no end time.
 * @author Zack
 *
 */
public class DayViewPanel extends JPanel{

	private MyCalendar c;
	private String[] daysOfWeek = {"Sunday", "Monday",
			"Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"};

	/**
	 * Constructor for the dayViewPanel
	 * @param c is the MyCalendar being used by the daypanel
	 */
	public DayViewPanel(MyCalendar c)
	{
		this.c = c;
		SimpleDateFormat timeFormat = new SimpleDateFormat("hh:mm");
		setLayout(new GridLayout(0,2));
		JLabel date = new JLabel();
		date.setText(daysOfWeek[c.getDayOfWeek()-1] + ", " + c.getMonth() + " " + c.getDayOfMonth() + ", " + c.getYear());
		date.setFont(new Font("default", Font.PLAIN, 30));
		JLabel blank = new JLabel();
		add(date);
		add(blank);
		boolean eventActive = false;
		for (int i = 0; i <= 24; i++)
		{
			JLabel time = new JLabel();
			time.setOpaque(true);

			time.setFont(new Font("default", Font.PLAIN, 30));
			JLabel event = new JLabel();
			String eventDetails = "";
			if (eventActive == true)
			{
				time.setBackground(Color.YELLOW);
				event.setBackground(Color.YELLOW);

			}
			for (Event e: c.getEvents())
			{
				if (e.sameDay(c.currentMonth, c.getDayOfMonth(), c.getYear()))
				{
					if (e.getEnd() == null)
					{
						if (i == e.getStart().get(Calendar.HOUR_OF_DAY))
						{
							String hrmm = timeFormat.format(e.getStart().getTime());
							eventDetails = e.getName();
							time.setBackground(Color.GREEN);
							event.setBackground(Color.GREEN);
						}
					}
					else
					{
						if (i == e.getStart().get(Calendar.HOUR_OF_DAY))
						{
							String hrmm = timeFormat.format(e.getStart().getTime());
							eventDetails = e.getName();
							time.setBackground(Color.YELLOW);
							event.setBackground(Color.YELLOW);
							eventActive = true;
						}
						else if (i == e.getEnd().get(Calendar.HOUR_OF_DAY))
						{
							String hrmm = timeFormat.format(e.getEnd().getTime());
							eventActive = false;
						}
					}
				}
			}
			event.setText(eventDetails);
			event.setFont(new Font("default", Font.PLAIN, 30));
			event.setOpaque(true);

			if (i == 0)
			{
				String timeString = "12am";
				time.setText(timeString);
				time.setFont(new Font("default", Font.PLAIN, 30));
				add(time);
				add(event);

			}
			else if (i == 24)
			{
				String timeString = "12am";
				time.setText(timeString);
				add(time);
				add(event);
			}
			else if (i == 12)
			{
				String timeString = 12 + "pm";
				time.setText(timeString);
				add(time);
				add(event);
			}
			else if (i > 12)
			{
				int adjustI = i - 12;
				String timeString = adjustI + "pm";
				time.setText(timeString);
				add(time);
				add(event);
			}
			else
			{
				String timeString = i + "am";
				time.setText(timeString);
				add(time);
				add(event);
			}
		}
	}

	
}