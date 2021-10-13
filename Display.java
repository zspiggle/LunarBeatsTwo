import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.image.*;
import java.io.*;
import java.lang.reflect.Array;

import javax.imageio.*;
import java.util.ArrayList;

public class Display extends JFrame {

	private boolean isPlaying = false;

	//private ArrayList<JButton> buttons;
	private static ButtonListener buttonListener;

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
	private JButton delete_playlist_button;

	private ArrayList<JButton> likeButtons;
	private ArrayList<JButton> addToButtons;
	private ArrayList<JButton> playButtons;
	private ArrayList<Integer> ids;


	public static Color ui_color = new Color(57, 172, 231);
	public static Color selected_color = new Color(65, 76, 80);
	public static Color primary = new Color(45, 56, 60);
	public static Color bgColor = new Color(25,36,40);


  private ArrayList<String> imageLocations;
  private ArrayList<Icon> icons;

  private String location = "Icons/";


  private JPanel contentPane;
	private JTextField search_field;

	private Label currentSongDisplay;
	private Label currentArtistDisplay;

	private Label currentPlaylistDisplay;

	//Central layout display
	private JPanel center_panel;

	private ArrayList<SongPanel> songPanels;


  public Display(){

		buttonListener = new ButtonListener(this);
		//buttons = new ArrayList<JButton>();
		loadImages(); 

		songPanels = new ArrayList<SongPanel>();
		likeButtons = new ArrayList<JButton>();
		addToButtons = new ArrayList<JButton>();
		playButtons = new ArrayList<JButton>();
		ids = new ArrayList<Integer>();

		//Color secondary = new Color(45, 56, 60); new Color(65, 76, 80);

    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 960, 540);
		contentPane = new JPanel();
		//contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);


		currentPlaylistDisplay = new Label("PLAYLIST: Null");
		
		
		delete_playlist_button = new JButton("Delete Playlist");
		setupButton(delete_playlist_button);

		resetCenter();



		JPanel north_panel = new JPanel();
		contentPane.add(north_panel, BorderLayout.NORTH);
		north_panel.setLayout(new BorderLayout(0, 0));
		north_panel.setBackground(Display.primary);




		JPanel search_panel = new JPanel();
		north_panel.add(search_panel, BorderLayout.EAST);
		search_panel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		search_panel.setBackground(Display.primary);
		
		search_field = new JTextField();
		search_panel.add(search_field);
		search_field.setColumns(20);
		search_field.setBackground(ui_color);
		search_field.setForeground(Color.WHITE);
		search_field.setCaretColor(Color.WHITE);

		search_button = new JButton("Search");//getIcon("search_button"));
		setupButton(search_button);
		search_button.setBackground(selected_color);
		search_panel.add(search_button);
		
		JPanel settings_panel = new JPanel();
		north_panel.add(settings_panel, BorderLayout.WEST);
		settings_panel.setBackground(Display.primary);
		
		settings_button = new JButton("Settings");//getIcon("settings_button"));
		setupButton(settings_button);
		settings_panel.add(settings_button);
		
		JPanel display_panel = new JPanel();
		north_panel.add(display_panel, BorderLayout.CENTER);
		display_panel.setBackground(Display.primary);

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
		south_panel.setBackground(Display.primary);
		
		JPanel center_control_panel = new JPanel();
		south_panel.add(center_control_panel, BorderLayout.CENTER);
		center_control_panel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		center_control_panel.setBackground(Display.primary);

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
		right_control_panel.setBackground(Display.primary);

		shuffle_button = new JButton(getIcon("Shuffle"));
		setupButton(shuffle_button);
		right_control_panel.add(shuffle_button);

		loop_button = new JButton(getIcon("Loop"));
		setupButton(loop_button);
		right_control_panel.add(loop_button);

		JPanel left_control_panel = new JPanel();
		south_panel.add(left_control_panel, BorderLayout.WEST);
		left_control_panel.setBackground(Display.primary);

		volume_button = new JButton(getIcon("VolumeMed"));
		setupButton(volume_button);
		left_control_panel.add(volume_button);
		
		mute_button = new JButton(getIcon("Mute"));
		setupButton(mute_button);
		left_control_panel.add(mute_button);
		
		



		JPanel west_panel = new JPanel();
		contentPane.add(west_panel, BorderLayout.WEST);
		west_panel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		west_panel.setBackground(Display.primary);

		JScrollPane scroll_pane2 = new JScrollPane(west_panel);
    contentPane.add(scroll_pane2, BorderLayout.WEST);

		JButton liked_playlist = new JButton("Liked Songs");
		setupButton(liked_playlist); 
		liked_playlist.setVerticalAlignment(SwingConstants.TOP);
		west_panel.add(liked_playlist);

    Image img = Toolkit.getDefaultToolkit().getImage("Icons/ProgramIcon.png");
    setIconImage(img);

