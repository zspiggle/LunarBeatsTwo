

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.image.*;
import java.io.*;
import javax.imageio.*;
import java.util.ArrayList;

public class Display extends JFrame {

	//private ArrayList<JButton> buttons;
	private ButtonListener buttonListener;

	//Buttons
	private JButton search_button;
	private JButton settings_button;
	private JButton previous_button;
	private JButton play_button;
	private JButton skip_button;
	private JButton shuffle_button;
	private JButton loop_button;
	private JButton volume_button;
	private JButton mute_button;

	private Color ui_color = new Color(63, 0, 113);

  private ArrayList<String> imageLocations;
  private ArrayList<Icon> icons;

  private String location = "Icons/";


  private JPanel contentPane;
	private JTextField search_field;

	private Label currentSongDisplay;
	private Label currentArtistDisplay;

  public Display(){

		buttonListener = new ButtonListener(this);
		//buttons = new ArrayList<JButton>();
		loadImages(); 

		Color primary = new Color(31, 27, 36);
		Color bgColor = new Color(0,0,0);
		//Color secondary = new Color(21, 0, 80);

    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 960, 540);
		contentPane = new JPanel();
		//contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);

		
		JPanel center_panel = new JPanel();
		contentPane.add(center_panel, BorderLayout.CENTER);
		center_panel.setBackground(bgColor);

    JScrollPane scroll_pane = new JScrollPane(center_panel);
    contentPane.add(scroll_pane);

		JPanel north_panel = new JPanel();
		contentPane.add(north_panel, BorderLayout.NORTH);
		north_panel.setLayout(new BorderLayout(0, 0));
		north_panel.setBackground(primary);




		JPanel search_panel = new JPanel();
		north_panel.add(search_panel, BorderLayout.EAST);
		search_panel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		search_panel.setBackground(primary);
		
		search_field = new JTextField();
		search_panel.add(search_field);
		search_field.setColumns(20);
		search_field.setBackground(ui_color);
		search_field.setForeground(Color.WHITE);
		search_field.setCaretColor(Color.WHITE);

		search_button = new JButton("Search");//getIcon("search_button"));
		setupButton(search_button);
		search_panel.add(search_button);
		
		JPanel settings_panel = new JPanel();
		north_panel.add(settings_panel, BorderLayout.WEST);
		settings_panel.setBackground(primary);
		
		settings_button = new JButton("Settings");//getIcon("settings_button"));
		setupButton(settings_button);
		settings_panel.add(settings_button);
		
		JPanel display_panel = new JPanel();
		north_panel.add(display_panel, BorderLayout.CENTER);
		display_panel.setBackground(primary);

		Label displayCurrent = new Label("Currently Playing...");
		displayCurrent.setForeground(Color.WHITE);
		display_panel.add(displayCurrent);

		currentSongDisplay = new Label("Song: null");
		currentSongDisplay.setForeground(Color.WHITE);
		display_panel.add(currentSongDisplay);

		currentArtistDisplay = new Label("Artist: null");
		currentArtistDisplay.setForeground(Color.WHITE);
		display_panel.add(currentArtistDisplay);





		JPanel south_panel = new JPanel();
		contentPane.add(south_panel, BorderLayout.SOUTH);
		south_panel.setLayout(new BorderLayout(0, 0));
		south_panel.setBackground(primary);
		
		JPanel center_control_panel = new JPanel();
		south_panel.add(center_control_panel, BorderLayout.CENTER);
		center_control_panel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		center_control_panel.setBackground(primary);

		previous_button = new JButton(getIcon("Previous"));
		setupButton(previous_button);
		center_control_panel.add(previous_button);
		
		play_button = new JButton(getIcon("Play"));
		setupButton(play_button);
		center_control_panel.add(play_button);
		
		skip_button = new JButton(getIcon("Skip"));
		setupButton(skip_button);
		center_control_panel.add(skip_button);
		
		JPanel right_control_panel = new JPanel();
		south_panel.add(right_control_panel, BorderLayout.EAST);
		right_control_panel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		right_control_panel.setBackground(primary);

		shuffle_button = new JButton(getIcon("Shuffle"));
		setupButton(shuffle_button);
		right_control_panel.add(shuffle_button);

		loop_button = new JButton(getIcon("Loop"));
		setupButton(loop_button);
		right_control_panel.add(loop_button);

		JPanel left_control_panel = new JPanel();
		south_panel.add(left_control_panel, BorderLayout.WEST);
		left_control_panel.setBackground(primary);

		volume_button = new JButton(getIcon("VolumeMed"));
		setupButton(volume_button);
		left_control_panel.add(volume_button);
		
