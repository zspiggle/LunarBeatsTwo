import javax.swing.*;
import javax.swing.BoxLayout;
import javax.swing.border.*;

//import org.graalvm.compiler.hotspot.nodes.aot.ResolveMethodAndLoadCountersNode;

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

	// private JButton create_playlist_button;

	private ArrayList<JButton> likeButtons;
	private ArrayList<JButton> addToButtons;
	private ArrayList<JButton> playButtons;
	private ArrayList<Integer> ids;


	public static Color ui_color = new Color(57, 172, 231);
	public static Color selected_color = new Color(7,132,181);//new Color(65, 76, 80);
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
	private JPanel song_display;
	JScrollPane scroll_pane;

	private ArrayList<SongPanel> songPanels;


	//West layout
	private JPanel west_panel;

	private ArrayList<JButton> playlistButtons;
	private ArrayList<Integer> playlistIds;
	private int selectedPlaylist;
	private ArrayList<JPanel> playlistPanels;		

  public Display(){

		buttonListener = new ButtonListener(this);
		//buttons = new ArrayList<JButton>();
		loadImages(); 

		songPanels = new ArrayList<SongPanel>();
		likeButtons = new ArrayList<JButton>();
		addToButtons = new ArrayList<JButton>();
		playButtons = new ArrayList<JButton>();
		ids = new ArrayList<Integer>();

		playlistIds = new ArrayList<Integer>();
		playlistButtons = new ArrayList<JButton>();

		playlistPanels = new ArrayList<JPanel>();
		//Color secondary = new Color(45, 56, 60); new Color(65, 76, 80);

    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 940, 540);
		contentPane = new JPanel();
		//contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);


		currentPlaylistDisplay = new Label("PLAYLIST: Null");
		
		
		delete_playlist_button = new JButton(getIcon("Bin"));
		setupButton(delete_playlist_button);





			center_panel = new JPanel();
			contentPane.add(center_panel, BorderLayout.CENTER);
			center_panel.setBackground(Display.bgColor);
			center_panel.setLayout(new BoxLayout(center_panel, BoxLayout.Y_AXIS));
	

			
			scroll_pane = new JScrollPane(center_panel,JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
			contentPane.add(scroll_pane);
	
			JPanel playlist_panel = new JPanel(new FlowLayout(FlowLayout.CENTER));
			center_panel.add(playlist_panel);
			playlist_panel.setBackground(Display.bgColor);
	
			playlist_panel.add(currentPlaylistDisplay);
			currentPlaylistDisplay.setForeground(Color.WHITE);
			//currentPlaylistDisplay.setPreferredSize(new Dimension(0,30));
			//currentPlaylistDisplay.setText("PLAYLIST: " + App.app.getCurrentPlaylist().getName());
			currentPlaylistDisplay.setFont(currentPlaylistDisplay.getFont().deriveFont(18.0f));
			playlist_panel.add(delete_playlist_button);
			//playlist_panel.setSize(new Dimension(250,25));
	
			JPanel header = new JPanel(new FlowLayout(FlowLayout.LEFT, 75, 0));
			center_panel.add(header);
			header.setBackground(Display.bgColor);
			//header.setSize(new Dimension(0,150));
	
			JLabel song_column = new JLabel("SONG"); 
			header.add(song_column);
			song_column.setForeground(Color.WHITE);
			song_column.setFont(song_column.getFont().deriveFont(14.0f));
			//song_column.setSize(new Dimension(250,25));
	
			JLabel artist_column = new JLabel("ARTIST"); 
			header.add(artist_column);
			artist_column.setForeground(Color.WHITE);
			artist_column.setFont(artist_column.getFont().deriveFont(14.0f));




			song_display = new JPanel(new GridLayout(0,1,0,0));
			center_panel.add(song_display);
			song_display.setBackground(Display.bgColor);


		//resetCenter();
		//contentPane.add(center_panel, BorderLayout.CENTER);

		//build



		//resetCenter();

		

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

		search_button = new JButton(getIcon("Glass"));
		setupButton(search_button);
		search_button.setBackground(ui_color);
		search_panel.add(search_button);
		
		JPanel settings_panel = new JPanel();
		north_panel.add(settings_panel, BorderLayout.WEST);
		settings_panel.setBackground(Display.primary);
		
		settings_button = new JButton(getIcon("Gear"));
		setupButton(settings_button);
		settings_panel.add(settings_button);
		
		JPanel display_panel = new JPanel();
		north_panel.add(display_panel, BorderLayout.CENTER);
		display_panel.setBackground(Display.primary);

		Label displayCurrent = new Label("Currently Playing...");
		displayCurrent.setForeground(Color.WHITE);
		display_panel.add(displayCurrent);

		currentSongDisplay = new Label("Song: ~");
		currentSongDisplay.setForeground(Color.WHITE);
		display_panel.add(currentSongDisplay);
		currentSongDisplay.setPreferredSize(new Dimension(200, 25));

		currentArtistDisplay = new Label("Artist: ~");
		currentArtistDisplay.setForeground(Color.WHITE);
		display_panel.add(currentArtistDisplay);
		currentSongDisplay.setPreferredSize(new Dimension(150, 25));




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
		
		


		createPlaylistsDisplay();
		updatePlaylistsDisplay();




    Image img = Toolkit.getDefaultToolkit().getImage("Icons/ProgramIcon.png");
    setIconImage(img);

		addSongPanels();

		loadPlaylistToDisplay(App.app.getCurrentPlaylist());
		selectedPlaylist = App.app.getCurrentPlaylist().getID();
		//updateSelectedPlaylist();
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
  public Icon getIcon(String name){
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
			song_display.add(s.toJPanel());
			// if(s.getLikeButton() != null){
			// 	setupButton(s.getLikeButton());
			// 	likeButtons.add(s.getLikeButton());
			// }
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

	// private void resetSongPanels(){

	// 	for(SongPanel sp : songPanels){
	// 		center_panel.remove(sp.toJPanel());
	// 	}

	// 	//contentPane.remove(center_panel);

	// 	likeButtons = new ArrayList<JButton>();
	// 	addToButtons = new ArrayList<JButton>();
	// 	playButtons = new ArrayList<JButton>();
	// 	songPanels = new ArrayList<SongPanel>();
	// 	ids = new ArrayList<Integer>();

	// 	resetCenter();
	// }


	// private void resetCenter(){
	// 	


	// }


	// private void buildCenter(){


	// 	//resetCenter();
	// 	contentPane.add(center_panel, BorderLayout.CENTER);

	// 	scroll_pane = new JScrollPane(center_panel,JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
	// 	contentPane.add(scroll_pane);


	// 	//center_panel.revalidate();
	// 	//center_panel.repaint();


	// 	addSongPanels();

	// 	center_panel.revalidate();
	// 	center_panel.repaint();

	// 	contentPane.revalidate();
	// 	contentPane.repaint();

	
	// }



	public void loadPlaylistToDisplay(Playlist p){


		currentPlaylistDisplay.setText("PLAYLIST: " + p.getName());
		center_panel.remove(song_display);

		//First remove any song panels, if they exist
		for(SongPanel sp : songPanels){
			song_display.remove(sp.toJPanel());
		}

		song_display = new JPanel(new GridLayout(0,1,0,5));
		center_panel.add(song_display);
		song_display.setBackground(Display.bgColor);

		//Reset arrays
		likeButtons = new ArrayList<JButton>();
		addToButtons = new ArrayList<JButton>();
		playButtons = new ArrayList<JButton>();
		songPanels = new ArrayList<SongPanel>();
		ids = new ArrayList<Integer>();

		song_display.revalidate();
		song_display.repaint();

		center_panel.revalidate();
		center_panel.repaint();

		contentPane.revalidate();
		contentPane.repaint();


		//Then load new song panels to the song display
		//Create song panels
		for(Song s : p.getSongs()){
			SongPanel sp = new SongPanel(s.getName(), s.getArtist(), s.getID(), this);
			ids.add(s.getID());
			songPanels.add(sp);
		}

		//Add song panels
		addSongPanels();



		//Reload
		song_display.revalidate();
		song_display.repaint();

		center_panel.revalidate();
		center_panel.repaint();

		contentPane.revalidate();
		contentPane.repaint();
	}

	


















	public void updateCurrentlyPlaying(){
		Song current = App.app.getCurrentSong();

		currentArtistDisplay.setText("Artist: " + current.getArtist());
		currentSongDisplay.setText("Song: " + current.getName());

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


	public void updateShuffleButton(){
		if(App.app.getIsShuffling() == true){
			shuffle_button.setIcon(getIcon("Shuffling"));
		} else {
			shuffle_button.setIcon(getIcon("Shuffle"));
		}
	}

	public void updateLoopButton(){
		if(App.app.getIsLooping() == true){
			loop_button.setIcon(getIcon("Looping"));
		} else {
			loop_button.setIcon(getIcon("Loop"));
		}
	}


	// public void updateSelectedPlaylist(){
	// 	if (App.app.getCurrentPlaylist().getID() != selectedPlaylist){
	// 		selectedPlaylist = App.app.getCurrentPlaylist().getID();
	// 	}

	// 	for(JButton b : playlistButtons){
	// 		b.setBackground(ui_color);
	// 	}

	// 	for(Integer i : playlistIds){
	// 		if(i == selectedPlaylist){
	// 			playButtons.get(playlistIds.indexOf(i)).setBackground(selected_color);
	// 			System.out.println(playButtons.get(playlistIds.indexOf(i)).getText());
	// 		}
	// 	}
		

	// }


	public void updatePlaylistsDisplay(){

		for(JPanel p : playlistPanels){
			west_panel.remove(p);
		}
		


		for(Playlist p : App.app.playlists){

			// boolean flag = false;

			// for(Integer i : playlistIds ){
			// 		if (p.getID() == i){
			// 			flag = true;
			// 		}
			// }

			// if(!flag){

				JPanel buttonPane = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));
				buttonPane.setBackground(Display.primary);
			//	buttonPane.setPreferredSize(new Dimension(25, 25));

				playlistPanels.add(buttonPane);

				JButton b = new JButton(p.getName());
				b.setPreferredSize(new Dimension(125,25));
				setupButton(b);
				playlistButtons.add(b);
				playlistIds.add(p.getID());
				buttonPane.add(b);
				west_panel.add(buttonPane);
			//}
		}

		west_panel.revalidate();
		west_panel.repaint();
	}



	public void createPlaylistsDisplay(){
		west_panel = new JPanel(new GridLayout(0,1,0,0));
		contentPane.add(west_panel, BorderLayout.WEST);
		//west_panel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		west_panel.setBackground(Display.primary);

		JScrollPane scroll_pane2 = new JScrollPane(west_panel, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
		JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
    	contentPane.add(scroll_pane2, BorderLayout.WEST);
	}


//---------------------------------------------------------------------------------------------------------------------------------------









	public void handleButtonPress(Object src){
		if(src == play_button){
			if(App.app.audio.isPlaying() == true){
				App.app.audio.pause();
			} else {
				App.app.audio.play();
			}

		} else if(src == skip_button) {
			App.app.nextSong();

		} else if(src == previous_button) {
			App.app.prevSong();

		} else if(src == volume_button){
			Audio audio = App.app.audio;


			if(audio.getAudioClip() != null){
				int vol = App.app.audio.getVolumeSetting();

				if(vol == 1){
					App.app.audio.setVolumeSetting(2);
				} else if(vol == 2){
					App.app.audio.setVolumeSetting(3);
				} else {
					App.app.audio.setVolumeSetting(1);
				}
			}


		} else if(src == mute_button){
			try{
				App.app.audio.mute();
			} catch (Exception e){
				//nothing
			}

		} else if(src == shuffle_button){
			if(App.app.getIsShuffling() == false){
				App.app.setIsShuffling(true);
			} else {
				App.app.setIsShuffling(false);
			}

		} else if(src == loop_button){
			if(App.app.getIsLooping() == false){
				App.app.setIsLooping(true);
			} else {
				App.app.setIsLooping(false);
			}


		} else if(src == delete_playlist_button){

			if(App.app.currentPlaylist.getDelete() == true){
				App.app.removePlaylist(App.app.currentPlaylist);

				App.app.setCurrentPlaylist(App.app.playlists.get(0)); //Should always return all songs

				updatePlaylistsDisplay();
				loadPlaylistToDisplay(App.app.currentPlaylist);

			} else {
				System.out.println("You cannot delete this playlist.");
			}
			
		} else if(src == settings_button){
			System.out.println(App.app.currentPlaylist);

		} else {

			boolean foundButton = false;

			//check all playlist and songpanel buttons
			for(JButton b : playButtons){
				if(src == b){
					//App.app.audio.loadSong(App.app.findSong(ids.get(playButtons.indexOf(b))).getFileLoc());
					//App.app.audio.play();
					App.app.currentSong = App.app.findSong(ids.get(playButtons.indexOf(b)));
					App.app.play();
					App.app.setTrackPos();
					foundButton = true;
				}
			}


			for(JButton b : addToButtons){
				//Add song to playlist
				if(src == b){
					String playlistName = JOptionPane.showInputDialog(null, "Please enter the playlist you want to add the song too.\n(If playlist does not exist, a new one will be created)");

					if(playlistName != null){

						Song song = App.app.findSong(ids.get(addToButtons.indexOf(b)));


						boolean exists = false;

						//Add to existing
						for(Playlist p : App.app.playlists){
							if (playlistName.equals(p.getName())){
								p.addSong(song);
								//loadPlaylistToDisplay(App.app.getCurrentPlaylist()); //Refresh
								exists = true;
							}
						}

						if (!exists){
							App.app.createPlaylist(playlistName, song);
						}

					}

					App.app.savePlaylists();
					foundButton = true;
				}
			}

			for(JButton b : playlistButtons){
				
				if(src == b){
					App.app.setCurrentPlaylist(App.app.findPlaylist(playlistIds.get(playlistButtons.indexOf(b))));
					loadPlaylistToDisplay(App.app.getCurrentPlaylist());
					foundButton = true;

					// for(SongPanel sp : songPanels){
					// 	System.out.println(sp);
					// }

				}
			}

			// for(JButton b : likeButtons){
				
			// 	if(src == b){
			// 		Song s = App.app.findSong(ids.get(likeButtons.indexOf(b)));
					
			// 		if(s.getLike() == true){
			// 			s.setLike(false);
			// 			b.setIcon(getIcon("Unliked"));
			// 		} else {
			// 			s.setLike(true);
			// 			b.setIcon(getIcon("Liked"));
			// 		}
					
			// 		foundButton = true;

			// 		App.app.saveSongs();

			// 	}
			// }

			if(foundButton == false){
				System.out.println("Button not found");
			}
			
		}



	}
}
