package Demos;

import java.io.IOException;
import javax.xml.bind.JAXBException;

import hexapaper.language.HPStrings;
import hexapaper.language.LanguageLoader;

public class LangTest {

	public static void main(String[] args) throws JAXBException, IOException {
		//LanguageLoader.createPreset("Czech");
		//LanguageLoader.createPreset("English");
		LanguageLoader.setupDir();
		HPStrings czech = LanguageLoader.load("Czech");
		HPStrings english = LanguageLoader.load("English");
		for(String s : LanguageLoader.getLanguages()){
			System.out.println(s);
		}
		System.out.println(czech.Yes);
		System.out.println(english.Yes);
	}

}
