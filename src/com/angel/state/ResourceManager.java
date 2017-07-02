package com.angel.state;

import java.awt.Graphics2D;
import java.awt.GraphicsConfiguration;
import java.awt.Image;
import java.awt.Transparency;
import java.awt.geom.AffineTransform;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import javax.sound.midi.Sequence;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.ImageIcon;

import com.angel.sound.MidiPlayer;
import com.angel.sound.Sound;
import com.angel.sound.SoundManager;

/**
 * The ResourceManager class loads resources like images and sounds.
 */
public class ResourceManager {

	private GraphicsConfiguration gc;
	private SoundManager soundManager;
	private MidiPlayer midiPlayer;

	/**
	 * Creates a new ResourceManager with the specified GraphicsConfiguration.
	 */
	public ResourceManager(GraphicsConfiguration gc, SoundManager soundManager, MidiPlayer midiPlayer) {
		this.gc = gc;
		this.soundManager = soundManager;
		this.midiPlayer = midiPlayer;

		try {
			java.util.Enumeration e = getClass().getClassLoader().getResources("com.angel.ResourceManager");
			while (e.hasMoreElements()) {
				System.out.println(e.nextElement());
			}

		} catch (IOException ex) {

		}

	}

	/**
	 * Gets an image from the images/ directory.
	 */
	public Image loadImage(String name) {
		String filename = "C:\\Users\\Ricardo\\Documents\\Workspaces\\myAngel\\MyAngel\\images\\" + name;
		return new ImageIcon(filename).getImage();
	}

	public Image getMirrorImage(Image image) {
		return getScaledImage(image, -1, 1);
	}

	public Image getFlippedImage(Image image) {
		return getScaledImage(image, 1, -1);
	}

	private Image getScaledImage(Image image, float x, float y) {

		// set up the transform
		AffineTransform transform = new AffineTransform();
		transform.scale(x, y);
		transform.translate((x - 1) * image.getWidth(null) / 2, (y - 1) * image.getHeight(null) / 2);

		// create a transparent (not translucent) image
		Image newImage = gc.createCompatibleImage(image.getWidth(null), image.getHeight(null), Transparency.BITMASK);

		// draw the transformed image
		Graphics2D g = (Graphics2D) newImage.getGraphics();
		g.drawImage(image, transform, null);
		g.dispose();

		return newImage;
	}

	public URL getResource(String filename) {
		return getClass().getClassLoader().getResource(filename);
	}

	public InputStream getResourceAsStream(String filename) {
		try {
			return new FileInputStream(filename);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return null;
		// return getClass().getClassLoader().
		// getResourceAsStream(filename);
	}

	public Sound loadSound(String name) {
		return soundManager.getSound(getResourceAsStream(name));
	}

	public Sequence loadSequence(String name) {
		return midiPlayer.getSequence(getResourceAsStream(name));
	}

	public AudioInputStream loadSoundTrack(String name) {
		try {
			InputStream audioSrc = getResourceAsStream(name);
			InputStream bufferedIn = new BufferedInputStream(audioSrc);
			AudioInputStream audioStream = AudioSystem.getAudioInputStream(bufferedIn);
			return audioStream;
//			return AudioSystem.getAudioInputStream(getResourceAsStream(name));
		} catch (UnsupportedAudioFileException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

}
