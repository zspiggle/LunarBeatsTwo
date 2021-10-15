import javax.swing.*;
import java.awt.*;

public class SongPanel{

  private int id;

  private String songName;
  private String artistName;

  private JButton likeButton;
  private JButton addToButton;
  private JButton playButton;


  public SongPanel(){
    songName = "";
    artistName = "";
    likeButton = null;

    addToButton = null;

    playButton = null;
  }


  public SongPanel(String song, String artist, int id, Display d){
    songName = song;
    artistName = artist;

    likeButton = new JButton("Like");

		//likeButton.addActionListener(Display.buttonListener); Do in display

    addToButton = new JButton(d.getIcon("Add"));

    playButton = new JButton(d.getIcon("Play"));

  }

  public JButton getLikeButton(){
    return likeButton;
  }

  public JButton getAddToButton(){
    return addToButton;
  }

  public JButton getPlayButton(){
    return playButton;
  }

  public JPanel toJPanel(){
    JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT, 75, 0));
		panel.setBackground(Display.bgColor);

    JLabel song_column = new JLabel(songName); 
		panel.add(song_column);
		song_column.setForeground(Color.WHITE);
		song_column.setFont(song_column.getFont().deriveFont(12.0f));
    song_column.setPreferredSize(new Dimension(125, 25));


		JLabel artist_column = new JLabel(artistName); 
		panel.add(artist_column);
		artist_column.setForeground(Color.WHITE);
		artist_column.setFont(artist_column.getFont().deriveFont(12.0f));
    artist_column.setPreferredSize(new Dimension(100, 25));


    if(likeButton != null){
      panel.add(likeButton);
    }
    if(addToButton != null){
      panel.add(addToButton);
    }
    if(playButton != null){
      panel.add(playButton);
    }

    return panel;
  }

}