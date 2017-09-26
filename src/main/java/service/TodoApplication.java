package service;

import io.dropwizard.Application;
import io.dropwizard.Configuration;
import io.dropwizard.setup.Environment;
import resource.TodoResource;

public class TodoApplication extends Application<Configuration> {

	public static void main(String[] args) throws Exception {
		new TodoApplication().run("server", "src/main/resources/config.yaml");
	}

	@Override
	public void run(Configuration configuration, Environment environment) throws Exception {
		environment.jersey().register(new TodoResource());
	}

}