		addSongPanels();

		loadPlaylistToDisplay(App.app.findPlaylist(0));

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
		b.setBackground(Display.ui_color);
		b.setForeground(Color.WHITE);
		//buttons.add(b);
		b.addActionListener(buttonListener);
	}


	private void addSongPanels(){

		for(SongPanel s : songPanels){
			center_panel.add(s.toJPanel());
			if(s.getLikeButton() != null){
				setupButton(s.getLikeButton());
				likeButtons.add(s.getLikeButton());
			}
			if(s.getAddToButton() != null){
				setupButton(s.getAddToButton());
				addToButtons.add(s.getAddToButton());
			
			}
			if(s.getPlayButton() != null){
				setupButton(s.getPlayButton());
				playButtons.add(s.getPlayButton());
			}
		}

	}

	private void resetSongPanels(){
		likeButtons = new ArrayList<JButton>();
		addToButtons = new ArrayList<JButton>();
		playButtons = new ArrayList<JButton>();
		songPanels = new ArrayList<SongPanel>();
		ids = new ArrayList<Integer>();

		resetCenter();
	}


	private void resetCenter(){
		center_panel = new JPanel();
		contentPane.add(center_panel, BorderLayout.CENTER);
		center_panel.setBackground(Display.bgColor);
		center_panel.setLayout(new GridLayout(0,1,0,0));
		
		
    JScrollPane scroll_pane = new JScrollPane(center_panel);
    contentPane.add(scroll_pane);

		JPanel playlist_panel = new JPanel();
		center_panel.add(playlist_panel);
		playlist_panel.setBackground(Display.bgColor);

		playlist_panel.add(currentPlaylistDisplay);
		currentPlaylistDisplay.setForeground(Color.WHITE);
		currentPlaylistDisplay.setFont(currentPlaylistDisplay.getFont().deriveFont(18.0f));
		playlist_panel.add(delete_playlist_button);

		JPanel header = new JPanel(new FlowLayout(FlowLayout.LEFT, 75, 0));
		center_panel.add(header);
		header.setBackground(Display.bgColor);

		JLabel song_column = new JLabel("SONG"); 
		header.add(song_column);
		song_column.setForeground(Color.WHITE);
		song_column.setFont(song_column.getFont().deriveFont(14.0f));

		JLabel artist_column = new JLabel("ARTIST"); 
		header.add(artist_column);
		artist_column.setForeground(Color.WHITE);
		artist_column.setFont(artist_column.getFont().deriveFont(14.0f));


		addSongPanels();
	}


	public void loadPlaylistToDisplay(Playlist p){

		resetSongPanels();

		currentPlaylistDisplay.setText("PLAYLIST: " + p.name);

		for(Song s : p.songs){
			SongPanel sp = new SongPanel(s.getName(), s.getArtist(), s.getID());
			ids.add(s.getID());
			songPanels.add(sp);
		}
		
		resetCenter();

	}

	
	public void updatePlayButton(){
		if(App.app.audio.isPlaying() == true){
			play_button.setIcon(getIcon("Play"));
		} else {
			play_button.setIcon(getIcon("Pause"));
		}
	}

	public void updateVolumeButton(){
		switch(App.app.audio.getVolumeSetting()){

			case 1: volume_button.setIcon(getIcon("VolumeLow")); break;
			case 2: volume_button.setIcon(getIcon("VolumeMed")); break;
			case 3: volume_button.setIcon(getIcon("VolumeMax")); break;

		}
	}

	public void updateMuteButton(){
		if(App.app.audio.isMute() == true){
			mute_button.setBackground(selected_color);
		} else {
			mute_button.setBackground(ui_color);
		}
	}


	public void handleButtonPress(Object src){
		
		if(src == play_button){
			if(App.app.audio.isPlaying() == true){
				App.app.audio.pause();
			} else {
				App.app.audio.play();
			}

		} else if(src == skip_button) {


		} else if(src == volume_button){
			Audio audio = App.app.audio;

			int vol = App.app.audio.getVolumeSetting();

			if(vol == 1){
				App.app.audio.setVolumeSetting(2);
			} else if(vol == 2){
				App.app.audio.setVolumeSetting(3);
			} else {
				App.app.audio.setVolumeSetting(1);
			}


		} else if(src == mute_button){
			try{
				App.app.audio.mute();
			} catch (Exception e){
				//nothing
			}
		} else {

			boolean foundButton = false;

			//check all playlist and songpanel buttons
			for(JButton b : playButtons){
				if(src == b){
					App.app.audio.loadSong(App.app.findSong(ids.get(playButtons.indexOf(b))).getFileLoc());
					App.app.audio.play();
					foundButton = true;
				}
			}



			if(foundButton == false){
				System.out.println("Button not found");
			}
			
		}
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