		mute_button = new JButton(getIcon("Mute"));
		setupButton(mute_button);
		left_control_panel.add(mute_button);
		
		


		
		JPanel west_panel = new JPanel();
		contentPane.add(west_panel, BorderLayout.WEST);
		west_panel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		west_panel.setBackground(primary);

		JButton liked_playlist = new JButton("Liked Songs");
		setupButton(liked_playlist); 
		liked_playlist.setVerticalAlignment(SwingConstants.TOP);
		west_panel.add(liked_playlist);

    Image img = Toolkit.getDefaultToolkit().getImage("Icons/ProgramIcon.png");
    setIconImage(img);


  }




  private void loadImages(){

		imageLocations = new ArrayList<String>();
		icons = new ArrayList<Icon>();

		File loc = new File(location);

		File[] files = loc.listFiles();

		//Go through all files within the folder
		if(files != null){
				for(int i = 0; i < files.length; i++){
						File f = files[i];

						imageLocations.add(f.getName());

				}
		}
	




    for(String l : imageLocations){
        try{
            String direct = location + l;
            Image buttonImage = ImageIO.read(new File(direct));
						//Image newimg = buttonImage.getScaledInstance( 24, 24,  java.awt.Image.SCALE_REPLICATE ) ;
            Icon buttonIcon = new ImageIcon(buttonImage);
						
            icons.add(buttonIcon);

        } catch (IOException e){
            System.out.println("There was an error loading an image.");
            System.out.println(e);
        }
    }

  }

  /**
   * Get the icon within the icon arraylist by name
   * 
   * @param  String name  name of icon you are looking for
   * @return  The icon with the name you gave as a parameter
   */
  private Icon getIcon(String name){
      String loc = name + ".png";
      return icons.get(imageLocations.indexOf(loc));
  }

	private void setupButton(JButton b){
		b.setFocusPainted(false);
		b.setBackground(ui_color);
		b.setForeground(Color.WHITE);
		//buttons.add(b);
		b.addActionListener(buttonListener);
	}

	public void handleButtonPress(Object src){
		System.out.println("Button not found");
	// 	if(src == playButton){
	// 		if (application.getisPlaying() == false){
	// 				application.setisPlaying(true);
	// 				application.playSong();
	// 				playButton.setIcon(getIcon("Pause"));
	// 		} else if(application.getisPlaying() == true){
	// 				application.setisPlaying(false);
	// 				application.pauseSong();
	// 				playButton.setIcon(getIcon("Play"));
	// 		}
	// } else if (src == skipButton){

	// 		application.nextSong();

	// } else if (src == prevButton){

	// 		application.prevSong();

	// } else if (src == shuffleButton){
	// 		if (application.getisShuffling() == false){
	// 				application.setisShuffling(true);
	// 				shuffleButton.setIcon(getIcon("Shuffling"));
	// 		} else if(application.getisShuffling() == true){
	// 				application.setisShuffling(false);
	// 				shuffleButton.setIcon(getIcon("Shuffle"));
	// 		}

	// } else if (src == loopButton){
	// 		if (application.getisLooping() == false){
	// 				application.setisLooping(true);
	// 				loopButton.setIcon(getIcon("Looping"));
	// 		} else if(application.getisLooping() == true){
	// 				application.setisLooping(false);
	// 				loopButton.setIcon(getIcon("Loop"));
	// 		}
	/*} else if (src == likeButton){
			if (application.getLiked() == false){
					application.likeSong();
					likeButton.setIcon(getIcon("Liked"));
					//likeButton.setIcon(getIcon("Looping"));
			} else if(application.getLiked() == true){
					application.unlikeSong();
					likeButton.setText("Like");
					//likeButton.setIcon(getIcon("Loop"));
			}*/
	// } else if (src == volumeButton){
	// 		if(application.getVolume() == 0){
	// 				volumeButton.setIcon(getIcon("VolumeLow"));
	// 				application.setVolume(1);
	// 		} else if(application.getVolume() == 1){
	// 				volumeButton.setIcon(getIcon("VolumeMed"));
	// 				application.setVolume(2);
	// 		} else if(application.getVolume() == 2){
	// 				volumeButton.setIcon(getIcon("VolumeMax"));
	// 				application.setVolume(3);
	// 		}else{
	// 				volumeButton.setIcon(getIcon("Mute"));
	// 				application.setVolume(0);
	// 		}
	// } else {
		
			
	// 		//if(found = false){
	// 		System.out.println("Button function not found");
	// 		//}
	// 	}
	// }
	}
}
