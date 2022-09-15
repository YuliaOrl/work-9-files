package guru.qa.filesParsing;

import com.fasterxml.jackson.databind.ObjectMapper;
import guru.qa.domain.Cat;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class JsonParseTest {

    ClassLoader classLoader = JsonParseTest.class.getClassLoader();

    @Test
    void jsonTestJackson() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        InputStream is = classLoader.getResourceAsStream("cat.json");
        Cat cat = mapper.readValue(is, Cat.class);
        assertThat(cat.name).isEqualTo("Kisunya");
        assertThat(cat.breed).isEqualTo("Maine Coon");
        assertThat(cat.gender).isEqualTo("female");
        assertThat(cat.vaccination).isEqualTo(true);
        assertThat(cat.properties.color).isEqualTo("brown");
        assertThat(cat.properties.age).isEqualTo(1);
        assertThat(cat.properties.weight).isEqualTo(3);
        assertThat(cat.feed).contains("meat", "fish", "cheese", "eggs");
        closeInputStream(is);
    }

    private void closeInputStream(InputStream is) throws IOException {
        if(is !=null)
            is.close();
    }
}
