import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioSystem;
import javax.swing.Timer;


public class TanksApplication 
{
	public static void main(String[] args) throws Exception
	{
		TanksModel model = new TanksModel(null);
		TanksController controller = new TanksController(model);
		model.setController(controller);
		
		final TanksGUI gui = new TanksGUI(model, controller);

		Timer t = new Timer(10, new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent a_arg0) {
				gui.update();
			}
		});
		t.start();
		
		for(AudioFileFormat.Type a : AudioSystem.getAudioFileTypes())
		{
			System.out.println(a);
		}
		
	}
}
