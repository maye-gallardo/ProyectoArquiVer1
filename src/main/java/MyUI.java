import java.util.List;
import java.util.Scanner;

import javax.servlet.annotation.WebServlet;
 
import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

@Theme("valo")
public class MyUI extends UI {
	MailSystem system = new MailSystem(20);
    String input = "";
    Scanner console = new Scanner(input);
    Telephone phone = new Telephone(console);
    Web web = new Web();
    Connection connection = new Connection(system, web);
    
 
    @Override
    protected void init(VaadinRequest vaadinRequest) {
        
    	final HorizontalLayout base = new HorizontalLayout();
        final VerticalLayout layout = new VerticalLayout();
        final TextField name = new TextField();
        
        TextArea menu = new TextArea(); 
        
        name.setReadOnly(true);
        menu.setReadOnly(true);
        menu.setValue(web.getResponse());
 
        Button button_number_1= new Button("1");
        button_number_1.addClickListener( e -> {
            name.setValue(name.getValue()+"1");
        });
        Button button_number_2 = new Button("2");
        button_number_2.addClickListener( e -> {
        	name.setValue(name.getValue()+"2");
        });
        Button button_number_3 = new Button("3");
        button_number_3.addClickListener( e -> {
        	name.setValue(name.getValue()+"3");
        });
        Button button_number_4 = new Button("4");
        button_number_4.addClickListener( e -> {
        	name.setValue(name.getValue()+"4");
        });
        Button button_number_5 = new Button("5");
        button_number_5.addClickListener( e -> {
        	name.setValue(name.getValue()+"5");
        });
        Button button_number_6 = new Button("6");
        button_number_6.addClickListener( e -> {
        	name.setValue(name.getValue()+"6");
        });
        Button button_number_7 = new Button("7");
        button_number_7.addClickListener( e -> {
        	name.setValue(name.getValue()+"7");
        });
        Button button_number_8 = new Button("8");
        button_number_8.addClickListener( e -> {
        	name.setValue(name.getValue()+"8");
        });
        Button button_number_9 = new Button("9");
        button_number_9.addClickListener( e -> {
        	name.setValue(name.getValue()+"9");
        });
        Button button_number_0 = new Button("0");
        button_number_0.addClickListener( e -> {
        	name.setValue(name.getValue()+"0");
        });
        
        final HorizontalLayout first_row = new HorizontalLayout();
        final HorizontalLayout second_row = new HorizontalLayout();
        final HorizontalLayout third_row = new HorizontalLayout();
        final HorizontalLayout fourt_row = new HorizontalLayout();
        final HorizontalLayout display = new HorizontalLayout();
        
        first_row.addComponents(button_number_1,button_number_2,button_number_3);
        second_row.addComponents(button_number_4,button_number_5,button_number_6);
        third_row.addComponents(button_number_7,button_number_8,button_number_9);
        fourt_row.addComponents(button_number_0);
        
        Button call = new Button("CALL");
        Button hangup = new Button("HANGUP");
        Button enter = new Button("enter");
        
        call.addClickListener( e -> pressCall(name, menu));
        enter.addClickListener( e -> pressEnter(name, menu));
        hangup.addClickListener( e -> pressHangup(name, menu));
        display.addComponents(name,call,hangup,enter);
        
        layout.addComponents(display, first_row,second_row, third_row, fourt_row);
        layout.setMargin(true);
        layout.setSpacing(true);
        
        base.addComponents(layout, menu);
        setContent(base);  
    }
    
    public void pressCall(TextField data, TextArea display) {
    	connection.dial(data.getValue());
    	connection.dial("#");
    	data.setValue("");
    	//display.setValue(phone.getResponse());
    	display.setValue(web.getResponse());
    }

    public void pressEnter(TextField data, TextArea display) {
    	connection.dial(data.getValue());
    	data.setValue("");
    	//display.setValue(phone.getResponse());
    	display.setValue(web.getResponse());
    }
    
    public void pressHangup(TextField data, TextArea display) {
    	connection.hangup();
    	//display.setValue(phone.getResponse());
    	display.setValue(web.getResponse());
    }
 
    @WebServlet(urlPatterns = "/*", name = "MyUIServlet", asyncSupported = true)
    @VaadinServletConfiguration(ui = MyUI.class, productionMode = false)
    public static class MyUIServlet extends VaadinServlet {
    }
}