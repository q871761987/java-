package com;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

public class StringFileInput extends Thread {
	int position = 0;
	int varTime = 0;
	long time = 80;
	Robot mRobot;
	String mFilePath;
	int deTime;

	public StringFileInput(String mFilePath ,int deTime) {
		super();
		this.mFilePath = mFilePath;
		this.deTime = deTime;
	}
	public StringFileInput(String mFilePath ,int deTime,int position) {
		super();
		this.mFilePath = mFilePath;
		this.deTime = deTime;
		this.position = position;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		writeTxt();
		try {
			mRobot = new Robot();
			
			try {
				sleep(3000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println("F2");
			mRobot.keyPress(KeyEvent.VK_F2);
			mRobot.keyRelease(KeyEvent.VK_F2);
			System.out.println("F2");

		} catch (AWTException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void writeTxt() {

		try {

			FileInputStream fileInputStream = new FileInputStream(new File(
					mFilePath));
			String content = getString(fileInputStream);
			// System.out.println(content);
			//
			mRobot = new Robot();
			sleep(deTime*1000);
			input(mRobot, content.substring(position));
		} catch (Exception e) {
			System.out.println("±¨´íÒ»´Î" + (position));
			writeTxt();

		}
	}

	public void input(Robot mRobot, String str) throws Exception {
		if (str != null) {
			for (char c : str.toCharArray()) {

				sleep(time);
				if (varTime % 2 == 0) {
					time = 150;
				} else if (varTime % 100 == 0) {
					++varTime ;
					throw new Exception("lalal");
				} else {
					time = 150;
				}
				if (c == 32)
					time = 100;
				inputStr(mRobot, c + "");
				varTime ++;
				position++;

			}
		}
	}

	public static void inputStr(Robot robot, String str) {// ×Ö·û´®
		Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
		clipboard.setContents(new StringSelection(str), null);
		robot.keyPress(KeyEvent.VK_CONTROL);
		robot.keyPress(KeyEvent.VK_V);
		robot.keyRelease(KeyEvent.VK_V);
		robot.keyRelease(KeyEvent.VK_CONTROL);
	}

	public static String getString(InputStream inputStream) {
		InputStreamReader inputStreamReader = null;
		try {
			inputStreamReader = new InputStreamReader(inputStream, "utf8");
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		}
		BufferedReader reader = new BufferedReader(inputStreamReader);
		StringBuffer sb = new StringBuffer("");
		String line;
		try {
			while ((line = reader.readLine()) != null) {
				sb.append(line);
				sb.append("\n");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return sb.toString();
	}

}
