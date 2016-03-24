package addons.dice;

import java.awt.GraphicsDevice;
import java.awt.GraphicsDevice.WindowTranslucency;
import java.awt.GraphicsEnvironment;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.Timer;

public class EE {

	public class HaGay extends JFrame implements Runnable {

		/**
		 * 
		 */
		private static final long serialVersionUID = 1683763407461041330L;
		JFrame frm = this;

		public HaGay() throws HeadlessException {
		}

		private void init() {
			Timer tim = new Timer(3000, new ClosingTimer());
			tim.start();
			setUndecorated(true);
			// Determine if the GraphicsDevice supports translucency.
			GraphicsEnvironment ge =
					GraphicsEnvironment.getLocalGraphicsEnvironment();
			GraphicsDevice gd = ge.getDefaultScreenDevice();

			// If translucent windows aren't supported, exit.
			if (!gd.isWindowTranslucencySupported(WindowTranslucency.TRANSLUCENT)) {
				System.err.println("Translucency is not supported.");
			} else {
				setOpacity(1f);
			}

			setDefaultCloseOperation(EXIT_ON_CLOSE);
			ImageIcon img = null;
			try {
				img = new ImageIcon(ImageIO.read(this.getClass().getResourceAsStream("/images/HG.png")));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			add(new JLabel(img));

			setSize(img.getIconWidth(), img.getIconHeight());
			setLocationRelativeTo(null);
			setVisible(true);
		}

		@Override
		public void run() {
			init();
		}

		class ClosingTimer implements ActionListener {

			@Override
			public void actionPerformed(ActionEvent e) {
				((Timer) e.getSource()).stop();
				frm.dispose();
			}
		}
	}

}
