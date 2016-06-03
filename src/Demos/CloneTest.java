package Demos;

import com.rits.cloning.Cloner;

import hexapaper.entity.Artefact;
import hexapaper.entity.Postava;

public class CloneTest {

	public static void main(String[] args) {
		Cloner cloner = new Cloner();
		cloner.setDumpClonedClasses(true);
		Artefact art = new Artefact("Bedna");
		Postava pos = new Postava("Karel");
		Artefact art_new = cloner.deepClone(art);
		System.out.println(art_new.toString());
	}

}
