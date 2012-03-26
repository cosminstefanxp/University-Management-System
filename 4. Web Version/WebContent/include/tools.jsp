<%!
public void error(String msg, JspWriter out) 
{
	try {
		out.write("<div class='error'>");
		out.write("Error: " + msg);
		out.write("</div>");
	} catch (Exception ex) {
		ex.printStackTrace();
	}
}
public void debug(String msg, JspWriter out) 
{
	try {
		out.write("<div class='notify'>");
		out.write("Debug: " + msg);
		out.write("</div>");
	} catch (Exception ex) {
		ex.printStackTrace();
	}
}

public void notify(String msg, JspWriter out) 
{
	try {
		out.write("<div class='notify'>");
		out.write("Notification: " + msg);
		out.write("</div>");
	} catch (Exception ex) {
		ex.printStackTrace();
	}
}
%>