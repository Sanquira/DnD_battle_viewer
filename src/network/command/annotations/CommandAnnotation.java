package network.command.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface CommandAnnotation {
	String name();
	String usage();
	String help();
	int max() default -1;
	int min() default -1;
	int arg() default -1;
	
}
