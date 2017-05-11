package com.gft.amdc.jbehave;

import com.gft.amdc.jbehave.addShop.AddShopSteps;
import com.gft.amdc.jbehave.addShopTwice.AddShopTwiceSteps;
import com.gft.amdc.jbehave.findNearestShop.FindNearestShopSteps;
import org.jbehave.core.configuration.Configuration;
import org.jbehave.core.configuration.MostUsefulConfiguration;
import org.jbehave.core.io.LoadFromClasspath;
import org.jbehave.core.io.StoryFinder;
import org.jbehave.core.junit.JUnitStories;
import org.jbehave.core.junit.JUnitStory;
import org.jbehave.core.reporters.Format;
import org.jbehave.core.reporters.StoryReporterBuilder;
import org.jbehave.core.steps.InjectableStepsFactory;
import org.jbehave.core.steps.InstanceStepsFactory;
import org.jbehave.core.steps.ParameterControls;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

import static java.util.Arrays.asList;
import static org.jbehave.core.io.CodeLocations.codeLocationFromClass;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class JBehaveTest extends JUnitStories {

    @LocalServerPort
    private int port;

    @Override
    public Configuration configuration() {
        return new MostUsefulConfiguration()
                .useParameterControls(new ParameterControls().useDelimiterNamedParameters(true))
                // where to find the stories
                .useStoryLoader(new LoadFromClasspath(this.getClass()))
                // CONSOLE and TXT reporting
                .useStoryReporterBuilder(new StoryReporterBuilder().withDefaultFormats().withFormats(Format.CONSOLE, Format.TXT));
    }

    // Here we specify the steps classes
    @Override
    public InjectableStepsFactory stepsFactory() {
        // varargs, can have more that one steps classes
        return new InstanceStepsFactory(configuration(), new AddShopSteps(port), new AddShopTwiceSteps(port), new FindNearestShopSteps(port));
    }

    @Override
    protected List<String> storyPaths() {
        // Specify story paths as URLs
        //String codeLocation = codeLocationFromClass(this.getClass()).getFile();
        return new StoryFinder().findPaths("src/test/resources", "**/*.story", null);
    }

}
