package env;

import model.*;
import view.*;

public class DebugMain {

	public static void main(String[] args) {
		LabirintoModel model = new LabirintoModel();
		model.initLabirintoLogico();
		LabirintoView  view = new LabirintoView(model.getTable());
		
		
		
				
	}

}
