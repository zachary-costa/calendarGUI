import java.io.IOException;

public class SimpleCalendar {
	public static void main(String []args) throws IOException
	{
		MyCalendar theCalendar = new MyCalendar();
		theCalendar.load();
		CalendarView view = new CalendarView(theCalendar);
	}
}
