package test;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.UnsupportedAudioFileException;

public class Teste {
	public static void main(String[] args) {
        AudioInputStream soundtrack = loadSoundTrack("C:\\Users\\Ricardo\\Documents\\Workspaces\\myAngel\\MyAngel\\sounds\\Bastion_Build_that_Wall.wav");

        try{
	        Clip clip = AudioSystem.getClip();
	        clip.open(soundtrack);
	        clip.loop(-1);
	        clip.start();
        } catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static AudioInputStream loadSoundTrack(String name) {
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
	
	public static InputStream getResourceAsStream(String filename) {
		try {
			return new FileInputStream(filename);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return null;
		// return getClass().getClassLoader().
		// getResourceAsStream(filename);
	}
}
